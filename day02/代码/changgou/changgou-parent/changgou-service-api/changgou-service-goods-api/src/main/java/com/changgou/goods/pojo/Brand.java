package com.changgou.goods.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;

/****
 * @Author:admin
 * @Description:Brand构建
 * @Date 2019/6/14 19:13
 *****/
@ApiModel(description = "Brand",value = "Brand")
@Table(name="tb_brand")
public class Brand implements Serializable{

	@ApiModelProperty(value = "品牌id",required = false)
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Integer id;//品牌id
	@ApiModelProperty(value = "品牌名称",required = false)
    @Column(name = "name")
	private String name;//品牌名称
	@ApiModelProperty(value = "品牌图片地址",required = false)
    @Column(name = "image")
	private String image;//品牌图片地址
	@ApiModelProperty(value = "品牌的首字母",required = false)
    @Column(name = "letter")
	private String letter;//品牌的首字母
	@ApiModelProperty(value = "排序",required = false)
    @Column(name = "seq")
	private Integer seq;//排序


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
	public String getImage() {
		return image;
	}

	//set方法
	public void setImage(String image) {
		this.image = image;
	}
	//get方法
	public String getLetter() {
		return letter;
	}

	//set方法
	public void setLetter(String letter) {
		this.letter = letter;
	}
	//get方法
	public Integer getSeq() {
		return seq;
	}

	//set方法
	public void setSeq(Integer seq) {
		this.seq = seq;
	}


}
