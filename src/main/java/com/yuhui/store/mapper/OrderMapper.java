package com.yuhui.store.mapper;

import com.yuhui.store.entity.Order;
import com.yuhui.store.entity.OrderItem;

public interface OrderMapper {

    /**
     * 插入订单数据
     * @param order 订单数据
     * @return 受影响的行数
     */
    Integer insertOrder(Order order);

    /**
     * 插入订单商品的数据
     * @param orderItem 订单某一商品的数据
     * @return 受影响的行数
     */
    Integer insertOrderItem(OrderItem orderItem);
}
