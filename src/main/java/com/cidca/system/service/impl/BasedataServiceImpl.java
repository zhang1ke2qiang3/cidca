package com.cidca.system.service.impl;

import java.util.List;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cidca.entity.TBaseData;
import com.cidca.system.dao.BaseDataDao;
import com.cidca.system.service.BasedataService;


@DynamicInsert
@DynamicUpdate
@Transactional
@Service
public class BasedataServiceImpl implements BasedataService {

	@Autowired
	private BaseDataDao basedataDao;

	public TBaseData insert(TBaseData vo) throws Exception {
		return basedataDao.save(vo);
	}

	public List<TBaseData> findAll(TBaseData bean) {
		return basedataDao.findAll(Example.of(bean));
	}

	@Override
	public TBaseData findById(Integer id) {
		return basedataDao.findById(id).orElse(null);
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		basedataDao.deleteById(id);
	}

	public void updateTBaseData(TBaseData vo) {
		basedataDao.save(vo);
	}

	public List<TBaseData> findAll() {
		return basedataDao.findAll();
	}

	public List<TBaseData> findByTypes(String types) throws Exception {
		return basedataDao.findByTypes(types);
	}
	
	public TBaseData findByCode(Integer code) throws Exception {
		return basedataDao.findByCode(code);
	}
	
	public List<TBaseData> findByTypesAndName(String types,String name) throws Exception {
		return basedataDao.findByTypesAndName(types,name);
	}

	public Page<TBaseData> findAll(Specification<TBaseData> spec, Pageable pageable) {
		return basedataDao.findAll(spec, pageable);
	}

	

}
