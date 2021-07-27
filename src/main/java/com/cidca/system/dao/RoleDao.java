package com.cidca.system.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.cidca.entity.TRole;

public interface RoleDao extends JpaRepository<TRole, Integer>,JpaSpecificationExecutor<TRole>{

}
