package com.iss.itreasury.loan.loanletterguarantee.bizlogic;

import java.util.Collection;

import com.iss.itreasury.loan.capitalprove.dao.CapitalProveDao;
import com.iss.itreasury.loan.capitalprove.dataentity.CapitalProveInfo;
import com.iss.itreasury.loan.creditext.bizlogic.BankCreditExtBalanceBiz;
import com.iss.itreasury.loan.loanletterguarantee.dao.LetterGuaranteeDao;
import com.iss.itreasury.loan.loanletterguarantee.dataentity.LetterGuaranteeInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

public class LetterGuaranteeBiz {
	private Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);
	
	//  新增，将info里的信息insert到表loan_Letter_Guarantee里
	public long add(LetterGuaranteeInfo info) throws IException
	{
		long lret = -1;
		try
		{
			LetterGuaranteeDao dao = new LetterGuaranteeDao();
			BankCreditExtBalanceBiz getBiz = new BankCreditExtBalanceBiz();//提供金额控制信息,返回值均为折合人民币
			double balance;
			balance = getBiz.getBalance(info.getNBankCreditExtId(),info.getSApplyCompanyCode(),LOANConstant.BankCreditVariety.LETTEROFIGUARANTEE);		
			double assureBalance;
			assureBalance = getBiz.getAssureBalance(info.getNBankCreditExtId(),info.getSApplyCompanyCode(),LOANConstant.BankCreditVariety.LETTEROFIGUARANTEE);
			//modified by fxzhang 2006-12-30
			
//			if ( balance >= (info.getMAmount()*info.getMExchangeRate()) && assureBalance >= (info.getMAmount()*info.getMExchangeRate()))
//			{
//				lret = dao.add(info);
//				lret = dao.getMaxID();
//			}
			if ( balance < (info.getMAmount()*info.getMExchangeRate()))             //授信余额不够
			{
				lret = -2;
			}
			else if ( assureBalance < (info.getMAmount()*info.getMExchangeRate()))       //担保余额不够
			{
				lret = -1;				
			}
			else 
			{
				lret = dao.add(info);
				lret = dao.getMaxID();
			}
			
		}
		catch(Exception e)
		{
			log4j.error(e.toString());
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lret;
	}
	
	//	修改，将info里的信息update到表loan_Letter_Guarantee里
	public long modify(LetterGuaranteeInfo info) throws IException
	{
		long lret = -1;
		try
		{	
			LetterGuaranteeDao dao = new LetterGuaranteeDao();
			BankCreditExtBalanceBiz getBiz = new BankCreditExtBalanceBiz();//提供金额控制信息,返回值均为折合人民币
			double balance;
			balance = getBiz.getBalance(info.getNBankCreditExtId(),info.getSApplyCompanyCode(),LOANConstant.BankCreditVariety.LETTEROFIGUARANTEE);
			balance += info.getOriginalCoverRMB(); 
			double assureBalance;
			assureBalance = getBiz.getAssureBalance(info.getNBankCreditExtId(),info.getSApplyCompanyCode(),LOANConstant.BankCreditVariety.LETTEROFIGUARANTEE);
			assureBalance += info.getOriginalCoverRMB();
			//modefied by fxzhang 2006-12-30
//			if ( balance >= (info.getMAmount()*info.getMExchangeRate()) && assureBalance >= (info.getMAmount()*info.getMExchangeRate()))
//			{
//				lret = dao.modify(info);
//			}
			if ( balance < (info.getMAmount()*info.getMExchangeRate()))             //授信余额不够
			{
				lret = -2;
			}
			else if ( assureBalance < (info.getMAmount()*info.getMExchangeRate()))       //担保余额不够
			{
				lret = -1;				
			}
			else 
			{
				lret = dao.modify(info);
			}
		}
		catch(Exception e)
		{
			log4j.error(e.toString());
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lret;
	}
	
	//	删除
	public long delete(long id) throws IException
	{
		long lret = -1;
		try
		{
			LetterGuaranteeDao dao = new LetterGuaranteeDao();
			lret = dao.delete(id);
		}
		catch(Exception e)
		{
			log4j.error(e.toString());
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lret;
	}
	
	//根据ID查询
	public LetterGuaranteeInfo findByID(long id) throws IException{
		LetterGuaranteeInfo cInfo = null;
		try{
			LetterGuaranteeDao dao = new LetterGuaranteeDao();
			cInfo = dao.findByID(id);
		}catch(Exception e){
			log4j.error(e.toString());
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return cInfo;
	}
	
	//获得所有的信息记录
	public Collection findAllCapitalProve() throws IException{
		Collection cret = null;
		try{
			LetterGuaranteeDao dao = new LetterGuaranteeDao();
			cret = dao.findAll();
		}catch(Exception e){
			log4j.error(e.toString());
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return cret;
	}
	
	
	public PageLoader findWithPage(LetterGuaranteeInfo info) throws IException{
		
		PageLoader loader = null;
		
		try
		{
			LetterGuaranteeDao dao = new LetterGuaranteeDao();
			loader = dao.findWithPage(info);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return loader;
	}
	
	public PageLoader findAll(long officeId) throws IException{
		
		PageLoader loader = null;
		
		try
		{
			LetterGuaranteeDao dao = new LetterGuaranteeDao();
			loader = dao.findAll(officeId);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return loader;
	}
	
	public String getNextCode() throws IException{
		String prefix = "BJLG";
		String newCode = null;
		try{
			LetterGuaranteeDao dao = new LetterGuaranteeDao();
			newCode = dao.getNextCode(prefix);
		}catch(Exception e){
			log4j.error(e.toString());
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return newCode;
	}
	
}
