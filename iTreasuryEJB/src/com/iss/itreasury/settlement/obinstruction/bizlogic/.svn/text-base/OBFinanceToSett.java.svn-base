/*
 * Created on 2004-02-02
 * 
 * To change the template for this generated file go to Window - Preferences - Java - Code Generation - Code and
 * Comments
 */
package com.iss.itreasury.settlement.obinstruction.bizlogic;


import java.rmi.RemoteException;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.settlement.bizdelegation.TransCurrentDepositDelegation;
import com.iss.itreasury.settlement.obinstruction.dataentity.OBFinanceInstrResult;
import com.iss.itreasury.settlement.query.queryobj.BaseQueryObject;
import com.iss.itreasury.settlement.setting.bizlogic.CommissionSettingBiz;
import com.iss.itreasury.settlement.setting.dataentity.CommissionSettingInfo;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositAssembler;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IExceptionMessage;
import com.iss.itreasury.util.Log4j;


public class OBFinanceToSett extends BaseQueryObject
{
	
	//
	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbOrderBy = null;
	Log4j logger = null;
	private final static  Object lockObj = new Object();  //静态
	private static Map lockMap=Collections.synchronizedMap(new HashMap());
	/**
	 *  
	 */
	public OBFinanceToSett()
	{
		super();
		// TODO Auto-generated constructor stub
		logger = new Log4j(Constant.ModuleType.SETTLEMENT, this);

	}
   //网银指令数据完整性校验
    public void validateInsr(FinanceInfo info)throws Exception
    {
    	
        try
        {   
            if(info.getID()<0)
			 {
            	
   	             throw new IException("请选择网银指令号！");   
   	             
             }
            if(info.getStatus()== OBConstant.SettInstrStatus.DEAL
            	|| info.getStatus()== OBConstant.SettInstrStatus.FINISH
            	|| info.getStatus()== OBConstant.SettInstrStatus.REFUSE)
             {  
       	
   	             throw new IException("银行指令号"+info.getID()+"已经被处理，请检查！");
   	              
            }
            // 银行付款 
        	if(info.getDefaultTransType()==SETTConstant.TransactionType.BANKPAY 
        			||info.getDefaultTransType()==SETTConstant.TransactionType.BANKPAY_DOWNTRANSFER)	
         	 {  	
        	          if(info.getBankID()<0)
        	          {
        	        	  throw new IException("银行指令号"+info.getID()+"开户行不能为空，请检查！");
        	          }
	
         	  }
        	 //内部转账
       	     else if(info.getDefaultTransType()==SETTConstant.TransactionType.INTERNALVIREMENT) 
			  { 

		      }
        }
        catch (Exception e)
		{
			throw  e;
		}
    }
    /**
	 * 
	 * Method isTouch
	 * @descriptin  判断是否被非法修改过
	 * @return long 根据条件查找
	 * @throws RemoteException
	 */
	private boolean isTouch(FinanceInfo info,TransInfo transInfo)throws Exception
	{
		try
		{
			//判断是否被非法修改过
			Timestamp lastTouchDate;
			lastTouchDate =transInfo.getObModifyDate(info.getID());
			Timestamp curTouchDate =info.getDtModify();
			if (curTouchDate == null || lastTouchDate.compareTo(curTouchDate) != 0)
				return true;
			else
				return false;
		}
		catch (Exception e)
		{
			throw new IException("银行指令号"+info.getID()+"已经被修改，请检查！");
		}
	 }
    
	 /**
	 * Method refuseFinaceinstr.网银指令接收业务
	 * @param Vector
	 * @return OBFinanceInstrResult 接收记录的返回信息
	 * @throws IException
	 */
    public OBFinanceInstrResult saveFinaceinstr(Vector v) throws Exception {
    	
		TransCurrentDepositDelegation depositDelegation = new TransCurrentDepositDelegation();
		TransCurrentDepositInfo currInfo = new TransCurrentDepositInfo();
		TransInfo transInfo = new TransInfo();
		OBFinanceInstrResult resultInfo = new OBFinanceInstrResult();
		int mSuccessCount = 0;
		int mFailedCount = 0;
		long strID = -1;
		long lockID=-1;
		for (int i = 0; i < v.size(); i++) {
			
			try {
				
				FinanceInfo info = new FinanceInfo();
				FinanceInfo tempInfo = new FinanceInfo();
				info = (FinanceInfo) v.elementAt(i);
				lockID=info.getID();
				System.out.println("**********************开始锁定************************"+lockID);
				this.lockObj(info.getID());
				System.out.println("**********************完成锁定************************"+lockID);
				tempInfo = transInfo.findInstr(info.getID(), info.getUserID(),info.getCurrencyID());
				FinanceInfo financeEbankInfo = transInfo.findEbankInstr(info.getID(), info.getUserID(), info.getCurrencyID());
				tempInfo.setPayerAcctID(financeEbankInfo.getPayerAcctID());
				tempInfo.setPayeeAcctID(financeEbankInfo.getPayeeAcctID());
				tempInfo.setInterestPayeeAcctID(financeEbankInfo.getInterestPayeeAcctID());
				tempInfo.setSDepositInterestToAccountID(financeEbankInfo.getSDepositInterestToAccountID());
				currInfo = transInfo.transCurrent(tempInfo, info.getUserID());
				currInfo.setBankID(info.getBankID());
				currInfo.setCommissionAmount(info.getCommissionAmount());
				currInfo.setCommissionType(info.getCommissionType());
				currInfo.setTransNo(info.getTransNo());
				currInfo.setCommissionType(financeEbankInfo.getRemitArea());
				currInfo.setIsUrgent(financeEbankInfo.getRemitSpeed());
				TransCurrentDepositAssembler assembler = new TransCurrentDepositAssembler(currInfo);
				strID = info.getID();
				// 校验数据的完整性
				info.setStatus(tempInfo.getStatus());
				validateInsr(info);
				// 检查记录是否被修改
			
				if (isTouch(info, transInfo)) {
					System.out.println("**********************解除锁定被修改************************"+lockID);
					this.unLockObj(info.getID());
					System.out.println("**********************完成解除锁定被修改************************"+lockID);
					throw new IException("银行指令号" + info.getID() + "已经被修改，请检查！");
				}

				// 银行付款
				if (tempInfo.getDefaultTransType() == SETTConstant.TransactionType.BANKPAY
						|| tempInfo.getDefaultTransType() == SETTConstant.TransactionType.BANKPAY_DOWNTRANSFER) {

					if (tempInfo.getDefaultTransType() == SETTConstant.TransactionType.BANKPAY_DOWNTRANSFER)
					{
						 currInfo.setTransactionTypeID(SETTConstant.TransactionType.BANKPAY_DOWNTRANSFER);

					} else {
						
						 currInfo.setTransactionTypeID(SETTConstant.TransactionType.BANKPAY);
					}
					if (Config.getBoolean(ConfigConstant.SETT_OBINSTRUCTION_AUTOCHECK, false)) {

						 depositDelegation.saveAndCheckAutomaticallyforEbank(assembler);
						
					} else {
						
						depositDelegation.save(assembler);
					}
					
					mSuccessCount++;
				}
				// 内部转账
				else if (tempInfo.getDefaultTransType() == SETTConstant.TransactionType.INTERNALVIREMENT) {
					
					    currInfo.setTransactionTypeID(SETTConstant.TransactionType.INTERNALVIREMENT);

					if (Config.getBoolean(ConfigConstant.SETT_OBINSTRUCTION_AUTOCHECK, false)) {

						depositDelegation.saveAndCheckAutomaticallyforEbank(assembler);

					} else {

						depositDelegation.save(assembler);

					}
					
					mSuccessCount++;
					
				}  else if (tempInfo.getDefaultTransType() == SETTConstant.TransactionType.OPENFIXEDDEPOSIT && (tempInfo.getSDepositBillNo() == null || tempInfo.getSDepositBillNo().length() <= 0)) {

				}  else if (tempInfo.getDefaultTransType() == SETTConstant.TransactionType.OPENFIXEDDEPOSIT
						&& tempInfo.getSDepositBillNo() != null
						&& tempInfo.getSDepositBillNo().length() > 0) {

				}  else if (tempInfo.getDefaultTransType() == SETTConstant.TransactionType.OPENNOTIFYACCOUNT) {

				}  else if (tempInfo.getDefaultTransType() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER) {

				}  else if (tempInfo.getDefaultTransType() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW) {

				}  else if (tempInfo.getDefaultTransType() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER) {

				}  else if (tempInfo.getDefaultTransType() == SETTConstant.TransactionType.TRUSTLOANRECEIVE) {

				}  else if (tempInfo.getDefaultTransType() == SETTConstant.TransactionType.BANKGROUPLOANRECEIVE) {

				}  else if (tempInfo.getDefaultTransType() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE) {

				}  else if (tempInfo.getDefaultTransType() == SETTConstant.TransactionType.INTERESTFEEPAYMENT) {

				}  else if (tempInfo.getDefaultTransType() == SETTConstant.TransactionType.SPECIALOPERATION) {

				}  else if (tempInfo.getDefaultTransType() == SETTConstant.TransactionType.BANKPAY_FINCOMPANYPAY) {

				}  else if (tempInfo.getDefaultTransType() == SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT) {

				}  else if (tempInfo.getDefaultTransType() == SETTConstant.TransactionType.SUBCLIENT_BANKPAY) {

				}
			} catch (Exception e) {
				System.out.println("**********************"+ e.getMessage()+"解除锁定被修改************************"+lockID);
				 this.unLockObj(lockID);
				 System.out.println("**********************"+ e.getMessage()+"完成解除锁定被修改************************"+lockID);
		         logger.error("网银指令批量接收时出错，原因：" + e.getMessage()); 
          	     e.printStackTrace();
          	     mFailedCount++;
          	     // String errorCode = ((IException) e).getErrorCode();
          	     String strTemp = IExceptionMessage.getExceptionMSG(e);
          	     resultInfo.addFailedMessage("" + strID, strTemp);
			}
			resultInfo.setSuccessCount(mSuccessCount);
        	resultInfo.setFailedCount(mFailedCount);
        	resultInfo.setRecevieCount(v.size());
        	System.out.println("**********************解除锁定被修改************************"+lockID);
        	this.unLockObj(lockID);
        	System.out.println("**********************完成锁定被修改************************"+lockID);
		}
			return resultInfo;
	}
    
	 /**
	 * Method refuseFinaceinstr.网银指令拒绝业务
	 * @param Vector
	 * @return OBFinanceInstrResult 拒绝记录的返回信息
	 * @throws IException
	 */
    public OBFinanceInstrResult refuseFinaceinstr(Vector vInstructions)throws Exception
    {
	    	OBFinanceInstrResult resultInfo = new OBFinanceInstrResult();
	    	OBFinanceInstrDao dao= new OBFinanceInstrDao();
	    	TransInfo transInfo = new TransInfo();  	
	    	int mSuccessCount = 0;
	    	int mFailedCount = 0;
	    	long id = -1;
	    	long strID = -1;
	    	long lockID=-1;
	        for (int i = 0; i < vInstructions.size(); i++)
			  {
	     	    
	        	try
	            {           	 
	        		FinanceInfo info = new FinanceInfo();
	        		FinanceInfo tempInfo = new FinanceInfo();
	        		info = (FinanceInfo) vInstructions.elementAt(i);
	        		lockID=info.getID();
					System.out.println("**********************开始锁定************************"+lockID);
					this.lockObj(info.getID());
					System.out.println("**********************完成锁定************************"+lockID);
	        		tempInfo = transInfo.findInstr(info.getID(), info.getUserID(), info.getCurrencyID());
	        		Timestamp tsDealDate = Env.getSystemDate(info.getOfficeID(), info.getCurrencyID());
	        		info.setDealDate(tsDealDate);
	        		info.setStatus(tempInfo.getStatus());
	        		strID = info.getID();
	        		if (isTouch(info, transInfo))
	        		{
						System.out.println("**********************解除锁定被修改************************"+lockID);
						this.unLockObj(info.getID());
						System.out.println("**********************完成解除锁定被修改************************"+lockID);
	        			throw new IException("银行指令号"+info.getID()+"已经被修改，请检查！");
	        		}
	        		// 校验数据的完整性
	        		validateRefuInsr(info);
	        		
	        		id = dao.refuseInstr(info); 
				     
					if(id>0)
					{	 
						mSuccessCount++;
					}
	              }
	              catch (Exception e)
	              {
						System.out.println("**********************解除锁定"+e.getMessage()+"************************"+lockID);
						this.unLockObj(lockID);
						System.out.println("**********************完成解除锁定"+e.getMessage()+"************************"+lockID);
	            	  logger.error("网银指令批量拒绝时出错，原因：" + e.getMessage()); 
	            	  e.printStackTrace();
	            	  mFailedCount++;
	            	  String errorCode = IExceptionMessage.getExceptionMSG(e);;
	            	  resultInfo.addFailedMessage("" + strID, errorCode.toString());
	      		  }
	           }
	        
	    	  resultInfo.setSuccessCount(mSuccessCount);
	    	  resultInfo.setFailedCount(mFailedCount);
	    	  resultInfo.setRecevieCount(vInstructions.size());
	    	  System.out.println("**********************解除锁定************************"+lockID);
			  this.unLockObj(lockID);
			  System.out.println("**********************完成解除锁定************************"+lockID);
	          return resultInfo;
    }
   //网银拒绝指令数据完整性校验
    public void validateRefuInsr(FinanceInfo info)throws Exception
    {
    	
        try
        {   
        	 if(info.getID()<0)
        	  {
	        	  throw new IException("请选择网银指令号！");
	          }
        	 if(info.getStatus()== OBConstant.SettInstrStatus.REFUSE)
        	  {        	        	  
	        	  throw new IException("银行指令号"+info.getID()+"已经被拒绝，请检查！");
	          }
             // 银行付款 
        	if(info.getDefaultTransType()==SETTConstant.TransactionType.BANKPAY 
        			||info.getDefaultTransType()==SETTConstant.TransactionType.BANKPAY_DOWNTRANSFER)	
         	  {  
      	          
         	  }
        	 //内部转账
       	   else if(info.getDefaultTransType()==SETTConstant.TransactionType.INTERNALVIREMENT) 
			  { 
     			
		      }
         }
        catch (Exception e)
		{
			throw  new IException("【"+info.getID()+"】指令已经被拒绝，请检查！");
		}
    }
    
    public void lockObj(long id) throws Exception
    {
    	if(id >0)
    	{
	    	if(lockMap.get(String.valueOf(id))!=null)
	    	{
	    		throw new IException("该笔数据正在被其它用户使用，请稍后再试！");
	    	}
	    	else
	    	{
	    		System.out.println("********************锁定信息--id:"+id+"--********************");
	    		lockMap.put(String.valueOf(id), new Object());
	    	}
    	}
    }
    
    public void unLockObj(long id)throws Exception
    {
    	if(id>0)
    	{
    		System.out.println("********************解锁信息--id:"+id+"--********************");
    		lockMap.remove(String.valueOf(id));
    	}
    }
    
}