package com.itheima.pojo;
import javax.persistence.*;
import java.io.Serializable;
import java.lang.String;
import java.lang.Integer;
/****
 * @Author:shenkunlin
 * @Description:ItemInfo构建
 * @Date 2019/6/14 19:13
 *****/
@Table(name="item_info")
public class ItemInfo implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Integer id;//

    @Column(name = "name")
	private String name;//商品名称

    @Column(name = "count")
	private Integer count;//商品数量

    @Column(name = "price")
	private Integer price;//商品价格



	//get方法
	public Integer getId() {
		return id;
	}

	//set方法
	public void setId(Integer id) {
		this.id = id;
	}
	//get方法
	public String getName() {
		return name;
	}

	//set方法
	public void setName(String name) {
		this.name = name;
	}
	//get方法
	public Integer getCount() {
		return count;
	}

	//set方法
	public void setCount(Integer count) {
		this.count = count;
	}
	//get方法
	public Integer getPrice() {
		return price;
	}

	//set方法
	public void setPrice(Integer price) {
		this.price = price;
	}


}
