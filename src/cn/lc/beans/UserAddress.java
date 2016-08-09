package cn.lc.beans;

public class UserAddress {
	private long id; //收货地址Id
	private int user_id;   //客户Id
    private String address_addres;  //收件人地址
    private String address_phone;   //收件人电话
    private String address_name;  //收件人名字
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
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
    
	
}
