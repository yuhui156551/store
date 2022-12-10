package com.yuhui.store.controller;

import com.yuhui.store.mapper.CartMapper;
import com.yuhui.store.service.CartService;
import com.yuhui.store.utils.JsonR;
import com.yuhui.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    /**
     * TODO 世界上有两个我，一个像我不是我，一个是我不像我
     * 请求路径：/carts/{cid}/num/add
     * 请求参数：@PathVariable("cid") Integer cid, HttpSession session
     * 请求类型：POST
     * 响应结果：JsonResult<Integer>
     */
    @RequestMapping("{cid}/num/add")
    public JsonR<Integer> addNum(@PathVariable("cid") Integer cid, HttpSession session){
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        Integer num = cartService.addNum(cid, uid, username);
        System.out.println("商品数量：" + num);

        return new JsonR<>(OK,num);
    }

    /**
     * TODO 先上个厕所。。。
     * 请求路径：/carts/list
     * 请求参数：Integer[] cids, HttpSession session
     * 请求类型：GET
     * 响应结果：JsonResult<List<CartVO>>
     */
    @GetMapping("list")
    public JsonR<List<CartVO>> getVOByCids(Integer[] cids,HttpSession session){
        Integer uid = getUidFromSession(session);
        List<CartVO> list = cartService.getVOByCids(uid, cids);

        return new JsonR<>(OK,list);
    }
}
