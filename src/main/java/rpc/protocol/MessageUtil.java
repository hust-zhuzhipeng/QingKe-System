package rpc.protocol;

import java.util.HashMap;
import java.util.Map;

/*
 * 为外部提供msg的一些信息
 */
public class MessageUtil {
	//msg的header中addach和key-value匹配
    private static Map<String,Class> attachMap = new HashMap<>();
    //type与body的匹配 待完善此处
	private static Map<MessageType,Class> bodyMap = new HashMap<>();
	static {
		attachMap.put("attach", Object.class);
		bodyMap.put(MessageType.REQUEST, RpcRequest.class);
		bodyMap.put(MessageType.RESPONSE, RpcResponse.class);
	}
	public static Class getAttachClass(String attachname) throws Exception{
		if(attachMap.containsKey(attachname)){
			return attachMap.get(attachname);
		}else{
			throw new Exception("no such attach is defined ->"+attachname);
		}
	}
	public static Class getbodyClass(MessageType msgType) throws Exception{
		return bodyMap.get(msgType);
	}
}
