package com.zongbang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @ClassName: SearchApplication
 * @Description: TODO
 * @Author: HuiLiao
 * @Date: 2020/2/26 12:07
 */
@EnableEurekaClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableFeignClients
@EnableElasticsearchRepositories
public class SearchApplication {

    public static void main(String[] args) {
        /**
         * @Author HuiLiao
         * @Description  解决netty冲突后初始化client时还会抛出异常
         * @Date 12:11 2020/2/26
         * @Param [args]
         * @return void
         **/
        System.setProperty("es.set.netty.runtime.available.processors","false");
        SpringApplication.run(SearchApplication.class,args);
    }
}
