package com.zongbang.controller;

import com.zongbang.pojo.Result;
import com.zongbang.pojo.StatusCode;
import com.zongbang.service.SkuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @ClassName: SkuController
 * @Description: TODO
 * @Author: HuiLiao
 * @Date: 2020/2/26 13:50
 */
@RestController
@RequestMapping(value = "/search")
@CrossOrigin
public class SkuController {

    @Resource
    private SkuService skuService;

    @GetMapping(value = "/import")
    public Result importData(){

        skuService.importData();
        return new Result(true, StatusCode.OK,"数据导入成功");
    }


    @GetMapping
    public Map<String, Object> search(@RequestParam(required = false) Map<String, String> searchMap){
        return skuService.search(searchMap);
    }
}
