package com.cidca.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cidca.entity.TEnterpriseChange;
import com.cidca.system.dao.EnterpriseChangeDao;
import com.cidca.system.service.EnterpriseChangeService;

@DynamicInsert
@DynamicUpdate
@Transactional
@Service
public class EnterpriseChangeServiceImpl implements EnterpriseChangeService{

	@Autowired
	private EnterpriseChangeDao muserdao;

	//----------------------------------
	@Override
	public List<TEnterpriseChange> findAll() {
		return muserdao.findAll();
	}

	@Override
	public TEnterpriseChange saveEnterpriseChange(TEnterpriseChange vo) throws Exception {
		TEnterpriseChange respVo = null;
		respVo = muserdao.save(vo);
		return respVo;
	}
	@Override
	public void updateEnterpriseChange(String idcard) {
		muserdao.update(idcard);
	}

	@Override
	public void updateEnterpriseChange(TEnterpriseChange obj) {
		muserdao.save(obj);
	}

	@Override
	public void deleteEnterpriseChange(TEnterpriseChange vo) throws Exception {
		muserdao.delete(vo);
	}

	@Override
	public List<TEnterpriseChange> findEnterpriseChangeById(List<String> ids) {
		List<TEnterpriseChange> muser = new ArrayList<TEnterpriseChange>();
		muser = muserdao.findAllById(ids);
		return muser;
	}

	@Override
	public List<TEnterpriseChange> findEnterpriseChangeByIdcard(String idcard) {
		return muserdao.findByIdcard(idcard);
	}


	@Override
	public List<TEnterpriseChange> findEnterpriseChangeByMobile(String mobile) {
		return muserdao.findByMobile(mobile);
	}

	@Override
	public List<TEnterpriseChange> findEnterpriseChangeByIdcardAndState(String idcard, String state) {
		return muserdao.findEnterpriseChangeByIdcardAndState(idcard, state);
	}



}
