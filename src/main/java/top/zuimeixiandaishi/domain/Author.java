package top.zuimeixiandaishi.domain;
/**
 * 诗人
 * @author 99759
 *
 */
public class Author {
	 private long id;	//唯一id
	 private String name;//姓名
	 private String ename;//英文名
	 private String profile; //简介
	 private String imageLink;//头像连接
	 private String baiduLink;//百度连接
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getImageLink() {
		return imageLink;
	}
	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}
	public String getBaiduLink() {
		return baiduLink;
	}
	public void setBaiduLink(String baiduLink) {
		this.baiduLink = baiduLink;
	}
	
}
