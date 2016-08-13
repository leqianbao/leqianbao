package cn.lc.beans;

import java.sql.Timestamp;

public class IntegralRecord {
	private int id;
	private String user_id;
	private int integral;
	private Timestamp create_time;
	private String comment;
	private int record_state;
	private String user_name;
	
	
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getIntegral() {
		return integral;
	}
	public void setIntegral(int integral) {
		this.integral = integral;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getState() {
		return record_state;
	}
	public void setState(int state) {
		this.record_state = state;
	}
}
