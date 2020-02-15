package com.zongbang.goods.pojo;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @program: zongbang_parent
 * @description:
 * @author: LiaoHui
 * @create: 2020-01-15 02:02
 **/
@Table(name="undo_log")
public class Log implements Serializable {

	@Id
	private Long id;//id
	private Long branchId;			  //branch_id
	private String xid;               //xid
	private byte[] rollbackInfo;      //rollback_info
	private Integer logStatus;        //log_status
	private Date logCreated;          //log_created
	private Date logModified;         //log_modified
	private String ext;               //ext

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Long getBranchId() {
		return branchId;
	}
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public String getXid() {
		return xid;
	}
	public void setXid(String xid) {
		this.xid = xid;
	}

	public byte[] getRollbackInfo() {
		return rollbackInfo;
	}
	public void setRollbackInfo(byte[] rollbackInfo) {
		this.rollbackInfo = rollbackInfo;
	}

	public Integer getLogStatus() {
		return logStatus;
	}
	public void setLogStatus(Integer logStatus) {
		this.logStatus = logStatus;
	}

	public Date getLogCreated() {
		return logCreated;
	}
	public void setLogCreated(Date logCreated) {
		this.logCreated = logCreated;
	}

	public Date getLogModified() {
		return logModified;
	}
	public void setLogModified(java.util.Date logModified) {
		this.logModified = logModified;
	}

	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}



}
