package com.sky.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author feng
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "员工登录返回的数据格式")
public class EmployeeLoginVO implements Serializable {

	private static final long serialVersionUID = 7887656506246686651L;

	@ApiModelProperty("主键值")
	private Long id;

	@ApiModelProperty("用户名")
	private String userName;

	@ApiModelProperty("姓名")
	private String name;

	@ApiModelProperty("jwt令牌")
	private String token;

}
