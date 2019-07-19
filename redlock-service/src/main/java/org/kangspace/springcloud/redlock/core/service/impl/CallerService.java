package org.kangspace.springcloud.redlock.core.service.impl;

import org.kangspace.springcloud.dtxs.common.utils.SnowflakeIdGenerator;
import org.kangspace.springcloud.dtxs.common.utils.SpringApplicationContext;
import org.kangspace.springcloud.dtxs.common.utils.exception.DistributedTxException;
import org.kangspace.springcloud.dtxs.common.utils.exception.RandomExceptionUtil;
import org.kangspace.springcloud.dtxs.common.utils.req.ResponseCode;
import org.kangspace.springcloud.dtxs.common.utils.req.ResponseEntity;
import org.kangspace.springcloud.redlock.core.model.OpTableCaller;
import org.kangspace.springcloud.redlock.core.service.base.BaseService;
import org.kangspace.springcloud.redlock.core.service.iface.ICallerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author kango2ger@gmail.com
 * @desc
 * @date 2019/5/9 9:36
 */
@Service
public class CallerService extends BaseService implements ICallerService {

    @Autowired
    private SpringApplicationContext springApplicationContext;

    @Override
    public int saveCallerOpInfo(OpTableCaller caller) {
        //随机抛出异常
        RandomExceptionUtil.random("CallerService Exception");
        return mapperFacade.getOpTableCallerMapper().insert(caller);
    }

    @Override
    public List<OpTableCaller> queryOpTablesCallers() {
        Example example = new Example(OpTableCaller.class);
        example.orderBy("cTime").desc();
        return mapperFacade.getOpTableCallerMapper().selectByExample(example);
    }

    @Override
    public int saveCallerAndMore() {
        Date nowDate = new Date();
        //保存Caller
        OpTableCaller caller = new OpTableCaller();
        caller.setOpId(SnowflakeIdGenerator.getInstance().nextId()+"");
        caller.setcTime(nowDate);
        caller.setOpName("ServiceCaller called");
        caller.setOpDesc("ServiceCaller called in " + nowDate.toString());
        int i = springApplicationContext.getBean(CallerService.class).saveCallerOpInfo(caller);
        String calledFrom = "ServiceCaller->";
        //保存ServiceOne
        i += serviceOneSave(calledFrom);
        return i;
    }


    public Integer serviceOneSave(String calledFrom){
        ResponseEntity<Integer> oneResponse = serviceOneClient.serviceOneSave(calledFrom);
        if(oneResponse != null && ResponseCode.SUCCESS.getCode().equals(oneResponse.getCode())){
            return oneResponse.getData();
        }
        throw new DistributedTxException("serviceOneSave Exception:"+(oneResponse!=null?oneResponse.toString():null));
    }
}
