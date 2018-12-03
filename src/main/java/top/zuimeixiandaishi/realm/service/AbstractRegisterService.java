package top.zuimeixiandaishi.realm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import top.zuimeixiandaishi.realm.domain.User;
import util.ECache;
import util.MD5Util;

/**
 * 注册服务模板
 * @author 99759
 * 1.输入必要的邮箱或者手机号等与用户名，密码
 * 2.生成该临时账号，存入缓存
 * 3.发送验证码到邮箱/手机登验证
 * 4.验证通过，存入数据库
 */
public abstract class AbstractRegisterService implements RegisterService{
	private static Logger log = LoggerFactory.getLogger(AbstractRegisterService.class);
	@Autowired
	private ECache<String,User> cverificationCodeCache;
	//密钥
	private final static String RegisterSecretKey = "REGIST";
	@Override
	public boolean rigister(User user) {
		//验证注册信息
		valid(user);
		//生成验证码
		String code = generateCode(user);
		log.info("生成验证码:"+code);
		//发出验证通知
		sendVerificationCode(user,code);
		//存入缓存
		addCache(code,user);
		return true;
	}
	@Override
	public boolean active(String key) {
		User user = getUser(key);
		if(user == null){
			throw new RegisterException(RegisterExceptionEnum.VerificationCode_Expired);
		}
		log.info("激活成功:"+user);
		createUser(user);
		return true;
	}
	/*
	 * 验证注册的必要条件是否满足，例如邮箱注册，就需要邮箱信息
	 * 如果不满足，抛出注册异常
	 */
	abstract void valid(User user) throws RegisterException;
	/*
	 * 根据用户username生成验证码
	 */
	private String generateCode(User user){
		String code = MD5Util.md5(user.getUsername(), RegisterSecretKey);
		return code;
	}
	/*
	 * 发送验证通知
	 */
	abstract void sendVerificationCode(User user,String code) throws RegisterException;
	/*
	 * 暂存入缓存
	 */
	void addCache(String code,User user){
		cverificationCodeCache.putValue(code, user);
	}
	/*
	 * 从预注册后缓存中取出user
	 */
	private User getUser(String code){
		return cverificationCodeCache.getValue(code);
	}
	/*
	 * 存入数据库
	 */
	abstract boolean createUser(User user);
}
