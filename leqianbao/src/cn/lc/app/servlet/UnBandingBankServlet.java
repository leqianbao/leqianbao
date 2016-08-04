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

import cn.lc.beans.BankCard;
import cn.lc.dao.BankCardDao;
import cn.lc.json.model.REQ_BODY;
import cn.lc.json.model.Root;
import cn.lc.utils.DataUtil;

import com.alibaba.fastjson.JSON;

public class UnBandingBankServlet extends HttpServlet {


	private static final long serialVersionUID = 3568602996724403453L;
	public UnBandingBankServlet() {
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

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		String date = DataUtil.readDateFromRequest(request.getInputStream());
		Root root = JSON.parseObject(date.substring(12), Root.class);
		REQ_BODY reqBody = root.getREQ_BODY();
		String user_phone = reqBody.getUser_phone();
		String bank_id = reqBody.getBank_id();
		BankCard bc = new BankCard();
		bc.setBank_id(Integer.parseInt(bank_id));
		bc.setUpdated_by(user_phone);
		bc.setStamp_updated(new Date());
		bc.setState("B");
		BankCardDao bcd = new BankCardDao();
		Map <String,String>map = new HashMap<String,String>(); 
		if(bcd.delBankCard(bc)){
			map.put("RSPCOD","000000");
			map.put("RSPMSG","解除绑定成功!");
		} else{
			map.put("RSPCOD","111111");
			map.put("RSPMSG","解除绑定失败!");
		}
		PrintWriter writer = response.getWriter();
	    writer.write(DataUtil.addReqBody(map));
		writer.flush();
		writer.close();	
	}

	public void init() throws ServletException {
	}

}
