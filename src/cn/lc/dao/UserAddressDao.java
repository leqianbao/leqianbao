package cn.lc.dao;

import java.sql.Connection;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.lc.beans.UserAddress;

public class UserAddressDao {
	QueryRunner qR = new QueryRunner();
	// @PT--获取收货地址详细信息
	public UserAddress getUserAddressById(long id){
		// 存放查询参数
		Connection connection = null;
		StringBuilder sql = new StringBuilder("SELECT * FROM lc_user_address where id=?");
		UserAddress userAddress = null;
		try {
			connection = DBUtils.getConnection();
			
			userAddress = qR.query(connection, sql.toString(), new BeanListHandler<UserAddress>(UserAddress.class), id).get(0);
		} catch (Exception e) {
			DBUtils.rollback(connection);
			e.printStackTrace();
		} finally {
			DBUtils.releaseDB(null, null, connection);
		}
		return userAddress;
	}
}
