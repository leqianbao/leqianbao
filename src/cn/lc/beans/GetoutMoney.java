package cn.lc.beans;

import java.util.Date;

public class GetoutMoney {
	
	private Integer getout_id;
	private String getout_num;
	private Double getout_money;
	private String getout_bankcode;
	private String getout_bankname;
	
	private String created_by;
	private Date stamp_created;
	private String updated_by;
	private Date stamp_updated;
	private String state;
	
	private User user;

	public Integer getGetout_id() {
		return getout_id;
	}

	public void setGetout_id(Integer getout_id) {
		this.getout_id = getout_id;
	}

	public String getGetout_num() {
		return getout_num;
	}

	public void setGetout_num(String getout_num) {
		this.getout_num = getout_num;
	}

	public Double getGetout_money() {
		return getout_money;
	}

	public void setGetout_money(Double getout_money) {
		this.getout_money = getout_money;
	}

	public String getGetout_bankcode() {
		return getout_bankcode;
	}

	public void setGetout_bankcode(String getout_bankcode) {
		this.getout_bankcode = getout_bankcode;
	}

	public String getGetout_bankname() {
		return getout_bankname;
	}

	public void setGetout_bankname(String getout_bankname) {
		this.getout_bankname = getout_bankname;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public Date getStamp_created() {
		return stamp_created;
	}

	public void setStamp_created(Date stamp_created) {
		this.stamp_created = stamp_created;
	}

	public String getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}

	public Date getStamp_updated() {
		return stamp_updated;
	}

	public void setStamp_updated(Date stamp_updated) {
		this.stamp_updated = stamp_updated;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
