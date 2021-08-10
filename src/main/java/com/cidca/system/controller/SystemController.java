package com.cidca.system.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cidca.entity.*;
import com.cidca.queryvo.RoleMenuVo;
import com.cidca.system.service.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cidca.common.Constants;
import com.cidca.util.BtoAUtil;
import com.cidca.util.DateTools;
import com.cidca.util.FileUtils;
import com.cidca.util.MD5;
import com.cidca.util.MultifunUtils;
import com.cidca.util.StringUtil;
import com.cidca.util.UserCounter;

import sun.misc.BASE64Encoder;

/**
 * 需要认证的放到这里
 * @author mingtian
 *
 */
@SuppressWarnings({ "unused", "restriction" })
@Controller
@RequestMapping("/sys")
public class SystemController{

	Logger logger =LoggerFactory.getLogger(SystemController.class);

	@Autowired
	private SystemService systemService;
	@Autowired
	private MuserService muserService;
	@Autowired
	private BasedataService basedataService;
	@Autowired
	private BusiLogService busiLogService;
	@Autowired
	private EnterpriseChangeService ecService;
	@Autowired
	private AccountChangeService accService;
	@Autowired
	RoleService roleService;


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
				TMuser user = (TMuser)request.getSession().getAttribute(Constants.SESSION_KEY);
//				String principal = (String)SecurityUtils.getSubject().getPrincipal();//用shiro获取当前登录用户名
		//model.addAttribute("user","欢迎："+user.getFullname());//两种方法都可以，和spring model and view一样
//		List menuList = systemService.getMenuList(principal);
//		request.getSession().setAttribute("menuList",menuList);
//		model.addAttribute("menuList",menuList);
		model.addAttribute("user",user.getFullname());//两种方法都可以，和spring model and view一样
		map.put("title", "对外援助统计数据直报平台 ");
		map.put("user",user);
		return "/sys/welcome";// 自动把String解析为视图
	}

	/**
	 * 账户管理-页面
	 */
	//	@RequiresRoles("1")
	//	@RequiresPermissions("external")
	@RequestMapping("/accountMan")
	public String accountMan(HashMap<String, Object> map,Model model,HttpServletRequest request) throws Exception {
		//		TMuser user = (TMuser)request.getSession().getAttribute(Constants.SESSION_KEY);
		//		String principal = (String)SecurityUtils.getSubject().getPrincipal();//用shiro获取当前登录用户名
		//model.addAttribute("user","欢迎："+user.getFullname());//两种方法都可以，和spring model and view一样
		model.addAttribute("user","Hi 王晓华");//两种方法都可以，和spring model and view一样
		TMuser muser = muserService.findByIdcard("qqq");
		muser=handleTMuser(muser);
		model.addAttribute("obj",muser);
		map.put("title", "对外援助统计数据直报平台 ");
		model.addAttribute("obj",muser);
		return "/enterprise/accountMan";// 自动把String解析为视图
	}

	/**
	 * 账户管理-发起变更前检测，如果有审核中的不能再次发起
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	//	@RequiresRoles("1")
	//	@RequiresPermissions("external")
	@RequestMapping("/changeAccountBefore")
	public @ResponseBody Map<String, Object> changeAccountBefore(HashMap<String, Object> map,Model model,HttpServletRequest request) throws Exception {
		//		TMuser user = (TMuser)request.getSession().getAttribute(Constants.SESSION_KEY);
		//		String principal = (String)SecurityUtils.getSubject().getPrincipal();//用shiro获取当前登录用户名
		//model.addAttribute("user","欢迎："+user.getFullname());//两种方法都可以，和spring model and view一样
		String idcard=request.getParameter("idcard");
		TMuser muser = muserService.findByIdcard(idcard);
		List<TAccountChange> his_Wait = accService.findAccountChangeByIdcardAndState(muser.getIdcard(),Constants.AUDIT_WAIT);//
		if ( null!=his_Wait && his_Wait.size()>0) {
			return StringUtil.returnMapToView("200", "您有待审核的信息变更记录，请等待审核通过后再次发起申请~");
		}else{
			return StringUtil.returnMapToView("100", "可以继续操作~");
		}
	}
	/**
	 * 账户管理-信息变更-页面
	 */
	//	@RequiresRoles("1")
	//	@RequiresPermissions("external")
	@RequestMapping("/changeAccount")
	public String changeAccount(HashMap<String, Object> map,Model model,HttpServletRequest request) throws Exception {
		//		TMuser user = (TMuser)request.getSession().getAttribute(Constants.SESSION_KEY);
		//		String principal = (String)SecurityUtils.getSubject().getPrincipal();//用shiro获取当前登录用户名
		//model.addAttribute("user","欢迎："+user.getFullname());//两种方法都可以，和spring model and view一样
		model.addAttribute("user","Hi 王晓华");//两种方法都可以，和spring model and view一样
		TMuser muser = muserService.findByIdcard("qqq");
		muser=handleTMuser(muser);
		model.addAttribute("objM",muser);//当前已生效的信息
		List<TAccountChange> his_Temp = accService.findAccountChangeByIdcardAndState(muser.getIdcard(),Constants.AUDIT_TEMP);//上次变更暂存的，如果有
		if ( null!=his_Temp && his_Temp.size()>0) {
			TAccountChange hisObj=his_Temp.get(0);
			//BeanUtils.copyProperties(dest, orig);
			TMuser muser2 =new TMuser();
			muser2.setFullname(hisObj.getFullname());
			muser2.setIdcard(hisObj.getIdcard());
			muser2.setMobile(hisObj.getMobile());
			muser2.setEmail(hisObj.getEmail());
			if (StringUtils.isNotEmpty(hisObj.getCertificate())) {
				muser2.setCertificate(Constants.ONE);
			}else if (StringUtils.isNotEmpty(muser.getCertificate())){
				muser2.setCertificate(Constants.ONE);
			}else{
				muser2.setCertificate(Constants.ZERO);
			}
			model.addAttribute("objC",muser2);//当前未生效的信息
		}
		map.put("title", "对外援助统计数据直报平台 ");
		return "/enterprise/changeAccount";// 自动把String解析为视图
	}

	/**
	 * 企业信息管理-信息变更-提交保存
	 * @param request
	 * @return
	 * @throws Exception
	 */
	//@RequiresRoles("1")
	//@RequiresPermissions("external")
	@RequestMapping(value = "/saveAccountChange")
	public @ResponseBody Map<String, Object> saveAccountChange(
			@RequestParam(value="fullname",required=false)String fullname,
			@RequestParam(value="mobile",required=false)String mobile,
			@RequestParam(value="mobileCode",required=false)String mobileCode,
			@RequestParam(value="email",required=false)String email,
			@RequestParam(value="state",required=false)String state,
			@RequestParam(value="idcard",required=true)String idcard,
			@RequestParam(value="certificate",required=false) MultipartFile file, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		if (StringUtils.isEmpty(idcard)) {
			return StringUtil.returnMapToView("200", "获取当前用户失败！");
		}

		TMuser muser = muserService.findByIdcard(idcard);
		if (!muser.getMobile().equals(mobile) && !muser.getEmail().equals(email)) {
			if (null==file || file.isEmpty()) {
				return StringUtil.returnMapToView("200", "按照规定，手机邮箱同时变更的，需要重新上传证明文件，请下载模板后上传~");
			}

			if (StringUtils.isEmpty(mobileCode)) {
				return StringUtil.returnMapToView("200", "请输入短信验证码~");
			}

			String mvc = (String)request.getSession().getAttribute("mvc");//手机+验证码串+发送时间
			if (StringUtils.isNotEmpty(mvc)) {
				String[] split = mvc.split("\\+");
				String mobile2 = split[0];
				String vali_code = split[1];
				if (!vali_code.equals(mobileCode)) {
					return StringUtil.returnMapToView("200", "短信验证码与输入的不一致，请重新输入。过期后需要重新获取~");
				}
				String sendTime = split[2];
				int dateDiff = DateTools.dateDiff(sendTime);
				if (10<dateDiff) {
					return StringUtil.returnMapToView("200", "短信验证码已失效，请刷新图形验证码后重新获取~");
				}
			}
		}

		if (!fullname.equals(muser.getFullname())) {
			if (null==file || file.isEmpty()) {
				return StringUtil.returnMapToView("200", "按照规定，姓名（填报人）变更的，需要重新上传证明文件，请下载模板后上传~");
			}
		}

		TAccountChange newVo=null;
		List<TAccountChange> his = accService.findAccountChangeByIdcardAndState(idcard,Constants.AUDIT_TEMP);
		if ( null!=his && his.size()>0) {
			newVo=his.get(0);
		}else{
			newVo=new TAccountChange();
			newVo.setUuid(StringUtil.getUUIDRandomHexToUpperCase());
		}
		
		StringBuilder sbr =new StringBuilder();
		if (StringUtils.isNotEmpty(fullname)) {
			newVo.setFullname(StringUtils.trim(fullname));
			sbr.append(getChangeMessage("姓名", muser.getFullname(), fullname));
		}
		if (StringUtils.isNotEmpty(mobile)) {
			newVo.setMobile(StringUtils.trim(mobile));
			sbr.append(getChangeMessage("手机号码", muser.getMobile(), mobile));
		}
		if (StringUtils.isNotEmpty(email)) {
			newVo.setEmail(StringUtils.trim(email));
			sbr.append(getChangeMessage("邮箱", muser.getEmail(), email));
		}

		if (null!=file) {
			try {
				String encode = new BASE64Encoder().encode(file.getBytes());
				newVo.setCertificate(encode);
				sbr.append("变更了证明文件<br>");
			} catch (IOException e) {
				logger.error(e.getMessage());
				return StringUtil.returnMapToView("200", "上传失败~");
			}
		}

		newVo.setIdcard(idcard);
		newVo.setState(state);
		newVo.setContent(sbr.toString());
		accService.saveAccountChange(newVo);
		return StringUtil.returnMapToView("100", "保存成功");
	}

	/**
	 * 账户管理-信息变更历史
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	//	@RequiresRoles("1")
	//	@RequiresPermissions("external")
	@RequestMapping("/accountChangeHis")
	public String accountChangeHis(HashMap<String, Object> map,Model model,HttpServletRequest request) throws Exception {
		//		TMuser user = (TMuser)request.getSession().getAttribute(Constants.SESSION_KEY);
		//		String principal = (String)SecurityUtils.getSubject().getPrincipal();//用shiro获取当前登录用户名
		//model.addAttribute("user","欢迎："+user.getFullname());//两种方法都可以，和spring model and view一样
		model.addAttribute("user","Hi 王晓华");//两种方法都可以，和spring model and view一样
		TMuser muser = muserService.findByIdcard("qqq");
		List<TEnterpriseChange> his = ecService.findEnterpriseChangeByIdcard(muser.getIdcard());
		if (null!=his && his.size()>0) {
			model.addAttribute("accountHisList",his);
		}
		map.put("title", "对外援助统计数据直报平台 ");
		return "/enterprise/accountChangeHis";// 自动把String解析为视图
	}


	/**
	 * 企业信息管理
	 */
	//	@RequiresRoles("1")
	//	@RequiresPermissions("external")
	@RequestMapping("/enterpriseMan")
	public String enterpriseMan(HashMap<String, Object> map,Model model,HttpServletRequest request) throws Exception {
		//		TMuser user = (TMuser)request.getSession().getAttribute(Constants.SESSION_KEY);
		//		String principal = (String)SecurityUtils.getSubject().getPrincipal();//用shiro获取当前登录用户名
		//model.addAttribute("user","欢迎："+user.getFullname());//两种方法都可以，和spring model and view一样
		model.addAttribute("user","Hi 王晓华");//两种方法都可以，和spring model and view一样
		TMuser muser = muserService.findByIdcard("qqq");
		muser=handleTMuser(muser);
		model.addAttribute("obj",muser);
		map.put("title", "对外援助统计数据直报平台 ");
		return "/enterprise/enterpriseMan";
	}

	/**
	 * 企业信息管理-发起变更前检测，如果有审核中的不能再次发起
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	//	@RequiresRoles("1")
	//	@RequiresPermissions("external")
	@RequestMapping("/changeEnterpriseBefore")
	public @ResponseBody Map<String, Object> changeEnterpriseBefore(HashMap<String, Object> map,Model model,HttpServletRequest request) throws Exception {
		//		TMuser user = (TMuser)request.getSession().getAttribute(Constants.SESSION_KEY);
		//		String principal = (String)SecurityUtils.getSubject().getPrincipal();//用shiro获取当前登录用户名
		//model.addAttribute("user","欢迎："+user.getFullname());//两种方法都可以，和spring model and view一样
		String idcard=request.getParameter("idcard");
		TMuser muser = muserService.findByIdcard(idcard);
		List<TEnterpriseChange> his_Wait = ecService.findEnterpriseChangeByIdcardAndState(muser.getIdcard(),Constants.AUDIT_WAIT);//
		if ( null!=his_Wait && his_Wait.size()>0) {
			return StringUtil.returnMapToView("200", "您有待审核的信息变更记录，请等待审核通过后再次发起申请~");
		}else{
			return StringUtil.returnMapToView("100", "可以继续操作~");
		}
	}

	/**
	 * 企业信息管理-信息变更-页面
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	//	@RequiresRoles("1")
	//	@RequiresPermissions("external")
	@RequestMapping("/changeEnterprise")
	public String changeEnterprise(HashMap<String, Object> map,Model model,HttpServletRequest request) throws Exception {
		//		TMuser user = (TMuser)request.getSession().getAttribute(Constants.SESSION_KEY);
		//		String principal = (String)SecurityUtils.getSubject().getPrincipal();//用shiro获取当前登录用户名
		//model.addAttribute("user","欢迎："+user.getFullname());//两种方法都可以，和spring model and view一样
		model.addAttribute("user","Hi 王晓华");//两种方法都可以，和spring model and view一样
		TMuser muser = muserService.findByIdcard("qqq");
		muser=handleTMuser(muser);
		model.addAttribute("objM",muser);//当前已生效的信息
		List<TEnterpriseChange> his = ecService.findEnterpriseChangeByIdcardAndState(muser.getIdcard(),Constants.AUDIT_TEMP);
		if ( null!=his && his.size()>0) {
			TEnterpriseChange hisObj=his.get(0);
			//BeanUtils.copyProperties(dest, orig);
			TMuser muser2 =new TMuser();
			muser2.setFullname(hisObj.getFullname());
			muser2.setIdcard(hisObj.getIdcard());
			muser2.setMobile(hisObj.getMobile());
			muser2.setEmail(hisObj.getEmail());
			muser2.setOrganizationtype(hisObj.getOrganizationtype());
			muser2.setOrganizationname(hisObj.getOrganizationname());
			muser2.setAffiliated(hisObj.getAffiliated());
			muser2.setIsCommerce(hisObj.getIsCommerce());
			muser2.setOrganizationcode(hisObj.getOrganizationcode());
			muser2.setArea(hisObj.getArea());
			muser2.setAddress(hisObj.getAddress());
			muser2.setManager(hisObj.getManager());
			muser2.setTelephone(hisObj.getTelephone());
			if (StringUtils.isNotEmpty(hisObj.getCertificate())) {
				muser2.setCertificate(Constants.ONE);
			}else if (StringUtils.isNotEmpty(muser.getCertificate())){
				muser2.setCertificate(Constants.ONE);
			}else{
				muser2.setCertificate(Constants.ZERO);
			}
			model.addAttribute("objC",muser2);//当前未生效的信息
		}
		map.put("title", "对外援助统计数据直报平台 ");
		model.addAttribute("areaList", basedataService.findByTypes("area"));
		return "/enterprise/changeEnterprise";// 自动把String解析为视图
	}

	/**
	 * 企业信息管理-信息变更-提交保存
	 * @param request
	 * @return
	 * @throws Exception
	 */
	//@RequiresRoles("1")
	//@RequiresPermissions("external")
	@RequestMapping(value = "/saveEnterpriseChange")
	public @ResponseBody Map<String, Object> saveEnterpriseChange(
			@RequestParam(value="fullname",required=false)String fullname,
			@RequestParam(value="mobile",required=false)String mobile,
			@RequestParam(value="mobileCode",required=false)String mobileCode,//用户输入的
			@RequestParam(value="email",required=false)String email,
			@RequestParam(value="organizationtype",required=false)String organizationtype,
			@RequestParam(value="organizationname",required=false)String organizationname,
			@RequestParam(value="affiliated",required=false)String affiliated,
			@RequestParam(value="isCommerce",required=false)String isCommerce,
			@RequestParam(value="organizationcode",required=false)String organizationcode,
			@RequestParam(value="area",required=false)String area,
			@RequestParam(value="address",required=false)String address,
			@RequestParam(value="manager",required=false)String manager,
			@RequestParam(value="telephone",required=false)String telephone,
			@RequestParam(value="state",required=false)String state,
			@RequestParam(value="idcard",required=true)String idcard,
			@RequestParam(value="certificate",required=false) MultipartFile file, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		if (StringUtils.isEmpty(idcard)) {
			return StringUtil.returnMapToView("200", "获取当前用户失败！");
		}

		TMuser muser = muserService.findByIdcard(idcard);
		if (!muser.getMobile().equals(mobile) && !muser.getEmail().equals(email)) {
			if (null==file || file.isEmpty()) {
				return StringUtil.returnMapToView("200", "按照规定，手机邮箱同时变更的，需要重新上传证明文件，请下载模板后上传~");
			}

			if (StringUtils.isEmpty(mobileCode)) {
				return StringUtil.returnMapToView("200", "手机号码已变更，请输入短信验证码校验~");
			}

			String mvc = (String)request.getSession().getAttribute("mvc");//手机+验证码串+发送时间
			if (StringUtils.isNotEmpty(mvc)) {
				String[] split = mvc.split("\\+");
				String mobile2 = split[0];
				String vali_code = split[1];
				if (!vali_code.equals(mobileCode)) {
					return StringUtil.returnMapToView("200", "短信验证码与输入的不一致，请重新输入。过期后需要重新获取~");
				}
				String sendTime = split[2];
				int dateDiff = DateTools.dateDiff(sendTime);
				if (10<dateDiff) {
					return StringUtil.returnMapToView("200", "短信验证码已失效，请刷新图形验证码后重新获取~");
				}
			}
		}

		if (!fullname.equals(muser.getFullname())) {
			if (null==file || file.isEmpty()) {
				return StringUtil.returnMapToView("200", "按照规定，姓名（填报人）变更的，需要重新上传证明文件，请下载模板后上传~");
			}
		}

		TEnterpriseChange newVo=null;
		List<TEnterpriseChange> his = ecService.findEnterpriseChangeByIdcardAndState(idcard,Constants.AUDIT_TEMP);
		if ( null!=his && his.size()>0) {
			newVo=his.get(0);
		}else{
			newVo=new TEnterpriseChange();
			newVo.setIdcard(idcard);
			newVo.setUuid(StringUtil.getUUIDRandomHexToUpperCase());
		}
		StringBuilder sbr =new StringBuilder();
		if (StringUtils.isNotEmpty(fullname)) {
			newVo.setFullname(StringUtils.trim(fullname));
			sbr.append(getChangeMessage("姓名", muser.getFullname(), fullname));
		}
		if (StringUtils.isNotEmpty(mobile)) {
			newVo.setMobile(StringUtils.trim(mobile));
			sbr.append(getChangeMessage("手机号码", muser.getMobile(), mobile));
		}
		if (StringUtils.isNotEmpty(email)) {
			newVo.setEmail(StringUtils.trim(email));
			sbr.append(getChangeMessage("邮箱", muser.getEmail(), email));
		}
		if (StringUtils.isNotEmpty(organizationtype)) {
			newVo.setOrganizationtype(StringUtils.trim(organizationtype));
			sbr.append(getChangeMessage("企业类型/社会组织类型", muser.getOrganizationtype(), organizationtype));
		}
		if (StringUtils.isNotEmpty(organizationname)) {
			newVo.setOrganizationname(StringUtils.trim(organizationname));
			sbr.append(getChangeMessage("企业全称/组织全称", muser.getOrganizationtype(), organizationtype));
		}
		if (StringUtils.isNotEmpty(affiliated)) {
			newVo.setAffiliated(StringUtils.trim(affiliated));
			sbr.append(getChangeMessage("所属上级单位", muser.getAffiliated(), affiliated));
		}
		if (StringUtils.isNotEmpty(isCommerce)) {
			newVo.setIsCommerce(StringUtils.trim(isCommerce));
			sbr.append(getChangeMessage("是否是两家商会会员单位", muser.getIsCommerce().equals("1")?"是":"否", isCommerce.equals("1")?"是":"否"));
		}
		if (StringUtils.isNotEmpty(organizationcode)) {
			newVo.setOrganizationcode(StringUtils.trim(organizationcode));
			sbr.append(getChangeMessage("统一社会信用代码", muser.getOrganizationcode(), organizationcode));
		}
		if (StringUtils.isNotEmpty(area)) {
			newVo.setArea(StringUtils.trim(area));
			 String oldName = basedataService.findByCode(Integer.parseInt(muser.getArea())).getName();
			 String newName = basedataService.findByCode(Integer.parseInt(area)).getName();
			sbr.append(getChangeMessage("所属省份",oldName, newName));
		}
		if (StringUtils.isNotEmpty(address)) {
			newVo.setAddress(StringUtils.trim(address));
			sbr.append(getChangeMessage("地址", muser.getAddress(), address));
		}
		if (StringUtils.isNotEmpty(manager)) {
			newVo.setManager(StringUtils.trim(manager));
			sbr.append(getChangeMessage("单位负责人", muser.getManager(), manager));
		}
		if (StringUtils.isNotEmpty(telephone)) {
			newVo.setTelephone(StringUtils.trim(telephone));
			sbr.append(getChangeMessage("单位联系电话", muser.getTelephone(), telephone));
		}

		if (null!=file) {
			try {
				String encode = new BASE64Encoder().encode(file.getBytes());
				newVo.setCertificate(encode);
				sbr.append("变更了证明文件<br>");
			} catch (IOException e) {
				logger.error(e.getMessage());
				return StringUtil.returnMapToView("200", "上传失败~");
			}
		}
		newVo.setState(state);
		newVo.setContent(sbr.toString());
		ecService.saveEnterpriseChange(newVo);
		return StringUtil.returnMapToView("100", "保存成功");
	}

	/**
	 * 企业信息管理-变更历史
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	//	@RequiresRoles("1")
	//	@RequiresPermissions("external")
	@RequestMapping("/enterpriseChangeHis")
	public String enterpriseChangeHis(HashMap<String, Object> map,Model model,HttpServletRequest request) throws Exception {
		//		TMuser user = (TMuser)request.getSession().getAttribute(Constants.SESSION_KEY);
		//		String principal = (String)SecurityUtils.getSubject().getPrincipal();//用shiro获取当前登录用户名
		//model.addAttribute("user","欢迎："+user.getFullname());//两种方法都可以，和spring model and view一样
		TMuser muser = muserService.findByIdcard("qqq");
		List<TEnterpriseChange> his = ecService.findEnterpriseChangeByIdcard(muser.getIdcard());
		if (null!=his && his.size()>0) {
			model.addAttribute("enterpriseHisList",his);
		}
		model.addAttribute("user","Hi 王晓华");//两种方法都可以，和spring model and view一样
		map.put("title", "对外援助统计数据直报平台 ");
		return "/enterprise/enterpriseChangeHis";// 自动把String解析为视图
	}

	/**
	 * 用户管理-修改密码-页面
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	//	@RequiresRoles("1")
	//	@RequiresPermissions("external")
	@RequestMapping("/changePw")
	public String changePw(HashMap<String, Object> map,Model model,HttpServletRequest request) throws Exception {
		//		TMuser user = (TMuser)request.getSession().getAttribute(Constants.SESSION_KEY);
		//		String principal = (String)SecurityUtils.getSubject().getPrincipal();//用shiro获取当前登录用户名
		//model.addAttribute("user","欢迎："+user.getFullname());//两种方法都可以，和spring model and view一样
		model.addAttribute("user","Hi 王晓华");//两种方法都可以，和spring model and view一样
		model.addAttribute("idcard","qqq");
		map.put("title", "对外援助统计数据直报平台 ");
		return "/enterprise/changePw";
	}

	/**
	 * 账户管理-修改密码-保存
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveChangePw")
	public @ResponseBody Map<String, Object> saveChangePw(HttpServletRequest request) throws Exception {
		//		TMuser user = (TMuser)request.getSession().getAttribute(Constants.SESSION_KEY);
		//		String principal = (String)SecurityUtils.getSubject().getPrincipal();//用shiro获取当前登录用户名
		String uJson = (String) request.getParameter("uJson");
		if (StringUtils.isEmpty(uJson)) {
			return StringUtil.returnMapToView("200","获取人员信息失败，请联系管理员~");
		}
		uJson = BtoAUtil.atob(uJson);
		JSONObject object = JSON.parseObject(uJson);
		String par1=object.getString("par1");
		String par2=object.getString("par2");
		String par3=object.getString("par3");
		String par4=object.getString("par4");

		if (!par2.equals(par3)) {
			return StringUtil.returnMapToView("200","两次输入的密码不一致，请重新输入~");
		}

		TMuser muser = muserService.findByIdcard(par4);
		String newPw = new MD5().getMD5ofStr(par1);
		if (!newPw.equals(muser.getPassword())) {
			return StringUtil.returnMapToView("200","验证原密码错误，请重新输入~");
		}

		if (null!=muser) {
			muser.setPassword(new MD5().getMD5ofStr(par3));
			muserService.saveTMuser(muser);
			busiLogService.insert(new TBusiLog(StringUtil.getUUIDRandomHexToUpperCase(), "修改密码", "t_muser", muser.getUuid(), 
					muser.getFullname(), muser.getFullname()+"修改了密码", new Date(), null));
			return StringUtil.returnMapToView("100","修改成功!请重新登录");
		}else{
			return StringUtil.returnMapToView("200","该号码不存在，请先注册!");
		}
	}

	public TMuser handleTMuser(TMuser muser){
		if (null!=muser) {
			String shortMobile = muser.getMobile().replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
			muser.setMobile(shortMobile);
			muser.setPassword("***");
			String address ="";
			if (StringUtils.isNotEmpty(muser.getArea())) {
				TBaseData basedata = basedataService.findById(Integer.parseInt(muser.getArea()));
				if (null!=basedata) {
					address+= basedata.getName();
				}
			}
			muser.setAddress(address+muser.getAddress());
		}
		return muser;
	}

	/**
	 * 下载“我的证明文件”
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/downloadMyCertificate")
	public String downloadMyCertificate(String idcard,HttpServletRequest request, HttpServletResponse response ) throws Exception {
		if (StringUtils.isNotEmpty(idcard)) {
			TMuser muser = muserService.findByIdcard(idcard);
			if (null!=muser && StringUtils.isNotEmpty(muser.getCertificate())) {
				byte[] ba = muser.getCertificate().getBytes();
				ba = Base64.decodeBase64(ba);//将base64格式解码
				String fileName= "d:\\Template3.pdf";//TODO 上线前记得替换
				File filePath = new File(fileName);
				FileOutputStream fout = new FileOutputStream(filePath);
				fout.write(ba);    //将解码后的内容写入文件，生成文件
				fout.close();
				
				//待下载文件名
				//设置为png格式的文件
				response.setHeader("content-type", "application/pdf");
				response.setContentType("application/octet-stream");
				String downFilename="我的证明文件.pdf";
				response.setHeader("Content-Disposition", "attachment; filename=" + new String(downFilename.getBytes("gb2312"), "ISO8859-1" ));
				byte[] buff = new byte[1024];
				BufferedInputStream bis = null;//创建缓冲输入流
				OutputStream outputStream = null;
				try {
					outputStream = response.getOutputStream();
					//这个路径为待下载文件的路径
					bis = new BufferedInputStream(new FileInputStream(new File(fileName)));
					int read = bis.read(buff);

					//通过while循环写入到指定了的文件夹中
					while (read != -1) {
						outputStream.write(buff, 0, buff.length);
						outputStream.flush();
						read = bis.read(buff);
					}
				} catch ( IOException e ) {
					e.printStackTrace();
				} finally {
					if (bis != null) {
						try {
							bis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (outputStream != null) {
						try {
							outputStream.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				FileUtils.deleteFile(fileName);
				FileUtils.deleteFile(downFilename);
			}
		}
		return null;
	}

	/**
	 * 账户信息变更/企业信息变更-获取手机验证码
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
		List<TMuser> muserList = muserService.findByMobile(mobile);
		if (null!=muserList && muserList.size()>0) {
			return StringUtil.returnMapToView("200", "该号码已存在，请更换一个!");
		}
		String vali_code=String.valueOf(new Random().nextInt(899999) + 100000);
		String content = "您的验证码为:" + vali_code+"，有效期为10分钟，该码只能使用一次，过期后需要重新获取。Cidca工作人员不会向您索要此验证码";
		//记录当前时间
		String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		request.getSession().setAttribute("mvc", mobile+"+"+vali_code+"+"+format);//登陆时拆分用
		try {
			//SendSms.sendMsg(mobile, content);
			logger.info("发送成功"+content);
			return StringUtil.returnMapToView("100",content);
		} catch (Exception e) {
			logger.info("error");
			e.printStackTrace();
		}
		return StringUtil.returnMapToView("200","该号码不存在，请确认!");
	}
	
	public String getChangeMessage(String changeName,String oldValue,String newValue){
		return changeName+"由"+oldValue+"变更为"+newValue+"<br>";
	}

	/**
	 * 获取菜单列表
	 * @param uuid
	 * @param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
//	@RequiresRoles("1")
//	@RequiresPermissions("external")
	@RequestMapping(value = "/getMenuList")
	public @ResponseBody Map<String, Object> getMenuList(String uuid, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		TMuser user = (TMuser) request.getSession().getAttribute(Constants.SESSION_KEY);
//		String roleid = "1";
		String principal = (String)SecurityUtils.getSubject().getPrincipal();//用shiro获取当前登录用户名
		List menuList = systemService.getMenuList(principal);
		map.put("menuList",menuList);
		map.put("user",user);
		map.put("resultcode", "200");
		return map;
	}

	/**
	 * 后台登录后的主页
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	//	@RequiresRoles("1")
	//	@RequiresPermissions("external")
	@RequestMapping("/exwelcome")
	public String exwelcome(HashMap<String, Object> map,Model model,HttpServletRequest request) throws Exception {
		//		TMuser user = (TMuser)request.getSession().getAttribute(Constants.SESSION_KEY);
		//		String principal = (String)SecurityUtils.getSubject().getPrincipal();//用shiro获取当前登录用户名
		//model.addAttribute("user","欢迎："+user.getFullname());//两种方法都可以，和spring model and view一样
		model.addAttribute("user","Hi 王晓华");//两种方法都可以，和spring model and view一样
		map.put("title", "对外援助统计数据直报平台 ");
		return "/sys/exwelcome";// 自动把String解析为视图
	}

	/**
	 * 跳转用户列表
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	//	@RequiresRoles("1")
	//	@RequiresPermissions("external")
	@RequestMapping("/userlist")
	public String userlist(HashMap<String, Object> map,Model model,HttpServletRequest request) throws Exception {
		return "/user/userList";// 自动把String解析为视图
	}



	/**
	 * 获取用户列表
	 * @param user
	 * @param pageIndex
	 * @param pageSize
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("serial")
	//	@RequiresRoles("1")
	//	@RequiresPermissions("external")
	@RequestMapping(value = "/getUserList")
	public @ResponseBody Map<String, Object> getUserlist(
			TMuser user, Integer pageIndex, Integer pageSize,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// 查询条件
		Specification<TMuser> spec = new Specification<TMuser>() {
			Predicate ca = null;
			@Override
			public Predicate toPredicate(Root<TMuser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> pList = new ArrayList<Predicate>();
				if (StringUtils.isNotEmpty(user.getFullname())) {
					ca = cb.like(root.get("fullname").as(String.class), "%" +user.getFullname().trim()+ "%");
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(user.getIdcard())) {
					ca = cb.like(root.get("idcard").as(String.class), "%" +user.getIdcard().trim()+ "%");
					pList.add(ca);
				}
				Predicate[] pre = new Predicate[pList.size()];
				query.where(pList.toArray(pre));
				return query.getRestriction();
			}
		};
		int startIndex=pageIndex-1;
		PageRequest pageable = PageRequest.of(startIndex, pageSize);
		Page<TMuser> pageList = muserService.findAll(spec, pageable);
		map.put("resultcode", "200");
		map.put("total", pageList.getTotalElements());
		map.put("rows", pageList.getContent());
		return map;
	}

	/**
	 * 人员角色
	 * @param
	 * @param pageIndex
	 * @param pageSize
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("serial")
	//	@RequiresRoles("1")
	//	@RequiresPermissions("external")
	@RequestMapping(value = "/getUserRoleList")
	public @ResponseBody Map<String, Object> getRoleMenuList(
			TRole role,Integer pageIndex,Integer pageSize,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// 查询条件
		Specification<TRole> spec = new Specification<TRole>() {
			Predicate ca = null;
			@Override
			public Predicate toPredicate(Root<TRole> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> pList = new ArrayList<Predicate>();
//				if (StringUtils.isNotEmpty(muser.getFullname())) {
//					ca = cb.like(root.get("fullname").as(String.class), "%" +muser.getFullname().trim()+ "%");
//					pList.add(ca);
//				}
				Predicate[] pre = new Predicate[pList.size()];
				query.where(pList.toArray(pre));
				return query.getRestriction();
			}
		};
		int startIndex=pageIndex-1;
		PageRequest pageable = PageRequest.of(startIndex, pageSize);
		Page<TRole> pageList = roleService.findAll(spec, pageable);
		List roleList = pageList.getContent();
		String idcard = request.getParameter("idcard");
//        TMuser user = (TMuser) request.getSession().getAttribute(Constants.SESSION_KEY);
//		String principal = (String) SecurityUtils.getSubject().getPrincipal();//用shiro获取当前登录用户名
		List userRoleList = roleService.getUserRoleList(idcard);

		List<TRole> newMenuList = new ArrayList<TRole>();
		if(null != roleList && null != userRoleList){
			for(int i = 0; i < roleList.size();i++){
				TRole rmenu = (TRole) roleList.get(i);
				for(int j = 0; j < userRoleList.size(); j++){
					Map rMap = (Map)userRoleList.get(j);
					if(rmenu.getRoleid() == (Integer) rMap.get("roleid")){
						rmenu.setChecked("1");
					}
				}
				newMenuList.add(rmenu);
			}
		}

		map.put("resultcode", "200");
		map.put("total", pageList.getTotalElements());
		map.put("rows", newMenuList);
		return map;
	}

	/**
	 * 给人员配置角色
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 坑2 @RequestBody 自动装配的语法要求：不允许在局部进行自动装配（即:只能写在method外class里）。
	 *
	 */
	@RequestMapping(value = "/saveUserRole")
	public @ResponseBody
	Map<String, Object> saveUserRole(@RequestBody RoleMenuVo roleMenuVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userid =roleMenuVo.getUserid();
		String roleids = roleMenuVo.getRoleids();
		boolean flag = systemService.saveUserRole(userid,roleids);
		if(flag){
			return StringUtil.returnMapToView("200", "配置成功！");
		}else{
			return StringUtil.returnMapToView("500", "配置失败！");
		}
	}

	/**
	 * 新增或者修改角色
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 坑2 @RequestBody 自动装配的语法要求：不允许在局部进行自动装配（即:只能写在method外class里）。
	 *
	 */
	@RequestMapping(value = "/saveUser")
	public @ResponseBody
	Map<String, Object> saveUser(@RequestBody TMuser user, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try{
			TMuser qUser = systemService.findUserByIdcard(user.getIdcard());
			if(null != qUser && !"".equals(qUser.getIdcard())){
				return StringUtil.returnMapToView("300", "用户名已存在！");
			}else{
				user.setPassword(new MD5().getMD5ofStr(user.getPassword()));
				systemService.saveUser(user);
				return StringUtil.returnMapToView("200", "用户添加成功！");
			}
		}catch(Exception e){
			e.printStackTrace();
			return StringUtil.returnMapToView("500", "用户添加失败");
		}
	}

	/**
	 * 删除用户
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 坑2 @RequestBody 自动装配的语法要求：不允许在局部进行自动装配（即:只能写在method外class里）。
	 *
	 */
	@RequestMapping(value = "/delUser")
	public @ResponseBody
	Map<String, Object> delUser(@RequestBody TMuser user, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try{
//            TRole menu = menuService.findById(uuid);
			systemService.deleteUser(user);
			return StringUtil.returnMapToView("200", "用户删除成功！");
		}catch(Exception e){
			e.printStackTrace();
			return StringUtil.returnMapToView("500", "用户删除失败！");
		}

	}




}



