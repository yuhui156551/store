package com.yuhui.store.entity;

import com.yuhui.store.controller.BaseController;
import lombok.Data;

import java.io.Serializable;

@Data
public class Product extends BaseEntity implements Serializable {
    private Integer id;
    private Integer categoryId;
    private String itemType;
    private String title;
    private String sellPoint;
    private Long price;
    private Integer num;
    private String image;
    private Integer status;
    private Integer priority;
}
