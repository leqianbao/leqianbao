package cn.lc.beans;

import java.util.Date;

public class FeedBack {
    
    private Integer feedback_id;
    private String feedback_type;      //类型
    private String feedback_content;   //内容
    private Integer is_read; 
    private String user_id;   
    private String created_by;
    private Date stamp_created;
    private String updated_by;
    private Date stamp_updated;
    private String state;
    

    
    
    public FeedBack() {
    }
    public Integer getIs_read() {
        return is_read;
    }
    public void setIs_read(Integer is_read) {
        this.is_read = is_read;
    }
    public Integer getFeedback_id() {
        return feedback_id;
    }
    public void setFeedback_id(Integer feedback_id) {
        this.feedback_id = feedback_id;
    }
    public String getFeedback_type() {
        return feedback_type;
    }
    public void setFeedback_type(String feedback_type) {
        this.feedback_type = feedback_type;
    }
    public String getFeedback_content() {
        return feedback_content;
    }
    public void setFeedback_content(String feedback_content) {
        this.feedback_content = feedback_content;
    }
    public String getUser_id() {
        return user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
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
