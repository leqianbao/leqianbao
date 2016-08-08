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






import cn.lc.beans.FetchCash;
import cn.lc.beans.User;
import cn.lc.dao.FetchCashDao;
import cn.lc.dao.UserDao;
import cn.lc.json.model.REQ_BODY;
import cn.lc.json.model.Root;
import cn.lc.utils.DataUtil;
import cn.lc.utils.StringUtils;

import com.alibaba.fastjson.JSON;


public class FetchCashServlet extends HttpServlet {

    private static final long serialVersionUID = -3595055268869827305L;

    public FetchCashServlet() {
        super();
    }
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
        String user_phone = reqBody.getUser_phone();
        String bank_id = reqBody.getBank_id();
        String pay_passwd = reqBody.getPay_passwd();  //支付密码   先验证支付密码
        UserDao ud = new UserDao();
        User u = ud.verifyPass(user_phone);
        String pass  = u.getPay_passwd();
        Map <String,String>map = new HashMap<String,String>(); 
        if(pay_passwd.equals(pass)){
            String user_id = reqBody.getUser_id();
            String card_number = reqBody.getCard_number();
            String bank_name = reqBody.getBank_name();
            String fetch_money = reqBody.getFetch_money();
            FetchCash fc = new FetchCash();
            fc.setCard_number(card_number);
            fc.setUser_id(user_id);
            fc.setBank_id(bank_id);
            fc.setFetch_money(Double.parseDouble(fetch_money));
            fc.setFetch_num(StringUtils.getstance());
            u.setUser_id(Integer.parseInt(user_id));
            u.setUser_name(reqBody.getUser_name());
            fc.setUser(u);
            fc.setHandle_tag("0");
            fc.setBank_name(bank_name);
            fc.setCreated_by(user_phone);
            fc.setStamp_created(new Date());
            fc.setUpdated_by(user_phone);
            fc.setStamp_updated(new Date());
            fc.setState("A");
            FetchCashDao fd = new FetchCashDao();
            if(fd.addFetchCash(fc)){
                map.put("RSPCOD","000000");
                map.put("RSPMSG","提现成功!");
            } else{
                map.put("RSPCOD","111111");
                map.put("RSPMSG","提现失败!");
            }
        } else{
            map.put("RSPCOD","111111");
            map.put("RSPMSG","支付密码错误!");
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
