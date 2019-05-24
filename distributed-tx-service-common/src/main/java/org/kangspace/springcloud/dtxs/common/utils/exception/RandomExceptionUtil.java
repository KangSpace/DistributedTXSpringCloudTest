package org.kangspace.springcloud.dtxs.common.utils.exception;

/**
 * 随机异常
 * 2019/5/17 17:49
 *
 * @author kango2ger@gmail.com
 */
public class RandomExceptionUtil{

    public static void random(){
        random("");
    }
    public static void random(String message){
        Long time = System.currentTimeMillis();
        if (time % 10 == 0){
            throw new RandomException(time+" - "+message);
        }
    }

    public static class RandomException extends RuntimeException{
        public RandomException() {
        }

        public RandomException(String message) {
            super(message);
        }

        public RandomException(String message, Throwable cause) {
            super(message, cause);
        }

        public RandomException(Throwable cause) {
            super(cause);
        }

        public RandomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }
}
