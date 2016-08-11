package cn.lc.app.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.lc.beans.BankCard;
import cn.lc.beans.Commodity;
import cn.lc.beans.IntegralBean;
import cn.lc.beans.Pager;
import cn.lc.dao.BankCardDao;
import cn.lc.dao.CommodityDao;
import cn.lc.dao.IntegralDao;
import cn.lc.json.model.REQ_BODY;
import cn.lc.json.model.Root;
import cn.lc.utils.DataUtil;

import com.alibaba.fastjson.JSON;

/**
 * Servlet implementation class CommodityListServlet
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
		String pageNum = reqBody.getPage_num();
		//每页显示数据数
		String pageSize = reqBody.getPage_size();
		//用户id
		int user_id = Integer.parseInt(reqBody.getUser_id());
		
		CommodityDao commodityDao = new CommodityDao();
		Pager <Commodity> pageData  = commodityDao.getAppPageCommodity(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
		IntegralDao interalDao = new IntegralDao();
		//用户总积分数
		IntegralBean integralBean  = interalDao.getCurrentIntegral(user_id);
		//组装主要数据部分
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("current_page", pageData.getCurrent_page());
		data.put("page_size", pageData.getPage_size());
		data.put("total_page", pageData.getTotal_page());
		data.put("total_record", pageData.getTotal_record());
		data.put("integralBean", integralBean.getUsr_intergral());
		data.put("data_list", pageData.getData_list());
		
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
