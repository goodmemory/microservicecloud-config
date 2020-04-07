package com.myq.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class GlobalExceptionHandler {
	//定义错误显示页，error.html
    public static final String DEFAULT_ERROR_VIEW="/error";
    
    @ExceptionHandler(value = Exception.class)
    public String defaultErrorHandler(HttpServletRequest request,Model model,Exception e) {
        model.addAttribute("exception",e);
        model.addAttribute("url",request.getRequestURL());
        return DEFAULT_ERROR_VIEW;
    }
}
