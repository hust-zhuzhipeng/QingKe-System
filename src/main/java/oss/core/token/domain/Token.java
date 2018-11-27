package oss.core.token.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import util.JsonOperate;

/**
 * 交给app的临时token
 * @author 99759
 *
 */
//json转token时，忽略json中多出来的字段
@JsonIgnoreProperties(ignoreUnknown = true)
public class Token {
	private String accessKeyId;
	private String accessKeySecret;
	private String token;
	@JsonIgnore
	private Long createTime;//生成时间 ms
	@JsonIgnore
	private Long timeOut;//token有效时长ms
	public Token(){
		
	}
	public Token(String accessKeyId,String accessKeySecret,String token,Long nowtime,Long timeOut){
		this.accessKeyId = accessKeyId;
		this.accessKeySecret = accessKeySecret;
		this.token = token;
		this.createTime = nowtime;
		this.timeOut=timeOut;
	}
	@JsonIgnore
	public boolean isExpired(){
		System.out.println("剩余 "+(timeOut-(System.currentTimeMillis()-createTime))+" ms");
		return (System.currentTimeMillis()-createTime)>=timeOut;
	}


	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(Long timeOut) {
		this.timeOut = timeOut;
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	@Override
	public String toString(){
		return JsonOperate.ObjToJson(this);
	}
}
