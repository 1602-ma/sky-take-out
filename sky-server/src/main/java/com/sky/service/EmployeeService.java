package com.sky.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

/**
 * @author feng
 */
public interface EmployeeService extends IService<Employee> {

	/**
	 * 员工登录
	 * @param employeeLoginDTO dto
	 * @return employee
	 */
	Employee login(EmployeeLoginDTO employeeLoginDTO);

	/**
	 * 新增员工
	 * @param dto dot
	 */
	void saveEmployee(EmployeeDTO dto);

	/**
	 * 分页查询员工
	 * @param dto  dot
	 * @return page
	 */
	PageResult pageQuery(EmployeePageQueryDTO dto);

	/**
	 * 启用禁用员工账号
	 * @param status status
	 * @param id id
	 * @return Boolean
	 */
	Boolean startOrStopAccount(Integer status, Long id);

	/**
	 * 更新员工信息
	 * @param dto dto
	 */
	void updateAccount(EmployeeDTO dto);
}
