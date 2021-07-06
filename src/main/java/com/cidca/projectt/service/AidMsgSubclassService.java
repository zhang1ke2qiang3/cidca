package com.cidca.projectt.service;

import java.util.List;

import com.cidca.entity.AidMsgSubclass;

public interface AidMsgSubclassService {

	public AidMsgSubclass insert(AidMsgSubclass vo) throws Exception;
	
	public int update(String password, String userName);
	
	public List<AidMsgSubclass> findById(List<AidMsgSubclass> userids);
	
	public void deleteById(String userid) throws Exception;
	
	public AidMsgSubclass insertAidMsg(AidMsgSubclass vo);
	
	public void updateAidMsg(AidMsgSubclass vo);
	
	public List<AidMsgSubclass> findAll();
	
	//public AidMsgSubclass findByUserid(String userid) throws Exception;
	
	//public List<AidMsgSubclass> findByUseridAndPassword(String userid,String password);
}
