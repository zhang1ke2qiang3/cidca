package com.cidca.system.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cidca.entity.TPermission;

public interface PermissionDao extends JpaRepository<TPermission, String>,JpaSpecificationExecutor<TPermission>{

	@Query(nativeQuery = true, value = "SELECT DISTINCT d.code,d.name,d.patternurl,d.levell,d.parentcode,d.orders "
			+ " FROM t_user_role b,t_role_permission c,t_permission d "
			+ " WHERE b.roleid = c.roleid "
			+ " AND c.permission_code = d.code "
			+ " AND b.roleid = c.roleid "
			+ " AND c.permission_code = d.code AND d.parentcode=:code AND b.userid =:userid")
	public List<Object> findPermissionByUseridAndCode(@Param("code") String code,@Param("userid")String userid);

}
