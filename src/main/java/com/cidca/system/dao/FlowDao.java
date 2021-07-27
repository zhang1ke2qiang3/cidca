package com.cidca.system.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.cidca.entity.TFlow;

public interface FlowDao extends JpaRepository<TFlow, Integer>,JpaSpecificationExecutor<TFlow>{

}
