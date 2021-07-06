package com.cidca.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cidca.entity.TUserRole;
import com.cidca.system.dao.UserRoleDao;
import com.cidca.system.service.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService{

	@Autowired
	private UserRoleDao userRoleDao;

	@Override
	public TUserRole insert(TUserRole vo) throws Exception {
		return userRoleDao.save(vo);
	}

	@Override
	public List<TUserRole> findById(List<Integer> ids) {
		return userRoleDao.findAllById(ids);
	}

	@Override
	public void deleteById(int id) throws Exception {
		userRoleDao.deleteById(id);
	}

	@Override
	public List<TUserRole> findAll() {
		return userRoleDao.findAll();
	}

	@Override
	public List<TUserRole> findAllByUserid(String userid) {
		return  userRoleDao.findAllByUserid(userid);
	}
	



	

}
