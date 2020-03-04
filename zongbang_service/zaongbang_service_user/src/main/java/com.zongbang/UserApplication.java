package com.zongbang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @ClassName: UserApplication
 * @Description: TODO
 * @Author: HuiLiao
 * @Date: 2020/3/1 22:01
 */
@EnableEurekaClient
@SpringBootApplication
@MapperScan(value = "com.zongbang.dao")
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }
}
