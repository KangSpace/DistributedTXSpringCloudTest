package org.kangspace.springcloud.dtxs.caller.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.kangspace.springcloud.dtxs.common.utils.req.ResponseCode;
import org.kangspace.springcloud.dtxs.common.utils.req.ResponseEntity;
import org.kangspace.springcloud.dtxs.caller.CallerApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * CallerController
 * 在{@link CallerApplication#main(String[])} 中启动}
 *
 *
 * @author kango2ger@gmail.com
 */
@RestController
@EnableAutoConfiguration
@Api(value = "CallerController", description = "CallerController", tags = "caller")
@RequestMapping(value = "v1/api/callers")
public class CallerController extends BaseController{

    @ApiOperation(value = "获取所有Caller信息"
            , notes = "获取所有Caller信息", httpMethod = "GET"
            , produces = MediaType.ALL_VALUE)
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity callers() {
        return new ResponseEntity(ResponseCode.SUCCESS,serviceFacade.getCallerService().queryOpTablesCallers());
    }
    @ApiOperation(value = "保存Caller->ServiceOne->ServiceTwo信息"
            , notes = "保存Caller->ServiceOne->ServiceTwo信息", httpMethod = "POST"
            , produces = MediaType.ALL_VALUE)
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity callersSave() {
        return new ResponseEntity(ResponseCode.SUCCESS,serviceFacade.getCallerService().saveCallerAndMore());
    }
}
