package com.cidca.system.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.cidca.entity.*;
import com.cidca.system.dao.*;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cidca.system.service.SystemService;

@DynamicInsert
@DynamicUpdate
@Transactional
@Service
public class SystemServiceImpl implements SystemService {

	@Autowired
	private AuditLogDao auditlogdao;

	@Autowired
	private PermissionDao permissiondao;
	
	@Autowired
	private RolePermissionDao rolePermissionDao;

	@Autowired
	MenuDao menuDao;

	@Autowired
	RoleDao roleDao;
	@Autowired
	MuserDao muserDao;

	@Override
	public TAuditLog save(TAuditLog vo) throws Exception {
		return auditlogdao.save(vo);
	}

	@Override
	public List<TAuditLog> findById(List<Integer> ids) {
		return auditlogdao.findAllById(ids);
	}

	@Override
	public void deleteTAuditLogById(int id) throws Exception {
		auditlogdao.deleteById(id);
	}

	@Override
	public List<TAuditLog> findAll() {
		return auditlogdao.findAll();
	}

	@Override
	public List<TAuditLog> findAllByUserid(String userid) {
		return auditlogdao.findAllByUserid(userid);
	}

	@Override
	public List<TPermission> getPermessionByUserid(String code, String userid) {
		List<Object> list = permissiondao.findPermissionByUseridAndCode(code, userid);
		if (CollectionUtils.isEmpty(list)) {
			return Collections.emptyList();
		}
		return list.stream().map(row -> {
			Object[] rowArray = (Object[]) row;
			TPermission obj = new TPermission();
			obj.setCode((String) rowArray[0]);
			obj.setName((String) rowArray[1]);
			obj.setPatternurl((String) rowArray[2]);
			obj.setLevel((String) rowArray[3]);
			obj.setParentcode((String) rowArray[4]);
			obj.setOrders((Integer) rowArray[5]);
			return obj;
		}).collect(Collectors.toList());
	}
	
	@Override
	public List<TRolePermission> findPermissionByRoleid(Integer roleid) {
		List<Object> list = rolePermissionDao.findPermissionByRoleid(roleid);
		if (CollectionUtils.isEmpty(list)) {
			return Collections.emptyList();
		}
		return list.stream().map(row -> {
			Object[] rowArray = (Object[]) row;
			TRolePermission obj = new TRolePermission();
			obj.setRoleid((Integer) rowArray[0]);
			obj.setPermission_code((String) rowArray[1]);
			return obj;
		}).collect(Collectors.toList());
		
	}

	@Override
	public List<Map> getMenuList(String idcard) {
		return menuDao.findByRoleId(idcard);
	}

	@Override
	public List<Map> getMenuByRoleid(Integer roleid) {
		return menuDao.getMenuByRoleid(roleid);
	}

	@Override
	public boolean saveUserRole(String userid, String roleids) {
		if(null != userid || "".equals(userid)){
			if("".equals(roleids)){
				return false;
			}else{
				try{
					roleDao.deleteUserRoleByUserid(userid);
					String[] strArray = roleids.split(",");
					for(int i = 0; i < strArray.length;i++){
						String uuid = UUID.randomUUID().toString().replace("-","");
						roleDao.saveUserRole(userid,strArray[i]);
					}
				}catch (Exception e){
					e.printStackTrace();
					return false;
				}
				return true;
			}
		}else{
			return false;
		}
	}

	@Override
	public void saveUser(TMuser user) {
		String uuid = UUID.randomUUID().toString().replace("-","");
		user.setUuid(uuid);
		muserDao.save(user);
	}

}
