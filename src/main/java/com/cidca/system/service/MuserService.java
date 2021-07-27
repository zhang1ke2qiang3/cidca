package com.cidca.system.service;

import java.util.List;

import com.cidca.entity.TMuser;

public interface MuserService {

public List<TMuser> findAll();
	
	public TMuser saveTMuser(TMuser vo) throws Exception;

	public List<TMuser> findTMuserById(List<String> ids);
	
	public void deleteTMuser(TMuser vo) throws Exception;
	
	public void updateTMuser(String idcard);
	
	public void updateTMuser(TMuser vo);
	
	public TMuser findByIdcard(String idcard);
	
	public List<TMuser> findByMobile(String mobile);
	
	public  List<TMuser> findByIdcardAndPassword(String idcard,String password);
	
	

}
