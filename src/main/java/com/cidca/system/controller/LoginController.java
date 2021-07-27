package com.cidca.system.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cidca.common.Constants;
import com.cidca.entity.TAuditLog;
import com.cidca.entity.TMuser;
import com.cidca.entity.TUserRole;
import com.cidca.system.service.MuserService;
import com.cidca.system.service.SystemService;
import com.cidca.system.service.UserRoleService;
import com.cidca.util.BtoAUtil;
import com.cidca.util.DateTools;
import com.cidca.util.FileUtils;
import com.cidca.util.MD5;
import com.cidca.util.RegexUtilxs;
import com.cidca.util.StringUtil;
import com.cidca.util.UserCounter;

/**
 * 不需要认证的全部放到这个Controller @RequestMapping("/public")
 * @author mingtian
 *
 */
@SuppressWarnings("unused")
@Controller
@RequestMapping("/public")
public class LoginController {

	Logger logger =LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private MuserService userService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private UserRoleService userRoleService;

	/**
	 * 登录页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/login")
	public String login() throws Exception {
		return "/sys/login";//自动把String解析为视图
	}

	/**
	 * 登录-普通用户名形式
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked"})
	@RequestMapping(value = "/idcardLogin")	
	public @ResponseBody Map<String, Object> idcardLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String rightCode = (String) request.getSession().getAttribute("rightCode");//服务器生成的验证码
		Date date = new Date();
		Map<String, String> jspMap =new HashMap<String, String>();
		request.getSession().removeAttribute(Constants.SESSION_KEY);

		String uJson = (String) request.getParameter("uJson");
		uJson = BtoAUtil.atob(uJson);//var uJson="{'one':"+userid+",'two':"+password+"}";
		JSONObject object = JSON.parseObject(uJson);
		String userid=object.getString("one");
		String password=object.getString("two");
		String identity=object.getString("identity");
		String tryCode=object.getString("tryCode");//用户输入的验证码
		logger.info("rightCode:" + rightCode + " ———— tryCode:" + tryCode);

		String ip=UserCounter.getClientIpAddress(request);

		//错误3次出3次以上锁定10分钟
		boolean checkLoginNum4 = checkLoginNum4ByUser(userid);
		if (checkLoginNum4) {
			Date lockTime = DateTools.getLockTime();//当前时间+5分钟
			TMuser muser = userService.findByIdcard(userid);
			if (null!=muser) {
				muser.setTimeLock(lockTime);//时间锁
				userService.updateTMuser(muser);
				systemService.save(new TAuditLog(userid, ip, lockTime, "账号锁定"));
			}
			Constants.errorMap.remove(userid);//清除，防止时间锁到了后不能登录
			return StringUtil.returnMapToView("200",  "请勿频繁登录，请与"+DateTools.getDateMilliFormat(lockTime)+"再试");
		}

		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(userid, password);// 在认证提交前准备 token（令牌）
		try {
			subject.login(token);
		} catch (UnknownAccountException e) {
			return StringUtil.returnMapToView( "200","未找到该用户，请您先注册");
		} catch (AuthenticationException e) {
			return StringUtil.returnMapToView( "200", "用户名或密码不正确！");
		} catch (AuthorizationException e) {
			return StringUtil.returnMapToView( "200","没有权限");
		}	
		if (subject.isAuthenticated()) {
			//return "登录成功";
			TMuser muser = userService.findByIdcard(userid);
			TMuser user = new TMuser();
			user.setPersontype(muser.getPersontype());
			user.setIdcard(muser.getIdcard());
			user.setFullname(muser.getFullname());
			user.setPersontype(muser.getPersontype());
			//设置首次登录标志
			Integer loginnum=muser.getLoginnum();
			if(null==loginnum){
				muser.setLoginnum(Integer.parseInt(Constants.ONE));
			}else{
				muser.setLoginnum(loginnum+1);
			}
			muser.setUpdatetime(new Date());
			if (null!=muser.getTimeLock()) {muser.setTimeLock(null);}//登录成功后移除锁
			userService.updateTMuser(muser);
			request.getSession().setAttribute(Constants.SESSION_KEY, user);
			Constants.errorMap.put(userid,0);//重置校验数值，防止登录失败
			return StringUtil.returnMapToView( "100", "登录成功");
		} else {
			token.clear();
			return StringUtil.returnMapToView( "200", "登录失败");
		}
	}

	/**
	 * 登录-手机+验证码形式
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/mobileLogin")	
	public Map<String, Object> mobileLoginning(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String rightCode = (String) request.getSession().getAttribute("rightCode");//服务器生成的验证码
		String mvc = (String)request.getSession().getAttribute("mvc");//手机+验证码串+发送时间
		if (StringUtils.isNotEmpty(mvc)) {
			String[] split = mvc.split("\\+");
			String mobile = split[0];
			String vali_code = split[1];
			String sendTime = split[2];
			int dateDiff = DateTools.dateDiff(sendTime);
			if (5>=dateDiff) {//验证码有效期内
				TMuser user=null;
				//验证通过，开始登陆
				List<TMuser> muserList = userService.findByMobile(mobile);
				if (null!=muserList && muserList.size()>0) {
					if (!rightCode.equals(vali_code)) {
						return StringUtil.returnMapToView( "200","您输入的图形验证码错误，请重新输入");
					}
					TMuser user1 = muserList.get(0);
					if(user1 != null){
						user=new TMuser();
						user.setPersontype(user1.getPersontype());
						user.setIdcard(user1.getIdcard());
						user.setFullname(user1.getFullname());
						if (null!=user1.getTimeLock()) {
							Date userLockDate = user1.getTimeLock();
							if (userLockDate.after( DateTools.getCurrentTime())) {
								long calTime = DateTools.CalTime(userLockDate,  DateTools.getCurrentTime());
								return StringUtil.returnMapToView( "200","请勿频繁登录，请与"+calTime+"再试");
							}else {
								user1.setTimeLock(null);
							}
						}//登录成功后移除锁
						Integer loginnum=user1.getLoginnum();//设置首次登录标志
						if(null==loginnum){
							user1.setLoginnum(Integer.parseInt(Constants.ONE));
						}else{
							user1.setLoginnum(loginnum+1);
						}
						user1.setUpdatetime(new Date());
						userService.saveTMuser(user1);
						//记录日志
						systemService.save(new TAuditLog(mobile,null,  DateTools.getCurrentTime(), mobile+"通过手机登录"));
						request.getSession().setAttribute(Constants.SESSION_KEY,user1);
						request.getSession().removeAttribute("mvc");
					} 
				}else{
					return StringUtil.returnMapToView("200","该号码不存在，请先注册!");
				}
			}else{
				return StringUtil.returnMapToView("200", "验证码校验失败或已过期，请重新获取");
			}
		}
		return null;
	}

	/**
	 * 登出
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Subject subject = SecurityUtils.getSubject();
		//注销
		subject.logout();
		request.getSession().setAttribute(Constants.SESSION_KEY, null);
		request.getSession().invalidate();
		String clientIpAddress = UserCounter.getClientIpAddress(request);//移除时间校验缓存20201218
		if (StringUtils.isNotEmpty(clientIpAddress)) {
			Constants.checkCountMap.remove(clientIpAddress);
		}
		return "/sys/login";
	}

	/**
	 * 注册-保存-跳转登录页
	 */
	@RequestMapping(value = "/inputRegister")
	public String inputRegister(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String clientIpAddress = UserCounter.getClientIpAddress(request);//获取客户端IP
		Date date = new Date();
		boolean frequency = Constants.getFrequency(clientIpAddress,date);//防止频繁刷新请
		if (!frequency) {
			return "/sys/login";
		}
		return "/sys/inputRegister";
	}

	/**
	 * 注册-提交保存
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 坑2 @RequestBody 自动装配的语法要求：不允许在局部进行自动装配（即:只能写在method外class里）。
	 * 
	 */
	@RequestMapping(value = "/saveMuser")
	public @ResponseBody Map<String, Object> saveMuser(@RequestBody TMuser muser,HttpServletRequest request,HttpServletResponse response) throws Exception {
		String idcard = muser.getIdcard();
		String password = muser.getPassword();
		String mobile = "13901234567";
		String email ="4233970@qq.com";
		String telephone ="010-12345678";
		String mobileCode =muser.getMobileCode();
		boolean result_mobile = RegexUtilxs.isMobileExact(mobile);
		boolean result_email = RegexUtilxs.isEmail(email);
		//		if (!Pattern.matches(RegexUtilxs.PASSWORD_FSF, password) && password.length()<8) {
		//			return StringUtil.returnMapToView("200", "密码不符合规范，应当包含大小写字母、数字、特殊符号且不小于8位");
		//		}
		//		if (Pattern.matches(RegexUtilxs.LETTERS_AND_NUMBERS,idcard)) {
		//			return StringUtil.returnMapToView("200", "用户名不符合规范，应当包含字母、数字，长度5~10位");
		//		}
		if (!result_mobile) {return StringUtil.returnMapToView("200", "手机号码格式不符合规范");}
		if (!result_email) {return StringUtil.returnMapToView("200", "邮箱格式不符合规范");}
		String clientIpAddress = UserCounter.getClientIpAddress(request);

		String mvc = (String)request.getSession().getAttribute("mvc");//手机+验证码串+发送时间
		if (StringUtils.isNotEmpty(mvc)) {
			String[] split = mvc.split("\\+");
			String mobile2 = split[0];
			String vali_code = split[1];
			String sendTime = split[2];
			int dateDiff = DateTools.dateDiff(sendTime);
			if (10>=dateDiff) {//验证码有效期内
				TMuser muser2 = userService.findByIdcard(idcard);
				if (null==muser2) {
					Date date = new Date();
					TMuser vo =new TMuser(StringUtil.getUUIDRandomHexToUpperCase(),Integer.toString(Constants.EXTERNAL), idcard, new MD5().getMD5ofStr(password), "测试账号", "CS", 
							mobile, email, Integer.toString(3), "组织名称", "Q1W2E3R4", Integer.toString(370), 
							"北京市房山区拱辰街道办事处拱辰北大街45号", "单位负责人", telephone,  null, null, date, date, 0, Constants.ZERO,null);
					userService.saveTMuser(vo);
					//保存角色
					TUserRole role = new TUserRole();
					role.setUserid(idcard);
					role.setRoleid(Constants.EXTERNAL);//外部人员
					userRoleService.save(role);
					if (StringUtils.isNotEmpty(vo.getEmail())) {
						try {
							// 发邮件
							StringBuffer sb = new StringBuffer();
							sb.append(vo.getFullname()+"先生/女士：<br><br>");
							sb.append("&nbsp;&nbsp;&nbsp;&nbsp;您已成功注册对外援助统计数据直报平台，您的登录账号为注册证件号码。系统登录地址为：http://www.cidca.gov.cn/cidca<br>");
							sb.append("<br><br><br>");
							sb.append("国家国际发展合作署<br>");
							sb.append("地址：北京东城区东安门大街82号<br>");
							sb.append("邮编：100006<br>");
							sb.append("<br>");
							sb.append("本邮件为系统自动发送，请勿回复。");
							//MailService.SendMail(vo.getEmail(), "对外援助统计数据直报平台注册成功",sb.toString()); // TODO
							logger.info("注册-邮件已发送");
							if (StringUtils.isNotEmpty(vo.getMobile())){//注册时只要求填写常用手机号码，所以不用考虑备用手机
								//SendSms.sendMsg(mobile, po.getFullname()+" 老师，您已注册成功。请登录对外援助统计数据直报平台。"); // TODO
								logger.info("注册-短信已发送");
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					systemService.save(new TAuditLog(idcard,clientIpAddress,new Date(),"新增用户"+idcard));
				}else{
					return StringUtil.returnMapToView("200", "该用户名已被注册");
				}

			}
			Map<String, Object> returnMap=new HashMap<String, Object>();
			returnMap.put("resultcode","100");
			returnMap.put("value", "注册成功");
			returnMap.put("idcard",muser.getIdcard());
			return returnMap;
		}else{
			return StringUtil.returnMapToView("200", "未验证手机，请获取手机验证码");
		}
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

	/**
	 * 登录-获取验证码
	 * 用HttpServletRequest 作用域范围最小 不再使用HttpSession
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkPhoneAndSendCode")
	public @ResponseBody Map<String, Object> checkPhoneAndSendCode(HttpServletRequest request) throws Exception {
		String mobileAndCode = (String)request.getSession().getAttribute("mvc");//检测上次发送的手机
		if(StringUtils.isNotEmpty(mobileAndCode)){
			String[] str=mobileAndCode.split("\\+");
			String mobile =str[0];
			String sendTime =str[2];
			long pareLong = DateTools.pareLong(sendTime);
			if((System.currentTimeMillis()-pareLong)<120000){
				return StringUtil.returnMapToView("200", "120秒内不能重复获取!");//120秒内不能重复获取
			}
		}
		String mobile=request.getParameter("uJson");
		String uJson = (String) request.getParameter("uJson");
		uJson = BtoAUtil.atob(uJson);
		JSONObject object = JSON.parseObject(uJson);
		mobile=object.getString("one");
		List<TMuser> muserList = userService.findByMobile(mobile);
		if (null==muserList || muserList.size()==0) {
			return StringUtil.returnMapToView("200", "该号码不存在，请先注册!");
		}
		if (muserList.size()==1) {
			String vali_code=String.valueOf(new Random().nextInt(899999) + 100000);
			String content = "您的验证码为:" + vali_code+"，有效期为5分钟，该码只能使用一次，过期后需要重新获取。Cidca工作人员不会向您索要此验证码";
			//记录当前时间
			String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			request.getSession().setAttribute("mvc", mobile+"+"+vali_code+"+"+format);//登陆时拆分用
			try {
				//SendSms.sendMsg(mobile, content);
				logger.info("发送成功");
				return StringUtil.returnMapToView("100",content);
			} catch (Exception e) {
				logger.info("error");
				e.printStackTrace();
			}
		}else{
			return StringUtil.returnMapToView("200","该号码存在异常，请联系管理员。");
		}
		return StringUtil.returnMapToView("200","该号码不存在，请确认!");
	}

	/**
	 * 修改密码页面
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/changePw")
	public String changePw(HttpServletRequest request) throws Exception {
		return "/sys/changePw";
	}
	
	/**
	 * 注册-验证手机号码并发送验证码
	 */
	@RequestMapping(value = "/registerMobile")
	public @ResponseBody Map<String, Object> registerMobile(HttpServletRequest request) throws Exception {
		String mobileAndCode = (String)request.getSession().getAttribute("mvc");//检测上次发送的手机
		if(StringUtils.isNotEmpty(mobileAndCode)){
			String[] str=mobileAndCode.split("\\+");
			String mobile =str[0];
			String sendTime =str[2];
			long pareLong = DateTools.pareLong(sendTime);
			if((System.currentTimeMillis()-pareLong)<120000){
				return StringUtil.returnMapToView("200", "120秒内不能重复获取!");//120秒内不能重复获取
			}
		}
		String mobile=request.getParameter("uJson");
		String uJson = (String) request.getParameter("uJson");
		uJson = BtoAUtil.atob(uJson);
		JSONObject object = JSON.parseObject(uJson);
		mobile=object.getString("one");
		List<TMuser> muserList = userService.findByMobile(mobile);
		if (null!=muserList && muserList.size()>0) {
			return StringUtil.returnMapToView("200", "该号码已存在，请更换手机号码!");
		}
		String vali_code=String.valueOf(new Random().nextInt(899999) + 100000);
		String content = "您的验证码为:" + vali_code+"，有效期为10分钟，该码只能使用一次，过期后需要重新获取。Cidca工作人员不会向您索要此验证码";
		//记录当前时间
		String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		request.getSession().setAttribute("mvc", mobile+"+"+vali_code+"+"+format);//登陆时拆分用
		try {
			//SendSms.sendMsg(mobile, content);
			logger.info("发送成功");
			return StringUtil.returnMapToView("100",content);
		} catch (Exception e) {
			logger.info("error");
			e.printStackTrace();
		}
		return StringUtil.returnMapToView("200","该号码不存在，请确认!");
	}

	/**
	 * 通过手机找回密码-获取验证码
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/resetPasswordSendCode")
	public @ResponseBody Map<String, Object> resetPasswordSendCode(HttpServletRequest request) throws Exception {
		String mobileAndCode = (String)request.getSession().getAttribute("mvcResetPassword");//上次发送到手机的信息检测
		String uJson = (String) request.getParameter("uJson");
		uJson = BtoAUtil.atob(uJson);
		JSONObject object = JSON.parseObject(uJson);
		String inputMobile=object.getString("one");

		if(StringUtils.isNotEmpty(mobileAndCode)){
			String[] str=mobileAndCode.split("\\+");
			String mobile =str[0];
			String sendTime =str[2];
			long pareLong = DateTools.pareLong(sendTime);
			if(inputMobile.equals(mobile) && (System.currentTimeMillis()-pareLong)<120000){
				return StringUtil.returnMapToView("200", "120秒内不能重复获取!");//120秒内不能重复获取
			}
		}
		List<TMuser> muserList = userService.findByMobile(inputMobile);
		if (null!=muserList && muserList.size()>0) {
			String vali_code=String.valueOf(new Random().nextInt(899999) + 100000);
			String content = "您的验证码为:" + vali_code+"，有效期为5分钟，该码只能使用一次，过期后需要重新获取。Cidca工作人员不会向您索要此验证码";
			//记录当前时间
			String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			request.getSession().setAttribute("mvcResetPassword", inputMobile+"+"+vali_code+"+"+format);//登陆时拆分用
			try {
				//SendSms.sendMsg(mobile, content);
				logger.info("发送成功");
				return StringUtil.returnMapToView("100",content);
			} catch (Exception e) {
				logger.info("error");
				e.printStackTrace();
			}
		}else{
			return StringUtil.returnMapToView("200","该号码不存在，请先注册!");
		}
		return null;
	}

	/**
	 * 通过手机找回密码-检查验证码并修改密码
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/resetPasswordViaMobile")	
	public Map<String, Object> resetPasswordViaMobile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//String mvc = (String)request.getSession().getAttribute("mvcResetPassword");//手机+验证码串+发送时间
		String mvc = "13012345678+111+6";

		String uJson = (String) request.getParameter("uJson");
		uJson = BtoAUtil.atob(uJson);//var uJson="{'one':"+userid+",'two':"+password+"}";
		JSONObject object = JSON.parseObject(uJson);
		String mobile=object.getString("one");
		String mobileCode=object.getString("two");

		if (StringUtils.isNotEmpty(mvc)) {
			String[] split = mvc.split("\\+");
			String mvc_mobile = split[0];
			String mvc_code = split[1];
			String sendTime = split[2];
			//int dateDiff = DateTools.dateDiff(sendTime);
			int dateDiff = 2;
			if (5<dateDiff) {
				return StringUtil.returnMapToView("200", "验证码校验失败或已过期，请重新获取");
			}
			if (!mobileCode.equals(mvc_code)) {
				request.getSession().removeAttribute("mvcResetPassword");
				return StringUtil.returnMapToView("200", "您输入的验证码不正确，请重新获取");
			}
			//验证码有效期内
			TMuser user=null;
			//验证通过，开始登陆
			List<TMuser> muserList = userService.findByMobile(mobile);
			if (null!=muserList && muserList.size()==1) {
				TMuser muser = muserList.get(0);
				String idcard = muser.getIdcard();
				idcard=idcard.substring(idcard.length()-3, idcard.length());
				muser.setPassword(new MD5().getMD5ofStr("Cidca#"+idcard));
				muser.setUpdatetime(new Date());
				userService.saveTMuser(muser);
				try {
					String content="您通过Cidca平台重置密码已处理完毕，密码已重置为Cidca#"+idcard;
					//SendSms.sendMsg(mobile, content);
					logger.info("发送成功");
					return StringUtil.returnMapToView("100",content);
				} catch (Exception e) {
					logger.info("error");
					e.printStackTrace();
				}
				//记录日志
				systemService.save(new TAuditLog(mobile,null,  DateTools.getCurrentTime(), mobile+"通过手机修改密码"));
				request.getSession().removeAttribute("mvcResetPassword");
			}else{
				return StringUtil.returnMapToView("200","号码不存在或重复，请联系管理员!");
			}
		}
		return null;
	}


	@RequestMapping(value = "/registerUploadFile")
	public String registerUploadFile(String idcard, Model model, HttpServletRequest request) throws Exception {
		if (StringUtils.isNotEmpty(idcard)) {
			model.addAttribute("idcard",idcard);
			return "/sys/registerUploadFile";
		}else{
			return "/sys/login";
		}
	}

	/**
	 * 证明文件上传-支持多文件
	 * @param files
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/multifileUpload")
	public @ResponseBody Map<String, Object> multifileUpload(@RequestParam(value = "fileName",required = false)MultipartFile[] files, HttpServletRequest request) throws Exception {
		String idcard=request.getParameter("idcard");
		if (StringUtils.isNotEmpty(idcard)) {
			TMuser muser = userService.findByIdcard(idcard);
			if (null==muser) {
				return StringUtil.returnMapToView("200","未找到当前用户，请登录!");
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		if(files.length<1){
			return StringUtil.returnMapToView("200","文件不存在");
		}
		String path = ClassUtils.getDefaultClassLoader().getResource("static").getPath()+"/upload/";

		for (int i = 0; i < files.length; i++) {
			MultipartFile file=files[i];
			String fileName = file.getOriginalFilename();
			if (StringUtils.isEmpty(fileName)) {
				continue;
			}
			//判断文件合法性
			int index = fileName.lastIndexOf(".");
			String suffix = null;
			if (index == -1 || (suffix = fileName.substring(index + 1)).isEmpty()) {
				return StringUtil.returnMapToView("200", "文件后缀不能为空");
			}

			boolean b1 = FileUtils.getLegitimacyOfFile(file.getInputStream());
			if (!b1) {
				return StringUtil.returnMapToView("200", "文件格式不符合规范");
			}

			int size = (int) file.getSize();
			logger.info("文件名称："+fileName + "，文件大小(long)-->" + size);

			if(file.isEmpty()){
				return StringUtil.returnMapToView("200","文件不存在");
			}else{
				File targetFile= new File(path + "/" + fileName);
				if(!targetFile.getParentFile().exists()){ //判断文件父目录是否存在
					targetFile.getParentFile().mkdir();
				}
				try {
					file.transferTo(targetFile);
				}catch (Exception e) {
					e.printStackTrace();
					return StringUtil.returnMapToView("200","upload file fail");
				} 
			}
			return StringUtil.returnMapToView("100","上传成功！请等待审核，审核通过后将有邮件和短信通知。");
		}
		return StringUtil.returnMapToView("200","上传失败，未检测到您上传文件");
	}


	@RequestMapping(value = "/error")
	public String error(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getSession().setAttribute(Constants.SESSION_KEY, null);
		request.getSession().invalidate();
		return "/sys/error";
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
		mav.setViewName("/sys/login_Kaptcha");
		return mav;
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

	@RequestMapping(value = "/loginTest")
	public @ResponseBody Map<String, Object> loginTest( HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String a = request.getParameter("a");
		logger.info(a);
		map.put("resultcode", "100");
		map.put("value", "提交成功");
		return map;
	}





}
