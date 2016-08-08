package cn.lc.beans;

import java.util.Date;

public class BankCard {
	
	private Integer bank_id;
	private String card_ownername;
	private String card_number;
	private String bank_name;
	private String bank_code;
	
	private String user_id;   
	
	private String created_by;
	private Date stamp_created;
	private String updated_by;
	private Date stamp_updated;
	private String state;
	

	
	
	public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Integer getBank_id() {
		return bank_id;
	}

	public void setBank_id(Integer bank_id) {
		this.bank_id = bank_id;
	}

	public String getCard_ownername() {
		return card_ownername;
	}

	public void setCard_ownername(String card_ownername) {
		this.card_ownername = card_ownername;
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

	public String getBank_code() {
		return bank_code;
	}

	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
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
