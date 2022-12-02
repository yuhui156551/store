package com.yuhui.store.service;

import com.yuhui.store.entity.User;
import com.yuhui.store.service.exception.ServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

	@Test
    void register(){
		try {
			User user = new User();
			user.setUsername("hua");
			user.setPassword("123456");
			user.setGender(1);
			user.setPhone("17858802974");
			user.setEmail("fan@tedu.cn");
			user.setAvatar("xxxx");
			userService.register(user);
			System.out.println("注册成功！");
		} catch (ServiceException e) {
			System.out.println(e.getClass().getSimpleName());
			System.out.println(e.getMessage());
		}
	}

	@Test
	void login(){
		try {
			String username = "xiaozha";
			String password = "123456";
			User user = userService.login(username, password);
			System.out.println("登录成功！" + user);
		} catch (ServiceException e) {
			System.out.println("登录失败！" + e.getClass().getSimpleName());
			System.out.println(e.getMessage());
		}
	}

	@Test
	void changePassword(){
		try {
			userService.changePassword(7,"xiaozhao","123456","123");
			System.out.println("密码修改成功");
		} catch (ServiceException e) {
			System.out.println("密码修改失败！" + e.getClass().getSimpleName());
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void getByUid() {
		try {
			Integer uid = 7;
			User user = userService.getByUid(uid);
			System.err.println(user);
		} catch (ServiceException e) {
			System.out.println(e.getClass().getSimpleName());
			System.out.println(e.getMessage());
		}
	}

	@Test
	void changeInfo(){
		try {
			Integer uid = 7;
			String username = "数据管理员";
			User user = new User();
			user.setPhone("15512328888");
			user.setEmail("admin03@cy.cn");
			user.setGender(2);
			userService.changeInfo(uid, username, user);
			System.err.println("OK.");
		} catch (ServiceException e) {
			System.out.println(e.getClass().getSimpleName());
			System.out.println(e.getMessage());
		}
	}
}
