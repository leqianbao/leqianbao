package cn.lc.app.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.lc.beans.OrderDetial;
import cn.lc.beans.Pager;
import cn.lc.dao.FinanceDao;
import cn.lc.json.model.REQ_BODY;
import cn.lc.json.model.Root;
import cn.lc.utils.DataUtil;

import com.alibaba.fastjson.JSON;

public class FinanceListServlet extends HttpServlet {

	private static final long serialVersionUID = -320475934847625832L;

	public FinanceListServlet() {
		super();
	}

	public void destroy() {
		super.destroy(); 
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPut(request, response);
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
		String page_num = reqBody.getPage_num();
		String page_size = reqBody.getPage_size();
		FinanceDao fd = new FinanceDao();
		Pager<OrderDetial> list = fd.financePager(Integer.parseInt(page_num), Integer.parseInt(page_size),user_id);;
		String jsonString = JSON.toJSONString(list);
		StringBuffer sbf = new StringBuffer();
		Map<String ,String> map = new HashMap<String ,String>(); 
		if(list!=null){
			map.put("RSPCOD","000000");
			map.put("RSPMSG","刷新成功!");
			String json=JSON.toJSONString(map);
	        sbf.append("{\"REP_BODY\":");
	        sbf.append(json.subSequence(0, json.length()-1));
	        sbf.append(",\"finance_list\":");
	        sbf.append(jsonString);
	        sbf.append("}}");
		}else{
			map.put("RSPCOD","111111");
			map.put("RSPMSG","没有理财记录!");
		}
		PrintWriter writer = response.getWriter();
	    writer.write(sbf.toString());
		writer.flush();
		writer.close();	
	}

	public void init() throws ServletException {
	}

}
