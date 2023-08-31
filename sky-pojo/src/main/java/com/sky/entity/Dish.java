package com.sky.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 菜品
 * @author feng
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "dish")
public class Dish extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /** 菜品名称 */
    private String name;

    /** 菜品分类id */
    private Long categoryId;

    /** 菜品价格 */
    private BigDecimal price;

    /** 图片 */
    private String image;

    /** 描述信息 */
    private String description;

    /** 0 停售 1 起售 */
    private Integer status;
}
