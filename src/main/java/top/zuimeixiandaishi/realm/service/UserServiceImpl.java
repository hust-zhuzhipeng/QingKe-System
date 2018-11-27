package top.zuimeixiandaishi.realm.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.zuimeixiandaishi.realm.dao.UserDao;
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

}
