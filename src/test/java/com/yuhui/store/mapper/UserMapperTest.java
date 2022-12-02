package com.yuhui.store.mapper;

import com.yuhui.store.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    void insert() {
        User user = new User();
        user.setUsername("fan");
        user.setPassword("123");
        System.out.println("影响行数--> " + userMapper.insert(user));
    }

    @Test
    void findByUsername(){
        System.out.println(userMapper.findByUsername("fan"));
    }

    @Test
    void updatePasswordByUid(){
        Integer rows = userMapper.updatePasswordByUid(9, "6666", "小范", new Date());
        System.out.println(rows);
    }

    @Test
    void findByUid(){
        User user = userMapper.findByUid(9);
        System.out.println(user);
    }
}
