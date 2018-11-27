package oss.core.token;

import java.util.Map;
import java.util.Timer;
import java.util.WeakHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import oss.core.token.domain.Policy;
import oss.core.token.domain.PolicyEnum;
import oss.core.token.domain.PolicyFactory;
import oss.core.token.domain.Token;
/**
 * Token管理器
 * @author zzp
 * 根据服务层的需求，生成Policy，传入OSSTokenFactory来获取Token
 * 每种Manager只提供有限类型的token
 * 目前对外的方法有2类：
 * getReadToken：使用了ehCache缓存技术，组合了TokenCache类
 * getReadTokenExpired：zzp自己用weakHashMap来做了缓存哈哈，需要开启init()方法，目前该方法被注释掉了
 */
@Service("oSSTokenManager")
public class OSSTokenManager {
	private static Logger log = LoggerFactory.getLogger(OSSTokenManager.class);
	@Autowired
	private OSSTokenFactory  oSSTokenFactory;
	@Autowired
	private Timer timer;
	@Value("${token_period}")	//
	private long period;
	@Autowired
	private PolicyFactory policyFactory;
	@Autowired
	private TokenCache tokenCache;
	
	public Token getReadToken(PolicyEnum pe,boolean isDesc,int offset,int limit){
		String key = pe.name()+":"+offset+":"+limit;
		return tokenCache.getReadToken(pe,isDesc,offset,limit,key);
	}
	public Token getReadToken(PolicyEnum pe,String fileName){
		String key = pe.name()+":"+fileName;
		return  tokenCache.getCommonToken(pe,fileName,key);
	}
	
	//poemshow token缓存
	final Map<String,Token> poemshowtokenCache = new WeakHashMap<>();
	//poemshow Policy缓存
	final Map<String,Policy> poemshowpolicyCache = new WeakHashMap<>();
	//poemshow token缓存
	final Map<String,Token> commontokenCache = new WeakHashMap<>();
		//poemshow Policy缓存
	final Map<String,Policy> commonpolicyCache = new WeakHashMap<>();
	
	/*
	 * 按照模式来获取指定位置的Token
	 * 目前已用ehcache缓存代替
	 */
	@Deprecated
	public Token getReadTokenExpired(PolicyEnum pe,boolean isDesc,int offset,int limit){
		final String key = pe.name()+":"+offset+":"+limit;
		//向token缓存查询
		if(poemshowtokenCache.containsKey(key)){
			log.info("token缓存命中");
			return poemshowtokenCache.get(key);
		}
		//向Policy缓存查询
		else if(poemshowpolicyCache.containsKey(key)){
			log.info("policy缓存命中");
			Policy policy = poemshowpolicyCache.get(key);
			Token t = oSSTokenFactory.getToken(policy.Json());
			poemshowtokenCache.put(key, t);
			return t;
		}//生成Policy
		else{	
			Policy policy = policyFactory.getPoemShowPolicy(pe, isDesc, offset, limit);
			poemshowpolicyCache.put(key, policy);			
			Token t = getTokenFromOss(policy);			
			poemshowtokenCache.put(key, t);
			return t;
		}
	}
	/*
	 * 目前已用ehcache缓存代替
	 */
	@Deprecated
	public Token getReadTokenExpired(PolicyEnum pe,String fileName){
		final String key = pe.name()+":"+fileName;
		//向token缓存查询
		if(commontokenCache.containsKey(key)){
			log.info("token缓存命中");
			return commontokenCache.get(key);
		}
		//向Policy缓存查询
		else if(commonpolicyCache.containsKey(key)){
			log.info("policy缓存命中");
			Policy policy = commonpolicyCache.get(key);
			Token t = oSSTokenFactory.getToken(policy.Json());
			commontokenCache.put(key, t);
			return t;
		}//生成Policy
		else{	
			Policy policy = policyFactory.getCommonPolicy(pe, fileName);
			commonpolicyCache.put(key, policy);			
			Token t = getTokenFromOss(policy);			
			commontokenCache.put(key, t);
			return t;
		}
	}
/*	@PostConstruct //指定该方法在对象被创建后马上调用 相当于配置文件中的init-method属性
	private void init(){
		//每过一段时间，自动更新所有的token
		log.info("定时器启动");
		TimerTask tt = new TimerTask(){
			@Override
			public void run() {
				Iterator<String> ite = poemshowtokenCache.keySet().iterator();
				while(ite.hasNext()){
					String key = ite.next();
					if(poemshowtokenCache.get(key).isExpired()){
						log.info("{} is expired",key);
						ite.remove();
					}
				}
				ite = commontokenCache.keySet().iterator();
				while(ite.hasNext()){
					String key = ite.next();
					if(commontokenCache.get(key).isExpired()){
						log.info("{} is expired",key);
						ite.remove();
					}
				}
			}
		};
		timer.scheduleAtFixedRate(tt, 1000, period);
	}*/
	
	private Token getTokenFromOss(Policy p){
		return oSSTokenFactory.getToken(p.Json());
	}
	
	public OSSTokenFactory getoSSTokenFactory() {
		return oSSTokenFactory;
	}
	public void setoSSTokenFactory(OSSTokenFactory oSSTokenFactory) {
		this.oSSTokenFactory = oSSTokenFactory;
	}
	public Timer getTimer() {
		return timer;
	}
	public void setTimer(Timer timer) {
		this.timer = timer;
	}
	public long getPeriod() {
		return period;
	}
	public void setPeriod(long period) {
		this.period = period;
	}
}