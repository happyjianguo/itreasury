package com.iss.itreasury.loan.bankloan.bizlogic;

import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.loan.bankloan.dao.BankLoanDao;
import com.iss.itreasury.loan.bankloan.dataentity.BankLoanInfo;
import com.iss.itreasury.loan.creditext.bizlogic.BankCreditExtBalanceBiz;
import com.iss.itreasury.loan.util.LOANConstant.BankCreditVariety;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

/**
 * @author yyhe
 * Created on 2006-10-20
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BankLoanBiz {
	
	private Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);
	
	// 判断是否超过了余额
	private boolean isOverBalance(double amount, double originalAmount, long bankCreditExtId, String applyCompanyCode, long variety) throws IException{
		try{
			double balance = new BankCreditExtBalanceBiz().getBalance(bankCreditExtId, applyCompanyCode, variety);
		
			return amount > balance + originalAmount;
		}catch(Exception e){
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		
	}
	
	//判断是否超过了担保额度
	private boolean isOverAssureBalance(double amount, double originalAmount, long bankCreditExtId, String applyCompanyCode, long variety) throws IException{
		try{
	    	double balance = new BankCreditExtBalanceBiz().getAssureBalance(bankCreditExtId, applyCompanyCode, variety);
		
	    	return amount > balance + originalAmount;
		}catch(Exception e){
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
	}
	
	//新增，将info里的信息insert到表loan_BankLoan里
	public long add(BankLoanInfo info) throws IException
	{
		if(isOverBalance(info.getAmount()*info.getExchangeRate(),0,info.getConferContractNo(),info.getLoanClient(),info.getLoanType())){
			return -2;
		}
		if(isOverAssureBalance(info.getAmount()*info.getExchangeRate(),0,info.getConferContractNo(),info.getLoanClient(),info.getLoanType())){
			return -3;
		}
		long lret = -1;
		try
		{
			BankLoanDao dao = new BankLoanDao();
			lret = dao.add(info);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return lret;
	}
	
	//	修改，将info里的信息update到表loan_BankLoan里
	public long modify(BankLoanInfo info,double originalAmount) throws IException
	{	
		if (isOverBalance(info.getAmount()*info.getExchangeRate(),originalAmount,info.getConferContractNo(),info.getLoanClient(),info.getLoanType())) {
			return -2;
		}
		
		if (isOverAssureBalance(info.getAmount()*info.getExchangeRate(),originalAmount,info.getConferContractNo(),info.getLoanClient(),info.getLoanType())) {
			return -3;
		}
		long lret = -1;
		try
		{
			BankLoanDao dao = new BankLoanDao();
			lret = dao.modify(info);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return lret;
	}
	
	//	删除，将info里的信息update到表loan_BankLoan里
	public long delete(long id) throws IException
	{
		long lret = -1;
		try
		{
			BankLoanDao dao = new BankLoanDao();
			lret = dao.delete(id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return lret;
	}
	
	//	查询，按info里的信息查询表loan_BankLoan
	//	返回由BankLoanInfo组成的集合
	public Collection findByCondition(BankLoanInfo info) throws IException
	{
		Vector vret = null;
		try
		{
			BankLoanDao dao = new BankLoanDao();
			vret = dao.findByBankLoan(info);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return vret;
	}
	
	//	查询，按id查询表loan_BankLoan
	//	返回由BankLoanInfo组成的集合
	public BankLoanInfo findByID(long id) throws IException
	{
		BankLoanInfo vret = null;
		try
		{
			BankLoanDao dao = new BankLoanDao();
			vret = dao.findByID(id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return vret;
	}
	
	public PageLoader findAllBankLoan(long officeId) throws IException{
		
		PageLoader loader = null;
		
		try
		{
			BankLoanDao dao = new BankLoanDao();
			loader = dao.findAllBankLoan(officeId);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return loader;
	}
	
	public PageLoader findWithPage(BankLoanInfo info) throws IException{
		
		PageLoader loader = null;
		
		try
		{
			BankLoanDao dao = new BankLoanDao();
			loader = dao.findWithPage(info);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return loader;
	}


	public String getNextCode() throws IException{
		String prefix = "BJBL";
		String newCode = null;
		try{
			BankLoanDao dao = new BankLoanDao();
			newCode = dao.getNextCode(prefix);
		}catch(Exception e){
			log4j.error(e.toString());
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return newCode;
	}
	
	public int getNextId() throws IException{
		int id = 0;
		try{
			BankLoanDao dao = new BankLoanDao();
			id = dao.getNextId();
		}catch(Exception e){
			e.printStackTrace();
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return id;
	}
}
