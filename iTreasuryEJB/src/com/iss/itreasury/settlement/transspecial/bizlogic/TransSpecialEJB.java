/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transspecial.bizlogic;

import javax.ejb.SessionBean;

import com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.settlement.transspecial.dataentity.*;
import java.rmi.RemoteException;
import java.util.*;
import com.iss.itreasury.util.*;
import com.iss.itreasury.settlement.transspecial.dao.*;
import com.iss.itreasury.settlement.account.bizlogic.AccountOperation;
import com.iss.itreasury.settlement.account.dataentity.ExternalAccountInfo;
import com.iss.itreasury.settlement.accountbook.bizlogic.*;
import com.iss.itreasury.settlement.bankinstruction.BankInstructionFactory;
import com.iss.itreasury.settlement.bankinstruction.IBankInstruction;
import com.iss.itreasury.settlement.bankinstruction.entity.CreateInstructionParam;
import com.iss.itreasury.settlement.util.*;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;

/**
 * 特殊业务SessionBean
 *  
 * @author yqliao
 *
 * 
 */
public class TransSpecialEJB implements SessionBean
{
	private javax.ejb.SessionContext mySessionCtx = null;
	final static long serialVersionUID = 3206093459760846163L;
	private static  Object lockObj = new Object();  //静态
	/**
	 * ejbActivate method comment
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbActivate() throws java.rmi.RemoteException
	{
	}

	/**
	 * ejbCreate method comment
	 * @exception javax.ejb.CreateException 异常说明。
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbCreate() throws javax.ejb.CreateException, java.rmi.RemoteException
	{
	}

	/**
	 * ejbPassivate method comment
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbPassivate() throws java.rmi.RemoteException
	{
	}

	/**
	 * ejbRemove method comment
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbRemove() throws java.rmi.RemoteException
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
	public void setSessionContext(javax.ejb.SessionContext ctx) throws java.rmi.RemoteException
	{
		mySessionCtx = ctx;
	}

	/**
	 * 检测交易是否被修改过
	 * 逻辑操作：
	 * （1）调用方法this.findByID(),得到一个新的特殊交易实体类。
	 * （2）对比传入参数特殊交易实体类的dtModify和查询出的新的特殊交易实体类的dtModify，是否相同。
	 * @param info TransSpecialOperationInfo 特殊交易实体类
	 * @return boolean True,被修改；false,未被修改。
	 * @throws Exception
	 */
	private boolean checkIsTouched(TransSpecialOperationInfo info) throws Exception
	{
		try
		{  
			Sett_TransSpecialOperationDAO tsodao = new Sett_TransSpecialOperationDAO();
			TransSpecialOperationInfo tsoi = new TransSpecialOperationInfo();
			tsoi = tsodao.findByID(info.getId());

			if (tsoi == null)
			{
				return false;
			}
			if ((tsoi.getDtmodify()).getTime() == info.getDtmodify().getTime())
			{
				return false;
			}
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception("Gen_E001");
		}
	}
	/**
	 * 根据交易号查找交易ID的方法
	 * @param strTransNo
	 * @return
	 * @throws Exception
	 */
	public long getIDByTransNo(String strTransNo) throws RemoteException, IRollbackException
	{
		Sett_TransSpecialOperationDAO tsodao = new Sett_TransSpecialOperationDAO();
		try
		{
			return tsodao.getIDByTransNo(strTransNo);
		}
/*		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}*/
		catch (Exception e)
		{
			throw new IRollbackException(this.getSessionContext(), "Gen_E001", e);
		}
	}

	/**
	 * 特殊交易暂存
	 * 逻辑操作：
	 * （1）如果lID不是-1，调用方法this.checkIsTouched(),判断要暂存的记录是否被修改过。      //checkistouched()不用
	     *      调用方法Sett_TransSpecialOperationDAO.update()保存交易记录信息。          //暂存之后再暂存
	 * 		调用方法Sett_TransSpecialOperationDAO.updateStatus()更改记录的状态为未保存。
	 * （2）如果lID是-1，调用方法Sett_TransSpecialOperationDAO.add()保存交易记录信息。
	 * 		调用方法Sett_TransSpecialOperationDAO.updateStatus()更改记录的状态为未保存。
	 * @param info TransSpecialOperationInfo 特殊交易实体类
	 * @return long 暂存成功，返回交易ID；否则返回-1。
	 * @throws Exception
	 */
	public long tempsave(TransSpecialOperationInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long lReturn = -1;
		boolean tag = false;
		Sett_TransSpecialOperationDAO tsodao = new Sett_TransSpecialOperationDAO();
		try
		{
			Log.print("取子账户ID");
			if (info.getNpayloannoteid() > 0)
			{
				info.setNpaysubaccountid(tsodao.getSubAccountIDByPayFormID(info.getNpayloannoteid()));
			}
			if (info.getNreceiveloannoteid() > 0)
			{
				info.setNreceivesubaccountid(tsodao.getSubAccountIDByPayFormID(info.getNreceiveloannoteid()));
			}
			Log.print("取子账户ID");
			if (info.getId() > 0)
			{
				tag = checkIsTouched(info);
				if (tag == true)
				{
					throw new IRollbackException(this.getSessionContext(), "Sett_E130");
				}
				else
				{
					System.out.println("modifytempsave begin!");
					java.sql.Timestamp tsDtmodify = null;
					
					tsDtmodify = Env.getSystemDateTime();
					info.setDtmodify(tsDtmodify);
					System.out.println(info.getDtmodify());
					tsodao.update(info);
					lReturn = tsodao.updateStatus(info.getId(), tsDtmodify, SETTConstant.TransactionStatus.TEMPSAVE);
					System.out.println("modifytempsave end!");
				}
			}
			else if (info.getId() < 0)
			{
				info.setId(tsodao.getNextID());
				java.sql.Timestamp tsDtmodify = null;
				
				tsDtmodify = Env.getSystemDateTime();
				info.setDtmodify(tsDtmodify);
				tsodao.add(info);
				lReturn = tsodao.updateStatus(info.getId(), tsDtmodify, SETTConstant.TransactionStatus.TEMPSAVE);

			}
		}
		//modified by mzh_fu 2007/05/011
/*		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			System.out.println("modifytempsave error:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}*/
		catch (Exception e)
		{
			System.out.println("modifytempsave error:" + e.getMessage());
			e.printStackTrace();
			throw new IRollbackException(this.getSessionContext(), "Gen_E001", e);
		}

		return lReturn;
		}
	}

	/**
	 * 特殊交易特殊交易保存前的检测方法
	 * 逻辑操作：
	 * （1）判断参数TransSpecialOperationInfo中的交易实体类的交易编号是否为空。
	 *      如果是空，说明是新增保存：
	 *      用方法：Sett_TransSpecialOperationDAO.checkIsDuplicate()判断是否重复。
	 * @param info TransSpecialOperationInfo 特殊交易实体类
	 * @return long -1,交易重复；1，检测通过。
	 * @throws Exception
	 */
	public long presave(TransSpecialOperationInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long lReturn = -1;
		Sett_TransSpecialOperationDAO tsodao = new Sett_TransSpecialOperationDAO();
		try
		{
			boolean tag = tsodao.checkIsDuplicate(info);
			lReturn = 1;
		}
		//modified by mzh_fu 2007/05/011
/*		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}*/
		catch (Exception e)
		{
			throw new IRollbackException(this.getSessionContext(), "Gen_E001", e);
		}
		return lReturn;
		}
	}

	/**
	 * 特殊交易交易删除
	 * 逻辑说明：
	 * （1）调用方法this.checkIsTouched,判断要删除的记录是否被修改过。
	 * （2）判断参数TransSpecialOperationInfo 中的交易实体类的状态，
	 *      如果是暂存：
	 * 	 		调用方法：Sett_TransSpecialOperationDAO.updateStatus。修改交易的状态为删除（无效）。
	 *      如果是保存：
	 * 			调用方法：AccountBookOperation.deleteSpecialOperation()。回滚原来的财务处理。注意参数是原来
	 * 						的实体TransSpecialOperationInfo.
	 * 			调用方法：Sett_TransSpecialOperationDAO.updateStatus。修改交易的状态为删除（无效）?
	 * @param info TransSpecialOperationInfo 特殊交易实体类
	 * @return boolean；true，删除成功；false,失败。
	 * @throws Exception
	 */
	public boolean delete(TransSpecialOperationInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		//System.out.println("SESSIONBEAN DELETE SACALE");
		boolean tag = false;
		long lReturn = -1;
		boolean blReturn = false;
		AccountBookOperation accountbookoperation = new AccountBookOperation();
		try
		{
			//System.out.println("SESSIONBEAN DELETEACTION getid: "+info.getId());
			tag = this.checkIsTouched(info);
			System.out.println("SESSIONBEAN DELETEACTION tag: " + tag);
			if (tag == false)
			{
				java.sql.Timestamp tsDtmodify = null;
				
				//tsDtmodify=Env.getSystemDateTime();            
				info.setDtmodify(tsDtmodify);
				//System.out.println("SESSIONBEAN DELETEACTION tsDtmodify: "+info.getDtmodify());
				//System.out.println("SESSIONBEAN DELETEACTION info.getNstatusid():"+info.getNstatusid());
				Sett_TransSpecialOperationDAO tsoi = new Sett_TransSpecialOperationDAO();
				if (info.getNstatusid() == SETTConstant.TransactionStatus.TEMPSAVE)
				{
					System.out.println("SESSIONBEAN DELETEACTION SETTConstant.TransactionStatus.TEMPSAVE: " + info.getNstatusid());
					
					//暂存
					lReturn = tsoi.updateStatus(info.getId(), tsDtmodify, SETTConstant.TransactionStatus.DELETED); //交易状态由暂存改为删除
					if(lReturn > 0) {
						blReturn = true;
					}
				}
				else if (info.getNstatusid() == SETTConstant.TransactionStatus.SAVE)
				{
					System.out.println("SESSIONBEAN DELETEACTION SETTConstant.TransactionStatus.SAVE: " + info.getNstatusid());
					//保存
					AccountBookOperation abo = new AccountBookOperation();
					//财务处理
					Log.print("***删除财务处理之前***");
					accountbookoperation.deleteSpecialOperation(info);
					Log.print("***删除财务处理之后***");
					
					//删除网银指令	
					if (info.getSinstructionno() != null && info.getSinstructionno().length() > 0)
					{
						Log.print("---------是网银指令----------");
						FinanceInfo financeInfo = new FinanceInfo();
						OBFinanceInstrDao financeDao = new OBFinanceInstrDao();
						financeDao.updateStatus(Long.valueOf(info.getSinstructionno()).longValue(), OBConstant.SettInstrStatus.REFUSE);
					}
					
					lReturn = tsoi.updateStatus(info.getId(), tsDtmodify, SETTConstant.TransactionStatus.DELETED);
					if(lReturn > 0) {
						blReturn = true;
					}
				}
				//Boxu Add 2008年12月11日 增加对其他状态的错误提示
				else
				{
					throw new IRollbackException(this.getSessionContext(), "该业务"+ SETTConstant.TransactionStatus.getName(info.getNstatusid()) + "，不能删除");
				}
			}
			else
			{
				throw new IRollbackException(this.getSessionContext(), "Sett_E019");
			}
		}
		//modified by mzh_fu 2007/05/011
		/*catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}*/
		catch (Exception e)
		{
			throw new IRollbackException(this.getSessionContext(), "系统操作错误，请重试", e);
		}
		
		return blReturn;
		}
	}

	/**
	 * 特殊交易保存
	 * 逻辑操作：
	 * （1）判断参数TransSpecialOperationInfo交易实体类中的交易编号是否为空。
	 *      如果是空，说明是新增保存：
	 * 			调用方法：XXX.getTransactionNo()得到一个交易号，并将其写入TransSpecialOperationInfo 。
	 *      如果非空，说明是修改保存:
	 *          调用方法：this.checkIsTouched,判断要暂存的记录是否被修改过。
	 *          调用方法：this.FindDetailByID(),得到包含特殊交易实体类TransSpecialOperationInfo。
	 * 			调用方法：AccountBookOperation.deleteSpecialOperation()。回滚原来的财务处理。注意参数是原来
	 * 						的实体TransSpecialOperationInfo。
	 * （2）调用方法：Sett_TransSpecailOperationDAO.add() 保存信息。
	 * （3）调用方法：AccountBookOperation.saveSpecialOperation()。进行财务处理。
	 * （4）调用方法：Sett_TransSpecailOperationDAO.updateStatus()。 修改交易的状态为保存。
	 * @param info TransSpecialOperationInfo 特殊交易实体类
	 * @return long 保存成功，返回交易ID；否则返回-1。
	 * @throws Exception
	 */
	public long save(TransSpecialOperationInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long lReturn = -1;
		Sett_TransSpecialOperationDAO tsodao = new Sett_TransSpecialOperationDAO();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		AccountBookOperation accountbookoperation = new AccountBookOperation();
		try
		{
			String transNo=info.getStransno();
			Log.print("取子账户ID开始");
			if (info.getNpayloannoteid() > 0)
			{
				info.setNpaysubaccountid(tsodao.getSubAccountIDByPayFormID(info.getNpayloannoteid()));
			}
			if (info.getNreceiveloannoteid() > 0)
			{
				info.setNreceivesubaccountid(tsodao.getSubAccountIDByPayFormID(info.getNreceiveloannoteid()));
			}
			Log.print("取子账户ID结束");
			if ((info.getStransno() == null) || (info.getStransno().compareTo("") == 0))
			{
				java.sql.Timestamp tsDtmodify = null;
				
				tsDtmodify = Env.getSystemDateTime();
				info.setDtmodify(tsDtmodify);

				//交易编号为空
				//通过工具操作接口类获取新交易号
				transNo = utilOperation.getNewTransactionNo(info.getNofficeid(), info.getNcurrencyid(),info.getNtransactiontypeid());
				if (transNo == null)
				{
					System.out.println("transNo is null");
				}
				else
				{
					System.out.println("Generate transNo is " + transNo);
					info.setStransno(transNo);
					System.out.println("SESSION BEAN info.getId()before:" + info.getId());
					if ((info.getId() < 0) && (info.getNstatusid() == SETTConstant.TransactionStatus.SAVE))
					{
						System.out.println("SESSION BEAN SAVE: " + info.getId());
						info.setId(tsodao.getNextID());
						tsodao.add(info);
						
						if(info.getSinstructionno()!=null && info.getSinstructionno().length()>0)
						{
							Log.print("---------是网银指令----------");
							FinanceInfo financeInfo = new FinanceInfo();
							OBFinanceInstrDao financeDao = new OBFinanceInstrDao();
								
							financeInfo.setID(Long.valueOf(info.getSinstructionno()).longValue());
							financeInfo.setDealUserID(info.getNinputuserid());
							financeInfo.setConfirmDate(info.getDtexecute());
							financeInfo.setFinishDate(info.getDtexecute());
							financeInfo.setTransNo(info.getStransno());
							financeInfo.setStatus(OBConstant.SettInstrStatus.DEAL);
							financeDao.updateStatusAndTransNo(null,financeInfo);
						}
					}
					else if ((info.getId() >= 0) && (info.getNstatusid() == SETTConstant.TransactionStatus.TEMPSAVE))
					{
						System.out.println("SESSION BEAN TEMPSAVE to SAVE: " + info.getId());
						tsodao.update(info);
					}
					System.out.println("SESSION BEAN info.getId() after:" + info.getId());
				}

				//财务处理
				Log.print("***保存财务处理之前***");
				accountbookoperation.saveSpecialOperation(info);
				Log.print("***保存财务处理之后***");

				//更改交易状态
				tsodao.updateStatus(info.getId(), tsDtmodify, SETTConstant.TransactionStatus.SAVE);
				//返回交易编号
				lReturn = info.getId();

			}
			else
			{
				System.out.println("the Stransno is not null,the action is modifysave");
				//交易编号非空
				boolean tag = false;
				tag = this.checkIsTouched(info);

				//判断状态是否合法
				Log.print("----------判断状态是否被非法修改过-开始-------------");
				TransSpecialOperationInfo newInfo = tsodao.findByID(info.getId());
				long lNewStatusID = newInfo.getNstatusid();
				String errMsg = UtilOperation.checkStatus(info.getNstatusid(), lNewStatusID, SETTConstant.Actions.MODIFYSAVE);
				//被修改过
				if (errMsg != null && !errMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, errMsg);
				}
				//判断是否被非法修改过										
				Log.print("----------判断是否被非法修改过-------------");
				if (this.checkIsTouched(info))
				{
					Log.print("----------被非法修改过,交易失败-------------");
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}
				Log.print("----------判断状态是否被非法修改过-结束-------------");

				if (tag == false)
				{
					//AccountBookOperation abo = new AccountBookOperation();
					System.out.println("modifysave begin!");

					java.sql.Timestamp tsDtmodify = null;
					
					tsDtmodify = Env.getSystemDateTime();
					info.setDtmodify(tsDtmodify);

					//取得数据库中数据
					TransSpecialOperationInfo tsoi = null;
					tsoi = this.findDetailByID(info.getId());
					Log.print("***删除财务处理之前***");
					accountbookoperation.deleteSpecialOperation(tsoi); // --AccountBookOperation.deleteSpecialOperation()没有该方法
					Log.print("***删除财务处理之后***");

					tsodao.update(info);

					Log.print("***保存财务处理之前***");
					accountbookoperation.saveSpecialOperation(info); // --该方法没有
					Log.print("***保存财务处理之后***");
					//修改交易状态
					tsodao.updateStatus(info.getId(), info.getDtmodify(), SETTConstant.TransactionStatus.SAVE);
					//返回交易编号
					lReturn = info.getId();
					System.out.println("modifysave end!");
				}
				else
				{
					System.out.println("CheckedIsTouched is true");
					throw new IRollbackException(this.getSessionContext(), "保存失败，请重试！");
				}
			}
			
			
			//外部账户处理
			if(info.getSextaccountno()!=null && info.getSextaccountno().trim().length()>0 && info.getSextclientname()!=null && info.getSextclientname().trim().length()>0)
			{
				AccountOperation accountOperation = new AccountOperation();
				
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
			
			
			/**
			 * 如果Info中的InutParameterInfo不为空,则需要提交审批 add by 刘琰 2007-04-17
			 */
				if(info.getInutParameterInfo()!=null)
				{
				Log.print("------提交审批--------");
				//设置返回的地址链接(交易id只能在交易保存之后加上,tempInfo.getUrl()得到的url没有具体的交易id)
				InutParameterInfo tempInfo = info.getInutParameterInfo();
				tempInfo.setUrl(tempInfo.getUrl()+lReturn);
				tempInfo.setTransID(transNo);//这里保存的是交易编号
				tempInfo.setDataEntity(info);
				
				//提交审批
				//FSWorkflowManager.initApproval(info.getInutParameterInfo());
				FSWorkflowManager.initApproval(tempInfo);
				//更新状态到审批中
				tsodao.updateStatus(info.getId(),info.getDtmodify(),SETTConstant.TransactionStatus.APPROVALING);
				Log.print("------提交审批成功--------");
				
				}
		}
		//modified by mzh_fu 2007/05/011
/*		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}*/
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(this.getSessionContext(), e.getMessage(), e);
		}
		return lReturn;
		}
	}
	/**
	 * 自动保存和复核
	 * Modify by Forest
	 * 1、调用活期保存的方法
	 * 2、调用活期复核的方法
	 * 3、更新连通的贷款表
	 * */
	public TransSpecialOperationAssembler saveAndCheckAutomatically(TransSpecialOperationAssembler assemble)throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		Log.print("===== start saveAndCheckAutomatically");
	    boolean AutoCreateBankInstruction = assemble.getSett_TransSpecialOperationInfo().isAutocreatebankinstruction();

		Log.print("===== start save");
        this.save(assemble.getSett_TransSpecialOperationInfo());
        Log.print("===== end save");
        
   
        assemble = new TransSpecialOperationAssembler(this.findDetailByID(assemble.getSett_TransSpecialOperationInfo().getId()));
        
        assemble.getSett_TransSpecialOperationInfo().setNinputuserid(assemble.getSett_TransSpecialOperationInfo().getNinputuserid());        
        assemble.getSett_TransSpecialOperationInfo().setScheckabstract("机核");
        assemble.getSett_TransSpecialOperationInfo().setNcheckuserid(Constant.MachineUser.CheckUser);
        
        assemble.getSett_TransSpecialOperationInfo().setAutocreatebankinstruction(AutoCreateBankInstruction);
       
        Log.print("===== start check");
        long res = this.check(assemble.getSett_TransSpecialOperationInfo());
        Log.print("===== end check");
        
        Log.print("===== end saveAndCheckAutomatically");
        
        return assemble;
		}
	}
	
	/**
	 * 通过ID查询特殊业务设置信息
	 * 逻辑操作：
	 *（1）调用方法：Sett_SpecialOperationDAO.findByID()
	 * @param lSettingID long 特殊业务设置信息的ID
	 * @return SpecialOperationInfo 特殊业务设置信息实体类。
	 * @throws Exception
	 */
	public SpecialOperationInfo findSettingDetailByID(long lSettingID) throws RemoteException, IRollbackException
	{
		Sett_SpecialOperationDAO sodao = new Sett_SpecialOperationDAO();
		SpecialOperationInfo soi = new SpecialOperationInfo();
		try
		{
			soi = sodao.findByID(lSettingID);
			return soi;
		}
/*		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}*/
		catch (Exception e)
		{
			throw new IRollbackException(this.getSessionContext(), "Gen_E001", e);
		}

	}

	/**
	 * 特殊交易复核
	 * 逻辑操作：
	 * （1）调用方法：this.checkIsTouched,判断要复核的记录是否被修改过，否则抛出异常"您要复核的单据已被修改，请检查！”。"
	 * （2）调用方法：AccountBookOperation.checkSpecialOperation()。进行复核的财务处理。
	 * （3）调用方法：Sett_TransSpecailOperationDAO.updateStatus。
	 * @param info TransSpecialOperationInfo 特殊交易实体类
	 * @return long 复核成功，返回交易ID；否则返回-1。
	 * @throws Exception
	 */
	public long check(TransSpecialOperationInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long lReturn = -1;
		AccountBookOperation accountbookoperation = new AccountBookOperation();
		try
		{
			boolean blnAutocreatebankinstruction = info.isAutocreatebankinstruction();
			
			AccountBookOperation abo = new AccountBookOperation();
			Sett_TransSpecialOperationDAO tsodao = new Sett_TransSpecialOperationDAO();

			//判断状态是否合法
			Log.print("----------判断状态是否被非法修改过-开始-------------");
			TransSpecialOperationInfo newInfo = tsodao.findByID(info.getId());
			long lNewStatusID = newInfo.getNstatusid();
			String errMsg = UtilOperation.checkStatus(info.getNstatusid(), lNewStatusID, SETTConstant.Actions.CHECK);
			//被修改过
			if (errMsg != null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			//判断是否被非法修改过										
			Log.print("----------判断是否被非法修改过-------------");
			if (this.checkIsTouched(info))
			{
				Log.print("----------被非法修改过,交易失败-------------");
				throw new IRollbackException(mySessionCtx, "Sett_E130");
			}
			Log.print("----------判断状态是否被非法修改过-结束-------------");

			java.sql.Timestamp tsDtmodify = null;
			
			tsDtmodify = Env.getSystemDateTime();
			String[] cols = { "Id", "ncheckuserid","scheckabstract" };
			tsodao.updatePartValues(cols, info);
			tsodao.updateStatus(info.getId(), tsDtmodify, SETTConstant.TransactionStatus.CHECK);
			info = this.findDetailByID(info.getId());
			
			if(info.getSinstructionno()!=null && info.getSinstructionno().length()>0)
			{
				Log.print("---------是网银指令----------");
				FinanceInfo financeInfo = new FinanceInfo();
				OBFinanceInstrDao financeDao = new OBFinanceInstrDao();
					
				financeInfo.setID(Long.valueOf(info.getSinstructionno()).longValue());
				financeInfo.setDealUserID(info.getNinputuserid());
				financeInfo.setConfirmDate(info.getDtexecute());
				financeInfo.setFinishDate(info.getDtexecute());
				financeInfo.setTransNo(info.getStransno());
				financeInfo.setStatus(OBConstant.SettInstrStatus.FINISH);
				financeDao.updateStatusAndTransNo(null,financeInfo);
			}
			//财务处理
			Log.print("*****###############*********");
			Log.print("***复核财务处理之前***");
			info.setAutocreatebankinstruction(blnAutocreatebankinstruction);
			accountbookoperation.checkSpecialOperation(info);
			Log.print("***复核财务处理之后***");
			//生成银行付款指令
			if (!(info.getNpaygeneralledgertypeid() > 0 && info.getNreceivegeneralledgertypeid() > 0))//如果付收双方均为总账业务则无需发送指令
			{
				//是否有银企接口
				boolean bIsValid = Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false);
				//是否需要生成银行指令
				boolean bCreateInstruction = false;
				long bankID = info.getNpaybankid();
				try {
					//调用此方法后，bankID的值变为银行类型ID
					bCreateInstruction = accountbookoperation.isCreateInstruction(bankID);
				} catch (Exception e1) {				
					Log.print("判断传入的银行ID是否需要生成银行指令出错！");
					e1.printStackTrace();
				}
				
				
				try
				{
					if(bIsValid && bCreateInstruction && info.isAutocreatebankinstruction()) {
						Log.print("*******************开始产生特殊业务付款指令，并保存**************************");
						try {
							Log.print("------开始特殊业务付款指令生成--------");
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
							
							Log.print("------生成特殊业务付款指令成功--------");
							
						} catch (Throwable e) {
							Log.print("生成特殊业务付款指令失败");
							e.printStackTrace();
							throw new IRollbackException(this.getSessionContext(), "生成特殊业务付款指令失败："+e.getMessage());
						}
					}	
					else {
						Log.print("没有银行接口或不需要生成银行指令！");
					}
				}
				catch (Exception e)
				{
					Log.print("-----保存特殊业务银行转账指令失败");
					throw new IRollbackException(this.getSessionContext(), "保存特殊业务银行转账指令出错！" + e.getMessage());
				}
			}
			lReturn = info.getId();
			return lReturn;

		}
		//modified by mzh_fu 2007/05/011
/*		catch (RemoteException e)
		{
			throw e; 
		}
		catch (IRollbackException e)
		{
			Log.print("*****###############*********");
			throw e;
		}*/
		catch (Exception e)
		{
			throw new IRollbackException(this.getSessionContext(), e.getMessage(), e);
		}
		}
	}

	/**
	 * 特殊交易取消复核
	 * 逻辑操作：
	 * （1）调用方法：this.checkIsTouched,判断要复核的记录是否被修改过，否则抛出异常" 您要取消复核的单据已被修改，请检查！"。
	 * （2）调用方法：AccountBookOperation.cancelCheckSpecialOperation()。进行取消复核的财务处理。
	 * （3）调用方法：Sett_TransSpecailOperationDAO.updateStatus。修改交易的状态为保存。
	 * @param info TransSpecialOperationInfo 特殊交易实体类
	 * @return long 取消复核成功，返回交易ID；否则返回-1。
	 * @throws Exception
	 */
	public long cancelCheck(TransSpecialOperationInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long lReturn = -1;
		AccountBookOperation accountbookoperation = new AccountBookOperation();
		try
		{
			Sett_TransSpecialOperationDAO tsodao = new Sett_TransSpecialOperationDAO();

			//判断状态是否合法
			Log.print("----------判断状态是否被非法修改过-开始-------------");
			TransSpecialOperationInfo newInfo = tsodao.findByID(info.getId());
			long lNewStatusID = newInfo.getNstatusid();
			String errMsg = UtilOperation.checkStatus(info.getNstatusid(), lNewStatusID, SETTConstant.Actions.CANCELCHECK);
			//被修改过
			if (errMsg != null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			//判断是否被非法修改过										
			Log.print("----------判断是否被非法修改过-------------");
			if (this.checkIsTouched(info))
			{
				Log.print("----------被非法修改过,交易失败-------------");
				throw new IRollbackException(mySessionCtx, "Sett_E130");
			}
			Log.print("----------判断状态是否被非法修改过-结束-------------");

			Log.print("----------更新系统状态-开始-------------");
			java.sql.Timestamp tsDtmodify = null;
			
			tsDtmodify = Env.getSystemDateTime();

			String[] cols = new String[3];
			cols[0] = "Id";
			cols[1] = "Ncheckuserid";
			cols[2] = "Scanclecheckabstract";
			
//			更新状态到：未复核或已审批(根据该业务是否有审批流判断)
			long lCancelCheckStatus = FSWorkflowManager.getSettCheckStatus(new InutParameterInfo(info.getNofficeid(),
					info.getNcurrencyid(),
					Constant.ModuleType.SETTLEMENT,
					info.getNoperationtypeid(),
					-1));
			
			tsodao.updatePartValues(cols, info);
			tsodao.updateStatus(info.getId(), tsDtmodify, lCancelCheckStatus);
			//清空复核人信息
			tsodao.updateCheckUser(info.getId(),tsDtmodify,-1);
			
			Log.print("----------更新系统状态-结束-------------");

			info = this.findDetailByID(info.getId());
			
			if(info.getSinstructionno()!=null && info.getSinstructionno().length()>0)
			{
				Log.print("---------是网银指令----------");
				FinanceInfo financeInfo = new FinanceInfo();
				OBFinanceInstrDao financeDao = new OBFinanceInstrDao();
				
				financeInfo.setID(Long.valueOf(info.getSinstructionno()).longValue());
				financeInfo.setDealUserID(info.getNinputuserid());
				financeInfo.setConfirmDate(info.getDtexecute());
				financeInfo.setFinishDate(info.getDtexecute());
				financeInfo.setTransNo(info.getStransno());
				financeInfo.setStatus(OBConstant.SettInstrStatus.DEAL);
				financeDao.updateStatusAndTransNo(null,financeInfo);
			}
			AccountBookOperation abo = new AccountBookOperation();
			Log.print("***取消复核财务处理之前***");
			accountbookoperation.cancelCheckSpecialOperation(info);
			Log.print("***取消复核财务处理之后***");

			lReturn = info.getId();
		}
		//modified by mzh_fu 2007/05/011
/*		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}*/
		catch (Exception e)
		{
			throw new IRollbackException(this.getSessionContext(), "存在未撤销的指令,无法取消复核", e);
		}
		return lReturn;
		}
	}

	/**
	 * 特殊交易复核匹配
	 * 逻辑操作：
	 * 		调用方法：Sett_TransSpecailOperationDAO.match()方法。
	 * @param info TransSpecialOperationInfo 特殊交易实体类（条件）
	 * @return Collection 包含特殊交易实体类的集合
	 * @throws Exception
	 */
	public TransSpecialOperationInfo match(TransSpecialOperationInfo info, long specialoperationinfoid) throws RemoteException, IRollbackException
	{
		Sett_TransSpecialOperationDAO tsodao = new Sett_TransSpecialOperationDAO();
		TransSpecialOperationInfo tsoi = new TransSpecialOperationInfo();
		Vector v = new Vector();
		try
		{
			System.out.println("specialoperationinfoid: " + specialoperationinfoid);
			if(specialoperationinfoid>0)
			{
				SpecialOperationInfo soi = this.findSettingDetailByID(specialoperationinfoid);
			
				if (soi == null)
				{
					System.out.println("SpecialOperationInfo is null");
					return null;
				}
				else
				{
					System.out.println("SpecialOperationInfo has value");
					//		控制匹配复核状态
					info.setNstatusid(FSWorkflowManager.getSettCheckStatus(new InutParameterInfo(info.getNofficeid(),
																				info.getNcurrencyid(),
																				Constant.ModuleType.SETTLEMENT,
																				info.getNoperationtypeid(),
																				-1)));
					tsoi = tsodao.match(info, soi);
				}
			}
			else{

				Sett_SpecialOperationDAO operationDao=new Sett_SpecialOperationDAO();
				
				SpecialOperationInfo operationInfo=operationDao.findByTranActionTypeID(info.getNtransactiontypeid());
				

				if(operationInfo==null)
				{
					System.out.println("SpecialOperationInfo is null");
					return null;
					
				}
				else
				{
					
					//		控制匹配复核状态
					info.setNstatusid(FSWorkflowManager.getSettCheckStatus(new InutParameterInfo(info.getNofficeid(),
																				info.getNcurrencyid(),
																				Constant.ModuleType.SETTLEMENT,
																				info.getNoperationtypeid(),
																				-1)));
					
					tsoi = tsodao.match(info, operationInfo);
				}
				
				
				
			}

		}
/*		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}*/
		catch (Exception e)
		{
			throw new IRollbackException(this.getSessionContext(), "Gen_E001", e);
		}
		return tsoi;
	}

	/**
	 * 按状态查询（链接查找）
	 * 逻辑操作：
	 *    调用方法：Sett_TransSpecailOperationDAO.findByStatus()方法。
	 * @param info QueryByStatusConditionInfo 按状态查询条件实体类
	 * @return Vector 包含特殊交易实体类的集合
	 * @throws Exception
	 */

	public Vector findByStatus(QueryByStatusConditionInfo info, String strOrderByName, boolean isDesc) throws RemoteException, IRollbackException
	{
		Sett_TransSpecialOperationDAO tsodao = new Sett_TransSpecialOperationDAO();
		Vector v = new Vector();
		try
		{

			v = tsodao.findByStatus(info, strOrderByName, isDesc);
			//			if (v == null)
			//			{
			//				System.out.println("TransSpecialEJB'S FindByStatus Method Result is null");
			//			}
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
			throw new IRollbackException(this.getSessionContext(), "Gen_E001", e);
		}
		return v;
	}
	
	/**
	 * 按特殊业务子类型和状态查询（链接查找）
	 * 逻辑操作：
	 *    调用方法：Sett_TransSpecailOperationDAO.findBySubtransSpecialIDandStatus()方法。
	 * @param info QueryByStatusConditionInfo 按状态查询条件实体类
	 * @return Vector 包含特殊交易实体类的集合
	 * @throws Exception
	 */

	public Vector findBySubtransSpecialIDandStatus(QueryBySubSpecialTypeAndStatusInfo info, String strOrderByName, boolean isDesc) throws RemoteException, IRollbackException
	{
		Sett_TransSpecialOperationDAO tsodao = new Sett_TransSpecialOperationDAO();
		Vector v = new Vector();
		try
		{

			v = tsodao.findBySubtransSpecialIDandStatus(info, strOrderByName, isDesc);
			//			if (v == null)
			//			{
			//				System.out.println("TransSpecialEJB'S FindByStatus Method Result is null");
			//			}
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
			throw new IRollbackException(this.getSessionContext(), "Gen_E001", e);
		}
		return v;
	}
	/**
	 * 根据ID查询特殊交易详细
	 * 逻辑操作：
	 *（1）调用方法：Sett_TransSpecailOperationDAO.findByID()
	 * @param lTransID long 特殊交易ID
	 * @return TransSpecialOperationInfo 特殊交易实体类
	 * @throws Exception
	 */
	public TransSpecialOperationInfo findDetailByID(long lTransID) throws RemoteException, IRollbackException
	{
		//System.out.println("SessionBEAN findDetailByID lTransID:"+lTransID);		
		Sett_TransSpecialOperationDAO tsodao = new Sett_TransSpecialOperationDAO();
		TransSpecialOperationInfo tsoi = new TransSpecialOperationInfo();
		try
		{
			tsoi = tsodao.findByID(lTransID);
			if (tsoi.getNpayloannoteid() > 0)
			{
				tsoi.setNpaysubaccountid(-1);
			}
			if (tsoi.getNreceiveloannoteid() > 0)
			{
				tsoi.setNreceivesubaccountid(-1);
			}
			//System.out.println("Ntransactiontypeid:"+tsoi.getNtransactiontypeid());
		}
		catch (RemoteException e)
		{
			//System.out.println("RemoteException:"+e.getMessage());
			throw e;
		}
		catch (IRollbackException e)
		{
			//System.out.println("IRollbackException:"+e.getMessage());
			throw e;
		}
		catch (Exception e)
		{
			//System.out.println("Exception:"+e.getMessage());
			e.printStackTrace();
			throw new IRollbackException(this.getSessionContext(), "Gen_E001", e);
		}
		return tsoi;
	}
	/**
	 * 审批方法。
	 * @param info
	 * @return long
	 * @throws IRollbackException
	 */
	public long doApproval(TransSpecialOperationInfo info)throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long depositId = -1;
		InutParameterInfo returnInfo = new InutParameterInfo();
		Sett_TransSpecialOperationDAO dao = new Sett_TransSpecialOperationDAO();
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
		depositId =info.getId();
		try
		{
			TransSpecialOperationInfo depositInfo = new TransSpecialOperationInfo();
			depositInfo = this.findDetailByID(info.getId());
			inutParameterInfo.setDataEntity(depositInfo);
			//提交审批
			returnInfo = FSWorkflowManager.doApproval(inutParameterInfo);
			//如果是最后一级,且为审批通过,更新状态为已审批
			if(returnInfo.isLastLevel())
			{	
				dao.updateStatus(
					info.getId(),
					info.getDtmodify(),
					SETTConstant.TransactionStatus.APPROVALED);
				//如果是自动复核
				if(FSWorkflowManager.isAutoCheck())
				{
					//构造check参数
					TransSpecialOperationInfo depositInfo1 = new TransSpecialOperationInfo();
					depositInfo1 = this.findDetailByID(info.getId());
					//depositInfo.setCheckUserID(assemble.getSett_TransCurrentDepositInfo().getInputUserID());			        
					//depositInfo1.setSabstract("机核");
					depositInfo1.setNcheckuserid(returnInfo.getUserID());					
					
					//TransCurrentDepositAssembler dataEntity = new TransCurrentDepositAssembler(depositInfo);
					
					//调用openCheck方法
					this.check(depositInfo1);
				}	
			}
			//	如果是最后一级,且为审批拒绝,更新状态为已保存
			else if(returnInfo.isRefuse())
			{
				dao.updateStatus(
						info.getId(),
						info.getDtmodify(),
						SETTConstant.TransactionStatus.SAVE);
			}
		}
		//modified by mzh_fu 2007/05/011
/*		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}*/
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return depositId;
		}
	}
	
	/**
	 * 取消审批方法（特殊）。如果是自动复核，则取消审批之前必须先取消复核，如果是手动复核，则只需取消审批
	 * 取消复核：调用ejb的取消复核方法
	 * 取消审批：将业务记录状态置为已保存即可
	 * @param tsoi
	 * @return long
	 * @throws IRollbackException
	 */
	public long cancelApproval(TransSpecialOperationInfo tsoi)throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long lReturn = -1;
		Sett_TransSpecialOperationDAO dao = new Sett_TransSpecialOperationDAO();
		InutParameterInfo inutParameterInfo = tsoi.getInutParameterInfo();
		try
		{
			//如果系统内设定为自动动复核，则需要先取消复核，然后取消审批
			if(FSWorkflowManager.isAutoCheck() && tsoi.getNstatusid()==SETTConstant.TransactionStatus.CHECK)
			{
				//取消复核
				this.cancelCheck(tsoi);
				//取消审批
				lReturn = dao.updateStatus(tsoi.getId(),tsoi.getDtmodify(), SETTConstant.TransactionStatus.SAVE);
			}
			else if( !FSWorkflowManager.isAutoCheck() && tsoi.getNstatusid()==SETTConstant.TransactionStatus.APPROVALED)
			{
				//取消审批
				lReturn = dao.updateStatus(tsoi.getId(),tsoi.getDtmodify(), SETTConstant.TransactionStatus.SAVE);
			}
			
			//将审批记录表内的该交易的审批记录状态置为无效
			if(inutParameterInfo.getApprovalEntryID()>0)
			{
				FSWorkflowManager.cancelApprovalRecord(inutParameterInfo);
			}							
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return lReturn;
		}
	}
}
