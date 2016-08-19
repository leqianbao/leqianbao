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

import cn.lc.beans.AddressBean;
import cn.lc.beans.User;
import cn.lc.beans.UserChildBean;
import cn.lc.dao.UserChildDao;
import cn.lc.dao.UserDao;
import cn.lc.dao.UserOrderDao;
import cn.lc.json.model.Body;
import cn.lc.json.model.REP_BODY;
import cn.lc.json.model.REQ_BODY;
import cn.lc.json.model.Root;
import cn.lc.utils.Const;
import cn.lc.utils.DataUtil;

public class GetChildUserListServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		Body<List<UserChildBean>> body = new Body<>();
		String date = DataUtil.readDateFromRequest(request.getInputStream());
		Root root = JSON.parseObject(date.substring(12), Root.class);
		REQ_BODY reqBody = root.getREQ_BODY();
		String userId = reqBody.getUser_id();
		UserChildDao userChildDao=new UserChildDao();
		List<UserChildBean> users = userChildDao.getChildUserList(Integer.parseInt(userId));
		body.setData(users);
		body.setRSPCOD(Const.CODE_SUCCESS);
		body.setRSPMSG(Const.REQUEST_SUCCESS);
		REP_BODY<List<UserChildBean>> repBody = new REP_BODY<>();
		repBody.REP_BODY = body;
		PrintWriter writer = response.getWriter();
		writer.write(JSON.toJSONString(repBody));
		writer.flush();
		writer.close();

	}
}
