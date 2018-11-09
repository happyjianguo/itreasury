/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.securities.deliveryorderservice.bizlogic;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.SessionBean;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.deliveryorder.dao.SEC_DeliveryOrderDAO;
import com.iss.itreasury.securities.deliveryorder.dataentity.DeliveryOrderInfo;
import com.iss.itreasury.securities.deliveryorderservice.dao.SEC_CarryCostRecordDAO;
import com.iss.itreasury.securities.deliveryorderservice.dataentity.CarryCostParam;
import com.iss.itreasury.securities.deliveryorderservice.dataentity.CarryCostRecordInfo;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.register.bizlogic.RegisterOperation;
import com.iss.itreasury.securities.securitiesaccount.bizlogic.SecuritiesAccountOperation;
import com.iss.itreasury.securities.securitiesaccount.dataentity.AccountParam;
import com.iss.itreasury.securities.securitiesaccount.exception.AccounStatusException;
import com.iss.itreasury.securities.securitiesaccount.exception.AccountOverDraftException;
import com.iss.itreasury.securities.setting.dataentity.TransactionTypeInfo;
import com.iss.itreasury.securities.stock.bizlogic.StockOperation;
import com.iss.itreasury.securities.stock.dao.SEC_DailyStockDAO;
import com.iss.itreasury.securities.stock.dataentity.DailyStockInfo;
import com.iss.itreasury.securities.stock.dataentity.SecuritiesStockParam;
import com.iss.itreasury.securities.stock.dataentity.SecuritiesStockReturn;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log4j;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class DeliveryOrderServiceEJB implements SessionBean
{
	private javax.ejb.SessionContext mySessionCtx = null;
	final static long serialVersionUID = 3206093459760846163L;
	
	/**
	 * �Ƿ����Զ�����ı�־����
	 * ռ�ý����isDelivery�ֶ�
	 * ����ʵ�ʽ���Ƿ񽻸��״̬Ӧ��Ϊ1��-1
	 * */
	private final static long DELIVERY_AUTO = 10;
	
	private Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);	
	/**
	 * ejbActivate method comment
	 * @exception java.rmi.RemoteException �쳣˵����
	 */
	public void ejbActivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbCreate method comment
	 * @exception javax.ejb.CreateException �쳣˵����
	 * @exception java.rmi.RemoteException �쳣˵����
	 */
	public void ejbCreate() throws javax.ejb.CreateException, java.rmi.RemoteException
	{
	}
	/**
	 * ejbPassivate method comment
	 * @exception java.rmi.RemoteException �쳣˵����
	 */
	public void ejbPassivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbRemove method comment
	 * @exception java.rmi.RemoteException �쳣˵����
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
	 * @exception java.rmi.RemoteException �쳣˵����
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx) throws java.rmi.RemoteException
	{
		mySessionCtx = ctx;
	}
	
//	/**
//	 * ����������
//	 * @return �ǼǱ�ID
//	 * */
//	public long save(DeliveryOrderInfo doInfo) throws  RemoteException,SecuritiesException{
//		log.debug("------��ʼ����������-----------");
//		log.debug("���뽻���Ϣ: "+doInfo);		
//		try{	
//			long id = saveCapital(doInfo);			
//			log.debug("------��������������-----------");
//			return id;
//		}
//		catch (SecuritiesException e) {
//			mySessionCtx.setRollbackOnly();
//			throw e;
//		}				
//	}
//	
//
//	
//	/**
//	 * ���ɾ������
//	 * */
//	public void delete(DeliveryOrderInfo doInfo) throws  RemoteException,SecuritiesException{
//		log.debug("------��ʼ���ɾ������-----------");
//		log.debug("���뽻���Ϣ: "+doInfo);		
//		try{
//			deleteCapital(doInfo);
//		}
//		catch (SecuritiesException e) {
//			mySessionCtx.setRollbackOnly();
//			throw e;
//		}		
//		log.debug("-----�������ɾ������-----------");		
//	}
//	
//	/**
//	 * ������˲���
//	 * */
//	public void check(DeliveryOrderInfo doInfo) throws  RemoteException,SecuritiesException{
//		log.debug("------��ʼ������˲���-----------");
//		log.debug("���뽻���Ϣ: "+doInfo);		
//		try{
//			checkCapital(doInfo);
//		}
//		catch (SecuritiesException e) {
//			mySessionCtx.setRollbackOnly();
//			throw e;
//		}
//		log.debug("------����������˲���-----------");		
//	}
//	
//	/**
//	 * ���ȡ�����˲���
//	 * */
//	public void cancelCheck(DeliveryOrderInfo doInfo) throws  RemoteException,SecuritiesException{
//		log.debug("------��ʼȡ��������˲���-----------");
//		log.debug("���뽻���Ϣ: "+doInfo);		
//		try{		
//			cancelCheckCapital(doInfo);
//		}
//		catch (SecuritiesException e) {
//			mySessionCtx.setRollbackOnly();
//			throw e;
//		}
//		log.debug("-----����ȡ��������˲���-----------");		
//	}			
//	
//	/**
//	 * 	����/���/���ع�/��ع�/����/��ȯ���ҵ�񱣴�
//	 * */
//	private long saveCapital(DeliveryOrderInfo doInfo)throws RemoteException,SecuritiesException{
//		log.debug("------��ʼ����/���/���ع�/��ع�/����/��ȯ���ҵ�񱣴�-----------");		
//		//���/�ع��Ǽǲ�����
//		log.debug("------��ʼ���/�ع��Ǽǲ�����-----------");		
//		RegisterOperation registerOperation = new RegisterOperation(true);
////		RepurchaseRegisterParam info = new RepurchaseRegisterParam(doInfo);
////		log.debug("�ǼǱ��������:"+info);	
//		long registerID = registerOperation.register(doInfo);
//		log.debug("------�������/�ع��Ǽǲ�����-----------");			
//		//��Ѻ֤ȯID>0������ÿ�涳�ᴦ��		
//		if(doInfo.getSecuritiesId() > 0){
//			log.debug("------��ʼ��洦��-----------");			
//			StockOperation stockOperation = new StockOperation();
//			SecuritiesStockParam stockParam = new SecuritiesStockParam(doInfo,StockOperation.STOCK_OPERATION_FREEZE);
//			//����ý������Ͷ�Ӧ��ҵ������Ϊ���м�ҵ���򿪻��ʽ��ʻ�ID=NULL???
//			stockParam.setAccountID(NameRef.getAccountIDFromDeliveryOrder(doInfo,false));
//			stockParam.setAmount(doInfo.getAmount());
//			stockParam.setClient(doInfo.getClientId());
//			stockParam.setDeliveryOrderCodeID(doInfo.getId());
//			stockParam.setQuantity(doInfo.getPledgeSecuritiesQuantity());
//			stockParam.setSecuritiesID(doInfo.getSecuritiesId());
//			log.debug("����������: "+stockParam);			
//			stockOperation.freezeStock(stockParam);
//			log.debug("------������洦��-----------");			
//			
//		}
//		operateAccount(doInfo,false);
//		log.debug("-----��������/���/���ع�/��ع�/����/��ȯ���ҵ�񱣴�-----------");
//		return registerID;
//		
//	}
//	
	
	public long saveDeliveryOrder(DeliveryOrderInfo doInfo) throws  RemoteException,SecuritiesException{
		try{
			log.debug("------��ʼ���ҵ�񱣴�::saveDeliveryOrder-----------");
			TransactionTypeInfo typeInfo = doInfo.getTransactionTypeInfo();
			log.debug("---------���뽻�����:"+doInfo);
			log.debug("---------����������ϢΪ:"+typeInfo);
			boolean isAutoDelivery = false;
			if(doInfo.getIsDelivery() == DELIVERY_AUTO)
				isAutoDelivery = true;
			operateAccount(doInfo,false,isAutoDelivery);
			operateStock(doInfo, false,isAutoDelivery);	
			long regID = operateRegister(doInfo,false,isAutoDelivery);
			//DeliveryOrderInfo updatedDO = new DeliveryOrderInfo();
			//updatedDO.setId(doInfo.getId());
			//updatedDO.setIsDelivery(0);
			//updateDeliveryOrder(updatedDO);
			log.debug("---�����գ�"+doInfo.getDeliveryDate());
			Timestamp today = Env.getSecuritiesSystemDate(doInfo.getOfficeId(), doInfo.getCurrencyId());
			if(!doInfo.getDeliveryDate().after(today)){
				DeliveryOrderInfo updatedInfo = new DeliveryOrderInfo();				
				updatedInfo.setId(doInfo.getId());
				updatedInfo.setIsDelivery(1);
				updateDeliveryOrder(updatedInfo);												
			}								
			log.debug("------�������ҵ�񱣴�::saveDeliveryOrder-----------");
			if(regID < 0)
				regID = doInfo.getRegisterId();

			log.debug("------�ǼǱ�ID�ǣ�"+regID);
			
			return regID;		
		}catch(SecuritiesException e){
			mySessionCtx.setRollbackOnly();
			throw e;
		}
		
//		try{	
//			DeliveryOrderServiceBean deliveryOrderServiceBean = new DeliveryOrderServiceBean();
//			return deliveryOrderServiceBean.saveDeliveryOrder(doInfo);
//		}catch(SecuritiesException e){
//			mySessionCtx.setRollbackOnly();
//			throw e;
//		}			
		

	}
	
	public void deleteDeliveryOrder(DeliveryOrderInfo doInfo) throws  RemoteException,SecuritiesException{
		log.debug("------��ʼ���ҵ��ɾ��::deleteDeliveryOrder-----------");
		TransactionTypeInfo typeInfo = doInfo.getTransactionTypeInfo();
		log.debug("---------���뽻�����:"+doInfo);
		log.debug("---------����������ϢΪ:"+typeInfo);
		boolean isAutoDelivery = false;
		if(doInfo.getIsDelivery() == DELIVERY_AUTO)
			isAutoDelivery = true;		

		try{
			operateAccount(doInfo,true,isAutoDelivery);
			operateStock(doInfo, true,isAutoDelivery);	
			operateRegister(doInfo,true,isAutoDelivery);
			DeliveryOrderInfo updatedDO = new DeliveryOrderInfo();
			updatedDO.setId(doInfo.getId());
			updatedDO.setIsDelivery(0);
			updateDeliveryOrder(updatedDO);
		}catch(SecuritiesException e){
			mySessionCtx.setRollbackOnly();
			throw e;
		}		
		log.debug("---�����գ�"+doInfo.getDeliveryDate());
		Timestamp today = Env.getSecuritiesSystemDate(doInfo.getOfficeId(), doInfo.getCurrencyId());
		
		log.debug("---�����ǣ�"+today);
		if(!doInfo.getDeliveryDate().after(today)){
			DeliveryOrderInfo updatedInfo = new DeliveryOrderInfo();				
			updatedInfo.setId(doInfo.getId());
			updatedInfo.setIsDelivery(0);
			updateDeliveryOrder(updatedInfo);												
		}				
		
		log.debug("------�������ҵ��ɾ��::deleteDeliveryOrder-----------");
//		try{	
//			DeliveryOrderServiceBean deliveryOrderServiceBean = new DeliveryOrderServiceBean();
//			deliveryOrderServiceBean.deleteDeliveryOrder(doInfo);
//		}catch(SecuritiesException e){
//			mySessionCtx.setRollbackOnly();
//			throw e;
//		}					
	}		
	
	public void checkDeliveryOrder(DeliveryOrderInfo doInfo) throws  RemoteException,SecuritiesException{
//		log.debug("------��ʼ���ҵ�񸴺�::checkDeliveryOrder-----------");
//		log.debug("------���˵Ľ����ϢΪ:"+doInfo);
//		TransactionTypeInfo typeInfo = doInfo.getTransactionTypeInfo();
//		log.debug("------����������ϢΪ:"+typeInfo);
//		if(typeInfo.getIsNeedNotifyForm() <= 0){
//			try{
//				long accountType = -1;
//				GenerateGLEntryParam gleParam = new GenerateGLEntryParam(doInfo);
//				if(doInfo.getSecuritiesId() < 0){
//					if(doInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN
//							||doInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN_REPAY){
//						accountType = SECConstant.EntryAccountType.AccountType_1;//�����ʽ�
//					}else if(doInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT
//							||doInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT_REPAY){
//						accountType = SECConstant.EntryAccountType.AccountType_2;//����ʽ�
//					}else if(doInfo.getTransactionTypeId() == SECConstant.BusinessType.BANK_BOND_REPURCHASE.FUND_REPAY
//							|| doInfo.getTransactionTypeId() == SECConstant.BusinessType.BANK_BOND_REPURCHASE.FUND_REPURCHASE){
//						accountType = SECConstant.EntryAccountType.AccountType_3; //���ع�
//					}else if(doInfo.getTransactionTypeId() == SECConstant.BusinessType.BANK_BOND_REPURCHASE.BOND_REPAY
//							||doInfo.getTransactionTypeId() == SECConstant.BusinessType.BANK_BOND_REPURCHASE.BOND_REPURCHASE){
//						accountType = SECConstant.EntryAccountType.AccountType_4; //��ع�
//					}else if(doInfo.getTransactionTypeId() == SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.FUND_REPAY
//							||doInfo.getTransactionTypeId() == SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.FUND_REPURCHASE){
//						accountType = SECConstant.EntryAccountType.AccountType_11; //���ʻع�
//					}else if(doInfo.getTransactionTypeId() == SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.BOND_REPAY
//							||doInfo.getTransactionTypeId() == SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.BOND_REPURCHASE){
//						accountType = SECConstant.EntryAccountType.AccountType_12; //��ȯ�ع�
//					}
//					gleParam.setAccountType(accountType);
//					if(doInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN
//					|| doInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT
//					||doInfo.getTransactionTypeId() == SECConstant.BusinessType.BANK_BOND_REPURCHASE.BOND_REPURCHASE
//					|| doInfo.getTransactionTypeId() == SECConstant.BusinessType.BANK_BOND_REPURCHASE.FUND_REPURCHASE
//					||doInfo.getTransactionTypeId() == SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.FUND_REPURCHASE
//					||doInfo.getTransactionTypeId() == SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.BOND_REPURCHASE){
//						log.debug("---------�ع�-----------");						
//						gleParam.setNetIncome(doInfo.getNetIncome());
//						gleParam.setPrincipal(doInfo.getAmount());
//						gleParam.setCommission(Math.abs(doInfo.getNetIncome()-doInfo.getAmount()));
//					}else{
//						log.debug("--------����------------");									
//						gleParam.setNetIncome(doInfo.getNetIncome());
//						gleParam.setPrincipal(doInfo.getAmount());
//						gleParam.setInterest(Math.abs(doInfo.getNetIncome()-doInfo.getAmount()));				
//					}						
//		
//				}else{
//					SEC_SecuritiesDAO secDAO = new SEC_SecuritiesDAO();
//					SecuritiesInfo secInfo = null;
//					try {
//						secInfo = (SecuritiesInfo) secDAO.findByID(doInfo.getSecuritiesId(), SecuritiesInfo.class);
//					} catch (ITreasuryDAOException e) {
//						throw new SecuritiesDAOException(e);
//					}
//					if(secInfo == null || secInfo.getId() < 0 || secInfo.getStatusID() == SECConstant.RemarkStatus.INVALID)
//						throw new SECInvalidRecordException("֤ȯ����");
//					
//					long accountTypeID = -1;
//					if(secInfo.getTypeID() == SECConstant.SecuritiesType.STOCK_A
//					|| secInfo.getTypeID() == SECConstant.SecuritiesType.STOCK_B
//					|| secInfo.getTypeID() == SECConstant.SecuritiesType.OTHERS)//֤ȯ���=A��/B��/����
//						accountTypeID = SECConstant.EntryAccountType.AccountType_6;//��������=��Ʊ
//					if(secInfo.getTypeID() == SECConstant.SecuritiesType.ENTERPRISE_BOND
//					|| secInfo.getTypeID() == SECConstant.SecuritiesType.EXCHANGECENTER_NATIONAL_BOND
//					||secInfo.getTypeID() == SECConstant.SecuritiesType.BANK_NATIONAL_BOND
//					||secInfo.getTypeID() == SECConstant.SecuritiesType.POLICY_RELATED_FINANCIAL_BOND
//					||secInfo.getTypeID() == SECConstant.SecuritiesType.FINANCIAL_BOND)//֤ȯ���=��ҵծ��ȯ/��������ծ��ȯ/���м��ծ��ȯ/�����Խ���ծ��ȯ/����ծ��ȯ
//						accountTypeID = SECConstant.EntryAccountType.AccountType_5;
//					if(secInfo.getTypeID() == SECConstant.SecuritiesType.TRANSFORMABLE_BOND)//֤ȯ���=��תծ��ȯ
//						accountTypeID = SECConstant.EntryAccountType.AccountType_7;
//					if(secInfo.getTypeID() == SECConstant.SecuritiesType.CENTRAL_BANK_NOTE)//֤ȯ���=����Ʊ����ȯ
//						accountTypeID = SECConstant.EntryAccountType.AccountType_8;
//					if(secInfo.getTypeID() == SECConstant.SecuritiesType.ENCLOSED_FUND)//֤ȯ���=���ʽ����
//						accountTypeID = SECConstant.EntryAccountType.AccountType_9;
//					if(secInfo.getTypeID() == SECConstant.SecuritiesType.MUTUAL_FUND)//��֤ȯ���=����ʽ����
//						accountTypeID = SECConstant.EntryAccountType.AccountType_10;
//					
//					TransactionTypeInfo transTypeInfo = null;
//					try {
//						transTypeInfo = SECConstant.BusinessType.getTransactionTypeInfoByID(doInfo.getTransactionTypeId());
//					} catch (ITreasuryDAOException e) {
//						throw new SecuritiesDAOException(e);
//					}
//					if(transTypeInfo.getIsNeedNotifyForm() <= 0){//������Ʒ�¼
//						
//						gleParam.setNetIncome(doInfo.getNetIncome());
//						gleParam.setPrincipal(doInfo.getQuantity()*doInfo.getUnitCost());
//						gleParam.setAccountType(accountTypeID);
//						//֤ȯ����/�������=ʵ���ո�-�ɱ�
//						gleParam.setMargin(doInfo.getNetIncome()-gleParam.getPrincipal());
//					}			
//				}
//				log.debug("zzzzzzzzzzzzzzzzzzzzz");
//				SecuritiesGeneralLedgerOperation gleOperation = new SecuritiesGeneralLedgerOperation();
//				gleOperation.generateGLEntry(gleParam);		
//			
//			}catch(SecuritiesException e){
//				mySessionCtx.setRollbackOnly();
//				throw e;
//			}
//		}
//		log.debug("------�������ҵ�񸴺�::checkDeliveryOrder-----------");		
		try{
			DeliveryOrderServiceBean deliveryOrderServiceBean = new DeliveryOrderServiceBean();
			deliveryOrderServiceBean.checkDeliveryOrder(doInfo);
		}catch(SecuritiesException e){
			mySessionCtx.setRollbackOnly();
			throw e;
		}					
	}
	
	public void cancelCheckDeliveryOrder(DeliveryOrderInfo doInfo) throws  RemoteException,SecuritiesException{
//		try{
//			SecuritiesGeneralLedgerOperation gleOperation = new SecuritiesGeneralLedgerOperation();
//			gleOperation.deleteGLEntry(doInfo.getCode());
//		}catch(SecuritiesException e){
//			mySessionCtx.setRollbackOnly();
//			throw e;
//		}		
		try{
			DeliveryOrderServiceBean deliveryOrderServiceBean = new DeliveryOrderServiceBean();
			deliveryOrderServiceBean.cancelCheckDeliveryOrder(doInfo);
		}catch(SecuritiesException e){
			mySessionCtx.setRollbackOnly();
			throw e;
		}				
		
	}
	


	
	/**
	 * ���һ��ҵ��λ��һ���ʽ��ʻ��µ�һ��֤ȯ���룬�����յ�ֹ�գ��������г��⽻�׽��гɱ���ת��
	 * */
	public void carryCost(CarryCostParam param) throws  RemoteException,SecuritiesException{
		log.debug("----------��ʼ�ɱ���ת-------------");
		log.debug("----------����Ĳ���Ϊ:"+param);
		SEC_DeliveryOrderDAO deliveryOderDAO = new SEC_DeliveryOrderDAO();
		Collection c = deliveryOderDAO.getDeliveryOrderForCarryCost(param.getClientID(), param.getAccountID(), param.getSecuritiesID(), param.getStartDate(), param.getEndDate());
		Iterator it = c.iterator();
		while(it.hasNext()){
			DeliveryOrderInfo deliveryOrderInfo = (DeliveryOrderInfo) it.next();
			log.debug("----------���ڱ�����Ľ��Ϊ:"+deliveryOrderInfo);
			SEC_DailyStockDAO dailyStockDAO = new SEC_DailyStockDAO();
			DailyStockInfo dailyStockInfo = new DailyStockInfo();
			dailyStockInfo.setStockDate(deliveryOrderInfo.getDeliveryDate());
			dailyStockInfo.setClientID(deliveryOrderInfo.getClientId());
			dailyStockInfo.setAccountID(deliveryOrderInfo.getAccountId());
			dailyStockInfo.setSecuritiesID(deliveryOrderInfo.getSecuritiesId());
			DailyStockInfo foundDailyStockInfo;
			try {
				
				Collection dailyStockCollection = dailyStockDAO.findByCondition(dailyStockInfo);
				Iterator dailyStockCollectionIT = dailyStockCollection.iterator();
				while(dailyStockCollectionIT.hasNext()){
				foundDailyStockInfo = (DailyStockInfo) dailyStockCollectionIT.next(); 
				log.debug("------�ý����Ӧ���ս���ϢΪ:"+foundDailyStockInfo);
	
				CarryCostRecordInfo carryCostRecordInfo = new CarryCostRecordInfo(deliveryOrderInfo,foundDailyStockInfo);
				SEC_CarryCostRecordDAO carryCostRecordDAO = new SEC_CarryCostRecordDAO();
				log.debug("------�����Ľ�ת�ɱ���ϸ��ϢΪ:"+carryCostRecordInfo);
				carryCostRecordDAO.add(carryCostRecordInfo);
				deliveryOrderInfo.setTransactionTypeId(SECConstant.BusinessType.CARRY_COST.CARRY_COST);
				System.out.println("---------------������ת�ɱ��Ļ�Ʒ�¼");
				checkDeliveryOrder(deliveryOrderInfo);
				deliveryOrderInfo.clearUsedFields();
				deliveryOrderInfo.setId(deliveryOrderInfo.getId());
				deliveryOrderInfo.setIsCarryCost(1);			
				deliveryOderDAO.update(deliveryOrderInfo);
				}
			} catch (ITreasuryDAOException e) {
				e.printStackTrace();
				throw new SecuritiesDAOException(e);
			}
			
			catch(SecuritiesException ex){
				ex.printStackTrace();
				throw ex; 
			}
			catch(Exception ex2){
				ex2.printStackTrace();
				throw new SecuritiesException();
			}
		}
		//�ڱ��Ρ���ת�ɱ������������л�Ʒ�¼�У��Ի�����ID�����ֺš���Ŀ�������ͬ�ķ�¼��
		//Ӧ�û������ʹ֮�ϲ���һ�ʷ�¼
		//SecuritiesGeneralLedgerOperation securitiesGeneralLedgerOperation = new SecuritiesGeneralLedgerOperation();
		//securitiesGeneralLedgerOperation.combineGLEntryForCarryCost(param.getOfficeID(), param.getCurrencyID(), param.get)
		log.debug("----------��ɳɱ���ת-------------");		
	}
	
	/**
	 * ��ϵͳ֧��T+N���ף�������Ľ����գ���Ƴɽ��գ����Ժͽ����գ���ƽ����ա������գ���ͬ������Ѵ���˳ɽ�Э�飬
	 * �����Ƿ�ʱ��������Լ�ʱ�Խ�����и��ˡ�Ҳ����˵����������ڽ�����¼�벢���ˣ����¼��ʱ���������ڽ����գ�
	 * ��˱�ҵ�����漰���ʽ���治�ἴʱ�����仯������Ҫ��ϵͳ���е������յ���ʱ���Żᷢ���仯
	 * */
	public void deliverAutomatically(long officeID,long currencyID) throws RemoteException,SecuritiesException{
		log.debug("-------��ʼ�Զ�����----------");
		SEC_DeliveryOrderDAO deliveryOrderDAO = new SEC_DeliveryOrderDAO();
		Collection c = deliveryOrderDAO.getDeliveryOrderForDeliverAutomatically(officeID,currencyID);
		Iterator it = c.iterator();
		while(it.hasNext()){
			DeliveryOrderInfo deliveryOrderInfo = (DeliveryOrderInfo) it.next();
			deliveryOrderInfo.setIsCheckOverDraft(SECConstant.FALSE);
			deliveryOrderInfo.setIsCheckOverStock(SECConstant.FALSE);
			deliveryOrderInfo.setIsDelivery(DELIVERY_AUTO);

			saveDeliveryOrder(deliveryOrderInfo);
			
			if(deliveryOrderInfo.getStatusId() == +SECConstant.DeliveryOrderStatus.CHECKED)			
				checkDeliveryOrder(deliveryOrderInfo);
			
			
		}
		log.debug("-------�����Զ�����----------");		
	}
	
	
	/**
	 * ���ݽ����Ϣ�����ʽ��˻�����
		* @param accountParam 
		* @param isDelete�Ƿ���ɾ������
		* @return
		* @throws
		 */
	private void operateAccount(DeliveryOrderInfo doInfo,boolean isDelete,boolean isAutoDelivery) throws RemoteException, AccounStatusException, AccountOverDraftException, SecuritiesException {
		log.debug("------��ʼ�ʽ��˻�����-----------");		
		
		if(doInfo.getAccountId() < 0){
			log.debug("-----��ҵ��ʹ���ʽ��ʻ����߲������ݴ���,�����ʽ��˻�����-----------");
			return;
		}
		TransactionTypeInfo transTypeInfo = null;
		transTypeInfo = doInfo.getTransactionTypeInfo();
		if(transTypeInfo.getCapitalDirection() > 0){//��Ҫ�����ʽ��˻�
			log.debug("-----��Ҫ�����ʽ��˻�----------");
			SecuritiesAccountOperation accOperation = new SecuritiesAccountOperation(true);
			AccountParam accParam = new AccountParam(doInfo);
			log.debug("-----�ʽ��˻��������: " + accParam);
			if(transTypeInfo.getCapitalDirection() == SECConstant.Direction.PAY
			|| transTypeInfo.getCapitalDirection() == SECConstant.Direction.PAY_AND_FINANCE_RECEIVE){
				if(isDelete)
					accOperation.cancelPayment(accParam);
				else{
					if(isAutoDelivery)
						accOperation.deliverPayment(accParam);
					else	
						accOperation.pay(accParam);
				}
			}else if(transTypeInfo.getCapitalDirection() == SECConstant.Direction.RECEIVE
			|| transTypeInfo.getCapitalDirection() == SECConstant.Direction.RECEIVE_AND_FINANCE_PAY){
				if(isDelete)
					accOperation.cancelReceipt(accParam);
				else{	
					if(isAutoDelivery)
						accOperation.deliverReceipt(accParam);
					else	
						accOperation.receive(accParam);
				}
			}else if(transTypeInfo.getCapitalDirection() == SECConstant.Direction.PAY_AND_RECEIVE){//һ��һ��
				if(isDelete){
					accOperation.cancelReceipt(accParam);
					accParam.setId(doInfo.getCounterpartAccountId());
					accOperation.cancelPayment(accParam);										
				}else{
					if(isAutoDelivery){
						accOperation.deliverReceipt(accParam);					
						accParam.setId(doInfo.getCounterpartAccountId());
						accOperation.deliverPayment(accParam);						
					}else{
						accOperation.receive(accParam);					
						accParam.setId(doInfo.getCounterpartAccountId());
						accOperation.pay(accParam);
					}
				}
			}
		}
		log.debug("------�����ʽ��˻�����-----------");		
	}	
	
	/**
	 * ���ݽ������Ͷ�����п��Ĵ���
	 * @param doInfo �����Ϣ
	 * @param isDelete�Ƿ��Ƿ�����(ȡ������)
	 * 
	 * */
	private void operateStock(DeliveryOrderInfo doInfo,boolean isDelete,boolean isAutoDelivery) throws RemoteException, SecuritiesException{
		log.debug("------��ʼ��洦��-----------");		
		
		StockOperation stockOperation = new StockOperation();
		TransactionTypeInfo transTypeInfo = null;
		transTypeInfo = doInfo.getTransactionTypeInfo();
		if(transTypeInfo.getStockDirection() > 0){//��Ҫ���¿�� 
			log.debug("-----��Ҫ���¿��----------");			
			if(transTypeInfo.getStockDirection() == SECConstant.StockDirection.IN){
				if(isDelete){
					SecuritiesStockParam stockParam = new SecuritiesStockParam(doInfo,StockOperation.STOCK_OPERATION_CANCELENTER);
					stockOperation.cancelEnterStock(stockParam);
				}
				else{
					SecuritiesStockParam stockParam = new SecuritiesStockParam(doInfo,StockOperation.STOCK_OPERATION_ENTER);
					if(isAutoDelivery)
						stockOperation.deliverStock(stockParam);
					else
						stockOperation.enterStock(stockParam);
				}
			}else{// stock out
					DeliveryOrderInfo updatedDOInfo = new DeliveryOrderInfo();
						if(transTypeInfo.getStockDirection() == SECConstant.StockDirection.OUT){
							if(isDelete){
								SecuritiesStockParam stockParam = new SecuritiesStockParam(doInfo,StockOperation.STOCK_OPERATION_CANCELEXIT);					
								stockOperation.cancelExitStock(stockParam);
								updatedDOInfo.setId(doInfo.getId());
								updatedDOInfo.setUnitCost(0.0);
								updatedDOInfo.setUnitNetProfitLoss(0.0);
								updatedDOInfo.setUnitProfitLoss(0.0);	
								updateDeliveryOrder(updatedDOInfo);
							}//end isDelete
							else{	
								SecuritiesStockParam stockParam = new SecuritiesStockParam(doInfo,StockOperation.STOCK_OPERATION_EXIT);
								SecuritiesStockReturn stockReturn = null;
								if(isAutoDelivery)
									stockReturn = stockOperation.deliverExitStock(stockParam);
								else
									stockReturn = stockOperation.exitStock(stockParam);
								if(stockReturn != null){
									updatedDOInfo = new DeliveryOrderInfo();
									updatedDOInfo.setId(doInfo.getId());
									updatedDOInfo.setUnitCost(stockReturn.getUnitCost());
									updatedDOInfo.setUnitNetCost(stockReturn.getUnitNetCost());
									updatedDOInfo.setUnitNetProfitLoss(stockReturn.getUnitNetProfitLoss());
									updatedDOInfo.setUnitProfitLoss(stockReturn.getUnitProfitLoss());
									updateDeliveryOrder(updatedDOInfo);
								}
							}
				}else{//һ��һ��
					if(isDelete){
						//IN
						SecuritiesStockParam stockParamIn = new SecuritiesStockParam(doInfo,StockOperation.STOCK_OPERATION_CANCELENTER);
						stockOperation.cancelEnterStock(stockParamIn);
						//OUT
						SecuritiesStockParam stockParamOut = new SecuritiesStockParam(doInfo,StockOperation.STOCK_OPERATION_CANCELEXIT);
						stockParamOut.setClient(doInfo.getCompanyAccountId());//ҵ��λ����
						stockParamOut.setAccountID(doInfo.getCounterpartAccountId());
						stockParamOut.setSecuritiesID(doInfo.getOppositeSecuritiesId());
						stockParamOut.setQuantity(doInfo.getOppositeQuantity());
						
						stockOperation.cancelExitStock(stockParamOut);
						updatedDOInfo.setId(doInfo.getId());
						updatedDOInfo.setUnitCost(0.0);
						updatedDOInfo.setUnitNetProfitLoss(0.0);
						updatedDOInfo.setUnitProfitLoss(0.0);	
						updateDeliveryOrder(updatedDOInfo);						
					}
					else{
						//IN
						SecuritiesStockParam stockParamIN = new SecuritiesStockParam(doInfo,StockOperation.STOCK_OPERATION_ENTER);
						if(isAutoDelivery)
							stockOperation.deliverStock(stockParamIN);
						else	
							stockOperation.enterStock(stockParamIN);
						//OUT
						SecuritiesStockParam stockParamOut = new SecuritiesStockParam(doInfo,StockOperation.STOCK_OPERATION_EXIT);
						stockParamOut.setClient(doInfo.getCompanyAccountId());//ҵ��λ����
						stockParamOut.setAccountID(doInfo.getCounterpartAccountId());
						stockParamOut.setSecuritiesID(doInfo.getOppositeSecuritiesId());
						stockParamOut.setQuantity(doInfo.getOppositeQuantity());
						
						SecuritiesStockReturn stockReturn = null;
						if(isAutoDelivery)
							stockReturn = stockOperation.deliverExitStock(stockParamOut);
						else
							stockReturn = stockOperation.exitStock(stockParamOut);
						if(stockReturn != null){
							updatedDOInfo = new DeliveryOrderInfo();
							updatedDOInfo.setId(doInfo.getId());
							updatedDOInfo.setUnitCost(stockReturn.getUnitCost());
							updatedDOInfo.setUnitNetCost(stockReturn.getUnitNetCost());
							updatedDOInfo.setUnitNetProfitLoss(stockReturn.getUnitNetProfitLoss());
							updatedDOInfo.setUnitProfitLoss(stockReturn.getUnitProfitLoss());
							updateDeliveryOrder(updatedDOInfo);						
					}
				}
		    }
			}				
	    }else if(transTypeInfo.getFrozenProcess() == SECConstant.FrozenProcess.FREEZE){
	    	if(isDelete){
		    	SecuritiesStockParam stockParam = new SecuritiesStockParam(doInfo,StockOperation.STOCK_OPERATION_CANCELFREEZE);
		    	stockOperation.cancelFreezeStock(stockParam);	    		    		
	    	}else{
		    	SecuritiesStockParam stockParam = new SecuritiesStockParam(doInfo,StockOperation.STOCK_OPERATION_FREEZE);
		    	stockOperation.freezeStock(stockParam);
	    	}
	    }else if(transTypeInfo.getFrozenProcess() == SECConstant.FrozenProcess.CANCEL_FREEZE){
	    	if(isDelete){
		    	SecuritiesStockParam stockParam = new SecuritiesStockParam(doInfo,StockOperation.STOCK_OPERATION_FREEZE);
		    	stockOperation.freezeStock(stockParam);	    		
	    	}else{
		    	SecuritiesStockParam stockParam = new SecuritiesStockParam(doInfo,StockOperation.STOCK_OPERATION_CANCELFREEZE);
		    	stockOperation.cancelFreezeStock(stockParam);
	    	}
	    }
		log.debug("------������洦��-----------");			

	} 
	
	
	/**
	 * ���ݽ������Ͷ�����еǼǱ��Ĵ���
	 * @param doInfo �����Ϣ
	 * @param isDelete�Ƿ��Ƿ�����(ȡ������)
	 * 
	 * */	
	private long operateRegister(DeliveryOrderInfo doInfo,boolean isDelete,boolean isAutoDelivery)throws RemoteException,SecuritiesException{
		log.debug("------��ʼ�ǼǱ�����-----------");		
		if(isAutoDelivery){
			log.debug("------�Զ�������еǼǱ�����-----------");	
			return -1;
		}
		TransactionTypeInfo transTypeInfo = doInfo.getTransactionTypeInfo();
		
		if(!(transTypeInfo.getRegisterProcess() == SECConstant.RegisterProcess.CAPITAL
				|| transTypeInfo.getRegisterProcess() == SECConstant.RegisterProcess.APPLICATION_APPLY
				|| transTypeInfo.getRegisterProcess() == SECConstant.RegisterProcess.LONGTERM_BUYIN
				) && doInfo.getRegisterId() <= 0){
			log.debug("------�ǼǱ�ID������,�˳��ǼǱ�����--------");
			return -1;
		}

		
		RegisterOperation registerOperation = new RegisterOperation(true);
		//RepurchaseRegisterParam doInfo = new RepurchaseRegisterParam(doInfo);
		long registerID = -1;

		if(transTypeInfo.getRegisterProcess() == SECConstant.RegisterProcess.CAPITAL
		|| transTypeInfo.getRegisterProcess() == SECConstant.RegisterProcess.APPLICATION_APPLY
		|| transTypeInfo.getRegisterProcess() == SECConstant.RegisterProcess.LONGTERM_BUYIN){
			if(isDelete)
				registerOperation.cancelRegister(doInfo);
			else
			   registerID = registerOperation.register(doInfo);
		}
		else if(transTypeInfo.getRegisterProcess() == SECConstant.RegisterProcess.APPLICATION_CONFIRM){		
			if(isDelete)
				registerOperation.cancelConfirmApplication(doInfo);
			else
				registerOperation.confirmApplication(doInfo);
		} else if(transTypeInfo.getRegisterProcess() == SECConstant.RegisterProcess.APPLICATION_SELLOUT){
			if(isDelete)
				registerOperation.cancelSellOut(doInfo);
			else
			registerOperation.sellOut(doInfo);
		}else if(transTypeInfo.getRegisterProcess() == SECConstant.RegisterProcess.CAPITAL_MATURE
			     ||  transTypeInfo.getRegisterProcess() == SECConstant.RegisterProcess.APPLICATION_MATURE){
		
				if(isDelete)
					registerOperation.cancelMaturate(doInfo);
				else	
					registerOperation.maturate(doInfo);
			} 
		
		log.debug("------�ǼǱ�ID��:"+registerID);
		log.debug("------�����ǼǱ�����-----------");
		return registerID;		
	}		
	
	
	/**
	 * ���½��
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
