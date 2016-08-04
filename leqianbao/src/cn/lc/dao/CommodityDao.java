package cn.lc.dao;

import java.sql.Connection;
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
    	
    	Pager<Commodity> page = new Pager(0, 0, 0, 0,null);

        Connection connection = null;
        List<Commodity> list = null;
        
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM lc_commodity_details WHERE 1=1");
        
        String commodity_name = searchMode.getCommodity_name();
        String commodity_use_flag = searchMode.getCommodity_use_flag();
        
        //参数集合
        List<String> params = new ArrayList<String>(); 
        if(null!=commodity_name&&!"".equals(commodity_name.trim())){
        	sql.append(" and commodity_name = ? ");
        	params.add(commodity_name.trim());
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
            totalRecord =(Long) (Number) qR.query(connection,sql.toString(),new BeanListHandler<Commodity>(Commodity.class), paramStr);
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
}
