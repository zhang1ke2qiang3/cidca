package com.cidca.system.service.impl;

import javax.annotation.Resource;

import com.cidca.entity.Config;
import com.cidca.system.dao.ConfigDao;
import com.cidca.system.service.ConfigService;



public class ConfigServiceImpl implements ConfigService {
	@Resource
	private ConfigDao configDao;
	
	@Override
	public Config findById(Integer id) {
		return configDao.findId(id);
	}
}
