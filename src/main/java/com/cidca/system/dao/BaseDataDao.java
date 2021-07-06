package com.cidca.system.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.cidca.entity.AidMsgSubclass;
import com.cidca.entity.TBaseData;
	
@Repository
public interface BaseDataDao extends JpaRepository<TBaseData, Integer> ,JpaSpecificationExecutor<AidMsgSubclass>{
	
}
