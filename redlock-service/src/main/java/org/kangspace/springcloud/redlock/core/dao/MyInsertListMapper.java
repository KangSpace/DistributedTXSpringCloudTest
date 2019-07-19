package org.kangspace.springcloud.redlock.core.dao;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import tk.mybatis.mapper.provider.SpecialProvider;

import java.util.List;

/**
 * refrece from  {@link tk.mybatis.mapper.common.special.InsertListMapper}
 * @param <T>
 */
public interface MyInsertListMapper<T> {
    @Options(
            useGeneratedKeys = true,
            keyProperty = "ref"
    )
    @InsertProvider(
            type = SpecialProvider.class,
            method = "dynamicSQL"
    )
    int insertList(List<T> var1);
}
