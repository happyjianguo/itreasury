/*
 * Created on 2004-4-28
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.securities.securitiescontract.dao;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.util.*;
import com.iss.itreasury.securities.securitiescontract.dataentity.*;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.util.*;
import java.util.*;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ContractBondTypeDao extends SecuritiesDAO
{
	protected Log4j log4j = new Log4j(Constant.ModuleType.SECURITIES, this);

	 public ContractBondTypeDao(){
		 super("SEC_CONTRACTFORMBONDTYPE");
	 }

	 /*
	  * 
	  */
	 public Collection findByContractID(long lContractID) throws SecuritiesDAOException {

		 String strSQL = "";
		 Vector v = new Vector ();
        		
		 try {
			 initDAO();
		 } catch (ITreasuryDAOException e) {
			 throw new SecuritiesDAOException(e);
		 }
	    		
		 strSQL = " select * from SEC_ContractFormBondType aa "
			 + " where 1=1 "
			 + " and StatusID = " + Constant.RecordStatus.VALID 
			 + " and ContractFormID = " + lContractID;
      
		 log4j.debug(strSQL);
		
		 try {
			 prepareStatement(strSQL);
			 ResultSet rs = executeQuery();
			 while (rs != null && rs.next()) 
			 {
				 ContractBondTypeInfo bondTypeInfo = new ContractBondTypeInfo ();
				 bondTypeInfo.setId(rs.getLong("id"));
				 bondTypeInfo.setContractFormId(rs.getLong("contractFormID"));
				 bondTypeInfo.setName(rs.getString("name"));
				 bondTypeInfo.setTerm(rs.getLong("term"));
				 bondTypeInfo.setAmount(rs.getDouble("amount"));
				 bondTypeInfo.setRate(rs.getString("rate"));
				
				 v.add (bondTypeInfo);
			 }
		 } catch (ITreasuryDAOException e) {
			 throw new SecuritiesDAOException("查询合同下债券种类产生错误",e);			
		 } catch (SQLException e) {
			 throw new SecuritiesDAOException("查询合同下债券种类产生错误",e);
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
		ContractBondTypeDao dao=new ContractBondTypeDao();
		ContractBondTypeInfo info=new ContractBondTypeInfo();
		
		info.setContractFormId( 1 );
		info.setStatusId(1);
		
		try
		{
			java.util.Collection c=dao.findByContractID( 1 );
			System.out.println(c.size());
		} catch (SecuritiesDAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}
