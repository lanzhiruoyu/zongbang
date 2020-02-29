package com.zongbang.pojo.feign;


import com.zongbang.pojo.Content;
import com.zongbang.pojo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @ClassName: ContentFeign
 * @Description: TODO
 * @Author: HuiLiao
 * @Date: 2020/2/24 23:08
 */
@FeignClient(name = "content")
@RequestMapping(value = "/content")
public interface ContentFeign {

    @GetMapping(value = "/list/category/{id}")
    public Result<List<Content>> findByCategory(@PathVariable Long id);
}
