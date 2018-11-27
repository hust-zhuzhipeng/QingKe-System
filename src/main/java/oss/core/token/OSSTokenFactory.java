package oss.core.token;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.BasicCredentials;
import com.aliyuncs.auth.STSAssumeRoleSessionCredentialsProvider;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import oss.core.exception.ResultEnum;
import oss.core.token.domain.Token;
import oss.persistent.PersistentPolicy;
/**
 * 获取token
 * @author zzp
 * 默认的权限是OSS读权限，需要传入更细致的policy来控制更细的权限
 * 该类是低层与oss直接通信的类，不给service层直接提供服务！
 * 复合优先于继承，外部对象通过调用其getToken方法，来获取token
 * 
 */
@Service("oSSTokenFactory")
public class OSSTokenFactory{
	@Value("${oss_regionId}")
	private String regionId;
	@Value("${oss_accessKeyId}")
	private String accessKeyId;
	@Value("${oss_accessKeySecret}")
	private String accessKeySecret;
	@Value("${oss_roleArn}")
	private String roleArn; //角色的全局资源名称
	@Value("${token_timeout}")
	private long timeout;	//token的超时时间
	//Token的命名策略
	@Autowired
	@Qualifier("poemShowPersistentPolicy")
	private PersistentPolicy persistentPolicy;
	//token生成器客户端
	private DefaultAcsClient client;
	private static Logger log = LoggerFactory.getLogger(OSSTokenFactory.class);
	@PostConstruct //指定该方法在对象被创建后马上调用 相当于配置文件中的init-method属性
	public void init(){
		DefaultProfile profile = DefaultProfile.getProfile(regionId);
		BasicCredentials basicCredentials = new BasicCredentials(
       		accessKeyId,
       		accessKeySecret
		);
       STSAssumeRoleSessionCredentialsProvider provider = new STSAssumeRoleSessionCredentialsProvider(
           basicCredentials,
           roleArn,
           profile
       );
       client = new DefaultAcsClient(profile, provider);
       log.info("【OSSClient】创建成功！");
	}
	/*
	 * 会失败，抛出异常 不能
	 */
	public Token getToken(String policy){
		String p = policy;
		final AssumeRoleRequest request = new AssumeRoleRequest();
		request.setMethod(MethodType.POST);
		request.setRoleArn(roleArn);
		request.setDurationSeconds(timeout);
		request.setRoleSessionName("session");
		request.setPolicy(p); // Optional
		AssumeRoleResponse response = null;
		try {
			response = client.getAcsResponse(request);
		} catch (ServerException e) {
			log.error("【OSS服务器】异常！", e);
		} catch (ClientException e) {
			log.error("【OSSClient】异常！", e);
			if (client != null) {
		        client.shutdown();
		    }
			//重新创建client
			init();
		}
		if(response == null){
			log.error("【Token】创建失败");
			throw new TokenNotFoundException(ResultEnum.TOKEN_UNFOUND);
		}
		String tokenID = response.getCredentials().getAccessKeyId();
		String tokenSec = response.getCredentials().getAccessKeySecret();
		String token = response.getCredentials().getSecurityToken();
		log.info("Token创建成功");
		return new Token(tokenID,tokenSec,token,System.currentTimeMillis(),timeout*1000);
	}
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	public String getAccessKeyId() {
		return accessKeyId;
	}
	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}
	public String getAccessKeySecret() {
		return accessKeySecret;
	}
	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}
	public String getRoleArn() {
		return roleArn;
	}
	public void setRoleArn(String roleArn) {
		this.roleArn = roleArn;
	}
	public long getTimeout() {
		return timeout;
	}
	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}
	
}
