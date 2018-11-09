/*
 * Created on 2004-5-24
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.attornmentapply.dao;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.assurechargenotice.dataentity.AssureChargeNoticeInfo;
import com.iss.itreasury.loan.attornmentapply.dataentity.AttornmentApplyInfo;
import com.iss.itreasury.loan.attornmentapply.dataentity.AttornmentContractInfo;
import com.iss.itreasury.loan.attornmentapply.dataentity.AttornmentQueryInfo;
import com.iss.itreasury.loan.base.LoanDAOException;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.securities.util.SecuritiesDAO;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AttornmentContractDao extends SecuritiesDAO {
    protected Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);

    public AttornmentContractDao() {
        super("SEC_AttornmentContract");
    }

    public AttornmentApplyInfo findApplyInfo(long oid) throws IException 
    {
    	AttornmentApplyInfo aInfo = new AttornmentApplyInfo();
    	String strSQL = "";
    	try
    	{
    		try
            {
            	initDAO();
            }
            catch (ITreasuryDAOException e)
            {
            	throw new LoanDAOException(e);
            }
            strSQL = " select a.*,c.id as c_id,c.CONTRACTID as contractID,c.CONTRACTCODE as contractCode "
    			+ ",c.CLIENTID as clientID "
    			+ " FROM SEC_AttornmentContract c,sec_attornmentapplyform a "
    			+ " where c.ATTORNMENTAPPLYID = a.ID  and a.id = "+oid;
            prepareStatement(strSQL);
        	ResultSet rs1 = executeQuery();
        	if (rs1 != null && rs1.next())
        	{
        		AttornmentContractInfo cInfo = new AttornmentContractInfo();
        		aInfo.setId(rs1.getLong("id"));
        		aInfo.setCode(rs1.getString("code"));
        		aInfo.setInputUserId(rs1.getLong("INPUTUSERID"));
        		aInfo.setInputDate(rs1.getTimestamp("INPUTDATE"));
        		aInfo.setNextCheckUserId(rs1.getLong("NEXTCHECKUSERID"));
        		aInfo.setUpdateDate(rs1.getTimestamp("UPDATEDATE"));
        		aInfo.setStatusId(rs1.getLong("STATUSID"));
        		cInfo.setId(rs1.getLong("c_id"));
        		cInfo.setAttornmentApplyId(rs1.getLong("id"));
        		cInfo.setContractId(rs1.getLong("contractID"));
        		cInfo.setContractCode(rs1.getString("contractCode"));
        		cInfo.setClientId(rs1.getLong("clientID"));
        		cInfo.setAttornmentAmount(rs1.getDouble("attornmentAmount"));
        		aInfo.setContractInfo(cInfo);
        	}
    	}
    	catch(Exception e)
    	{
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
        return aInfo;
    }
    
    public long findApplyIdByContractId(long oid) throws IException {
        if (oid != -1) {
            StringBuffer buf = new StringBuffer();
            buf.append(" SELECT ATTORNMENTAPPLYID FROM SEC_AttornmentContract ");
            buf.append(" WHERE 1=1 AND id=").append(oid);
            prepareStatement(buf.toString());
            ResultSet rs = executeQuery();
            try {
                while (rs.next()) {
                    return rs.getLong("ATTORNMENTAPPLYID");
                }
            } catch (SQLException ex) {
                return -1L;
            }
        }
        return -1L;
    }

    public Collection findAttormentForLoanByMultioption(AttornmentQueryInfo qInfo) throws java.rmi.RemoteException, IException 
    {
		String strSelect = "";
		String strSQL = "";
		String strSQL1 = "";
		Vector v = new Vector();


		long statusID = qInfo.getStatusId();
		long userID = qInfo.getUserId();
		long queryPurpose = qInfo.getQueryPurpose();
		long contractIDStart = qInfo.getStartAttornmentApplyId();
		long contractIDEnd = qInfo.getEndAttornmentApplyId();		
		long currencyID = qInfo.getCurrencyId();
		long officeID = qInfo.getOfficeId();
		
		long pageLineCount = qInfo.getPageLineCount();
		long pageNo = qInfo.getPageNo();
		long orderParam = qInfo.getOrderParam();
		long desc = qInfo.getDesc();
		String orderParamString = qInfo.getOrderParamString();
		long recordCount = -1;
		long pageCount = -1;
		long rowNumStart = -1;
		long rowNumEnd = -1;
		
		try
        {
            try
            {
            	initDAO();
            }
            catch (ITreasuryDAOException e)
            {
            	throw new LoanDAOException(e);
            }
            //计算记录总数				
            if (queryPurpose == 1) //for modify
            {
            	strSQL = "";
            	strSelect = " select count(*) ";
            	strSQL = " FROM SEC_AttornmentContract c,sec_attornmentapplyform a "
            			+ " where c.ATTORNMENTAPPLYID = a.ID ";
            	if(statusID == LOANConstant.AttornmentStatus.SUBMIT)
            	{
            		strSQL += " and a.INPUTUSERID = "
            				+ userID
            				+ " and a.STATUSID = "
                			+ statusID; 
            	}
            	else
            	{
            		strSQL += " and a.STATUSID = "
            				+ statusID; 
            	}	          	
            }
            else if (queryPurpose == 2) //for examine
            {
            	strSelect = " select count(*) ";
            	strSQL = " FROM SEC_AttornmentContract c,sec_attornmentapplyform a "
        			+ " where c.ATTORNMENTAPPLYID = a.ID ";
            	if(statusID == LOANConstant.AttornmentStatus.SUBMIT)
            	{
            		strSQL += " and a.INPUTUSERID != "
        					+ userID
        					+ " and a.STATUSID = "
        					+ LOANConstant.AttornmentStatus.SUBMIT; 
            	}
            	else
            	{
            		strSQL += " and a.NEXTCHECKUSERID = "
            				+ userID
            				+ " and a.STATUSID = "
            				+ LOANConstant.AttornmentStatus.CHECK; 
            	}	
            } 
            if(contractIDStart>0)
            {
            	strSQL += " and c.CONTRACTID >= "+ contractIDStart;
            }
            if(contractIDEnd>0)
            {
            	strSQL += " and c.CONTRACTID <= "+ contractIDEnd;
            }	
            ////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
            int nIndex = 0;
            nIndex = orderParamString.indexOf(".");
            if (nIndex > 0)
            {
            	if (orderParamString.substring(0, nIndex).toLowerCase().equals("sec_attornmentapplyform"))
            	{
            		strSQL += " order by a." + orderParamString.substring(nIndex + 1);
            	} 
            	else if (orderParamString.substring(0, nIndex).toLowerCase().equals("SEC_AttornmentContract"))
            	{
            		strSQL += " order by c." + orderParamString.substring(nIndex + 1);
            	}
            }
            else
            {
            	strSQL += " order by a.ID";
            }
            if (desc == Constant.PageControl.CODE_ASCORDESC_DESC)
            {
            	strSQL += " desc";
            }
            System.out.println(strSelect + strSQL);
            log4j.debug(strSelect + strSQL);
            try
            {
            	prepareStatement(strSelect + strSQL);
            	ResultSet rs = executeQuery();
            	if (rs != null && rs.next())
            	{
            		recordCount = rs.getLong(1);
            	}
            }
            catch (ITreasuryDAOException e)
            {
            	throw new LoanDAOException("批量查询通知单笔数产生错误", e);
            }
            catch (SQLException e)
            {
            	throw new LoanDAOException("批量查询通知单笔数产生错误", e);
            }
            recordCount = recordCount / pageLineCount;
            if ((recordCount % pageLineCount) != 0)
            {
                recordCount++;
            }
            
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //返回需求的结果集
            rowNumStart = (pageNo - 1) * pageLineCount + 1;
            rowNumEnd = rowNumStart + pageLineCount - 1;
            strSelect = " select a.*,c.CONTRACTID as contractID,c.CONTRACTCODE as contractCode "
            			+ ",c.CLIENTID as clientID ";
            strSQL = " select * from ( select aa.*,rownum r from ( " + strSelect + strSQL;
            strSQL += " ) aa ) where r between " + rowNumStart + " and " + rowNumEnd;
            System.out.println(strSQL);
            log4j.debug(strSQL);
            try
            {
            	prepareStatement(strSQL);
            	ResultSet rs1 = executeQuery();
            	while (rs1 != null && rs1.next())
            	{
            		AttornmentApplyInfo aInfo = new AttornmentApplyInfo();
            		AttornmentContractInfo cInfo = new AttornmentContractInfo();
            		aInfo.setId(rs1.getLong("id"));
            		aInfo.setCode(rs1.getString("code"));
            		aInfo.setInputUserId(rs1.getLong("INPUTUSERID"));
            		aInfo.setInputDate(rs1.getTimestamp("INPUTDATE"));
            		aInfo.setNextCheckUserId(rs1.getLong("NEXTCHECKUSERID"));
            		aInfo.setUpdateDate(rs1.getTimestamp("UPDATEDATE"));//复核时间
            		aInfo.setStatusId(rs1.getLong("STATUSID"));
            		cInfo.setAttornmentApplyId(rs1.getLong("id"));
            		cInfo.setContractId(rs1.getLong("contractID"));
            		cInfo.setContractCode(rs1.getString("contractCode"));
            		cInfo.setClientId(rs1.getLong("clientID"));
            		cInfo.setAttornmentAmount(rs1.getDouble("attornmentAmount"));
            		aInfo.setContractInfo(cInfo);
            		
            		v.add(aInfo);
            	}
            }
            catch (ITreasuryDAOException e)
            {
            	throw new LoanDAOException("批量查询申请书产生错误", e);
            }
            catch (SQLException e)
            {
            	throw new LoanDAOException("批量查询申请书产生错误", e);
            }
            try
            {
            	finalizeDAO();
            }
            catch (ITreasuryDAOException e)
            {
            	throw new LoanDAOException(e);
            }
        } catch (Exception e)
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
		return (v.size() > 0 ? v : null);
	}
    
    public static void main(String[] args) {
    	AttornmentContractDao dao = new AttornmentContractDao();
    	Collection c = null;
    	try
    	{
    		AttornmentQueryInfo qInfo = new AttornmentQueryInfo();
    		qInfo.setQueryPurpose(2);
    		qInfo.setStatusId(2);
    		
    		AttornmentApplyInfo info = dao.findApplyInfo(1);
    		System.out.print("-----------code = "+info.getCode());
    		
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	
    }
    
    /**取消资产转申请对应的让合同转让信息
     * @param lAyylyId
     * @throws ITreasuryDAOException
     */
    public void cancleByApplyId(long lAyylyId) throws ITreasuryDAOException{
    	String sql = "update sec_attornmentcontract set statusid = " + LOANConstant.AttornmentStatus.CANCEL + " where attornmentapplyid = " + lAyylyId;
    	try{
    		initDAO();
    		prepareStatement(sql, true);
    		executeUpdate();
    	}catch(Exception e){
    		e.printStackTrace();
    		throw new ITreasuryDAOException("Gen_001",e);
    	}finally{
    		 finalizeDAO();
    	}
    }    
}
