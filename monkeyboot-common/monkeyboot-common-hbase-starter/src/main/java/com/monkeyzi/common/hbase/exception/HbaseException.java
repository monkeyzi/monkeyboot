package com.monkeyzi.common.hbase.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * hbase异常
 */
@Getter
@Setter
public class HbaseException extends RuntimeException {

    private int code;
    public HbaseException(){}
    public HbaseException(Throwable t){super(t);}
    public HbaseException(String msg){
        super(msg);
    }
    public HbaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public HbaseException(int code, String message) {
        super(message);
        this.code = code;
    }

    public HbaseException(int code, String msgFormat, Object... args) {
        super(String.format(msgFormat, args));
        this.code = code;
    }
}
