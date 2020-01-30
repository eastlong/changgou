package com.changgou.goods.pojo;

import javax.persistence.*;
import java.io.Serializable;

/****
 * @Author:admin
 * @Description:Template构建
 * @Date 2019/6/14 19:13
 *****/
@Table(name="tb_template")
public class Template implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Integer id;//ID

    @Column(name = "name")
	private String name;//模板名称

    @Column(name = "spec_num")
	private Integer specNum;//规格数量

    @Column(name = "para_num")
	private Integer paraNum;//参数数量



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
	public Integer getSpecNum() {
		return specNum;
	}

	//set方法
	public void setSpecNum(Integer specNum) {
		this.specNum = specNum;
	}
	//get方法
	public Integer getParaNum() {
		return paraNum;
	}

	//set方法
	public void setParaNum(Integer paraNum) {
		this.paraNum = paraNum;
	}


}
