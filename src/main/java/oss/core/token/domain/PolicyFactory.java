package oss.core.token.domain;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import oss.persistent.PersistentPolicy;

/**
 * 获取与业务场景相关的Policy
 * @author zzp
 *
 */
@Service("policyFactory")
public class PolicyFactory {
	@Autowired
	private PersistentPolicy poemShowPersistentPolicy;
	@Value("${db_poemshow}")
	public String poemShowBucket;
	@Value("${db_poem}")
	public String poemBucket;
	@Value("${db_author}")
	public String authorBucket;
	public Policy getCommonPolicy(PolicyEnum pe, String fileName){
		//acs:oss:*:*:{bucketName}/fileName
		String resource = generateResource(pe)+fileName;
		String Action = pe.getAction();
		String Effect = pe.getEffect();
		Policy res = new Policy();
		res.addStatement(Effect,Action,resource);
		System.out.println(res.Json());
		return res;
	}
	public Policy getPoemShowPolicy(PolicyEnum pe,boolean isDesc,int offset,int limit){
		String resource = generateResource(pe);
		Map<String,List<String>> policyMap = poemShowPersistentPolicy.getExactPolicy(isDesc, offset, limit);
		Policy res = new Policy(); 
		if(policyMap.containsKey("Allow")){
			for(String Allowp : policyMap.get("Allow")){
				res.addStatement("Allow", pe.Action, resource+Allowp);
			}
		}
		if(policyMap.containsKey("Deny")){
			for(String Denyp : policyMap.get("Deny")){
				res.addStatement("Deny", pe.Action, resource+Denyp);
			}
		}
		System.out.println(res.Json());
		return res;
	
	}
	private String generateResource(PolicyEnum pe){
		switch(pe){
			case READ_AUTHOR: return pe.getResource()+authorBucket+"/";
		
			case READ_POEM:	return pe.getResource()+poemBucket+"/";
				
			case READ_POEMSHOW: return pe.getResource()+poemShowBucket+"/";	
		}
		return null;
	}
}
