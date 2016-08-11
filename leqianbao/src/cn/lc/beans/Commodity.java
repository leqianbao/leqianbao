package cn.lc.beans;

public class Commodity {
	
	private Integer id;
    private String commodity_name;      //商品名称
    private Integer commodity_num;   //商品数量
    private Integer commodity_pay;  //商品价格
    private String commodity_imgurl;   //商品图片路径
    private String commodity_use_flag; //商品出售状态
    private String commodity_comment; //商品简介
    
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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

}
