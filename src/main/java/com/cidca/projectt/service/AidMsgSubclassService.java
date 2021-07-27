package com.cidca.projectt.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.cidca.entity.AidMsgSubclass;

public interface AidMsgSubclassService {

	public AidMsgSubclass insert(AidMsgSubclass vo) throws Exception;

	public int update(String project_name);

	public AidMsgSubclass findById(String id);

	public void deleteById(String id) throws Exception;

	public void updateAidMsg(AidMsgSubclass vo);

	public List<AidMsgSubclass> findAll();

	public Page<AidMsgSubclass> findAll(Specification<AidMsgSubclass> spec, Pageable pageable);

	public List<AidMsgSubclass> findAllByAidmsgid(String aidmsgid);
	
	public List<AidMsgSubclass> findAllByAidmsgidAndYear(String aidmsgid,String yearr);

}
