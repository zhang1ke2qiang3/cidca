package com.cidca.projectt.comtroller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.cidca.common.Constants;
import com.cidca.entity.AidMsg;
import com.cidca.entity.AidMsgSubclass;
import com.cidca.entity.TBaseData;
import com.cidca.entity.TBusiLog;
import com.cidca.entity.TMuser;
import com.cidca.projectt.service.AidMsgService;
import com.cidca.projectt.service.AidMsgSubclassService;
import com.cidca.system.service.BasedataService;
import com.cidca.system.service.BusiLogService;
import com.cidca.system.service.MuserService;
import com.cidca.util.BigDecimalUtil;
import com.cidca.util.DateTools;
import com.cidca.util.MultifunUtils;
import com.cidca.util.StringUtil;

/**
 * 首页 待办
 * 
 * @author mingtian
 */
@SuppressWarnings("unused")
@Controller
@RequestMapping("/aidmsg")
public class AidMsgController {

	@Autowired
	private AidMsgService aidmsgservice;
	@Autowired
	private AidMsgSubclassService ascService;
	@Autowired
	private MuserService userService;
	@Autowired
	private BusiLogService busiLogService;
	@Autowired
	private BasedataService basedataService;

	/**
	 * 数据填报-页面
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	//	@RequiresRoles("1")
	//	@RequiresPermissions("external")
	@RequestMapping("/inputAidmsg")
	public String inputAidmsg(HashMap<String, Object> map, Model model, HttpServletRequest request) throws Exception {
		//		TMuser user = (TMuser)request.getSession().getAttribute(Constants.SESSION_KEY);
		//		model.addAttribute("user","欢迎："+user.getFullname());//两种方法都可以，和spring model and view一样
		//		map.put("title", "对外援助统计数据直报平台 ");
		//model.addAttribute("idcard",user.getIdcard());
		//		if (StringUtils.isEmpty(user.getIdcard())) {
		//			model.addAttribute("msg","获取当前登录人失败，请联系管理员~");
		//			return "/sys/error";
		//		}

		model.addAttribute("idcard","qqq");
		model.addAttribute("yearList", basedataService.findByTypes("year"));
		return "/aidmsg/inputAidmsg";// 自动把String解析为视图
	}

	/**
	 * 数据填报-页面
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	//	@RequiresRoles("1")
	//	@RequiresPermissions("external")
	@RequestMapping("/getInputAidmsg")
	public String getInputAidmsg(HashMap<String, Object> map, Model model, HttpServletRequest request) throws Exception {

		String uuid = request.getParameter("uuid");
		AidMsgSubclass childObj = ascService.findById(uuid);
		AidMsg obj = aidmsgservice.findById(childObj.getAidmsgid());
		String principal = (String) SecurityUtils.getSubject().getPrincipal();//用shiro获取当前登录用户名
		model.addAttribute("idcard",principal);
//		model.addAttribute("yearList", basedataService.findByTypes("year"));
		model.addAttribute("obj",obj);
		model.addAttribute("childObj",childObj);
		return "/aidmsg/exinputAidmsg";// 自动把String解析为视图
	}

	/**
	 * 数据填报-提交保存 
	 * ~1、存主表的：项目名称、受援国/受援地区/国际或区域性组织、项目备注存主表
	 * ~2、存子表的：年度、受援助内容、年度支出金额（*3）、起止时间（*2）、资金来源备注
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	//	@RequiresRoles("1") //shiro 先注释了
	//	@RequiresPermissions("external") //shiro 先注释了
	@RequestMapping(value = "/saveAidMsg")
	public @ResponseBody Map<String, Object> saveAidMsg(@RequestBody AidMsgSubclass aidmsgSc, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			//TMuser user = (TMuser)request.getSession().getAttribute(Constants.SESSION_KEY);
			//			if (null==user) {
			//				return StringUtil.returnMapToView( "200", "获取用户失败");
			//			}
			// 
			AidMsgSubclass obj = null;
			AidMsg aidMsg = null;
			BigDecimal total = BigDecimalUtil.toZero(aidmsgSc.getExpenditure_form_my()).add(aidmsgSc.getExpenditure_form_fundraising()).add(aidmsgSc.getExpenditure_form_other());
			//已存在的
			List<AidMsgSubclass> asList = ascService.findByCreateUserAndProject_name(aidmsgSc.getCreate_user(), aidmsgSc.getProject_name());
			if (null!=asList && asList.size()>0) {//填报过的，在原基础上修改即可
				obj=asList.get(0);
				if (!obj.getStatee().equals(Constants.AUDIT_TEMP) || !obj.getStatee().equals(Constants.AUDIT_BACK)) {
					return StringUtil.returnMapToView( "200", "您已提交过该项目数据，请更换项目名称申报");
				}
				aidMsg = aidmsgservice.findById(obj.getUuid());
				if (null!=aidMsg) {
					aidMsg.setAnnual_expenditure(total);
					aidMsg.setUpdate_time(new Date());
				}
			} else {
				aidMsg=new AidMsg();
				aidMsg.setUuid(StringUtil.getUUIDRandomHexToUpperCase());
				obj=new AidMsgSubclass();
				obj.setUuid(StringUtil.getUUIDRandomHexToUpperCase());
				obj.setAidmsgid(aidMsg.getUuid());
			}


			obj.setYearr(MultifunUtils.judgeStr(aidmsgSc.getYearr()));
			obj.setProject_name(MultifunUtils.judgeStr(aidmsgSc.getProject_name()));
			obj.setRecipientCode(MultifunUtils.judgeInteger(aidmsgSc.getRecipientCode()));
			obj.setRecipient(MultifunUtils.judgeStr(aidmsgSc.getRecipient()));
			obj.setRecipient_content(MultifunUtils.judgeStr(aidmsgSc.getRecipient_content()));

			obj.setExpenditure_form_my(BigDecimalUtil.toZero(aidmsgSc.getExpenditure_form_my()));
			obj.setExpenditure_form_fundraising(BigDecimalUtil.toZero(aidmsgSc.getExpenditure_form_fundraising()));
			obj.setExpenditure_form_other(BigDecimalUtil.toZero(aidmsgSc.getExpenditure_form_other()));
			obj.setAnnual_expenditure(total);

			obj.setExpenditure_remark(MultifunUtils.judgeStr(aidmsgSc.getExpenditure_remark()));
			obj.setProject_begin(MultifunUtils.judgeDate(aidmsgSc.getProject_begin()));
			obj.setProject_end(MultifunUtils.judgeDate(aidmsgSc.getProject_end()));
			obj.setProject_remark(MultifunUtils.judgeStr(aidmsgSc.getProject_remark()));
			obj.setStatee(aidmsgSc.getStatee());
			//obj.setCreate_user(user.getIdcard());//TODO 上线前记得替换
			obj.setCreate_user(aidmsgSc.getCreate_user());
			obj.setCreate_time(new Date());
			ascService.save(obj);

			aidMsg.setYearr(MultifunUtils.judgeStr(aidmsgSc.getYearr()));//作为第一次填报年度留存
			aidMsg.setProject_name(MultifunUtils.judgeStr(aidmsgSc.getProject_name()));
			aidMsg.setRecipient(MultifunUtils.judgeStr(aidmsgSc.getRecipient()));
			aidMsg.setProject_remark(MultifunUtils.judgeStr(aidmsgSc.getProject_remark()));
			aidmsgservice.insert(aidMsg);

			busiLogService.insert(
					new TBusiLog(StringUtil.getUUIDRandomHexToUpperCase(),"数据填报-新建", "t_aidmsg", 
							obj.getUuid(), null,"填报了项目："+obj.getProject_name(), new Date(), null)
					);// user.getFullname()+"填报了项目："+obj.getProject_name(),
		} catch (Exception e) {
			e.printStackTrace();
			return StringUtil.returnMapToView( "200", "error"+e.getMessage());
		}
		return StringUtil.returnMapToView( "100", "提交成功");
	}

	/**
	 * 待报项目-页面
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresRoles("1")
	@RequiresPermissions("external")
	@RequestMapping("/aidMsgWaitList")
	public String aidMsgWaitList(HashMap<String, Object> map,Model model,HttpServletRequest request) throws Exception {
		//		TMuser user = (TMuser)request.getSession().getAttribute(Constants.SESSION_KEY);
		//		String principal = (String)SecurityUtils.getSubject().getPrincipal();//用shiro获取当前登录用户名
		//		model.addAttribute("user","欢迎："+user.getFullname());//两种方法都可以，和spring model and view一样
		map.put("title", "对外援助统计数据直报平台 ");
		return "/aidmsg/aidMsgWaitList";// 自动把String解析为视图
	}

	/**
	 * 待报项目-列表
	 * @param aidmsg
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
	@RequestMapping(value = "/getAidMsgWaitList")
	public @ResponseBody Map<String, Object> getAidMsgWaitList(
			AidMsgSubclass aidmsg,Integer pageIndex,Integer pageSize,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// 查询条件
		Specification<AidMsgSubclass> spec = new Specification<AidMsgSubclass>() {
			Predicate ca = null;
			@Override
			public Predicate toPredicate(Root<AidMsgSubclass> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> pList = new ArrayList<Predicate>();
				if (StringUtils.isNotEmpty(aidmsg.getYearr())) {
					ca = cb.equal(root.get("yearr").as(String.class), aidmsg.getYearr());
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getProject_name())) {
					ca = cb.like(root.get("project_name").as(String.class),"%" + aidmsg.getProject_name() + "%");
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getRecipient())) {
					ca = cb.like(root.get("recipient").as(String.class), "%" + aidmsg.getRecipient() + "%");
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getRecipient_content())) {
					ca = cb.like(root.get("recipient_content").as(String.class), "%" + aidmsg.getRecipient_content() + "%");
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getProject_b())) {
					Date begin = DateTools.parseDateDayFormat(aidmsg.getProject_b());
					ca = cb.greaterThanOrEqualTo(root.get("project_begin").as(Date.class), begin);
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getProject_e())) {
					Date endDate = DateTools.parseDateDayFormat(aidmsg.getProject_e());
					ca = cb.lessThanOrEqualTo(root.get("project_end").as(Date.class), endDate);
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getCreate_user())) {
					ca = cb.equal(root.get("create_user").as(String.class), aidmsg.getCreate_user());
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getStatee())) {
					ca = cb.equal(root.get("statee").as(String.class), aidmsg.getStatee());
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getAudit_user())) {
					ca = cb.equal(root.get("audit_user").as(String.class), aidmsg.getAudit_user());
					pList.add(ca);
				}
				ca = cb.equal(root.get("statee").as(String.class), Constants.AUDIT_TEMP);// 0==草稿
				pList.add(ca);
				Predicate[] pre = new Predicate[pList.size()];
				query.where(pList.toArray(pre));
				return query.getRestriction();
			}
		};
		int startIndex=pageIndex-1;
		PageRequest pageable = PageRequest.of(startIndex, pageSize);
		Page<AidMsgSubclass> pageList = ascService.findAll(spec, pageable);
		map.put("resultcode", "100");
		map.put("total", pageList.getTotalElements());
		map.put("rows", pageList.getContent());
		return map;
	}

	/**
	 * 已报项目/进度查询-页面
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresRoles("1")
	@RequiresPermissions("external")
	@RequestMapping("/aidMsgAlreadyList")
	public String aidMsgAlreadyList(HashMap<String, Object> map,Model model,HttpServletRequest request) throws Exception {
		//		TMuser user = (TMuser)request.getSession().getAttribute(Constants.SESSION_KEY);
		//		String principal = (String)SecurityUtils.getSubject().getPrincipal();//用shiro获取当前登录用户名
		//		model.addAttribute("user","欢迎："+user.getFullname());//两种方法都可以，和spring model and view一样
		map.put("title", "对外援助统计数据直报平台 ");
		return "/aidmsg/aidMsgAlreadyList";// 自动把String解析为视图
	}

	/**
	 * 已报项目/进度查询-列表
	 * @param aidmsg
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
	@RequestMapping("/getAidMsgAlreadyList")
	public @ResponseBody Map<String, Object> getAidMsgAlreadyList(AidMsgSubclass aidmsg,Integer pageIndex,Integer pageSize,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// 查询条件
		Specification<AidMsgSubclass> spec = new Specification<AidMsgSubclass>() {
			Predicate ca = null;
			@Override
			public Predicate toPredicate(Root<AidMsgSubclass> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> pList = new ArrayList<Predicate>();
				if (StringUtils.isNotEmpty(aidmsg.getYearr())) {
					ca = cb.equal(root.get("yearr").as(String.class), aidmsg.getYearr());
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getProject_name())) {
					ca = cb.like(root.get("project_name").as(String.class), "%" +aidmsg.getProject_name()+ "%");
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getRecipient())) {
					ca = cb.like(root.get("recipient").as(String.class), "%" +aidmsg.getRecipient()+ "%");
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getRecipient_content())) {
					ca = cb.like(root.get("recipient_content").as(String.class), "%" +aidmsg.getRecipient_content()+ "%");
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getValue_b())) {
					BigDecimal value = BigDecimalUtil.strToBigDecimal(aidmsg.getValue_b());
					ca = cb.greaterThanOrEqualTo(root.get("annual_expenditure").as(BigDecimal.class), value);
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getValue_e())) {
					BigDecimal value = BigDecimalUtil.strToBigDecimal(aidmsg.getValue_e());
					ca = cb.lessThanOrEqualTo(root.get("annual_expenditure").as(BigDecimal.class), value);
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getProject_b())) {
					Date begin = DateTools.parseDateDayFormat(aidmsg.getProject_b());
					ca = cb.greaterThanOrEqualTo(root.get("project_begin").as(Date.class), begin);
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getProject_e())) {
					Date endDate = DateTools.parseDateDayFormat(aidmsg.getProject_e());
					ca = cb.lessThanOrEqualTo(root.get("project_end").as(Date.class), endDate);
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getCreate_user())) {
					ca = cb.equal(root.get("create_user").as(String.class), aidmsg.getCreate_user());
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getStatee())) {
					ca = cb.equal(root.get("statee").as(String.class), aidmsg.getStatee());
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getAudit_user())) {
					ca = cb.equal(root.get("audit_user").as(String.class), aidmsg.getAudit_user());
					pList.add(ca);
				}
				ca = cb.notEqual(root.get("statee").as(String.class), Constants.AUDIT_TEMP);// 0==草稿
				pList.add(ca);
				Predicate[] pre = new Predicate[pList.size()];
				query.where(pList.toArray(pre));
				return query.getRestriction();
			}
		};
		int startIndex=pageIndex-1;
		PageRequest pageable = PageRequest.of(startIndex, pageSize);
		Page<AidMsgSubclass> pageList = ascService.findAll(spec, pageable);
		map.put("resultcode", "100");
		map.put("total", pageList.getTotalElements());
		map.put("rows", pageList.getContent());
		return map;
	}

	/**
	 * 数据填报-修改页
	 * @param uuid
	 * @param type
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	//	@RequiresRoles("1")
	//	@RequiresPermissions("external")
	@RequestMapping(value="/editAidMsg")
	public @ResponseBody String editAidMsg(String uuid,String type,HttpServletRequest request,Model model) throws Exception{
		JSONObject result = new JSONObject();
		//TMuser user = (TMuser) request.getSession().getAttribute(Constants.SESSION_KEY);// 经过用户认证后的user提交自己的数据
		if (StringUtils.isNotEmpty(uuid)) {
			AidMsgSubclass obj = ascService.findById(uuid);
			if (null != obj) {
				//				if (!user.getUserid().equals(aidMsg.getCreate_user())) {
				//					return StringUtil.returnMapToView("200","删除失败，请选择您本人提交的数据进行删除");
				//				}
				if (Constants.AUDIT_TEMP.equals(obj.getStatee()) || Constants.AUDIT_WAIT.equals(obj.getStatee())) {
					model.addAttribute("obj", obj);
				} 
			}
		}else{
			result.put("resultcode", "200");
			result.put("value", "请选择一条数据进行编辑");
		}
		result.put("resultcode", "100");
		result.put("value", model);
		System.out.println(result.toJSONString());
		return result.toJSONString();
	}	

	/**
	 * 提交审核-表格上的提交按钮
	 * @param uuid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	//	@RequiresRoles("1")
	//	@RequiresPermissions("external")
	@RequestMapping(value = "/saveAidmsgSecondSubmission")
	public @ResponseBody Map<String, Object> saveAidmsgSecondSubmission(String uuid, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (StringUtils.isNotEmpty(uuid)) {
			AidMsgSubclass data = ascService.findById(uuid);
			if (null!=data) {
				if (Constants.AUDIT_TEMP.equals(data.getStatee()) || Constants.AUDIT_BACK.equals(data.getStatee())) {
					data.setStatee(Constants.AUDIT_WAIT);
					data.setUpdate_time(new Date());
					ascService.updateAidMsg(data);
					busiLogService.insert(
							new TBusiLog(StringUtil.getUUIDRandomHexToUpperCase(),
									"数据填报-提交审核", "t_aidmsg", uuid, null, "提交项目审核："+data.getProject_name(), new Date(), null)
							);
				}else{
					return StringUtil.returnMapToView("200", "请选择草稿或者退回的数据进行提交！");
				}
			}else{
				return StringUtil.returnMapToView("200", "未找到该数据，请确认并重新选择");
			}
		}else{
			return StringUtil.returnMapToView("200", "未找到该数据，请确认并重新选择");
		}
		return StringUtil.returnMapToView("100", "提交成功");
	}

	/** 数据填报-删除数据*/
	//	@RequiresRoles("1")
	//	@RequiresPermissions("external")
	@RequestMapping(value = "/delAidMsg")
	public @ResponseBody Map<String, Object> delAidMsg(@RequestBody AidMsgSubclass aidMsg, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//TMuser user = (TMuser) request.getSession().getAttribute(Constants.SESSION_KEY);// 经过用户认证后的user提交自己的数据
		if (null!=aidMsg) {
			AidMsgSubclass obj = ascService.findById(aidMsg.getUuid());
			if (null != obj) {
				//				if (!user.getUserid().equals(aidMsg.getCreate_user())) {
				//					return StringUtil.returnMapToView("200","删除失败，请选择您本人提交的数据进行删除");
				//				}
				if (Constants.AUDIT_TEMP.equals(obj.getStatee())) {
					ascService.deleteById(obj.getUuid());
				} else {
					return StringUtil.returnMapToView("200", "删除失败，请草稿状态的数据进行删除");
				}
			}
		}else{
			return StringUtil.returnMapToView("200", "删除失败，请选择一条数据进行删除");
		}
		return StringUtil.returnMapToView("100", "删除成功");
	}

	/**
	 * 数据填报-复制引用（根据UUID获取一条记录）
	 * @param uuid
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	//	@RequiresRoles("1")
		@RequiresPermissions("external")
	@RequestMapping(value="/getAidMsgOne")
	public @ResponseBody String getAidMsgOne(String uuid,HttpServletRequest request,Model model) throws Exception{
		JSONObject result = new JSONObject();
		//TMuser user = (TMuser) request.getSession().getAttribute(Constants.SESSION_KEY);// 经过用户认证后的user提交自己的数据
		if (StringUtils.isNotEmpty(uuid)) {
			AidMsgSubclass obj = ascService.findById(uuid);
			if (null != obj) {
				model.addAttribute("obj", obj);
			}
		}else{
			result.put("resultcode", "200");
			result.put("value", "请选择一条数据进行编辑");
		}
		result.put("resultcode", "100");
		result.put("value", model);
		System.out.println(result.toJSONString());
		return result.toJSONString();
	}	

	/**
	 * 数据填报-查看详情
	 * @param uuid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	//	@RequiresRoles("1")
	//	@RequiresPermissions("external")
	@RequestMapping(value = "/aidMsgDetail")
	public @ResponseBody Map<String, Object> aidMsgDetail(String uuid, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(uuid)) {
			AidMsgSubclass obj = ascService.findById(uuid);
			if (null != obj) {
				map.put("obj", obj);
				map.put("resultcode", "100");
			}
		}else{
			return StringUtil.returnMapToView("200", "未查询到信息！");
		}
		return map;
	}

	/**
	 * 获取该填报人历年的所有数据 TODO
	 */
	@SuppressWarnings("serial")
	//	@RequiresRoles("1")
	//	@RequiresPermissions("external")
	@RequestMapping("/getAidMsgList")
	public @ResponseBody Map<String, Object> getAidMsgList(AidMsgSubclass aidmsg,Integer pageIndex,Integer pageSize,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// 查询条件
		if (StringUtils.isEmpty(aidmsg.getCreate_user())) {
			return StringUtil.returnMapToView("200", "获取当前信息失败！");
		}
		Specification<AidMsgSubclass> spec = new Specification<AidMsgSubclass>() {
			Predicate ca = null;
			@Override
			public Predicate toPredicate(Root<AidMsgSubclass> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> pList = new ArrayList<Predicate>();
				if (StringUtils.isNotEmpty(aidmsg.getCreate_user())) {
					ca = cb.equal(root.get("create_user").as(String.class), aidmsg.getCreate_user());
					pList.add(ca);
				}
				pList.add(ca);
				Predicate[] pre = new Predicate[pList.size()];
				query.where(pList.toArray(pre));
				return query.getRestriction();
			}
		};
		int startIndex=pageIndex-1;
		PageRequest pageable = PageRequest.of(startIndex, pageSize);
		Page<AidMsgSubclass> pageList = ascService.findAll(spec, pageable);
		map.put("resultcode", "100");
		map.put("total", pageList.getTotalElements());
		map.put("rows", pageList.getContent());
		return map;
	}

	/**
	 * 获取受援助国家或组织信息
	 * @param msg
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	//	@RequiresRoles("1")
	//	@RequiresPermissions("external")
	@RequestMapping(value = "/getRecipientList")
	public @ResponseBody Map<String, Object> getRecipientList(String msg,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<TBaseData> list=null;
		Map<String, String> paramap=new HashMap<String, String>();
		list = basedataService.findAll();
		map.put("list", list);
		return map;
	}

	/**
	 * 已报项目/进度查询-页面
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions("external")
	@RequestMapping("/shenpiList")
	public String shenpiList(HashMap<String, Object> map,Model model,HttpServletRequest request) throws Exception {
		//		TMuser user = (TMuser)request.getSession().getAttribute(Constants.SESSION_KEY);
		//		String principal = (String)SecurityUtils.getSubject().getPrincipal();//用shiro获取当前登录用户名
		//		model.addAttribute("user","欢迎："+user.getFullname());//两种方法都可以，和spring model and view一样
		map.put("title", "对外援助统计数据直报平台 ");
		return "/aidmsg/waitFlowList";// 自动把String解析为视图
	}

	/**
	 * 已报项目/进度查询-列表
	 * @param aidmsg
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
	@RequestMapping("/getShenPiList")
	public @ResponseBody Map<String, Object> getShenPiList(AidMsgSubclass aidmsg,Integer pageIndex,Integer pageSize,
																  HttpServletRequest request,
																  HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// 查询条件
		Specification<AidMsgSubclass> spec = new Specification<AidMsgSubclass>() {
			Predicate ca = null;
			@Override
			public Predicate toPredicate(Root<AidMsgSubclass> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> pList = new ArrayList<Predicate>();
				if (StringUtils.isNotEmpty(aidmsg.getYearr())) {
					ca = cb.equal(root.get("yearr").as(String.class), aidmsg.getYearr());
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getProject_name())) {
					ca = cb.like(root.get("project_name").as(String.class), "%" +aidmsg.getProject_name()+ "%");
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getRecipient())) {
					ca = cb.like(root.get("recipient").as(String.class), "%" +aidmsg.getRecipient()+ "%");
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getRecipient_content())) {
					ca = cb.like(root.get("recipient_content").as(String.class), "%" +aidmsg.getRecipient_content()+ "%");
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getValue_b())) {
					BigDecimal value = BigDecimalUtil.strToBigDecimal(aidmsg.getValue_b());
					ca = cb.greaterThanOrEqualTo(root.get("annual_expenditure").as(BigDecimal.class), value);
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getValue_e())) {
					BigDecimal value = BigDecimalUtil.strToBigDecimal(aidmsg.getValue_e());
					ca = cb.lessThanOrEqualTo(root.get("annual_expenditure").as(BigDecimal.class), value);
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getProject_b())) {
					Date begin = DateTools.parseDateDayFormat(aidmsg.getProject_b());
					ca = cb.greaterThanOrEqualTo(root.get("project_begin").as(Date.class), begin);
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getProject_e())) {
					Date endDate = DateTools.parseDateDayFormat(aidmsg.getProject_e());
					ca = cb.lessThanOrEqualTo(root.get("project_end").as(Date.class), endDate);
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getCreate_user())) {
					ca = cb.equal(root.get("create_user").as(String.class), aidmsg.getCreate_user());
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getStatee())) {
					ca = cb.equal(root.get("statee").as(String.class), aidmsg.getStatee());
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getAudit_user())) {
					ca = cb.equal(root.get("audit_user").as(String.class), aidmsg.getAudit_user());
					pList.add(ca);
				}
				ca = cb.equal(root.get("statee").as(String.class), Constants.AUDIT_WAIT);// 0==草稿
				pList.add(ca);
				Predicate[] pre = new Predicate[pList.size()];
				query.where(pList.toArray(pre));
				return query.getRestriction();
			}
		};
		int startIndex=pageIndex-1;
		PageRequest pageable = PageRequest.of(startIndex, pageSize);
		Page<AidMsgSubclass> pageList = ascService.findAll(spec, pageable);
		map.put("resultcode", "100");
		map.put("total", pageList.getTotalElements());
		map.put("rows", pageList.getContent());
		return map;
	}

	/**
	 * 数据填报-提交保存
	 * ~1、存主表的：项目名称、受援国/受援地区/国际或区域性组织、项目备注存主表
	 * ~2、存子表的：年度、受援助内容、年度支出金额（*3）、起止时间（*2）、资金来源备注
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	//	@RequiresRoles("1") //shiro 先注释了
		@RequiresPermissions("external") //shiro 先注释了
	@RequestMapping(value = "/shenhe")
	public @ResponseBody Map<String, Object> shenhe(@RequestBody AidMsgSubclass aidmsgSc, HttpServletRequest request,
														HttpServletResponse response) throws Exception {
		try {
			String principal = (String) SecurityUtils.getSubject().getPrincipal();//用shiro获取当前登录用户名
			AidMsgSubclass saveObj = ascService.findById(aidmsgSc.getUuid());
			saveObj.setStatee(aidmsgSc.getStatee());
			saveObj.setAudit_user(principal);
			ascService.updateAidMsg(saveObj);
		} catch (Exception e) {
			e.printStackTrace();
			return StringUtil.returnMapToView( "200", "error"+e.getMessage());
		}
		if("2".equals(aidmsgSc.getStatee())){
			return StringUtil.returnMapToView( "100", "审批成功！");
		}else{
			return StringUtil.returnMapToView( "100", "退回成功！");
		}

	}

	/**
	 * 进行下一项审批
	 * @param map
	 * @param
	 * @param request
	 * @return
	 * @throws Exception
	 */
	//	@RequiresRoles("1")
	//	@RequiresPermissions("external")
	@RequestMapping("/getNextInputAidmsg")
	public @ResponseBody Map<String, Object> getNextInputAidmsg(HashMap<String, Object> map, HttpServletRequest request) throws Exception {
		String uuid = request.getParameter("uuid");
		AidMsgSubclass saveObj = ascService.findById(uuid);
		saveObj.setStatee("2");
		ascService.updateAidMsg(saveObj);

		List<AidMsgSubclass> list = ascService.findByStaee();
		AidMsgSubclass childObj;
		if(null != list && list.size() > 0){
			childObj = list.get(0);
			AidMsg obj = aidmsgservice.findById(childObj.getAidmsgid());
			String principal = (String) SecurityUtils.getSubject().getPrincipal();//用shiro获取当前登录用户名
//			map.put("idcard",principal);
////		model.addAttribute("yearList", basedataService.findByTypes("year"));
//			map.put("obj",obj);
//			map.put("childObj",childObj);
//			map.put("uuid",childObj.getUuid());
			return StringUtil.returnMapToView( "200", childObj.getUuid());
		}else{
			//如果审核数据位空则跳转到列表页面
			return StringUtil.returnMapToView( "300", "所有数据已审批完成！");
		}
	}

	/**
	 * 已审批项目
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions("external")
	@RequestMapping("/alreadyShenpiList")
	public String alreadyShenpiList(HashMap<String, Object> map,Model model,HttpServletRequest request) throws Exception {
		//		TMuser user = (TMuser)request.getSession().getAttribute(Constants.SESSION_KEY);
		//		String principal = (String)SecurityUtils.getSubject().getPrincipal();//用shiro获取当前登录用户名
		//		model.addAttribute("user","欢迎："+user.getFullname());//两种方法都可以，和spring model and view一样
		map.put("title", "对外援助统计数据直报平台 ");
		return "/aidmsg/alreadyList";// 自动把String解析为视图
	}
	/**
	 * 全部项目查询
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions("external")
	@RequestMapping("/queryProject")
	public String queryProject(HashMap<String, Object> map,Model model,HttpServletRequest request) throws Exception {
		//		TMuser user = (TMuser)request.getSession().getAttribute(Constants.SESSION_KEY);
		//		String principal = (String)SecurityUtils.getSubject().getPrincipal();//用shiro获取当前登录用户名
		//		model.addAttribute("user","欢迎："+user.getFullname());//两种方法都可以，和spring model and view一样
		map.put("title", "对外援助统计数据直报平台 ");
		return "/aidmsg/allProjectList";// 自动把String解析为视图
	}

	/**
	 * 待报项目-列表
	 * @param aidmsg
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
	@RequestMapping(value = "/getAlreadyList")
	public @ResponseBody Map<String, Object> getAlreadyList(
			AidMsgSubclass aidmsg,Integer pageIndex,Integer pageSize,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// 查询条件
		Specification<AidMsgSubclass> spec = new Specification<AidMsgSubclass>() {
			Predicate ca = null;
			@Override
			public Predicate toPredicate(Root<AidMsgSubclass> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> pList = new ArrayList<Predicate>();
				if (StringUtils.isNotEmpty(aidmsg.getYearr())) {
					ca = cb.equal(root.get("yearr").as(String.class), aidmsg.getYearr());
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getProject_name())) {
					ca = cb.like(root.get("project_name").as(String.class),"%" + aidmsg.getProject_name() + "%");
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getRecipient())) {
					ca = cb.like(root.get("recipient").as(String.class), "%" + aidmsg.getRecipient() + "%");
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getRecipient_content())) {
					ca = cb.like(root.get("recipient_content").as(String.class), "%" + aidmsg.getRecipient_content() + "%");
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getProject_b())) {
					Date begin = DateTools.parseDateDayFormat(aidmsg.getProject_b());
					ca = cb.greaterThanOrEqualTo(root.get("project_begin").as(Date.class), begin);
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getProject_e())) {
					Date endDate = DateTools.parseDateDayFormat(aidmsg.getProject_e());
					ca = cb.lessThanOrEqualTo(root.get("project_end").as(Date.class), endDate);
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getCreate_user())) {
					ca = cb.equal(root.get("create_user").as(String.class), aidmsg.getCreate_user());
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getStatee())) {
					ca = cb.equal(root.get("statee").as(String.class), aidmsg.getStatee());
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getAudit_user())) {
					ca = cb.equal(root.get("audit_user").as(String.class), aidmsg.getAudit_user());
					pList.add(ca);
				}
				ca = cb.equal(root.get("statee").as(String.class), Constants.AUDIT_OK);// 0==草稿
				pList.add(ca);
				Predicate[] pre = new Predicate[pList.size()];
				query.where(pList.toArray(pre));
				return query.getRestriction();
			}
		};
		int startIndex=pageIndex-1;
		PageRequest pageable = PageRequest.of(startIndex, pageSize);
		Page<AidMsgSubclass> pageList = ascService.findAll(spec, pageable);
		map.put("resultcode", "100");
		map.put("total", pageList.getTotalElements());
		map.put("rows", pageList.getContent());
		return map;
	}

	/**
	 * 待报项目-列表
	 * @param aidmsg
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
	@RequestMapping(value = "/getAllProjectList")
	public @ResponseBody Map<String, Object> getAllProjectList(
			AidMsgSubclass aidmsg,Integer pageIndex,Integer pageSize,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// 查询条件
		Specification<AidMsgSubclass> spec = new Specification<AidMsgSubclass>() {
			Predicate ca = null;
			@Override
			public Predicate toPredicate(Root<AidMsgSubclass> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> pList = new ArrayList<Predicate>();
				if (StringUtils.isNotEmpty(aidmsg.getYearr())) {
					ca = cb.equal(root.get("yearr").as(String.class), aidmsg.getYearr());
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getProject_name())) {
					ca = cb.like(root.get("project_name").as(String.class),"%" + aidmsg.getProject_name() + "%");
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getRecipient())) {
					ca = cb.like(root.get("recipient").as(String.class), "%" + aidmsg.getRecipient() + "%");
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getRecipient_content())) {
					ca = cb.like(root.get("recipient_content").as(String.class), "%" + aidmsg.getRecipient_content() + "%");
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getProject_b())) {
					Date begin = DateTools.parseDateDayFormat(aidmsg.getProject_b());
					ca = cb.greaterThanOrEqualTo(root.get("project_begin").as(Date.class), begin);
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getProject_e())) {
					Date endDate = DateTools.parseDateDayFormat(aidmsg.getProject_e());
					ca = cb.lessThanOrEqualTo(root.get("project_end").as(Date.class), endDate);
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getCreate_user())) {
					ca = cb.equal(root.get("create_user").as(String.class), aidmsg.getCreate_user());
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getStatee())) {
					ca = cb.equal(root.get("statee").as(String.class), aidmsg.getStatee());
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(aidmsg.getAudit_user())) {
					ca = cb.equal(root.get("audit_user").as(String.class), aidmsg.getAudit_user());
					pList.add(ca);
				}
				ca = cb.notEqual(root.get("statee").as(String.class), Constants.AUDIT_TEMP);// 0==草稿
				pList.add(ca);
				Predicate[] pre = new Predicate[pList.size()];
				query.where(pList.toArray(pre));
				return query.getRestriction();
			}
		};
		int startIndex=pageIndex-1;
		PageRequest pageable = PageRequest.of(startIndex, pageSize);
		Page<AidMsgSubclass> pageList = ascService.findAll(spec, pageable);
		map.put("resultcode", "100");
		map.put("total", pageList.getTotalElements());
		map.put("rows", pageList.getContent());
		return map;
	}

	/**
	 * 批量审核通过
	 * @param map
	 * @param
	 * @param request
	 * @return
	 * @throws Exception
	 */
	//	@RequiresRoles("1")
	//	@RequiresPermissions("external")
	@RequestMapping("/bathPass")
	public @ResponseBody Map<String, Object> bathPass(HashMap<String, Object> map, HttpServletRequest request) throws Exception {
		String uuids = request.getParameter("uuid");
//		AidMsgSubclass saveObj = ascService.findById(uuid);
//		saveObj.setStatee("3");
		String principal = (String)SecurityUtils.getSubject().getPrincipal();//用shiro获取当前登录用户名
//		saveObj.setAudit_user(principal);
		Boolean flag = ascService.bathPass(uuids,principal);
		if(flag){
			return StringUtil.returnMapToView( "200", "批量审批完成！");
		}else{
			return StringUtil.returnMapToView( "500", "批量审批失败！");
		}

	}

	/**
	 * 批量审核通过
	 * @param map
	 * @param
	 * @param request
	 * @return
	 * @throws Exception
	 */
	//	@RequiresRoles("1")
	//	@RequiresPermissions("external")
	@RequestMapping("/bathNoPass")
	public @ResponseBody Map<String, Object> bathNoPass(HashMap<String, Object> map, HttpServletRequest request) throws Exception {
		String uuids = request.getParameter("uuid");
		String principal = (String)SecurityUtils.getSubject().getPrincipal();//用shiro获取当前登录用户名
//		saveObj.setAudit_user(principal);
		Boolean flag = ascService.bathNoPass(uuids,principal);
		if(flag){
			return StringUtil.returnMapToView( "200", "批量不通过完成！");
		}else{
			return StringUtil.returnMapToView( "500", "批量不通过失败！");
		}


	}

	/**
	 * 批量退回
	 * @param map
	 * @param
	 * @param request
	 * @return
	 * @throws Exception
	 */
	//	@RequiresRoles("1")
	//	@RequiresPermissions("external")
	@RequestMapping("/bathBack")
	public @ResponseBody Map<String, Object> bathBack(HashMap<String, Object> map, HttpServletRequest request) throws Exception {
		String uuids = request.getParameter("uuid");
//		AidMsgSubclass saveObj = ascService.findById(uuid);
//		saveObj.setStatee("3");
		String principal = (String)SecurityUtils.getSubject().getPrincipal();//用shiro获取当前登录用户名
//		saveObj.setAudit_user(principal);
		Boolean flag = ascService.bathBack(uuids,principal);
		if(flag){
			return StringUtil.returnMapToView( "200", "批量退回完成！");
		}else{
			return StringUtil.returnMapToView( "500", "批量退回失败！");
		}

	}

	/**
	 *  退回修改页面
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	//	@RequiresRoles("1")
	//	@RequiresPermissions("external")
	@RequestMapping("/getEditInputAidmsg")
	public String getEditInputAidmsg(HashMap<String, Object> map, Model model, HttpServletRequest request) throws Exception {

		String uuid = request.getParameter("uuid");
		AidMsgSubclass childObj = ascService.findById(uuid);
		AidMsg obj = aidmsgservice.findById(childObj.getAidmsgid());
		String principal = (String) SecurityUtils.getSubject().getPrincipal();//用shiro获取当前登录用户名

		List<TBaseData> list=null;
		Map<String, String> paramap=new HashMap<String, String>();
		list = basedataService.findAll();

		model.addAttribute("idcard",principal);
		model.addAttribute("yearList", basedataService.findByTypes("year"));
		model.addAttribute("obj",obj);
		model.addAttribute("childObj",childObj);
		model.addAttribute("ecList",list);
		return "aidmsg/editInputAidmsg";// 自动把String解析为视图
	}

}
