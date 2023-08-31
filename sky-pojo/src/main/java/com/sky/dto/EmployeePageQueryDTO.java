package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author feng
 */
@Data
public class EmployeePageQueryDTO implements Serializable {

	private static final long serialVersionUID = -4725990807411608878L;

	/** 员工姓名 */
	private String name;

	/** 页码 */
	private Integer page;

	/** 每页显示记录数 */
	private Integer pageSize;

}