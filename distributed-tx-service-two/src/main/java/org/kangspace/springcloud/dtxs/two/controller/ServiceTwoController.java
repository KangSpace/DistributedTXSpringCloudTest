package org.kangspace.springcloud.dtxs.two.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.kangspace.springcloud.dtxs.common.utils.req.ResponseCode;
import org.kangspace.springcloud.dtxs.common.utils.req.ResponseEntity;
import org.kangspace.springcloud.dtxs.two.ServiceTwoApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * CallerController
 * 在{@link ServiceTwoApplication#main(String[])} 中启动}
 *
 *
 * @author kango2ger@gmail.com
 */
@RestController
@EnableAutoConfiguration
@Api(value = "ServiceTwoController", description = "ServiceTwoController", tags = "serviceTwo")
@RequestMapping(value = "v1/api/servicetwos")
public class ServiceTwoController extends BaseController{

    @ApiOperation(value = "获取所有ServiceOne信息"
            , notes = "获取所有ServiceOne信息", httpMethod = "GET"
            , produces = MediaType.ALL_VALUE)
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity serviceTwos() {
        return new ResponseEntity(ResponseCode.SUCCESS,serviceFacade.getServiceTwoService().queryOpTablesTwo());
    }

    /**
     * @param calledFrom 服务调用链
     * @author kango2ger@gmail.com
     * @date 2019/5/23 9:59
     * @return
     */
    @ApiOperation(value = "保存ServiceOne->ServiceTwo信息"
            , notes = "保存ServiceOne->ServiceTwo信息", httpMethod = "POST"
            , produces = MediaType.ALL_VALUE)
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity twoSave(@RequestParam(value = "calledFrom" ,required = false) String calledFrom) {
        return new ResponseEntity(ResponseCode.SUCCESS,serviceFacade.getServiceTwoService().saveTwo(calledFrom));
    }
}
