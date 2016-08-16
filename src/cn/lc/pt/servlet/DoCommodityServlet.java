package cn.lc.pt.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetAddress;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.lc.beans.Commodity;
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
		request.setCharacterEncoding("utf-8"); 
		response.setContentType("text/html;charset=utf-8"); 
		CommodityDao dao = new CommodityDao();
		String tag=request.getParameter("tag");
		if(tag.equals("query")){
			String commodity_status = request.getParameter("commodity_status"); //商品销售状态
			String commodity_type = request.getParameter("commodity_type"); //商品销售状态
			String commodity_id = request.getParameter("commodity_id"); //商品id
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
            searchModel.setCommodity_id(commodity_id);
            searchModel.setCommodity_use_flag(commodity_status);
            searchModel.setCommodity_name(commodity_name);
            searchModel.setCommodity_type(commodity_type);
            // 调用service 获取查询结果
            Pager<Commodity> result = dao.getCommodityList(searchModel, pageNum, pageSize);
            // 返回结果到页面
            request.setAttribute("result", result);
            request.setAttribute("commodity_status", commodity_status);
            request.setAttribute("commodity_id", commodity_id);
            request.setAttribute("commodity_name", commodity_name);
            request.setAttribute("commodity_type", commodity_type);
            request.getRequestDispatcher("/WEB-INF/intergral/commoditylist.jsp").forward(request, response);
		}else if(tag.equals("edit")){
			String commodity_id = request.getParameter("commodity_id"); //商品id
			Commodity result = new Commodity("","", 0, 0, new BigDecimal(0), 0, new BigDecimal(0), "", "0", "0", "");
			if(commodity_id != null && !(commodity_id.trim().equals(""))){
				String imagePath = request.getScheme()+"://"+
						InetAddress.getLocalHost().getHostAddress()+":"+request.getServerPort()+
							request.getContextPath()+"/";
				// 调用service 获取查询结果
				result = dao.getCommodityById(commodity_id);
				String imgUrl = result.getCommodity_imgurl();
				if(imgUrl != null && !(imgUrl.trim().equals(""))){
					result.setCommodity_imgurl(imagePath + imgUrl);
				}
			}
            request.setAttribute("result", result);
			request.getRequestDispatcher("/WEB-INF/intergral/commodityDetail.jsp").forward(request, response);
		}else if(tag.equals("del")){
			String commodity_id = request.getParameter("commodity_id"); //商品id
			int del = dao.deleteCommodityById(commodity_id);
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
			response.sendRedirect(basePath+"/pt/doCommodity?tag=query");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
