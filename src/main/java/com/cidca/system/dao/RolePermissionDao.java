package com.cidca.system.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cidca.entity.TRolePermission;

public interface RolePermissionDao extends JpaRepository<TRolePermission, Integer>,JpaSpecificationExecutor<TRolePermission>{
	
	@Query(nativeQuery = true, value = "select roleid,permission_code from t_role_permission  where roleid=:roleid")
	public List<Object> findPermissionByRoleid(@Param("roleid") Integer roleid);
}
