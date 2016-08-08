package cn.lc.app.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import cn.lc.beans.User;
import cn.lc.dao.UserDao;
import cn.lc.json.model.REQ_BODY;
import cn.lc.json.model.Root;
import cn.lc.utils.DataUtil;

/**
 * 登录
 * @author lianjie
 *
 */
public class UserLoginServlet extends HttpServlet {

	private static final long serialVersionUID = 7387010790668561269L;

	public UserLoginServlet() {
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
		User u = ud.login(user_phone, user_passwd);
		if(u!=null){
			map.put("RSPCOD","000000");
			map.put("user_id",u.getUser_id()+"");
			if(u.getUser_name()==null){
			map.put("user_name","未认证"); 
			} else{
				map.put("user_name",u.getUser_name()+""); 
			}
			map.put("is_verify", u.getIs_verify());//实名认证
			map.put("user_phone",u.getUser_phone()+""); 
			map.put("RSPMSG","登录成功!");
		}else{
			map.put("RSPCOD","111111"); 
			map.put("RSPMSG","用户名密码错误!");
		}
		 PrintWriter writer = response.getWriter();
	        writer.write(DataUtil.addReqBody(map));
			writer.flush();
			writer.close();	
	}
	public void init() throws ServletException {
	}

}
