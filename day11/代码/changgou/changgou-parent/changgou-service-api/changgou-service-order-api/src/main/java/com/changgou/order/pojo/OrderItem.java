package com.changgou.order.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/****
 * @Author:admin
 * @Description:OrderItem构建
 * @Date 2019/6/14 19:13
 *****/
@Table(name="tb_order_item")
public class OrderItem implements Serializable{

	@Id
    @Column(name = "id")
	private String id;//ID

    @Column(name = "category_id1")
	private Integer categoryId1;//1级分类

    @Column(name = "category_id2")
	private Integer categoryId2;//2级分类

    @Column(name = "category_id3")
	private Integer categoryId3;//3级分类

    @Column(name = "spu_id")
	private Long spuId;//SPU_ID

    @Column(name = "sku_id")
	private Long skuId;//SKU_ID

    @Column(name = "order_id")
	private String orderId;//订单ID

    @Column(name = "name")
	private String name;//商品名称

    @Column(name = "price")
	private Integer price;//单价

    @Column(name = "num")
	private Integer num;//数量

    @Column(name = "money")
	private Integer money;//总金额

    @Column(name = "pay_money")
	private Integer payMoney;//实付金额

    @Column(name = "image")
	private String image;//图片地址

    @Column(name = "weight")
	private Integer weight;//重量

    @Column(name = "post_fee")
	private Integer postFee;//运费

    @Column(name = "is_return")
	private String isReturn;//是否退货,0:未退货，1：已退货



	//get方法
	public String getId() {
		return id;
	}

	//set方法
	public void setId(String id) {
		this.id = id;
	}
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
	public Long getSpuId() {
		return spuId;
	}

	//set方法
	public void setSpuId(Long spuId) {
		this.spuId = spuId;
	}
	//get方法
	public Long getSkuId() {
		return skuId;
	}

	//set方法
	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
	//get方法
	public String getOrderId() {
		return orderId;
	}

	//set方法
	public void setOrderId(String orderId) {
		this.orderId = orderId;
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
	public Integer getPrice() {
		return price;
	}

	//set方法
	public void setPrice(Integer price) {
		this.price = price;
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
	//get方法
	public Integer getPayMoney() {
		return payMoney;
	}

	//set方法
	public void setPayMoney(Integer payMoney) {
		this.payMoney = payMoney;
	}
	//get方法
	public String getImage() {
		return image;
	}

	//set方法
	public void setImage(String image) {
		this.image = image;
	}
	//get方法
	public Integer getWeight() {
		return weight;
	}

	//set方法
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	//get方法
	public Integer getPostFee() {
		return postFee;
	}

	//set方法
	public void setPostFee(Integer postFee) {
		this.postFee = postFee;
	}
	//get方法
	public String getIsReturn() {
		return isReturn;
	}

	//set方法
	public void setIsReturn(String isReturn) {
		this.isReturn = isReturn;
	}


}
