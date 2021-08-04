package com.cidca.system.dao;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import com.cidca.entity.TUser;
	
@Repository
public interface UserDao extends JpaRepository<TUser, String> ,JpaSpecificationExecutor<TUser>{
	
	@Query(value = "update TUser set password = ?1 where username = ?2")
	@Modifying
    @Transactional
	public int update(String password, String userName);
	
	public TUser findByUserid(String userid);
	
	@Modifying
    @Transactional
	@Query(value = "select u from TUser u where userid = ?1 and password = ?2")
	public List<TUser> findByUseridAndPassword(@Param("userid")String userid, @Param("password")String password);




}
