package realm.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
/**
 * 用户模型
 * @author zzp
 *
 */
public class User {
	private Integer id;		//用户id
	@NotNull(message="username can't be null!")
	@Size(min=2,max=16,message="username can only be in [2,16]!")
	private String username;//用户名
	@NotNull
	@Size(min=5,max=25)
	private String password;//密码
	private Role roleId;	//该用户的角色
	private String email;	//邮箱
	private String phone;	//手机
	private State state;	//用户当前状态
	
	public Role getRoleId() {
		return roleId;
	}
	public void setRoleId(Role roleId) {
		this.roleId = roleId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	
	@Override
	public String toString(){
		return "id="+id+"\tusername="+username+"\tpassword="+password;
	}
}
