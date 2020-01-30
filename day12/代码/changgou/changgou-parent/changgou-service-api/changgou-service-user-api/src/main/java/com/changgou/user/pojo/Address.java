package com.changgou.user.pojo;

import javax.persistence.*;
import java.io.Serializable;

/****
 * @Author:admin
 * @Description:Address构建
 * @Date 2019/6/14 19:13
 *****/
@Table(name="tb_address")
public class Address implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Integer id;//

    @Column(name = "username")
	private String username;//用户名

    @Column(name = "provinceid")
	private String provinceid;//省

    @Column(name = "cityid")
	private String cityid;//市

    @Column(name = "areaid")
	private String areaid;//县/区

    @Column(name = "phone")
	private String phone;//电话

    @Column(name = "address")
	private String address;//详细地址

    @Column(name = "contact")
	private String contact;//联系人

    @Column(name = "is_default")
	private String isDefault;//是否是默认 1默认 0否

    @Column(name = "alias")
	private String alias;//别名



	//get方法
	public Integer getId() {
		return id;
	}

	//set方法
	public void setId(Integer id) {
		this.id = id;
	}
	//get方法
	public String getUsername() {
		return username;
	}

	//set方法
	public void setUsername(String username) {
		this.username = username;
	}
	//get方法
	public String getProvinceid() {
		return provinceid;
	}

	//set方法
	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}
	//get方法
	public String getCityid() {
		return cityid;
	}

	//set方法
	public void setCityid(String cityid) {
		this.cityid = cityid;
	}
	//get方法
	public String getAreaid() {
		return areaid;
	}

	//set方法
	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}
	//get方法
	public String getPhone() {
		return phone;
	}

	//set方法
	public void setPhone(String phone) {
		this.phone = phone;
	}
	//get方法
	public String getAddress() {
		return address;
	}

	//set方法
	public void setAddress(String address) {
		this.address = address;
	}
	//get方法
	public String getContact() {
		return contact;
	}

	//set方法
	public void setContact(String contact) {
		this.contact = contact;
	}
	//get方法
	public String getIsDefault() {
		return isDefault;
	}

	//set方法
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	//get方法
	public String getAlias() {
		return alias;
	}

	//set方法
	public void setAlias(String alias) {
		this.alias = alias;
	}


}
