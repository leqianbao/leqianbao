package cn.lc.pt.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.lc.beans.Pager;
import cn.lc.beans.PutinMoney;
import cn.lc.beans.SysManager;
import cn.lc.dao.PutinMoneyDao;
import cn.lc.utils.DataUtil;
import cn.lc.utils.StringUtils;


public class DoPutinMoneyServlet extends HttpServlet {

    private static final long serialVersionUID = 1389596228561560596L;

    public DoPutinMoneyServlet() {
        super();
    }

    PutinMoneyDao pd = new PutinMoneyDao();
    public void destroy() {
        super.destroy(); //
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String tag=request.getParameter("tag");
        
        if (tag.equals("query")) {
             String putin_num = request.getParameter("putin_num");
             String putin_money = request.getParameter("putin_money");
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
            PutinMoney searchModel = new PutinMoney();
            searchModel.setPutin_num(putin_num);
            if(null!=putin_money&&!"".equals(putin_money.trim())){
                searchModel.setPutin_money(Double.parseDouble(putin_money));
            }
            searchModel.setUser_id(user_id);
            // 调用service 获取查询结果
            Pager<PutinMoney> result = pd.getPutinPager(searchModel, pageNum, pageSize);
            // 返回结果到页面
            request.setAttribute("result", result);
            request.setAttribute("putin_num", putin_num);
            request.setAttribute("putin_money", putin_money);
            request.setAttribute("user_id", user_id);
            request.getRequestDispatcher("/WEB-INF/putin/listputin.jsp").forward(request, response);
        } else if (tag.equals("toadd")) {
            request.getRequestDispatcher("/WEB-INF/putin/addputin.jsp").forward(request, response);
        } else if (tag.equals("add")) {
            
           String money = request.getParameter("putin_money");
           String desc = request.getParameter("putin_desc");
           String uid = request.getParameter("user_id");
           
           PutinMoney p = new PutinMoney();
           HttpSession session=request.getSession();
           SysManager sy = (SysManager) session.getAttribute("SysManager");
           p.setPutin_num(StringUtils.getstance());
           p.setPutin_desc(desc);
           p.setPutin_money(Double.parseDouble(money));
           p.setUser_id(uid);
           p.setCreated_by(sy.getMgr_loginid());
           p.setUpdated_by(sy.getMgr_loginid());
           p.setStamp_created(new Date());
           p.setStamp_updated(new Date());
           p.setState("A");
           if(  pd.addPutinMoney(p)){
               String path = request.getContextPath();
               String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
               response.sendRedirect(basePath+"/pt/doPutinMoney?tag=query");
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
