package com.yuhui.store.controller;

import com.yuhui.store.entity.BaseEntity;
import com.yuhui.store.entity.Order;
import com.yuhui.store.service.OrderService;
import com.yuhui.store.utils.JsonR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/orders")
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    /**
     * 请求路径：/orders/create
     * 请求参数：Integer aid, Integer[] cids, HttpSession session
     * 请求类型：POST
     * 响应结果：JsonResult<Order>
     */
    @RequestMapping("create")
    public JsonR<Order> create(Integer aid, Integer[] cids, HttpSession session){
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        Order order = orderService.create(aid, cids, uid, username);

        return new JsonR<>(OK,order);
    }
}
