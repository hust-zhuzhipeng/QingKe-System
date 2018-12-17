package rpc.test;

import rpc.server.RpcService;

@RpcService(HelloService.class)
public class HelloServiceImp implements HelloService{

	@Override
	public String hello() {
		return "Hello, i am HelloServiceImp";
	}

}
