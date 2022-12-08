package com.yuhui.store.service.impl;

import com.yuhui.store.entity.Product;
import com.yuhui.store.mapper.ProductMapper;
import com.yuhui.store.service.ProductService;
import com.yuhui.store.service.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> findHotList() {
        List<Product> hotList = productMapper.findHotList();
        for (Product product : hotList) {
            product.setPriority(null);
            product.setCreatedUser(null);
            product.setCreatedTime(null);
            product.setModifiedUser(null);
            product.setModifiedTime(null);
        }
        return hotList;
    }

    @Override
    public Product findById(Integer id) {
        Product product = productMapper.findById(id);
        if(product == null){
            throw new ProductNotFoundException("商品信息不存在");
        }

        // 将查询结果中的部分属性设置为null
        product.setPriority(null);
        product.setCreatedUser(null);
        product.setCreatedTime(null);
        product.setModifiedUser(null);
        product.setModifiedTime(null);

        return product;
    }
}
