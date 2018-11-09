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
	 * 入库
	 * */
	public void enterStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException{
		log.debug("------开始库存处理::入库-----------");		
		log.debug("------输入参数为:"+stockParam);		
		SEC_SecuritiesStockDAO dao = new SEC_SecuritiesStockDAO();
		SecuritiesStockInfo stockInfo = null;
		boolean isNew = false;
		try{
			stockInfo = getAndCheckStock(stockParam,SECConstant.StockDirection.IN);
		}catch(SECInvalidRecordException e)
		{//库存不存在，新增一条库存记录
			log.debug("------库存不存在，新增一条库存记录-----------");			
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

		log.debug("-----正在被操作的库存信息为:"+stockInfo);
		
	    Timestamp currentDate = Env.getSecuritiesSystemDate(stockParam.getOfficeID(), stockParam.getCurrencyID());
		Timestamp delvieryDate = stockParam.getDeliveryDate();
		log.debug("----今天是:"+currentDate);
		log.debug("---交割日是:"+delvieryDate);		
		
		
		if(delvieryDate.after(currentDate)){//还未到交割日，挂库		
			suspendInStock(stockInfo, stockParam);
		}else{  //入库
			caculateEnterStock(stockInfo, stockParam);
			try {
				log.debug("------库存更新信息为:stockInfo"+stockInfo);	
				dao.update(stockInfo);
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			}					
		}
		log.debug("------结束库存处理::入库-----------");		
		
	}
	/**
	 * 销库(入库删除)
	 * */	
	public void deleteEnterStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException{
		log.debug("------开始库存处理::取消入库-----------");
		log.debug("------输入参数为:"+stockParam);		
		SEC_SecuritiesStockDAO dao = new SEC_SecuritiesStockDAO();		
		SecuritiesStockInfo stockInfo = getAndCheckStock(stockParam,SECConstant.StockDirection.OUT);
		log.debug("-----正在被操作的库存信息为:"+stockInfo);		
	    Timestamp currentDate = Env.getSecuritiesSystemDate(stockParam.getOfficeID(), stockParam.getCurrencyID());
		Timestamp delvieryDate = stockParam.getDeliveryDate();	
		log.debug("----今天是:"+currentDate);
		log.debug("---交割日是:"+delvieryDate);				
		if(delvieryDate.after(currentDate)){//还未到交割日，取消挂库
			cancelSuspendInStock(stockInfo, stockParam);
		}else{  //取消入库
			caculateDeleteEnterStock(stockInfo, stockParam);
			try {
				log.debug("------库存更新信息为:stockInfo"+stockInfo);				
				dao.update(stockInfo);
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			}					
		}		
		log.debug("------结束库存处理::取消入库-----------");		
	}	
	

	/**
	 * 入库交割
	 * */	
	public void deliverStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException{
		log.debug("------开始库存处理::入库交割-----------");
		log.debug("------输入参数为:"+stockParam);		
		SEC_SecuritiesStockDAO dao = new SEC_SecuritiesStockDAO();		
		SecuritiesStockInfo stockInfo = getAndCheckStock(stockParam,SECConstant.StockDirection.IN);
		log.debug("-----正在被操作的库存信息为:"+stockInfo);		
	    Timestamp currentDate = Env.getSecuritiesSystemDate(stockParam.getOfficeID(), stockParam.getCurrencyID());
		Timestamp delvieryDate = stockParam.getDeliveryDate();				
		if(delvieryDate.after(currentDate)){//还未到交割日
			//Nothing to do
		}else{  //更新库存
			//取消挂库
			cancelSuspendInStock(stockInfo, stockParam);
			//入库
			caculateEnterStock(stockInfo, stockParam);
			try {
				log.debug("------库存更新信息为:stockInfo"+stockInfo);				
				dao.update(stockInfo);
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			}					
		}				
		log.debug("------结束库存处理::入库交割-----------");		
	}
	/**
	 * 入库取消交割
	 * */		
	public void cancelDeliverStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException{
		log.debug("------开始库存处理::入库取消交割-----------");
		log.debug("------输入参数为:"+stockParam);				
		SEC_SecuritiesStockDAO dao = new SEC_SecuritiesStockDAO();		
		SecuritiesStockInfo stockInfo = getAndCheckStock(stockParam,SECConstant.StockDirection.OUT);
	    Timestamp currentDate = Env.getSecuritiesSystemDate(stockParam.getOfficeID(), stockParam.getCurrencyID());
		Timestamp delvieryDate = stockParam.getDeliveryDate();				
		if(delvieryDate.after(currentDate)){//还未到交割日
			//Nothing to do
		}else{  //更新库存
			//取消挂库
			suspendInStock(stockInfo, stockParam);
			//入库
			caculateDeleteEnterStock(stockInfo, stockParam);
			try {
				log.debug("------库存更新信息为:stockInfo"+stockInfo);				
				dao.update(stockInfo);
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			}		
		}						
	}
	/**
	 * 出库
	 * */	
	public SecuritiesStockReturn exitStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException{
		log.debug("------开始库存处理::出库-----------");
		log.debug("------输入参数为:"+stockParam);						
		SecuritiesStockReturn stockOutReturn = null;
		SEC_SecuritiesStockDAO dao = new SEC_SecuritiesStockDAO();		
		SecuritiesStockInfo stockInfo = getAndCheckStock(stockParam,SECConstant.StockDirection.OUT);
	    Timestamp currentDate = Env.getSecuritiesSystemDate(stockParam.getOfficeID(), stockParam.getCurrencyID());
		Timestamp delvieryDate = stockParam.getDeliveryDate();				
		if(!delvieryDate.after(currentDate)){
			stockOutReturn = caculateExitStock(stockInfo, stockParam);
			log.debug("------出库返回值"+stockOutReturn);	
			try {
				log.debug("------库存更新信息为:stockInfo"+stockInfo);					
				dao.update(stockInfo);
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			}					
		}else{  
			suspendOutStock(stockInfo, stockParam);
		}							
		log.debug("------结束库存处理::出库-----------");
		return stockOutReturn;
	}
	
	/**
	 * 退库(出库删除)
	 * */		
	public void cancelExitStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException{
		log.debug("------开始库存处理::退库(出库删除)-----------");
		log.debug("------输入参数为:"+stockParam);								
		SEC_SecuritiesStockDAO dao = new SEC_SecuritiesStockDAO();		
		SecuritiesStockInfo stockInfo = getAndCheckStock(stockParam,SECConstant.StockDirection.IN);
	    Timestamp currentDate = Env.getSecuritiesSystemDate(stockParam.getOfficeID(), stockParam.getCurrencyID());
		Timestamp delvieryDate = stockParam.getDeliveryDate();				
		if(!delvieryDate.after(currentDate)){
			caculateCancelExitStock(stockInfo, stockParam);
			try {
				log.debug("------库存更新信息为:stockInfo"+stockInfo);				
				dao.update(stockInfo);
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			}					
		}else{  
			cancelSuspendOutStock(stockInfo, stockParam);						
		}								
		log.debug("------结束库存处理::退库(出库删除)-----------");		
	}
	/**
	 * 出库交割
	 * */		
	public SecuritiesStockReturn deliverExitStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException{
		log.debug("------开始库存处理::出库交割-----------");
		log.debug("------输入参数为:"+stockParam);		
		SEC_SecuritiesStockDAO dao = new SEC_SecuritiesStockDAO();		
		SecuritiesStockReturn stockOutReturn = null;		
		SecuritiesStockInfo stockInfo = getAndCheckStock(stockParam,SECConstant.StockDirection.OUT);
	    Timestamp currentDate = Env.getSecuritiesSystemDate(stockParam.getOfficeID(), stockParam.getCurrencyID());
		Timestamp delvieryDate = stockParam.getDeliveryDate();				
		if(!delvieryDate.after(currentDate)){
			cancelSuspendOutStock(stockInfo, stockParam);
			//出库
			stockOutReturn = caculateExitStock(stockInfo, stockParam);		
			try {
				log.debug("------库存更新信息为:stockInfo"+stockInfo);				
				dao.update(stockInfo);
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			}					
		}else{  
			//Nothing to do
		}		
		log.debug("------结束库存处理::出库交割-----------");
		return stockOutReturn;		
	}
	/**
	 * 出库取消交割
	 * */			
	public void cancelDeliverExitStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException{
		log.debug("------开始库存处理::出库取消交割-----------");	
		log.debug("------输入参数为:"+stockParam);		
		SEC_SecuritiesStockDAO dao = new SEC_SecuritiesStockDAO();		
		SecuritiesStockInfo stockInfo = getAndCheckStock(stockParam,SECConstant.StockDirection.IN);
	    Timestamp currentDate = Env.getSecuritiesSystemDate(stockParam.getOfficeID(), stockParam.getCurrencyID());
		Timestamp delvieryDate = stockParam.getDeliveryDate();				
		if(delvieryDate.after(currentDate)){//还未到交割日
			//Nothing to do
		}else{  //更新库存
			suspendOutStock(stockInfo, stockParam);
			//出库
			caculateCancelExitStock(stockInfo, stockParam);					
			try {
				log.debug("------库存更新信息为:stockInfo"+stockInfo);				
				dao.update(stockInfo);
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			}					
		}								
		log.debug("------结束库存处理::出库取消交割-----------");		
	}
	/**
	 * 库存冻结：不改变库存，只是增加了库存冻结数量
	 * @param stockParam
	 * @return
	 * */
	public void freezeStock(SecuritiesStockParam stockParam) throws RemoteException,DuplicatedStockException,OutOfStockException,SecuritiesException{
		log.debug("------开始库存处理::库存冻结-----------");
		log.debug("输入的库存操作参数:"+stockParam);
		SEC_SecuritiesStockDAO dao = new SEC_SecuritiesStockDAO();
		SecuritiesStockInfo foundInfo = findSecuritiesStock(stockParam.getAccountID(),stockParam.getClient(),stockParam.getSecuritiesID());
		log.debug("对应的库存信息为: "+foundInfo);
		double transQuantity = stockParam.getQuantity();
		if(stockParam.getIsNeedCheckOverDraft() == SECConstant.TRUE)
			checkIsOutOfStock(foundInfo, transQuantity);	
		//更新库存表：库存冻结量+=抵押券数量
		SecuritiesStockInfo updatedStockInfo = new SecuritiesStockInfo();
		updatedStockInfo.setId(foundInfo.getId());
		//updatedStockInfo.setSuspenseOutQuantity(transQuantity+foundInfo.getSuspenseOutQuantity());
		updatedStockInfo.setFrozenQuantity(transQuantity+foundInfo.getFrozenQuantity());
		log.debug("被更新的库存信息为: "+foundInfo);		
		try {
			dao.update(updatedStockInfo);
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
			
		}
		log.debug("------结束库存处理::库存冻结-----------");		

	}
	
	/**
	 * 不改变库存，只是减少了库存冻结数量
	 * @param stockParam
	 * @return
	 * */	
	public void cancelFreezeStock(SecuritiesStockParam stockParam) throws RemoteException,DuplicatedStockException,SecuritiesException{
		log.debug("------开始库存处理::取消库存冻结-----------");
		log.debug("------输入参数为:"+stockParam);		
		SecuritiesStockInfo foundInfo = findSecuritiesStock(stockParam.getAccountID(),stockParam.getClient(),stockParam.getSecuritiesID());
		log.debug("对应的库存信息为: "+foundInfo);		
		SecuritiesStockInfo updatedStockInfo = new SecuritiesStockInfo();
		updatedStockInfo.setId(foundInfo.getId());
		double transQuantity = stockParam.getQuantity();		
		updatedStockInfo.setFrozenQuantity(foundInfo.getFrozenQuantity()-transQuantity);
		SEC_SecuritiesStockDAO dao = new SEC_SecuritiesStockDAO();
		log.debug("被更新的库存信息为: "+foundInfo);		
		try {
			dao.update(updatedStockInfo);
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}		
		log.debug("------结束库存处理::取消库存冻结-----------");		
		
	}
	
	
		/**
		 * 根据条件查询库存记录并检验是否透库
		* @param accountParam 
		* @param
		* @return
		* @throws
		 */
	private SecuritiesStockInfo getAndCheckStock(SecuritiesStockParam stockParam,long direction) throws NoExistStockException, OutOfStockException, DuplicatedStockException, SecuritiesDAOException, SECInvalidRecordException {
		SEC_SecuritiesStockDAO dao = new SEC_SecuritiesStockDAO();
		//查询是否该库存已经存在
		SecuritiesStockInfo stockInfo = findSecuritiesStock(stockParam.getAccountID(),stockParam.getClient(),stockParam.getSecuritiesID());
		//检查库存是否透库
		if(direction == SECConstant.StockDirection.OUT){
			if(stockParam.getIsNeedCheckOverDraft() == SECConstant.TRUE)
				checkIsOutOfStock(stockInfo, stockParam.getQuantity());
		}
		return stockInfo;
	}

	/**
	 * 根据主键查询对应的证券
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
		//由于查询条件为主键，因此结果集只可能<=1	
		if(list.size() == 1){
			SecuritiesStockInfo info = (SecuritiesStockInfo) list.get(0);
			if(info.getId() < 0)
				throw new SECInvalidRecordException("库存");
			return info;
		}else if(list.size() <= 0){
			throw new SECInvalidRecordException("库存");
		}
		else{		//由于查询条件为主键，因此结果集只可能<=1
			throw new DuplicatedStockException();
		}		
	}	
	
	/**
	 * 检查库存是否不足
	 * 请在进行该操作前判断是否需要进行库存检查(即检查标志stockParam.getIsNeedCheckOverDraft())
		* @param accountParam 
		* @param
		* @return
		* @throws
		 */
	private void checkIsOutOfStock(SecuritiesStockInfo foundInfo, double transQuantity) throws OutOfStockException {
		//可用库存 = 当前库存数量 + 待交割入库挂库数量 - 待交割出库挂库数量 - 库存冻结量			
		//if(transQuantity > (foundInfo.getQuantity()+foundInfo.getSuspenseInQuantity()-foundInfo.getSuspenseOutQuantity()-foundInfo.getFrozenQuantity())){
		if(transQuantity > (foundInfo.getQuantity()-foundInfo.getSuspenseOutQuantity()-foundInfo.getFrozenQuantity())){
			String accountNo = NameRef.getAccountNameById(foundInfo.getAccountID());
			String clientNo = NameRef.getClientNameByID(foundInfo.getClientID());
			String secName = NameRef.getSecuritiesCodeByID(foundInfo.getSecuritiesID());
			throw new OutOfStockException(accountNo,clientNo,secName);
		}
	}
	
	/**
	 * 入库公式
	 * @param updatingStockInfo 需要被更新的库存信息
	 * @param stockParam        交易信息
	 * 
	 * */
	private SecuritiesStockReturn caculateEnterStock(SecuritiesStockInfo updatingStockInfo,SecuritiesStockParam stockParam) throws SecuritiesException{
		log.debug("------开始入库公式-----------");			
		log.debug("------输入参数1为:updatingStockInfo"+updatingStockInfo);		
		log.debug("------输入参数2为:stockParam"+stockParam);		
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
			//log.debug("------库存更新信息为:updatingStockInfo"+updatingStockInfo);			
			//dao.update(updatingStockInfo);
		//} catch (ITreasuryDAOException e) {
		//	throw new SecuritiesDAOException(e);
		//}
		res.setNewQuanitity(updatingStockInfo.getQuantity());
		res.setNewCost(updatingStockInfo.getCost());
		res.setNewNetCost(updatingStockInfo.getNetCost());		
		log.debug("------入库返回值为:"+res);	
		log.debug("------结束入库公式-----------");		
		return res;
	}
	
	/**
	 * 销库(入库删除)公式
	 * @param updatingStockInfo 需要被更新的库存信息
	 * @param stockParam        交易信息
	 * 
	 * */
	private void caculateDeleteEnterStock(SecuritiesStockInfo updatingStockInfo,SecuritiesStockParam stockParam) throws SecuritiesException{
		log.debug("------开始销库(入库删除)公式-----------");		
		log.debug("------输入参数1为:updatingStockInfo"+updatingStockInfo);		
		log.debug("------输入参数2为:stockParam"+stockParam);				
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
			log.debug("------库存更新信息为:updatingStockInfo"+updatingStockInfo);			
			dao.update(updatingStockInfo);
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}								
		log.debug("------结束销库(入库删除)公式-----------");
	}	
	
	
	/**
	 * 出库公式
	 * @param updatingStockInfo 需要被更新的库存信息
	 * @param stockParam        交易信息
	 * @return SecuritiesStockOutReturn 出库返回的除了库存记录以外的信息信息
	 * */	
	private SecuritiesStockReturn caculateExitStock(SecuritiesStockInfo updatingStockInfo,SecuritiesStockParam stockParam) throws SecuritiesException{
		SecuritiesStockReturn stockOutReturn = new SecuritiesStockReturn();
		log.debug("------开始出库公式-----------");		
		log.debug("------输入参数1为:updatingStockInfo"+updatingStockInfo);		
		log.debug("------输入参数2为:stockParam"+stockParam);						
		SEC_SecuritiesStockDAO dao = new SEC_SecuritiesStockDAO();	
		updatingStockInfo.clearUsedFields();		
		updatingStockInfo.setId(updatingStockInfo.getId());		
		
		//新库存数量 = 原库存数量-出库数量
		double oldQuantity = updatingStockInfo.getQuantity();
		updatingStockInfo.setQuantity(updatingStockInfo.getQuantity()-stockParam.getQuantity());
		//rate = (1-出库数量/ 原库存数量)
		log.debug("--------出库数量:"+stockParam.getQuantity());
		log.debug("--------原库存数量:"+oldQuantity);
		double rate = 1.0;
		if(oldQuantity != 0.0)
			rate = 1.0-(stockParam.getQuantity()/oldQuantity);
		log.debug("--------rate = (1-出库数量/ 原库存数量):"+rate);
		//新总成本 = 原总成本 * rate
		double oldCost = updatingStockInfo.getCost();
		if(stockParam.getQuantity() == 0)
			updatingStockInfo.setCost(updatingStockInfo.getCost()-stockParam.getAmount());
		else if(oldQuantity == 0.0)
			updatingStockInfo.setCost(0.0);
		else
			updatingStockInfo.setCost(updatingStockInfo.getCost()*rate);
		//新净价总成本 = 原净价总成本 * rate
		double oldNetCost = updatingStockInfo.getNetCost();
//		updatingStockInfo.setNetCost(updatingStockInfo.getNetCost()*rate);
		if(stockParam.getQuantity() == 0)
			updatingStockInfo.setNetCost(updatingStockInfo.getNetCost()-stockParam.getNetPriceAmount());
		else if(oldQuantity == 0.0)
			updatingStockInfo.setNetCost(0.0);
		else
			updatingStockInfo.setNetCost(updatingStockInfo.getNetCost()*rate);		
		
		stockOutReturn.setNewQuanitity(updatingStockInfo.getQuantity());
		//出库总成本 = 原总成本 – 新总成本
		double outCost = oldCost-updatingStockInfo.getCost();
		stockOutReturn.setNewCost(updatingStockInfo.getCost());	
		//出库净价总成本 = 原净价总成本 – 新净价总成本
		double outNetCost = oldNetCost-updatingStockInfo.getNetCost();
		stockOutReturn.setNewNetCost(updatingStockInfo.getNetCost());
		//出库实际盈亏 = 出库金额 - 出库总成本
		stockOutReturn.setNewProfitLoss(stockParam.getAmount()-outCost);
		//出库净价实际盈亏 = 出库净价金额 - 出库净价总成本
		stockOutReturn.setNewNetProfitLoss(stockParam.getNetPriceAmount()-outNetCost);
		//新累计实际盈亏 = 原累计实际盈亏 + 出库实际盈亏
		
		//新累计净价实际盈亏 = 原累计实际盈亏 + 出库净价实际盈亏
		
		//有库存变化才更新以下数据
		if(stockParam.getQuantity() > 0.0){
			stockOutReturn.setUnitCost(outCost/stockParam.getQuantity());
			//单位净价总成本 = 出库净价总成本/出库数量
			stockOutReturn.setUnitNetCost(outNetCost/stockParam.getQuantity());
			//单位实际盈亏 = 出库实际盈亏/出库数量
			stockOutReturn.setUnitProfitLoss(stockOutReturn.getNewProfitLoss()/stockParam.getQuantity());
			//单位净价实际盈亏 = 出库净价实际盈亏/出库数量
			stockOutReturn.setUnitNetProfitLoss(stockOutReturn.getNewNetProfitLoss()/stockParam.getQuantity());
		}else{
			stockOutReturn.setUnitCost(0.0);
			stockOutReturn.setUnitNetCost(0.0);
			//单位实际盈亏 = 出库实际盈亏/出库数量
			stockOutReturn.setUnitProfitLoss(0.0);
			//单位净价实际盈亏 = 出库净价实际盈亏/出库数量
			stockOutReturn.setUnitNetProfitLoss(0.0);			
		}
//		try {
//			log.debug("------库存更新信息为:updatingStockInfo"+updatingStockInfo);					
//			dao.update(updatingStockInfo);
//		} catch (ITreasuryDAOException e) {
//			throw new SecuritiesDAOException(e);
//		}			
		log.debug("------出库公式返回信息为:stockOutReturn"+stockOutReturn);		
		log.debug("------结束出库公式-----------");		
		return stockOutReturn;		
	}
	/**
	 * 退库(出库删除)公式
	 * @param updatingStockInfo 需要被更新的库存信息
	 * @param stockParam        交易信息
	 * */		
	private void caculateCancelExitStock(SecuritiesStockInfo updatingStockInfo,SecuritiesStockParam stockParam) throws SecuritiesException{
		log.debug("------开始退库(出库删除)公式-----------");		
		log.debug("------输入参数1为:updatingStockInfo"+updatingStockInfo);		
		log.debug("------输入参数2为:stockParam"+stockParam);								
		
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
//			log.debug("------库存更新信息为:updatingStockInfo"+updatingStockInfo);				
//			dao.update(updatingStockInfo);
//		} catch (ITreasuryDAOException e) {
//			throw new SecuritiesDAOException(e);
//		}			
		log.debug("------结束退库(出库删除)公式-----------");				
	}
	
	/**
	 * 入库挂库
	 * @param updatingStockInfo 需要被更新的库存信息
	 * @param stockParam        交易信息 
	 * */
	private void suspendInStock(SecuritiesStockInfo updatingStockInfo,SecuritiesStockParam stockParam) throws SecuritiesException{
		log.debug("------开始入库挂库-----------");				
		SEC_SecuritiesStockDAO dao = new SEC_SecuritiesStockDAO();	
		//updatingStockInfo.clearUsedFields();
		updatingStockInfo.setId(updatingStockInfo.getId());
		updatingStockInfo.setSuspenseInQuantity(updatingStockInfo.getSuspenseInQuantity()+stockParam.getQuantity());
		updatingStockInfo.setSuspenseInAmount(updatingStockInfo.getSuspenseInAmount()+stockParam.getAmount());
		updatingStockInfo.setSuspenseInNetAmount(updatingStockInfo.getSuspenseInNetAmount()+stockParam.getNetPriceAmount());
		try {
			log.debug("------库存更新信息为:updatingStockInfo"+updatingStockInfo);				
			dao.update(updatingStockInfo);
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		log.debug("------结束入库挂库-----------");		
	}
	
	/**
	 * 取消入库挂库
	 * @param updatingStockInfo 需要被更新的库存信息
	 * @param stockParam        交易信息 
	 * */
	private void cancelSuspendInStock(SecuritiesStockInfo updatingStockInfo,SecuritiesStockParam stockParam) throws SecuritiesException{
		log.debug("------开始取消入库挂库-----------");						
		SEC_SecuritiesStockDAO dao = new SEC_SecuritiesStockDAO();	
		updatingStockInfo.clearUsedFields();
		updatingStockInfo.setId(updatingStockInfo.getId());		
		updatingStockInfo.setSuspenseInQuantity(updatingStockInfo.getSuspenseInQuantity()-stockParam.getQuantity());
		updatingStockInfo.setSuspenseInAmount(updatingStockInfo.getSuspenseInAmount()-stockParam.getAmount());
		updatingStockInfo.setSuspenseInNetAmount(updatingStockInfo.getSuspenseInNetAmount()-stockParam.getNetPriceAmount());
		try {
			log.debug("------库存更新信息为:updatingStockInfo"+updatingStockInfo);			
			dao.update(updatingStockInfo);
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}	
		log.debug("------结束取消入库挂库-----------");				
	}	
	
	
	/**
	 * 出库挂库
	 * @param updatingStockInfo 需要被更新的库存信息
	 * @param stockParam        交易信息 
	 * */
	private void suspendOutStock(SecuritiesStockInfo updatingStockInfo,SecuritiesStockParam stockParam) throws SecuritiesException{
		log.debug("------开始出库挂库-----------");							
		SEC_SecuritiesStockDAO dao = new SEC_SecuritiesStockDAO();	
		updatingStockInfo.clearUsedFields();		
		updatingStockInfo.setSuspenseOutQuantity(updatingStockInfo.getSuspenseOutQuantity()+stockParam.getQuantity());
		updatingStockInfo.setSuspenseOutAmount(updatingStockInfo.getSuspenseOutAmount()+stockParam.getAmount());
		updatingStockInfo.setSuspenseOutNetAmount(updatingStockInfo.getSuspenseOutNetAmount()+stockParam.getNetPriceAmount());
		try {
			log.debug("------库存更新信息为:updatingStockInfo"+updatingStockInfo);					
			dao.update(updatingStockInfo);
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}				
		log.debug("------结束出库挂库-----------");			
	}
	
	/**
	 * 取消出库挂库
	 * @param updatingStockInfo 需要被更新的库存信息
	 * @param stockParam        交易信息 
	 * */
	private void cancelSuspendOutStock(SecuritiesStockInfo updatingStockInfo,SecuritiesStockParam stockParam) throws SecuritiesException{
		log.debug("------开始取消出库挂库-----------");					
		SEC_SecuritiesStockDAO dao = new SEC_SecuritiesStockDAO();	
		updatingStockInfo.clearUsedFields();		
		updatingStockInfo.setSuspenseOutQuantity(updatingStockInfo.getSuspenseOutQuantity()-stockParam.getQuantity());
		updatingStockInfo.setSuspenseOutAmount(updatingStockInfo.getSuspenseOutAmount()-stockParam.getAmount());
		updatingStockInfo.setSuspenseOutNetAmount(updatingStockInfo.getSuspenseOutNetAmount()-stockParam.getNetPriceAmount());
		try {
			log.debug("------库存更新信息为:updatingStockInfo"+updatingStockInfo);					
			dao.update(updatingStockInfo);
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}	
		log.debug("------结束取消出库挂库-----------");			
	}	
	
	/**
	 * 	单户库存日结
	 * 针对一个业务单位下一个资金帐户下的一个证券代码，从起日到止日，进行库存日结
	 * */
	public void caculateSingleAccountDailyStock(SingleAccountDailyStockParam param)throws RemoteException,SecuritiesException{
		log.debug("------开始单户库存日结---------");	
		log.debug("------单户库存日结输入参数为:"+param);
//		if(param.getSecuritiesID() == 8 && param.getClientID() == 2 && param.getAccountID() == 45){
//		}
//	else{
//			log.debug("----------测试跳过该记录-------------");
//			return;
//		}
		
		SecuritiesStockInfo securitiesInfo = findSecuritiesStock(param.getAccountID(),param.getClientID(),param.getSecuritiesID());
		log.debug("-----被处理的库存信息为:"+securitiesInfo);
		
		//zpli modify 2006-03-10 股票不区分全价净价，因此如果类型是股票，所有全价类的字段置为0
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
		log.debug("-----被处理的库存日结信息为:"+dailyStockInfo);
		if(dailyStockInfo == null && !sDate.after(eDate)){//说明是数据初始化的库存：向库存日结情况表sec_DailyStock中插入一条记录
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
				log.debug("-----新增的库存日结信息为:"+dailyStockInfo);				
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
		
		log.debug("----------开始从开始日到结束日进行日结-----------");		
		while(!sDate.after(eDate)){
			System.out.println("----------------正在处理的日期是 "+sDate);
			//yesterdayOfStart = DataFormat.getNextDate(sDate, -1);
			SEC_VStockDetailDAO stockDetailDAO = new SEC_VStockDetailDAO();
			DailyStockInfo inDailyStockInfo = stockDetailDAO.sumStockDetails(param.getClientID(), param.getAccountID(), param.getSecuritiesID(), sDate, SECConstant.StockDirection.IN);
			DailyStockInfo outDailyStockInfo = stockDetailDAO.sumStockDetails(param.getClientID(), param.getAccountID(), param.getSecuritiesID(), sDate, SECConstant.StockDirection.OUT);
			log.debug("---------本日入库:"+inDailyStockInfo);			
			log.debug("---------本日出库:"+outDailyStockInfo);			
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
			log.debug("----------开始逐笔更新交割单-----------");	
			while(it.hasNext()){
				VStockDetailInfo tmpStockDetail = (VStockDetailInfo) it.next();
				log.debug("---------正在处理的StockDetail信息为:"+tmpStockDetail);
				//if(tmpStockDetail.getQuantity() == 0.0 || currentStockQuantity == 0.0){
				//	log.debug("---------正在处理的交割单没有库存,跳过处理下一笔-------------");
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
//					当前库存数量=返回的新库存数量
					currentStockQuantity = stockReturn.getNewQuanitity();					
//					当前总成本=返回的新总成本
					currentCost = stockReturn.getNewCost();					
//					当前净价总成本=返回的新净价总成本
					currentNetCost = stockReturn.getNewNetCost();					
//					当前计实际盈亏=返回的新累计实际盈亏
					currentProfitLoss = stockReturn.getNewProfitLoss();					
//					当前计净价实际盈亏=返回的新累计净价实际盈亏
					currentNetProfitLoss = stockReturn.getNewNetProfitLoss();
					//更新交割单记录
					SEC_DeliveryOrderDAO doDAO = new SEC_DeliveryOrderDAO();
					DeliveryOrderInfo updatedDeliveryOrder = new DeliveryOrderInfo();
					updatedDeliveryOrder.setId(tmpStockDetail.getDeliveryOrderID());
					updatedDeliveryOrder.setUnitCost(stockReturn.getUnitCost());
					updatedDeliveryOrder.setUnitNetCost(stockReturn.getUnitNetCost());
					updatedDeliveryOrder.setUnitProfitLoss(stockReturn.getUnitProfitLoss());
					updatedDeliveryOrder.setUnitNetProfitLoss(stockReturn.getUnitNetProfitLoss());
					log.debug("----------被更新的交割单信息为:"+updatedDeliveryOrder);
					try {
						doDAO.update(updatedDeliveryOrder);
					} catch (ITreasuryDAOException e) {
						throw new SecuritiesDAOException(e);	
					}							
				}
		
			}//end of while(it.hasNext())
			double[] costs = vStockDetailDAO.sumTodayStockCost(param.getClientID(), param.getAccountID(), param.getSecuritiesID(), sDate);
			double dailyOutCost = costs[0];	  //本日出库成本（全价）
			double dailyOutNetCost = costs[1];//本日出库成本（净价）
			log.debug("----------本日出库成本（全价）:"+dailyOutCost);	
			log.debug("----------本日出库成本（净价）:"+dailyOutNetCost);
			
			//期末数
			DailyStockInfo currentDailyStockInfo = new DailyStockInfo();
			//本日库存数量=当前库存数量
			currentDailyStockInfo.setQuantity(currentStockQuantity);
			//本日冻结库存数量
			double currentForzenStock = vStockDetailDAO.sumTodayFrozenStock(param.getClientID(), param.getAccountID(), param.getSecuritiesID(), sDate);
			System.out.println("------------------今日冻结库存为 "+currentForzenStock);
			currentDailyStockInfo.setFrozenQuantity(currentFrozenStockQuantity+currentForzenStock);
			
			//zpli modify 2006-03-10 股票不区分全价净价，因此如果类型是股票，所有全价类的字段置为0
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
			//今天的日结库存
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
			log.debug("----------今天的日结库存:"+dailyStockInfo);
			
			initStockQuantity = currentDailyStockInfo.getQuantity();
			initFrozenStockQuantity = currentDailyStockInfo.getFrozenQuantity();
			initCost = currentDailyStockInfo.getCost();
			initNetCost = currentDailyStockInfo.getNetCost();
			initProfitLoss = currentDailyStockInfo.getProfitLoss();
			initNetProfitLoss =currentDailyStockInfo.getNetProfitLoss();		
			
			//日期增加一天
			sDate = DataFormat.getNextDate(sDate, 1); 
			log.debug("更新后的开始日为: "+sDate);	
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
		log.debug("------开始库存处理::取消入库-----------");
		log.debug("------输入参数为:"+stockParam);		
		SEC_SecuritiesStockDAO dao = new SEC_SecuritiesStockDAO();		
		SecuritiesStockInfo stockInfo = getAndCheckStock(stockParam,SECConstant.StockDirection.OUT);
		log.debug("-----正在被操作的库存信息为:"+stockInfo);		
	    Timestamp currentDate = Env.getSecuritiesSystemDate(stockParam.getOfficeID(), stockParam.getCurrencyID());
		Timestamp delvieryDate = stockParam.getDeliveryDate();	
		log.debug("----今天是:"+currentDate);
		log.debug("---交割日是:"+delvieryDate);				
		if(delvieryDate.after(currentDate)){//还未到交割日，取消挂库
			cancelSuspendInStock(stockInfo, stockParam);
		}else{  //取消入库
			caculateDeleteEnterStock(stockInfo, stockParam);
			try {
				log.debug("------库存更新信息为:stockInfo"+stockInfo);				
				dao.update(stockInfo);
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			}					
		}		
		log.debug("------结束库存处理::取消入库-----------");		
	}	
}
