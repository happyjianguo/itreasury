/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-4
 */
package com.iss.itreasury.securities.register.bizlogic;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.deliveryorder.dataentity.DeliveryOrderInfo;
import com.iss.itreasury.securities.exception.SECInvalidRecordException;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.register.dao.RegisterDAOFactory;
import com.iss.itreasury.securities.register.dao.SEC_RepurchaseRegisterDAO;
import com.iss.itreasury.securities.register.dataentity.RepurchaseRegisterInfo;
import com.iss.itreasury.securities.register.exception.CapitalHasReturnException;
import com.iss.itreasury.securities.register.exception.NoSuchRegisterOperationException;
import com.iss.itreasury.securities.register.exception.TransAmontNotMatchException;
import com.iss.itreasury.securities.setting.dataentity.TransactionTypeInfo;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.securities.util.SecuritiesDAO;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;
/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class RepurchaseRegisterBean extends RegisterBean {
	private Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);	
	/**
	 * 买卖登记薄到期操作的处理
	 * @param regiesterInfo 登记薄信息
	 * */	

	
	/**
	 * 登记薄登记操作的统一处理
	 * @param regiesterInfo 登记薄信息
	 * */
	public void cancelRegister(DeliveryOrderInfo doInfo) throws SecuritiesException{
		log.debug("-------开始登记薄处理::取消登记处理---------");		
		SecuritiesDAO dao = RegisterDAOFactory.getRegisterDAO(doInfo.getTransactionTypeInfo());
		try {
			long regID = doInfo.convertToRegisterInfo(REGISTER_OPERATION_TYPE_CANCELREGISTER).getId();
			RepurchaseRegisterInfo foundInfo = null;
			foundInfo = (RepurchaseRegisterInfo) dao.findByID(regID, RepurchaseRegisterInfo.class);
			log.debug("-------需要被更新的登记薄信息为:"+foundInfo);			
			if(foundInfo == null || foundInfo.getId() < 0 || foundInfo.getStatusID() != SECConstant.BusinessAttributeStatus.SAVED){
				throw new SECInvalidRecordException("");
			}			
			if(foundInfo.getLastDeliveryOrderID() > 0)//该笔业务已返款	
				throw new SecuritiesException("Sec_E120",null);						
			
			dao.delete(regID);
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		log.debug("-------结束登记薄处理::取消登记处理---------");		
	}		
	
	
	/**
	 * 买卖登记薄登记取消到期
	 * @param regiesterInfo 登记薄信息
	 * */	
	public void cancelMaturate(DeliveryOrderInfo doInfo) throws SecuritiesException{
		log.debug("-------开始买卖登记薄处理::取消到期处理---------");		
		RepurchaseRegisterInfo info = (RepurchaseRegisterInfo)doInfo.convertToRegisterInfo(RegisterBean.REGISTER_OPERATION_TYPE_CANCELMATURATE);
		log.debug("-------买卖登记薄处理参数为:"+info);		
		SecuritiesDAO dao = RegisterDAOFactory.getRegisterDAO(doInfo.getTransactionTypeInfo());
		RepurchaseRegisterInfo foundInfo = null;
		try {
			foundInfo = (RepurchaseRegisterInfo) dao.findByID(info.getId(), RepurchaseRegisterInfo.class);
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		if(foundInfo.getId() < 0)
			throw new SECInvalidRecordException("到期返款");
			
		log.debug("取消到期登记薄信息为: " +foundInfo);		
//		//该笔返款业务已经被取消，不能再被取消
//		if(foundInfo.getBalance() == 0.0){
//			throw new RegisterHasCancelException();
//		}
//		
//		//返款金额
//		if(foundInfo.getBalance() != info.getAmount()){
//			throw new TransAmontNotMatchException(info.getAmount(),foundInfo.getBalance());
//		}
		//更新登记薄信息
		RepurchaseRegisterInfo updatedInfo = new RepurchaseRegisterInfo();
		updatedInfo.setId(info.getId());
		updatedInfo.setBalance(foundInfo.getAmount());
		//末次交割单编号=null
		updatedInfo.setLastDeliveryOrderID(-1);
		log.debug("取消到期登记薄更新信息为: " +updatedInfo);		
		try {
			dao.update(updatedInfo);
		} catch (ITreasuryDAOException e1) {
			throw new SecuritiesDAOException(e1);
		}
		log.debug("-------结束买卖登记薄处理::取消到期处理---------");		
	}


	public void confirmApplication(DeliveryOrderInfo doInfo) throws SecuritiesException {
		 throw new NoSuchRegisterOperationException();
	}


	public void cancelConfirmApplication(DeliveryOrderInfo doInfo) throws SecuritiesException {
		 throw new NoSuchRegisterOperationException();
	}


	public void sellOut(DeliveryOrderInfo doInfo) throws SecuritiesException {
		 throw new NoSuchRegisterOperationException();
	}

	public void cancelSellOut(DeliveryOrderInfo doInfo) throws SecuritiesException {
		 throw new NoSuchRegisterOperationException();
	}


	public void maturate(DeliveryOrderInfo doInfo) throws SecuritiesException{
		log.debug("-------开始买卖登记薄处理::到期处理---------");		
		RepurchaseRegisterInfo info = (RepurchaseRegisterInfo)doInfo.convertToRegisterInfo(RegisterBean.REGISTER_OPERATION_TYPE_MATURATE);
		log.debug("-------买卖登记薄处理参数为:"+info);
		SecuritiesDAO dao = RegisterDAOFactory.getRegisterDAO(doInfo.getTransactionTypeInfo());
		
		RepurchaseRegisterInfo foundInfo = null;
		try {
			foundInfo = (RepurchaseRegisterInfo) dao.findByID(info.getId(), RepurchaseRegisterInfo.class);
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		log.debug("到期登记薄信息为: " +foundInfo);
		if(foundInfo.getBalance() == 0.0){//该笔业务已返款
			throw new CapitalHasReturnException();	
		}
		//返款金额
		if(foundInfo.getBalance() != info.getAmount()){
			throw new TransAmontNotMatchException(info.getAmount(),foundInfo.getBalance());
		}
		//更新登记薄信息
		RepurchaseRegisterInfo updatedInfo = new RepurchaseRegisterInfo();
		updatedInfo.setId(info.getId());
		updatedInfo.setBalance(0.0);
		//传递数据时使用FirstDeliveryOrderIDz作为末次交割单编号ID进行传递
		updatedInfo.setLastDeliveryOrderID(info.getFirstDeliveryOrderID());
		log.debug("到期登记薄更新信息为: " +updatedInfo);		
		try {
			dao.update(updatedInfo);
		} catch (ITreasuryDAOException e1) {
			throw new SecuritiesDAOException(e1);
		}
		log.debug("-------结束买卖登记薄处理::到期处理---------");		
	}	
	
	
	/**
	 * 判断关联交易的交割单是否可以被取消复核
	 * */
	public void isAllowCancelCheck(DeliveryOrderInfo doInfo) throws SecuritiesException{
		RepurchaseRegisterInfo foundInfo = null;
		try {
			SecuritiesDAO dao = RegisterDAOFactory.getRegisterDAO(doInfo.getTransactionTypeInfo());			
			foundInfo = (RepurchaseRegisterInfo) dao.findByID(doInfo.getRegisterId(), RepurchaseRegisterInfo.class);
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		if(foundInfo.getId() < 0)
			throw new SECInvalidRecordException("到期返款");		
	
		if(foundInfo.getBalance() == 0.0 && foundInfo.getLastDeliveryOrderID() <= 0)//该笔业务已返款
			throw new CapitalHasReturnException();	
	}		
}
