package com.iss.itreasury.craftbrother.transferloancontract.transferapply.bizlogic;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.ejb.SessionContext;

import com.iss.itreasury.craftbrother.transferloancontract.transferapply.dao.TransferApplyDao;
import com.iss.itreasury.craftbrother.transferloancontract.transferapply.dataentity.TransferApplyInfo;
import com.iss.itreasury.craftbrother.util.CRAconstant;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

public class TransferApplyBiz {
	
	private Log4j log = new Log4j(Constant.ModuleType.CREDITRATING, this);
	private SessionContext context;
	//	翻页查询
	public PageLoader queryTransferApplyInfo(TransferApplyInfo transferApplyInfo) throws Exception
	{
		PageLoader pageLoader = null;
		TransferApplyDao transferApplyDao = new TransferApplyDao();
		pageLoader = transferApplyDao.queryTransferApplyInfo(transferApplyInfo);
		return pageLoader;
	}
	
	public long add(TransferApplyInfo transferApplyInfo) throws ITreasuryDAOException,IException
	{
		long lReturn = -1;
		
		try {
			Connection  conn = Database.getConnection();
			try
			{
				TransferApplyDao transferApplyDao=new TransferApplyDao("CRA_TRANSFERAPPLYFORM","SEQ_CRA_TRANSFERAPPLYFORM",conn);
				transferApplyInfo.setSapplyCode(applyCode(transferApplyInfo,conn));//设置编号
				lReturn = transferApplyDao.add(transferApplyInfo);//插入数据库
				
				/**
				 * 如果Info中的InutParameterInfo不为空,则需要提交审批 add by 刘琰 2007-04-17
				 */
				if (transferApplyInfo.getInutParameterInfo() != null) {
					log.debug("------提交审批--------");	
					InutParameterInfo tempInfo = transferApplyInfo.getInutParameterInfo();
					tempInfo.setUrl(tempInfo.getUrl() + lReturn);
					tempInfo.setTransID(String.valueOf(lReturn));// 这里保存的是交易编号
					tempInfo.setDataEntity(transferApplyInfo);

					// 提交审批
					FSWorkflowManager.initApproval(tempInfo);
					log.debug("------提交审批成功--------");
				}
				
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IException(e.getMessage());
			}
			finally
			{
				conn.close();
				conn=null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		return lReturn;
	}
	
	public long update(TransferApplyInfo transferApplyInfo ) throws ITreasuryDAOException,IException
	{
		long lReturn = -1;
		
		try {
			Connection  conn = Database.getConnection();
			try
			{
				TransferApplyDao transferApplyDao=new TransferApplyDao("CRA_TRANSFERAPPLYFORM","SEQ_CRA_TRANSFERAPPLYFORM",conn);
				
				transferApplyDao.update(transferApplyInfo);//更新数据库
				
				/**
				 * 如果Info中的InutParameterInfo不为空,则需要提交审批 add by 刘琰 2007-04-17
				 */
				if (transferApplyInfo.getInutParameterInfo() != null) {
					log.debug("------提交审批--------");	
					InutParameterInfo tempInfo = transferApplyInfo.getInutParameterInfo();
					tempInfo.setUrl(tempInfo.getUrl() + transferApplyInfo.getId());
					tempInfo.setTransID(String.valueOf(transferApplyInfo.getId()));// 这里保存的是交易编号
					tempInfo.setDataEntity(transferApplyInfo);

					// 提交审批
					FSWorkflowManager.initApproval(tempInfo);
					log.debug("------提交审批成功--------");
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IException(e.getMessage());
			}
			finally
			{
				conn.close();
				conn=null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		return lReturn;
	}
	
	public long delete(TransferApplyInfo transferApplyInfo) throws ITreasuryDAOException,IException
	{
		long lReturn = -1;
		
		try {
			Connection  conn = Database.getConnection();
			try
			{	
				if(transferApplyInfo.getTransTypeId()==CRAconstant.CraTransactionType.AVERAGE_NOTIFY ||
						transferApplyInfo.getTransTypeId()==CRAconstant.CraTransactionType.BREAK_AVERAGE )
				{
					if(checkContractForm(transferApplyInfo.getId())>0)
					{
						lReturn = -3; 
					}else{
						
						TransferApplyDao transferApplyDao=new TransferApplyDao("CRA_TRANSFERAPPLYFORM","SEQ_CRA_TRANSFERAPPLYFORM",conn);
						transferApplyDao.update(transferApplyInfo);
						lReturn = 1;
					}
				}else if(transferApplyInfo.getTransTypeId()==CRAconstant.CraTransactionType.BREAK_NOTIFY||
						transferApplyInfo.getTransTypeId()==CRAconstant.CraTransactionType.REPURCHASE_NOTIFY )
				{
					if(checkApplyForm(transferApplyInfo.getId())>0)
					{
						lReturn = -2; 
					}else{
						
						TransferApplyDao transferApplyDao=new TransferApplyDao("CRA_TRANSFERAPPLYFORM","SEQ_CRA_TRANSFERAPPLYFORM",conn);
						transferApplyDao.update(transferApplyInfo);
						lReturn = 1;
					}
				}
				
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IException(e.getMessage());
			}
			finally
			{
				conn.close();
				conn=null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		return lReturn;
	}
	
	public long examinePass(TransferApplyInfo transferApplyInfo) throws RemoteException, IRollbackException
	{
	   	 
		long lReturnId = -1;
		try
		{
			TransferApplyDao transferApplyDao = new TransferApplyDao();

			InutParameterInfo inutParameterInfo = transferApplyInfo.getInutParameterInfo();
			InutParameterInfo returnInfo = new InutParameterInfo();
	   				
	   		//将业务记录置入pinfo,转换成标准map传递到审批流引擎
	   		inutParameterInfo.setDataEntity(transferApplyInfo);
	   				
	   		//提交审批
	   		returnInfo = FSWorkflowManager.doApproval(inutParameterInfo);
	   				
	   		//如果是最后一级,且为审批通过,更新状态为已审批
	   		if(returnInfo.isLastLevel())
	   		{	
	   			transferApplyInfo.setStatusId(CRAconstant.TransactionStatus.APPROVALED);
	   			transferApplyDao.updateApplyStatus(transferApplyInfo);
	   		}
	   		//如果是最后一级,且为审批拒绝,更新状态为已保存
	   		else if(returnInfo.isRefuse())
	   		{
	   			transferApplyInfo.setStatusId(CRAconstant.TransactionStatus.SAVE);
	   			transferApplyDao.updateApplyStatus(transferApplyInfo);	
	   		}
		}
	   	catch(Exception e){
	   		throw new IRollbackException(context, e.getMessage(), e); 
	   	}
	   	return lReturnId;
	}
	
	public long cancelApproval(TransferApplyInfo transferApplyInfo)throws java.rmi.RemoteException, SecuritiesException,SQLException
	{
		long lReturn = -1;
		InutParameterInfo inutParameterInfo = transferApplyInfo.getInutParameterInfo();
		
		TransferApplyDao transferApplyDao = new TransferApplyDao();
		
		long ifUsed= -1;
		//买入型，检查是否有使用该申请的合同
		if(transferApplyInfo.getTransTypeId()==CRAconstant.CraTransactionType.AVERAGE_NOTIFY ||
				transferApplyInfo.getTransTypeId()==CRAconstant.CraTransactionType.BREAK_AVERAGE )
		{
			try
			{
				ifUsed=checkContractForm(transferApplyInfo.getId());
			}catch (Exception e) 
			{
				throw new SecuritiesException();
			}
			if(ifUsed>0)
			{
				throw new SecuritiesException("请先取消合同,再取消申请!",null);
			}
		}
		//卖出型，检查是否有使用该申请的合同申请
		if(transferApplyInfo.getTransTypeId()==CRAconstant.CraTransactionType.BREAK_NOTIFY||
				transferApplyInfo.getTransTypeId()==CRAconstant.CraTransactionType.REPURCHASE_NOTIFY)
		{
			try
			{
				ifUsed=checkApplyForm(transferApplyInfo.getId());
			}catch (Exception e) 
			{
				throw new SecuritiesException();
			}
			if(ifUsed>0)
			{
				throw new SecuritiesException("请先取消贷款资产转让申请,再取消申请!",null);
			}
		}
		
		try
		{
			
		    transferApplyInfo.setStatusId(CRAconstant.TransactionStatus.SAVE);
			//取消审批
			lReturn = transferApplyDao.updateApplyStatus(transferApplyInfo);
			
			if(lReturn > 0){
				//将审批记录表内的该交易的审批记录状态置为无效
				if(inutParameterInfo.getApprovalEntryID()>0)
				{
					FSWorkflowManager.cancelApprovalRecord(inutParameterInfo);
				}
			}
		
		}
		catch (Exception e)
		{
			throw new SecuritiesException();
		}
		return lReturn;
	}
	
	private long checkApplyForm(long id) throws SQLException
	{
		TransferApplyDao transferApplyDao = new TransferApplyDao();
		return transferApplyDao.checkApplyForm(id);
	}
	private long checkContractForm(long id) throws SQLException
	{
		TransferApplyDao transferApplyDao = new TransferApplyDao();
		return transferApplyDao.checkContractForm(id);
	}
	
	public String applyCode(TransferApplyInfo transferApplyInfo,Connection conn)throws Exception
	{
		String applyCode = "";
		TransferApplyDao transferApplyDao = new TransferApplyDao();
		applyCode = transferApplyDao.applyCode(transferApplyInfo,conn);
		return applyCode;
	}
	
	public TransferApplyInfo findTransferApplyInfoById(long id)throws ITreasuryDAOException
	{
		TransferApplyInfo transferApplyInfo = new TransferApplyInfo();
		return (TransferApplyInfo) (new TransferApplyDao()).findByID(id,transferApplyInfo.getClass());
	}
}
