/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.securities.stock.bizlogic;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.SessionBean;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.deliveryorder.dao.SEC_DeliveryOrderDAO;
import com.iss.itreasury.securities.deliveryorder.dataentity.DeliveryOrderInfo;
import com.iss.itreasury.securities.exception.DuplicatePrimaryKeyException;
import com.iss.itreasury.securities.exception.SECInvalidRecordException;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.stock.dao.SEC_DailyStockDAO;
import com.iss.itreasury.securities.stock.dao.SEC_SecuritiesStockDAO;
import com.iss.itreasury.securities.stock.dao.SEC_VStockDetailDAO;
import com.iss.itreasury.securities.stock.dataentity.DailyStockInfo;
import com.iss.itreasury.securities.stock.dataentity.SecuritiesStockInfo;
import com.iss.itreasury.securities.stock.dataentity.SecuritiesStockReturn;
import com.iss.itreasury.securities.stock.dataentity.SecuritiesStockParam;
import com.iss.itreasury.securities.stock.dataentity.SingleAccountDailyStockParam;
import com.iss.itreasury.securities.stock.dataentity.VStockDetailInfo;
import com.iss.itreasury.securities.setting.dataentity.SecuritiesInfo;
import com.iss.itreasury.securities.setting.dao.SEC_SecuritiesDAO;
import com.iss.itreasury.securities.stock.exception.*;
import com.iss.itreasury.securities.util.NameRef;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Env;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class StockBiz implements SessionBean
{
	private javax.ejb.SessionContext mySessionCtx = null;
	final static long serialVersionUID = 3206093459760846163L;
	private Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);
	public final static int STOCK_OPERATION_ENTER = 0;
	public final static int STOCK_OPERATION_CANCELENTER = 1;
	public final static int STOCK_OPERATION_EXIT = 2;
	public final static int STOCK_OPERATION_CANCELEXIT = 3;
	public final static int STOCK_OPERATION_FREEZE = 4;
	public final static int STOCK_OPERATION_CANCELFREEZE = 5;
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
	/**
	 * ���
	 * */
	public void enterStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException{
		log.debug("------��ʼ��洦��::���-----------");		
		log.debug("------�������Ϊ:"+stockParam);		
		SEC_SecuritiesStockDAO dao = new SEC_SecuritiesStockDAO();
		SecuritiesStockInfo stockInfo = null;
		boolean isNew = false;
		try{
			stockInfo = getAndCheckStock(stockParam,SECConstant.StockDirection.IN);
		}catch(SECInvalidRecordException e)
		{//��治���ڣ�����һ������¼
			log.debug("------��治���ڣ�����һ������¼-----------");			
			SecuritiesStockInfo addStockInfo = new SecuritiesStockInfo();
			addStockInfo.resetInitValue();
			addStockInfo.setAccountID(stockParam.getAccountID());
			addStockInfo.setClientID(stockParam.getClient());
			addStockInfo.setSecuritiesID(stockParam.getSecuritiesID());
			//addStockInfo.setInitQuantity(stockParam.getQuantity());
			//addStockInfo.setInitCost(stockParam.getCost());
			//addStockInfo.setInitNetCost(stockParam.getNetCost());			
			long id = -1;
			
			try {
				id = dao.add(addStockInfo);
				addStockInfo.setId(id);
				stockInfo = addStockInfo;
				isNew = true;
				
			} catch (ITreasuryDAOException e1) {
				throw new SecuritiesDAOException(e1);
			}			
		}

		log.debug("-----���ڱ������Ŀ����ϢΪ:"+stockInfo);
		
	    Timestamp currentDate = Env.getSecuritiesSystemDate(stockParam.getOfficeID(), stockParam.getCurrencyID());
		Timestamp delvieryDate = stockParam.getDeliveryDate();
		log.debug("----������:"+currentDate);
		log.debug("---��������:"+delvieryDate);		
		
		
		if(delvieryDate.after(currentDate)){//��δ�������գ��ҿ�		
			suspendInStock(stockInfo, stockParam);
		}else{  //���
			caculateEnterStock(stockInfo, stockParam);
			try {
				log.debug("------��������ϢΪ:stockInfo"+stockInfo);	
				dao.update(stockInfo);
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			}					
		}
		log.debug("------������洦��::���-----------");		
		
	}
	/**
	 * ����(���ɾ��)
	 * */	
	public void deleteEnterStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException{
		log.debug("------��ʼ��洦��::ȡ�����-----------");
		log.debug("------�������Ϊ:"+stockParam);		
		SEC_SecuritiesStockDAO dao = new SEC_SecuritiesStockDAO();		
		SecuritiesStockInfo stockInfo = getAndCheckStock(stockParam,SECConstant.StockDirection.OUT);
		log.debug("-----���ڱ������Ŀ����ϢΪ:"+stockInfo);		
	    Timestamp currentDate = Env.getSecuritiesSystemDate(stockParam.getOfficeID(), stockParam.getCurrencyID());
		Timestamp delvieryDate = stockParam.getDeliveryDate();	
		log.debug("----������:"+currentDate);
		log.debug("---��������:"+delvieryDate);				
		if(delvieryDate.after(currentDate)){//��δ�������գ�ȡ���ҿ�
			cancelSuspendInStock(stockInfo, stockParam);
		}else{  //ȡ�����
			caculateDeleteEnterStock(stockInfo, stockParam);
			try {
				log.debug("------��������ϢΪ:stockInfo"+stockInfo);				
				dao.update(stockInfo);
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			}					
		}		
		log.debug("------������洦��::ȡ�����-----------");		
	}	
	

	/**
	 * ��⽻��
	 * */	
	public void deliverStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException{
		log.debug("------��ʼ��洦��::��⽻��-----------");
		log.debug("------�������Ϊ:"+stockParam);		
		SEC_SecuritiesStockDAO dao = new SEC_SecuritiesStockDAO();		
		SecuritiesStockInfo stockInfo = getAndCheckStock(stockParam,SECConstant.StockDirection.IN);
		log.debug("-----���ڱ������Ŀ����ϢΪ:"+stockInfo);		
	    Timestamp currentDate = Env.getSecuritiesSystemDate(stockParam.getOfficeID(), stockParam.getCurrencyID());
		Timestamp delvieryDate = stockParam.getDeliveryDate();				
		if(delvieryDate.after(currentDate)){//��δ��������
			//Nothing to do
		}else{  //���¿��
			//ȡ���ҿ�
			cancelSuspendInStock(stockInfo, stockParam);
			//���
			caculateEnterStock(stockInfo, stockParam);
			try {
				log.debug("------��������ϢΪ:stockInfo"+stockInfo);				
				dao.update(stockInfo);
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			}					
		}				
		log.debug("------������洦��::��⽻��-----------");		
	}
	/**
	 * ���ȡ������
	 * */		
	public void cancelDeliverStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException{
		log.debug("------��ʼ��洦��::���ȡ������-----------");
		log.debug("------�������Ϊ:"+stockParam);				
		SEC_SecuritiesStockDAO dao = new SEC_SecuritiesStockDAO();		
		SecuritiesStockInfo stockInfo = getAndCheckStock(stockParam,SECConstant.StockDirection.OUT);
	    Timestamp currentDate = Env.getSecuritiesSystemDate(stockParam.getOfficeID(), stockParam.getCurrencyID());
		Timestamp delvieryDate = stockParam.getDeliveryDate();				
		if(delvieryDate.after(currentDate)){//��δ��������
			//Nothing to do
		}else{  //���¿��
			//ȡ���ҿ�
			suspendInStock(stockInfo, stockParam);
			//���
			caculateDeleteEnterStock(stockInfo, stockParam);
			try {
				log.debug("------��������ϢΪ:stockInfo"+stockInfo);				
				dao.update(stockInfo);
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			}		
		}						
	}
	/**
	 * ����
	 * */	
	public SecuritiesStockReturn exitStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException{
		log.debug("------��ʼ��洦��::����-----------");
		log.debug("------�������Ϊ:"+stockParam);						
		SecuritiesStockReturn stockOutReturn = null;
		SEC_SecuritiesStockDAO dao = new SEC_SecuritiesStockDAO();		
		SecuritiesStockInfo stockInfo = getAndCheckStock(stockParam,SECConstant.StockDirection.OUT);
	    Timestamp currentDate = Env.getSecuritiesSystemDate(stockParam.getOfficeID(), stockParam.getCurrencyID());
		Timestamp delvieryDate = stockParam.getDeliveryDate();				
		if(!delvieryDate.after(currentDate)){
			stockOutReturn = caculateExitStock(stockInfo, stockParam);
			log.debug("------���ⷵ��ֵ"+stockOutReturn);	
			try {
				log.debug("------��������ϢΪ:stockInfo"+stockInfo);					
				dao.update(stockInfo);
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			}					
		}else{  
			suspendOutStock(stockInfo, stockParam);
		}							
		log.debug("------������洦��::����-----------");
		return stockOutReturn;
	}
	
	/**
	 * �˿�(����ɾ��)
	 * */		
	public void cancelExitStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException{
		log.debug("------��ʼ��洦��::�˿�(����ɾ��)-----------");
		log.debug("------�������Ϊ:"+stockParam);								
		SEC_SecuritiesStockDAO dao = new SEC_SecuritiesStockDAO();		
		SecuritiesStockInfo stockInfo = getAndCheckStock(stockParam,SECConstant.StockDirection.IN);
	    Timestamp currentDate = Env.getSecuritiesSystemDate(stockParam.getOfficeID(), stockParam.getCurrencyID());
		Timestamp delvieryDate = stockParam.getDeliveryDate();				
		if(!delvieryDate.after(currentDate)){
			caculateCancelExitStock(stockInfo, stockParam);
			try {
				log.debug("------��������ϢΪ:stockInfo"+stockInfo);				
				dao.update(stockInfo);
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			}					
		}else{  
			cancelSuspendOutStock(stockInfo, stockParam);						
		}								
		log.debug("------������洦��::�˿�(����ɾ��)-----------");		
	}
	/**
	 * ���⽻��
	 * */		
	public SecuritiesStockReturn deliverExitStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException{
		log.debug("------��ʼ��洦��::���⽻��-----------");
		log.debug("------�������Ϊ:"+stockParam);		
		SEC_SecuritiesStockDAO dao = new SEC_SecuritiesStockDAO();		
		SecuritiesStockReturn stockOutReturn = null;		
		SecuritiesStockInfo stockInfo = getAndCheckStock(stockParam,SECConstant.StockDirection.OUT);
	    Timestamp currentDate = Env.getSecuritiesSystemDate(stockParam.getOfficeID(), stockParam.getCurrencyID());
		Timestamp delvieryDate = stockParam.getDeliveryDate();				
		if(!delvieryDate.after(currentDate)){
			cancelSuspendOutStock(stockInfo, stockParam);
			//����
			stockOutReturn = caculateExitStock(stockInfo, stockParam);		
			try {
				log.debug("------��������ϢΪ:stockInfo"+stockInfo);				
				dao.update(stockInfo);
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			}					
		}else{  
			//Nothing to do
		}		
		log.debug("------������洦��::���⽻��-----------");
		return stockOutReturn;		
	}
	/**
	 * ����ȡ������
	 * */			
	public void cancelDeliverExitStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException{
		log.debug("------��ʼ��洦��::����ȡ������-----------");	
		log.debug("------�������Ϊ:"+stockParam);		
		SEC_SecuritiesStockDAO dao = new SEC_SecuritiesStockDAO();		
		SecuritiesStockInfo stockInfo = getAndCheckStock(stockParam,SECConstant.StockDirection.IN);
	    Timestamp currentDate = Env.getSecuritiesSystemDate(stockParam.getOfficeID(), stockParam.getCurrencyID());
		Timestamp delvieryDate = stockParam.getDeliveryDate();				
		if(delvieryDate.after(currentDate)){//��δ��������
			//Nothing to do
		}else{  //���¿��
			suspendOutStock(stockInfo, stockParam);
			//����
			caculateCancelExitStock(stockInfo, stockParam);					
			try {
				log.debug("------��������ϢΪ:stockInfo"+stockInfo);				
				dao.update(stockInfo);
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			}					
		}								
		log.debug("------������洦��::����ȡ������-----------");		
	}
	/**
	 * ��涳�᣺���ı��棬ֻ�������˿�涳������
	 * @param stockParam
	 * @return
	 * */
	public void freezeStock(SecuritiesStockParam stockParam) throws RemoteException,DuplicatedStockException,OutOfStockException,SecuritiesException{
		log.debug("------��ʼ��洦��::��涳��-----------");
		log.debug("����Ŀ���������:"+stockParam);
		SEC_SecuritiesStockDAO dao = new SEC_SecuritiesStockDAO();
		SecuritiesStockInfo foundInfo = findSecuritiesStock(stockParam.getAccountID(),stockParam.getClient(),stockParam.getSecuritiesID());
		log.debug("��Ӧ�Ŀ����ϢΪ: "+foundInfo);
		double transQuantity = stockParam.getQuantity();
		if(stockParam.getIsNeedCheckOverDraft() == SECConstant.TRUE)
			checkIsOutOfStock(foundInfo, transQuantity);	
		//���¿�����涳����+=��Ѻȯ����
		SecuritiesStockInfo updatedStockInfo = new SecuritiesStockInfo();
		updatedStockInfo.setId(foundInfo.getId());
		//updatedStockInfo.setSuspenseOutQuantity(transQuantity+foundInfo.getSuspenseOutQuantity());
		updatedStockInfo.setFrozenQuantity(transQuantity+foundInfo.getFrozenQuantity());
		log.debug("�����µĿ����ϢΪ: "+foundInfo);		
		try {
			dao.update(updatedStockInfo);
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
			
		}
		log.debug("------������洦��::��涳��-----------");		

	}
	
	/**
	 * ���ı��棬ֻ�Ǽ����˿�涳������
	 * @param stockParam
	 * @return
	 * */	
	public void cancelFreezeStock(SecuritiesStockParam stockParam) throws RemoteException,DuplicatedStockException,SecuritiesException{
		log.debug("------��ʼ��洦��::ȡ����涳��-----------");
		log.debug("------�������Ϊ:"+stockParam);		
		SecuritiesStockInfo foundInfo = findSecuritiesStock(stockParam.getAccountID(),stockParam.getClient(),stockParam.getSecuritiesID());
		log.debug("��Ӧ�Ŀ����ϢΪ: "+foundInfo);		
		SecuritiesStockInfo updatedStockInfo = new SecuritiesStockInfo();
		updatedStockInfo.setId(foundInfo.getId());
		double transQuantity = stockParam.getQuantity();		
		updatedStockInfo.setFrozenQuantity(foundInfo.getFrozenQuantity()-transQuantity);
		SEC_SecuritiesStockDAO dao = new SEC_SecuritiesStockDAO();
		log.debug("�����µĿ����ϢΪ: "+foundInfo);		
		try {
			dao.update(updatedStockInfo);
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}		
		log.debug("------������洦��::ȡ����涳��-----------");		
		
	}
	
	
		/**
		 * ����������ѯ����¼�������Ƿ�͸��
		* @param accountParam 
		* @param
		* @return
		* @throws
		 */
	private SecuritiesStockInfo getAndCheckStock(SecuritiesStockParam stockParam,long direction) throws NoExistStockException, OutOfStockException, DuplicatedStockException, SecuritiesDAOException, SECInvalidRecordException {
		SEC_SecuritiesStockDAO dao = new SEC_SecuritiesStockDAO();
		//��ѯ�Ƿ�ÿ���Ѿ�����
		SecuritiesStockInfo stockInfo = findSecuritiesStock(stockParam.getAccountID(),stockParam.getClient(),stockParam.getSecuritiesID());
		//������Ƿ�͸��
		if(direction == SECConstant.StockDirection.OUT){
			if(stockParam.getIsNeedCheckOverDraft() == SECConstant.TRUE)
				checkIsOutOfStock(stockInfo, stockParam.getQuantity());
		}
		return stockInfo;
	}

	/**
	 * ����������ѯ��Ӧ��֤ȯ
	 * @param stockParam
	 * @exception DuplicatedStockException
	 * */
	private SecuritiesStockInfo findSecuritiesStock(
													long accountID,
													long clientID,
													long securitiesID) throws DuplicatedStockException, SecuritiesDAOException, SECInvalidRecordException{
		SEC_SecuritiesStockDAO dao = new SEC_SecuritiesStockDAO();
		SecuritiesStockInfo findCondition = new SecuritiesStockInfo();
		findCondition.setAccountID(accountID);
		findCondition.setClientID(clientID);
		findCondition.setSecuritiesID(securitiesID);
		ArrayList list = null;
		try {
			list = (ArrayList) dao.findByCondition(findCondition);
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		//���ڲ�ѯ����Ϊ��������˽����ֻ����<=1	
		if(list.size() == 1){
			SecuritiesStockInfo info = (SecuritiesStockInfo) list.get(0);
			if(info.getId() < 0)
				throw new SECInvalidRecordException("���");
			return info;
		}else if(list.size() <= 0){
			throw new SECInvalidRecordException("���");
		}
		else{		//���ڲ�ѯ����Ϊ��������˽����ֻ����<=1
			throw new DuplicatedStockException();
		}		
	}	
	
	/**
	 * ������Ƿ���
	 * ���ڽ��иò���ǰ�ж��Ƿ���Ҫ���п����(������־stockParam.getIsNeedCheckOverDraft())
		* @param accountParam 
		* @param
		* @return
		* @throws
		 */
	private void checkIsOutOfStock(SecuritiesStockInfo foundInfo, double transQuantity) throws OutOfStockException {
		//���ÿ�� = ��ǰ������� + ���������ҿ����� - ���������ҿ����� - ��涳����			
		//if(transQuantity > (foundInfo.getQuantity()+foundInfo.getSuspenseInQuantity()-foundInfo.getSuspenseOutQuantity()-foundInfo.getFrozenQuantity())){
		if(transQuantity > (foundInfo.getQuantity()-foundInfo.getSuspenseOutQuantity()-foundInfo.getFrozenQuantity())){
			String accountNo = NameRef.getAccountNameById(foundInfo.getAccountID());
			String clientNo = NameRef.getClientNameByID(foundInfo.getClientID());
			String secName = NameRef.getSecuritiesCodeByID(foundInfo.getSecuritiesID());
			throw new OutOfStockException(accountNo,clientNo,secName);
		}
	}
	
	/**
	 * ��⹫ʽ
	 * @param updatingStockInfo ��Ҫ�����µĿ����Ϣ
	 * @param stockParam        ������Ϣ
	 * 
	 * */
	private SecuritiesStockReturn caculateEnterStock(SecuritiesStockInfo updatingStockInfo,SecuritiesStockParam stockParam) throws SecuritiesException{
		log.debug("------��ʼ��⹫ʽ-----------");			
		log.debug("------�������1Ϊ:updatingStockInfo"+updatingStockInfo);		
		log.debug("------�������2Ϊ:stockParam"+stockParam);		
		SEC_SecuritiesStockDAO dao = new SEC_SecuritiesStockDAO();	
		updatingStockInfo.clearUsedFields();			
		updatingStockInfo.setId(updatingStockInfo.getId());
		updatingStockInfo.setQuantity(updatingStockInfo.getQuantity()+stockParam.getQuantity());
		updatingStockInfo.setCost(updatingStockInfo.getCost()+stockParam.getAmount());
		if(stockParam.getNetPriceAmount() > 0)
			updatingStockInfo.setNetCost(updatingStockInfo.getNetCost()+stockParam.getNetPriceAmount());
		else
			updatingStockInfo.setNetCost(updatingStockInfo.getNetCost()+stockParam.getAmount());		
		
		SecuritiesStockReturn res = new SecuritiesStockReturn();
		//try {
			//log.debug("------��������ϢΪ:updatingStockInfo"+updatingStockInfo);			
			//dao.update(updatingStockInfo);
		//} catch (ITreasuryDAOException e) {
		//	throw new SecuritiesDAOException(e);
		//}
		res.setNewQuanitity(updatingStockInfo.getQuantity());
		res.setNewCost(updatingStockInfo.getCost());
		res.setNewNetCost(updatingStockInfo.getNetCost());		
		log.debug("------��ⷵ��ֵΪ:"+res);	
		log.debug("------������⹫ʽ-----------");		
		return res;
	}
	
	/**
	 * ����(���ɾ��)��ʽ
	 * @param updatingStockInfo ��Ҫ�����µĿ����Ϣ
	 * @param stockParam        ������Ϣ
	 * 
	 * */
	private void caculateDeleteEnterStock(SecuritiesStockInfo updatingStockInfo,SecuritiesStockParam stockParam) throws SecuritiesException{
		log.debug("------��ʼ����(���ɾ��)��ʽ-----------");		
		log.debug("------�������1Ϊ:updatingStockInfo"+updatingStockInfo);		
		log.debug("------�������2Ϊ:stockParam"+stockParam);				
		SEC_SecuritiesStockDAO dao = new SEC_SecuritiesStockDAO();	
		updatingStockInfo.clearUsedFields();		
		updatingStockInfo.setId(updatingStockInfo.getId());		
		
		double newQuantity = updatingStockInfo.getQuantity()-stockParam.getQuantity();
		if(newQuantity < 0)
			newQuantity = 0;
		updatingStockInfo.setQuantity(newQuantity);
		
		double newCost = updatingStockInfo.getCost()-stockParam.getAmount();
		if(newCost < 0)
			newCost = 0.0;
		updatingStockInfo.setCost(newCost);
		
		double newNetCost = updatingStockInfo.getNetCost()-stockParam.getNetPriceAmount();
		if(newNetCost < 0)
			newNetCost = 0.0;		
		updatingStockInfo.setNetCost(newNetCost);
		try {
			log.debug("------��������ϢΪ:updatingStockInfo"+updatingStockInfo);			
			dao.update(updatingStockInfo);
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}								
		log.debug("------��������(���ɾ��)��ʽ-----------");
	}	
	
	
	/**
	 * ���⹫ʽ
	 * @param updatingStockInfo ��Ҫ�����µĿ����Ϣ
	 * @param stockParam        ������Ϣ
	 * @return SecuritiesStockOutReturn ���ⷵ�صĳ��˿���¼�������Ϣ��Ϣ
	 * */	
	private SecuritiesStockReturn caculateExitStock(SecuritiesStockInfo updatingStockInfo,SecuritiesStockParam stockParam) throws SecuritiesException{
		SecuritiesStockReturn stockOutReturn = new SecuritiesStockReturn();
		log.debug("------��ʼ���⹫ʽ-----------");		
		log.debug("------�������1Ϊ:updatingStockInfo"+updatingStockInfo);		
		log.debug("------�������2Ϊ:stockParam"+stockParam);						
		SEC_SecuritiesStockDAO dao = new SEC_SecuritiesStockDAO();	
		updatingStockInfo.clearUsedFields();		
		updatingStockInfo.setId(updatingStockInfo.getId());		
		
		//�¿������ = ԭ�������-��������
		double oldQuantity = updatingStockInfo.getQuantity();
		updatingStockInfo.setQuantity(updatingStockInfo.getQuantity()-stockParam.getQuantity());
		//rate = (1-��������/ ԭ�������)
		log.debug("--------��������:"+stockParam.getQuantity());
		log.debug("--------ԭ�������:"+oldQuantity);
		double rate = 1.0;
		if(oldQuantity != 0.0)
			rate = 1.0-(stockParam.getQuantity()/oldQuantity);
		log.debug("--------rate = (1-��������/ ԭ�������):"+rate);
		//���ܳɱ� = ԭ�ܳɱ� * rate
		double oldCost = updatingStockInfo.getCost();
		if(stockParam.getQuantity() == 0)
			updatingStockInfo.setCost(updatingStockInfo.getCost()-stockParam.getAmount());
		else if(oldQuantity == 0.0)
			updatingStockInfo.setCost(0.0);
		else
			updatingStockInfo.setCost(updatingStockInfo.getCost()*rate);
		//�¾����ܳɱ� = ԭ�����ܳɱ� * rate
		double oldNetCost = updatingStockInfo.getNetCost();
//		updatingStockInfo.setNetCost(updatingStockInfo.getNetCost()*rate);
		if(stockParam.getQuantity() == 0)
			updatingStockInfo.setNetCost(updatingStockInfo.getNetCost()-stockParam.getNetPriceAmount());
		else if(oldQuantity == 0.0)
			updatingStockInfo.setNetCost(0.0);
		else
			updatingStockInfo.setNetCost(updatingStockInfo.getNetCost()*rate);		
		
		stockOutReturn.setNewQuanitity(updatingStockInfo.getQuantity());
		//�����ܳɱ� = ԭ�ܳɱ� �C ���ܳɱ�
		double outCost = oldCost-updatingStockInfo.getCost();
		stockOutReturn.setNewCost(updatingStockInfo.getCost());	
		//���⾻���ܳɱ� = ԭ�����ܳɱ� �C �¾����ܳɱ�
		double outNetCost = oldNetCost-updatingStockInfo.getNetCost();
		stockOutReturn.setNewNetCost(updatingStockInfo.getNetCost());
		//����ʵ��ӯ�� = ������ - �����ܳɱ�
		stockOutReturn.setNewProfitLoss(stockParam.getAmount()-outCost);
		//���⾻��ʵ��ӯ�� = ���⾻�۽�� - ���⾻���ܳɱ�
		stockOutReturn.setNewNetProfitLoss(stockParam.getNetPriceAmount()-outNetCost);
		//���ۼ�ʵ��ӯ�� = ԭ�ۼ�ʵ��ӯ�� + ����ʵ��ӯ��
		
		//���ۼƾ���ʵ��ӯ�� = ԭ�ۼ�ʵ��ӯ�� + ���⾻��ʵ��ӯ��
		
		//�п��仯�Ÿ�����������
		if(stockParam.getQuantity() > 0.0){
			stockOutReturn.setUnitCost(outCost/stockParam.getQuantity());
			//��λ�����ܳɱ� = ���⾻���ܳɱ�/��������
			stockOutReturn.setUnitNetCost(outNetCost/stockParam.getQuantity());
			//��λʵ��ӯ�� = ����ʵ��ӯ��/��������
			stockOutReturn.setUnitProfitLoss(stockOutReturn.getNewProfitLoss()/stockParam.getQuantity());
			//��λ����ʵ��ӯ�� = ���⾻��ʵ��ӯ��/��������
			stockOutReturn.setUnitNetProfitLoss(stockOutReturn.getNewNetProfitLoss()/stockParam.getQuantity());
		}else{
			stockOutReturn.setUnitCost(0.0);
			stockOutReturn.setUnitNetCost(0.0);
			//��λʵ��ӯ�� = ����ʵ��ӯ��/��������
			stockOutReturn.setUnitProfitLoss(0.0);
			//��λ����ʵ��ӯ�� = ���⾻��ʵ��ӯ��/��������
			stockOutReturn.setUnitNetProfitLoss(0.0);			
		}
//		try {
//			log.debug("------��������ϢΪ:updatingStockInfo"+updatingStockInfo);					
//			dao.update(updatingStockInfo);
//		} catch (ITreasuryDAOException e) {
//			throw new SecuritiesDAOException(e);
//		}			
		log.debug("------���⹫ʽ������ϢΪ:stockOutReturn"+stockOutReturn);		
		log.debug("------�������⹫ʽ-----------");		
		return stockOutReturn;		
	}
	/**
	 * �˿�(����ɾ��)��ʽ
	 * @param updatingStockInfo ��Ҫ�����µĿ����Ϣ
	 * @param stockParam        ������Ϣ
	 * */		
	private void caculateCancelExitStock(SecuritiesStockInfo updatingStockInfo,SecuritiesStockParam stockParam) throws SecuritiesException{
		log.debug("------��ʼ�˿�(����ɾ��)��ʽ-----------");		
		log.debug("------�������1Ϊ:updatingStockInfo"+updatingStockInfo);		
		log.debug("------�������2Ϊ:stockParam"+stockParam);								
		
		SEC_SecuritiesStockDAO dao = new SEC_SecuritiesStockDAO();	
		updatingStockInfo.clearUsedFields();		
		updatingStockInfo.setId(updatingStockInfo.getId());		
		
		if(stockParam.getQuantity() == 0){
			updatingStockInfo.setCost(updatingStockInfo.getCost()+stockParam.getAmount());
			updatingStockInfo.setNetCost(updatingStockInfo.getNetCost()+stockParam.getNetPriceAmount());
		}else{
			updatingStockInfo.setQuantity(updatingStockInfo.getQuantity()+stockParam.getQuantity());
			updatingStockInfo.setCost(updatingStockInfo.getCost()+stockParam.getQuantity()*stockParam.getUnitCost());
			updatingStockInfo.setNetCost(updatingStockInfo.getNetCost()+stockParam.getQuantity()*stockParam.getUnitNetCost());
			updatingStockInfo.setProfitLoss(updatingStockInfo.getProfitLoss()+stockParam.getUnitProfitLoss()*stockParam.getQuantity());
			updatingStockInfo.setNetProfitLoss(updatingStockInfo.getNetProfitLoss()+stockParam.getUnitNetProfitLoss()*stockParam.getQuantity());
		}
//		try {
//			log.debug("------��������ϢΪ:updatingStockInfo"+updatingStockInfo);				
//			dao.update(updatingStockInfo);
//		} catch (ITreasuryDAOException e) {
//			throw new SecuritiesDAOException(e);
//		}			
		log.debug("------�����˿�(����ɾ��)��ʽ-----------");				
	}
	
	/**
	 * ���ҿ�
	 * @param updatingStockInfo ��Ҫ�����µĿ����Ϣ
	 * @param stockParam        ������Ϣ 
	 * */
	private void suspendInStock(SecuritiesStockInfo updatingStockInfo,SecuritiesStockParam stockParam) throws SecuritiesException{
		log.debug("------��ʼ���ҿ�-----------");				
		SEC_SecuritiesStockDAO dao = new SEC_SecuritiesStockDAO();	
		//updatingStockInfo.clearUsedFields();
		updatingStockInfo.setId(updatingStockInfo.getId());
		updatingStockInfo.setSuspenseInQuantity(updatingStockInfo.getSuspenseInQuantity()+stockParam.getQuantity());
		updatingStockInfo.setSuspenseInAmount(updatingStockInfo.getSuspenseInAmount()+stockParam.getAmount());
		updatingStockInfo.setSuspenseInNetAmount(updatingStockInfo.getSuspenseInNetAmount()+stockParam.getNetPriceAmount());
		try {
			log.debug("------��������ϢΪ:updatingStockInfo"+updatingStockInfo);				
			dao.update(updatingStockInfo);
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		log.debug("------�������ҿ�-----------");		
	}
	
	/**
	 * ȡ�����ҿ�
	 * @param updatingStockInfo ��Ҫ�����µĿ����Ϣ
	 * @param stockParam        ������Ϣ 
	 * */
	private void cancelSuspendInStock(SecuritiesStockInfo updatingStockInfo,SecuritiesStockParam stockParam) throws SecuritiesException{
		log.debug("------��ʼȡ�����ҿ�-----------");						
		SEC_SecuritiesStockDAO dao = new SEC_SecuritiesStockDAO();	
		updatingStockInfo.clearUsedFields();
		updatingStockInfo.setId(updatingStockInfo.getId());		
		updatingStockInfo.setSuspenseInQuantity(updatingStockInfo.getSuspenseInQuantity()-stockParam.getQuantity());
		updatingStockInfo.setSuspenseInAmount(updatingStockInfo.getSuspenseInAmount()-stockParam.getAmount());
		updatingStockInfo.setSuspenseInNetAmount(updatingStockInfo.getSuspenseInNetAmount()-stockParam.getNetPriceAmount());
		try {
			log.debug("------��������ϢΪ:updatingStockInfo"+updatingStockInfo);			
			dao.update(updatingStockInfo);
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}	
		log.debug("------����ȡ�����ҿ�-----------");				
	}	
	
	
	/**
	 * ����ҿ�
	 * @param updatingStockInfo ��Ҫ�����µĿ����Ϣ
	 * @param stockParam        ������Ϣ 
	 * */
	private void suspendOutStock(SecuritiesStockInfo updatingStockInfo,SecuritiesStockParam stockParam) throws SecuritiesException{
		log.debug("------��ʼ����ҿ�-----------");							
		SEC_SecuritiesStockDAO dao = new SEC_SecuritiesStockDAO();	
		updatingStockInfo.clearUsedFields();		
		updatingStockInfo.setSuspenseOutQuantity(updatingStockInfo.getSuspenseOutQuantity()+stockParam.getQuantity());
		updatingStockInfo.setSuspenseOutAmount(updatingStockInfo.getSuspenseOutAmount()+stockParam.getAmount());
		updatingStockInfo.setSuspenseOutNetAmount(updatingStockInfo.getSuspenseOutNetAmount()+stockParam.getNetPriceAmount());
		try {
			log.debug("------��������ϢΪ:updatingStockInfo"+updatingStockInfo);					
			dao.update(updatingStockInfo);
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}				
		log.debug("------��������ҿ�-----------");			
	}
	
	/**
	 * ȡ������ҿ�
	 * @param updatingStockInfo ��Ҫ�����µĿ����Ϣ
	 * @param stockParam        ������Ϣ 
	 * */
	private void cancelSuspendOutStock(SecuritiesStockInfo updatingStockInfo,SecuritiesStockParam stockParam) throws SecuritiesException{
		log.debug("------��ʼȡ������ҿ�-----------");					
		SEC_SecuritiesStockDAO dao = new SEC_SecuritiesStockDAO();	
		updatingStockInfo.clearUsedFields();		
		updatingStockInfo.setSuspenseOutQuantity(updatingStockInfo.getSuspenseOutQuantity()-stockParam.getQuantity());
		updatingStockInfo.setSuspenseOutAmount(updatingStockInfo.getSuspenseOutAmount()-stockParam.getAmount());
		updatingStockInfo.setSuspenseOutNetAmount(updatingStockInfo.getSuspenseOutNetAmount()-stockParam.getNetPriceAmount());
		try {
			log.debug("------��������ϢΪ:updatingStockInfo"+updatingStockInfo);					
			dao.update(updatingStockInfo);
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}	
		log.debug("------����ȡ������ҿ�-----------");			
	}	
	
	/**
	 * 	��������ս�
	 * ���һ��ҵ��λ��һ���ʽ��ʻ��µ�һ��֤ȯ���룬�����յ�ֹ�գ����п���ս�
	 * */
	public void caculateSingleAccountDailyStock(SingleAccountDailyStockParam param)throws RemoteException,SecuritiesException{
		log.debug("------��ʼ��������ս�---------");	
		log.debug("------��������ս��������Ϊ:"+param);
//		if(param.getSecuritiesID() == 8 && param.getClientID() == 2 && param.getAccountID() == 45){
//		}
//	else{
//			log.debug("----------���������ü�¼-------------");
//			return;
//		}
		
		SecuritiesStockInfo securitiesInfo = findSecuritiesStock(param.getAccountID(),param.getClientID(),param.getSecuritiesID());
		log.debug("-----������Ŀ����ϢΪ:"+securitiesInfo);
		
		//zpli modify 2006-03-10 ��Ʊ������ȫ�۾��ۣ������������ǹ�Ʊ������ȫ������ֶ���Ϊ0
		boolean bStock=false;
		SEC_SecuritiesDAO dao=new SEC_SecuritiesDAO();
		try{
			SecuritiesInfo secInfo=(SecuritiesInfo)dao.findByID(param.getSecuritiesID(),SecuritiesInfo.class);
			if(secInfo.getTypeID()==SECConstant.SecuritiesType.STOCK_A)
				bStock=true;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		Timestamp sDate = param.getStartDate();
		Timestamp eDate = param.getEndDate();
		
		SEC_DailyStockDAO dailyStockDAO = new SEC_DailyStockDAO();
		Timestamp yesterdayOfStart = DataFormat.getNextDate(sDate, -1);
		DailyStockInfo dailyStockInfo = findDailyStockInfoByConditions(param.getAccountID(),param.getClientID(),param.getSecuritiesID(),yesterdayOfStart);
		log.debug("-----������Ŀ���ս���ϢΪ:"+dailyStockInfo);
		if(dailyStockInfo == null && !sDate.after(eDate)){//˵�������ݳ�ʼ���Ŀ�棺�����ս������sec_DailyStock�в���һ����¼
			DailyStockInfo addedDailyStockInfo = new DailyStockInfo();
			addedDailyStockInfo.setAccountID(param.getAccountID());
			addedDailyStockInfo.setClientID(param.getClientID());
			addedDailyStockInfo.setStockDate(yesterdayOfStart);			
			addedDailyStockInfo.setQuantity(securitiesInfo.getInitQuantity());
			addedDailyStockInfo.setFrozenQuantity(securitiesInfo.getInitFrozenQuantity());
			addedDailyStockInfo.setCost(securitiesInfo.getInitCost());
			addedDailyStockInfo.setNetCost(securitiesInfo.getInitNetCost());
			addedDailyStockInfo.setNetProfitLoss(securitiesInfo.getInitNetProfitLoss());
			addedDailyStockInfo.setProfitLoss(securitiesInfo.getInitProfitLoss());
			addedDailyStockInfo.setSecuritiesID(param.getSecuritiesID());
			try {
				long id = dailyStockDAO.add(addedDailyStockInfo);
				addedDailyStockInfo.setId(id);
				dailyStockInfo = addedDailyStockInfo;
				log.debug("-----�����Ŀ���ս���ϢΪ:"+dailyStockInfo);				
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			}
		}
		
		
		double initStockQuantity = dailyStockInfo.getQuantity();
		double initFrozenStockQuantity = dailyStockInfo.getFrozenQuantity();
		double initCost = dailyStockInfo.getCost();
		double initNetCost = dailyStockInfo.getNetCost();
		double initProfitLoss = dailyStockInfo.getProfitLoss();
		double initNetProfitLoss = dailyStockInfo.getNetProfitLoss();
		
		log.debug("----------��ʼ�ӿ�ʼ�յ������ս����ս�-----------");		
		while(!sDate.after(eDate)){
			System.out.println("----------------���ڴ���������� "+sDate);
			//yesterdayOfStart = DataFormat.getNextDate(sDate, -1);
			SEC_VStockDetailDAO stockDetailDAO = new SEC_VStockDetailDAO();
			DailyStockInfo inDailyStockInfo = stockDetailDAO.sumStockDetails(param.getClientID(), param.getAccountID(), param.getSecuritiesID(), sDate, SECConstant.StockDirection.IN);
			DailyStockInfo outDailyStockInfo = stockDetailDAO.sumStockDetails(param.getClientID(), param.getAccountID(), param.getSecuritiesID(), sDate, SECConstant.StockDirection.OUT);
			log.debug("---------�������:"+inDailyStockInfo);			
			log.debug("---------���ճ���:"+outDailyStockInfo);			
			SEC_VStockDetailDAO vStockDetailDAO = new SEC_VStockDetailDAO();
			Collection vStockDetails = vStockDetailDAO.getDeliveryOrderInfosByCondition(param.getClientID(), param.getAccountID(), param.getSecuritiesID(), sDate);
			
			Iterator it = vStockDetails.iterator();
			//dailyStockInfo = findDailyStockInfoByConditions(param.getAccountID(),param.getClientID(),param.getSecuritiesID(),yesterdayOfStart);			
			
			double currentStockQuantity = initStockQuantity;
			double currentFrozenStockQuantity = initFrozenStockQuantity;
			double currentCost = initCost;	
			double currentNetCost = initNetCost;
			double currentProfitLoss = initProfitLoss;
			double currentNetProfitLoss = initNetProfitLoss;			
			log.debug("----------��ʼ��ʸ��½��-----------");	
			while(it.hasNext()){
				VStockDetailInfo tmpStockDetail = (VStockDetailInfo) it.next();
				log.debug("---------���ڴ����StockDetail��ϢΪ:"+tmpStockDetail);
				//if(tmpStockDetail.getQuantity() == 0.0 || currentStockQuantity == 0.0){
				//	log.debug("---------���ڴ���Ľ��û�п��,����������һ��-------------");
				//	continue;
				//}
				
				SecuritiesStockInfo updatingStockInfo = new SecuritiesStockInfo(); 
				SecuritiesStockParam stockParam	= new SecuritiesStockParam();
				updatingStockInfo.setId(tmpStockDetail.getDeliveryOrderID());
				updatingStockInfo.setQuantity(currentStockQuantity);
				updatingStockInfo.setCost(currentCost);
				updatingStockInfo.setNetCost(currentNetCost);
				
				stockParam.setQuantity(tmpStockDetail.getQuantity());
				stockParam.setNetPriceAmount(tmpStockDetail.getNetPriceAmount());
				stockParam.setAmount(tmpStockDetail.getAmount());
				SecuritiesStockReturn stockReturn = null;
				if(tmpStockDetail.getDirection() == SECConstant.StockDirection.IN){
					stockReturn = caculateEnterStock(updatingStockInfo,stockParam);
					currentStockQuantity = stockReturn.getNewQuanitity();
					currentCost = stockReturn.getNewCost();
					currentNetCost = stockReturn.getNewNetCost();
				}else{
					updatingStockInfo.setProfitLoss(currentProfitLoss);
					updatingStockInfo.setNetProfitLoss(currentNetProfitLoss);
					
					stockReturn = caculateExitStock(updatingStockInfo,stockParam);
//					��ǰ�������=���ص��¿������
					currentStockQuantity = stockReturn.getNewQuanitity();					
//					��ǰ�ܳɱ�=���ص����ܳɱ�
					currentCost = stockReturn.getNewCost();					
//					��ǰ�����ܳɱ�=���ص��¾����ܳɱ�
					currentNetCost = stockReturn.getNewNetCost();					
//					��ǰ��ʵ��ӯ��=���ص����ۼ�ʵ��ӯ��
					currentProfitLoss = stockReturn.getNewProfitLoss();					
//					��ǰ�ƾ���ʵ��ӯ��=���ص����ۼƾ���ʵ��ӯ��
					currentNetProfitLoss = stockReturn.getNewNetProfitLoss();
					//���½����¼
					SEC_DeliveryOrderDAO doDAO = new SEC_DeliveryOrderDAO();
					DeliveryOrderInfo updatedDeliveryOrder = new DeliveryOrderInfo();
					updatedDeliveryOrder.setId(tmpStockDetail.getDeliveryOrderID());
					updatedDeliveryOrder.setUnitCost(stockReturn.getUnitCost());
					updatedDeliveryOrder.setUnitNetCost(stockReturn.getUnitNetCost());
					updatedDeliveryOrder.setUnitProfitLoss(stockReturn.getUnitProfitLoss());
					updatedDeliveryOrder.setUnitNetProfitLoss(stockReturn.getUnitNetProfitLoss());
					log.debug("----------�����µĽ����ϢΪ:"+updatedDeliveryOrder);
					try {
						doDAO.update(updatedDeliveryOrder);
					} catch (ITreasuryDAOException e) {
						throw new SecuritiesDAOException(e);	
					}							
				}
		
			}//end of while(it.hasNext())
			double[] costs = vStockDetailDAO.sumTodayStockCost(param.getClientID(), param.getAccountID(), param.getSecuritiesID(), sDate);
			double dailyOutCost = costs[0];	  //���ճ���ɱ���ȫ�ۣ�
			double dailyOutNetCost = costs[1];//���ճ���ɱ������ۣ�
			log.debug("----------���ճ���ɱ���ȫ�ۣ�:"+dailyOutCost);	
			log.debug("----------���ճ���ɱ������ۣ�:"+dailyOutNetCost);
			
			//��ĩ��
			DailyStockInfo currentDailyStockInfo = new DailyStockInfo();
			//���տ������=��ǰ�������
			currentDailyStockInfo.setQuantity(currentStockQuantity);
			//���ն���������
			double currentForzenStock = vStockDetailDAO.sumTodayFrozenStock(param.getClientID(), param.getAccountID(), param.getSecuritiesID(), sDate);
			System.out.println("------------------���ն�����Ϊ "+currentForzenStock);
			currentDailyStockInfo.setFrozenQuantity(currentFrozenStockQuantity+currentForzenStock);
			
			//zpli modify 2006-03-10 ��Ʊ������ȫ�۾��ۣ������������ǹ�Ʊ������ȫ������ֶ���Ϊ0
			if (bStock==true)
				currentDailyStockInfo.setCost(0);
			else
				currentDailyStockInfo.setCost(currentCost);
			
			currentDailyStockInfo.setNetCost(currentNetCost);
			currentDailyStockInfo.setProfitLoss(currentProfitLoss);
			currentDailyStockInfo.setNetProfitLoss(currentNetProfitLoss);
			currentDailyStockInfo.setOutCost(dailyOutCost);
			currentDailyStockInfo.setOutNetCost(dailyOutNetCost);
			currentDailyStockInfo.setInAmount(inDailyStockInfo.getInAmount());
			currentDailyStockInfo.setInNetAmount(inDailyStockInfo.getInNetAmount());
			currentDailyStockInfo.setInNumber(inDailyStockInfo.getInNumber());
			currentDailyStockInfo.setInQuantity(inDailyStockInfo.getInQuantity());

			currentDailyStockInfo.setOutAmount(outDailyStockInfo.getOutAmount());
			currentDailyStockInfo.setOutNetAmount(outDailyStockInfo.getOutNetAmount());
			currentDailyStockInfo.setOutNumber(outDailyStockInfo.getOutNumber());
			currentDailyStockInfo.setOutQuantity(outDailyStockInfo.getOutQuantity());
			//������ս���
			dailyStockInfo = findDailyStockInfoByConditions(param.getAccountID(),param.getClientID(),param.getSecuritiesID(),sDate);
			try {
				currentDailyStockInfo.setStockDate(sDate);	
				currentDailyStockInfo.setClientID(param.getClientID());
				currentDailyStockInfo.setSecuritiesID(param.getSecuritiesID());
				currentDailyStockInfo.setAccountID(param.getAccountID());				
				if(dailyStockInfo == null){
					dailyStockDAO.add(currentDailyStockInfo);	
				}else{
					currentDailyStockInfo.setId(dailyStockInfo.getId());
					dailyStockDAO.update(currentDailyStockInfo);
				}
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);	
			}
			log.debug("----------������ս���:"+dailyStockInfo);
			
			initStockQuantity = currentDailyStockInfo.getQuantity();
			initFrozenStockQuantity = currentDailyStockInfo.getFrozenQuantity();
			initCost = currentDailyStockInfo.getCost();
			initNetCost = currentDailyStockInfo.getNetCost();
			initProfitLoss = currentDailyStockInfo.getProfitLoss();
			initNetProfitLoss =currentDailyStockInfo.getNetProfitLoss();		
			
			//��������һ��
			sDate = DataFormat.getNextDate(sDate, 1); 
			log.debug("���º�Ŀ�ʼ��Ϊ: "+sDate);	
		}
	}

	private DailyStockInfo findDailyStockInfoByConditions(long accountID,long clientID,long securitiesID, Timestamp currentDate) throws SecuritiesException{
		SEC_DailyStockDAO dao = new SEC_DailyStockDAO();
		DailyStockInfo condition = new DailyStockInfo();
		condition.setAccountID(accountID);
		condition.setClientID(clientID);
		condition.setSecuritiesID(securitiesID);
		condition.setStockDate(currentDate);
		ArrayList daliyStocks = null;
		try {
			daliyStocks = (ArrayList) dao.findByCondition(condition);
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}

		if(daliyStocks.size() == 0)
			return null;
		else if(daliyStocks.size() == 1)
			return (DailyStockInfo)daliyStocks.get(0);
		else{
			throw new DuplicatePrimaryKeyException("SEC_DailyStock");
		}
	}
	public void cancelEnterStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException{
		log.debug("------��ʼ��洦��::ȡ�����-----------");
		log.debug("------�������Ϊ:"+stockParam);		
		SEC_SecuritiesStockDAO dao = new SEC_SecuritiesStockDAO();		
		SecuritiesStockInfo stockInfo = getAndCheckStock(stockParam,SECConstant.StockDirection.OUT);
		log.debug("-----���ڱ������Ŀ����ϢΪ:"+stockInfo);		
	    Timestamp currentDate = Env.getSecuritiesSystemDate(stockParam.getOfficeID(), stockParam.getCurrencyID());
		Timestamp delvieryDate = stockParam.getDeliveryDate();	
		log.debug("----������:"+currentDate);
		log.debug("---��������:"+delvieryDate);				
		if(delvieryDate.after(currentDate)){//��δ�������գ�ȡ���ҿ�
			cancelSuspendInStock(stockInfo, stockParam);
		}else{  //ȡ�����
			caculateDeleteEnterStock(stockInfo, stockParam);
			try {
				log.debug("------��������ϢΪ:stockInfo"+stockInfo);				
				dao.update(stockInfo);
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			}					
		}		
		log.debug("------������洦��::ȡ�����-----------");		
	}	
}
