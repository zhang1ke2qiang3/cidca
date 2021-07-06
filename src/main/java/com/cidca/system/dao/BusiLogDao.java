package com.cidca.system.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.cidca.entity.AidMsgSubclass;
import com.cidca.entity.TBusiLog;

public interface BusiLogDao extends JpaRepository<TBusiLog, Integer>,JpaSpecificationExecutor<AidMsgSubclass>{

}
