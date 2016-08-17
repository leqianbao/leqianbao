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

import cn.lc.beans.IntegralRecord;
import cn.lc.dao.IntegralRecordDao;
import cn.lc.json.model.REP_BODY;
import cn.lc.json.model.REQ_BODY;
import cn.lc.json.model.Root;
import cn.lc.utils.Const;
import cn.lc.utils.DataUtil;

import com.alibaba.fastjson.JSON;

public class GetIntegralListServlet extends HttpServlet{

	private static final long serialVersionUID = 7687226064561166079L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");

		REP_BODY<List<IntegralRecord>> body=new REP_BODY<>();
		PrintWriter writer = response.getWriter();
		Map<String, String> map = new HashMap<>();
		IntegralRecordDao integralDao=new IntegralRecordDao();
		String date = DataUtil.readDateFromRequest(request.getInputStream());
		Root root = JSON.parseObject(date.substring(12), Root.class);
		REQ_BODY reqBody = root.getREQ_BODY();
		if(checkForm(reqBody)){
			int state=reqBody.getRecord_state();
			int pageNum=reqBody.getPageNum();
			int pageSize=reqBody.getPageSize();
			String userId=reqBody.getUser_id();
			List<IntegralRecord> integrals=integralDao.getRecordList(Integer.parseInt(userId), state, pageNum, pageSize);
			map.put(Const.CODE_KEY, Const.CODE_SUCCESS);
			map.put(Const.MSG_KEY, Const.INTEGRAL_LIST_SUCCESS);
			body.setData(integrals);
		}else{
			map.put(Const.CODE_KEY, Const.CODE_ERROR);
			map.put(Const.MSG_KEY, Const.PARAM_ERROR);
		}
		
		body.setRSPCOD(map.get(Const.CODE_KEY));
		body.setRSPMSG(map.get(Const.MSG_KEY));
		writer.write(JSON.toJSONString(body));
		writer.flush();
		writer.close();
		
		
	}
	
	public boolean checkForm(REQ_BODY request){
		int state=request.getRecord_state();
		String userId=request.getUser_id();
		int pageNum=request.getPageNum();
		int pageSize=request.getPageSize();
		if(TextUtils.isEmpty(userId)){
			return false;
		}
		if(state>2){
			return false;
		}
		if(pageNum<1){
			return false;
		}
		if(pageSize<0){
			return false;
		}
		return true;
	}

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public void init() throws ServletException {
		super.init();
	}

}
