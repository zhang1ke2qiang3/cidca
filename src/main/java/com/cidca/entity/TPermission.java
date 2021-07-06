package com.cidca.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "t_permission")
public class TPermission implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "patternurl")
	private String patternurl;

	@Column(name = "levell")
	private String level;

	@Column(name = "parentcode")
	private String parentcode;

	@Column(name = "orders")
	private int orders;

	@Transient
	private List<TPermission> subList = new ArrayList<TPermission>();

	@Transient
	private String ischeck;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPatternurl() {
		return patternurl;
	}

	public void setPatternurl(String patternurl) {
		this.patternurl = patternurl;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getParentcode() {
		return parentcode;
	}

	public void setParentcode(String parentcode) {
		this.parentcode = parentcode;
	}

	public int getOrders() {
		return orders;
	}

	public void setOrders(int orders) {
		this.orders = orders;
	}

	public List<TPermission> getSubList() {
		return subList;
	}

	public void setSubList(List<TPermission> subList) {
		this.subList = subList;
	}

	public String getIscheck() {
		return ischeck;
	}

	public void setIscheck(String ischeck) {
		this.ischeck = ischeck;
	}

}
