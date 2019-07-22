
package com.monkeyzi.mboot.common.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.http.HttpStatus;

@JsonSerialize(using = MbootAuth2ExceptionSerializer.class)
public class NoHandlerFoundException extends MbootAuth2Exception {

	public NoHandlerFoundException(String msg, Throwable t) {
		super(msg);
	}
	public NoHandlerFoundException(String msg){
		super(msg);
	}

	@Override
	public String getOAuth2ErrorCode() {
		return "404";
	}

	@Override
	public int getHttpErrorCode() {
		return HttpStatus.NOT_FOUND.value();
	}

}
