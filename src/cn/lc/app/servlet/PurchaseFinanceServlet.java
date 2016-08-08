package cn.lc.app.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.lc.beans.Finance;
import cn.lc.beans.FinanceProduct;
import cn.lc.beans.User;
import cn.lc.dao.FinanceDao;
import cn.lc.dao.UserDao;
import cn.lc.json.model.REQ_BODY;
import cn.lc.json.model.Root;
import cn.lc.utils.DataUtil;
import cn.lc.utils.StringUtils;

import com.alibaba.fastjson.JSON;

public class PurchaseFinanceServlet extends HttpServlet {

	private static final long serialVersionUID = 7919856022247246489L;

	public PurchaseFinanceServlet() {
		super();
	}

	public void destroy() {
		super.destroy(); 
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		String date = DataUtil.readDateFromRequest(request.getInputStream());
		Root root = JSON.parseObject(date.substring(12), Root.class);
		REQ_BODY reqBody = root.getREQ_BODY();
		String user_phone = reqBody.getUser_phone();
		String finance_money = reqBody.getFinance_money();
		String product_id = reqBody.getProduct_id();
		String pay_passwd = reqBody.getPay_passwd();  //支付密码   先验证支付密码
		String user_id = reqBody.getUser_id();
		UserDao ud = new UserDao();
		User u = ud.verifyPass(user_phone);
		String pass  = u.getPay_passwd();
		Map <String,String>map = new HashMap<String,String>(); 
		if(pay_passwd.equals(pass)){
			FinanceDao fd = new FinanceDao();
			Finance f = new Finance();
			f.setFinance_num(StringUtils.getstance());
			f.setFinance_money(Double.parseDouble(finance_money));//金额
			f.setCreated_by(user_phone);
			f.setStamp_created(new Date());
			f.setUpdated_by(user_phone);
			f.setStamp_updated(new Date());
			f.setState("A");
			u.setUser_id(Integer.parseInt(user_id));
			u.setUser_name(reqBody.getUser_name()); //用户名
			f.setUser(u);
			FinanceProduct fP = new FinanceProduct();
			fP.setProduct_id(Integer.parseInt(product_id));
			fP.setInterest_rate(Double.parseDouble(reqBody.getInterest_rate()));
			fP.setInvest_days(Integer.parseInt(reqBody.getInvest_days()));
			fP.setProduct_name(reqBody.getProduct_name());
			f.setFinanceProduct(fP);
			if(fd.addFinance(f)){
				map.put("RSPCOD","000000");
				map.put("RSPMSG","购买成功!");
			}else{
				map.put("RSPCOD","111111");
				map.put("RSPMSG","购买失败!");
			}
		} else{
			map.put("RSPCOD","111111");
			map.put("RSPMSG","支付密码错误!");
		}
		PrintWriter writer = response.getWriter();
	    writer.write(DataUtil.addReqBody(map));
		writer.flush();
		writer.close();	
	}

	public void init() throws ServletException {
	}

}
