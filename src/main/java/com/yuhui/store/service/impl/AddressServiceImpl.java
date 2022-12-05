package com.yuhui.store.service.impl;

import com.yuhui.store.entity.Address;
import com.yuhui.store.mapper.AddressMapper;
import com.yuhui.store.mapper.UserMapper;
import com.yuhui.store.service.AddressService;
import com.yuhui.store.service.DistrictService;
import com.yuhui.store.service.exception.AddressCountLimitException;
import com.yuhui.store.service.exception.InsertException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

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
        if(count >= maxCount) {
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
        if(rows != 1) {
            throw new InsertException("插入数据时出现未知错误");
        }
    }
}
