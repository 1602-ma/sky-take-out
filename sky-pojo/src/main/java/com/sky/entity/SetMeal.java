package com.sky.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 套餐
 * @author feng
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "setmeal")
public class SetMeal extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /** 分类id */
    private Long categoryId;

    /** 套餐名称 */
    private String name;

    /** 套餐价格 */
    private BigDecimal price;

    /** 状态 0:停用 1:启用 */
    private Integer status;

    /** 描述信息 */
    private String description;

    /** 图片 */
    private String image;
}
