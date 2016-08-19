package cn.lc.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.lc.beans.User;
import cn.lc.beans.UserChildBean;

public class UserChildDao {

	QueryRunner qR = new QueryRunner();

	public boolean addChildUser(int userId, String childName, String phone, String idCard, String bankName,
			String bankAccount) {
		boolean result = false;
		Connection connection = null;
		try {
			connection = DBUtils.getConnection();
			String sql = "INSERT INTO lc_user_child(user_id,child_name,child_phone,child_id_card,child_bank_name,child_bank_account) VALUES(?,?,?,?,?,?)";
			DBUtils.beginTx(connection);
			int isSuccess = qR.update(connection, sql, userId, childName, phone, idCard, bankName, bankAccount);
			if (isSuccess == 1) {
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
	
	public List<UserChildBean> getChildUserList(int userId){
    	Connection connection = null;
		List<UserChildBean> users=new ArrayList<>();
		try {
			connection = DBUtils.getConnection();
			String sql = "SELECT user_id,child_name,child_phone,child_id_card,child_bank_name,child_bank_account FROM lc_user_child WHERE user_id = ?";
			DBUtils.beginTx(connection);
			users = qR.query(connection,sql,new BeanListHandler<UserChildBean>(UserChildBean.class),userId);
		} catch (Exception e) {
			DBUtils.rollback(connection);
			e.printStackTrace();
		} finally{
			DBUtils.releaseDB(null, null, connection);
		}
    	return users;
    }
}
