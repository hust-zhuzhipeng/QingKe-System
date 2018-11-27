package top.zuimeixiandaishi.domain;

import java.util.Date;

/**
 * 诗歌
 * @author 99759
 */
public class Poem {
	private Long id;	//唯一id
	private String title;	//标题
	private String abstra;	//摘要
	private String context;	//内容
	private String type;	//类型
	private Long authorId;	//诗人id
	private Date createTime;	//出版时间
	private Long anthologyId;//所属诗集
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAbstra() {
		return abstra;
	}
	public void setAbstra(String abstra) {
		this.abstra = abstra;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getAuthorId() {
		return authorId;
	}
	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getAnthologyId() {
		return anthologyId;
	}
	public void setAnthologyId(Long anthologyId) {
		this.anthologyId = anthologyId;
	}
}
