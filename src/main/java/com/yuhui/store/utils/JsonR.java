package com.yuhui.store.utils;

import lombok.Data;

import java.io.Serializable;

/**
 * Json格式的响应结果类
 * @param <E> 响应数据的类型
 */
@Data
public class JsonR<E> implements Serializable {
    private Integer code; //状态码
    private String message;
    private E data;

    public JsonR() {
        super();
    }

    public JsonR(Integer code) {
        super();
        this.code = code;
    }

    /** 出现异常时调用 */
    public JsonR(Throwable e) {
        super();
        this.message = e.getMessage();
    }

    public JsonR(Integer code, E data) {
        super();
        this.code = code;
        this.data = data;
    }


}
