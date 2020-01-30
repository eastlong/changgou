package com.changgou.user.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/****
 * @Author:admin
 * @Description:Cities构建
 * @Date 2019/6/14 19:13
 *****/
@Table(name="tb_cities")
public class Cities implements Serializable{

	@Id
    @Column(name = "cityid")
	private String cityid;//城市ID

    @Column(name = "city")
	private String city;//城市名称

    @Column(name = "provinceid")
	private String provinceid;//省份ID



	//get方法
	public String getCityid() {
		return cityid;
	}

	//set方法
	public void setCityid(String cityid) {
		this.cityid = cityid;
	}
	//get方法
	public String getCity() {
		return city;
	}

	//set方法
	public void setCity(String city) {
		this.city = city;
	}
	//get方法
	public String getProvinceid() {
		return provinceid;
	}

	//set方法
	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}


}
