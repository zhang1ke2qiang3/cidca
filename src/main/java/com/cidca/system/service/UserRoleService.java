package com.cidca.system.service;

import java.util.List;

import com.cidca.entity.TUserRole;

public interface UserRoleService {

	public TUserRole insert(TUserRole vo) throws Exception;
	
	public List<TUserRole> findById(List<Integer> ids);
	
	public void deleteById(int id) throws Exception;
	
	public List<TUserRole> findAll();
	
	public List<TUserRole> findAllByUserid(String userid);
	
}
