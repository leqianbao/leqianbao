package cn.lc.pt.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.lc.beans.SysManager;
import cn.lc.dao.SysManagersDao;
import cn.lc.utils.DataUtil;
import cn.lc.utils.StringUtils;


public class DoLoginServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -5133433346929092869L;

    public DoLoginServlet() {
        super();
    }
    SysManagersDao sD = new SysManagersDao();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("ucode");
        String loginID = request.getParameter("uname");
        String upass = request.getParameter("upass");
        if (StringUtils.IsEmpty(loginID)) {
            request.setAttribute("codeError", "登录账号不能为空！");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } else if (StringUtils.IsEmpty(upass)) {
            request.setAttribute("codeError", "密码不能为空！");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } else if (StringUtils.IsEmpty(code)) {
            request.setAttribute("codeError", "验证码不能为空！");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } else {
            String pass = DataUtil.getpass(upass);
            HttpSession session = request.getSession();
            String realCode = (String) session.getAttribute("check_code");
            if (!code.equalsIgnoreCase(realCode)) {
                request.setAttribute("codeError", "验证码有误！");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            } else {
                SysManager sm = sD.login(loginID, pass);
                String[] rightList = { "A", "AA", "AB", "AC" };

                if (null != sm) {
                    session.setAttribute("SysManager", sm);
                    session.setAttribute("rightList", rightList);
                    request.getRequestDispatcher("/jsp/main.jsp").forward(request, response);
                } else {
                    request.setAttribute("codeError", "用户名密码错误！");
                    request.getRequestDispatcher("/login.jsp").forward(request, response);
                }
            }

        }
    }

    public void init() throws ServletException {
    }


}
