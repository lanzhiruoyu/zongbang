package com.zongbang.feign;

import com.zongbang.pojo.Result;
import com.zongbang.pojo.Sku;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @ClassName: SkuFeign
 * @Description: TODO
 * @Author: HuiLiao
 * @Date: 2020/2/26 12:43
 */
@FeignClient(value = "goods")
@RequestMapping("/sku")
public interface SkuFeign {

    @GetMapping
    Result<List<Sku>> findAll();

    @GetMapping(value = "/status/{status}")
    Result<List<Sku>> findByStatus(@PathVariable String status);
}
