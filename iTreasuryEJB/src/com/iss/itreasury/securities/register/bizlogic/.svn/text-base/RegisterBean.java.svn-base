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
import com.iss.itreasury.securities.register.dataentity.PurchaseRegisterInfo;
import com.iss.itreasury.securities.setting.dataentity.TransactionTypeInfo;
import com.iss.itreasury.securities.util.SECBaseDataEntity;
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
public abstract class RegisterBean {
	
	public final static int REGISTER_OPERATION_TYPE_REGISTER = 0;            //拆借/回购登记,申购申请
	public final static int REGISTER_OPERATION_TYPE_CANCELREGISTER = 1;
	public final static int REGISTER_OPERATION_TYPE_MATURATE = 2;            //拆借/回购到期,申购返款 
	public final static int REGISTER_OPERATION_TYPE_CANCELMATURATE = 3;
	public final static int REGISTER_OPERATION_TYPE_APPLYCONFIRM = 4;        //申购确认
	public final static int REGISTER_OPERATION_TYPE_CANCELAPPLYCONFIRM = 5;     
	public final static int REGISTER_OPERATION_TYPE_APPLYSELL = 6;            //申购卖出
	public final static int REGISTER_OPERATION_TYPE_CANCELAPPLYSELL = 7;    		
	
	private Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);
	
	public static RegisterBean newInstance(TransactionTypeInfo transationTypeInfo){
		long registerType = SECConstant.RegisterProcess.getBelongRegister(transationTypeInfo.getRegisterProcess());
		if(registerType == SECConstant.RegisterProcess.REPURCHASE_REGISTER){
			return new RepurchaseRegisterBean();
		}else if(registerType == SECConstant.RegisterProcess.LONGTERMINVESTMENT_REGISTER){
			//return new SEC_InvestmentRegisterDAO();			
		}else if(registerType == SECConstant.RegisterProcess.PURCHASE_REGISTER){
			return new PurchaseRegisterBean();
		}
		return null;
	}
	/**
	 * 登记薄登记操作的统一处理
	 * @param regiesterInfo 登记薄信息
	 * */
	public long register(DeliveryOrderInfo doInfo) throws SecuritiesException{
		log.debug("-------开始登记薄处理::登记处理---------");
		long regID = -1;
		SecuritiesDAO dao = RegisterDAOFactory.getRegisterDAO(doInfo.getTransactionTypeInfo());
		log.debug("使用的登记薄DAO为:"+dao.getClass());
		try {
			SECBaseDataEntity obj = doInfo.convertToRegisterInfo(REGISTER_OPERATION_TYPE_REGISTER);			
			log.debug("新增的登记薄信息为:"+obj);
			regID = dao.add(obj);
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}		
		log.debug("-------结束登记薄处理::登记处理---------");
		return regID;
	}
	
	/**
	 * 登记薄登记操作的统一处理
	 * @param regiesterInfo 登记薄信息
	 * */
	abstract public void cancelRegister(DeliveryOrderInfo doInfo) throws SecuritiesException;
	
	/**
	 * 登记薄到期操作的统一处理
	 * @param regiesterInfo 登记薄信息
	 * */	
	abstract public void maturate(DeliveryOrderInfo doInfo) throws SecuritiesException;
	
	/**
	 * 登记薄登记取消到期
	 * @param regiesterInfo 登记薄信息
	 * */	
	abstract public void cancelMaturate(DeliveryOrderInfo doInfo) throws SecuritiesException;
	
	/**
	 * 登记薄申购确认，仅仅针对申购相关业务
	 * @param regiesterInfo 登记薄信息
	 * */
	abstract public void confirmApplication(DeliveryOrderInfo doInfo)throws SecuritiesException;
	
	/**
	 * 登记薄申购取消确认，仅仅针对申购相关业务
	 * @param regiesterInfo 登记薄信息
	 * */
	abstract public void cancelConfirmApplication(DeliveryOrderInfo doInfo)throws SecuritiesException;
	
	/**
	 * 申购卖出
	 * 一级卖出时，修改证券申购登记簿
	 * @param regiesterInfo 登记薄信息
	 * */
	abstract public void sellOut(DeliveryOrderInfo doInfo)throws SecuritiesException;
	
	/**
	 * 申购取消卖出
	 * 修改证券申购登记簿
	 * @param regiesterInfo 登记薄信息
	 * */	
	abstract public void cancelSellOut(DeliveryOrderInfo doInfo)throws SecuritiesException;	
	
	/**
	 * 判断关联交易的交割单是否可以被取消复核
	 * */
	abstract void isAllowCancelCheck(DeliveryOrderInfo doInfo) throws SecuritiesException;
	
	}

