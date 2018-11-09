package com.iss.itreasury.craftbrother.transferloancontract.transfercontract.bizlogic;


import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.HashMap;

import com.iss.itreasury.craftbrother.transferloancontract.counterparty.dao.CounterparBankDao;
import com.iss.itreasury.craftbrother.transferloancontract.counterparty.dao.CounterpartDao;
import com.iss.itreasury.craftbrother.transferloancontract.counterparty.dataentity.CounterpartBankInfo;
import com.iss.itreasury.craftbrother.transferloancontract.counterparty.dataentity.CounterpartInfo;
import com.iss.itreasury.craftbrother.transferloancontract.transferapply.dao.TransferApplyDao;
import com.iss.itreasury.craftbrother.transferloancontract.transferapply.dataentity.TransferApplyInfo;
import com.iss.itreasury.craftbrother.transferloancontract.transfercontract.dao.ContractDetailDao;

import java.util.ArrayList;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.craftbrother.transferloancontract.transfercontract.dataentity.TransferAccountInfo;
import com.iss.itreasury.craftbrother.transferloancontract.transfercontract.dataentity.TransferContractInfo;

import com.iss.itreasury.craftbrother.transferloancontract.transfercontract.dao.TransferContractDao;

import com.iss.itreasury.craftbrother.transferloancontract.transfercontract.dataentity.ContractdetailInfo;

import com.iss.itreasury.craftbrother.util.CRAconstant;

import com.iss.itreasury.craftbrother.transferloancontract.transferloancontract.dao.TransferloanContractDao;
import com.iss.itreasury.craftbrother.transferloancontract.transferloancontract.dataentity.LoanapplyformInfo;
import com.iss.itreasury.craftbrother.transferloancontract.transferloancontract.dataentity.LoancontractdetailInfo;
import com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dao.TransferNoticeDao;
import com.iss.itreasury.creditrating.util.CreRtConstant;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.settlement.transferloancontract.dao.TransferLoanAmountDetailDao;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.CreateCodeManager;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

public class TransferContractBiz {
	
	private Log4j log = new Log4j(Constant.ModuleType.CRAFTBROTHER, this);
	//�������
	public String getTransCode(TransferContractInfo info) throws Exception 
	{
		String strReturn ="";
		try {		
				HashMap map = new HashMap();
				map.put("officeID",String.valueOf(info.getOfficeId()));
				map.put("currencyID",String.valueOf(info.getCurrencyId()));
				map.put("moduleID",String.valueOf(Constant.ModuleType.CRAFTBROTHER));
				map.put("transTypeID",String.valueOf(Constant.ApprovalAction.CRA_TRANSLOANCONTRACT));
			    strReturn=CreateCodeManager.createCode(map);	
				
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
			
		}
		return strReturn;
	}
	
	
	//	��ҳ��ѯ
	public PageLoader queryTransferContractInfo(TransferContractInfo transferContractInfo) throws Exception
	{
		PageLoader pageLoader = null;
		TransferContractDao transferContractDao = new TransferContractDao();
		pageLoader = transferContractDao.queryTransferContractInfo(transferContractInfo);
		return pageLoader;
	}
	
	public long activeContract(String ids) throws ITreasuryDAOException
	{
		TransferContractDao transferContractDao = new TransferContractDao();
		long activeResult = transferContractDao.activeContract(ids);
		return activeResult;
	}
	
	//�������˻�
	public void createTransferAccount(String ids) throws ITreasuryDAOException
	{
		try{
			TransferContractDao transferContractDao = new TransferContractDao();
			
			//����id��ȡ�輤��ĺ�ͬ����
			ArrayList colResult = (ArrayList)transferContractDao.findInfosByIds(ids);
			for(int i=0 ; i<colResult.size() ; i++)
			{
				TransferContractInfo transferContractInfo = null;
				TransferAccountInfo transferAccountInfo = new TransferAccountInfo();
				TransferAccountBiz transferAccountBiz = new TransferAccountBiz();
				
				//�������ÿ����¼ת��ΪTransferContractInfo����,��ȡ�������һ����¼
				transferContractInfo = (TransferContractInfo)colResult.get(i);
				
				//���������Ը���info
				transferAccountInfo.setOfficeId(transferContractInfo.getOfficeId());
				transferAccountInfo.setCurrencyId(transferContractInfo.getCurrencyId());
				transferAccountInfo.setZcontractid(transferContractInfo.getId());
				//transferAccountInfo.setZamount(transferContractInfo.getAmount());
				transferAccountInfo.setStatusid(Constant.RecordStatus.INVALID);
				
				//�����ع���ֻ��ԡ����׶��֡�����һ�����˻�
				if(transferContractInfo.getTranstypeId() == CRAconstant.CraTransactionType.REPURCHASE_NOTIFY  )
				{
					transferAccountInfo.setTranstypeid(transferContractInfo.getTranstypeId());
					transferAccountInfo.setOperationtypeid(CRAconstant.operationType.CONTERPART);
					transferAccountBiz.add(transferAccountInfo);
				}
				//�������ϣ���ԡ����׶��֡�����һ�����˻���ͬʱ���ÿ������Ա��λ��������һ�����˻�
				if(transferContractInfo.getTranstypeId() == CRAconstant.CraTransactionType.BREAK_NOTIFY  )
				{
					//��ԡ����׶��֡�����һ�����˻�
					transferAccountInfo.setTranstypeid(transferContractInfo.getTranstypeId());
					transferAccountInfo.setOperationtypeid(CRAconstant.operationType.CONTERPART);
					transferAccountBiz.add(transferAccountInfo);
					
					//���ÿ������Ա��λ��������һ�����˻�
					//ͨ����ͬ�����id������ͬ��ϸ���У�����ſ�֪ͨ��id����
					ArrayList loannoteids = (ArrayList)transferContractDao.findLoanNoteIdsByInfo(transferContractInfo.getId());
					//��ÿ��֪ͨ��id��һ����¼
					for(int j=0 ; j<loannoteids.size() ; j++)
					{
						long loannoteid = Long.parseLong(loannoteids.get(j).toString());
						transferAccountInfo.setOperationtypeid(CRAconstant.operationType.CLIENT);
						transferAccountInfo.setLoannoteid(loannoteid);
						transferAccountBiz.add(transferAccountInfo);
					}	
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * ����ID��ѯ�Ŵ�ת�ú�ͬ
	 * @param tracontractId
	 * @return
	 * @throws IException
	 */
	public TransferContractInfo findInfoById(long tracontractId) throws Exception
	{
		TransferContractInfo contractInfo = null;
		
		ContractDetailDao contractdetaildao = new ContractDetailDao();
		TransferloanContractDao transferloancontractdao = new TransferloanContractDao();
		TransferLoanAmountDetailDao detaildao= new TransferLoanAmountDetailDao();
		
		TransferNoticeDao noticedao=new TransferNoticeDao();
		
		ContractdetailInfo[] contractdetailinfos=null;
		ContractdetailInfo detailinfo=new ContractdetailInfo();
		ContractInfo contractinfo=null;
		detailinfo.setTransfercontractformid(tracontractId);
		try{
			TransferContractDao transferContractDao = new TransferContractDao();
			
			contractInfo = (TransferContractInfo)transferContractDao.findByID(tracontractId, TransferContractInfo.class);
			contractInfo.setSellamount(noticedao.searchSellAmount(tracontractId));
			contractdetailinfos=contractdetaildao.findContractdetailInfo(detailinfo);
			for(int i=0;i<contractdetailinfos.length;i++)
			{
				contractinfo=transferloancontractdao.findGuoDianByID(contractdetailinfos[i].getLoancontractid(), contractdetailinfos[i].getLoannoteid());
				contractdetailinfos[i].setSellamount(detaildao.searchSellAmountByContractid(contractdetailinfos[i].getLoannoteid(), contractdetailinfos[i].getId()));
				contractdetailinfos[i].setContractinfo(contractinfo);
			}
			contractInfo.setContractdetailinfo(contractdetailinfos);
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return contractInfo;
	}
	
	/**
	 * ����ID��ѯ�Ŵ�ת�ú�ͬ(�Ǵ��ղ�ѯת�ú�ͬ)
	 * @param tracontractId
	 * @return
	 * @throws IException
	 * @author liangli
	 */
	public TransferContractInfo findContractInfoById(long tracontractId) throws Exception
	{
		TransferContractInfo info = new TransferContractInfo();
		TransferContractDao transferContractDao = new TransferContractDao();
		try{
			info = (TransferContractInfo)transferContractDao.findContractInfoById(tracontractId);
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return info;
	}
	
	/**
	 * ��ѯ�Ŵ��ʲ�ת�ú�ͬ(�򵥲�ѯ)
	 * 
	 * @param contractID
	 * @return
	 * @throws Exception
	 */
	public TransferContractInfo findTransferContractInfoID(long contractID) throws IException
	{
		TransferContractDao contractDAO = new TransferContractDao();
		return contractDAO.findTransferContractInfoByID(contractID);
	}
	
	
	public long save(TransferContractInfo info) throws IException
	{
		long id=-1;
		
		try
		{
			Connection conn=Database.getConnection();
			conn.setAutoCommit(false);
			try
			{
				TransferContractDao transfercontractdao=new TransferContractDao(conn);
				ContractDetailDao contractdetaildao=new ContractDetailDao(conn);
				//�жϴ����ͬ�����Ƿ�ʹ����
				if(info.getLoanApplyId()>0&&transfercontractdao.checkLoanapplyUsed(info)>0)
				{
					throw new IException("�ñ������Ѿ���������������ת�ú�ͬ��");
				}
				
				
				if(transfercontractdao.checkcontractcode(info)>0)
				{
					throw new IException("�ú�ͬ����Ѿ���ʹ�ã�");
				}
				if(info.getId()>0)
				{
					update(info);
					id=info.getId();
				}
				else
				{
					info.setContractCode(getTransCode(info));
					
					id=transfercontractdao.add(info);
					
					for(int i=0;i<info.getContractdetailinfo().length;i++)
					{
						if(info.getContractdetailinfo()[i].getTransferamount()!=0)
						{
							info.getContractdetailinfo()[i].setTransfercontractformid(id);
							contractdetaildao.add(info.getContractdetailinfo()[i]);
						}
					}
				}
				
				
				if(info.getInutparameterinfo()!=null)
				{
					log.debug("------�ύ����--------");	
					InutParameterInfo tempInfo = info.getInutparameterinfo();
					tempInfo.setUrl(tempInfo.getUrl() + id);
					tempInfo.setTransID(String.valueOf(id));// ���ﱣ����ǽ��ױ��
					tempInfo.setDataEntity(info);

					// �ύ����
					FSWorkflowManager.initApproval(tempInfo);
					log.debug("------�ύ�����ɹ�--------");
				}
				
				conn.commit();
				
			}
			catch(Exception e)
			{
				conn.rollback();
				id=-1;
				e.printStackTrace();
				throw new IException(e.getMessage());
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
			throw new IException(e.getMessage());
		}
		
		
		return id;
	}
	
	public LoanapplyformInfo findInfoforwork(TransferContractInfo info) throws IException
	{
		LoanapplyformInfo returninfo=new LoanapplyformInfo();//�ʲ�ת������
		TransferContractInfo tempinfo=new TransferContractInfo();//�ʲ�ת�ú�ͬ
		ContractdetailInfo detailinfo=new ContractdetailInfo();//�ʲ�ת�ú�ͬ��ϸ
		TransferApplyInfo transferapplyinfo=new TransferApplyInfo();//����
		CounterpartInfo counterpartinfo=new CounterpartInfo();
		CounterpartBankInfo counterpartbankinfo= new CounterpartBankInfo();
		
		LoancontractdetailInfo[] loancontractdetailinfo = null;
		ContractdetailInfo[] detailinfos=null;
		ContractInfo contractinfo=null;
		detailinfo.setTransfercontractformid(info.getId());
		
		try
		{
			TransferContractDao dao=new TransferContractDao();//�ʲ�ת�ú�ͬ
			ContractDetailDao detaildao=new ContractDetailDao();//�ʲ�ת�ú�ͬ��ϸ
			TransferloanContractDao  loancontractdao=new TransferloanContractDao();//�ʲ�ת������
			TransferApplyDao transferapplydao=new TransferApplyDao();//����
			CounterpartDao counterpartdao=new CounterpartDao();
			CounterparBankDao counterparbankdao =new CounterparBankDao();
			
			
			tempinfo=(TransferContractInfo)dao.findByID(info.getId(), TransferContractInfo.class);
			
			counterpartbankinfo=(CounterpartBankInfo)counterparbankdao.findByID(tempinfo.getCounterpartbankid(), CounterpartBankInfo.class);
			tempinfo.setCounterpartbankinfo(counterpartbankinfo);
			
			
			counterpartinfo=(CounterpartInfo)counterpartdao.findByID(tempinfo.getCounterPartId(), CounterpartInfo.class);
			detailinfos=detaildao.findContractdetailInfo(detailinfo);
			returninfo=(LoanapplyformInfo)loancontractdao.findByID(tempinfo.getLoanApplyId(), LoanapplyformInfo.class);
			//���׶�����Ϣ��ת�ú�ͬ��ȡ
			returninfo.setTranstypeid(tempinfo.getTranstypeId());
			returninfo.setStatusid(tempinfo.getStatusId());
			transferapplyinfo=(TransferApplyInfo)transferapplydao.findByID(returninfo.getSapplyformid(), TransferApplyInfo.class);
			//������Ϣ��ת�ú�ͬ��ȡ
			transferapplyinfo.setZstartDate(tempinfo.getZstartDate());
			transferapplyinfo.setZendDate(tempinfo.getZendDate());
			transferapplyinfo.setCounterPartId(tempinfo.getCounterPartId());
			transferapplyinfo.setCounterPartCode(counterpartinfo.getCounterpartcode());
			transferapplyinfo.setCounterPartName(counterpartinfo.getCounterpartname());
			returninfo.setApplyinfo(transferapplyinfo);
			
			loancontractdetailinfo=new LoancontractdetailInfo[detailinfos.length];
			for(int i=0;i<detailinfos.length;i++)
			{
				LoancontractdetailInfo loandinfo=new LoancontractdetailInfo();
				loandinfo.setOfficeid(detailinfos[i].getOfficeid());
				loandinfo.setCurrencyid(detailinfos[i].getCurrencyid());
				loandinfo.setSapplyid(detailinfos[i].getSapplyid());
				loandinfo.setLoancontractid(detailinfos[i].getLoancontractid());
				loandinfo.setLoannoteid(detailinfos[i].getLoannoteid());                      
				loandinfo.setTransferamount(detailinfos[i].getTransferamount());                      
				loandinfo.setStartdate(detailinfos[i].getStartdate());
				loandinfo.setEnddate(detailinfos[i].getEnddate());
				loandinfo.setInputdate(detailinfos[i].getInputdate());
				loandinfo.setInputuserid(detailinfos[i].getInputuserid());
				contractinfo=loancontractdao.findbyid(detailinfos[i].getLoannoteid(), -1);
				loandinfo.setContractinfo(contractinfo);
				loancontractdetailinfo[i]=loandinfo;
			}
			returninfo.setInfo(loancontractdetailinfo);
			returninfo.setTransfercontractinfo(tempinfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		
		
		
		return returninfo;
	}
	
	
	public void update(TransferContractInfo info) throws Exception
	{
		long id=-1;
		
		try
		{
			Connection conn=Database.getConnection();
			conn.setAutoCommit(false);
			try
			{
				TransferContractDao transfercontractdao=new TransferContractDao(conn);
				ContractDetailDao contractdetaildao=new ContractDetailDao(conn);
				
				if(transfercontractdao.checkcontractcode(info)>0)
				{
					throw new IException("�ú�ͬ����Ѿ���ʹ�ã�");
				}
				//�޸�����
				transfercontractdao.update(info);
				
				//�޸�ʱ���ӱ��е�����ɾ�������µ����ݲ������ݿ�
				if(info.getId()!=-1)
				{
					contractdetaildao.deldetailinfo(info.getId());
				}
				for(int i=0;i<info.getContractdetailinfo().length;i++)
				{
					if(info.getContractdetailinfo()[i].getTransferamount()!=0)
					{
						info.getContractdetailinfo()[i].setTransfercontractformid(info.getId());
						contractdetaildao.add(info.getContractdetailinfo()[i]);
					}
				}
				conn.commit();
				
			}
			catch(Exception e)
			{
				conn.rollback();
				e.printStackTrace();
				throw new IException(e.getMessage());
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
			throw new IException(e.getMessage());
		}
		
		
	}
	
	
	public long delete(TransferContractInfo info)
	{
		long id=-1;
		
		try
		{
			Connection conn=Database.getConnection();
			conn.setAutoCommit(false);
			try
			{
				TransferContractDao transfercontractdao=new TransferContractDao(conn);
				ContractDetailDao contractdetaildao=new ContractDetailDao(conn);
				info.setContractCode(getTransCode(info));
				
				//�޸�����
				transfercontractdao.update(info);
				
				
				//�޸�ʱ���ӱ��е�����ɾ�������µ����ݲ������ݿ�
				if(info.getId()!=-1)
				{
					contractdetaildao.deldetailinfo(info.getId());
				}
				conn.commit();
				
			}
			catch(Exception e)
			{
				conn.rollback();
				e.printStackTrace();
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
		
		
		return id;
	}
	
	/**
	 * ����
	 * @param info
	 * @return
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public long doApproval(TransferContractInfo info) throws Exception
	{
		long lReturn = -1;
		TransferContractDao dao = new TransferContractDao();
		InutParameterInfo returnInfo = new InutParameterInfo();
		InutParameterInfo inutParameterInfo = info.getInutparameterinfo();
		try {
			
			TransferContractInfo depositInfo = new TransferContractInfo();
			depositInfo = (TransferContractInfo)dao.findByID(info.getId(), TransferContractInfo.class);
			inutParameterInfo.setDataEntity(depositInfo);
			// �ύ����
			returnInfo = FSWorkflowManager.doApproval(inutParameterInfo);
			//��������һ��,��Ϊ����ͨ��,����״̬Ϊ������
			if (returnInfo.isLastLevel()) {
					depositInfo.setStatusId(CRAconstant.TransactionStatus.USED);// ��ͬ������ֱ�Ӽ���
					dao.update(depositInfo);
				}
			
			// ��������һ��,��Ϊ�����ܾ�,����״̬Ϊ�ѱ���
			else if (returnInfo.isRefuse()) {
				depositInfo.setStatusId(CRAconstant.TransactionStatus.SAVE);
				dao.update(depositInfo);
			}
			lReturn = depositInfo.getId();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("����ʧ�ܣ�");
		}
		return lReturn;
	}
	
	
	/**
	 *˵������ͬ�Զ������÷�������������
	 *���ߣ�����
	 *ʱ�䣺2009-8-21����01:38:20
	 *@param info
	 *@return
	 *@throws Exception
	 *long
	 */
	public long cancelApproval(TransferContractInfo info) throws Exception 
	{
		long lReturn = -1;
		TransferContractDao dao = new TransferContractDao();
		TransferNoticeDao noticedao =new TransferNoticeDao();
		InutParameterInfo inutParameterInfo = info.getInutparameterinfo();
		try {
			TransferContractInfo depositInfo = new TransferContractInfo();
			  depositInfo = (TransferContractInfo)dao.findByID(info.getId(), TransferContractInfo.class);
			  depositInfo.setStatusId(CRAconstant.TransactionStatus.SAVE);
			  dao.update(depositInfo);
			//��������¼���ڵĸý��׵�������¼״̬��Ϊ��Ч
			if (inutParameterInfo.getApprovalEntryID() > 0) {
				FSWorkflowManager.cancelApprovalRecord(inutParameterInfo);
			}
			lReturn = depositInfo.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("ȡ������ʧ�ܣ�");
		}
		return lReturn;
	}
	public ContractdetailInfo[] findContractdetailInfo(ContractdetailInfo info) throws Exception
	{
		ContractdetailInfo[] detailInfos=null;
		ContractDetailDao dao =new ContractDetailDao();
		try
		{
			detailInfos=dao.findContractdetailInfo(info);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new  IException(e.getMessage());
		}
		
		return detailInfos;
	}
}
