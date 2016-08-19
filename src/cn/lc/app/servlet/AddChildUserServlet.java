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

import com.alibaba.fastjson.JSON;

import cn.lc.beans.User;
import cn.lc.beans.UserChildBean;
import cn.lc.dao.UserChildDao;
import cn.lc.dao.UserDao;
import cn.lc.json.model.Body;
import cn.lc.json.model.REP_BODY;
import cn.lc.json.model.REQ_BODY;
import cn.lc.json.model.Root;
import cn.lc.utils.Const;
import cn.lc.utils.DataUtil;

public class AddChildUserServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7288578220564487583L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		Body<Boolean> body = new Body<>();
		UserChildDao userChildDao = new UserChildDao();
		String date = DataUtil.readDateFromRequest(request.getInputStream());
		Root root = JSON.parseObject(date.substring(12), Root.class);
		REQ_BODY reqBody = root.getREQ_BODY();
		String userId = reqBody.getUser_id();
		String name = reqBody.getChildName();
		String phone = reqBody.getChildPhone();
		String idCard = reqBody.getIdCard();
		String bankName = reqBody.getBankName();
		String bankAccount = reqBody.getBankAccount();
		boolean back = userChildDao.addChildUser(Integer.parseInt(userId), name, phone, idCard, bankName, bankAccount);
		if (back) {
			body.setRSPCOD(Const.CODE_SUCCESS);
			body.setRSPMSG(Const.CHILD_ADD_SUCCESS);
		} else {
			body.setRSPCOD(Const.CODE_ERROR);
			body.setRSPMSG(Const.CHILD_ADD_ERROR);
		}
		REP_BODY<Boolean> repBody = new REP_BODY<>();
		repBody.REP_BODY = body;
		PrintWriter writer = response.getWriter();
		writer.write(JSON.toJSONString(repBody));
		writer.flush();
		writer.close();

	}

}
