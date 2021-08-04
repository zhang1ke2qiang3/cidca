package com.cidca.system.service.impl;

import java.util.List;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cidca.entity.TBusiLog;
import com.cidca.system.dao.BusiLogDao;
import com.cidca.system.service.BusiLogService;

@DynamicInsert
@DynamicUpdate
@Transactional
@Service
public class BusiLogServiceImpl implements BusiLogService {

	@Autowired
	private BusiLogDao busilogDao;

	@Override
	public TBusiLog insert(TBusiLog vo) throws Exception {
		return busilogDao.save(vo);
	}

	public TBusiLog findById(String uuid) {
		return busilogDao.findById(uuid).orElse(null);
	}
	
	@Override
	public void deleteById(String uuid) throws Exception {
		busilogDao.deleteById(uuid);
	}

	@Override
	public void updateTBusiLog(TBusiLog vo) {
		busilogDao.save(vo);
	}

	@Override
	public List<TBusiLog> findAll() {
		return busilogDao.findAll();
	}

	@Override
	public List<TBusiLog> findByBusitypeAndBusiid(String busitype,String busiid) throws Exception {
		return busilogDao.findByBusitypeAndBusiid(busitype,busiid);
	}

	@Override
	public Page<TBusiLog> findAll(Specification<TBusiLog> spec, Pageable pageable) {
		return busilogDao.findAll(spec, pageable);
	}
	

}
