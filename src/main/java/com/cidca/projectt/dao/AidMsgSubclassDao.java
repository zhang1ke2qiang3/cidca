package com.cidca.projectt.dao;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cidca.entity.AidMsg;
import com.cidca.entity.AidMsgSubclass;
	
@Repository
public interface AidMsgSubclassDao extends JpaRepository<AidMsgSubclass, String>,JpaSpecificationExecutor<AidMsgSubclass>{
	
	// 手写根据user_name修改password的方法
	@Query(value = "update AidMsgSubclass set recipient_content = ?1 where aidmsg_id = ?2")
	@Modifying
    @Transactional
	public int update(String project_name);
	
	@Modifying
    @Transactional
	@Query(value = "select u from AidMsgSubclass u where aidmsgid = ?1")
	public List<AidMsgSubclass> findAllByAidmsgid(@Param("aidmsgid")String aidmsg_id);
	
	@Modifying
	@Transactional
	@Query(value = "select u from AidMsgSubclass u where aidmsgid = ?1 and yearr=?2")
	public List<AidMsgSubclass> findAllByAidmsgidAndYear(@Param("aidmsgid")String aidmsg_id,@Param("yearr")String yearr);
	
	
	@Modifying
	@Transactional
	@Query(value = "select u from AidMsgSubclass u where yearr = ?1 and project_name=?2")
	public List<AidMsgSubclass> findByYearAndProject_name(@Param("yearr")String yearr,@Param("project_name")String project_name);
	
	@Modifying
	@Transactional
	@Query(value = "select u from AidMsgSubclass u where create_user = ?1 and project_name=?2")
	public List<AidMsgSubclass> findByCreateUserAndProject_name(@Param("create_user")String createUser,@Param("project_name")String project_name);

	@Transactional
	@Query(value = "from AidMsgSubclass u where statee = '1'")
	List<AidMsgSubclass>findByStaee();
}
