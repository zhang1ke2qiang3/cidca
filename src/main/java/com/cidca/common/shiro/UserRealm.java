package com.cidca.common.shiro;


import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.cidca.common.Constants;
import com.cidca.entity.TMuser;
import com.cidca.system.service.MuserService;
import com.cidca.util.MD5;

public class UserRealm extends AuthorizingRealm{

	@Autowired
	private MuserService userService;

	/**执行授权逻辑 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection ppc) {
		String name= (String) ppc.getPrimaryPrincipal();//获取登录用户名
		TMuser user = userService.findByIdcard(name); //查询用户名称
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();//添加角色和权限
		if (Constants.ZERO.equals(user.getPersontype())) {//内部人员可以访问的
			simpleAuthorizationInfo.addStringPermission("internal");//注解名称
		}
		if (Constants.ONE.equals(user.getPersontype())) {//外部人员可以访问的
			simpleAuthorizationInfo.addStringPermission("external");//注解名称
		}
		return simpleAuthorizationInfo;
	}

	/**执行认证逻辑 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		if (authenticationToken.getPrincipal() == null) {	//加这一步的目的是在Post请求的时候会先进认证，然后在到请求
			return null;
		}
		//获取用户信息
		String name = authenticationToken.getPrincipal().toString();
		String userPwd = new String((char[]) authenticationToken.getCredentials());
		String md5ofStr = new MD5().getMD5ofStr(userPwd);
		TMuser user = userService.findByIdcard(name);
		if (user == null) {
			//这里返回后会报出对应异常
			return null;
		} else if (!md5ofStr.equals(user.getPassword())) {
			throw new AccountException("密码不正确");
		}else {
			//这里验证authenticationToken和simpleAuthenticationInfo的信息
			SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name, userPwd, getName());//用户输入的信息，别放数据库返回的否则认证失败 20210715
			return simpleAuthenticationInfo;
		}
	}
}