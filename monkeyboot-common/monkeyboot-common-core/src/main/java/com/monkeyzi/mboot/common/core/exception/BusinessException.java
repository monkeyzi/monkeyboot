package com.monkeyzi.mboot.common.core.exception;

import com.monkeyzi.mboot.common.core.result.ResponseCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 系统业务异常
 */
@Getter
@Setter
public class BusinessException extends RuntimeException {

    private int code;
    public BusinessException(){}
    public BusinessException(Throwable t){super(t);}
    public BusinessException(String msg){
        super(msg);
        this.code=ResponseCode.ERROR.getCode();
    }
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(int code, String msgFormat, Object... args) {
        super(String.format(msgFormat, args));
        this.code = code;
    }
}
