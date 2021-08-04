package com.cidca.system.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cidca.entity.TEnterpriseChange;

public interface EnterpriseChangeDao extends JpaRepository<TEnterpriseChange, String>,JpaSpecificationExecutor<TEnterpriseChange>{
	
		@Query(value = "update TEnterpriseChange set idcard = ?1")
		@Modifying
	    @Transactional
		public void update(String idcard);
		
		@Modifying
	    @Transactional
		@Query(value = "select u from TEnterpriseChange u where idcard = ?1")
		public List<TEnterpriseChange> findByIdcard(@Param("idcard")String idcard);
		
		@Modifying
	    @Transactional
		@Query(value = "select u from TEnterpriseChange u where mobile = ?1")
		public List<TEnterpriseChange> findByMobile(@Param("mobile") String mobile);
		
		@Modifying
	    @Transactional
		@Query(value = "select u from TEnterpriseChange u where u.idcard = ?1 and u.state = ?2")
		public List<TEnterpriseChange> findEnterpriseChangeByIdcardAndState(@Param("idcard")String idcard, @Param("state")String state);
		
}
