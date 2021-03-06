package com.cidca.system.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.cidca.entity.TFlowWork;

public interface FlowWorkDao extends JpaRepository<TFlowWork, Integer>,JpaSpecificationExecutor<TFlowWork>{

}
