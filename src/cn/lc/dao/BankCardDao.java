package cn.lc.dao;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.lc.beans.BankCard;

public class BankCardDao {
    QueryRunner qR = new QueryRunner();

    // @APP--银行卡列表
    public List<BankCard> getBankList(String uid) {

        Connection connection = null;
        List<BankCard> list = null;
        try {
            connection = DBUtils.getConnection();
            String sql = "SELECT bank_id,card_ownername,card_number,bank_name,bank_code,user_id,created_by,stamp_created,updated_by,stamp_updated,state FROM lc_bank_card WHERE state='A' and user_id = ?";
            DBUtils.beginTx(connection);
            list = qR.query(connection, sql, new BeanListHandler<BankCard>(BankCard.class), uid);
        } catch (Exception e) {
            DBUtils.rollback(connection);
            e.printStackTrace();
        } finally {
            DBUtils.releaseDB(null, null, connection);
        }
        return list;
    }

    // @APP--绑定银行卡
    public boolean addBankCard(BankCard b) {
        boolean result = false;
        Connection connection = null;
        try {
            connection = DBUtils.getConnection();
            String sql = "INSERT INTO lc_bank_card(card_ownername,card_number,bank_name,user_id,created_by,stamp_created,updated_by,stamp_updated,state) VALUES (?,?,?,?,?,?,?,?,?)";
            DBUtils.beginTx(connection);
            int isSuccess = qR.update(connection, sql, b.getCard_ownername(), b.getCard_number(), b.getBank_name(), b.getUser_id(),
                    b.getCreated_by(), b.getStamp_created(), b.getUpdated_by(), b.getStamp_updated(), b.getState());
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

    // @APP--解绑银行卡
    public boolean delBankCard(BankCard b) {
        boolean result = false;
        Connection connection = null;
        try {
            connection = DBUtils.getConnection();
            String sql = "UPDATE lc_bank_card SET updated_by=?,stamp_updated=?,state=? WHERE bank_id=?";
            DBUtils.beginTx(connection);
            int isSuccess = qR.update(connection, sql, b.getUpdated_by(), b.getStamp_updated(), b.getState(), b.getBank_id());
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
