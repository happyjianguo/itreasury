/*
 * Created on 2004-8-3
 *
 * Title:				iTreasury
 * @author             	yfan 
 * Company:             iSoftStone
 * Copyright:           Copyright (c) 2003
 * @version
 * Description:         
 */
package com.iss.itreasury.loan.transdiscountcontract.dao;
import com.iss.itreasury.loan.base.LoanDAO;
import com.iss.itreasury.util.*;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.loan.base.*;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.transdiscountcontract.dataentity.TransDiscountContractBillInfo;
import com.iss.itreasury.loan.transdiscountcontract.dataentity.TransDiscountContractInfo;
//中油
//import com.iss.cpf.util.*;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.*;
/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TransDiscountContractBillDAO extends LoanDAO
{
    protected Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);
    public TransDiscountContractBillDAO(String strTableName)
    {
        super(strTableName); 
    }
    public static void main(String[] args)
        throws java.rmi.RemoteException, LoanException
    {
        TransDiscountContractBillDAO dao = new TransDiscountContractBillDAO("Loan_DiscountContractBill");
        try
        {
            TransDiscountContractBillInfo info = new TransDiscountContractBillInfo();
            info = (TransDiscountContractBillInfo) dao.findByID(3176, info.getClass());
        }
        catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        /*
        try
        {
            Collection c = null;
            c = dao.findBillByTransDiscountID(125);
        }
        catch (LoanException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        catch (RemoteException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        //*/
    }
    /**
     * 生成票据编号
     * 操作数据库表
     * @param     long     lTypeID            贷款种类(1-12)见Notes.java,贴现=13需额外处理
     * @return    String   sCode         信贷申请书编号
     **/
    public long findMaxTransDiscountContractBillSerialNo(long lTransDiscountApplyID)
        throws java.rmi.RemoteException, LoanException
    {
        long nSerialNo = -1;
        String strSQL = "";
        try
        {
            initDAO();
            try
            {
                strSQL =
                    " select nvl(max(nSerialNo),0) nSerialNo from  "
                        + this.strTableName
                        + " where 1 = 1 "
                        + " and nStatusId = "
                        + Constant.RecordStatus.VALID
                        + " and nContractId = "
                        + lTransDiscountApplyID;
                log4j.debug(strSQL);
                prepareStatement(strSQL);
                ResultSet rs = executeQuery();
                if (rs != null && rs.next())
                {
                    nSerialNo = rs.getLong(1);
                }
                if (rs != null)
                {
                    rs.close();
                    rs = null;
                }
            }
            catch (SQLException e1)
            {
                e1.printStackTrace();
            }
            finalizeDAO();
        }
        catch (ITreasuryDAOException e)
        {
            throw new LoanDAOException(e);
        }
        finally
	    {
            try
            {
                finalizeDAO();
            } catch (ITreasuryDAOException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
	    }
    
        return nSerialNo;
    }

    /**
     * 生成票据编号
     * 操作数据库表
     * @param     long     lTypeID            贷款种类(1-12)见Notes.java,贴现=13需额外处理
     * @return    String   sCode         信贷申请书编号
     **/
    public long findMaxDiscountContractBillSerialNo(long lTransDiscountApplyID)
        throws java.rmi.RemoteException, LoanException
    {
        long nSerialNo = -1;
        String strSQL = "";
        try
        {
            initDAO();
            try
            {
                strSQL =
                    " select nvl(max(nSerialNo),0) nSerialNo from  "
                        +" cra_transdiscountbill"
                        + " where 1 = 1 "
                        + " and nStatusId = "
                        + Constant.RecordStatus.VALID
                        + " and nContractId = "
                        + lTransDiscountApplyID;
                log4j.debug(strSQL);
                prepareStatement(strSQL);
                ResultSet rs = executeQuery();
                if (rs != null && rs.next())
                {
                    nSerialNo = rs.getLong(1);
                }
                if (rs != null)
                {
                    rs.close();
                    rs = null;
                }
            }
            catch (SQLException e1)
            {
                e1.printStackTrace();
            }
            finalizeDAO();
        }
        catch (ITreasuryDAOException e)
        {
            throw new LoanDAOException(e);
        }
        finally
	    {
            try
            {
                finalizeDAO();
            } catch (ITreasuryDAOException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
	    }
    
        return nSerialNo;
    }

    /**
     *申请书的单笔查询操作
    */
    public TransDiscountContractBillInfo findByID(long lID) throws java.rmi.RemoteException, LoanException
    {
        ResultSet rs = null;
        String strSQL = "";
        TransDiscountContractBillInfo info = new TransDiscountContractBillInfo();

        try
        {
            initDAO();
            
            strSQL = " select * from  " + this.strTableName + " where id=" + lID;
            
            Log.print(strSQL);
            prepareStatement(strSQL);
            rs = executeQuery();
            
            if (rs != null && rs.next())
            {
                info.setId(rs.getLong("id"));
                info.setContractID(rs.getLong("nContractId"));
                info.setSerialNo(rs.getLong("nSerialNo"));
                info.setUserName(rs.getString("sUserName"));
                info.setBank(rs.getString("sBank"));
                info.setIsLocal(rs.getLong("nIsLocal"));
                info.setCreate(rs.getTimestamp("dtCreate"));
                info.setEnd(rs.getTimestamp("dtEnd"));
                info.setCode(rs.getString("sCode"));
                info.setAmount(rs.getDouble("mAmount"));
                info.setStatusID(rs.getLong("nStatusID"));
                info.setAddDays(rs.getLong("nAddDays"));
                info.setDiscountCredenceID(
                    rs.getLong("nDiscountCredenceID"));
                info.setOB_nDiscountCredenceID(
                    rs.getLong("OB_nDiscountCredenceID"));
                info.setAcceptPoTypeID(rs.getLong("nAcceptPoTypeID"));
                info.setFormerOwner(rs.getString("sFormerOwner"));
                info.setCheckAmount(rs.getDouble("mCheckAmount"));
                info.setNstoragestatusid(rs.getLong("Nstoragestatusid"));
                    
            }
            
            finalizeDAO();
            
        }
        catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
                finalizeDAO();
            } catch (ITreasuryDAOException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
	    }
        
        return info;
    }
    
}
