package org.kangspace.springcloud.dtxs.common.utils.req;

import lombok.Data;

/**
 * 2019/5/9 14:41
 *
 * @author kango2ger@gmail.com
 */
@Data
public class ResponseEntity<T> {
    private int code;
    private String msg;
    private T data;
    public ResponseEntity(ResponseCode responseCode, T data) {
        this.code = responseCode.getCode();
        this.msg = responseCode.getMessage();
        this.data = data;
    }

    public ResponseEntity(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseEntity(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResponseEntity(int code) {
        this.code = code;
    }

    public ResponseEntity() {
    }
}
