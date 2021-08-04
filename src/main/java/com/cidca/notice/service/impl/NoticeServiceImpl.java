package com.cidca.notice.service.impl;

import java.util.List;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cidca.entity.Tnotice;
import com.cidca.notice.dao.NoticeDao;
import com.cidca.notice.service.NoticeService;

@DynamicInsert
@DynamicUpdate
@Transactional
@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private NoticeDao noticeDao;

	@Override
	public List<Tnotice> findAll() {
		return noticeDao.findAll();
	}
	@Override
	public Tnotice findById(String uuid){
		return noticeDao.findById(uuid).orElse(null);
	}

	@Override
	public Tnotice saveTnotice(Tnotice obj) throws Exception {
		return noticeDao.save(obj);
	}

	@Override
	public void deleteTnotice(Tnotice obj) throws Exception {
		noticeDao.delete(obj);
	}

	@Override
	public void updateTnotice(Tnotice vo) {
		noticeDao.save(vo);
	}

	@Override
	public  List<Tnotice> findByTitle(String title) {
		return noticeDao.findByTitle(title);
	}

	@Override
	public List<Tnotice> findByCreateUser(String createUser) {
		return noticeDao.findByCreateUser(createUser);
	}

	@Override
	public Page<Tnotice> findAll(Specification<Tnotice> spec, Pageable pageable) {
		return noticeDao.findAll(spec, pageable);
	}
	

}
