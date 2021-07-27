package com.cidca.system.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cidca.entity.TMuser;
public interface MuserDao extends JpaRepository<TMuser, String>,JpaSpecificationExecutor<TMuser>{
	
		@Query(value = "update TMuser set idcard = ?1")
		@Modifying
	    @Transactional
		public void update(String idcard);
		
		public TMuser findByIdcard(String idcard);
		
		@Modifying
	    @Transactional
		@Query(value = "select u from TMuser u where mobile = ?1")
		public List<TMuser> findByMobile(String mobile);
		
		@Modifying
	    @Transactional
		@Query(value = "select u from TMuser u where u.idcard = ?1 and u.password = ?2")
		public List<TMuser> findByIdcardAndPassword(@Param("idcard")String idcard, @Param("password")String password);
		
		/**
		 * 这种方法不支持，必须是int或者id 或者返回List
		 */
//		@Modifying
//	    @Transactional
//		@Query(value = "select u from TMuser u where idcard = ?1 and password = ?2")
//		public TMuser findByIdcardAndPasswordUni(@Param("idcard")String userid, @Param("password")String password);
}
