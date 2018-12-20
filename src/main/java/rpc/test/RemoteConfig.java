package rpc.test;

import rpc.proxy.RpcRemoteInvoke;
import rpc.proxy.RpcRemoteInvokeConfig;

@RpcRemoteInvokeConfig
public class RemoteConfig {
	@RpcRemoteInvoke()
	HelloService helloService;
	@RpcRemoteInvoke()
	EchoService echoService;
}
