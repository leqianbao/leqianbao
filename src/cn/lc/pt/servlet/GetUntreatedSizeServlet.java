package cn.lc.pt.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.lc.beans.FetchCash;
import cn.lc.dao.FetchCashDao;

public class GetUntreatedSizeServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		FetchCashDao fetchCashDao = new FetchCashDao();
		List<FetchCash> fetchCashs = fetchCashDao.getUntreatedList();
		request.setAttribute("fetch_size",fetchCashs==null?0:fetchCashs.size());
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}

}
