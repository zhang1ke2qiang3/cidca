package com.cidca.system.service.impl;

import com.cidca.entity.TRole;
import com.cidca.system.dao.RoleDao;
import com.cidca.system.service.RoleService;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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

    @Override
    public Optional<TRole> findById(Integer roleid) {
        return roleDao.findById(roleid);
    }

    @Override
    public Page<TRole> findAll(Specification<TRole> spec, PageRequest pageable) {
        return roleDao.findAll(spec, pageable);
    }

    @Override
    public boolean saveRoleMenu(String roleid, String menuids) {
        if(null != roleid || "".equals(roleid)){
           if("".equals(menuids)){
               return false;
           }else{
               try{
                   roleDao.deleteRoleMenuByRoleId(roleid);
                   String[] strArray = menuids.split(",");
                   for(int i = 0; i < strArray.length;i++){
                       String uuid = UUID.randomUUID().toString().replace("-","");
                       roleDao.saveRoleMenu(uuid,roleid,strArray[i]);
                   }
               }catch (Exception e){
                   e.printStackTrace();
                   return false;
               }
               return true;
           }
        }else{
            return false;
        }
    }

    @Override
    public List<Map> getUserRoleList(String idcard) {
        return roleDao.getUserRoleList(idcard);
    }
}
