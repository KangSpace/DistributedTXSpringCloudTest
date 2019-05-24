package org.kangspace.springcloud.dtxs.one.service.impl;

import com.codingapi.txlcn.tc.annotation.DTXPropagation;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import org.kangspace.springcloud.dtxs.common.utils.SnowflakeIdGenerator;
import org.kangspace.springcloud.dtxs.common.utils.exception.DistributedTxException;
import org.kangspace.springcloud.dtxs.common.utils.exception.RandomExceptionUtil;
import org.kangspace.springcloud.dtxs.common.utils.req.ResponseCode;
import org.kangspace.springcloud.dtxs.common.utils.req.ResponseEntity;
import org.kangspace.springcloud.dtxs.one.model.OpTableOne;
import org.kangspace.springcloud.dtxs.one.service.base.BaseService;
import org.kangspace.springcloud.dtxs.one.service.iface.IServiceOneService;
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
public class ServiceOneService extends BaseService implements IServiceOneService {
    @Override
    public List<OpTableOne> queryOpTablesOne() {
        Example example = new Example(OpTableOne.class);
        example.orderBy("cTime").desc();
        return mapperFacade.getOpTableOneMapper().selectByExample(example);
    }

    @Override
    public int saveOneOpInfo(OpTableOne one) {
        //随机抛出异常
        RandomExceptionUtil.random("ServiceOneService Random Exception");
        return mapperFacade.getOpTableOneMapper().insert(one);
    }

    /**
     * 此处@LcnTransaction(propagation = DTXPropagation.REQUIRED) 需要使用required,因为其调用了ServiceTwo
     * @param calledFrom
     * @return
     */
    @Override
    //@TxcTransaction(propagation = DTXPropagation.SUPPORTS)
    @LcnTransaction(propagation = DTXPropagation.REQUIRED)
    public int saveOneAndMore(String calledFrom) {
        calledFrom = (calledFrom!=null?calledFrom:"")+"ServiceOne->";
        Date nowDate = new Date();
        //保存ServiceOne
        OpTableOne one = new OpTableOne();
        one.setOpId(SnowflakeIdGenerator.getInstance().nextId()+"");
        one.setcTime(nowDate);
        one.setOpName("ServiceOneService called,calledFrom:"+calledFrom);
        one.setOpDesc(one.getOpName()+ " in " + nowDate.toString());
//        int i = springApplicationContext.getBean(ServiceOneService.class).saveOneOpInfo(one);
        int i = saveOneOpInfo(one);
        System.out.println("saveOneOpInfo:"+i+","+one);

        //保存ServiceTwo
        i += serviceTwoSave(calledFrom);
        return i;
    }

    public Integer serviceTwoSave(String calledFrom){
        ResponseEntity<Integer> twoResponse = serviceTwoClient.twoSave(calledFrom);
        if(twoResponse != null && ResponseCode.SUCCESS.getCode().equals(twoResponse.getCode())){
            return twoResponse.getData();
        }
        throw new DistributedTxException("serviceTwoSave Exception:"+(twoResponse!=null?twoResponse.toString():null));
    }
}
