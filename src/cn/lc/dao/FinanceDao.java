package cn.lc.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.lc.beans.Finance;
import cn.lc.beans.OrderDetial;
import cn.lc.beans.Pager;

public class FinanceDao {
	QueryRunner qR = new QueryRunner();

	//@APP--购买理财产品
	public boolean addFinance(Finance f){
		boolean  result = false;
		Connection connection = null;
		try {
			connection = DBUtils.getConnection();
			String sql = "INSERT INTO lc_finance (finance_num,finance_money,user_id,product_id,created_by,stamp_created,updated_by,stamp_updated,state) VALUES (?,?,?,?,?,?,?,?,?)";
			String sql2 = "INSERT INTO lc_message (msg_title,msg_type,user_id,msg_content,created_by,stamp_created,updated_by,stamp_updated,state) VALUES (?,?,?,?,?,?,?,?,?)";
			SimpleDateFormat gs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			DecimalFormat    df   = new DecimalFormat("######0.00");
			StringBuffer cont = new StringBuffer("尊敬的"+f.getUser().getUser_name()+"用户,你在");
			cont.append(gs.format(f.getStamp_created()));
			cont.append("购买了"+f.getFinance_money()+"元的"+f.getFinanceProduct().getProduct_name()+"预计到期收益为："+df.format(f.getFinance_money()*f.getFinanceProduct().getInterest_rate()*f.getFinanceProduct().getInvest_days()/360/100));
			cont.append(",订单号为："+f.getFinance_num());
			
			DBUtils.beginTx(connection);
			int isSuccess = qR.update(connection,sql,f.getFinance_num(),f.getFinance_money(),f.getUser().getUser_id(),f.getFinanceProduct().getProduct_id(),f.getCreated_by(),f.getStamp_created(),f.getUpdated_by(),f.getStamp_updated(),f.getState());
			int isSuccess2 = qR.update(connection,sql2,"购买理财消息",1,f.getUser().getUser_id(),cont.toString(),f.getCreated_by(),f.getStamp_created(),f.getUpdated_by(),f.getStamp_updated(),f.getState());
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
		}
		return result;
	}
	
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
	//@APP--分页理财购买记录
	public Pager<OrderDetial> financePager(int pageNum,int pageSize,String uid){
		Pager<OrderDetial> result = null;
		// 存放查询参数
		Connection connection = null;
		StringBuilder sql = new StringBuilder(
				"SELECT A.finance_id, A.finance_num, A.finance_money, A.stamp_created,B.interest_rate,B.invest_days,B.product_name,truncate( A.finance_money*B.interest_rate*B.invest_months/12/100,2) as money_rate  from lc_finance A ,lc_finance_product B where 1=1 AND A.product_id=B.product_id  AND user_id=?");
		StringBuilder countSql = new StringBuilder("SELECT  count(finance_id) as totalRecord FROM lc_finance WHERE 1=1 AND user_id=?");
		sql.append("  and A.state = ?");
		countSql.append(" and state = ?");
		long totalRecord  = 0;
		// 起始索引
		long fromIndex	= pageSize * (pageNum -1);			
		// 使用limit关键字，实现分页
		sql.append(" order by A.stamp_created desc  limit " + fromIndex + ", " + pageSize);
		List<OrderDetial> msgList = new ArrayList<OrderDetial>();
		try {
			connection = DBUtils.getConnection();
			DBUtils.beginTx(connection);
			// 获取总记录数
			totalRecord =(Long) (Number) qR.query(connection,countSql.toString(),scalarHandler,uid,"A");
			// 获取查询的消息记录
			msgList  = qR.query(connection,sql.toString(),new BeanListHandler<OrderDetial>(OrderDetial.class),uid,"A");
			//获取总页数
			long totalPage = totalRecord / pageSize;
			if(totalRecord % pageSize !=0){
				totalPage++;
			}
			// 组装pager对象
			result = new Pager<OrderDetial>(pageSize, pageNum, totalRecord, totalPage, msgList);
		} catch (Exception e) {
			DBUtils.rollback(connection);
			e.printStackTrace();
		} finally{
			DBUtils.releaseDB(null, null, connection);
		}
		return result;
	}
	
	
	//@APP--统计理财
	public String countMoney(String uid){
		String result ="";
		Connection connection = null;
		String sql = "SELECT  sum(finance_money) as result FROM lc_finance WHERE 1=1 AND is_count=1 AND user_id=? AND state=?";
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
	
	
	//@PT---理财查询

}
