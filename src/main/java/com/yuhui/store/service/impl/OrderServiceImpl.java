package com.yuhui.store.service.impl;

import com.yuhui.store.entity.Address;
import com.yuhui.store.entity.Order;
import com.yuhui.store.entity.OrderItem;
import com.yuhui.store.mapper.AddressMapper;
import com.yuhui.store.mapper.OrderMapper;
import com.yuhui.store.service.AddressService;
import com.yuhui.store.service.CartService;
import com.yuhui.store.service.OrderService;
import com.yuhui.store.service.exception.AddressNotFoundException;
import com.yuhui.store.service.exception.InsertException;
import com.yuhui.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private AddressService addressService;
    @Autowired
    private CartService cartService;

    @Transactional
    @Override
    public Order create(Integer aid, Integer[] cids, Integer uid, String username) {
        //TODO 理一遍思路吧。。。1、首先想的是补全order数据，uid、地址、总价格、status、时间、日志。
        // 2、插入订单数据
        // 3、订单详细项数据存入数据库
        // 4、返回order对象给控制层

        //根据cids查询所勾选的购物车列表中的数据
        List<CartVO> cartVOS = cartService.getVOByCids(uid, cids);
        // 计算这些商品的总价
        long totalPrice = 0;
        for (CartVO cart : cartVOS) {
            totalPrice += cart.getRealPrice() * cart.getNum();
        }

        //补全数据
        Order order = new Order();
        order.setUid(uid);
        //获取收获地址信息
        Address address = addressService.getByAid(aid);
        //补全地址信息数据
        order.setRecvName(address.getName());
        order.setRecvPhone(address.getPhone());
        order.setRecvProvince(address.getProvinceName());
        order.setRecvCity(address.getCityName());
        order.setRecvArea(address.getAreaName());
        order.setRecvAddress(address.getAddress());
        //status--0代表未支付、时间、价格、日志
        order.setStatus(0);
        Date date = new Date();
        order.setOrderTime(date);
        order.setTotalPrice(totalPrice);
        order.setCreatedUser(username);
        order.setCreatedTime(date);
        order.setModifiedUser(username);
        order.setModifiedTime(date);

        //插入订单数据
        Integer rows1 = orderMapper.insertOrder(order);
        if(rows1 != 1) {
            throw new InsertException("插入数据时出现未知异常");
        }


        //遍历carts，循环插入订单商品数据
        for (CartVO cart : cartVOS) {
            // 创建订单商品数据
            OrderItem item = new OrderItem();
            // 补全数据：setOid(order.getOid())
            item.setOid(order.getOid());
            // 补全数据：pid, title, image, price, num
            item.setPid(cart.getPid());
            item.setTitle(cart.getTitle());
            item.setImage(cart.getImage());
            item.setPrice(cart.getRealPrice());
            item.setNum(cart.getNum());
            // 补全数据：4项日志
            item.setCreatedUser(username);
            item.setCreatedTime(date);
            item.setModifiedUser(username);
            item.setModifiedTime(date);

            Integer rows2 = orderMapper.insertOrderItem(item);
            if(rows2 != 1) {
                throw new InsertException("插入数据时出现未知异常");
            }
        }

        return order;
    }
}
