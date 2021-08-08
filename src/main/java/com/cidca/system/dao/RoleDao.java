package com.cidca.system.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.cidca.entity.TRole;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Repository
public interface RoleDao extends JpaRepository<TRole, Integer>,JpaSpecificationExecutor<TRole>{

    @Modifying
    @Transactional
    @Query(value = "delete from t_role_menu where role_id=?1",nativeQuery = true)
    public void deleteRoleMenuByRoleId(@Param("roleid") String roleid);

    @Modifying
    @Transactional
    @Query(value = "insert into t_role_menu (uuid,role_id,menu_id)" +
            " values (?1,?2,?3) ",nativeQuery = true)
    public void saveRoleMenu(@Param("uuid")String uuid,@Param("roleid") String roleid,@Param("menuid")String menuid);

    @Modifying
    @Transactional
    @Query(value = "select roleid from t_user_role where userid = ?1",nativeQuery = true)
    public List<Map> getUserRoleList(@Param("idcard") String idcard);

    @Modifying
    @Transactional
    @Query(value = "delete  from t_user_role where userid = ?1",nativeQuery = true)
    public void deleteUserRoleByUserid(@Param("userid") String userid);

    @Modifying
    @Transactional
    @Query(value = "insert into t_user_role (userid,roleid)" +
            " values (?1,?2) ",nativeQuery = true)
    public void saveUserRole(@Param("userid") String userid,@Param("roleid")String roleid);

}
