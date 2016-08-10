package cn.lc.beans;

import java.sql.Timestamp;

public class IntegralBean {
	private int user_id;
	private int user_integral;
	private Timestamp update_date;
	
	
	
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getUser_integral() {
		return user_integral;
	}
	public void setUser_integral(int user_integral) {
		this.user_integral = user_integral;
	}
	public Timestamp getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(Timestamp update_date) {
		this.update_date = update_date;
	}
	
}
