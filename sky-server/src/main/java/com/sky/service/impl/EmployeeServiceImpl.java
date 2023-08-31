package com.sky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author feng
 */
@Service
@Slf4j
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

	@Resource
	private EmployeeMapper employeeMapper;

	/**
	 * 员工登录
	 *
	 * @param employeeLoginDTO dto
	 * @return employee
	 */
	@Override
	public Employee login(EmployeeLoginDTO employeeLoginDTO) {
		String username = employeeLoginDTO.getUsername();
		String password = employeeLoginDTO.getPassword();

		//1、根据用户名查询数据库中的数据
		LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(!Objects.isNull(username), Employee::getUsername, username);
		Employee employee = this.getOne(wrapper);

		//2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
		if (Objects.isNull(employee)) {
			//账号不存在
			throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
		}

		//密码比对
		password = DigestUtils.md5DigestAsHex(password.getBytes());
		if (!password.equals(employee.getPassword())) {
			//密码错误
			throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
		}

		if (Objects.equals(employee.getStatus(), StatusConstant.DISABLE)) {
			//账号被锁定
			throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
		}

		//3、返回实体对象
		return employee;
	}

	/**
	 * 新曾员工
	 * @param dto dot
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveEmployee(EmployeeDTO dto) {
		Employee employee = new Employee();

		// 拷贝属性
		BeanUtils.copyProperties(dto, employee);
		// 设置账号的状态：默认正常状态 1表示正常 0表示锁定
		employee.setStatus(StatusConstant.ENABLE);
		// 设置密码，默认为username
		employee.setPassword(DigestUtils.md5DigestAsHex(employee.getUsername().getBytes()));

		this.save(employee);
	}

	/**
	 * 分页查询员工
	 * @param dto  dot
	 * @return page
	 */
	@Override
	public PageResult pageQuery(EmployeePageQueryDTO dto) {
		LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(Objects.nonNull(dto.getName()), Employee::getName, dto.getName())
				.orderByAsc(Employee::getCreateTime);
		PageInfo<Employee> pageInfo = PageHelper.startPage(dto.getPage(), dto.getPageSize())
				.doSelectPageInfo(() -> baseMapper.selectList(queryWrapper));

		return new PageResult(pageInfo.getTotal(), pageInfo.getList());
	}

	/**
	 * 启用禁用员工账号
	 * @param status status
	 * @param id id
	 * @return Boolean
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean startOrStopAccount(Integer status, Long id) {
		Employee employee = Employee.builder().status(status).id(id).build();
		return this.updateById(employee);
	}

	/**
	 * 更新员工信息
	 * @param dto dto
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateAccount(EmployeeDTO dto) {
		Employee employee = new Employee();
		BeanUtils.copyProperties(dto, employee);

		baseMapper.updateById(employee);
	}
}
