package cn.lc.pt.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.lc.beans.FetchCash;
import cn.lc.beans.Pager;
import cn.lc.beans.SysManager;
import cn.lc.dao.FetchCashDao;
import cn.lc.utils.DataUtil;



public class DoFetchCashServlet extends HttpServlet {

    private static final long serialVersionUID = 8683509551307435644L;

    public DoFetchCashServlet() {
        super();
    }

    FetchCashDao gd = new FetchCashDao();
    public void destroy() {
        super.destroy(); //
    }
    
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String tag=request.getParameter("tag");
       
        if (tag.equals("query")) {
        	String main_no = request.getParameter("main_no");//主账户订单号
            String fetch_num = request.getParameter("fetch_num"); //订单号
            String fetch_money = request.getParameter("fetch_money");
            String user_id = request.getParameter("user_id");
            String handle_tag = request.getParameter("handle_tag");
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
            FetchCash searchModel = new FetchCash();
            searchModel.setFetch_num(fetch_num);
            if(null!=fetch_money&&!"".equals(fetch_money.trim())){
                searchModel.setFetch_money(Double.parseDouble(fetch_money));
            }
            searchModel.setMain_no(main_no);
            searchModel.setUser_id(user_id);
            searchModel.setHandle_tag(handle_tag);
            // 调用service 获取查询结果
            Pager<FetchCash> result = gd.findOutPager(searchModel, pageNum, pageSize);
            // 返回结果到页面
            request.setAttribute("result", result);
            request.setAttribute("handle_tag", handle_tag);
            request.setAttribute("fetch_money", fetch_money);
            request.setAttribute("fetch_num", fetch_num);
            request.setAttribute("main_no", main_no);
            request.getRequestDispatcher("/WEB-INF/tixian/listtixian.jsp").forward(request, response);
        } else if (tag.equals("handler")) {
            
          String fetch_id =  request.getParameter("fetch_id");
          String main_no = request.getParameter("main_no");
          FetchCash fc = new FetchCash();
          fc.setFetch_id(Integer.parseInt(fetch_id));
          fc.setHandle_tag("1");
          fc.setStamp_updated(new Date());
          HttpSession session=request.getSession();
          SysManager sy = (SysManager) session.getAttribute("SysManager");
          fc.setUpdated_by(sy.getMgr_loginid());
          
          if(gd.handler(fc)){
              String path = request.getContextPath();
              String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
              response.sendRedirect(basePath+"/pt/doFetchCash?tag=query&main_no="+main_no);
          }else{
              request.setAttribute("errorMsg", "参数传输错误");
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
