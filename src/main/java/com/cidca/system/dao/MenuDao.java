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
    @Query(value = "select distinct m.menu_name as name," +
            " m.menu_url as path, m.menu_icon as icon  " +
            "from t_menu m where m.uuid in (select menu_id from t_role_menu where role_id in " +
            "(select roleid from t_user_role where userid = ?1))",nativeQuery = true)
    public List<Map> findByRoleId(@Param("roleid")String roleid);
}
