package com.itheima.pojo;
import javax.persistence.*;
import java.io.Serializable;
import java.lang.String;
import java.lang.Integer;
/****
 * @Author:shenkunlin
 * @Description:UserInfo构建
 * @Date 2019/6/14 19:13
 *****/
@Table(name="user_info")
public class UserInfo implements Serializable{

	@Id
    @Column(name = "account")
	private String account;//

    @Column(name = "money")
	private Integer money;//

    @Column(name = "name")
	private String name;//



	//get方法
	public String getAccount() {
		return account;
	}

	//set方法
	public void setAccount(String account) {
		this.account = account;
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
	public String getName() {
		return name;
	}

	//set方法
	public void setName(String name) {
		this.name = name;
	}


}
