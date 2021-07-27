package com.cidca.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "t_muser")
public class TMuser implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "uuid")
	private String uuid;

	//人员类型：内部/外部
	@Column(name = "persontype")
	private String persontype;

	@Column(name = "idcard")
	private String idcard;

	@Column(name = "passwordd")
	private String password;
	
	//姓名
	@Column(name = "fullname")
	private String fullname;

	//简写
	@Column(name = "shortname")
	private String shortname;

	//手机
	@Column(name = "mobile")
	private String mobile;
	//邮箱
	@Column(name = "email")
	private String email;

	//企业类型/社会组织类型
	@Column(name = "organizationtype")
	private String organizationtype;

	//企业全称/组织全称
	@Column(name = "organizationname")
	private String organizationname;
	
	//所属上级单位
	@Column(name = "affiliated")
	private String affiliated;
	
	//是否是两家商会会员单位 0否 1是
	@Column(name = "iscommerce")
	private String isCommerce;

	//统一社会信用代码
	@Column(name = "organizationcode")
	private String organizationcode;	

	//注册地址-省份
	@Column(name = "area")
	private String area;
	
	//注册地址
	@Column(name = "address")
	private String address;

	//单位负责人
	@Column(name = "manager")
	private String manager;

	//单位联系电话座机
	@Column(name = "telephone")
	private String telephone;
	
	//证明文件
	@Column(name = "certificate")
	private String certificate;

	//数据来源
	@Column(name = "dbfrom")
	private String dbfrom;

	@Column(name = "createtime")
	private Date createtime;

	@Column(name = "updatetime")
	private Date updatetime;

	@Column(name = "loginnum")
	private Integer loginnum;

	//国籍nationality 
	@Column(name = "nationality")
	private String nationality;

	@Column(name = "timelock")
	private Date timeLock;

	@Transient
	private String imgCode_mobile;//图形验证码
	@Transient
	private String mobileCode;//短信验证码
	
	public TMuser() {
		super();
	}


	/**
	 * 
	 * @param persontype 人员类型：内部/外部
	 * @param idcard
	 * @param password
	 * @param fullname
	 * @param shortname
	 * @param mobile
	 * @param email
	 * @param organizationtype 组织类型
	 * @param organizationname 组织名称
	 * @param organizationcode 统一社会信用代码
	 * @param area
	 * @param address
	 * @param manager 单位负责人
	 * @param telephone
	 * @param certificate 证明文件
	 * @param dbfrom
	 * @param createtime
	 * @param updatetime
	 * @param loginnum
	 * @param nationality
	 * @param timeLock
	 * @param uuid
	 */
	public TMuser(String uuid, String persontype, String idcard, String password, String fullname, String shortname,
			String mobile, String email, String organizationtype, String organizationname, String organizationcode,
			String area, String address, String manager, String telephone, String certificate, String dbfrom,
			Date createtime, Date updatetime, Integer loginnum, String nationality, Date timeLock) {
		super();
		this.uuid = uuid;
		this.persontype = persontype;
		this.idcard = idcard;
		this.password = password;
		this.fullname = fullname;
		this.shortname = shortname;
		this.mobile = mobile;
		this.email = email;
		this.organizationtype = organizationtype;
		this.organizationname = organizationname;
		this.organizationcode = organizationcode;
		this.area = area;
		this.address = address;
		this.manager = manager;
		this.telephone = telephone;
		this.certificate = certificate;
		this.dbfrom = dbfrom;
		this.createtime = createtime;
		this.updatetime = updatetime;
		this.loginnum = loginnum;
		this.nationality = nationality;
		this.timeLock = timeLock;
	}


	public String getUuid() {
		return uuid;
	}


	public void setUuid(String uuid) {
		this.uuid = uuid;
	}


	public String getPersontype() {
		return persontype;
	}


	public void setPersontype(String persontype) {
		this.persontype = persontype;
	}


	public String getIdcard() {
		return idcard;
	}


	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getFullname() {
		return fullname;
	}


	public void setFullname(String fullname) {
		this.fullname = fullname;
	}


	public String getShortname() {
		return shortname;
	}


	public void setShortname(String shortname) {
		this.shortname = shortname;
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


	public String getOrganizationtype() {
		return organizationtype;
	}


	public void setOrganizationtype(String organizationtype) {
		this.organizationtype = organizationtype;
	}


	public String getOrganizationname() {
		return organizationname;
	}


	public void setOrganizationname(String organizationname) {
		this.organizationname = organizationname;
	}


	public String getOrganizationcode() {
		return organizationcode;
	}


	public void setOrganizationcode(String organizationcode) {
		this.organizationcode = organizationcode;
	}


	public String getArea() {
		return area;
	}


	public void setArea(String area) {
		this.area = area;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getManager() {
		return manager;
	}


	public void setManager(String manager) {
		this.manager = manager;
	}


	public String getTelephone() {
		return telephone;
	}


	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}


	public String getCertificate() {
		return certificate;
	}


	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}


	public String getDbfrom() {
		return dbfrom;
	}


	public void setDbfrom(String dbfrom) {
		this.dbfrom = dbfrom;
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


	public Integer getLoginnum() {
		return loginnum;
	}


	public void setLoginnum(Integer loginnum) {
		this.loginnum = loginnum;
	}


	public String getNationality() {
		return nationality;
	}


	public void setNationality(String nationality) {
		this.nationality = nationality;
	}


	public Date getTimeLock() {
		return timeLock;
	}


	public void setTimeLock(Date timeLock) {
		this.timeLock = timeLock;
	}


	public String getAffiliated() {
		return affiliated;
	}


	public void setAffiliated(String affiliated) {
		this.affiliated = affiliated;
	}


	public String getImgCode_mobile() {
		return imgCode_mobile;
	}


	public void setImgCode_mobile(String imgCode_mobile) {
		this.imgCode_mobile = imgCode_mobile;
	}


	public String getMobileCode() {
		return mobileCode;
	}


	public void setMobileCode(String mobileCode) {
		this.mobileCode = mobileCode;
	}


	public String getIsCommerce() {
		return isCommerce;
	}


	public void setIsCommerce(String isCommerce) {
		this.isCommerce = isCommerce;
	}



}
