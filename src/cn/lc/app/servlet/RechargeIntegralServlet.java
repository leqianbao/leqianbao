package cn.lc.app.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.util.TextUtils;

import com.alibaba.fastjson.JSON;

import cn.lc.beans.AddressBean;
import cn.lc.beans.IntegralRateBean;
import cn.lc.dao.IntegralDao;
import cn.lc.dao.IntegralRateDao;
import cn.lc.json.model.REP_BODY;
import cn.lc.json.model.REQ_BODY;
import cn.lc.json.model.Root;
import cn.lc.utils.Const;
import cn.lc.utils.DataUtil;

public class RechargeIntegralServlet extends HttpServlet{
	private static final long serialVersionUID = 1267396443105330943L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");

		REP_BODY<List<AddressBean>> body=new REP_BODY<>();
		PrintWriter writer = response.getWriter();
		Map<String, String> map = new HashMap<>();
		int integral=0;
		int rate=0;
		IntegralDao integralDao=new IntegralDao();
		String date = DataUtil.readDateFromRequest(request.getInputStream());
		Root root = JSON.parseObject(date.substring(12), Root.class);
		REQ_BODY reqBody = root.getREQ_BODY();
		float money=reqBody.getMoney();
		int rechargeType=reqBody.getRecharge_type();
		
		String userId=reqBody.getUser_id();
		String comment=reqBody.getComment();
		IntegralRateDao integralRateDao=new IntegralRateDao();
		IntegralRateBean integralRate=integralRateDao.getInegralRate();
		switch(rechargeType){
		case 1:
			rate=integralRate.getIntegral_rate_t();
			break;
		case 2:
			rate=integralRate.getIntegral_rate_gl();
			break;
		case 3:
			rate=integralRate.getIntegral_rate_gs();
			break;
		}
		integral=getIntegral(rate,money);
		if(TextUtils.isEmpty(reqBody.getUser_id())){
			map.put(Const.CODE_KEY, Const.CODE_ERROR);
			map.put(Const.MSG_KEY, Const.PARAM_ERROR);
		}else{
			boolean result=integralDao.rechargeIntegral(Integer.parseInt(userId), integral, comment);
			if(result){
				map.put(Const.CODE_KEY, Const.CODE_SUCESS);
				map.put(Const.MSG_KEY, Const.INTEGRAL_RECORD_SUCESS);
			}else{
				map.put(Const.CODE_KEY, Const.CODE_ERROR);
				map.put(Const.MSG_KEY, Const.INTEGRAL_RECORD_ERROR);
			}
		}
		body.setRSPCOD(map.get(Const.CODE_KEY));
		body.setRSPMSG(map.get(Const.MSG_KEY));
		writer.write(JSON.toJSONString(body));
		writer.flush();
		writer.close();
	}
	
	
	public int getIntegral(int rate,float money){
		return ((int)money)/100*rate;
	}
	
	public boolean checkForm(REQ_BODY request){
		int integral=request.getIntegral();
		String userId=request.getUser_id();
		if(TextUtils.isEmpty(userId)){
			return false;
		}
		if(integral<0){
			return false;
		}
		return true;
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
