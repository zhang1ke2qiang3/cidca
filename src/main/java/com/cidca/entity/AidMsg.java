package com.cidca.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "t_aidmsg")
public class AidMsg  implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "yearr")
	private Integer yearr;

	@Column(name = "project_name")
	private String project_name;

	@ManyToOne(targetEntity = TBaseData.class, fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "recipient")
	@NotFound(action = NotFoundAction.IGNORE)
	private TBaseData recipient;

	@Column(name = "recipient_content")
	private String recipient_content;//受援内容

	@Column(name = "annual_expenditure")
	private BigDecimal annual_expenditure;//年度支出金额
	
	@ManyToOne(targetEntity = TBaseData.class, fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "expenditure_form")
	@NotFound(action = NotFoundAction.IGNORE)
	private TBaseData expenditure_form;//资金来源
	
	@Column(name = "expenditure_remark")
	private String expenditure_remark;//资金来源备注

	@Column(name = "project_begin")
	private Date project_begin;//起止时间
	
	@Column(name = "project_end")
	private Date project_end;//起止时间
	
	@Column(name = "project_remark")
	private String project_remark;//项目备注
	
	@Transient
	private int col1;//自定义
	@Transient
	private int col2;//自定义
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getYearr() {
		return yearr;
	}
	public void setYearr(Integer yearr) {
		this.yearr = yearr;
	}
	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	public TBaseData getRecipient() {
		return recipient;
	}
	public void setRecipient(TBaseData recipient) {
		this.recipient = recipient;
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
	public TBaseData getExpenditure_form() {
		return expenditure_form;
	}
	public void setExpenditure_form(TBaseData expenditure_form) {
		this.expenditure_form = expenditure_form;
	}
	public String getExpenditure_remark() {
		return expenditure_remark;
	}
	public void setExpenditure_remark(String expenditure_remark) {
		this.expenditure_remark = expenditure_remark;
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

	
}
