package com.changgou.order.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/****
 * @Author:admin
 * @Description:OrderConfig构建
 * @Date 2019/6/14 19:13
 *****/
@Table(name="tb_order_config")
public class OrderConfig implements Serializable{

	@Id
    @Column(name = "id")
	private Integer id;//ID

    @Column(name = "order_timeout")
	private Integer orderTimeout;//正常订单超时时间（分）

    @Column(name = "seckill_timeout")
	private Integer seckillTimeout;//秒杀订单超时时间（分）

    @Column(name = "take_timeout")
	private Integer takeTimeout;//自动收货（天）

    @Column(name = "service_timeout")
	private Integer serviceTimeout;//售后期限

    @Column(name = "comment_timeout")
	private Integer commentTimeout;//自动五星好评



	//get方法
	public Integer getId() {
		return id;
	}

	//set方法
	public void setId(Integer id) {
		this.id = id;
	}
	//get方法
	public Integer getOrderTimeout() {
		return orderTimeout;
	}

	//set方法
	public void setOrderTimeout(Integer orderTimeout) {
		this.orderTimeout = orderTimeout;
	}
	//get方法
	public Integer getSeckillTimeout() {
		return seckillTimeout;
	}

	//set方法
	public void setSeckillTimeout(Integer seckillTimeout) {
		this.seckillTimeout = seckillTimeout;
	}
	//get方法
	public Integer getTakeTimeout() {
		return takeTimeout;
	}

	//set方法
	public void setTakeTimeout(Integer takeTimeout) {
		this.takeTimeout = takeTimeout;
	}
	//get方法
	public Integer getServiceTimeout() {
		return serviceTimeout;
	}

	//set方法
	public void setServiceTimeout(Integer serviceTimeout) {
		this.serviceTimeout = serviceTimeout;
	}
	//get方法
	public Integer getCommentTimeout() {
		return commentTimeout;
	}

	//set方法
	public void setCommentTimeout(Integer commentTimeout) {
		this.commentTimeout = commentTimeout;
	}


}
