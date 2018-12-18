package rpc.protocol;

import java.util.Map;

/**
 * rpc远程调用协议
 * @author zzp
 * head：
 * 	crcCode：int，32，Netty消息的校验码 0xABEF（2字节）+主版本号（1-255 1字节）+次版本号（1-255 1字节）
 * 	length：int， 32，消息长度，包括消息头和消息体
 * 	sessionID：long，64，全局唯一会话ID
 * 	type：byte，8，类型
 * 		0：业务请求
 * 		1：业务响应
 * 		2：长连接握手请求
 * 		3：长连接握手响应
 * 		4：心跳请求
 * 		5：心跳响应 等
 * 	priority：byte，8，消息优先级
 * 	attachment：变长，Map<String,Object> 附加信息
 * body
 * 	Object，变长
 */
public class NettyMessage {
	private MsgHeader header;
	private Object body;
	
	//仅内部解码可用
	NettyMessage(){
		header = new MsgHeader();
	}
	public MsgHeader getHeader() {
		return header;
	}
	public void setHeader(MsgHeader header) {
		this.header = header;
	}
	public Object getBody() {
		return body;
	}
	public void setBody(Object body) {
		this.body = body;
	}
	public int getCrcCode() {
		return header.crcCode;
	}
	public void setCrcCode(int crcCode) {
		this.header.crcCode = crcCode;
	}
	public int getLength() {
		return header.length;
	}
	public void setLength(int length) {
		this.header.length = length;
	}
	public long getSessionID() {
		return header.sessionID;
	}
	public void setSessionID(long sessionID) {
		this.header.sessionID = sessionID;
	}
	public MessageType getType() {
		return header.getType();
	}
	byte getTypeToByte() {
		return MessageType.TypeToByte(header.getType());
	}
	void setType(byte type) {
		this.header.type = MessageType.getType(type);
	}
	public void setType(MessageType type) {
		this.header.type = type;
	}
	public byte getPriority() {
		return header.priority;
	}
	public void setPriority(byte priority) {
		this.header.priority = priority;
	}
	public Map<String, Object> getAttachment() {
		return header.attachment;
	}
	public void setAttachment(Map<String, Object> attachment) {
		this.header.attachment = attachment;
	}
	
	@Override
	public String toString() {
		String str1 = "";
		String str2 = "";
		if(header != null){
			str1 += header.toString();
		}
		if(body != null){
			str2 += "body [";
			str2 += body.toString();
			str2 += "]";
		}
		
		return str1+" "+str2;
	}
}
