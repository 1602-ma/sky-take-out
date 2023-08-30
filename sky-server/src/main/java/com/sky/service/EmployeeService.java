package com.sky.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;

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

}
