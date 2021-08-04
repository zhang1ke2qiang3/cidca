package com.cidca.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 账户信息变更表/历史表
 * @author mingtian
 *
 */
@Entity
@Table(name = "t_account_change")
public class TAccountChange implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "uuid")
	private String uuid;

	@Column(name = "idcard")
	private String idcard;

	//姓名
	@Column(name = "fullname")
	private String fullname;

	//手机
	@Column(name = "mobile")
	private String mobile;
	//邮箱
	@Column(name = "email")
	private String email;

	//证明文件
	@Column(name = "certificate")
	private  String certificate;

	//变更内容
	@Column(name = "content")
	private String content;
	
	//状态 0暂存； 1待审核； 2 审核通过； 3审核不通过； -1退回；
	@Column(name = "statee")
	private String state;

	@Column(name = "createtime")
	private Date createtime;

	@Column(name = "updatetime")
	private Date updatetime;
	
	public TAccountChange() {
		super();
	}

	public TAccountChange(String uuid, String idcard, String fullname, String mobile, String email, String certificate,
			String content, String state, Date createtime, Date updatetime) {
		super();
		this.uuid = uuid;
		this.idcard = idcard;
		this.fullname = fullname;
		this.mobile = mobile;
		this.email = email;
		this.certificate = certificate;
		this.content = content;
		this.state = state;
		this.createtime = createtime;
		this.updatetime = updatetime;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	/**0暂存； 1待审核； 2 审核通过； 3审核不通过； -1退回；*/
	public String getState() {
		return state;
	}
	
	/**0暂存； 1待审核； 2 审核通过； 3审核不通过； -1退回；*/
	public void setState(String state) {
		this.state = state;
	}

}
