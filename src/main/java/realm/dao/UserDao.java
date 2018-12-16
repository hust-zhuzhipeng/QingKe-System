package realm.dao;

import java.util.Set;

import realm.domain.Role;
import realm.domain.User;

public interface UserDao {
	/**
	 * 通过用户名查找用户
	 * @param username
	 * @return
	 */
	public User getByUserName(String username);
	/**
	 * 通过用户名查找该用户所有的角色并并保存在集合中
	 * @param username
	 * @return
	 */
	public Set<String> getRoles(String username);
	/**
	 * 通过用户名查找该用户所有的权限并保存在Set集合中
	 * @param username
	 * @return
	 */
	public Set<String> getPermissions(String username);
	/**
	 * 创建角色
	 */
	public int insertUser(User user);
	/**
	 * 修改角色
	 */
	public int updateUser(User user);
}
