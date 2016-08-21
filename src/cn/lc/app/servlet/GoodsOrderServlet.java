package cn.lc.app.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import cn.lc.beans.AddressBean;
import cn.lc.beans.Commodity;
import cn.lc.beans.IntegralBean;
import cn.lc.beans.UserOrder;
import cn.lc.dao.CommodityDao;
import cn.lc.dao.IntegralDao;
import cn.lc.dao.UserOrderDao;
import cn.lc.json.model.REP_BODY;
import cn.lc.json.model.REQ_BODY;
import cn.lc.json.model.Root;
import cn.lc.utils.DataUtil;

public class GoodsOrderServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6169036480806676270L;
	public GoodsOrderServlet() {
		super();
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
		String order_commodity_id = reqBody.getOrder_commodity_id();
		String user_id = reqBody.getUser_id();
        String order_num = reqBody.getOrder_commodity_num();
        String addressId = reqBody.getOrder_address_id();
        
		Map<String ,String> map = new HashMap<String ,String>(); 
		
        Commodity commodity = new CommodityDao().getCommodity(order_commodity_id);
        if(commodity == null) {
			map.put("RSPCOD","222222");
			map.put("RSPMSG","对不起，您要下单的商品可能已经下架，请选择其他商品");
			PrintWriter writer = response.getWriter();
		    writer.write(DataUtil.addReqBody(map));
			writer.flush();
			writer.close();	
			return;
        }
        
        int commodity_num = commodity.getCommodity_num();
        if(commodity_num < Integer.parseInt(order_num)) {
			map.put("RSPCOD","333333");
			map.put("RSPMSG","商品库存数量不足，请更改下单数量");
			PrintWriter writer = response.getWriter();
		    writer.write(DataUtil.addReqBody(map));
			writer.flush();
			writer.close();	
			return;
        }
        
        int commodity_pay = commodity.getCommodity_pay();
        //生成一个订单对象
    	UserOrder userOrder = new UserOrder();
    	userOrder.setUser_id(Integer.parseInt(user_id));
    	userOrder.setCommodity_id(order_commodity_id);
    	userOrder.setOrder_no(DataUtil.generateOrderNo(Integer.parseInt(user_id)));
    	userOrder.setCommodity_num(Integer.parseInt(order_num));
    	userOrder.setCreate_date(new Timestamp(System.currentTimeMillis()));
    	userOrder.setEnd_date(null);
    	userOrder.setOrder_state("0");
    	userOrder.setLogistics_number("");
    	userOrder.setReceipt_address_id(Long.valueOf(addressId));
        boolean result = new UserOrderDao().insertUserOrder(userOrder,commodity_num);
		if(result){
			map.put("RSPCOD","000000");
			map.put("RSPMSG","下单成功!");
			map.put("user_id", user_id);
			map.put("order_no", userOrder.getOrder_no());
		}else{
			map.put("RSPCOD","111111");
			map.put("RSPMSG","下单失败!");
		}
		PrintWriter writer = response.getWriter();
	    writer.write(DataUtil.addReqBody(map));
		writer.flush();
		writer.close();	
	}
	public void init() throws ServletException {
	}


}
