package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author feng
 */
@Data
public class CategoryPageQueryDTO implements Serializable {

	private static final long serialVersionUID = 5489510512768020631L;

	/** 页码 */
    private Integer page;

    /** 每页记录数 */
    private Integer pageSize;

    /** 分类名称 */
    private String name;

    /** 分类类型 1菜品分类  2套餐分类 */
    private Integer type;

}
