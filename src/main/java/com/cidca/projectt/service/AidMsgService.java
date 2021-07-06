package com.cidca.projectt.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.cidca.entity.AidMsg;

public interface AidMsgService {

	public AidMsg insert(AidMsg vo) throws Exception;
	
	public List<AidMsg> findById(List<String> ids);
	
	public void deleteById(String userid) throws Exception;
	
	public AidMsg insertAidMsg(AidMsg vo);
	
	public void updateAidMsg(AidMsg vo);
	
	public List<AidMsg> findAll();
	
	public List<AidMsg> findByProject_name(String project_name) throws Exception;

	public int update(String project_name);
	
	public Page<AidMsg> findAll(Specification<AidMsg> spec, Pageable pageable);

}
