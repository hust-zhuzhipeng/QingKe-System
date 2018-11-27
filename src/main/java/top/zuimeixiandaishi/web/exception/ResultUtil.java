package top.zuimeixiandaishi.web.exception;
/**
 * Result的常用封装
 * @author zzp
 *
 */
public class ResultUtil {
	public static <T> Result<T> success(T object) {
        return new Result<>(0, "成功", object);
    }

    public static Result<?> success() {
        return success(null);
    }

    public static Result<?> error(Integer code, String msg) {
        return new Result<>(code, msg);
    }

}
