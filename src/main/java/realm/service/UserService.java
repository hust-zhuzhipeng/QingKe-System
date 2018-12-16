package realm.service;

import java.util.Set;

import realm.domain.Role;
import realm.domain.User;

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
	/**
	 * 创建新的账户
	 */
	public boolean createUser(User user);
	/**
	 * 更新的账户
	 * 通过username找到需要更新的数据行
	 */
	public boolean updateUser(User user);
}
