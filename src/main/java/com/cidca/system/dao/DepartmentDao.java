package com.cidca.system.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.cidca.entity.AidMsgSubclass;
import com.cidca.entity.TDepartment;

public interface DepartmentDao extends JpaRepository<TDepartment,Integer> ,JpaSpecificationExecutor<AidMsgSubclass>{

}
