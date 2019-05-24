package org.kangspace.springcloud.dtxs.common.utils.req;

/**
 * 2019/5/9 14:48
 *
 * @author kango2ger@gmail.com
 */
public enum ResponseCode {
        SUCCESS(1, "操作成功"),
        ALL_DATA(2, "返回全部数据"),
        NO_DATA(3, "没有数据"),
        INTERNAL_SERVER_ERROR(-1, "系统开小差啦~ 请重试"),
        VALIDATION_ERROR(-2, "参数错误");

        private Integer code;
        private String message;

        private ResponseCode(Integer code, String message) {
            this.code = code;
            this.message = message;
        }

        public Integer getCode() {
            return this.code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
}
