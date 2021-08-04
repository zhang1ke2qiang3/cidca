package com.cidca.system.service;

import com.cidca.entity.TMenu;

public interface MenuService {

    public TMenu saveOrUpdate(TMenu vo);

    public void delete(TMenu vo);
}
