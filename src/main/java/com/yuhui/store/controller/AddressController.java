package com.yuhui.store.controller;

import com.yuhui.store.entity.Address;
import com.yuhui.store.service.AddressService;
import com.yuhui.store.utils.JsonR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

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

    /** TODO 设计请求
     * 请求路径：/addresses
     * 请求参数：HttpSession session
     * 请求类型：GET
     * 响应结果：JsonResult<List<Address>>
     */
    @GetMapping({"","/"})
    public JsonR<List<Address>> getByUid(HttpSession session){
        Integer uid = getUidFromSession(session);

        List<Address> addresses = addressService.getByUid(uid);

        return new JsonR<>(OK,addresses);
    }

    /** TODO 设计请求
     * 请求路径：/addresses/{aid}/set_default
     * 请求参数：@PathVaraible("aid") Integer aid, HttpSession sesion
     * 请求类型：POST
     * 响应结果：JsonResult<Void>
     */
    @RequestMapping("{aid}/set_default")
    public JsonR<Void> setDefault(@PathVariable("aid") Integer aid,HttpSession session){
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        addressService.setDefault(aid,uid,username);

        return new JsonR<>(OK);
    }
}
