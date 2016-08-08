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

public class RegisterServlet extends HttpServlet {


	private static final long serialVersionUID = -3687488036326692997L;

	public RegisterServlet() {
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
		String user_passwd = reqBody.getUser_passwd();
		UserDao ud = new UserDao();
		Map <String,String>map = new HashMap<String,String>(); 
		if(ud.isReged(user_phone)){
			User u = new User();
			u.setUser_phone(user_phone);
			u.setUser_passwd(user_passwd);
			u.setUser_grade("1");
			u.setIs_verify("0");
			u.setCreated_by(user_phone);
			u.setStamp_created(new Date());
			u.setUpdated_by(user_phone);
			u.setStamp_updated(new Date());
			u.setState("A");
			if(ud.regUser(u)){
				map.put("RSPCOD","000000"); 
				map.put("RSPMSG","注册成功!");
			}else{
				map.put("RSPCOD","111111"); 
				map.put("RSPMSG","注册失败!");
			}
		}
		else {
			map.put("RSPCOD","111111"); 
			map.put("RSPMSG","手机号码已注册!");
		}
		 PrintWriter writer = response.getWriter();
	        writer.write(DataUtil.addReqBody(map));
			writer.flush();
			writer.close();		
	}
	public void init() throws ServletException {
	}

}
