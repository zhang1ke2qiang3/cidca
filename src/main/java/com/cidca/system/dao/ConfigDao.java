package com.cidca.system.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cidca.entity.Config;


public interface ConfigDao extends JpaRepository< Config,Integer> {
	
	
	//没有nativeQuery = true时，就不是原生sql，而其中的select * from xxx中xxx也不是数据库对应的真正的表名，而是对应的实体名，并且sql中的字段名也不是数据库中真正的字段名，而是实体的字段名
	@Query(value="select * from t_config  where id = ?1",nativeQuery = true)
	public  Config findId(Integer id);
	
}
