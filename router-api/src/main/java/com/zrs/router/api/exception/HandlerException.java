package com.zrs.router.api.exception;

/**
 * @author zhang
 * @date 2020/3/29 0029
 * @time 16:10
 * @describe TODO
 */
public class HandlerException extends RuntimeException {
    public HandlerException(String detailMessage) {
        super(detailMessage);
    }
}
