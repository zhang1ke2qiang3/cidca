package com.cidca.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cidca.common.Constants;
import com.cidca.entity.TAuditLog;
import com.cidca.entity.TMuser;
import com.cidca.entity.TPermission;
import com.cidca.system.service.SystemService;
import com.cidca.util.DateTools;
import com.cidca.util.StringUtil;
import com.cidca.util.UserCounter;

/**
 * 需要认证的放到这里
 * @author mingtian
 *
 */
@SuppressWarnings("unused")
@Controller
@RequestMapping("/sys")
public class SystemController{

	@Autowired
	private SystemService systemService;

	/**
	 * 登录后的主页
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	//	@RequiresRoles("1")
	//	@RequiresPermissions("external")
	@RequestMapping("/welcome")
	public String welcome(HashMap<String, Object> map,Model model,HttpServletRequest request) throws Exception {
		//		TMuser user = (TMuser)request.getSession().getAttribute(Constants.SESSION_KEY);
		//		String principal = (String)SecurityUtils.getSubject().getPrincipal();//用shiro获取当前登录用户名
		//model.addAttribute("user","欢迎："+user.getFullname());//两种方法都可以，和spring model and view一样
		model.addAttribute("user","Hi 王晓华");//两种方法都可以，和spring model and view一样
		map.put("title", "对外援助统计数据直报平台 ");
		return "/sys/welcome";// 自动把String解析为视图
	}
	
	/**
	 * 主页-获取相关基本信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/index")
	public @ResponseBody Map<String, Object> index(HttpServletRequest request,HttpServletResponse response) throws Exception {
		//public @ResponseBody Object index(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		TMuser user = (TMuser)request.getSession().getAttribute(Constants.SESSION_KEY);
		if (null==user) {
			map.put("resultcode","200");
			map.put("value", "请先登录");
			return map;
		}
		systemService.save(new TAuditLog(user.getIdcard(), UserCounter.getClientIpAddress(request),DateTools.getCurrentTime(), user.getIdcard()+"登录成功"));
		String userid = user.getIdcard();
		List<TPermission> menuList = systemService.getPermessionByUserid(Constants.ONE,userid);
		for(TPermission tpermission : menuList){
			List<TPermission> sublist = systemService.getPermessionByUserid(tpermission.getCode(),userid);
			tpermission.setSubList(sublist);
		}
		map.put("resultcode","100");
		map.put("userid", userid);
		map.put("userName", user.getFullname());
		map.put("menuList", menuList);
		return map;
	}

}



