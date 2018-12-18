package rpc.protocol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 消息生成模板
 * @author zzp
 */
@Component("nettyMessageFactory")
public class NettyMessageFactory {
	private final byte priority;
	@Autowired
	public NettyMessageFactory(@Value("${RpcServer.NettyMessageFactory.priority}")
	byte defaultPriority){
		this.priority = defaultPriority;
	}
	//request模板 
	public NettyMessage newRpcRequest(byte priority){
		NettyMessage res = newRpcRequest();
		res.setPriority(priority);
		return res;
	}
	public NettyMessage newRpcRequest(){
		NettyMessage res = new NettyMessage();
		//header
		res.setPriority(this.priority);
		res.setType(MessageType.REQUEST);
		res.setSessionID(genereateSessionID());
		//body
		RpcRequest request = new RpcRequest();
		request.setRequestId(genereateRequestId());
		res.setBody(request);
		return res;
	}
	public NettyMessage newRpcResponse(long sessionID,long requestId,byte priority){
		NettyMessage res = newRpcResponse(sessionID,requestId);
		res.setPriority(priority);
		return res;
	}
	public NettyMessage newRpcResponse(long sessionID,long requestId){
		NettyMessage res = new NettyMessage();
		//header
		res.setPriority(this.priority);
		res.setType(MessageType.RESPONSE);
		res.setSessionID(sessionID);
		//body
		RpcResponse response = new RpcResponse();
		response.setRequestId(requestId);
		res.setBody(response);
		return res;
	}
	private static long genereateSessionID(){
		return 0;
	}
	private static long genereateRequestId(){
		return 0;
	}
}
