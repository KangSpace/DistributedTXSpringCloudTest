package org.kangspace.springcloud.dtxs.two.service.impl;

import com.codingapi.txlcn.tc.annotation.DTXPropagation;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import org.kangspace.springcloud.dtxs.common.utils.SnowflakeIdGenerator;
import org.kangspace.springcloud.dtxs.common.utils.exception.RandomExceptionUtil;
import org.kangspace.springcloud.dtxs.two.model.OpTableTwo;
import org.kangspace.springcloud.dtxs.two.service.base.BaseService;
import org.kangspace.springcloud.dtxs.two.service.iface.IServiceTwoService;
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
public class ServiceTwoService extends BaseService implements IServiceTwoService {
    @Override
    public List<OpTableTwo> queryOpTablesTwo() {
        Example example = new Example(OpTableTwo.class);
        example.orderBy("cTime").desc();
        return mapperFacade.getOpTableTwoMapper().selectByExample(example);
    }

    @Override
    public int saveTwoOpInfo(OpTableTwo two) {
        //随机抛出异常
        RandomExceptionUtil.random("ServiceTwoService Random Exception");
        return mapperFacade.getOpTableTwoMapper().insert(two);
    }


    @Override
    //@TccTransaction(propagation = DTXPropagation.SUPPORTS)
    @LcnTransaction(propagation = DTXPropagation.SUPPORTS)
    public int saveTwo(String calledFrom) {
        calledFrom = (calledFrom!=null?calledFrom:"")+"ServiceTwo->";
        Date nowDate = new Date();
        //保存ServiceTwo
        OpTableTwo two = new OpTableTwo();
        two.setOpId(SnowflakeIdGenerator.getInstance().nextId()+"");
        two.setcTime(nowDate);
        two.setOpName("ServiceTwoService called,calledFrom:"+calledFrom);
        two.setOpDesc(two.getOpName()+ " in " + nowDate.toString());
//        int i = springApplicationContext.getBean(ServiceTwoService.class).saveTwoOpInfo(two);
        int i = saveTwoOpInfo(two);
        System.out.println("saveTwoOpInfo:"+i+","+two);
        return i;
    }
}
