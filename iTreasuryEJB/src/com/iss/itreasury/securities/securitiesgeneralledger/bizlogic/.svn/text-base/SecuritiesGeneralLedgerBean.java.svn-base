/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-4-13
 */

package com.iss.itreasury.securities.securitiesgeneralledger.bizlogic;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.securitiesaccount.bizlogic.SecuritiesAccountOperation;
import com.iss.itreasury.securities.securitiesaccount.dataentity.AccountTypeInfo;
import com.iss.itreasury.securities.securitiesgeneralledger.dao.SEC_GLEntryDAO;
import com.iss.itreasury.securities.securitiesgeneralledger.dao.SEC_GLEntryDefinitionDAO;
import com.iss.itreasury.securities.securitiesgeneralledger.dataentity.GLEntryDefinitionInfo;
import com.iss.itreasury.securities.securitiesgeneralledger.dataentity.GLEntryInfo;
import com.iss.itreasury.securities.securitiesgeneralledger.dataentity.GenerateGLEntryParam;
import com.iss.itreasury.securities.securitiesgeneralledger.exception.DebitAndCreditNotBalanceException;
import com.iss.itreasury.securities.util.NameRef;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.securities.util.SECUtil;
import com.iss.itreasury.settlement.account.bizlogic.AccountOperation;
import com.iss.itreasury.settlement.generalledger.bizlogic.GeneralLedgerOperation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log4j;


/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SecuritiesGeneralLedgerBean {
	
	/**
	 * 用于维护事务完整性的数据库连接
	 * */
	private Connection transConn = null;
	
	private Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);	
	
	public SecuritiesGeneralLedgerBean(){
	}
	
	public SecuritiesGeneralLedgerBean(Connection conn){
		transConn = conn;
	}	
	
	
	/**
	 * 交易产生会计分录
	 * @param GenerateGLEntryParam
	 * */
	public void generateGLEntry(GenerateGLEntryParam param) throws SecuritiesException,RemoteException{
		log.debug("-----------开始产生会计分录-----------");	
		log.debug("-----------输入参数: "+param);
		SEC_GLEntryDefinitionDAO gLEntryDefinitionDAO = new SEC_GLEntryDefinitionDAO(transConn);
		log.debug("----------根据交易类型，分录类型查询会计分录定义--------------");
		
		ArrayList gLEntryDefinitions = null;
		try {
			gLEntryDefinitions = (ArrayList) gLEntryDefinitionDAO.findAllByTransactionTypeIDAndEntryType(param.getTransactionType(), param.getEntryType(), param.getSubTransactionType(), param.getCurrencyID(), param.getOfficeID());
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);	
		}
		log.debug("-----查询到" + gLEntryDefinitions.size() + "个会计分录定义----------");
		
		if (gLEntryDefinitions != null && gLEntryDefinitions.size() > 0){
			if(param.getTransactionType() == SECConstant.BusinessType.CARRY_COST.CARRY_COST && gLEntryDefinitions.size() == 2){
				//结转成本交易 如果分录设置 仅生成利息的分录，如果利息为0，则退出结转成本分录产生
				GLEntryDefinitionInfo tmp1 = (GLEntryDefinitionInfo) gLEntryDefinitions.get(0);		
				GLEntryDefinitionInfo tmp2 = (GLEntryDefinitionInfo) gLEntryDefinitions.get(1);
				if(param.getInterest() == 0.0 && tmp1.getAmountType() == SECConstant.AmountType.AmountType_3 && tmp2.getAmountType() == SECConstant.AmountType.AmountType_3)
					return;
			}
			
			
			for (int i = 0; i < gLEntryDefinitions.size(); i++){
				GLEntryDefinitionInfo tmp = (GLEntryDefinitionInfo) gLEntryDefinitions.get(i);		
				log.debug("--------会计分录定义" + i + "是:"+tmp);
				
				double dAmount = 0.0;
				log.debug("---------金额类型是: " + tmp.getAmountType() + "------------");
				switch ((int) tmp.getAmountType())
				{
					case SECConstant.AmountType.AmountType_1 :
						{
							dAmount = param.getNetIncome();
						}
						break;
					case SECConstant.AmountType.AmountType_2 :
						{
							dAmount = param.getPrincipal();
						}
						break;
					case SECConstant.AmountType.AmountType_3 :
						{
							dAmount = param.getInterest();
						}
						break;
					case SECConstant.AmountType.AmountType_4 :
						{
							dAmount = param.getMargin();
						}
						break;
					case SECConstant.AmountType.AmountType_5 :
						{
							dAmount = param.getSuspenseInterest();
						}
						break;
					case SECConstant.AmountType.AmountType_6 :
						{
							dAmount = param.getUnrealizedGain();
						}
						break;
					case SECConstant.AmountType.AmountType_7 :
						{
							dAmount = param.getCommission();
						}
						break;
					case SECConstant.AmountType.AmountType_8 :
						{
							dAmount = param.getDiscount();
						}
						break;
					case SECConstant.AmountType.AmountType_9 :
						{
							dAmount = param.getPremium();
						}
						break;
					case SECConstant.AmountType.AmountType_10 :
						{
							dAmount = param.getStampDuty();
						}
						break;
					case SECConstant.AmountType.AmountType_11 :
						{
							dAmount = param.getBusinessTax();
						}
						break;
					case SECConstant.AmountType.AmountType_12 :
						{
							dAmount = param.getOverdueFine();
						}
						break;
					case SECConstant.AmountType.AmountType_13 :
						{
							dAmount = param.getStockJobberNetIncome();
						}
						break;
					case SECConstant.AmountType.AmountType_14 :
							{
								dAmount = param.getStockExchangeNetIncome();
							}
							break;
					case SECConstant.AmountType.AmountType_15 :
					{
						dAmount = param.getVentureCapital();
					}
					case SECConstant.AmountType.AmountType_16 :
					{
						dAmount = param.getNetPrincipal();
					}					
					break;	
					default:
						throw new SecuritiesException("Sec_E141",null);
				}
				log.debug("---------金额是: " + dAmount + "------------");
				if (dAmount == 0.0)
					continue;	
				
				AccountTypeInfo accType = null;
				SecuritiesAccountOperation accOperation = new SecuritiesAccountOperation(false);	
				if(tmp.getSubjectType() != SECConstant.EntrySubjectType.SUBJECT_TYPE_1
				&& tmp.getSubjectType() != SECConstant.EntrySubjectType.SUBJECT_TYPE_2
				&& tmp.getSubjectType() != SECConstant.EntrySubjectType.SUBJECT_TYPE_31
				&& tmp.getSubjectType() != SECConstant.EntrySubjectType.SUBJECT_TYPE_32
				&& tmp.getSubjectType() != SECConstant.EntrySubjectType.SUBJECT_TYPE_99){
					if(SECConstant.EntrySubjectType.getSubjectTypeDirection((int)tmp.getSubjectType()) == SECConstant.Direction.PAY){
						accType = getSubjectCodeBySurities(param.getOfficeID(), param.getCurrencyID(), param.getAccountType(), param.getTermTypeID(), param.getTerm(), param.getPayCounterpartID(), param.getPayClientID(), -1, param.getSeatCode(), param.getPayCapitalAccountID(), param.getPaySecuritiesID(), tmp.getSubjectType());					
					}else{
						accType = getSubjectCodeBySurities(param.getOfficeID(), param.getCurrencyID(), param.getAccountType(), param.getTermTypeID(), param.getTerm(), param.getReceiveCounterpartID(), param.getReceiveClientID(), -1, param.getSeatCode(), param.getReceiveCapitalAccountID(), param.getReceiveSecuritiesID(), tmp.getSubjectType());					
					}
				}

				String sujectCode = null;
				switch((int)tmp.getSubjectType()){
					case SECConstant.EntrySubjectType.SUBJECT_TYPE_1:{
						sujectCode = accOperation.getAccountSubjectCode(param.getPayCapitalAccountID());
					}
					break;
					case SECConstant.EntrySubjectType.SUBJECT_TYPE_2:{
						sujectCode = accOperation.getAccountSubjectCode(param.getReceiveCapitalAccountID());						
					}
					break;
					case SECConstant.EntrySubjectType.SUBJECT_TYPE_3:
					case SECConstant.EntrySubjectType.SUBJECT_TYPE_4:{
						sujectCode = accType.getPrincipalSubject();						
					}
					break;
					case SECConstant.EntrySubjectType.SUBJECT_TYPE_5:
					case SECConstant.EntrySubjectType.SUBJECT_TYPE_6:{
						sujectCode = accType.getInterestSubject();
					}
					break;
					case SECConstant.EntrySubjectType.SUBJECT_TYPE_7:
					case SECConstant.EntrySubjectType.SUBJECT_TYPE_8:{
						sujectCode = accType.getMarginSubject();
					}
					break;
					case SECConstant.EntrySubjectType.SUBJECT_TYPE_9:
					case SECConstant.EntrySubjectType.SUBJECT_TYPE_10:{
						sujectCode = accType.getSuspenseInterestSubject();
					}
					break;
					case SECConstant.EntrySubjectType.SUBJECT_TYPE_11:
					case SECConstant.EntrySubjectType.SUBJECT_TYPE_12:{
						sujectCode = accType.getUnrealizedGainSubject();
					}
					break;
					case SECConstant.EntrySubjectType.SUBJECT_TYPE_13:
					case SECConstant.EntrySubjectType.SUBJECT_TYPE_14:{
						sujectCode = accType.getCommissionSubject();						
					}
					break;
					case SECConstant.EntrySubjectType.SUBJECT_TYPE_15:
					case SECConstant.EntrySubjectType.SUBJECT_TYPE_16:{
						sujectCode = accType.getDiscountSubject();
					}
					break;
					case SECConstant.EntrySubjectType.SUBJECT_TYPE_17:
					case SECConstant.EntrySubjectType.SUBJECT_TYPE_18:{
						sujectCode = accType.getPremiumSubject();
					}
					break;
					case SECConstant.EntrySubjectType.SUBJECT_TYPE_19:
					case SECConstant.EntrySubjectType.SUBJECT_TYPE_20:{
						sujectCode = accType.getDepreciateSubject();
					}
					break;
					case SECConstant.EntrySubjectType.SUBJECT_TYPE_21:
					case SECConstant.EntrySubjectType.SUBJECT_TYPE_22:{
						sujectCode = accType.getStampDutySubject();
					}
					break;
					case SECConstant.EntrySubjectType.SUBJECT_TYPE_23:
					case SECConstant.EntrySubjectType.SUBJECT_TYPE_24:{
						sujectCode = accType.getBusinessTaxSubject();
					}
					break;
					case SECConstant.EntrySubjectType.SUBJECT_TYPE_25:
					case SECConstant.EntrySubjectType.SUBJECT_TYPE_26:{
						sujectCode = accType.getOverdueFineSubject();
					}
					break;
					case SECConstant.EntrySubjectType.SUBJECT_TYPE_27:
					case SECConstant.EntrySubjectType.SUBJECT_TYPE_28:{
						sujectCode = accType.getReserveCapitalSubject();
					}
					break;
					case SECConstant.EntrySubjectType.SUBJECT_TYPE_29:
					case SECConstant.EntrySubjectType.SUBJECT_TYPE_30:{
						sujectCode = accType.getSuspenseSubject();	
					}
					break;
					case SECConstant.EntrySubjectType.SUBJECT_TYPE_31:{
						AccountOperation settlmentAccountOperation = new AccountOperation(-1);
						try {
							sujectCode = settlmentAccountOperation.getSubjectByBankID(param.getPayBankID());
						} catch (RemoteException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						} catch (IRollbackException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}	
					}
					break;
					case SECConstant.EntrySubjectType.SUBJECT_TYPE_32:{
						AccountOperation settlmentAccountOperation = new AccountOperation(-1);
						try {
							sujectCode = settlmentAccountOperation.getSubjectByBankID(param.getReceiveBankID());
						} catch (RemoteException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						} catch (IRollbackException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}		
					}										
					break;
					case SECConstant.EntrySubjectType.SUBJECT_TYPE_33:
					case SECConstant.EntrySubjectType.SUBJECT_TYPE_35:{
						sujectCode = accType.getPayableSubject();	
					}
					break;
					case SECConstant.EntrySubjectType.SUBJECT_TYPE_34:
					case SECConstant.EntrySubjectType.SUBJECT_TYPE_36:{
						sujectCode = accType.getReceivableSubject();
					}
					break;					
					case SECConstant.EntrySubjectType.SUBJECT_TYPE_99:{
						sujectCode = tmp.getSubjectCode();
					}
					break;					

				}
				log.debug("--------科目号是: "+sujectCode+"------------");
				
				checkSubjectValidity(sujectCode, param.getPayCounterpartID(), param.getPayCapitalAccountID(), param.getPaySecuritiesID());
				
				GLEntryInfo gleInfo = new GLEntryInfo();
				dAmount = SECUtil.Arith.round(dAmount,2);
				gleInfo.setAmount(dAmount);
				gleInfo.setCheckUserID(param.getCheckUserID());
				gleInfo.setCurrencyID(param.getCurrencyID());
				gleInfo.setDeliveryOrderCode(param.getDeliverOrderCode());
				gleInfo.setExecuteDate(param.getExecuteDate());
				gleInfo.setInputUserID(param.getInputUserID());
				gleInfo.setInterestStartDate(param.getDeliveryDate());
				//gleInfo.setMultiCode()
				gleInfo.setOfficeID(param.getOfficeID());
				gleInfo.setPostStatusID(SECConstant.PostEntryStatus.NotPost);
				//gleInfo.setRemark(param.get)
				gleInfo.setStatusID(SECConstant.TransactionStatus.CHECK);
				gleInfo.setSubjectCode(sujectCode);
				gleInfo.setTransactionTypeID(param.getTransactionType());
				gleInfo.setTransDirection(tmp.getDirection());
				gleInfo.setRemark(param.getAbstractStr());
				//gleInfo.setType(serialVersionUID)
				SEC_GLEntryDAO gleDAO = new SEC_GLEntryDAO(transConn);	
				try {
					gleDAO.add(gleInfo);
				} catch (ITreasuryDAOException e1) {
					throw new SecuritiesDAOException(e1);
				}
			}//end for
			//检查借贷是否平衡
			checkTransDCBalance(param.getDeliverOrderCode());
		}else{
			throw new SecuritiesException("Sec_E145",null);
		}		
		log.debug("-----------结束产生会计分录-----------");
	}
	
	/**
	 * 交易删除会计分录
	 * @param 交割单ID
	 * */
	public void deleteGLEntry(String deliverOrderCode) throws SecuritiesException,RemoteException{
		log.debug("----------取消复核的交割单号为:"+deliverOrderCode	);
		SEC_GLEntryDAO gleDAO = new SEC_GLEntryDAO(transConn);
		GLEntryInfo gleInfo = new GLEntryInfo();
		gleInfo.setStatusID(SECConstant.TransactionStatus.CHECK);
		gleInfo.setDeliveryOrderCode(deliverOrderCode);
		
		Collection c = null;
		try {
			c = gleDAO.findByCondition(gleInfo);
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}	
		Iterator it = c.iterator();
		while(it.hasNext()){
			GLEntryInfo tmp = (GLEntryInfo) it.next();
			try {
				gleDAO.delete(tmp.getId());
			} catch (ITreasuryDAOException e1) {
				throw new SecuritiesDAOException(e1);
			}
		}
	}
	
	/**
	 * 交易删除会计分录
	 * @param 交割单ID
	 * */
	public void deleteGLEntry(String contractCode,Timestamp interestStartDate) throws SecuritiesException,RemoteException{
		log.debug("----------取消复核的合同号为:"+contractCode	);
		SEC_GLEntryDAO gleDAO = new SEC_GLEntryDAO(transConn);
		GLEntryInfo gleInfo = new GLEntryInfo();
		gleInfo.setStatusID(SECConstant.TransactionStatus.CHECK);
		gleInfo.setDeliveryOrderCode(contractCode);
		gleInfo.setInterestStartDate(interestStartDate);
		
		Collection c = null;
		try {
			c = gleDAO.findByCondition(gleInfo);
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}	
		Iterator it = c.iterator();
		while(it.hasNext()){
			GLEntryInfo tmp = (GLEntryInfo) it.next();
			try {
				gleDAO.delete(tmp.getId());
			} catch (ITreasuryDAOException e1) {
				throw new SecuritiesDAOException(e1);
			}
		}
	}	
	
	/**
	 * 检查借贷是否平衡
	 * @param 交割单ID
	 * */
	private void checkTransDCBalance(String deliverOrderCode) throws DebitAndCreditNotBalanceException, SecuritiesDAOException{
		SEC_GLEntryDAO gleDAO = new SEC_GLEntryDAO();
		GLEntryInfo gleInfo = new GLEntryInfo();
		gleInfo.setStatusID(SECConstant.TransactionStatus.CHECK);
		gleInfo.setDeliveryOrderCode(deliverOrderCode);
		
		Collection c = null;
		try {
			c = gleDAO.findByCondition(gleInfo);
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		if (c == null || c.size() == 0)
			throw new DebitAndCreditNotBalanceException();
		
		Iterator i = c.iterator();
		double lendAmount = 0.0;
		double loanAmount = 0.0;
		while (i.hasNext())
		{
			GLEntryInfo gLEntryInfo = (GLEntryInfo) i.next();
			long transDirection = gLEntryInfo.getTransDirection();
			if ((int) transDirection == 1)					//			2.	累计借方金额 = Sum(sett_GLEntry.mAmount) where  sett_GLEntry.nTransDirection = 1；
				lendAmount += gLEntryInfo.getAmount();
			else if ((int) transDirection == 2)					//			累计贷方金额 = Sum(sett_GLEntry.mAmount) where  sett_GLEntry.nTransDirection = 2；  
				loanAmount += gLEntryInfo.getAmount();
		}
		//		3.	如果累计借方金额<>累计贷方金额，则返回0，否则返回1。
		log.debug("----借方累计金额" + lendAmount + "---------");
		log.debug("----贷方累计金额" + loanAmount + "---------");		
		
		if(!SECUtil.Arith.equal(lendAmount, loanAmount, 4))
			throw new DebitAndCreditNotBalanceException();
		
	}	
	
	private AccountTypeInfo getSubjectCodeBySurities(long officeID,
			long currencyID,
			long accountType,
			long termTypeID,
			long term,
			long counterpartID,
			long clientID,
			long exchangeCenterCode,
			String seatCode,
			long accountID,
			long securitiesID,
			long subjectType) throws RemoteException, SecuritiesException{
		SecuritiesAccountOperation accOperation = new SecuritiesAccountOperation(false);
		String counterpartName = NameRef.getCounterpartNameByID(counterpartID);
		String accountNo = NameRef.getAccountNobyAccountID(accountID);
		String secritiesCode = NameRef.getSecuritiesCodeByID(securitiesID);		
		String subjectCode =  SECConstant.EntrySubjectType.getName(subjectType);
		String[] strs = {counterpartName,accountNo,secritiesCode,subjectCode};
		
		ArrayList list = (ArrayList) accOperation.getSecuritiesSubjectCode(officeID, currencyID, accountType, termTypeID, term, counterpartID, clientID, exchangeCenterCode, seatCode, accountID, securitiesID, subjectType);
		log.debug("list.size()"+list.size());
		if(list == null || list.size() == 0){
			//Sec_E142=交易对手[?]资金帐户[?]证券[?]的科目[?]未设置
			throw new SecuritiesException("Sec_E142",strs,null);
		}else if(list.size() > 1){
			//Sec_E143=交易对手[?]资金帐户[?]证券[?]的科目[?]不唯一
			throw new SecuritiesException("Sec_E143",strs,null);
		}else{
			return (AccountTypeInfo) list.get(0);
		}
	}	
	
	
	/**
	 * 校验科目是否有效
	 * */
	private void checkSubjectValidity(String strSubject,
			long counterpartID,
			long accountID,
			long securitiesID) throws SecuritiesException{
		log.debug("-----------开始校验科目号合法性----------------");
		String counterpartName = NameRef.getCounterpartNameByID(counterpartID);
		String accountNo = NameRef.getAccountNobyAccountID(accountID);
		String secritiesCode = NameRef.getSecuritiesCodeByID(securitiesID);	
		String[] strs = {counterpartName,accountNo,secritiesCode,strSubject};
		if (strSubject == null || strSubject.trim().compareToIgnoreCase("") == 0)
		{
			throw new SecuritiesException("Sec_E142",strs,null);
		}

		GeneralLedgerOperation glOperation = new GeneralLedgerOperation(transConn);
		try {
			//GLSubjectDefinitionInfo glSubjectDefInfo1 = glOperation.findBySubjectOldCode(strSubject);
			//GLSubjectDefinitionInfo glSubjectDefInfo2 = glOperation.findBySubjectCode(strSubject);
			if (!glOperation.isExistSubeject(strSubject, transConn))
				throw new SecuritiesException("Sec_E144",strs,null);
		}  catch (IException e) {
			throw new SecuritiesException("Sec_E144",strs,null);
		}
	}
}
