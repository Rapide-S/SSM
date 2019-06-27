package com.db.sys.service.realm;

import java.util.HashSet;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SaltedAuthenticationInfo;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.db.sys.dao.SysMenuDao;
import com.db.sys.dao.SysRoleMenuDao;
import com.db.sys.dao.SysUserDao;
import com.db.sys.dao.SysUserRoleDao;
import com.db.sys.entity.SysUser;
@Service
public class ShiroUserRealm extends AuthorizingRealm {

	@Autowired
	SysUserDao sysUserDao;
	
	@Autowired
	SysUserRoleDao sysUserRoleDao;
	
	@Autowired
	SysRoleMenuDao sysRoleMenuDao;
	
	@Autowired
	SysMenuDao sysMenuDao;
	
	//设置凭证匹配器(与用户添加操作使用相同的加密算法)
	@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
				//构建凭证匹配对象
				HashedCredentialsMatcher cMatcher=
				new HashedCredentialsMatcher();
				//设置加密算法（名称 -md5）
				cMatcher.setHashAlgorithmName("MD5");
				//设置加密次数
				cMatcher.setHashIterations(1);
				//告诉认证管理器用这个cMatcher来完成认证
				super.setCredentialsMatcher(cMatcher);

	}
	
	
	
	//该方法是进行用户验证的
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//1.获取用户名(用户页面输入)
		UsernamePasswordToken upToken=(UsernamePasswordToken)token;
		//2.基于用户名查询用户信息
		String username = upToken.getUsername();
		//3.判定用户是否存在
		SysUser user = sysUserDao.findUserByUserName(username);
		if (user==null) {
			throw new UnknownAccountException();
		}
		if (user.getId()==0) {
			throw new LockedAccountException();
		}
		
		ByteSource  credentialsSalt = ByteSource.Util.bytes(user.getSalt());
		SaltedAuthenticationInfo info = new SimpleAuthenticationInfo(
				user, user.getPassword(), credentialsSalt, getName());
				
		return info;
	}
	
	

	//该方法主要是用于当前登录用户授权(
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//1.获取登录用户信息，例如用户id
		SysUser user = (SysUser)principals.getPrimaryPrincipal();
		Integer userId = user.getId();
		//2.基于用户id获取用户拥有的角色(sys_user_roles)
		
		List<Integer> roleIds = sysUserRoleDao.findRoleIdsByUserId(userId);
		if (roleIds==null||roleIds.size()==0) {
			throw new AuthenticationException();
		}
		//3.基于角色id获取菜单id(sys_role_menus)
		Integer[] array = {};
		List<Integer> menuIds = sysRoleMenuDao.findMenuIdsByRoleIds(roleIds.toArray(array));//集合转属组
		if (menuIds==null||menuIds.size()==0) {
			throw new AuthenticationException();
		}
		//4.基于菜单id获取权限标识(sys_menus)
		List<String> permissions = sysMenuDao.findPermissions(menuIds.toArray(array));
		//5.对权限标识信息进行封装并返回
		HashSet<String> set = new HashSet<>();//用set封装，set不予许重复
		for (String per : permissions) {
			if (!StringUtils.isEmpty(per)) {
				set.add(per);
			}
		}
		//是返回值AuthorizationInfo接口   的实现类
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(set);
		return info;//返回给授权管理器
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
