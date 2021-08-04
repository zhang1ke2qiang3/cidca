package com.cidca.notice.controller;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cidca.common.Constants;
import com.cidca.entity.AidMsg;
import com.cidca.entity.Tnotice;
import com.cidca.notice.service.NoticeService;
import com.cidca.system.service.BasedataService;
import com.cidca.system.service.BusiLogService;
import com.cidca.system.service.MuserService;
import com.cidca.util.DateTools;
import com.cidca.util.StringUtil;

/**
 * 通知公告
 */
@SuppressWarnings("unused")
@Controller
@RequestMapping("/noticeShow")
public class NoticeController {

	@Autowired
	private NoticeService noticeService;
	@Autowired
	private MuserService userService;
	@Autowired
	private BusiLogService busiLogService;
	@Autowired
	private BasedataService basedataService;



	/**
	 * 通知公告-首页
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	//	@RequiresRoles("1")
	//	@RequiresPermissions("external")
	@RequestMapping("/noticeList")
	public String noticeList(HashMap<String, Object> map,Model model,HttpServletRequest request) throws Exception {
		//		TMuser user = (TMuser)request.getSession().getAttribute(Constants.SESSION_KEY);
		//		String principal = (String)SecurityUtils.getSubject().getPrincipal();//用shiro获取当前登录用户名
		//		model.addAttribute("user","欢迎："+user.getFullname());//两种方法都可以，和spring model and view一样
		map.put("title", "对外援助统计数据直报平台 ");
		return "/noticeShow/noticeList";// 自动把String解析为视图
	}

	/**
	 * 通知公告-列表
	 * @param notice
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
	@RequestMapping(value = "/getNoticeList")
	public @ResponseBody Map<String, Object> getNoticeList(
			Tnotice notice,Integer pageIndex,Integer pageSize,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// 查询条件
		Specification<Tnotice> spec = new Specification<Tnotice>() {
			Predicate ca = null;
			@Override
			public Predicate toPredicate(Root<Tnotice> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> pList = new ArrayList<Predicate>();
				if (StringUtils.isNotEmpty(notice.getCreateUser())) {
					ca = cb.equal(root.get("createUser").as(String.class), notice.getCreateUser());
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(notice.getTitle())) {
					ca = cb.like(root.get("title").as(String.class), "%" +notice.getTitle()+ "%");
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(notice.getAnnounceB())) {
					Date beginDate = DateTools.parseDateDayFormat(notice.getAnnounceB());
					ca = cb.greaterThanOrEqualTo(root.get("announceTime").as(Date.class), beginDate);
					pList.add(ca);
				}
				if (StringUtils.isNotEmpty(notice.getAnnounceE())) {
					Date endDate = DateTools.parseDateDayFormat(notice.getAnnounceE());
					ca = cb.lessThanOrEqualTo(root.get("announceTime").as(Date.class), endDate);
					pList.add(ca);
				}
				ca = cb.equal(root.get("state").as(String.class), Constants.ONE);//1==已发布的
				pList.add(ca);
				Predicate[] pre = new Predicate[pList.size()];
				query.where(pList.toArray(pre));
				return query.getRestriction();
			}
		};
		int startIndex=pageIndex-1;
		PageRequest pageable = PageRequest.of(startIndex, pageSize);
		Page<Tnotice> pageList = noticeService.findAll(spec, pageable);
		map.put("resultcode", "100");
		map.put("total", pageList.getTotalElements());
		map.put("rows", pageList.getContent());
		return map;
	}

	/**
	 * 通知公告-查看详情
	 * @param uuid
	 * @param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
//	@RequiresRoles("1")
//	@RequiresPermissions("external")
	@RequestMapping(value = "/noticeDetail")
	public @ResponseBody Map<String, Object> noticeDetail(String uuid, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(uuid)) {
			Tnotice notice = noticeService.findById(uuid);
			if (null != notice) {
				map.put("obj", notice);
				map.put("resultcode", "100");
			}
		}else{
			return StringUtil.returnMapToView("200", "未查询到信息！");
		}
		return map;
	}
	

}
