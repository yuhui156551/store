package com.yuhui.store.service;

import com.yuhui.store.entity.Address;

import java.util.List;

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

    /**
     * 查询某用户的收货地址列表数据
     * @param uid 收货地址归属的用户id
     * @return 该用户的收货地址列表数据
     */
    List<Address> getByUid(Integer uid);

    /**
     * 设置默认收货地址
     * @param aid 收货地址id
     * @param uid 归属的用户id
     * @param username 当前登录的用户名
     */
    void setDefault(Integer aid, Integer uid, String username);

    /**
     * 删除收货地址
     * @param aid 收货地址id
     * @param uid 归属的用户id
     * @param username 当前登录的用户名
     */
    void delete(Integer aid,Integer uid, String username);
}
