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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import cn.lc.beans.FetchCash;
import cn.lc.beans.Pager;
import cn.lc.beans.User;

public class FetchCashDao {
    
    private static Logger logger = (Logger) LogManager.getLogger(FetchCashDao.class);
    QueryRunner qR = new QueryRunner();
    
    //@APP--添加提现订单
    public boolean  addFetchCash(FetchCash f){

        boolean  result = false;
        Connection connection = null;
        try {
            connection = DBUtils.getConnection();
            String sql = "INSERT INTO lc_fetch_cash (fetch_num,fetch_money,card_number,bank_name,bank_id,user_id,handle_tag,created_by,stamp_created,updated_by,stamp_updated,state) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            String sql2 = "INSERT INTO lc_message (msg_title,msg_type,user_id,msg_content,created_by,stamp_created,updated_by,stamp_updated,state) VALUES (?,?,?,?,?,?,?,?,?)"; 
            SimpleDateFormat gs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            StringBuffer cont = new StringBuffer("尊敬的"+f.getUser().getUser_name()+"用户,你在");
            cont.append(gs.format(f.getStamp_created()));
            cont.append("提现"+f.getFetch_money()+"元的到卡号为："+f.getCard_number()+"的"+f.getBank_name()+"，预计一个小时后到账，");
            cont.append("提现流水号为："+f.getFetch_num());
            DBUtils.beginTx(connection);
            int isSuccess = qR.update(connection,sql,f.getFetch_num(),f.getFetch_money(),f.getCard_number(),f.getBank_name(),f.getBank_id(),f.getUser_id(),f.getHandle_tag(),f.getCreated_by(),f.getStamp_created(),f.getUpdated_by(),f.getStamp_updated(),f.getState());
            int isSuccess2 = qR.update(connection,sql2,"提现消息",1,f.getUser().getUser_id(),cont.toString(),f.getCreated_by(),f.getStamp_created(),f.getUpdated_by(),f.getStamp_updated(),f.getState());
            if(isSuccess==1&&isSuccess2==1){
            DBUtils.commit(connection);
            result = true;
            } else{
                DBUtils.rollback(connection);
                result = false;
            }
        } catch (Exception e) {
            DBUtils.rollback(connection);
            logger.error("Cash to bank card is failed!",e.getMessage());
            e.printStackTrace();
        } finally{
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
    
    //@APP--获取提现订单列表
    public Pager<FetchCash> fetchCashPager(int pageNum,int pageSize,String uid){
        Pager<FetchCash> result = null;
        // 存放查询参数
        Connection connection = null;
        StringBuilder sql = new StringBuilder(
                "SELECT fetch_id,fetch_num,fetch_money,card_number,bank_name,bank_id,user_id,created_by,stamp_created,updated_by,stamp_updated,state FROM lc_fetch_cash WHERE 1=1 AND user_id=?");
        StringBuilder countSql = new StringBuilder("SELECT  count(fetch_id) as totalRecord FROM lc_fetch_cash WHERE 1=1 AND user_id=?");
        sql.append("  and state = ?");
        countSql.append(" and state = ?");
        long totalRecord  = 0;
        // 起始索引
        long fromIndex  = pageSize * (pageNum -1);          
        // 使用limit关键字，实现分页
        sql.append(" order by stamp_created desc   limit " + fromIndex + ", " + pageSize );
        List<FetchCash> msgList = new ArrayList<FetchCash>();
        try {
            connection = DBUtils.getConnection();
            DBUtils.beginTx(connection);
            // 获取总记录数
            totalRecord =(Long) (Number) qR.query(connection,countSql.toString(),scalarHandler,uid,"A");
            // 获取查询的消息记录
            msgList  = qR.query(connection,sql.toString(),new BeanListHandler<FetchCash>(FetchCash.class),uid,"A");
            DBUtils.commit(connection);
            //获取总页数
            long totalPage = totalRecord / pageSize;
            if(totalRecord % pageSize !=0){
                totalPage++;
            }
            // 组装pager对象
            result = new Pager<FetchCash>(pageSize, pageNum, totalRecord, totalPage, msgList);
        } catch (Exception e) {
            DBUtils.rollback(connection);
            logger.error("Query  fetch_money record is failed!",e.getMessage());
            e.printStackTrace();
        } finally{
            DBUtils.releaseDB(null, null, connection);
        }
        return result;
    }
    
    //@APP--统计提现
    @SuppressWarnings("unchecked")
    public String countMoney(String uid){
        String result = "";
        Connection connection = null;
        String sql = "SELECT  sum(fetch_money) as result FROM lc_fetch_cash WHERE 1=1 AND user_id=? and state=?";
        try {
            connection = DBUtils.getConnection();
            DBUtils.beginTx(connection);
            if(qR.query(connection,sql,scalarHandler,uid,"A")!=null){
                result = qR.query(connection,sql,scalarHandler,uid,"A").toString(); 
            }
            DBUtils.commit(connection);
    } catch (Exception e) {
        DBUtils.rollback(connection);
        logger.error("Statistics fetch_money is failed!",e.getMessage());
        e.printStackTrace();
    } finally{
        DBUtils.releaseDB(null, null, connection);
    }
        return result;
    }
    

    //@PT--分页条件查询
     public Pager<FetchCash> findOutPager(FetchCash m,int pageNum,int pageSize){
         Pager<FetchCash> result = null;
         // 存放查询参数
         Connection connection = null;
         StringBuilder sql = new StringBuilder(
                 "SELECT fetch_id,fetch_num,fetch_money,card_number,bank_name,bank_id,user_id,handle_tag,created_by,stamp_created,updated_by,stamp_updated,state FROM lc_fetch_cash WHERE 1=1  AND state=? ");
         StringBuilder countSql = new StringBuilder("SELECT  count(fetch_id) as totalRecord FROM lc_fetch_cash WHERE 1=1 AND state=? ");
      // 存放查询参数
         List<Object> paramList = new ArrayList<Object>();
         paramList.add("A");
         String fetch_num = m.getFetch_num();
         String user_id = m.getUser_id();
         String handle_tag = m.getHandle_tag();

         String fetch_money = null;
         if(null!=m.getFetch_money()){
             fetch_money = m.getFetch_money()+"";
         }
         
         if(null!=fetch_num&&!"".equals(fetch_num.trim())){
             sql.append(" and fetch_num = ? ");
             countSql.append(" and fetch_num = ? ");
             paramList.add(fetch_num.trim() );
         }
         if(null!=handle_tag&&!"".equals(handle_tag.trim())){
             sql.append(" and handle_tag = ? ");
             countSql.append(" and handle_tag = ? ");
             paramList.add(handle_tag.trim() );
         }
         
         if(null!=fetch_money&&!"".equals(fetch_money.trim())){
             sql.append(" and fetch_money = ? ");
             countSql.append(" and fetch_money = ? ");
             paramList.add(fetch_money.trim() );
         }
         if(null!=user_id&&!"".equals(user_id.trim())){
             sql.append(" and user_id = ? ");
             countSql.append(" and user_id = ? ");
             paramList.add(user_id.trim() );
         }
         
         String[] str = paramList.toArray(new String[paramList.size()]); 
         
         long totalRecord  = 0;
         // 起始索引
         long fromIndex  = pageSize * (pageNum -1);      
         // 使用limit关键字，实现分页
         sql.append(" order by stamp_created desc   limit " + fromIndex + ", " + pageSize );
         List<FetchCash> msgList = new ArrayList<FetchCash>();
         try {
             connection = DBUtils.getConnection();
             DBUtils.beginTx(connection);
             // 获取总记录数
             totalRecord =(Long) (Number) qR.query(connection,countSql.toString(),scalarHandler,str);
             // 获取查询的消息记录
             msgList  = qR.query(connection,sql.toString(),new BeanListHandler<FetchCash>(FetchCash.class),str);
             //获取总页数
             long totalPage = totalRecord / pageSize;
             if(totalRecord % pageSize !=0){
                 totalPage++;
             }
             // 组装pager对象
             result = new Pager<FetchCash>(pageSize, pageNum, totalRecord, totalPage, msgList);
         } catch (Exception e) {
             DBUtils.rollback(connection);
             e.printStackTrace();
         } finally{
             DBUtils.releaseDB(null, null, connection);
         }
         return result;
     }


     //@PT--处理提现订单--打款
     
     public boolean  handler(FetchCash fc) {
         boolean result = false;
         Connection connection = null;
         try {
             connection = DBUtils.getConnection();
             StringBuilder sql = new StringBuilder("UPDATE lc_fetch_cash set handle_tag=?,stamp_updated =?,updated_by=? WHERE fetch_id=?");
             DBUtils.beginTx(connection); 
             int isSuccess = qR.update(connection, sql.toString(),fc.getHandle_tag(),fc.getStamp_updated(),fc.getUpdated_by(),fc.getFetch_id());
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
     
     /**
      * 获取未处理的提现申请
      * @return
      */
     public List<FetchCash> getUntreatedList(){
    	 List<FetchCash> fetchCashs=null;
    	 Connection connection = null;
         try {
        	 connection = DBUtils.getConnection();
 			String sql = "SELECT * FROM lc_fetch_cash WHERE handle_tag = 0";
 			DBUtils.beginTx(connection);
 			fetchCashs = qR.query(connection,sql,new BeanListHandler<FetchCash>(FetchCash.class));
         } catch (Exception e) {
             DBUtils.rollback(connection);
             e.printStackTrace();
         } finally {
             DBUtils.releaseDB(null, null, connection);
         }
         return fetchCashs;
     }

}
