package com.cidca.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@EntityListeners(value = { Tnotice.class })
@Entity
@Table(name = "t_notice")
public class Tnotice implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "uuid")
	private String uuid;

	@Column(name = "title")
	private String title;

	//内容
	@Column(name = "contentt")
	private String content;

	//发布人
	@Column(name = "create_user")
	private String createUser;

	@CreatedDate
	@Column(name = "create_time")
	private Date createTime;

	@LastModifiedDate
	@Column(name = "update_time")
	private Date updateTime;

	//发布时间
	@Column(name = "announce_time")
	private Date announceTime;

	//0：草稿；1：已发布的；2：失效；
	@Column(name = "statee")
	private String state;

	//是否置顶
	@Column(name = "is_top")
	private String isTop;

	@Transient
	private String announceB;
	@Transient
	private String announceE;

	public Tnotice() {
		super();
	}

	public Tnotice(String uuid, String title,String content,String createUser, Date createTime, Date updateTime, Date announceTime,
			String state, String isTop, String announceB, String announceE) {
		super();
		this.uuid = uuid;
		this.title = title;
		this.content = content;
		this.createUser = createUser;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.announceTime = announceTime;
		this.state = state;
		this.isTop = isTop;
		this.announceB = announceB;
		this.announceE = announceE;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getAnnounceTime() {
		return announceTime;
	}

	public void setAnnounceTime(Date announceTime) {
		this.announceTime = announceTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIsTop() {
		return isTop;
	}

	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}

	public String getAnnounceB() {
		return announceB;
	}

	public void setAnnounceB(String announceB) {
		this.announceB = announceB;
	}

	public String getAnnounceE() {
		return announceE;
	}

	public void setAnnounceE(String announceE) {
		this.announceE = announceE;
	}





}
