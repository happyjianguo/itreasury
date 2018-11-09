package com.iss.itreasury.loan.obinterface.dao;
import java.rmi.RemoteException;
import java.util.*;
import java.sql.*;
import com.iss.itreasury.loan.obinterface.dataentity.OBBackInfo;
import com.iss.itreasury.loan.obinterface.dataentity.OBDiscountCredenceInfo;
import com.iss.itreasury.util.*;
import com.iss.itreasury.loan.util.*;
import com.iss.itreasury.ebank.util.*;
/**
 * @author gqzhang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class OBDiscountCredenceDao
{
    private static Log4j log4j = null;
    private Connection m_Conn = null;
    public OBDiscountCredenceDao(Connection con)
    {
        log4j = new Log4j(Constant.ModuleType.LOAN, this);
        m_Conn = con;
    }
    private void cleanup(ResultSet rs) throws SQLException
    {
        try
        {
            if (rs != null)
            {
                rs.close();
                rs = null;
            }
        }
        catch (SQLException sqle)
        {
        }
    }
    private void cleanup(PreparedStatement ps) throws SQLException
    {
        try
        {
            if (ps != null)
            {
                ps.close();
                ps = null;
            }
        }
        catch (SQLException sqle)
        {
        }
    }
    private void cleanup(Connection con) throws SQLException
    {
        try
        {
            if (con != null)
            {
                con.close();
                con = null;
            }
        }
        catch (SQLException sqle)
        {
        }
    }
    ////////////////////////////////////////////////////////////////
    /**
     * findByID 
     * 操作 OB_DiscountCredence 表
     * 查询记录
     * haoning 2004-02-13
     * @param lID
     * @param lOfficeID
     * @throws RemoteException;Exception
     */
    public OBDiscountCredenceInfo findByID(long lID, long lOfficeID)
        throws Exception
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        //StringBuffer sbSQL = null;
        String strSQL = "";
        OBDiscountCredenceInfo info = null;
        try
        {
            //conn = Database.getConnection();
            //获取贷款主信息数据
            Log.print("获取Credence数据");
            /*
            sbSQL.setLength(0);
            sbSQL.append(" select  a.* ");
            sbSQL.append(" from OB_DiscountCredence a ");
            sbSQL.append("  where  a.id = ? ");
            //sbSQL.append("  ");
            Log.print("strSQL is : " + sbSQL.toString());
            ps = m_Conn.prepareStatement(sbSQL.toString());
            strSQL = " select a.* from OB_DiscountCredence a where a.id=? ";
            Log.print(strSQL);
            ps = m_Conn.prepareStatement(strSQL);
            ps.setLong(1, lID);
            rs = ps.executeQuery();
            //*/
            strSQL  = " select a.* ";
            strSQL += " ,b.ID nContractID,b.sContractCode";
            strSQL += " ,b.sApplyCode,b.nBorrowClientID ";
            strSQL += " ,b.mExamineAmount,b.mCheckAmount,b.mDiscountRate ";
            strSQL += " ,c.sName sClientName, d.sName sInputUserName ";
            strSQL += " ,e.sAccountno sGrantCurrentAccount,e.sName sGrantName ";
            strSQL += " ,f.sName AccName,f.sCode AccCode";
            strSQL += " from OB_DiscountCredence a, Loan_ContractForm b ";
            strSQL += " , Client c, ob_User d, Sett_Account e, Sett_Branch f ";
            strSQL += " where a.nContractID=b.ID and b.nBorrowClientID=c.ID(+) ";
            strSQL += "   and a.nInputUserID=d.ID and b.nTypeID=? and a.ID=? ";
            strSQL += "   and a.nGrantCurrentAccountID = e.ID(+)  ";
            strSQL += "   and a.nAccountBankID = f.ID(+)";
            Log.print(strSQL);
            ps = m_Conn.prepareStatement(strSQL);
            ps.setLong(1, LOANConstant.LoanType.TX);
            ps.setLong(2, lID);
            rs = ps.executeQuery();
            if (rs != null && rs.next())
            {
                info = new OBDiscountCredenceInfo();
                info.setID(rs.getLong("ID"));
                info.setContractID(rs.getLong("NCONTRACTID"));
                info.setContractCode(rs.getString("sContractCode"));
                info.setLoanApplyCode(rs.getString("sApplyCode"));
                Log.print("===1===");
                info.setInputDate(rs.getTimestamp("DTINPUTDATE"));
                info.setDraftTypeID(rs.getLong("NDRAFTTYPEID"));
                info.setDraftCode(rs.getString("SDRAFTCODE"));
                info.setPublicDate(rs.getTimestamp("DTPUBLICDATE"));
                info.setAtTerm(rs.getTimestamp("DTATTERM"));
                info.setApplyClientName(rs.getString("SAPPLYCLIENT"));
                info.setApplyAccount(rs.getString("SAPPLYACCOUNT"));
                info.setApplyBank(rs.getString("SAPPLYBANK"));
                //Log.print("===2===");
                info.setAcceptClientName(rs.getString("SACCEPTCLIENT"));
                info.setAcceptAccount(rs.getString("SACCEPTACCOUNT"));
                info.setAcceptBank(rs.getString("SACCEPTBANK"));
                info.setAmount(rs.getDouble("MAMOUNT"));
                info.setRate(rs.getDouble("MRATE"));
                info.setInterest(rs.getDouble("MINTEREST"));
                info.setDiscountExamineAmount(rs.getDouble("mExamineAmount"));
                info.setDiscountCheckAmount(rs.getDouble("mCheckAmount"));
                info.setDiscountRate(rs.getDouble("mDiscountRate"));
                //Log.print("===3===");
                info.setStatusID(rs.getLong("NSTATUSID"));
                info.setInputUserID(rs.getLong("NINPUTUSERID"));
                info.setInputUserName(rs.getString("sInputUserName"));
                //info.setNextCheckUserID(rs.getLong(""));
                info.setFillDate(rs.getTimestamp("DTFILLDATE"));
                info.setCode(rs.getString("SCODE"));
                //Log.print("===4===");
                info.setGrantTypeID(rs.getLong("NGRANTTYPEID"));
                info.setAccountBankID(rs.getLong("NACCOUNTBANKID"));
                info.setAccountBankCode(rs.getString("AccCode"));
                info.setAccountBankName(rs.getString("AccName"));
                info.setReceiveAccount(rs.getString("SRECEIVEACCOUNT"));
                info.setReceiveClientName(rs.getString("SRECEIVECLIENTNAME"));
                info.setRemitBank(rs.getString("SREMITBANK"));
                //Log.print("===5===");
                info.setRemitProvince(rs.getString("SREMITINPROVINCE"));
                info.setRemitCity(rs.getString("SREMITINCITY"));
                info.setGrantCurrentAccountID(rs.getLong("NGRANTCURRENTACCOUNTID"));
                info.setGrantCurrentAccountCode(rs.getString("sGrantCurrentAccount"));
//              add by wjliu --begin 2007/3/21 加两个字段 
                info.setOfficeID(rs.getLong("nofficeid"));
                info.setCurrencyID(rs.getInt("ncurrencyid"));
//              add --end
                //Log.print("===6===");
            }
            cleanup(rs);
            cleanup(ps);
            //cleanup(conn);
        }
        catch (Exception e)
        {
            Log.print(e.toString());
            Log.print(" 查询贴现凭证申请失败。OBDiscountCredenceDao.findByID()");
            throw new Exception(e.getMessage());
        }
        finally
        {
            try
            {
                cleanup(rs);
                cleanup(ps);
                //cleanup(conn);
            }
            catch (Exception ex)
            {
                throw new Exception(ex.getMessage());
            }
        }
        return info;
    }
    /**
     * updateOB
     * 操作 OB_DiscountCredence 表
     * haoning 2004-02-13
     * @param OBLoanInfo
     * @throws RemoteException;Exception
     */
    public long updateOB(OBBackInfo Info) throws Exception, IException
    {
        //Connection conn = null;
        PreparedStatement ps = null;
        String strSQL = null;
        String strSQL1 = null;
        long lBillID = -1;
        long lResult = -1;
        try
        {
            //conn = Database.getConnection();
            //获取相关数据
            //修改相关数据--状态、内部申请标示、内部申请编号
            strSQL =
                "update OB_DiscountCredence set nStatusID = ?" 
                    + ",nInID = ? "
                    + ",nHandleUserID = ?  "
                    + ",sInCode = ? "
                    + " where id = ? ";
            Log.print(strSQL);
            ps = m_Conn.prepareStatement(strSQL);
            ps.setLong(1, Info.getStatusID());
            ps.setLong(2, Info.getInID());
            ps.setLong(3, Info.getUserID());
            ps.setString(4, DataFormat.formatString(Info.getApplyCode()));
            ps.setLong(5, Info.getID());
            lResult = ps.executeUpdate();
            cleanup(ps);
            if(Info.getStatusID() == OBConstant.LoanInstrStatus.REFUSE)
            {                
                strSQL1 =" update loan_discountcontractbill " 
                        + "set ob_ndiscountcredenceid = null "
                        + " where ob_ndiscountcredenceid = ?  and nstatusid = ? ";
                Log.print("拒绝贴现凭证："+strSQL1);
                ps = m_Conn.prepareStatement(strSQL1);
                ps.setLong(1, Info.getID());
                ps.setLong(2, Constant.RecordStatus.VALID);
                lResult = ps.executeUpdate();                   
                cleanup(ps);
            }
            cleanup(ps);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Log.print(" 修改贴现凭证申请失败。OBDiscountCredenceDao.updateOB()");
            throw new Exception(e.getMessage());
        }
        finally
        {
            try
            {
                cleanup(ps);
            }
            catch (Exception ex)
            {
                throw new Exception(ex.getMessage());
            }
        }
        return lResult;
    }
    /**
     * updateOBStatus
     * 操作 OB_DiscountCredence 表
     * haoning 2004-02-13
     * @param long lInID
     * @param long lStatusID
     * @throws RemoteException;Exception
     */
    public long updateOBStatus(long lInID, long lStatusID) throws Exception
    {
        //Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String strSQL = null;
        String strSQL1 = null;
        long lResult = -1;
        long lID = 0; //
        try
        {
            //conn = Database.getConnection();
            strSQL = "select ID from OB_DiscountCredence where nInID = ?";
            ps = m_Conn.prepareStatement(strSQL);
            ps.setLong(1, lInID);
            rs = ps.executeQuery();
            if (rs != null && rs.next())
            {
                lID = rs.getLong("ID");
            }
            cleanup(rs);
            cleanup(ps);
            if (lID > 0)
            {
                Log.print("===更新网银状态===");
                Log.print("lID = " + lID);
                //修改状态
                strSQL =
                    "update OB_DiscountCredence set nStatusID = ? "
                        + " where id = ? ";
                ps = m_Conn.prepareStatement(strSQL);
                ps.setLong(1, lStatusID);
                ps.setLong(2, lID);
                lResult = ps.executeUpdate();
                cleanup(ps);
                /*
                if(lStatusID == OBConstant.LoanInstrStatus.REFUSE)
                {                
                    strSQL1 =" update loan_discountcontractbill " 
                            + "set ob_ndiscountcredenceid = null "
                            + " where ob_ndiscountcredenceid = ?  and nstatusid = ? ";
                    Log.print("拒绝贴现凭证："+strSQL1);
                    ps = m_Conn.prepareStatement(strSQL1);
                    ps.setLong(1, lID);
                    ps.setLong(2, Constant.RecordStatus.VALID);
                    lResult = ps.executeUpdate();                   
                    cleanup(ps);
                }//*/
            }
            else
            {
                lResult = -1;
            }
            cleanup(rs);
            cleanup(ps);
            //cleanup(conn);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Log.print(" 修改贷款申请状态失败。OBLoanDao.updateOBStatus()");
            throw new Exception(e.getMessage());
        }
        finally
        {
            try
            {
                cleanup(rs);
                cleanup(ps);
                //cleanup(conn);
            }
            catch (Exception ex)
            {
                throw new Exception(ex.getMessage());
            }
        }
        return lResult;
    }
    
    /**
     * ChangeCredenceOBToLoan
     * 操作 loan_DiscountcontractBill 表
     * haoning 2004-03-10
     * @param long lInID 信贷的CredenceID
     * @param long lID   网银的CredenceID
     * @throws RemoteException;Exception
     */
    public long ChangeCredenceBillOBToLoan(long lInID, long lID) throws Exception
    {
        //Connection conn = null;
        PreparedStatement ps = null;
        //ResultSet rs = null;
        String strSQL = null;
        String strSQL1 = null;
        long lResult = -1;
        try
        {
            Log.print("============");
            Log.print("lID = " + lID);
            Log.print("lInID = " + lInID);
            //修改
            strSQL  = " update loan_discountcontractbill "
                    + " set ndiscountcredenceid = ? "
                    + " where ob_ndiscountcredenceid = ? "
                    + " and nstatusid = ? ";
            ps = m_Conn.prepareStatement(strSQL);
            ps.setLong(1, lInID);
            ps.setLong(2, lID);
            ps.setLong(3, Constant.RecordStatus.VALID);
            lResult = ps.executeUpdate();
            cleanup(ps);
            
            if(lResult > 0)
            {
                //修改
                strSQL  = " update loan_discountcontractbill "
                        + " set ob_ndiscountcredenceid = '' "
                        + " where ndiscountcredenceid = ? "
                        + " and nstatusid = ? ";
                ps = m_Conn.prepareStatement(strSQL);
                ps.setLong(1, lInID);
                ps.setLong(2, Constant.RecordStatus.VALID);
                lResult = ps.executeUpdate();
                cleanup(ps);
            }
        
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Log.print(" 修改贷款申请状态失败。OBLoanDao.updateOBStatus()");
            throw new Exception(e.getMessage());
        }
        finally
        {
            try
            {
                //cleanup(rs);
                cleanup(ps);
                //cleanup(conn);
            }
            catch (Exception ex)
            {
                throw new Exception(ex.getMessage());
            }
        }
        return lResult;
    }
    
    
    public static void main(String args[])
    {
        Connection conn = null;
        try
        {
            conn = Database.getConnection();
            OBDiscountCredenceDao obdcDao = new OBDiscountCredenceDao(conn);
            OBDiscountCredenceInfo info = obdcDao.findByID(1, 1);
            Log.print("1" + info.getApplyDiscountPO());
            if (conn != null)
            {
                conn.close();
                conn = null;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}