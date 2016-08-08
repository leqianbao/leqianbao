package cn.lc.json.model;

import java.io.Serializable;

public class REQ_HEAD implements Serializable{
	
	private static final long serialVersionUID = 1563865088230828124L;
	private String SIGN;

	public void setSIGN(String SIGN) {
		this.SIGN = SIGN;
	}

	public String getSIGN() {
		return this.SIGN;
	}
}
