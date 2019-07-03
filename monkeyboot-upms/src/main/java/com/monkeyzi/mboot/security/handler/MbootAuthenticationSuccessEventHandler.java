
package com.monkeyzi.mboot.security.handler;

import com.monkeyzi.mboot.utils.util.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
		log.info("用户：{} 登录成功", authentication.getPrincipal());
		HttpServletRequest request=WebUtils.getRequest();
		log.info("用户登录的地址为 path={}",request.getRequestURI());
		System.out.println("1111"+SecurityContextHolder.getContext().getAuthentication());
	}
}
