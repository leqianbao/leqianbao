package cn.lc.app.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.lc.dao.FetchCashDao;
import cn.lc.dao.FinanceDao;
import cn.lc.dao.InterestDao;
import cn.lc.dao.PutinMoneyDao;
import cn.lc.json.model.REQ_BODY;
import cn.lc.json.model.Root;
import cn.lc.utils.DataUtil;

import com.alibaba.fastjson.JSON;

public class BalanceStatisticServlet extends HttpServlet {

	private static final long serialVersionUID = 1160173894114894941L;

	public BalanceStatisticServlet() {
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
		String user_id = reqBody.getUser_id();
		Map <String,String>map = new HashMap<String,String>(); 
		
		//计算方式   ==== 利息==count利息表  === 余额 --充值 -减-提现1--减理财--+加利息 ---
		//理财
		FinanceDao fd = new FinanceDao();
		String licais = fd.countMoney(user_id);
		Double licai =  0.0;
		if(licais!=null&&licais!=""){licai= Double.parseDouble(licais);}
		//充值
		PutinMoneyDao pd = new PutinMoneyDao();
		String chongzhis = pd.countMoney(user_id);
		Double chongzhi =  0.0;
		if(chongzhis!=null&&chongzhis!=""){chongzhi= Double.parseDouble(chongzhis);}
		
//		GetoutMoneyDao gd = new GetoutMoneyDao();
//		String tixis =  gd.countMoney(user_id);
//		Double tixi = 0.0;
//		if(tixis!=null&&tixis!=""){tixi= Double.parseDouble(tixis);}
		//提现
		FetchCashDao fcd = new FetchCashDao();
		String ftixis =  fcd.countMoney(user_id);
        Double ftixi = 0.0;
        if(ftixis!=null&&ftixis!=""){ftixi = Double.parseDouble(ftixis);}
		//利息
		InterestDao id = new InterestDao();
		String lixis = id.countMoney(user_id);
		Double lixi = 0.0;
		if(lixis!=null&&lixis!=""){lixi= Double.parseDouble(lixis);}
		
		
		
		Double balances = chongzhi - licai + lixi -ftixi; // - tixi
		
		
		//Double interests = licai;
		String balance = new DecimalFormat("0.00").format(balances);
        String interest = new DecimalFormat("0.00").format(licai);
		
        
        
        
		map.put("RSPCOD","000000");
		map.put("RSPMSG","查询余额成功!");
		map.put("interest",interest);//利息-理财
	    map.put("balance",balance);//余额
		PrintWriter writer = response.getWriter();
	    writer.write(DataUtil.addReqBody(map));
		writer.flush();
		writer.close();	

	}

	public void init() throws ServletException {
	}

}
