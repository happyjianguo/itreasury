/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-3
 */
package com.iss.itreasury.securities.deliveryorderservice.bizlogic;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.CreateException;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.apply.dao.SEC_ApplyDAO;
import com.iss.itreasury.securities.apply.dataentity.ApplyInfo;
import com.iss.itreasury.securities.deliveryorder.dao.SEC_DeliveryOrderDAO;
import com.iss.itreasury.securities.deliveryorder.dataentity.DeliveryOrderInfo;
import com.iss.itreasury.securities.deliveryorderservice.dao.SecuritiesCreditLineDAO;
import com.iss.itreasury.securities.deliveryorderservice.dao.SecuritiesTypeCreditLineDAO;
import com.iss.itreasury.securities.deliveryorderservice.dataentity.CarryCostParam;
import com.iss.itreasury.securities.deliveryorderservice.dataentity.UsableCreditLineOfSecuritiesInfo;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.register.bizlogic.RegisterOperation;
import com.iss.itreasury.securities.setting.dataentity.TransactionTypeInfo;
import com.iss.itreasury.securities.stock.dao.SEC_SecuritiesStockDAO;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log4j;

/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class DeliveryOrderServiceOperation {
	
	private DeliveryOrderService deliveryOrderServiceFacade = null;
	
	private final static String VIOLATION_REASON_1 = "成交价大于申请书中规定的最高成交价;";
	private final static String VIOLATION_REASON_2 = "成交价小于申请书中规定的最低成交价;";	
	private final static String VIOLATION_REASON_3 = "成交价不等于申请书中规定的成交价;";	
	private final static String VIOLATION_REASON_4 = "成交数量/金额大于申请书中规定的最高成交数量/金额;";	
	private final static String VIOLATION_REASON_5 = "成交数量/金额小于申请书中规定的最低成交数量/金额;";
	private final static String VIOLATION_REASON_6 = "成交数量/金额不等于申请书中规定的成交数量/金额;";
	private final static String VIOLATION_REASON_7 = "成交日期晚于申请书中规定的最晚成交日期;";	
	private final static String VIOLATION_REASON_8 = "成交日期早于申请书中规定的最早成交日期;";	
	private final static String VIOLATION_REASON_9 = "成交日期不等于申请书中规定的成交日期;";	
	private final static String VIOLATION_REASON_10 = "成交日期不在申请书中规定的成交日期范围之内;";	
	private final static String VIOLATION_REASON_11 = "成交利率大于申请书中规定的最高成交利率;";	
	private final static String VIOLATION_REASON_12 = "成交利率小于申请书中规定的最低成交利率;";	
	private final static String VIOLATION_REASON_13 = "成交利率不等于申请书中规定的成交利率;";	
	
	
	private Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);	
	/**
	 * 用于维护事务完整性的数据库连接
	 * */
	private Connection transConn = null;
	
	public DeliveryOrderServiceOperation() throws RemoteException{
		try
		{
			DeliveryOrderServiceHome home;
			try {
				home = (DeliveryOrderServiceHome) EJBHomeFactory.getFactory().lookUpHome(
							DeliveryOrderServiceHome.class);
			} catch (Exception e) {
				throw new RemoteException("EJBHomeFactory连接错误",e);
			}
			deliveryOrderServiceFacade = (DeliveryOrderService) home.create();
		}

		catch (CreateException ce)
		{
			throw new RemoteException("发生CreateException",ce);
		}				
	}
	
	public DeliveryOrderServiceOperation(Connection conn){
		transConn = conn;
	}
	

	
	static public void main(String[] args){
		DeliveryOrderServiceOperation op = new DeliveryOrderServiceOperation(null);
		Timestamp date = Env.getSecuritiesSystemDate(1, 1);
//		date = UtilOperation.getNextNDay(date, -1);
//		System.out.println("检查日:"+date);
//		try {
//			op.checkViolativeDeliveryOrder(date);
//		} catch (SecuritiesException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		UsableCreditLineOfSecuritiesInfo info= null;
		try {
			op.checkViolativeDeliveryOrder(date);
		} catch (SecuritiesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
//	/**
//	 * 交割单保存操作
//	 * @return 登记薄ID
//	 * */
//	public long save(DeliveryOrderInfo doInfo) throws  RemoteException,SecuritiesException{
//		return deliveryOrderServiceFacade.save(doInfo);
//	}
//	
//	/**
//	 * 交割单删除操作
//	 * */
//	public void delete(DeliveryOrderInfo doInfo) throws  RemoteException,SecuritiesException{
//		deliveryOrderServiceFacade.delete(doInfo);
//	}
//	
//	/**
//	 * 交割单复核操作
//	 * */
//	public void check(DeliveryOrderInfo doInfo) throws  RemoteException,SecuritiesException{
//
//	}
//	
//	/**
//	 * 交割单取消复核操作
//	 * */
//	public void cancelCheck(DeliveryOrderInfo doInfo) throws  RemoteException,SecuritiesException{
//		deliveryOrderServiceFacade.cancelCheck(doInfo);
//	}		
	
	/**
	 *从回购买卖登记薄中获得所有未返款的交割单 
	 * */
	public Collection getAllNotRepayDeliverOrderID(long transactionTypeID) throws SecuritiesException{
		RegisterOperation registerOperation = null;
		try {
			registerOperation = new RegisterOperation(false);
		} catch (RemoteException e) {
			//No exception will happen at here
		}
		return registerOperation.getAllNotRepayDeliverOrderID(transactionTypeID);

	}
	
	
	/**
	 * 交割单保存操作
	 * @return 登记薄ID
	 * */
	public long saveDeliveryOrder(DeliveryOrderInfo doInfo) throws  RemoteException,SecuritiesException{
		return deliveryOrderServiceFacade.saveDeliveryOrder(doInfo);
	}
	
	/**
	 * 交割单删除操作
	 * */
	public void deleteDeliveryOrder(DeliveryOrderInfo doInfo) throws  RemoteException,SecuritiesException{
		deliveryOrderServiceFacade.deleteDeliveryOrder(doInfo);
	}
	/**
	 * 交割单复核操作
	 * */
	public void checkDeliveryOrder(DeliveryOrderInfo doInfo) throws  RemoteException,SecuritiesException{
		if(transConn != null){
			DeliveryOrderServiceBean deliveryOrderServiceBean = new DeliveryOrderServiceBean(transConn);
			deliveryOrderServiceBean.checkDeliveryOrder(doInfo);
		}else
			deliveryOrderServiceFacade.checkDeliveryOrder(doInfo);				
	}
	/**
	 * 交割单取消复核操作
	 * */
	public void cancelCheckDeliveryOrder(DeliveryOrderInfo doInfo) throws  RemoteException,SecuritiesException{
		if(transConn != null){
			DeliveryOrderServiceBean deliveryOrderServiceBean = new DeliveryOrderServiceBean(transConn);
			deliveryOrderServiceBean.cancelCheckDeliveryOrder(doInfo);
		}else
			deliveryOrderServiceFacade.cancelCheckDeliveryOrder(doInfo);						
		
	}
	
	/**
	 * 针对一个业务单位下一个资金帐户下的一个证券代码，从起日到止日，对其所有出库交易进行成本结转。
	 * */
	public void carryCost(CarryCostParam param) throws  RemoteException,SecuritiesException{
		deliveryOrderServiceFacade.carryCost(param);
	}
	

	/**
	 * 本系统支持T+N交易，即交割单的交易日（或称成交日）可以和交割日（或称结算日、记帐日）不同。如果已达成了成交协议，
	 * 不论是否当时交割，都可以即时对交割单进行复核。也就是说，交割单可以在交易日录入并复核，如果录入时交割日晚于交割日，
	 * 则此笔业务所涉及的资金或库存不会即时发生变化，而是要到系统运行到交割日当天时，才会发生变化
	 * */
	public void deliverAutomatically(long officeID,long currencyID) throws RemoteException,SecuritiesException{
		deliveryOrderServiceFacade.deliverAutomatically(officeID,currencyID);
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
	

	/**
	 * 检查指定成交日期的所有交割单，如果是违规交割单，则做上标记，并注明违规原因。
	 * */
	public void checkViolativeDeliveryOrder(Timestamp transactionDate) throws SecuritiesException{
		SEC_DeliveryOrderDAO doDAO = new SEC_DeliveryOrderDAO();
		DeliveryOrderInfo conditoinDOInfo = new DeliveryOrderInfo();
		conditoinDOInfo.setTransactionDate(transactionDate);
		Collection c;
		try {
			c = doDAO.findByCondition(conditoinDOInfo);
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesException("",e);
		}
		Iterator it = c.iterator();
		while(it.hasNext()){
			DeliveryOrderInfo tmpDOInfo = (DeliveryOrderInfo) it.next();
			log.debug("正在检查的交割单为:"+tmpDOInfo);
			log.debug("交易信息为:"+tmpDOInfo.getTransactionTypeInfo());
			
			SEC_ApplyDAO applyDAO = new SEC_ApplyDAO();
			ApplyInfo tmpApplyInfo;
			try {
				tmpApplyInfo = (ApplyInfo) applyDAO.findByID(tmpDOInfo.getApplyFormId(), ApplyInfo.class);
				if(tmpApplyInfo.getId() < 0)
					continue;
				log.debug("正在对比的申请书为:"+tmpApplyInfo);	
			} catch (ITreasuryDAOException e1) {
				throw new SecuritiesException("",e1);
			}
			
			boolean isNeedUpdate = false;
			TransactionTypeInfo transTypeInfo = tmpDOInfo.getTransactionTypeInfo();
			if(transTypeInfo.getIsNeedApplyForm() <= 0){
				log.debug("没有申请书，不做违规检查");
				continue;
			}
			
			DeliveryOrderInfo updatedDOInfo = new DeliveryOrderInfo();
			updatedDOInfo.setId(tmpDOInfo.getId());
			String violationReason = "";
			if(transTypeInfo.getPriceTarget() != 0){
				//检查价格指标
				if(transTypeInfo.getPriceTarget() == 1 && tmpDOInfo.getPrice() > tmpApplyInfo.getPrice()){//不大于/小于等于
					updatedDOInfo.setIsViolation(1);
					updatedDOInfo.setReasonOfViolation(violationReason+VIOLATION_REASON_1);
					log.debug("交割单 "+updatedDOInfo.getId() + " "+ violationReason);
				}else if(transTypeInfo.getPriceTarget() == 2 && tmpDOInfo.getPrice() < tmpApplyInfo.getPrice()){//不小于/大于等于
					updatedDOInfo.setIsViolation(1);
					updatedDOInfo.setReasonOfViolation(violationReason+VIOLATION_REASON_2);				
				}else if(transTypeInfo.getPriceTarget() == 3 && tmpDOInfo.getPrice() != tmpApplyInfo.getPrice()){//不小于/大于等于
					updatedDOInfo.setIsViolation(1);
					updatedDOInfo.setReasonOfViolation(violationReason+VIOLATION_REASON_3);				
				}
			}
			
			if(transTypeInfo.getAmountTarget() != 0){
				//检查数量指标
				log.debug("检查数量指标");
				double[] applyAmountAndQuan = doDAO.sumAmountAndQuantityByApplyFormID(tmpDOInfo.getApplyFormId());
				double usableAmount = tmpApplyInfo.getAmount()-applyAmountAndQuan[0];
				log.debug("---------usableAmount: "+usableAmount);
				double usableQuantity = tmpApplyInfo.getQuantity()-applyAmountAndQuan[1];
				log.debug("---------usableQuantity: "+usableQuantity);				
				if(transTypeInfo.getAmountTarget() == 1 &&	(
					   usableAmount < 0.0
					|| usableQuantity < 0.0
					)
				){//不大于/小于等于
					updatedDOInfo.setIsViolation(1);
					updatedDOInfo.setReasonOfViolation(violationReason+VIOLATION_REASON_4);
					log.debug("zzzzzzzzzz"+updatedDOInfo.getReasonOfViolation());					
				}else if(transTypeInfo.getAmountTarget() == 2 && ( usableAmount >= 0
					|| 	 usableQuantity >= 0.0)
						){//不小于/大于等于
					updatedDOInfo.setIsViolation(1);
					updatedDOInfo.setReasonOfViolation(violationReason+VIOLATION_REASON_5);

				}else if(transTypeInfo.getAmountTarget() == 3 && (0.0 != usableAmount
				|| 0.0 != usableQuantity)){//不小于/大于等于
					updatedDOInfo.setIsViolation(1);
					updatedDOInfo.setReasonOfViolation(violationReason+VIOLATION_REASON_6);				
				}				
				
			}
			
			
			if(transTypeInfo.getDateTarget() != 0){
				//检查日期指标
				if(transTypeInfo.getDateTarget() == 1 && tmpDOInfo.getTransactionDate().after(tmpApplyInfo.getTransactionStartDate())){//不大于/小于等于
					updatedDOInfo.setIsViolation(1);
					updatedDOInfo.setReasonOfViolation(violationReason+VIOLATION_REASON_7);
				}else if(transTypeInfo.getDateTarget() == 2 && tmpDOInfo.getTransactionDate().before(tmpApplyInfo.getTransactionStartDate())){//不小于/大于等于
					updatedDOInfo.setIsViolation(1);
					updatedDOInfo.setReasonOfViolation(violationReason+VIOLATION_REASON_8);				
				}else if(transTypeInfo.getDateTarget() == 3 && tmpDOInfo.getTransactionDate().equals(tmpApplyInfo.getTransactionStartDate())){//不小于/大于等于
					updatedDOInfo.setIsViolation(1);
					updatedDOInfo.setReasonOfViolation(violationReason+VIOLATION_REASON_9);				
				}else if(transTypeInfo.getDateTarget() == 4 && (tmpDOInfo.getTransactionDate().before(tmpApplyInfo.getTransactionStartDate()) || tmpDOInfo.getTransactionDate().after(tmpApplyInfo.getTransactionEndDate()))){//不小于/大于等于
					updatedDOInfo.setIsViolation(1);
					updatedDOInfo.setReasonOfViolation(violationReason+VIOLATION_REASON_10);				
				}					
				
			}			
			
			
			if(transTypeInfo.getInterestRateTarget() != 0){
				//检查利率指标
				if(transTypeInfo.getInterestRateTarget() == 1 && tmpDOInfo.getRate() > tmpApplyInfo.getRate()){//不大于/小于等于
					updatedDOInfo.setIsViolation(1);
					updatedDOInfo.setReasonOfViolation(violationReason+VIOLATION_REASON_11);
				}else if(transTypeInfo.getInterestRateTarget() == 2 && tmpDOInfo.getRate() < tmpApplyInfo.getRate()){//不小于/大于等于
					updatedDOInfo.setIsViolation(1);
					updatedDOInfo.setReasonOfViolation(violationReason+VIOLATION_REASON_12);				
				}else if(transTypeInfo.getInterestRateTarget() == 3 && tmpDOInfo.getRate() != tmpApplyInfo.getRate()){//不小于/大于等于
					updatedDOInfo.setIsViolation(1);
					updatedDOInfo.setReasonOfViolation(violationReason+VIOLATION_REASON_13);				
				}				
				
			}			
						
			try {			
				if(!updatedDOInfo.getReasonOfViolation().trim().equalsIgnoreCase("")){
					System.out.println("交割单:"+tmpDOInfo.getId()+"违规信息为:"+violationReason);					
					doDAO.update(updatedDOInfo);
				}
			} catch (ITreasuryDAOException e2) {
				throw new SecuritiesException("",e2);
			}
			}
		
		
	}

	
	
}
