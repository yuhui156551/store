package com.yuhui.store.controller;

import com.yuhui.store.entity.Address;
import com.yuhui.store.service.AddressService;
import com.yuhui.store.utils.JsonR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/addresses")
public class AddressController extends BaseController {
    @Autowired
    private AddressService addressService;

    @RequestMapping("add_new_address")
    public JsonR<Void> addNewAddress(Address address, HttpSession session) {
        // 从Session中获取uid和username
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);

        // 新增地址数据
        addressService.addNewAddress(uid, username, address);

        // 响应
        return new JsonR<>(OK);
    }
}
