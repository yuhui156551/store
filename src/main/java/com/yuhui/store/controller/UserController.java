package com.yuhui.store.controller;


import com.yuhui.store.entity.User;
import com.yuhui.store.service.UserService;
import com.yuhui.store.utils.JsonR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletSecurityElement;
import javax.servlet.http.HttpSession;

/*springboot约定大于配置，越来越能感觉到了*/

@RestController
@RequestMapping("/users")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    /**
     * 注册
     * @param user 注册填写的数据
     * @return OK or 异常
     */
    @RequestMapping("reg")
    public JsonR<Void> reg(User user) {

        userService.register(user);

        return new JsonR<Void>(OK);
    }

    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @param session
     * @return OK+部分用户数据
     */
    @RequestMapping("login")
    public JsonR<User> login(String username, String password, HttpSession session){
        // 调用业务对象的方法执行登录，并获取返回值
        User data = userService.login(username, password);

        // 向session对象中完成数据的绑定（全局）
        session.setAttribute("uid",data.getUid());
        session.setAttribute("username",data.getUsername());

        //获取session中的数据
        System.out.println(getUidFromSession(session));
        System.out.println(getUsernameFromSession(session));

        // 将以上返回值和状态码OK封装到响应结果中并返回
        return new JsonR<User>(OK,data);
    }

    /**
     * 修改密码
     * @param oldPassword 原密码
     * @param newPassword 新密码
     * @param session
     * @return OK or 异常
     */
    @RequestMapping("change_password")
    public JsonR<Void> changePassword(String oldPassword,String newPassword,
                                      HttpSession session){
        // 调用session.getAttribute("")获取uid和username
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        // 调用业务对象执行修改密码
        userService.changePassword(uid,username,oldPassword,newPassword);
        // 返回成功
        return new JsonR<>(OK);
    }

    /**
     * 获取用户数据
     * @param session
     * @return 回显数据
     */
    @GetMapping("get_by_uid")
    public JsonR<User> getByUid(HttpSession session){
        // 从HttpSession对象中获取uid
        Integer uid = getUidFromSession(session);

        // 调用业务对象执行获取数据
        User data = userService.getByUid(uid);

        // 响应成功和数据
        return new JsonR<>(OK,data);
    }

    /**
     * 修改信息
     * @param user 用户数据
     * @param session
     * @return OK or 异常
     */
    @RequestMapping("change_info")
    public JsonR<Void> changeInfo(User user,HttpSession session){
        // 从HttpSession对象中获取uid和username
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);

        // 调用业务对象执行修改用户资料
        userService.changeInfo(uid,username,user);

        // 响应成功
        return new JsonR<Void>(OK);
    }
}
