package realm.domain;
/**
 * 当前用户状态
 * @author zzp
 * ACTIVE:激活状态，正常使用
 * FROZEN:冻结状态，无法登录
 * 目前只是提前保留了该设计，未具体的构思实践
 */
public enum State {
	ACTIVE,	//激活状态
	FROZEN,	//冻结状态
}
