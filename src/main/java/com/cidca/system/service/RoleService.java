package com.cidca.system.service;

import com.cidca.entity.TRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RoleService {

    public TRole save(TRole vo);

    public void delete(TRole vo);

    public Optional<TRole> findById(Integer roleid);

    Page<TRole> findAll(Specification<TRole> spec, PageRequest pageable);

    public boolean saveRoleMenu(String roleid,String menuids);

    public List<Map> getUserRoleList(String idcard);
}
