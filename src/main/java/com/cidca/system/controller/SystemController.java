package com.cidca.system.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.cidca.common.Constants;
import com.cidca.entity.TMuser;
import com.cidca.entity.TUser;
import com.cidca.system.service.UserService;
import com.cidca.util.MD5;
import com.cidca.util.RegexUtilxs;
import com.cidca.util.StringUtil;
import com.cidca.util.UserCounter;

@Controller
public class SystemController{

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/inputRegister")
	public ModelAndView inputRegister(String userid,HttpServletRequest request, HttpServletResponse response) throws Exception {
		//防止频繁请求”登录验证“以外的恶意请求20201218
		String clientIpAddress = UserCounter.getClientIpAddress(request);
		Date date = new Date();
		boolean frequency = Constants.getFrequency(clientIpAddress,date);
		if (!frequency) {
			//TODO 跳转到一个验证页面，验证通过后返回当前页
			return new ModelAndView(new RedirectView("login.do"));
		}
		ModelAndView mv = new ModelAndView("manager/inputRegister");
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value = "/saveMuser", produces = "text/html;charset=UTF-8")
	public Object saveMuser(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String idcard="wang";
		String mobile ="13718651580";
		String email ="4233970@qq.com";
		String telephone ="010-12345678";
		String password ="w1";
		Boolean result_mobile = RegexUtilxs.isMobileExact(mobile);
		Boolean result_email = RegexUtilxs.isEmail(email);
		Boolean result_telephone = RegexUtilxs.isTel(telephone);
		if (result_mobile && result_email && result_telephone) {
			String clientIpAddress = UserCounter.getClientIpAddress(request);
			TMuser muser = userService.findByIdcard(idcard);
//			if (null==muser) {
//				MD5 md5 = new MD5();
//				TMuser po = new TMuser();
//				po.setCreatetime(new Date());
//				po.setUpdatetime(new Date());
//				if (StringUtils.isEmpty(po.getUuid())) {po.setUuid(StringUtil.getRandomHexToUpperCase());}//20210323
//				UuidCache.CacheDataMap.get("muser").put(po.getUuid(), po.getIdcard());
//				systemService.saveOrUpdateMuser(po);
//				String md5ofStr = md5.getMD5ofStr(password);
//				String updatePwSql=" update t_muser set PASSWORDD ='"+md5ofStr+"' where ID='"+po.getId()+"'";
//				systemService.updateBySql(updatePwSql);
//				TUserRole v = new TUserRole();
//				v.setUserid(email);
//				v.setRoleid(Constants.ROLEXY);
//				systemService.saveUserRole(v);
//				if (StringUtils.isNotEmpty(po.getEmail())) {
//					try {
//						// 发邮件
//						StringBuffer sb = new StringBuffer();
//						sb.append(po.getFullname()+"先生/女士：<br><br>");
//						sb.append("&nbsp;&nbsp;&nbsp;&nbsp;您已成功注册对外援助统计数据直报平台，您的登录账号为注册证件号码。系统登录地址为：http://www.cidca.gov.cn/fas<br>");
//						sb.append("<br><br><br>");
//						sb.append("国家国际发展合作署<br>");
//						sb.append("地址：北京东城区东安门大街82号<br>");
//						sb.append("邮编：100006<br>");
//						sb.append("<br>");
//						sb.append("本邮件为系统自动发送，请勿回复。");
//						//MailService.SendMail(po.getEmail(), "对外援助统计数据直报平台注册成功",sb.toString());
//						//3phase--自动短信，第一次注册发短信--20180921
//						if (StringUtils.isNotEmpty(po.getMobile())){//注册时只要求填写常用手机号码，所以不用考虑备用手机
//							//SendSms.sendMsg(mobile, po.getFullname()+" 老师，您已注册成功。请登录对外援助统计数据直报平台 维护 意向制度及需求专业覆盖。");
//						}
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//				systemService.saveOrUpdateTAuditLog(new TAuditLog(email,clientIpAddress,new Date(),"新增用户"+email));
//			}
		}
		return StringUtil.ConvertToStringWithJSON(true, "注册成功");
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "/saveOrUpdateUser", produces = "text/html;charset=UTF-8")
	public Object saveOrUpdateUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		TUser user = (TUser) request.getSession().getAttribute(Constants.SESSION_KEY);
		String userid = request.getParameter("userid");
		String username = request.getParameter("username");
		String deptid = request.getParameter("deptid");
		String tel = request.getParameter("tel");
		String email = request.getParameter("email");
		String emailpwd = request.getParameter("emailpwd");
		String mobile = request.getParameter("mobile");
		String[] roleids = request.getParameterValues("roleid");
		String zhize = request.getParameter("zhize");
		String lines = request.getParameter("lines");
		String str = Arrays.toString(roleids);
		if(StringUtils.isNotEmpty(str)){
			str=str.substring(1,str.length()-1);
		}
		//保存用户.userid是主键所以不能修改用户名，否则会新产生一条数据.2017-07-13
		MD5 md5 = new MD5();
		TUser po = userService.findByUserid(userid);
//		if (po!= null) {
//			po.setUserid(userid);
//			po.setUsername(username);
//			po.setDept(systemService.getDept(Integer.valueOf(deptid)));
//			po.setTel(tel);
//			if (StringUtils.isNotEmpty(email)) {
//				po.setEmail(email);
//			}
//			po.setMobile(mobile);
//			po.setZhize(zhize);
//			po.setLine(systemService.getBaseData(Integer.parseInt(lines)));
//			po.setEmailpwd(emailpwd);
//			po.setRoleid(str);
//			systemService.saveOrUpdateUser(po);
//			String clientIpAddress = UserCounter.getClientIpAddress(request);
//			systemService.saveOrUpdateTAuditLog(new TAuditLog(userid,clientIpAddress,new Date(), user.getUserid()+"更新了系统用户："+po.getUserid()));
//		} else {
//			po = new TUser();
//			po.setUserid(userid);
//			po.setUsername(username);
//			po.setDept(systemService.getDept(Integer.valueOf(deptid)));
//			po.setTel(tel);
//			if (StringUtils.isNotEmpty(email)) {
//				po.setEmail(email);
//			}
//			po.setMobile(mobile);
//			po.setZhize(zhize);
//			po.setLine(systemService.getBaseData(Integer.parseInt(lines)));
//			po.setEmailpwd(emailpwd);
//			po.setRoleid(str);
//			systemService.saveOrUpdateUser(po);
//			String md5ofStr = md5.getMD5ofStr("fas1111");
//			String updatePwSql=" update T_USER set PASSWORDD ='"+md5ofStr+"' where userid='"+po.getUserid()+"'";
//			systemService.updateBySql(updatePwSql);
//			String clientIpAddress = UserCounter.getClientIpAddress(request);
//			systemService.saveOrUpdateTAuditLog(new TAuditLog(userid,clientIpAddress,new Date(), user.getUserid()+"新增了系统用户："+po.getUserid()));
//		}
//		// 保存用户角色
//		systemService.delUserRole(userid);
//		if (roleids != null) {
//			for (int i = 0; i < roleids.length; i++) {
//				TUserRole v = new TUserRole();
//				v.setUserid(userid);
//				v.setRoleid(Integer.valueOf(roleids[i]));
//				systemService.saveUserRole(v);
//			}
//		}
//		// 封装
//		Map<String, Object> map1 = new HashMap<String, Object>();
//		map1.put("success", true);
//		Gson gson = new Gson();
//		return gson.toJson(map1);
		return null;
	}
	
	
}
