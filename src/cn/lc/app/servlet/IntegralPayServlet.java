package cn.lc.app.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.lc.dao.IntegralDao;
import cn.lc.json.model.REQ_BODY;
import cn.lc.json.model.Root;
import cn.lc.utils.DataUtil;

import com.alibaba.fastjson.JSON;

public class IntegralPayServlet extends HttpServlet{

	private static final long serialVersionUID = 1617875298953965031L;
	
	
	public IntegralPayServlet(){
		super();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		
		IntegralDao integralDao=new IntegralDao();
		
		Map<String, String> map = null;
		String date = DataUtil.readDateFromRequest(request.getInputStream());
		Root root = JSON.parseObject(date.substring(12), Root.class);
		REQ_BODY reqBody = root.getREQ_BODY();
		long integral=reqBody.getIntegral();
		IntegralBean integralBean=integralDao.getCurrentIntegral(reqBody.getUser_id());
		
		
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
