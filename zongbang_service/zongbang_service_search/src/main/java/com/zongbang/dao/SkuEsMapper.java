package com.zongbang.dao;

import com.zongbang.pojo.SkuInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @ClassName: SkuEsMapper
 * @Description: TODO
 * @Author: HuiLiao
 * @Date: 2020/2/26 13:07
 */
public interface SkuEsMapper extends ElasticsearchRepository<SkuInfo,Long> {
}
