package oss.core.token.domain;
/**
 * 与业务相关的Policy类型模板
 * @author zzp
 * 模板中含有Policy的关键数据。
 * 待改进：Effect不是必要字段，Effect的statement的字段，不是确定的
 */
public enum PolicyEnum {
	READ_POEMSHOW("acs:oss:*:*:","oss:Get*","Allow"),
	READ_POEM("acs:oss:*:*:","oss:Get*","Allow"),
	READ_AUTHOR("acs:oss:*:*:","oss:Get*","Allow");
	String Resource;
	String Action;
	String Effect;
	private PolicyEnum(){};
	private PolicyEnum(String Resource,String Action,String Effect){
		this.Resource = Resource;
		this.Action = Action;
		this.Effect = Effect;
	}
	public String getResource() {
		return Resource;
	}
	public String getAction() {
		return Action;
	}
	public String getEffect() {
		return Effect;
	}
}
