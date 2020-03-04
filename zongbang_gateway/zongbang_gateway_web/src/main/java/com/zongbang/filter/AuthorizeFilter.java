package com.zongbang.filter;

import com.alibaba.fastjson.JSON;
import com.zongbang.utils.IPUtils;
import com.zongbang.utils.JwtUtil;
import com.zongbang.utils.MD5Utils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Objects;

/**
 * @ClassName: AuthorizeFilter
 * @Description: 用户鉴权过滤器
 * @Author: HuiLiao
 * @Date: 2020/3/3 16:55
 */
@Component
public class AuthorizeFilter implements GlobalFilter  , Ordered {

    private static final String AUTHORIZE_TOKEN = "Authorization";

    //全局拦截
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String token = request.getHeaders().getFirst(AUTHORIZE_TOKEN);
        boolean tokenInHead = true;
        //3.判断当前的请求是否为登录请求,如果是,则直接放行
        if (request.getURI().getPath().contains("/user/login")){
            //放行
            return chain.filter(exchange);
        }

        if (StringUtils.isEmpty(token)){
            token = request.getQueryParams().getFirst(AUTHORIZE_TOKEN);
            tokenInHead = false;
        }
        if (StringUtils.isEmpty(token)){
            HttpCookie httpCookie = request.getCookies().getFirst(AUTHORIZE_TOKEN);
            if (null != httpCookie){
                token = httpCookie.getValue();
            }
        }
        if (StringUtils.isEmpty(token)){
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        try {
            JwtUtil.parseJWT(token);
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        if (!tokenInHead) {
            //把token放到头文件中
            request.mutate().header(AUTHORIZE_TOKEN, token);
        }
        String realIp = IPUtils.getIpAddress(request);
        @SuppressWarnings("all")
        Map<String,Object> tokenMap = JSON.parseObject(token, Map.class);
        if (!Objects.requireNonNull(MD5Utils.stringMD5(realIp)).equals(String.valueOf(tokenMap.get("ip")))){
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        return chain.filter(exchange);
    }

    //排序
    @Override
    public int getOrder() {
        return 0;
    }
}
