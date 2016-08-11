package cn.lc.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.lc.beans.GetoutMoney;
import cn.lc.beans.Pager;

public class GetoutMoneyDao {
	
	QueryRunner qR = new QueryRunner();
	
	//添加提现订单
	public boolean  addOutMoney(GetoutMoney m){

		boolean  result = false;
		Connection connection = null;
		try {
			connection = DBUtils.getConnection();
			String sql = "INSERT INTO lc_getout_money (getout_num,getout_money,getout_bankcode,getout_bankname,user_id,created_by,stamp_created,updated_by,stamp_updated,state) VALUES (?,?,?,?,?,?,?,?,?,?)";
			String sql2 = "INSERT INTO lc_message (msg_title,msg_type,user_id,msg_content,created_by,stamp_created,updated_by,stamp_updated,state) VALUES (?,?,?,?,?,?,?,?,?)";	
			SimpleDateFormat gs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			StringBuffer cont = new StringBuffer("尊敬的"+m.getUser().getUser_name()+"用户,你在");
			cont.append(gs.format(m.getStamp_created()));
			cont.append("提现"+m.getGetout_money()+"元的到卡号为："+m.getGetout_bankcode()+"的"+m.getGetout_bankname()+"，预计一个小时后到账，");
			cont.append("提现流水号为："+m.getGetout_num());
			DBUtils.beginTx(connection);
			int isSuccess = qR.update(connection,sql,m.getGetout_num(),m.getGetout_money(),m.getGetout_bankcode(),m.getGetout_bankname(),m.getUser().getUser_id(),m.getCreated_by(),m.getStamp_created(),m.getUpdated_by(),m.getStamp_updated(),m.getState());
			int isSuccess2 = qR.update(connection,sql2,"提现消息",1,m.getUser().getUser_id(),cont.toString(),m.getCreated_by(),m.getStamp_created(),m.getUpdated_by(),m.getStamp_updated(),m.getState());
			if(isSuccess==1&&isSuccess2==1){
			DBUtils.commit(connection);
			result = true;
			} else{
				DBUtils.rollback(connection);
				result = false;
			}
		} catch (Exception e) {
			DBUtils.rollback(connection);
			e.printStackTrace();
		} finally{
			DBUtils.releaseDB(null, null, connection);
			return result;
		}
	}
	
	//获取提现订单列表
	 @SuppressWarnings("rawtypes")
		private ScalarHandler scalarHandler = new ScalarHandler() {  
		        @Override  
		        public Object handle(ResultSet rs) throws SQLException {  
		            Object obj = super.handle(rs);  
		            if (obj instanceof BigInteger)  
		                return ((BigInteger) obj).longValue();  
		            return obj;  
		        }  
		    };  
	
	//获取提现订单列表
	public Pager<GetoutMoney> getoutList(int pageNum,int pageSize,String uid){
		Pager<GetoutMoney> result = null;
		// 存放查询参数
		Connection connection = null;
		StringBuilder sql = new StringBuilder(
				"SELECT getout_id,getout_num,getout_money,getout_bankcode,getout_bankname,user_id,created_by,stamp_created,updated_by,stamp_updated,state FROM lc_getout_money WHERE 1=1 AND user_id=?");
		StringBuilder countSql = new StringBuilder("SELECT  count(getout_id) as totalRecord FROM lc_getout_money WHERE 1=1 AND user_id=?");
		sql.append("  and state = ?");
		countSql.append(" and state = ?");
		long totalRecord  = 0;
		// 起始索引
		long fromIndex	= pageSize * (pageNum -1);			
		// 使用limit关键字，实现分页
		sql.append(" order by stamp_created desc   limit " + fromIndex + ", " + pageSize );
		List<GetoutMoney> msgList = new ArrayList<GetoutMoney>();
		try {
			connection = DBUtils.getConnection();
			DBUtils.beginTx(connection);
			// 获取总记录数
			totalRecord =(Long) (Number) qR.query(connection,countSql.toString(),scalarHandler,uid,"A");
			// 获取查询的消息记录
			msgList  = qR.query(connection,sql.toString(),new BeanListHandler<GetoutMoney>(GetoutMoney.class),uid,"A");
			DBUtils.commit(connection);
			//获取总页数
			long totalPage = totalRecord / pageSize;
			if(totalRecord % pageSize !=0){
				totalPage++;
			}
			// 组装pager对象
			result = new Pager<GetoutMoney>(pageSize, pageNum, totalRecord, totalPage, msgList);
		} catch (Exception e) {
			DBUtils.rollback(connection);
			e.printStackTrace();
		} finally{
			DBUtils.releaseDB(null, null, connection);
		}
		return result;
	}
	
	//统计提现
	
	public String countMoney(String uid){
		String result = "";
		Connection connection = null;
		String sql = "SELECT  sum(getout_money) as result FROM lc_getout_money WHERE 1=1 AND user_id=? and state=?";
		try {
			connection = DBUtils.getConnection();
			DBUtils.beginTx(connection);
			if(qR.query(connection,sql,scalarHandler,uid,"A")!=null){
				result = qR.query(connection,sql,scalarHandler,uid,"A").toString();	
			}
			DBUtils.commit(connection);
	} catch (Exception e) {
		DBUtils.rollback(connection);
		e.printStackTrace();
	} finally{
		DBUtils.releaseDB(null, null, connection);
	}
		return result;
	}

}
