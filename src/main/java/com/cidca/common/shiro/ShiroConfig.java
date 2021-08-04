package com.cidca.common.shiro;

import java.util.LinkedHashMap;import java.util.Map;import org.apache.shiro.spring.web.ShiroFilterFactoryBean;import org.apache.shiro.web.mgt.DefaultWebSecurityManager;import org.springframework.beans.factory.annotation.Qualifier;import org.springframework.context.annotation.Bean;import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {

	/**创建ShiroFilterFactoryBean*/
	@Bean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager")DefaultWebSecurityManager securityManager){
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();//设置安全管理器
		//设置安全管理器
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		//添加Shiro内置过滤器
		/** 
		 * Shiro内置过滤器，可以实现权限相关的拦截器 * 常用的过滤器：
		 * anon: 无需认证（登录）可以访问 
		 * * authc: 必须认证才可以访问 
		 * * user: 如果使用rememberMe的功能可以直接访问 
		 * * perms： 该资源必须得到资源权限才可以访问 
		 * * role: 该资源必须得到角色权限才可以访问 */
		Map<String,String> filterMap = new LinkedHashMap<String,String>();
		filterMap.put("/public/**", "anon");
		filterMap.put("/user/**", "anon");
		filterMap.put("/role/**", "anon");
		filterMap.put("/menu/**", "anon");
		filterMap.put("/aidmsg/**", "anon");//authc
		filterMap.put("/sys/**", "anon");//authc
		filterMap.put("/noticeShow/**", "anon");//authc
		//filterMap.put("/static/**", "anon");//麻痹的这样没用
		filterMap.put("/css/**", "anon");
		filterMap.put("/js/**", "anon");
		filterMap.put("/img/**", "anon");
		filterMap.put("/temp/**", "anon");
		filterMap.put("/upload/**", "anon");
		
		shiroFilterFactoryBean.setLoginUrl("/public/login");//修改调整的登录页面 如果登录的url不是“login”需要指定一下
		shiroFilterFactoryBean.setLoginUrl("/public/login2");//修改调整的登录页面 如果登录的url不是“login”需要指定一下
		shiroFilterFactoryBean.setSuccessUrl("/sys/welcome");
		shiroFilterFactoryBean.setUnauthorizedUrl("/public/error");
		
		filterMap.put("/**", "authc");//放在最后
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
		return shiroFilterFactoryBean;
	}

	/** * 创建DefaultWebSecurityManager */
	@Bean(name="securityManager")
	public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm")UserRealm userRealm){
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();//关联realm
		securityManager.setRealm(userRealm);//关联realm
		return securityManager;
	}

	/** * 创建Realm */
	@Bean(name="userRealm")
	public UserRealm getRealm(){
		return new UserRealm();
	}

}
