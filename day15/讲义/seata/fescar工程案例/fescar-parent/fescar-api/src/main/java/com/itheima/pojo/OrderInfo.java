package com.itheima.pojo;
import javax.persistence.*;
import java.io.Serializable;
import java.lang.String;
import java.lang.Integer;
/****
 * @Author:shenkunlin
 * @Description:OrderInfo构建
 * @Date 2019/6/14 19:13
 *****/
@Table(name="order_info")
public class OrderInfo implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Integer id;//主键

    @Column(name = "message")
	private String message;//留言

    @Column(name = "money")
	private Integer money;//总金额



	//get方法
	public Integer getId() {
		return id;
	}

	//set方法
	public void setId(Integer id) {
		this.id = id;
	}
	//get方法
	public String getMessage() {
		return message;
	}

	//set方法
	public void setMessage(String message) {
		this.message = message;
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
