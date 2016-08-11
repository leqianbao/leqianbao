package cn.lc.beans;

import java.util.Date;

public class SysManager {
	
	private Integer manager_id ;
	private String mgr_loginid;
	private String mgr_passwd;
	private String mgr_name;
	private String mgr_sex;
	private String mgr_phone;
	private String mgr_address;
	private String role_ids;
	
	
	
	private String created_by;
	private Date stamp_created;
	private String updated_by;
	private Date stamp_updated;
	private String state;
	
	
    public Integer getManager_id() {
        return manager_id;
    }
    public void setManager_id(Integer manager_id) {
        this.manager_id = manager_id;
    }
    public String getMgr_loginid() {
        return mgr_loginid;
    }
    public void setMgr_loginid(String mgr_loginid) {
        this.mgr_loginid = mgr_loginid;
    }
    public String getMgr_passwd() {
        return mgr_passwd;
    }
    public void setMgr_passwd(String mgr_passwd) {
        this.mgr_passwd = mgr_passwd;
    }
    public String getMgr_name() {
        return mgr_name;
    }
    public void setMgr_name(String mgr_name) {
        this.mgr_name = mgr_name;
    }
    public String getMgr_sex() {
        return mgr_sex;
    }
    public void setMgr_sex(String mgr_sex) {
        this.mgr_sex = mgr_sex;
    }
    public String getMgr_phone() {
        return mgr_phone;
    }
    public void setMgr_phone(String mgr_phone) {
        this.mgr_phone = mgr_phone;
    }
    
    public String getMgr_address() {
        return mgr_address;
    }
    public void setMgr_address(String mgr_address) {
        this.mgr_address = mgr_address;
    }
    public String getRole_ids() {
        return role_ids;
    }
    public void setRole_ids(String role_ids) {
        this.role_ids = role_ids;
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
