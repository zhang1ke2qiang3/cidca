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
	
@Repository
public interface AidMsgDao extends JpaRepository<AidMsg, String>,JpaSpecificationExecutor<AidMsg>{
	
	// 手写根据user_name修改password的方法
	@Query(value = "update AidMsg set project_name = ?1 where project_name = ?2")
	@Modifying
    @Transactional
	public int update(String project_name);
	
	@Modifying
    @Transactional
	@Query(value = "select u from AidMsg u where project_name = ?1")
	public List<AidMsg> findByProject_name(@Param("project_name")String project_name);
	
	
}
