package com.cidca.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_audit_log")
public class TAuditLog implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	//发生事件的用户
	@Column(name = "userid")
	private String userid;

	//IP
	@Column(name = "ipp")
	private String ipp;

	//发生时间
	@Column(name = "createtime")
	private Date createtime;

	//事件内容
	@Column(name = "content")
	private String content;
	
	public TAuditLog() {
		super();
	}
	
	public TAuditLog( String userid, String ipp, Date createtime, String content) {
		super();
		this.userid = userid;
		this.ipp = ipp;
		this.createtime = createtime;
		this.content = content;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getIpp() {
		return ipp;
	}

	public void setIpp(String ipp) {
		this.ipp = ipp;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}
