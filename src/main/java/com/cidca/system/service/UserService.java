package com.cidca.system.service;

import java.util.List;

import com.cidca.entity.TMuser;
import com.cidca.entity.TUser;

public interface UserService {

	public TUser insert(TUser vo) throws Exception;
	
	public int update(String password, String userName);
	
	public List<TUser> findById(List<String> userids);
	
	public void deleteById(String userid) throws Exception;
	
	public TUser insertUser(TUser user);
	
	public void updateUser(TUser user);
	
	public List<TUser> findAll();
	
	public TUser findByUserid(String userid) throws Exception;
	
	public List<TUser> findByUseridAndPassword(String userid,String password);
	
	
	public TMuser insert(TMuser vo) throws Exception;

	public List<TMuser> findTMuserById(List<Integer> ids);
	
	public void deleteTMuserById(int id) throws Exception;
	
	public void updateTMuser(String idcard);
	
	public void updateTMuser(TMuser muser);
	
	public TMuser findByIdcard(String idcard);
	
	public TMuser findByMobile(String mobile);
	
	public  List<TMuser> findByIdcardAndPassword(String idcard,String password);

}
