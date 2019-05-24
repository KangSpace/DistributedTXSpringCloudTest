package org.kangspace.springcloud.dtxs.caller.service.iface;

import org.kangspace.springcloud.dtxs.caller.model.OpTableCaller;
import org.kangspace.springcloud.dtxs.caller.service.base.IBaseService;

import java.util.List;

/**
 * @author kango2ger@gmail.com
 * @desc
 * @date 2019/5/9 9:36
 */
public interface ICallerService extends IBaseService {

    int saveCallerOpInfo(OpTableCaller caller);

    List<OpTableCaller> queryOpTablesCallers();

    int saveCallerAndMore();
}
