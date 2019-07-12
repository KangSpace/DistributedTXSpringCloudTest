package org.kangspace.springcloud.graphql.core.service.iface;

import org.kangspace.springcloud.graphql.core.model.OpTableCaller;
import org.kangspace.springcloud.graphql.core.service.base.IBaseService;

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
