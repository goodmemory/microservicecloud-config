package com.myq.utils;

/**
 * Created by Poorzerg on 2017/四月/13.
 */
public enum ResultBuilder {

    
    SUCCESS(0, "操作成功"),
    ERROR(3, "当前服务器出错"),
    PARAM_ERROR(6, "请求参数错误"),
    
    //100-199用于用户的
    USERNAME_NULL(100,"用户名不能为空！"),
    PASSWORD_NULL(101,"密码不能为空！"),
    USER_ERROR(102,"用户名或密码有误，请重新输入！"),
    DELETE_USER(103,"删除用户成功！"),
    USER_EXISTS(104,"用户名已存在！"),
    USER_REGISTER_FAIL(105,"用户注册失败！"),
    USER_REGISTER_SUCCESS(106,"用户注册成功！"),
    USER_NO_AUTHORITY(107,"此用户没有权限！"),
    //200-210用于角色
    DELETE_ROLE(200,"角色删除成功！"),
    
    //211-220用于新闻类别
    DELETE_CATEGORY(211,"类别删除成功！"),
    
    //221-230用于新闻
    DELETE_NEWS(221,"删除新闻成功！"),
    
    //231-240用于评论
    DELETE_COMMENT(221,"删除评论成功！"),
    ADD_COMMENT(222,"添加评论成功！"),
    ;

    private int status;
    private String message;

    ResultBuilder(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public Result build() {
        return new Result(this.status, this.message);
    }

    public Result build(Object data) {
        return new Result(this.status, this.message, data);
    }

    public Result build(int second) {
        return new Result(this.status, this.message, second);
    }

    public Result build(Object data, long total) {
        return new Result(this.status, this.message, data, total);
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }


}
