package cn.lc.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.lc.beans.IntegralBean;

public class IntegralDao {
	QueryRunner qR = new QueryRunner();

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
}
