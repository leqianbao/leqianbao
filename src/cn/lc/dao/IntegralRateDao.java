package cn.lc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.lc.beans.IntegralRateBean;

public class IntegralRateDao {
	QueryRunner qR = new QueryRunner();
	
	public IntegralRateBean getInegralRate(){
		Connection connection = null;
		IntegralRateBean integralRate = new IntegralRateBean();
		StringBuilder sql = new StringBuilder();
		sql.append(" select * from lc_integral_rate;");
		try {
	        connection = DBUtils.getConnection();
	        DBUtils.beginTx(connection);
	        // 获取总记录数
	        integralRate = qR.query(connection, sql.toString(), new BeanListHandler<IntegralRateBean>(IntegralRateBean.class)).get(0);
	    } catch (Exception e) {
	        DBUtils.rollback(connection);
	        e.printStackTrace();
	    } finally {
	        DBUtils.releaseDB(null, null, connection);
	    }
	    return integralRate;
	}
	
	/**
	 * 更新积分汇率
	 * */
	public boolean updateIntegralRate(int id, int integral_rate){
		int update_flag = 0;
    	Connection connection = null;
    	PreparedStatement st = null;
        
        StringBuffer sql = new StringBuffer();
        sql.append(" update lc_integral_rate set integral_rate = ? ");
        sql.append(" WHERE id = ?");
        try {
            connection = DBUtils.getConnection();
            st = connection.prepareStatement(sql.toString());
            st.setInt(1, integral_rate);
            st.setInt(2, id);
            update_flag = st.executeUpdate();
        } catch (Exception e) {
            DBUtils.rollback(connection);
            e.printStackTrace();
        } finally {
            DBUtils.releaseDB(null, null, connection);
        }
        if(update_flag == 0){
        	return false;
        }else{
        	return true;
        }
	}
}
