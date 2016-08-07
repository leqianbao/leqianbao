package cn.lc.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.http.util.TextUtils;

import cn.lc.beans.Pager;
import cn.lc.beans.User;
import cn.lc.beans.UserOrder;

public class UserOrderDao {
	QueryRunner qR = new QueryRunner();
	@SuppressWarnings("rawtypes")
	private ScalarHandler scalarHandler = new ScalarHandler() {
		@Override
		public Object handle(ResultSet rs) throws SQLException {
			Object obj = super.handle(rs);
			if (obj instanceof BigInteger)
				return ((BigInteger) obj).longValue();
			return obj;
		}
	};

	// @PT--分页查询
	public Pager<UserOrder> getUserOrderPager(UserOrder userOrder, int pageNum, int pageSize) {
		Pager<UserOrder> result = null;
		// 存放查询参数
		Connection connection = null;
		StringBuilder sql = new StringBuilder("SELECT * FROM lc_user_order_details where 1=1");
		StringBuilder countSql = new StringBuilder(
				"SELECT  count(id) as totalRecord FROM lc_user_order_details WHERE 1=1");
		// 存放查询参数
		List<Object> paramList = new ArrayList<Object>();
		int userId = userOrder.getUser_id();
		String create_date = userOrder.getCreate_date();
		String orderNo = userOrder.getOrder_no();
		if (userId != 0) {
			sql.append(" and user_id = ?");
			countSql.append(" and user_id = ?");
			paramList.add(userId);
		}
		if (!TextUtils.isEmpty(create_date)) {
			sql.append(" and create_date = ? ");
			countSql.append(" and create_date = ? ");
			paramList.add(create_date);
		}
		if (!TextUtils.isEmpty(orderNo)) {
			sql.append(" and order_no = ? ");
			countSql.append(" and order_no = ? ");
			paramList.add(orderNo);
		}
		Object[] str = paramList.toArray(new Object[paramList.size()]);

		long totalRecord = 0;
		// 起始索引
		long fromIndex = pageSize * (pageNum - 1);
		// 使用limit关键字，实现分页
		sql.append(" order by create_date desc   limit " + fromIndex + ", " + pageSize);
		List<UserOrder> msgList = new ArrayList<UserOrder>();
		try {
			connection = DBUtils.getConnection();
			DBUtils.beginTx(connection);
			// 获取总记录数
			totalRecord = (Long) (Number) qR.query(connection, countSql.toString(), scalarHandler, str);
			// 获取查询的消息记录
			msgList = qR.query(connection, sql.toString(), new BeanListHandler<UserOrder>(UserOrder.class), str);
			// 获取总页数
			long totalPage = totalRecord / pageSize;
			if (totalRecord % pageSize != 0) {
				totalPage++;
			}
			// 组装pager对象
			result = new Pager<UserOrder>(pageSize, pageNum, totalRecord, totalPage, msgList);
		} catch (Exception e) {
			DBUtils.rollback(connection);
			e.printStackTrace();
		} finally {
			DBUtils.releaseDB(null, null, connection);
		}
		return result;
	}
}
