package cn.lc.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.http.util.TextUtils;

import cn.lc.beans.IntegralBean;
import cn.lc.beans.IntegralRecord;

public class IntegralRecordDao {
	QueryRunner qR = new QueryRunner();
	
	
	/**
	 * 根据条件获取积分列表
	 * @param userId 用户id
	 * @param date 时间
	 * @param state 状态 0：全部，1：收入，2：支出
	 * @return
	 */
	public List<IntegralRecord> getRecordList(String userId,long date,int state,int pageNum,int pageSize){
		List<IntegralRecord> integralRecords=null;
		Connection connection = null;
		List<Object> params=new ArrayList<>();
		try {
			connection = DBUtils.getConnection();
			StringBuilder sql=new StringBuilder("SELECT * FROM lc_user_integral_record WHERE 1=1");
			if(!TextUtils.isEmpty(userId)){
				sql.append(" and user_id=?");
				params.add(userId);
			}
//			if(date!=0){
//				
//			}
			if(state!=0){
				sql.append(" and state=?");
				params.add(state);
			}
			long fromIndex = pageSize * (pageNum - 1);
			sql.append(" order by create_date desc   limit " + fromIndex + ", " + pageSize);
			DBUtils.beginTx(connection);
			integralRecords=qR.query(connection, sql.toString(),new BeanListHandler<IntegralRecord>(IntegralRecord.class),params.toArray());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			DBUtils.rollback(connection);
			e.printStackTrace();
		} finally {
			DBUtils.releaseDB(null, null, connection);
		}	
		return integralRecords;
	}
}
