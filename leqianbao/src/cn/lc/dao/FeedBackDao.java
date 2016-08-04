package cn.lc.dao;

import java.sql.Connection;

import org.apache.commons.dbutils.QueryRunner;

import cn.lc.beans.FeedBack;

public class FeedBackDao {

    QueryRunner qR = new QueryRunner();

    // @APP--添加意见反馈
    public boolean addFeedBack(FeedBack fb) {
        boolean result = false;
        Connection connection = null;

        try {
            connection = DBUtils.getConnection();
            String sql = "INSERT INTO lc_feedback (feedback_type,feedback_content,is_read,user_id,created_by,stamp_created,updated_by,stamp_updated,state) VALUES (?,?,?,?,?,?,?,?,?)";
            DBUtils.beginTx(connection);
            int isSuccess = qR.update(connection, sql, fb.getFeedback_type(), fb.getFeedback_content(), fb.getIs_read(), fb.getUser_id(),
                    fb.getCreated_by(), fb.getStamp_created(), fb.getUpdated_by(), fb.getStamp_updated(), fb.getState());
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

}
