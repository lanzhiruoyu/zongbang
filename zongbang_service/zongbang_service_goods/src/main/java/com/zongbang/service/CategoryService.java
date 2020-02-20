package com.zongbang.service;

import com.github.pagehelper.PageInfo;
import com.zongbang.goods.pojo.Category;

import java.util.List;

/****
 * @Author:www.itheima.com
 * @Description:Category业务层接口
 * @Date www.itheima.com 0:16
 *****/
public interface CategoryService {

    /***
     * Category多条件分页查询
     * @param category
     * @param page
     * @param size
     * @return
     */
    PageInfo<Category> findPage(Category category, int page, int size);

    /***
     * Category分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<Category> findPage(int page, int size);

    /***
     * Category多条件搜索方法
     * @param category
     * @return
     */
    List<Category> findList(Category category);

    /***
     * 删除Category
     * @param id
     */
    void delete(Integer id);

    /***
     * 修改Category数据
     * @param category
     */
    void update(Category category);

    /***
     * 新增Category
     * @param category
     */
    void add(Category category);

    /**
     * 根据ID查询Category
     *
     * @param id
     * @return
     */
    Category findById(Integer id);

    /***
     * 查询所有Category
     * @return
     */
    List<Category> findAll();


    /**
     * @Author HuiLiao
     * @Description 通过父Id查找子分类
     * @Date 23:28 2020/2/16
     * @Param [id]
     * @return java.util.List<com.zongbang.goods.pojo.Category>
     **/
    List<Category> findByParentId(Integer pid);
}
