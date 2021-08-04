package com.cidca.notice.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.cidca.entity.Tnotice;

public interface NoticeService {

	public List<Tnotice> findAll();
	
	public Tnotice findById(String uuid);
	
	public Tnotice saveTnotice(Tnotice obj) throws Exception;

	public void deleteTnotice(Tnotice obj) throws Exception;
	
	public void updateTnotice(Tnotice obj);
	
	public  List<Tnotice> findByTitle(String title);
	
	public List<Tnotice> findByCreateUser(String createUser);
	
	public Page<Tnotice> findAll(Specification<Tnotice> spec, Pageable pageable);
	
	

}
