package cn.lc.app.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.lc.beans.IntegralBean;
import cn.lc.beans.IntegralRecord;
import cn.lc.dao.IntegralDao;
import cn.lc.dao.IntegralRecordDao;
import cn.lc.json.model.REQ_BODY;
import cn.lc.json.model.Root;
import cn.lc.utils.DataUtil;

import com.alibaba.fastjson.JSON;

public class GetIntegralListServlet extends HttpServlet{

	private static final long serialVersionUID = 7687226064561166079L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");

		Map<String, String> map = null;
		IntegralRecordDao integralDao=new IntegralRecordDao();
		String date = DataUtil.readDateFromRequest(request.getInputStream());
		Root root = JSON.parseObject(date.substring(12), Root.class);
		REQ_BODY reqBody = root.getREQ_BODY();
		int state=reqBody.getIntegral_state();
		int pageNum=reqBody.getPageNum();
		int pageSize=reqBody.getPageNum();
		List<IntegralRecord> integrals=integralDao.getRecordList(reqBody.getUser_id(), 0, state, pageNum, pageSize);
		
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
