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

@Entity
@Table(name = "t_flow_work")
public class TFlowWork implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	//流程节点ID
	@ManyToOne(targetEntity = TFlow.class, fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "flowid")
	private TFlow flowid;

	//业务主表 ID
	@Column(name = "workid")
	private Integer workid;

	//业务主表 表名
	@Column(name = "worktable")
	private String worktable;

	//节点顺序号，目前无实际意义20180709
	@Column(name = "nodeno")
	private Integer nodeno;

	//目前没有用到20180709
	@Column(name = "nodetype")
	private String nodetype;

	//角色信息表中对应的角色roleID
	@ManyToOne(targetEntity = TRole.class, fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "auditrole")
	private TRole auditrole;

	//岗位/部门主任/会签/领导/专家/评审员等,要处理这条数据的人的userid
	@Column(name = "audituser")
	private String audituser;

	//状态
	@ManyToOne(targetEntity = TBaseData.class, fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "auditresult")
	private TBaseData auditresult;

	@Column(name = "auditremark")
	private String auditremark;

	//处理时间
	@Column(name = "audittime")
	private Date audittime;

	//创建时间
	@Column(name = "createtime")
	private Date createtime;

	@Column(name = "auditusername")
	private String auditusername;
	
	//2phase 流程节点名称
	@Column(name = "flowName")
	private String flowName;
	
	//2phase 主表的被操作人 Primary Key name
	@Column(name = "pkname")
	private String pkname;
	
	//加入该流程对应的制度--2017-08-08
	@Transient
	private String territory;
	
	//被操作人:指被晋级、被聘用、被处置、被培养的人或者提交变更的人、请假申请人--2017-08-08
	@Transient
	private String intendedName;
	
	@Transient
	private String otherinfo;
	
	//上一操作人
	@Transient
	private String preoperuser;
	
	public TFlowWork() {
		super();
	}

	
	public TFlowWork(TFlow flowid, Integer workid, String worktable, Integer nodeno, String nodetype, TRole auditrole,
			String audituser, TBaseData auditresult, String auditremark, Date audittime, Date createtime,
			String auditusername, String flowName, String pkname) {
		super(); 
		this.flowid = flowid;
		this.workid = workid;
		this.worktable = worktable;
		this.nodeno = nodeno;
		this.nodetype = nodetype;
		this.auditrole = auditrole;
		this.audituser = audituser;
		this.auditresult = auditresult;
		this.auditremark = auditremark;
		this.audittime = audittime;
		this.createtime = createtime;
		this.auditusername = auditusername;
		this.flowName = flowName;
		this.pkname = pkname;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TFlow getFlowid() {
		return flowid;
	}

	public void setFlowid(TFlow flowid) {
		this.flowid = flowid;
	}

	public Integer getWorkid() {
		return workid;
	}

	public void setWorkid(Integer workid) {
		this.workid = workid;
	}

	public String getWorktable() {
		return worktable;
	}

	public void setWorktable(String worktable) {
		this.worktable = worktable;
	}

	public Integer getNodeno() {
		return nodeno;
	}

	public void setNodeno(Integer nodeno) {
		this.nodeno = nodeno;
	}

	public String getNodetype() {
		return nodetype;
	}

	public void setNodetype(String nodetype) {
		this.nodetype = nodetype;
	}

	public TRole getAuditrole() {
		return auditrole;
	}

	public void setAuditrole(TRole auditrole) {
		this.auditrole = auditrole;
	}

	public String getAudituser() {
		return audituser;
	}

	public void setAudituser(String audituser) {
		this.audituser = audituser;
	}

	public TBaseData getAuditresult() {
		return auditresult;
	}

	/**
	 * 状态
	 * 草稿249； 待审核250； 通过251； 退回321；
	 */
	public void setAuditresult(TBaseData auditresult) {
		this.auditresult = auditresult;
	}

	public String getAuditremark() {
		return auditremark;
	}

	public void setAuditremark(String auditremark) {
		this.auditremark = auditremark;
	}

	public Date getAudittime() {
		return audittime;
	}

	/**
	 * 处理时间
	 */
	public void setAudittime(Date audittime) {
		this.audittime = audittime;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getAuditusername() {
		return auditusername;
	}

	public void setAuditusername(String auditusername) {
		this.auditusername = auditusername;
	}

	public String getTerritory() {
		return territory;
	}

	public void setTerritory(String territory) {
		this.territory = territory;
	}

	public String getIntendedName() {
		return intendedName;
	}

	public void setIntendedName(String intendedName) {
		this.intendedName = intendedName;
	}

	public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	public String getOtherinfo() {
		return otherinfo;
	}

	public void setOtherinfo(String otherinfo) {
		this.otherinfo = otherinfo;
	}

	public String getPreoperuser() {
		return preoperuser;
	}

	public void setPreoperuser(String preoperuser) {
		this.preoperuser = preoperuser;
	}

	public String getPkname() {
		return pkname;
	}

	public void setPkname(String pkname) {
		this.pkname = pkname;
	}
}
