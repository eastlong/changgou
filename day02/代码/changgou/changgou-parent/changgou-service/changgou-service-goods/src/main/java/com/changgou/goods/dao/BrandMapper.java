package com.changgou.goods.dao;

import com.changgou.goods.pojo.Brand;
import tk.mybatis.mapper.common.Mapper;

/**
 * 描述
 *
 * @author 三国的包子
 * @version 1.0
 * @package com.changgou.dao *
 * @since 1.0
 */

/**
 * Mapper<Brand>  指定通用的mapper的父接口:封装了所有的CRUD的操作
 * T  指定操作的表对应的POJO
 */
public interface BrandMapper extends Mapper<Brand> {
}
