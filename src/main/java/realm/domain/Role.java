package realm.domain;
/*
 * 角色枚举
 */
public enum Role {
	ADMIN(0,"管理员"),	//管理员
	GUEST(1,"未登录浏览者"),	//浏览者 无需登录
	USER(2,"客户");	//需要账号登录的用户
	private int code;	//存于数据库中的id
	private String desc;//描述
	Role(int code,String desc){
		this.code = code;
		this.desc = desc;
	}
	
	public static Role getRole(String role){
		switch(role){
			case "ADMIN": return ADMIN;
			case "GUEST": return GUEST;
			case "USER" : return USER;
			default : return GUEST;
		}
	}
	public static Role getRole(int code){
		switch(code){
			case 0: return ADMIN;
			case 1: return GUEST;
			case 2: return USER;
			default : return GUEST;
		}
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
