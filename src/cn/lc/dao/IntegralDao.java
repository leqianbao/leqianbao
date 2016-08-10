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
import cn.lc.beans.UserOrder;

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

	
	//@app--根据用户ID获取相应的积分信息
	public IntegralBean getUserIntergralById(int userId){
		Connection connection = null;
		IntegralBean integralBean = null;
		try {
			connection = DBUtils.getConnection();
			String sql = "SELECT * FROM lc_user_intergral WHERE user_id = ?";
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
			String sql = "INSERT INTO lc_user_intergral (user_id,user_integral,update_date) VALUES (?,?,?)";
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
