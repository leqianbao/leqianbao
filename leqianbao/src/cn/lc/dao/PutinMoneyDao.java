package cn.lc.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;


import cn.lc.beans.Pager;
import cn.lc.beans.PutinMoney;

public class PutinMoneyDao {
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
	
	//@APP--充值记录列表
	public Pager<PutinMoney> putinList(int pageNum,int pageSize,String uid){
		Pager<PutinMoney> result = null;
		// 存放查询参数
		Connection connection = null;
		StringBuilder sql = new StringBuilder(
				"SELECT putin_id,putin_num,putin_money,putin_desc,user_id,created_by,stamp_created,updated_by,stamp_updated,state FROM lc_putin_money WHERE 1=1 AND is_show=1 AND user_id=? ");
		StringBuilder countSql = new StringBuilder("SELECT  count(putin_id) as totalRecord FROM lc_putin_money WHERE 1=1 AND is_show=1 AND user_id=?");
		sql.append("  and state = ?");
		countSql.append(" and state = ?");
		long totalRecord  = 0;
		// 起始索引
		long fromIndex	= pageSize * (pageNum -1);			
		// 使用limit关键字，实现分页
		sql.append(" order by stamp_created desc  limit " + fromIndex + ", " + pageSize );
		List<PutinMoney> msgList = new ArrayList<PutinMoney>();
		try {
			connection = DBUtils.getConnection();
			DBUtils.beginTx(connection);
			// 获取总记录数
			totalRecord =(Long) (Number) qR.query(connection,countSql.toString(),scalarHandler,uid,"A");
			// 获取查询的消息记录
			msgList  = qR.query(connection,sql.toString(),new BeanListHandler<PutinMoney>(PutinMoney.class),uid,"A");
			//获取总页数
			long totalPage = totalRecord / pageSize;
			if(totalRecord % pageSize !=0){
				totalPage++;
			}
			// 组装pager对象
			result = new Pager<PutinMoney>(pageSize, pageNum, totalRecord, totalPage, msgList);
		} catch (Exception e) {
			DBUtils.rollback(connection);
			e.printStackTrace();
		} finally{
			DBUtils.releaseDB(null, null, connection);
		}
		return result;
	}

	//@APP--统计充值金额
	public String countMoney(String uid){
		String result = "";
		Connection connection = null;
		String sql = "SELECT  sum(putin_money) as result FROM lc_putin_money WHERE 1=1 AND is_count=1 AND user_id=? AND state=?";
		try {
			connection = DBUtils.getConnection();
			DBUtils.beginTx(connection);
			if(qR.query(connection,sql,scalarHandler,uid,"A")!=null){
				result = qR.query(connection,sql,scalarHandler,uid,"A").toString();	
			}
			DBUtils.commit(connection);
	} catch (Exception e) {
		DBUtils.rollback(connection);
		e.printStackTrace();
	} finally{
		DBUtils.releaseDB(null, null, connection);
	}
		return result;
	}
	
	//@PT--添加充值记录
	public boolean addPutinMoney(PutinMoney p){
        boolean  result = false;
         Connection connection = null;
         try {
             connection = DBUtils.getConnection();
             String sql = "INSERT INTO lc_putin_money(putin_num,putin_money,putin_desc,user_id,created_by,stamp_created,updated_by,stamp_updated,state) VALUES (?,?,?,?,?,?,?,?,?)";
             String sql2 = "INSERT INTO lc_message (msg_title,msg_type,user_id,msg_content,created_by,stamp_created,updated_by,stamp_updated,state) VALUES (?,?,?,?,?,?,?,?,?)"; 
             SimpleDateFormat gs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
             StringBuffer cont = new StringBuffer("尊敬的用户你好,系统在");
             cont.append(gs.format(p.getStamp_created()));
             cont.append("给你充值了"+p.getPutin_money()+"元,订单号为："+p.getPutin_num());
             //尊敬的用户你好,系统在2016-06-30 10:18:19给你充值了1000.0元，订单号为：8491201606301018198683701
             DBUtils.beginTx(connection);
             int isSuccess = qR.update(connection,sql,p.getPutin_num(),p.getPutin_money(),p.getPutin_desc(),p.getUser_id(),p.getCreated_by(),p.getStamp_created(),p.getUpdated_by(),p.getStamp_updated(),p.getState());
             int isSuccess2 = qR.update(connection,sql2,"充值消息",1,p.getUser_id(),cont.toString(),p.getCreated_by(),p.getStamp_created(),p.getUpdated_by(),p.getStamp_updated(),p.getState());
             if(isSuccess==1&&isSuccess2==1){
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
    
    //@PT--条件查询充值列表
    public Pager<PutinMoney> getPutinPager(PutinMoney m,int pageNum,int pageSize){
        Pager<PutinMoney> result = null;
        // 存放查询参数
        Connection connection = null;
        StringBuilder sql = new StringBuilder(
                "SELECT putin_id,putin_num,putin_money,putin_desc,user_id,created_by,stamp_created,updated_by,stamp_updated,state FROM lc_putin_money WHERE 1=1 AND is_show=1  AND state=? ");
        StringBuilder countSql = new StringBuilder("SELECT  count(putin_id) as totalRecord FROM lc_putin_money WHERE 1=1 AND is_show=1  AND state=? ");
        // 存放查询参数
        List<Object> paramList = new ArrayList<Object>();
        paramList.add("A");
        String orderNum = m.getPutin_num();
        String uid = m.getUser_id();

        String money = null;
        if(null!=m.getPutin_money()){
            money = m.getPutin_money()+"";
        }
        
        if(null!=orderNum&&!"".equals(orderNum.trim())){
            sql.append(" and putin_num = ? ");
            countSql.append(" and putin_num = ? ");
            paramList.add(orderNum.trim() );
        }
        
        if(null!=money&&!"".equals(money.trim())){
            sql.append(" and putin_money = ? ");
            countSql.append(" and putin_money = ? ");
            paramList.add(money.trim() );
        }
        if(null!=uid&&!"".equals(uid.trim())){
            sql.append(" and user_id = ? ");
            countSql.append(" and user_id = ? ");
            paramList.add(uid.trim() );
        }
        String[] str = paramList.toArray(new String[paramList.size()]); 
        
        long totalRecord  = 0;
        // 起始索引
        long fromIndex  = pageSize * (pageNum -1);          
        // 使用limit关键字，实现分页
        sql.append(" order by stamp_created desc  limit " + fromIndex + ", " + pageSize );
        List<PutinMoney> msgList = new ArrayList<PutinMoney>();
        try {
            connection = DBUtils.getConnection();
            DBUtils.beginTx(connection);
            // 获取总记录数
            totalRecord =(Long) (Number) qR.query(connection,countSql.toString(),scalarHandler,str);
            // 获取查询的消息记录
            msgList  = qR.query(connection,sql.toString(),new BeanListHandler<PutinMoney>(PutinMoney.class),str);
            //获取总页数
            long totalPage = totalRecord / pageSize;
            if(totalRecord % pageSize !=0){
                totalPage++;
            }
            // 组装pager对象
            result = new Pager<PutinMoney>(pageSize, pageNum, totalRecord, totalPage, msgList);
        } catch (Exception e) {
            DBUtils.rollback(connection);
            e.printStackTrace();
        } finally{
            DBUtils.releaseDB(null, null, connection);
        }
        return result;
    }
	
}
