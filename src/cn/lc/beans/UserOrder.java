package cn.lc.beans;

public class UserOrder {
	private Integer id;
    private int user_id;      //客户id
    private String order_no;     //按一定规则生成的订单号
    private String commodity_id; //商品id
    private int commodity_num; //下单商品数量
    private String create_date;  //订单创建时间
    private String end_date;  //订单结束时间
    private String order_state; //0:订单提交，1:等待确认，2:已收货
    private String logistics_number;  //后台发货之后填写物流单号
    private long receipt_address_id;  //收货地址Id
    private String commodity_name;  //商品名称
    private String user_phone; //用户手机号码
    private String address_addres;  //收件人地址
    private String address_phone;   //收件人电话
    private String address_name;  //收件人名字

	public UserOrder() {
    	
    }
	public long getReceipt_address_id() {
		return receipt_address_id;
	}
	public void setReceipt_address_id(long receipt_address_id) {
		this.receipt_address_id = receipt_address_id;
	}
	public String getAddress_addres() {
		return address_addres;
	}

	public void setAddress_addres(String address_addres) {
		this.address_addres = address_addres;
	}

	public String getAddress_phone() {
		return address_phone;
	}

	public void setAddress_phone(String address_phone) {
		this.address_phone = address_phone;
	}

	public String getAddress_name() {
		return address_name;
	}

	public void setAddress_name(String address_name) {
		this.address_name = address_name;
	}
    
	public String getUser_phone() {
		return user_phone;
	}

	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}
	
    public String getCommodity_name() {
		return commodity_name;
	}

	public void setCommodity_name(String commodity_name) {
		this.commodity_name = commodity_name;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getCommodity_id() {
		return commodity_id;
	}

	public void setCommodity_id(String commodity_id) {
		this.commodity_id = commodity_id;
	}

	public int getCommodity_num() {
		return commodity_num;
	}

	public void setCommodity_num(int commodity_num) {
		this.commodity_num = commodity_num;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getOrder_state() {
		return order_state;
	}

	public void setOrder_state(String order_state) {
		this.order_state = order_state;
	}

	public String getLogistics_number() {
		return logistics_number;
	}

	public void setLogistics_number(String logistics_number) {
		this.logistics_number = logistics_number;
	}
    
    
}
