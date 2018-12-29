package com.Educharge.EduchargeAPI.Model;

import java.util.Date;

public class Comment {

	private int cid;	
	private int uid;
	private String cmnt;
	private Date dateCreated;

	public Comment() {
	}
	
	public Comment(int cid, int uid, String cmnt, Date dateCreated) {
		super();
		this.cid = cid;
		this.uid = uid;
		this.cmnt = cmnt;
		this.dateCreated = dateCreated;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getCmnt() {
		return cmnt;
	}
	public void setCmnt(String cmnt) {
		this.cmnt = cmnt;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
}