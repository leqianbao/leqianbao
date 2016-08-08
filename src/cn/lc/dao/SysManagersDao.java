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
import cn.lc.beans.SysManager;
import cn.lc.utils.StringUtils;



public class SysManagersDao {
	
	QueryRunner qR = new QueryRunner();
	//@PT--管理员登陆
	public SysManager login (String phone,String custPwd){
		Connection connection = null;
		SysManager sm = null;
		try {
			connection = DBUtils.getConnection();
			String sql = "SELECT manager_id,mgr_loginid,mgr_passwd,mgr_name,mgr_sex,mgr_phone,mgr_address,role_ids,created_by,stamp_created,updated_by,stamp_updated,state FROM lc_sys_manager WHERE mgr_loginid = ? and mgr_passwd = ?";
			DBUtils.beginTx(connection);
			sm = qR.query(connection,sql,new BeanHandler<SysManager>(SysManager.class),phone,custPwd);
		} catch (Exception e) {
			DBUtils.rollback(connection);
			e.printStackTrace();
		} finally{
			DBUtils.releaseDB(null, null, connection);
		}
		return sm;
	}
	
	//@PT--添加管理员
	public boolean addSysManager(SysManager sm){
		
		boolean result = false;
		Connection connection = null;
		try {
			connection = DBUtils.getConnection();
			String sql = "INSERT INTO lc_sys_manager (mgr_loginid,mgr_passwd,mgr_name,mgr_sex,mgr_phone,mgr_address,role_ids,created_by,stamp_created,updated_by,stamp_updated,state) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
			DBUtils.beginTx(connection);
			int isSuccess = qR.update(connection,sql,sm.getCreated_by(),sm.getStamp_created(),sm.getUpdated_by(),sm.getStamp_updated(),sm.getState());
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
	 
	//修改PT
	public boolean updateSysManager(SysManager sm){
	        
	        boolean result = false;
	        Connection connection = null;
	        List<Object> paramList = new ArrayList<Object>();
	        StringBuilder sql = new StringBuilder("UPDATE bsx_sys_manager SET　");
	        String mgr_name =sm.getMgr_name();
	        if(null!=mgr_name&&!"".equals(mgr_name.trim())){
	            sql.append(" mgr_name =?,");
	            paramList.add(mgr_name);
	        }
	        String mgr_loginid = sm.getMgr_loginid();
	        if(null!=mgr_loginid&&!"".equals(mgr_loginid.trim())){
	            sql.append(" mgr_loginid =?,");
	            paramList.add(mgr_loginid);
	        }
	        String mgr_passwd = sm.getMgr_passwd();
	        if(null!=mgr_passwd&&!"".equals(mgr_passwd.trim())){
	            sql.append(" mgr_passwd =?,");
	            paramList.add(mgr_passwd);
	        }
	        String mgr_sex = sm.getMgr_sex();
	        if(null!=mgr_sex&&!"".equals(mgr_sex.trim())){
	            sql.append(" mgr_sex =?,");
	            paramList.add(mgr_sex);
	        }
	        String mgr_phone = sm.getMgr_phone();
	        if(null!=mgr_phone&&!"".equals(mgr_phone.trim())){
	            sql.append(" mgr_phone =?,");
	            paramList.add(mgr_phone);
	        }
	        String mgr_address = sm.getMgr_address();
	        if(null!=mgr_address&&!"".equals(mgr_address.trim())){
	            sql.append(" mgr_address =?,");
	            paramList.add(mgr_address);
	        }
	        String role_ids = sm.getRole_ids();
	        if(null!=role_ids&&!"".equals(role_ids.trim())){
	            sql.append(" role_ids =?,");
	            paramList.add(role_ids);
	        }
	        String state = sm.getRole_ids();
	        if(null!=state&&!"".equals(state.trim())){
	            sql.append(" state =?,");
	            paramList.add(state);
	        }
	        sql.append(" updated_by=?,stamp_updated=? WHERE manager_id=? ");
	        String stamp_updated = StringUtils.getDateString(sm.getStamp_updated());
	        String updated_by = sm.getUpdated_by();
	        String manager_id = sm.getManager_id()+"";
	        paramList.add(updated_by);
	        paramList.add(stamp_updated);
	        paramList.add(manager_id);

	        String[] str = StringUtils.getArrayStringFromArrayList(paramList); 
	        for (String string : str) {
	            System.out.println(string);
	        }
	        String[] strs = paramList.toArray(new String[paramList.size()]); 
	        for (String string : strs) {
	            System.out.println(string);
	        }
	        try {
	            connection = DBUtils.getConnection();
	           // connection.setAutoCommit(false);
	            DBUtils.beginTx(connection);
	            int isSuccess = qR.update(connection,sql.toString(),strs);
	            if(isSuccess==1){
	                DBUtils.commit(connection);
	                result = true;
	            } else{
	                DBUtils.rollback(connection);
	                result = false;
	            }
	        } catch (Exception e) {
	            DBUtils.rollback(connection);
	            result = false;
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
	 
	 //@PT--分页查询
	 public Pager<SysManager> findManagerPager(SysManager u,int pageNum,int pageSize){
	     Pager<SysManager> result = null;
	     // 存放查询参数
	     Connection connection = null;
	     StringBuilder sql = new StringBuilder(
	             "SELECT manager_id,mgr_loginid,mgr_passwd,mgr_name,mgr_sex,mgr_phone,mgr_address,role_ids,created_by,stamp_created,updated_by,stamp_updated,state FROM lc_sys_manager WHERE 1=1 and state=? ");
	     StringBuilder countSql = new StringBuilder("SELECT  count(manager_id) as totalRecord FROM lc_sys_manager WHERE 1=1 and state=? ");
	  // 存放查询参数
	     List<Object> paramList = new ArrayList<Object>();
	     paramList.add("A");
	     
	     String name =u.getMgr_name();
	     if(null!=name&&!"".equals(name.trim())){
	         sql.append(" and mgr_name like ? ");
	         countSql.append("and mgr_name like ?");
	         paramList.add("%"+name+"%");
	     }
	     String mgr_loginid =u.getMgr_loginid();
	     if(null!=mgr_loginid&&!"".equals(mgr_loginid.trim())){
	         sql.append(" and mgr_loginid like ? ");
	         countSql.append("and mgr_loginid like ?");
	         paramList.add("%"+mgr_loginid+"%");
	     }
	     String phone = u.getMgr_phone();
	     if(null!=phone&&!"".equals(phone.trim())){
	         sql.append(" and mgr_phone = ? ");
	         countSql.append(" and mgr_phone = ? ");
	         paramList.add(phone );
	     }
	     
	     
	     String[] str = paramList.toArray(new String[paramList.size()]); 
	     
	     long totalRecord  = 0;
	     // 起始索引
	     long fromIndex  = pageSize * (pageNum -1);      
	     // 使用limit关键字，实现分页
	     sql.append(" order by stamp_created desc   limit " + fromIndex + ", " + pageSize );
	     List<SysManager> msgList = new ArrayList<SysManager>();
	     try {
	         connection = DBUtils.getConnection();
	         DBUtils.beginTx(connection);
	         // 获取总记录数
	         totalRecord =(Long) (Number) qR.query(connection,countSql.toString(),scalarHandler,str);
	         // 获取查询的消息记录
	         msgList  = qR.query(connection,sql.toString(),new BeanListHandler<SysManager>(SysManager.class),str);
	         //获取总页数
	         long totalPage = totalRecord / pageSize;
	         if(totalRecord % pageSize !=0){
	             totalPage++;
	         }
	         // 组装pager对象
	         result = new Pager<SysManager>(pageSize, pageNum, totalRecord, totalPage, msgList);
	     } catch (Exception e) {
	         DBUtils.rollback(connection);
	         e.printStackTrace();
	     } finally{
	         DBUtils.releaseDB(null, null, connection);
	     }
	     return result;
	 }


}
