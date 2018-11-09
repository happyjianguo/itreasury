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
import com.iss.itreasury.securities.apply.dao.SEC_ApplyDAO;
import com.iss.itreasury.securities.deliveryorder.dao.SEC_DeliveryOrderDAO;
import com.iss.itreasury.securities.deliveryorder.dataentity.DeliveryOrderInfo;
import com.iss.itreasury.securities.deliveryorderservice.dao.SEC_CarryCostRecordDAO;
import com.iss.itreasury.securities.deliveryorderservice.dao.SecuritiesCreditLineDAO;
import com.iss.itreasury.securities.deliveryorderservice.dao.SecuritiesTypeCreditLineDAO;
import com.iss.itreasury.securities.deliveryorderservice.dataentity.CarryCostParam;
import com.iss.itreasury.securities.deliveryorderservice.dataentity.CarryCostRecordInfo;
import com.iss.itreasury.securities.deliveryorderservice.dataentity.UsableCreditLineOfSecuritiesInfo;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.register.bizlogic.RegisterBiz;
import com.iss.itreasury.securities.securitiesaccount.bizlogic.SecuritiesAccountOperation;
import com.iss.itreasury.securities.securitiesaccount.dataentity.AccountParam;
import com.iss.itreasury.securities.securitiesaccount.exception.AccounStatusException;
import com.iss.itreasury.securities.securitiesaccount.exception.AccountOverDraftException;
import com.iss.itreasury.securities.setting.dataentity.TransactionTypeInfo;
import com.iss.itreasury.securities.stock.bizlogic.StockBiz;
import com.iss.itreasury.securities.stock.dao.SEC_DailyStockDAO;
import com.iss.itreasury.securities.stock.dao.SEC_SecuritiesStockDAO;
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
public class DeliveryOrderServiceBiz implements SessionBean
{
	private javax.ejb.SessionContext mySessionCtx = null;
	final static long serialVersionUID = 3206093459760846163L;
	
	/**
	 * 是否是自动交割的标志变量
	 * 占用交割单中isDelivery字段
	 * 但是实际交割单是否交割的状态应该为1或-1
	 * */
	private final static long DELIVERY_AUTO = 10;
	
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
	
//	/**
//	 * 交割单保存操作
//	 * @return 登记薄ID
//	 * */
//	public long save(DeliveryOrderInfo doInfo) throws  RemoteException,SecuritiesException{
//		log.debug("------开始交割单保存操作-----------");
//		log.debug("输入交割单信息: "+doInfo);		
//		try{	
//			long id = saveCapital(doInfo);			
//			log.debug("------结束交割单保存操作-----------");
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
//	 * 交割单删除操作
//	 * */
//	public void delete(DeliveryOrderInfo doInfo) throws  RemoteException,SecuritiesException{
//		log.debug("------开始交割单删除操作-----------");
//		log.debug("输入交割单信息: "+doInfo);		
//		try{
//			deleteCapital(doInfo);
//		}
//		catch (SecuritiesException e) {
//			mySessionCtx.setRollbackOnly();
//			throw e;
//		}		
//		log.debug("-----结束交割单删除操作-----------");		
//	}
//	
//	/**
//	 * 交割单复核操作
//	 * */
//	public void check(DeliveryOrderInfo doInfo) throws  RemoteException,SecuritiesException{
//		log.debug("------开始交割单复核操作-----------");
//		log.debug("输入交割单信息: "+doInfo);		
//		try{
//			checkCapital(doInfo);
//		}
//		catch (SecuritiesException e) {
//			mySessionCtx.setRollbackOnly();
//			throw e;
//		}
//		log.debug("------结束交割单复核操作-----------");		
//	}
//	
//	/**
//	 * 交割单取消复核操作
//	 * */
//	public void cancelCheck(DeliveryOrderInfo doInfo) throws  RemoteException,SecuritiesException{
//		log.debug("------开始取消交割单复核操作-----------");
//		log.debug("输入交割单信息: "+doInfo);		
//		try{		
//			cancelCheckCapital(doInfo);
//		}
//		catch (SecuritiesException e) {
//			mySessionCtx.setRollbackOnly();
//			throw e;
//		}
//		log.debug("-----结束取消交割单复核操作-----------");		
//	}			
//	
//	/**
//	 * 	拆入/拆出/正回购/逆回购/融资/融券交割单业务保存
//	 * */
//	private long saveCapital(DeliveryOrderInfo doInfo)throws RemoteException,SecuritiesException{
//		log.debug("------开始拆入/拆出/正回购/逆回购/融资/融券交割单业务保存-----------");		
//		//拆借/回购登记簿处理
//		log.debug("------开始拆借/回购登记簿处理-----------");		
//		RegisterOperation registerOperation = new RegisterOperation(true);
////		RepurchaseRegisterParam info = new RepurchaseRegisterParam(doInfo);
////		log.debug("登记薄输入参数:"+info);	
//		long registerID = registerOperation.register(doInfo);
//		log.debug("------结束拆借/回购登记簿处理-----------");			
//		//抵押证券ID>0，则调用库存冻结处理		
//		if(doInfo.getSecuritiesId() > 0){
//			log.debug("------开始库存处理-----------");			
//			StockOperation stockOperation = new StockOperation();
//			SecuritiesStockParam stockParam = new SecuritiesStockParam(doInfo,StockOperation.STOCK_OPERATION_FREEZE);
//			//如果该交易类型对应的业务性质为银行间业务，则开户资金帐户ID=NULL???
//			stockParam.setAccountID(NameRef.getAccountIDFromDeliveryOrder(doInfo,false));
//			stockParam.setAmount(doInfo.getAmount());
//			stockParam.setClient(doInfo.getClientId());
//			stockParam.setDeliveryOrderCodeID(doInfo.getId());
//			stockParam.setQuantity(doInfo.getPledgeSecuritiesQuantity());
//			stockParam.setSecuritiesID(doInfo.getSecuritiesId());
//			log.debug("库存输入参数: "+stockParam);			
//			stockOperation.freezeStock(stockParam);
//			log.debug("------结束库存处理-----------");			
//			
//		}
//		operateAccount(doInfo,false);
//		log.debug("-----结束拆入/拆出/正回购/逆回购/融资/融券交割单业务保存-----------");
//		return registerID;
//		
//	}
//	
	
	public long saveDeliveryOrder(DeliveryOrderInfo doInfo) throws  RemoteException,SecuritiesException{
		try{
			log.debug("------开始交割单业务保存::saveDeliveryOrder-----------");
			TransactionTypeInfo typeInfo = doInfo.getTransactionTypeInfo();
			log.debug("---------输入交割单参数:"+doInfo);
			log.debug("---------交易类型信息为:"+typeInfo);
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
			log.debug("---交割日："+doInfo.getDeliveryDate());
			Timestamp today = Env.getSecuritiesSystemDate(doInfo.getOfficeId(), doInfo.getCurrencyId());
			if(!doInfo.getDeliveryDate().after(today)){
				DeliveryOrderInfo updatedInfo = new DeliveryOrderInfo();				
				updatedInfo.setId(doInfo.getId());
				updatedInfo.setIsDelivery(1);
				updateDeliveryOrder(updatedInfo);												
			}								
			log.debug("------结束交割单业务保存::saveDeliveryOrder-----------");
			if(regID < 0)
				regID = doInfo.getRegisterId();

			log.debug("------登记薄ID是："+regID);
			
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
		log.debug("------开始交割单业务删除::deleteDeliveryOrder-----------");
		TransactionTypeInfo typeInfo = doInfo.getTransactionTypeInfo();
		log.debug("---------输入交割单参数:"+doInfo);
		log.debug("---------交易类型信息为:"+typeInfo);
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
		log.debug("---交割日："+doInfo.getDeliveryDate());
		Timestamp today = Env.getSecuritiesSystemDate(doInfo.getOfficeId(), doInfo.getCurrencyId());
		
		log.debug("---今天是："+today);
		if(!doInfo.getDeliveryDate().after(today)){
			DeliveryOrderInfo updatedInfo = new DeliveryOrderInfo();				
			updatedInfo.setId(doInfo.getId());
			updatedInfo.setIsDelivery(0);
			updateDeliveryOrder(updatedInfo);												
		}				
		
		log.debug("------结束交割单业务删除::deleteDeliveryOrder-----------");
//		try{	
//			DeliveryOrderServiceBean deliveryOrderServiceBean = new DeliveryOrderServiceBean();
//			deliveryOrderServiceBean.deleteDeliveryOrder(doInfo);
//		}catch(SecuritiesException e){
//			mySessionCtx.setRollbackOnly();
//			throw e;
//		}					
	}		
	
	public void checkDeliveryOrder(DeliveryOrderInfo doInfo) throws  RemoteException,SecuritiesException{
//		log.debug("------开始交割单业务复核::checkDeliveryOrder-----------");
//		log.debug("------复核的交割单信息为:"+doInfo);
//		TransactionTypeInfo typeInfo = doInfo.getTransactionTypeInfo();
//		log.debug("------交易类型信息为:"+typeInfo);
//		if(typeInfo.getIsNeedNotifyForm() <= 0){
//			try{
//				long accountType = -1;
//				GenerateGLEntryParam gleParam = new GenerateGLEntryParam(doInfo);
//				if(doInfo.getSecuritiesId() < 0){
//					if(doInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN
//							||doInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN_REPAY){
//						accountType = SECConstant.EntryAccountType.AccountType_1;//拆入资金
//					}else if(doInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT
//							||doInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT_REPAY){
//						accountType = SECConstant.EntryAccountType.AccountType_2;//拆出资金
//					}else if(doInfo.getTransactionTypeId() == SECConstant.BusinessType.BANK_BOND_REPURCHASE.FUND_REPAY
//							|| doInfo.getTransactionTypeId() == SECConstant.BusinessType.BANK_BOND_REPURCHASE.FUND_REPURCHASE){
//						accountType = SECConstant.EntryAccountType.AccountType_3; //正回购
//					}else if(doInfo.getTransactionTypeId() == SECConstant.BusinessType.BANK_BOND_REPURCHASE.BOND_REPAY
//							||doInfo.getTransactionTypeId() == SECConstant.BusinessType.BANK_BOND_REPURCHASE.BOND_REPURCHASE){
//						accountType = SECConstant.EntryAccountType.AccountType_4; //逆回购
//					}else if(doInfo.getTransactionTypeId() == SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.FUND_REPAY
//							||doInfo.getTransactionTypeId() == SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.FUND_REPURCHASE){
//						accountType = SECConstant.EntryAccountType.AccountType_11; //融资回购
//					}else if(doInfo.getTransactionTypeId() == SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.BOND_REPAY
//							||doInfo.getTransactionTypeId() == SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.BOND_REPURCHASE){
//						accountType = SECConstant.EntryAccountType.AccountType_12; //融券回购
//					}
//					gleParam.setAccountType(accountType);
//					if(doInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN
//					|| doInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT
//					||doInfo.getTransactionTypeId() == SECConstant.BusinessType.BANK_BOND_REPURCHASE.BOND_REPURCHASE
//					|| doInfo.getTransactionTypeId() == SECConstant.BusinessType.BANK_BOND_REPURCHASE.FUND_REPURCHASE
//					||doInfo.getTransactionTypeId() == SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.FUND_REPURCHASE
//					||doInfo.getTransactionTypeId() == SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.BOND_REPURCHASE){
//						log.debug("---------回购-----------");						
//						gleParam.setNetIncome(doInfo.getNetIncome());
//						gleParam.setPrincipal(doInfo.getAmount());
//						gleParam.setCommission(Math.abs(doInfo.getNetIncome()-doInfo.getAmount()));
//					}else{
//						log.debug("--------到期------------");									
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
//						throw new SECInvalidRecordException("证券类型");
//					
//					long accountTypeID = -1;
//					if(secInfo.getTypeID() == SECConstant.SecuritiesType.STOCK_A
//					|| secInfo.getTypeID() == SECConstant.SecuritiesType.STOCK_B
//					|| secInfo.getTypeID() == SECConstant.SecuritiesType.OTHERS)//证券类别=A股/B股/其它
//						accountTypeID = SECConstant.EntryAccountType.AccountType_6;//帐务类型=股票
//					if(secInfo.getTypeID() == SECConstant.SecuritiesType.ENTERPRISE_BOND
//					|| secInfo.getTypeID() == SECConstant.SecuritiesType.EXCHANGECENTER_NATIONAL_BOND
//					||secInfo.getTypeID() == SECConstant.SecuritiesType.BANK_NATIONAL_BOND
//					||secInfo.getTypeID() == SECConstant.SecuritiesType.POLICY_RELATED_FINANCIAL_BOND
//					||secInfo.getTypeID() == SECConstant.SecuritiesType.FINANCIAL_BOND)//证券类别=企业债现券/交易所国债现券/银行间国债现券/政策性金融债现券/金融债现券
//						accountTypeID = SECConstant.EntryAccountType.AccountType_5;
//					if(secInfo.getTypeID() == SECConstant.SecuritiesType.TRANSFORMABLE_BOND)//证券类别=可转债现券
//						accountTypeID = SECConstant.EntryAccountType.AccountType_7;
//					if(secInfo.getTypeID() == SECConstant.SecuritiesType.CENTRAL_BANK_NOTE)//证券类别=央行票据现券
//						accountTypeID = SECConstant.EntryAccountType.AccountType_8;
//					if(secInfo.getTypeID() == SECConstant.SecuritiesType.ENCLOSED_FUND)//证券类别=封闭式基金
//						accountTypeID = SECConstant.EntryAccountType.AccountType_9;
//					if(secInfo.getTypeID() == SECConstant.SecuritiesType.MUTUAL_FUND)//若证券类别=开放式基金
//						accountTypeID = SECConstant.EntryAccountType.AccountType_10;
//					
//					TransactionTypeInfo transTypeInfo = null;
//					try {
//						transTypeInfo = SECConstant.BusinessType.getTransactionTypeInfoByID(doInfo.getTransactionTypeId());
//					} catch (ITreasuryDAOException e) {
//						throw new SecuritiesDAOException(e);
//					}
//					if(transTypeInfo.getIsNeedNotifyForm() <= 0){//产生会计分录
//						
//						gleParam.setNetIncome(doInfo.getNetIncome());
//						gleParam.setPrincipal(doInfo.getQuantity()*doInfo.getUnitCost());
//						gleParam.setAccountType(accountTypeID);
//						//证券销售/差价收入=实际收付-成本
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
//		log.debug("------结束交割单业务复核::checkDeliveryOrder-----------");		
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
	 * 针对一个业务单位下一个资金帐户下的一个证券代码，从起日到止日，对其所有出库交易进行成本结转。
	 * */
	public void carryCost(CarryCostParam param) throws  RemoteException,SecuritiesException{
		log.debug("----------开始成本结转-------------");
		log.debug("----------输入的参数为:"+param);
		SEC_DeliveryOrderDAO deliveryOderDAO = new SEC_DeliveryOrderDAO();
		Collection c = deliveryOderDAO.getDeliveryOrderForCarryCost(param.getClientID(), param.getAccountID(), param.getSecuritiesID(), param.getStartDate(), param.getEndDate());
		Iterator it = c.iterator();
		while(it.hasNext()){
			DeliveryOrderInfo deliveryOrderInfo = (DeliveryOrderInfo) it.next();
			log.debug("----------正在被处理的交割单为:"+deliveryOrderInfo);
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
				log.debug("------该交割单对应的日结信息为:"+foundDailyStockInfo);
	
				CarryCostRecordInfo carryCostRecordInfo = new CarryCostRecordInfo(deliveryOrderInfo,foundDailyStockInfo);
				SEC_CarryCostRecordDAO carryCostRecordDAO = new SEC_CarryCostRecordDAO();
				log.debug("------新增的结转成本明细信息为:"+carryCostRecordInfo);
				carryCostRecordDAO.add(carryCostRecordInfo);
				deliveryOrderInfo.setTransactionTypeId(SECConstant.BusinessType.CARRY_COST.CARRY_COST);
				System.out.println("---------------产生结转成本的会计分录");
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
		//在本次“结转成本”产生的所有会计分录中，对机构号ID、币种号、科目代码均相同的分录，
		//应该汇总其金额、使之合并成一笔分录
		//SecuritiesGeneralLedgerOperation securitiesGeneralLedgerOperation = new SecuritiesGeneralLedgerOperation();
		//securitiesGeneralLedgerOperation.combineGLEntryForCarryCost(param.getOfficeID(), param.getCurrencyID(), param.get)
		log.debug("----------完成成本结转-------------");		
	}
	
	/**
	 * 本系统支持T+N交易，即交割单的交易日（或称成交日）可以和交割日（或称结算日、记帐日）不同。如果已达成了成交协议，
	 * 不论是否当时交割，都可以即时对交割单进行复核。也就是说，交割单可以在交易日录入并复核，如果录入时交割日晚于交割日，
	 * 则此笔业务所涉及的资金或库存不会即时发生变化，而是要到系统运行到交割日当天时，才会发生变化
	 * */
	public void deliverAutomatically(long officeID,long currencyID) throws RemoteException,SecuritiesException{
		log.debug("-------开始自动交割----------");
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
		log.debug("-------结束自动交割----------");		
	}
	
	
	/**
	 * 根据交割单信息进行资金账户操作
		* @param accountParam 
		* @param isDelete是否是删除操作
		* @return
		* @throws
		 */
	private void operateAccount(DeliveryOrderInfo doInfo,boolean isDelete,boolean isAutoDelivery) throws RemoteException, AccounStatusException, AccountOverDraftException, SecuritiesException {
		log.debug("------开始资金账户处理-----------");		
		
		if(doInfo.getAccountId() < 0){
			log.debug("-----该业务不使用资金帐户或者参数传递错误,结束资金账户处理-----------");
			return;
		}
		TransactionTypeInfo transTypeInfo = null;
		transTypeInfo = doInfo.getTransactionTypeInfo();
		if(transTypeInfo.getCapitalDirection() > 0){//需要更新资金账户
			log.debug("-----需要更新资金账户----------");
			SecuritiesAccountOperation accOperation = new SecuritiesAccountOperation(true);
			AccountParam accParam = new AccountParam(doInfo);
			log.debug("-----资金账户传入参数: " + accParam);
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
			}else if(transTypeInfo.getCapitalDirection() == SECConstant.Direction.PAY_AND_RECEIVE){//一付一收
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
		log.debug("------结束资金账户处理-----------");		
	}	
	
	/**
	 * 根据交易类型定义进行库存的处理
	 * @param doInfo 交割单信息
	 * @param isDelete是否是反向交易(取消操作)
	 * 
	 * */
	private void operateStock(DeliveryOrderInfo doInfo,boolean isDelete,boolean isAutoDelivery) throws RemoteException, SecuritiesException{
		log.debug("------开始库存处理-----------");		
		
		StockBiz stockOperation = new StockBiz();
		TransactionTypeInfo transTypeInfo = null;
		transTypeInfo = doInfo.getTransactionTypeInfo();
		if(transTypeInfo.getStockDirection() > 0){//需要更新库存 
			log.debug("-----需要更新库存----------");			
			if(transTypeInfo.getStockDirection() == SECConstant.StockDirection.IN){
				if(isDelete){
					SecuritiesStockParam stockParam = new SecuritiesStockParam(doInfo,StockBiz.STOCK_OPERATION_CANCELENTER);
					stockOperation.cancelEnterStock(stockParam);
				}
				else{
					SecuritiesStockParam stockParam = new SecuritiesStockParam(doInfo,StockBiz.STOCK_OPERATION_ENTER);
					if(isAutoDelivery)
						stockOperation.deliverStock(stockParam);
					else
						stockOperation.enterStock(stockParam);
				}
			}else{// stock out
					DeliveryOrderInfo updatedDOInfo = new DeliveryOrderInfo();
						if(transTypeInfo.getStockDirection() == SECConstant.StockDirection.OUT){
							if(isDelete){
								SecuritiesStockParam stockParam = new SecuritiesStockParam(doInfo,StockBiz.STOCK_OPERATION_CANCELEXIT);					
								stockOperation.cancelExitStock(stockParam);
								updatedDOInfo.setId(doInfo.getId());
								updatedDOInfo.setUnitCost(0.0);
								updatedDOInfo.setUnitNetProfitLoss(0.0);
								updatedDOInfo.setUnitProfitLoss(0.0);	
								updateDeliveryOrder(updatedDOInfo);
							}//end isDelete
							else{	
								SecuritiesStockParam stockParam = new SecuritiesStockParam(doInfo,StockBiz.STOCK_OPERATION_EXIT);
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
				}else{//一出一入
					if(isDelete){
						//IN
						SecuritiesStockParam stockParamIn = new SecuritiesStockParam(doInfo,StockBiz.STOCK_OPERATION_CANCELENTER);
						stockOperation.cancelEnterStock(stockParamIn);
						//OUT
						SecuritiesStockParam stockParamOut = new SecuritiesStockParam(doInfo,StockBiz.STOCK_OPERATION_CANCELEXIT);
						stockParamOut.setClient(doInfo.getCompanyAccountId());//业务单位名称
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
						SecuritiesStockParam stockParamIN = new SecuritiesStockParam(doInfo,StockBiz.STOCK_OPERATION_ENTER);
						if(isAutoDelivery)
							stockOperation.deliverStock(stockParamIN);
						else	
							stockOperation.enterStock(stockParamIN);
						//OUT
						SecuritiesStockParam stockParamOut = new SecuritiesStockParam(doInfo,StockBiz.STOCK_OPERATION_EXIT);
						stockParamOut.setClient(doInfo.getCompanyAccountId());//业务单位名称
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
		    	SecuritiesStockParam stockParam = new SecuritiesStockParam(doInfo,StockBiz.STOCK_OPERATION_CANCELFREEZE);
		    	stockOperation.cancelFreezeStock(stockParam);	    		    		
	    	}else{
		    	SecuritiesStockParam stockParam = new SecuritiesStockParam(doInfo,StockBiz.STOCK_OPERATION_FREEZE);
		    	stockOperation.freezeStock(stockParam);
	    	}
	    }else if(transTypeInfo.getFrozenProcess() == SECConstant.FrozenProcess.CANCEL_FREEZE){
	    	if(isDelete){
		    	SecuritiesStockParam stockParam = new SecuritiesStockParam(doInfo,StockBiz.STOCK_OPERATION_FREEZE);
		    	stockOperation.freezeStock(stockParam);	    		
	    	}else{
		    	SecuritiesStockParam stockParam = new SecuritiesStockParam(doInfo,StockBiz.STOCK_OPERATION_CANCELFREEZE);
		    	stockOperation.cancelFreezeStock(stockParam);
	    	}
	    }
		log.debug("------结束库存处理-----------");			

	} 
	
	
	/**
	 * 根据交易类型定义进行登记薄的处理
	 * @param doInfo 交割单信息
	 * @param isDelete是否是反向交易(取消操作)
	 * 
	 * */	
	private long operateRegister(DeliveryOrderInfo doInfo,boolean isDelete,boolean isAutoDelivery)throws RemoteException,SecuritiesException{
		log.debug("------开始登记薄处理-----------");		
		if(isAutoDelivery){
			log.debug("------自动交割不进行登记薄处理-----------");	
			return -1;
		}
		TransactionTypeInfo transTypeInfo = doInfo.getTransactionTypeInfo();
		
		if(!(transTypeInfo.getRegisterProcess() == SECConstant.RegisterProcess.CAPITAL
				|| transTypeInfo.getRegisterProcess() == SECConstant.RegisterProcess.APPLICATION_APPLY
				|| transTypeInfo.getRegisterProcess() == SECConstant.RegisterProcess.LONGTERM_BUYIN
				) && doInfo.getRegisterId() <= 0){
			log.debug("------登记薄ID不存在,退出登记薄处理--------");
			return -1;
		}

		
		RegisterBiz registerOperation = new RegisterBiz();
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
		
		log.debug("------登记薄ID是:"+registerID);
		log.debug("------结束登记薄处理-----------");
		return registerID;		
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
	/**
	 *从回购买卖登记薄中获得所有未返款的交割单 
	 * */
	public Collection getAllNotRepayDeliverOrderID(long transactionTypeID) throws SecuritiesException{
		RegisterBiz registerOperation = null;
		try {
			registerOperation = new RegisterBiz();
		} catch (Exception e) {
			//No exception will happen at here
		}
		return registerOperation.getAllNotRepayDeliverOrderID(transactionTypeID);

	}
	/**
	 * 每一笔证券买入的申请在提交时，系统将首先检查单一产品（即一个证券代码或证券名称）或该证券类别的授信总额度。
	 *状态为“已结束”、“已拒绝”和“已取消”的申请书不在统计范围之内，若申请书状态从“已提交”、“已审核”或“已使用”变为“已结束”、“已拒绝”或“已取消”后，
	 *该申请书的额度应当扣除，对于“已使用”的申请书，应当只扣除未被交割单使用的金额。
	 *@param1 securitiesID 证券ID，如果是获取一类证券的额度，该值为-1
	 *@param2 securitiesID 证券类别ID，如果是获取一种证券的额度，该值为-1
	 * */
	public UsableCreditLineOfSecuritiesInfo getUsableCreditLineOfSingleSecurities(long securitiesID,long securitiesTypeID) throws SecuritiesException{
		UsableCreditLineOfSecuritiesInfo resInfo = new UsableCreditLineOfSecuritiesInfo();
		
		SecuritiesTypeCreditLineDAO securitiesTypeCreditLineDAO = new SecuritiesTypeCreditLineDAO();
		SecuritiesCreditLineDAO securitiesCreditLineDAO = new SecuritiesCreditLineDAO();
		if(securitiesID > 0){
			log.debug("计算证券ID为: "+securitiesID+" 的可用额度");
			resInfo.setCreditLine(securitiesCreditLineDAO.getCreditLine(securitiesID));
		}
		else{
			log.debug("计算证券类型ID为: "+securitiesTypeID+" 的可用额度");			
			resInfo.setCreditLine(securitiesTypeCreditLineDAO.getCreditLine(securitiesTypeID));
		}

		SEC_SecuritiesStockDAO securitiesStockDAO = new SEC_SecuritiesStockDAO();
		resInfo.setCost(securitiesStockDAO.getAmountOfSecuritiesStock(securitiesID, securitiesTypeID));
		log.debug("交割单的成本为:"+resInfo.getCost());
		
		SEC_ApplyDAO applyDAO = new SEC_ApplyDAO();
		resInfo.setAmountOfApprovedAndUsing(applyDAO.getAmountOfApprovedAndUsing(securitiesID, securitiesTypeID));
		log.debug("已审批未使用占用的额度为:"+resInfo.getAmountOfApprovedAndUsing());		
		double applyAmount = applyDAO.getAmountOfUsed(securitiesID, securitiesTypeID);
		log.debug("申请书中已审批使用占用的额度为:"+applyAmount);		
		SEC_DeliveryOrderDAO deliveryOrderDAO = new SEC_DeliveryOrderDAO();
		double applyAmountUsedByDeliveryOrder = deliveryOrderDAO.getApplyAmountThatUsedByDeliveryOrder(securitiesID, securitiesTypeID);
		log.debug("其中被交割单占用的额度为:"+applyAmountUsedByDeliveryOrder);		
		double amount = applyAmount - applyAmountUsedByDeliveryOrder;
		log.debug("申请书中已审批使用未被交割单占用的额度为:"+amount);		
		if(amount > 0)
			resInfo.setAmountOfUsed(amount);
		
		
		resInfo.setAmount(resInfo.getCreditLine()-resInfo.getCost()-resInfo.getAmountOfApprovedAndUsing() - resInfo.getAmountOfUsed());
		
		log.debug("受信额度信息为:"+resInfo);
		return resInfo;
	}
	
}
