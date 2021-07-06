package com.cidca.system.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cidca.common.Constants;
import com.cidca.entity.TMuser;
import com.cidca.entity.TUser;
import com.cidca.entity.TUserRole;
import com.cidca.system.dao.BaseDataDao;
import com.cidca.system.service.UserRoleService;
import com.cidca.system.service.UserService;
import com.cidca.util.BtoAUtil;
import com.cidca.util.DateTools;
import com.cidca.util.MD5;
import com.cidca.util.SendSms;
import com.cidca.util.StringUtil;
import com.cidca.util.UserCounter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@RequestMapping("/")
public class LoginController {

	//@Autowired(required=true)
	@Autowired
	private UserService userService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private BaseDataDao basedataDao;

	@RequestMapping(value = "/idcardLoginTest", method = RequestMethod.GET)
	@ResponseBody
	public String idcardLogin() {
		return "证件号码登录";
	}

	@RequestMapping(value="/insertOrUpdateTMuser")
	@ResponseBody
	public String insertTMuser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//		Date date = new Date();
		//		TMuser tMuser = new TMuser(basedataDao.getOne(0), "www", "A95DCB8AEBB202EFEEDB10D5538EDEB9", "测试", "CS", "13718651580", 
		//				"4233970@qq.com", basedataDao.getOne(3), "组织名称", "Q1W2E3R4", basedataDao.getOne(370), 
		//				"北京市房山区拱辰街道办事处拱辰北大街45号", "单位负责人", "010-12345678", null, null, date, date, Constants.ZERO, 0, null, null);
		//		userService.insert(tMuser);
		//		TMuser muser = userService.findByIdcard("qqq");
		//		String userPassword="";
		//		List<TMuser> list = userService.findByIdcardAndPassword("qqq", "A95DCB8AEBB202EFEEDB10D5538EDEB9");
		//		if (list.size()==1) {
		//			userPassword = list.get(0).getPassword();
		//		}
		//		muser.setIdcard("www");
		//		userService.updateTMuser(muser);
		return "insertTMuser ok";
	}

	@RequestMapping("/login")
	public ModelAndView login() throws Exception {
		ModelAndView mv = new ModelAndView("manager/login");
		mv.addObject("title", "loginPage");
		//mav.setViewName("/manager/login");
		return mv;
	}
	
	@RequestMapping("/login2")
	public ModelAndView login2() throws Exception {
		ModelAndView mv = new ModelAndView("manager/login2");
		mv.addObject("title", "loginPage");
		//mav.setViewName("/manager/login2");
		return mv;
	}


	@RequestMapping(value="/welcome")
	public ModelAndView welcome(HttpServletRequest request, HttpServletResponse response) {
		TUser user= (TUser)request.getSession().getAttribute(Constants.SESSION_KEY);
		ModelAndView mav = new ModelAndView();
		if (null!=user) {
			mav.addObject("title", "welcome back"+user.getUsername());
		}
		mav.setViewName("/manager/welcome");
		return mav;
	}

	/**登录中*/
	@SuppressWarnings({ "unchecked", "unused" })
	@ResponseBody
	@RequestMapping(value = "/idcardLogin",produces="text/html;charset=UTF-8")	
	public Object idcardLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		response.setContentType("application/json");
		String rightCode = (String) request.getSession().getAttribute("rightCode");//服务器生成的验证码
		Date date = new Date();
		Map<String, String> jspMap =new HashMap<String, String>();
		Gson gson = new GsonBuilder() .setDateFormat("yyyy-MM-dd").create(); 
		request.getSession().removeAttribute(Constants.SESSION_KEY);
		
		String uJson = (String) request.getParameter("uJson");
		uJson = BtoAUtil.atob(uJson);//var uJson="{'one':"+userid+",'two':"+password+"}";
		JSONObject object = JSON.parseObject(uJson);
		String userid=object.getString("one");
		String password=object.getString("two");
		String identity=object.getString("identity");
		String tryCode=object.getString("tryCode");//用户输入的验证码
		System.out.println("rightCode:" + rightCode + " ———— tryCode:" + tryCode);
		if (!rightCode.equals(tryCode)) {
			return StringUtil.ConvertToStringWithJSON(false, "验证码错误,请再输一次!");
		} 
		
		String ip=UserCounter.getClientIpAddress(request);
		Calendar nowTime = Calendar.getInstance();//得到当前时间
		Date sTime = nowTime.getTime();
		nowTime.add(Calendar.MINUTE, 5);
		Date lockTime = nowTime.getTime();
		//错误3次出3次以上锁定10分钟
		boolean checkLoginNum4 = checkLoginNum4ByUser(userid);
		if (checkLoginNum4) {
			if (Constants.ZERO.equals(identity)) {
				//系统用户
				TUser user = userService.findByUserid(userid);
				if (null!=user) {
					user.setTimeLock(lockTime);
					userService.updateUser(user);
					//					systemService.executeBySql("insert into t_login(userid,ip,logintime,TARGETT,ACTIONCONTENT,RESULTT) "
					//							+ " values('"+userid+"','"+ip+"',current timestamp,'timelock','账号锁定','success')");
				}
			}else{
				//外部用户
				if (checkLoginNum4) {
					TMuser muser = userService.findByIdcard(userid);
					if (null!=muser) {
						muser.setTimeLock(lockTime);//时间锁
						userService.updateTMuser(muser);
						//						systemService.executeBySql("insert into t_login(userid,ip,logintime,TARGETT,ACTIONCONTENT,RESULTT) "
						//								+ " values('"+userid+"','"+ip+"',current timestamp,'timelock','账号锁定','success')");
					}
				}
			}
			Constants.errorMap.remove(userid);//清除，防止时间锁到了后不能登录
			return StringUtil.ConvertToStringWithJSON(false, "请勿频繁登录，请与"+DateTools.getDateMilliFormat(lockTime)+"再试");
		}

		MD5 md5 = new MD5();//加密工具
		String md5Str ="";
		if (!StringUtils.isEmpty(password)) {
			md5Str = md5.getMD5ofStr(password);//输入的密码转换密文
		}
		TUser user = new TUser();
		if (Constants.ZERO.equals(identity)) {
			user = userService.findByUserid(userid);
			if (user != null) {
				if (null!=user.getTimeLock()) {
					long calTime = DateTools.CalTime(user.getTimeLock(), sTime);// 计算两个时间差，返回为分钟。
					if (user.getTimeLock().after(sTime)) {
						return StringUtil.ConvertToStringWithJSON(false, "请勿频繁登录，请与"+calTime+"再试");
					}else {
						user.setTimeLock(null);
					}
				}
				String userPassword="";
				List<TUser> list = userService.findByUseridAndPassword(user.getUserid(),password);
				if (list.size()==1) {
					userPassword = list.get(0).getPassword();
				}
				if(md5Str.equals(userPassword)){
					if (null!=user.getTimeLock()) {//登录成功后移除锁
						user.setTimeLock(null);
						userService.updateUser(user);
					}
				}else {
					Integer num =0;
					if (null!=Constants.errorMap.get(userid)) {
						num=(Integer)Constants.errorMap.get(userid);
					}
					Constants.errorMap.put(userid, num+=1);
					//					systemService.executeBySql("insert into t_login(userid,ip,logintime,TARGETT,ACTIONCONTENT,RESULTT) "
					//							+ " values('"+userid+"','"+ip+"',current timestamp,'timelock','"+user.getUserid()+"密码校验不通过','success')");
					return StringUtil.ConvertToStringWithJSON(false, "用户名或者密码验证失败");
				}
			}
		}else{
			//2学员/评审员/技术专家
			TMuser muser = userService.findByIdcard(userid);
			if(muser != null){
				//时间锁的，根据情况判断是否允许登录
				if (null!=muser.getTimeLock()) {
					if (muser.getTimeLock().after(sTime)) {
						long calTime = DateTools.CalTime(muser.getTimeLock(), sTime);
						return StringUtil.ConvertToStringWithJSON(false, "请勿频繁登录，请与"+calTime+"再试");
					}else {
						muser.setTimeLock(null);
					}
				}
				String muserPassword="";
				List<TMuser> list = userService.findByIdcardAndPassword(muser.getIdcard(),password);
				if (list.size()==1) {
					muserPassword = list.get(0).getPassword();
				}
				if(md5Str.equals(muserPassword)){
					user.setUserid(muser.getIdcard());
					user.setUsername(muser.getFullname());
					user.setUuid(muser.getUuid());
					//设置首次登录标志
					String loginnum=muser.getLoginnum();
					if(StringUtils.isEmpty(loginnum)){
						muser.setLoginnum(Constants.ONE);
					}else{
						muser.setLoginnum(Integer.toString(Integer.valueOf(loginnum)+1));
					}
					muser.setUpdatetime(new Date());
					if (null!=muser.getTimeLock()) {muser.setTimeLock(null);}//登录成功后移除锁
					if (StringUtils.isEmpty(muser.getUuid())) {muser.setUuid(StringUtil.getRandomHexToUpperCase());}
					userService.updateTMuser(muser);
				}else{
					Integer num =0;
					if (null!=Constants.errorMap.get(userid)) {
						num=(Integer)Constants.errorMap.get(userid);
					}
					Constants.errorMap.put(userid, num+=1);
					//					systemService.executeBySql("insert into t_login(userid,ip,logintime,TARGETT,ACTIONCONTENT,RESULTT) "
					//							+ " values('"+userid+"','"+ip+"',current timestamp,'timelock','"+muser.getIdcard()+"密码校验不通过','success')");
					return StringUtil.ConvertToStringWithJSON(false, "用户名或者密码验证失败");
				}
			}else{
				return StringUtil.ConvertToStringWithJSON(false, "未找到该用户，请您先注册用户");
			}
		}

		//设置权限、缓存
		if (user != null) {
			List<TUserRole> list=userRoleService.findAllByUserid(userid);
			String roleIds="";
			if(list!=null){
				for(TUserRole bo:list){
					roleIds+=bo.getRoleid()+",";
				}
				if(roleIds.length()>0){roleIds=roleIds.substring(0,roleIds.length()-1);}
				if (Constants.ZERO.equals(identity)) {
					roleIds+=",00";
				}
				roleIds+=",99";
			}
			user.setRoleIds(roleIds);
			request.getSession().setAttribute(Constants.SESSION_KEY, user);
		}
		//记录日志
		//systemService.executeBySql("insert into t_login(userid,ip,logintime) values('"+userid+"','"+ip+"',current timestamp)");
		Constants.errorMap.put(userid,0);//重置校验数值，防止登录失败
		mv.setViewName("/manager/welcome");
		return mv;
	}

	@RequestMapping(value = "/mobileLoginTest", method = RequestMethod.GET)
	@ResponseBody
	public String mobileLogin() {
		return "手机登录";
	}

	@ResponseBody
	@RequestMapping(value = "/mobileLogin",produces="text/html;charset=UTF-8")	
	public Object mobileLoginning(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> jspMap =new HashMap<String, String>();
		String phoneNum = (String) request.getParameter("phone-num");
		String dynamicPw = (String) request.getParameter("dynamicPw");
		String attribute = (String)request.getSession().getAttribute("sendTime");
		String mvc = (String)request.getSession().getAttribute("mvc");//手机+验证码串
		if (StringUtils.isNotEmpty(attribute) && StringUtils.isNotEmpty(mvc)) {
			int dateDiff = DateTools.dateDiff(attribute);
			String[] split = mvc.split("-");
			String mobile = split[0];
			String svc = split[1];
			if (5>=dateDiff && phoneNum.equals(mobile)&&svc.equals(dynamicPw)) {//验证码不超过5分钟，同一个手机号码及密码
				request.getSession().removeAttribute("verificationCode");
				TUser user=null;
				//验证通过，开始登陆
				TMuser user1 = userService.findByMobile(phoneNum);
				if(user1 != null){
					user=new TUser();
					user.setUserid(user1.getIdcard());
					user.setUsername(user1.getFullname());
					String loginnum=user1.getLoginnum();//设置首次登录标志
					if(StringUtils.isEmpty(loginnum)){
						user1.setLoginnum(Constants.ONE);
					}else{
						user1.setLoginnum(Integer.toString(Integer.valueOf(loginnum)+1));
					}
					user1.setUpdatetime(new Date());
					userService.updateTMuser(user1);
					//设置角色
					List<TUserRole> list=userRoleService.findAllByUserid(user.getUserid());
					String roleIds="";
					String rolenames="";
					if(list!=null){
						for(TUserRole bo:list){
							roleIds+=bo.getRoleid()+",";
						}
						if(roleIds.length()>0){roleIds=roleIds.substring(0,roleIds.length()-1);}
						if(rolenames.length()>0){rolenames=rolenames.substring(0,rolenames.length()-1);}
					}
					user.setRoleIds(roleIds);
					user.setRoleNames(rolenames);
					request.getSession().setAttribute(Constants.SESSION_KEY, user);
					//记录日志
					//systemService.executeBySql("insert into t_login(userid,ip,logintime) values('"+user1.getIdcard()+"','"+user1.getMobile()+"',current timestamp)");
					jspMap.put("success", "true");
					jspMap.put("msg", "ok");
				} 
			}else{
				jspMap.put("success", "false");
				jspMap.put("msg", "overdue");//验证码校验失败或已过期，请重新获取
				return jspMap;
			}
		}
		Gson gson = new GsonBuilder() .setDateFormat("yyyy-MM-dd").create(); 
		return gson.toJson(jspMap);
	}


	@RequestMapping(value = "/logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getSession().setAttribute(Constants.SESSION_KEY, null);
		request.getSession().invalidate();
		ModelAndView mv = new ModelAndView(new RedirectView("login.do"));
		String clientIpAddress = UserCounter.getClientIpAddress(request);//移除时间校验缓存20201218
		if (StringUtils.isNotEmpty(clientIpAddress)) {
			Constants.checkCountMap.remove(clientIpAddress);
		}
		return mv;
	}

	//-----------------------------公共方法区-----------------------------

	//大于3次 因登录网页也属于刷新一次所以这里设置是4（锁定账号）
	public boolean checkLoginNum4ByUser(String userid) {
		boolean flag=false;
		Integer num = (Integer)Constants.errorMap.get(userid);
		System.out.println(userid+"当前次数锁==>"+num);
		if (null!=num && num>=4) {
			flag=true;
		}
		return flag;
	}

	//注册验证手机号码
	@ResponseBody
	@RequestMapping(value = "/getVerificationCode", produces = "text/html;charset=UTF-8")
	public Object getVerificationCode(HttpServletRequest request,HttpServletResponse response)throws Exception{
		boolean flag=false;
		String msg="";
		Map<String, Object> map=new HashMap<String, Object>();
		Gson gson = new Gson();
		String mobilee=request.getParameter("mobilee");
		String str = (String)request.getSession().getAttribute("verificationCode"+","+mobilee);
		TMuser muser = userService.findByMobile(mobilee);
		if (null!=muser) {
			msg="该号码已被注册。如继续使用本号码注册请联系管理员。";
		}else {
			if (!StringUtils.isEmpty(str)) {
				Long split = Long.parseLong(str.split(",")[0]);
				//String mobile2=split[1];
				if ((System.currentTimeMillis()-split)<300000) {//5分钟
					map.put("success", false);
					map.put("msg", "5分钟内不允许重复获取");
					return gson.toJson(map); 
				}
			}
			String validate=String.valueOf(new Random().nextInt(899999) + 100000);
			String content = "您的验证码为：" + validate+"，有效期为5分钟，只能使用一次。";
			SendSms.sendMsg(mobilee, content);
			long millis = System.currentTimeMillis();
			request.getSession().setAttribute(mobilee+"mvm", mobilee+","+validate+","+millis);//手机+验证码，登陆时拆分用 13718651580,181196,1568181607177
			request.getSession().setAttribute("mobilee", mobilee);//本方法用，判断是否同一个手机号码在规定时间内重复获取验证码
			request.getSession().setAttribute("verificationCode"+","+mobilee, millis+","+mobilee);//防止重复获取验证码
			flag=true;
		}
		//封装
		map.put("success", flag);
		map.put("msg", msg);
		return gson.toJson(map); 
	}
	
	
	//-----------------测试区-----------------
	/**
	 * 首页：验证码-首页 OK 测试无误
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/login_Kaptcha")
	public ModelAndView login_Kaptcha() throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("title", "loginPage");
		mav.setViewName("/manager/login_Kaptcha");
		return mav;
	}

	/**
	 * 登录：验证码-首页 OK 测试无误
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/kaptchalogin", method = RequestMethod.POST)
	public ModelAndView imgvrifyControllerDefaultKaptcha(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		String rightCode = (String) request.getSession().getAttribute("rightCode");
		String tryCode = request.getParameter("tryCode");
		System.out.println("rightCode:" + rightCode + " ———— tryCode:" + tryCode);
		if (!rightCode.equals(tryCode)) {
			mav.addObject("info", "验证码错误,请再输一次!");
			mav.setViewName("login");
		} else {
			mav.addObject("info", "登陆成功");
			mav.setViewName("/manager/welcome");
		}
		return mav;
	}

}
