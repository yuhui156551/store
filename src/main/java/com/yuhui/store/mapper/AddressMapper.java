package com.yuhui.store.mapper;

import com.yuhui.store.entity.Address;

/**
 * 收货地址数据的持久层接口
 */
public interface AddressMapper {

    /**
     * 插入收货地址数据
     * @param address 收货地址
     * @return 受影响的行数
     */
    Integer insert(Address address);

    /**
     * 统计某用户收货地址总数
     * @param uid 用户id
     * @return 数量
     */
    Integer countByUid(Integer uid);
}
