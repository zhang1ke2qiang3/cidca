package com.cidca.system.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.cidca.entity.TBusiLog;

public interface BusiLogService {

	public TBusiLog insert(TBusiLog vo) throws Exception;

	public TBusiLog findById(String uuid);

	public void deleteById(String uuid) throws Exception;

	public void updateTBusiLog(TBusiLog vo);

	public List<TBusiLog> findAll();

	public List<TBusiLog> findByBusitypeAndBusiid(String busitype,String busiid) throws Exception;

	public Page<TBusiLog> findAll(Specification<TBusiLog> spec, Pageable pageable);
}
