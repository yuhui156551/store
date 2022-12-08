package com.yuhui.store.service;

import com.yuhui.store.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> findHotList();

    //TODO 很舒爽，GitHub又可以正常访问了，现在唯一心结是身份证的问题

    Product findById(Integer id);
}
