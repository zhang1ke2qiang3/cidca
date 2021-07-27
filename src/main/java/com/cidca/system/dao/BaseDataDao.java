package com.cidca.system.dao;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cidca.entity.TBaseData;
	
@Repository
public interface BaseDataDao extends JpaRepository<TBaseData, Integer> ,JpaSpecificationExecutor<TBaseData>{
	
	@Modifying
    @Transactional
	@Query(value = "select u from TBaseData u where types = ?1 and name like %?2%")
	public List<TBaseData> findByTypesAndName(@Param("types")String types, @Param("name")String name);
	
	@Modifying
	@Transactional
	@Query(value = "select u from TBaseData u where types = ?1")
	public List<TBaseData> findByTypes(@Param("types")String types);
}
