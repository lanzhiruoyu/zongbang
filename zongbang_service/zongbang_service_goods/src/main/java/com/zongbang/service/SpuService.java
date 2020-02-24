package com.zongbang.service;

import com.github.pagehelper.PageInfo;
import com.zongbang.pojo.Goods;
import com.zongbang.pojo.Spu;

import java.util.List;

/****
 * @Author:www.itheima.com
 * @Description:Spu业务层接口
 * @Date www.itheima.com 0:16
 *****/
public interface SpuService {

    /***
     * Spu多条件分页查询
     * @param spu
     * @param page
     * @param size
     * @return
     */
    PageInfo<Spu> findPage(Spu spu, int page, int size);

    /***
     * Spu分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<Spu> findPage(int page, int size);

    /***
     * Spu多条件搜索方法
     * @param spu
     * @return
     */
    List<Spu> findList(Spu spu);

    /***
     * 删除Spu
     * @param id
     */
    void delete(String id);

    /***
     * 修改Spu数据
     * @param spu
     */
    void update(Spu spu);

    /***
     * 新增Spu
     * @param spu
     */
    void add(Spu spu);

    /**
     * 根据ID查询Spu
     *
     * @param id
     * @return
     */
    Spu findById(String id);

    /***
     * 查询所有Spu
     * @return
     */
    List<Spu> findAll();

    /**
     * @Author HuiLiao
     * @Description 添加商品信息
     * @Date 23:03 2020/2/17
     * @Param
     * @return
     **/
    void saveGoods(Goods goods);


    /**
     * @Author HuiLiao
     * @Description 通过spuId查询商品信息
     * @Date 16:52 2020/2/21
     * @Param [id]
     * @return com.zongbang.goods.pojo.Goods
     **/
    Goods findGoodsById(String spuId);

    /**
     * @Author HuiLiao
     * @Description 商品审核
     * @Date 18:47 2020/2/21
     * @Param [spuId]
     * @return void
     **/
    void audit(String spuId);

    /**
     * @Author HuiLiao
     * @Description 商品下架
     * @Date 19:30 2020/2/21
     * @Param [spuId]
     * @return void
     **/
    void pull(String spuId);

    /**
     * @Author HuiLiao
     * @Description 商品上架
     * @Date 19:31 2020/2/21
     * @Param [spuId]
     * @return void
     **/
    void put(String spuId);

    /**
     * @Author HuiLiao
     * @Description 商品批量上架
     * @Date 19:45 2020/2/21
     * @Param [spuIds]
     * @return void
     **/
    void putMany(String[] spuIds);

    /**
     * @Author HuiLiao
     * @Description 找回商品
     * @Date 22:39 2020/2/21
     * @Param [spuId]
     * @return void
     **/
    void restore(String spuId);

    /**
     * @Author HuiLiao
     * @Description 物理删除商品
     * @Date 22:43 2020/2/21
     * @Param [spuId]
     * @return void
     **/
    void realDel(String spuId);
}
