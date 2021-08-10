package com.cidca.projectt.service.impl;

import java.util.List;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cidca.entity.AidMsgSubclass;
import com.cidca.projectt.dao.AidMsgSubclassDao;
import com.cidca.projectt.service.AidMsgSubclassService;

@DynamicInsert
@DynamicUpdate
@Transactional
@Service
public class AidMsgSubclassServiceImpl implements AidMsgSubclassService{

	@Autowired
	private  AidMsgSubclassDao aidMsgSubclassDao;
	
	@Override
	public AidMsgSubclass save(AidMsgSubclass vo) throws Exception {
		return aidMsgSubclassDao.save(vo);
	}

	@Override
	public int update(String project_name) {
		return aidMsgSubclassDao.update(project_name);
	}

	@Override
	public AidMsgSubclass findById(String id){
		return aidMsgSubclassDao.findById(id).orElse(null);
	}
	
	@Override
	public void deleteById(String id) throws Exception {
		aidMsgSubclassDao.deleteById(id);
	}

	@Override
	public void updateAidMsg(AidMsgSubclass vo) {
		aidMsgSubclassDao.save(vo);
	}
	
	public List<AidMsgSubclass> findAll(AidMsgSubclass bean){
		return aidMsgSubclassDao.findAll(Example.of(bean));
	}

	@Override
	public List<AidMsgSubclass> findAll() {
		return aidMsgSubclassDao.findAll();
	}

	@Override
	public Page<AidMsgSubclass> findAll(Specification<AidMsgSubclass> spec, Pageable pageable) {
		return aidMsgSubclassDao.findAll(spec,pageable);
	}
	
	@Override
	public List<AidMsgSubclass> findAllByAidmsgid(String aidmsgid) {
		return aidMsgSubclassDao.findAllByAidmsgid(aidmsgid);
	}

	@Override
	public List<AidMsgSubclass> findAllByAidmsgidAndYear(String aidmsgid, String yearr) {
		return aidMsgSubclassDao.findAllByAidmsgidAndYear(aidmsgid,yearr);
	}

	@Override
	public List<AidMsgSubclass> findByYearAndProject_name(String yearr,String project_name) throws Exception {
		return aidMsgSubclassDao.findByYearAndProject_name(yearr,project_name);
	}
	
	@Override
	public List<AidMsgSubclass> findByCreateUserAndProject_name(String createUser,String project_name) throws Exception {
		return aidMsgSubclassDao.findByCreateUserAndProject_name(createUser,project_name);
	}

	@Override
	public List<AidMsgSubclass> findByStaee() {
		return aidMsgSubclassDao.findByStaee();
	}

	@Override
	public Boolean bathPass(String uuids,String idcard) {
		if(!"".equals(uuids)){
			String[] uuidArey = uuids.split(",");
			for(int i = 0; i < uuidArey.length; i++){
				AidMsgSubclass obj = this.findById(uuidArey[i]);
				obj.setStatee("2");
				obj.setAudit_user(idcard);
				this.updateAidMsg(obj);
			}
			return true;
		}else{
			return false;
		}
	}

	@Override
	public Boolean bathNoPass(String uuids,String idcard) {
		if(!"".equals(uuids)){
			String[] uuidArey = uuids.split(",");
			for(int i = 0; i < uuidArey.length; i++){
				AidMsgSubclass obj = this.findById(uuidArey[i]);
				obj.setStatee("3");
				obj.setAudit_user(idcard);
				this.updateAidMsg(obj);
			}
			return true;
		}else{
			return false;
		}
	}

	@Override
	public Boolean bathBack(String uuids,String idcard) {
		if(!"".equals(uuids)){
			String[] uuidArey = uuids.split(",");
			for(int i = 0; i < uuidArey.length; i++){
				AidMsgSubclass obj = this.findById(uuidArey[i]);
				obj.setStatee("-1");
				obj.setAudit_user(idcard);
				this.updateAidMsg(obj);
			}
			return true;
		}else{
			return false;
		}
	}

}
