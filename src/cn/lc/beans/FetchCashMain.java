package cn.lc.beans;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class FetchCashMain {
	private int main_id;
	private String main_no;
	private int user_id;
	private String create_date;
	private String main_state;
	private BigDecimal fetch_money;
	public int getMain_id() {
		return main_id;
	}
	public String getMain_state() {
		return main_state;
	}
	public void setMain_state(String main_state) {
		this.main_state = main_state;
	}
	public BigDecimal getFetch_money() {
		return fetch_money;
	}
	public void setFetch_money(BigDecimal fetch_money) {
		this.fetch_money = fetch_money;
	}
	public void setMain_id(int main_id) {
		this.main_id = main_id;
	}
	public String getMain_no() {
		return main_no;
	}
	public void setMain_no(String main_no) {
		this.main_no = main_no;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	
}
