package com.cidca.system.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.cidca.entity.TBaseData;

public interface BasedataService {

	public TBaseData insert(TBaseData vo) throws Exception;
	
	public TBaseData findById(Integer id);
	
	public void deleteById(Integer id) throws Exception;
	
	public void updateTBaseData(TBaseData vo);
	
	public List<TBaseData> findAll();
	
	public List<TBaseData> findByTypes(String types) throws Exception;
	
	public TBaseData findByCode(Integer code) throws Exception;
	
	public List<TBaseData> findByTypesAndName(String types,String name) throws Exception;
	
	public Page<TBaseData> findAll(Specification<TBaseData> spec, Pageable pageable);
	
	

}
