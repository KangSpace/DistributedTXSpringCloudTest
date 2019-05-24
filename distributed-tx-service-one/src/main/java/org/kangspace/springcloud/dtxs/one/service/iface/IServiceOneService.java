package org.kangspace.springcloud.dtxs.one.service.iface;

import org.kangspace.springcloud.dtxs.one.model.OpTableOne;
import org.kangspace.springcloud.dtxs.one.service.base.IBaseService;

import java.util.List;

/**
 * @author kango2ger@gmail.com
 * @desc
 * @date 2019/5/9 9:36
 */
public interface IServiceOneService extends IBaseService {

    List<OpTableOne> queryOpTablesOne();

    int saveOneOpInfo(OpTableOne one);

    int saveOneAndMore(String calledFrom);
}
