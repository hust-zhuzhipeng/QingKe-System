package oss.core.token.domain;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import util.JsonOperate;
/**
 * STS授权策略 
 * {@link https://help.aliyun.com/document_detail/31867.html?spm=a2c4g.11186623.6.582.2334354eVhHmRX#h2-url-6}
 * @author 99759
 * 每个Policy生成后就不应该被修改。
 */
public class Policy {
	@JsonProperty(value="Version")
	private final static String version = "1";
	@JsonProperty("Statement")
	private final List<Statement> statement = new LinkedList<>();
	
	//获取Policy的String格式
	public String Json(){
		return toString();
	}
	public String getVersion() {
		return version;
	}
	public List<Statement> getStatement() {
		return statement;
	}
	public Policy addStatement(Statement sta){
		statement.add(sta);
		return this;
	}
	public Policy addStatement(String Effect,String Action,String Resource){
		return this.addStatement(new Statement(Effect,Action,Resource));
	}
	
	@Override
	public String toString(){
		return JsonOperate.ObjToJson(this);
	}
}