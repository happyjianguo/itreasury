package com.iss.itreasury.securities.deliveryorder.dataentity;

import java.sql.Timestamp;
import java.util.Calendar;

import com.iss.itreasury.securities.apply.dao.SEC_BidRangeDAO;
import com.iss.itreasury.securities.apply.dataentity.ApplyInfo;
import com.iss.itreasury.securities.apply.dataentity.BidRangeInfo;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.util.NameRef;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.securities.util.SECUtil;

/*
 * 
 * 13.资金拆借
 * 
 * 16.股票一级网上申购
 * 17.股票一级网下申购
 * 18.股票二级
 * 
 * 26.银行间债券回购
 * 27.交易所债券回购
 * 
 * 31.银行间国债一级
 * 32.银行间国债二级
 * 33.交易所国债一级
 * 34.交易所国债二级
 * 
 * 46.企业债一级(参照股票一级网下申购)
 * 47.企业债二级(参照股票二级)
 * 
 * 53.可转债二级(参照股票二级)
 * 54.债转股
 * 
 * 57.封闭式基金一级网下申购(参照股票一级网下申购) 58.封闭式基金二级(参照股票二级)
 * 
 * 61.开放式基金一级认购
 * 62.开放式基金二级申购(参照开放式基金一级认购) 
 * 63.开放式基金二级赎回(参照开放式基金一级认购) 
 * 64.开放式基金分红设计(没有申请书，所以不需要转换)
 * 92.开放式基金转换
 * 
 * 
 */
public class MappingDataentity {

    public DeliveryOrderInfo map(ApplyInfo applyInfo) throws SecuritiesDAOException
    {
    	long businessType = Long.parseLong(Long.toString(applyInfo.getTransactionTypeId()).substring(0,2));
		DeliveryOrderInfo deliveryOrderInfo = new DeliveryOrderInfo();
		
		switch ((int) businessType)
		{
			//资金拆借
			case (int) SECConstant.BusinessType.CAPITAL_LANDING.ID:
				deliveryOrderInfo = this.matchCapitalLanding(applyInfo);
				break;
				
			//股票一级网上申购
			case (int) SECConstant.BusinessType.STOCK_BID_ONLINE.ID:
				deliveryOrderInfo = this.matchStockBidOnline(applyInfo);
				break;
			//股票一级网下申购
			case (int) SECConstant.BusinessType.STOCK_BID.ID:
				deliveryOrderInfo = this.matchStockBid(applyInfo);
				break;
			//股票二级
			case (int) SECConstant.BusinessType.STOCK_TRANSACTION.ID:
				deliveryOrderInfo = this.matchStockTransaction(applyInfo);
				break;
			//央行票据一级
			case (int) SECConstant.BusinessType.CENTRAL_BANK_NOTE_BID.ID:
				deliveryOrderInfo = this.matchCentralBankNoteBid(applyInfo);
				break;
			//央行票据二级
			case (int) SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.ID:
				deliveryOrderInfo = this.matchCentralBankNoteTransaction(applyInfo);
				break;
			//银行间债券回购
			case (int) SECConstant.BusinessType.BANK_BOND_REPURCHASE.ID:
				deliveryOrderInfo = this.matchBankBondRepurchase(applyInfo);
				break;
			//交易所债券回购
			case (int) SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.ID:
				deliveryOrderInfo = this.matchExchangeCenterBondRepurchase(applyInfo);
				break;
				
			//银行间国债一级
			case (int) SECConstant.BusinessType.BANK_NATIONAL_BOND_BID.ID:
				deliveryOrderInfo = this.matchBankNationalBondBid(applyInfo);
				break;
			//银行间国债二级
			case (int) SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.ID:
				deliveryOrderInfo = this.matchBankNationalBondTransaction(applyInfo);
				break;
			//交易所国债一级
			case (int) SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_BID.ID:
				deliveryOrderInfo = this.matchExchangeCenterNationalBondBid(applyInfo);
				break;
			//交易所国债二级
			case (int) SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.ID:
				deliveryOrderInfo = this.matchExchangeCenterNationalBondTransaction(applyInfo);
				break;
			//金融债一级
			case (int) SECConstant.BusinessType.FINANCIAL_BOND_BID.ID:
				deliveryOrderInfo = this.matchFinacialBondBid(applyInfo);
				break;
			//金融债二级
			case (int) SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.ID:
				deliveryOrderInfo = this.matchFinacialBondTransaction(applyInfo);
				break;
			//政策性金融债一级
			case (int) SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_BID.ID:
				deliveryOrderInfo = this.matchPolicyRelatedFinacialBondBid(applyInfo);
				break;
			//政策性金融债二级
			case (int) SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.ID:
				deliveryOrderInfo = this.matchPolicyRelatedFinacialBondTransaction(applyInfo);
				break;
			//企业债一级
			case (int) SECConstant.BusinessType.ENTERPRISE_BOND_BID.ID:
				deliveryOrderInfo = this.matchEnterpriseBondBid(applyInfo);
				break;
			//企业债二级
			case (int) SECConstant.BusinessType.ENTERPRISE_BOND_TRANSACTION.ID:
				deliveryOrderInfo = this.matchEnterpriseBondTransaction(applyInfo);
				break;
			//可转债一级网上
			case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_BID_ONLINE.ID:
				deliveryOrderInfo = this.matchTransformableBondBidOnline(applyInfo);
				break;
			//可转债一级网下
			case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.ID:
				deliveryOrderInfo = this.matchTransformableBondBid(applyInfo);
				break;
			//可转债二级
			case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_TRANSACTION.ID:
				deliveryOrderInfo = this.matchTransformableBondTransaction(applyInfo);
				break;
			//债转股
			case (int) SECConstant.BusinessType.DEBT_TO_EQUITY.ID:
				deliveryOrderInfo = this.matchDebt_to_equity(applyInfo);
				break;
			//封闭式基金一级网上申购
			case (int) SECConstant.BusinessType.ENCLOSED_FUND_BID_ONLINE.ID:
				deliveryOrderInfo = this.matchEnclosedFundBidOnline(applyInfo);
				break;
			//封闭式基金一级网下申购
			case (int) SECConstant.BusinessType.ENCLOSED_FUND_BID.ID:
				deliveryOrderInfo = this.matchEnclosedFundBid(applyInfo);
				break;
			//封闭式基金二级
			case (int) SECConstant.BusinessType.ENCLOSED_FUND_TRANSACTION.ID:
				deliveryOrderInfo = this.matchEnclosedFundTransaction(applyInfo);
				break;
			//开放式基金一级认购
			case (int) SECConstant.BusinessType.MUTUAL_FUND_SUBSCRIBE.ID:
				deliveryOrderInfo = this.matchMutualFundSubscribe(applyInfo);
				break;
			//开放式基金二级申购
			case (int) SECConstant.BusinessType.MUTUAL_FUND_BID.ID:
				deliveryOrderInfo = this.matchMutualFundBid(applyInfo);
				break;
			//开放式基金二级赎回
			case (int) SECConstant.BusinessType.MUTUAL_FUND_REDEEM.ID:
				deliveryOrderInfo = this.matchMutualFundRedeem(applyInfo);
				break;
			//基金转换
			case (int) SECConstant.BusinessType.FUND_TRANSFER.ID:
				deliveryOrderInfo = this.matchFundTransfer(applyInfo);
				break;
				
		}
		return deliveryOrderInfo;
    }
	/**
	 * 13.资金拆借
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchCapitalLanding(ApplyInfo applyInfo)
	{
		DeliveryOrderInfo deliveryOrderInfo = new DeliveryOrderInfo();
		Calendar cal = Calendar.getInstance();
		
//		申请书ID
		deliveryOrderInfo.setApplyFormId(applyInfo.getId());
		
//		交易类型
		deliveryOrderInfo.setTransactionTypeId(applyInfo.getTransactionTypeId()); 
		
//		成交日期
		deliveryOrderInfo.setTransactionDate(applyInfo.getTransactionEndDate());
		
//		业务单位名称  
		deliveryOrderInfo.setClientId(applyInfo.getClientId()); 
		
//		交易对手编号
		deliveryOrderInfo.setCounterpartId(applyInfo.getCounterpartId()); 
		
//		起息日(成交日期+申请书中的清算速度N天)
		cal.setTime(applyInfo.getTransactionEndDate());//设成交日期
		int liquidateRate = (int)applyInfo.getLiquidateRate();//清算速度
		if(liquidateRate == -1) liquidateRate = 0;
		 
		cal.add(Calendar.DATE, liquidateRate);//成交日期+清算速度
		deliveryOrderInfo.setValueDate(new Timestamp(cal.getTime().getTime()));
		
//		交割日(默认值为起息日)
		deliveryOrderInfo.setDeliveryDate(new Timestamp(cal.getTime().getTime()));
		
//		还款日(起息日+拆借期限)
		int term = (int)applyInfo.getTerm();//拆借期限
		cal.add(Calendar.DATE, term);//起息日+拆借期限
		deliveryOrderInfo.setMaturityDate(new Timestamp(cal.getTime().getTime()));

//		拆借金额
		deliveryOrderInfo.setPledgeSecuritiesAmount(applyInfo.getPledgeSecuritiesAmount());//万元 
		deliveryOrderInfo.setAmount(applyInfo.getAmount());//元
		
//		拆借利率
		deliveryOrderInfo.setRate(applyInfo.getRate());
		
//		拆借期限
		deliveryOrderInfo.setTerm(applyInfo.getTerm());
		
//		应计利息(拆借金额*拆借利率*拆借期限/(360*100))
		double suspenseInterest = SECUtil.Arith.div(SECUtil.Arith.mul(SECUtil.Arith.mul(applyInfo.getPledgeSecuritiesAmount()*10000,applyInfo.getRate()),applyInfo.getTerm()),36000);
		deliveryOrderInfo.setSuspenseInterest(suspenseInterest);
		
//		到期还款金额(拆借金额+应计利息)
		deliveryOrderInfo.setMaturityAmount(applyInfo.getPledgeSecuritiesAmount()*10000+suspenseInterest);
		
//		实计利息(默认值为应计利息)
		deliveryOrderInfo.setInterest(suspenseInterest);
		
//		税费(默认值为0)
		deliveryOrderInfo.setTax(0.0);
		
//		实际收付(默认值为拆借金额)
		deliveryOrderInfo.setNetIncome(applyInfo.getPledgeSecuritiesAmount());
		
		//备注
		deliveryOrderInfo.setRemark(applyInfo.getRemark());
		
		return deliveryOrderInfo;
	}


	/**
	 * 16.股票一级网上申购
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchStockBidOnline(ApplyInfo applyInfo)
	{
		DeliveryOrderInfo deliveryOrderInfo = new DeliveryOrderInfo();
		
//		申请书ID
		deliveryOrderInfo.setApplyFormId(applyInfo.getId());
		
//		交易类型
		deliveryOrderInfo.setTransactionTypeId(applyInfo.getTransactionTypeId()); 
		
//		成交日期
		deliveryOrderInfo.setTransactionDate(applyInfo.getTransactionStartDate());
		
//		业务单位名称  
		deliveryOrderInfo.setClientId(applyInfo.getClientId()); 
		
//		开户营业部名称
		deliveryOrderInfo.setCounterpartId(applyInfo.getCounterpartId()); 
		
//		资金帐号
		deliveryOrderInfo.setAccountId(applyInfo.getAccountId());
		
//		股东账户代码/名称(资金帐号放大镜带出)
//		发行时证券代码(首发SecuritiesCode1)
		deliveryOrderInfo.setSecuritiesId(applyInfo.getSecuritiesId());
		
//		成交价格
		deliveryOrderInfo.setPrice(applyInfo.getPrice());
		
//		成交数量
		deliveryOrderInfo.setQuantity(applyInfo.getQuantity());
		
				
//		成交金额
		double amount = SECUtil.Arith.mul(applyInfo.getPrice(), applyInfo.getQuantity());
		deliveryOrderInfo.setAmount(amount);//元
		
//		税费(默认值为0)
		deliveryOrderInfo.setTax(0.0);
		
		
//		实际收付(默认值为拆借金额)
		deliveryOrderInfo.setNetIncome(applyInfo.getAmount());
		
		//备注
		deliveryOrderInfo.setRemark(applyInfo.getRemark());
		
		return deliveryOrderInfo;
	}
	/**
	 * 17.股票一级网下申购
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchStockBid(ApplyInfo applyInfo)
	{
		DeliveryOrderInfo deliveryOrderInfo = new DeliveryOrderInfo();
		
//		申请书ID
		deliveryOrderInfo.setApplyFormId(applyInfo.getId());
		
//		交易类型
		deliveryOrderInfo.setTransactionTypeId(applyInfo.getTransactionTypeId()); 
		
//		成交日期
		deliveryOrderInfo.setTransactionDate(applyInfo.getTransactionStartDate());
		
//		业务单位名称  
		deliveryOrderInfo.setClientId(applyInfo.getClientId()); 
		
//		开户营业部名称
		deliveryOrderInfo.setCounterpartId(applyInfo.getCounterpartId()); 
		
//		资金帐号
		deliveryOrderInfo.setAccountId(applyInfo.getAccountId());
		
//		股东账户代码/名称(资金帐号放大镜带出)
//		证券代码(首发SecuritiesCode1)
		deliveryOrderInfo.setSecuritiesId(applyInfo.getSecuritiesId());
		
//		成交价格
		deliveryOrderInfo.setPrice(applyInfo.getPrice());
		
//		成交数量
		deliveryOrderInfo.setQuantity(applyInfo.getQuantity());
				
//		成交金额
		double amount = SECUtil.Arith.mul(applyInfo.getPrice(), applyInfo.getQuantity());
		deliveryOrderInfo.setAmount(amount);//元
		
//		预付订金比例
		deliveryOrderInfo.setRate(applyInfo.getConvertRate());
		
		//预付定金(成交金额*预付订金比例/100)
		double maturityAmount = SECUtil.Arith.div(SECUtil.Arith.mul(amount, applyInfo.getConvertRate()), 100);
		deliveryOrderInfo.setMaturityAmount(maturityAmount);
		
//		税费(默认值为0)
		deliveryOrderInfo.setTax(0.0);
		
//		实际收付(默认值为拆借金额)
		deliveryOrderInfo.setNetIncome(maturityAmount);
		
		//备注
		deliveryOrderInfo.setRemark(applyInfo.getRemark());
		
		return deliveryOrderInfo;
	}
	/**
	 * 18.股票二级
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchStockTransaction(ApplyInfo applyInfo)
	{
		DeliveryOrderInfo deliveryOrderInfo = new DeliveryOrderInfo();
		
//		申请书ID
		deliveryOrderInfo.setApplyFormId(applyInfo.getId());
		
//		交易类型
		deliveryOrderInfo.setTransactionTypeId(applyInfo.getTransactionTypeId()); 
		
//		成交日期
		deliveryOrderInfo.setTransactionDate(applyInfo.getTransactionStartDate());
		
//		业务单位名称  
		deliveryOrderInfo.setClientId(applyInfo.getClientId()); 
		
//		开户营业部名称
		deliveryOrderInfo.setCounterpartId(applyInfo.getCounterpartId()); 
		
//		资金帐号
		deliveryOrderInfo.setAccountId(applyInfo.getAccountId());
		
//		股东账户代码/名称(资金帐号放大镜带出)
//		证券代码(二级SecuritiesCode2)
		deliveryOrderInfo.setSecuritiesId(applyInfo.getSecuritiesId());
		
//		成交价格
		deliveryOrderInfo.setPrice(applyInfo.getPrice());
		
//		成交数量
		deliveryOrderInfo.setQuantity(applyInfo.getQuantity());
						
//		成交金额
		double amount = SECUtil.Arith.mul(applyInfo.getPrice(), applyInfo.getQuantity());
		deliveryOrderInfo.setAmount(amount);//元
		
//		税费(默认值为0)
		deliveryOrderInfo.setTax(0.0);
		
		
//		实际收付(默认值为拆借金额)
		deliveryOrderInfo.setNetIncome(amount);
		
		//备注
		deliveryOrderInfo.setRemark(applyInfo.getRemark());
		
		return deliveryOrderInfo;
	}
	/**
	 * 21.央行票据一级
	 * @param amount The amount to set.
	 * @throws SecuritiesDAOException 
	 */
	private DeliveryOrderInfo matchCentralBankNoteBid(ApplyInfo applyInfo) throws SecuritiesDAOException
	{
		return matchBankNationalBondBid(applyInfo);
	}
	/**
	 * 22.央行票据二级
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchCentralBankNoteTransaction(ApplyInfo applyInfo)
	{
		return matchBankNationalBondTransaction(applyInfo);
	}
	/**
	 * 26.银行间债券回购
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchBankBondRepurchase(ApplyInfo applyInfo)
	{
		DeliveryOrderInfo deliveryOrderInfo = new DeliveryOrderInfo();
		Calendar cal = Calendar.getInstance();
		
//		申请书ID
		deliveryOrderInfo.setApplyFormId(applyInfo.getId());
		
//		交易类型
		deliveryOrderInfo.setTransactionTypeId(applyInfo.getTransactionTypeId()); 
		
//		成交日期
		deliveryOrderInfo.setTransactionDate(applyInfo.getTransactionStartDate());
		
//		业务单位名称  
		deliveryOrderInfo.setClientId(applyInfo.getClientId()); 
		
//		交易对手编号
		deliveryOrderInfo.setCounterpartId(applyInfo.getCounterpartId()); 
		
//		首次结算日(成交日期+申请书中的清算速度N天)
		cal.setTime(applyInfo.getTransactionStartDate());//设成交日期
		int liquidateRate = (int)applyInfo.getLiquidateRate();//清算速度
		if(liquidateRate == -1) liquidateRate = 0;
		
		cal.add(Calendar.DATE, liquidateRate);//成交日期+清算速度
		deliveryOrderInfo.setDeliveryDate(new Timestamp(cal.getTime().getTime()));

//		到期结算日(首次结算日+回购期限)
		int term = (int)applyInfo.getTerm();//回购期限
		cal.add(Calendar.DATE, term);//首次结算日+回购期限
		deliveryOrderInfo.setMaturityDate(new Timestamp(cal.getTime().getTime()));

//		抵押债券代码
		deliveryOrderInfo.setPledgeSecuritiesId(applyInfo.getSecuritiesId());
		
//		回购利率
		deliveryOrderInfo.setRate(applyInfo.getRate());
		
//		回购期限
		deliveryOrderInfo.setTerm(applyInfo.getTerm());
		
//		券面总额(从申请书取过来的值是元)
		deliveryOrderInfo.setPledgeSecuritiesAmount(applyInfo.getPledgeSecuritiesAmount());//万元 
				
//		拆借利率
		deliveryOrderInfo.setRate(applyInfo.getRate());
		
//		折算比例
		deliveryOrderInfo.setPledgeRate(applyInfo.getConvertRate());
		
//		成交金额(券面总额*折算比例)
		//double amount = SECUtil.Arith.mul(applyInfo.getPledgeSecuritiesAmount(), applyInfo.getConvertRate())*100;
		deliveryOrderInfo.setAmount(applyInfo.getAmount());

//		应计利息(券面总额*回购期限*回购利率/(365*100))
		double interest = SECUtil.Arith.div(SECUtil.Arith.mul(SECUtil.Arith.mul(applyInfo.getPledgeSecuritiesAmount()*10000,applyInfo.getRate()),applyInfo.getTerm()),36500);
		deliveryOrderInfo.setInterest(interest);

//		到期还款金额(成交金额+应计利息)
		deliveryOrderInfo.setMaturityAmount(applyInfo.getAmount() + interest);
		
//		税费(默认值为0)
		deliveryOrderInfo.setTax(0.0);
		
//		实际收付(默认值为成交金额)
		deliveryOrderInfo.setNetIncome(applyInfo.getAmount());
		
		//备注
		deliveryOrderInfo.setRemark(applyInfo.getRemark());
		
		return deliveryOrderInfo;
	}
	/**
	 * 27.交易所债券回购
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchExchangeCenterBondRepurchase(ApplyInfo applyInfo)
	{
		DeliveryOrderInfo deliveryOrderInfo = new DeliveryOrderInfo();
		Calendar cal = Calendar.getInstance();
		
//		申请书ID
		deliveryOrderInfo.setApplyFormId(applyInfo.getId());
		
//		交易类型
		deliveryOrderInfo.setTransactionTypeId(applyInfo.getTransactionTypeId()); 
		
//		成交日期
		deliveryOrderInfo.setTransactionDate(applyInfo.getTransactionStartDate());
		
//		业务单位名称  
		deliveryOrderInfo.setClientId(applyInfo.getClientId()); 
		
//		开户营业部名称
		deliveryOrderInfo.setCounterpartId(applyInfo.getCounterpartId()); 
		
//		资金帐号
		deliveryOrderInfo.setAccountId(applyInfo.getAccountId());
		
//		股东账户代码/名称(资金帐号放大镜带出)
//		证券代码(二级SecuritiesCode2)
		deliveryOrderInfo.setSecuritiesId(applyInfo.getSecuritiesId());
		
//		购回日期(成交日期+1+该回购品种所设置的回购的天数)
		cal.setTime(applyInfo.getTransactionStartDate());//设成交日期
		String pledgeTerm = NameRef.getPledgeTermBySecuritiesId(applyInfo.getSecuritiesId());
		
		if(pledgeTerm.equals("")){
		    pledgeTerm = "0";
		}
		int term = Integer.parseInt(pledgeTerm);//回购期限
		cal.add(Calendar.DATE, term+1);//成交日期+回购期限+1
		deliveryOrderInfo.setDeliveryDate(new Timestamp(cal.getTime().getTime()));
				
//		成交价格
		deliveryOrderInfo.setPrice(applyInfo.getPrice());
		
//		成交数量
		deliveryOrderInfo.setQuantity(applyInfo.getQuantity());
		
//		抵押标准券(默认值为成交数量)
		deliveryOrderInfo.setPledgeSecuritiesQuantity((long)applyInfo.getQuantity());
		
//		成交金额
		deliveryOrderInfo.setAmount(applyInfo.getAmount());//元
		
		//购回金额(默认值：0)
		deliveryOrderInfo.setMaturityAmount(0.0);
		
//		税费(默认值为0)
		deliveryOrderInfo.setTax(0.0);
		
		
//		实际收付(默认值为拆借金额)
		deliveryOrderInfo.setNetIncome(applyInfo.getAmount());
		
		//备注
		deliveryOrderInfo.setRemark(applyInfo.getRemark());
		
		return deliveryOrderInfo;
	}
	
	/**
	 * 31.银行间国债一级
	 * @param amount The amount to set.
	 * @throws SecuritiesDAOException 
	 * @throws SecuritiesDAOException 
	 * 
	 */
	//Modify by leiyang 2007/12/28  暂不修改需要确认
	private DeliveryOrderInfo matchBankNationalBondBid(ApplyInfo applyInfo) throws SecuritiesDAOException{
		DeliveryOrderInfo deliveryOrderInfo = new DeliveryOrderInfo();
		SEC_BidRangeDAO brDao = new SEC_BidRangeDAO();
		Calendar cal = Calendar.getInstance();
		
		//申请书信息
		BidRangeInfo bidRangeInfo = brDao.findMaxAmountByBidRangeInfo(applyInfo.getId());
		
//		申请书ID
		deliveryOrderInfo.setApplyFormId(applyInfo.getId());
		
//		交易类型
		deliveryOrderInfo.setTransactionTypeId(applyInfo.getTransactionTypeId()); 
		
//		成交日期
		deliveryOrderInfo.setTransactionDate(applyInfo.getTransactionStartDate());
		
//		业务单位名称  
		deliveryOrderInfo.setClientId(applyInfo.getClientId()); 
		
//		债券分销商编号
		deliveryOrderInfo.setCounterpartId(applyInfo.getCounterpartId()); 
		
//		证券代码(首发SecuritiesCode1)
		deliveryOrderInfo.setSecuritiesId(applyInfo.getSecuritiesId());
		
//		投标方式
		deliveryOrderInfo.setBidTypeId(applyInfo.getBidTypeId());
		
//		结算日
		cal.setTime(applyInfo.getTransactionStartDate());//设成交日期
		int liquidateRate = (int)applyInfo.getLiquidateRate();//清算速度
		if(liquidateRate == -1) liquidateRate = 0;
		
		cal.add(Calendar.DATE, liquidateRate);//成交日期+清算速度
		deliveryOrderInfo.setDeliveryDate(new Timestamp(cal.getTime().getTime()));
		
//		成交价格
		deliveryOrderInfo.setPrice(bidRangeInfo.getBidEndAmount());
		
//		成交利率
		deliveryOrderInfo.setRate(bidRangeInfo.getBidEndRate());
		
//		成交数量
		deliveryOrderInfo.setQuantity(bidRangeInfo.getApplyQuantity());
		
//		成交金额
		deliveryOrderInfo.setAmount(bidRangeInfo.getBidEndAmount()*bidRangeInfo.getApplyQuantity());

//		预计手续费返还
		deliveryOrderInfo.setCommissionCharge(applyInfo.getCommissionCharge());

//		税费(默认值为0)
		deliveryOrderInfo.setTax(0.0);
		
//		实际收付(默认值为拆借金额)
		deliveryOrderInfo.setNetIncome(bidRangeInfo.getBidEndAmount()*bidRangeInfo.getApplyQuantity());
		
		//备注
		deliveryOrderInfo.setRemark(applyInfo.getRemark());

		return deliveryOrderInfo;
	}

	/**
	 * 32.银行间国债二级
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchBankNationalBondTransaction(ApplyInfo applyInfo)
	{
		DeliveryOrderInfo deliveryOrderInfo = new DeliveryOrderInfo();
		Calendar cal = Calendar.getInstance();
		
//		申请书ID
		deliveryOrderInfo.setApplyFormId(applyInfo.getId());
		
//		交易类型
		deliveryOrderInfo.setTransactionTypeId(applyInfo.getTransactionTypeId()); 
		
//		成交日期
		deliveryOrderInfo.setTransactionDate(applyInfo.getTransactionStartDate());
		
//		业务单位名称  
		deliveryOrderInfo.setClientId(applyInfo.getClientId()); 
		
//		交易对手名称
		deliveryOrderInfo.setCounterpartId(applyInfo.getCounterpartId()); 
		
//		证券代码(首发SecuritiesCode1)
		deliveryOrderInfo.setSecuritiesId(applyInfo.getSecuritiesId());
		
//		结算日
		cal.setTime(applyInfo.getTransactionStartDate());//设成交日期
		int liquidateRate = (int)applyInfo.getLiquidateRate();//清算速度
		if(liquidateRate == -1) liquidateRate = 0;
		
		cal.add(Calendar.DATE, liquidateRate);//成交日期+清算速度
		deliveryOrderInfo.setDeliveryDate(new Timestamp(cal.getTime().getTime()));
		
//		净价(默认值：0)
		deliveryOrderInfo.setNetPrice(0.0);
		
//		应计利息(默认值：0)
		
//		全价
		deliveryOrderInfo.setPrice(applyInfo.getPrice());
		
//		券面总额(万元)(成交数量*100/10000)
		deliveryOrderInfo.setPledgeSecuritiesAmount(SECUtil.Arith.div(applyInfo.getQuantity(), 100));
		
//		成交数量
		deliveryOrderInfo.setQuantity(applyInfo.getQuantity());
		
//		净价金额(净价*成交数量)
//		应计利息总额(应计利息*成交数量)

//		全价金额(全价*成交数量)
		double amount = SECUtil.Arith.mul(applyInfo.getPrice(), applyInfo.getQuantity());
		deliveryOrderInfo.setAmount(amount);

//		税费(默认值为0)
		deliveryOrderInfo.setTax(0.0);
		
//		实际收付(默认值为拆借金额)
		deliveryOrderInfo.setNetIncome(amount);
		
		//备注
		deliveryOrderInfo.setRemark(applyInfo.getRemark());
		
//		交易对手债券托管账号
        deliveryOrderInfo.setCounterpartTrusteeCode(NameRef.getCounterpartTrusteeCodeByCounterpartId(applyInfo.getCounterpartId()) );
		
		return deliveryOrderInfo;
	}
	/**
	 * 33.交易所国债一级
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchExchangeCenterNationalBondBid(ApplyInfo applyInfo)
	{
		DeliveryOrderInfo deliveryOrderInfo = new DeliveryOrderInfo();
		Calendar cal = Calendar.getInstance();
		
//		申请书ID
		deliveryOrderInfo.setApplyFormId(applyInfo.getId());
		
//		交易类型
		deliveryOrderInfo.setTransactionTypeId(applyInfo.getTransactionTypeId()); 
		
//		成交日期
		deliveryOrderInfo.setTransactionDate(applyInfo.getTransactionStartDate());
		
//		业务单位名称  
		deliveryOrderInfo.setClientId(applyInfo.getClientId()); 
		
//		开户营业部名称
		deliveryOrderInfo.setCounterpartId(applyInfo.getCounterpartId()); 
		
//		资金帐号
		deliveryOrderInfo.setAccountId(applyInfo.getAccountId());
		
//		股东账户代码/名称(资金帐号放大镜带出)
//		证券代码(首发SecuritiesCode1)
		deliveryOrderInfo.setSecuritiesId(applyInfo.getSecuritiesId());
		
		//投标方式
		deliveryOrderInfo.setBidTypeId(applyInfo.getBidTypeId());
		
//		结算日(成交日期+清算速度)
		cal.setTime(applyInfo.getTransactionStartDate());//设成交日期
		int liquidateRate = (int)applyInfo.getLiquidateRate();//清算速度
		if(liquidateRate == -1) liquidateRate = 0;
		
		cal.add(Calendar.DATE, liquidateRate);//成交日期+清算速度
		deliveryOrderInfo.setDeliveryDate(new Timestamp(cal.getTime().getTime()));
		
//		成交价格
		deliveryOrderInfo.setPrice(applyInfo.getPrice());
		
//		成交数量
		deliveryOrderInfo.setQuantity(applyInfo.getQuantity());
				
//		成交金额(成交价格*成交数量)
		double amount = SECUtil.Arith.mul(applyInfo.getPrice(), applyInfo.getQuantity());
		deliveryOrderInfo.setAmount(amount);//元
		
//		预计手续费返还
		deliveryOrderInfo.setCommissionCharge(applyInfo.getCommissionCharge());
		
//		税费(默认值为0)
		deliveryOrderInfo.setTax(0.0);
		
		
//		实际收付(默认值为拆借金额)
		deliveryOrderInfo.setNetIncome(amount);
		
		//备注
		deliveryOrderInfo.setRemark(applyInfo.getRemark());
		
		return deliveryOrderInfo;
	}
	/**
	 * 34.交易所国债二级
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchExchangeCenterNationalBondTransaction(ApplyInfo applyInfo)
	{
		DeliveryOrderInfo deliveryOrderInfo = new DeliveryOrderInfo();
		Calendar cal = Calendar.getInstance();
		
//		申请书ID
		deliveryOrderInfo.setApplyFormId(applyInfo.getId());
		
//		交易类型
		deliveryOrderInfo.setTransactionTypeId(applyInfo.getTransactionTypeId()); 
		
//		成交日期
		deliveryOrderInfo.setTransactionDate(applyInfo.getTransactionStartDate());
		
//		业务单位名称  
		deliveryOrderInfo.setClientId(applyInfo.getClientId()); 
		
//		开户营业部名称
		deliveryOrderInfo.setCounterpartId(applyInfo.getCounterpartId()); 
		
//		资金帐号
		deliveryOrderInfo.setAccountId(applyInfo.getAccountId());
		
//		股东账户代码/名称(资金帐号放大镜带出)
//		发行时证券代码(首发SecuritiesCode1)
		deliveryOrderInfo.setSecuritiesId(applyInfo.getSecuritiesId());
		
//		净价(默认值：0)
		deliveryOrderInfo.setNetPrice(0.0);
		
//		应计利息(默认值：0)
		
//		全价
		deliveryOrderInfo.setPrice(applyInfo.getPrice());
		
//		成交数量
		deliveryOrderInfo.setQuantity(applyInfo.getQuantity());
		
//		成交金额(全价*成交数量)
		double amount = SECUtil.Arith.mul(applyInfo.getPrice(), applyInfo.getQuantity());
		
		deliveryOrderInfo.setAmount(amount);

//		税费(默认值为0)
		deliveryOrderInfo.setTax(0.0);
		
//		实际收付(默认值为拆借金额)
		deliveryOrderInfo.setNetIncome(amount);
		
		//备注
		deliveryOrderInfo.setRemark(applyInfo.getRemark());
		
		return deliveryOrderInfo;
	}
	/**
	 * 36.金融债一级
	 * @param amount The amount to set.
	 * @throws SecuritiesDAOException 
	 */
	private DeliveryOrderInfo matchFinacialBondBid(ApplyInfo applyInfo) throws SecuritiesDAOException
	{
		return matchBankNationalBondBid(applyInfo);
	}
	/**
	 * 37.金融债二级
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchFinacialBondTransaction(ApplyInfo applyInfo)
	{
		return matchBankNationalBondTransaction(applyInfo);
	}
	/**
	 * 41.政策性金融债一级
	 * @param amount The amount to set.
	 * @throws SecuritiesDAOException 
	 */
	private DeliveryOrderInfo matchPolicyRelatedFinacialBondBid(ApplyInfo applyInfo) throws SecuritiesDAOException
	{
		return matchBankNationalBondBid(applyInfo);
	}
	/**
	 * 42.政策性金融债二级
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchPolicyRelatedFinacialBondTransaction(ApplyInfo applyInfo)
	{
		return matchBankNationalBondTransaction(applyInfo);
	}
	/**
	 * 46.企业债一级
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchEnterpriseBondBid(ApplyInfo applyInfo)
	{
		return matchStockBid(applyInfo);
	}
	/**
	 * 47.企业债二级
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchEnterpriseBondTransaction(ApplyInfo applyInfo)
	{
		return matchStockTransaction(applyInfo);
	}
	/**
	 * 51.可转债一级网上申购
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchTransformableBondBidOnline(ApplyInfo applyInfo)
	{
		return matchStockBidOnline(applyInfo);
	}
	/**
	 * 52.可转债一级网下申购
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchTransformableBondBid(ApplyInfo applyInfo)
	{
		return matchStockBid(applyInfo);
	}
	/**
	 * 53.可转债二级
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchTransformableBondTransaction(ApplyInfo applyInfo)
	{
		return matchStockTransaction(applyInfo);
	}
	/**
	 * 54.债转股
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchDebt_to_equity(ApplyInfo applyInfo)
	{
		DeliveryOrderInfo deliveryOrderInfo = new DeliveryOrderInfo();
		
		deliveryOrderInfo.setTransactionTypeId(applyInfo.getTransactionTypeId());
		deliveryOrderInfo.setTransactionDate(applyInfo.getTransactionStartDate());
		deliveryOrderInfo.setClientId(applyInfo.getClientId());
		deliveryOrderInfo.setCounterpartId(applyInfo.getCounterpartId());
		deliveryOrderInfo.setAccountId(applyInfo.getAccountId());
		deliveryOrderInfo.setSecuritiesId(applyInfo.getSecuritiesId());
		
		deliveryOrderInfo.setPrice(applyInfo.getPrice());
		deliveryOrderInfo.setQuantity(applyInfo.getQuantity());
//		转股金额
		double amount = SECUtil.Arith.mul(applyInfo.getPrice(), applyInfo.getQuantity());
		deliveryOrderInfo.setAmount(amount);//元
		deliveryOrderInfo.setOppositeSecuritiesId(applyInfo.getStockId());
		
		deliveryOrderInfo.setOppositeQuantity(applyInfo.getStockQuantity());
		
		//备注
		deliveryOrderInfo.setRemark(applyInfo.getRemark());
		
		
		return deliveryOrderInfo;
	}
	/**
	 * 56.封闭式基金一级网上申购
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchEnclosedFundBidOnline(ApplyInfo applyInfo)
	{
		return matchStockBidOnline(applyInfo);
	}
	/**
	 * 57.封闭式基金一级网下申购
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchEnclosedFundBid(ApplyInfo applyInfo)
	{
		return matchStockBid(applyInfo);
	}
	/**
	 * 58.封闭式基金二级
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchEnclosedFundTransaction(ApplyInfo applyInfo)
	{
		return matchStockTransaction(applyInfo);
	}
	
	/**
	 * 61.开放式基金一级认购
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchMutualFundSubscribe(ApplyInfo applyInfo)
	{
		DeliveryOrderInfo deliveryOrderInfo = new DeliveryOrderInfo();
		
//		申请书ID
		deliveryOrderInfo.setApplyFormId(applyInfo.getId());
		
//		交易类型
		deliveryOrderInfo.setTransactionTypeId(applyInfo.getTransactionTypeId()); 
		
//		成交日期
		deliveryOrderInfo.setTransactionDate(applyInfo.getTransactionStartDate());
		
//		业务单位名称  
		deliveryOrderInfo.setClientId(applyInfo.getClientId()); 
		
//		基金管理公司编号
		deliveryOrderInfo.setCounterpartId(applyInfo.getCounterpartId()); 
		
//		交易帐号
		deliveryOrderInfo.setAccountId(applyInfo.getAccountId());
		
//		基金帐户代码/名称(交易帐号放大镜带出)

//		证券代码(首发SecuritiesCode2)
		deliveryOrderInfo.setSecuritiesId(applyInfo.getSecuritiesId());
		
//		结算日(需要输入)

//		认购金额
		deliveryOrderInfo.setAmount(applyInfo.getCostAmount() * applyInfo.getQuantity());//元

//		确认份额
		deliveryOrderInfo.setQuantity(applyInfo.getQuantity());
		
//		净值(默认值：0)
		deliveryOrderInfo.setNetPrice(applyInfo.getCostAmount());
		
//		税费(默认值为0)
		deliveryOrderInfo.setTax(0.0);
		
//		实际收付(默认值为认购金额额)
		deliveryOrderInfo.setNetIncome(applyInfo.getAmount());
		
		//备注
		deliveryOrderInfo.setRemark(applyInfo.getRemark());
		
		return deliveryOrderInfo;
	}
	/**
	 * 62.开放式基金二级申购
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchMutualFundBid(ApplyInfo applyInfo)
	{
		return matchMutualFundSubscribe(applyInfo);
	}
	/**
	 * 63.开放式基金二级赎回
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchMutualFundRedeem(ApplyInfo applyInfo)
	{
		return matchMutualFundSubscribe(applyInfo);
	}
	/**
	 * 92.基金转换
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchFundTransfer(ApplyInfo applyInfo)
	{
		DeliveryOrderInfo deliveryOrderInfo = new DeliveryOrderInfo();
		
//		申请书ID
		deliveryOrderInfo.setApplyFormId(applyInfo.getId());
		
//		交易类型
		deliveryOrderInfo.setTransactionTypeId(applyInfo.getTransactionTypeId()); 
		
//		成交日期
		deliveryOrderInfo.setTransactionDate(applyInfo.getTransactionStartDate());
		
//		业务单位名称  
		deliveryOrderInfo.setClientId(applyInfo.getClientId()); 
		
//		基金管理公司编号
		deliveryOrderInfo.setCounterpartId(applyInfo.getCounterpartId()); 
		
//		交易帐号
		deliveryOrderInfo.setAccountId(applyInfo.getAccountId());
		
//		基金帐户代码/名称(交易帐号放大镜带出)

//		证券代码(转入)
		deliveryOrderInfo.setSecuritiesId(applyInfo.getSecuritiesId());
		//证券代码(转出)
		deliveryOrderInfo.setOppositeSecuritiesId(applyInfo.getStockId());
		
		
		
//		结算日(需要输入)


//		确认份额(转入)
		deliveryOrderInfo.setQuantity(applyInfo.getQuantity());
		//确认份额(转出)
		deliveryOrderInfo.setOppositeQuantity(applyInfo.getStockQuantity());
				
//		确认金额(转出)
		deliveryOrderInfo.setMaturityAmount(applyInfo.getAmount());
		
		
//		净值(默认值：0)
		
//		税费(默认值为0)
		//deliveryOrderInfo.setTax(0.0);
		
//		实际收付(默认值为认购金额额)
		//deliveryOrderInfo.setNetIncome(applyInfo.getAmount());
		
		//备注
		deliveryOrderInfo.setRemark(applyInfo.getRemark());
		
		return deliveryOrderInfo;
	}
	
	

	public static void main(String[] args) {
	}
}
