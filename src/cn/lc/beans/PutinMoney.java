package cn.lc.beans;

import java.util.Date;

public class PutinMoney {
	
	private Integer putin_id;
	private String putin_num;
	private Double putin_money;  
	private String putin_desc;
	private Integer is_count;
	private Integer is_show;
	private String user_id;
	
	
	private String created_by;
	private Date stamp_created;
	private String updated_by;
	private Date stamp_updated;
	private String state;
	
	
	
	
	private User user;

	
	
	
	public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public Integer getIs_count() {
        return is_count;
    }

    public void setIs_count(Integer is_count) {
        this.is_count = is_count;
    }

    public Integer getIs_show() {
        return is_show;
    }

    public void setIs_show(Integer is_show) {
        this.is_show = is_show;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Integer getPutin_id() {
		return putin_id;
	}

	public void setPutin_id(Integer putin_id) {
		this.putin_id = putin_id;
	}

	public String getPutin_num() {
		return putin_num;
	}

	public void setPutin_num(String putin_num) {
		this.putin_num = putin_num;
	}

	public Double getPutin_money() {
		return putin_money;
	}

	public void setPutin_money(Double putin_money) {
		this.putin_money = putin_money;
	}

	public String getPutin_desc() {
		return putin_desc;
	}

	public void setPutin_desc(String putin_desc) {
		this.putin_desc = putin_desc;
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
