package com.cidca.entity;

import java.io.Serializable;
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
@Table(name = "t_muser")
public class TMuser implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	//人员类型：内部/外部
	@ManyToOne(targetEntity = TBaseData.class, fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "persontype")
	@NotFound(action = NotFoundAction.IGNORE)
	private TBaseData persontype;

	@Column(name = "idcard")
	private String idcard;

	@Column(name = "password")
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

	//组织类型
	@ManyToOne(targetEntity = TBaseData.class, fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "organizationtype")
	@NotFound(action = NotFoundAction.IGNORE)
	private TBaseData organizationtype;

	//组织名称
	@Column(name = "organizationname")
	private String organizationname;

	//统一社会信用代码
	@Column(name = "organizationcode")
	private String organizationcode;	

	//注册地址-省份
	@ManyToOne(targetEntity = TBaseData.class, fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "area")
	@NotFound(action = NotFoundAction.IGNORE)
	private TBaseData area;
	
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
	private String loginnum;

	//国籍nationality 
	@Column(name = "nationality")
	private Integer nationality;

	@Column(name = "timelock")
	private Date timeLock;

	@Column(name = "uuidd")
	private String uuid;

	@Transient
	private int col1;//自定义
	@Transient
	private int col2;//自定义
	
	
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
	public TMuser(TBaseData persontype, String idcard, String password, String fullname, String shortname,
			String mobile, String email, TBaseData organizationtype, String organizationname, String organizationcode,
			TBaseData area, String address, String manager, String telephone, String certificate, String dbfrom,
			Date createtime, Date updatetime, String loginnum, Integer nationality, Date timeLock, String uuid) {
		super();
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
		this.uuid = uuid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TBaseData getPersontype() {
		return persontype;
	}

	public void setPersontype(TBaseData persontype) {
		this.persontype = persontype;
	}

	public String getIdcard() {
		return idcard;
	}
	
	public String getIdcardTemp() {
		return "***";
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getPassword() {
		return password;
	}
	
	public String getPasswordTemp() {
		return "***";
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
	
	public String getMobileTemp() {
		return "***";
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}
	
	public String getEmailTemp() {
		return "***";
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public TBaseData getOrganizationtype() {
		return organizationtype;
	}

	public void setOrganizationtype(TBaseData organizationtype) {
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

	public TBaseData getArea() {
		return area;
	}

	public void setArea(TBaseData area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}
	
	public String getAddressTemp() {
		return "***";
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
	
	public String getTelephoneTemp() {
		return "***";
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

	public String getLoginnum() {
		return loginnum;
	}

	public void setLoginnum(String loginnum) {
		this.loginnum = loginnum;
	}

	public Integer getNationality() {
		return nationality;
	}

	public void setNationality(Integer nationality) {
		this.nationality = nationality;
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

	

}
