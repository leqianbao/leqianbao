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


import cn.lc.beans.Message;
import cn.lc.beans.Pager;

public class MessageDao {
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

    // @APP--分页条件查询消息--个人-系统
    public Pager<Message> findMsgPager(String msgType, String uid, int pageNum, int pageSize) {
        Pager<Message> result = null;
        // 存放查询参数
        Connection connection = null;
        StringBuilder sql = new StringBuilder(
                "SELECT msg_id,msg_title,msg_content,msg_content,created_by,stamp_created,updated_by,stamp_updated,state FROM lc_message WHERE 1=1");
        StringBuilder countSql = new StringBuilder("SELECT  count(msg_id) as totalRecord FROM lc_message WHERE 1=1");
        long totalRecord = 0;
        // 起始索引
        long fromIndex = pageSize * (pageNum - 1);
        // msgType 0 系统消息 1个人消息
        if (msgType.equals("0")) {
            sql.append("  and state = ? and msg_type=? and user_id is ?");
            countSql.append(" and state = ? and msg_type=? and user_id is ?");
            uid = null;
        } else {
            sql.append("  and state = ? and msg_type=? and user_id =?");
            countSql.append(" and state = ? and msg_type=? and user_id =?");
        }
        // 使用limit关键字，实现分页
        sql.append(" order by stamp_created desc   limit " + fromIndex + ", " + pageSize);
        List<Message> msgList = new ArrayList<Message>();
        try {
            connection = DBUtils.getConnection();
            DBUtils.beginTx(connection);
            // 获取总记录数
            totalRecord = (Long) (Number) qR.query(connection, countSql.toString(), scalarHandler, "A", msgType, uid);
            // 获取查询的消息记录
            msgList = qR.query(connection, sql.toString(), new BeanListHandler<Message>(Message.class), "A", msgType, uid);
            // 获取总页数
            long totalPage = totalRecord / pageSize;
            if (totalRecord % pageSize != 0) {
                totalPage++;
            }
            // 组装pager对象
            result = new Pager<Message>(pageSize, pageNum, totalRecord, totalPage, msgList);
        } catch (Exception e) {
            DBUtils.rollback(connection);
            e.printStackTrace();
        } finally {
            DBUtils.releaseDB(null, null, connection);
        }
        return result;
    }

    // @PT--添加消息
    public boolean addMessage(Message m) {
        boolean result = false;
        Connection connection = null;
        try {
            connection = DBUtils.getConnection();
            String sql = "INSERT INTO lc_message (msg_title,msg_content,created_by,stamp_created,updated_by,stamp_updated,state) VALUES (?,?,?,?,?,?,?)";
            DBUtils.beginTx(connection);
            int isSuccess = qR.update(connection, sql, m.getMsg_title(), m.getMsg_content(), m.getCreated_by(), m.getStamp_created(),
                    m.getUpdated_by(), m.getStamp_updated(), m.getState());
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
    
    
  //@PT--分页查询
    public Pager<Message> findMessgePager(Message m,int pageNum,int pageSize){
        Pager<Message> result = null;
        Connection connection = null;
        StringBuilder sql = new StringBuilder(
                "SELECT msg_id,msg_title,msg_type,msg_content,created_by,stamp_created,updated_by,stamp_updated,state FROM lc_message WHERE 1=1 and state=? ");
        StringBuilder countSql = new StringBuilder("SELECT  count(msg_id) as totalRecord FROM lc_message WHERE 1=1 and state=? ");
     // 存放查询参数
        List<Object> paramList = new ArrayList<Object>();
        paramList.add("A");
        
        String msg_type =m.getMsg_type();
        String msg_title = m.getMsg_title();
        String created_by = m.getCreated_by();
//      if("1".equals(msg_type)&&m.getMsg_id()!=null){
//            sql.append(" and user_id = ? ");
//            countSql.append("and user_id = ?");
//            paramList.add(m.getMsg_id());
//            }
        
        if(null!=msg_type&&!"".equals(msg_type.trim())){
            sql.append(" and msg_type = ? ");
            countSql.append("and msg_type = ?");
            paramList.add(msg_type.trim());
        }
        if(null!=created_by&&!"".equals(created_by.trim())){
            sql.append(" and created_by like ? ");
            countSql.append(" and created_by like ? ");
            paramList.add("%"+created_by.trim()+"%" );
        }
        if(null!=msg_title&&!"".equals(msg_title.trim())){
            sql.append(" and msg_title like ? ");
            countSql.append(" and msg_title like ? ");
            paramList.add("%"+msg_title.trim()+"%" );
        }
        String[] str = paramList.toArray(new String[paramList.size()]); 
        
        long totalRecord  = 0;
        // 起始索引
        long fromIndex  = pageSize * (pageNum -1);      
        // 使用limit关键字，实现分页
        sql.append(" order by stamp_created desc   limit " + fromIndex + ", " + pageSize );
        List<Message> msgList = new ArrayList<Message>();
        try {
            connection = DBUtils.getConnection();
            DBUtils.beginTx(connection);
            // 获取总记录数
            totalRecord =(Long) (Number) qR.query(connection,countSql.toString(),scalarHandler,str);
            // 获取查询的消息记录
            msgList  = qR.query(connection,sql.toString(),new BeanListHandler<Message>(Message.class),str);
            //获取总页数
            long totalPage = totalRecord / pageSize;
            if(totalRecord % pageSize !=0){
                totalPage++;
            }
            // 组装pager对象
            result = new Pager<Message>(pageSize, pageNum, totalRecord, totalPage, msgList);
        } catch (Exception e) {
            DBUtils.rollback(connection);
            e.printStackTrace();
        } finally{
            DBUtils.releaseDB(null, null, connection);
        }
        return result;
    }

}
