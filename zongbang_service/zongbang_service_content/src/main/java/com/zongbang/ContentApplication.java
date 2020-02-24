package com.zongbang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @program: zongbang_parent
 * @description: 广告服务的启动类
 * @author: LiaoHui
 * @create: 2020-02-04 20:56
 **/
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = "com.zongbang.dao")
public class ContentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContentApplication.class, args);
    }


}
