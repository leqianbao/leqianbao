package cn.lc.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.http.util.TextUtils;

import cn.lc.beans.Commodity;
import cn.lc.beans.IntegralBean;
import cn.lc.beans.IntegralRecord;
import cn.lc.beans.Pager;

public class IntegralRecordDao {
	QueryRunner qR = new QueryRunner();

	/**
	 * 根据条件获取积分列表
	 * 
	 * @param userId
	 *            用户id
	 * @param date
	 *            时间
	 * @param state
	 *            状态 0：全部，1：收入，2：支出
	 * @return
	 */
	public List<IntegralRecord> getRecordList(int userId, int state, int pageNum, int pageSize) {
		List<IntegralRecord> integralRecords = null;
		Connection connection = null;
		List<Object> params = new ArrayList<>();
		try {
			connection = DBUtils.getConnection();
			StringBuilder sql = new StringBuilder("SELECT * FROM lc_user_integral_record WHERE 1=1");
			if (state != 0) {
				sql.append(" and record_state=?");
				params.add(state);
			}
			long fromIndex = pageSize * (pageNum - 1);
			sql.append(" order by " + "\'create_date\'" + "desc limit " + fromIndex + ", " + pageSize);
			DBUtils.beginTx(connection);
			integralRecords = qR.query(connection, sql.toString(),
					new BeanListHandler<IntegralRecord>(IntegralRecord.class), params.toArray());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			DBUtils.rollback(connection);
			e.printStackTrace();
		} finally {
			DBUtils.releaseDB(null, null, connection);
		}
		return integralRecords;
	}

	/**
	 * 根据条件获取积分列表
	 * 
	 * @param userId
	 *            用户id
	 * @param date
	 *            时间
	 * @param state
	 *            状态 0：全部，1：收入，2：支出
	 * @return
	 */
	public Pager<IntegralRecord> getRecordList(String userPhone, String state, String create_date, String pageNum,
			int pageSize) {
		List<IntegralRecord> integralRecords = null;
		List<IntegralRecord> integralRecordsAll = null;
		Pager<IntegralRecord> page = new Pager<IntegralRecord>(0, 0, 0, 0, null);
		Connection connection = null;
		List<Object> params = new ArrayList<>();

		try {
			connection = DBUtils.getConnection();
			String allList = "SELECT lc_user.user_name,integral,create_time,comment,lc_user_integral_record.record_state FROM lc_user_integral_record ,lc_user WHERE lc_user_integral_record.user_id=lc_user.user_id";
			StringBuilder sql = new StringBuilder(
					"SELECT lc_user.user_name,integral,create_time,comment,lc_user_integral_record.record_state FROM lc_user_integral_record ,lc_user WHERE lc_user_integral_record.user_id=lc_user.user_id");
			if (!TextUtils.isEmpty(state) && Integer.parseInt(state) != 0) {
				sql.append(" and record_state=?");
				params.add(state);
			}
			if (!TextUtils.isEmpty(userPhone)) {
				UserDao userDao = new UserDao();
				int userId = userDao.getUserId(userPhone);
				if (userId != 0) {
					sql.append(" and lc_user_integral_record.user_id=?");
					params.add(userId);
				}

			}

			if (!TextUtils.isEmpty(create_date)) {
				// create_date = create_date.replaceAll("-", "");
				sql.append(" and create_time like ? ");
				params.add("%" + create_date + "%");
			}
			if (TextUtils.isEmpty(pageNum)) {
				pageNum = "1";
			}
			long fromIndex = pageSize * (Integer.parseInt(pageNum) - 1);
			sql.append(" order by " + "\'create_date\'" + " desc limit " + fromIndex + ", " + pageSize);
			String sqlStr = sql.toString();
			DBUtils.beginTx(connection);
			integralRecords = qR.query(connection, sql.toString(),
					new BeanListHandler<IntegralRecord>(IntegralRecord.class), params.toArray());
			integralRecordsAll = qR.query(connection, allList,
					new BeanListHandler<IntegralRecord>(IntegralRecord.class));
			DBUtils.commit(connection);
			page.setData_list(integralRecords);
			page.setTotal_record(integralRecordsAll == null ? 0 : integralRecordsAll.size());
			page.setTotal_page(integralRecordsAll == null ? 1 : integralRecordsAll.size() / 10 + 1);
			page.setCurrent_page(Integer.parseInt(pageNum));
			page.setPage_size(pageSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			DBUtils.rollback(connection);
			e.printStackTrace();
		} finally {
			DBUtils.releaseDB(null, null, connection);
		}
		return page;
	}
}
