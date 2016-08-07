package cn.lc.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.lc.beans.Pager;
import cn.lc.beans.User;
import cn.lc.beans.UserOrder;

public class UserOrderDao {
	QueryRunner qR = new QueryRunner();
//	//@PT--分页查询
//    public Pager<UserOrder> findUserPager(UserOrder u,int pageNum,int pageSize){
//        Pager<User> result = null;
//        // 存放查询参数
//        Connection connection = null;
//        StringBuilder sql = new StringBuilder(
//                "SELECT * FROM lc_user_order_details WHERE 1=1 and state=? ");
//        StringBuilder countSql = new StringBuilder("SELECT  count(id) as totalRecord FROM lc_user_order_details WHERE 1=1 and state=? ");
//     // 存放查询参数
//        List<Object> paramList = new ArrayList<Object>();
//        paramList.add("A");
//        String name =u.getUser_name();
//        String phone = u.getUser_phone();
//        String is_verify = u.getIs_verify();
//        if(null!=name&&!"".equals(name.trim())){
//            sql.append(" and user_name like ? ");
//            countSql.append("and user_name like ?");
//            paramList.add("%"+name+"%");
//        }
//        if(null!=phone&&!"".equals(phone.trim())){
//            sql.append(" and user_phone = ? ");
//            countSql.append(" and user_phone = ? ");
//            paramList.add(phone );
//        }
//        if(null!=is_verify&&!"".equals(is_verify.trim())){
//            sql.append(" and is_verify = ? ");
//            countSql.append(" and is_verify = ? ");
//            paramList.add(is_verify );
//        }
//        String[] str = paramList.toArray(new String[paramList.size()]); 
//        
//        long totalRecord  = 0;
//        // 起始索引
//        long fromIndex  = pageSize * (pageNum -1);      
//        // 使用limit关键字，实现分页
//        sql.append(" order by stamp_created desc   limit " + fromIndex + ", " + pageSize );
//        List<User> msgList = new ArrayList<User>();
//        try {
//            connection = DBUtils.getConnection();
//            DBUtils.beginTx(connection);
//            // 获取总记录数
//            totalRecord =(Long) (Number) qR.query(connection,countSql.toString(),scalarHandler,str);
//            // 获取查询的消息记录
//            msgList  = qR.query(connection,sql.toString(),new BeanListHandler<User>(User.class),str);
//            //获取总页数
//            long totalPage = totalRecord / pageSize;
//            if(totalRecord % pageSize !=0){
//                totalPage++;
//            }
//            // 组装pager对象
//            result = new Pager<User>(pageSize, pageNum, totalRecord, totalPage, msgList);
//        } catch (Exception e) {
//            DBUtils.rollback(connection);
//            e.printStackTrace();
//        } finally{
//            DBUtils.releaseDB(null, null, connection);
//        }
//        return result;
//    }
}
