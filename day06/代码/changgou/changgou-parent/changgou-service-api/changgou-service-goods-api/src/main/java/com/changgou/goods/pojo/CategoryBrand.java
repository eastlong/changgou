package com.changgou.goods.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/****
 * @Author:admin
 * @Description:CategoryBrand构建
 * @Date 2019/6/14 19:13
 *****/
@Table(name="tb_category_brand")
public class CategoryBrand implements Serializable{

	@Id
    @Column(name = "category_id")
	private Integer categoryId;//分类ID

    @Column(name = "brand_id")
	private Integer brandId;//品牌ID



	//get方法
	public Integer getCategoryId() {
		return categoryId;
	}

	//set方法
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	//get方法
	public Integer getBrandId() {
		return brandId;
	}

	//set方法
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}


}
