package com.cidca.system.service;

import com.cidca.entity.TUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface UserService {

    public TUser save(TUser vo) throws Exception;

    public TUser findById(String id);

    public void deleteById(String id) throws Exception;

    public List<TUser> findAll();

    public TUser findByUserid(String userid);

    public TUser update(TUser vo) throws Exception;

    public Page<TUser> findAll(Specification<TUser> spec, Pageable pageable);
}
