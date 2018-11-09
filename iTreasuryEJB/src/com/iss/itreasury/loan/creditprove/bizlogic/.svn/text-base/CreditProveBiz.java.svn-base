/*
 * Created on 2006-10-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.loan.creditprove.bizlogic;

import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.loan.creditext.bizlogic.BankCreditExtBalanceBiz;
import com.iss.itreasury.loan.creditprove.dao.CreditProveDao;
import com.iss.itreasury.loan.creditprove.dataentity.CreditProveInfo;
import com.iss.itreasury.loan.query.dao.QueryDao;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.system.bulletin.dataentity.BulletinInfo;
import com.iss.itreasury.system.bulletin.dao.BulletinDao;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;

/**
 * @author yyhe
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CreditProveBiz {
	

	
	//新增信贷证明，将info里的信息insert到表loan_CreditProve里
	public long add(CreditProveInfo info) throws Exception
	{
		long lret = -1;//授信金额超过可用授信余额
		try
		{
			CreditProveDao dao = new CreditProveDao();
			BankCreditExtBalanceBiz getBiz = new BankCreditExtBalanceBiz();//提供金额控制信息,返回值均为折合人民币
			double balance;
			balance = getBiz.getBalance(info.getConferContractNo(),info.getApplyClient(),LOANConstant.BankCreditVariety.PROVEOFCREDIT);
			double assureBalance;
			assureBalance = getBiz.getAssureBalance(info.getConferContractNo(),info.getApplyClient(),LOANConstant.BankCreditVariety.PROVEOFCREDIT);
			if ( balance >= info.getConvertRMB()  )
			{
				lret = -2;//授信金额超超过担保余额
				if (assureBalance >= info.getConvertRMB())
				{
					lret = dao.add(info);
				}
			}		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lret;
	}
	
	//	修改信贷证明，将info里的信息update到表loan_CreditProve里
	public long modify(CreditProveInfo info) throws Exception
	{
		long lret = -1;//授信金额超过可用授信余额
		try
		{
			CreditProveDao dao = new CreditProveDao();
			BankCreditExtBalanceBiz getBiz = new BankCreditExtBalanceBiz();//提供金额控制信息,返回值均为折合人民币
			double balance;
			balance = getBiz.getBalance(info.getConferContractNo(),info.getApplyClient(),LOANConstant.BankCreditVariety.PROVEOFCREDIT);			
			balance += info.getOriginalCoverRMB(); 
			double assureBalance;
			assureBalance = getBiz.getAssureBalance(info.getConferContractNo(),info.getApplyClient(),LOANConstant.BankCreditVariety.PROVEOFCREDIT);			
			assureBalance += info.getOriginalCoverRMB();
			if ( balance >= info.getConvertRMB()  )
			{
				lret = -2;//授信金额超超过担保余额
				if (assureBalance >= info.getConvertRMB())
				{
					lret = dao.modify(info);
				}
			}		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lret;
	}
	
	//	删除信贷证明，将info里的信息update到表loan_CreditProve里
	public long delete(CreditProveInfo info) throws Exception
	{
		long lret = -1;
		try
		{
			CreditProveDao dao = new CreditProveDao();
			lret = dao.delete(info);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lret;
	}
	
	//	查询信贷证明，按info里的信息查询表loan_CreditProve
	//	返回由CreditProveInfo组成的集合
	public Collection findByCondition(CreditProveInfo info) throws Exception
	{
		Vector vret = null;
		try
		{
			CreditProveDao dao = new CreditProveDao();
			vret = dao.findByCreditProve(info);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return vret;
	}
	
	// 产生信贷证明内部流水号
	public String getNextCode() throws Exception
	{
		String newCode ;
		try
		{
			CreditProveDao dao = new CreditProveDao();
			newCode = dao.getNextCode();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return newCode;
	}
	
	//分页查找
	public PageLoader findWithPage (CreditProveInfo info) throws Exception
	{
		PageLoader loader = null;
		
		try
		{
			CreditProveDao dao = new CreditProveDao();
			loader = dao.findWithPage(info);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return loader;
	}
}
