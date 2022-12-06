package com.yuhui.store.service.impl;

import com.yuhui.store.entity.Address;
import com.yuhui.store.mapper.AddressMapper;
import com.yuhui.store.mapper.UserMapper;
import com.yuhui.store.service.AddressService;
import com.yuhui.store.service.DistrictService;
import com.yuhui.store.service.exception.AddressCountLimitException;
import com.yuhui.store.service.exception.AddressNotFoundException;
import com.yuhui.store.service.exception.InsertException;
import com.yuhui.store.service.exception.UpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private DistrictService districtService;

    @Value("${user.address.max-count}")
    private Integer maxCount;

    @Override
    public void addNewAddress(Integer uid, String username, Address address) {
        //TODO 自己捋一遍思路，自己写

        // 根据uid查询收货地址总数，若超出限制，抛异常
        Integer count = addressMapper.countByUid(uid);
        if (count >= maxCount) {
            throw new AddressCountLimitException("收货地址数量已达上限");
        }

        // 根据code补全省市区名称
        address.setProvinceName(districtService.geyNameByCode(address.getProvinceCode()));
        address.setCityName(districtService.geyNameByCode(address.getCityCode()));
        address.setAreaName(districtService.geyNameByCode(address.getAreaCode()));

        // 得到正确的isDefault值(是否默认：1-默认,0-不默认)
        // 当前插入的收货地址是第一条，将其作为默认的收货地址
        Integer idDefault = count == 0 ? 1 : 0;
        address.setIsDefault(idDefault);

        // 补全数据
        address.setUid(uid);
        Date now = new Date();
        address.setCreatedUser(username);
        address.setCreatedTime(now);
        address.setModifiedUser(username);
        address.setModifiedTime(now);

        // 插入数据
        Integer rows = addressMapper.insert(address);
        if (rows != 1) {
            throw new InsertException("插入数据时出现未知错误");
        }
    }

    @Override
    public List<Address> getByUid(Integer uid) {
        // TODO 看文档自己捋思路自己写了，不会的再看视频

        List<Address> addresses = addressMapper.findByUid(uid);

        // 节省流量，给一些不展示的值设为null
        // 其实不是很理解，为什么查的时候直接查指定数据不就行了
        for (Address address : addresses) {
            address.setUid(null);
            address.setProvinceCode(null);
            address.setCityCode(null);
            address.setAreaCode(null);
            address.setCreatedUser(null);
            address.setCreatedTime(null);
            address.setModifiedUser(null);
            address.setModifiedTime(null);
        }
        return addresses;
    }

    @Transactional
    @Override
    public void setDefault(Integer aid, Integer uid, String username) {
        // TODO 添加@Transactional注解

        // 根据aid获取收货地址数据
        Address addresses = addressMapper.findByAid(aid);
        // 判断查询结果是否为null
        if (addresses == null) {
            throw new AddressNotFoundException("收获数据不存在");
        }

        // 设置所有收获地址为非默认
        Integer rows = addressMapper.updateNonDefaultByUid(uid);
        // 如果rows的值小于1，抛异常
        if (rows < 1) {
            throw new UpdateException("更新数据时出现未知异常");
        }

        // 将指定aid的收货地址设为默认
        Integer row = addressMapper.updateDefaultByAid(aid, username, new Date());
        if(row != 1){
            throw new UpdateException("更新数据时出现未知异常");
        }
    }
}
