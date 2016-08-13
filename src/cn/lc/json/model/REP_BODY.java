package cn.lc.json.model;

import java.util.List;

public class REP_BODY<T> {
	private String RSPCOD;
	private String RSPMSG;
	private T data;
	
	
	public String getRSPCOD() {
		return RSPCOD;
	}
	public void setRSPCOD(String rSPCOD) {
		RSPCOD = rSPCOD;
	}
	public String getRSPMSG() {
		return RSPMSG;
	}
	public void setRSPMSG(String rSPMSG) {
		RSPMSG = rSPMSG;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
}
