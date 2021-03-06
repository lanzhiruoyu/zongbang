package com.zongbang.service;

import com.github.pagehelper.PageInfo;
import com.zongbang.pojo.Spec;

import java.util.List;

/****
 * @Author:www.itheima.com
 * @Description:Spec业务层接口
 * @Date www.itheima.com 0:16
 *****/
public interface SpecService {

    /***
     * Spec多条件分页查询
     * @param spec
     * @param page
     * @param size
     * @return
     */
    PageInfo<Spec> findPage(Spec spec, int page, int size);

    /***
     * Spec分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<Spec> findPage(int page, int size);

    /***
     * Spec多条件搜索方法
     * @param spec
     * @return
     */
    List<Spec> findList(Spec spec);

    /***
     * 删除Spec
     * @param id
     */
    void delete(Integer id);

    /***
     * 修改Spec数据
     * @param spec
     */
    void update(Spec spec);

    /***
     * 新增Spec
     * @param spec
     */
    void add(Spec spec);

    /**
     * 根据ID查询Spec
     *
     * @param id
     * @return
     */
    Spec findById(Integer id);

    /***
     * 查询所有Spec
     * @return
     */
    List<Spec> findAll();

    /**
     * @Author HuiLiao
     * @Description 通过分类查询规格集合
     * @Date 21:23 2020/2/17
     * @Param [categoryId]
     * @return java.util.List<com.zongbang.goods.pojo.Spec>
     **/
    List<Spec> findByCategoryId(Integer categoryId);
}
