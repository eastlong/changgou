package com.changgou.content.pojo;

import javax.persistence.*;
import java.io.Serializable;

/****
 * @Author:admin
 * @Description:ContentCategory构建
 * @Date 2019/6/14 19:13
 *****/
@Table(name="tb_content_category")
public class ContentCategory implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Long id;//类目ID

    @Column(name = "name")
	private String name;//分类名称



	//get方法
	public Long getId() {
		return id;
	}

	//set方法
	public void setId(Long id) {
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


}
