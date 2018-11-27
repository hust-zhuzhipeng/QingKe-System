package top.zuimeixiandaishi.domain;

import java.util.Date;
import java.util.List;

/**
 * 诗集
 * @author 99759
 *
 */
public class Anthology {
	private long id;		//唯一id
	private long authorId;	//作者id
	private List<Poem> poemList; //诗歌集合
	private Date publishTime;	//出版时间
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getAuthorId() {
		return authorId;
	}
	public void setAuthorId(long authorId) {
		this.authorId = authorId;
	}
	public List<Poem> getPoemList() {
		return poemList;
	}
	public void setPoemList(List<Poem> poemList) {
		this.poemList = poemList;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	
	
}
