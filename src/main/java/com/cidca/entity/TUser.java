package com.cidca.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "t_user")
public class TUser implements Serializable {
 
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "userid")
	private String userid;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;
	
	//部门
	@Column(name = "deptid")
	private String deptid;
	
	@Column(name = "tel")
	private String tel;
	
	//20160913加入新字段，为了发送邮件时候获取邮件服务器密码
	@Column(name = "emailpwd")
	private String emailpwd;

	@Column(name = "email")
	private String email;

	@Column(name = "mobile")
	private String mobile;
	
//	@Column(name = "roleid")
//	private String roleid;
	
	@ManyToMany(targetEntity = TRole.class, fetch = FetchType.EAGER)
	@JoinTable(name = "roleid")
    private List<TRole> roleid;
	 
	//时间锁
	@Column(name = "timelock")
	private Date timeLock;

	//姓名
	@Column(name = "realname")
	private String realname;
	
	@Column(name = "uuid")
	private String uuid;
	
	@Transient
	private String deptname;
	@Transient
	private String roleNames;
	@Transient
	private List<TBaseData> lines;

	@Transient
	private String roleIds;

	//评审员类型
	@Transient
	private int Judgetype;
	@Transient
	Map<String,String> tpmap=new HashMap<String,String>();

	public TUser() {
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public String getEmailpwd() {
		return emailpwd;
	}

	public void setEmailpwd(String emailpwd) {
		this.emailpwd = emailpwd;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public List<TBaseData> getLines() {
		return lines;
	}

	public void setLines(List<TBaseData> lines) {
		this.lines = lines;
	}

	public int getJudgetype() {
		return Judgetype;
	}

	public void setJudgetype(int  judgetype) {
		Judgetype  =  judgetype;
	} 

	public Map<String, String> getTpmap() {
		return tpmap;
	}

	public void setTpmap(Map<String, String> tpmap) {
		this.tpmap = tpmap;
	}

	public Date getTimeLock() {
		return timeLock;
	}

	public void setTimeLock(Date timeLock) {
		this.timeLock = timeLock;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<TRole> getRoleid() {
		return roleid;
	}

	public void setRoleid(List<TRole> roleid) {
		this.roleid = roleid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}
}
