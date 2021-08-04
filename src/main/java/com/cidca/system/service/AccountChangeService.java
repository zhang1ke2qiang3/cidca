package com.cidca.system.service;

import java.util.List;

import com.cidca.entity.TAccountChange;

public interface AccountChangeService {

	public List<TAccountChange> findAll();

	public TAccountChange saveAccountChange(TAccountChange vo) throws Exception;

	public void updateAccountChange(String idcard);

	public void updateAccountChange(TAccountChange vo);

	public void deleteAccountChange(TAccountChange vo) throws Exception;

	public List<TAccountChange> findAccountChangeById(List<String> ids);

	public List<TAccountChange> findAccountChangeByIdcard(String idcard);

	public List<TAccountChange> findAccountChangeByMobile(String mobile);

	public List<TAccountChange> findAccountChangeByIdcardAndState(String idcard,String state);



}
