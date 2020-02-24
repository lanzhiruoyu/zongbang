package com.zongbang.service;

import com.github.pagehelper.PageInfo;
import com.zongbang.pojo.Para;

import java.util.List;

/****
 * @Author:www.itheima.com
 * @Description:Para业务层接口
 * @Date www.itheima.com 0:16
 *****/
public interface ParaService {

    /***
     * Para多条件分页查询
     * @param para
     * @param page
     * @param size
     * @return
     */
    PageInfo<Para> findPage(Para para, int page, int size);

    /***
     * Para分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<Para> findPage(int page, int size);

    /***
     * Para多条件搜索方法
     * @param para
     * @return
     */
    List<Para> findList(Para para);

    /***
     * 删除Para
     * @param id
     */
    void delete(Integer id);

    /***
     * 修改Para数据
     * @param para
     */
    void update(Para para);

    /***
     * 新增Para
     * @param para
     */
    void add(Para para);

    /**
     * 根据ID查询Para
     *
     * @param id
     * @return
     */
    Para findById(Integer id);

    /***
     * 查询所有Para
     * @return
     */
    List<Para> findAll();

    /**
     * @Author HuiLiao
     * @Description 通过分类id查询参数
     * @Date 21:33 2020/2/17
     * @Param [category]
     * @return java.util.List<com.zongbang.goods.pojo.Para>
     **/
    List<Para> findByCategoryId(Integer categoryId);
}
