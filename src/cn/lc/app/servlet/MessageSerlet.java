package cn.lc.app.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.lc.beans.Message;
import cn.lc.beans.Pager;
import cn.lc.dao.MessageDao;
import cn.lc.json.model.REQ_BODY;
import cn.lc.json.model.Root;
import cn.lc.utils.DataUtil;

import com.alibaba.fastjson.JSON;

public class MessageSerlet extends HttpServlet {

	private static final long serialVersionUID = -8270967739180933781L;

	public MessageSerlet() {
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
		String msg_type = reqBody.getMsg_type();
		String user_id = reqBody.getUser_id();
        String page_num = reqBody.getPage_num();
        String page_size = reqBody.getPage_size();
		
		MessageDao md = new MessageDao();
		Pager<Message> list = md.findMsgPager(msg_type,user_id,Integer.parseInt(page_num), Integer.parseInt(page_size));
		String jsonString = JSON.toJSONString(list);
		StringBuffer sbf = new StringBuffer();
		Map<String ,String> map = new HashMap<String ,String>(); 
		if(list!=null){
			map.put("RSPCOD","000000");
			map.put("RSPMSG","刷新成功!");
			String json=JSON.toJSONString(map);
	        sbf.append("{\"REP_BODY\":");
	        sbf.append(json.subSequence(0, json.length()-1));
	        sbf.append(",\"msg_list\":");
	        sbf.append(jsonString);
	        sbf.append("}}");
		}else{
			map.put("RSPCOD","111111");
			map.put("RSPMSG","没有消息!");
		}
		PrintWriter writer = response.getWriter();
	    writer.write(sbf.toString());
		writer.flush();
		writer.close();	
	}
	public void init() throws ServletException {
	}

}
