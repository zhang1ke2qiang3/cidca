package com.cidca.system.service.impl;

import com.cidca.entity.TRole;
import com.cidca.system.dao.RoleDao;
import com.cidca.system.service.RoleService;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DynamicInsert
@DynamicUpdate
@Transactional
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleDao roleDao;

    @Override
    public TRole save(TRole vo) {
        return roleDao.save(vo);
    }

    @Override
    public void delete(TRole vo) {
        roleDao.delete(vo);
    }
}
