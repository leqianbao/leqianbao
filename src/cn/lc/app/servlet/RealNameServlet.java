package cn.lc.app.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.lc.beans.User;
import cn.lc.dao.UserDao;
import cn.lc.json.model.REQ_BODY;
import cn.lc.json.model.Root;
import cn.lc.utils.DataUtil;

import com.alibaba.fastjson.JSON;

public class RealNameServlet extends HttpServlet {

	private static final long serialVersionUID = -5160420625111987351L;

	public RealNameServlet() {
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
		String user_phone = reqBody.getUser_phone();
		String pay_passwd = reqBody.getPay_passwd();
		String is_verify =reqBody.getIs_verify();
		String user_id = reqBody.getUser_id();
		String user_name = reqBody.getUser_name();
		User u = new User();
		u.setUser_id(Integer.parseInt(user_id));
		u.setIs_verify("2");
		u.setIdcard_num(is_verify);
		u.setUser_phone(user_phone);
		u.setUser_name(user_name);
		u.setPay_passwd(pay_passwd);
		u.setStamp_updated(new Date());
		u.setUpdated_by(user_phone);
		
		UserDao ud = new UserDao();
		Map <String,String>map = new HashMap<String,String>(); 
		if(ud.realName(u)){
			map.put("RSPCOD","000000");
			map.put("RSPMSG","实名认证成功!");
		}else{
			map.put("RSPCOD","111111");
			map.put("RSPMSG","实名认证失败!");
		}
		PrintWriter writer = response.getWriter();
	    writer.write(DataUtil.addReqBody(map));
		writer.flush();
		writer.close();	
	}
	public void init() throws ServletException {
	}

}
