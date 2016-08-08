package cn.lc.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

public class InterestDao {
	QueryRunner qR = new QueryRunner();
	
	private ScalarHandler scalarHandler = new ScalarHandler() {  
        @Override  
        public Object handle(ResultSet rs) throws SQLException {  
            Object obj = super.handle(rs);  
            if (obj instanceof BigInteger)  
                return ((BigInteger) obj).longValue();  
            return obj;  
        }  
    };  
    
	//@APP--利息统计
	public String countMoney(String uid){
		String result = "";
		Connection connection = null;
		String sql = "SELECT  sum(interest_money) as result FROM lc_interest WHERE 1=1 AND is_count=1 AND user_id=? and state=?";
		try {
			connection = DBUtils.getConnection();
			DBUtils.beginTx(connection);
			if(qR.query(connection,sql,scalarHandler,uid,"A")!=null){
				result = qR.query(connection,sql,scalarHandler,uid,"A").toString();	
			}
			
			//result =((Number) qR.query(connection,sql,scalarHandler,uid,"A")).toString();
			DBUtils.commit(connection);
	} catch (Exception e) {
		DBUtils.rollback(connection);
		e.printStackTrace();
	} finally{
		DBUtils.releaseDB(null, null, connection);
	}
		return result;
	}
	
	//@PT--修改
	
}
