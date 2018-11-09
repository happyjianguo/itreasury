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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import com.iss.itreasury.craftbrother.credit.dao.CreditSettingDAO;
import com.iss.itreasury.craftbrother.util.CRAconstant;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.iss.itreasury.bill.draft.bizlogic.DraftEJB;
import com.iss.itreasury.bill.draft.dao.DiscountContractBillDao;
import com.iss.itreasury.bill.draft.dao.TransDraftInDAO;
import com.iss.itreasury.bill.draft.dao.TransDraftOutDao;
import com.iss.itreasury.bill.draft.dataentity.DiscountContractBillInfo;
import com.iss.itreasury.bill.draft.dataentity.TransDraftInInfo;
import com.iss.itreasury.bill.draft.dataentity.TransDraftOutInfo;
import com.iss.itreasury.bill.draft.dataentity.assemble.DraftStorageAssembleInfo;
import com.iss.itreasury.bill.util.BILLConstant;
import com.iss.itreasury.bill.util.BillException;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.base.LoanException;
import com.iss.itreasury.loan.discount.dataentity.DiscountLoanInfo;
import com.iss.itreasury.loan.loanapply.dao.LoanApplyDao;
import com.iss.itreasury.loan.loanapply.dataentity.LoanExaminePassInfo;
import com.iss.itreasury.loan.transdiscountapply.dao.TransDiscountApplyBillDAO;
import com.iss.itreasury.loan.transdiscountapply.dao.TransDiscountApplyDAO;
import com.iss.itreasury.loan.transdiscountapply.dataentity.SelectBillInfo;
import com.iss.itreasury.loan.transdiscountapply.dataentity.TransDiscountApplyBillInfo;
import com.iss.itreasury.loan.transdiscountapply.dataentity.TransDiscountApplyInfo;
import com.iss.itreasury.loan.transdiscountapply.dataentity.TransDiscountApplyQueryInfo;
import com.iss.itreasury.loan.transdiscountcontract.dao.TransDiscountContractDAO;
import com.iss.itreasury.loan.transdiscountcontract.dataentity.TransDiscountContractInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.system.bizdelegation.ApprovalDelegation;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.CreateCodeManager;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.util.*;
import com.iss.itreasury.loan.base.LoanDAOException;

import com.iss.itreasury.bill.util.BILLConstant;


/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TransDiscountApplyBiz implements SessionBean
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
	public TransDiscountApplyBiz()
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
	 * @throws Exception 
	*/
	public long save(TransDiscountApplyInfo info) throws Exception
	{
        long id = -1;
        long ret =-1;
        Timestamp Date = Env.getSystemDateTime();
        HashMap map = new HashMap();
        String applyCode = "";
        try
        {
            TransDiscountApplyDAO dao = new TransDiscountApplyDAO(this.getTransDiscountApplyTableName());
            CreditSettingDAO cdao = new CreditSettingDAO();
            if(info.getId() <= 0)
            {
                //将下一个审批级别设为1
                info.setNextCheckLevel(1);
                info.setStatusId(LOANConstant.LoanStatus.SAVE);
                //info.setApplyCode(dao.createApplyCode());
              /*  info.setApplyCode(dao.createApplyCode(
                                info.getBorrowClientId(),
                                info.getOfficeId(),
                                info.getInOrOut()));
               */
        		map.put("officeID",String.valueOf(info.getOfficeId()));
        		map.put("currencyID",String.valueOf(info.getCurrencyId()));
        		map.put("moduleID",String.valueOf(info.getModuleId()));
        		map.put("transTypeID",String.valueOf(info.getTypeId()));
        		map.put("actionID",String.valueOf(info.getActionId()));                
                applyCode = CreateCodeManager.createCode(map);
                info.setApplyCode(applyCode);
                Log.print("Code:"+info.getApplyCode());
                info.setInputDate(Date);
                //info.setStartDate(info.getDiscountDate());
                
                //dao.setUseMaxID();//取MAX_ID
                
                id = dao.add(info);
                Log.print("id:"+id);
                //modify by xwhe 2007-11-5
                if(info.getCreditId() >0 ){              
                	ret = cdao.updateStatus(info.getCreditId(),CRAconstant.TransactionStatus.USED); 
                	System.out.print("修改的授信ID是:"+ret);
                          }
                
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
            throw new LoanException("Loan_E100",e);
        }
        catch (ITreasuryDAOException e)
        {
            e.printStackTrace();
            throw new LoanException("Loan_E100",e);
        }
        catch(IException e)
        {
        	e.printStackTrace();
        	throw new IException("未关联编码规则!",e); 
        }       
        catch (RemoteException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
            throw new LoanException("Loan_E100",e);
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
//                if (lApprovalID <= 0)
//                {
//                    lApprovalID = appbiz.getApprovalID(lModuleID, lLoanTypeID, lActionID,ATInfo.getOfficeID(),ATInfo.getCurrencyID());
//                }
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
                    System.out.println("审核申请书dao.check()   "+ATInfo.getApprovalID());
                    dao.check(ATInfo);

                    log4j.debug("saveApprovalTracing begin");
                    try
                    {
                    	System.out.println("审核申请书appbiz.saveApprovalTracing(ATInfo)   "+ATInfo.getApprovalID());
                        appbiz.saveApprovalTracing(ATInfo);
                    }
                    catch (Exception e)
                    {
                        log4j.error("saveApprovalTracing fail");
                        e.printStackTrace();
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
					e.printStackTrace();
				}
				log4j.debug("saveReviewOpinion end");
			}
		}


	/**
	 *申请书的取消操作
	 * @throws Exception 
	*/
	public void cancel(long lID) throws Exception
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
            //取消合同时，不再改变票据可买卖状态，相关事项由票据模块的取消审批操作及待提交取消申请来完成，否则会造成被选票据无法释放（不能再被选择）
            // modify by wangzhen
//            if(info.getInOrOut() == LOANConstant.TransDiscountInOrOut.OUT)
//            {
//                cb = dao.findOutBillByTransDiscountApplyID(lID);
//                if( (cb != null) && (cb.size() > 0) )
//                {
//                    Iterator it = cb.iterator();
//                    while (it.hasNext() )
//                    {
//                        TransDiscountApplyBillInfo binfo = ( TransDiscountApplyBillInfo ) it.next();
//                        lListBillId[i]=binfo.getId();
//                        i++;
//                    }
//                }
//                SelectBillInfo sinfo = new SelectBillInfo();
//                sinfo.setTransDiscountApplyID(lID);
//                sinfo.setAllBillID(lListBillId);
//                sinfo.setIsSubmit(Constant.YesOrNo.YES);
//                dao.deleteOutBill(sinfo);
//            }
            
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

			//aInfo.setStatusId(LOANConstant.LoanStatus.CANCEL);//更新状态为已取消
            
             //将状态改为已审批，这样在票据模块该申请还可以通过取消审批，待提交取消申请完全被退回，将已选票据释放。
             //modify by wangzhen
			  aInfo.setStatusId(LOANConstant.LoanStatus.CHECK); 
			  
			/*  TOCONFIG—END  */
			
            dao.update(aInfo);
         //modify by xwhe 2007-11-5   
            aInfo = dao.findByCreditID(info.getCreditId());
            CreditSettingDAO cdao = new CreditSettingDAO();
            if(aInfo.getId()>0){         	
            }
            else{           	
            	cdao.updateStatus(info.getCreditId(), CRAconstant.TransactionStatus.APPROVALED);
            }         
        }
        catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new LoanException("Loan_E100",e);
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
	 *申请书的单笔查询操作
	*/
	public TransDiscountApplyInfo findByID1(long lID) throws java.rmi.RemoteException, LoanException
	{
        TransDiscountApplyInfo info = new TransDiscountApplyInfo();
        try
        {
            TransDiscountApplyDAO dao = new TransDiscountApplyDAO(this.getTransDiscountApplyTableName());

            info = (TransDiscountApplyInfo)dao.findByID1(lID);
            
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
            if(qInfo.getQueryPurpose()==1)
            	c=dao.findByMultiOption2(qInfo);
            else if(qInfo.getQueryPurpose()==2)
            	c=dao.findByMultiOption1(qInfo);
            else if(qInfo.getQueryPurpose()==3)
            	c=dao.findByMultiOption3(qInfo);
            else if(qInfo.getQueryPurpose()==4)
            	c=dao.findByMultiOption4(qInfo);
            else if(qInfo.getQueryPurpose()==5)//update by dwj 20111214 同步方正票据管理模块
            	c=dao.findByMultiOption5(qInfo);
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
                    
                    //加入合同票据信息(loan_discountcontractbill)
                    //加入合同票据信息(cra_transdiscountbill)
    //                dao.saveContractBillInfo(info.getLoanID());
                    
                    //票据入库
   //                 long lReturn = -1;
   //                 Collection coll = bdao.in_depot(info.getLoanID(),info.getRepurchaseTypeID(),LOANConstant.LoanType.ZTX,LOANConstant.TransDiscountInOrOut.IN);
   //                 Iterator it=null;
   //             	it = coll.iterator();
                	
   //                 while(it.hasNext())
   //                 {
   //                 	DraftStorageAssembleInfo draftStorageAssembleInfo=(DraftStorageAssembleInfo)it.next();
   //                 	DraftEJB draftEJB = new DraftEJB();
                    	//lReturn = bdao.saveDraftIn(draftStorageAssembleInfo);
  //                  	lReturn = draftEJB.saveDraftIn(draftStorageAssembleInfo);
   //                 }
   //                 if(lReturn>0)
  //                  {
  //                  	System.out.println("入库成功!");
  //                  }
  //                  else
 //                   {
  //                  	System.out.println("入库失败!");
  //                  }
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
            throw new LoanException("Loan_E100",e);
        }
        catch (RemoteException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } //catch (BillException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        
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
	 *by gangwang
	*/
	public void deleteAllBill(SelectBillInfo selectBillInfo) throws java.rmi.RemoteException, LoanException
	{
       try
        {
            if(selectBillInfo.getInOrOut() == LOANConstant.TransDiscountInOrOut.IN)
            {
                TransDiscountApplyBillDAO bdao = new TransDiscountApplyBillDAO(this.getTransDiscountApplyBillTableName());
                
                bdao.deleteAllBill(selectBillInfo);
            }
            else if(selectBillInfo.getInOrOut() == LOANConstant.TransDiscountInOrOut.OUT)
            {
                TransDiscountApplyDAO dao = new TransDiscountApplyDAO(this.getTransDiscountApplyTableName());

                selectBillInfo.setIsSubmit(Constant.YesOrNo.YES);
                
                dao.deleteAllOutBill(selectBillInfo);
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
            throw new LoanException("Loan_E100",e);
        }
        catch (RemoteException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	    
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
            throw new LoanException("Loan_E100",e);
        }
        catch (RemoteException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
            throw new LoanException("Loan_E100",e);
        }
        catch (RemoteException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

    /**
     * return String
     */
    public String getTransDiscountApplyTableName()
    {
        
		TransDiscountApplyTableName = "Cra_loanform";

		/*  TOCONFIG—END  */
		
        return TransDiscountApplyTableName;
    }

    /**
     * return String
     */
    public String getTransDiscountApplyBillTableName()
    {
        TransDiscountApplyBillTableName = "cra_transdiscountbill";
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
            throw new LoanException("Loan_E006",null,context);
        }//*/
        catch (RemoteException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
      * added by xwhe 2007/09/14
      * @param info
      * @return
      * @throws RemoteException
      * @throws IRollbackException
      */
     public long submitApplyAndApprovalInit(TransDiscountApplyInfo info)
 			throws RemoteException, IRollbackException {
 		String strContractCode = "";
 		long lReturnId = -1;
 		try {
 			
 			lReturnId = save(info);
 			TransDiscountApplyDAO loanApplyDao = new TransDiscountApplyDAO(this.getTransDiscountApplyTableName());
 			InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
 			inutParameterInfo.setTransID(String.valueOf(lReturnId));
 			inutParameterInfo.setUrl(inutParameterInfo.getUrl()+lReturnId);
 			// 提交审批
 			FSWorkflowManager.initApproval(inutParameterInfo);
 			
 			// 更新状态到"审批中"
 			loanApplyDao.update(lReturnId, info.getInputUserId(),LOANConstant.LoanStatus.APPROVALING);
 			
 		} catch (Exception e) {
 			throw new IRollbackException(context, e.getMessage(), e);
 		}

 		return lReturnId;
 	} 
     /**
      * added by xwhe 2007/09/14
      * @param info
      * @return
      * @throws RemoteException
      * @throws IRollbackException
      */
     public long updateApplyAndApprovalInit(TransDiscountApplyInfo info)
 			throws RemoteException, IRollbackException {
 		String strContractCode = "";
 		long lReturnId = -1;
 		try {
 	
 			TransDiscountApplyDAO loanApplyDao = new TransDiscountApplyDAO(this.getTransDiscountApplyTableName());
 			lReturnId = save(info);
 			InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
 			inutParameterInfo.setTransID(String.valueOf(info.getId()));
 			inutParameterInfo.setUrl(inutParameterInfo.getUrl()+info.getId());
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
      * added by xwhe 2007/09/14
      * @param info
      * @return
      * @throws RemoteException
      * @throws IRollbackException
      */
     public long examinePass(TransDiscountApplyInfo info)
 	throws RemoteException, IRollbackException{
    	 long lReturnId = -1;
 		 long lTransDiscountApplyID=info.getId();
		 long lUserID=-1;
    			try
    			{
    				TransDiscountApplyDAO dao = new TransDiscountApplyDAO(this.getTransDiscountApplyTableName());
    				TransDiscountApplyInfo	appInfo=dao.findByID(info.getId());
                    //lLoanTypeID=appInfo.getTypeID();
    				//lLoanTypeID=appInfo.getSubTypeId();
    				long status=appInfo.getStatusId() ;
    				
 
    				//---- added by xwhe 2007/09/12 审批流 begin
    				
    				InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
    				InutParameterInfo returnInfo = new InutParameterInfo();
    				
    				//将业务记录置入pinfo,转换成标准map传递到审批流引擎
    				inutParameterInfo.setDataEntity(info);
    				
    				//提交审批
    				returnInfo = FSWorkflowManager.doApproval(inutParameterInfo);
    				
    				//如果是最后一级,且为审批通过,更新状态为已审批
    				if(returnInfo.isLastLevel())
    				{	
    					dao.update(
    							lTransDiscountApplyID,
    							lUserID,
    							SECConstant.ContractStatus.CHECK );
    					
    					//将贷款申请信息复制到合同表				  		
    					//dao.doAfterApprovalOver(lTransDiscountApplyID);
    				}
    				//如果是最后一级,且为审批拒绝,更新状态为已保存
    				else if(returnInfo.isRefuse())
    				{
    					dao.update(
    							lTransDiscountApplyID,
    							lUserID,
    							LOANConstant.LoanStatus.SAVE);
    				}	
    		 
    	 }
    	 catch(Exception e){
    		 throw new IRollbackException(context, e.getMessage(), e); 
    	 }
    	 return lReturnId;
     }
     /**
 	 * Modify by xwhe date 2007/09/10
 	 * 审批流：校验合同状态
 	 * @param loanInfo
 	 * @return
 	 * @throws RemoteException
 	 * @throws IRollbackException
 	 */
 	public long checkContract(TransDiscountApplyInfo info)throws RemoteException, IRollbackException
 	{
 		long lReturn = -1;
 		TransDiscountApplyDAO dao = new TransDiscountApplyDAO(this.getTransDiscountApplyTableName());
 		try
 		{
 			//校验合同状态
 			lReturn = dao.checkContractState(info.getId());					
 		}
 		catch (Exception e)
 		{
 			throw new IRollbackException(context, e.getMessage(), e);
 		}
 		return lReturn;
 	}
	/**
	 * Modify by xwge date 2007/09/10
	 * 审批流：取消审批方法
	 * @param loanInfo
	 * @return long
	 * @throws IRollbackException
	 */
	public long cancelApproval(TransDiscountApplyInfo info)throws java.rmi.RemoteException, LoanException
	{
		long lReturn = -1;
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
		TransDiscountApplyDAO dao = new TransDiscountApplyDAO(this.getTransDiscountApplyTableName());
		boolean ifUsed=false;
		if(info.getInOrOut() == LOANConstant.TransDiscountInOrOut.IN)
		{
			try{
					ifUsed=dao.checkStatuID(info.getId());
				}catch (LoanDAOException e) {
					throw new LoanException();
				}
				if(ifUsed){
					throw new LoanException("请先取消合同,再取消申请!",null);
				}
			try{	
				//删除票据
				lReturn = dao.cancelBill(info.getId(),BILLConstant.TransctionStatus.DELETE);
			   }
			catch(Exception e){
				
				throw new LoanException();
			}
			
		}
		if(info.getInOrOut() == LOANConstant.TransDiscountInOrOut.OUT)
		{
			try{
				ifUsed=dao.checkBillStatus(info.getId());
			}catch (LoanDAOException e) {
				throw new LoanException();
			}
			if(ifUsed){
				throw new LoanException("请先取消转贴现票据,再取消申请!",null);
			}	
			
		}
		//try{
		//	ifUsed=dao.checkStatuID(info.getId());
		//}catch (LoanDAOException e) {
		//	throw new +LoanException();
		//}
		//if(ifUsed){
		//	throw new LoanException("请先取消合同,再取消申请!",null);
		//}
		
		try
		{
			//dao.cancelContract(info.getId());
			//取消审批
			lReturn = dao.update(info.getId(), info.getInputUserId(), LOANConstant.LoanStatus.SAVE);
			
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
			throw new LoanException();
		}
		return lReturn;
	}
	
	/**
	 * @param applyId
	 * @return
	 * @throws IException
	 */
	public long getContracIDByApplyID(long applyId) throws IException{
		try{
			return new TransDiscountContractDAO("loan_contractform").getContracIDByApplyID(applyId);
		}catch(Exception e){
			throw new IException(e.getMessage(),e);
		}
	}

}
