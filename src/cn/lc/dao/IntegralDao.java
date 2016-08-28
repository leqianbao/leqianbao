package cn.lc.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.lc.beans.Commodity;
import cn.lc.beans.IntegralBean;
import cn.lc.beans.Pager;
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
			intergralBean = qR.query(connection, sql, new BeanHandler<IntegralBean>(IntegralBean.class), userId);
			if (intergralBean == null) {
				qR.update(connection, initSql, userId, 0, DateUtil.getCurrentTimestamp());
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

	public boolean initIntegralWithVal(String userId, int value) {
		boolean isSave = false;
		Connection connection = null;
		IntegralBean intergralBean = null;
		try {
			connection = DBUtils.getConnection();
			String sql = "SELECT user_integral FROM lc_user_integral WHERE user_id=?";
			String initSql = "INSERT INTO lc_user_integral(user_id,user_integral,update_date) VALUES(?,?,?)";
			String updateSql = "UPDATE lc_user_integral SET user_integral = ? , update_date = ? WHERE user_id = ? ";
			DBUtils.beginTx(connection);
			intergralBean = qR.query(connection, sql, new BeanHandler<IntegralBean>(IntegralBean.class), userId);
			if (intergralBean == null) {
				qR.update(connection, initSql, userId, value, DateUtil.getCurrentTimestamp());
			} else {
				qR.update(connection, updateSql, value, DateUtil.getCurrentTimestamp(), userId);
			}
			DBUtils.commit(connection);
			isSave = true;
		} catch (Exception e) {
			DBUtils.rollback(connection);
			e.printStackTrace();
		} finally {
			DBUtils.releaseDB(null, null, connection);
		}
		return isSave;
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
			intergralBean = qR.query(connection, sql, new BeanHandler<IntegralBean>(IntegralBean.class), userId);

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
	public boolean payIntegral(int userId, int integral, String comment, BigDecimal money) {
		boolean result = false;
		Connection connection = null;
		IntegralBean intergralBean = null;
		try {

			connection = DBUtils.getConnection();
			String getIntegral = "SELECT user_integral FROM lc_user_integral WHERE user_id=?";
			String updateIntegral = "UPDATE lc_user_integral SET user_integral = ? , update_date = ? WHERE user_id = ?";
			String addRecord = "INSERT INTO lc_user_integral_record(user_id,integral,create_time,comment,record_state,money) VALUES(?,?,?,?,?,?)";
			initIntegral(userId);
			DBUtils.beginTx(connection);
			// 获取当前用户的积分
			intergralBean = qR.query(connection, getIntegral, new BeanHandler<IntegralBean>(IntegralBean.class),
					userId);
			int row = qR.update(connection, updateIntegral, intergralBean.getUser_integral() - integral,
					DateUtil.getCurrentTimestamp(), userId);
			if (row > 0) {
				qR.update(connection, addRecord, userId, integral, DateUtil.getCurrentTimestamp(), comment, 2, money);
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
			intergralBean = qR.query(connection, getIntegral, new BeanHandler<IntegralBean>(IntegralBean.class),
					userId);
			int row = qR.update(connection, updateIntegral, intergralBean.getUser_integral() + integral,
					new Timestamp(System.currentTimeMillis()), userId);
			if (row > 0) {
				qR.update(connection, addRecord, userId, integral, new Timestamp(System.currentTimeMillis()), comment,
						1);
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

	// @app--根据用户ID获取相应的积分信息
	public IntegralBean getUserIntergralById(int userId) {
		Connection connection = null;
		IntegralBean integralBean = null;
		try {
			connection = DBUtils.getConnection();
			String sql = "SELECT * FROM lc_user_integral WHERE user_id = ?";
			DBUtils.beginTx(connection);
			integralBean = qR.query(connection, sql, new BeanHandler<IntegralBean>(IntegralBean.class), userId);
		} catch (Exception e) {
			DBUtils.rollback(connection);
			e.printStackTrace();
		} finally {
			DBUtils.releaseDB(null, null, connection);
		}
		return integralBean;
	}

	// @APP--插入积分数据
	public boolean insertUserIntegral(IntegralBean integralBean) {
		boolean result = false;
		Connection connection = null;
		try {
			connection = DBUtils.getConnection();
			String sql = "INSERT INTO lc_user_integral (user_id,user_integral,update_date) VALUES (?,?,?)";
			DBUtils.beginTx(connection);
			int isSuccess = qR.update(connection, sql, integralBean.getUser_id(), integralBean.getUser_integral(),
					integralBean.getUpdate_date());
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

	public Pager<IntegralBean> getUserIntegralList(String user_phone, int page_num, int page_size) {
		Pager<IntegralBean> page = new Pager<IntegralBean>(0, 0, 0, 0, null);

		Connection connection = null;
		List<IntegralBean> list = null;
		List<IntegralBean> allList = null;

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT lc_user_integral.user_integral," + "lc_user_integral.update_date, "
				+ "lc_user.user_id,lc_user.user_name, " + "lc_user.user_phone" + " FROM lc_user_integral ");
		sql.append(" right join lc_user on lc_user_integral.user_id = lc_user.user_id where 1=1 ");
		StringBuffer sql2 = new StringBuffer();
		sql2.append("SELECT lc_user_integral.user_integral," + "lc_user_integral.update_date, lc_user.user_id,"
				+ "lc_user.user_name, lc_user.user_name ,lc_user.user_phone " + "FROM lc_user_integral  ");
		sql2.append(" right join lc_user on lc_user_integral.user_id = lc_user.user_id where 1=1  ");
		// 参数集合
		List<Object> params = new ArrayList<Object>();
		if (null != user_phone && !"".equals(user_phone.trim())) {
			sql.append(" and lc_user.user_phone like ? ");
			sql2.append(" and lc_user.user_phone like ? ");
			params.add("%" + user_phone.trim() + "%" );
		}
		// 创建参数字符串数组
		Object[] paramStr = params.toArray(new String[params.size()]);

		long fromIndex = page_size * (page_num - 1);

		long totalRecord = 0;
		sql.append(" limit " + fromIndex + ", " + page_size);
		try {
			connection = DBUtils.getConnection();
			DBUtils.beginTx(connection);
			// 获取总记录数
			allList = qR.query(connection, sql2.toString(), new BeanListHandler<IntegralBean>(IntegralBean.class),
					paramStr);
			if (allList == null) {
				totalRecord = 0;
			} else {
				totalRecord = allList.size();
			}
			list = qR.query(connection, sql.toString(), new BeanListHandler<IntegralBean>(IntegralBean.class),
					paramStr);
			long totalPage = totalRecord / page_size;
			if (totalRecord % page_size != 0) {
				totalPage++;
			}
			// 组装pager对象
			page = new Pager<IntegralBean>(page_size, page_num, totalRecord, totalPage, list);
		} catch (Exception e) {
			DBUtils.rollback(connection);
			e.printStackTrace();
		} finally {
			DBUtils.releaseDB(null, null, connection);
		}
		return page;
	}
}
