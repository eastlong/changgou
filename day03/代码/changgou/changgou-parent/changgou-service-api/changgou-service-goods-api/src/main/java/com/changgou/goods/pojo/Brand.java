package com.changgou.goods.pojo;

import javax.persistence.*;
import java.io.Serializable;

/****
 * @Author:admin
 * @Description:Brand构建
 * @Date 2019/6/14 19:13
 *****/
@Table(name="tb_brand")
public class Brand implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Integer id;//品牌id

    @Column(name = "name")
	private String name;//品牌名称

    @Column(name = "image")
	private String image;//品牌图片地址

    @Column(name = "letter")
	private String letter;//品牌的首字母

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
