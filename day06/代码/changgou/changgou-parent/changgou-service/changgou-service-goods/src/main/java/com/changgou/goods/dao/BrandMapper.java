package com.changgou.goods.dao;
import com.changgou.goods.pojo.Brand;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/****
 * @Author:admin
 * @Description:Brand的Dao
 * @Date 2019/6/14 0:12
 *****/
public interface BrandMapper extends Mapper<Brand> {

    @Select(value="select tb.* from tb_brand tb ,tb_category_brand tbc where tb.id = tbc.brand_id and tbc.category_id=#{categoryid}")
    List<Brand> findByCategory(Integer categoryid);
}
