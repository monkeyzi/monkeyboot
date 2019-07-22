
package com.monkeyzi.mboot.security.handler;

import com.monkeyzi.mboot.common.core.constant.SecurityConstants;
import com.monkeyzi.mboot.common.core.holder.SpringContextHolder;
import com.monkeyzi.mboot.common.security.handler.AbstractAuthenticationSuccessEventHandler;
import com.monkeyzi.mboot.log.MbootLoginLogEvent;
import com.monkeyzi.mboot.utils.util.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@Component
public class MbootAuthenticationSuccessEventHandler extends AbstractAuthenticationSuccessEventHandler {

	/**
	 * 处理登录成功方法
	 * <p>
	 * 获取到登录的authentication 对象
	 *
	 * @param authentication 登录对象
	 */
	@Override
	public void handle(Authentication authentication) {
		HttpServletRequest request = WebUtils.getRequest();
		String path = request.getRequestURI();
		if (path.equals(SecurityConstants.OAUTH_TOKEN_URL)) {
			log.info("用户={}登录成功", authentication.getPrincipal());
			SpringContextHolder.publishEvent(new MbootLoginLogEvent(request));
		}
	}
}
