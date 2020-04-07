package com.myq.utils;


import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 处理结果，返回给客户端
 */
public class Result implements Serializable {

	private static final long serialVersionUID = 1L;

	private int status = 0;

    private String message = "";
    private String errorMsg = "";

    private long total;
    private Object data = "";

    private int second=0;

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    Result() {

    }

    public Result(int status, String message) {
        this.status = status;
        this.message = message;
    }

    Result(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    Result(int status, String message, int second) {
        this.status = status;
        this.message = message;
        this.second = second;
    }
    
    Result(int status, String message, Object data, long total) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.total = total;
    }

    public static Result fail(int status, String message) {
        return new Result(status, message);
    }

    public Result changeMsg(String message) {
        this.message = message;
        return this;
    }

    public Result changeTotal(long total) {
        this.total = total;
        return this;
    }

    public Object getData() {
        return data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public Result setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public long getTotal() {
        return total;
    }

    @JSONField(serialize = false)
    public boolean isSuccess() {
        return status == 0;
    }
}
