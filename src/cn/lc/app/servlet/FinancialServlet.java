package cn.lc.app.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.lc.beans.FinanceProduct;
import cn.lc.beans.Pager;
import cn.lc.dao.FinanceProductDao;
import cn.lc.json.model.REQ_BODY;
import cn.lc.json.model.Root;
import cn.lc.utils.DataUtil;

import com.alibaba.fastjson.JSON;

public class FinancialServlet extends HttpServlet {

	private static final long serialVersionUID = -1180007205342844356L;

	public FinancialServlet() {
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
		PrintWriter writer = response.getWriter();
		String date = DataUtil.readDateFromRequest(request.getInputStream());
		Root root = JSON.parseObject(date.substring(12), Root.class);
		REQ_BODY reqBody = root.getREQ_BODY();
	    String page_num = reqBody.getPage_num();
	    String page_size = reqBody.getPage_size();
		FinanceProductDao fd = new FinanceProductDao();
		Pager<FinanceProduct>  list = fd.getFinanceProductPageList(Integer.parseInt(page_num), Integer.parseInt(page_size));
		String jsonString = JSON.toJSONString(list);
		Map<String ,String> map = new HashMap<String ,String>();
		
		StringBuffer sbf = new StringBuffer();
		if(null!=list){
			map.put("RSPCOD","000000"); 
			map.put("RSPMSG","刷新成功!");
			String json=JSON.toJSONString(map);
	        sbf.append("{\"REP_BODY\":");
	        sbf.append(json.subSequence(0, json.length()-1));
	        sbf.append(",\"financeproduct_list\":");
	        sbf.append(jsonString);
	        sbf.append("}}");
			
		}else{
			map.put("RSPCOD","111111"); 
			map.put("RSPMSG","刷新失败!");
			String json=JSON.toJSONString(map);
			sbf.append("{\"REP_BODY\":");
	        sbf.append(json.subSequence(0, json.length()-1));
	        sbf.append(",\"financeproduct_list\":");
	        sbf.append(jsonString);
	        sbf.append("}}");
		}
		writer.write(sbf.toString());
		writer.flush();
		writer.close();
		
	}

	public void init() throws ServletException {
	}

}
