package com.cidca.system.service;

import java.util.List;

import com.cidca.entity.TAuditLog;
import com.cidca.entity.TMenu;
import com.cidca.entity.TPermission;
import com.cidca.entity.TRolePermission;
import java.util.Map;

public interface SystemService {

	public TAuditLog save(TAuditLog vo) throws Exception;
	
	public List<TAuditLog> findById(List<Integer> ids);
	
	public void deleteTAuditLogById(int id) throws Exception;
	
	public List<TAuditLog> findAll();
	
	public List<TAuditLog> findAllByUserid(String userid);
	
	List<TPermission> getPermessionByUserid(String code,String userid);
	
	public List<TRolePermission> findPermissionByRoleid(Integer roleid);

	public List<Map> getMenuList(String idcard);
}
