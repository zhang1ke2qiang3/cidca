package com.cidca.projectt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.cidca.entity.AidMsg;
import com.cidca.entity.TUser;
import com.cidca.projectt.dao.AidMsgDao;
import com.cidca.projectt.service.AidMsgService;

@Service
public class AidMsgServiceImpl implements AidMsgService{

	@Autowired
	private AidMsgDao aidMsgDao;


	@Override
	public AidMsg insert(AidMsg vo) throws Exception {
		return aidMsgDao.save(vo);
	}

	@Override
	@Transactional
	public int update(String project_name) {
		int count = aidMsgDao.update(project_name);
		return count;
	}

	@Override
	public List<AidMsg> findById(List<String> ids) {
		List<AidMsg> list = new ArrayList<AidMsg>();
		list=aidMsgDao.findAllById(ids);
		return list;
	}

	@Override
	public void deleteById(String id) throws Exception {
		aidMsgDao.deleteById(id);
	}

	@Transactional
	@Override
	public AidMsg insertAidMsg(AidMsg vo) {
		return aidMsgDao.save(vo);
	}

	@Override
	public void updateAidMsg(AidMsg vo) {
		aidMsgDao.save(vo);
	}

	@Override
	public List<AidMsg> findAll() {
		return aidMsgDao.findAll();
	}

	@Override
	public List<AidMsg> findByProject_name(String project_name) throws Exception {
		return aidMsgDao.findByProject_name(project_name);
	}

	@Override
	public Page<AidMsg> findAll(Specification<AidMsg> spec, Pageable pageable) {
		return aidMsgDao.findAll(spec,pageable);
	}
}
