package com.cidca.system.dao;

import com.cidca.entity.TMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

public interface MenuDao extends JpaRepository<TMenu, Integer>, JpaSpecificationExecutor<TMenu> {

    @Modifying
    @Transactional
    @Query(value = "select distinct uuid,m.menu_name as menuName ," +
            " m.menu_url as menuUrl , m.menu_icon as menuIcon ,m.parent_id as parentId  " +
            "from t_menu m where m.uuid in (select menu_id from t_role_menu where role_id in " +
            "(select roleid from t_user_role where userid = ?1))",nativeQuery = true)
    public List<Map> findByRoleId(@Param("roleid")String roleid);

    @Transactional
    @Query(value = "from TMenu where uuid=?1 ")
    public TMenu findById(@Param("uuid") String uuid);

    @Modifying
    @Transactional
    @Query(value = "delete from TMenu where uuid=?1 ")
    public void delete(@Param("uuid")String uuid);

    @Modifying
    @Transactional
    @Query(value = "select distinct uuid,m.menu_name as menuName ," +
            " m.menu_url as menuUrl , m.menu_icon as menuIcon ,m.parent_id as parentId  " +
            "from t_menu m where m.uuid in (select menu_id from t_role_menu where role_id = ?1 " +
            ")",nativeQuery = true)
    public List<Map> getMenuByRoleid(Integer roleid);


}
