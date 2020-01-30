package com.changgou.seckill.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/****
 * @Author:admin
 * @Description:SeckillOrder构建
 * @Date 2019/6/14 19:13
 *****/
@Table(name="tb_seckill_order")
public class SeckillOrder implements Serializable{

	@Id
    @Column(name = "id")
	private Long id;//主键

    @Column(name = "seckill_id")
	private Long seckillId;//秒杀商品ID

    @Column(name = "money")
	private String money;//支付金额

    @Column(name = "user_id")
	private String userId;//用户

    @Column(name = "create_time")
	private Date createTime;//创建时间

    @Column(name = "pay_time")
	private Date payTime;//支付时间

    @Column(name = "status")
	private String status;//状态，0未支付，1已支付

    @Column(name = "receiver_address")
	private String receiverAddress;//收货人地址

    @Column(name = "receiver_mobile")
	private String receiverMobile;//收货人电话

    @Column(name = "receiver")
	private String receiver;//收货人

    @Column(name = "transaction_id")
	private String transactionId;//交易流水



	//get方法
	public Long getId() {
		return id;
	}

	//set方法
	public void setId(Long id) {
		this.id = id;
	}
	//get方法
	public Long getSeckillId() {
		return seckillId;
	}

	//set方法
	public void setSeckillId(Long seckillId) {
		this.seckillId = seckillId;
	}
	//get方法
	public String getMoney() {
		return money;
	}

	//set方法
	public void setMoney(String money) {
		this.money = money;
	}
	//get方法
	public String getUserId() {
		return userId;
	}

	//set方法
	public void setUserId(String userId) {
		this.userId = userId;
	}
	//get方法
	public Date getCreateTime() {
		return createTime;
	}

	//set方法
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	//get方法
	public Date getPayTime() {
		return payTime;
	}

	//set方法
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	//get方法
	public String getStatus() {
		return status;
	}

	//set方法
	public void setStatus(String status) {
		this.status = status;
	}
	//get方法
	public String getReceiverAddress() {
		return receiverAddress;
	}

	//set方法
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	//get方法
	public String getReceiverMobile() {
		return receiverMobile;
	}

	//set方法
	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}
	//get方法
	public String getReceiver() {
		return receiver;
	}

	//set方法
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	//get方法
	public String getTransactionId() {
		return transactionId;
	}

	//set方法
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}


}
