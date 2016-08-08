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

public class ModifyPwdServlet extends HttpServlet {

	private static final long serialVersionUID = -3467792752027876769L;

	public ModifyPwdServlet() {
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
		String data = DataUtil.readDateFromRequest(request.getInputStream());
		Root root = JSON.parseObject(data.substring(12), Root.class);
		System.out.println(data);
		REQ_BODY reqBody = root.getREQ_BODY();
		String user_phone = reqBody.getUser_phone();
		String new_passwd = reqBody.getNew_passwd();
		//updateType修改密码方式，2验证码，1。原密码找回
		String modify_type = reqBody.getModify_type(); 
		//pwdType密码修改类型：1.登录密码  2.修改支付密码
		String passwd_type = reqBody.getPasswd_type();
		String old_passwd = reqBody.getOld_passwd();//原密码--验证码
		User u = new User();
		u.setUser_phone(user_phone);
		u.setUpdated_by(user_phone);
		u.setStamp_updated(new Date());
		UserDao ud = new UserDao();
		
		System.out.println();
		Map<String ,String> map = new HashMap<String ,String>(); 
		if(modify_type.equals("1")){
		    //原密码找回
		    User u2 = ud.verifyPass(user_phone);
			if(passwd_type.equals("1")){
			    //登录密码
				String yu = u2.getUser_passwd();
				if(yu.equals(old_passwd)){
					u.setPay_passwd(new_passwd);
					ud.modifyPassword("1",u);
					map.put("RSPCOD","000000"); 
					map.put("RSPMSG","登录密码修改成功!"); 
				}else{
					map.put("RSPCOD","111111"); 
					map.put("RSPMSG","原密码错误!"); 
				}
			} else{
			    //修改支付密码
				String yu = u2.getPay_passwd();
				if(yu.equals(old_passwd)){
					u.setPay_passwd(new_passwd);
					ud.modifyPassword("2",u);
					map.put("RSPCOD","000000"); 
					map.put("RSPMSG","支付密码修改成功!"); 
				}else{
					map.put("RSPCOD","111111"); 
					map.put("RSPMSG","原密码错误!"); 
				}
			}
		} else{
			if(passwd_type.equals("1")){
				u.setPay_passwd(new_passwd);
				ud.modifyPassword("1",u);
				map.put("RSPCOD","000000"); 
				map.put("RSPMSG","登录密码修改成功!"); 
			} else{
				u.setPay_passwd(new_passwd);
				ud.modifyPassword("2",u);
				map.put("RSPCOD","000000"); 
				map.put("RSPMSG","支付密码修改成功!"); 
			}
		}
		 PrintWriter writer = response.getWriter();
         writer.write(DataUtil.addReqBody(map));
         writer.flush();
         writer.close();
		
	}
	public void init() throws ServletException {
	}

}
