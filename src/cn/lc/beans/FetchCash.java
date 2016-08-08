package cn.lc.beans;

import java.util.Date;


public class FetchCash {
    
    private Integer fetch_id;
    private String fetch_num;
    private Double fetch_money;
    private String card_number;
    private String bank_name;
    private String bank_id;
    private String user_id;
    private String handle_tag;
    
    private String created_by;
    private Date stamp_created;
    private String updated_by;
    private Date stamp_updated;
    private String state;
    
    private User user;
    
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    
    
    public Integer getFetch_id() {
        return fetch_id;
    }
    public void setFetch_id(Integer fetch_id) {
        this.fetch_id = fetch_id;
    }
    public String getFetch_num() {
        return fetch_num;
    }
    public void setFetch_num(String fetch_num) {
        this.fetch_num = fetch_num;
    }
    public Double getFetch_money() {
        return fetch_money;
    }
    public void setFetch_money(Double fetch_money) {
        this.fetch_money = fetch_money;
    }
    public String getCard_number() {
        return card_number;
    }
    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }
    public String getBank_name() {
        return bank_name;
    }
    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }
    public String getBank_id() {
        return bank_id;
    }
    public void setBank_id(String bank_id) {
        this.bank_id = bank_id;
    }
    public String getUser_id() {
        return user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public String getHandle_tag() {
        return handle_tag;
    }
    public void setHandle_tag(String handle_tag) {
        this.handle_tag = handle_tag;
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
