package org.kangspace.springcloud.dtxs.two.dao;


import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.common.example.UpdateByExampleMapper;
import tk.mybatis.mapper.common.ids.DeleteByIdsMapper;
import tk.mybatis.mapper.common.ids.SelectByIdsMapper;

/**
 * {@link tk.mybatis.mapper.common.MySqlMapper}
 * @param <T>
 */
public interface MyMapper<T> extends Mapper<T>
        , DeleteByIdsMapper<T>, SelectByIdsMapper<T>,UpdateByExampleMapper<T>, MySqlMapper<T>{
}
