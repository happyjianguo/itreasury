package com.iss.itreasury.securities.apply.dao;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.util.*;
import com.iss.itreasury.securities.apply.dataentity.*;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.util.*;
import java.util.*;
//import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SEC_BidRangeDAO extends SecuritiesDAO {

    protected Log4j log4j = new Log4j(Constant.ModuleType.SECURITIES, this);

	public SEC_BidRangeDAO(){
		super("SEC_BidRange");
	}

	/*
	 * 
	 */
	public Collection findByApplyID(long lApplyID) throws SecuritiesDAOException {

		String strSQL = "";
		Vector v = new Vector ();
        		
	    try {
			initDAO();
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
	    		
		strSQL = " select * from SEC_BidRange aa "
		    + " where 1=1 "
		    + " and StatusID = " + Constant.RecordStatus.VALID 
			+ " and ApplyFormID = " + lApplyID;
      
		log4j.debug(strSQL);
		
		try {
			prepareStatement(strSQL);
			ResultSet rs = executeQuery();
			while (rs != null && rs.next()) 
			{
			    BidRangeInfo bidRangeInfo = new BidRangeInfo ();
				//bidRangeInfo = (BidRangeInfo) getDataEntityFromResultSet(bidRangeInfo.getClass());
			    bidRangeInfo.setId(rs.getLong("id"));
			    bidRangeInfo.setApplyFormId(rs.getLong("applyFormID"));
			    bidRangeInfo.setBidStartAmount(rs.getDouble("bidStartAmount"));
			    bidRangeInfo.setBidEndAmount(rs.getDouble("bidEndAmount"));
			    bidRangeInfo.setBidStartRate(rs.getDouble("bidStartRate"));
			    bidRangeInfo.setBidEndRate(rs.getDouble("bidEndRate"));
			    bidRangeInfo.setApplyQuantity(rs.getDouble("applyQuantity"));
				
				v.add (bidRangeInfo);
			}
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException("查询申请书下投标区间产生错误",e);			
		} catch (SQLException e) {
		    throw new SecuritiesDAOException("查询申请书下投标区间产生错误",e);
		}
		
		try {
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		
		return (v.size () > 0 ? v : null);
	}
	
	/**
	 * Added by leiyang 2007/11/29
	 * @param lApplyID
	 * @return
	 * @throws SecuritiesDAOException
	 */
	public long findSumApplyQuantity(long lApplyID) throws SecuritiesDAOException {
		String strSQL = "";
		//Vector v = new Vector ();
		long lSumApplyQuantity = 0;
        		
	    try {
			initDAO();
	    		
			strSQL = " select sum(t.applyquantity) from sec_bidrange t"
			    + " where 1=1 "
			    + " and t.StatusID = " + Constant.RecordStatus.VALID 
			    + " and t.ApplyFormID = " + lApplyID;
      
			log4j.debug(strSQL);
		
			prepareStatement(strSQL);
			ResultSet rs = executeQuery();
			if (rs != null && rs.next()){
				lSumApplyQuantity = rs.getLong(1);
			}
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException("查询申请书下投标数据总和产生错误",e);			
		} catch (SQLException e) {
		    throw new SecuritiesDAOException("查询申请书下投标数据总和产生错误",e);
		}
		
		try {
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		
		return lSumApplyQuantity;
	}
	
	/*
	 * 
	 */
	public BidRangeInfo findMaxAmountByBidRangeInfo(long lApplyID) throws SecuritiesDAOException {

		StringBuffer strSQL = null;
		BidRangeInfo bidRangeInfo = null;
        		
	    try {
			initDAO();
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		strSQL = new StringBuffer();
		strSQL.append("select t.* from SEC_BidRange t ");
		strSQL.append("where t.applyformid = " + lApplyID + " ");
		strSQL.append("and t.statusid = " + Constant.RecordStatus.VALID + " ");
		strSQL.append("and t.bidendamount =( ");
		strSQL.append("    select max(a.bidEndAmount) from SEC_BidRange a ");
		strSQL.append("    where a.applyformid = " + lApplyID + " ");
		strSQL.append("    and a.statusid = "+ Constant.RecordStatus.VALID +")");
      
		log4j.debug(strSQL.toString());
		
		try {
			prepareStatement(strSQL.toString());
			ResultSet rs = executeQuery();
			if (rs != null && rs.next()) 
			{
			    bidRangeInfo = new BidRangeInfo ();
			    bidRangeInfo.setId(rs.getLong("id"));
			    bidRangeInfo.setApplyFormId(rs.getLong("applyFormID"));
			    bidRangeInfo.setBidStartAmount(rs.getDouble("bidStartAmount"));
			    bidRangeInfo.setBidEndAmount(rs.getDouble("bidEndAmount"));
			    bidRangeInfo.setBidStartRate(rs.getDouble("bidStartRate"));
			    bidRangeInfo.setBidEndRate(rs.getDouble("bidEndRate"));
			    bidRangeInfo.setApplyQuantity(rs.getDouble("applyQuantity"));
			}
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException("查询申请书下投标区间最大金额产生错误",e);			
		} catch (SQLException e) {
		    throw new SecuritiesDAOException("查询申请书下投标区间最大金额产生错误",e);
		}
		
		try {
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		
		return bidRangeInfo;
	}
	
	
	public double findMaxAmount(long lApplyID) throws SecuritiesDAOException {

		String strSQL = "";
		Vector v = new Vector ();
		double dMaxAmount = 0;
        		
	    try {
			initDAO();
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
	    		
		/*strSQL = " select max(nvl(decode(bidEndAmount,0,100,bidEndAmount),100)*nvl(applyQuantity,0)) "
		    + " from SEC_BidRange aa "
		    + " where 1=1 "
		    + " and StatusID = " + Constant.RecordStatus.VALID 
			+ " group by ApplyFormID "
		    + " having ApplyFormID = " + lApplyID;/*/
		
		strSQL = "select max(bidEndAmount) from SEC_BidRange t where t.applyformid = "+ lApplyID +" and t.statusid = " + Constant.RecordStatus.VALID;
      
		log4j.debug(strSQL);
		
		try {
			prepareStatement(strSQL);
			ResultSet rs = executeQuery();
			if (rs != null && rs.next()) 
			{
			    dMaxAmount = rs.getDouble(1);
			}
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException("查询申请书下投标区间最大金额产生错误",e);			
		} catch (SQLException e) {
		    throw new SecuritiesDAOException("查询申请书下投标区间最大金额产生错误",e);
		}
		
		try {
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		
		return (dMaxAmount);
	}
	
	public static void main(String[] args)
	{
	    long lTransactionTypeID = -1;
	    long lActionID = -1;
	    long lApprovalID = -1;
	    
	    try {
            
            for (int ii=10;ii<83;ii++)
            {
                for (int jj=1;jj<10;jj++)
                {
                    //lApprovalID = NameRef.getApprovalIDByTransactionTypeID(ii*100+jj,1);
                    Log.print("lTransactionTypeID = " + ii*100+jj);
                    Log.print("lApprovalID        = " + lApprovalID);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
 	    
	}
	
}
