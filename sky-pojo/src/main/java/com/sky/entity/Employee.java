package com.sky.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author feng
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "employee")
public class Employee extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;

	private String username;

	private String name;

	private String password;

	private String phone;

	private String sex;

	private String idNumber;

	private Integer status;
}
