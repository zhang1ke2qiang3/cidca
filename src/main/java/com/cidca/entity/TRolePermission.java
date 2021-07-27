package com.cidca.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_role_permission")
public class TRolePermission implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name="roleid")
	private int roleid;

	@Column(name="permission_code")
	private String permission_code;

	public TRolePermission() {}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRoleid() {
		return roleid;
	}

	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getPermission_code() {
		return permission_code;
	}


	public void setPermission_code(String permission_code) {
		this.permission_code = permission_code;
	}







}
