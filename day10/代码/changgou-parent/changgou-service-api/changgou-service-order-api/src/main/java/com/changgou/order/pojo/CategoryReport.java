package com.changgou.order.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/****
 * @Author:admin
 * @Description:CategoryReport构建
 * @Date 2019/6/14 19:13
 *****/
@Table(name="tb_category_report")
public class CategoryReport implements Serializable{

    @Column(name = "category_id1")
	private Integer categoryId1;//1级分类

    @Column(name = "category_id2")
	private Integer categoryId2;//2级分类

    @Column(name = "category_id3")
	private Integer categoryId3;//3级分类

	@Id
    @Column(name = "count_date")
	private Date countDate;//统计日期

    @Column(name = "num")
	private Integer num;//销售数量

    @Column(name = "money")
	private Integer money;//销售额



	//get方法
	public Integer getCategoryId1() {
		return categoryId1;
	}

	//set方法
	public void setCategoryId1(Integer categoryId1) {
		this.categoryId1 = categoryId1;
	}
	//get方法
	public Integer getCategoryId2() {
		return categoryId2;
	}

	//set方法
	public void setCategoryId2(Integer categoryId2) {
		this.categoryId2 = categoryId2;
	}
	//get方法
	public Integer getCategoryId3() {
		return categoryId3;
	}

	//set方法
	public void setCategoryId3(Integer categoryId3) {
		this.categoryId3 = categoryId3;
	}
	//get方法
	public Date getCountDate() {
		return countDate;
	}

	//set方法
	public void setCountDate(Date countDate) {
		this.countDate = countDate;
	}
	//get方法
	public Integer getNum() {
		return num;
	}

	//set方法
	public void setNum(Integer num) {
		this.num = num;
	}
	//get方法
	public Integer getMoney() {
		return money;
	}

	//set方法
	public void setMoney(Integer money) {
		this.money = money;
	}


}
