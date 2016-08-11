package cn.lc.app.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.lc.beans.Commodity;
import cn.lc.beans.IntegralBean;
import cn.lc.beans.Pager;
import cn.lc.dao.CommodityDao;
import cn.lc.dao.IntegralDao;
import cn.lc.json.model.REQ_BODY;
import cn.lc.json.model.Root;
import cn.lc.utils.DataUtil;

import com.alibaba.fastjson.JSON;

/**
 * class CommodityListServlet
 */
@WebServlet("/commodityListServlet")
public class CommodityListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommodityListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		String date = DataUtil.readDateFromRequest(request.getInputStream());
		Root root = JSON.parseObject(date.substring(12), Root.class);
		REQ_BODY reqBody = root.getREQ_BODY();
		//当前页码
		int pageNum = Integer.parseInt(reqBody.getPage_num());
		//每页显示数据数
		int pageSize = Integer.parseInt(reqBody.getPage_size());
		//用户id
		int user_id = Integer.parseInt(reqBody.getUser_id());
		//用户初次查询的时间
		String first_search_date = "";
		if(pageNum == 1){
			SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");   
			String timeStr = sDateFormat.format(new Date());  
			first_search_date = timeStr;
		}else{
			first_search_date = reqBody.getSearch_date();
		}
		CommodityDao commodityDao = new CommodityDao();
		Pager <Commodity> pageData  = commodityDao.getAppPageCommodity(pageNum, pageSize, first_search_date);
		IntegralDao interalDao = new IntegralDao();
		//用户总积分数
		IntegralBean integralBean  = interalDao.getCurrentIntegral(user_id);
		int integral = 0;
		if(integralBean != null){
			integral = integralBean.getUser_integral();
		}
		//组装主要数据部分
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("current_page", pageData.getCurrent_page());
		data.put("page_size", pageData.getPage_size());
		data.put("total_page", pageData.getTotal_page());
		data.put("total_record", pageData.getTotal_record());
		data.put("integral", integral);
		data.put("data_list", pageData.getData_list());
		data.put("first_search_date", first_search_date);
		
		String jsonString = JSON.toJSONString(data);
		StringBuffer sbf = new StringBuffer();
		Map<String ,String> map = new HashMap<String ,String>(); 
		
		PrintWriter writer = response.getWriter();
		if(pageData.getData_list().isEmpty()){
			map.put("RSPCOD","000000");
			map.put("RSPMSG","刷新成功!");
			writer.write(DataUtil.addReqBody(map));
		}else{
			map.put("RSPCOD","000000");
			map.put("RSPMSG","刷新成功!");
			String json=JSON.toJSONString(map);
	        sbf.append("{\"REP_BODY\":");
	        sbf.append(json.subSequence(0, json.length()-1));
	        sbf.append(",\"goods_list\":");
	        sbf.append(jsonString);
	        sbf.append("}}");
	        writer.write(sbf.toString());
		}
		writer.flush();
		writer.close();	
	}

}
