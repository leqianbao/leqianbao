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
import cn.lc.dao.IntegralDao;
import cn.lc.dao.IntegralRateDao;
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
		int integral=0;
		int rate=0;
		IntegralDao integralDao = new IntegralDao();
		Map<String, String> map = new HashMap<>();
		String date = DataUtil.readDateFromRequest(request.getInputStream());
		Root root = JSON.parseObject(date.substring(12), Root.class);
		REQ_BODY reqBody = root.getREQ_BODY();
		float money=reqBody.getMoney();
		int payType=reqBody.getPay_type();
		String comment=reqBody.getComment();
		IntegralRateDao integralRateDao=new IntegralRateDao();
		IntegralRateBean integralRate=integralRateDao.getInegralRate();
		switch(payType){
		case 1:
			rate=integralRate.getIntegral_rate();
			break;
	
		}
		integral=getIntegral(rate,money);
		IntegralBean integralBean = integralDao.getCurrentIntegral(Integer.parseInt(reqBody
				.getUser_id()));
		
		if (integralBean.getUser_integral() - integral >= 0) {
			boolean back = integralDao.payIntegral(Integer.parseInt(reqBody
					.getUser_id()),
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

		PrintWriter writer = response.getWriter();
		writer.write(DataUtil.addReqBody(map));
		writer.flush();
		writer.close();
	}
	
	public int getIntegral(int rate,float money){
		return (int)Math.ceil(money*rate);
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
