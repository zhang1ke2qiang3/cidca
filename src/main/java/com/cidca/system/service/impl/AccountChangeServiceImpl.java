package com.cidca.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cidca.entity.TAccountChange;
import com.cidca.system.dao.AccountChangeDao;
import com.cidca.system.service.AccountChangeService;

@DynamicInsert
@DynamicUpdate
@Transactional
@Service
public class AccountChangeServiceImpl implements AccountChangeService{

	@Autowired
	private AccountChangeDao accountChangeDao;

	//----------------------------------
	@Override
	public List<TAccountChange> findAll() {
		return accountChangeDao.findAll();
	}

	@Override
	public TAccountChange saveAccountChange(TAccountChange vo) throws Exception {
		TAccountChange respVo = null;
		respVo = accountChangeDao.save(vo);
		return respVo;
	}
	@Override
	public void updateAccountChange(String idcard) {
		accountChangeDao.update(idcard);
	}

	@Override
	public void updateAccountChange(TAccountChange obj) {
		accountChangeDao.save(obj);
	}

	@Override
	public void deleteAccountChange(TAccountChange vo) throws Exception {
		accountChangeDao.delete(vo);
	}

	@Override
	public List<TAccountChange> findAccountChangeById(List<String> ids) {
		List<TAccountChange> muser = new ArrayList<TAccountChange>();
		muser = accountChangeDao.findAllById(ids);
		return muser;
	}

	@Override
	public List<TAccountChange> findAccountChangeByIdcard(String idcard) {
		return accountChangeDao.findByIdcard(idcard);
	}


	@Override
	public List<TAccountChange> findAccountChangeByMobile(String mobile) {
		return accountChangeDao.findByMobile(mobile);
	}

	@Override
	public List<TAccountChange> findAccountChangeByIdcardAndState(String idcard, String state) {
		return accountChangeDao.findMuserChangeHistoryByIdcardAndState(idcard, state);
	}

}
