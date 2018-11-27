package top.zuimeixiandaishi.realm;

import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import top.zuimeixiandaishi.realm.domain.User;
import top.zuimeixiandaishi.realm.service.UserService;

public class MyRealm extends AuthorizingRealm{
	@Autowired
	private UserService userService;
	
	/**
	 * 授权方法
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		/**
		 * 注意principals.getPrimaryPrincipal()对应
		 * new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), getName())的第一个参数
		 */
		//获取当前身份
		String userName = (String) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		
		//从数据库中查找该用户有何角色和权限
		Set<String> roles = userService.getRoles(userName);
		Set<String> permissions = userService.getPermissions(userName);
		
		//为当前用户赋予对应角色和权限
		info.setRoles(roles);
		info.setStringPermissions(permissions);
		
		return info;
	}
	/**
	 * 认证方法 返回认证信息，从安全数据源中获取认证信息给认证器进行比较
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//获取用户名
		String username = (String) token.getPrincipal();
		
		//从数据库中查找用户信息
		User user = userService.getByUserName(username);
		if(user == null) return null;
		
		AuthenticationInfo info = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getName());
		
		return info;
	}

}
