package com.changgou.order.pojo;

import javax.persistence.*;
import java.io.Serializable;

/****
 * @Author:admin
 * @Description:ReturnCause构建
 * @Date 2019/6/14 19:13
 *****/
@Table(name="tb_return_cause")
public class ReturnCause implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Integer id;//ID

    @Column(name = "cause")
	private String cause;//原因

    @Column(name = "seq")
	private Integer seq;//排序

    @Column(name = "status")
	private String status;//是否启用



	//get方法
	public Integer getId() {
		return id;
	}

	//set方法
	public void setId(Integer id) {
		this.id = id;
	}
	//get方法
	public String getCause() {
		return cause;
	}

	//set方法
	public void setCause(String cause) {
		this.cause = cause;
	}
	//get方法
	public Integer getSeq() {
		return seq;
	}

	//set方法
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	//get方法
	public String getStatus() {
		return status;
	}

	//set方法
	public void setStatus(String status) {
		this.status = status;
	}


}
