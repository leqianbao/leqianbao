package cn.lc.pt.servlet;

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

import cn.lc.beans.IntegralRecord;
import cn.lc.beans.Pager;
import cn.lc.dao.IntegralDao;
import cn.lc.dao.IntegralRecordDao;
import cn.lc.json.model.REP_BODY;
import cn.lc.json.model.REQ_BODY;
import cn.lc.json.model.Root;
import cn.lc.utils.Const;
import cn.lc.utils.DataUtil;

import com.alibaba.fastjson.JSON;

public class DoIntegralListServlet extends HttpServlet{

	private static final long serialVersionUID = 7687226064561166079L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		String state = request.getParameter("record_state"); //状态：0：全部，1：收入，2：支出
		String userPhone = request.getParameter("user_phone"); //用户手机号
		String create_date = request.getParameter("create_date"); //创建时间
		String pageNum = request.getParameter("page_num"); //页数
		
		IntegralRecordDao integralDao=new IntegralRecordDao();
		Pager<IntegralRecord> integralRecords=integralDao.getRecordList(userPhone, state, create_date, pageNum, 10);
		
		request.setAttribute("result", integralRecords);
		request.setAttribute("record_state", state);
        request.setAttribute("user_phone", userPhone);
        request.setAttribute("create_date", create_date);
		request.getRequestDispatcher("/WEB-INF/intergral/integralList.jsp").forward(request, response);
		
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
