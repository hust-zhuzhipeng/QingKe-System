package top.zuimeixiandaishi.web.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import oss.core.exception.ResultEnum;
import oss.core.token.TokenNotFoundException;
import oss.core.token.domain.Token;
import top.zuimeixiandaishi.realm.domain.User;

/**
 * 统一的异常处理
 * @author zzp
 *
 */
@ControllerAdvice
public class ExceptionHandle {
	private static final Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);
	/*
	 * 该方法负责处理Controller层抛出的所有异常
	 */
    @ExceptionHandler(value = Exception.class)  //申明捕获那个异常类
    public ResponseEntity<Result<?>> handle(Exception e) {
    	if (e instanceof TokenNotFoundException) {
    		TokenNotFoundException tokenException = (TokenNotFoundException) e;
    		return new ResponseEntity<>(ResultUtil.error(tokenException.getCode(), tokenException.getMessage()),HttpStatus.NOT_FOUND);
    	}
        logger.error("【系统异常】", e);
        return new ResponseEntity<>(ResultUtil.error(ResultEnum.UNKOWN_ERROR.getCode(), ResultEnum.UNKOWN_ERROR.getMsg()),HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
