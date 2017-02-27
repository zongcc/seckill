package com.zongcc.exception;

/**
 * Created by chunchengzong on 2016-09-20.
 */
public class SeckillException extends RuntimeException {

    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
