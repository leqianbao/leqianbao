package cn.lc.beans;

import java.util.Date;

public class OrderDetial {
	
	private Integer finance_id;
	private String finance_num;
	private Double finance_money;
	private Date stamp_created;
	private Double interest_rate;
	private Integer invest_days;
	private String product_name;
	private String money_rate;
	
	
	public OrderDetial() {
	}
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
	public Date getStamp_created() {
		return stamp_created;
	}
	public void setStamp_created(Date stamp_created) {
		this.stamp_created = stamp_created;
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
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getMoney_rate() {
		return money_rate;
	}
	public void setMoney_rate(String money_rate) {
		this.money_rate = money_rate;
	}

	
}
