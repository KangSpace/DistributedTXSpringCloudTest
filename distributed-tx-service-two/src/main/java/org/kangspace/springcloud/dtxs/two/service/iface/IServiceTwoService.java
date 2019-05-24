package org.kangspace.springcloud.dtxs.two.service.iface;

import org.kangspace.springcloud.dtxs.two.model.OpTableTwo;
import org.kangspace.springcloud.dtxs.two.service.base.IBaseService;

import java.util.List;

/**
 * @author kango2ger@gmail.com
 * @desc
 * @date 2019/5/9 9:36
 */
public interface IServiceTwoService extends IBaseService {
    List<OpTableTwo> queryOpTablesTwo();

    int saveTwoOpInfo(OpTableTwo two);

    int saveTwo(String calledFrom);
}
