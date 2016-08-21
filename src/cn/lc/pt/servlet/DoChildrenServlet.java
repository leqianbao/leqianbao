package cn.lc.pt.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.lc.beans.Commodity;
import cn.lc.beans.Pager;
import cn.lc.beans.UserChildBean;
import cn.lc.dao.CommodityDao;
import cn.lc.dao.UserChildDao;
import cn.lc.utils.DataUtil;

/**
 * Servlet implementation class DoChildrenServlet
 */
@WebServlet("/doChildrenServlet")
public class DoChildrenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoChildrenServlet() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); 
		response.setContentType("text/html;charset=utf-8"); 
        PrintWriter out = response.getWriter();
		UserChildDao dao = new UserChildDao();
		String tag=request.getParameter("tag");
		if(tag.equals("query")){
			String user_id = request.getParameter("user_id"); //user_id
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
            UserChildBean searchModel = new UserChildBean();
            if(user_id != null && !((user_id.trim()).equals(""))){
                searchModel.setUser_id(Integer.parseInt(user_id));
            }else{
            	searchModel.setUser_id(Integer.parseInt("-1"));
            }
         // 调用service 获取查询结果
            Pager<UserChildBean> result = dao.getChildrenList(searchModel, pageNum, pageSize);
            // 返回结果到页面
            request.setAttribute("result", result);
            request.setAttribute("user_id", user_id);
            request.getRequestDispatcher("/WEB-INF/children/childrenlist.jsp").forward(request, response);
		}else if(tag.equals("update")){
			boolean update = false;
			String id = request.getParameter("id");
			String money = request.getParameter("value");
			update = dao.saveMoney(id, money);
			StringBuffer json = new StringBuffer();
	           json.append('{');
			if(update){
//				String path = request.getContextPath();
//	            String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//	            response.sendRedirect(basePath+"/pt/doChildren?tag=query&pageNum="+page_num+"&pageSize="+page_size);
			}else{
				json.append("saveMsg:").append("金额保存失败！");
			}
			 json.append("}");
             out.print(json.toString());
             out.close();
		}
	}

}
