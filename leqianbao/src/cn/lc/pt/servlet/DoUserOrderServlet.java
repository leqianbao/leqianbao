package cn.lc.pt.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.util.TextUtils;

import cn.lc.beans.Commodity;
import cn.lc.beans.Pager;
import cn.lc.beans.User;
import cn.lc.beans.UserOrder;
import cn.lc.dao.UserDao;
import cn.lc.dao.UserOrderDao;
import cn.lc.utils.DataUtil;

public class DoUserOrderServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DoUserOrderServlet(){
		super();
	}
	UserOrderDao dao = new UserOrderDao();
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tag=request.getParameter("tag");
		if(tag.equals("query")) {
			String user_phone = request.getParameter("user_phone"); //用户手机号
			String order_no = request.getParameter("order_no"); //订单号
			String create_date = request.getParameter("create_date"); //创建时间
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
            int userId = 0;
            if(!TextUtils.isEmpty(user_phone)) {
                User user = new User();
                user.setUser_phone(user_phone);
                UserDao userDao = new UserDao();
                userId = userDao.getUserId(user_phone);
            }
            // 组装查询条件
            UserOrder searchModel = new UserOrder();
            searchModel.setUser_id(userId);
            searchModel.setOrder_no(order_no);
            searchModel.setCreate_date(create_date);
//            // 调用service 获取查询结果
//            Pager<Commodity> result = dao.getCommodityList(searchModel, pageNum, pageSize);
//            // 返回结果到页面
//            request.setAttribute("result", result);
            request.setAttribute("user_phone", user_phone);
            request.setAttribute("order_no", order_no);
            request.setAttribute("create_date", create_date);
            request.getRequestDispatcher("/WEB-INF/userOrder/userOrder.jsp").forward(request, response);
		}
		
	}
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request, response);
    }

    public void init() throws ServletException {
    }
}
