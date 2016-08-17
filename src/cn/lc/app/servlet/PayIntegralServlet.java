package cn.lc.app.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.lc.beans.IntegralBean;
import cn.lc.beans.IntegralRateBean;
import cn.lc.beans.UserOrder;
import cn.lc.dao.IntegralDao;
import cn.lc.dao.IntegralRateDao;
import cn.lc.dao.UserOrderDao;
import cn.lc.json.model.REQ_BODY;
import cn.lc.json.model.Root;
import cn.lc.utils.Const;
import cn.lc.utils.DataUtil;

import com.alibaba.fastjson.JSON;

public class PayIntegralServlet extends HttpServlet {

	private static final long serialVersionUID = 1617875298953965031L;

	public PayIntegralServlet() {
		super();
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		
		UserOrderDao userOrderDao=new UserOrderDao();
		Map<String, String> map = new HashMap<>();
		String date = DataUtil.readDateFromRequest(request.getInputStream());
		Root root = JSON.parseObject(date.substring(12), Root.class);
		REQ_BODY reqBody = root.getREQ_BODY();
		//获取传入的参数
		String userId=reqBody.getUser_id();
		float money=reqBody.getMoney();
		int integral=reqBody.getIntegral();
		String comment=reqBody.getComment();
		String orderId=reqBody.getUser_order_id();
		//获取订单信息
		UserOrder userOrder=userOrderDao.getUserOrderById(Integer.parseInt(orderId));
		String orderState=userOrder.getOrder_state();
		if("0".equals(orderState)){
			if(integral>0){
				map=payIntegral(userId,integral,comment);
			}	
			if(money>0){
				
			}
		}else{
			map.put(Const.CODE_KEY, Const.CODE_ERROR);
			map.put(Const.MSG_KEY, Const.ORDER_STATE_ERROR);
		}
		
		PrintWriter writer = response.getWriter();
		writer.write(DataUtil.addReqBody(map));
		writer.flush();
		writer.close();
	}
	

	public Map<String,String> payIntegral(String userId,int integral,String comment){
		IntegralDao integralDao = new IntegralDao();
		Map<String, String> map = new HashMap<>();
		IntegralBean integralBean = integralDao.getCurrentIntegral(Integer.parseInt(userId));
		if (integralBean.getUser_integral() - integral >= 0) {
			boolean back = integralDao.payIntegral(Integer.parseInt(userId),
					integral,comment);
			if (back) {
				map.put(Const.CODE_KEY, Const.CODE_SUCESS);
				map.put(Const.MSG_KEY, Const.INTEGRAL_PAY_SUCESS);
			} else {
				map.put(Const.CODE_KEY, Const.CODE_ERROR);
				map.put(Const.MSG_KEY, Const.INTEGRAL_PAY_ERROR);
			}
		} else {
			map.put(Const.CODE_KEY, Const.CODE_ERROR);
			map.put(Const.MSG_KEY, Const.INTEGRAL_PAY_ERROR_SHORT);
		}
		return map;
	}
	
	public Map<String,String> payMoney(){
		
	}
	

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public void init() throws ServletException {
		super.init();
	}

}
