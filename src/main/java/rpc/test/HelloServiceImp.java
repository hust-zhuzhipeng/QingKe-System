package rpc.test;

import rpc.server.RpcService;

@RpcService(value=HelloService.class)
public class HelloServiceImp implements HelloService{

	@Override
	public String hello() {
		return "Hello, i am HelloServiceImp";
	}

	@Override
	public String echo(String str) {
		return "echo "+str;
	}

}
