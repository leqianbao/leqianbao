package cn.lc.pt.servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.util.TextUtils;

import cn.lc.beans.FetchCash;
import cn.lc.beans.FetchCashMain;
import cn.lc.beans.IntegralRateBean;
import cn.lc.beans.Pager;
import cn.lc.beans.SysManager;
import cn.lc.dao.FetchCashMainDao;
import cn.lc.dao.IntegralDao;
import cn.lc.dao.IntegralRateDao;
import cn.lc.utils.DataUtil;

public class DoWithdrawMainServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3997911730040046561L;

	public DoWithdrawMainServlet() {
		super();
	}

	FetchCashMainDao gd = new FetchCashMainDao();

	public void destroy() {
		super.destroy(); //
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String tag = request.getParameter("tag");

		if (tag.equals("query")) {
			String main_no = request.getParameter("main_no"); // 订单号
			String create_date = request.getParameter("create_date");
			String user_id = request.getParameter("user_id");
			// 校验pageNum参数输入合法性
			String pageNumStr = request.getParameter("pageNum");
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
			FetchCashMain searchModel = new FetchCashMain();
			searchModel.setMain_no(main_no);
			searchModel.setCreate_date(create_date);
			if (!TextUtils.isEmpty(user_id)) {
				searchModel.setUser_id(Integer.parseInt(user_id));
			} else {
				searchModel.setUser_id(0);
			}

			// 调用service 获取查询结果
			Pager<FetchCashMain> result = gd.findOutPager(searchModel, pageNum, pageSize);
			// 返回结果到页面
			request.setAttribute("result", result);
			request.setAttribute("main_no", main_no);
			request.setAttribute("user_id", user_id);
			request.setAttribute("create_date", create_date);
			request.getRequestDispatcher("/WEB-INF/tixian/withdrawmain.jsp").forward(request, response);
		} else if (tag.equals("handler")) {
			String main_no = request.getParameter("main_no"); // 订单号
			String user_id = request.getParameter("user_id");
			FetchCashMainDao fetchCashMainDao = new FetchCashMainDao();
			boolean back = fetchCashMainDao.modifyFetchMainState(Integer.parseInt(user_id), main_no, "A");
			if (back) {
				IntegralDao integralDao=new IntegralDao();
				IntegralRateDao integralRateDao=new IntegralRateDao();
				IntegralRateBean integralRate=integralRateDao.getInegralRate();
				FetchCashMainDao searchModelDao = new FetchCashMainDao();
				int rate=integralRate.getIntegral_rate_t();
				FetchCashMain fetchCashMain=searchModelDao.getFetchMain(Integer.parseInt(user_id), main_no);
				int integral=getIntegral(rate,fetchCashMain.getFetch_money().floatValue());
				integralDao.rechargeIntegral(Integer.parseInt(user_id), integral, "提现");
				String path = request.getContextPath();
				String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
						+ path + "/";
				response.sendRedirect(basePath + "/pt/doWithdrawMain?tag=query");
			} else {
			}

			// String fetch_id = request.getParameter("fetch_id");
			// FetchCash fc = new FetchCash();
			// fc.setFetch_id(Integer.parseInt(fetch_id));
			// fc.setHandle_tag("1");
			// fc.setStamp_updated(new Date());
			// HttpSession session=request.getSession();
			// SysManager sy = (SysManager) session.getAttribute("SysManager");
			// fc.setUpdated_by(sy.getMgr_loginid());
			//
			// if(gd.handler(fc)){
			// String path = request.getContextPath();
			// String basePath =
			// request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
			// response.sendRedirect(basePath+"/pt/doFetchCash?tag=query");
			// }else{
			// request.setAttribute("errorMsg", "参数传输错误");
			// request.getRequestDispatcher("/jsp/error.jsp").forward(request,
			// response);
			// }
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

	public void init() throws ServletException {
	}
	
	public int getIntegral(int rate,float money){
		return ((int)money)/100*rate;
	}
}
