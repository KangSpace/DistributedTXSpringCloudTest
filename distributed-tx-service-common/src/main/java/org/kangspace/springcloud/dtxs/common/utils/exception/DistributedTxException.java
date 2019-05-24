package org.kangspace.springcloud.dtxs.common.utils.exception;

/**
 * 2019/5/23 9:47
 *
 * @author kango2ger@gmail.com
 */
public class DistributedTxException extends RuntimeException{
    public DistributedTxException() {
    }

    public DistributedTxException(String message) {
        super(message);
    }

    public DistributedTxException(String message, Throwable cause) {
        super(message, cause);
    }

    public DistributedTxException(Throwable cause) {
        super(cause);
    }

    public DistributedTxException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
