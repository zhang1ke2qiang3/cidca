package com.cidca.system.dao;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cidca.entity.TAuditLog;
	
@Repository
public interface AuditLogDao extends JpaRepository<TAuditLog, Integer> ,JpaSpecificationExecutor<TAuditLog>{
	
	@Query(value = "select u.userid from TAuditLog u where userid = ?1")
	@Modifying
    @Transactional
	List<TAuditLog> findAllByUserid(String userid);
}
