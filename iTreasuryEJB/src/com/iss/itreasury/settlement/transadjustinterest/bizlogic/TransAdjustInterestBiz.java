/*
 * Created on 2005-9-6
 * 
 * To change the template for this generated type comment go to Window>Preferences>Java>Code Generation>Code and Comments
 */

package com.iss.itreasury.settlement.transadjustinterest.bizlogic;

import java.rmi.RemoteException;
import java.util.Collection;


import com.iss.itreasury.settlement.transadjustinterest.dao.AccumulateFeeDao;
import com.iss.itreasury.settlement.transadjustinterest.dao.Sett_AdjustInterestDao;
import com.iss.itreasury.settlement.transadjustinterest.dataentity.AccumulateFeeInfoQuery;
import com.iss.itreasury.settlement.transadjustinterest.dataentity.AdjustInterestInfoQuery;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.base.SettlementDAOException;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.transadjustinterest.dataentity.AdjustInterestInfo;
import com.iss.itreasury.settlement.transloan.dao.Sett_TransGrantLoanDAO;
import com.iss.itreasury.settlement.transloan.dataentity.TransGrantLoanInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IRollbackException;

/**
 * @author feiye
 * 
 * 累计费用及利息调整功能商业逻辑类
 * 
 * To change the template for this generated type comment go to Window>Preferences>Java>Code Generation>Code and
 * Comments
 */
public class TransAdjustInterestBiz {
	/**
     * 结算模块--查找(账户各项费用)当日信息(某一个时间段的记录查询值) 
     */
    public Collection findForm(AccumulateFeeInfoQuery queryCondition)  {
    	Collection coll=null;	//coll:用Vector封装
    	AccumulateFeeDao accumulateDao=new AccumulateFeeDao();
    	try{
    		//查找集合功能
    		coll=accumulateDao.findForm(queryCondition);
    		
    	}catch(SettlementDAOException e){
    		coll=null;
    		e.printStackTrace();
    	}catch(ITreasuryDAOException e){
    		coll=null;
    		e.printStackTrace();
    	}
    	return coll;
    }
    /**
     * 结算模块----查找(账户各项费用)历史信息 
     */
    public Collection findFormHistory(AccumulateFeeInfoQuery queryCondition)  {
    	Collection coll=null;	//coll:用Vector封装
    	AccumulateFeeDao accumulateDao=new AccumulateFeeDao();
    	try{
    		//查找集合功能
    		coll=accumulateDao.findFormHistory(queryCondition);
    		
    	}catch(SettlementDAOException e){
    		coll=null;
    		e.printStackTrace();
    	}catch(ITreasuryDAOException e){
    		coll=null;
    		e.printStackTrace();
    	}
    	return coll;
    }
    
   /////////////////////////////////////////////////
    	
	/**
     * 结算模块--累计费用调整信息(调整) ---根据记录ID查询
     * 
     */
	public AdjustInterestInfo findAdjustByID(long lID) throws SettlementException{
		AdjustInterestInfo adjustInfo=null;	//AdjustInterestInfo类型
    	Sett_AdjustInterestDao adjustDao=new Sett_AdjustInterestDao();
    	try{
    		adjustInfo=adjustDao.findAdjustByID(lID);
    	}catch(SettlementDAOException e){
    		adjustInfo=null;
    		e.printStackTrace();
    	}catch(ITreasuryDAOException e){
    		adjustInfo=null;
    		e.printStackTrace();
    	}
    	return adjustInfo;
    }
   
	/**
     * 结算模块--累计费用调整信息(调整) ---根据条件查询
     * 
     */
    public Collection findAdjustByCondition(AdjustInterestInfoQuery info) throws SettlementException{
    	Collection coll=null;	//coll:用Vector封装
    	Sett_AdjustInterestDao adjustDao=new Sett_AdjustInterestDao();
    	try{
    		coll=adjustDao.findAdjustByCondition(info);
    	}catch(SettlementDAOException e){
    		coll=null;
    		e.printStackTrace();
    	}catch(ITreasuryDAOException e){
    		coll=null;
    		e.printStackTrace();
    	}
    	return coll;
    }
	
    /**
     * 结算模块--累计费用调整信息(调整) ---添加
     * 
     */
    public long addAdjust(AdjustInterestInfo info) throws SettlementException{
    	long backYN=0;	//-1:失败  其它:添加的记录ID
    	Sett_AdjustInterestDao adjustDao=new Sett_AdjustInterestDao();
    	try{
    		backYN=adjustDao.addAdjust(info);
    	}catch(SettlementDAOException e){
    		backYN=-1;
    		e.printStackTrace();
    		throw new SettlementException();
    	}catch(ITreasuryDAOException e){
    		backYN=-1;
    		e.printStackTrace();
    		throw new SettlementException();
    	}catch(Exception e){
    		backYN=-1;
    		e.printStackTrace();
    		throw new SettlementException();
    	}
    	return backYN;
    }
    
    /**
     * 结算模块--累计费用调整信息(调整) ---删除
     * 
     */
    public long delAdjust(long lID) throws SettlementException{
    	long backYN=0;	//-1:失败  其它:删除的记录ID
    	Sett_AdjustInterestDao adjustDao=new Sett_AdjustInterestDao();
    	try{
    		backYN=adjustDao.delAdjust(lID);
    	}catch(SettlementDAOException e){
    		backYN=-1;
    		e.printStackTrace();
    		throw new SettlementException();
    	}catch(ITreasuryDAOException e){
    		backYN=-1;
    		e.printStackTrace();
    		throw new SettlementException();
    	}
    	return backYN;
    }
 
    /**
     * 结算模块--累计费用调整信息(调整) ---修改和项调整值
     * 
     */
	public long valueAdjust(AdjustInterestInfo adjustInfo) throws SettlementException{
		long backYN=0;	//-1:失败  0:正常
    	Sett_AdjustInterestDao adjustDao=new Sett_AdjustInterestDao();
    	try{
    		backYN=adjustDao.updateAdjustForValue(adjustInfo);
    	}catch(SettlementDAOException e){
    		backYN=-1;
    		e.printStackTrace();
    		throw new SettlementException();
    	}catch(ITreasuryDAOException e){
    		backYN=-1;
    		e.printStackTrace();
    		throw new SettlementException();
    	}
    	return backYN;
    }
	/**
	 * 审批流：审批方法
	 * Added by zwsun, 2007-06-21
	 * @param info
	 * @return long
	 * @throws IRollbackException
	 */
	public long doApproval(AdjustInterestInfo info)throws SettlementException
	{
		long loanId = -1;
		InutParameterInfo returnInfo = new InutParameterInfo();
		Sett_AdjustInterestDao dao = new Sett_AdjustInterestDao();
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
		loanId =info.getLID();
		try
		{
			AdjustInterestInfo loanInfo = new AdjustInterestInfo();
			loanInfo=dao.findAdjustByID(loanId);
			inutParameterInfo.setDataEntity(loanInfo);
			//提交审批
			returnInfo = FSWorkflowManager.doApproval(inutParameterInfo);
			//如果是最后一级,且为审批通过,更新状态为已审批
			if(returnInfo.isLastLevel())
			{	
				dao.upStatus(
					info.getLID(),
					SETTConstant.TransactionStatus.APPROVALED);
				//如果是自动复核
				if(FSWorkflowManager.isAutoCheck())
				{
					AdjustInterestInfo loanInfo1 = new AdjustInterestInfo();
					loanInfo1=dao.findAdjustByID(info.getLID());
					loanInfo1.setLCheckUserID(returnInfo.getUserID());	//最后审批人为复核人				
					
					check(loanInfo1, false);
				}	
			}
			//	如果是最后一级,且为审批拒绝,更新状态为已保存
			else if(returnInfo.isRefuse())
			{
				dao.upStatus(
						info.getLID(),
						SETTConstant.TransactionStatus.SAVE);
			}
		}
		catch (Exception e)
		{
			throw new SettlementException();
		}
		return loanId;
	}	
	/**
	 * 审批流：取消审批方法。如果是自动复核，则取消审批之前必须先取消复核，如果是手动复核，则只需取消审批
	 * 取消复核：调用ejb的取消复核方法
	 * 取消审批：将业务记录状态置为已保存即可
	 * @param info
	 * @return long
	 * @throws IRollbackException
	 */
	public long cancelApproval(AdjustInterestInfo loanInfo)throws SettlementException
	{
		long lReturn = -1;
		Sett_AdjustInterestDao loanDao = new Sett_AdjustInterestDao();		
		InutParameterInfo inutParameterInfo = loanInfo.getInutParameterInfo();	
		
		try
		{
			//如果系统内设定为自动动复核，则需要先取消复核，然后取消审批
			if(FSWorkflowManager.isAutoCheck() && loanInfo.getLStatusID()==SETTConstant.TransactionStatus.CHECK)
			{
				//取消复核
				check(loanInfo, true);
				//取消审批
				lReturn = loanDao.upStatus(loanInfo.getLID(), SETTConstant.TransactionStatus.SAVE);
			}
			else if( !FSWorkflowManager.isAutoCheck() && loanInfo.getLStatusID()==SETTConstant.TransactionStatus.APPROVALED)
			{
				//取消审批
				lReturn = loanDao.upStatus(loanInfo.getLID(), SETTConstant.TransactionStatus.SAVE);
			}
			
			//将审批记录表内的该交易的审批记录状态置为无效
			if(inutParameterInfo.getApprovalEntryID()>0)
			{
				FSWorkflowManager.cancelApprovalRecord(inutParameterInfo);
			}							
		}
		catch (Exception e)
		{
			throw new SettlementException();
		}
		return lReturn;
	}	

    /**
     * 结算模块--累计费用调整信息(复核) 
     * 1.检查记录的输入人与复核人是否为同一个人
     * 2.根据AdjustInterestInfo更新表sett_AdjustInterest有中的nStatus状态为复核
     * 3.同时更新表Sett_SubAccount相应的字段的余额值(根据子账户ID)
     * 4.同时更新表Sett_DailyAccountBlance相应的字段的余额值(根据子账户ID)
     * 
     */
    public long checkAdjust(AdjustInterestInfo adjustInfo) throws SettlementException {
    	long backYN=0;	//-1:失败  0:正常
    	
    	Sett_AdjustInterestDao adjustDao=new Sett_AdjustInterestDao();
    	AccumulateFeeDao accumulateDao=new AccumulateFeeDao();
    	try{
    		AdjustInterestInfo adjustInfoTmp=new AdjustInterestInfo();
    		adjustInfoTmp=adjustDao.findAdjustByID(adjustInfo.getLID());
    		
    		//判断是否自动复核, 如果是则允许复核人与输入人相同 Added  by zwsun, 2007/7/7
    		if(!FSWorkflowManager.isAutoCheck()){	    		   		
	    		//如果复核人与将要复核的记录中的输入人相同，则抛出异常
	    		System.out.println("----输入人的ID号为:"+adjustInfoTmp.getLInputUserID());
	    		System.out.println("----复核人的ID号为:"+adjustInfoTmp.getLCheckUserID());
	    		if(adjustInfo.getLCheckUserID()==adjustInfoTmp.getLInputUserID()){
	    			throw new SettlementException("参考号["+adjustInfo.getLID()+"]记录,复合人不能与操作人相同，请重新复核!",null);
	    		
	    		}
    		}
    		
    		//更新账户每日余额数据	---------------------------
    		if(backYN!=-1)
    			backYN=accumulateDao.updateDailyAccountBlance(adjustInfoTmp);
    		
    		//更新子账户数据		
    		if(backYN!=-1)
    			backYN=accumulateDao.updateSubAccount(adjustInfoTmp);

    		//Added by zwsun, 没有记录的时候不让复核
    		if(backYN==-1 || backYN==-100){
    			throw new SettlementException();
    		}
    		
    		//将更新该记录的状态为复核
    		if(backYN!=-1)
    			backYN=adjustDao.updateAdjustForCheck(adjustInfo);   
    		
    			
    	}catch(SettlementDAOException e){
    		backYN=-1;
    		e.printStackTrace();
    		throw new SettlementException();
    	}catch(ITreasuryDAOException e){
    		backYN=-1;
    		e.printStackTrace();
    		throw new SettlementException();
    	}
    	return backYN;
    }
    /**
     * 结算模块--累计费用调整信息(复核) 
     * Added by zwsun, 2007/7/6
     * @param isCancel 是否为取消复核，true表示取消复核，false表示复核
     */
    public long check(AdjustInterestInfo adjustInfo, boolean isCancel) throws SettlementException {
    	if(isCancel){
    		adjustInfo.setLStatusID(SETTConstant.TransactionStatus.SAVE);
    	}else{
    		adjustInfo.setLStatusID(SETTConstant.TransactionStatus.CHECK);
    	}
    	return checkAdjust(adjustInfo);
    }    
}