package com.yuhui.store.service;

import com.yuhui.store.entity.Cart;
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

    /**
     * 将购物车中某商品的数量加1
     * @param cid 购物车数量的id
     * @param uid 当前登录的用户的id
     * @param username 当前登录的用户名
     * @return 增加成功后新的数量
     */
    Integer addNum(Integer cid, Integer uid, String username);

    /**
     * 根据若干个购物车数据id查询详情的列表
     * @param uid 当前登录的用户的id
     * @param cids 若干个购物车数据id
     * @return 匹配的购物车数据详情的列表
     */
    List<CartVO> getVOByCids(Integer uid, Integer[] cids);
}
