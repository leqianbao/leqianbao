package cn.lc.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.http.util.TextUtils;

import cn.lc.beans.FetchCash;
import cn.lc.beans.FetchCashMain;
import cn.lc.beans.Pager;
import cn.lc.beans.UserOrder;

public class FetchCashMainDao {
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
    //@PT--分页条件查询
    public Pager<FetchCashMain> findOutPager(FetchCashMain m,int pageNum,int pageSize){
        Pager<FetchCashMain> result = null;
        // 存放查询参数
        Connection connection = null;
        StringBuilder sql = new StringBuilder(
                "SELECT * FROM lc_fetch_cash_main WHERE 1=1");
        StringBuilder countSql = new StringBuilder("SELECT  count(main_id) as totalRecord FROM lc_fetch_cash_main WHERE 1=1");
     // 存放查询参数
        List<Object> paramList = new ArrayList<Object>();
        String main_no = m.getMain_no();
        int user_id = m.getUser_id();
        String create_date = m.getCreate_date();
        
        if(!TextUtils.isEmpty(main_no)){
            sql.append(" and main_no = ? ");
            countSql.append(" and main_no = ? ");
            paramList.add(main_no );
        }
        if(user_id != 0){
            sql.append(" and user_id = ? ");
            countSql.append(" and user_id = ? ");
            paramList.add(user_id );
        }
        
        if(create_date != null){
            sql.append(" and create_date like ? ");
            countSql.append(" and create_date like ? ");
            paramList.add("%" + create_date + "%" );
        }
        
        Object[] str = paramList.toArray(new Object[paramList.size()]); 
        
        long totalRecord  = 0;
        // 起始索引
        long fromIndex  = pageSize * (pageNum -1);      
        // 使用limit关键字，实现分页
        sql.append(" order by create_date desc   limit " + fromIndex + ", " + pageSize );
        List<FetchCashMain> msgList = new ArrayList<FetchCashMain>();
        try {
            connection = DBUtils.getConnection();
            DBUtils.beginTx(connection);
            // 获取总记录数
            totalRecord =(Long) (Number) qR.query(connection,countSql.toString(),scalarHandler,str);
            // 获取查询的消息记录
            msgList  = qR.query(connection,sql.toString(),new BeanListHandler<FetchCashMain>(FetchCashMain.class),str);
            //获取总页数
            long totalPage = totalRecord / pageSize;
            if(totalRecord % pageSize !=0){
                totalPage++;
            }
            // 组装pager对象
            result = new Pager<FetchCashMain>(pageSize, pageNum, totalRecord, totalPage, msgList);
        } catch (Exception e) {
            DBUtils.rollback(connection);
            e.printStackTrace();
        } finally{
            DBUtils.releaseDB(null, null, connection);
        }
        return result;
    }
    
    
  //@APP--修改提现状态
  	public boolean modifyFetchMainState (int userId,String mainNo,String state){
  		boolean  result = false;
  		Connection connection = null;
  		try {
  			String sql ="";
  			connection = DBUtils.getConnection();
  			sql = "UPDATE  lc_fetch_cash_main SET main_state=? WHERE user_id=? and main_no=?";
  			DBUtils.beginTx(connection);
  			int isSuccess = qR.update(connection,sql,"A",userId,mainNo);
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
  	
  	public FetchCashMain getFetchMain(int userId,String main_no){
  		Connection connection = null;
  		FetchCashMain fetchCashMain=null;
  		String sql="SELECT * FROM lc_fetch_cash_main WHERE user_id=? and main_no=?";
  		try {
            connection = DBUtils.getConnection();
            DBUtils.beginTx(connection);
            // 获取总记录数
            // 获取查询的消息记录
            fetchCashMain  = qR.query(connection,sql,new BeanListHandler<FetchCashMain>(FetchCashMain.class),userId,main_no).get(0);
            //获取总页数
        } catch (Exception e) {
            DBUtils.rollback(connection);
            e.printStackTrace();
        } finally{
            DBUtils.releaseDB(null, null, connection);
        }
  		return fetchCashMain;
  	}
  	
}
