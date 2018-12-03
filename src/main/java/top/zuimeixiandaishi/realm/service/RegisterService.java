package top.zuimeixiandaishi.realm.service;

import top.zuimeixiandaishi.realm.domain.User;

/**
 * 注册服务，提供用户注册服务
 * @author zzp
 *
 */
public interface RegisterService {
	//预注册
	boolean rigister(User user);
	//激活
	boolean active(String key);
}
