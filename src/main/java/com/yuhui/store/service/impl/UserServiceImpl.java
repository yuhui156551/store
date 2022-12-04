package com.yuhui.store.service.impl;

import com.yuhui.store.entity.User;
import com.yuhui.store.mapper.UserMapper;
import com.yuhui.store.service.UserService;
import com.yuhui.store.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void register(User user) {
        String username = user.getUsername();
        User result = userMapper.findByUsername(username);
        //用户名占用，抛出异常
        if (result != null) {
            throw new UsernameDuplicateException("尝试注册的用户名[" + username + "]已被占用");
        }

        Date now = new Date();

        //补全数据
        String salt = UUID.randomUUID().toString().toUpperCase();
        String md5Password = getMd5Password(user.getPassword(), salt);

        user.setPassword(md5Password);
        user.setSalt(salt); //保存盐值，登录时用来进行密码比对
        user.setIsDelete(0);

        user.setCreatedUser(username);
        user.setCreatedTime(now);
        user.setModifiedUser(username);
        user.setModifiedTime(now);

        //插入用户数据
        Integer rows = userMapper.insert(user);
        if (rows != 1) {
            throw new InsertException("添加用户数据时出现未知错误");
        }
    }

    @Override
    public User login(String username, String password) {
        // 调用userMapper的findByUsername()方法，根据参数username查询用户数据
        User user = userMapper.findByUsername(username);

        // 判断查询结果是否为null
        if (user == null) {
            // 是：抛出UserNotFoundException异常
            throw new UserNotFoundException("用户数据不存在");
        }

        // 判断查询结果中的isDelete是否为1
        if (user.getIsDelete() == 1) {
            // 是：抛出UserNotFoundException异常
            throw new UserNotFoundException("用户数据不存在");
        }

        // 从查询结果中获取盐值
        String salt = user.getSalt();
        // 调用getMd5Password()方法，将参数password和salt结合起来进行加密
        String md5Password = getMd5Password(password, salt);
        // 判断查询结果中的密码，与以上加密得到的密码是否不一致
        if (!user.getPassword().equals(md5Password)) {
            // 是：抛出PasswordNotMatchException异常
            throw new PasswordNotMatchException("密码验证失败");
        }

        // 创建新的User对象
        User result = new User();
        // 将查询结果中的uid、username、avatar封装到新的user对象中
        result.setUid(user.getUid());
        result.setUsername(user.getUsername());
        result.setAvatar(user.getAvatar());// 头像数据

        // 返回新的user对象,返回的数据是为了辅助前端页面做数据展示，例如头像，用户名等
        return result;
    }

    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        // 调用userMapper的findByUid()方法，根据参数uid查询用户数据
        User user = userMapper.findByUid(uid);
        // 检查查询结果是否为null，检查查询结果中的isDelete是否为1
        if (user == null || user.getIsDelete() == 1) {
            // 是：抛出UserNotFoundException异常
            throw new UserNotFoundException("用户数据不存在");
        }

        // 从查询结果中取出盐值
        // 将参数oldPassword结合盐值加密，得到oldMd5Password
        String oldMd5Password = getMd5Password(oldPassword, user.getSalt());
        // 判断查询结果中的password与oldMd5Password是否不一致
        if (!user.getPassword().contentEquals(oldMd5Password)) {
            // 是：抛出PasswordNotMatchException异常
            throw new PasswordNotMatchException("原密码错误");
        }

        // 将参数newPassword结合盐值加密，得到newMd5Password
        String newMd5Password = getMd5Password(newPassword, user.getSalt());
        // 创建当前时间对象
        Date date = new Date();
        // 调用userMapper的updatePasswordByUid()更新密码，并获取返回值
        Integer row = userMapper.updatePasswordByUid(uid, newMd5Password, username, date);

        // 判断以上返回的受影响行数是否不为1
        if (row != 1) {
            // 是：抛了UpdateException异常
            throw new UpdateException("更新用户数据时出现未知异常");
        }

    }

    @Override
    public User getByUid(Integer uid) {
        // 调用userMapper的findByUid()方法，根据参数uid查询用户数据
        User user = userMapper.findByUid(uid);
        // 判断查询结果是否为null,判断查询结果中的isDelete是否为1
        if (user == null || user.getIsDelete() == 1) {
            // 是：抛出UserNotFoundException异常
            throw new UserNotFoundException("用户数据不存在");
        }

        // 创建新的User对象
        // 回显数据
        // 将以上查询结果中的username/phone/email/gender封装到新User对象中
        User result = new User();
        result.setUsername(user.getUsername());
        result.setPhone(user.getPhone());
        result.setEmail(user.getEmail());
        result.setGender(user.getGender());

        // 返回新的User对象
        return result;
    }

    @Override
    public void changeInfo(Integer uid, String username, User user) {
        // 调用userMapper的findByUid()方法，根据参数uid查询用户数据
        User result = userMapper.findByUid(uid);
        // 判断查询结果是否为null,判断查询结果中的isDelete是否为1
        if (result == null || result.getIsDelete() == 1) {
            // 是：抛出UserNotFoundException异常
            throw new UserNotFoundException("用户数据不存在");
        }

        // 向参数user中补全数据：uid（页面停留时间过长，数据丢失）
        user.setUid(uid);
        // username因为不可修改，无需补全数据
        // 向参数user中补全数据：modifiedUser(username)
        user.setModifiedUser(username);
        // 向参数user中补全数据：modifiedTime(new Date())
        user.setModifiedTime(new Date());

        // 调用userMapper的updateInfoByUid(User user)方法执行修改，并获取返回值
        Integer row = userMapper.updateInfoByUid(user);

        // 判断以上返回的受影响行数是否不为1
        if (row != 1) {
            // 是：抛出UpdateException异常
            throw new UpdateException("更新数据时出现未知错误");
        }
    }

    @Override
    public void changeAvatar(Integer uid, String username, String avatar) {
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }

        Integer rows = userMapper.updateAvatarByUid(uid, avatar, username, new Date());

        if(rows != 1){
            throw new UpdateException("更新数据时出现未知错误");
        }
    }

    /**
     * 执行密码加密
     *
     * @param password 原始密码
     * @param salt     盐值
     * @return 加密后的密文
     */
    private String getMd5Password(String password, String salt) {
        /*
         * 加密规则：
         * 1、无视原始密码的强度
         * 2、使用UUID作为盐值，在原始密码的左右两侧拼接
         * 3、循环加密3次
         */
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }
}
