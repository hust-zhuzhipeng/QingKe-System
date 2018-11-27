package oss.core.token.domain;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Statement {
	@JsonProperty(value="Effect")
	private String effect;	//授权 Alow|Den y
	@JsonProperty(value="Action")
	private List<String> action = new LinkedList<>();	//操作 oss:GetObject* oss:PutObject*
	@JsonProperty(value="Resource")
	private List<String> resource = new LinkedList<>();//资源 acs:oss:*(Region):*(owner):bucket:object
	
	public Statement(){};
	public Statement(String Effect,String Action,String Resource){
		setEffect(Effect);
		this.addAction(Action);
		this.addResource(Resource);
	}
	public void addAction(String ac){
		action.add(ac);
	}
	public void addResource(String re){
		resource.add(re);
	}
	
	public String getEffect() {
		return effect;
	}
	public void setEffect(String effect) {
		this.effect = effect;
	}
	public List<String> getAction() {
		return action;
	}
	public void setAction(List<String> action) {
		this.action = action;
	}
	public List<String> getResource() {
		return resource;
	}
	public void setResource(List<String> resource) {
		this.resource = resource;
	}
}
