package com.zrs.router.api.exception;

/**
 * @author zhang
 * @date 2020/3/29 0029
 * @time 16:09
 * @describe TODO
 */
public class NoRouteFoundException extends RuntimeException {
    /**
     * Constructs a new {@code RuntimeException} with the current stack trace
     * and the specified detail message.
     *
     * @param detailMessage the detail message for this exception.
     */
    public NoRouteFoundException(String detailMessage) {
        super(detailMessage);
    }
}