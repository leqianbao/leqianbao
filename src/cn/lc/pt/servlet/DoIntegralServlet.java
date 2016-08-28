package cn.lc.pt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

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
import cn.lc.dao.UserDao;
import cn.lc.utils.DataUtil;

/**
 * Servlet implementation class DoIntegralServlet
 */
@WebServlet("/doIntegralServlet")
public class DoIntegralServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoIntegralServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); 
		response.setContentType("text/html;charset=utf-8"); 
        PrintWriter out = response.getWriter();
		IntegralDao dao = new IntegralDao();
		String tag = request.getParameter("tag");
		if(tag.equals("query")){
			String user_phone = request.getParameter("user_phone"); //用户手机号
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
            // 调用service 获取查询结果
            Pager<IntegralBean> result = dao.getUserIntegralList(user_phone, pageNum, pageSize);
            // 返回结果到页面
            request.setAttribute("result", result);
            request.setAttribute("user_phone", user_phone);
            request.getRequestDispatcher("/WEB-INF/intergral/userIntegralList.jsp").forward(request, response);
		}else if(tag.equals("update")){
			String user_id = request.getParameter("user_id"); //用户id
			int value = Integer.parseInt(request.getParameter("value"));

			StringBuffer json = new StringBuffer();
			json.append('{');
			boolean isSave = dao.initIntegralWithVal(user_id, value);
			if(isSave){
				json.append("saveMsg:").append("积分修改成功！");
			}else{
				json.append("saveMsg:").append("积分修改失败！");
			}
			json.append("}");
            out.print(json.toString());
            out.close();
		}
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}
