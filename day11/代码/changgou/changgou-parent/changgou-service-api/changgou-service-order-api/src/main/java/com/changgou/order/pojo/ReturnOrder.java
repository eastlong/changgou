package com.changgou.order.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/****
 * @Author:admin
 * @Description:ReturnOrder构建
 * @Date 2019/6/14 19:13
 *****/
@Table(name="tb_return_order")
public class ReturnOrder implements Serializable{

	@Id
    @Column(name = "id")
	private Long id;//服务单号

    @Column(name = "order_id")
	private Long orderId;//订单号

    @Column(name = "apply_time")
	private Date applyTime;//申请时间

    @Column(name = "user_id")
	private Long userId;//用户ID

    @Column(name = "user_account")
	private String userAccount;//用户账号

    @Column(name = "linkman")
	private String linkman;//联系人

    @Column(name = "linkman_mobile")
	private String linkmanMobile;//联系人手机

    @Column(name = "type")
	private String type;//类型

    @Column(name = "return_money")
	private Integer returnMoney;//退款金额

    @Column(name = "is_return_freight")
	private String isReturnFreight;//是否退运费

    @Column(name = "status")
	private String status;//申请状态

    @Column(name = "dispose_time")
	private Date disposeTime;//处理时间

    @Column(name = "return_cause")
	private Integer returnCause;//退货退款原因

    @Column(name = "evidence")
	private String evidence;//凭证图片

    @Column(name = "description")
	private String description;//问题描述

    @Column(name = "remark")
	private String remark;//处理备注

    @Column(name = "admin_id")
	private Integer adminId;//管理员id



	//get方法
	public Long getId() {
		return id;
	}

	//set方法
	public void setId(Long id) {
		this.id = id;
	}
	//get方法
	public Long getOrderId() {
		return orderId;
	}

	//set方法
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	//get方法
	public Date getApplyTime() {
		return applyTime;
	}

	//set方法
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	//get方法
	public Long getUserId() {
		return userId;
	}

	//set方法
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	//get方法
	public String getUserAccount() {
		return userAccount;
	}

	//set方法
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	//get方法
	public String getLinkman() {
		return linkman;
	}

	//set方法
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	//get方法
	public String getLinkmanMobile() {
		return linkmanMobile;
	}

	//set方法
	public void setLinkmanMobile(String linkmanMobile) {
		this.linkmanMobile = linkmanMobile;
	}
	//get方法
	public String getType() {
		return type;
	}

	//set方法
	public void setType(String type) {
		this.type = type;
	}
	//get方法
	public Integer getReturnMoney() {
		return returnMoney;
	}

	//set方法
	public void setReturnMoney(Integer returnMoney) {
		this.returnMoney = returnMoney;
	}
	//get方法
	public String getIsReturnFreight() {
		return isReturnFreight;
	}

	//set方法
	public void setIsReturnFreight(String isReturnFreight) {
		this.isReturnFreight = isReturnFreight;
	}
	//get方法
	public String getStatus() {
		return status;
	}

	//set方法
	public void setStatus(String status) {
		this.status = status;
	}
	//get方法
	public Date getDisposeTime() {
		return disposeTime;
	}

	//set方法
	public void setDisposeTime(Date disposeTime) {
		this.disposeTime = disposeTime;
	}
	//get方法
	public Integer getReturnCause() {
		return returnCause;
	}

	//set方法
	public void setReturnCause(Integer returnCause) {
		this.returnCause = returnCause;
	}
	//get方法
	public String getEvidence() {
		return evidence;
	}

	//set方法
	public void setEvidence(String evidence) {
		this.evidence = evidence;
	}
	//get方法
	public String getDescription() {
		return description;
	}

	//set方法
	public void setDescription(String description) {
		this.description = description;
	}
	//get方法
	public String getRemark() {
		return remark;
	}

	//set方法
	public void setRemark(String remark) {
		this.remark = remark;
	}
	//get方法
	public Integer getAdminId() {
		return adminId;
	}

	//set方法
	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}


}
