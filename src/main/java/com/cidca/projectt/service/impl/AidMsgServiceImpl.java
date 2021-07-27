package com.cidca.projectt.service.impl;

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

import com.cidca.entity.AidMsg;
import com.cidca.projectt.dao.AidMsgDao;
import com.cidca.projectt.service.AidMsgService;

@DynamicInsert
@DynamicUpdate
@Transactional
@Service
public class AidMsgServiceImpl implements AidMsgService {

	@Autowired
	private AidMsgDao aidMsgDao;

	@Override
	public AidMsg insert(AidMsg vo) throws Exception {
		// abean a=new abean();
		// a.set1;
		// a.set2;
		// a.set3;
		// insert into a (1,2,3,4,5,6,7,8,9,10)
		// values(?,?,null,null,null,null,null,null,null,null)
		// insert into a (1,2) values(?,?)
		return aidMsgDao.save(vo);
	}

	@Override
	public int update(String project_name) {
		// RowMapper<AidMsg> rm =
		// BeanPropertyRowMapper.newInstance(AidMsg.class);
		// List q=new ArrayList<String>();
		// String sql = " select * from PM_GUIDE_CATE t where sbid=? ";
		// List<AidMsg> query = jdbcTemplate.query(sql, q.toArray(), rm);
		// return query;
		return aidMsgDao.update(project_name);
	}

	public List<AidMsg> findAll(AidMsg bean) {
		return aidMsgDao.findAll(Example.of(bean));
	}

	@Override
	public AidMsg findById(String id) {
		return aidMsgDao.findById(id).orElse(null);
	}

	@Override
	public void deleteById(String id) throws Exception {
		aidMsgDao.deleteById(id);
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
		return aidMsgDao.findAll(spec, pageable);
	}

}
