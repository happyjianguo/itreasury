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
public class SEC_InsuranceTypeDAO extends SecuritiesDAO {

    protected Log4j log4j = new Log4j(Constant.ModuleType.SECURITIES, this);

	public SEC_InsuranceTypeDAO(){
		super("SEC_InsuranceType");
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
	    		
		strSQL = " select * from SEC_InsuranceType aa "
		    + " where 1=1 "
		    + " and StatusID = " + Constant.RecordStatus.VALID 
			+ " and ApplyFormID = " + lApplyID;
      
		log4j.debug(strSQL);
		
		try {
			prepareStatement(strSQL);
			ResultSet rs = executeQuery();
			while (rs != null && rs.next()) 
			{
			    InsuranceTypeInfo insuranceTypeInfo = new InsuranceTypeInfo ();
				//bidRangeInfo = (InsuranceTypeInfo) getDataEntityFromResultSet(bidRangeInfo.getClass());
			    insuranceTypeInfo.setId(rs.getLong("id"));
			    insuranceTypeInfo.setApplyFormId(rs.getLong("applyFormID"));
			    insuranceTypeInfo.setName(rs.getString("name"));
			    insuranceTypeInfo.setAmount(rs.getDouble("amount"));
			    insuranceTypeInfo.setRate(rs.getDouble("rate"));
				
				v.add (insuranceTypeInfo);
			}
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException("查询申请书下保险种类产生错误",e);			
		} catch (SQLException e) {
		    throw new SecuritiesDAOException("查询申请书下保险种类产生错误",e);
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