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
 * @author boxu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PrintBillrelationDao extends PrintDAO
{
	public PrintBillrelationDao()
	{
		super("print_billrelation");
	}
	
	public Collection findbyallset(PrintBillrelationInfo pbli)throws VoucherException
	{
		Collection coll = new ArrayList();
		try
		{
			StringBuffer strSQL = new StringBuffer();
			
			strSQL.append(" select aa.*,bb.countrow from ( ");
			strSQL.append(" select 1 id,a.id setid,a.sname setname,a.nbillsegment billsegment,b.id templateid,b.sdescription templatename,b.ncoupletno templateno, ");
			strSQL.append(" c.id relationid,c.ntransactiontypeid relationtypeid,c.ndeptid relationdeptid,c.nmaxprint relationmax ");
			strSQL.append(" from print_billsetting a,print_billtemplate b,print_billrelation c ");
			strSQL.append(" where c.ntempid(+)=b.id and b.nbillid=a.id ");
			strSQL.append(" and c.ndeptid(+)="+ pbli.getNdeptid() +" and nTransactionTypeId(+)= "+ pbli.getNtransactiontypeid() +" ");
			strSQL.append(" and a.nofficeid = "+ pbli.getNofficeid() +" and a.ncurrency = "+ pbli.getNcurrency() +" and a.nstatusid = 1 ");
			strSQL.append(" and a.nmoduleid = " + pbli.getModuleID() );
			strSQL.append(" and c.nofficeid(+)= "+ pbli.getNofficeid() +" and c.ncurrency(+) = "+ pbli.getNcurrency() +" ) aa, ");
			
			//加入对摸版数量的查询
			strSQL.append(" ( select count(setid) countrow,setid from ( ");
			strSQL.append(" select 1 id,a.id setid,a.sname setname,a.nbillsegment billsegment,b.id templateid,b.sdescription templatename,b.ncoupletno templateno, ");
			strSQL.append(" c.id relationid,c.ntransactiontypeid relationtypeid,c.ndeptid relationdeptid,c.nmaxprint relationmax ");
			strSQL.append(" from print_billsetting a,print_billtemplate b,print_billrelation c ");
			strSQL.append(" where c.ntempid(+)=b.id and b.nbillid=a.id ");
			strSQL.append(" and c.ndeptid(+)="+ pbli.getNdeptid() +" and nTransactionTypeId(+)= "+ pbli.getNtransactiontypeid() +" ");
			strSQL.append(" and a.nofficeid = "+ pbli.getNofficeid() +" and a.ncurrency = "+ pbli.getNcurrency() +" and a.nstatusid = 1 ");
			strSQL.append(" and c.nofficeid(+)= "+ pbli.getNofficeid() +" and c.ncurrency(+) = "+ pbli.getNcurrency() +" ");
			strSQL.append(" ) group by setid ) bb ");
			
			strSQL.append(" where 1=1 and aa.setid = bb.setid order by aa.setid,aa.templateid,aa.templateno ");
			
			System.out.println("SQL====="+strSQL.toString());
			
			initDAO();
			
			prepareStatement(strSQL.toString());
			executeQuery();
			
			coll = getDataEntitiesFromResultSet(BillrelationSetInfo.class);
			
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
			
			strSQL.append(" delete print_billrelation ");
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
