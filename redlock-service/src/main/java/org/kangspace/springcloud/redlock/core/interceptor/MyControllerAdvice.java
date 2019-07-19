package org.kangspace.springcloud.redlock.core.interceptor;


import org.kangspace.springcloud.dtxs.common.utils.req.ResponseCode;
import org.kangspace.springcloud.dtxs.common.utils.req.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * Controller异常处理
 * @author kango2ger@gmail.com
 * @date 2019/5/20 17:23
 * @return
 */
@ControllerAdvice
public class MyControllerAdvice {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity exceptionHandlerWithEntity(HttpServletResponse httpServletResponse, Exception e) {
        httpServletResponse.setStatus(500);
        logger.error(e.getMessage(),e);
        return new ResponseEntity(ResponseCode.INTERNAL_SERVER_ERROR.getCode(),e.getMessage(),null);
    }
}
