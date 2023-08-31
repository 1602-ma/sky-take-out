package com.sky.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.sky.context.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author f
 * @date 2023/8/30 23:12
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

	@Override
	public void insertFill(MetaObject metaObject) {
		log.info("start insert fill.......");
		Long currentId = BaseContext.getCurrentId();
		if (Objects.isNull(currentId)) {
			currentId = 1L;
		}
		this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
		this.strictInsertFill(metaObject, "createUser", Long.class, currentId);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		log.info("start update fill.....");
		Long currentId = BaseContext.getCurrentId();
		if (Objects.isNull(currentId)) {
			currentId = 1L;
		}
		this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
		this.strictUpdateFill(metaObject, "createUser", Long.class, currentId);
	}
}
