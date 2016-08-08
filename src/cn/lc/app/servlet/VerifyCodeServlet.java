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

import cn.lc.json.model.REQ_BODY;
import cn.lc.json.model.Root;
import cn.lc.utils.DataUtil;
import cn.lc.utils.SmsUtils;


public class VerifyCodeServlet extends HttpServlet {

	private static final long serialVersionUID = -6487652950162308909L;

	public VerifyCodeServlet() {
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
		
		String code = cn.lc.utils.StringUtils.getCode();

		String rs = SmsUtils.sendSms(code, user_phone);
		
		Map <String,String>map = new HashMap<String,String>(); 
		String rcode = rs.substring(rs.indexOf("code")+6, rs.indexOf("msg")-2);
		 if("0".equals(rcode)){
	            map.put("verify_code",code);
	            map.put("RSPCOD","000000"); 
	            map.put("RSPMSG","获取验证码成功!");
	        }else if("22".equals(rcode)){
	            map.put("RSPCOD","111111"); 
	            map.put("RSPMSG","同一手机号,1小时内最多发送3次验证码!");
	        }else if("17".equals(rcode)){
	            map.put("RSPCOD","111111"); 
	            map.put("RSPMSG","同一手机号,1天内最多发送10次验证码!");
	        }
/*		if(true){
			map.put("verifyCode",code);
			map.put("RSPCOD","000000"); 
			map.put("RSPMSG","获取验证码成功!");
		}else{
			map.put("RSPCOD","111111"); 
			map.put("RSPMSG","获取验证码失败!");
		}*/
		
		 PrintWriter writer = response.getWriter();
	        writer.write(DataUtil.addReqBody(map));
			writer.flush();
			writer.close();		
	}

	public void init() throws ServletException {
	}

}
