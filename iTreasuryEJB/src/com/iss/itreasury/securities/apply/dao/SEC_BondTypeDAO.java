package com.iss.itreasury.securities.apply.dao;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.util.*;
import com.iss.itreasury.securities.apply.dataentity.*;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.util.*;
import java.util.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SEC_BondTypeDAO extends SecuritiesDAO {

    protected Log4j log4j = new Log4j(Constant.ModuleType.SECURITIES, this);

	public SEC_BondTypeDAO(){
		super("SEC_ApplyFormBondType");
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
	    		
		strSQL = " select * from SEC_ApplyFormBondType aa "
		    + " where 1=1 "
		    + " and StatusID = " + Constant.RecordStatus.VALID 
			+ " and ApplyFormID = " + lApplyID;
      
		log4j.debug(strSQL);
		
		try {
			prepareStatement(strSQL);
			ResultSet rs = executeQuery();
			while (rs != null && rs.next()) 
			{
			    BondTypeInfo bondTypeInfo = new BondTypeInfo ();
				//bidRangeInfo = (BondTypeInfo) getDataEntityFromResultSet(bidRangeInfo.getClass());
			    bondTypeInfo.setId(rs.getLong("id"));
			    bondTypeInfo.setApplyFormId(rs.getLong("applyFormID"));
			    bondTypeInfo.setName(rs.getString("name"));
			    bondTypeInfo.setTerm(rs.getLong("term"));
			    bondTypeInfo.setAmount(rs.getDouble("amount"));
			    bondTypeInfo.setRate(rs.getString("rate"));
				
				v.add (bondTypeInfo);
			}
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException("查询申请书下债券种类产生错误",e);			
		} catch (SQLException e) {
		    throw new SecuritiesDAOException("查询申请书下债券种类产生错误",e);
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
	    SEC_ApplyDAO dao = new SEC_ApplyDAO();
	    ApplyInfo info = new ApplyInfo();
        /*  
        info.setId(0);
        try {
            info.setCode(dao.getApplyCode(1));
        } catch (SecuritiesDAOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Timestamp tsDate = Env.getSystemDateTime();
        info.setTransactionTypeId(1101);
        info.setInputDate(tsDate);
        info.setInputUserId(1);
        info.setStatusId(1);
        try {
            dao.add(info);
        } catch (SecuritiesDAOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
     

        info.setId(42);
        try {
            info.setCode(dao.getApplyCode(1));
        } catch (SecuritiesDAOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Timestamp tsDate = Env.getSystemDateTime();
        info.setTransactionTypeId(1101);
        info.setInputDate(tsDate);
        info.setInputUserId(2);
        info.setStatusId(2);
        try {
            dao.update(info);
        } catch (SecuritiesDAOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }



	    try {
            info = (ApplyInfo)dao.findByID(1,info.getClass());
        } catch (SecuritiesDAOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	    Log.print(info.toString());

 */      

	    ApplyQueryInfo qInfo = new ApplyQueryInfo();
	    Collection c = null;
	    qInfo.setQueryPurpose(2);
	    qInfo.setUserId(1);
	    qInfo.setTransactionTypeId(1301);
	    qInfo.setPageLineCount(100);
	    qInfo.setPageNo(1);
	    try {
            c = dao.findByMultiOption(qInfo);
            Iterator it = c.iterator();
            while (it.hasNext())
            {
                ApplyInfo aInfo = ( ApplyInfo ) it.next();
                Log.print(aInfo.toString());
            }
        } catch (SecuritiesDAOException e) {
            e.printStackTrace();
        }
 	    
	}
	
}
