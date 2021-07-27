package com.cidca.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cidca.entity.TMuser;
import com.cidca.system.dao.MuserDao;
import com.cidca.system.service.MuserService;

@DynamicInsert
@DynamicUpdate
@Transactional
@Service
public class MuserServiceImpl implements MuserService{

	@Autowired
	private MuserDao muserdao;
	
	//----------------------------------
	@Override
	public TMuser saveTMuser(TMuser vo) throws Exception {
		TMuser respVo = null;
		respVo = muserdao.save(vo);
		return respVo;
	}

	@Override
	public void updateTMuser(String idcard) {
		muserdao.update(idcard);
	}

	@Override
	public void deleteTMuser(TMuser vo) throws Exception {
		muserdao.delete(vo);
	}
	
	@Override
	public List<TMuser> findTMuserById(List<String> ids) {
		List<TMuser> muser = new ArrayList<TMuser>();
		muser = muserdao.findAllById(ids);
		return muser;
	}

	@Override
	public TMuser findByIdcard(String idcard) {
		return muserdao.findByIdcard(idcard);
	}

	
	@Override
	public List<TMuser> findByMobile(String mobile) {
		return muserdao.findByMobile(mobile);
	}

	@Override
	public List<TMuser> findByIdcardAndPassword(String idcard, String password) {
		return muserdao.findByIdcardAndPassword(idcard, password);
	}

	@Override
	public void updateTMuser(TMuser vo) {
		muserdao.save(vo);
		
	}

	@Override
	public List<TMuser> findAll() {
		return muserdao.findAll();
	}


}
