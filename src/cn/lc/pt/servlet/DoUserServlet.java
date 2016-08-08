package cn.lc.pt.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;





import cn.lc.beans.Pager;
import cn.lc.beans.User;
import cn.lc.dao.UserDao;
import cn.lc.utils.DataUtil;


public class DoUserServlet extends HttpServlet {

    private static final long serialVersionUID = 6776573363854732563L;
    private UserDao ud = new UserDao();

    public DoUserServlet() {
        super();
    }

    public void destroy() {
        super.destroy();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String tag = request.getParameter("tag");
//        HttpSession session = request.getSession();
        if (tag.equals("query")) {
            String user_name = request.getParameter("user_name");
            String user_phone = request.getParameter("user_phone");
            String is_verify = request.getParameter("is_verify");
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
            User searchModel = new User();
            searchModel.setUser_name(user_name);
            searchModel.setUser_phone(user_phone);;
            searchModel.setIs_verify(is_verify);;
            // 调用service 获取查询结果
            Pager<User> result = ud.findUserPager(searchModel, pageNum, pageSize);
            // 返回结果到页面
            request.setAttribute("result", result);
            request.setAttribute("user_name", user_name);
            request.setAttribute("user_phone", user_phone);
            request.setAttribute("is_verify", is_verify);
            request.getRequestDispatcher("/WEB-INF/user/listuser.jsp").forward(request, response);
        } else if (tag.equals("toedit")) {
            
            request.getRequestDispatcher("/WEB-INF/user/edituser.jsp").forward(request, response);
            
        } else if (tag.equals("edit")) {
            
            
        } else if (tag.equals("sendmsg")) {
            request.getRequestDispatcher("/WEB-INF/user/adduser.jsp").forward(request, response);
        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    public void init() throws ServletException {
    }

}
