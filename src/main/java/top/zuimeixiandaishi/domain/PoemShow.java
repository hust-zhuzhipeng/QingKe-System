package top.zuimeixiandaishi.domain;

import java.util.Date;
/**
 * 展示层数据
 * @author 99759
 */
public class PoemShow {
	public Long id;	//唯一id
	public Long poemId;	//诗歌id
	public String imageLink; //图片连接
	public Long orderId;	//展示次序
	public Date publishTime;	//发表时间
	public Date lastUpdateTime;	//最近修改时间
	public String uuid;	//uuid
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPoemId() {
		return poemId;
	}
	public void setPoemId(Long poemId) {
		this.poemId = poemId;
	}
	public String getImageLink() {
		return imageLink;
	}
	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
