package cn.lc.beans;

import java.util.Date;

public class Interest {
	
	private Integer interest_id;
	private Double interest_money;
	private Integer is_count;
	private String user_id;
	
	private String created_by;
	private Date stamp_created;
	private String updated_by;
	private Date stamp_updated;
	private String state;
	
	
	
	public Integer getIs_count() {
        return is_count;
    }
    public void setIs_count(Integer is_count) {
        this.is_count = is_count;
    }
    public String getUser_id() {
        return user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    private User user;
	private Finance finance;
	
	public Integer getInterest_id() {
		return interest_id;
	}
	public void setInterest_id(Integer interest_id) {
		this.interest_id = interest_id;
	}
	public Double getInterest_money() {
		return interest_money;
	}
	public void setInterest_money(Double interest_money) {
		this.interest_money = interest_money;
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
	public Finance getFinance() {
		return finance;
	}
	public void setFinance(Finance finance) {
		this.finance = finance;
	}
	
	
	

}
