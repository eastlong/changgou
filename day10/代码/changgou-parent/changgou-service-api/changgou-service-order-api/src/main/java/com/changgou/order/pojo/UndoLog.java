package com.changgou.order.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/****
 * @Author:admin
 * @Description:UndoLog构建
 * @Date 2019/6/14 19:13
 *****/
@Table(name="undo_log")
public class UndoLog implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Long id;//

    @Column(name = "branch_id")
	private Long branchId;//

    @Column(name = "xid")
	private String xid;//

    @Column(name = "rollback_info")
	private String rollbackInfo;//

    @Column(name = "log_status")
	private Integer logStatus;//

    @Column(name = "log_created")
	private Date logCreated;//

    @Column(name = "log_modified")
	private Date logModified;//

    @Column(name = "ext")
	private String ext;//



	//get方法
	public Long getId() {
		return id;
	}

	//set方法
	public void setId(Long id) {
		this.id = id;
	}
	//get方法
	public Long getBranchId() {
		return branchId;
	}

	//set方法
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}
	//get方法
	public String getXid() {
		return xid;
	}

	//set方法
	public void setXid(String xid) {
		this.xid = xid;
	}
	//get方法
	public String getRollbackInfo() {
		return rollbackInfo;
	}

	//set方法
	public void setRollbackInfo(String rollbackInfo) {
		this.rollbackInfo = rollbackInfo;
	}
	//get方法
	public Integer getLogStatus() {
		return logStatus;
	}

	//set方法
	public void setLogStatus(Integer logStatus) {
		this.logStatus = logStatus;
	}
	//get方法
	public Date getLogCreated() {
		return logCreated;
	}

	//set方法
	public void setLogCreated(Date logCreated) {
		this.logCreated = logCreated;
	}
	//get方法
	public Date getLogModified() {
		return logModified;
	}

	//set方法
	public void setLogModified(Date logModified) {
		this.logModified = logModified;
	}
	//get方法
	public String getExt() {
		return ext;
	}

	//set方法
	public void setExt(String ext) {
		this.ext = ext;
	}


}
