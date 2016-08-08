package cn.lc.pt.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DoLogoutServlet extends HttpServlet {

    private static final long serialVersionUID = -1949045080648782519L;

    public DoLogoutServlet() {
        super();
    }

    public void destroy() {
        super.destroy();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doPost(request, response);

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session =request.getSession();
        session.removeAttribute("SysManager");
        session.removeAttribute("rightlist");
        session.invalidate();
        request.setAttribute("codeError", "已成功退出！");
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    public void init() throws ServletException {
    }

}
