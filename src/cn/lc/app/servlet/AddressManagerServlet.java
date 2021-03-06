package cn.lc.app.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.TextUtils;

import cn.lc.dao.AddressDao;
import cn.lc.json.model.Body;
import cn.lc.json.model.REP_BODY;
import cn.lc.json.model.REQ_BODY;
import cn.lc.json.model.Root;
import cn.lc.utils.Const;
import cn.lc.utils.DataUtil;

import com.alibaba.fastjson.JSON;

public class AddressManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 6413357406907742657L;

	public AddressManagerServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");

		Map<String, String> map = new HashMap<>();
		String date = DataUtil.readDateFromRequest(request.getInputStream());
		Root root = JSON.parseObject(date.substring(12), Root.class);
		REQ_BODY reqBody = root.getREQ_BODY();
		int type = reqBody.getAddress_type();
		switch (type) {
		case 1:// 新增
			map = addAddress(reqBody);
			break;
		case 2:// 修改
			if (checkForm(reqBody)) {
				map = updateAddress(reqBody);
			}else{
				map.put(Const.CODE_KEY, Const.CODE_ERROR);
				map.put(Const.MSG_KEY, Const.PARAM_ERROR);
			}
			break;
		case 3:// 删除
			if (checkForm(reqBody)) {
				map = deleteAddress(reqBody.getAddress_id());
			}else{
				map.put(Const.CODE_KEY, Const.CODE_ERROR);
				map.put(Const.MSG_KEY, Const.PARAM_ERROR);
			}
			break;
		default:
			map.put(Const.CODE_KEY, Const.CODE_ERROR);
			map.put(Const.MSG_KEY, Const.PARAM_ERROR);
			break;
		}
		PrintWriter writer = response.getWriter();
		Body<Boolean> body = new Body<>();
		body.setRSPCOD(map.get(Const.CODE_KEY));
		;
		body.setRSPMSG(map.get(Const.MSG_KEY));
		;
		REP_BODY<Boolean> repBody = new REP_BODY<>();
		repBody.REP_BODY = body;
		writer.write(JSON.toJSONString(repBody));
		writer.flush();
		writer.close();
	}

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public void init() throws ServletException {
		super.init();
	}

	private Map<String, String> addAddress(REQ_BODY request) {
		Map<String, String> map = new HashMap<>();
		AddressDao addressDao = new AddressDao();
		if (TextUtils.isEmpty(request.getAddress_name())) {
			map.put(Const.CODE_KEY, Const.CODE_ERROR);
			map.put(Const.MSG_KEY, Const.ADDRESS_NAME_ERROR);
			return map;
		}

		if (TextUtils.isEmpty(request.getAddress_phone())) {
			map.put(Const.CODE_KEY, Const.CODE_ERROR);
			map.put(Const.MSG_KEY, Const.ADDRESS_PHONE_ERROR);
			return map;
		}

		if (TextUtils.isEmpty(request.getAddress())) {
			map.put(Const.CODE_KEY, Const.CODE_ERROR);
			map.put(Const.MSG_KEY, Const.ADDRESS_ADDRESS_ERROR);
			return map;
		}

		boolean back = addressDao.addAddress(request.getUser_id(), request.getAddress(), request.getAddress_phone(),
				request.getAddress_name());
		if (back) {
			map.put(Const.CODE_KEY, Const.CODE_SUCCESS);
			map.put(Const.MSG_KEY, Const.ADDRESS_ADD_SUCCESS);
		} else {
			map.put(Const.CODE_KEY, Const.CODE_ERROR);
			map.put(Const.MSG_KEY, Const.ADDRESS_ADD_ERROR);
		}
		return map;
	}

	private Map<String, String> deleteAddress(int id) {
		Map<String, String> map = new HashMap<>();
		AddressDao addressDao = new AddressDao();
		boolean back = addressDao.deleteAddress(id);
		if (back) {
			map.put(Const.CODE_KEY, Const.CODE_SUCCESS);
			map.put(Const.MSG_KEY, Const.ADDRESS_DELETE_SUCCESS);
		} else {
			map.put(Const.CODE_KEY, Const.CODE_ERROR);
			map.put(Const.MSG_KEY, Const.ADDRESS_DELETE_ERROR);
		}
		return map;
	}

	private Map<String, String> updateAddress(REQ_BODY request) {
		Map<String, String> map = new HashMap<>();
		AddressDao addressDao = new AddressDao();
		boolean back = addressDao.updateAddress(request.getAddress_id(), request.getAddress(),
				request.getAddress_phone(), request.getAddress_name());
		if (back) {
			map.put(Const.CODE_KEY, Const.CODE_SUCCESS);
			map.put(Const.MSG_KEY, Const.ADDRESS_NOTIFY_SUCCESS);
		} else {
			map.put(Const.CODE_KEY, Const.CODE_ERROR);
			map.put(Const.MSG_KEY, Const.ADDRESS_NOTIFY_ERROR);
		}
		return map;
	}

	public boolean checkForm(REQ_BODY request) {
		int addressId = request.getAddress_id();
		if (addressId == 0) {
			return false;
		}
		return true;
	}

}
