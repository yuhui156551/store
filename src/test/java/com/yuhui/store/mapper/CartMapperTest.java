package com.yuhui.store.mapper;


import com.yuhui.store.entity.Cart;
import com.yuhui.store.vo.CartVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CartMapperTest {
    @Autowired
    private CartMapper cartMapper;

    @Test
    public void findVOByUid() {
        List<CartVO> list = cartMapper.findVOByUid(10);
        System.out.println(list);
    }

    @Test
    public void findByCid() {
        Cart cart = cartMapper.findByCid(2);
        System.out.println(cart);
    }
}
