package com.yuhui.store.controller;


import com.yuhui.store.controller.exception.FileEmptyException;
import com.yuhui.store.controller.exception.FileSizeException;
import com.yuhui.store.controller.exception.FileTypeException;
import com.yuhui.store.controller.exception.FileUploadIOException;
import com.yuhui.store.entity.User;
import com.yuhui.store.service.UserService;
import com.yuhui.store.utils.JsonR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletSecurityElement;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*springboot约定大于配置，越来越能感觉到了*/

@RestController
@RequestMapping("/users")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    /**
     * 注册
     *
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
     *
     * @param username 用户名
     * @param password 密码
     * @param session
     * @return OK+部分用户数据
     */
    @RequestMapping("login")
    public JsonR<User> login(String username, String password, HttpSession session) {
        // 调用业务对象的方法执行登录，并获取返回值
        User data = userService.login(username, password);

        // 向session对象中完成数据的绑定（全局）
        session.setAttribute("uid", data.getUid());
        session.setAttribute("username", data.getUsername());

        //获取session中的数据
        System.err.println(getUidFromSession(session));
        System.err.println(getUsernameFromSession(session));

        // 将以上返回值和状态码OK封装到响应结果中并返回
        return new JsonR<>(OK, data);
    }

    /**
     * 修改密码
     *
     * @param oldPassword 原密码
     * @param newPassword 新密码
     * @param session
     * @return OK or 异常
     */
    @RequestMapping("change_password")
    public JsonR<Void> changePassword(String oldPassword, String newPassword,
                                      HttpSession session) {
        // 调用session.getAttribute("")获取uid和username
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        // 调用业务对象执行修改密码
        userService.changePassword(uid, username, oldPassword, newPassword);
        // 返回成功
        return new JsonR<>(OK);
    }

    /**
     * 获取用户数据
     *
     * @param session
     * @return 回显数据
     */
    @GetMapping("get_by_uid")
    public JsonR<User> getByUid(HttpSession session) {
        // 从HttpSession对象中获取uid
        Integer uid = getUidFromSession(session);

        // 调用业务对象执行获取数据
        User data = userService.getByUid(uid);

        // 响应成功和数据
        return new JsonR<>(OK, data);
    }

    /**
     * 修改信息
     *
     * @param user    用户数据
     * @param session
     * @return OK or 异常
     */
    @RequestMapping("change_info")
    public JsonR<Void> changeInfo(User user, HttpSession session) {
        // 从HttpSession对象中获取uid和username
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);

        // 调用业务对象执行修改用户资料
        userService.changeInfo(uid, username, user);

        // 响应成功
        return new JsonR<Void>(OK);
    }

    /*头像文件大小的上限值(10MB)*/
    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;
    /*允许上传的头像的文件类型*/
    public static final List<String> AVATAR_TYPES = new ArrayList<String>();

    /*初始化允许上传的头像的文件类型*/
    static {
        AVATAR_TYPES.add("image/jpeg");
        AVATAR_TYPES.add("image/png");
        AVATAR_TYPES.add("image/bmp");
        AVATAR_TYPES.add("image/gif");
    }

    @PostMapping("change_avatar")
    public JsonR<String> changeAvatar(HttpSession session,
                                      // -->表单中name的值<input type="file" name="file"> , 前端传过来的数据才能对应上
                                      // 但此时name的值和变量名一致，可以省略RequestParam的书写
                                      @RequestParam("file") MultipartFile file) {
        // 判断上传的文件是否为空
        if (file.isEmpty()) {
            throw new FileEmptyException("上传的头像文件不允许为空");
        }

        // 判断上传的文件大小是否超出限制值
        if (file.getSize() > AVATAR_MAX_SIZE) {
            throw new FileSizeException("\"不允许上传超过\" + (AVATAR_MAX_SIZE / 1024) + \"KB的头像文件\"");
        }

        // 判断上传的文件类型是否超出限制
        String contentType = file.getContentType();
        if (!AVATAR_TYPES.contains(contentType)) {
            throw new FileTypeException("文件类型不支持，允许的文件类型：\n" + AVATAR_TYPES);
        }

        // 获取当前项目的绝对磁盘路径
        String realPath = session.getServletContext().getRealPath("upload");
        System.err.println(realPath);
        // 保存头像文件的文件夹,不存在则创建目录结构
        File dir = new File(realPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 获取文件名称，使用uuid生成新文件名
        String originalFilename = file.getOriginalFilename();// 文件名
        System.err.println(originalFilename);
        int beginIndex = originalFilename.lastIndexOf(".");// 小数点的索引
        String suffix = originalFilename.substring(beginIndex);// 文件后缀
        String filename = UUID.randomUUID() + suffix;// 新文件名

        // 创建文件对象，表示保存的头像文件
        File dest = new File(dir, filename);// 此时是一个空文件

        // 执行保存头像文件
        try {
            file.transferTo(dest);// 参数file中数据写入到这个空文件
        } catch (IOException e) {
            throw new FileUploadIOException("文件读写异常");
        }

        // 头像路径:项目下-->/
        String avatar = "/upload/" + filename;
        // 从Session中获取uid和username
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);

        // 将头像写入到数据库中
        userService.changeAvatar(uid, username, avatar);

        // 返回成功和头像路径
        return new JsonR<>(OK, avatar);
    }
}
