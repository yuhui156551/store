package com.yuhui.store.service.impl;

import com.yuhui.store.entity.Cart;
import com.yuhui.store.entity.Product;
import com.yuhui.store.mapper.CartMapper;
import com.yuhui.store.service.CartService;
import com.yuhui.store.service.ProductService;
import com.yuhui.store.service.exception.CartNotFoundException;
import com.yuhui.store.service.exception.InsertException;
import com.yuhui.store.service.exception.UpdateException;
import com.yuhui.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductService productService;

    @Override
    public void addToCart(Integer uid, Integer pid, Integer amount, String username) {
        // 根据参数pid和uid查询购物车中的数据
        Cart result = cartMapper.findByUidAndPid(uid, pid);
        Date now = new Date();
        // 判断查询结果是否为null
        // 是：表示该用户并未将该商品添加到购物车
        if(result == null) {
            //封装数据
            Cart cart = new Cart();
            cart.setUid(uid);
            cart.setPid(pid);
            cart.setNum(amount);

            //查询价格
            Product product = productService.findById(pid);
            cart.setPrice(product.getPrice());

            //封装四个日志
            cart.setCreatedUser(username);
            cart.setCreatedTime(now);
            cart.setModifiedUser(username);
            cart.setModifiedTime(now);

            //将数据插入数据表
            Integer rows = cartMapper.insert(cart);
            if(rows != 1){
                throw new InsertException("插入数据时出现未知异常");
            }
        } else {
            // 否：表示该用户的购物车中已有该商品
            Integer cid = result.getCid();
            Integer num = result.getNum() + amount;//更新数量

            Integer rows = cartMapper.updateNumByCid(cid, num, username, now);
            if(rows != 1){
                throw new UpdateException("修改商品时出现未知错误");
            }
        }


    }

    @Override
    public List<CartVO> getVOByUid(Integer uid) {
        List<CartVO> cartVOList = cartMapper.findVOByUid(uid);

        return cartVOList;
    }

    @Override
    public Integer addNum(Integer cid, Integer uid, String username) {
        //查询购物车数据
        Cart cart = cartMapper.findByCid(cid);
        if(cart == null){
            throw new CartNotFoundException("购物车数据不存在");
        }

        //数量+1
        Integer num = cart.getNum() + 1;

        //执行修改操作
        Integer rows = cartMapper.updateNumByCid(cid, num, username, new Date());
        if(rows != 1){
            throw new UpdateException("修改数据时出现未知错误");
        }

        return num;
    }

    @Override
    public List<CartVO> getVOByCids(Integer uid, Integer[] cids) {
        List<CartVO> list = cartMapper.findVOByCids(cids);

        //迭代器遍历，检测商品数据是否属于当前用户
        Iterator<CartVO> it = list.iterator();
        while (it.hasNext()){
            CartVO cartVO = it.next();
            if(!cartVO.getUid().equals(uid)){
                it.remove();
            }
        }

        return list;
    }
}
