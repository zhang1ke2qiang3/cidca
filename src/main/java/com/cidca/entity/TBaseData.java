package com.cidca.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

@Entity
@Table(name = "t_base_data")
public class TBaseData implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "code")
	private Integer code;

	@Column(name = "name")
	private String name;

	@Column(name = "types")
	private String types;

	@Column(name = "pcode")
	private Integer pcode;

	@Column(name = "shortname")
	private String shortName;
	
	@Column(name = "sx")
	private Integer sx;
	
	@Column(name = "remark")
	private String remark;
	
	public TBaseData() {
		super();
	}
	
	public TBaseData(String types) {
		super();
		this.types = types;
	}
	
	public TBaseData(int code, String name, String types, String pcode) throws ClassCastException{
		super();
		this.code= code;
		this.name = name;
		if (!StringUtils.isEmpty(types)) {this.types = types;}
		if (!StringUtils.isEmpty(pcode)) {this.pcode = Integer.parseInt(pcode);}
	}
	
	
	@Override
	public String toString() {
		return "TBaseData [code=" + code + ", name=" + name + ", types=" + types + ", pcode=" + pcode + "]";
	}

	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTypes() {
		return types;
	}
	public void setTypes(String types) {
		this.types = types;
	}
	public Integer getPcode() {
		return pcode;
	}
	public void setPcode(Integer pcode) {
		this.pcode = pcode;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public Integer getSx() {
		return sx;
	}
	public void setSx(Integer sx) {
		this.sx = sx;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TBaseData) {
			TBaseData other = (TBaseData) obj;
			return this.types.equals(other.getTypes());
		}
		return false;
	}
	
	
}
