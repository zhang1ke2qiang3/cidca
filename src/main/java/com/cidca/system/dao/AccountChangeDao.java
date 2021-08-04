package com.cidca.system.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cidca.entity.TAccountChange;

public interface AccountChangeDao extends JpaRepository<TAccountChange, String>,JpaSpecificationExecutor<TAccountChange>{

	@Query(value = "update TAccountChange set idcard = ?1")
	@Modifying
	@Transactional
	public void update(String idcard);

	@Modifying
	@Transactional
	@Query(value = "select u from TAccountChange u where idcard = ?1")
	public List<TAccountChange> findByIdcard(@Param("idcard")String idcard);

	@Modifying
	@Transactional
	@Query(value = "select u from TAccountChange u where mobile = ?1")
	public List<TAccountChange> findByMobile(@Param("mobile") String mobile);

	@Modifying
	@Transactional
	@Query(value = "select u from TAccountChange u where u.idcard = ?1 and u.state = ?2")
	public List<TAccountChange> findMuserChangeHistoryByIdcardAndState(@Param("idcard")String idcard, @Param("state")String state);

}
