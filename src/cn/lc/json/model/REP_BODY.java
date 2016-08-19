package cn.lc.json.model;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class REP_BODY<T> {
	private String RSPCOD;
	private String RSPMSG;
	private T data;
	
	@JSONField  (name = "RSPCOD" )
	public String getRSPCOD() {
		return RSPCOD;
	}
	@JSONField  (name = "RSPCOD" )
	public void setRSPCOD(String RSPCOD) {
		this.RSPCOD = RSPCOD;
	}
	@JSONField  (name = "RSPMSG")
	public String getRSPMSG() {
		return RSPMSG;
	}
	@JSONField  (name = "RSPMSG")
	public void setRSPMSG(String RSPMSG) {
		this.RSPMSG = RSPMSG;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
}
