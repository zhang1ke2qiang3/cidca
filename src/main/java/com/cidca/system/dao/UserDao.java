package com.cidca.system.dao;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cidca.entity.AidMsgSubclass;
import com.cidca.entity.TUser;
	
@Repository
public interface UserDao extends JpaRepository<TUser, String> ,JpaSpecificationExecutor<AidMsgSubclass>{
	
	// 手写根据user_name修改password的方法
	@Query(value = "update TUser set password = ?1 where username = ?2")
	@Modifying
    @Transactional
	public int update(String password, String userName);
	
	public TUser findByUserid(String userid);
	
	@Modifying
    @Transactional
	@Query(value = "select u from TUser u where userid = ?1 and password = ?2")
	public List<TUser> findByUseridAndPassword(@Param("userid")String userid, @Param("password")String password);
	
//	@Modifying
//    @Transactional
//	@Query(value = "select u.password from TUser u where userid = ?1 and password = ?2")
//	public TUser findByUseridAndPassword(@Param("userid")String userid, @Param("password")String password);
	
}
