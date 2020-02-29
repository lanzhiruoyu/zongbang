package com.zongbang.service;

import java.util.Map;

/**
 * @ClassName: SkuService
 * @Description: TODO
 * @Author: HuiLiao
 * @Date: 2020/2/26 12:51
 */
public interface SkuService {

    /**
     * @Author HuiLiao
     * @Description 导入数据到索引库
     * @Date 12:53 2020/2/26
     * @Param []
     * @return void
     **/
    void importData();

    /**
     * @Author HuiLiao
     * @Description 根据多条件分页查询
     * @Date 14:34 2020/2/28
     * @Param [searchMap]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    Map<String,Object> search(Map<String,String> searchMap);
}
