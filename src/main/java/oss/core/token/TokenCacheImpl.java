package oss.core.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import oss.core.token.domain.Policy;
import oss.core.token.domain.PolicyEnum;
import oss.core.token.domain.PolicyFactory;
import oss.core.token.domain.Token;
/**
 * Token缓存
 * @author zzp
 * 此处有坑，坑了我一整天。
 * 如果该类中，有别的方法调用被Cacheable修饰的方法，那么，是不能触发ehcache的
 * 原因可能是字节码代理的一些特征，我还不是很熟悉。
 * 所以，不要在类的内部调用=。=，血坑！！
 */
@Service("tokenCache")
public class TokenCacheImpl implements TokenCache{
	@Autowired
	private OSSTokenFactory  oSSTokenFactory;
	@Autowired
	PolicyFactory policyFactory;
	
	@Override
	@Cacheable(value="tokenCache",key="#key")
	public Token getCommonToken(PolicyEnum pe, String fileName, String key) {
		Policy policy = getCommonPolicy(pe,fileName,key);
		return oSSTokenFactory.getToken(policy.Json());
	}

	@Override
	@Cacheable(value="policyCache",key="#key")
	public Policy getCommonPolicy(PolicyEnum pe, String fileName, String key) {
		Policy res = policyFactory.getCommonPolicy(pe, fileName);
		return res;
	}

	@Override
	@Cacheable(value="tokenCache",key="#key")
	public Token getReadToken(PolicyEnum pe, boolean isDesc, int offset, int limit, String key) {
		Policy policy = getReadPolicy(pe,isDesc,offset,limit,key);
		return oSSTokenFactory.getToken(policy.Json());
	}

	@Override
	@Cacheable(value="policyCache",key="#key")
	public Policy getReadPolicy(PolicyEnum pe, boolean isDesc, int offset, int limit, String key) {
		return policyFactory.getPoemShowPolicy(pe, isDesc, offset, limit);
	}

}
