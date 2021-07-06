package com.cidca.entity;

import java.io.Serializable;

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

/**
 * @author 陈庆钊
 * @Email parkme210@hotmail.com
 * @创建时间 2015-12-16 下午4:46:05
 */
@Entity
@Table(name = "t_flow_nodes")
public class TFlowNodes implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "flowid")
	private Integer flowid;

	@Column(name = "nodeno")
	private Integer nodeno;

	@Column(name = "nodenames")
	private String nodenames;

	@ManyToOne(targetEntity = TRole.class, fetch = FetchType.EAGER,optional=true)
	@JoinColumn(name = "roleid")
	private TRole role;

	@Column(name = "userid")
	private String userid;

	@Transient
	private Integer tempid;

	@Transient
	private String username;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFlowid() {
		return flowid;
	}

	public void setFlowid(Integer flowid) {
		this.flowid = flowid;
	}

	public Integer getNodeno() {
		return nodeno;
	}

	public void setNodeno(Integer nodeno) {
		this.nodeno = nodeno;
	}

	public String getNodenames() {
		return nodenames;
	}

	public void setNodenames(String nodenames) {
		this.nodenames = nodenames;
	}

	public TRole getRole() {
		return role;
	}

	public void setRole(TRole role) {
		this.role = role;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Integer getTempid() {
		return tempid;
	}

	public void setTempid(Integer tempid) {
		this.tempid = tempid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


}
