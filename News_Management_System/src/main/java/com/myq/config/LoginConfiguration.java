package com.myq.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.myq.interceptor.LoginInterceptor;

@Configuration
public class LoginConfiguration implements WebMvcConfigurer {

//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(new LoginInterceptor())
//				// 禁止所有路径
//				.addPathPatterns("/**")
//				// 排除登录，退出路径
//				.excludePathPatterns("/login")
//				.excludePathPatterns("/doLogin")
//				.excludePathPatterns("/loginout")
//				.excludePathPatterns("/register")
//				.excludePathPatterns("/front_index")
//				.excludePathPatterns("/front_information")
//				.excludePathPatterns("/frontNews/show")
//				.excludePathPatterns("/user/registeruser")
//				.excludePathPatterns("/static/**","/templates/**","images/**");
//	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 静态资源
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");
	}

}
