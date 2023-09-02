package com.sky.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.service.DishFlavorService;
import com.sky.service.DishService;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author f
 * @date 2023/9/2 12:01
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

	@Resource
	private DishMapper dishMapper;

	@Resource
	private DishFlavorMapper dishFlavorMapper;

	@Resource
	private DishFlavorService dishFlavorService;

	/**
	 * 新增菜品和对应的口味
	 * @param dto dto
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveWithFlavor(DishDTO dto) {
		Dish dish = new Dish();
		BeanUtils.copyProperties(dto, dish);

		dishMapper.insert(dish);

		Long dishId = dish.getId();

		List<DishFlavor> flavors = dto.getFlavors();
		if (!Collections.isEmpty(flavors)) {
			flavors.forEach(t -> {
				t.setDishId(dishId);
			});
		}
		dishFlavorService.saveBatch(flavors);
	}
}
