package cn.lc.json.model;

public class REQ_BODY {
	
	 //通用
    private String created_by;                  //创建人
    private String stamp_created;               //创建时间
    private String updated_by;                  //更新人
    private String stamp_updated;               //最后更新时间
    //分页
    private String page_num;                    //第几页
    private String page_size;                  //每页多少条
    //用户
    private String user_phone;              //手机号
    private String user_id;                 //用户id
    private String user_passwd;             //登录密码
    private String user_name;               //用户名
    private String pay_passwd;              //支付密码
    private String idcard_num;              //省份证号
    private String is_verify;               //是否实名认证0-未认证，1已认证
    private String new_passwd;              //新密码
    private String old_passwd;              //原密码
    private String passwd_type;             //密码修改类型:1.登录密码  2.修改支付密码
    private String modify_type;             //修改密码方式:1.原密码找回 2.短信验证码找回
    
    //银行卡
    private String bank_id;                 //银行卡id
    private String card_number;             //银行卡号
    private String bank_name;               //开户行名称
    //短消息
    private String msg_type;                //消息类型：0系统消息、1个人消息
    private String msg_id;                  //消息记录id号
    //提现
    private String fetch_money;             //提现金额
   

    //理财
    private String finance_money;           //购买金额
    private String product_id;              //理财产品id
    private String interest_rate;           //利率
    private String product_name;            //理财产品名称
    private String invest_days;             //理财天数


	//意见反馈
	private String feedback_type;      //类型
	private String feedback_content;   //内容
	
	//地址管理  user_id
	private int address_id;//地址id;
	private int address_type;  //0：新增；1:修改；2:删除
	private String address;
	private String address_phone;
	private String address_name;
	
	private String order_commodity_id;  //下单商品ID
	private String order_commodity_num;   //下单商品数量
	private String order_address_id;    //下单收货地址
	
	
	//积分支付
	private long integral;
	

	public String getOrder_commodity_id() {
		return order_commodity_id;
	}
	public void setOrder_commodity_id(String order_commodity_id) {
		this.order_commodity_id = order_commodity_id;
	}
	public String getOrder_commodity_num() {
		return order_commodity_num;
	}
	public void setOrder_commodity_num(String order_commodity_num) {
		this.order_commodity_num = order_commodity_num;
	}
	public String getOrder_address_id() {
		return order_address_id;
	}
	public void setOrder_address_id(String order_address_id) {
		this.order_address_id = order_address_id;
	}
	public long getIntegral() {
		return integral;
	}
	public void setIntegral(long integral) {
		this.integral = integral;
	}
	public int getAddress_id() {
		return address_id;
	}
	public void setAddress_id(int address_id) {
		this.address_id = address_id;
	}
	public int getAddress_type() {
		return address_type;
	}
	public void setAddress_type(int address_type) {
		this.address_type = address_type;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public String getCreated_by() {
        return created_by;
    }
    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }
    public String getStamp_created() {
        return stamp_created;
    }
    public void setStamp_created(String stamp_created) {
        this.stamp_created = stamp_created;
    }
    public String getUpdated_by() {
        return updated_by;
    }
    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }
    public String getStamp_updated() {
        return stamp_updated;
    }
    public void setStamp_updated(String stamp_updated) {
        this.stamp_updated = stamp_updated;
    }
    public String getPage_num() {
        return page_num;
    }
    public void setPage_num(String page_num) {
        this.page_num = page_num;
    }
    public String getPage_size() {
        return page_size;
    }
    public void setPage_size(String page_size) {
        this.page_size = page_size;
    }
    public String getUser_phone() {
        return user_phone;
    }
    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }
    public String getUser_id() {
        return user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public String getUser_passwd() {
        return user_passwd;
    }
    public void setUser_passwd(String user_passwd) {
        this.user_passwd = user_passwd;
    }
    public String getUser_name() {
        return user_name;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    public String getPay_passwd() {
        return pay_passwd;
    }
    public void setPay_passwd(String pay_passwd) {
        this.pay_passwd = pay_passwd;
    }
    public String getIdcard_num() {
        return idcard_num;
    }
    public void setIdcard_num(String idcard_num) {
        this.idcard_num = idcard_num;
    }
    public String getIs_verify() {
        return is_verify;
    }
    public void setIs_verify(String is_verify) {
        this.is_verify = is_verify;
    }
    public String getNew_passwd() {
        return new_passwd;
    }
    public void setNew_passwd(String new_passwd) {
        this.new_passwd = new_passwd;
    }
    public String getOld_passwd() {
        return old_passwd;
    }
    public void setOld_passwd(String old_passwd) {
        this.old_passwd = old_passwd;
    }
    public String getPasswd_type() {
        return passwd_type;
    }
    public void setPasswd_type(String passwd_type) {
        this.passwd_type = passwd_type;
    }
    public String getModify_type() {
        return modify_type;
    }
    public void setModify_type(String modify_type) {
        this.modify_type = modify_type;
    }
    public String getBank_id() {
        return bank_id;
    }
    public void setBank_id(String bank_id) {
        this.bank_id = bank_id;
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
    public String getMsg_type() {
        return msg_type;
    }
    public void setMsg_type(String msg_type) {
        this.msg_type = msg_type;
    }
    public String getMsg_id() {
        return msg_id;
    }
    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }
    public String getFetch_money() {
        return fetch_money;
    }
    public void setFetch_money(String fetch_money) {
        this.fetch_money = fetch_money;
    }
    public String getFinance_money() {
        return finance_money;
    }
    public void setFinance_money(String finance_money) {
        this.finance_money = finance_money;
    }
    public String getProduct_id() {
        return product_id;
    }
    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }
    public String getInterest_rate() {
        return interest_rate;
    }
    public void setInterest_rate(String interest_rate) {
        this.interest_rate = interest_rate;
    }
    public String getProduct_name() {
        return product_name;
    }
    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
    public String getInvest_days() {
        return invest_days;
    }
    public void setInvest_days(String invest_days) {
        this.invest_days = invest_days;
    }
    public String getFeedback_type() {
        return feedback_type;
    }
    public void setFeedback_type(String feedback_type) {
        this.feedback_type = feedback_type;
    }
    public String getFeedback_content() {
        return feedback_content;
    }
    public void setFeedback_content(String feedback_content) {
        this.feedback_content = feedback_content;
    }
	


}
