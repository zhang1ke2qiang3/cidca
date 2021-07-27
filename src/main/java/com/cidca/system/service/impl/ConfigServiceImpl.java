package com.cidca.system.service.impl;

import javax.annotation.Resource;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cidca.entity.Config;
import com.cidca.system.dao.ConfigDao;
import com.cidca.system.service.ConfigService;

@DynamicInsert
@DynamicUpdate
@Transactional
@Service
public class ConfigServiceImpl implements ConfigService {
	@Resource
	private ConfigDao configDao;
	
	@Override
	public Config findById(Integer id) {
		return configDao.findId(id);
	}
}
