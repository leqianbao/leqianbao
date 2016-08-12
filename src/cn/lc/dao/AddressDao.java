package cn.lc.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang3.StringUtils;

import cn.lc.beans.AddressBean;
import cn.lc.beans.BankCard;

public class AddressDao {
	QueryRunner qR = new QueryRunner();

	/**
	 * 添加地址信息
	 * 
	 * @param userId
	 *            用户Id
	 * @param address
	 *            详细地址
	 * @param phone
	 *            手机号
	 * @param name
	 *            姓名
	 * @return
	 */
	public boolean addAddress(String userId, String address, String phone,
			String name) {
		boolean result = false;
		Connection connection = null;

		try {
			connection = DBUtils.getConnection();
			String sql = "INSERT INTO  lc_user_address(user_id,address_address,address_phone,address_name) VALUES(?,?,?,?)";
			DBUtils.beginTx(connection);
			int row = qR.update(connection, sql, userId, address, phone, name);
			if (row > 0) {
				DBUtils.commit(connection);
				result = true;
			} else {
				DBUtils.rollback(connection);
				result = false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			DBUtils.rollback(connection);
			e.printStackTrace();
		} finally {
			DBUtils.releaseDB(null, null, connection);
		}
		return result;
	}

	/**
	 * 根据
	 * 
	 * @param id
	 *            根据id删除地址
	 * @return
	 */
	public boolean deleteAddress(int id) {
		boolean result = false;
		Connection connection = null;

		try {
			connection = DBUtils.getConnection();
			String sql = "DELETE FROM lc_user_address WHERE id=?";
			DBUtils.beginTx(connection);
			int row = qR.update(connection, sql, id);
			if (row > 0) {
				DBUtils.commit(connection);
				result = true;
			} else {
				DBUtils.rollback(connection);
				result = false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			DBUtils.rollback(connection);
			e.printStackTrace();
		} finally {
			DBUtils.releaseDB(null, null, connection);
		}
		return result;

	}

	/**
	 * 获取用户的地址列表
	 * 
	 * @param userId
	 *            用户id
	 * @return
	 */
	public List<AddressBean> getAddressList(int userId) {
		List<AddressBean> list = null;
		Connection connection = null;
		try {
			connection = DBUtils.getConnection();
			String sql = "SELECT * FROM lc_user_address WHERE user_id = ?";
			DBUtils.beginTx(connection);
			list = qR.query(connection, sql, new BeanListHandler<AddressBean>(
					AddressBean.class), userId);
		} catch (Exception e) {
			DBUtils.rollback(connection);
			e.printStackTrace();
		} finally {
			DBUtils.releaseDB(null, null, connection);
		}
		return list;
	}

	public boolean updateAddress(int id, String address, String phone,
			String name) {
		boolean result = false;
		Connection connection = null;
		List<Object> params=new ArrayList<>();
		StringBuilder sql=new StringBuilder();
		sql.append("UPDATE lc_user_address SET ");
		if(!StringUtils.isEmpty(address)){
			sql.append("address_address = ?");
			params.add(address);
		}
		if(!StringUtils.isEmpty(phone)){
			sql.append(",address_phone = ?");
			params.add(phone);
		}
		if(!StringUtils.isEmpty(name)){
			sql.append(",address_name = ?");
			params.add(name);
		}
		sql.append("where id = ?");
		params.add(id);
		try {
			connection = DBUtils.getConnection();
			DBUtils.beginTx(connection);
			int row = qR.update(connection, sql.toString(),params.toArray());
			if (row > 0) {
				DBUtils.commit(connection);
				result = true;
			} else {
				DBUtils.rollback(connection);
				result = false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			DBUtils.rollback(connection);
			e.printStackTrace();
		} finally {
			DBUtils.releaseDB(null, null, connection);
		}
		return result;
	}
}
