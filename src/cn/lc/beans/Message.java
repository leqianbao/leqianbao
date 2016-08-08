package cn.lc.beans;

import java.util.Date;

public class Message {
	
	private Integer msg_id;
	private String msg_title;
	private String msg_type;
	private String user_id;
	private String msg_content;
	
	private String created_by;
	private Date stamp_created;
	private String updated_by;
	private Date stamp_updated;
	private String state;
	
	private User user;
	
	
	
	public Message() {
	}
	
	
	
	public String getUser_id() {
        return user_id;
    }



    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }



    public String getMsg_type() {
		return msg_type;
	}


	public void setMsg_type(String msg_type) {
		this.msg_type = msg_type;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Integer getMsg_id() {
		return msg_id;
	}
	public void setMsg_id(Integer msg_id) {
		this.msg_id = msg_id;
	}
	public String getMsg_title() {
		return msg_title;
	}
	public void setMsg_title(String msg_title) {
		this.msg_title = msg_title;
	}
	public String getMsg_content() {
		return msg_content;
	}
	public void setMsg_content(String msg_content) {
		this.msg_content = msg_content;
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


	
}
