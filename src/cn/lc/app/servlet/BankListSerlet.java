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

import cn.lc.beans.BankCard;
import cn.lc.dao.BankCardDao;
import cn.lc.json.model.REQ_BODY;
import cn.lc.json.model.Root;
import cn.lc.utils.DataUtil;

import com.alibaba.fastjson.JSON;

public class BankListSerlet extends HttpServlet {

	private static final long serialVersionUID = -3517278344411833647L;

	public BankListSerlet() {
		super();
	}

	public void destroy() {
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
		String user_id = reqBody.getUser_id();
		BankCardDao bcd = new BankCardDao();
		List <BankCard> list  = bcd.getBankList(user_id);
		String jsonString = JSON.toJSONString(list);
		StringBuffer sbf = new StringBuffer();
		Map<String ,String> map = new HashMap<String ,String>(); 
		
		PrintWriter writer = response.getWriter();
		if(list.isEmpty()){
			map.put("RSPCOD","000000");
			map.put("RSPMSG","刷新成功!");
			writer.write(DataUtil.addReqBody(map));
		}else{
			map.put("RSPCOD","000000");
			map.put("RSPMSG","刷新成功!");
			String json=JSON.toJSONString(map);
	        sbf.append("{\"REP_BODY\":");
	        sbf.append(json.subSequence(0, json.length()-1));
	        sbf.append(",\"bank_list\":");
	        sbf.append(jsonString);
	        sbf.append("}}");
	        writer.write(sbf.toString());
		}
		
		writer.flush();
		writer.close();	
	   
	}

	public void init() throws ServletException {
	}

}
