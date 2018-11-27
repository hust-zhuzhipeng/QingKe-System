package top.zuimeixiandaishi.realm.service;

import java.util.Set;

import top.zuimeixiandaishi.realm.domain.User;

public interface UserService {
	/**
	 * 通过用户名查找用户
	 * @param username
	 * @return
	 */
	public User getByUserName(String username);
	/**
	 * 通过用户名查找该用户所有的角色并保存在Set集合中
	 * @param username
	 * @return
	 */
	public Set<String> getRoles(String username);
	/**
	 * 过用户名查找该用户所有的权限并保存在Set集合中
	 * @param username
	 * @return
	 */
	public Set<String> getPermissions(String username);
}
