package cn.lc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;


import cn.lc.beans.Commodity;
import cn.lc.beans.Pager;
import cn.lc.beans.User;
import cn.lc.beans.UserChildBean;

public class UserChildDao {

	QueryRunner qR = new QueryRunner();

	public boolean addChildUser(int userId, String childName, String phone, String idCard, String bankName,
			String bankAccount) {
		boolean result = false;
		Connection connection = null;
		try {
			connection = DBUtils.getConnection();
			String sql = "INSERT INTO lc_user_child(user_id,child_name,child_phone,child_id_card,child_bank_name,child_bank_account) VALUES(?,?,?,?,?,?)";
			DBUtils.beginTx(connection);
			int isSuccess = qR.update(connection, sql, userId, childName, phone, idCard, bankName, bankAccount);
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
	
	public List<UserChildBean> getChildUserList(int userId){
    	Connection connection = null;
		List<UserChildBean> users=new ArrayList<>();
		try {
			connection = DBUtils.getConnection();
			String sql = "SELECT user_id,child_name,child_phone,child_id_card,child_bank_name,child_bank_account,child_balance FROM lc_user_child WHERE user_id = ?";
			DBUtils.beginTx(connection);
			users = qR.query(connection,sql,new BeanListHandler<UserChildBean>(UserChildBean.class),userId);
		} catch (Exception e) {
			DBUtils.rollback(connection);
			e.printStackTrace();
		} finally{
			DBUtils.releaseDB(null, null, connection);
		}
    	return users;
    }
	
	//��̨ȡ�����˻��б�
    public Pager<UserChildBean> getChildrenList(UserChildBean searchMode, Integer pageNum, Integer pageRows) {
    	
    	Pager<UserChildBean> page = new Pager<UserChildBean>(0, 0, 0, 0,null);

        Connection connection = null;
        List<UserChildBean> list = null;
        List<UserChildBean> allList = null;
        
        StringBuffer sql = new StringBuffer();
        sql.append("select * from lc_user_child WHERE 1=1 ");
        StringBuffer sql2 = new StringBuffer();
        sql2.append("SELECT * FROM lc_user_child WHERE 1=1 ");
        
        String user_id = searchMode.getUser_id() + "";
        if(user_id.equals("-1")){
        	user_id = null;
        }
        //��������
        List<String> params = new ArrayList<String>(); 
        if(null!=user_id&&!"".equals(user_id.trim())){
        	sql.append(" and user_id = ? ");
        	sql2.append(" and user_id = ? ");
        	params.add(user_id.trim());
        }
        
        //���������ַ�������
        String[] paramStr = params.toArray(new String[params.size()]); 
        
        long fromIndex  = pageRows * (pageNum -1); 

        long totalRecord  = 0;
        sql.append(" limit " + fromIndex + ", " + pageRows);
        try {
            connection = DBUtils.getConnection();
            DBUtils.beginTx(connection);
            // ��ȡ�ܼ�¼��
            allList = qR.query(connection,sql2.toString(),new BeanListHandler<UserChildBean>(UserChildBean.class), paramStr);
            if(allList == null){
            	totalRecord = 0;
            }else{
            	totalRecord = allList.size();
            }
            list = qR.query(connection, sql.toString(), new BeanListHandler<UserChildBean>(UserChildBean.class), paramStr);
            long totalPage = totalRecord / pageRows;
            if(totalRecord % pageRows !=0){
                totalPage++;
            }
            // ��װpager����
            page = new Pager<UserChildBean>(pageRows, pageNum, totalRecord, totalPage, list);
        } catch (Exception e) {
            DBUtils.rollback(connection);
            e.printStackTrace();
        } finally {
            DBUtils.releaseDB(null, null, connection);
        }
        return page;
    }
    
    public boolean saveMoney(String id, String money){
    	int setflag = 0;
    	Connection connection = null;
    	PreparedStatement st = null; 
        
        StringBuffer sql = new StringBuffer();
        sql.append(" update lc_user_child set child_balance = ? ");
        sql.append(" WHERE id = ? ");
        try {
            connection = DBUtils.getConnection();
            st = connection.prepareStatement(sql.toString());
            st.setString(1, money);
            st.setString(2, id);
            setflag = st.executeUpdate();
        } catch (Exception e) {
            DBUtils.rollback(connection);
            e.printStackTrace();
        } finally {
            DBUtils.releaseDB(null, null, connection);
        }
        if(setflag == 0){
        	return false;
        }else{
        	return true;
        }
    }
}
