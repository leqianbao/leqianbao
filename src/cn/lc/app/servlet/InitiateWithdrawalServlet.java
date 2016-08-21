package cn.lc.app.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import cn.lc.dao.FetchCashDao;
import cn.lc.dao.UserChildDao;
import cn.lc.json.model.Body;
import cn.lc.json.model.REP_BODY;
import cn.lc.json.model.REQ_BODY;
import cn.lc.json.model.Root;
import cn.lc.utils.Const;
import cn.lc.utils.DataUtil;

/**
 * 發起提現請求接口
 * @author Administrator
 *
 */
public class InitiateWithdrawalServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		Body<Boolean> body = new Body<>();
		FetchCashDao fetchCashDao = new FetchCashDao();
		String date = DataUtil.readDateFromRequest(request.getInputStream());
		Root root = JSON.parseObject(date.substring(12), Root.class);
		REQ_BODY reqBody = root.getREQ_BODY();
		String userId = reqBody.getUser_id();
		boolean back=fetchCashDao.addFetchCash(Integer.parseInt(userId), DataUtil.generateOrderNo(Integer.parseInt(userId)));
		if (back) {
			body.setRSPCOD(Const.CODE_SUCCESS);
			body.setRSPMSG(Const.FETCH_SUCCESS);
		} else {
			body.setRSPCOD(Const.CODE_ERROR);
			body.setRSPMSG(Const.FETCH_ERROR);
		}
		REP_BODY<Boolean> repBody = new REP_BODY<>();
		repBody.REP_BODY = body;
		PrintWriter writer = response.getWriter();
		writer.write(JSON.toJSONString(repBody));
		writer.flush();
		writer.close();
	}

}
