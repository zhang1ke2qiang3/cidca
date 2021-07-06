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
	@Query(value = "update AidMsgSubclass set aidmsg_id = ?1 where aidmsg_id = ?2")
	@Modifying
    @Transactional
	public int update(String project_name);
	
	public AidMsgSubclass findByAidmsg_id(String aidmsg_id);
	
	@Modifying
    @Transactional
	@Query(value = "select u from AidMsgSubclass u where aidmsg_id = ?1")
	public List<AidMsg> findByUseridAndPassword(@Param("aidmsg_id")String aidmsg_id);
	
	
}
