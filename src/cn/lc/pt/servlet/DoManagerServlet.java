package cn.lc.pt.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.lc.beans.Pager;
import cn.lc.beans.SysManager;
import cn.lc.dao.SysManagersDao;
import cn.lc.utils.DataUtil;


public class DoManagerServlet extends HttpServlet {

    private static final long serialVersionUID = -3916163653810048593L;
    public DoManagerServlet() {
        super();
    }
    SysManagersDao sd = new SysManagersDao();
    public void destroy() {
        super.destroy(); //
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String tag = request.getParameter("tag");
        if (tag.equals("query")) {
            String mgr_name = request.getParameter("mgr_name");
            String mgr_loginid = request.getParameter("mgr_loginid");
            String mgr_phone = request.getParameter("mgr_phone");
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
            SysManager searchModel = new SysManager();
            searchModel.setMgr_name(mgr_name);
            searchModel.setMgr_phone(mgr_phone);
            searchModel.setMgr_loginid(mgr_loginid);
            // 调用service 获取查询结果
            Pager<SysManager> result = sd.findManagerPager(searchModel, pageNum, pageSize); 
            // 返回结果到页面
            request.setAttribute("result", result);
            request.setAttribute("mgr_name", mgr_name);
            request.setAttribute("mgr_phone", mgr_phone);
            request.setAttribute("mgr_loginid", mgr_loginid);
            request.getRequestDispatcher("/WEB-INF/manager/listmanager.jsp").forward(request, response);
        } else if (tag.equals("toadd")) {
            request.getRequestDispatcher("/WEB-INF/manager/addmanager.jsp").forward(request, response);
        } else if (tag.equals("add")) {
            String mgr_loginid = request.getParameter("mgr_loginid");
            String mgr_passwd = request.getParameter("mgr_passwd");
            String mgr_name = request.getParameter("mgr_name");
            String mgr_sex = request.getParameter("mgr_sex");
            String mgr_phone = request.getParameter("mgr_phone");
            String mgr_address = request.getParameter("mgr_address");
            SysManager sm = new SysManager();
            sm.setMgr_address(mgr_address);
            sm.setMgr_loginid(mgr_loginid);
            sm.setMgr_name(mgr_name);
            sm.setMgr_passwd(DataUtil.getpass(mgr_passwd));
            sm.setMgr_phone(mgr_phone);
            sm.setMgr_sex(mgr_sex);
            HttpSession session=request.getSession();
            SysManager sy = (SysManager) session.getAttribute("SysManager");
            sm.setCreated_by(sy.getMgr_loginid());
            sm.setUpdated_by(sy.getMgr_loginid());
            sm.setStamp_created(new Date());
            sm.setStamp_updated(new Date());
            sm.setState("A");
            sm.setRole_ids("A,B,C,D,E,F,G,H");
           if( sd.addSysManager(sm)){
               request.getRequestDispatcher("/WEB-INF/manager/addmanager.jsp").forward(request, response);
           }else{
               request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
               return;
           }
        } else if (tag.equals("toedit")) {
          String manager_id = request.getParameter("manager_id");
          String mgr_loginid = request.getParameter("mgr_loginid");
          String mgr_name = request.getParameter("mgr_name");
          String mgr_sex = request.getParameter("mgr_sex");
          String mgr_phone = request.getParameter("mgr_phone");
          String mgr_address = request.getParameter("mgr_address");
          mgr_name=new String(mgr_name.getBytes("ISO8859_1"),"UTF-8");
          mgr_sex=new String(mgr_sex.getBytes("ISO8859_1"),"UTF-8");
          mgr_address=new String(mgr_address.getBytes("ISO8859_1"),"UTF-8");
            request.setAttribute("manager_id", manager_id);
            request.setAttribute("mgr_loginid", mgr_loginid);
            request.setAttribute("mgr_name", mgr_name);
            request.setAttribute("mgr_sex", mgr_sex);
            request.setAttribute("mgr_phone", mgr_phone);
            request.setAttribute("mgr_address", mgr_address);
            request.getRequestDispatcher("/WEB-INF/manager/editmanager.jsp").forward(request, response);
        } else if (tag.equals("edit")) {
            String manager_id = request.getParameter("manager_id");
            String mgr_loginid = request.getParameter("mgr_loginid");
            String mgr_name = request.getParameter("mgr_name");
            String mgr_sex = request.getParameter("mgr_sex");
            String mgr_phone = request.getParameter("mgr_phone");
            String mgr_address = request.getParameter("mgr_address");
            SysManager sm = new SysManager();
            sm.setManager_id(Integer.parseInt(manager_id));
            sm.setMgr_address(mgr_address);
            sm.setMgr_loginid(mgr_loginid);
            sm.setMgr_name(mgr_name);
            sm.setMgr_phone(mgr_phone);
            sm.setMgr_sex(mgr_sex);
            HttpSession session=request.getSession();
            SysManager sy = (SysManager) session.getAttribute("SysManager");
            sm.setUpdated_by(sy.getMgr_loginid());
            sm.setStamp_updated(new Date());
            if(sd.updateSysManager(sm)){
                String path = request.getContextPath();
                String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
                response.sendRedirect(basePath+"/pt/doManager?tag=query");
            }else{
                request.getRequestDispatcher("/jsp/error.jsp").forward(request, response); 
            }
        } else if (tag.equals("toright")) {
            String manager_id = request.getParameter("manager_id");
            String role_ids = request.getParameter("role_ids");
            request.setAttribute("manager_id", manager_id);
            request.setAttribute("role_ids", role_ids);
            
            request.getRequestDispatcher("/WEB-INF/manager/rightmanager.jsp").forward(request, response);
        } else if (tag.equals("right")) {
            
            
            
            String path = request.getContextPath();
            String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
            response.sendRedirect(basePath+"/pt/doManager?tag=query");
           // request.getRequestDispatcher("/WEB-INF/manager/addmanager.jsp").forward(request, response);
            
        } else if (tag.equals("resetpasswd")) {
            String mgr_passwd = request.getParameter("mgr_passwd");
            SysManager sm = new SysManager();
            sm.setMgr_passwd(mgr_passwd);
            HttpSession session=request.getSession();
            SysManager sy = (SysManager) session.getAttribute("SysManager");
            sm.setUpdated_by(sy.getMgr_loginid());
            sm.setStamp_updated(new Date());
            if(sd.updateSysManager(sm)){
                String path = request.getContextPath();
                String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
                response.sendRedirect(basePath+"/pt/doManager?tag=query");
            }else{
                request.getRequestDispatcher("/jsp/error.jsp").forward(request, response); 
            }
            
        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    public void init() throws ServletException {
    }

}
