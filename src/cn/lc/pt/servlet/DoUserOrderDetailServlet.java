package cn.lc.pt.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.lc.dao.UserOrderDao;

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
		if(tag.equals("edit")) {
	        // 返回结果到页面
	        request.setAttribute("result", request);
	        request.setAttribute("tag", "edit");
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
	               response.sendRedirect(basePath+"/pt/doUserOrderDetail?tag=edit");
	           }else{
	               request.getRequestDispatcher("/jsp/error.jsp").forward(request, response); 
	           }
			
		}
		
	}


}
