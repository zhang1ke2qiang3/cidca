package com.cidca.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "t_busi_log")
public class TBusiLog implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "uuid")
	private String uuid;

	//业务名称/模块名称
	@Column(name = "busitype")
	private String busitype;
	
	//业务表名称
	@Column(name = "busitable")
	private String busitable;
	
	//业务ID
	@Column(name = "busiid")
	private String busiid;

	//操作人
	@Column(name = "operuser")
	private String operuser;
	
	//4phase 操作人角色 
	@Column(name = "operuser_role")
	private Integer operuserRole;
		
	//操作内容
	@Column(name = "opercontent")
	private String opercontent;
	
	//操作时间
	@Column(name = "opertime")
	private Date opertime;
	
	//备注
	@Column(name = "remark")
	private String remark;
	
	@Transient
	private int index;//当前日志自定义下标
	@Transient
	private String nodeName;//当前日志自定义名称
	@Transient
	private int x_axis;//节点的横向坐标
	@Transient
	private int y_axis;//节点的纵向坐标
	@Transient
	private int source;//链接线：源坐标
	@Transient
	private int target;//链接线：目标坐标
	
	public TBusiLog() {
		super();
	}

	/**业务名称（如资格处置）、业务表的名称、业务表ID、操作人、操作内容、操作时间、备注**/
	public TBusiLog(String uuid,String busitype, String busitable, String busiid, String operuser,String opercontent,
			Date opertime, String remark) {
		super();
		this.uuid = uuid;
		this.busitype = busitype;
		this.busitable = busitable;
		this.busiid = busiid;
		this.operuser = operuser;
		this.opercontent = opercontent;
		this.opertime = opertime;
		this.remark = remark;
	}
	
	public String getBusitype() {
		return busitype;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setBusitype(String busitype) {
		this.busitype = busitype;
	}

	public String getBusitable() {
		return busitable;
	}

	public void setBusitable(String busitable) {
		this.busitable = busitable;
	}

	public String getBusiid() {
		return busiid;
	}

	public void setBusiid(String busiid) {
		this.busiid = busiid;
	}

	public String getOpercontent() {
		return opercontent;
	}

	public void setOpercontent(String opercontent) {
		this.opercontent = opercontent;
	}

	public String getOperuser() {
		return operuser;
	}

	public void setOperuser(String operuser) {
		this.operuser = operuser;
	}

	public Date getOpertime() {
		return opertime;
	}

	public void setOpertime(Date opertime) {
		this.opertime = opertime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getOperuserRole() {
		return operuserRole;
	}

	public void setOperuserRole(Integer operuserRole) {
		this.operuserRole = operuserRole;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public int getX_axis() {
		return x_axis;
	}

	public void setX_axis(int x_axis) {
		this.x_axis = x_axis;
	}

	public int getY_axis() {
		return y_axis;
	}

	public void setY_axis(int y_axis) {
		this.y_axis = y_axis;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public int getTarget() {
		return target;
	}

	public void setTarget(int target) {
		this.target = target;
	}

	
	
}
