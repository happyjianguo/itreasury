package com.iss.itreasury.craftbrother.transferloancontract.transferloancontract.bizlogic;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import com.iss.itreasury.craftbrother.transferloancontract.transferapply.dao.TransferApplyDao;
import com.iss.itreasury.craftbrother.transferloancontract.transferapply.dataentity.TransferApplyInfo;
import com.iss.itreasury.craftbrother.transferloancontract.transfercontract.dao.TransferContractDao;
import com.iss.itreasury.craftbrother.transferloancontract.transfercontract.dataentity.TransferContractInfo;
import com.iss.itreasury.craftbrother.transferloancontract.transferloancontract.dao.TransferloanContractDao;
import com.iss.itreasury.craftbrother.transferloancontract.transferloancontract.dao.TransferloanContractDetailDao;
import com.iss.itreasury.craftbrother.transferloancontract.transferloancontract.dataentity.LoanapplyformInfo;
import com.iss.itreasury.craftbrother.transferloancontract.transferloancontract.dataentity.LoancontractdetailInfo;
import com.iss.itreasury.craftbrother.util.CRAconstant;
import com.iss.itreasury.creditrating.creditrating.dataentity.CreditRatingInfo;
import com.iss.itreasury.creditrating.util.CreRtConstant;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.contract.dao.ContractDao;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.CreateCodeManager;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

public class TransferloanContractBiz 
{

	private Log4j log = new Log4j(Constant.ModuleType.CRAFTBROTHER, this);
	
	
	public String getTransCode(LoanapplyformInfo info) throws Exception 
	{
		String strReturn ="";
		try {		
				HashMap map = new HashMap();
				map.put("officeID",String.valueOf(info.getOfficeid()));
				map.put("currencyID",String.valueOf(info.getCurrencyid()));
				map.put("moduleID",String.valueOf(Constant.ModuleType.CRAFTBROTHER));
				map.put("transTypeID",String.valueOf(Constant.ApprovalAction.CRA_LOANCONTRACT_APPLY));
			    strReturn=CreateCodeManager.createCode(map);	
				
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
			
		}
		return strReturn;
	}
	
	//	翻页查询
	public PageLoader queryTransferApplyInfo(TransferApplyInfo transferApplyInfo) throws Exception
	{
		PageLoader pageLoader = null;
		TransferloanContractDao transferApplyDao = new TransferloanContractDao();
		pageLoader = transferApplyDao.queryTransferApplyInfo(transferApplyInfo);
		return pageLoader;
	}
	public Collection queryLoancontractInfo(LoanapplyformInfo info) throws Exception
	{
		Collection conllenction = null;
		TransferloanContractDao transferApplyDao = new TransferloanContractDao();
		conllenction = transferApplyDao.findContractForAttornment(info);
		return conllenction;
	}
	
	public Collection findContractByIDS(long[] lID) throws java.rmi.RemoteException,IException
	{
		Vector c=new Vector();
		TransferloanContractDao dao = new TransferloanContractDao();
		
		try {
			for ( int i=0;i<lID.length;i++ )
			{
				if (lID[i]>0)
				{
					ContractInfo aInfo=dao.findbyid(lID[i],-1);
					if (aInfo!=null) c.add(aInfo);
				}
			}
		} catch (Exception e) {
			throw new IException("",e);
		}
		return c;		
	}
	
	public long save(LoanapplyformInfo info) throws Exception
	{
		long id=-1;
		
		try 
		{
			Connection  conn = Database.getConnection();
			conn.setAutoCommit(false);
			
			try
			{
				TransferloanContractDao dao = new TransferloanContractDao(conn);
				TransferloanContractDetailDao detaildao = new TransferloanContractDetailDao(conn);
				if(dao.checkdatause(info.getSapplyformid(),info.getId())>0)
				{
					throw new IException("该笔数据已经被处理！");
				}
				
				if(info.getId()<0)
				{
					String tempcode=getTransCode(info);
					info.setSloanapplycode(tempcode);
					id=dao.add(info);
				}
				else
				{
					dao.update(info);
					detaildao.deletedetailinfo(info.getId());
					id=info.getId();
				}
				
				
				for(int i=0;i<info.getInfo().length;i++)
				{
					
					if(info.getInfo()[i]!=null)
					{
						info.getInfo()[i].setSapplyid(id);
						detaildao.add(info.getInfo()[i]);
					}
				}
				if(info.getInutparameterinfo()!=null)
				{
					log.debug("------提交审批--------");	
					InutParameterInfo tempInfo = info.getInutparameterinfo();
					tempInfo.setUrl(tempInfo.getUrl() + id);
					tempInfo.setTransID(String.valueOf(id));// 这里保存的是交易编号
					tempInfo.setDataEntity(info);

					// 提交审批
					FSWorkflowManager.initApproval(info.getInutparameterinfo());
					log.debug("------提交审批成功--------");
				}
				
				conn.commit();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				conn.rollback();
				throw new IException(e.getMessage());
			}
			finally
			{
				if(conn!=null)
				{
					conn.close();
					conn=null;
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new IException(ex.getMessage());
		}
		
		return id;
	}
	
	public LoanapplyformInfo findLoanapplyformInfo(LoanapplyformInfo info)
	{
		Vector c=new Vector();
		Collection coll=null;
		TransferloanContractDao dao = new TransferloanContractDao();
		TransferloanContractDetailDao detaildao= new TransferloanContractDetailDao();
		TransferApplyDao applydao = new TransferApplyDao();
		LoanapplyformInfo returninfo=new LoanapplyformInfo();
		LoancontractdetailInfo detailinfo = new LoancontractdetailInfo();
		TransferApplyInfo applyinfo =new TransferApplyInfo();
		try
		{
			returninfo=(LoanapplyformInfo)dao.findByID(info.getId(), LoanapplyformInfo.class);
			detailinfo.setSapplyid(returninfo.getId());
			detailinfo.setStatusid(CRAconstant.TransactionType.counterpartBank.VALID);
			coll=detaildao.findLoancontractDetail(detailinfo);
			
			
			applyinfo=(TransferApplyInfo)applydao.findByID(returninfo.getSapplyformid(), TransferApplyInfo.class);
		
			returninfo.setApplyinfo(applyinfo);
			LoancontractdetailInfo[] detailinfos= new LoancontractdetailInfo[coll.size()];
			for(int i=0;i<coll.size();i++)
			{
				detailinfos[i]=(LoancontractdetailInfo)coll.toArray()[i];
				
			}
			returninfo.setInfo(detailinfos);
		
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
		}
		return returninfo;
	}
	
	public ContractInfo findContractByIDS(long lID) throws java.rmi.RemoteException,IException
	{
		ContractInfo aInfo = new ContractInfo();
		TransferloanContractDao dao = new TransferloanContractDao();
		
		try {
			
			 aInfo=dao.findbyid(lID,-1);
					
		} catch (Exception e) {
			throw new IException("",e);
		}
		return aInfo;		
	}
	
	public LoanapplyformInfo findInfoforwork(LoanapplyformInfo info)
	{
		LoanapplyformInfo loanapplyforminfo=new LoanapplyformInfo();
		LoancontractdetailInfo detailinfo=new LoancontractdetailInfo();
		LoancontractdetailInfo[] detailinfos=null;
		TransferApplyInfo applyinfo = new TransferApplyInfo();
		Collection coll=null;
		ContractInfo contractinfo=null;
		TransferloanContractDao dao=new TransferloanContractDao();
		TransferloanContractDetailDao detaildao=new TransferloanContractDetailDao();
		TransferApplyDao applydao=new TransferApplyDao();
		detailinfo.setSapplyid(info.getId());
		detailinfo.setStatusid(CRAconstant.TransactionType.counterpartBank.VALID);
		
		try 
		{
			loanapplyforminfo=(LoanapplyformInfo) dao.findByID(info.getId(), LoanapplyformInfo.class);
			applyinfo=(TransferApplyInfo)applydao.findByID(loanapplyforminfo.getSapplyformid(), TransferApplyInfo.class);
			
			coll=detaildao.findLoancontractDetail(detailinfo);
			detailinfos=new LoancontractdetailInfo[coll.size()];
			for(int i=0;i<coll.size();i++)
			{
				detailinfos[i]=(LoancontractdetailInfo) coll.toArray()[i];
				try {
					contractinfo=dao.findbyid(detailinfos[i].getLoannoteid(), detailinfos[i].getId());
				} catch (IException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				detailinfos[i].setContractinfo(contractinfo);
			}
			loanapplyforminfo.setInfo(detailinfos);
			loanapplyforminfo.setApplyinfo(applyinfo);
			
		} 
		catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loanapplyforminfo;
	}
	
	/**
	 * 审批
	 * @param info
	 * @return
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public long doApproval(LoanapplyformInfo info) throws Exception
	{
		long lReturn = -1;
		TransferloanContractDao dao = new TransferloanContractDao();
		InutParameterInfo returnInfo = new InutParameterInfo();
		InutParameterInfo inutParameterInfo = info.getInutparameterinfo();
		try {
			
			LoanapplyformInfo depositInfo = new LoanapplyformInfo();
			depositInfo = (LoanapplyformInfo)dao.findByID(info.getId(), LoanapplyformInfo.class);
			inutParameterInfo.setDataEntity(depositInfo);
			// 提交审批
			returnInfo = FSWorkflowManager.doApproval(inutParameterInfo);
			//如果是最后一级,且为审批通过,更新状态为已审批
			if (returnInfo.isLastLevel()) {
					depositInfo.setStatusid(CreRtConstant.CreRtStatus.APPROVALED);
					dao.update(depositInfo);
				}
			
			// 如果是最后一级,且为审批拒绝,更新状态为已保存
			else if (returnInfo.isRefuse()) {
				depositInfo.setStatusid(CreRtConstant.CreRtStatus.SAVE);
				dao.update(depositInfo);
			}
			lReturn = depositInfo.getId();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("审批失败！");
		}
		return lReturn;
	}
	
	
	public long cancelApproval(LoanapplyformInfo info) throws Exception 
	{
		long lReturn = -1;
		TransferloanContractDao dao = new TransferloanContractDao();
		InutParameterInfo inutParameterInfo = info.getInutparameterinfo();
		
		if(dao.checkloancontract(info.getId())>0)
		{
			throw new IException("该申请已经生成转让合同，不能取消审批！");
		}
		
		try {
			LoanapplyformInfo depositInfo = new LoanapplyformInfo();
			  depositInfo = (LoanapplyformInfo)dao.findByID(info.getId(), LoanapplyformInfo.class);
			  depositInfo.setStatusid(CreRtConstant.CreRtStatus.SAVE);
			  dao.update(depositInfo);
			//将审批记录表内的该交易的审批记录状态置为无效
			if (inutParameterInfo.getApprovalEntryID() > 0) {
				FSWorkflowManager.cancelApprovalRecord(inutParameterInfo);
			}
			lReturn = depositInfo.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("取消审批失败！");
		}
		return lReturn;
	}
	
	public void delete(LoanapplyformInfo info)
	{
		
		try 
		{
			Connection  conn = Database.getConnection();
			conn.setAutoCommit(false);
				try
				{
			
					TransferloanContractDao dao = new TransferloanContractDao();
					TransferloanContractDetailDao detaildao = new TransferloanContractDetailDao(conn);
					
					dao.update(info);
					
					detaildao.deletedetailinfo(info.getId());
					conn.commit();
				}
				catch (Exception e)
				{
					e.printStackTrace();
					conn.rollback();
				}
				finally
				{
					conn.close();
					conn=null;
				}
		}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
	/**
	 * 说明：查询贷款合同信息
	 * 作者:minzhao
	 * 日期:Aug 7, 2009
	 * 参数:@param loanapplyforminfo
	 * 参数:@return
	 * 返回类型:PageLoader
	 */
	public PageLoader queryLoanApplyform(LoanapplyformInfo loanapplyforminfo)
	{
		PageLoader pageLoader = null;
		TransferloanContractDao loancontractdao = new TransferloanContractDao();
		pageLoader = loancontractdao.queryLoanApplyform(loanapplyforminfo);
		return pageLoader;
	}

}
