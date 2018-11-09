/*
 * Created on 2005-5-12
 *
 * Title:				iTreasury
 * @author             	yfan 
 * Company:             iSoftStone
 * Copyright:           Copyright (c) 2003
 * @version
 * Description:         
 */
package com.iss.itreasury.ebank.approval.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.base.LoanDAOException;
import com.iss.itreasury.ebank.approval.dataentity.ApprovalRelationInfo;

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ApprovalRelationDao extends ITreasuryDAO implements java.io.Serializable
{

	private Connection m_Conn = null;

	public ApprovalRelationDao()
	{
		super("Loan_ApprovalRelation");
	}

	public ApprovalRelationDao(Connection con)
	{
		m_Conn = con;
	}
   
	/**
	 * 保存信息
	 * 操作数据库表
	 * @param       info      	信息
	 * @return      long        成功，返回值=1；失败，返回值=-1
	 */
	public long save(ApprovalRelationInfo info) throws Exception
    {
        String strSQL = "";
        long lResult = -1;
        PreparedStatement ps = null;
		ResultSet rs = null;

        try
        {            
            initDAO();            
            strSQL = " insert into ob_ApprovalRelation(ApprovalID,ModuleID,LoanTypeID,SubLoanTypeID,ActionID,OfficeID,CurrencyID) values(?,?,?,?,?,?,?) ";            
            ps = prepareStatement(strSQL);
			ps.setLong(1, info.getApprovalID());
			ps.setLong(2, info.getModuleID());
			ps.setLong(3, info.getLoanTypeID());
			ps.setLong(4, info.getSubLoanTypeID());
			ps.setLong(5, info.getActionID());
			ps.setLong(6, info.getOfficeID());
			ps.setLong(7, info.getCurrencyID());
			lResult = ps.executeUpdate();            
            finalizeDAO();            
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally
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
        return lResult;
	}
	
	/**
	 * 删除信息
	 * 操作数据库表
	 * @param       info      	信息
	 * @return      long        成功，返回值=1；失败，返回值=-1
	 */
	public long delete(ApprovalRelationInfo info) throws LoanDAOException
    {
        String strSQL = "";
        long lResult = -1;
        PreparedStatement ps = null;
		ResultSet rs = null;

        try
        {           
            initDAO();            
            strSQL = " delete ob_ApprovalRelation where OfficeID = ? and CurrencyID = ? ";//and LoanTypeID = ? and SubLoanTypeID = ? ";
            
            ps = prepareStatement(strSQL);
			ps.setLong(1, info.getOfficeID());
			ps.setLong(2, info.getCurrencyID());
			//ps.setLong(3, info.getLoanTypeID());
			//ps.setLong(4, info.getSubLoanTypeID());
			lResult = ps.executeUpdate();            
            finalizeDAO();            
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally
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
        return lResult;
	}
	
	/**
	 * 更新信息
	 * 操作数据库表
	 * @param       info      	信息
	 * @return      long        成功，返回值=1；失败，返回值=-1
	 */
	public long update(ApprovalRelationInfo info) throws Exception
    {
        String strSQL = "";
        long lResult = -1;
        PreparedStatement ps = null;
		ResultSet rs = null;

        try
        {            
            initDAO();            
            strSQL = " update ob_ApprovalRelation set ApprovalID = ? " +
            		"  where ModuleID = ? and LoanTypeID = ? and SubLoanTypeID = ? " +
            		"  and ActionID = ? and OfficeID = ? and CurrencyID = ? and ApprovalID = ? ";            
            ps = prepareStatement(strSQL);
			ps.setLong(1, info.getApprovalID());
			ps.setLong(2, info.getModuleID());
			ps.setLong(3, info.getLoanTypeID());
			ps.setLong(4, info.getSubLoanTypeID());
			ps.setLong(5, info.getActionID());
			ps.setLong(6, info.getOfficeID());
			ps.setLong(7, info.getCurrencyID());
			ps.setLong(8, info.getId());
			lResult = ps.executeUpdate();            
            finalizeDAO();            
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally
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
        return lResult;
	}
	
	/**
	 * 通过模块类型，贷款类型，操作类型等查找对应的审批流ID
	 * 操作数据库表
	 * @param       info      	信息
	 * @return      long        成功，返回审批流ID值；失败，返回值=-1
	 */
	public long findApprovalID(ApprovalRelationInfo qInfo) throws LoanDAOException
    {
        String strSQL = "";
        long lApprovalID = -1;
        PreparedStatement ps = null;
		ResultSet rs = null;

        try
        {           
            initDAO();            
            strSQL = " select * from ob_ApprovalRelation where 1 = 1 ";
            if (qInfo.getLoanTypeID() > 0)
            {
                strSQL += " and LoanTypeID = " + qInfo.getLoanTypeID();
            }
            if (qInfo.getSubLoanTypeID() > 0)
            {
                strSQL += " and SubLoanTypeID = " + qInfo.getSubLoanTypeID();
            }
            if (qInfo.getOfficeID() > 0)
            {
                strSQL += " and OfficeID = " + qInfo.getOfficeID();
            }
            if (qInfo.getModuleID() > 0)
            {
                strSQL += " and ModuleID = " + qInfo.getModuleID();
            }
            if (qInfo.getActionID() > 0)
            {
            	strSQL += " and ActionID = " + qInfo.getActionID();
            }
            ps = prepareStatement(strSQL);
			
            rs = ps.executeQuery();
            if(rs != null && rs.next())
            {
            	lApprovalID = rs.getLong("ApprovalID");
            }
            finalizeDAO();            
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return lApprovalID;
	}
    /*
     *  查找贷款子类型下已设置审批流的操作类型
     */
    public Collection findByMultiOption(ApprovalRelationInfo qInfo)
            throws LoanDAOException
    {
        String strSelect = "";
        String strSQL = "";
        Vector v = new Vector();           

        try
        {
            initDAO();            
            strSQL = " select * from ob_ApprovalRelation where 1 = 1 ";
            if (qInfo.getLoanTypeID() > 0)
            {
                strSQL += " and LoanTypeID = " + qInfo.getLoanTypeID();
            }
            if (qInfo.getSubLoanTypeID() > 0)
            {
                strSQL += " and SubLoanTypeID = " + qInfo.getSubLoanTypeID();
            }
            if (qInfo.getOfficeID() > 0)
            {
                strSQL += " and OfficeID = " + qInfo.getOfficeID();
            }
            if (qInfo.getModuleID() > 0)
            {
                strSQL += " and ModuleID = " + qInfo.getModuleID();
            }
            
			//strSQL += " order by a.LoanTypeID,a.SubLoanTypeID ";

            try
            {
                prepareStatement(strSQL);
                ResultSet rs1 = executeQuery();
                while (rs1 != null && rs1.next())
                {
                	ApprovalRelationInfo info = new ApprovalRelationInfo();
                	info.setApprovalID(rs1.getLong("ApprovalID"));
                	info.setModuleID(rs1.getLong("ModuleID"));
                    info.setLoanTypeID(rs1.getLong("LoanTypeID")); //贷款类型大类
                    info.setSubLoanTypeID(rs1.getLong("SubLoanTypeID")); //贷款类型子类ID
                    info.setActionID(rs1.getLong("ActionID"));
                    info.setOfficeID(rs1.getLong("OfficeID")); //办事处
                    info.setCurrencyID(rs1.getLong("CurrencyID")); //币种
                    
                    v.add(info);
                }
            } catch (ITreasuryDAOException e)
            {
                ;
            } 
            finalizeDAO();            
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally
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
        return (v.size() > 0 ? v : null);
    }    
    /**
	 * 检验贷款子类型下的该操作类型与对应审批流的组合是否存在 
	 * @param       info      	信息
	 * @return      long        存在，返回值=1；否，返回值=-1
	 */
    public long checkInsert(ApprovalRelationInfo info) throws LoanDAOException
    {
        String strSQL = "";
        long lResult = -1;
        
        try
        {
            initDAO();            
            strSQL = " select * from " + strTableName
                    + " where 1 = 1 " 
                    + " and OfficeID = " + info.getOfficeID()
                    + " and CurrencyID = " + info.getCurrencyID()
                    + " and LoanTypeID = " + info.getLoanTypeID()
                    + " and SubLoanTypeID = " + info.getSubLoanTypeID()
                    + " and ModuleID = " + info.getModuleID()
                    + " and ActionID = " + info.getActionID()
                    + " and ApprovalID = " + info.getApprovalID()
                    ;           
            System.out.println("----------------strSQL=" + strSQL);
            prepareStatement(strSQL);
            ResultSet rs = executeQuery();
            if (rs != null && rs.next())
            {
               lResult = 1;
                    
            }
            finalizeDAO();            
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally
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
        return lResult;
    }    
}
