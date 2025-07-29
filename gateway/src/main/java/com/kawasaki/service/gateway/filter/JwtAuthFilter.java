package com.kawasaki.service.gateway.filter;

import com.kawasaki.service.common.utils.JWTUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

//@Component
//public class JwtAuthFilter implements GlobalFilter, Ordered {
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        ServerHttpRequest request = exchange.getRequest();
//
//        String path = request.getURI().getPath();
//        if (path.contains("/auth/login") || path.contains("/auth/register")) {
//            return chain.filter(exchange);
//        }
//
//        HttpHeaders headers = request.getHeaders();
//        String token = headers.getFirst("Authorization");
//        if (token == null || !token.startsWith("Bearer ")) {
//            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//            return exchange.getResponse().setComplete();
//        }
//
//        token = token.substring(7);
//        try {
//            Claims claims = JWTUtils.verifyToken(token);
//            String id = claims.get("id", Long.class).toString();
//            String name = claims.get("name", String.class);
//            String role = claims.get("role", String.class);
//            String email = claims.getSubject();
//
//            ServerHttpRequest newRequest = request.mutate()
//                    .header("X_User_Id", id)
//                    .header("X_User_Name", name)
//                    .header("X_User_Email", email)
//                    .header("X_User_Role", role)
//                    .build();
//
//            exchange = exchange.mutate().request(newRequest).build();
//        } catch (Exception e) {
//            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//            return exchange.getResponse().setComplete();
//        }
//
//        return chain.filter(exchange);
//    }
//
//    @Override
//    public int getOrder() {
//        return -1;
//    }
//}
