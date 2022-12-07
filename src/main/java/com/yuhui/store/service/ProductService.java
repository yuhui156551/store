package com.yuhui.store.service;

import com.yuhui.store.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> findHotList();
}
