package com.cidca.projectt.comtroller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cidca.common.Constants;
import com.cidca.entity.AidMsg;
import com.cidca.entity.AidMsgSubclass;
import com.cidca.entity.TUser;
import com.cidca.projectt.service.AidMsgService;
import com.cidca.projectt.service.AidMsgSubclassService;
import com.cidca.util.MultifunUtils;
import com.cidca.util.StringUtil;

/**
 * 审核
 * @author mingtian
 *
 */
@SuppressWarnings("unused")
@Controller
@RequestMapping("/aidmsgAudit")
public class AidMsgAuditController {

	@Autowired
	private AidMsgService aidmsgservice;
	@Autowired
	private AidMsgSubclassService ascService;



}
