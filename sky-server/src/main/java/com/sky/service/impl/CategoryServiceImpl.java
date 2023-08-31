package com.sky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Dish;
import com.sky.entity.SetMeal;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 分类业务层
 * @author feng
 */
@Service
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

	@Resource
	private CategoryMapper categoryMapper;

	@Resource
	private DishMapper dishMapper;

	@Resource
	private SetMealMapper setmealMapper;

	/**
	 * 新增分类
	 * @param categoryDTO dto
	 */
	@Override
	public void save(CategoryDTO categoryDTO) {
		Category category = new Category();
		//属性拷贝
		BeanUtils.copyProperties(categoryDTO, category);

		//分类状态默认为禁用状态0
		category.setStatus(StatusConstant.DISABLE);

		//设置创建时间、修改时间、创建人、修改人
		category.setCreateUser(BaseContext.getCurrentId());
		category.setUpdateUser(BaseContext.getCurrentId());

		categoryMapper.insert(category);
	}

	/**
	 * 分页查询
	 * @param categoryPageQueryDTO dto
	 * @return page
	 */
	@Override
	public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
		LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper
				.eq(Objects.nonNull(categoryPageQueryDTO.getName()), Category::getName, categoryPageQueryDTO.getName())
				.eq(Objects.nonNull(categoryPageQueryDTO.getType()), Category::getType, categoryPageQueryDTO.getType())
				.orderByAsc(Category::getCreateTime);
		PageInfo<Category> pageInfo = PageMethod
				.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize())
				.doSelectPageInfo(() -> baseMapper.selectList(queryWrapper));

		return new PageResult(pageInfo.getTotal(), pageInfo.getList());
	}

	/**
	 * 根据id删除分类
	 * @param id id
	 */
	@Override
	public void deleteById(Long id) {
		//查询当前分类是否关联了菜品，如果关联了就抛出业务异常
		LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
		dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
		int count = dishMapper.selectCount(dishLambdaQueryWrapper);
		if (count > 0) {
			//当前分类下有菜品，不能删除
			throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
		}

		//查询当前分类是否关联了套餐，如果关联了就抛出业务异常
		LambdaQueryWrapper<SetMeal> setMealLambdaQueryWrapper = new LambdaQueryWrapper<>();
		setMealLambdaQueryWrapper.eq(SetMeal::getCategoryId, id);
		count = setmealMapper.selectCount(setMealLambdaQueryWrapper);
		if (count > 0) {
			//当前分类下有菜品，不能删除
			throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
		}

		//删除分类数据
		categoryMapper.deleteById(id);
	}

	/**
	 * 修改分类
	 * @param categoryDTO dto
	 */
	@Override
	public void update(CategoryDTO categoryDTO) {
		Category category = new Category();
		BeanUtils.copyProperties(categoryDTO, category);

		//设置修改人
		category.setUpdateUser(BaseContext.getCurrentId());

		categoryMapper.updateById(category);
	}

	/**
	 * 启用、禁用分类
	 * @param status status
	 * @param id id
	 */
	@Override
	public void startOrStop(Integer status, Long id) {
		Category category = Category.builder().id(id).status(status).build();
		categoryMapper.updateById(category);
	}

	/**
	 * 根据类型查询分类
	 * @param type type
	 * @return list
	 */
	@Override
	public List<Category> list(Integer type) {
		LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(Objects.nonNull(type), Category::getType, type);
		return categoryMapper.selectList(queryWrapper);
	}
}
