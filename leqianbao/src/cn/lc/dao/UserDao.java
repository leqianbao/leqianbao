package cn.lc.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;



import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.lc.beans.Pager;
import cn.lc.beans.User;


public class UserDao {
	QueryRunner qR = new QueryRunner();
	
	//@APP--用户注册
	public boolean regUser(User u){
		boolean  result = false;
		Connection connection = null;
		try {
			connection = DBUtils.getConnection();
			String sql = "INSERT INTO lc_user (user_phone,user_passwd,user_grade,is_verify,created_by,stamp_created,updated_by,stamp_updated,state) VALUES (?,?,?,?,?,?,?,?,?)";
			DBUtils.beginTx(connection);
			int isSuccess = qR.update(connection,sql,u.getUser_phone(),u.getUser_passwd(),u.getUser_grade(),u.getIs_verify(),u.getCreated_by(),u.getStamp_created(),u.getUpdated_by(),u.getStamp_updated(),u.getState());
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
	
	//@APP--验证用户是否存在 存在返回true 不存在返回
	public boolean isReged(String phone){
		Connection connection = null;
		User u = null;
		boolean  result = false;
		try {
			connection = DBUtils.getConnection();
			String sql = "SELECT user_phone,state FROM lc_user WHERE user_phone = ?";
			DBUtils.beginTx(connection);
			u = qR.query(connection,sql,new BeanHandler<User>(User.class),phone);
			if(u==null){
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
	
	
	
	//@APP--登录
	public User login (String phone,String custPwd){
		Connection connection = null;
		User u = null;
		try {
			connection = DBUtils.getConnection();
			String sql = "SELECT user_id,user_name,user_phone,user_passwd,pay_passwd,user_grade,is_verify,created_by,stamp_created,updated_by,stamp_updated,state FROM lc_user WHERE user_phone = ? and user_passwd = ?";
			DBUtils.beginTx(connection);
			u = qR.query(connection,sql,new BeanHandler<User>(User.class),phone,custPwd);
		} catch (Exception e) {
			DBUtils.rollback(connection);
			e.printStackTrace();
		} finally{
			DBUtils.releaseDB(null, null, connection);
		}
		return u;
	}
	//@APP--修改密码
	public boolean modifyPassword (String type, User u){
		boolean  result = false;
		Connection connection = null;
		try {
			String sql ="";
			connection = DBUtils.getConnection();
			if(type.equals("2")){
				sql = "UPDATE  lc_user SET pay_passwd=?,updated_by=?,stamp_updated=? WHERE   user_phone=?";
			} else{
				sql = "UPDATE  lc_user SET user_passwd=?,updated_by=?,stamp_updated=? WHERE  user_phone=?";
			}
			DBUtils.beginTx(connection);
			int isSuccess = qR.update(connection,sql,u.getPay_passwd(),u.getUpdated_by(),u.getStamp_updated(),u.getUser_phone());
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
	//@APP--验证 支付.登录 密码
	public User verifyPass (String phone){
		Connection connection = null;
		User u = null;
		try {
			connection = DBUtils.getConnection();
			String sql = "SELECT user_passwd,pay_passwd FROM lc_user WHERE user_phone = ?";
			DBUtils.beginTx(connection);
			u = qR.query(connection,sql,new BeanHandler<User>(User.class),phone);
		} catch (Exception e) {
			DBUtils.rollback(connection);
			e.printStackTrace();
		} finally{
			DBUtils.releaseDB(null, null, connection);
		}
		return u;
	}
	
	
	//@APP--实名认证
	public boolean realName (User u){
		boolean  result = false;
		Connection connection = null;
		try {
			connection = DBUtils.getConnection();
			String sql = "UPDATE  lc_user SET user_name=?,pay_passwd=?,idcard_num=?,is_verify=?,updated_by=?,stamp_updated=? WHERE user_id=? and user_phone=?";
			DBUtils.beginTx(connection);
			int isSuccess = qR.update(connection,sql,u.getUser_name(),u.getPay_passwd(),u.getIdcard_num(),u.getIs_verify(),u.getUpdated_by(),u.getStamp_updated(),u.getUser_id(),u.getUser_phone());
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
	
	//@APP--刷新用户信息
	public User getUserInof (String phone,String userid){
		Connection connection = null;
		User u = null;
		try {
			connection = DBUtils.getConnection();
			String sql = "SELECT user_id,user_name,user_phone,user_passwd,user_grade,is_verify,created_by,stamp_created,updated_by,stamp_updated,state FROM lc_user WHERE user_phone = ? and user_id = ?";
			DBUtils.beginTx(connection);
			u = qR.query(connection,sql,new BeanHandler<User>(User.class),phone,userid);
		} catch (Exception e) {
			DBUtils.rollback(connection);
			e.printStackTrace();
		} finally{
			DBUtils.releaseDB(null, null, connection);
		}
		return u;
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
	
	//@PT--分页查询
    public Pager<User> findUserPager(User u,int pageNum,int pageSize){
        Pager<User> result = null;
        // 存放查询参数
        Connection connection = null;
        StringBuilder sql = new StringBuilder(
                "SELECT user_id,user_name,user_phone,idcard_num,user_grade,is_verify,created_by,stamp_created,updated_by,stamp_updated,state FROM lc_user WHERE 1=1 and state=? ");
        StringBuilder countSql = new StringBuilder("SELECT  count(user_id) as totalRecord FROM lc_user WHERE 1=1 and state=? ");
     // 存放查询参数
        List<Object> paramList = new ArrayList<Object>();
        paramList.add("A");
        String name =u.getUser_name();
        String phone = u.getUser_phone();
        String is_verify = u.getIs_verify();
        if(null!=name&&!"".equals(name.trim())){
            sql.append(" and user_name like ? ");
            countSql.append("and user_name like ?");
            paramList.add("%"+name+"%");
        }
        if(null!=phone&&!"".equals(phone.trim())){
            sql.append(" and user_phone = ? ");
            countSql.append(" and user_phone = ? ");
            paramList.add(phone );
        }
        if(null!=is_verify&&!"".equals(is_verify.trim())){
            sql.append(" and is_verify = ? ");
            countSql.append(" and is_verify = ? ");
            paramList.add(is_verify );
        }
        String[] str = paramList.toArray(new String[paramList.size()]); 
        
        long totalRecord  = 0;
        // 起始索引
        long fromIndex  = pageSize * (pageNum -1);      
        // 使用limit关键字，实现分页
        sql.append(" order by stamp_created desc   limit " + fromIndex + ", " + pageSize );
        List<User> msgList = new ArrayList<User>();
        try {
            connection = DBUtils.getConnection();
            DBUtils.beginTx(connection);
            // 获取总记录数
            totalRecord =(Long) (Number) qR.query(connection,countSql.toString(),scalarHandler,str);
            // 获取查询的消息记录
            msgList  = qR.query(connection,sql.toString(),new BeanListHandler<User>(User.class),str);
            //获取总页数
            long totalPage = totalRecord / pageSize;
            if(totalRecord % pageSize !=0){
                totalPage++;
            }
            // 组装pager对象
            result = new Pager<User>(pageSize, pageNum, totalRecord, totalPage, msgList);
        } catch (Exception e) {
            DBUtils.rollback(connection);
            e.printStackTrace();
        } finally{
            DBUtils.releaseDB(null, null, connection);
        }
        return result;
    }

}
