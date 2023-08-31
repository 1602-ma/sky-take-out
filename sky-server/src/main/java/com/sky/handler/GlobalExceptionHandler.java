package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.constant.SqlConstant;
import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 * @author feng
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	/**
	 * 捕获业务异常
	 * @param ex ex
	 * @return result
	 */
	@ExceptionHandler
	public Result<T> exceptionHandler(BaseException ex) {
		log.error("异常信息：{}", ex.getMessage());
		return Result.error(ex.getMessage());
	}

	@ExceptionHandler
	public Result<T> exceptionHandler(SQLIntegrityConstraintViolationException ex) {
		String message = ex.getMessage();
		// java.sql.SQLIntegrityConstraintViolationException: Duplicate entry 'thomas' for key 'idx_username'
		if (message.contains(SqlConstant.DUPLICATE_ENTRY)) {
			String[] strArray = message.split(" ");
			String username = strArray[2];
			String msg = username + MessageConstant.ACCOUNT_EXISTS;
			return Result.error(msg);
		} else {
			return Result.error(MessageConstant.UNKNOWN_ERROR);
		}
	}
}
