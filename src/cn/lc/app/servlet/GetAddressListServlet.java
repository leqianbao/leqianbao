package cn.lc.app.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.util.TextUtils;

import com.alibaba.fastjson.JSON;

import cn.lc.beans.AddressBean;
import cn.lc.dao.AddressDao;
import cn.lc.json.model.REP_BODY;
import cn.lc.json.model.REQ_BODY;
import cn.lc.json.model.Root;
import cn.lc.utils.Const;
import cn.lc.utils.DataUtil;

public class GetAddressListServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");

		REP_BODY<List<AddressBean>> body=new REP_BODY<>();
		PrintWriter writer = response.getWriter();
		Map<String, String> map = new HashMap<>();
		AddressDao addressDao=new AddressDao();
		String date = DataUtil.readDateFromRequest(request.getInputStream());
		Root root = JSON.parseObject(date.substring(12), Root.class);
		REQ_BODY reqBody = root.getREQ_BODY();
		if(TextUtils.isEmpty(reqBody.getUser_id())){
			map.put(Const.CODE_KEY, Const.CODE_ERROR);
			map.put(Const.MSG_KEY, Const.PARAM_ERROR);
		}else{
			List<AddressBean> addressBeans=addressDao.getAddressList(Integer.parseInt(reqBody.getUser_id()));
			map.put(Const.CODE_KEY, Const.CODE_SUCCESS);
			map.put(Const.MSG_KEY, Const.ADDRESS_LIST_SUCCESS);
			body.setData(addressBeans);
		}
		body.setRSPCOD(map.get(Const.CODE_KEY));
		body.setRSPMSG(map.get(Const.MSG_KEY));
		writer.write(JSON.toJSONString(body));
		writer.flush();
		writer.close();
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
	}

	private static final long serialVersionUID = -7870723042179387132L;
	
	
}
