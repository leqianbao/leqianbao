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

import cn.lc.beans.GetoutMoney;
import cn.lc.beans.User;
import cn.lc.dao.GetoutMoneyDao;
import cn.lc.dao.UserDao;
import cn.lc.json.model.REQ_BODY;
import cn.lc.json.model.Root;
import cn.lc.utils.DataUtil;
import cn.lc.utils.StringUtils;

import com.alibaba.fastjson.JSON;

public class GetCashedServlet extends HttpServlet {

	private static final long serialVersionUID = -2565096916982162174L;

	public GetCashedServlet() {
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
		String pay_passwd = reqBody.getPay_passwd();  //支付密码   先验证支付密码
		UserDao ud = new UserDao();
		User u = ud.verifyPass(user_phone);
		String pass  = u.getPay_passwd();
		Map <String,String>map = new HashMap<String,String>(); 
		if(pay_passwd.equals(pass)){
			
			String user_id = reqBody.getUser_id();
			String bank_name = reqBody.getBank_name();
			String card_number = reqBody.getCard_number();
			String fetch_money = reqBody.getFetch_money();
			GetoutMoney gm = new GetoutMoney();
			gm.setGetout_bankcode(card_number);
			gm.setGetout_money(Double.parseDouble(fetch_money));
			gm.setGetout_num(StringUtils.getstance());
			//User u = new User();
			u.setUser_id(Integer.parseInt(user_id));
			u.setUser_name(reqBody.getUser_name());
			gm.setUser(u);
			gm.setGetout_bankname(bank_name);
			gm.setCreated_by(user_phone);
			gm.setStamp_created(new Date());
			gm.setUpdated_by(user_phone);
			gm.setStamp_updated(new Date());
			gm.setState("A");
			GetoutMoneyDao gmd = new GetoutMoneyDao();
			if(gmd.addOutMoney(gm)){
				map.put("RSPCOD","000000");
				map.put("RSPMSG","提现成功!");
			} else{
				map.put("RSPCOD","111111");
				map.put("RSPMSG","提现失败!");
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
