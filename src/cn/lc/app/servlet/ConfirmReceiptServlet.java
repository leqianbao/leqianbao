package cn.lc.app.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import cn.lc.beans.User;
import cn.lc.beans.UserOrder;
import cn.lc.dao.UserDao;
import cn.lc.dao.UserOrderDao;
import cn.lc.json.model.REQ_BODY;
import cn.lc.json.model.Root;
import cn.lc.utils.DataUtil;

public class ConfirmReceiptServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7553783651191337904L;
	
	public ConfirmReceiptServlet(){
		super();
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
		String user_order_id = reqBody.getUser_order_id();
		String order_state =reqBody.getOrder_state();
		UserOrder userOrder = new UserOrder();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		userOrder.setId(Integer.valueOf(user_order_id));
		userOrder.setEnd_date(sdf.format(new Date()));
		userOrder.setOrder_state(order_state);
		
		UserOrderDao userOrderDao = new UserOrderDao();
		
		Map <String,String>map = new HashMap<String,String>(); 
		if(userOrderDao.modifyOrderState(userOrder)){
			map.put("RSPCOD","000000");
			map.put("RSPMSG","收货状态确认成功!");
		}else{
			map.put("RSPCOD","111111");
			map.put("RSPMSG","收货状态确认失败!");
		}
		PrintWriter writer = response.getWriter();
	    writer.write(DataUtil.addReqBody(map));
		writer.flush();
		writer.close();	
	}
	public void init() throws ServletException {
	}

}
