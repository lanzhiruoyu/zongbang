package com.zongbang.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zongbang.dao.*;
import com.zongbang.goods.pojo.*;
import com.zongbang.pojo.*;
import com.zongbang.service.SpuService;
import org.assertj.core.util.Arrays;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/****
 * @Author:www.itheima.com
 * @Description:Spu业务层接口实现类
 * @Date www.itheima.com 0:16
 *****/
@Service
public class SpuServiceImpl implements SpuService {

    @Resource
    private SpuMapper spuMapper;
    @Resource
    private SkuMapper skuMapper;
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private BrandMapper brandMapper;
    @Resource
    private CategoryBrandMapper categoryBrandMapper;
    @Resource
    private IdWorker idWorker;
    /**
     * Spu条件+分页查询
     *
     * @param spu  查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Spu> findPage(Spu spu, int page, int size) {
        //分页
        PageHelper.startPage(page, size);
        //搜索条件构建
        Example example = createExample(spu);
        //执行搜索
        return new PageInfo<Spu>(spuMapper.selectByExample(example));
    }

    /**
     * Spu分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Spu> findPage(int page, int size) {
        //静态分页
        PageHelper.startPage(page, size);
        //分页查询
        return new PageInfo<Spu>(spuMapper.selectAll());
    }

    /**
     * Spu条件查询
     *
     * @param spu
     * @return
     */
    @Override
    public List<Spu> findList(Spu spu) {
        //构建查询条件
        Example example = createExample(spu);
        //根据构建的条件查询数据
        return spuMapper.selectByExample(example);
    }


    /**
     * Spu构建查询对象
     *
     * @param spu
     * @return
     */
    public Example createExample(Spu spu) {
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if (spu != null) {
            // 主键
            if (!StringUtils.isEmpty(spu.getId())) {
                criteria.andEqualTo("id", spu.getId());
            }
            // 货号
            if (!StringUtils.isEmpty(spu.getSn())) {
                criteria.andEqualTo("sn", spu.getSn());
            }
            // SPU名
            if (!StringUtils.isEmpty(spu.getName())) {
                criteria.andLike("name", "%" + spu.getName() + "%");
            }
            // 副标题
            if (!StringUtils.isEmpty(spu.getCaption())) {
                criteria.andEqualTo("caption", spu.getCaption());
            }
            // 品牌ID
            if (!StringUtils.isEmpty(spu.getBrandId())) {
                criteria.andEqualTo("brandId", spu.getBrandId());
            }
            // 一级分类
            if (!StringUtils.isEmpty(spu.getCategory1Id())) {
                criteria.andEqualTo("category1Id", spu.getCategory1Id());
            }
            // 二级分类
            if (!StringUtils.isEmpty(spu.getCategory2Id())) {
                criteria.andEqualTo("category2Id", spu.getCategory2Id());
            }
            // 三级分类
            if (!StringUtils.isEmpty(spu.getCategory3Id())) {
                criteria.andEqualTo("category3Id", spu.getCategory3Id());
            }
            // 模板ID
            if (!StringUtils.isEmpty(spu.getTemplateId())) {
                criteria.andEqualTo("templateId", spu.getTemplateId());
            }
            // 运费模板id
            if (!StringUtils.isEmpty(spu.getFreightId())) {
                criteria.andEqualTo("freightId", spu.getFreightId());
            }
            // 图片
            if (!StringUtils.isEmpty(spu.getImage())) {
                criteria.andEqualTo("image", spu.getImage());
            }
            // 图片列表
            if (!StringUtils.isEmpty(spu.getImages())) {
                criteria.andEqualTo("images", spu.getImages());
            }
            // 售后服务
            if (!StringUtils.isEmpty(spu.getSaleService())) {
                criteria.andEqualTo("saleService", spu.getSaleService());
            }
            // 介绍
            if (!StringUtils.isEmpty(spu.getIntroduction())) {
                criteria.andEqualTo("introduction", spu.getIntroduction());
            }
            // 规格列表
            if (!StringUtils.isEmpty(spu.getSpecItems())) {
                criteria.andEqualTo("specItems", spu.getSpecItems());
            }
            // 参数列表
            if (!StringUtils.isEmpty(spu.getParaItems())) {
                criteria.andEqualTo("paraItems", spu.getParaItems());
            }
            // 销量
            if (!StringUtils.isEmpty(spu.getSaleNum())) {
                criteria.andEqualTo("saleNum", spu.getSaleNum());
            }
            // 评论数
            if (!StringUtils.isEmpty(spu.getCommentNum())) {
                criteria.andEqualTo("commentNum", spu.getCommentNum());
            }
            // 是否上架
            if (!StringUtils.isEmpty(spu.getIsMarketable())) {
                criteria.andEqualTo("isMarketable", spu.getIsMarketable());
            }
            // 是否启用规格
            if (!StringUtils.isEmpty(spu.getIsEnableSpec())) {
                criteria.andEqualTo("isEnableSpec", spu.getIsEnableSpec());
            }
            // 是否删除
            if (!StringUtils.isEmpty(spu.getIsDelete())) {
                criteria.andEqualTo("isDelete", spu.getIsDelete());
            }
            // 审核状态
            if (!StringUtils.isEmpty(spu.getStatus())) {
                criteria.andEqualTo("status", spu.getStatus());
            }
        }
        return example;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Transactional
    @Override
    public void delete(String id) {
        spuMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改Spu
     *
     * @param spu
     */
    @Transactional
    @Override
    public void update(Spu spu) {
        spuMapper.updateByPrimaryKey(spu);
    }

    /**
     * 增加Spu
     *
     * @param spu
     */
    @Transactional
    @Override
    public void add(Spu spu) {
        spuMapper.insert(spu);
    }

    /**
     * 根据ID查询Spu
     *
     * @param id
     * @return
     */
    @Override
    public Spu findById(String id) {
        return spuMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询Spu全部数据
     *
     * @return
     */
    @Override
    public List<Spu> findAll() {
        return spuMapper.selectAll();
    }

    @Transactional
    @Override
    public void saveGoods(Goods goods) {
        Spu spu = goods.getSpu();
        if (null == spu.getId()){
            long spuId = idWorker.nextId();
            spu.setId(String.valueOf(spuId));
            spuMapper.insertSelective(spu);
        }else {
            spuMapper.updateByPrimaryKeySelective(spu);
            Sku sku = new Sku();
            sku.setSpuId(spu.getId());
            skuMapper.delete(sku);
        }
        Date date = new Date();
        Category category = categoryMapper.selectByPrimaryKey(spu.getCategory3Id());
        Brand brand = brandMapper.selectByPrimaryKey(spu.getBrandId());
        CategoryBrand categoryBrand = new CategoryBrand();
        categoryBrand.setBrandId(spu.getBrandId());
        int count = categoryBrandMapper.selectCount(categoryBrand);
        if (count == 0){
            //品牌与分类还没有关联关系
            categoryBrandMapper.insert(categoryBrand);
        }
        List<Sku> skus = goods.getSkuList();
        for (Sku sku : skus) {
            sku.setId(String.valueOf(idWorker.nextId()));
            StringBuilder name = new StringBuilder(spu.getName());
            if (StringUtils.isEmpty(sku.getSpec())){
                sku.setSpec("{}");
            }
            @SuppressWarnings("unchecked")
            Map<String,String> map = JSON.parseObject(sku.getSpec(), Map.class);
            for (Map.Entry<String
                    ,String> entry:
                 map.entrySet()) {
                 name.append(entry.getValue());
            }
            sku.setName(name.toString());
            sku.setCreateTime(date);
            sku.setUpdateTime(date);
            sku.setSpuId(spu.getId());
            sku.setCategoryId(category.getId());
            sku.setCategoryName(category.getName());
            sku.setBrandName(brand.getName());
            skuMapper.insertSelective(sku);
        }
    }

    @Override
    public Goods findGoodsById(String spuId) {

        Goods goods = new Goods();
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        Sku sku = new Sku();
        sku.setSpuId(spu.getId());
        List<Sku> skuList = skuMapper.select(sku);
        goods.setSpu(spu);
        goods.setSkuList(skuList);
        return goods;
    }

    @Transactional
    @Override
    public void audit(String spuId) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        //判断商品是否存在
        if (spu.getIsDelete().equalsIgnoreCase("1")){
            throw new RuntimeException("商品已经删除不能审核");
        }else {
            spu.setIsEnableSpec("1");
            spu.setIsMarketable("1");
            spuMapper.updateByPrimaryKeySelective(spu);
        }
    }

    @Transactional
    @Override
    public void pull(String spuId) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        //判断商品是否存在
        if (spu.getIsDelete().equalsIgnoreCase("1")){
            throw new RuntimeException("商品已经删除");
        }else {
            spu.setIsMarketable("0");
            spuMapper.updateByPrimaryKeySelective(spu);
        }
    }

    @Transactional
    @Override
    public void put(String spuId) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        //判断商品是否存在
        if (spu.getIsDelete().equalsIgnoreCase("1")){
            throw new RuntimeException("商品已经删除");
        }else if (!spu.getStatus().equalsIgnoreCase("1")) {
            throw new RuntimeException("商品未审核通过");
        }else {
            spu.setIsMarketable("1");
            spuMapper.updateByPrimaryKeySelective(spu);
        }
    }


    @Transactional
    @Override
    public void putMany(String[] spuIds) {
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", Arrays.asList(spuIds));
        criteria.andEqualTo("isDelete","0");
        criteria.andEqualTo("status","1");

        Spu spu = new Spu();
        spu.setIsMarketable("1");
        spuMapper.updateByExampleSelective(spu,example);
    }

    @Override
    public void restore(String spuId) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        if (!spu.getIsDelete().equalsIgnoreCase("1")){
            throw new RuntimeException("商品未删除");
        }else {
            spu.setIsDelete("0");
            spu.setStatus("0");
            spuMapper.updateByPrimaryKeySelective(spu);
        }
    }

    @Override
    public void realDel(String spuId) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        if (!spu.getIsDelete().equalsIgnoreCase("1")){
            throw new RuntimeException("商品未逻辑删除");
        }else {
           spuMapper.deleteByPrimaryKey(spuId);
        }
    }
}
