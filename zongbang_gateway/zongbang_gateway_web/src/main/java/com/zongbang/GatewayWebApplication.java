package com.zongbang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @ClassName: GatewayWebApplication
 * @Description: TODO
 * @Author: HuiLiao
 * @Date: 2020/3/1 12:56
 */
@EnableEurekaClient
@SpringBootApplication
public class GatewayWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayWebApplication.class,args);
    }


    //创建用户唯一标识，使用IP
    @Bean(name = "ipKeyResolver")
    public KeyResolver userKeyResolver(){
        return exchange -> {
            String ip = Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getHostString();
            return Mono.just(ip);
        };
    }
}
