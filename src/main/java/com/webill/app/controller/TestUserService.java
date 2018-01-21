package com.webill.app.controller;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.webill.app.util.PageModel;
import com.webill.core.model.User;
import com.webill.core.model.UserInfo;
import com.webill.core.service.IUserMongoDBService;

@Controller
@RequestMapping("/mongoDB")
public class TestUserService {

	@Autowired
	private IUserMongoDBService iUserMongoDBService;

	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public void save() {
		UserInfo user = new UserInfo();
		user.setName("王五");
		user.setAge(29);
		user.setBirth(new Timestamp(System.currentTimeMillis()));
		iUserMongoDBService.save(user);
		System.out.println("已生成ID:" + user.getId());
	}

	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public void find() {
		UserInfo user = iUserMongoDBService.find("5a585d1b0cf0744a0b48204c");
		System.out.println(user.getName());
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public void update() {
//		UserInfo user = iUserMongoDBService.find("5a585d1b0cf0744a0b48204c");
		UserInfo user = new UserInfo();
		user.setName("张三");
		user.setBirth(new Timestamp(System.currentTimeMillis()));
		user.setSex("男");
//		user.setAge(151);
		iUserMongoDBService.updateField(user);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public void delete() {
		iUserMongoDBService.delete("58edef886f03c7b0fdba51b9");
	}

	@RequestMapping(value = "/findAll", method = RequestMethod.GET)
	public void findAll() {
		List<UserInfo> list = iUserMongoDBService.findAll("age desc");
		for (UserInfo u : list) {
			System.out.println(u.getName());
		}
	}

	@RequestMapping(value = "/findByProp", method = RequestMethod.GET)
	public void findByProp() {
		List<UserInfo> list = iUserMongoDBService.findByProp("name", "张三");
		for (UserInfo u : list) {
			System.out.println(u.getName());
		}
	}

	@RequestMapping(value = "/findByProps", method = RequestMethod.GET)
	public void findByProps() {
		List<UserInfo> list = iUserMongoDBService.findByProps(new String[] { "name", "age" }, new Object[] { "张三", 18 });
		for (UserInfo u : list) {
			System.out.println(u.getName());
		}
	}

	@RequestMapping(value = "/pageAll", method = RequestMethod.GET)
	public void pageAll() {
		PageModel<UserInfo> page = iUserMongoDBService.pageAll(1, 10);
		System.out.println("总记录：" + page.getTotalCount() + "，总页数：" + page.getTotalPage());
		for (UserInfo u : page.getList()) {
			System.out.println(u.getName());
		}
	}

	@RequestMapping(value = "/pageByProp", method = RequestMethod.GET)
	public void pageByProp() {
		PageModel<UserInfo> page = iUserMongoDBService.pageByProp(1, 10, "name", "张三");
		System.out.println("总记录：" + page.getTotalCount() + "，总页数：" + page.getTotalPage());
		for (UserInfo u : page.getList()) {
			System.out.println(u.getName());
		}
	}

}
