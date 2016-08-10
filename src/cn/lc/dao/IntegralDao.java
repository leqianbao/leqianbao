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
	 * 
	 * @param userId
	 *            用户id
	 * @return
	 */
	public IntegralBean getCurrentIntegral(String userId) {
		Connection connection = null;
		IntegralBean intergralBean = null;
		try {
			connection = DBUtils.getConnection();
			String sql = "SELECT user_integral FROM lc_user_intergral WHERE user_id=?";
			DBUtils.beginTx(connection);
			intergralBean = qR.query(connection, sql,
					new BeanHandler<IntegralBean>(IntegralBean.class), userId);
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
	 * 
	 * @param userId
	 *            用户id
	 * @param integral
	 * @return
	 */
	public boolean payIntegral(String userId, long integral,String comment) {
		boolean result = false;
		Connection connection = null;
		IntegralBean intergralBean = null;
		try {
			connection = DBUtils.getConnection();
			String getIntegral = "SELECT user_integral FROM lc_user_integral WHERE user_id=?";
			String updateIntegral = "UPDATE lc_user_intergral SET user_integral = ? , update_date = ? WHERE user_id = ?";
			String addRecord = "INSERT INTO lc_user_intergral VALUES(?,?,?,?,?)";
			DBUtils.beginTx(connection);
			// 获取当前用户的积分
			intergralBean = qR.query(connection, getIntegral,
					new BeanHandler<IntegralBean>(IntegralBean.class), userId);
			int row = qR.update(connection, updateIntegral,
					intergralBean.getUsr_intergral() - integral, new Date(),
					userId);
			if (row > 0) {
				qR.update(connection, addRecord, userId, integral, new Date(),
						comment, 1);
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
	 * 积分充值
	 * 
	 * @param userId
	 *            用户id
	 * @param integral
	 *            充值的积分
	 * @return
	 */
	public boolean rechargeIntegral(String userId, long integral, String comment) {
		boolean result = false;
		Connection connection = null;
		try {
			connection = DBUtils.getConnection();
			// 获取当前积分
			String getIntegral = "SELECT user_integral FROM lc_user_integral WHERE user_id=?";
			// 更新当前积分
			String updateIntegral = "UPDATE lc_user_intergral SET user_integral = ? , update_date = ? WHERE user_id = ?";
			// 添加积分记录
			String addRecord = "INSERT INTO lc_user_intergral VALUES(?,?,?,?,?)";
			DBUtils.beginTx(connection);
			IntegralBean intergralBean = null;
			intergralBean = qR.query(connection, getIntegral,
					new BeanHandler<IntegralBean>(IntegralBean.class), userId);
			int row = qR.update(connection, updateIntegral,
					intergralBean.getUsr_intergral() + integral, new Date(),
					userId);
			if (row > 0) {
				qR.update(connection, addRecord, userId, integral, new Date(),
						comment, 1);
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
