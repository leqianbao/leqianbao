package cn.lc.pt.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.lc.beans.IntegralRateBean;
import cn.lc.dao.IntegralRateDao;

/**
 * class DoIntegralRateServlet
 */
@WebServlet("/doIntegralRateServlet")
public class DoIntegralRateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoIntegralRateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		IntegralRateDao dao = new IntegralRateDao();
		request.setCharacterEncoding("utf-8"); 
		response.setContentType("text/html;charset=utf-8"); 
		String tag = request.getParameter("tag");
		if(tag.equals("query")){//查询
			//取得积分汇率bean
			IntegralRateBean integralRateBean = dao.getInegralRate();
			//取得积分汇率数值
			request.setAttribute("integral_rate", integralRateBean.getIntegral_rate());
			request.setAttribute("integral_rate_t", integralRateBean.getIntegral_rate_t());
			request.setAttribute("integral_rate_gl", integralRateBean.getIntegral_rate_gl());
			request.setAttribute("integral_rate_gs", integralRateBean.getIntegral_rate_gs());
			//取得积分汇率主键id
			request.setAttribute("id", integralRateBean.getId());
			//跳转到积分汇率编辑画面
			request.getRequestDispatcher("/WEB-INF/intergral/integralRate.jsp").forward(request, response);
		}else if(tag.equals("update")){//更新
			//取得画面传过来的修改后的积分汇率数值
			int update_rate = Integer.parseInt(request.getParameter("integral_rate"));
			int update_rate_t = Integer.parseInt(request.getParameter("integral_rate_t"));
			int update_rate_gl = Integer.parseInt(request.getParameter("integral_rate_gl"));
			int update_rate_gs = Integer.parseInt(request.getParameter("integral_rate_gs"));
			//取得画面传回来的积分汇率主键id
			int rate_id = Integer.parseInt(request.getParameter("id"));
			//更新积分汇率到数据库
			boolean updateFlag = dao.updateIntegralRate(rate_id, update_rate, update_rate_t, update_rate_gl, update_rate_gs);
			//成功之后刷新页面数据
			if(updateFlag){
				String path = request.getContextPath();
	            String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	            response.sendRedirect(basePath+"/pt/doIntegralRate?tag=query");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
