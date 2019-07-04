package com.monkeyzi.mboot.common.security.service;

import com.monkeyzi.mboot.common.core.constant.SecurityConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.util.List;

/**
 * client 客户端service
 */
@Slf4j
public class MbootClientDetailService extends JdbcClientDetailsService {
    private RedisTemplate<String, Object> redisTemplate;

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public MbootClientDetailService(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
        // 先从redis获取
        ClientDetails clientDetails = (ClientDetails) redisTemplate.opsForValue().get(clientRedisKey(clientId));
        if (clientDetails == null) {
            //从数据库中获取
            clientDetails = cacheAndGetClient(clientId);
        }
        return clientDetails;
    }

    /**
     * 从数据库中读取并写入redis缓存
     * @param clientId
     * @return
     */
    private ClientDetails cacheAndGetClient(String clientId) {
        // 从数据库中读取
        ClientDetails clientDetails = null;
        try {
            clientDetails = super.loadClientByClientId(clientId);
            if (clientDetails != null) {
                // 写入redis缓存
                redisTemplate.opsForValue().set(clientRedisKey(clientId), clientDetails);
                log.info("缓存clientId:{},{}", clientId, clientDetails);
            }
        } catch (NoSuchClientException e) {
            log.error("clientId:{},{}", clientId, clientId);
        } catch (InvalidClientException e) {
            log.error("cacheAndGetClient-invalidClient:{}", clientId, e);
        }
        return clientDetails;
    }

    private String clientRedisKey(String clientId) {
        return SecurityConstants.CACHE_CLIENT_KEY + ":" + clientId;
    }

    @Override
    public void updateClientDetails(ClientDetails clientDetails) {
        super.updateClientDetails(clientDetails);
        cacheAndGetClient(clientDetails.getClientId());
    }

    @Override
    public void updateClientSecret(String clientId, String secret) {
        super.updateClientSecret(clientId, secret);
        cacheAndGetClient(clientId);
    }

    @Override
    public void removeClientDetails(String clientId) {
        super.removeClientDetails(clientId);
        removeRedisCache(clientId);
    }

    /**
     * 删除redis缓存
     *
     * @param clientId
     */
    private void removeRedisCache(String clientId) {
        redisTemplate.delete(clientRedisKey(clientId));
    }
    /**
     * 将oauth_client_details全表刷入redis
     */
    public void loadAllClientToCache() {
        List<ClientDetails> list = super.listClientDetails();
        if (CollectionUtils.isEmpty(list)) {
            log.error("oauth_client_details表数据为空，请检查");
            return;
        }
        list.parallelStream().forEach(client -> redisTemplate.opsForValue().set(clientRedisKey(client.getClientId()), client));
    }
}
