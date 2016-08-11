package cn.lc.app.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import cn.lc.beans.FetchCash;
import cn.lc.beans.Pager;
import cn.lc.dao.FetchCashDao;
import cn.lc.json.model.REQ_BODY;
import cn.lc.json.model.Root;
import cn.lc.utils.DataUtil;

import com.alibaba.fastjson.JSON;


public class FetchCashListServlet extends HttpServlet {

    private static final long serialVersionUID = 1819450372994941594L;
    public FetchCashListServlet() {
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
        String user_id = reqBody.getUser_id();
        String page_num = reqBody.getPage_num();
        String page_size = reqBody.getPage_size();
        FetchCashDao fd = new FetchCashDao();
        Pager<FetchCash> list = fd.fetchCashPager(Integer.parseInt(page_num), Integer.parseInt(page_size), user_id);
        String jsonString = JSON.toJSONString(list);
        StringBuffer sbf = new StringBuffer();
        Map<String ,String> map = new HashMap<String ,String>(); 
        if(list!=null){
            map.put("RSPCOD","000000");
            map.put("RSPMSG","刷新成功!");
            String json=JSON.toJSONString(map);
            sbf.append("{\"REP_BODY\":");
            sbf.append(json.subSequence(0, json.length()-1));
            sbf.append(",\"fetchcash_list\":");
            sbf.append(jsonString);
            sbf.append("}}");
        }else{
            map.put("RSPCOD","111111");
            map.put("RSPMSG","没有提现记录!");
            String json=JSON.toJSONString(map); 
            sbf.append("{\"REP_BODY\":");
            sbf.append(json);
            sbf.append("}");
        }
        PrintWriter writer = response.getWriter();
        writer.write(sbf.toString());
        writer.flush();
        writer.close();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        this.doGet(request, response);
    }

    public void init() throws ServletException {
    }

}
