package com.cidca.system.service;

import com.cidca.entity.TMenu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface MenuService {

    public TMenu saveOrUpdate(TMenu vo);

    public void delete(TMenu vo);

    public Page<TMenu> findAll(Specification<TMenu> spec, Pageable pageable);

    public TMenu findById(String uuid);

    public void delete(String uuid);

}
