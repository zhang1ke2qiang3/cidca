package com.cidca.system.service.impl;

import com.cidca.entity.TUser;
import com.cidca.entity.Tnotice;
import com.cidca.system.dao.UserDao;
import com.cidca.system.service.UserService;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DynamicInsert
@DynamicUpdate
@Transactional
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    public TUser save(TUser vo) throws Exception {
        return userDao.save(vo);
    }

    @Override
    public TUser findById(String id) {
        return userDao.getOne(id);
    }

    @Override
    public void deleteById(String id) throws Exception {
        userDao.deleteById(id);
    }

    @Override
    public List<TUser> findAll() {
        return userDao.findAll();
    }

    @Override
    public TUser findByUserid(String userid) {
        return userDao.findByUserid(userid);
    }

    @Override
    public TUser update(TUser vo) throws Exception {
        return userDao.save(vo);
    }

    @Override
    public Page<TUser> findAll(Specification<TUser> spec, Pageable pageable) {
        return userDao.findAll(spec, pageable);
    }
}
