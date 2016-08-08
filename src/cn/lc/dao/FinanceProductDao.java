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

import cn.lc.beans.FinanceProduct;
import cn.lc.beans.Pager;

public class FinanceProductDao {
    QueryRunner qR = new QueryRunner();

    // @APP--获取理财产品列表
    public Pager<FinanceProduct> getFinanceProductPageList(int pageNum, int pageSize) {

        Connection connection = null;
        Pager<FinanceProduct> result = null;
        StringBuilder sql = new StringBuilder(
                "SELECT product_id,interest_rate,invest_days,invest_months,invest_money,product_name,finance_img,product_desc,is_expire,state FROM lc_finance_product WHERE 1=1 and state=? ");
        StringBuilder countSql = new StringBuilder("SELECT  count(product_id) as totalRecord FROM lc_finance_product WHERE 1=1 and state=? ");
        long totalRecord = 0;
        // 起始索引
        long fromIndex = pageSize * (pageNum - 1);
        // 使用limit关键字，实现分页
        sql.append(" order by stamp_created desc   limit " + fromIndex + ", " + pageSize);
        List<FinanceProduct> msgList = new ArrayList<FinanceProduct>();
        try {
            connection = DBUtils.getConnection();
            connection = DBUtils.getConnection();
            DBUtils.beginTx(connection);
            // 获取总记录数
            totalRecord = (Long) (Number) qR.query(connection, countSql.toString(), scalarHandler, "A");
            // 获取查询的消息记录
            msgList = qR.query(connection, sql.toString(), new BeanListHandler<FinanceProduct>(FinanceProduct.class), "A");
            // 获取总页数
            long totalPage = totalRecord / pageSize;
            if (totalRecord % pageSize != 0) {
                totalPage++;
            }
            // 组装pager对象
            result = new Pager<FinanceProduct>(pageSize, pageNum, totalRecord, totalPage, msgList);
        } catch (Exception e) {
            DBUtils.rollback(connection);
            e.printStackTrace();
        } finally {
            DBUtils.releaseDB(null, null, connection);
        }
        return result;
    }

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

    // @PT--分页条件查询
    public Pager<FinanceProduct> findFinanceProductPager(FinanceProduct f, int pageNum, int pageSize) {
        Pager<FinanceProduct> result = null;
        // 存放查询参数
        Connection connection = null;
        StringBuilder sql = new StringBuilder(
                "SELECT product_id,interest_rate,invest_days,invest_months,invest_money,product_name,product_desc,is_expire,stamp_created,stamp_updated,state FROM lc_finance_product WHERE 1=1 and state=? ");
        StringBuilder countSql = new StringBuilder("SELECT  count(product_id) as totalRecord FROM lc_finance_product WHERE 1=1 and state=? ");
        // 存放查询参数
        List<Object> paramList = new ArrayList<Object>();
        paramList.add("A");
        String name = f.getProduct_name();
        String days = null;
        if (null != f.getInvest_days()) {
            days = f.getInvest_days() + "";
        }
        if (null != name && !"".equals(name.trim())) {
            sql.append(" and product_name like ? ");
            countSql.append("and product_name like ?");
            paramList.add("%" + name + "%");
        }
        if (null != days && !"".equals(days.trim())) {
            sql.append(" and invest_days = ? ");
            countSql.append(" and invest_days = ? ");
            paramList.add(days);
        }
        String[] str = paramList.toArray(new String[paramList.size()]);

        long totalRecord = 0;
        // 起始索引
        long fromIndex = pageSize * (pageNum - 1);
        // 使用limit关键字，实现分页
        sql.append(" order by stamp_created desc   limit " + fromIndex + ", " + pageSize);
        List<FinanceProduct> msgList = new ArrayList<FinanceProduct>();
        try {
            connection = DBUtils.getConnection();
            DBUtils.beginTx(connection);
            // 获取总记录数
            totalRecord = (Long) (Number) qR.query(connection, countSql.toString(), scalarHandler, str);
            // 获取查询的消息记录
            msgList = qR.query(connection, sql.toString(), new BeanListHandler<FinanceProduct>(FinanceProduct.class), str);
            // 获取总页数
            long totalPage = totalRecord / pageSize;
            if (totalRecord % pageSize != 0) {
                totalPage++;
            }
            // 组装pager对象
            result = new Pager<FinanceProduct>(pageSize, pageNum, totalRecord, totalPage, msgList);
        } catch (Exception e) {
            DBUtils.rollback(connection);
            e.printStackTrace();
        } finally {
            DBUtils.releaseDB(null, null, connection);
        }
        return result;
    }

    // @PT--添加理财产品
    public boolean addFinanceProduct(FinanceProduct f) {

        boolean result = false;
        Connection connection = null;
        try {
            connection = DBUtils.getConnection();
            String sql = "INSERT INTO lc_finance_product(interest_rate,invest_days,invest_months,invest_money,product_name,finance_img,product_desc,is_expire,created_by,stamp_created,updated_by,stamp_updated,state) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            DBUtils.beginTx(connection);
            int isSuccess = qR.update(connection, sql, f.getInterest_rate(), f.getInvest_days(), f.getInvest_months(), f.getInvest_money(),
                    f.getProduct_name(),f.getFinance_img(), f.getProduct_desc(),f.getIs_expire(), f.getCreated_by(), f.getStamp_created(), f.getUpdated_by(), f.getStamp_updated(),
                    f.getState());
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
}
