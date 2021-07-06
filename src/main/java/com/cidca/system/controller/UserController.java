package com.cidca.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cidca.entity.TUser;
import com.cidca.system.service.UserService;
import com.cidca.util.StringUtil;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/list")
	@ResponseBody
	public  Object findAll(){
		Map<String,Object> map=new HashMap<>();
		List<TUser> findAll = userService.findAll();
		map.put("msg","部署成功");
		map.put("userList",findAll);
		StringUtil.ConvertToStringWithJSON(0, findAll);
		return  map;
	}


}
