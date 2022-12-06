package com.yuhui.store.mapper;

import com.yuhui.store.entity.Address;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

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

    /**
     * 查询收获地址列表数据
     * @param uid 用户id
     * @return 数据
     */
    List<Address> findByUid(Integer uid);

    /**
     * 将某用户的所有收货地址设置为非默认地址
     * @param uid 收货地址归属的用户id
     * @return 受影响的行数
     */
    Integer updateNonDefaultByUid(Integer uid);

    /**
     * 将指定的收货地址设置为默认地址
     * @param aid 收货地址id
     * @param modifiedUser 修改执行人
     * @param modifiedTime 修改时间
     * @return 受影响的行数
     */
    Integer updateDefaultByAid(
            @Param("aid") Integer aid,
            @Param("modifiedUser") String modifiedUser,
            @Param("modifiedTime") Date modifiedTime);

    /**
     * 根据收货地址aid值，查询收货地址详情
     * @param aid 收货地址id
     * @return 匹配的收货地址详情，如果没有匹配的数据，则返回null
     */
    Address findByAid(Integer aid);
}
