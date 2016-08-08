package cn.lc.pt.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.lc.beans.FinanceProduct;
import cn.lc.beans.Pager;
import cn.lc.beans.SysManager;
import cn.lc.dao.FinanceProductDao;
import cn.lc.utils.DataUtil;


public class DoFinanceProductServlet extends HttpServlet {

    private static final long serialVersionUID = 6602360626688112686L;

    public DoFinanceProductServlet() {
        super();
    }

    FinanceProductDao fd = new FinanceProductDao();
    public void destroy() {
        super.destroy(); //
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String tag=request.getParameter("tag");
        if (tag.equals("query")) {
            String product_name = request.getParameter("product_name");
            String invest_days = request.getParameter("invest_days");
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
            FinanceProduct searchModel = new FinanceProduct();
            if(null!=product_name&&!"".equals(product_name.trim())){
                searchModel.setProduct_name(product_name);
            }
            if(null!=invest_days&&!"".equals(invest_days.trim())){
                searchModel.setInvest_days(Integer.parseInt(invest_days));
            }
            // 调用service 获取查询结果
            Pager<FinanceProduct> result =  fd.findFinanceProductPager(searchModel, pageNum, pageSize);
            // 返回结果到页面
            request.setAttribute("result", result);
            request.setAttribute("name", product_name);
            request.setAttribute("days", invest_days);
            request.getRequestDispatcher("/WEB-INF/financeproduct/listfinanceproduct.jsp").forward(request, response);
        } else if (tag.equals("toadd")) {
            request.getRequestDispatcher("/WEB-INF/financeproduct/addfinanceproduct.jsp").forward(request, response);
        } else if (tag.equals("add")) {
            
          String interest_rate = request.getParameter("interest_rate");
          String interest_days = request.getParameter("interest_days");
          String interest_months = request.getParameter("interest_months");
          String interest_money = request.getParameter("interest_money");
          String product_name = request.getParameter("product_name");
          String product_desc = request.getParameter("product_desc");
          
          FinanceProduct fp = new FinanceProduct();
          HttpSession session=request.getSession();
          SysManager sy = (SysManager) session.getAttribute("SysManager");
          fp.setFinance_img("/image/days.png");
          fp.setInvest_days(Integer.parseInt(interest_days));
          fp.setInvest_money(Integer.parseInt(interest_money));
          fp.setInvest_months((Integer.parseInt(interest_months)));
          fp.setProduct_desc(product_desc);
          fp.setProduct_name(product_name);
          fp.setIs_expire(1);
          
          fp.setInterest_rate(Double.parseDouble(interest_rate));
          fp.setCreated_by(sy.getMgr_name());
          fp.setUpdated_by(sy.getMgr_name());
          fp.setStamp_created(new Date());
          fp.setStamp_updated(new Date());
          fp.setState("A");

        if(  fd.addFinanceProduct(fp)){
            String path = request.getContextPath();
            String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
            response.sendRedirect(basePath+"/pt/doFinanceProduct?tag=query");
        }else{
            request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
        }
        }
        
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request, response);
    }

    public void init() throws ServletException {
    }


}
