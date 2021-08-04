package com.cidca.projectt.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.cidca.entity.AidMsgSubclass;

public interface AidMsgSubclassService {

	public AidMsgSubclass save(AidMsgSubclass vo) throws Exception;

	public int update(String project_name);

	public AidMsgSubclass findById(String id);

	public void deleteById(String id) throws Exception;

	public void updateAidMsg(AidMsgSubclass vo);

	public List<AidMsgSubclass> findAll();

	public Page<AidMsgSubclass> findAll(Specification<AidMsgSubclass> spec, Pageable pageable);

	public List<AidMsgSubclass> findAllByAidmsgid(String aidmsgid);
	
	public List<AidMsgSubclass> findAllByAidmsgidAndYear(String aidmsgid,String yearr);

	public List<AidMsgSubclass> findByYearAndProject_name(String yearr,String project_name) throws Exception;
	
	/**
	 * 
	 * @param createUser 人员表里的idcard
	 * @param project_name
	 * @return
	 * @throws Exception
	 */
	public List<AidMsgSubclass> findByCreateUserAndProject_name(String createUser,String project_name) throws Exception;
	
}
