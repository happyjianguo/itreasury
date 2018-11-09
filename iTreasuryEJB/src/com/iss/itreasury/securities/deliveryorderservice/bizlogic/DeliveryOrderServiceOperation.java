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
	
	private final static String VIOLATION_REASON_1 = "�ɽ��۴����������й涨����߳ɽ���;";
	private final static String VIOLATION_REASON_2 = "�ɽ���С���������й涨����ͳɽ���;";	
	private final static String VIOLATION_REASON_3 = "�ɽ��۲������������й涨�ĳɽ���;";	
	private final static String VIOLATION_REASON_4 = "�ɽ�����/�������������й涨����߳ɽ�����/���;";	
	private final static String VIOLATION_REASON_5 = "�ɽ�����/���С���������й涨����ͳɽ�����/���;";
	private final static String VIOLATION_REASON_6 = "�ɽ�����/�������������й涨�ĳɽ�����/���;";
	private final static String VIOLATION_REASON_7 = "�ɽ����������������й涨������ɽ�����;";	
	private final static String VIOLATION_REASON_8 = "�ɽ����������������й涨������ɽ�����;";	
	private final static String VIOLATION_REASON_9 = "�ɽ����ڲ������������й涨�ĳɽ�����;";	
	private final static String VIOLATION_REASON_10 = "�ɽ����ڲ����������й涨�ĳɽ����ڷ�Χ֮��;";	
	private final static String VIOLATION_REASON_11 = "�ɽ����ʴ����������й涨����߳ɽ�����;";	
	private final static String VIOLATION_REASON_12 = "�ɽ�����С���������й涨����ͳɽ�����;";	
	private final static String VIOLATION_REASON_13 = "�ɽ����ʲ������������й涨�ĳɽ�����;";	
	
	
	private Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);	
	/**
	 * ����ά�����������Ե����ݿ�����
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
				throw new RemoteException("EJBHomeFactory���Ӵ���",e);
			}
			deliveryOrderServiceFacade = (DeliveryOrderService) home.create();
		}

		catch (CreateException ce)
		{
			throw new RemoteException("����CreateException",ce);
		}				
	}
	
	public DeliveryOrderServiceOperation(Connection conn){
		transConn = conn;
	}
	

	
	static public void main(String[] args){
		DeliveryOrderServiceOperation op = new DeliveryOrderServiceOperation(null);
		Timestamp date = Env.getSecuritiesSystemDate(1, 1);
//		date = UtilOperation.getNextNDay(date, -1);
//		System.out.println("�����:"+date);
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
//	 * ����������
//	 * @return �ǼǱ�ID
//	 * */
//	public long save(DeliveryOrderInfo doInfo) throws  RemoteException,SecuritiesException{
//		return deliveryOrderServiceFacade.save(doInfo);
//	}
//	
//	/**
//	 * ���ɾ������
//	 * */
//	public void delete(DeliveryOrderInfo doInfo) throws  RemoteException,SecuritiesException{
//		deliveryOrderServiceFacade.delete(doInfo);
//	}
//	
//	/**
//	 * ������˲���
//	 * */
//	public void check(DeliveryOrderInfo doInfo) throws  RemoteException,SecuritiesException{
//
//	}
//	
//	/**
//	 * ���ȡ�����˲���
//	 * */
//	public void cancelCheck(DeliveryOrderInfo doInfo) throws  RemoteException,SecuritiesException{
//		deliveryOrderServiceFacade.cancelCheck(doInfo);
//	}		
	
	/**
	 *�ӻع������ǼǱ��л������δ����Ľ�� 
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
	 * ����������
	 * @return �ǼǱ�ID
	 * */
	public long saveDeliveryOrder(DeliveryOrderInfo doInfo) throws  RemoteException,SecuritiesException{
		return deliveryOrderServiceFacade.saveDeliveryOrder(doInfo);
	}
	
	/**
	 * ���ɾ������
	 * */
	public void deleteDeliveryOrder(DeliveryOrderInfo doInfo) throws  RemoteException,SecuritiesException{
		deliveryOrderServiceFacade.deleteDeliveryOrder(doInfo);
	}
	/**
	 * ������˲���
	 * */
	public void checkDeliveryOrder(DeliveryOrderInfo doInfo) throws  RemoteException,SecuritiesException{
		if(transConn != null){
			DeliveryOrderServiceBean deliveryOrderServiceBean = new DeliveryOrderServiceBean(transConn);
			deliveryOrderServiceBean.checkDeliveryOrder(doInfo);
		}else
			deliveryOrderServiceFacade.checkDeliveryOrder(doInfo);				
	}
	/**
	 * ���ȡ�����˲���
	 * */
	public void cancelCheckDeliveryOrder(DeliveryOrderInfo doInfo) throws  RemoteException,SecuritiesException{
		if(transConn != null){
			DeliveryOrderServiceBean deliveryOrderServiceBean = new DeliveryOrderServiceBean(transConn);
			deliveryOrderServiceBean.cancelCheckDeliveryOrder(doInfo);
		}else
			deliveryOrderServiceFacade.cancelCheckDeliveryOrder(doInfo);						
		
	}
	
	/**
	 * ���һ��ҵ��λ��һ���ʽ��ʻ��µ�һ��֤ȯ���룬�����յ�ֹ�գ��������г��⽻�׽��гɱ���ת��
	 * */
	public void carryCost(CarryCostParam param) throws  RemoteException,SecuritiesException{
		deliveryOrderServiceFacade.carryCost(param);
	}
	

	/**
	 * ��ϵͳ֧��T+N���ף�������Ľ����գ���Ƴɽ��գ����Ժͽ����գ���ƽ����ա������գ���ͬ������Ѵ���˳ɽ�Э�飬
	 * �����Ƿ�ʱ��������Լ�ʱ�Խ�����и��ˡ�Ҳ����˵����������ڽ�����¼�벢���ˣ����¼��ʱ���������ڽ����գ�
	 * ��˱�ҵ�����漰���ʽ���治�ἴʱ�����仯������Ҫ��ϵͳ���е������յ���ʱ���Żᷢ���仯
	 * */
	public void deliverAutomatically(long officeID,long currencyID) throws RemoteException,SecuritiesException{
		deliveryOrderServiceFacade.deliverAutomatically(officeID,currencyID);
	}
	
	/**
	 * ÿһ��֤ȯ������������ύʱ��ϵͳ�����ȼ�鵥һ��Ʒ����һ��֤ȯ�����֤ȯ���ƣ����֤ȯ���������ܶ�ȡ�
	 *״̬Ϊ���ѽ����������Ѿܾ����͡���ȡ�����������鲻��ͳ�Ʒ�Χ֮�ڣ���������״̬�ӡ����ύ����������ˡ�����ʹ�á���Ϊ���ѽ����������Ѿܾ�������ȡ������
	 *��������Ķ��Ӧ���۳������ڡ���ʹ�á��������飬Ӧ��ֻ�۳�δ�����ʹ�õĽ�
	 *@param1 securitiesID ֤ȯID������ǻ�ȡһ��֤ȯ�Ķ�ȣ���ֵΪ-1
	 *@param2 securitiesID ֤ȯ���ID������ǻ�ȡһ��֤ȯ�Ķ�ȣ���ֵΪ-1
	 * */
	public UsableCreditLineOfSecuritiesInfo getUsableCreditLineOfSingleSecurities(long securitiesID,long securitiesTypeID) throws SecuritiesException{
		UsableCreditLineOfSecuritiesInfo resInfo = new UsableCreditLineOfSecuritiesInfo();
		
		SecuritiesTypeCreditLineDAO securitiesTypeCreditLineDAO = new SecuritiesTypeCreditLineDAO();
		SecuritiesCreditLineDAO securitiesCreditLineDAO = new SecuritiesCreditLineDAO();
		if(securitiesID > 0){
			log.debug("����֤ȯIDΪ: "+securitiesID+" �Ŀ��ö��");
			resInfo.setCreditLine(securitiesCreditLineDAO.getCreditLine(securitiesID));
		}
		else{
			log.debug("����֤ȯ����IDΪ: "+securitiesTypeID+" �Ŀ��ö��");			
			resInfo.setCreditLine(securitiesTypeCreditLineDAO.getCreditLine(securitiesTypeID));
		}

		SEC_SecuritiesStockDAO securitiesStockDAO = new SEC_SecuritiesStockDAO();
		resInfo.setCost(securitiesStockDAO.getAmountOfSecuritiesStock(securitiesID, securitiesTypeID));
		log.debug("����ĳɱ�Ϊ:"+resInfo.getCost());
		
		SEC_ApplyDAO applyDAO = new SEC_ApplyDAO();
		resInfo.setAmountOfApprovedAndUsing(applyDAO.getAmountOfApprovedAndUsing(securitiesID, securitiesTypeID));
		log.debug("������δʹ��ռ�õĶ��Ϊ:"+resInfo.getAmountOfApprovedAndUsing());		
		double applyAmount = applyDAO.getAmountOfUsed(securitiesID, securitiesTypeID);
		log.debug("��������������ʹ��ռ�õĶ��Ϊ:"+applyAmount);		
		SEC_DeliveryOrderDAO deliveryOrderDAO = new SEC_DeliveryOrderDAO();
		double applyAmountUsedByDeliveryOrder = deliveryOrderDAO.getApplyAmountThatUsedByDeliveryOrder(securitiesID, securitiesTypeID);
		log.debug("���б����ռ�õĶ��Ϊ:"+applyAmountUsedByDeliveryOrder);		
		double amount = applyAmount - applyAmountUsedByDeliveryOrder;
		log.debug("��������������ʹ��δ�����ռ�õĶ��Ϊ:"+amount);		
		if(amount > 0)
			resInfo.setAmountOfUsed(amount);
		
		
		resInfo.setAmount(resInfo.getCreditLine()-resInfo.getCost()-resInfo.getAmountOfApprovedAndUsing() - resInfo.getAmountOfUsed());
		
		log.debug("���Ŷ����ϢΪ:"+resInfo);
		return resInfo;
	}
	

	/**
	 * ���ָ���ɽ����ڵ����н���������Υ�潻��������ϱ�ǣ���ע��Υ��ԭ��
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
			log.debug("���ڼ��Ľ��Ϊ:"+tmpDOInfo);
			log.debug("������ϢΪ:"+tmpDOInfo.getTransactionTypeInfo());
			
			SEC_ApplyDAO applyDAO = new SEC_ApplyDAO();
			ApplyInfo tmpApplyInfo;
			try {
				tmpApplyInfo = (ApplyInfo) applyDAO.findByID(tmpDOInfo.getApplyFormId(), ApplyInfo.class);
				if(tmpApplyInfo.getId() < 0)
					continue;
				log.debug("���ڶԱȵ�������Ϊ:"+tmpApplyInfo);	
			} catch (ITreasuryDAOException e1) {
				throw new SecuritiesException("",e1);
			}
			
			boolean isNeedUpdate = false;
			TransactionTypeInfo transTypeInfo = tmpDOInfo.getTransactionTypeInfo();
			if(transTypeInfo.getIsNeedApplyForm() <= 0){
				log.debug("û�������飬����Υ����");
				continue;
			}
			
			DeliveryOrderInfo updatedDOInfo = new DeliveryOrderInfo();
			updatedDOInfo.setId(tmpDOInfo.getId());
			String violationReason = "";
			if(transTypeInfo.getPriceTarget() != 0){
				//���۸�ָ��
				if(transTypeInfo.getPriceTarget() == 1 && tmpDOInfo.getPrice() > tmpApplyInfo.getPrice()){//������/С�ڵ���
					updatedDOInfo.setIsViolation(1);
					updatedDOInfo.setReasonOfViolation(violationReason+VIOLATION_REASON_1);
					log.debug("��� "+updatedDOInfo.getId() + " "+ violationReason);
				}else if(transTypeInfo.getPriceTarget() == 2 && tmpDOInfo.getPrice() < tmpApplyInfo.getPrice()){//��С��/���ڵ���
					updatedDOInfo.setIsViolation(1);
					updatedDOInfo.setReasonOfViolation(violationReason+VIOLATION_REASON_2);				
				}else if(transTypeInfo.getPriceTarget() == 3 && tmpDOInfo.getPrice() != tmpApplyInfo.getPrice()){//��С��/���ڵ���
					updatedDOInfo.setIsViolation(1);
					updatedDOInfo.setReasonOfViolation(violationReason+VIOLATION_REASON_3);				
				}
			}
			
			if(transTypeInfo.getAmountTarget() != 0){
				//�������ָ��
				log.debug("�������ָ��");
				double[] applyAmountAndQuan = doDAO.sumAmountAndQuantityByApplyFormID(tmpDOInfo.getApplyFormId());
				double usableAmount = tmpApplyInfo.getAmount()-applyAmountAndQuan[0];
				log.debug("---------usableAmount: "+usableAmount);
				double usableQuantity = tmpApplyInfo.getQuantity()-applyAmountAndQuan[1];
				log.debug("---------usableQuantity: "+usableQuantity);				
				if(transTypeInfo.getAmountTarget() == 1 &&	(
					   usableAmount < 0.0
					|| usableQuantity < 0.0
					)
				){//������/С�ڵ���
					updatedDOInfo.setIsViolation(1);
					updatedDOInfo.setReasonOfViolation(violationReason+VIOLATION_REASON_4);
					log.debug("zzzzzzzzzz"+updatedDOInfo.getReasonOfViolation());					
				}else if(transTypeInfo.getAmountTarget() == 2 && ( usableAmount >= 0
					|| 	 usableQuantity >= 0.0)
						){//��С��/���ڵ���
					updatedDOInfo.setIsViolation(1);
					updatedDOInfo.setReasonOfViolation(violationReason+VIOLATION_REASON_5);

				}else if(transTypeInfo.getAmountTarget() == 3 && (0.0 != usableAmount
				|| 0.0 != usableQuantity)){//��С��/���ڵ���
					updatedDOInfo.setIsViolation(1);
					updatedDOInfo.setReasonOfViolation(violationReason+VIOLATION_REASON_6);				
				}				
				
			}
			
			
			if(transTypeInfo.getDateTarget() != 0){
				//�������ָ��
				if(transTypeInfo.getDateTarget() == 1 && tmpDOInfo.getTransactionDate().after(tmpApplyInfo.getTransactionStartDate())){//������/С�ڵ���
					updatedDOInfo.setIsViolation(1);
					updatedDOInfo.setReasonOfViolation(violationReason+VIOLATION_REASON_7);
				}else if(transTypeInfo.getDateTarget() == 2 && tmpDOInfo.getTransactionDate().before(tmpApplyInfo.getTransactionStartDate())){//��С��/���ڵ���
					updatedDOInfo.setIsViolation(1);
					updatedDOInfo.setReasonOfViolation(violationReason+VIOLATION_REASON_8);				
				}else if(transTypeInfo.getDateTarget() == 3 && tmpDOInfo.getTransactionDate().equals(tmpApplyInfo.getTransactionStartDate())){//��С��/���ڵ���
					updatedDOInfo.setIsViolation(1);
					updatedDOInfo.setReasonOfViolation(violationReason+VIOLATION_REASON_9);				
				}else if(transTypeInfo.getDateTarget() == 4 && (tmpDOInfo.getTransactionDate().before(tmpApplyInfo.getTransactionStartDate()) || tmpDOInfo.getTransactionDate().after(tmpApplyInfo.getTransactionEndDate()))){//��С��/���ڵ���
					updatedDOInfo.setIsViolation(1);
					updatedDOInfo.setReasonOfViolation(violationReason+VIOLATION_REASON_10);				
				}					
				
			}			
			
			
			if(transTypeInfo.getInterestRateTarget() != 0){
				//�������ָ��
				if(transTypeInfo.getInterestRateTarget() == 1 && tmpDOInfo.getRate() > tmpApplyInfo.getRate()){//������/С�ڵ���
					updatedDOInfo.setIsViolation(1);
					updatedDOInfo.setReasonOfViolation(violationReason+VIOLATION_REASON_11);
				}else if(transTypeInfo.getInterestRateTarget() == 2 && tmpDOInfo.getRate() < tmpApplyInfo.getRate()){//��С��/���ڵ���
					updatedDOInfo.setIsViolation(1);
					updatedDOInfo.setReasonOfViolation(violationReason+VIOLATION_REASON_12);				
				}else if(transTypeInfo.getInterestRateTarget() == 3 && tmpDOInfo.getRate() != tmpApplyInfo.getRate()){//��С��/���ڵ���
					updatedDOInfo.setIsViolation(1);
					updatedDOInfo.setReasonOfViolation(violationReason+VIOLATION_REASON_13);				
				}				
				
			}			
						
			try {			
				if(!updatedDOInfo.getReasonOfViolation().trim().equalsIgnoreCase("")){
					System.out.println("���:"+tmpDOInfo.getId()+"Υ����ϢΪ:"+violationReason);					
					doDAO.update(updatedDOInfo);
				}
			} catch (ITreasuryDAOException e2) {
				throw new SecuritiesException("",e2);
			}
			}
		
		
	}

	
	
}
