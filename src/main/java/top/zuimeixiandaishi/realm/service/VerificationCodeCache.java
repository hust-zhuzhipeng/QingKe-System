package top.zuimeixiandaishi.realm.service;
import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import top.zuimeixiandaishi.realm.domain.User;
import util.ECache;
/**
 * 验证码的缓存
 * @author 99759
 * key为验证码，value是User信息
 */
@Service("verificationCodeCache")
public class VerificationCodeCache implements ECache<String,User>{
	private static Logger log = LoggerFactory.getLogger(VerificationCodeCache.class);
	@Autowired
	private CacheManager cacheManager;
	private final static String cachename ="gisterCache";
	private Cache cache;
	@PostConstruct
	public void init(){
		cache = cacheManager.getCache(cachename);
		log.info("VerificationCodeCache 初始化完成");
	}
	@Override
	public boolean InCache(String key) {
		return cache.get(key, User.class)!=null;
	}

	@Override
	public User getValue(String key) {
		return cache.get(key,User.class);
	}

	@Override
	public void putValue(String key, User value) {
		cache.put(key, value);
	}
	//测试
	public static void main(String[]args){
		ApplicationContext ap = 
				new ClassPathXmlApplicationContext(
						new String[]{"classpath:/spring/spring-ehcache.xml"
								,"classpath:/spring/spring-aliyun.xml"
								,"classpath:/spring/spring-dao.xml"
								,"classpath:/spring/spring-service.xml"
								,"classpath:/spring/spring-util.xml"});
		VerificationCodeCache cache = (VerificationCodeCache)ap.getBean("verificationCodeCache");
		User a = new User();
		a.setId(1);
		User b = new User();
		b.setId(2);
		cache.putValue("1", a);
		System.out.println(cache.InCache("1"));
		System.out.println(cache.getValue("1").getId());
		cache.putValue("1", b);
		System.out.println(cache.InCache("1"));
		System.out.println(cache.getValue("1").getId());
	}
	

}
