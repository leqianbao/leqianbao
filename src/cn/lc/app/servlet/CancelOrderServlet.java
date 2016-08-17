package cn.lc.app.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import cn.lc.dao.UserOrderDao;
import cn.lc.json.model.REQ_BODY;
import cn.lc.json.model.Root;
import cn.lc.utils.Const;
import cn.lc.utils.DataUtil;

public class CancelOrderServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 630464109342659799L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		Map<String,String> map=new HashMap<>();
		String date = DataUtil.readDateFromRequest(request.getInputStream());
		Root root = JSON.parseObject(date.substring(12), Root.class);
		REQ_BODY reqBody = root.getREQ_BODY();
		String orderId=reqBody.getUser_order_id();
		UserOrderDao userOrderDao=new UserOrderDao();
		boolean back=userOrderDao.cancelOrder(Integer.parseInt(orderId));
		if(back){
			map.put(Const.CODE_KEY, Const.CODE_SUCESS);
			map.put(Const.MSG_KEY, Const.ORDER_CANCEL_SUCESS);
		}else{
			map.put(Const.CODE_KEY, Const.CODE_ERROR);
			map.put(Const.MSG_KEY, Const.ORDER_CANCEL_ERROR);
		}
	
		PrintWriter writer = response.getWriter();
	    writer.write(DataUtil.addReqBody(map));
		writer.flush();
		writer.close();	
	}

}
