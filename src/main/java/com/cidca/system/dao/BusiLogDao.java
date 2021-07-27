package com.cidca.system.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cidca.entity.TBusiLog;

public interface BusiLogDao extends JpaRepository<TBusiLog, String>,JpaSpecificationExecutor<TBusiLog>{

	@Modifying
    @Transactional
	@Query(value = "select u from TBusiLog u where busitype = ?1 and busiid=?2")
	public List<TBusiLog> findByBusitypeAndBusiid(@Param("busitype")String busitype,@Param("busiid")String busiid);
	

}
