package com.sky.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 封装分页查询结果
 * @author feng
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult implements Serializable {

	private static final long serialVersionUID = 5687030413105370215L;

	/** 总记录数 */
	private long total;

	/** 当前页数据集合 */
	private List records;

}
