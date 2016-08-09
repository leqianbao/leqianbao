package cn.lc.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.lc.beans.IntegralBean;

public class IntegralDao {
	QueryRunner qR = new QueryRunner();

	
	/**
	 * 获取当前的积分
	 * @param userId 用户id
	 * @return
	 */
	public IntegralBean getCurrentIntegral(String userId) {
		Connection connection = null;
		IntegralBean intergralBean = null;
		try {
			connection = DBUtils.getConnection();
			String sql = "SELECT user_integral FROM lc_user_intergral WHERE user_id=?";
			DBUtils.beginTx(connection);
			intergralBean = qR
					.query(connection, sql, new BeanHandler<IntegralBean>(
							IntegralBean.class), userId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			DBUtils.rollback(connection);
			e.printStackTrace();
		} finally {
			DBUtils.releaseDB(null, null, connection);
		}
		return intergralBean;
	}
	
	/**
	 * 支付积分
	 * @param userId 用户id
	 * @param integral 
	 * @return
	 */
	public boolean payIntegral(String userId,long integral){
		boolean result = false;
		Connection connection = null;
		try {
			connection = DBUtils.getConnection();
			String sql = "UPDATE lc_user_intergral SET user_integral = ? , update_date = ? WHERE user_id = ?";
			DBUtils.beginTx(connection);
			int row = qR.update(connection, sql, integral,new Date(),userId);
			if (row > 0) {
				DBUtils.commit(connection);
				result = true;
			} else {
				DBUtils.rollback(connection);
				result = false;
			}
		} catch (Exception e) {
			DBUtils.rollback(connection);
			e.printStackTrace();
		} finally {
			DBUtils.releaseDB(null, null, connection);
		}
		return result;
	}
	
	/**
	 * 根据用户id获取积分列表
	 * @param userId 用户Id
	 * @param getState 0：全部 1：收入 2：支出
	 * @return
	 */
	public List<IntegralBean> getIntegralList(String userId,int getState){
		List<IntegralBean> integrals=null;
		Connection connection=null;
		try {
			connection=DBUtils.getConnection();
			String sql="SELECT * FROM lc_user_intergral WHERE user_id=? and get_state=?";
			DBUtils.beginTx(connection);
			integrals=qR.query(connection, sql,new BeanListHandler<IntegralBean>(IntegralBean.class),userId,getState);
		} catch (Exception e) {
			DBUtils.rollback(connection);
			e.printStackTrace();
		}finally {
			DBUtils.releaseDB(null, null, connection);
		}
		return integrals;
	}
	
	public boolean rechargeIntegral(String userId){
		
	}
	
	
	
	
	
}
