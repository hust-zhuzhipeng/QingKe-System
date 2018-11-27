package oss.core.token;

import oss.core.token.domain.Policy;
import oss.core.token.domain.PolicyEnum;
import oss.core.token.domain.Token;
/**
 * Token的缓存方法接口
 * @author zzp
 * 这里怎么合理设计还没有思路，只是一个重复
 */
public interface TokenCache {
	Token getCommonToken(PolicyEnum pe,String fileName,String key);
	Policy getCommonPolicy(PolicyEnum pe,String fileName,String key);
	Token getReadToken(PolicyEnum pe,boolean isDesc,int offset,int limit,String key);
	Policy getReadPolicy(PolicyEnum pe,boolean isDesc,int offset,int limit,String key);
}
