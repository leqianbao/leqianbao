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
import cn.lc.dao.UserDao;
import cn.lc.json.model.REP_BODY;
import cn.lc.json.model.REQ_BODY;
import cn.lc.json.model.Root;
import cn.lc.utils.Const;
import cn.lc.utils.DataUtil;

public class AddChildUserServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7288578220564487583L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		Map<String, String> map = new HashMap<>();
		REP_BODY<List<User>> body = new REP_BODY<>();
		String date = DataUtil.readDateFromRequest(request.getInputStream());
		Root root = JSON.parseObject(date.substring(12), Root.class);
		REQ_BODY reqBody = root.getREQ_BODY();
		int rootId = reqBody.getRoot();
		UserDao userDao = new UserDao();
		List<User> users = userDao.getChildUserList(rootId);
		body.setData(users);
		body.setRSPCOD(Const.CODE_SUCESS);
		body.setRSPMSG(Const.REQUEST_SUCCESS);
		PrintWriter writer = response.getWriter();
		writer.write(JSON.toJSONString(body));
		writer.flush();
		writer.close();

	}

}
