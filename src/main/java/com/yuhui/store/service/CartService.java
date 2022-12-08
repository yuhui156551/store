package com.yuhui.store.service;

import com.yuhui.store.vo.CartVO;

import java.util.List;

public interface CartService {

    /**
     * 添加商品进购物车
     * @param uid 用户id
     * @param pid 商品id
     * @param amount 增加的数量
     * @param username 用户名
     */
    void addToCart(Integer uid, Integer pid, Integer amount, String username);

    /**
     * 查询用户的购物车数据
     * @param uid 用户id
     * @return 购物车数据
     */
    List<CartVO> getVOByUid(Integer uid);
}
