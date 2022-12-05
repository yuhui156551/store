package com.yuhui.store.service;

import com.yuhui.store.entity.Address;

/**
 * 处理收货地址数据的业务层接口
 */
public interface AddressService {
    /**
     * 创建新的收货地址
     * @param uid 用户id
     * @param username 用户名，即修改人
     * @param address 地址信息
     */
    void addNewAddress(Integer uid, String username, Address address);
}
