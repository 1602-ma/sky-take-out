package com.sky.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.dto.DishDTO;
import com.sky.entity.Dish;

/**
 * @author f
 * @date 2023/9/2 12:00
 */
public interface DishService extends IService<Dish> {

	/**
	 * 新增菜品和对应的口味
	 * @param dto dot
	 */
	void saveWithFlavor(DishDTO dto);
}
