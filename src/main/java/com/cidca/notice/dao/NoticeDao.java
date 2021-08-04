package com.cidca.notice.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cidca.entity.Tnotice;
public interface NoticeDao extends JpaRepository<Tnotice, String>,JpaSpecificationExecutor<Tnotice>{

	@Modifying
	@Transactional
	@Query(value = "select u from Tnotice u where title like %?1%")
	public List<Tnotice> findByTitle(@Param("title")String title);
	@Modifying
	@Transactional
	@Query(value = "select u from Tnotice u where createUser  = ?1")
	public List<Tnotice> findByCreateUser(@Param("createUser")String createUser);

}
