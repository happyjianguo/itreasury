/*
 * Created on 2004-4-26
 *
 * Title:				iTreasury
 * @author             	yfan 
 * Company:             iSoftStone
 * Copyright:           Copyright (c) 2003
 * @version
 * Description:         
 */
package com.iss.itreasury.securities.apply.dao;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.util.*;
import com.iss.itreasury.securities.apply.dataentity.*;
import com.iss.itreasury.securities.exception.*;
import com.iss.itreasury.util.*;
import java.util.*;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SEC_AcceptInsuranceDAO extends SecuritiesDAO {

    protected Log4j log4j = new Log4j(Constant.ModuleType.SECURITIES, this);

	public SEC_AcceptInsuranceDAO(){
		super("SEC_AcceptInsurance");
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
	    		
		strSQL = " select * from SEC_AcceptInsurance aa "
		    + " where 1=1 "
		    + " and StatusID = " + Constant.RecordStatus.VALID 
			+ " and ApplyFormID = " + lApplyID;
      
		log4j.debug(strSQL);
		
		try {
			prepareStatement(strSQL);
			ResultSet rs = executeQuery();
			while (rs != null && rs.next()) 
			{
			    AcceptInsuranceInfo acceptInsuranceInfo = new AcceptInsuranceInfo ();
				//bidRangeInfo = (AcceptInsuranceInfo) getDataEntityFromResultSet(bidRangeInfo.getClass());
			    acceptInsuranceInfo.setId(rs.getLong("id"));
			    acceptInsuranceInfo.setApplyFormId(rs.getLong("applyFormID"));
			    acceptInsuranceInfo.setInsuranceAcceptorID(rs.getLong("insuranceAcceptorID"));
			    acceptInsuranceInfo.setInsuranceAcceptRate(rs.getDouble("insuranceAcceptRate"));
				
				v.add (acceptInsuranceInfo);
			}
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException("查询申请书下副承保人承保信息产生错误",e);			
		} catch (SQLException e) {
		    throw new SecuritiesDAOException("查询申请书下副承保人承保信息产生错误",e);
		}
		
		try {
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		
		return (v.size () > 0 ? v : null);
	}
	
	public static void main(String[] args)
	{
	     	    
	}
	
}
