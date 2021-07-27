package com.cidca.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "t_aidmsgsubclass")
public class AidMsgSubclass  implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "uuid")
	private String uuid;
	
	@Column(name = "yearr")
	private String yearr;

	@Column(name = "aidmsgid")
	private String aidmsgid;//坑3  不支持下划线 , aidmsg_id 在Dao会报错

	@Column(name = "recipient_content")
	private String recipient_content;//受援内容

	@Column(name = "annual_expenditure")
	private BigDecimal annual_expenditure;//年度支出金额
	
	@Column(name = "expenditure_form")
	private String expenditure_form;//资金来源
	
	@Column(name = "expenditure_remark")
	private String expenditure_remark;//资金来源备注
	
	@Transient
	private int col1;//自定义
	@Transient
	private int col2;//自定义
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
	public String getExpenditure_form() {
		return expenditure_form;
	}
	public void setExpenditure_form(String expenditure_form) {
		this.expenditure_form = expenditure_form;
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
	
	
	
	
}
