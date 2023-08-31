package com.sky.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

/**
 * @author feng
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("category")
public class Category extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	/** 类型: 1菜品分类 2套餐分类 */
	private Integer type;

	/** 分类名称 */
	private String name;

	/** 顺序 */
	private Integer sort;

	/** 分类状态 0标识禁用 1表示启用 */
	private Integer status;
}
