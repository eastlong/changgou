package com.changgou.order.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/****
 * @Author:admin
 * @Description:OrderLog构建
 * @Date 2019/6/14 19:13
 *****/
@Table(name="tb_order_log")
public class OrderLog implements Serializable{

	@Id
    @Column(name = "id")
	private String id;//ID

    @Column(name = "operater")
	private String operater;//操作员

    @Column(name = "operate_time")
	private Date operateTime;//操作时间

    @Column(name = "order_id")
	private String orderId;//订单ID

    @Column(name = "order_status")
	private String orderStatus;//订单状态,0未完成，1已完成，2，已退货

    @Column(name = "pay_status")
	private String payStatus;//付款状态

    @Column(name = "consign_status")
	private String consignStatus;//发货状态

    @Column(name = "remarks")
	private String remarks;//备注

    @Column(name = "money")
	private Integer money;//支付金额

    @Column(name = "username")
	private String username;//



	//get方法
	public String getId() {
		return id;
	}

	//set方法
	public void setId(String id) {
		this.id = id;
	}
	//get方法
	public String getOperater() {
		return operater;
	}

	//set方法
	public void setOperater(String operater) {
		this.operater = operater;
	}
	//get方法
	public Date getOperateTime() {
		return operateTime;
	}

	//set方法
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
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
	public String getOrderStatus() {
		return orderStatus;
	}

	//set方法
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	//get方法
	public String getPayStatus() {
		return payStatus;
	}

	//set方法
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	//get方法
	public String getConsignStatus() {
		return consignStatus;
	}

	//set方法
	public void setConsignStatus(String consignStatus) {
		this.consignStatus = consignStatus;
	}
	//get方法
	public String getRemarks() {
		return remarks;
	}

	//set方法
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	public String getUsername() {
		return username;
	}

	//set方法
	public void setUsername(String username) {
		this.username = username;
	}


}
