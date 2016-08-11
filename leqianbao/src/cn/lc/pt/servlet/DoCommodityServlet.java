package cn.lc.pt.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.lc.beans.Commodity;
import cn.lc.beans.FetchCash;
import cn.lc.beans.Pager;
import cn.lc.dao.CommodityDao;
import cn.lc.utils.DataUtil;

/**
 * Servlet implementation class DoCommodityServlet
 */
@WebServlet("/doCommodityServlet")
public class DoCommodityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCommodityServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		CommodityDao dao = new CommodityDao();
		String tag=request.getParameter("tag");
		if(tag.equals("query")){
			String commodity_status = request.getParameter("commodity_status"); //商品销售状态
			String commodity_name = request.getParameter("commodity_name"); //商品名称
			String pageNumStr = request.getParameter("pageNum"); //页数
			if (pageNumStr != null && !DataUtil.isNum(pageNumStr)) {
                request.setAttribute("errorMsg", "参数传输错误");
                request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
                return;
            }
			int pageNum = 1; // 显示第几页数据
            if (pageNumStr != null && !"".equals(pageNumStr.trim())) {
                pageNum = Integer.parseInt(pageNumStr);
            }
            int pageSize = 10; // 每页显示多少条记录
            String pageSizeStr = request.getParameter("pageSize");
            if (pageSizeStr != null && !"".equals(pageSizeStr.trim())) {
                pageSize = Integer.parseInt(pageSizeStr);
            }
            // 组装查询条件
            Commodity searchModel = new Commodity();
            searchModel.setCommodity_name(commodity_name);
            searchModel.setCommodity_use_flag(commodity_status);
            // 调用service 获取查询结果
            Pager<Commodity> result = dao.getCommodityList(searchModel, pageNum, pageSize);
            // 返回结果到页面
            request.setAttribute("result", result);
            request.setAttribute("commodity_status", commodity_status);
            request.setAttribute("commodity_name", commodity_name);
            request.getRequestDispatcher("/WEB-INF/intergral/commoditylist.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
