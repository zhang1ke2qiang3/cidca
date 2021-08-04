package com.cidca.system.service;

import java.util.List;

import com.cidca.entity.TEnterpriseChange;

public interface EnterpriseChangeService {

	public List<TEnterpriseChange> findAll();

	public TEnterpriseChange saveEnterpriseChange(TEnterpriseChange vo) throws Exception;

	public void updateEnterpriseChange(String idcard);

	public void updateEnterpriseChange(TEnterpriseChange vo);

	public void deleteEnterpriseChange(TEnterpriseChange vo) throws Exception;

	public List<TEnterpriseChange> findEnterpriseChangeById(List<String> ids);

	public List<TEnterpriseChange> findEnterpriseChangeByIdcard(String idcard);

	public List<TEnterpriseChange> findEnterpriseChangeByMobile(String mobile);

	public List<TEnterpriseChange> findEnterpriseChangeByIdcardAndState(String idcard,String state);



}
