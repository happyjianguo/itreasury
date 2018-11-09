/*
 * Created on 2006-12-7
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.evoucher.setting.dao;

import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.evoucher.base.VoucherException;
import com.iss.itreasury.evoucher.setting.dataentity.BillrelationSetInfo;
import com.iss.itreasury.evoucher.setting.dataentity.PrintBillrelationInfo;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;

/**
 * @author zyyao
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ManyPrintBillrelationDao extends PrintDAO
{
	public ManyPrintBillrelationDao()
	{
		super("print_manybillrelation");
	}
	
	//批量打印设置查询已设置打印模板
	public Collection findbyallset(PrintBillrelationInfo pbli)throws VoucherException
	{
		Collection coll = new ArrayList();
		try
		{
			String strSQL = "select id, sbilltypeName, stempName, nmaxprint from print_billrelation where 1=1 " 
				+ " and nofficeid = " + pbli.getNofficeid()
				+ " and ncurrency = " + pbli.getNcurrency()
				+ " and ntransactiontypeid = " + pbli.getNtransactiontypeid()
				+ " and ndeptid = " + pbli.getNdeptid()
				+ " order by stempname ";
			
			System.out.println("SQL====="+strSQL);
			
			initDAO();
			
			prepareStatement(strSQL);
			executeQuery();
			
			coll = getDataEntitiesFromResultSet(PrintBillrelationInfo.class);
			
			finalizeDAO();
		}
		catch(Exception e)
		{
			throw new VoucherException();
		}
		try
		{
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new VoucherException();
		}
		
		return coll;
	}
	
	public void deleteAllset(PrintBillrelationInfo pbli)throws VoucherException
	{
		Collection coll = new ArrayList();
		try
		{
			StringBuffer strSQL = new StringBuffer();
			
			strSQL.append(" delete print_manybillrelation ");
			strSQL.append(" where nofficeid = "+ pbli.getNofficeid() +" and ncurrency = "+ pbli.getNcurrency() +" ");
			strSQL.append(" and nTransactionTypeId ="+pbli.getNtransactiontypeid()+" and nDeptId = "+pbli.getNdeptid()+" ");
			strSQL.append(" and nmoduleid ="+pbli.getModuleID()+" ");
			System.out.println("SQL====="+strSQL.toString());
			
			initDAO();
			
			prepareStatement(strSQL.toString());
			executeUpdate();
			
			finalizeDAO();
		}
		catch(Exception e)
		{
			throw new VoucherException();
		}
		try
		{
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new VoucherException();
		}
		
	}
	
}
