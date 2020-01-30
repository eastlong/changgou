package com.changgou.order.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/****
 * @Author:admin
 * @Description:Preferential构建
 * @Date 2019/6/14 19:13
 *****/
@Table(name="tb_preferential")
public class Preferential implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Integer id;//ID

    @Column(name = "buy_money")
	private Integer buyMoney;//消费金额

    @Column(name = "pre_money")
	private Integer preMoney;//优惠金额

    @Column(name = "category_id")
	private Long categoryId;//品类ID

    @Column(name = "start_time")
	private Date startTime;//活动开始日期

    @Column(name = "end_time")
	private Date endTime;//活动截至日期

    @Column(name = "state")
	private String state;//状态

    @Column(name = "type")
	private String type;//类型1不翻倍 2翻倍



	//get方法
	public Integer getId() {
		return id;
	}

	//set方法
	public void setId(Integer id) {
		this.id = id;
	}
	//get方法
	public Integer getBuyMoney() {
		return buyMoney;
	}

	//set方法
	public void setBuyMoney(Integer buyMoney) {
		this.buyMoney = buyMoney;
	}
	//get方法
	public Integer getPreMoney() {
		return preMoney;
	}

	//set方法
	public void setPreMoney(Integer preMoney) {
		this.preMoney = preMoney;
	}
	//get方法
	public Long getCategoryId() {
		return categoryId;
	}

	//set方法
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	//get方法
	public Date getStartTime() {
		return startTime;
	}

	//set方法
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	//get方法
	public Date getEndTime() {
		return endTime;
	}

	//set方法
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	//get方法
	public String getState() {
		return state;
	}

	//set方法
	public void setState(String state) {
		this.state = state;
	}
	//get方法
	public String getType() {
		return type;
	}

	//set方法
	public void setType(String type) {
		this.type = type;
	}


}
