package cn.lc.beans;

import java.util.Date;

public class Finance {
	
	private Integer finance_id;
	private String finance_num;
	private Double finance_money;
	private Integer is_count;
	
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
    private User user;
	private FinanceProduct financeProduct;
	
	public Integer getFinance_id() {
		return finance_id;
	}
	public void setFinance_id(Integer finance_id) {
		this.finance_id = finance_id;
	}
	public String getFinance_num() {
		return finance_num;
	}
	public void setFinance_num(String finance_num) {
		this.finance_num = finance_num;
	}
	public Double getFinance_money() {
		return finance_money;
	}
	public void setFinance_money(Double finance_money) {
		this.finance_money = finance_money;
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

	public FinanceProduct getFinanceProduct() {
		return financeProduct;
	}
	public void setFinanceProduct(FinanceProduct financeProduct) {
		this.financeProduct = financeProduct;
	}
	public Finance() {
	}
	
	

}
