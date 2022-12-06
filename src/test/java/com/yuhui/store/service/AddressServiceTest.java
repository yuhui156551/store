package com.yuhui.store.service;

import com.yuhui.store.entity.Address;
import com.yuhui.store.service.exception.ServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AddressServiceTest {
    @Autowired
    private AddressService addressService;

    @Test
    void addNewAddress() {
        try {
            Address address = new Address();
            address.setPhone("123456789");
            address.setName("张三");
            address.setAddress("江西省吉安市");
            addressService.addNewAddress(18, "管理员", address);
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    void getByUid(){
        List<Address> addresses = addressService.getByUid(10);
        System.out.println(addresses.size());
        for (Address address : addresses) {
            System.out.println(address);
        }
    }
}
