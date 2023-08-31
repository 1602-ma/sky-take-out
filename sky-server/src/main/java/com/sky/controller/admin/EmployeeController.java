package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.constant.SqlConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 * @author feng
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "员工相关接口")
public class EmployeeController {

	@Resource
	private EmployeeService employeeService;

	@Resource
	private JwtProperties jwtProperties;

	/**
	 * 登录
	 *
	 * @param employeeLoginDTO dto
	 * @return result
	 */
	@PostMapping("/login")
	@ApiOperation(value = "员工登录")
	public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
		log.info("员工登录：{}", employeeLoginDTO);

		Employee employee = employeeService.login(employeeLoginDTO);

		//登录成功后，生成jwt令牌
		Map<String, Object> claims = new HashMap<>(23);
		claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
		String token = JwtUtil.createJwt(jwtProperties.getAdminSecretKey(), jwtProperties.getAdminTtl(), claims);

		EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder().id(employee.getId())
				.userName(employee.getUsername()).name(employee.getName()).token(token).build();

		return Result.success(employeeLoginVO);
	}

	/**
	 * 退出
	 *
	 * @return result
	 */
	@PostMapping("/logout")
	@ApiOperation("员工推出")
	public Result<String> logout() {
		return Result.success();
	}

	/**
	 * 新增员工
	 * @param dto dto
	 * @return result
	 */
	@PostMapping
	@ApiOperation(value = "新增员工")
	public Result<T> save(@RequestBody EmployeeDTO dto) {
		log.info("新增员工：{}", dto);
		employeeService.saveEmployee(dto);
		return Result.success();
	}

	/**
	 * 员工分页查询
	 * @param dto dto
	 * @return page
	 */
	@GetMapping("/page")
	@ApiOperation("员工分页查询")
	public Result<PageResult> page(EmployeePageQueryDTO dto) {
		log.info("员工分页查询，参数为：{}", dto);
		PageResult pageResult = employeeService.pageQuery(dto);
		return Result.success(pageResult);
	}

	@PostMapping("/status/{status}")
	@ApiOperation("启动禁用员工账号")
	public Result<T> startOrStopAccount(@ApiParam(value = "状态", required = true) @PathVariable Integer status,
			@ApiParam(value = "员工id", required = true) Long id) {
		log.info("启用禁用员工账号：{}，{}", status, id);
		Boolean flag = employeeService.startOrStopAccount(status, id);
		if (flag) {
			return Result.success();
		}else {
			return Result.error("操作失败");
		}
	}

	@GetMapping("/{id}")
	@ApiOperation("根据id查询员工信息")
	public Result<Employee> getById(@PathVariable Long id) {
		Employee employee = employeeService.getById(id);
		employee.setPassword(SqlConstant.PASSWORD);
		return Result.success(employee);
	}

	@PutMapping
	@ApiOperation("编辑员工信息")
	public Result<T> update(@RequestBody EmployeeDTO dto) {
		log.info("编辑员工信息：{}，", dto);
		employeeService.updateAccount(dto);
		return Result.success();
	}
}
