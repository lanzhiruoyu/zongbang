package com.zongbang;

import com.zongbang.pojo.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @program: zongbang_parent
 * @description: 商品服务的启动类
 * @author: LiaoHui
 * @create: 2020-02-04 20:56
 **/
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = "com.zongbang.dao")
public class GoodsApplication {
    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication.class,args);
    }


    @Bean
    public IdWorker idWorker(){
        return new IdWorker(0,0);
    }

}
