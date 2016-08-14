package cn.lc.dao;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.lc.beans.IntegralBean;
import cn.lc.utils.DateUtil;

public class IntegralDao {
	QueryRunner qR = new QueryRunner();
	
	
	/**
	 * 当用户没有数据时，插入一条积分为0的数据
	 * 
	 * @param userId
	 *            用户id
	 * @return
	 */
	public void initIntegral(int userId) {
		Connection connection = null;
		IntegralBean intergralBean = null;
		try {
			connection = DBUtils.getConnection();
			String sql = "SELECT user_integral FROM lc_user_integral WHERE user_id=?";
			String initSql = "INSERT INTO lc_user_integral(user_id,user_integral,update_date) VALUES(?,?,?)";
			DBUtils.beginTx(connection);
			intergralBean = qR.query(connection, sql,
					new BeanHandler<IntegralBean>(IntegralBean.class), userId);
			if(intergralBean==null){
				qR.update(connection, initSql, userId,0,DateUtil.getCurrentTimestamp());
			}
			DBUtils.commit(connection);
			
		} catch (Exception e) {
			DBUtils.rollback(connection);
			e.printStackTrace();
		} finally {
			DBUtils.releaseDB(null, null, connection);
		}
		return;
	}
	
	

	/**
	 * 获取当前的积分
	 * 
	 * @param userId
	 *            用户id
	 * @return
	 */
	public IntegralBean getCurrentIntegral(int userId) {
		Connection connection = null;
		IntegralBean intergralBean = null;
		try {
			connection = DBUtils.getConnection();
			String sql = "SELECT user_integral FROM lc_user_integral WHERE user_id=?";
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
	public boolean payIntegral(int userId, int integral,String comment) {
		boolean result = false;
		Connection connection = null;
		IntegralBean intergralBean = null;
		try {
			
			connection = DBUtils.getConnection();
			String getIntegral = "SELECT user_integral FROM lc_user_integral WHERE user_id=?";
			String updateIntegral = "UPDATE lc_user_integral SET user_integral = ? , update_date = ? WHERE user_id = ?";
			String addRecord = "INSERT INTO lc_user_integral_record(user_id,integral,create_time,comment,record_state) VALUES(?,?,?,?,?)";
			initIntegral(userId);
			DBUtils.beginTx(connection);
			// 获取当前用户的积分
			intergralBean = qR.query(connection, getIntegral,
					new BeanHandler<IntegralBean>(IntegralBean.class), userId);
			int row = qR.update(connection, updateIntegral,
					intergralBean.getUser_integral() - integral, DateUtil.getCurrentTimestamp(),
					userId);
			if (row > 0) {
				qR.update(connection, addRecord, userId, integral, DateUtil.getCurrentTimestamp(),
						comment, 2);
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
	public boolean rechargeIntegral(int userId, int integral, String comment) {
		boolean result = false;
		Connection connection = null;
		try {
			connection = DBUtils.getConnection();
			// 获取当前积分
			String getIntegral = "SELECT user_integral FROM lc_user_integral WHERE user_id=?";
			// 更新当前积分
			String updateIntegral = "UPDATE lc_user_integral SET user_integral = ? , update_date = ? WHERE user_id = ?";
			// 添加积分记录
			String addRecord = "INSERT INTO lc_user_integral_record(user_id,integral,create_time,comment,record_state) VALUES(?,?,?,?,?)";
			initIntegral(userId);
			DBUtils.beginTx(connection);
			IntegralBean intergralBean = null;
			intergralBean = qR.query(connection, getIntegral,
					new BeanHandler<IntegralBean>(IntegralBean.class), userId);
			int row = qR.update(connection, updateIntegral,
					intergralBean.getUser_integral() + integral, new Timestamp(System.currentTimeMillis()),
					userId);
			if (row > 0) {
				qR.update(connection, addRecord, userId, integral, new Timestamp(System.currentTimeMillis()),
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

	
	//@app--根据用户ID获取相应的积分信息
	public IntegralBean getUserIntergralById(int userId){
		Connection connection = null;
		IntegralBean integralBean = null;
		try {
			connection = DBUtils.getConnection();
			String sql = "SELECT * FROM lc_user_integral WHERE user_id = ?";
			DBUtils.beginTx(connection);
			integralBean = qR.query(connection,sql,new BeanHandler<IntegralBean>(IntegralBean.class),userId);
		} catch (Exception e) {
			DBUtils.rollback(connection);
			e.printStackTrace();
		} finally{
			DBUtils.releaseDB(null, null, connection);
		}
    	return integralBean;
	}
	//@APP--插入积分数据
	public boolean insertUserIntegral(IntegralBean integralBean){
		boolean  result = false;
		Connection connection = null;
		try {
			connection = DBUtils.getConnection();
			String sql = "INSERT INTO lc_user_integral (user_id,user_integral,update_date) VALUES (?,?,?)";
			DBUtils.beginTx(connection);
			int isSuccess = qR.update(connection,sql,integralBean.getUser_id(),
					integralBean.getUser_integral(),integralBean.getUpdate_date());
			if(isSuccess==1){
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
}
