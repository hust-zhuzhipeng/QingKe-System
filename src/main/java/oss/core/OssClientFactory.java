package oss.core;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.comm.Protocol;
/**
 * 实例工厂，获取以Ram身份登录的客户端。
 * 该客户端配置为原型模式
 * @author 99759
 */
@Service("ossClientFactory")
public class OssClientFactory {
	@Value("${oss_accessKeyId}")
	private String oss_accessKeyId;
	@Value("${oss_accessKeySecret}")
	private String oss_accessKeySecret;
	@Value("${oss_endpoint}")
	private String oss_endpoint;
	//客户端 只读
	@Autowired
	private ClientConfiguration clientConfiguration;
	@Value("${Protocol}")
	private String protocol;
	
	public OSSClient getOSSClient(){
		return new OSSClient(oss_endpoint, oss_accessKeyId,
				oss_accessKeySecret);
	}
	
	/*
	 * 初始化方法 读取配置文件中的Protocol,
	 * 可能是我太蠢了，还不知道spring如何处理映射，因此先放在init里处理
	 */
	@PostConstruct
	public void init(){
		//默认HTTP
		if(protocol.equals("HTTPS")) clientConfiguration.setProtocol(Protocol.HTTPS);
	}
	public String getOss_accessKeyId() {
		return oss_accessKeyId;
	}
	public String getOss_endpoint() {
		return oss_endpoint;
	}
	public void setOss_endpoint(String oss_endpoint) {
		this.oss_endpoint = oss_endpoint;
	}
	public void setOss_accessKeyId(String oss_accessKeyId) {
		this.oss_accessKeyId = oss_accessKeyId;
	}
	public String getOss_accessKeySecret() {
		return oss_accessKeySecret;
	}
	public void setOss_accessKeySecret(String oss_accessKeySecret) {
		this.oss_accessKeySecret = oss_accessKeySecret;
	}
	
	public ClientConfiguration getClientConfiguration() {
		return clientConfiguration;
	}

	public void setClientConfiguration(ClientConfiguration clientConfiguration) {
		this.clientConfiguration = clientConfiguration;
	}

	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
}
