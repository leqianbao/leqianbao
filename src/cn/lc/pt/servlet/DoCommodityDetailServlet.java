package cn.lc.pt.servlet;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.lc.beans.Commodity;
import cn.lc.dao.CommodityDao;

/**
 * Servlet implementation class DoCommodityDetail
 */
@WebServlet("/doCommodityDetail")
public class DoCommodityDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCommodityDetailServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		CommodityDao dao = new CommodityDao();
		request.setCharacterEncoding("utf-8"); 
		response.setContentType("text/html;charset=utf-8"); 
		String tag = request.getParameter("tag");
		//商品id
		String commodity_id = request.getParameter("commodity_id");
		//商品名称
		String name = request.getParameter("commodity_name");
		//商品价格
		Integer pay = Integer.parseInt(request.getParameter("commodity_pay"));
		//商品数量
		Integer num = Integer.parseInt(request.getParameter("commodity_num"));
		//商品出售状态
		String use_flag = request.getParameter("commodity_use_flag");
		//商品类别
		String type = request.getParameter("commodity_type");
		//商品介绍
		String comment = request.getParameter("commodity_comment");
		//结果
		Boolean update = true;
		if(tag.equals("add")){
			if(commodity_id != null && !(commodity_id.trim().equals(""))){
				update = dao.updateCommodity(commodity_id, name, pay,
							num, use_flag, type, comment);
			}else if(commodity_id == null || commodity_id.trim().equals("")){
				commodity_id = getCommodityId();
				update = dao.createCommodity(commodity_id, name, pay,
						num, use_flag, type, comment);
			}
			if(update){
				String path = request.getContextPath();
	            String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	            response.sendRedirect(basePath+"/pt/doCommodity?tag=edit&commodity_id="+commodity_id);
			}
		}
	}
	
	/**
	 * 规则生成商品id
	 * 
	 * */
	private String getCommodityId(){
		Calendar time = Calendar.getInstance();
		String commodity_id = String.valueOf("SP"+time.get(Calendar.YEAR))
			   	+ String.valueOf(time.get(Calendar.MONTH))
			   	+ String.valueOf(time.get(Calendar.DAY_OF_MONTH))
			   	+ String.valueOf(time.get(Calendar.HOUR_OF_DAY))
			   	+ String.valueOf(time.get(Calendar.MINUTE))
			   	+ String.valueOf(time.get(Calendar.SECOND))
			   	+ String.valueOf(time.get(Calendar.MILLISECOND));
		return commodity_id;
	}

}

