//package com.myq.config;
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * 解决web端访问跨域的问题 Created by ZLong on 2016/十一月/22.
// */
//@Component
//public class CORSFilter extends OncePerRequestFilter {
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        response.addHeader("Access-Control-Allow-Origin", "*");
//        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT,OPTIONS, DELETE");
//        response.addHeader("Access-Control-Allow-Headers", "x_requested_with,Content-Type");
//        response.addHeader("Access-Control-Max-Age", "1800");// 30 min
//        filterChain.doFilter(request, response);
//    }
//}