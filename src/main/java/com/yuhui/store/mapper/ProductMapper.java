package com.yuhui.store.mapper;

import com.yuhui.store.entity.Product;

import java.util.List;

/**
 * 处理商品数据的持久层接口
 */
public interface ProductMapper {

    /**
     * 查找热销商品的前四名
     * @return
     */
    List<Product> findHotList();

    /**
     * 查找商品详细信息
     * @param id 商品id
     * @return 信息 or null
     */
    Product findById(Integer id);
}
