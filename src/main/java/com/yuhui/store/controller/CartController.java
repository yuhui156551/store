package com.yuhui.store.controller;

import com.yuhui.store.service.CartService;
import com.yuhui.store.utils.JsonR;
import com.yuhui.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController extends BaseController{
    @Autowired
    private CartService cartService;

    /**TODO 设计请求
     * 请求路径：/carts/add_to_cart
     * 请求参数：Integer pid, Integer amount, HttpSession session
     * 请求类型：POST
     * 响应结果：JsonR<Void>
     */
    @RequestMapping("add_to_cart")
    public JsonR<Void> addToCart(Integer pid, Integer amount, HttpSession session){
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        cartService.addToCart(uid,pid,amount,username);

        return new JsonR<>(OK);
    }

    /**
     * TODO 设计请求
     * 请求路径：/carts/
     * 请求参数：HttpSession session
     * 请求类型：GET
     * 响应结果：JsonResult<List<CartVO>>
     */
    @GetMapping({"","/"})
    public JsonR<List<CartVO>> getVOByUid(HttpSession session){
        Integer uid = getUidFromSession(session);
        List<CartVO> cartVOList = cartService.getVOByUid(uid);

        return new JsonR<>(OK,cartVOList);
    }
}
