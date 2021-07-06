package com.cidca.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cidca.entity.TMuser;
import com.cidca.entity.TUser;
import com.cidca.system.dao.MuserDao;
import com.cidca.system.dao.UserDao;
import com.cidca.system.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	@Autowired
	private MuserDao muserdao;
	
	@Override
	@Transactional
	public TUser insert(TUser vo) throws Exception {
		TUser respVo = null;
		respVo = userDao.save(vo);
		return respVo;
	}

	@Override
	@Transactional
	public int update(String password, String userName) {		
		// 在UserDao新增update方法,手写sql
		int count = userDao.update(password, userName);
		return count;
	}

	@Override
	public List<TUser> findById(List<String> ids) {
		List<TUser> respList = new ArrayList<TUser>();
		respList = userDao.findAllById(ids);
		return respList;
	}

	@Override
	public void deleteById(String userid) throws Exception{
		userDao.deleteById(userid);
	}
	
	@Transactional
	@Override
	public TUser insertUser(TUser user) {
		return userDao.save(user);
	}

	@Override
	@Transactional
	public void updateUser(TUser TUser) {
		userDao.save(TUser);
	}

	@Override
	public List<TUser> findAll() {
		return userDao.findAll();
	}
	
	@Override
	public TUser findByUserid(String userid) throws Exception {
		return userDao.findByUserid(userid);
	}
	
	@Override
	public List<TUser> findByUseridAndPassword(String userid, String password) {
		return userDao.findByUseridAndPassword(userid, password);
	}

	//----------------------------------
	@Override
	public TMuser insert(TMuser vo) throws Exception {
		TMuser respVo = null;
		respVo = muserdao.save(vo);
		return respVo;
	}

	@Override
	public void updateTMuser(String idcard) {
		muserdao.update(idcard);
	}
	
	@Override
	public void updateTMuser(TMuser muser) {
		muserdao.save(muser);
	}

	@Override
	public void deleteTMuserById(int id) throws Exception {
		muserdao.deleteById(id);
	}
	
	@Override
	public List<TMuser> findTMuserById(List<Integer> ids) {
		List<TMuser> muser = new ArrayList<TMuser>();
		muser = muserdao.findAllById(ids);
		return muser;
	}

	@Override
	public TMuser findByIdcard(String idcard) {
		return muserdao.findByIdcard(idcard);
	}

	
	@Override
	public TMuser findByMobile(String mobile) {
		return muserdao.findByMobile(mobile);
	}

	@Override
	public List<TMuser> findByIdcardAndPassword(String idcard, String password) {
		return muserdao.findByIdcardAndPassword(idcard, password);
	}


}
