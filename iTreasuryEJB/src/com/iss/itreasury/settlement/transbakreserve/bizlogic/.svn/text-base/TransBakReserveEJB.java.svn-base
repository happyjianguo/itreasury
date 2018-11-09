package com.iss.itreasury.settlement.transbakreserve.bizlogic;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.SessionBean;

import com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.settlement.account.bizlogic.AccountOperation;
import com.iss.itreasury.settlement.account.dao.Sett_AccountDAO;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.account.dataentity.ExternalAccountInfo;
import com.iss.itreasury.settlement.accountbook.bizlogic.AccountBookOperation;
import com.iss.itreasury.settlement.bankinstruction.BankInstructionFactory;
import com.iss.itreasury.settlement.bankinstruction.IBankInstruction;
import com.iss.itreasury.settlement.bankinstruction.entity.CreateInstructionParam;
import com.iss.itreasury.settlement.setting.dao.Sett_OfficeDAO;
import com.iss.itreasury.settlement.transbakreserve.dao.TransBakReserveDao;
import com.iss.itreasury.settlement.transbakreserve.dataentity.TransBakReserveInfo;
import com.iss.itreasury.settlement.transcurrentdeposit.dao.Sett_TransCurrentDepositDAO;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositAssembler;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;

public class TransBakReserveEJB  implements SessionBean{
	
	private javax.ejb.SessionContext mySessionCtx = null;
	final static private int ACTION_CHECK = 0;
	final static private int ACTION_CANCEL_CHECK = 1;
	final static long serialVersionUID = 3206091459770846163L;
	
	private final static  Object lockObj = new Object();  //静态
	
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	/**
	 * ejbActivate method comment
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbActivate() throws RemoteException
	{
	}
	/**
	 * ejbCreate method comment
	 * @exception javax.ejb.CreateException 异常说明。
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbCreate() throws javax.ejb.CreateException, RemoteException
	{
	}
	/**
	 * ejbPassivate method comment
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbPassivate() throws RemoteException
	{
	}
	/**
	 * ejbRemove method comment
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbRemove() throws RemoteException
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
	public void setSessionContext(javax.ejb.SessionContext ctx)
		throws RemoteException
	{
		mySessionCtx = ctx;
	}
	/**
	 * Method findByConditions.
	 * @param1 TransBakReserveInfo   
	 * @param2 isMatch 是否是匹配查找
	 * @return Collection 根据条件查找
	 * @throws  RemoteException, IRollbackException
	 */
	public Collection findByConditions(TransBakReserveInfo info,int orderByType,boolean isDesc)throws RemoteException, IRollbackException
	{
		TransBakReserveDao dao = new TransBakReserveDao();
		try
		{
			return dao.findByConditions(info,orderByType,isDesc);
		}
		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
	}
	
	/**
	 * Method preSave.
	 * @param TransBakReserveInfo
	 * @return int 0: 正常 1: 委付凭证不可用--提示输入 2: 票据不可用 3: 重复交易记录 4: 账户透支
	 * @throws RemoteException, IRollbackException
	 */
	public long preSave(TransBakReserveInfo info)
		throws RemoteException, IRollbackException
	{
		synchronized(lockObj){


		Collection c = this.findByConditions(info, -1, true);
		if (c != null && c.size() > 0)
		{
			if (c.size() == 1)
			{
				TransBakReserveInfo tmpInfo =
					(TransBakReserveInfo) ((ArrayList) c).get(0);
				if (tmpInfo.getId()== info.getId())
					return SETTConstant.PreSaveResult.NORMAL;
			}
			return SETTConstant.PreSaveResult.REPEATED;
		}
		return SETTConstant.PreSaveResult.NORMAL;
		}
	}

	
	/**
	 * Method tempSave.
	 * @param TransBakReserveInfo
	 * @return long
	 * @throws  RemoteException, IRollbackException
	 */
	public long tempSave(TransBakReserveInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){

		TransBakReserveDao transbakDepositDao =new TransBakReserveDao();
		long depositId = partSave(info, transbakDepositDao);
		//更新状态到：暂存
		try
		{
			transbakDepositDao.updateStatus(
				info.getId(),
				SETTConstant.TransactionStatus.TEMPSAVE);
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return depositId;
		}
	}
	
	/**
	 *Save part of information during transaction
	 */
	private long partSave(TransBakReserveInfo info,TransBakReserveDao transbakDepositDao)throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long lReturn=-1;
		try
		{
			//账簿操作接口类 
			AccountOperation accountOperation = new AccountOperation();
			
			
			if (info.getId()<0)//(transbakDepositDao.findByID(info.getId())== null)
			{
				//新增或更新活期信息
				lReturn=transbakDepositDao.add(info);
				if(lReturn>0){
					info.setId(lReturn);	
				}

			}
			else	//更新
			{
				lReturn=transbakDepositDao.updateBakReserveInfo(info);

			}

			//外部账户处理
			if(info.getNtransactiontypeid() == SETTConstant.TransactionType.BAKRETURN)
			{
				ExternalAccountInfo exInfo=new ExternalAccountInfo();
				
				exInfo.setNcurrencyID(info.getNcurrencyid());
				exInfo.setOfficeID(info.getNofficeid());
				exInfo.setExtAcctNo(info.getSextaccountno());
				exInfo.setExtAcctName(info.getSextclientname());
				exInfo.setBankName(info.getSremitinbank());
				exInfo.setCity(info.getSremitincity());
				exInfo.setProvince(info.getSremitinprovince());
				exInfo.setSpayeebankexchangeno(info.getSpayeebankexchangeno());
				exInfo.setSpayeebankcnapsno(info.getSpayeebankcnapsno());
				exInfo.setSpayeebankorgno(info.getSpayeebankorgno());
				
				accountOperation.saveExternalAccount(exInfo);
			}	
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return lReturn>0?info.getId():lReturn;
		}
	}

	/**
	 * Method saveUpreceive.总部-备付金上收-保存
	 * @param1 TransBakReserveInfo  
	 * @param2 InutParameterInfo  
	 * @return long 保存的记录ID
	 * @throws RemoteException,IRollbackException
	 */
	public long saveUpreceive(TransBakReserveInfo info,InutParameterInfo pInfo) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){

		long sessionID = -1;
		long depositId = -1;
		TransBakReserveDao dao = new TransBakReserveDao();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try
		{
			
			
			//获取当前交易号
			String transNo = info.getStransno();
			//标志位：是否是新增交易号
			boolean bNewTransNo = false;
			if (transNo == null || transNo.trim().equalsIgnoreCase("") || transNo.trim().equalsIgnoreCase("null"))
			{ //未被保存过，生成新交易号
				bNewTransNo = true;
				//通过工具操作接口类获取新交易号
				log.debug("------开始获取新交易号--------");
				transNo =
					utilOperation.getNewTransactionNo(
						info.getNofficeid(),
						info.getNcurrencyid(),
						info.getNtransactiontypeid());
				info.setStransno(transNo);
				log.debug("------新交易号是:" + transNo + "--------");
			}
			else
			{ //被保存过， 即保存再保存
				//判断是否被非法修改过
				log.debug("------开始判断是否被非法修改过--------");
				//校验状态是否正确
				TransBakReserveInfo newInfo =
					this.findByID(info.getId());
				if (newInfo == null)
				{
					throw new IRollbackException(
						mySessionCtx,
						"无法找到交易ID对应的旧交易信息，交易失败");
				}
				String errMsg =
					UtilOperation.checkStatus(
						info.getNstatusid(),
						newInfo.getNstatusid(),
						SETTConstant.Actions.MODIFYSAVE);
				//被修改过
				if (errMsg != null && !errMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, errMsg);
				}
				if (isTouch(info, dao))
				{
					log.debug("------被非法修改过--------");
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}
				else
				{ 
					accountBookOperation.deleteBakReserveAccountDetails(newInfo);
					log.debug("------结束删除账簿信息--------");
				}
			}
			Sett_OfficeDAO ofDao=new Sett_OfficeDAO();
			if(ofDao.getOfficeLevelByID(info.getNofficeid())!=SETTConstant.OrganizationLevel.Headquarters)
			{
				throw new RemoteException("机构没有做此业务的权限，请联系管理员.");
				
			}
			else if(info.getBranchid()<0 )
			{
				throw new RemoteException("获取的分支机构id出错，请联系管理员.");
				
			}
			else if(ofDao.getOfficeLevelByID(info.getBranchid())==SETTConstant.OrganizationLevel.Headquarters )
			{
				throw new RemoteException("总部不能对总部做此业务，请联系管理员.");
				
			}
			else if(info.getBakaccountid()<0)
			{
				
				throw new RemoteException("获取的备付金账户id出错，请联系管理员.");
				
			}
			else if(info.getPayorreceivetype()!=1 && info.getPayorreceivetype()!=2)
			{
				throw new RemoteException("获取的付款方式出错，请联系管理员.");
				
			}
			else if(info.getPayorreceivetype()==1 && info.getReserveaccountid()<0)
			{
				throw new RemoteException("获取的付款方式与选择的账户类型不匹配，请联系管理员.");//Payorreceivetype 付款方式： 1 准备金付款（内部转账），2 银行付款/银行收款。
				
			}
			else if(info.getPayorreceivetype()==2 && info.getBankid()<0)
			{
				
				throw new RemoteException("获取的付款方式与选择的账户类型不匹配，请联系管理员.");
			}

			log.debug("------开始　PartSave--------");
			depositId = partSave(info, dao);
			log.debug("------结束　PartSave--------");
			info.setId(depositId);
			//保存账簿信息	
			log.debug("------开始保存账簿信息--------");
			log.debug(UtilOperation.dataentityToString(info));
			

			accountBookOperation.saveBakReserveAccountDetails(info);
			log.debug("------结束保存账簿信息--------");
			log.debug("------开始更新状态到未复核--------");
			//更新状态到：2保存（未复核）
			dao.updateStatus(info.getId(),
				SETTConstant.TransactionStatus.SAVE);
			log.debug("------更新状态到未复核成功--------");
			
			/**
			 * 如果Info中的InutParameterInfo不为空,则需要提交审批 add by 刘琰 2007-04-17
			 */

			if(pInfo!=null)
			{
				log.debug("------提交审批--------");
				//设置返回的地址链接(交易id只能在交易保存之后加上,tempInfo.getUrl()得到的url没有具体的交易id)
				pInfo.setUrl(pInfo.getUrl()+depositId);
				pInfo.setTransID(transNo);//这里保存的是交易编号
				pInfo.setDataEntity(info);
				//提交审批
				FSWorkflowManager.initApproval(pInfo);
				//更新状态到审批中
				dao.updateStatus(info.getId(),SETTConstant.TransactionStatus.APPROVALING);
				log.debug("------提交审批成功--------");
			}	
			
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}

		return depositId;
		}
	}
	
	/**
	 * Method saveDownReturn.总部-备付金调回-保存
	 * @param1 TransBakReserveInfo  
	 * @param2 InutParameterInfo  
	 * @return long 保存的记录ID
	 * @throws RemoteException,IRollbackException
	 */
	public long saveDownReturn(TransBakReserveInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){

			long sessionID = -1;
			long depositId = -1;
			TransBakReserveDao dao = new TransBakReserveDao();
			//工具操作接口类
			UtilOperation utilOperation = new UtilOperation();
			//账簿操作接口类 
			AccountBookOperation accountBookOperation = new AccountBookOperation();
			try
			{
				
				
				//获取当前交易号
				String transNo = info.getStransno();
				//标志位：是否是新增交易号
				boolean bNewTransNo = false;
				if (transNo == null || transNo.trim().equalsIgnoreCase("") || transNo.trim().equalsIgnoreCase("null"))
				{ //未被保存过，生成新交易号
					bNewTransNo = true;
					//通过工具操作接口类获取新交易号
					log.debug("------开始获取新交易号--------");
					transNo =
						utilOperation.getNewTransactionNo(
							info.getNofficeid(),
							info.getNcurrencyid(),
							info.getNtransactiontypeid());
					info.setStransno(transNo);
					log.debug("------新交易号是:" + transNo + "--------");
				}
				else
				{ //被保存过， 即保存再保存
					//判断是否被非法修改过
					log.debug("------开始判断是否被非法修改过--------");
					//校验状态是否正确
					TransBakReserveInfo newInfo =
						this.findByID(info.getId());
					if (newInfo == null)
					{
						throw new IRollbackException(
							mySessionCtx,
							"无法找到交易ID对应的旧交易信息，交易失败");
					}
					String errMsg =
						UtilOperation.checkStatus(
							info.getNstatusid(),
							newInfo.getNstatusid(),
							SETTConstant.Actions.MODIFYSAVE);
					//被修改过
					if (errMsg != null && !errMsg.equals(""))
					{
						throw new IRollbackException(mySessionCtx, errMsg);
					}
					if (isTouch(info, dao))
					{
						log.debug("------被非法修改过--------");
						throw new IRollbackException(mySessionCtx, "Sett_E130");
					}
					else
					{ 
						accountBookOperation.deleteBakReserveAccountDetails(newInfo);
						log.debug("------结束删除账簿信息--------");
					}
				}
				Sett_OfficeDAO ofDao=new Sett_OfficeDAO();
				if(ofDao.getOfficeLevelByID(info.getNofficeid())!=SETTConstant.OrganizationLevel.Headquarters)
				{
					throw new RemoteException("机构没有做此业务的权限，请联系管理员.");
					
				}
				else if(info.getBranchid()<0 )
				{
					throw new RemoteException("获取的分支机构id出错，请联系管理员.");
					
				}
				else if(ofDao.getOfficeLevelByID(info.getBranchid())==SETTConstant.OrganizationLevel.Headquarters )
				{
					throw new RemoteException("总部不能对总部做此业务，请联系管理员.");
					
				}
				else if(info.getBakaccountid()<0)
				{
					
					throw new RemoteException("获取的备付金账户id出错，请联系管理员.");
					
				}
				else if(info.getPayorreceivetype()!=1 && info.getPayorreceivetype()!=2)
				{
					throw new RemoteException("获取的付款方式出错，请联系管理员.");
					
				}
				else if(info.getPayorreceivetype()==1 && info.getReserveaccountid()<0)//Payorreceivetype 付款方式： 1 准备金付款（内部转账），2 银行付款/银行收款。
				{
					throw new RemoteException("获取的调出方式与选择的账户类型不匹配，请联系管理员.");
					
				}
				else if(info.getPayorreceivetype()==2 && (info.getSextaccountno()==null || info.getSextaccountno().trim().length()==0 || info.getBankid()<0 ) )
				{
					
					throw new RemoteException("获取的调出方式与选择的账户类型不匹配，请联系管理员.");
				}

				log.debug("------开始　PartSave--------");
				depositId = partSave(info, dao);
				log.debug("------结束　PartSave--------");
				info.setId(depositId);
				//保存账簿信息	
				log.debug("------开始保存账簿信息--------");
				log.debug(UtilOperation.dataentityToString(info));
				

				accountBookOperation.saveBakReserveAccountDetails(info);
				log.debug("------结束保存账簿信息--------");
				log.debug("------开始更新状态到未复核--------");
				//更新状态到：2保存（未复核）
				dao.updateStatus(info.getId(),
					SETTConstant.TransactionStatus.SAVE);
				log.debug("------更新状态到未复核成功--------");

				
			}
			catch (Exception e)
			{
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}

			return depositId;
			}
		}

	
	/**
	 * 
	 * Method isTouch
	 * @descriptin  判断是否被非法修改过
	 * @return long 根据条件查找
	 * @throws RemoteException
	 */
	private boolean isTouch(TransBakReserveInfo info,TransBakReserveDao dao)throws RemoteException, IRollbackException
	{
		try
		{
		//判断是否被非法修改过
		Timestamp lastTouchDate;
		lastTouchDate =	dao.findTouchDate(info.getId());
		Timestamp curTouchDate = info.getDtmodify();
		if (curTouchDate == null
			|| lastTouchDate.compareTo(curTouchDate) != 0)
			return true;
		else
			return false;
		}
		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new IRollbackException(
				mySessionCtx,
				"SQLException in TransBakReserveEJB",
				e);
		}
	}

	
	/**
	 * Method deleteUpreceive.总部-备付金上收-删除
	 * @param TransBakReserveInfo
	 * @return long 删除的记录ID
	 * @throws RemoteException,IRollbackException
	 */
	public long deleteUpreceive(TransBakReserveInfo info) throws RemoteException, IRollbackException
	{
		
		synchronized(lockObj){

		try{
			TransBakReserveDao dao = new TransBakReserveDao();
			
			Sett_OfficeDAO ofDao=new Sett_OfficeDAO();
			
			if(ofDao.getOfficeLevelByID(info.getNofficeid())!=SETTConstant.OrganizationLevel.Headquarters)
			{
				throw new RemoteException("机构没有做此业务的权限，请联系管理员.");
				
			}
			else if(info.getBranchid()<0 )
			{
				throw new RemoteException("获取的分支机构id出错，请联系管理员.");
				
			}
			else if(ofDao.getOfficeLevelByID(info.getBranchid())==SETTConstant.OrganizationLevel.Headquarters )
			{
				throw new RemoteException("总部不能对总部做此业务，请联系管理员.");
				
			}

		//账簿操作接口类 
		AccountBookOperation accountBookOperation = new AccountBookOperation();

			//校验状态是否正确
			TransBakReserveInfo newInfo = this.findByID(info.getId());
			
		    //删除交易记录
		    if (newInfo.getNstatusid() == SETTConstant.TransactionStatus.SAVE)
		    {
		    	accountBookOperation.deleteBakReserveAccountDetails(newInfo);
		    }
			//返回ID
			return dao.delete(info.getId());
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		}
	}
	
	/**
	 * Method deleteDownReturn.总部-备付金调回-删除
	 * @param TransBakReserveInfo
	 * @return long 删除的记录ID
	 * @throws RemoteException,IRollbackException
	 */
	public long deleteDownReturn(TransBakReserveInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){

			try{
				TransBakReserveDao dao = new TransBakReserveDao();
				Sett_OfficeDAO ofDao=new Sett_OfficeDAO();
				
				if(ofDao.getOfficeLevelByID(info.getNofficeid())!=SETTConstant.OrganizationLevel.Headquarters)
				{
					throw new RemoteException("机构没有做此业务的权限，请联系管理员.");
					
				}
				else if(info.getBranchid()<0 )
				{
					throw new RemoteException("获取的分支机构id出错，请联系管理员.");
					
				}
				else if(ofDao.getOfficeLevelByID(info.getBranchid())==SETTConstant.OrganizationLevel.Headquarters )
				{
					throw new RemoteException("总部不能对总部做此业务，请联系管理员.");
					
				}

			
			//账簿操作接口类 
			AccountBookOperation accountBookOperation = new AccountBookOperation();

				//校验状态是否正确
				TransBakReserveInfo newInfo = this.findByID(info.getId());
				
			    //删除交易记录
			    if (newInfo.getNstatusid() == SETTConstant.TransactionStatus.SAVE)
			    {
			    	accountBookOperation.deleteBakReserveAccountDetails(newInfo);
			    }
				//返回ID
				return dao.delete(info.getId());
			}
			catch (Exception e)
			{
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
			}
		}
	
	/**
	 * 方法说明：根据账户ID，得到账户信息
	 * @param transCurrentDepositID
	 * @return Sett_TransCurrentDepositInfo
	 * @throws IException
	 */
	public TransBakReserveInfo findByID(long transBakReserveID)
		throws RemoteException, IRollbackException
	{
		//Sett_TransCurrentDeposit数据访问对象
		TransBakReserveDao dao = new TransBakReserveDao();
		try
		{
			return dao.findByID(transBakReserveID);
		}
		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
	}
	


	/**
	* 方法说明：根据查询条件匹配
	*  Method  matchUpreceive.总部-备付金上收-复核匹配
	* @param TransBakReserveInfo info
	* @return TransBakReserveInfo
	* @throws RemoteException,IRollbackException
	*/

	public TransBakReserveInfo matchUpreceive(TransBakReserveInfo info)throws RemoteException, IRollbackException	
	{
		//Sett_TransCurrentDeposit数据访问对象
		TransBakReserveDao dao = new TransBakReserveDao();
		try
		{
			//控制匹配复核状态
			info.setNstatusid(FSWorkflowManager.getSettCheckStatus(new InutParameterInfo(info.getNofficeid(),
																		info.getNcurrencyid(),
																		Constant.ModuleType.SETTLEMENT,
																		info.getNtransactiontypeid(),
																		-1)));
			return dao.match(info);
		}
		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
	}
	
	/**
	* 方法说明：根据查询条件匹配
	*  Method  matchUpreceive.总部-备付金调回-复核匹配
	* @param TransBakReserveInfo info
	* @return TransBakReserveInfo
	* @throws RemoteException,IRollbackException
	*/
	public TransBakReserveInfo matchDownReturn(TransBakReserveInfo info)throws RemoteException, IRollbackException
	{
		//Sett_TransCurrentDeposit数据访问对象
		TransBakReserveDao dao = new TransBakReserveDao();
		try
		{
			//控制匹配复核状态
			info.setNstatusid(FSWorkflowManager.getSettCheckStatus(new InutParameterInfo(info.getNofficeid(),
																		info.getNcurrencyid(),
																		Constant.ModuleType.SETTLEMENT,
																		info.getNtransactiontypeid(),
																		-1)));
			return dao.match(info);
		}
		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
	}

	
	/**
	  * 方法说明：根据记录ID进行复核（总部-备付金上收-复核）
	 * @param TransBakReserveInfo
	 * @return long 复核的记录ID
	 * @throws RemoteException, IRollbackException
	 */
	public long checkUpreceive(TransBakReserveInfo info) throws RemoteException, IRollbackException
	{
		
	synchronized(lockObj){
	return check(ACTION_CHECK, info);
	}
    }
	/**
	 * 方法说明：根据记录ID进行取消复核（总部-备付金上收-取消复核）
	 * @param TransBakReserveInfo
	 * @return long 取消复核的记录ID
	 * @throws RemoteException, IRollbackException
	 */
	public long cancelCheckUpreceive(TransBakReserveInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		return check(ACTION_CANCEL_CHECK, info);
		}
	}
	
	private long check(int checkOrCancelCheck,TransBakReserveInfo info)throws RemoteException, IRollbackException
	{
		synchronized(lockObj){

			
		Sett_OfficeDAO ofDao=new Sett_OfficeDAO();
		try{
			if(ofDao.getOfficeLevelByID(info.getNofficeid())!=SETTConstant.OrganizationLevel.Headquarters)
			{
				throw new RemoteException("机构没有做此业务的权限，请联系管理员.");
				
			}
			else if(info.getBranchid()<0 )
			{
				throw new RemoteException("获取的分支机构id出错，请联系管理员.");
				
			}
			else if(ofDao.getOfficeLevelByID(info.getBranchid())==SETTConstant.OrganizationLevel.Headquarters )
			{
				throw new RemoteException("总部不能对总部做此业务，请联系管理员.");
				
			}
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}

		TransBakReserveDao dao = new TransBakReserveDao();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation;
		accountBookOperation = new AccountBookOperation();
		try
		{
			//校验状态是否正确
			TransBakReserveInfo newInfo =
				this.findByID(info.getId());
			String errMsg = "";
			if (checkOrCancelCheck == ACTION_CHECK)
			{
				errMsg =
					UtilOperation.checkStatus(
						info.getNstatusid(),
						newInfo.getNstatusid(),
						SETTConstant.Actions.CHECK);
			}
			else
			{
				errMsg =
					UtilOperation.checkStatus(
						info.getNstatusid(),
						newInfo.getNstatusid(),
						SETTConstant.Actions.CANCELCHECK);
			}
			if (errMsg != null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			//判断是否被修改
			if (checkOrCancelCheck == ACTION_CHECK)
			{
				if (isTouch(info, dao))
					throw new IRollbackException(mySessionCtx, "Sett_E020");
			}
			else
			{
				if (isTouch(info, dao))
					throw new IRollbackException(mySessionCtx, "Sett_E024");
			}
			if (checkOrCancelCheck == ACTION_CHECK)
			{
				//复核交易记录
				log.info("--------------开始AccountBook复核交易记录--------------");
				accountBookOperation.checkBakReserveDetails(info);
				System.out.println("新test************************************");
				dao.updateStatus(info.getId(),SETTConstant.TransactionStatus.CHECK,info.getNcheckuserid(),info.getScheckabstract());
				log.info("--------------结束AccountBook复核交易记录--------------");
				

			}
			else
			{
				//取消复核交易记录
				log.info("--------------开始取消AccountBook复核交易记录--------------");
				accountBookOperation.unCheckBakReserveDetails(
					info);
				//更新状态到：未复核或已审批(根据该业务是否有审批流判断)
				long lCancelCheckStatus = FSWorkflowManager.getSettCheckStatus(new InutParameterInfo(info.getNofficeid(),
						info.getNcurrencyid(),
						Constant.ModuleType.SETTLEMENT,
						info.getNtransactiontypeid(),
						-1));
				dao.updateStatus(info.getId(),lCancelCheckStatus,-1,info.getScheckabstract());
				log.info("--------------结束取消AccountBook复核交易记录--------------");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return info.getId();
		}
	}
	/**
	  * 方法说明：根据记录ID进行复核（总部-备付金调回-复核）
	 * @param TransBakReserveInfo
	 * @return long 复核的记录ID
	 * @throws RemoteException, IRollbackException
	 */
	public long checkDownReturn(TransBakReserveInfo info) throws RemoteException, IRollbackException
	{
		
		synchronized(lockObj){
		long lResult=-1;
		try{	
				TransBakReserveDao dao = new TransBakReserveDao();
				//账簿操作接口类 
				AccountBookOperation accountBookOperation;
				accountBookOperation = new AccountBookOperation();
				
				// 账簿信息处理 以及会计分录
				lResult= check(ACTION_CHECK, info);
			
				if(lResult>0)
				{
						log.info("--------------开始生成银行指令--------------");
						//if(false) {  //由于中交项目不需要生成银行指令所以把判断条件改为false	
			
						/***********构造银行付款指令**********/
						//是否有银企接口
						boolean bIsValid = Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false);
						//是否需要生成银行指令
						boolean bCreateInstruction = false;
						long bankID = info.getBankid();
						
						if(info.getPayorreceivetype()==2 )//银行汇款方式调回时 才生成银行指令
						{
						
								try {
									//调用此方法后，bankID的值变为银行类型ID
									bCreateInstruction = accountBookOperation.isCreateInstruction(bankID);
								} catch (Exception e1) {				
									log.error("判断传入的银行ID是否需要生成银行指令出错！");
									e1.printStackTrace();
								}
								
								if(bIsValid && bCreateInstruction) {//有银企接口并且是需要生成银行指令
									Log.print("*******************开始产生银行收款指令，并保存**************************");
									try {
										//构造参数
										CreateInstructionParam instructionParam = new CreateInstructionParam();
										instructionParam.setTransactionTypeID(info.getNtransactiontypeid());
										instructionParam.setObjInfo(info);
										instructionParam.setOfficeID(info.getNofficeid());
										instructionParam.setCurrencyID(info.getNcurrencyid());
										instructionParam.setCheckUserID(info.getNcheckuserid());
										instructionParam.setBankType(bankID);
										instructionParam.setInputUserID(info.getNinputuserid());
										//生成银行指令并保存
										IBankInstruction bankInstruction = BankInstructionFactory.getInstance();
										bankInstruction.createBankInstruction(instructionParam);
										
										log.debug("------生成银行活期指令结束--------");
										
									} catch (Throwable e) {
										log.error("生成银行付款指令失败");
										e.printStackTrace();
										throw new IRollbackException(mySessionCtx, "生成银行付款指令失败："+e.getMessage());
									}
								}
								else {
									Log.print("没有银行接口或不需要生成银行指令！");
								}
								
						}

						
				}
				else
				{
					
					throw new IRollbackException(mySessionCtx, "账簿信息处理失败,导致未生成银行指令.");
					
				}
			log.info("--------------结束生成银行指令--------------");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
			return lResult;
		}
	}
	
	/**
	 * 方法说明：根据记录ID进行取消复核（总部-备付金调回-取消复核）
	 * @param TransBakReserveInfo
	 * @return long 取消复核的记录ID
	 * @throws RemoteException, IRollbackException
	 */
	public long cancelCheckDownReturn(TransBakReserveInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
			return check(ACTION_CANCEL_CHECK, info);
			}
	}
	
	
	/**
	 * 提交审批方法（没有实现，结算暂不直接审批）
	 */
	public long initApproval(TransBakReserveInfo info)throws RemoteException, IRollbackException
	{
		return -1;
	}
	
	/**
	 * 审批方法（没有实现，结算暂不直接审批）
	 */
	public long doApproval(TransBakReserveInfo info)throws RemoteException, IRollbackException
	{
		return -1;
	}
	
	/**
	 * 取消审批方法（没有实现，结算暂不直接审批）
	 */
	public long cancelApproval(TransBakReserveInfo info)throws RemoteException, IRollbackException
	{
		return -1;
	}
}
