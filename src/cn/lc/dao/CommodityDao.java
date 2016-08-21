package cn.lc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.sun.jmx.snmp.Timestamp;

import cn.lc.beans.Commodity;
import cn.lc.beans.Pager;

public class CommodityDao {
	QueryRunner qR = new QueryRunner();
	
	//后台取得商品列表
    public Pager<Commodity> getCommodityList(Commodity searchMode, Integer pageNum, Integer pageRows) {
    	
    	Pager<Commodity> page = new Pager<Commodity>(0, 0, 0, 0,null);

        Connection connection = null;
        List<Commodity> list = null;
        List<Commodity> allList = null;
        
        StringBuffer sql = new StringBuffer();
        sql.append("select * from (SELECT * FROM lc_commodity_details WHERE 1=1 and delete_flag = '0' ");
        StringBuffer sql2 = new StringBuffer();
        sql2.append("SELECT * FROM lc_commodity_details WHERE 1=1 and delete_flag = '0' ");
        
        String commodity_id = searchMode.getCommodity_id();
        String commodity_use_flag = searchMode.getCommodity_use_flag();
        String commodity_name = searchMode.getCommodity_name();
        String Commodity_type = searchMode.getCommodity_type();
        
        //参数集合
        List<String> params = new ArrayList<String>(); 
        if(null!=commodity_id&&!"".equals(commodity_id.trim())){
        	sql.append(" and commodity_id = ? ");
        	sql2.append(" and commodity_id = ? ");
        	params.add(commodity_id.trim());
        }
        if(null!=commodity_name&&!"".equals(commodity_name.trim())){
        	sql.append(" and commodity_name = ? ");
        	sql2.append(" and commodity_name = ? ");
        	params.add(commodity_name.trim());
        }

        if(null!=commodity_use_flag&&!"".equals(commodity_use_flag.trim())){
        	sql.append(" and commodity_use_flag = ? ");
        	sql2.append(" and commodity_use_flag = ? ");
        	params.add(commodity_use_flag.trim());
        }

        if(null!=Commodity_type&&!"".equals(Commodity_type.trim())){
        	sql.append(" and Commodity_type = ? ");
        	sql2.append(" and Commodity_type = ? ");
        	params.add(Commodity_type.trim());
        }
        sql.append(" order by create_date desc ) a ");
        
        //创建参数字符串数组
        String[] paramStr = params.toArray(new String[params.size()]); 
        
        long fromIndex  = pageRows * (pageNum -1); 

        long totalRecord  = 0;
        sql.append(" limit " + fromIndex + ", " + pageRows);
        try {
            connection = DBUtils.getConnection();
            DBUtils.beginTx(connection);
            // 获取总记录数
            allList = qR.query(connection,sql2.toString(),new BeanListHandler<Commodity>(Commodity.class), paramStr);
            if(allList == null){
            	totalRecord = 0;
            }else{
            	totalRecord = allList.size();
            }
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
     * 获取所有的商品信息
     * */
    public Pager<Commodity> getAppPageCommodity(Integer pageNum, Integer pageSize, String first_search_date){
    	Pager<Commodity> commodityPage = new Pager<Commodity>(0,0,0,0,null);
        List<Commodity> commodityList = new ArrayList<Commodity>();
        List<Commodity> commodityAllList = new ArrayList<Commodity>();
    	
        Connection connection = null;
        StringBuffer sql2 = new StringBuffer();
        sql2.append("SELECT commodity_id, commodity_name, commodity_pay, commodity_imgurl, commodity_type, ");
        sql2.append(" commodity_comment, create_date, update_date ");
        sql2.append(" FROM lc_commodity_details WHERE delete_flag = '0' and commodity_use_flag = '0' ");
        sql2.append(" and create_date < ?");
        
        StringBuffer sql = new StringBuffer();
        sql.append("select * from (SELECT commodity_id, commodity_name, commodity_num, "
        		+ "commodity_pay, commodity_money,commodity_blend_integral,commodity_blend_money,"
        		+ "commodity_imgurl, commodity_type, ");
        sql.append(" commodity_comment, create_date, update_date ");
        sql.append(" FROM lc_commodity_details WHERE delete_flag = '0' and commodity_use_flag = '0' ");
        sql.append(" and create_date < ? order by create_date desc) a ");
        long fromIndex  = pageSize * (pageNum -1); 
        sql.append(" limit " + fromIndex + ", " + pageSize);
        long totalRecord  = 0;
        try {
            connection = DBUtils.getConnection();
            DBUtils.beginTx(connection);
            // 获取总记录数
            commodityAllList = qR.query(connection, sql2.toString(), new BeanListHandler<Commodity>(Commodity.class), first_search_date);
            if(commodityAllList == null){
            	totalRecord = 0;
            }else{
            	totalRecord = commodityAllList.size();
            }
            commodityList = qR.query(connection, sql.toString(), new BeanListHandler<Commodity>(Commodity.class), first_search_date);
            // 组装pager对象
            long totalPage = totalRecord / pageSize;
            if(totalRecord % pageSize !=0){
                totalPage++;
            }
            commodityPage = new Pager<Commodity>(pageSize, pageNum, totalRecord, totalPage, commodityList);
        } catch (Exception e) {
            DBUtils.rollback(connection);
            e.printStackTrace();
        } finally {
            DBUtils.releaseDB(null, null, connection);
        }
    	return commodityPage;
    }
    /**
     * 根据指定id查询商品信息
     * */
    public Commodity getCommodityById(String commodity_id){
    	Connection connection = null;
        Commodity commodity = new Commodity();
        
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM lc_commodity_details WHERE commodity_id = ? and delete_flag = '0'");
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
     * 根据指定id查询商品信息
     * */
    public Commodity getCommodity(String commodity_id){
    	Connection connection = null;
        Commodity commodity = null;
        
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM lc_commodity_details WHERE commodity_id = ?");
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
	public Boolean updateCommodity(String id, String name, Integer pay, String money,
			Integer blend_integral, String blend_money,
    		Integer num, String use_flag, String type, String comment){
    	int update_flag = 0;
    	String update_date = (new Timestamp(System.currentTimeMillis()))+"";
    	Connection connection = null;
    	PreparedStatement st = null;
        
        StringBuffer sql = new StringBuffer();
        sql.append(" update lc_commodity_details set commodity_name = ?, ");
        sql.append(" commodity_pay = ?, ");
        sql.append(" commodity_money = ?, ");
        sql.append(" commodity_blend_integral = ?, ");
        sql.append(" commodity_blend_money = ?, ");
        sql.append(" commodity_num = ?, ");
        sql.append(" commodity_use_flag = ?, ");
        sql.append(" commodity_type = ?, ");
        sql.append(" commodity_comment = ?, ");
        sql.append(" update_date = ? ");
        sql.append(" WHERE commodity_id = ? and delete_flag = '0'");
        try {
            connection = DBUtils.getConnection();
            st = connection.prepareStatement(sql.toString());
            st.setString(1, name);
            st.setInt(2, pay);
            st.setString(3, money);
            st.setInt(4, blend_integral);
            st.setString(5, blend_money);
            st.setInt(6, num);
            st.setString(7, use_flag);
            st.setString(8, type);
            st.setString(9, comment);
            st.setString(10, update_date);
            st.setString(11, id);
            update_flag = st.executeUpdate();
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
    	PreparedStatement st = null; 
    	String update_date = (new Timestamp(System.currentTimeMillis()))+"";
        
        StringBuffer sql = new StringBuffer();
        sql.append(" update lc_commodity_details set commodity_imgurl = ?, update_date = ? ");
        sql.append(" WHERE commodity_id = ? and delete_flag = '0'");
        try {
            connection = DBUtils.getConnection();
            st = connection.prepareStatement(sql.toString());
            st.setString(1, url);
            st.setString(2, update_date);
            st.setString(3, commodity_id);
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
	
	/**
	 * 新建商品
	 * */
	public boolean createCommodity(String id, String name, Integer pay, String money,
			Integer blend_integral, String blend_money,
    		Integer num, String use_flag, String type, String comment){
    	Connection connection = null;
    	PreparedStatement st = null;
    	int insertNum = -1;
    	String create_date = (new Timestamp(System.currentTimeMillis()))+"";
    	
        StringBuffer sql = new StringBuffer();
        sql.append(" insert into lc_commodity_details (commodity_id, commodity_name");
        sql.append(" , commodity_pay, commodity_money, commodity_blend_integral, commodity_blend_money, commodity_num, commodity_type, commodity_use_flag, commodity_comment, create_date)");
        sql.append(" values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        try {
            connection = DBUtils.getConnection();
            st = connection.prepareStatement(sql.toString());
            st.setString(1, id);
            st.setString(2, name);
            st.setInt(3, pay);
            st.setString(4, money);
            st.setInt(5, blend_integral);
            st.setString(6, blend_money);
            st.setInt(7, num);
            st.setString(8, type);
            st.setString(9, use_flag);
            st.setString(10, comment);
            st.setString(11, create_date);
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
	
	/**
	 * 删除商品
	 * */
	public int deleteCommodityById(String commodity_id){
		int delflag = 0;
    	Connection connection = null;
    	PreparedStatement st = null;
        
        StringBuffer sql = new StringBuffer();
        sql.append(" update lc_commodity_details set delete_flag = '1'");
        sql.append(" WHERE commodity_id = ? ");
        try {
            connection = DBUtils.getConnection();
            st = connection.prepareStatement(sql.toString());
            st.setString(1, commodity_id);
            delflag = st.executeUpdate();
        } catch (Exception e) {
            DBUtils.rollback(connection);
            e.printStackTrace();
        } finally {
            DBUtils.releaseDB(null, null, connection);
        }
        return delflag;
	}
	
}
