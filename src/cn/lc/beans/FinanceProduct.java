package cn.lc.beans;

import java.util.Date;

public class FinanceProduct {
	
	private Integer product_id;
	private Double interest_rate;
	private Integer invest_days;
	private Integer invest_months;
	private Integer invest_money;
	private String product_name;
	private String finance_img;
	private String product_desc;
	private Integer is_expire;
	private String created_by;
	private Date stamp_created;
	private String updated_by;
	private Date stamp_updated;
	private String state;
	
	
	public FinanceProduct() {
	}
	
	
	public Integer getIs_expire() {
        return is_expire;
    }


    public void setIs_expire(Integer is_expire) {
        this.is_expire = is_expire;
    }


    public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public Integer getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}
	public Double getInterest_rate() {
		return interest_rate;
	}
	public void setInterest_rate(Double interest_rate) {
		this.interest_rate = interest_rate;
	}
	public Integer getInvest_days() {
		return invest_days;
	}
	public void setInvest_days(Integer invest_days) {
		this.invest_days = invest_days;
	}
	public Integer getInvest_months() {
		return invest_months;
	}
	public void setInvest_months(Integer invest_months) {
		this.invest_months = invest_months;
	}
	public Integer getInvest_money() {
		return invest_money;
	}
	public void setInvest_money(Integer invest_money) {
		this.invest_money = invest_money;
	}
	public String getFinance_img() {
		return finance_img;
	}
	public void setFinance_img(String finance_img) {
		this.finance_img = finance_img;
	}
	public String getProduct_desc() {
		return product_desc;
	}
	public void setProduct_desc(String product_desc) {
		this.product_desc = product_desc;
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
