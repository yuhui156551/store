package com.yuhui.store.mapper;


import com.yuhui.store.entity.Address;
import com.yuhui.store.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AddressMapperTest {
    @Autowired
    private AddressMapper addressMapper;

    @Test
    void insert() {
        Address address = new Address();
        address.setUid(18);
        address.setName("admin");
        address.setPhone("17858802974");
        address.setAddress("雁塔区小寨赛格");
        System.out.println("rows-->" + addressMapper.insert(address));
    }

    @Test
    void countById(){
        System.out.println("收货地址总数："+addressMapper.countByUid(18));
    }
}