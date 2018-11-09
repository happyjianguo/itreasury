/*
 * Created on 2005-6-15
 */
package com.iss.itreasury.ebank.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.ebank.system.dataentity.AcctTransInfo;
import com.iss.itreasury.ebank.system.dataentity.AcctTransParam;

/**
 * 账户历史交易DAO
 * @author mxzhou
 */
public class AccountHisTransDAO extends ITreasuryDAO
{
    public static final String strTableName = "BS_ACCTHISTRANSINFO";
	public static boolean isNeedPrefix = true;
	
	/**
     * 返回单一账户在查询期间内的历史交易信息
     * 
     * @param paramInfo
     * @return
     * @throws BusinessException
     */
    public Collection findSingleAcctHisBalance(AcctTransParam param) throws Exception
    {
        ArrayList list = new ArrayList();
        StringBuffer sbSQL = new StringBuffer();

        Connection transConn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        //初始化数据库联接
        try
        {
            if (transConn == null)
            {
                transConn = Database.getConnection();
            }
        }
        catch (Exception e)
        {
            throw new Exception("数据库初使化异常发生", e);
        }
        

        sbSQL.append(" 		SELECT dt_TransactionTime as transactionTime , \n");
        sbSQL.append(" 		  s_RemarkInfo as remarkInfo , \n");
        sbSQL.append(" 		  s_Abstractinfo as abstractInfo , \n");
        sbSQL.append(" 		  s_CheckNo as checkNo , \n");
        sbSQL.append(" 		  s_OppAccountNo as oppAccountNo , \n");
        sbSQL.append(" 		  s_OppAccountName as oppAccountName , \n");
        sbSQL.append(" 		  n_direction as direction , \n");
        sbSQL.append(" 		  decode(n_direction,1,n_amount,null) as debitAmount , \n");
        sbSQL.append(" 		  decode(n_direction,2,n_amount,null) as creditAmount  \n");
        sbSQL.append(" 		FROM ( \n");
        sbSQL.append("           SELECT his.* from bs_accthistransinfo his \n");
        sbSQL.append("           union \n");
        sbSQL.append("           SELECT cur.* from bs_acctcurtransinfo cur \n");
        sbSQL.append("           ) all_transinfo \n");
        sbSQL.append(" 		WHERE   \n");

        sbSQL.append(" \n 	n_accountid = " + param.getAccountId());
        
        //12.23日修改，更正对账单中统计已被银行删除数据
        sbSQL.append("\n  and n_isdeletedbybank = 0 ");

        if (param.getTransactionStartDate() != null)// 查询日期起
        {
            sbSQL.append(" and to_char( dt_transactiontime,'yyyy-mm-dd' ) >= " + "'"
                    + DataFormat.formatDate(param.getTransactionStartDate(), 1) + "'" + " \n");
        }

        if (param.getTransactionEndDate() != null)// 查询日期止
        {
            sbSQL.append(" and to_char( dt_transactiontime,'yyyy-mm-dd' ) <= " + "'"
                    + DataFormat.formatDate(param.getTransactionEndDate(), 1) + "'" + " \n");
        }

        sbSQL.append(" Order By transactionTime \n");

        log.debug("Log Informations：  sbSQL = " + sbSQL.toString());

        try {
            ps = transConn.prepareStatement(sbSQL.toString());
            rs = ps.executeQuery();
            while (rs.next()) 
            {
                AcctTransInfo info = new AcctTransInfo();

                info.setTransactionTime(rs.getDate("transactionTime"));
                info.setRemarkInfo(rs.getString("remarkInfo"));
                info.setAbstractInfo(rs.getString("abstractInfo"));
                info.setCheckNo(rs.getString("checkNo"));
                info.setOppAccountNo(rs.getString("oppAccountNo"));
                info.setOppAccountName(rs.getString("oppAccountName"));
                info.setDirection(rs.getLong("direction"));
                info.setDebitAmount(rs.getDouble("debitAmount"));
                info.setCreditAmount(rs.getDouble("creditAmount"));

                list.add(info);

            }
            
            //关闭资源
            if (rs != null)
            {
                rs.close();
                rs = null;
            }
            
            if (ps != null)
            {
                ps.close();
                ps = null;
            }
            
            if (transConn != null)
            {
                transConn.close();
                transConn = null;
            }
        }
        catch (SQLException e) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                    rs = null;
                }

                if (ps != null)
                {
                    ps.close();
                    ps = null;
                }

                if (transConn != null)
                {
                    transConn.close();
                    transConn = null;
                }
            }
            catch (SQLException e)
            {
                throw new Exception("数据库关闭异常发生", e);
            }
        }

        return list;
    }
}
