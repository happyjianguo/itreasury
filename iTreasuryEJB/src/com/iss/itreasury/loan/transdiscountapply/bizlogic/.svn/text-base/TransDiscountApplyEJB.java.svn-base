/*
 * Created on 2004-8-3
 *
 * Title:				iTreasury
 * @author             	yfan 
 * Company:             iSoftStone
 * Copyright:           Copyright (c) 2003
 * @version
 * Description:         
 */
package com.iss.itreasury.loan.transdiscountapply.bizlogic;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.iss.itreasury.bill.bizdelegation.DraftDelegation;
import com.iss.itreasury.bill.draft.bizlogic.Draft;
import com.iss.itreasury.bill.draft.bizlogic.DraftHome;
import com.iss.itreasury.bill.draft.dao.DiscountContractBillDao;
import com.iss.itreasury.bill.draft.dao.TransDraftOutDao;
import com.iss.itreasury.bill.draft.dataentity.DiscountContractBillInfo;
import com.iss.itreasury.bill.draft.dataentity.TransDraftOutInfo;
import com.iss.itreasury.bill.draft.dataentity.assemble.DraftStorageAssembleInfo;
import com.iss.itreasury.bill.util.BILLConstant;
import com.iss.itreasury.bill.util.BillException;
import com.iss.itreasury.bill.util.UtilOperation;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.base.LoanException;
import com.iss.itreasury.loan.transdiscountapply.dao.TransDiscountApplyBillDAO;
import com.iss.itreasury.loan.transdiscountapply.dao.TransDiscountApplyDAO;
import com.iss.itreasury.loan.transdiscountapply.dataentity.SelectBillInfo;
import com.iss.itreasury.loan.transdiscountapply.dataentity.TransDiscountApplyBillInfo;
import com.iss.itreasury.loan.transdiscountapply.dataentity.TransDiscountApplyInfo;
import com.iss.itreasury.loan.transdiscountapply.dataentity.TransDiscountApplyQueryInfo;
import com.iss.itreasury.loan.transdiscountcontract.dao.TransDiscountContractBillDAO;
import com.iss.itreasury.loan.transdiscountcontract.dao.TransDiscountContractDAO;
import com.iss.itreasury.loan.transdiscountcontract.dataentity.TransDiscountContractBillInfo;
import com.iss.itreasury.loan.transdiscountcontract.dataentity.TransDiscountContractInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.system.approval.bizlogic.InutApprovalRelationBiz;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.system.approval.dataentity.InutApprovalRelationInfo;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.system.bizdelegation.ApprovalDelegation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.EJBObject;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;


/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TransDiscountApplyEJB implements SessionBean
{
	/* Methods required by SessionBean Interface. EJB 1.1 section 6.5.1. */
	/**
	 * @see javax.ejb.SessionBean#setContext(javax.ejb.SessionContext)
	 */
	public void setSessionContext(SessionContext context)
	{
		this.context = context;
	}
	private SessionContext context;
	private static Log4j log4j = null;
	/**
	 * No argument constructor required by container.
	 */
	public TransDiscountApplyEJB()
	{
		log4j = new Log4j(Constant.ModuleType.LOAN, this);
	}
	/**
	 * Create method specified in EJB 1.1 section 6.10.3
	 */
	public void ejbCreate()
	{
	}
	/**
	 * @see javax.ejb.SessionBean#ejbActivate()
	 */
	public void ejbActivate()
	{
	}
	/**
	 * @see javax.ejb.SessionBean#ejbPassivate()
	 */
	public void ejbPassivate()
	{
	}
	/**
	 * @see javax.ejb.SessionBean#ejbRemove()
	 */
	public void ejbRemove()
	{
	}
    
    private String TransDiscountApplyTableName = "";
    private String TransDiscountApplyBillTableName = "";
	
	/**
	 *申请书的保存操作
	*/
	public long save(TransDiscountApplyInfo info) throws java.rmi.RemoteException, LoanException
	{
        long id = -1;
        Timestamp Date = Env.getSystemDateTime();
        try
        {
            TransDiscountApplyDAO dao = new TransDiscountApplyDAO(this.getTransDiscountApplyTableName());
            
            if(info.getId() <= 0)
            {
                //将下一个审批级别设为1
                info.setNextCheckLevel(1);
                info.setStatusId(LOANConstant.LoanStatus.SAVE);
                //info.setApplyCode(dao.createApplyCode());
                info.setApplyCode(dao.createApplyCode(
                                info.getBorrowClientId(),
                                info.getOfficeId(),
                                info.getInOrOut()));
                Log.print("Code:"+info.getApplyCode());
                info.setInputDate(Date);
                //info.setStartDate(info.getDiscountDate());
                
                dao.setUseMaxID();//取MAX_ID
                
                id = dao.add(info);
                Log.print("id:"+id);
            }
            else
            {
            	TransDiscountApplyInfo aInfo = new TransDiscountApplyInfo();
            	aInfo = dao.findByID(info.getId());
            	
            	//中油－－－允许对已审核的申请修改后再次提交，此时需要执行下列操作
                if (aInfo.getStatusId()==LOANConstant.LoanStatus.CHECK)
                {
                	//删除审批记录
                	ApprovalTracingInfo ATInfo = new ApprovalTracingInfo();
                	//ATInfo.setActionID(LOANConstant.CODE_EXAMINE_TYPE_TRANSDISCOUNT);
                	ATInfo.setApprovalContentID(info.getId());
                	dao.deleteReviewOpinion(ATInfo);
                	//修改对应合同的状态
                	System.out.println("－－将对应合同记录置为无效状态");
                	TransDiscountContractDAO cDao = new TransDiscountContractDAO("Loan_ContractForm");
                	TransDiscountContractInfo cInfo =new TransDiscountContractInfo();
                	cInfo.setId(cDao.findContractByApplyID(info.getId()).getId());
                	cInfo.setStatusId(Constant.RecordStatus.INVALID);
                	cDao.update(cInfo);
                	//将该合同置为已提交状态
                	info.setStatusId(LOANConstant.LoanStatus.SUBMIT);
                }
                dao.update(info);
                id = info.getId();
            }
        }
        catch (LoanException e)
        {
            e.printStackTrace();
            //modified by mzh_fu 2007/08/07
            //throw new LoanException("Loan_E100",e);
            throw new LoanException("Loan_E100", e,context);
        }
        catch (ITreasuryDAOException e)
        {
            e.printStackTrace();
            //modified by mzh_fu 2007/08/07
            //throw new LoanException("Loan_E100",e);
            throw new LoanException("Loan_E100", e,context);
        }
        catch (RemoteException e)
        {
            // TODO Auto-generated catch block
            //modified by mzh_fu 2007/08/07
            //throw new LoanException("Loan_E100",e);
            throw new LoanException("Loan_E100", e,context);
        }
        
	    return id;
	}

	/**
	 *申请书的删除操作
	*/
	public void delete(long lID) throws java.rmi.RemoteException, LoanException
	{
        TransDiscountApplyInfo info = new TransDiscountApplyInfo();
        try
        {            
            TransDiscountApplyDAO dao = new TransDiscountApplyDAO(this.getTransDiscountApplyTableName());
            info.setId(lID);
            info.setStatusId(Constant.RecordStatus.INVALID);//状态置为无效
            dao.update(info);
        }
        catch (ITreasuryDAOException e)
        {
            e.printStackTrace();
            //modified by mzh_fu 2007/08/07
           // throw new LoanException("Loan_E100",e);
            throw new LoanException("Loan_E100", e,context);
        }
	}

	/**
	 *申请书的审核操作
	*/
	public void check(ApprovalTracingInfo ATInfo) throws java.rmi.RemoteException, LoanException
	{
        TransDiscountApplyDAO dao = new TransDiscountApplyDAO(this.getTransDiscountApplyTableName());

        long lCount = 0;
        long lSerialID = -1;
        long lStatusID = -1;
        long lResultID = -1;
        long[] lApprovalContentIDList;
        String strSQL = "";

        //定义相应操作常量
        //模块类型
        long lModuleID = ATInfo.getModuleID();
        //业务类型
        long lLoanTypeID = ATInfo.getLoanTypeID();
        //操作类型
        long lActionID = ATInfo.getActionID();

        long lApprovalContentID = ATInfo.getApprovalContentID();
        long lNextUserID = ATInfo.getNextUserID();
        long lApprovalID = ATInfo.getApprovalID();
        long lUserID = ATInfo.getInputUserID();
        String sOpinion = ATInfo.getOpinion();

        ApprovalTracingInfo info = new ApprovalTracingInfo();
        ApprovalDelegation appbiz = new ApprovalDelegation();

        lApprovalContentIDList = ATInfo.getApprovalContentIDList();

        if (lApprovalContentIDList.length > 0)
        {
            try
            {
                //获得ApprovalID
                if (lApprovalID <= 0)
                {
                    lApprovalID = appbiz.getApprovalID(lModuleID, lLoanTypeID, lActionID,ATInfo.getOfficeID(),ATInfo.getCurrencyID());
                }
            }
            catch (Exception e1)
            {
                log4j.error("getApprovalID fail");
                e1.printStackTrace();
            }

            //处理审批意见
            if (ATInfo.getCheckActionID() == LOANConstant.Actions.REJECT) //拒绝
            {
                //审批意见状态
                lStatusID = Constant.RecordStatus.VALID;
                //审批操作类型
                lResultID = Constant.ApprovalDecision.REFUSE;
            }
            if (ATInfo.getCheckActionID() == LOANConstant.Actions.CHECK) //审批
            {
                lStatusID = Constant.RecordStatus.VALID;
                lResultID = Constant.ApprovalDecision.PASS;
            }
            if (ATInfo.getCheckActionID() == LOANConstant.Actions.CHECKOVER) //审批&&最后
            {
                lStatusID = Constant.RecordStatus.VALID;
                lResultID = Constant.ApprovalDecision.FINISH;
                //审批完成后需要做的操作
            }
            if (ATInfo.getCheckActionID() == LOANConstant.Actions.RETURN) //修改
            {
                lStatusID = Constant.RecordStatus.VALID;
                lResultID = Constant.ApprovalDecision.RETURN;
            }
            ATInfo.setApprovalID(lApprovalID);
            ATInfo.setResultID(lResultID);
            ATInfo.setStatusID(lStatusID);

            lCount = lApprovalContentIDList.length;
            Log.print("lCount="+lCount);
            for (int i = 0; i < lCount; i++)
            {
                if (lApprovalContentIDList[i] > 0)
                {
                    /*
                    if (ATInfo.getNextLevel() <= 0)
                    {
                        ApplyInfo aInfo = new ApplyInfo();
                        try
                        {
                            aInfo = (ApplyInfo)dao.findByID(lApprovalContentIDList[i],aInfo.getClass());
                        } 
                        catch (ITreasuryDAOException e2)
                        {
                            e2.printStackTrace();
                        }
                        ATInfo.setNextLevel(aInfo.getNextCheckLevel());
                    }
                    */
                    Log.print("i="+i);
                    ATInfo.setApprovalContentID(lApprovalContentIDList[i]);
                    Log.print("ATInfo.getApprovalContentID()=" + ATInfo.getApprovalContentID());

                    //审核申请书
                    dao.check(ATInfo);

                    log4j.debug("saveApprovalTracing begin");
                    try
                    {
                        appbiz.saveApprovalTracing(ATInfo);
                    }
                    catch (Exception e)
                    {
                        log4j.error("saveApprovalTracing fail");
                       // e.printStackTrace();
                        //modified by mzh_fu 2007/08/07
                        throw new LoanException("Loan_E100",e,context);
                    }
                    log4j.debug("saveApprovalTracing end");
                }
            }
        }
    }

	/**
		 * lazhang add method
		 * @param  ApprovalTracingInfo info
		 * @throws java.rmi.RemoteException
		 * @throws LoanException
		 */
		public void cpfCheck(ApprovalTracingInfo info) throws java.rmi.RemoteException, LoanException
		{
			TransDiscountApplyDAO dao = new TransDiscountApplyDAO(this.getTransDiscountApplyTableName());
						
			long lStatusID = -1; //审核记录状态：有效、无效
			long lResultID = -1; //ApprovalDecision 审核决策
			long[] lApprovalContentIDList; //批量审核内容标识数组
			long lCount = 0;

			if (info.getCheckActionID() == LOANConstant.Actions.REJECT) //拒绝
			{
				lStatusID = Constant.RecordStatus.INVALID;
				lResultID = Constant.ApprovalDecision.REFUSE;
			}
			if (info.getCheckActionID() == LOANConstant.Actions.CHECK) //审批
			{
				lStatusID = Constant.RecordStatus.VALID;
				lResultID = Constant.ApprovalDecision.PASS;
			}
			if (info.getCheckActionID() == LOANConstant.Actions.CHECKOVER) //审批&&最后
			{
				lStatusID = Constant.RecordStatus.VALID;
				lResultID = Constant.ApprovalDecision.FINISH;
			}
			if (info.getCheckActionID() == LOANConstant.Actions.RETURN) //修改
			{
				lStatusID = Constant.RecordStatus.VALID;
				lResultID = Constant.ApprovalDecision.RETURN;
			}

			//info设置
			info.setResultID(lResultID);
			info.setStatusID(lStatusID);

			lApprovalContentIDList = info.getApprovalContentIDList();
			lCount = lApprovalContentIDList.length;
			for (int i = 0; i < lCount; i++)
			{
				if (lApprovalContentIDList[i] > 0)
				{
					info.setApprovalContentID(lApprovalContentIDList[i]);
					//Log.print("ATInfo.getApprovalContentID()="+info.getApprovalContentID());
				}
				else
				{
					break;
				}
				//审核申请书
				dao.cpfCheckApply(info);
				
				log4j.debug("saveReviewOpinion begin");
				try
				{
					dao.addReviewOpinion(info);
				}
				catch (Exception e)
				{
					log4j.error("saveReviewOpinion fail");
					
					//modified by mzh_fu 2007/08/07
					//e.printStackTrace();
                    throw new LoanException(e.getMessage(),e,context);
				}
				log4j.debug("saveReviewOpinion end");
			}
		}


	/**
	 *申请书的取消操作
	*/
	public void cancel(long lID) throws java.rmi.RemoteException, LoanException
	{
        TransDiscountApplyInfo info = new TransDiscountApplyInfo();
        //TransDiscountApplyBillInfo binfo = new TransDiscountApplyBillInfo();
        Collection cb = null;
        long[] lListBillId = new long[1000];
        int i = 0;
        try
        {            
            TransDiscountApplyDAO dao = new TransDiscountApplyDAO(this.getTransDiscountApplyTableName());
                        
            //如果是卖出，删除票据关系表
            info = dao.findByID(lID);
            if(info.getInOrOut() == LOANConstant.TransDiscountInOrOut.OUT)
            {
                cb = dao.findOutBillByTransDiscountApplyID(lID);
                if( (cb != null) && (cb.size() > 0) )
                {
                    Iterator it = cb.iterator();
                    while (it.hasNext() )
                    {
                        TransDiscountApplyBillInfo binfo = ( TransDiscountApplyBillInfo ) it.next();
                        lListBillId[i]=binfo.getId();
                        i++;
                    }
                }
                SelectBillInfo sinfo = new SelectBillInfo();
                sinfo.setTransDiscountApplyID(lID);
                sinfo.setAllBillID(lListBillId);
                sinfo.setIsSubmit(Constant.YesOrNo.YES);
                dao.deleteOutBill(sinfo);
            }
            
			/*  TOCONFIG—TODELETE  */
			/*
			 * 产品化不再区分项目 
			 * ninh 
			 * 2005-03-24
			 */

            //中油－－如果是已审核状态的申请将对应的合同置为无效，国机－－不存在已审核的申请还能修改再提交的情况
//            if (Env.getProjectName().equals("cpf"))
//            {
//            	System.out.println("status:" + info.getStatusId());
//            	if (info.getStatusId()==LOANConstant.LoanStatus.CHECK)
//            	{
//            		System.out.println("－－中油－－－将对应合同记录置为无效状态");
//            		TransDiscountContractDAO cDao = new TransDiscountContractDAO("Loan_ContractForm");
//            		TransDiscountContractInfo cInfo =new TransDiscountContractInfo();
//            		cInfo.setId(cDao.findContractByApplyID(info.getId()).getId());
//            		cInfo.setStatusId(Constant.RecordStatus.INVALID);
//            		cDao.update(cInfo);
//            	}
//            }
            
            TransDiscountApplyInfo aInfo = new TransDiscountApplyInfo();
            aInfo.setId(lID);

//            if (Env.getProjectName().equals("cpf"))
//            {
//            	aInfo.setStatusId(Constant.RecordStatus.INVALID);
//            }
//            else aInfo.setStatusId(LOANConstant.LoanStatus.CANCEL);//更新状态为已取消

			aInfo.setStatusId(LOANConstant.LoanStatus.CANCEL);//更新状态为已取消
			
			/*  TOCONFIG—END  */
			
            dao.update(aInfo);
        }
        catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
        	
            e.printStackTrace();
            
            //modified by mzh_fu 2007/08/07
            //throw new LoanException("Loan_E100",e);
            throw new LoanException("Loan_E100",e,context);
        }
	}
    /**
     *申请书的单笔查询操作
    */
    public TransDiscountApplyBillInfo findBillByID(long lID) throws java.rmi.RemoteException, LoanException
    {
        TransDiscountApplyBillInfo info = new TransDiscountApplyBillInfo();
        try
        {
            TransDiscountApplyBillDAO bdao = new TransDiscountApplyBillDAO(this.getTransDiscountApplyBillTableName());
            
            info = (TransDiscountApplyBillInfo)bdao.findByID(lID);//,info.getClass());

        }
        /*
        catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }//*/
        //*
        catch (LoanException e)
        {
            e.printStackTrace();
            throw new LoanException("Loan_E100",e);
        }
        catch (RemoteException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //*/
        return info;
    }



	/**
	 *申请书的单笔查询操作
	*/
	public TransDiscountApplyInfo findByID(long lID) throws java.rmi.RemoteException, LoanException
	{
        TransDiscountApplyInfo info = new TransDiscountApplyInfo();
        try
        {
            TransDiscountApplyDAO dao = new TransDiscountApplyDAO(this.getTransDiscountApplyTableName());
            //TransDiscountApplyBillDAO bdao = new TransDiscountApplyBillDAO();
            
            //info = (TransDiscountApplyInfo)dao.findByID(lID,info.getClass());

            info = (TransDiscountApplyInfo)dao.findByID(lID);
            
            /*
            if(info.getInOrOut() == LOANConstant.TransDiscountInOrOut.IN)//买入
            {
                info.setBill(bdao.findBillByTransDiscountApplyID(lID));
            }
            else//卖出
            {
                info.setBill(bdao.findBillByTransDiscountApplyID(lID));
            }//*/
        }
        /*
        catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }//*/
        //*
        catch (LoanException e)
        {
            e.printStackTrace();
            throw new LoanException("Loan_E100",e);
        }
        catch (RemoteException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //*/
	    return info;
	}

	/**
	 *申请书的多笔查询操作
	*/
	public Collection findByMultiOption(TransDiscountApplyQueryInfo qInfo) throws java.rmi.RemoteException, LoanException
	{
        Collection c = null;
        try
        {
            TransDiscountApplyDAO dao = new TransDiscountApplyDAO(this.getTransDiscountApplyTableName());
            c=dao.findByMultiOption(qInfo);
        }
        catch (LoanException e)
        {
            e.printStackTrace();
            throw new LoanException("Loan_E100",e);
        }
        catch (RemoteException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
         
        return c;
    }

	/**
	 *申请书下的票据保存操作
	*/
	public long saveBill(TransDiscountApplyBillInfo info) throws java.rmi.RemoteException, LoanException
	{
        long id = -1;
        
        try
        {
            TransDiscountApplyDAO dao = new TransDiscountApplyDAO(this.getTransDiscountApplyTableName());
            TransDiscountApplyBillDAO bdao = new TransDiscountApplyBillDAO(this.getTransDiscountApplyBillTableName());
            
            
            if(info.getInOrOut() == LOANConstant.TransDiscountInOrOut.IN)
            {
                if(info.getId() <= 0)
                {
                    //info.setApplyCode(dao.createApplyCode(info.getBorrowClientId(),info.getOfficeId()));
                    //设置下一个序列号
                    info.setSerialNo(bdao.findMaxTransDiscountApplyBillSerialNo(info.getLoanID())+1);
                    info.setStatusID(Constant.RecordStatus.VALID);
                    bdao.setUseMaxID();//取MAX_ID
                
                    id = bdao.add(info);
                }
                else
                {
                    bdao.update(info);
                    id = info.getId();
                }
            }
            else if(info.getInOrOut() == LOANConstant.TransDiscountInOrOut.OUT)
            {
                if(info.getId() > 0 && info.getLoanID() > 0)
                {
                    dao.saveOutBill(info);
                }
            }
            
            //System.out.println("bill's loanID:"+info.getLoanID());
            if (info.getLoanID()>0)           dao.updateEndDate(info.getLoanID());
            /*
            //更新到期日
            if(info.getLoanID() > 0)
            {
                TransDiscountApplyInfo aInfo = new TransDiscountApplyInfo();
                TransDiscountApplyInfo aInfo2 = new TransDiscountApplyInfo();
                //TransDiscountApplyBillInfo bInfo = new TransDiscountApplyBillInfo();
                
                aInfo = dao.findByID(info.getLoanID());
                //bInfo = bdao.findByID(id);
                
                long statusId = aInfo.getStatusId();
                Timestamp oldEndDate = aInfo.getEndDate();
                Timestamp endDate = info.getEnd();
                
                if (oldEndDate == null && endDate != null)
                {
                    aInfo2.setId(info.getLoanID());
                    aInfo2.setEndDate(endDate);
                    dao.update(aInfo2);
                }
                else if (oldEndDate != null && endDate != null && oldEndDate.before(endDate))
                {
                    aInfo2.setId(info.getLoanID());
                    aInfo2.setEndDate(endDate);
                    dao.update(aInfo2);
                }                
            }
            */

        }
        catch (ITreasuryDAOException e)
        {
            e.printStackTrace();
            //modified by mzh_fu 2007/08/07								
            //throw new LoanException("Loan_E100",e);
            throw new LoanException("Loan_E100", e,context);
        }
        catch (RemoteException e)
        {
            // TODO Auto-generated catch block
        	//modified by mzh_fu 2007/08/07								
            //e.printStackTrace();
        	throw new LoanException("Loan_E100", e,context);
        }
        
        return id;
    }

	/**
	 *申请书下的票据查询操作
	*/
	public Collection findBillByTransDiscountApplyID(long lTransDiscountApplyID ,long lInOrOut) throws java.rmi.RemoteException, LoanException
	{
        Collection c = null;
        try
        {            
            if(lInOrOut == LOANConstant.TransDiscountInOrOut.IN)
            {
                TransDiscountApplyBillDAO bdao = new TransDiscountApplyBillDAO(this.getTransDiscountApplyBillTableName());
                c=bdao.findBillByTransDiscountApplyID(lTransDiscountApplyID);
            }
            else if(lInOrOut == LOANConstant.TransDiscountInOrOut.OUT)
            {
                TransDiscountApplyDAO dao = new TransDiscountApplyDAO(this.getTransDiscountApplyTableName());
                c=dao.findOutBillByTransDiscountApplyID(lTransDiscountApplyID);
            } 
        }
        catch (LoanException e)
        {
            e.printStackTrace();
            throw new LoanException("Loan_E100",e);
        }
        catch (RemoteException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
         
	    return c;
	}

	/**
	 *申请书下的票据删除操作
	*/
	public void deleteBill(SelectBillInfo selectBillInfo) throws java.rmi.RemoteException, LoanException
	{
       try
        {
            if(selectBillInfo.getInOrOut() == LOANConstant.TransDiscountInOrOut.IN)
            {
                TransDiscountApplyBillDAO bdao = new TransDiscountApplyBillDAO(this.getTransDiscountApplyBillTableName());
                
                bdao.deleteBill(selectBillInfo);
            }
            else if(selectBillInfo.getInOrOut() == LOANConstant.TransDiscountInOrOut.OUT)
            {
                TransDiscountApplyDAO dao = new TransDiscountApplyDAO(this.getTransDiscountApplyTableName());

                selectBillInfo.setIsSubmit(Constant.YesOrNo.YES);
                
                dao.deleteOutBill(selectBillInfo);
            }
            if (selectBillInfo.getTransDiscountApplyID()>0)
            {
            	TransDiscountApplyDAO adao = new TransDiscountApplyDAO(this.getTransDiscountApplyTableName());
            	adao.updateEndDate(selectBillInfo.getTransDiscountApplyID());
            }
        }
        catch (LoanException e)
        {
            e.printStackTrace();
            //modified by mzh_fu 2007/08/07
            //throw new LoanException("Loan_E100",e);
            throw new LoanException("Loan_E100", e,context);
        }
        catch (RemoteException e)
        {
            // TODO Auto-generated catch block
        	//modified by mzh_fu 2007/08/07
           // e.printStackTrace();
        	throw new LoanException("Loan_E100", e,context);
        }
	    
	}

	/**
	 *票据查询操作
	*/
	public Collection findTransDiscountApplyBill(TransDiscountApplyQueryInfo qInfo) throws java.rmi.RemoteException, LoanException
	{
        Collection c = null;
        try
        {
            TransDiscountApplyDAO dao = new TransDiscountApplyDAO(this.getTransDiscountApplyTableName());
            c=dao.findTransDiscountApplyBill(qInfo);
        }
        catch (LoanException e)
        {
            e.printStackTrace();
            throw new LoanException("Loan_E100",e);
        }
        catch (RemoteException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
         
        return c;
	}

	/**
	 *票据选择操作
	*/
	public void saveTransDiscountApplyBill(SelectBillInfo selectBillInfo) throws java.rmi.RemoteException, LoanException
	{
        try
        {
            TransDiscountApplyDAO dao = new TransDiscountApplyDAO(this.getTransDiscountApplyBillTableName());
            
            dao.saveTransDiscountApplyBill(selectBillInfo);
            
            //更新申请的结束日期
            TransDiscountApplyDAO adao = new TransDiscountApplyDAO(this.getTransDiscountApplyTableName());
            if (selectBillInfo.getTransDiscountApplyID()>0 ) adao.updateEndDate(selectBillInfo.getTransDiscountApplyID());
        }
        catch (LoanException e)
        {
            e.printStackTrace();
            //modified by mzh_fu 2007/08/07								
            //throw new LoanException("Loan_E100",e);
            throw new LoanException("Loan_E100", e,context);
        }
        catch (RemoteException e)
        {
            // TODO Auto-generated catch block
        	//modified by mzh_fu 2007/08/07								
            //e.printStackTrace();
        	throw new LoanException("Loan_E100", e,context);
        }
	}

    /**
     * return String
     */
    public String getTransDiscountApplyTableName()
    {
		/*  TOCONFIG—TODELETE  */
		/*
		 * 产品化不再区分项目 
		 * ninh 
		 * 2005-03-24
		 */

//        if(Env.getProjectName().equals("cpf"))
//        {
//            TransDiscountApplyTableName = "Loan_loanform";
//        }
//        else// if(Env.getProjectName().equals("hn"))
//        {
//            TransDiscountApplyTableName = "Loan_loanform";
//        }
        
		TransDiscountApplyTableName = "Loan_loanform";

		/*  TOCONFIG—END  */
		
        return TransDiscountApplyTableName;
    }

    /**
     * return String
     */
    public String getTransDiscountApplyBillTableName()
    {
        TransDiscountApplyBillTableName = "Loan_DiscountFormBill";
        return TransDiscountApplyBillTableName;
    }

    /**
     * 计算转贴现申请下的贴现票据利息，
     * 操作Loan_DiscountFormBill表
     * @param lDiscountApplyID 贴现标识
     * @param  dRate        贴现利率
     * @param  tsDate       贴现日
     * @return 返回贴现票据的列表
     */
     public Collection calculateTransDiscountBillInterest(TransDiscountApplyQueryInfo qInfo)
     throws java.rmi.RemoteException, LoanException
	{
        Collection c = null;
        try
        {
            TransDiscountApplyDAO dao = new TransDiscountApplyDAO(this.getTransDiscountApplyTableName());
            if(qInfo.getInOrOut() == LOANConstant.TransDiscountInOrOut.IN)
            {
                c=dao.calInBillInterest(qInfo);
            }
            else if(qInfo.getInOrOut() == LOANConstant.TransDiscountInOrOut.OUT)
            {
                c=dao.calOutBillInterest(qInfo);
            }
        }
        /*
        catch (IException ie)
        {
            throw ie;
        }//*/
        //*
        catch (LoanException e)
        {
            e.printStackTrace();
            throw new LoanException("Loan_E006",e,context);
        }//*/
        catch (RemoteException e)
        {
            // TODO Auto-generated catch block
        	
        	//modified by mzh_fu 2007/08/07
           // e.printStackTrace();
        	throw new LoanException("Loan_E006",e,context);
        }
         
        return c;
	}
     
     /**
      * 中油的申请书多条件查询操作
      * @author zntan
      *
      * TODO To change the template for this generated type comment go to
      * Window - Preferences - Java - Code Style - Code Templates
      */
     public Collection cpfFindByMultiOption(TransDiscountApplyQueryInfo qInfo)
 	throws java.rmi.RemoteException, LoanException
	{
        Collection c = null;
        try
        {
            TransDiscountApplyDAO dao = new TransDiscountApplyDAO(this.getTransDiscountApplyTableName());
            c=dao.cpfFindByMultiOption(qInfo);
        }
        catch (LoanException e)
        {
            e.printStackTrace();
            throw new LoanException("Loan_E100",e);
        }
        catch (RemoteException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
         
        return c;
    
     	
	}
     /**
      * added by xwhe 2007/06/14
      * @param info
      * @return
      * @throws RemoteException
      * @throws IRollbackException
      */
     public long submitApplyAndApprovalInit(TransDiscountApplyInfo info)
 			throws RemoteException, IRollbackException {
 		
 		long lReturnId = -1;
 		try {
 			
 			save(info);

 			TransDiscountApplyDAO loanApplyDao = new TransDiscountApplyDAO(this.getTransDiscountApplyTableName());
 			InutParameterInfo inutParameterInfo = info
 					.getInutParameterInfo();

 			// 提交审批
 			FSWorkflowManager.initApproval(inutParameterInfo);
 			
 			// 更新状态到"审批中"
 			loanApplyDao.update(info.getId(), info.getInputUserId(),LOANConstant.LoanStatus.APPROVALING);
 			
 		} catch (Exception e) {
 			throw new IRollbackException(context, e.getMessage(), e);
 		}

 		return lReturnId;
 	} 
     
     /**
      * added by xwhe 2007/06/14
      * @param info
      * @return
      * @throws RemoteException
      * @throws IRollbackException
      */
       public long examinePass(TransDiscountApplyInfo info)
 	throws RemoteException, IRollbackException{
 		long lReturnId = -1;
 		try{
 		
 		}
 		catch(Exception e){
 		throw new IRollbackException(context, e.getMessage(), e);
 		}
 		return  lReturnId;
 		
 	}
       
 	/**
    * added by qhzhou 2007/09/22
    * @param info
    * @return
    * @throws RemoteException
    * @throws IRollbackException
    */
       public long submitApplyAndApprovalBillInit(TransDiscountApplyInfo info)
   			throws RemoteException, IRollbackException {
   		String strContractCode = "";
   		long lApplyID = -1;
   		try {
   			System.out.println("-------------提交审批成功------------");
   			TransDiscountApplyDAO loanApplyDao = new TransDiscountApplyDAO(this.getTransDiscountApplyTableName());
   			InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
   			lApplyID = Long.parseLong(inutParameterInfo.getTransID().trim());
   			// 提交审批
   			FSWorkflowManager.initApproval(inutParameterInfo);
   			
   			// 添加附件
   			loanApplyDao.addAttachment(lApplyID,info.getBillAttachId());
   			// 更新状态到"审批中"
   			loanApplyDao.updateBillStatus(lApplyID,BILLConstant.TransctionStatus.APPROVALING);
   			
   		} catch (Exception e) {
   			throw new IRollbackException(context, e.getMessage(), e);
   		}

   		return lApplyID;
   	} 
       
 	/**
    * added by qhzhou 2007/09/22
    * @param info
    * @return
    * @throws RemoteException
    * @throws IRollbackException
    */
       public long doApprovalApplyBillInit(TransDiscountApplyInfo nInfo) throws RemoteException,
   	IRollbackException,IException, BillException{
    		long lResult = -1;
     		long lCurrencyID = -1;
     		long lOfficeID = -1;
     		long lModuleID = -1;
     		// 业务类型
    	    long lLoanTypeID = -1;
    	    //操作类型
    	    long lActionID = -1;
    	    long lApprovalContentID = -1;;
     		long lUserID = -1;;
     		Timestamp tsNow=Env.getSystemDateTime();
     		try {
     			DiscountContractBillInfo dcbInfo;
		    	DiscountContractBillDao dcbDao=new DiscountContractBillDao();
		    	
     			TransDiscountApplyInfo transDiscountApplyInfo = new TransDiscountApplyInfo();
     			TransDiscountApplyDAO transDiscountApplyDao = new TransDiscountApplyDAO("Loan_LoanForm");
     			transDiscountApplyInfo = transDiscountApplyDao.findByID(nInfo.getId());
     			
     			InutParameterInfo inutParameterInfo = nInfo.getInutParameterInfo();
     			InutParameterInfo returnInfo = new InutParameterInfo();
     	        //定义相应操作常量
     			lCurrencyID = inutParameterInfo.getCurrencyID();
     			lOfficeID = inutParameterInfo.getOfficeID();
     	        //模块类型
     	        lModuleID = inutParameterInfo.getModuleID();
     	        //业务类型
     	        lLoanTypeID = inutParameterInfo.getTransTypeID();
     	        //操作类型
     	        lActionID = inutParameterInfo.getActionID();
     	        lApprovalContentID = nInfo.getId();
     			//当前用户
     	        lUserID = inutParameterInfo.getUserID();
     		
     			// 将业务记录置入nInfo1,转换成标准map传递到审批流引擎
     			inutParameterInfo.setDataEntity(transDiscountApplyInfo);
     		
     			// 提交审批
     			returnInfo = FSWorkflowManager.doApproval(inutParameterInfo);
     			// 如果是最后一级,且为审批通过,更新状态为已审批
     			if (returnInfo.isLastLevel()) {
     				long lll=nInfo.getId();
//     				更新状态已审批
            		lResult = transDiscountApplyDao.updateBillStatus(lll,BILLConstant.TransctionStatus.APPROVALED);
     				
            		//审批完成后需要做的操作
     				/**----------------------------------------Beginning--------------------------------------*/
     	            try
     	            {
     	            	// 生成转贴现合同信息
     	            	transDiscountApplyDao.doAfterApprovalOver(lApprovalContentID);
                		//票据出库
                		System.out.println("-----------票据出库开始---------");
                		//查询该笔申请下的已选票据信息
                		System.out.println("-----------取得票据信息---------");
                		Collection outBillColl = transDiscountApplyDao.findOutBillByTransDiscountApplyID(lApprovalContentID);
                		Iterator iter = null;
                		if(outBillColl!=null && outBillColl.size()>0)
                		{
                			iter = outBillColl.iterator();
                			while(iter.hasNext())
                			{
                				TransDiscountApplyBillInfo tdabinfo = (TransDiscountApplyBillInfo)iter.next();
                				TransDiscountApplyInfo aInfo1 = new TransDiscountApplyInfo();
                				aInfo1 = (TransDiscountApplyInfo) findByID(lApprovalContentID);
                				System.out.println("-----------取得入库信息id---------");
                				long lCraftInID = transDiscountApplyDao.getCraftInIDbyBillID(tdabinfo.getId());
                        		long lCraftOutID = -1;
                        		if(lCraftInID > 0)
                        		{
                        			
                        			DraftDelegation delegation = new DraftDelegation();
                        			DraftStorageAssembleInfo draftStorageAssembleInfo = new DraftStorageAssembleInfo();
                        			TransDraftOutInfo transDraftOutInfo = new TransDraftOutInfo();
                        			
                        			transDraftOutInfo.setCurrencyID(tdabinfo.getCurrencyId());
                        			transDraftOutInfo.setOfficeID(tdabinfo.getOfficeId());
                        			transDraftOutInfo.setInputDate(Env.getSystemDate(lOfficeID,lCurrencyID));
                        			transDraftOutInfo.setInputUserID(lUserID);
                        			transDraftOutInfo.setStatusID(Constant.RecordStatus.VALID);
                        			UtilOperation utilOperation=new UtilOperation(); 
                        			transDraftOutInfo.setTransCode(utilOperation.getNewDraftTransactionNo(tdabinfo.getOfficeId(),tdabinfo.getCurrencyId(),BILLConstant.DraftOperationType.DraftOut));
                        			transDraftOutInfo.setTransTypeID(BILLConstant.DraftOperationType.DraftIn);
                        			transDraftOutInfo.setOutDate(tdabinfo.getCreate());
                        			// 加入卖出转贴现出库类型
                        			// 卖出买断：转贴现卖出（出库）
                        			// 卖出回购：正回购卖出（出库）
                        			if(aInfo1.getDiscountTypeId()==LOANConstant.TransDiscountType.BUYBREAK)
                        			{
                        				transDraftOutInfo.setBillDestinationID(BILLConstant.BillWhither.REDISCOUNTSELLOUT);
                        			}
                        			if(aInfo1.getDiscountTypeId()==LOANConstant.TransDiscountType.REPURCHASE)
                        			{
                        				transDraftOutInfo.setBillDestinationID(BILLConstant.BillWhither.REPURCHASESELLOUT);
                        			}
                        			
                        			//transDraftOutInfo.setOutContractCode(tdabinfo.getContractID());//卖出时合同编号
                        			
                        			System.out.println("-----------取得入库信息---------");
                        			draftStorageAssembleInfo = delegation.findDraftInByID(lCraftInID);
                        			draftStorageAssembleInfo.setTransDraftOutInfo(transDraftOutInfo);
                        			
                        			//检查出库票据状态信息
                        			dcbInfo = draftStorageAssembleInfo.getDiscountContractBillInfo();
                        	    	if(dcbInfo == null || dcbInfo.getId() ==-1)
                        	    	{
                        	    		throw new BillException("Bill_E033",new Exception(),this.context);
                        	    	}
                        	    	if(dcbInfo!= null && dcbInfo.getNStorageStatusID() == BILLConstant.DraftInOrOut.OUT){
                        	    		throw new BillException("Bill_E037",new Exception(),this.context);
                        	    	}
                        			
                        			//保存出库记录
                        			System.out.println("-----------保存出库信息---------");
                        			
                        			lCraftOutID = delegation.saveDraftOut(draftStorageAssembleInfo);
                        			
                        			//-------------------判断汇票出库是否关联审批流---------------------------------
                        			boolean isNeedApproval = false;
                	    			//初始化查询类和参数类
	                				InutApprovalRelationBiz iaBiz = new InutApprovalRelationBiz();	
	                	    		InutApprovalRelationInfo qInfo = new InutApprovalRelationInfo();
	                				//构造参数类
	                				qInfo.setModuleID(Constant.ModuleType.BILL);
	                				qInfo.setOfficeID(transDraftOutInfo.getOfficeID());
	                				qInfo.setCurrencyID(transDraftOutInfo.getCurrencyID());
	                				qInfo.setTransTypeID(BILLConstant.TraceModule.DRAFT);
	                				qInfo.setActionID(BILLConstant.DraftOperationType.DraftOut);
	                				//根据条件查询给业务是否有审批流
	                				long lApprovalID = -1;
	                				try {
	                					lApprovalID= iaBiz.findApprovalID(qInfo);
	                				} catch (Exception e) {
	                					// TODO Auto-generated catch block
	                					e.printStackTrace();
	                					System.out.println("查找审批关联失败");
	                					throw new IException();
	                				}
	                				
	                				if(lApprovalID>0)
	                				{
	                					isNeedApproval = true;		//有审批流返回true
	                				}
	                				else
	                				{
	                					isNeedApproval = false;	//没有审批流返回false
	                				}
                        			//-------------------------------------------------------------------------
                        			//如果关联审批流
	                				if(isNeedApproval){
	                			    	dcbInfo.setDtModifyDate(tsNow);
	                			    	dcbInfo.setNModifyUserID(transDraftOutInfo.getInputUserID());
	                			    	
	                			    	dcbInfo.setNStorageStatusID(BILLConstant.DraftInOrOut.OUT);
	                			    	dcbInfo.setNStorageTransID(lCraftOutID);
	                			    	//dcbDao.update(dcbInfo);
	                			    	dcbDao.updateDiscountContractBill(dcbInfo);
	                					//将出库交易记录更新以审批状态
                        				new TransDraftOutDao().updateStatus(lCraftOutID,BILLConstant.TransctionStatus.APPROVALED);
                        			}
                        		}
                        		else
                        		{
                        			System.out.println("根据票据id取得相应入库信息出错！");
                        			throw new BillException();
                        		}
                			}
                		}
                		System.out.println("-----------票据出库结束---------");
                		
     	            }
     	            catch (LoanException e1)
     	            {
     	                e1.printStackTrace();
     	                throw new IRollbackException(this.context, e1);
     	            }
     	            catch (BillException e) {
     					// TODO Auto-generated catch block
     					e.printStackTrace();
     					System.out.println("票据出库失败");
     					throw new IRollbackException(this.context, e);
     				}
     	           /**----------------------------------------end--------------------------------------*/
     			}
     			// 如果是最后一级,且为审批拒绝,更新状态为已保存
     			else if (returnInfo.isRefuse()) {
     				
     				lResult = transDiscountApplyDao.updateBillStatus(nInfo.getId(),BILLConstant.TransctionStatus.SUBMIT);
     				
     				// 被拒绝时应该清除的关联

     			}
     		
     		} catch (Exception e) {
     			log4j.error(e.toString());
     			if (e instanceof IRollbackException) {
					throw (IRollbackException)e;
					
				}else{
					e.printStackTrace();
					throw new IRollbackException(this.context,"审批失败");
				}
     		}
     		return lResult;
     	}
   	/**
        * added by qhzhou 2007/09/22
        * @param info
        * @return
        * @throws RemoteException
        * @throws IRollbackException
        */
       public long doCancleApprovalApplyBillInit(TransDiscountApplyInfo nInfo) throws RemoteException,
   	IRollbackException,IException{
    	System.out.println("--------------开始进入取消审批方法---------------");
      	long lResult = -1;
      	long lTransDiscountApplyID = nInfo.getId();
   		try {
   			TransDiscountApplyInfo transDiscountApplyInfo = new TransDiscountApplyInfo();
 			TransDiscountApplyDAO transDiscountApplyDao = new TransDiscountApplyDAO(this.getTransDiscountApplyTableName());
 			transDiscountApplyInfo = transDiscountApplyDao.findByID(nInfo.getId());
 			
 			InutParameterInfo inutParameterInfo = nInfo.getInutParameterInfo();
 			
 			// 	将业务记录置入nInfo1,转换成标准map传递到审批流引擎
 			inutParameterInfo.setDataEntity(transDiscountApplyInfo);
 			
 			// 	提交审批
 			FSWorkflowManager.cancelApprovalRecord(inutParameterInfo);
			
			
 			//  取消审批完成后需要做的操作
 			//查询申请合同
 			TransDiscountContractDAO cdao = new TransDiscountContractDAO("Loan_ContractForm");
			TransDiscountContractInfo cinfo = cdao.findByApplyCode(nInfo.getApplyCode(),nInfo.getOfficeId(),nInfo.getCurrencyId());
			
 			//删除申请书下所有出库信息
    		System.out.println("-----------删除票据出库信息开始---------");
    		//查询该笔申请下的已选票据信息
    		System.out.println("-----------取得票据信息---------");
    		
    		Collection outBillColl = this.findBillByTransDiscountApplyID(lTransDiscountApplyID, LOANConstant.TransDiscountInOrOut.OUT);
    		Iterator iter = null;
    		if(outBillColl!=null && outBillColl.size()>0)
    		{
//	 			DraftHome home = (DraftHome)EJBObject.getEJBHome("DraftHome");
//	    		Draft draft = home.create();
	    		TransDraftOutDao tdDao = new TransDraftOutDao();
	    		
    			iter = outBillColl.iterator();
    			while(iter.hasNext())
    			{
    				TransDiscountApplyBillInfo tdabinfo = (TransDiscountApplyBillInfo)iter.next();
    				
    				tdDao.deleteByBillID(tdabinfo.getId());
    				//更新票据的出/入库状态为入库状态
    				TransDiscountContractBillDAO dao = new TransDiscountContractBillDAO("Loan_DiscountContractBill");
    				TransDiscountContractBillInfo cBillInfo = dao.findByID(tdabinfo.getId());
    				cBillInfo.setId(tdabinfo.getId());
    			
    				cBillInfo.setNstoragestatusid(BILLConstant.DraftInOrOut.IN);//入库
    				dao.update(cBillInfo);
    				
    			}
    			
    		}
 			
			//	如果是最后一级,且为取消审批,更新状态为已提交
			lResult = transDiscountApplyDao.updateBillStatus(nInfo.getId(),BILLConstant.TransctionStatus.SUBMIT);
			
 			
   		} catch (Exception e) {
   			log4j.error(e.toString());
   			throw new IRollbackException(context, e.getMessage(), e);
   		}
   		System.out.println("--------------------取消审批完成----------------");
   		return lResult;
   	}

}
