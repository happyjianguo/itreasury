/*
 * Created on 2006-12-7
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.evoucher.setting.bizlogic;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.evoucher.base.VoucherException;
import com.iss.itreasury.evoucher.setting.dataentity.PrintBillrelationInfo;
import com.iss.itreasury.evoucher.setting.dao.ManyPrintBillrelationDao;
import com.iss.itreasury.evoucher.setting.dao.PrintBillrelationDao;
import com.iss.itreasury.evoucher.util.NameRef;
import com.iss.itreasury.util.Constant;

/**
 * @author boxu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PrintBillrelationBiz 
{
	
	//新增和修改
	public long saveBillrelation(PrintBillrelationInfo pbli)throws VoucherException
	{
		PrintBillrelationDao printdao = new PrintBillrelationDao();
		long lID =-1;
		
		try
		{
			if ( pbli.getId() < 0 )
			{
				try
				{
					printdao.setUseMaxID();  //使用MAX增加ID
					lID = printdao.add(pbli);
				}
				catch(ITreasuryDAOException e)
				{
					throw new VoucherException();
				}
			}
			else
			{
				try
				{
					printdao.update(pbli);
				}
				catch(ITreasuryDAOException e)
				{
					throw new VoucherException();
				}
				lID = pbli.getId();
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
			throw new VoucherException("Gen_E001", e);
		}
		return lID;
	}
	
	//根据业务类型和打印部门查询
	public Collection findbyall(PrintBillrelationInfo pbli)throws VoucherException
	{
		PrintBillrelationDao printdao = new PrintBillrelationDao();
		Collection coll = null;
		
		try
		{
			coll = printdao.findbyallset(pbli);
		}
		catch(Exception e)
		{
			throw new VoucherException();
		}
		
		return coll;
	}
	
	//根据业务类型和打印部门查询删除表中的所有记录
	public void deleteAllset(PrintBillrelationInfo pbli)throws VoucherException
	{
		PrintBillrelationDao printdao = new PrintBillrelationDao();
		
		try
		{
			printdao.deleteAllset(pbli);
		}
		catch(Exception e)
		{
			throw new VoucherException();
		}
	}
	
//	批量打印设置新增和修改
	public long savemanyBillrelation(PrintBillrelationInfo pbli)throws VoucherException
	{
		ManyPrintBillrelationDao printdao = new ManyPrintBillrelationDao();
		long lID =-1;
		
		try
		{
			if ( pbli.getId() < 0 )
			{
				try
				{
					printdao.setUseMaxID();  //使用MAX增加ID
					lID = printdao.add(pbli);
				}
				catch(ITreasuryDAOException e)
				{
					throw new VoucherException();
				}
			}
			else
			{
				try
				{
					printdao.update(pbli);
				}
				catch(ITreasuryDAOException e)
				{
					throw new VoucherException();
				}
				lID = pbli.getId();
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
			throw new VoucherException("Gen_E001", e);
		}
		return lID;
	}
	
	//批量打印设置根据业务类型和打印部门查询
	public Collection findbyallmany(PrintBillrelationInfo pbli)throws VoucherException
	{
		ManyPrintBillrelationDao printdao = new ManyPrintBillrelationDao();
		Collection coll = null;
		
		try
		{
			coll = printdao.findbyallset(pbli);
		}
		catch(Exception e)
		{
			throw new VoucherException();
		}
		
		return coll;
	}
	
	//批量打印设置根据业务类型和打印部门查询删除表中的所有记录
	public void deleteAllsetmany(PrintBillrelationInfo pbli)throws VoucherException
	{
		ManyPrintBillrelationDao printdao = new ManyPrintBillrelationDao();
		
		try
		{
			printdao.deleteAllset(pbli);
		}
		catch(Exception e)
		{
			throw new VoucherException();
		}
	}
	
	//针对于WebSphere服务器读取文件顺序混乱的问题
	public Collection findSomeFileName(PrintBillrelationInfo pbli, String path)throws VoucherException
	{
		Collection coll = null;
		String moudleName="";
		switch ( (int)pbli.getModuleID())
		{
			case (int) Constant.ModuleType.SETTLEMENT:
				moudleName = "settlement";
				break;
			case (int) Constant.ModuleType.LOAN:
				moudleName = "loan";
				break;
			case (int) Constant.ModuleType.SYSTEM:
				moudleName = "system";
				break;
			case (int) Constant.ModuleType.FOREIGN:
				moudleName = "foreign";
				break;
			case (int) Constant.ModuleType.SECURITIES:
				moudleName = "securities";
				break;
			case (int) Constant.ModuleType.EBANK:
				moudleName = "ebank";
				break;
			case (int) Constant.ModuleType.PLAN:
				moudleName = "plan";
				break;
			case (int) Constant.ModuleType.CLIENTCENTER:
				moudleName = "clientcenter";
				break;
			case (int) Constant.ModuleType.GENERALLEDGER:
				moudleName = "generalledger";
				break;
			case (int) Constant.ModuleType.BILL:
				moudleName = "bill";
				break;	
			case (int) Constant.ModuleType.BUDGET:
				moudleName = "budget";
				break;
			case (int) Constant.ModuleType.BANKPORTAL:
				moudleName = "bankportal";
				break;
			case (int) Constant.ModuleType.MANAGER:
				moudleName = "manager";
				break;
			case (int) Constant.ModuleType.CLIENTMANAGE:
				moudleName = "clientmanage";
				break;
			case (int) Constant.ModuleType.CRAFTBROTHER:
				moudleName = "craftbrother";
				break;
			case (int) Constant.ModuleType.EVOUCHER:
				moudleName = "evoucher";
				break;	
		}
		
		try
		{
			ArrayList al = new ArrayList();
			File file = new File(path);
			coll = new ArrayList();
			
			if (file.isDirectory()) 
			{
				String[] strings = file.list();
				for (int i = 0; i < strings.length; i++) 
				{
					if (strings[i].startsWith(moudleName+"_")&& strings[i].endsWith(".xml")) 
					{
						al.add(strings[i]);
					}
				}
				
				//数组排序
				Collections.sort(al);
				
				for (int i = 0; i < al.size(); i++) 
				{
					pbli.setStempName((String)al.get(i));
					
					System.out.println(pbli.getStempName());
					
					PrintBillrelationInfo info = NameRef.getPartByInfo(pbli);
					coll.add(info);
				}
			}	
		}
		catch(Exception e)
		{
			throw new VoucherException();
		}
		
		return coll;
	}
	
}
