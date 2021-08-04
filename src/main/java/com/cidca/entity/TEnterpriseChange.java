package com.cidca.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 企业信息变更表/历史表
 * @author mingtian
 *
 */
@Entity
@Table(name = "t_enterprise_change")
public class TEnterpriseChange implements Serializable {

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

	
	public TEnterpriseChange() {
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
	public TEnterpriseChange(String uuid, String persontype, String idcard, String password, String fullname,
			String shortname, String mobile, String email, String organizationtype, String organizationname,
			String affiliated, String isCommerce, String organizationcode, String area, String address, String manager,
			String telephone,String certificate, String content, Date createtime, Date updatetime) {
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
		this.affiliated = affiliated;
		this.isCommerce = isCommerce;
		this.organizationcode = organizationcode;
		this.area = area;
		this.address = address;
		this.manager = manager;
		this.telephone = telephone;
		this.certificate = certificate;
		this.content = content;
		this.createtime = createtime;
		this.updatetime = updatetime;
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


	public String getAffiliated() {
		return affiliated;
	}


	public void setAffiliated(String affiliated) {
		this.affiliated = affiliated;
	}


	public String getIsCommerce() {
		return isCommerce;
	}


	public void setIsCommerce(String isCommerce) {
		this.isCommerce = isCommerce;
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
