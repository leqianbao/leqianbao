package cn.lc.beans;

import java.math.BigDecimal;

public class Commodity {
	
	private String commodity_id;
    private String commodity_name;      //商品名称
    private Integer commodity_num;   //商品数量
    private Integer commodity_pay;  //商品价格
    private BigDecimal commodity_money;  //商品价格（现金）
    private Integer commodity_blend_integral;  //商品价格（混合：积分）
    private BigDecimal commodity_blend_money;  //商品价格（混合：现金）
    private String commodity_imgurl;   //商品图片路径
    private String commodity_type; //商品出售状态
    private String commodity_use_flag; //商品出售状态
    private String commodity_comment; //商品简介
    private String delete_flag;//停用标记
    private String create_date;//创建时间
    private String update_date;//更新时间
    
    public Commodity(){
    	
    }
    
    public Commodity(String commodity_id, String commodity_name, Integer commodity_num, Integer commodity_pay,
    			BigDecimal commodity_money, Integer commodity_blend_integral, BigDecimal commodity_blend_money,
    			String commodity_imgurl, String commodity_type, String commodity_use_flag, String commodity_comment){
    	this.commodity_id = commodity_id;
    	this.commodity_name = commodity_name;
    	this.commodity_num = commodity_num;
    	this.commodity_pay = commodity_pay;
    	this.commodity_money = commodity_money;
    	this.commodity_blend_integral = commodity_blend_integral;
    	this.commodity_blend_money = commodity_blend_money;
    	this.commodity_imgurl = commodity_imgurl;
    	this.commodity_type = commodity_type;
    	this.commodity_use_flag = commodity_use_flag;
    	this.commodity_comment = commodity_comment;
    }
    
	public String getCommodity_id() {
		return commodity_id;
	}
	public void setCommodity_id(String commodity_id) {
		this.commodity_id = commodity_id;
	}
	public String getCommodity_name() {
		return commodity_name;
	}
	public void setCommodity_name(String commodity_name) {
		this.commodity_name = commodity_name;
	}
	public Integer getCommodity_num() {
		return commodity_num;
	}
	public void setCommodity_num(Integer commodity_num) {
		this.commodity_num = commodity_num;
	}
	public Integer getCommodity_pay() {
		return commodity_pay;
	}
	public void setCommodity_pay(Integer commodity_pay) {
		this.commodity_pay = commodity_pay;
	}
	public String getCommodity_imgurl() {
		return commodity_imgurl;
	}
	public void setCommodity_imgurl(String commodity_imgurl) {
		this.commodity_imgurl = commodity_imgurl;
	}
	public String getCommodity_type() {
		return commodity_type;
	}
	public void setCommodity_type(String commodity_type) {
		this.commodity_type = commodity_type;
	}
	public String getCommodity_use_flag() {
		return commodity_use_flag;
	}
	public void setCommodity_use_flag(String commodity_use_flag) {
		this.commodity_use_flag = commodity_use_flag;
	}
	public String getCommodity_comment() {
		return commodity_comment;
	}
	public void setCommodity_comment(String commodity_comment) {
		this.commodity_comment = commodity_comment;
	}
	public String getdDelete_flag() {
		return delete_flag;
	}
	public void setdDelete_flag(String delete_flag) {
		this.delete_flag = delete_flag;
	}

	public String getDelete_flag() {
		return delete_flag;
	}

	public void setDelete_flag(String delete_flag) {
		this.delete_flag = delete_flag;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public String getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}

	public BigDecimal getCommodity_money() {
		return commodity_money;
	}

	public void setCommodity_money(BigDecimal commodity_money) {
		this.commodity_money = commodity_money;
	}

	public Integer getCommodity_blend_integral() {
		return commodity_blend_integral;
	}

	public void setCommodity_blend_integral(Integer commodity_blend_integral) {
		this.commodity_blend_integral = commodity_blend_integral;
	}

	public BigDecimal getCommodity_blend_money() {
		return commodity_blend_money;
	}

	public void setCommodity_blend_money(BigDecimal commodity_blend_money) {
		this.commodity_blend_money = commodity_blend_money;
	}

}
