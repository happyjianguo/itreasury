package com.iss.itreasury.craftbrother.credit.bizlogic;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.craftbrother.credit.dao.CreditApprovalDAO;
import com.iss.itreasury.craftbrother.credit.dao.CreditSettingDAO;
import com.iss.itreasury.craftbrother.credit.dataentity.CreditQueryInfo;
import com.iss.itreasury.craftbrother.credit.dataentity.CreditSettingInfo;
import com.iss.itreasury.craftbrother.util.CRANameRef;
import com.iss.itreasury.craftbrother.util.CRAconstant;
import com.iss.itreasury.securities.apply.dao.SEC_ApplyDAO;
import com.iss.itreasury.securities.apply.dataentity.ApplyInfo;
import com.iss.itreasury.securities.approvaltran.dao.Sec_ApprovalTranDao;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.UtilOperation;

public class CreditSettingBiz {
	private static Log4j log4j = null;

	public CreditSettingBiz()
	{
		log4j = new Log4j(Constant.ModuleType.CRAFTBROTHER, this);
	}
	public long saveCredit(CreditSettingInfo creditInfo) throws Exception
	{
		long lReturn=-1;
		CreditSettingDAO creditDao=new CreditSettingDAO();
		if(creditInfo.getID()>0)
		{
			lReturn = creditDao.updateCredit(creditInfo);
		}else
		{
		    lReturn = creditDao.saveCredit(creditInfo);
		}
		//保存并提交审批
		if(creditInfo.getInutParameterInfo()!=null)
		{
			log4j.debug("------提交审批--------");
			//设置返回的地址链接(交易id只能在交易保存之后加上,tempInfo.getUrl()得到的url没有具体的交易id)
			InutParameterInfo tempInfo = creditInfo.getInutParameterInfo();
			tempInfo.setUrl(tempInfo.getUrl()+lReturn);
			String transNo=UtilOperation.getNewTransactionNo(tempInfo,lReturn);
			tempInfo.setTransID(transNo);
			tempInfo.setDataEntity(creditInfo);
			
			//提交审批
			FSWorkflowManager.initApproval(tempInfo);
			//更新状态到审批中		
			creditDao.updateStatus(lReturn, CRAconstant.TransactionStatus.APPROVALING);
			log4j.debug("------提交审批成功--------");
		}		
		return lReturn;
	}
	/**
	 *申请书的审批操作
	*/
	public long doApproval(CreditSettingInfo info) throws Exception{
		long creditId = -1;
		InutParameterInfo returnInfo = new InutParameterInfo();
		CreditSettingDAO creditDao=new CreditSettingDAO();
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
		creditId =info.getID();
		//读配置文件，是否检查当同行对财务公司授信, 该交易对手对所有成员单位关于此种交易类型的授信额度总和
/*		if(Config.getBoolean(ConfigConstant.CRAFTBROTHER_CREDIT_ISALLMEMBER, true)){
			//检查当同行对财务公司授信, 该交易对手对所有成员单位关于此种交易类型的授信额度总和
			if(info.getCreditDirection() == 2){
				if(info.getTransactionType() == CRAconstant.TransactionType.REPURCHASE_NOTIFY || info.getTransactionType() == CRAconstant.TransactionType.BREAK_NOTIFY){
					long financeID = CRANameRef.getPartenerInfo(info.getOfficeID()).getClientID();
					if(info.getCreditedClientID() == financeID){
						String error = creditDao.checkRepurchaseCredit(creditId);
						if(!error.equals("")){
							throw new IException(error);
						}
					}
				}
			}
		}
		*/
		inutParameterInfo.setDataEntity(info);
		//提交审批
		returnInfo = FSWorkflowManager.doApproval(inutParameterInfo);
		//如果是最后一级,且为审批通过,更新状态为已审批
		if(returnInfo.isLastLevel())
		{	
			creditDao.updateStatus(creditId, CRAconstant.TransactionStatus.APPROVALED);
			creditDao.updateAllStatus(creditId, CRAconstant.TransactionStatus.APPROVALED, CRAconstant.TransactionStatus.SAVE);
		}
		//	如果是最后一级,且为审批拒绝,更新状态为已保存
		else if(returnInfo.isRefuse())
		{
			creditDao.updateStatus(creditId, CRAconstant.TransactionStatus.SAVE);
			creditDao.updateAllStatus(creditId, CRAconstant.TransactionStatus.SAVE, CRAconstant.TransactionStatus.APPROVALED);
		}

		return creditId;		
	}
	/**
	 *申请书的取消审批操作
	*/
	public long cancelApproval(CreditSettingInfo info) throws Exception{
		CreditSettingDAO creditDao=new CreditSettingDAO();	
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();	

		if(info.getStatusID() == CRAconstant.TransactionStatus.APPROVALED){
			creditDao.updateStatus(info.getID(), CRAconstant.TransactionStatus.SAVE);
			creditDao.updateAllStatus(info.getID(), CRAconstant.TransactionStatus.SAVE, CRAconstant.TransactionStatus.APPROVALED);
			
			if(inutParameterInfo.getApprovalEntryID()>0){
				FSWorkflowManager.cancelApprovalRecord(inutParameterInfo);
			}
			return info.getID();	
		}
		else{
			return -1;
		}	
	}
	
	public CreditSettingInfo findCreditByID(long lID) throws IException, SQLException
	{
		CreditSettingDAO creditDao=new CreditSettingDAO();
		CreditSettingInfo creditInfo=creditDao.findCreditByID(lID);
		return creditInfo;
	}
	public long updateStatus(long lID,long statusID) throws Exception
	{
		CreditSettingDAO creditDao=new CreditSettingDAO();
		long lReturn = -1;
		lReturn = creditDao.updateStatus(lID,statusID);
		return lReturn;
	}
	public Collection findCreditByCondition(CreditQueryInfo queryInfo) throws Exception
	{
		CreditSettingDAO creditDao=new CreditSettingDAO();
		Collection coll = null;
		coll = creditDao.findCreditByCondition(queryInfo);
		return coll;
	}
	public Collection  findApprovalCreditByCondition(CreditQueryInfo info,long userID) throws IException, SQLException
	{
		CreditApprovalDAO creditApprovalDao=new CreditApprovalDAO();
		Collection coll = null;
		coll = creditApprovalDao.findApprovalCreditByCondition(info,userID);
		return coll;
	}
	public long updateDataStatusID(ApprovalTracingInfo info) throws Exception{
		   long lretu=-1;
		   try{
			   CreditApprovalDAO dao = new CreditApprovalDAO();
			   lretu=dao.updateDataStatusID(info);		   
		   }catch(Exception e)
		   {
			   e.printStackTrace();
		       throw e;
		   }
		   return lretu;
	}
	
	public long showApprovalUserList(
	        JspWriter out, 
	        String strFieldName,
	        String strNextFieldName, 
	        long lModuleID,
			long lSubLoanTypeID, 
			double amount,
			long lOfficeID, 
			long lCurrencyID,
			long lUserID,
			long dataStatusID,
			long ActionID
		) throws Exception
		{
		    long lretu=-1;
		    CreditApprovalDAO dao = new CreditApprovalDAO();
		    lretu = dao.showApprovalUserList(out,strFieldName, strNextFieldName,lModuleID,
            lSubLoanTypeID, amount,lOfficeID,lCurrencyID,lUserID,dataStatusID,ActionID);
	        return lretu;
		}
}