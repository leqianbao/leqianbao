package cn.lc.beans;

import java.math.BigDecimal;

public class UserChildBean {
	private int user_id;
	private String child_name;
	private String child_phone;
	private String child_id_card;
	private String child_bank_name;
	private String child_bank_account;
	private BigDecimal child_balance;
	
	
	public BigDecimal getChild_balance() {
		return child_balance;
	}
	public void setChild_balance(BigDecimal child_balance) {
		this.child_balance = child_balance;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getChild_name() {
		return child_name;
	}
	public void setChild_name(String child_name) {
		this.child_name = child_name;
	}
	public String getChild_phone() {
		return child_phone;
	}
	public void setChild_phone(String child_phone) {
		this.child_phone = child_phone;
	}
	public String getChild_id_card() {
		return child_id_card;
	}
	public void setChild_id_card(String child_id_card) {
		this.child_id_card = child_id_card;
	}
	public String getChild_bank_name() {
		return child_bank_name;
	}
	public void setChild_bank_name(String child_bank_name) {
		this.child_bank_name = child_bank_name;
	}
	public String getChild_bank_account() {
		return child_bank_account;
	}
	public void setChild_bank_account(String child_bank_account) {
		this.child_bank_account = child_bank_account;
	}
}
