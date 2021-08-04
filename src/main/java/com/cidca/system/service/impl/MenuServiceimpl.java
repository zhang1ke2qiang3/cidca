package com.cidca.system.service.impl;

import com.cidca.entity.TMenu;
import com.cidca.system.dao.MenuDao;
import com.cidca.system.service.MenuService;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DynamicInsert
@DynamicUpdate
@Transactional
@Service
public class MenuServiceimpl implements MenuService {

    @Autowired
    MenuDao menuDao;

    @Override
    public TMenu saveOrUpdate(TMenu vo) {
        return menuDao.save(vo);
    }

    @Override
    public void delete(TMenu vo) {
        menuDao.delete(vo);
    }
}
