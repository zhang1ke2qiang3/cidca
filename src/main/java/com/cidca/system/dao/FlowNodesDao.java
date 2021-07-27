package com.cidca.system.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.cidca.entity.TFlowNodes;

public interface FlowNodesDao extends JpaRepository<TFlowNodes, Integer>,JpaSpecificationExecutor<TFlowNodes>{

}
