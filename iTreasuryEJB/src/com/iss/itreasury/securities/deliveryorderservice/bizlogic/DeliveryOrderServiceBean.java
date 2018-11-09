/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-4-13
 */
package com.iss.itreasury.securities.deliveryorderservice.bizlogic;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.Timestamp;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.deliveryorder.dao.SEC_DeliveryOrderDAO;
import com.iss.itreasury.securities.deliveryorder.dataentity.DeliveryOrderInfo;
import com.iss.itreasury.securities.exception.SECInvalidRecordException;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.register.bizlogic.RegisterOperation;
import com.iss.itreasury.securities.securitiesaccount.bizlogic.SecuritiesAccountOperation;
import com.iss.itreasury.securities.securitiesaccount.dataentity.AccountParam;
import com.iss.itreasury.securities.securitiesaccount.exception.AccounStatusException;
import com.iss.itreasury.securities.securitiesaccount.exception.AccountOverDraftException;
import com.iss.itreasury.securities.securitiescontract.dao.SecuritiesContractDao;
import com.iss.itreasury.securities.securitiescontract.dataentity.SecuritiesContractInfo;
import com.iss.itreasury.securities.securitiesgeneralledger.bizlogic.SecuritiesGeneralLedgerOperation;
import com.iss.itreasury.securities.securitiesgeneralledger.dataentity.GenerateGLEntryParam;
import com.iss.itreasury.securities.setting.dao.SEC_SecuritiesDAO;
import com.iss.itreasury.securities.setting.dataentity.SecuritiesInfo;
import com.iss.itreasury.securities.setting.dataentity.TransactionTypeInfo;
import com.iss.itreasury.securities.stock.bizlogic.StockOperation;
import com.iss.itreasury.securities.stock.dataentity.SecuritiesStockParam;
import com.iss.itreasury.securities.stock.dataentity.SecuritiesStockReturn;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log4j;

/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class DeliveryOrderServiceBean {
	
	public DeliveryOrderServiceBean(){
	}
	
	public DeliveryOrderServiceBean(Connection conn){
		transConn = conn;
	}	
	
	
	/**
	 * 用于维护事务完整性的数据库连接
	 * */
	private Connection transConn = null;	
	private Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);
	
	
//	public long saveDeliveryOrder(DeliveryOrderInfo doInfo) throws  RemoteException,SecuritiesException{
//
//			log.debug("------开始交割单业务保存::saveDeliveryOrder-----------");
//			TransactionTypeInfo typeInfo = doInfo.getTransactionTypeInfo();
//			log.debug("---------输入交割单参数:"+doInfo);
//			log.debug("---------交易类型信息为:"+typeInfo);
//			if((doInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_TRANSFER.CAPITAL_IN
//					|| doInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_TRANSFER.CAPITAL_OUT) 
//			&& doInfo.getIsSettlementInvoke() == SECConstant.FALSE){
//				log.debug("------资金划拨业务保存不进行任何处理.退出-----------");
//				return -1;
//			}
//			operateAccount(doInfo,false);
//			operateStock(doInfo, false);	
//			long regID = operateRegister(doInfo,false);
//			DeliveryOrderInfo updatedDO = new DeliveryOrderInfo();
//			log.debug("------结束交割单业务保存::saveDeliveryOrder-----------");
//			if(regID < 0)
//				regID = doInfo.getRegisterId();
//
//			log.debug("------登记薄ID是："+regID);
//			log.debug("---交割日："+doInfo.getDeliveryDate());
//			Timestamp today = Env.getSecuritiesSystemDate(doInfo.getOfficeId(), doInfo.getCurrencyId());
//			
//			log.debug("---今天是："+today);
//			if(!doInfo.getDeliveryDate().after(today)){
//				DeliveryOrderInfo updatedInfo = new DeliveryOrderInfo();				
//				updatedInfo.setId(doInfo.getId());
//				updatedInfo.setIsDelivery(1);
//				updateDeliveryOrder(updatedInfo);												
//			}					
//			
//			return regID;		
//
//	}	
//	
//	public void deleteDeliveryOrder(DeliveryOrderInfo doInfo) throws  RemoteException,SecuritiesException{
//		log.debug("------开始交割单业务删除::deleteDeliveryOrder-----------");
//		TransactionTypeInfo typeInfo = doInfo.getTransactionTypeInfo();
//		log.debug("---------输入交割单参数:"+doInfo);
//		log.debug("---------交易类型信息为:"+typeInfo);		
//		if(doInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_TRANSFER.CAPITAL_IN
//				|| doInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_TRANSFER.CAPITAL_OUT){
//					log.debug("------资金划拨业务删除不进行任何处理.退出-----------");
//					return;
//				}
//		operateAccount(doInfo,true);
//		operateStock(doInfo, true);	
//		operateRegister(doInfo,true);
//
//		log.debug("---交割日："+doInfo.getDeliveryDate());
//		Timestamp today = Env.getSecuritiesSystemDate(doInfo.getOfficeId(), doInfo.getCurrencyId());
//		
//		log.debug("---今天是："+today);
//		if(!doInfo.getDeliveryDate().after(today)){
//			DeliveryOrderInfo updatedInfo = new DeliveryOrderInfo();				
//			updatedInfo.setId(doInfo.getId());
//			updatedInfo.setIsDelivery(0);
//			updateDeliveryOrder(updatedInfo);												
//		}		
//
//		log.debug("------结束交割单业务删除::deleteDeliveryOrder-----------");		
//	}			
	
	
	public void checkDeliveryOrder(DeliveryOrderInfo doInfo) throws RemoteException,SecuritiesException{
		log.debug("------开始交割单业务复核::checkDeliveryOrder-----------");
		log.debug("---交割日："+doInfo.getDeliveryDate());
		//Timestamp today = Env.getSecuritiesSystemDate(doInfo.getOfficeId(), doInfo.getCurrencyId());
		Timestamp today=Env.getSystemDate();
		log.debug("---今天是："+today);
		
		//Modify by leiyang 20071119
		if(doInfo.getDeliveryDate().after(today)){
			log.debug("----------交割日>当前日，暂不产生会计分录，退出--------------");
			return;
		}
		doInfo.resetTransactionTypeInfo();		
		log.debug("------复核的交割单信息为:"+doInfo);
		TransactionTypeInfo typeInfo = doInfo.getTransactionTypeInfo();

		if((typeInfo.getIsNeedNotifyForm() <= 0 || doInfo.getIsSettlementInvoke() == SECConstant.TRUE)
			&& typeInfo.getIsNeedGLEntry() == 1){
			
			log.debug("------交易类型ID为:"+doInfo.getTransactionTypeId());
			
//			if(doInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_TRANSFER.CAPITAL_IN || doInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_TRANSFER.CAPITAL_OUT){
//						log.debug("------资金划拨业务复核进行保存处理-----------");
//						saveDeliveryOrder(doInfo);
//		   }			
			
			
				long accountType = -1;
				GenerateGLEntryParam gleParam = new GenerateGLEntryParam(doInfo);
				
				log.debug("----------证券ID: "+ doInfo.getSecuritiesId());				
				if(doInfo.getSecuritiesId() < 0){
					if(doInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN
							||doInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN_REPAY){
						accountType = SECConstant.EntryAccountType.AccountType_1;//拆入资金
					}else if(doInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT
							||doInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT_REPAY){
						accountType = SECConstant.EntryAccountType.AccountType_2;//拆出资金
					}else if(doInfo.getTransactionTypeId() == SECConstant.BusinessType.BANK_BOND_REPURCHASE.FUND_REPAY
							|| doInfo.getTransactionTypeId() == SECConstant.BusinessType.BANK_BOND_REPURCHASE.FUND_REPURCHASE){
						accountType = SECConstant.EntryAccountType.AccountType_3; //正回购
					}else if(doInfo.getTransactionTypeId() == SECConstant.BusinessType.BANK_BOND_REPURCHASE.BOND_REPAY
							||doInfo.getTransactionTypeId() == SECConstant.BusinessType.BANK_BOND_REPURCHASE.BOND_REPURCHASE){
						accountType = SECConstant.EntryAccountType.AccountType_4; //逆回购
					}else if(doInfo.getTransactionTypeId() == SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.FUND_REPAY
							||doInfo.getTransactionTypeId() == SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.FUND_REPURCHASE){
						accountType = SECConstant.EntryAccountType.AccountType_7; //融资回购
					}else if(doInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_TRANSFER.CAPITAL_IN
						   ||doInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_TRANSFER.CAPITAL_OUT){
						accountType = SECConstant.EntryAccountType.AccountType_10;
					}else if(doInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_REPURCHASE.CAPITAL_REPURCHASE
							|| doInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_REPURCHASE.PAYBACK_NOTIFY
							|| doInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY
							|| doInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_REPURCHASE.INTEREST_PAYMENT){
						accountType = SECConstant.EntryAccountType.AccountType_11;
					}else if(doInfo.getTransactionTypeId() == SECConstant.BusinessType.ENTRUST_FINANCING.ENTRUST_FINANCING
						|| doInfo.getTransactionTypeId() == SECConstant.BusinessType.ENTRUST_FINANCING.CORPORACITY_PAYMENT_NOTIFY
						|| doInfo.getTransactionTypeId() == SECConstant.BusinessType.ENTRUST_FINANCING.CORPORACITY_DRAWBACK_NOTIFY
						|| doInfo.getTransactionTypeId() == SECConstant.BusinessType.ENTRUST_FINANCING.INCOME_DRAWBACK_NOTIFY
						|| doInfo.getTransactionTypeId() == SECConstant.BusinessType.ENTRUST_FINANCING.SECURITIES_PAYMENT_NOTIFY
						|| doInfo.getTransactionTypeId() == SECConstant.BusinessType.ENTRUST_FINANCING.SECURITIES_DRAWBACK_NOTIFY){
						accountType = SECConstant.EntryAccountType.AccountType_14;
					}else if(doInfo.getTransactionTypeId() == SECConstant.BusinessType.ENTRUSTED_FINANCING.ENTRUSTED_FINANCING
						|| doInfo.getTransactionTypeId() == SECConstant.BusinessType.ENTRUSTED_FINANCING.CORPORACITY_DRAWBACK_NOTIFY
						|| doInfo.getTransactionTypeId() == SECConstant.BusinessType.ENTRUSTED_FINANCING.CORPORACITY_PAYMENT_NOTIFY
						|| doInfo.getTransactionTypeId() == SECConstant.BusinessType.ENTRUSTED_FINANCING.INCOME_PAYMENT_NOTIFY){
						accountType = SECConstant.EntryAccountType.AccountType_15;
					}else if(doInfo.getTransactionTypeId() == SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.FOREIGN_CURRENCY_INVESTMENT
							|| doInfo.getTransactionTypeId() == SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.INVESTMENT_PAYMENT_NOTIFY
							|| doInfo.getTransactionTypeId() == SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.INVESTMENT_DRAWBACK_NOTIFY
							|| doInfo.getTransactionTypeId() == SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.INCOME_DRAWBACK_NOTIFY){
						accountType = SECConstant.EntryAccountType.AccountType_12;
					}else if(doInfo.getTransactionTypeId() == SECConstant.BusinessType.STOCK_INVESTMENT.ID){
						accountType = SECConstant.EntryAccountType.AccountType_16;
					}else if(doInfo.getTransactionTypeId() == SECConstant.BusinessType.BOND_UNDERWRITING.BOND_UNDERWRITING
						|| doInfo.getTransactionTypeId() == SECConstant.BusinessType.BOND_UNDERWRITING.UNDERWRITING_DRAWBACK_NOTIFY
						|| doInfo.getTransactionTypeId() == SECConstant.BusinessType.BOND_UNDERWRITING.UNDERWRITING_PAYMENT_NOTIFY
						|| doInfo.getTransactionTypeId() == SECConstant.BusinessType.BOND_UNDERWRITING.INCOME_DRAWBACK_NOTIFY){
						accountType = SECConstant.EntryAccountType.AccountType_13;
						SecuritiesContractDao scDAO = new SecuritiesContractDao(transConn);
						SecuritiesContractInfo scInfo = scDAO.findContractInfoByDeliveryOrderID(doInfo.getId());
						gleParam.setSubTransactionType(scInfo.getInterestTypeId());
					}else if(doInfo.getTransactionTypeId() == SECConstant.BusinessType.INSURANCE.ID){
						accountType = SECConstant.EntryAccountType.AccountType_17;
					}
					
					
					gleParam.setAccountType(accountType);
					if(doInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN
					|| doInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT
					||doInfo.getTransactionTypeId() == SECConstant.BusinessType.BANK_BOND_REPURCHASE.BOND_REPURCHASE
					|| doInfo.getTransactionTypeId() == SECConstant.BusinessType.BANK_BOND_REPURCHASE.FUND_REPURCHASE
					||doInfo.getTransactionTypeId() == SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.FUND_REPURCHASE
					||doInfo.getTransactionTypeId() == SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.BOND_REPURCHASE){
						log.debug("---------回购-----------");						
						gleParam.setNetIncome(doInfo.getNetIncome());
						gleParam.setPrincipal(doInfo.getAmount());
						//gleParam.setCommission(Math.abs(doInfo.getNetIncome()-doInfo.getAmount()));
						gleParam.setCommission(doInfo.getTax());
					}else{
						log.debug("--------到期购回------------");									
						gleParam.setNetIncome(doInfo.getNetIncome());
						gleParam.setPrincipal(doInfo.getAmount());
						//gleParam.setInterest(Math.abs(doInfo.getNetIncome()-doInfo.getAmount()));				
						gleParam.setInterest(doInfo.getMaturityAmount()-doInfo.getAmount());
						gleParam.setCommission(doInfo.getTax());
					}	
					log.debug("---------------CAPITAL_REPURCHASE:getTransactionTypeId--------"+doInfo.getTransactionTypeId());
					if(doInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_REPURCHASE.INTEREST_PAYMENT){
						gleParam.setInterest(doInfo.getInterest());
						gleParam.setSuspenseInterest(doInfo.getSuspenseInterest());
						log.debug("---------------CAPITAL_REPURCHASE--------");
					}
		
				}else{
				
					SEC_SecuritiesDAO secDAO = new SEC_SecuritiesDAO(transConn);
					SecuritiesInfo secInfo = null;
					try {
						secInfo = (SecuritiesInfo) secDAO.findByID(doInfo.getSecuritiesId(), SecuritiesInfo.class);
					} catch (ITreasuryDAOException e) {
						throw new SecuritiesDAOException(e);
					}
					if(secInfo == null || secInfo.getId() < 0 || secInfo.getStatusID() != SECConstant.SecuritiesStatus.CHECKED)
						throw new SECInvalidRecordException("证券类型");
					
					log.debug("----------证券类型: "+ secInfo);					
					long accountTypeID = -1;
					if(secInfo.getTypeID() == SECConstant.SecuritiesType.BANK_NATIONAL_BOND
					|| secInfo.getTypeID() == SECConstant.SecuritiesType.POLICY_RELATED_FINANCIAL_BOND
					|| secInfo.getTypeID() == SECConstant.SecuritiesType.FINANCIAL_BOND
					||secInfo.getTypeID() == SECConstant.SecuritiesType.CENTRAL_BANK_NOTE){//银行间国债现券/政策性金融债现券/金融债现券/央行票据现券
						accountTypeID = SECConstant.EntryAccountType.AccountType_5;//帐务类型=银行间证券现券
						log.debug("---------------1-------");
					}else if(secInfo.getTypeID() == SECConstant.SecuritiesType.MUTUAL_FUND){
						accountTypeID = SECConstant.EntryAccountType.AccountType_9; //帐务类型=开放式基金
					}else if(secInfo.getTypeID() == SECConstant.SecuritiesType.EXCHANGECENTER_BOND_REPURCHASE
						|| secInfo.getTypeID() == SECConstant.SecuritiesType.EXCHANGECENTER_ENTERPRISE_BOND)
					{
						log.debug("---------------2-------");						
						if(doInfo.getTransactionTypeId() == SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.FUND_REPAY
								||doInfo.getTransactionTypeId() == SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.FUND_REPURCHASE){
							log.debug("---------------3-------");							
							accountTypeID = SECConstant.EntryAccountType.AccountType_7; //融资回购
						}else if(doInfo.getTransactionTypeId() == SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.BOND_REPAY
								||doInfo.getTransactionTypeId() == SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.BOND_REPURCHASE){
							log.debug("---------------4-------");							
							accountTypeID = SECConstant.EntryAccountType.AccountType_8; //融券回购
						}						
					}
					else{
						log.debug("---------------5-------");						
						accountTypeID = SECConstant.EntryAccountType.AccountType_6;  //交易所证券现券
					}

					
					TransactionTypeInfo transTypeInfo = null;
					try {
						transTypeInfo = SECConstant.BusinessType.getTransactionTypeInfoByID(doInfo.getTransactionTypeId());
					} catch (ITreasuryDAOException e) {
						throw new SecuritiesDAOException(e);
					}
					
					if(doInfo.getTransactionTypeId() == SECConstant.BusinessType.CARRY_COST.CARRY_COST){
						//if(Env.getProjectName().compareToIgnoreCase(Constant.ProjectName.CNMEF) == 0){
						//	gleParam.setInterest(UtilOperation.Arith.round(doInfo.getQuantity() * doInfo.getUnitProfitLoss(),2));
						//}else
							gleParam.setInterest(UtilOperation.Arith.round(doInfo.getQuantity() * (doInfo.getUnitCost() - doInfo.getUnitNetCost()),2));
						gleParam.setPrincipal(UtilOperation.Arith.round(doInfo.getQuantity()*doInfo.getUnitCost(),2));
						gleParam.setNetPrincipal(UtilOperation.Arith.round(doInfo.getQuantity()*doInfo.getUnitNetCost(),2));
					}else{
						if(doInfo.getTransactionTypeId() == SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.FUND_REPAY
						|| doInfo.getTransactionTypeId() == SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.BOND_REPAY)
							gleParam.setInterest(doInfo.getMaturityAmount()-doInfo.getAmount());
						else{
							//利息=交割单上的预付款利息（针对开放式基金）
							gleParam.setInterest(doInfo.getInterest());
						}
						
						if(doInfo.getTransactionTypeId() == SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.BOND_BUYIN
						|| doInfo.getTransactionTypeId() == SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.BOND_SELL
						|| doInfo.getTransactionTypeId() == SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_BUYIN
						|| doInfo.getTransactionTypeId() == SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_SELL)
							gleParam.setSuspenseInterest(doInfo.getPerSuspenseInterest()*doInfo.getQuantity());
						else//应计利息=交割单上的应计利息总额
							gleParam.setSuspenseInterest(doInfo.getSuspenseInterest());
						
						gleParam.setPrincipal(doInfo.getAmount());						
						
					}
						
						gleParam.setNetIncome(doInfo.getNetIncome());

						gleParam.setAccountType(accountTypeID);
						//证券销售/差价收入=实际收付-成本
						gleParam.setMargin(doInfo.getNetIncome()-gleParam.getPrincipal());
						
						gleParam.setCommission(Math.abs(doInfo.getTax()));
						//}			
						
						gleParam.setNetPrincipal(doInfo.getNetPrice()*doInfo.getQuantity());
				}

				SecuritiesGeneralLedgerOperation gleOperation = new SecuritiesGeneralLedgerOperation(transConn);
				gleOperation.generateGLEntry(gleParam);
				DeliveryOrderInfo updatedDO = new DeliveryOrderInfo();				
				updatedDO.setId(doInfo.getId());
				updatedDO.setIsDelivery(1);
				updateDeliveryOrder(updatedDO);			

		}else{
			log.debug("---------没有产生会计分录--------------");
		}
		log.debug("------结束交割单业务复核::checkDeliveryOrder-----------");		
	}
	
	
	public void cancelCheckDeliveryOrder(DeliveryOrderInfo doInfo) throws  RemoteException,SecuritiesException{
		log.debug("------开始交割单业务取消复核::checkDeliveryOrder-----------");
		
		log.debug("------取消复核的交割单信息为:"+doInfo);
			
		
		RegisterOperation registerOperation = new RegisterOperation(false);
		registerOperation.isAllowCancelCheck(doInfo);
		
			SecuritiesGeneralLedgerOperation gleOperation = new SecuritiesGeneralLedgerOperation(transConn);
			SEC_DeliveryOrderDAO doDAO = new SEC_DeliveryOrderDAO(transConn);
			try {
				doInfo = (DeliveryOrderInfo) doDAO.findByID(doInfo.getId(), DeliveryOrderInfo.class);
				log.debug("------查询到需要取消复核的交割单信息为:"+doInfo);				
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesException("",e);
			}
			if(doInfo != null){				
				gleOperation.deleteGLEntry(doInfo.getCode());
			}

	}	
	
	/**
	 * 更新交割单
	 * */
	private void updateDeliveryOrder(DeliveryOrderInfo doInfo) throws SecuritiesDAOException{
		SEC_DeliveryOrderDAO doDAO = new SEC_DeliveryOrderDAO();
		try {
			doDAO.update(doInfo);
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
	}

}
