package com.cidca.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "t_aidmsgsubclass")
public class AidMsgSubclass  implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "uuid")
	private String uuid;
	
	@Column(name = "yearr")
	private String yearr;//申报年度

	@Column(name = "aidmsgid")
	private String aidmsgid;//主表外键

	@Column(name = "project_name")
	private String project_name;//项目名称

	@Column(name = "recipient")
	private String recipient; //受援国家、组织
	
	@Column(name = "recipient_code")
	private Integer recipientCode; //受援国家、组织
	
	@Column(name = "recipient_content")
	private String recipient_content;//受援内容

	@Column(name = "expenditure_form_my")
	private BigDecimal expenditure_form_my;//资金来源-自有
	
	@Column(name = "expenditure_form_fundraising")
	private BigDecimal expenditure_form_fundraising;//资金来源-自筹
	
	@Column(name = "expenditure_form_other")
	private BigDecimal expenditure_form_other;//资金来源-其它
	
	@Column(name = "annual_expenditure")
	private BigDecimal annual_expenditure;//年度支出金额合计-"资金来源"的总数
	
	@Column(name = "expenditure_remark")
	private String expenditure_remark;//资金来源备注
	
	@Column(name = "project_begin")
	private Date project_begin;//起止时间
	
	@Column(name = "project_end")
	private Date project_end;//起止时间
	
	@Column(name = "project_remark")
	private String project_remark;//项目备注
	
	@Column(name = "create_user")
	private String create_user;//创建人
	
	@CreatedDate
	@Column(name = "create_time")
	private Date create_time;//创建日期
	
	@LastModifiedDate
	@Column(name = "update_time")
	private Date update_time;//创建日期
	
	/**
	 * 状态
	 * "0";草稿
	 * "1";//待审核
	 * "2";//审核通过
	 * "3";//审核不通过
	 * "-1";//退回
	 */
	@Column(name = "statee")
	private String statee;//状态
	
	@Column(name = "audit_user")
	private String audit_user;//审核人

	@Column(name = "audit_date")
	private Date audit_date;//审核日期
	
	@Transient
	private int col1;//自定义
	@Transient
	private int col2;//自定义
	
	@Transient
	private String project_b;
	@Transient
	private String project_e;
	@Transient
	private String value_b;
	@Transient
	private String value_e;
	
	@Transient
	private Integer page;
	
	@Transient
	private Integer size;
	
	@Transient
	private String recipient_content_ac;//子表-受援内容

	@Transient
	private BigDecimal annual_expenditure_ac;//子表-年度支出金额
	
	@Transient
	private String expenditure_form_ac;//子表-资金来源
	
	@Transient
	private String expenditure_remark_ac;//子表-资金来源备注
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getYearr() {
		return yearr;
	}
	public void setYearr(String yearr) {
		this.yearr = yearr;
	}
	public String getAidmsgid() {
		return aidmsgid;
	}
	public void setAidmsgid(String aidmsgid) {
		this.aidmsgid = aidmsgid;
	}
	public String getRecipient_content() {
		return recipient_content;
	}
	public void setRecipient_content(String recipient_content) {
		this.recipient_content = recipient_content;
	}
	public BigDecimal getAnnual_expenditure() {
		return annual_expenditure;
	}
	public void setAnnual_expenditure(BigDecimal annual_expenditure) {
		this.annual_expenditure = annual_expenditure;
	}
	public String getExpenditure_remark() {
		return expenditure_remark;
	}
	public void setExpenditure_remark(String expenditure_remark) {
		this.expenditure_remark = expenditure_remark;
	}
	public int getCol1() {
		return col1;
	}
	public void setCol1(int col1) {
		this.col1 = col1;
	}
	public int getCol2() {
		return col2;
	}
	public void setCol2(int col2) {
		this.col2 = col2;
	}
	public BigDecimal getExpenditure_form_my() {
		return expenditure_form_my;
	}
	public void setExpenditure_form_my(BigDecimal expenditure_form_my) {
		this.expenditure_form_my = expenditure_form_my;
	}
	public BigDecimal getExpenditure_form_fundraising() {
		return expenditure_form_fundraising;
	}
	public void setExpenditure_form_fundraising(BigDecimal expenditure_form_fundraising) {
		this.expenditure_form_fundraising = expenditure_form_fundraising;
	}
	public BigDecimal getExpenditure_form_other() {
		return expenditure_form_other;
	}
	public void setExpenditure_form_other(BigDecimal expenditure_form_other) {
		this.expenditure_form_other = expenditure_form_other;
	}
	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public Date getProject_begin() {
		return project_begin;
	}
	public void setProject_begin(Date project_begin) {
		this.project_begin = project_begin;
	}
	public Date getProject_end() {
		return project_end;
	}
	public void setProject_end(Date project_end) {
		this.project_end = project_end;
	}
	public String getProject_remark() {
		return project_remark;
	}
	public void setProject_remark(String project_remark) {
		this.project_remark = project_remark;
	}
	public String getStatee() {
		return statee;
	}
	public void setStatee(String statee) {
		this.statee = statee;
	}
	public String getAudit_user() {
		return audit_user;
	}
	public void setAudit_user(String audit_user) {
		this.audit_user = audit_user;
	}
	public Date getAudit_date() {
		return audit_date;
	}
	public void setAudit_date(Date audit_date) {
		this.audit_date = audit_date;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public String getProject_b() {
		return project_b;
	}
	public void setProject_b(String project_b) {
		this.project_b = project_b;
	}
	public String getProject_e() {
		return project_e;
	}
	public void setProject_e(String project_e) {
		this.project_e = project_e;
	}
	public String getValue_b() {
		return value_b;
	}
	public void setValue_b(String value_b) {
		this.value_b = value_b;
	}
	public String getValue_e() {
		return value_e;
	}
	public void setValue_e(String value_e) {
		this.value_e = value_e;
	}
	public Integer getRecipientCode() {
		return recipientCode;
	}
	public void setRecipientCode(Integer recipientCode) {
		this.recipientCode = recipientCode;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public String getRecipient_content_ac() {
		return recipient_content_ac;
	}
	public void setRecipient_content_ac(String recipient_content_ac) {
		this.recipient_content_ac = recipient_content_ac;
	}
	public BigDecimal getAnnual_expenditure_ac() {
		return annual_expenditure_ac;
	}
	public void setAnnual_expenditure_ac(BigDecimal annual_expenditure_ac) {
		this.annual_expenditure_ac = annual_expenditure_ac;
	}
	public String getExpenditure_form_ac() {
		return expenditure_form_ac;
	}
	public void setExpenditure_form_ac(String expenditure_form_ac) {
		this.expenditure_form_ac = expenditure_form_ac;
	}
	public String getExpenditure_remark_ac() {
		return expenditure_remark_ac;
	}
	public void setExpenditure_remark_ac(String expenditure_remark_ac) {
		this.expenditure_remark_ac = expenditure_remark_ac;
	}
	
	
}
