package com.monkeyzi.mboot.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * 自定义OAuth2Exception
 */
@JsonSerialize(using = MbootAuth2ExceptionSerializer.class)
public class MbootAuth2Exception extends OAuth2Exception {
	@Getter
	private String errorCode;

	public MbootAuth2Exception(String msg) {
		super(msg);
	}

	public MbootAuth2Exception(String msg, String errorCode) {
		super(msg);
		this.errorCode = errorCode;
	}
}
