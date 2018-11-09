package com.iss.itreasury.loan.lettercredit.bizlogic;

import java.util.Collection;

import com.iss.itreasury.loan.lettercredit.dao.LetterCreditDAO;
import com.iss.itreasury.loan.lettercredit.dataentity.LetterCreditQueryInfo;
import com.iss.itreasury.loan.lettercredit.dataentity.LetterCreditInfo;
import com.iss.itreasury.loan.lettercredit.dataentity.LetterCreditPaymentInfo;
import com.iss.itreasury.loan.creditext.bizlogic.BankCreditExtBalanceBiz;
import com.iss.itreasury.loan.util.LOANConstant.BankCreditVariety;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;

public class LetterCreditBiz {
	

	// 查询符合条件的信用证信息
	public PageLoader getLetterCreditInfo(LetterCreditQueryInfo info) throws IException {
		PageLoader loader = null;
		try
		{
			LetterCreditDAO dao = new LetterCreditDAO();
			loader = dao.getLetterCreditInfo(info);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return loader;
	}
	
	public PageLoader getAllLetterCredit(long officeId) throws IException{
		PageLoader loader = null;
		try{
			LetterCreditDAO dao = new LetterCreditDAO();
			loader = dao.getAllLetterCredit(officeId);
		}catch(Exception e){
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return loader;
	}
	
	// 得到信用证信息
	public LetterCreditInfo getLetterCreditInfo(String innerCode) throws IException{
		LetterCreditInfo info = null;
		try{
			LetterCreditDAO dao = new LetterCreditDAO();
			info = dao.getLetterCreditInfo(innerCode);
		}catch(Exception e){
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return info;
	}
	
	//	 查询信用证实际付款情况
	public Collection getLetterCreditPaymentInfo(String innerCode) throws IException{
		Collection list = null;
		try{
			LetterCreditDAO dao = new LetterCreditDAO();
			list = dao.getLetterCreditPaymentInfo(innerCode);
		}catch(Exception e){
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return list;
	}
	
	// 新增信用证信息
	public long insert(LetterCreditInfo info) throws IException{
		if (isOverBalance(info.getApplyAmount()*info.getExchangeRate(),0,info.getBankCreditExtId(),info.getApplyCompanyCode())) {
			return -1;
		}
		
		if (isOverAssureBalance(info.getApplyAmount()*info.getExchangeRate(),0,info.getBankCreditExtId(),info.getApplyCompanyCode())) {
			return -2;
		}
		boolean isInserted = false;
		try{
			LetterCreditDAO dao = new LetterCreditDAO();
			isInserted = dao.insert(info);
			if(isInserted){
				return 1;
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return -3;
	}
	
	// 新增信用证实际付款信息
	public long insert(LetterCreditPaymentInfo info) throws IException{
		boolean isInserted = false;
		try{
			LetterCreditDAO dao = new LetterCreditDAO();
			isInserted = dao.insert(info);
			if(isInserted){
				return 1;
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return -3;
	}
	
	// 修改信用证信息
	public long modify(LetterCreditInfo info, double originalAmount) throws IException{
		if (isOverBalance(info.getApplyAmount()*info.getExchangeRate(),originalAmount,info.getBankCreditExtId(),info.getApplyCompanyCode())) {
			return -1;
		}
		
		if (isOverAssureBalance(info.getApplyAmount()*info.getExchangeRate(),originalAmount,info.getBankCreditExtId(),info.getApplyCompanyCode())) {
			return -2;
		}
		boolean isModified = false;
		try{
			LetterCreditDAO dao = new LetterCreditDAO();
			isModified = dao.modify(info);
			if(isModified){
				return 1;
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return -3;
	}
		
	// 判断是否超过了余额
	private boolean isOverBalance(double amount, double originalAmount, long bankCreditExtId, String applyCompanyCode) throws IException{
		try{
			double balance = new BankCreditExtBalanceBiz().getBalance(bankCreditExtId, applyCompanyCode, BankCreditVariety.LETTEROFCREDIT);
		
			return amount > balance + originalAmount;
		}catch(Exception e){
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		
	}
	
	//判断是否超过了担保额度
	private boolean isOverAssureBalance(double amount, double originalAmount, long bankCreditExtId, String applyCompanyCode) throws IException{
		try{
	    	double balance = new BankCreditExtBalanceBiz().getAssureBalance(bankCreditExtId, applyCompanyCode, BankCreditVariety.LETTEROFCREDIT);
		
	    	return amount > balance + originalAmount;
		}catch(Exception e){
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
	}
	
	// 删除信用证信息
	public long delete(String innerCode) throws IException{
		boolean isDeleted = false;
		boolean deletePayment = false;
		try{
			LetterCreditDAO dao = new LetterCreditDAO();
			isDeleted = dao.delete(innerCode);
			deletePayment = dao.deletePayment(innerCode);
			if(isDeleted && deletePayment){
				return 1;
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		
		return -3;
	}
	
	// 删除信用证实际付款信息
	public long deletePayment(String[] innerCode) throws IException{
		boolean isDeleted = false;
		try{
			LetterCreditDAO dao = new LetterCreditDAO();
			isDeleted = dao.deletePayment(innerCode);
			if(isDeleted){
				return 1;
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return -3;
	}
	
	public String getNextCode() throws IException{
		String prefix = "BJLC";
		String newCode = null;
		try{
			LetterCreditDAO dao = new LetterCreditDAO();
			newCode = dao.getNextCode(prefix);
		}catch(Exception e){
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return newCode;
	}
}
