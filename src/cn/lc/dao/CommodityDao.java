package cn.lc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.lc.beans.Commodity;
import cn.lc.beans.Pager;

public class CommodityDao {
	QueryRunner qR = new QueryRunner();
	
	// @APP--银行卡列表
    public Pager<Commodity> getCommodityList(Commodity searchMode, Integer pageNum, Integer pageRows) {
    	
    	Pager<Commodity> page = new Pager<Commodity>(0, 0, 0, 0,null);

        Connection connection = null;
        List<Commodity> list = null;
        
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM lc_commodity_details WHERE 1=1");
        
        String commodity_id = searchMode.getCommodity_id();
        String commodity_use_flag = searchMode.getCommodity_use_flag();
        
        //参数集合
        List<String> params = new ArrayList<String>(); 
        if(null!=commodity_id&&!"".equals(commodity_id.trim())){
        	sql.append(" and commodity_id = ? ");
        	params.add(commodity_id.trim());
        }
        if(null!=commodity_use_flag&&!"".equals(commodity_use_flag.trim())){
        	sql.append(" and commodity_use_flag = ? ");
        	params.add(commodity_use_flag.trim());
        }
        
        //创建参数字符串数组
        String[] paramStr = params.toArray(new String[params.size()]); 
        
        long fromIndex  = pageRows * (pageNum -1); 

        long totalRecord  = 0;
        sql.append(" limit " + fromIndex + ", " + pageRows);
        try {
            connection = DBUtils.getConnection();
            DBUtils.beginTx(connection);
            // 获取总记录数
            totalRecord =(Integer)qR.query(connection,sql.toString(),new BeanListHandler<Commodity>(Commodity.class), paramStr).size();
            list = qR.query(connection, sql.toString(), new BeanListHandler<Commodity>(Commodity.class), paramStr);
            long totalPage = totalRecord / pageRows;
            if(totalRecord % pageRows !=0){
                totalPage++;
            }
            // 组装pager对象
            page = new Pager<Commodity>(pageRows, pageNum, totalRecord, totalPage, list);
        } catch (Exception e) {
            DBUtils.rollback(connection);
            e.printStackTrace();
        } finally {
            DBUtils.releaseDB(null, null, connection);
        }
        return page;
    }
    
    /**
     * 根据指定id查询商品信息
     * */
    public Commodity getCommodityById(String commodity_id){
    	Connection connection = null;
        Commodity commodity = new Commodity();
        
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM lc_commodity_details WHERE id = ?");
        try {
            connection = DBUtils.getConnection();
            DBUtils.beginTx(connection);
            commodity = qR.query(connection, sql.toString(), new BeanListHandler<Commodity>(Commodity.class), commodity_id).get(0);
        } catch (Exception e) {
            DBUtils.rollback(connection);
            e.printStackTrace();
        } finally {
            DBUtils.releaseDB(null, null, connection);
        }
        return commodity;
    }
    
   /**
    * 根据指定id更行商品
    * */
	public Boolean updateCommodity(String id, String name, Integer pay, 
    		Integer num, String use_flag, String type, String comment){
    	int update_flag = 0;
    	Connection connection = null;
        
        StringBuffer sql = new StringBuffer();
        sql.append(" update lc_commodity_details set commodity_name = ?, ");
        sql.append(" commodity_pay = ?, ");
        sql.append(" commodity_num = ?, ");
        sql.append(" commodity_use_flag = ?, ");
        sql.append(" commodity_type = ?, ");
        sql.append(" commodity_comment = ? ");
        sql.append(" WHERE commodity_id = ? ");
        try {
            connection = DBUtils.getConnection();
            DBUtils.beginTx(connection);
            update_flag = qR.update(connection, sql.toString(), name, pay, num, use_flag, type, comment, id);
        } catch (Exception e) {
            DBUtils.rollback(connection);
            e.printStackTrace();
        } finally {
            DBUtils.releaseDB(null, null, connection);
        }
        if(update_flag == 0){
        	return false;
        }else{
        	return true;
        }
    }
	
	/**
	 * 保存商品图片
	 * */
	public Boolean setCommodityImg(String commodity_id, String url){
		int setflag = 0;
    	Connection connection = null;
        
        StringBuffer sql = new StringBuffer();
        sql.append(" update lc_commodity_details set commodity_imgurl = ? ");
        sql.append(" WHERE commodity_id = ? ");
        try {
            connection = DBUtils.getConnection();
            DBUtils.beginTx(connection);
            setflag = qR.update(connection, sql.toString(), url, commodity_id);
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
	
	/**
	 * 新建商品
	 * */
	public boolean createCommodity(String id, String name, Integer pay, 
    		Integer num, String use_flag, String type, String comment){
    	Connection connection = null;
    	PreparedStatement st = null;
    	int insertNum = -1;
    	
        StringBuffer sql = new StringBuffer();
        sql.append(" insert into lc_commodity_details (commodity_id, commodity_name");
        sql.append(" , commodity_pay, commodity_num, commodity_type, commodity_use_flag, commodity_comment)");
        sql.append(" values(?, ?, ?, ?, ?, ?, ?) ");
        try {
            connection = DBUtils.getConnection();
            st = connection.prepareStatement(sql.toString());
            st.setString(1, id);
            st.setString(2, name);
            st.setInt(3, pay);
            st.setInt(4, num);
            st.setString(5, type);
            st.setString(6, use_flag);
            st.setString(7, comment);
            insertNum = st.executeUpdate();
        } catch (Exception e) {
            DBUtils.rollback(connection);
            e.printStackTrace();
        } finally {
            DBUtils.releaseDB(null, null, connection);
        }
        if(insertNum == -1){
        	return false;
        }else{
        	return true;
        }
	}
}
