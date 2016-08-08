package cn.lc.pt.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.lc.beans.Message;
import cn.lc.beans.Pager;
import cn.lc.beans.SysManager;
import cn.lc.dao.MessageDao;
import cn.lc.utils.DataUtil;


public class DoMessageServlet extends HttpServlet {

    private static final long serialVersionUID = 4567706729747972843L;
    public DoMessageServlet() {
        super();
    }
    MessageDao md = new MessageDao();
    public void destroy() {
        super.destroy(); //
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String tag=request.getParameter("tag");
        
        if (tag.equals("query")) {
            
            String msg_type = request.getParameter("msg_type");
            String created_by = request.getParameter("created_by");
            String msg_title = request.getParameter("msg_title");
            
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
            Message searchModel = new Message();
            searchModel.setMsg_type(msg_type);
            searchModel.setMsg_title(msg_title);
            
            if("1".equals(msg_type)&&created_by!=null){
                searchModel.setCreated_by(created_by);
            }
            // 调用service 获取查询结果
            Pager<Message> result = md.findMessgePager(searchModel, pageNum, pageSize); 
            // 返回结果到页面
            request.setAttribute("result", result);
            request.setAttribute("msgTitle", msg_title);
            request.setAttribute("msg_type", msg_type);
            request.setAttribute("created_by", created_by);
            request.getRequestDispatcher("/WEB-INF/msg/listmsg.jsp").forward(request, response);
        } else if (tag.equals("toadd")) {
            request.getRequestDispatcher("/WEB-INF/msg/addmsg.jsp").forward(request, response);
        } else if (tag.equals("add")) {
            
           String msg_title = request.getParameter("msg_title");
           String msg_type = request.getParameter("msg_type");
           String msg_content = request.getParameter("msg_content");
           String user_id = request.getParameter("user_id");
           Message m = new Message();
           m.setMsg_content(msg_content);
           m.setMsg_title(msg_title);
           m.setMsg_type(msg_type);
           if("1".equals(msg_type)){
               m.setUser_id(user_id);
           }else{
               m.setUser_id("0");
           }
           HttpSession session=request.getSession();
           SysManager sy = (SysManager) session.getAttribute("SysManager");
           m.setCreated_by(sy.getMgr_name());
           m.setUpdated_by(sy.getMgr_name());
           m.setStamp_created(new Date());
           m.setStamp_updated(new Date());
           m.setState("A");
          if( md.addMessage(m)){
              String path = request.getContextPath();
              String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
              response.sendRedirect(basePath+"/pt/doMessage?tag=query");
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
