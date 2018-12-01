package top.zuimeixiandaishi.realm.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.zuimeixiandaishi.realm.dao.UserDao;
import top.zuimeixiandaishi.realm.domain.Role;
import top.zuimeixiandaishi.realm.domain.User;
@Service("userService")
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDao userDao;
	@Override
	public User getByUserName(String username) {
		return userDao.getByUserName(username);
	}

	@Override
	public Set<String> getRoles(String username) {
		return userDao.getRoles(username);
	}

	@Override
	public Set<String> getPermissions(String username) {
		return userDao.getPermissions(username);
	}

	@Override
	public boolean createUser(User user) {
		User temp = userDao.getByUserName(user.getUsername());
		if(temp!=null) return false;
		else{
			return userDao.insertUser(user)>0;
		}
	}

	@Override
	public boolean updateUser(User user) {
		User temp = userDao.getByUserName(user.getUsername());
		//不存在该账户 无从更新
		if(temp==null) return false;
		else{
			return userDao.updateUser(user)>0;
		}
	}

}
