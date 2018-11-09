/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-17
 */
package com.iss.itreasury.securities.register.bizlogic;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.deliveryorder.dataentity.DeliveryOrderInfo;
import com.iss.itreasury.securities.exception.SECInvalidRecordException;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.register.dao.RegisterDAOFactory;
import com.iss.itreasury.securities.register.dao.SEC_PurchaseRegisterDAO;
import com.iss.itreasury.securities.register.dataentity.PurchaseRegisterInfo;
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
public class PurchaseRegisterBean extends RegisterBean {

	private Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);

	
	
	/**
	 * 登记薄登记操作的统一处理
	 * @param regiesterInfo 登记薄信息
	 * */
	public void cancelRegister(DeliveryOrderInfo doInfo) throws SecuritiesException{
		log.debug("-------开始登记薄处理::取消登记处理---------");		
		SecuritiesDAO dao = RegisterDAOFactory.getRegisterDAO(doInfo.getTransactionTypeInfo());
		try {
			long regID = doInfo.convertToRegisterInfo(REGISTER_OPERATION_TYPE_CANCELREGISTER).getId();
			PurchaseRegisterInfo foundInfo = null;
			foundInfo = (PurchaseRegisterInfo) dao.findByID(regID, PurchaseRegisterInfo.class);
			log.debug("-------需要被更新的登记薄信息为:"+foundInfo);			
			if(foundInfo == null || foundInfo.getId() < 0 || foundInfo.getStatusID() != SECConstant.BusinessAttributeStatus.SAVED){
				throw new SECInvalidRecordException("申购");
			}			
			if(foundInfo.getConfirmDeliveryOrderID() > 0)//该笔申购已确认	
				throw new SecuritiesException("Sec_E123",null);						
			
			if(foundInfo.getDrawbackDeliveryOrderID() > 0)//该笔申购已返款	
				throw new SecuritiesException("Sec_E126",null);			
			
			
			
			dao.delete(regID);
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		log.debug("-------结束登记薄处理::取消登记处理---------");		
	}	
	
	/**
	 * 申购确认
	 * 中签、获配、确认时，修改证券申购登记簿
	 * @param IRegisterParam
	 * */
	public void confirmApplication(DeliveryOrderInfo doInfo) throws SecuritiesException {
		log.debug("-------开始申购登记簿处理::申购确认---------");
		PurchaseRegisterInfo info = (PurchaseRegisterInfo) doInfo.convertToRegisterInfo(RegisterBean.REGISTER_OPERATION_TYPE_APPLYCONFIRM);
		log.debug("-------登记薄输入参数:"+info);
		SEC_PurchaseRegisterDAO dao = new SEC_PurchaseRegisterDAO();
		PurchaseRegisterInfo foundInfo = null;
		try {
			foundInfo = (PurchaseRegisterInfo) dao.findByID(info.getId(), PurchaseRegisterInfo.class);
			log.debug("-------需要被更新的登记薄信息为:"+foundInfo);
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		if(foundInfo == null || foundInfo.getId() < 0 || foundInfo.getStatusID() != SECConstant.BusinessAttributeStatus.SAVED){
			throw new SECInvalidRecordException("申购");
		}
		
		if(foundInfo.getConfirmDeliveryOrderID() > 0)//该笔申购已确认
			throw new SecuritiesException("Sec_E123",null);
		
		//if(foundInfo.getApplyAmount() < info.getConfirmAmount())//中签数量不应大于申购数量
		//	throw new SecuritiesException("Sec_E124",null);
		
		
		try {
			dao.update(info);
		} catch (ITreasuryDAOException e1) {
			throw new SecuritiesDAOException(e1);
		}
		log.debug("-------结束申购登记簿处理::申购确认---------");		
	}

	/**
	 * 申购确认取消
	 * 修改证券申购登记簿
	 * @param IRegisterParam
	 * */
	public void cancelConfirmApplication(DeliveryOrderInfo doInfo) throws SecuritiesException {
		log.debug("-------开始申购登记簿处理::取消申购确认---------");		
		PurchaseRegisterInfo info = (PurchaseRegisterInfo) doInfo.convertToRegisterInfo(RegisterBean.REGISTER_OPERATION_TYPE_CANCELAPPLYCONFIRM);
		SEC_PurchaseRegisterDAO dao = new SEC_PurchaseRegisterDAO();
		PurchaseRegisterInfo foundInfo = null;
		try {
			foundInfo = (PurchaseRegisterInfo) dao.findByID(info.getId(), PurchaseRegisterInfo.class);
			log.debug("-------需要被更新的登记薄信息为:"+foundInfo);			
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		if(foundInfo == null || foundInfo.getId() < 0 || foundInfo.getStatusID() != SECConstant.BusinessAttributeStatus.SAVED){
			throw new SECInvalidRecordException("申购");
		}
		
		if(foundInfo.getConfirmDeliveryOrderID() < 0)//该笔申购未确认，不能取消确认
			throw new SecuritiesException("Sec_E125",null);
		
		if(foundInfo.getDrawbackDeliveryOrderID() > 0)//该笔申购已返款	
			throw new SecuritiesException("Sec_E126",null);
		
		try {
			dao.update(info);
		} catch (ITreasuryDAOException e1) {
			throw new SecuritiesDAOException(e1);
		}
		log.debug("-------结束申购登记簿处理::取消申购确认---------");		
	}
	
	
	/**
	 * 申购返款
	 * 返款、补款时，修改证券申购登记簿
	 * @param IRegisterParam
	 * */
	public void maturate(DeliveryOrderInfo doInfo) throws SecuritiesException {
		log.debug("-------开始申购登记簿处理::申购返款---------");		
		//PurchaseRegisterParam param = (PurchaseRegisterParam)regiesterInfo;
		PurchaseRegisterInfo info = (PurchaseRegisterInfo) doInfo.convertToRegisterInfo(RegisterBean.REGISTER_OPERATION_TYPE_MATURATE);
		SEC_PurchaseRegisterDAO dao = new SEC_PurchaseRegisterDAO();
		PurchaseRegisterInfo foundInfo = null;
		try {
			foundInfo = (PurchaseRegisterInfo) dao.findByID(info.getId(), PurchaseRegisterInfo.class);
			log.debug("-------需要被更新的登记薄信息为:"+foundInfo);			
			
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		if(foundInfo == null || foundInfo.getId() < 0 || foundInfo.getStatusID() != SECConstant.BusinessAttributeStatus.SAVED){
			throw new SECInvalidRecordException("申购");
		}
		
		if(foundInfo.getDrawbackDeliveryOrderID() > 0)//该笔申购已返款	
			throw new SecuritiesException("Sec_E126",null);
		
		//if(foundInfo.getDrawbackAmount() + doInfo.getNetIncome() > foundInfo.getApplyAmount())//返款总金额不应大于申购金额
		//	throw new SecuritiesException("Sec_E127",null);
		
		
		try {
			//返款金额=+/-实际收付
			info.setDrawbackAmount(foundInfo.getDrawbackAmount()+doInfo.getNetIncome());
			dao.update(info);
		} catch (ITreasuryDAOException e1) {
			throw new SecuritiesDAOException(e1);
		}		
		log.debug("-------结束申购登记簿处理::申购返款---------");		
	}

	/**
	 * 申购返款取消
	 * 修改证券申购登记簿
	 * @param IRegisterParam
	 * */
	public void cancelMaturate(DeliveryOrderInfo doInfo) throws SecuritiesException {
		log.debug("-------开始申购登记簿处理::取消申购返款---------");		
		//PurchaseRegisterParam param = (PurchaseRegisterParam)regiesterInfo;
		PurchaseRegisterInfo info = (PurchaseRegisterInfo) doInfo.convertToRegisterInfo(RegisterBean.REGISTER_OPERATION_TYPE_CANCELMATURATE);
		SEC_PurchaseRegisterDAO dao = new SEC_PurchaseRegisterDAO();
		PurchaseRegisterInfo foundInfo = null;
		try {
			foundInfo = (PurchaseRegisterInfo) dao.findByID(info.getId(), PurchaseRegisterInfo.class);
			log.debug("-------需要被更新的登记薄信息为:"+foundInfo);			
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		if(foundInfo == null || foundInfo.getId() < 0 || foundInfo.getStatusID() != SECConstant.BusinessAttributeStatus.SAVED){
			throw new SECInvalidRecordException("申购");
		}
		
//		if(foundInfo.getDrawbackDeliveryOrderID() > 0)//该笔申购已返款	
//			throw new SecuritiesException("Sec_E126",null);
		
//		if(foundInfo.getSellingDeliveryOrderID() > 0)//该笔申购已卖出
//			throw new SecuritiesException("Sec_E128",null);
		
		
		try {
			dao.update(info);
		} catch (ITreasuryDAOException e1) {
			throw new SecuritiesDAOException(e1);
		}		
		log.debug("-------开始申购登记簿处理::取消申购返款---------");		
	}
	
	/**
	 * 申购卖出
	 * 一级卖出时，修改证券申购登记簿
	 * @param regiesterInfo 登记薄信息
	 * */
	public void sellOut(DeliveryOrderInfo doInfo)throws SecuritiesException{
		//PurchaseRegisterParam param = (PurchaseRegisterParam)regiesterInfo;		
		log.debug("-------开始申购登记簿处理::申购卖出---------");		
		PurchaseRegisterInfo info = (PurchaseRegisterInfo) doInfo.convertToRegisterInfo(RegisterBean.REGISTER_OPERATION_TYPE_APPLYSELL);
		SEC_PurchaseRegisterDAO dao = new SEC_PurchaseRegisterDAO();
		PurchaseRegisterInfo foundInfo = null;
		try {
			foundInfo = (PurchaseRegisterInfo) dao.findByID(info.getId(), PurchaseRegisterInfo.class);
			log.debug("-------需要被更新的登记薄信息为:"+foundInfo);			
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		if(foundInfo == null || foundInfo.getId() < 0 || foundInfo.getStatusID() != SECConstant.BusinessAttributeStatus.SAVED){
			throw new SECInvalidRecordException("申购");
		}
		
		if(foundInfo.getSellingAmount() == foundInfo.getConfirmAmount())//该笔申购已全部卖出
			throw new SecuritiesException("Sec_E129",null);
		
		if(foundInfo.getSellingAmount() + doInfo.getAmount() > foundInfo.getConfirmAmount())//卖出总数量不应大于中签数量
			throw new SecuritiesException("Sec_E160",null);

		try {
			dao.update(info);
		} catch (ITreasuryDAOException e1) {
			throw new SecuritiesDAOException(e1);
		}	
		log.debug("-------结束申购登记簿处理::申购卖出---------");		
	}
	
	/**
	 * 申购取消卖出
	 * 修改证券申购登记簿
	 * @param regiesterInfo 登记薄信息
	 * */	
	public void cancelSellOut(DeliveryOrderInfo doInfo)throws SecuritiesException{
		log.debug("-------开始申购登记簿处理::取消申购卖出---------");		
		//PurchaseRegisterParam param = (PurchaseRegisterParam)regiesterInfo;		
		PurchaseRegisterInfo info = (PurchaseRegisterInfo) doInfo.convertToRegisterInfo(RegisterBean.REGISTER_OPERATION_TYPE_CANCELAPPLYSELL);
		SEC_PurchaseRegisterDAO dao = new SEC_PurchaseRegisterDAO();
		PurchaseRegisterInfo foundInfo = null;
		try {
			foundInfo = (PurchaseRegisterInfo) dao.findByID(info.getId(), PurchaseRegisterInfo.class);
			log.debug("-------需要被更新的登记薄信息为:"+foundInfo);				
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		if(foundInfo == null || foundInfo.getId() < 0 || foundInfo.getStatusID() != SECConstant.BusinessAttributeStatus.SAVED){
			throw new SECInvalidRecordException("申购");
		}
		
		if(foundInfo.getSellingDeliveryOrderID() <= 0)//该笔申购未卖出，不能取消卖出
			throw new SecuritiesException("Sec_E161",null);
		
		try {
			dao.update(info);
		} catch (ITreasuryDAOException e1) {
			throw new SecuritiesDAOException(e1);
		}				
		log.debug("-------结束申购登记簿处理::取消申购卖出---------");		
	}		
	
	/**
	 * 判断关联交易的交割单是否可以被取消复核
	 * */
	public void isAllowCancelCheck(DeliveryOrderInfo doInfo) throws SecuritiesException{
		TransactionTypeInfo transactionTypeInfo = doInfo.getTransactionTypeInfo();
		if(doInfo.getRegisterId() < 0)
			return;
		
		SEC_PurchaseRegisterDAO dao = new SEC_PurchaseRegisterDAO();
		PurchaseRegisterInfo foundInfo = null;
		try {
			foundInfo = (PurchaseRegisterInfo) dao.findByID(doInfo.getRegisterId(), PurchaseRegisterInfo.class);
			log.debug("-------需要检查的登记薄信息为:"+foundInfo);			
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}		
 
		if(transactionTypeInfo.getRegisterProcess() == SECConstant.RegisterProcess.APPLICATION_APPLY){
			if(foundInfo.getConfirmDeliveryOrderID() > 0)//该笔申购已中签
				throw new SecuritiesException("Sec_E123",null);
			if(foundInfo.getDrawbackDeliveryOrderID() > 0)//该笔申购已返款	
				throw new SecuritiesException("Sec_E126",null);			
			if(foundInfo.getSellingDeliveryOrderID() > 0)//该笔申购已卖出
				throw new SecuritiesException("Sec_E128",null);			
		}else if(transactionTypeInfo.getRegisterProcess() == SECConstant.RegisterProcess.APPLICATION_CONFIRM){
			if(foundInfo.getDrawbackDeliveryOrderID() > 0)//该笔申购已返款	
				throw new SecuritiesException("Sec_E126",null);			
			if(foundInfo.getSellingDeliveryOrderID() > 0)//该笔申购已卖出
				throw new SecuritiesException("Sec_E128",null);						
		}else if(transactionTypeInfo.getRegisterProcess() == SECConstant.RegisterProcess.APPLICATION_MATURE){
			if(foundInfo.getSellingDeliveryOrderID() > 0)//该笔申购已卖出
				throw new SecuritiesException("Sec_E128",null);						
		}
		

	}	
}
