package com.yuhui.store.vo;

import lombok.Data;

import java.io.Serializable;


/**
 * 购物车数据的Value Object(值对象)类
 * */
@Data
public class CartVO implements Serializable {
    private Integer cid;
    private Integer uid;
    private Integer pid;
    private Long price;
    private Integer num;
    private String title;
    private Long realPrice;
    private String image;
}
