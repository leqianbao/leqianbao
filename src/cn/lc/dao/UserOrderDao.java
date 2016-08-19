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

import cn.lc.beans.Commodity;
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
			create_date = create_date.replaceAll("-", "");
			sql.append(" and create_date like ? ");
			countSql.append(" and create_date like ? ");
			paramList.add("%" + create_date + "%");
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
	// @APP--分页查询
	public Pager<UserOrder> getUserOrderPagerByUserId(int userId, int pageNum, int pageSize) {
		Pager<UserOrder> result = null;
		// 存放查询参数
		Connection connection = null;
		StringBuilder sql = new StringBuilder("SELECT * FROM lc_user_order_details where user_id = ?");
		StringBuilder countSql = new StringBuilder(
				"SELECT  count(id) as totalRecord FROM lc_user_order_details WHERE user_id = ?");
		// 存放查询参数
		List<Object> paramList = new ArrayList<Object>();

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
			totalRecord = (Long) (Number) qR.query(connection, countSql.toString(), scalarHandler, userId);
			// 获取查询的消息记录
			msgList = qR.query(connection, sql.toString(), new BeanListHandler<UserOrder>(UserOrder.class), userId);
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
	// @PT--获取订单详细信息
	public UserOrder getUserOrderById(long id){
		// 存放查询参数
		Connection connection = null;
		StringBuilder sql = new StringBuilder("SELECT * FROM lc_user_order_details where user_order_id=?");
		UserOrder userOrder = null;
		try {
			connection = DBUtils.getConnection();
			DBUtils.beginTx(connection);
			userOrder = qR.query(connection, sql.toString(), new BeanListHandler<UserOrder>(UserOrder.class), id).get(0);
		} catch (Exception e) {
			DBUtils.rollback(connection);
			e.printStackTrace();
		} finally {
			DBUtils.releaseDB(null, null, connection);
		}
		return userOrder;
	}
	
	// @PT--获取更新订单信息
	public boolean UpdateUserOrderById(long id,String logistics_number,String order_state){
		// 存放查询参数
		Connection connection = null;
		StringBuilder sql = new StringBuilder("UPDATE  lc_user_order_details SET logistics_number=?,order_state=? WHERE user_order_id=?");
		boolean result = false;
		try {
			connection = DBUtils.getConnection();
			DBUtils.beginTx(connection);
			
			int isSuccess = qR.update(connection, sql.toString(), logistics_number,order_state,id);
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
		} finally {
			DBUtils.releaseDB(null, null, connection);
		}
		return result;
	}
	//@APP--插入积分数据
	public boolean insertUserOrder(UserOrder userOrder,int commodityNum){
		boolean  result = false;
		Connection connection = null;
		try {
			connection = DBUtils.getConnection();
			String sql = "INSERT INTO lc_user_order_details (user_id,order_no,commodity_id,commodity_num,create_date,end_date,order_state,logistics_number,receipt_address_id) VALUES (?,?,?,?,?,?,?,?,?)";
			String commodity="UPDATE lc_commodity_details set commodity_num = ? WHERE commodity_id = ?";
			DBUtils.beginTx(connection);
			int isSuccess = qR.update(connection,sql,userOrder.getUser_id(),
					userOrder.getOrder_no(),userOrder.getCommodity_id(),
					userOrder.getCommodity_num(),userOrder.getCreate_date(),
					userOrder.getEnd_date(),userOrder.getOrder_state(),
					userOrder.getLogistics_number(),userOrder.getReceipt_address_id());
			qR.update(connection, commodity, commodityNum-userOrder.getCommodity_num(),userOrder.getCommodity_id());
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
	//@APP--修改订单状态
	public boolean modifyOrderState (UserOrder userOrder){
		boolean  result = false;
		Connection connection = null;
		try {
			String sql ="";
			connection = DBUtils.getConnection();
			sql = "UPDATE  lc_user_order_details SET end_date=?,order_state=? WHERE user_order_id=?";
			DBUtils.beginTx(connection);
			int isSuccess = qR.update(connection,sql,userOrder.getEnd_date(),userOrder.getOrder_state(),userOrder.getId().intValue());
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
	
	
	
	/**
	 * 取消订单
	 * @param orderId
	 * @return
	 */
	public boolean cancelOrder(int orderId,Commodity commodity,UserOrder userOrder){
		boolean result=false;
		Connection connection = null;
		try {
			String sql ="";
			connection = DBUtils.getConnection();
			sql = "DELETE FROM  lc_user_order_details WHERE user_order_id=?";
			String commoditySql="UPDATE lc_commodity_details set commodity_num = ? WHERE commodity_id = ?";
			DBUtils.beginTx(connection);
			int isSuccess = qR.update(connection,sql,orderId);
			qR.update(connection,commoditySql,commodity.getCommodity_num()+userOrder.getCommodity_num(),commodity.getCommodity_id());
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
