package com.cidca.system.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cidca.entity.TUserRole;


public interface UserRoleDao extends JpaRepository<TUserRole, Integer> ,JpaSpecificationExecutor<TUserRole>{

	@Query(value = "select u.userid from TUserRole u where userid = ?1")
	@Modifying
    @Transactional
	List<TUserRole> findAllByUserid(String userid);

}
