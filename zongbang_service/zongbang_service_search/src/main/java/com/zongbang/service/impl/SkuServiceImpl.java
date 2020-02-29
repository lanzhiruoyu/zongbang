package com.zongbang.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zongbang.dao.SkuEsMapper;
import com.zongbang.feign.SkuFeign;
import com.zongbang.pojo.Result;
import com.zongbang.pojo.Sku;
import com.zongbang.pojo.SkuInfo;
import com.zongbang.service.SkuService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @ClassName: SkuServiceImpl
 * @Description: sku搜索的实现类
 * @Author: HuiLiao
 * @Date: 2020/2/26 12:52
 */
@Service
public class SkuServiceImpl implements SkuService {

    @Resource
    private SkuFeign skuFeign;

    @Resource
    private SkuEsMapper skuEsMapper;

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public void importData() {

        //Feign调用，查询List<Sku>
        Result<List<Sku>> listResult = skuFeign.findByStatus("1");
        if (null != listResult) {
            //将List<Sku>转成List<SkuInfo>
            List<SkuInfo> skuInfoList = JSON.parseArray(JSON.toJSONString(listResult.getData()), SkuInfo.class);
            //对规格生成域
            for (SkuInfo skuInfo : skuInfoList) {
                //把规格转为map其中key作为域value作为值
                @SuppressWarnings("all")
                Map<String, Object> specMap = JSON.parseObject(skuInfo.getSpec(), Map.class);
                skuInfo.setSpecMap(specMap);
            }
            //调用Dao实现数据批量导入
            skuEsMapper.saveAll(skuInfoList);
        }
    }

    @Override
    public Map<String, Object> search(Map<String, String> searchMap) {

        //封装搜索条件
        NativeSearchQueryBuilder builder = buildBasicBuilder(searchMap);

        //集合搜索
        Map<String, Object> resaultMap = searchList(builder);

        Map<String, Object> groupMap = searchGroupList(builder, searchMap);
        resaultMap.putAll(groupMap);
//        if (null == searchMap || StringUtils.isEmpty(searchMap.get("category"))){
//            List<String> categoryList = searchCategoryList(builder);
//            resaultMap.put("categoryList",categoryList);
//        }
//
//        if (null == searchMap || StringUtils.isEmpty(searchMap.get("brand"))) {
//            List<String> brandList = searchBrandList(builder);
//            resaultMap.put("brandList", brandList);
//        }
//        Map<String, Set<String>> specList = searchSpec(builder);
//        resaultMap.put("specList",specList);
        return resaultMap;
    }

    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author HuiLiao
     * @Description 分组查询集合包含 分类分组，品牌分组，规格分组
     * @Date 22:35 2020/2/28
     * @Param [builder, searchMap]
     **/
    private Map<String, Object> searchGroupList(NativeSearchQueryBuilder builder, Map<String, String> searchMap) {

        /***
         * 分组查询分类集合
         * .addAggregation():添加一个聚合操作
         * 1)取别名
         * 2)表示根据哪个域进行分组查询
         */
        if (null == searchMap || StringUtils.isEmpty(searchMap.get("category"))) {
            builder.addAggregation(AggregationBuilders.terms("skuCategory").field("categoryName"));
        }
        if (null == searchMap || StringUtils.isEmpty(searchMap.get("brand"))) {
            builder.addAggregation(AggregationBuilders.terms("skuBrand").field("brandName"));
        }
        builder.addAggregation(AggregationBuilders.terms("skuSpec").field("spec.keyword").size(10000));
        AggregatedPage<SkuInfo> aggregatedPage = elasticsearchTemplate.queryForPage(builder.build(), SkuInfo.class);
        //获取分组信息
        HashMap<String, Object> groupMapResult = new HashMap<>();
        if (null == searchMap || StringUtils.isEmpty(searchMap.get("category"))) {
            StringTerms categoryTerms = aggregatedPage.getAggregations().get("skuCategory");
            //获取分类分组信息
            List<String> categoryList = getGroupList(categoryTerms);
            groupMapResult.put("categoryList", categoryList);

        }
        if (null == searchMap || StringUtils.isEmpty(searchMap.get("brand"))) {
            StringTerms brandTerns = aggregatedPage.getAggregations().get("skuBrand");
            List<String> brandList = getGroupList(brandTerns);
            groupMapResult.put("brandList", brandList);
        }
        StringTerms specTerms = aggregatedPage.getAggregations().get("skuSpec");
        if (null != specTerms){
            List<String> specList = getGroupList(specTerms);
            Map<String, Set<String>> specMap = putAllSpec(specList);
            groupMapResult.put("specList", specMap);
        }
        return groupMapResult;
    }

    /**
     * @return java.util.List<java.lang.String>
     * @Author HuiLiao
     * @Description 获取分组集合数据
     * @Date 22:46 2020/2/28
     * @Param [stringTerms]
     **/
    private List<String> getGroupList(StringTerms stringTerms) {
        List<String> categoryList = new ArrayList<>();
        for (StringTerms.Bucket bucket : stringTerms.getBuckets()) {
            String Name = bucket.getKeyAsString();
            categoryList.add(Name);
        }
        return categoryList;
    }


    /**
     * @return java.util.Map<java.lang.String, java.util.Set < java.lang.String>>
     * @Author HuiLiao
     * @Description 规格合并
     * @Date 22:57 2020/2/28
     * @Param [specList]
     **/
    private Map<String, Set<String>> putAllSpec(List<String> specList) {
        //合并后的map对象
        Map<String, Set<String>> allSpec = new HashMap<>();
        //循环specList
        for (String spec : specList) {
            //把spec字符串转换为map对象
            @SuppressWarnings("all")
            Map<String, String> map = JSON.parseObject(spec, Map.class);
            for (Map.Entry<String, String> entry : map.entrySet()) {
                //规格的属性
                String key = entry.getKey();
                //规格的值
                String value = entry.getValue();
                //判断allSpec中该属性是否为空
                Set<String> set = allSpec.get(key);
                if (null == set || set.size() == 0) {
                    set = new HashSet<>();
                }
                set.add(value);
                allSpec.put(key, set);
            }
        }
        return allSpec;
    }

    /**
     * @return org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder
     * @Author HuiLiao
     * @Description 搜索条件封装
     * @Date 17:54 2020/2/28
     * @Param [searchMap]
     **/
    private NativeSearchQueryBuilder buildBasicBuilder(Map<String, String> searchMap) {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (null != searchMap && searchMap.size() > 0) {
            //根据关键字搜索
            if (!StringUtils.isEmpty(searchMap.get("keywords"))) {
                boolQueryBuilder.must(QueryBuilders.queryStringQuery(searchMap.get("keywords")).field("name"));
            }
            //根据分类搜索
            if (!StringUtils.isEmpty(searchMap.get("category"))) {
                boolQueryBuilder.must(QueryBuilders.termQuery("categoryName", searchMap.get("category")));
            }
            //根据品牌搜索
            if (!StringUtils.isEmpty(searchMap.get("brand"))) {
                boolQueryBuilder.must(QueryBuilders.termQuery("brandName", searchMap.get("brand")));
            }
            //根据规格搜索
            for (Map.Entry<String, String> entry : searchMap.entrySet()) {
                String key = entry.getKey();
                if (key.startsWith("spec_")) {
                    String value = entry.getValue();
                    boolQueryBuilder.must(QueryBuilders.termQuery("specMap." + key.substring(5) + ".keyword", value));
                }
            }
            //根据价格搜索
            String price = searchMap.get("price");
            //price如果以gt为结尾说明是大于
            if (!StringUtils.isEmpty(price)) {
                if (price.endsWith("gt")) {
                    boolQueryBuilder.must(QueryBuilders.rangeQuery("price").gt(Integer.parseInt(price.substring(0, price.length() - 2))));
                } else {
                    String[] prices = price.split("-");
                    boolQueryBuilder.must(QueryBuilders.rangeQuery("price").gt(Integer.parseInt(prices[0])).lt(Integer.parseInt(prices[1])));
                }
            }

            //排序实现
            String sortFiled = searchMap.get("sortFiled");   //指定排序类型
            String sortRule = searchMap.get("sortRule");      //指定排序规则
            if (!StringUtils.isEmpty(sortFiled) && !StringUtils.isEmpty(sortRule)) {
                builder.withSort(
                        new FieldSortBuilder(sortFiled).
                                order(SortOrder.valueOf(sortRule)));
            }

        }

        //分页 默认第一页
        Integer pageNum = getPage(searchMap);
        Integer size = 30;
        builder.withPageable(PageRequest.of(pageNum - 1, size));
        builder.withQuery(boolQueryBuilder);
        return builder;
    }

    /**
     * @return java.lang.Integer
     * @Author HuiLiao
     * @Description 获取请求传来的页数
     * @Date 14:19 2020/2/29
     * @Param [searchMap]
     **/
    private Integer getPage(Map<String, String> searchMap) {
        if (null != searchMap && searchMap.size()>0) {
            String pageNum = searchMap.get("pageNum");
            try {
                return Integer.parseInt(pageNum);
            } catch (NumberFormatException e) {
                System.err.println("请求页数为空");
            }
        }
        return 1;
    }

    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author HuiLiao
     * @Description 集合查询结果集
     * @Date 17:52 2020/2/28
     * @Param [builder]
     **/
    private Map<String, Object> searchList(NativeSearchQueryBuilder builder) {

        HighlightBuilder.Field field = new HighlightBuilder.Field("name");
        //前缀
        field.preTags("<em style= \'color:red\'>");
        //后缀
        field.postTags("</em>");
        //关键词数据长度
        field.fragmentSize(100);
        builder.withHighlightFields(field);
        AggregatedPage<SkuInfo> Page = elasticsearchTemplate.
                queryForPage(builder.build(), SkuInfo.class, new SearchResultMapper() {
                    @Override
                    @SuppressWarnings("all")
                    public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                        ArrayList<T> list = new ArrayList<>();
                        for (SearchHit hit : searchResponse.getHits()) {
                            SkuInfo skuInfo = JSON.parseObject(hit.getSourceAsString(), SkuInfo.class);
                            HighlightField highlightField = hit.getHighlightFields().get("name");
                            if (null != highlightField && null != highlightField.getFragments()) {
                                StringBuffer stringBuffer = new StringBuffer();
                                for (Text fragment : highlightField.getFragments()) {
                                    stringBuffer.append(fragment.toString());
                                }
                                skuInfo.setName(stringBuffer.toString());
                            }
                            list.add((T) skuInfo);
                        }
                        /***
                         * 1)搜索的集合数据：(携带高亮)List<T> content
                         * 2)分页对象信息：Pageable pageable
                         * 3)搜索记录的总条数：long total
                         */
                        return new AggregatedPageImpl<T>(list, pageable, searchResponse.getHits().getTotalHits());
                    }

                    @Override
                    public <T> T mapSearchHit(SearchHit searchHit, Class<T> aClass) {
                        return null;
                    }
                });

        //总页数
        long totalPages = Page.getTotalPages();
        //总的记录数
        long totalElements = Page.getTotalElements();
        //结果集
        List<SkuInfo> skuInfos = Page.getContent();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("totalPages", totalPages);
        resultMap.put("rows", skuInfos);
        resultMap.put("total", totalElements);


        //获取搜索封装信息
        NativeSearchQuery query = builder.build();
        Pageable pageable = query.getPageable();
        int pageSize = pageable.getPageSize();
        int pageNumber = pageable.getPageNumber();

        //分页数据
        resultMap.put("pageSize",pageSize);
        resultMap.put("pageNumber",pageNumber);
        return resultMap;
    }


//
//    /**
//     * @Author HuiLiao
//     * @Description 分类分组查询结果集
//     * @Date 17:52 2020/2/28
//     * @Param [builder]
//     * @return java.util.List<java.lang.String>
//     **/
//    private List<String> searchCategoryList(NativeSearchQueryBuilder builder) {
//
//        builder.addAggregation(AggregationBuilders.terms("skuCategory").field("categoryName"));
//        AggregatedPage<SkuInfo> aggregatedPage = elasticsearchTemplate.queryForPage(builder.build(), SkuInfo.class);
//        StringTerms stringTerms = aggregatedPage.getAggregations().get("skuCategory");
//        return getGroupList(stringTerms);
//    }
//
//
//
//    /**
//     * @Author HuiLiao
//     * @Description 品牌分组查询结果集
//     * @Date 18:07 2020/2/28
//     * @Param [builder]
//     * @return java.util.List<java.lang.String>
//     **/
//    private List<String> searchBrandList(NativeSearchQueryBuilder builder) {
//        builder.addAggregation(AggregationBuilders.terms("skuBrand").field("brandName"));
//        AggregatedPage<SkuInfo> aggregatedPage = elasticsearchTemplate.queryForPage(builder.build(), SkuInfo.class);
//        StringTerms stringTerms = aggregatedPage.getAggregations().get("skuBrand");
//        return getGroupList(stringTerms);
//    }
//
//    /**
//     * @Author HuiLiao
//     * @Description 根据规格分组查询结果集
//     * @Date 18:51 2020/2/28
//     * @Param [builder]
//     * @return java.util.Map<java.lang.String,java.util.List<java.lang.String>>
//     **/
//    private Map<String, Set<String>> searchSpec(NativeSearchQueryBuilder builder) {
//        builder.addAggregation(AggregationBuilders.terms("skuSpec").field("spec.keyword").size(10000));
//        AggregatedPage<SkuInfo> aggregatedPage = elasticsearchTemplate.queryForPage(builder.build(), SkuInfo.class);
//        StringTerms stringTerms = aggregatedPage.getAggregations().get("skuSpec");
//        List<String> specList = new ArrayList<>();
//        for (StringTerms.Bucket bucket : stringTerms.getBuckets()) {
//            String specName = bucket.getKeyAsString();
//            specList.add(specName);
//        }
//        Map<String, Set<String>> allSpec = putAllSpec(specList);
//        return allSpec;
//    }
//
//
}
