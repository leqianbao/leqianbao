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
import cn.lc.dao.CommodityDao;
import cn.lc.dao.UserDao;
import cn.lc.dao.UserOrderDao;
import cn.lc.utils.DataUtil;
import cn.lc.utils.StringUtils;

public class DoUserOrderDetailServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6363779322604806776L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoUserOrderDetailServlet() {
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
		request.setCharacterEncoding("utf-8"); 
		response.setContentType("text/html;charset=utf-8"); 
		String tag = request.getParameter("tag");
		UserOrderDao userOrderDao = new UserOrderDao();
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
            // 调用service 获取查询结果
            Pager<UserOrder> result = userOrderDao.getUserOrderPager(searchModel, pageNum, pageSize);
            for(int i=0;i<result.getData_list().size();i++) {
            	UserOrder userOrder = result.getData_list().get(i);
            	
                //根据商品ID，获取各个订单中的商品名称
            	String commodity_id = userOrder.getCommodity_id();
            	CommodityDao commodityDao = new CommodityDao();
            	Commodity commodity = commodityDao.getCommodityById(commodity_id);
            	String commodity_name = "";
            	if(commodity != null) {
            		commodity_name = commodity.getCommodity_name();
            	}
        		result.getData_list().get(i).setCommodity_name(commodity_name);
        		

                //根据用户ID，获取各个订单中的用户手机号
        		UserDao userDao = new UserDao();
            	int user_id = userOrder.getUser_id();
        		User user = userDao.getUserById(user_id);
        		String userPhone = "";
        		if(user != null) {
        			userPhone = user.getUser_phone();
        		}
        		result.getData_list().get(i).setUser_phone(userPhone);
        		
        		//格式化创建时间戳
        		String createDate = userOrder.getCreate_date();
        		if(!TextUtils.isEmpty(createDate)) {
        			createDate = StringUtils.formatDateString(createDate);
            		result.getData_list().get(i).setCreate_date(createDate);
        		}
        		
        		//格式化结束时间戳
        		String endDate = userOrder.getEnd_date();
        		if(!TextUtils.isEmpty(endDate)) {
        			endDate = StringUtils.formatDateString(endDate);
            		result.getData_list().get(i).setEnd_date(endDate);
        		}
        		
            }
            // 返回结果到页面
            request.setAttribute("result", result);
            request.setAttribute("user_phone", user_phone);
            request.setAttribute("order_no", order_no);
            request.setAttribute("create_date", create_date);
	        request.getRequestDispatcher("/WEB-INF/userOrder/userOrder.jsp").forward(request, response);
		} else if(tag.equals("save")) {
			//id
			long id = Long.valueOf(request.getParameter("id"));
			//物流单号
			String logistics_number = request.getParameter("logistics_number");
			//订单状态
			String order_state = request.getParameter("order_state");
			boolean result = userOrderDao.UpdateUserOrderById(id, logistics_number, order_state);
	           if( result){
	               String path = request.getContextPath();
	               String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	               response.sendRedirect(basePath+"/pt/doUserOrderDetail?tag=query");
	           }else{
	               request.getRequestDispatcher("/jsp/error.jsp").forward(request, response); 
	           }
			
		}
		
	}


}
