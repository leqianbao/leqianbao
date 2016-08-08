package cn.lc.app.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.lc.beans.FeedBack;
import cn.lc.dao.FeedBackDao;
import cn.lc.json.model.REQ_BODY;
import cn.lc.json.model.Root;
import cn.lc.utils.DataUtil;

import com.alibaba.fastjson.JSON;

public class FeedBackServlet extends HttpServlet {

    private static final long serialVersionUID = -6568443147742663880L;

    public FeedBackServlet() {
        super();
    }
    FeedBackDao fbd = new FeedBackDao();
    public void destroy() {
        super.destroy();
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        String date = DataUtil.readDateFromRequest(request.getInputStream());
        Root root = JSON.parseObject(date.substring(12), Root.class);
        REQ_BODY reqBody = root.getREQ_BODY();
        String feedback_type = reqBody.getFeedback_type();
        String feedback_content = reqBody.getFeedback_content();
        String user_id = reqBody.getUser_id();
        String user_phone = reqBody.getUser_phone();
        
        FeedBack feedBack = new FeedBack();
        feedBack.setUser_id(user_id);
        feedBack.setIs_read(0);
        feedBack.setFeedback_content(feedback_content);
        feedBack.setFeedback_type(feedback_type);
        feedBack.setCreated_by(user_phone);
        feedBack.setStamp_created(new Date());
        feedBack.setUpdated_by(user_phone);
        feedBack.setStamp_updated(new Date());
        feedBack.setState("A");
        
        Map <String,String>map = new HashMap<String,String>(); 
        if( fbd.addFeedBack(feedBack)){
            map.put("RSPCOD","000000"); 
            map.put("RSPMSG","反馈成功!");
        }else{
            map.put("RSPCOD","111111"); 
            map.put("RSPMSG","反馈失败!");
        }
        
        
        PrintWriter writer = response.getWriter();
        writer.write(DataUtil.addReqBody(map));
        writer.flush();
        writer.close(); 
        
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        this.doGet(request, response);
    }

    public void init() throws ServletException {
    }

}
