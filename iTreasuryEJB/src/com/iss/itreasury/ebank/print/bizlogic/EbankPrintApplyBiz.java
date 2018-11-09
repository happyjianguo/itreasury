/*
 * Created on 2006-12-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.ebank.print.bizlogic;

import java.rmi.RemoteException;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.ebank.print.dao.EbankPrintApplyDao;
import com.iss.itreasury.ebank.print.dataentity.PrintapplyInfo;
import com.iss.itreasury.ebank.print.dataentity.PrintrecordInfo;
import com.iss.itreasury.evoucher.base.VoucherException;
import com.iss.itreasury.evoucher.print.dao.QueryPrintDao;
import com.iss.itreasury.evoucher.print.dataentity.PrintXMLTimeInfo;
import com.iss.itreasury.util.IException;

/**
 * @author boxu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EbankPrintApplyBiz 
{
	//打印申请新增查询已打印记录
	public Collection findAllprint(PrintrecordInfo prinfo)throws RemoteException,Exception
	{
		Collection coll = null;
		
		try
		{
			EbankPrintApplyDao printapplydao = new EbankPrintApplyDao();
			
			coll = printapplydao.findAllprint(prinfo);
		}
		catch(Exception re)
		{
			throw new RemoteException(re.getMessage());
		}
		
		return coll;
	}
	
	//打印申请新增查询已打印记录
	public Collection getPrintDetailByTransID(long lTransID, long officeID, long currencyID)throws RemoteException,Exception 
	{
		Collection coll = null;
		
		try
		{
			EbankPrintApplyDao printapplydao = new EbankPrintApplyDao();
			
			coll = printapplydao.getPrintDetailByTransID(lTransID, officeID, currencyID);
		}
		catch(Exception re)
		{
			throw new RemoteException(re.getMessage());
		}
		
		return coll;
	}
	
	//结算打印接收查看申请单据明细
	public Collection getPrintDetailByTransIDTWO(long lTransID, long officeID, long currencyID)throws RemoteException,Exception 
	{
		Collection coll = null;
		
		try
		{
			EbankPrintApplyDao printapplydao = new EbankPrintApplyDao();
			
			coll = printapplydao.getPrintDetailByTransIDTWO(lTransID, officeID, currencyID);
		}
		catch(Exception re)
		{
			throw new RemoteException(re.getMessage());
		}
		
		return coll;
	}
	
	//保存打印申请
	public void saveApply(PrintapplyInfo applyinfo)throws RemoteException,Exception
	{
		try
		{
			EbankPrintApplyDao printapplydao = new EbankPrintApplyDao();
			if (applyinfo.getId() < 0)
			{
				try
				{
					printapplydao.setUseMaxID();  //使用MAX增加ID
					printapplydao.add(applyinfo);
				}
				catch (ITreasuryDAOException e)
				{
					throw new RemoteException(e.getMessage());
				}
			}
			else
			{
				try
				{
					printapplydao.update(applyinfo);
				}
				catch (ITreasuryDAOException e)
				{
					throw new RemoteException(e.getMessage());
				}
			}
		}
		catch(Exception re)
		{
			throw new RemoteException(re.getMessage());
		}
	}
	 public Collection getPrintOptionsByTransID (String TransIDs , long lDeptID, long lCurrencyID , long lOfficeID, long lModuleID) throws VoucherException
	 {
		 Collection coll = null;
		 EbankPrintApplyDao dao = new EbankPrintApplyDao();
		 
		 try {
			 coll = dao.getPrintOptionsByTransID(TransIDs,lDeptID,lCurrencyID,lOfficeID,lModuleID);
		 } catch (Exception e) { 
			e.printStackTrace();
			throw new VoucherException("Gen_E001", e);
		 }
		 return coll;
	 }
	 
	 //addby zyyao 批量打印 2007-6-28
	 public Collection getPrintTemplateContentmany (String strTransNos, String transIDs , int deptID, long lCurrencyID , long lOfficeID, long lModuleID)throws Exception
	 {
		 Collection resultPrintOption = null;
		 EbankPrintApplyDao dao = new EbankPrintApplyDao();
		 
		 try 
		 {
			 resultPrintOption = dao.getPrintTemplateContentmany(strTransNos,transIDs,deptID,lCurrencyID,lOfficeID,lModuleID);
		 } 
		 catch(Exception e) 
		 {
			 e.printStackTrace();
			 throw new IException(e.getMessage());
		 }
		 
		 return resultPrintOption;
	 }
	 
	 //计算折行数
	 public long countRowNumber(String content,long maxLength)
	 {
		 long rowNumber = 0;
		 int begin = content.indexOf("<b>");
		 int end = content.indexOf("</b>");
		 if(!content.equals(""))
		 {
			 if(begin>-1&&end>-1)
			 {
				 content = content.substring(begin+3, end);
			 }
			 byte[] strContent = content.getBytes();
			 rowNumber = strContent.length/maxLength;
			 if((strContent.length%maxLength)!=0)
			 {
				 rowNumber++;
			 }
			 
		 }
		 else
		 {
			 rowNumber = 1;
		 }
		
		 return rowNumber;
	 }
	 
}
