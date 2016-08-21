package cn.lc.app.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.lc.beans.IntegralBean;
import cn.lc.beans.IntegralRateBean;
import cn.lc.beans.UserOrder;
import cn.lc.dao.FetchCashDao;
import cn.lc.dao.FinanceDao;
import cn.lc.dao.IntegralDao;
import cn.lc.dao.IntegralRateDao;
import cn.lc.dao.IntegralRecordDao;
import cn.lc.dao.InterestDao;
import cn.lc.dao.PutinMoneyDao;
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		IntegralDao integralDao = new IntegralDao();
		UserOrderDao userOrderDao = new UserOrderDao();
		boolean integralBack = true;
		boolean moneyBack = true;
		Map<String, String> map = new HashMap<>();
		String date = DataUtil.readDateFromRequest(request.getInputStream());
		Root root = JSON.parseObject(date.substring(12), Root.class);
		REQ_BODY reqBody = root.getREQ_BODY();
		// 获取传入的参数
		String userId = reqBody.getUser_id();
		float money = reqBody.getMoney();
		int integral = reqBody.getIntegral();
		String comment = reqBody.getComment();
		String orderNo = reqBody.getOrder_no();
		int payType = reqBody.getPay_type();
		// 获取订单信息
		UserOrder userOrder = userOrderDao.getUserOrderById(orderNo);
		String orderState = userOrder.getOrder_state();
		if ("0".equals(orderState)) {
			switch(payType){
			case 1:
				integralDao.initIntegral(Integer.parseInt(userId));
				IntegralBean integralBean = integralDao.getCurrentIntegral(Integer.parseInt(userId));
				if (integralBean.getUser_integral() - integral >= 0) {
					integralBack = payIntegral(userId, integral, comment,new BigDecimal(0));
					if(integralBack){
						map.put(Const.CODE_KEY, Const.CODE_SUCCESS);
						map.put(Const.MSG_KEY, Const.INTEGRAL_PAY_SUCCESS);
						changState(orderNo);
					}else{
						map.put(Const.CODE_KEY, Const.CODE_ERROR);
						map.put(Const.MSG_KEY, Const.INTEGRAL_PAY_ERROR);
					}
					
				}else{
					map.put(Const.CODE_KEY, Const.CODE_ERROR);
					map.put(Const.MSG_KEY, Const.INTEGRAL_PAY_ERROR_SHORT);
				}
				break;
			case 2:
				if (getMoney(userId) - money >= 0) {
					IntegralRecordDao integralRecord = new IntegralRecordDao();
					boolean back = integralRecord.addRecord(Integer.parseInt(userId), 0, comment, new BigDecimal(Float.toString(money)));
					if(back){
						changState(orderNo);
						map.put(Const.CODE_KEY, Const.CODE_SUCCESS);
						map.put(Const.MSG_KEY, Const.INTEGRAL_PAY_SUCCESS);
					}else{
						map.put(Const.CODE_KEY, Const.CODE_ERROR);
						map.put(Const.MSG_KEY, Const.INTEGRAL_PAY_ERROR);
					}
				} else {
					map.put(Const.CODE_KEY, Const.CODE_ERROR);
					map.put(Const.MSG_KEY, Const.MONEY_PAY_ERROR_SHORT);
				}
				break;
			case 3:
				integralDao.initIntegral(Integer.parseInt(userId));
				IntegralBean integralBean2 = integralDao.getCurrentIntegral(Integer.parseInt(userId));
				if (integralBean2.getUser_integral() - integral >= 0) {
					if(getMoney(userId) - money >= 0){
						integralBack = payIntegral(userId, integral, comment,new BigDecimal(money));
						if(integralBack){
							map.put(Const.CODE_KEY, Const.CODE_SUCCESS);
							map.put(Const.MSG_KEY, Const.INTEGRAL_PAY_SUCCESS);
							changState(orderNo);
						}else{
							map.put(Const.CODE_KEY, Const.CODE_ERROR);
							map.put(Const.MSG_KEY, Const.INTEGRAL_PAY_ERROR);
						}
					}else {
						map.put(Const.CODE_KEY, Const.CODE_ERROR);
						map.put(Const.MSG_KEY, Const.MONEY_PAY_ERROR_SHORT);
					}
					
				}else{
					map.put(Const.CODE_KEY, Const.CODE_ERROR);
					map.put(Const.MSG_KEY, Const.INTEGRAL_PAY_ERROR_SHORT);
				}
				break;
			}
			
		} else {
			map.put(Const.CODE_KEY, Const.CODE_ERROR);
			map.put(Const.MSG_KEY, Const.ORDER_STATE_ERROR);
		}
		PrintWriter writer = response.getWriter();
		writer.write(DataUtil.addReqBody(map));
		writer.flush();
		writer.close();
	}

	public boolean payIntegral(String userId, int integral, String comment,BigDecimal money) {
		if (integral == 0) {
			return true;
		}
		IntegralDao integralDao = new IntegralDao();
		boolean back = integralDao.payIntegral(Integer.parseInt(userId), integral, comment,money);
		return back;
	}

	public double getMoney(String userId) {
		FinanceDao fd = new FinanceDao();
		String licais = fd.countMoney(userId);
		double licai = 0.0;
		if (licais != null && licais != "") {
			licai = Double.parseDouble(licais);
		}
		// 充值
		PutinMoneyDao pd = new PutinMoneyDao();
		String chongzhis = pd.countMoney(userId);
		double chongzhi = 0.0;
		if (chongzhis != null && chongzhis != "") {
			chongzhi = Double.parseDouble(chongzhis);
		}

		// GetoutMoneyDao gd = new GetoutMoneyDao();
		// String tixis = gd.countMoney(user_id);
		// Double tixi = 0.0;
		// if(tixis!=null&&tixis!=""){tixi= Double.parseDouble(tixis);}
		// 提现
		FetchCashDao fcd = new FetchCashDao();
		String ftixis = fcd.countMoney(userId);
		double ftixi = 0.0;
		if (ftixis != null && ftixis != "") {
			ftixi = Double.parseDouble(ftixis);
		}
		// 利息
		InterestDao id = new InterestDao();
		String lixis = id.countMoney(userId);
		double lixi = 0.0;
		if (lixis != null && lixis != "") {
			lixi = Double.parseDouble(lixis);
		}
		double balances = chongzhi - licai + lixi - ftixi; // - tixi
		return balances;
	}

	
	public void changState(String orderNO){
		UserOrderDao userOrderDao = new UserOrderDao();
		UserOrder userOrderBean = new UserOrder();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		userOrderBean.setOrder_no(orderNO);
		userOrderBean.setEnd_date(new Timestamp(System.currentTimeMillis()));
		userOrderBean.setOrder_state("1");
		userOrderDao.modifyOrderState(userOrderBean);
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
