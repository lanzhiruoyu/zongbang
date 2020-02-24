package com.zongbang.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @program: zongbang_parent
 * @description: 商品的实体类
 * @author: LiaoHui
 * @create: 2020-01-14 23:58
 **/
public class Goods implements Serializable {

    private static final long serialVersionUID = 3674123152067636876L;

    //Spu信息
    private Spu spu;

    //Sku集合信息
    private List<Sku> skuList;

    public Spu getSpu() {
        return spu;
    }

    public void setSpu(Spu spu) {
        this.spu = spu;
    }

    public List<Sku> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<Sku> skuList) {
        this.skuList = skuList;
    }
}
