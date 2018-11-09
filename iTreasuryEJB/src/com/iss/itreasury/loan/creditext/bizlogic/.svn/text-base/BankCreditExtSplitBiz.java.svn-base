package com.iss.itreasury.loan.creditext.bizlogic;

import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;


import com.iss.itreasury.loan.creditext.dao.BankCreditExtAllotDAO;
import com.iss.itreasury.loan.creditext.dao.BankCreditExtSplitDAO;
import com.iss.itreasury.loan.util.*;
import com.iss.itreasury.loan.creditext.dataentity.BankCreditExtSplitInfo;import com.iss.itreasury.util.IException;
;

/**
* 银行授信分解与调整
* @author mayongming
* @version 1.0
*/
public class BankCreditExtSplitBiz {
	//通过授信年度、授信合同号查找授信合同号id，为下一步查询做准备
	public long getContractId (String year,String contractNo,long officeId) throws Exception 
	{
		long lret = -1;
		try
		{
			BankCreditExtSplitDAO dao = new BankCreditExtSplitDAO();
			lret = dao.getContractId(year,contractNo,officeId);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lret;
	}
	//通过授信年度、授信合同号和结算中心代码查询该授信已经分解到各结算中心的情况
	//v001，进入
	public Collection getBankCreditSplit(long id, long officeId) throws Exception
	{
		Vector vret = null;
		try
		{
			BankCreditExtSplitDAO dao = new BankCreditExtSplitDAO();
			vret = dao.getBankCreditAllot(id,officeId);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return vret;
	}
	
	//通过银行授信ID、授信品种和成员单位代码查询授信分配信息
	//v002点击金额进入
	public BankCreditExtSplitInfo getBankCreditSplitInfo(long id, long variety, String companyCode, String companyName,long officeId) throws Exception
	{
		BankCreditExtSplitInfo info = null;
		try
		{
			BankCreditExtSplitDAO dao = new BankCreditExtSplitDAO();
			info = dao.getBankCreditSplitInfo( id,variety,companyCode,companyCode,officeId);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return info;
	}
	
	//删除授信分解记录，删除之前要判断分配给该成员单位的授信品种是否已经使用
	//v003,在详细信息当中，点击删除
	public String delete(long contractNoid, long variety, String companyCode,long lastModifier, String lastModifyDate) throws Exception
	{
		String lret = "isUsed"; 
		boolean isUsed = true;
		boolean isDele = false;
		try
		{
			BankCreditExtSplitDAO dao = new BankCreditExtSplitDAO();
			isUsed = isUsed(contractNoid,variety,companyCode);
			if(!isUsed)//
			{
				lret = "failed";
				isDele = dao.delete(contractNoid,variety,companyCode,lastModifier,lastModifyDate);
				if(isDele)
				{
					lret = "success";
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
	
	
	//判断分解到各成员单位的授信是是否可以删除
	//true 是不可以删除，false可以删除
	private boolean isUsed(long contractNoid, long variety, String companyCode) throws Exception 
	{
		boolean lret = true ;//默认已经使用
		BankCreditExtBalanceBiz getBiz = new BankCreditExtBalanceBiz();//提供金额控制信息,返回值均为折合人民币
		long[] varieties = new long [5];//getBankCreditExtBalanceBiz的参数
		double[] amounts = new double[5];//getBankCreditExtBalanceBiz的参数
		double samount = 0;//getBankCreditExtBalanceBiz的结果
				
		double splitAmountCREDIT = 0;//信用证分解金额；
		double splitAmountGUARANTEE = 0;//保函分解金额
		double splitAmountPROVE = 0; //信贷证明分解金额
		double splitAmountACCEPTBILL = 0;//承兑汇票分解金额
		double splitAmountSHORT = 0; //短期贷款分解金额
		double splitAmountLONG = 0; //长期贷款分解金额
		
		double hasUsedAmountCREDIT = 0 ;//信用证已经使用的金额
		double hasUsedAmountGUARANTEE = 0 ;//保函已使用金额
		double hasUsedAmountPROVE = 0 ;//信贷证明已使用金额
		double hasUsedAmountACCEPTBILL = 0 ;//承兑汇票已使用金额
		double hasUsedAmountSHORT = 0 ;//短期贷款已使用金额
		double hasUsedAmountLONG = 0 ;//长期贷款已使用金额
		
		double AmountCREDIT = 0 ;//信用证已经使用的混合授信金额
		double AmountGUARANTEE = 0 ;//保函已使用混合授信金额
		double AmountPROVE = 0 ;//信贷证明已使用混合授信金额
		double AmountACCEPTBILL = 0 ;//承兑汇票已使用混合授信金额
		double AmountSHORT = 0 ;//短期贷款已使用混合授信金额
		double AmountLONG = 0 ;//长期贷款已使用混合授信金额
		
		splitAmountCREDIT = getBiz.getSplitedListAmount(contractNoid,companyCode,LOANConstant.BankCreditVariety.LETTEROFCREDIT);
		splitAmountGUARANTEE = getBiz.getSplitedListAmount(contractNoid,companyCode,LOANConstant.BankCreditVariety.LETTEROFIGUARANTEE);
		splitAmountPROVE = getBiz.getSplitedListAmount(contractNoid,companyCode,LOANConstant.BankCreditVariety.PROVEOFCREDIT);
		splitAmountACCEPTBILL = getBiz.getSplitedListAmount(contractNoid,companyCode,LOANConstant.BankCreditVariety.ACCEPTBILL);
		splitAmountSHORT = getBiz.getSplitedListAmount(contractNoid,companyCode,LOANConstant.BankCreditVariety.SHORTTERMLOAN);
		splitAmountLONG = getBiz.getSplitedListAmount(contractNoid,companyCode,LOANConstant.BankCreditVariety.LONGTERMLOAN);		
		
		hasUsedAmountCREDIT = getBiz.getUsedLetterCredit(contractNoid,companyCode);
		hasUsedAmountGUARANTEE = getBiz.getUsedLetterGuarantee(contractNoid,companyCode);
		hasUsedAmountPROVE = getBiz.getUsedCreditProve(contractNoid,companyCode);
		hasUsedAmountACCEPTBILL = getBiz.getUsedAcceptBill(contractNoid,companyCode);
		hasUsedAmountSHORT = getBiz.getUsedShortTermLoan(contractNoid,companyCode);
		hasUsedAmountLONG = getBiz.getUsedLongTermLoan(contractNoid,companyCode);
		
		AmountCREDIT = (splitAmountCREDIT - hasUsedAmountCREDIT) >=0 ? 0 : (hasUsedAmountCREDIT - splitAmountCREDIT) ;
		AmountGUARANTEE = (splitAmountGUARANTEE - hasUsedAmountGUARANTEE) >=0 ? 0 : (hasUsedAmountGUARANTEE - splitAmountGUARANTEE) ;
		AmountPROVE = (splitAmountPROVE - hasUsedAmountPROVE) >=0 ? 0 : (hasUsedAmountPROVE - splitAmountPROVE) ;
		AmountACCEPTBILL = (splitAmountACCEPTBILL - hasUsedAmountACCEPTBILL) >=0 ? 0 : (hasUsedAmountACCEPTBILL - splitAmountACCEPTBILL) ;
		AmountSHORT = (splitAmountSHORT - hasUsedAmountSHORT) >=0 ? 0 : (hasUsedAmountSHORT - splitAmountSHORT) ;
		AmountLONG = (splitAmountLONG - hasUsedAmountLONG) >=0 ? 0 : (hasUsedAmountLONG - splitAmountLONG) ;
		
		try
		{
			switch ((int)variety)
			{
			case (int) LOANConstant.BankCreditVariety.MIXEDBANKCREDIT://混用额度
				double amount = AmountCREDIT + AmountGUARANTEE + AmountPROVE + AmountACCEPTBILL + AmountSHORT + AmountLONG;
				if ( amount == 0)
				{
					lret = false;
				}				
				break;
			case (int) LOANConstant.BankCreditVariety.LETTEROFCREDIT: //信用证
			
				varieties[0] = LOANConstant.BankCreditVariety.LETTEROFIGUARANTEE;
				varieties[1] = LOANConstant.BankCreditVariety.PROVEOFCREDIT;
				varieties[2] = LOANConstant.BankCreditVariety.ACCEPTBILL;
				varieties[3] = LOANConstant.BankCreditVariety.SHORTTERMLOAN;
				varieties[4] = LOANConstant.BankCreditVariety.LONGTERMLOAN;
				amounts[0] = AmountGUARANTEE;
				amounts[1] = AmountPROVE;
				amounts[2] = AmountACCEPTBILL;
				amounts[3] = AmountSHORT;
				amounts[4] = AmountLONG;
				samount = getBiz.getMixBalance(contractNoid,companyCode,variety,varieties,amounts);
				if ( samount- hasUsedAmountCREDIT >=0)
				{
					lret = false;
				}
				break;
			case (int) LOANConstant.BankCreditVariety.LETTEROFIGUARANTEE://保函
				
				varieties[0] = LOANConstant.BankCreditVariety.LETTEROFCREDIT;
				varieties[1] = LOANConstant.BankCreditVariety.PROVEOFCREDIT;
				varieties[2] = LOANConstant.BankCreditVariety.ACCEPTBILL;
				varieties[3] = LOANConstant.BankCreditVariety.SHORTTERMLOAN;
				varieties[4] = LOANConstant.BankCreditVariety.LONGTERMLOAN;
				amounts[0] = AmountCREDIT;
				amounts[1] = AmountPROVE;
				amounts[2] = AmountACCEPTBILL;
				amounts[3] = AmountSHORT;
				amounts[4] = AmountLONG;
				samount = getBiz.getMixBalance(contractNoid,companyCode,variety,varieties,amounts);
				if ( samount- hasUsedAmountGUARANTEE >=0)
				{
					lret = false;
				}
				
				break;
			case (int) LOANConstant.BankCreditVariety.PROVEOFCREDIT://信贷证明
			
				varieties[0] = LOANConstant.BankCreditVariety.LETTEROFCREDIT;
				varieties[1] = LOANConstant.BankCreditVariety.LETTEROFIGUARANTEE;
				varieties[2] = LOANConstant.BankCreditVariety.ACCEPTBILL;
				varieties[3] = LOANConstant.BankCreditVariety.SHORTTERMLOAN;
				varieties[4] = LOANConstant.BankCreditVariety.LONGTERMLOAN;
			
				amounts[0] = AmountCREDIT;
				amounts[1] = AmountGUARANTEE;
				amounts[2] = AmountACCEPTBILL;
				amounts[3] = AmountSHORT;
				amounts[4] = AmountLONG;
				samount = getBiz.getMixBalance(contractNoid,companyCode,variety,varieties,amounts);
				if ( samount- hasUsedAmountPROVE >=0)
				{
					lret = false;
				}
				break;
			case (int) LOANConstant.BankCreditVariety.ACCEPTBILL://承兑汇票
				
				varieties[0] = LOANConstant.BankCreditVariety.LETTEROFCREDIT;
				varieties[1] = LOANConstant.BankCreditVariety.LETTEROFIGUARANTEE;
				varieties[2] = LOANConstant.BankCreditVariety.PROVEOFCREDIT;
				varieties[3] = LOANConstant.BankCreditVariety.SHORTTERMLOAN;
				varieties[4] = LOANConstant.BankCreditVariety.LONGTERMLOAN;
				
				amounts[0] = AmountCREDIT;
				amounts[1] = AmountGUARANTEE;
				amounts[2] = AmountPROVE;
				amounts[3] = AmountSHORT;
				amounts[4] = AmountLONG;
				samount = getBiz.getMixBalance(contractNoid,companyCode,variety,varieties,amounts);
				if ( samount- hasUsedAmountACCEPTBILL >=0)
				{
					lret = false;
				}
				
				break;
			case (int) LOANConstant.BankCreditVariety.SHORTTERMLOAN://短期贷款
				varieties[0] = LOANConstant.BankCreditVariety.LETTEROFCREDIT;
				varieties[1] = LOANConstant.BankCreditVariety.LETTEROFIGUARANTEE;
				varieties[2] = LOANConstant.BankCreditVariety.PROVEOFCREDIT;
				varieties[3] = LOANConstant.BankCreditVariety.ACCEPTBILL;
				varieties[4] = LOANConstant.BankCreditVariety.LONGTERMLOAN;
				amounts[0] = AmountCREDIT;
				amounts[1] = AmountGUARANTEE;
				amounts[2] = AmountPROVE;
				amounts[3] = AmountACCEPTBILL;
				amounts[4] = AmountLONG;
				samount = getBiz.getMixBalance(contractNoid,companyCode,variety,varieties,amounts);
				if ( samount- hasUsedAmountSHORT >=0)
				{
					lret = false;
				}
				
				break;
			case (int) LOANConstant.BankCreditVariety.LONGTERMLOAN://长期贷款
				
				varieties[0] = LOANConstant.BankCreditVariety.LETTEROFCREDIT;
				varieties[1] = LOANConstant.BankCreditVariety.LETTEROFIGUARANTEE;
				varieties[2] = LOANConstant.BankCreditVariety.PROVEOFCREDIT;
				varieties[3] = LOANConstant.BankCreditVariety.ACCEPTBILL;
				varieties[4] = LOANConstant.BankCreditVariety.SHORTTERMLOAN;
				amounts[0] = AmountCREDIT;
				amounts[1] = AmountGUARANTEE;
				amounts[2] = AmountPROVE;
				amounts[3] = AmountACCEPTBILL;
				amounts[4] = AmountSHORT;
				samount = getBiz.getMixBalance(contractNoid,companyCode,variety,varieties,amounts);
				if ( samount- hasUsedAmountLONG >=0)
				{
					lret = false;
				}
				
				break;			
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lret;
	}
	
	//	得到某具体品种的风险系数
	private double getRiskcoefficient(long contractNoid, long variety) throws Exception 
	{
		double lret ;
		try
		{
			BankCreditExtSplitDAO dao = new BankCreditExtSplitDAO();
			lret = dao.getRiskcoefficient(contractNoid,variety);							
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lret;
	}
	
	//新增银行授信分解记录，需要判断以下几点：
	//1.判断分解信息中是否已经包含了该种授信品种的记录，如果包含则不能改变授信品种
	//2.判断该种授信品种的分配金额是否大于该品种的余额，如果大于则不能新增
	public String insert(BankCreditExtSplitInfo info) throws Exception
	{
		String lret = "exist";
		boolean exist = true;//判断是否存在
		boolean isOverBalance = true;//是否超过余额
		boolean isInsert = false;//是否插入成功
		try
		{
			BankCreditExtSplitDAO dao = new BankCreditExtSplitDAO();
			//判断该品种是否已经分配给了该结算中心
			exist = dao.exist(info.getId(),info.getVariety(),info.getCompanyCode());
			if(!exist)
			{
				lret = "inOver";
				//若没有分配给该结算中心,则看是否超过余额
				isOverBalance = isOverBalance(info.getAmount(),info.getId(),info.getVariety(),info.getOfficeId(),null);
				if(!isOverBalance)
				{
					//如果没有超过余额就进行添加信息
					lret = "InsertFailed";
					isInsert = dao.insert(info);
					if(isInsert)
					{
						lret = "InsertSuccess";
					}
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
	
	//通过授信合同号得到授信信息
	public BankCreditExtSplitInfo getBankCreditInfo(long contractNoid , long officeId ) throws Exception 
	{
		BankCreditExtSplitInfo info = null;
		try
		{
			BankCreditExtSplitDAO dao = new BankCreditExtSplitDAO();
			info = dao.getBankCreditInfo(contractNoid);
			info.setBalances ( dao.getAllBalance(contractNoid,officeId) );
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		
		return info;
	}
	
	//得到该结算中心下所有授信品种的授信金额币种
	public  HashMap getAllCurrencytype (long contractNoid , long officeId ) throws Exception 
	{
		HashMap hm = null;
		try
		{
			BankCreditExtSplitDAO dao = new BankCreditExtSplitDAO();
			hm = dao.getAllCurrencytype(contractNoid,officeId);			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return hm;
	}
	//修改银行授信分解记录，需要判断以下几点：
	//授信分解信息修改后，要判断该种授信品种的分配金额是否大于该品种的余额，如果大于则不能被修改
	public String modify(BankCreditExtSplitInfo info) throws Exception
	{
		String lret = "notEnough";//默认分解金额大于余额
		
		boolean isOverBalance = true; //是否超过余额
		boolean isModify = false; //是否修改成功
		
		double needAmount = -1; //计算所需金额

		long[] varieties = new long [5];
		double[] amounts = new double[5];
		double riskcoefficient = 0; //要修改的某品种的风险系数
		
		double splitAmountCREDIT = 0;//信用证分解金额；
		double splitAmountGUARANTEE = 0;//保函分解金额
		double splitAmountPROVE = 0; //信贷证明分解金额
		double splitAmountACCEPTBILL = 0;//承兑汇票分解金额
		double splitAmountSHORT = 0; //短期贷款分解金额
		double splitAmountLONG = 0; //长期贷款分解金额
		
		double hasUsedAmountCREDIT = 0 ;//信用证已经使用的金额
		double hasUsedAmountGUARANTEE = 0 ;//保函已使用金额
		double hasUsedAmountPROVE = 0 ;//信贷证明已使用金额
		double hasUsedAmountACCEPTBILL = 0 ;//承兑汇票已使用金额
		double hasUsedAmountSHORT = 0 ;//短期贷款已使用金额
		double hasUsedAmountLONG = 0 ;//长期贷款已使用金额
		
		double AmountCREDIT = 0 ;//信用证已经使用的混合授信金额
		double AmountGUARANTEE = 0 ;//保函已使用混合授信金额
		double AmountPROVE = 0 ;//信贷证明已使用混合授信金额
		double AmountACCEPTBILL = 0 ;//承兑汇票已使用混合授信金额
		double AmountSHORT = 0 ;//短期贷款已使用混合授信金额
		double AmountLONG = 0 ;//长期贷款已使用混合授信金额
		try
		{
			BankCreditExtBalanceBiz getBiz = new BankCreditExtBalanceBiz();//提供金额控制信息,返回值均为折合人民币
			BankCreditExtSplitDAO dao = new BankCreditExtSplitDAO();
			
			riskcoefficient = getRiskcoefficient(info.getId(),info.getVariety());
			
			splitAmountCREDIT = getBiz.getSplitedListAmount(info.getId(),info.getCompanyCode(),LOANConstant.BankCreditVariety.LETTEROFCREDIT);
			splitAmountGUARANTEE = getBiz.getSplitedListAmount(info.getId(),info.getCompanyCode(),LOANConstant.BankCreditVariety.LETTEROFIGUARANTEE);
			splitAmountPROVE = getBiz.getSplitedListAmount(info.getId(),info.getCompanyCode(),LOANConstant.BankCreditVariety.PROVEOFCREDIT);
			splitAmountACCEPTBILL = getBiz.getSplitedListAmount(info.getId(),info.getCompanyCode(),LOANConstant.BankCreditVariety.ACCEPTBILL);
			splitAmountSHORT = getBiz.getSplitedListAmount(info.getId(),info.getCompanyCode(),LOANConstant.BankCreditVariety.SHORTTERMLOAN);
			splitAmountLONG = getBiz.getSplitedListAmount(info.getId(),info.getCompanyCode(),LOANConstant.BankCreditVariety.LONGTERMLOAN);		
			
			hasUsedAmountCREDIT = getBiz.getUsedLetterCredit(info.getId(),info.getCompanyCode());
			hasUsedAmountGUARANTEE = getBiz.getUsedLetterGuarantee(info.getId(),info.getCompanyCode());
			hasUsedAmountPROVE = getBiz.getUsedCreditProve(info.getId(),info.getCompanyCode());
			hasUsedAmountACCEPTBILL = getBiz.getUsedAcceptBill(info.getId(),info.getCompanyCode());
			hasUsedAmountSHORT = getBiz.getUsedShortTermLoan(info.getId(),info.getCompanyCode());
			hasUsedAmountLONG = getBiz.getUsedLongTermLoan(info.getId(),info.getCompanyCode());
			
			AmountCREDIT = (splitAmountCREDIT - hasUsedAmountCREDIT) >=0 ? 0 : (hasUsedAmountCREDIT - splitAmountCREDIT) ;
			AmountGUARANTEE = (splitAmountGUARANTEE - hasUsedAmountGUARANTEE) >=0 ? 0 : (hasUsedAmountGUARANTEE - splitAmountGUARANTEE) ;
			AmountPROVE = (splitAmountPROVE - hasUsedAmountPROVE) >=0 ? 0 : (hasUsedAmountPROVE - splitAmountPROVE) ;
			AmountACCEPTBILL = (splitAmountACCEPTBILL - hasUsedAmountACCEPTBILL) >=0 ? 0 : (hasUsedAmountACCEPTBILL - splitAmountACCEPTBILL) ;
			AmountSHORT = (splitAmountSHORT - hasUsedAmountSHORT) >=0 ? 0 : (hasUsedAmountSHORT - splitAmountSHORT) ;
			AmountLONG = (splitAmountLONG - hasUsedAmountLONG) >=0 ? 0 : (hasUsedAmountLONG - splitAmountLONG) ;
			
			double lastAmount = info.getAmount()* info.getExchangeRate()/riskcoefficient * 100;//修改后的金额(折算到可以使用的人民币)
			double samount = 0;//getBiz.getMixBalance的结果
			//具体某品种修改的时候,判断,samount+lastAmount-该品种已使用的金额为需要金额,要大于0
			
			switch ((int)info.getVariety())
			{
			case (int) LOANConstant.BankCreditVariety.MIXEDBANKCREDIT://混用额度						 
				//此处needAmount>0才可以修改,即修改后的金额减去其他品种已使用的混合余额之和	
				long[] varietiesmix = new long [6];
				double[] amountsmix = new double[6];
				varietiesmix[0] = LOANConstant.BankCreditVariety.LETTEROFCREDIT; //信用证
				varietiesmix[1] = LOANConstant.BankCreditVariety.LETTEROFIGUARANTEE;//保函
				varietiesmix[2] = LOANConstant.BankCreditVariety.PROVEOFCREDIT;//信贷证明
				varietiesmix[3] = LOANConstant.BankCreditVariety.ACCEPTBILL;//承兑汇票
				varietiesmix[4] = LOANConstant.BankCreditVariety.SHORTTERMLOAN;//短期贷款
				varietiesmix[5] = LOANConstant.BankCreditVariety.LONGTERMLOAN;//长期贷款
				amountsmix[0] = AmountCREDIT;
				amountsmix[1] = AmountGUARANTEE;
				amountsmix[2] = AmountPROVE;
				amountsmix[3] = AmountACCEPTBILL;
				amountsmix[4] = AmountSHORT;
				amountsmix[5] = AmountLONG;
				
				//以下是已经使用的混用额度
				double usedMixAmount = getBiz.getUsedMixAmount(info.getId(), info.getCompanyCode(), varietiesmix,amountsmix); 
				needAmount = info.getAmount()*info.getExchangeRate() - usedMixAmount;
				break;

			case (int) LOANConstant.BankCreditVariety.LETTEROFCREDIT: //信用证
				
				varieties[0] = LOANConstant.BankCreditVariety.LETTEROFIGUARANTEE;
				varieties[1] = LOANConstant.BankCreditVariety.PROVEOFCREDIT;
				varieties[2] = LOANConstant.BankCreditVariety.ACCEPTBILL;
				varieties[3] = LOANConstant.BankCreditVariety.SHORTTERMLOAN;
				varieties[4] = LOANConstant.BankCreditVariety.LONGTERMLOAN;
				amounts[0] = AmountGUARANTEE;
				amounts[1] = AmountPROVE;
				amounts[2] = AmountACCEPTBILL;
				amounts[3] = AmountSHORT;
				amounts[4] = AmountLONG;
				samount = getBiz.getMixBalance(info.getId(),info.getCompanyCode(),info.getVariety(),varieties,amounts);
				needAmount = lastAmount + samount- hasUsedAmountCREDIT ;

				break;
			case (int) LOANConstant.BankCreditVariety.LETTEROFIGUARANTEE://保函
				
				varieties[0] = LOANConstant.BankCreditVariety.LETTEROFCREDIT;
				varieties[1] = LOANConstant.BankCreditVariety.PROVEOFCREDIT;
				varieties[2] = LOANConstant.BankCreditVariety.ACCEPTBILL;
				varieties[3] = LOANConstant.BankCreditVariety.SHORTTERMLOAN;
				varieties[4] = LOANConstant.BankCreditVariety.LONGTERMLOAN;
				amounts[0] = AmountCREDIT;
				amounts[1] = AmountPROVE;
				amounts[2] = AmountACCEPTBILL;
				amounts[3] = AmountSHORT;
				amounts[4] = AmountLONG;
				samount = getBiz.getMixBalance(info.getId(),info.getCompanyCode(),info.getVariety(),varieties,amounts);
				needAmount = lastAmount + samount- hasUsedAmountGUARANTEE ;
				
				break;
			case (int) LOANConstant.BankCreditVariety.PROVEOFCREDIT://信贷证明
				
				varieties[0] = LOANConstant.BankCreditVariety.LETTEROFCREDIT;
				varieties[1] = LOANConstant.BankCreditVariety.LETTEROFIGUARANTEE;
				varieties[2] = LOANConstant.BankCreditVariety.ACCEPTBILL;
				varieties[3] = LOANConstant.BankCreditVariety.SHORTTERMLOAN;
				varieties[4] = LOANConstant.BankCreditVariety.LONGTERMLOAN;
			
				amounts[0] = AmountCREDIT;
				amounts[1] = AmountGUARANTEE;
				amounts[2] = AmountACCEPTBILL;
				amounts[3] = AmountSHORT;
				amounts[4] = AmountLONG;
				samount = getBiz.getMixBalance(info.getId(),info.getCompanyCode(),info.getVariety(),varieties,amounts);
				needAmount = lastAmount + samount- hasUsedAmountPROVE ;

				break;
			case (int) LOANConstant.BankCreditVariety.ACCEPTBILL://承兑汇票
				
				varieties[0] = LOANConstant.BankCreditVariety.LETTEROFCREDIT;
				varieties[1] = LOANConstant.BankCreditVariety.LETTEROFIGUARANTEE;
				varieties[2] = LOANConstant.BankCreditVariety.PROVEOFCREDIT;
				varieties[3] = LOANConstant.BankCreditVariety.SHORTTERMLOAN;
				varieties[4] = LOANConstant.BankCreditVariety.LONGTERMLOAN;
				
				amounts[0] = AmountCREDIT;
				amounts[1] = AmountGUARANTEE;
				amounts[2] = AmountPROVE;
				amounts[3] = AmountSHORT;
				amounts[4] = AmountLONG;
				samount = getBiz.getMixBalance(info.getId(),info.getCompanyCode(),info.getVariety(),varieties,amounts);
				needAmount = lastAmount + samount- hasUsedAmountACCEPTBILL ;
				
				break;
			case (int) LOANConstant.BankCreditVariety.SHORTTERMLOAN://短期贷款
				varieties[0] = LOANConstant.BankCreditVariety.LETTEROFCREDIT;
				varieties[1] = LOANConstant.BankCreditVariety.LETTEROFIGUARANTEE;
				varieties[2] = LOANConstant.BankCreditVariety.PROVEOFCREDIT;
				varieties[3] = LOANConstant.BankCreditVariety.ACCEPTBILL;
				varieties[4] = LOANConstant.BankCreditVariety.LONGTERMLOAN;
				amounts[0] = AmountCREDIT;
				amounts[1] = AmountGUARANTEE;
				amounts[2] = AmountPROVE;
				amounts[3] = AmountACCEPTBILL;
				amounts[4] = AmountLONG;
				samount = getBiz.getMixBalance(info.getId(),info.getCompanyCode(),info.getVariety(),varieties,amounts);
				needAmount = lastAmount + samount- hasUsedAmountSHORT ;
				
				break;
			case (int) LOANConstant.BankCreditVariety.LONGTERMLOAN://长期贷款
				//lret = dao.isUsedBankLoan(contractNoid , companyCode,variety);
				varieties[0] = LOANConstant.BankCreditVariety.LETTEROFCREDIT;
				varieties[1] = LOANConstant.BankCreditVariety.LETTEROFIGUARANTEE;
				varieties[2] = LOANConstant.BankCreditVariety.PROVEOFCREDIT;
				varieties[3] = LOANConstant.BankCreditVariety.ACCEPTBILL;
				varieties[4] = LOANConstant.BankCreditVariety.SHORTTERMLOAN;
				amounts[0] = AmountCREDIT;
				amounts[1] = AmountGUARANTEE;
				amounts[2] = AmountPROVE;
				amounts[3] = AmountACCEPTBILL;
				amounts[4] = AmountSHORT;
				samount = getBiz.getMixBalance(info.getId(),info.getCompanyCode(),info.getVariety(),varieties,amounts);
				needAmount = lastAmount +samount- hasUsedAmountLONG ;
				
				break;	
			}
			
			if (needAmount >=0 )
			{
				lret = "over";//默认输入金额大于余额
				isOverBalance = isOverBalance(info.getAmount(),info.getId(),info.getVariety(),info.getOfficeId(),info.getCompanyCode());
				if(!isOverBalance)
				{
					lret = "ModifyFailed";//修改失败
					isModify = dao.modify(info);
					if (isModify)
					{
						lret = "ModifySuccess";//修改成功
					}
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
	
	
	// 判断分配金额大于授信余额
    //	 返回true 超过余额，返回false 没有超过余额
	private boolean isOverBalance (double amount, long contractNoid, long variety, long officeId,String companyCode) throws Exception 
	{
		boolean lret = true ;//默认超过余额
		double balance = 0;
		
		try
		{
			BankCreditExtSplitDAO dao = new BankCreditExtSplitDAO();
			balance = dao.getBalance( contractNoid,variety,officeId,companyCode );
			if(balance - amount >= 0) //余额大于金额则没有超过余额，返回false
			{
				lret = false;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lret;
	}
}
