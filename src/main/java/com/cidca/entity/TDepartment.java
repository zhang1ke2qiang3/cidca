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

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "t_department2")
public class TDepartment implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "deptid")
	private Integer deptid;

	@Column(name = "deptname")
	private String deptname;

	//部门归属 wmt 20200415
	@ManyToOne(targetEntity = TDepartmentType.class, fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "typee")
	@NotFound(action = NotFoundAction.IGNORE)
	private TDepartmentType typee;

	//分类
	@Column(name = "classification")
	private String classification;
	
	@Column(name = "remark")
	private String remark;

	//排序用--20180801
	@Column(name = "orderr")
	private Integer order;

	@GeneratedValue(strategy =GenerationType.IDENTITY)
	public int getDeptid() {
		return deptid;
	}
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	public void setDeptid(int deptid) {
		this.deptid = deptid;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public TDepartmentType getTypee() {
		return typee;
	}
	public void setTypee(TDepartmentType typee) {
		this.typee = typee;
	}
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}


}
