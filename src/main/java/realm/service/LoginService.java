package realm.service;
/*
 * 登录服务，提供登录的验证
 */
public interface LoginService {
	//是否已经认证完毕(已登录)
	boolean isAuthenticated();
	/*
	 * 进行验证，验证只是一个过程，不需要反馈
	 * 验证通过与不过不需要用户知道详细的情况
	 */
	boolean Authenticate(String username,String password);
}
