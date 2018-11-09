/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.securities.securitiesgeneralledger.bizlogic;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.SessionBean;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.securitiesgeneralledger.dao.SEC_GLEntryDAO;
import com.iss.itreasury.securities.securitiesgeneralledger.dataentity.GLEntryInfo;
import com.iss.itreasury.securities.securitiesgeneralledger.dataentity.GenerateGLEntryParam;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SecuritiesGeneralLedgerEJB implements SessionBean
{
	private javax.ejb.SessionContext mySessionCtx = null;
	final static long serialVersionUID = 3206093459760846163L;
	private Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);	
	/**
	 * ejbActivate method comment
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbActivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbCreate method comment
	 * @exception javax.ejb.CreateException 异常说明。
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbCreate() throws javax.ejb.CreateException, java.rmi.RemoteException
	{
	}
	/**
	 * ejbPassivate method comment
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbPassivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbRemove method comment
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbRemove() throws java.rmi.RemoteException
	{
	}
	/**
	 * getSessionContext method comment
	 * @return javax.ejb.SessionContext
	 */
	public javax.ejb.SessionContext getSessionContext()
	{
		return mySessionCtx;
	}
	/**
	 * setSessionContext method comment
	 * @param ctx javax.ejb.SessionContext
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx) throws java.rmi.RemoteException
	{
		mySessionCtx = ctx;
	}
	
	/**
	 * 交易产生会计分录
	 * @param GenerateGLEntryParam
	 * */
	public void generateGLEntry(GenerateGLEntryParam param) throws SecuritiesException,RemoteException{
//		log.debug("-----------开始产生会计分录-----------");	
//		log.debug("-----------输入参数: "+param);
//		SEC_GLEntryDefinitionDAO gLEntryDefinitionDAO = new SEC_GLEntryDefinitionDAO();
//		log.debug("----------根据交易类型，分录类型查询会计分录定义--------------");
//		
//		ArrayList gLEntryDefinitions = null;
//		try {
//			gLEntryDefinitions = (ArrayList) gLEntryDefinitionDAO.findAllByTransactionTypeIDAndEntryType(param.getTransactionType(), param.getEntryType(), param.getSubTransactionType(), 1, param.getOfficeID());
//		} catch (ITreasuryDAOException e) {
//			throw new SecuritiesDAOException(e);	
//		}
//		log.debug("-----查询到" + gLEntryDefinitions.size() + "个会计分录定义----------");
//		if (gLEntryDefinitions != null && gLEntryDefinitions.size() > 0){
//			for (int i = 0; i < gLEntryDefinitions.size(); i++){
//				GLEntryDefinitionInfo tmp = (GLEntryDefinitionInfo) gLEntryDefinitions.get(i);		
//				log.debug("--------会计分录定义" + i + "是:"+tmp);
//				
//				double dAmount = 0.0;
//				log.debug("---------金额类型是: " + tmp.getAmountType() + "------------");
//				switch ((int) tmp.getAmountType())
//				{
//					case SECConstant.AmountType.AmountType_1 :
//						{
//							dAmount = param.getNetIncome();
//						}
//						break;
//					case SECConstant.AmountType.AmountType_2 :
//						{
//							dAmount = param.getPrincipal();
//						}
//						break;
//					case SECConstant.AmountType.AmountType_3 :
//						{
//							dAmount = param.getInterest();
//						}
//						break;
//					case SECConstant.AmountType.AmountType_4 :
//						{
//							dAmount = param.getMargin();
//						}
//						break;
//					case SECConstant.AmountType.AmountType_5 :
//						{
//							dAmount = param.getSuspenseInterest();
//						}
//						break;
//					case SECConstant.AmountType.AmountType_6 :
//						{
//							dAmount = param.getUnrealizedGain();
//						}
//						break;
//					case SECConstant.AmountType.AmountType_7 :
//						{
//							dAmount = param.getCommission();
//						}
//						break;
//					case SECConstant.AmountType.AmountType_8 :
//						{
//							dAmount = param.getDiscount();
//						}
//						break;
//					case SECConstant.AmountType.AmountType_9 :
//						{
//							dAmount = param.getPremium();
//						}
//						break;
//					case SECConstant.AmountType.AmountType_10 :
//						{
//							dAmount = param.getStampDuty();
//						}
//						break;
//					case SECConstant.AmountType.AmountType_11 :
//						{
//							dAmount = param.getBusinessTax();
//						}
//						break;
//					case SECConstant.AmountType.AmountType_12 :
//						{
//							dAmount = param.getOverdueFine();
//						}
//						break;
//					case SECConstant.AmountType.AmountType_13 :
//						{
//							dAmount = param.getStockJobberNetIncome();
//						}
//						break;
//					case SECConstant.AmountType.AmountType_14 :
//							{
//								dAmount = param.getStockExchangeNetIncome();
//							}
//							break;
//					case SECConstant.AmountType.AmountType_15 :
//					{
//						dAmount = param.getVentureCapital();
//					}
//					break;	
//					default:
//						throw new SecuritiesException("Sec_E141",null);
//				}
//				log.debug("---------金额是: " + dAmount + "------------");
//				if (dAmount == 0.0)
//					continue;	
//				
//				AccountTypeInfo accType = null;
//				SecuritiesAccountOperation accOperation = new SecuritiesAccountOperation(false);	
//				if(tmp.getSubjectType() != SECConstant.EntrySubjectType.SUBJECT_TYPE_1
//				&& tmp.getSubjectType() != SECConstant.EntrySubjectType.SUBJECT_TYPE_2){
//					if(SECConstant.EntrySubjectType.getSubjectTypeDirection((int)tmp.getSubjectType()) == SECConstant.Direction.PAY){
//						accType = getSubjectCodeBySurities(param.getOfficeID(), param.getCurrencyID(), param.getAccountType(), param.getTermTypeID(), param.getTerm(), param.getPayCounterpartID(), param.getPayClientID(), -1, param.getSeatCode(), param.getPayCapitalAccountID(), param.getPaySecuritiesID(), tmp.getSubjectType());					
//					}else{
//						accType = getSubjectCodeBySurities(param.getOfficeID(), param.getCurrencyID(), param.getAccountType(), param.getTermTypeID(), param.getTerm(), param.getReceiveCounterpartID(), param.getReceiveClientID(), -1, param.getSeatCode(), param.getReceiveCapitalAccountID(), param.getReceiveSecuritiesID(), tmp.getSubjectType());					
//					}
//				}
//
//				String sujectCode = null;
//				switch((int)tmp.getSubjectType()){
//					case SECConstant.EntrySubjectType.SUBJECT_TYPE_1:{
//						sujectCode = accOperation.getAccountSubjectCode(param.getPayCapitalAccountID());
//					}
//					break;
//					case SECConstant.EntrySubjectType.SUBJECT_TYPE_2:{
//						sujectCode = accOperation.getAccountSubjectCode(param.getReceiveCapitalAccountID());						
//					}
//					break;
//					case SECConstant.EntrySubjectType.SUBJECT_TYPE_3:
//					case SECConstant.EntrySubjectType.SUBJECT_TYPE_4:{
//						sujectCode = accType.getPrincipalSubject();						
//					}
//					break;
//					case SECConstant.EntrySubjectType.SUBJECT_TYPE_5:
//					case SECConstant.EntrySubjectType.SUBJECT_TYPE_6:{
//						sujectCode = accType.getInterestSubject();
//					}
//					break;
//					case SECConstant.EntrySubjectType.SUBJECT_TYPE_7:
//					case SECConstant.EntrySubjectType.SUBJECT_TYPE_8:{
//						sujectCode = accType.getMarginSubject();
//					}
//					break;
//					case SECConstant.EntrySubjectType.SUBJECT_TYPE_9:
//					case SECConstant.EntrySubjectType.SUBJECT_TYPE_10:{
//						sujectCode = accType.getSuspenseInterestSubject();
//					}
//					break;
//					case SECConstant.EntrySubjectType.SUBJECT_TYPE_11:
//					case SECConstant.EntrySubjectType.SUBJECT_TYPE_12:{
//						sujectCode = accType.getUnrealizedGainSubject();
//					}
//					break;
//					case SECConstant.EntrySubjectType.SUBJECT_TYPE_13:
//					case SECConstant.EntrySubjectType.SUBJECT_TYPE_14:{
//						sujectCode = accType.getCommissionSubject();						
//					}
//					break;
//					case SECConstant.EntrySubjectType.SUBJECT_TYPE_15:
//					case SECConstant.EntrySubjectType.SUBJECT_TYPE_16:{
//						sujectCode = accType.getDiscountSubject();
//					}
//					break;
//					case SECConstant.EntrySubjectType.SUBJECT_TYPE_17:
//					case SECConstant.EntrySubjectType.SUBJECT_TYPE_18:{
//						sujectCode = accType.getPremiumSubject();
//					}
//					break;
//					case SECConstant.EntrySubjectType.SUBJECT_TYPE_19:
//					case SECConstant.EntrySubjectType.SUBJECT_TYPE_20:{
//						sujectCode = accType.getDepreciateSubject();
//					}
//					break;
//					case SECConstant.EntrySubjectType.SUBJECT_TYPE_21:
//					case SECConstant.EntrySubjectType.SUBJECT_TYPE_22:{
//						sujectCode = accType.getStampDutySubject();
//					}
//					break;
//					case SECConstant.EntrySubjectType.SUBJECT_TYPE_23:
//					case SECConstant.EntrySubjectType.SUBJECT_TYPE_24:{
//						sujectCode = accType.getBusinessTaxSubject();
//					}
//					break;
//					case SECConstant.EntrySubjectType.SUBJECT_TYPE_25:
//					case SECConstant.EntrySubjectType.SUBJECT_TYPE_26:{
//						sujectCode = accType.getOverdueFineSubject();
//					}
//					break;
//					case SECConstant.EntrySubjectType.SUBJECT_TYPE_27:
//					case SECConstant.EntrySubjectType.SUBJECT_TYPE_28:{
//						sujectCode = accType.getReserveCapitalSubject();
//					}
//					break;
//					case SECConstant.EntrySubjectType.SUBJECT_TYPE_29:
//					case SECConstant.EntrySubjectType.SUBJECT_TYPE_30:{
//						sujectCode = accType.getSuspenseSubject();	
//					}
//					break;
//					case SECConstant.EntrySubjectType.SUBJECT_TYPE_99:{
//						sujectCode = tmp.getSubjectCode();
//					}
//					break;					
//
//				}
//				log.debug("--------科目号是: "+sujectCode+"------------");
//				
//				GLEntryInfo gleInfo = new GLEntryInfo();
//				gleInfo.setAmount(dAmount);
//				gleInfo.setCheckUserID(param.getCheckUserID());
//				gleInfo.setCurrencyID(param.getCurrencyID());
//				gleInfo.setDeliveryOrderCode(param.getDeliverOrderCode());
//				gleInfo.setExecuteDate(param.getExecuteDate());
//				gleInfo.setInputUserID(param.getInputUserID());
//				gleInfo.setInterestStartDate(param.getDeliveryDate());
//				//gleInfo.setMultiCode()
//				gleInfo.setOfficeID(param.getOfficeID());
//				gleInfo.setPostStatusID(SECConstant.PostEntryStatus.NotPost);
//				//gleInfo.setRemark(param.get)
//				gleInfo.setStatusID(SECConstant.TransactionStatus.CHECK);
//				gleInfo.setSubjectCode(sujectCode);
//				gleInfo.setTransactionTypeID(param.getTransactionType());
//				gleInfo.setTransDirection(tmp.getDirection());
//				//gleInfo.setType(serialVersionUID)
//				SEC_GLEntryDAO gleDAO = new SEC_GLEntryDAO();	
//				try {
//					gleDAO.add(gleInfo);
//				} catch (ITreasuryDAOException e1) {
//					throw new SecuritiesDAOException(e1);
//				}
//			}//end for
//			//检查借贷是否平衡
//			checkTransDCBalance(param.getDeliverOrderCode());
//		}		
//		log.debug("-----------结束产生会计分录-----------");
		SecuritiesGeneralLedgerBean securitiesGeneralLedgerBean = new SecuritiesGeneralLedgerBean();
		securitiesGeneralLedgerBean.generateGLEntry(param);
	}
	
	/**
	 * 交易删除会计分录
	 * @param 交割单ID
	 * */
	public void deleteGLEntry(String deliverOrderCode) throws SecuritiesException,RemoteException{
//		SEC_GLEntryDAO gleDAO = new SEC_GLEntryDAO();
//		GLEntryInfo gleInfo = new GLEntryInfo();
//		gleInfo.setStatusID(SECConstant.TransactionStatus.CHECK);
//		gleInfo.setDeliveryOrderCode(deliverOrderCode);
//		
//		Collection c = null;
//		try {
//			c = gleDAO.findByCondition(gleInfo);
//		} catch (ITreasuryDAOException e) {
//			throw new SecuritiesDAOException(e);
//		}	
//		Iterator it = c.iterator();
//		while(it.hasNext()){
//			GLEntryInfo tmp = (GLEntryInfo) it.next();
//			try {
//				gleDAO.delete(tmp.getId());
//			} catch (ITreasuryDAOException e1) {
//				throw new SecuritiesDAOException(e1);
//			}
//		}
		SecuritiesGeneralLedgerBean securitiesGeneralLedgerBean = new SecuritiesGeneralLedgerBean();
		securitiesGeneralLedgerBean.deleteGLEntry(deliverOrderCode);		
	}
	
//	/**
//	 * 检查借贷是否平衡
//	 * @param 交割单ID
//	 * */
//	private void checkTransDCBalance(String deliverOrderCode) throws DebitAndCreditNotBalanceException, SecuritiesDAOException{
//		SEC_GLEntryDAO gleDAO = new SEC_GLEntryDAO();
//		GLEntryInfo gleInfo = new GLEntryInfo();
//		gleInfo.setStatusID(SECConstant.TransactionStatus.CHECK);
//		gleInfo.setDeliveryOrderCode(deliverOrderCode);
//		
//		Collection c = null;
//		try {
//			c = gleDAO.findByCondition(gleInfo);
//		} catch (ITreasuryDAOException e) {
//			throw new SecuritiesDAOException(e);
//		}
//		if (c == null || c.size() == 0)
//			throw new DebitAndCreditNotBalanceException();
//		
//		Iterator i = c.iterator();
//		double lendAmount = 0.0;
//		double loanAmount = 0.0;
//		while (i.hasNext())
//		{
//			GLEntryInfo gLEntryInfo = (GLEntryInfo) i.next();
//			long transDirection = gLEntryInfo.getTransDirection();
//			if ((int) transDirection == 1)					//			2.	累计借方金额 = Sum(sett_GLEntry.mAmount) where  sett_GLEntry.nTransDirection = 1；
//				lendAmount += gLEntryInfo.getAmount();
//			else if ((int) transDirection == 2)					//			累计贷方金额 = Sum(sett_GLEntry.mAmount) where  sett_GLEntry.nTransDirection = 2；  
//				loanAmount += gLEntryInfo.getAmount();
//		}
//		//		3.	如果累计借方金额<>累计贷方金额，则返回0，否则返回1。
//		log.debug("----借方累计金额" + lendAmount + "---------");
//		log.debug("----贷方累计金额" + loanAmount + "---------");		
//		
//		if(!SECUtil.Arith.equal(lendAmount, loanAmount, 4))
//			throw new DebitAndCreditNotBalanceException();
//		
//	}
	
	/**
	 * “结转成本”产生的所有会计分录中，对机构号ID、币种号、科目代码均相同的分录，
	 * 应该汇总其金额、使之合并成一笔分录
	 * */
	public void combineGLEntryForCarryCost(long officeID,long currencyID,String subjectCode) throws SecuritiesException,RemoteException{
		SEC_GLEntryDAO dao = new SEC_GLEntryDAO();
		GLEntryInfo info = new GLEntryInfo();
		info.setOfficeID(officeID);
		info.setCurrencyID(currencyID);
		info.setSubjectCode(subjectCode);
		Collection c = null;
		try {
			c = dao.findByCondition(info);
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		Iterator it = c.iterator();
		double sumAmount = 0.0;
		GLEntryInfo glEntryInfo = null; 
		while(it.hasNext()){
			glEntryInfo = (GLEntryInfo) it.next();
			sumAmount += glEntryInfo.getAmount();
			try {
				dao.delete(glEntryInfo.getId());
			} catch (ITreasuryDAOException e1) {
				throw new SecuritiesDAOException(e1);
			}
		}
		glEntryInfo.clearUsedFields();
		glEntryInfo.setAmount(sumAmount);
		glEntryInfo.setCheckUserID(glEntryInfo.getCheckUserID());
		glEntryInfo.setCurrencyID(glEntryInfo.getCurrencyID());
		glEntryInfo.setDeliveryOrderCode(glEntryInfo.getDeliveryOrderCode());
		glEntryInfo.setExecuteDate(glEntryInfo.getExecuteDate());
		glEntryInfo.setGroup(glEntryInfo.getGroup());
		glEntryInfo.setInputUserID(glEntryInfo.getInputUserID());
		glEntryInfo.setInterestStartDate(glEntryInfo.getInterestStartDate());
		glEntryInfo.setMultiCode(glEntryInfo.getMultiCode());
		glEntryInfo.setOfficeID(glEntryInfo.getOfficeID());
		glEntryInfo.setPostStatusID(glEntryInfo.getPostStatusID());
		glEntryInfo.setRemark(glEntryInfo.getRemark());
		glEntryInfo.setStatusID(1);
		glEntryInfo.setSubjectCode(glEntryInfo.getSubjectCode());
		glEntryInfo.setTransactionTypeID(glEntryInfo.getTransactionTypeID());
		glEntryInfo.setTransDirection(glEntryInfo.getTransDirection());
		glEntryInfo.setType(glEntryInfo.getType());
		try {
			dao.add(glEntryInfo);
		} catch (ITreasuryDAOException e1) {
			throw new SecuritiesDAOException(e1);
		}
	}
	
	
//	private AccountTypeInfo getSubjectCodeBySurities(long officeID,
//			long currencyID,
//			long accountType,
//			long termTypeID,
//			long term,
//			long counterpartID,
//			long clientID,
//			long exchangeCenterCode,
//			String seatCode,
//			long accountID,
//			long securitiesID,
//			long subjectType) throws RemoteException, SecuritiesException{
//		SecuritiesAccountOperation accOperation = new SecuritiesAccountOperation(false);
//		String counterpartName = NameRef.getCounterpartNameByID(counterpartID);
//		String accountNo = NameRef.getAccountNobyAccountID(accountID);
//		String secritiesCode = NameRef.getSecuritiesCodeByID(securitiesID);		
//		String subjectCode =  SECConstant.EntrySubjectType.getName(subjectType);
//		String[] strs = {counterpartName,accountNo,secritiesCode,subjectCode};
//		
//		ArrayList list = (ArrayList) accOperation.getSecuritiesSubjectCode(officeID, currencyID, accountType, termTypeID, term, counterpartID, clientID, exchangeCenterCode, seatCode, accountID, securitiesID, subjectType);
//		log.debug("list.size()"+list.size());
//		if(list == null || list.size() == 0){
//			//Sec_E142=交易对手[?]资金帐户[?]证券[?]的科目[?]未设置
//			throw new SecuritiesException("Sec_E142",strs,null);
//		}else if(list.size() > 1){
//			//Sec_E143=交易对手[?]资金帐户[?]证券[?]的科目[?]不唯一
//			throw new SecuritiesException("Sec_E143",strs,null);
//		}else{
//			return (AccountTypeInfo) list.get(0);
//		}
//	}
}
