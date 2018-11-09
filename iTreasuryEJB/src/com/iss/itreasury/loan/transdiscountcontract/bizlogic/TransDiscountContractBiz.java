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
package com.iss.itreasury.loan.transdiscountcontract.bizlogic;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Collection;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.base.LoanDAOException;
import com.iss.itreasury.loan.base.LoanException;
import com.iss.itreasury.loan.bizdelegation.TransDiscountApplyDelegation;
import com.iss.itreasury.loan.transdiscountapply.bizlogic.TransDiscountApplyBiz;
import com.iss.itreasury.loan.transdiscountapply.dao.TransDiscountApplyDAO;
import com.iss.itreasury.loan.transdiscountapply.dataentity.TransDiscountApplyInfo;
import com.iss.itreasury.loan.transdiscountcontract.dao.TransDiscountContractBillDAO;
import com.iss.itreasury.loan.transdiscountcontract.dao.TransDiscountContractDAO;
import com.iss.itreasury.loan.transdiscountcontract.dataentity.TransDiscountContractBillInfo;
import com.iss.itreasury.loan.transdiscountcontract.dataentity.TransDiscountContractInfo;
import com.iss.itreasury.loan.transdiscountcontract.dataentity.TransDiscountContractQueryInfo;
import com.iss.itreasury.loan.transdiscountcredence.bizlogic.TransDiscountCredenceBiz;
import com.iss.itreasury.loan.transdiscountcredence.dao.TransDiscountCredenceDAO;
import com.iss.itreasury.loan.transdiscountcredence.dataentity.TransDiscountCredenceInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.system.bizdelegation.ApprovalDelegation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.loan.transdiscountapply.dataentity.TransDiscountApplyBillInfo;
import com.iss.itreasury.loan.transdiscountapply.dataentity.SelectBillInfo;
import java.util.Iterator;
/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TransDiscountContractBiz implements SessionBean
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
	public TransDiscountContractBiz()
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
	
	private String TransDiscountContractTableName = "";
    private String TransDiscountContractBillTableName = "";
		
    public static void main(String[] args)
        throws java.rmi.RemoteException, LoanException
    {
        TransDiscountContractInfo info = new TransDiscountContractInfo();
        TransDiscountContractDAO dao = new TransDiscountContractDAO("Loan_ContractForm");
        try
        {
            
            info = (TransDiscountContractInfo)dao.findByID(224,info.getClass());

            Log.print("id:"+info.getId());
            Log.print("getApplyCode:"+info.getApplyCode());
            Log.print("getBankAcceptPO:"+info.getBankAcceptPO());
            Log.print("getBizAcceptPO:"+info.getBizAcceptPO());
            Log.print("getBorrowClientId:"+info.getBorrowClientId());
            Log.print("getCheckAmount:"+info.getCheckAmount());
            Log.print("getCurrencyId:"+info.getCurrencyId());
            Log.print("getDiscountRate:"+info.getDiscountRate());
            Log.print("getTypeId:"+info.getTypeId());
            Log.print("getCurrencyId:"+info.getCurrencyId());
            Log.print("getOfficeId:"+info.getOfficeId());
            Log.print("getLoanAmount:"+info.getLoanAmount());
            Log.print("id:"+info.getId());
            Log.print("code:"+info.getContractCode());
        }
        catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	/**
	 *合同的保存操作
	*/
	public long save(TransDiscountContractInfo info) throws java.rmi.RemoteException, LoanException
	{
        long id = -1;
        String strFileName = "";
        Timestamp Date = Env.getSystemDateTime();
        try
        {
            TransDiscountContractDAO dao = new TransDiscountContractDAO(this.getTransDiscountContractTableName());
            
            if(info.getId() <= 0)
            {//由于新增一个合同的时候，在合同审核完成以后调用dao的新增方法来完成，
             //同时为了配合中油生成合同文本没有ContractContentInfo这些类而造成的共享代码困难，因此将新增合同的保存操作注释掉
            	//*
                //将下一个审批级别设为1
                info.setNextCheckLevel(1);
                info.setStatusId(LOANConstant.ContractStatus.SAVE);
                //info.setApplyCode(dao.createApplyCode());
                info.setContractCode(dao.createContractCode(
                                info.getBorrowClientId(),
                                info.getOfficeId(),
                                info.getInOrOut()));
                Log.print("ContractCode:"+info.getContractCode());
                info.setInputDate(Date);
                //info.setStartDate(info.getDiscountDate());
                
                dao.setUseMaxID();//取MAX_ID
                
                id = dao.add(info);
                Log.print("id:"+id);
                
                //生成转贴现合同文本
                
                //ContractDao conDao = new ContractDao();
                //lContractID = conDao.insert(lApprovalContentID, LOANConstant.LoanType.TX);
			/*
                ContractContentDao conconDao = new ContractContentDao();
                strFileName = conconDao.addZTX(id);
                
                if (Env.getProjectName().equals(Constant.ProjectName.CPF))//中油
                {
                }
                else//国机、华能
                {
                    ContractContentInfo CCInfo = new ContractContentInfo();
                    CCInfo.setParentID(id);
                    CCInfo.setContractID(id);
                    CCInfo.setContractTypeID(LOANConstant.ContractType.ZTX);
                    CCInfo.setDocName(strFileName);
                    conconDao.saveContractContent(CCInfo);
                }

            //*/
            }
            else
            {                
                dao.update(info);
                id = info.getId();
            }
        }
        catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }/*
        catch (RemoteException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }//*/
        
        return id;
    }

	/**
	 *合同的删除操作
	*/
	public void delete(long lID) throws java.rmi.RemoteException, LoanException
	{
	    ;
	}

	/**
	 *合同的审核操作
	*/
	public void check(ApprovalTracingInfo ATInfo) throws java.rmi.RemoteException, LoanException
	{

        TransDiscountContractDAO dao = new TransDiscountContractDAO(this.getTransDiscountContractTableName());

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
//                //获得ApprovalID
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
                    ATInfo.setApprovalContentID(lApprovalContentIDList[i]);
                    Log.print("ATInfo.getApprovalContentID()=" + ATInfo.getApprovalContentID());
                }
                else
                {
                    break;
                }
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
                    e.printStackTrace();
                }
                log4j.debug("saveApprovalTracing end");
            }
        }
    
	}

	/**
	 *合同的取消操作
	 * @throws Exception 
	*/
	public void cancel(long lID) throws Exception
	{

        TransDiscountContractInfo info = new TransDiscountContractInfo();
        SelectBillInfo selectInfo = new SelectBillInfo();
        //TransDiscountApplyBillInfo binfo = new TransDiscountApplyBillInfo();
        Collection cb = null;
        Collection bills = null;
        long[] lListBillId = new long[1000];
        int i = 0;
        try
        {            
            TransDiscountContractDAO dao = new TransDiscountContractDAO(this.getTransDiscountContractTableName());
            info.setId(lID);

			/*  TOCONFIG—TODELETE  */
			/*
			 * 产品化不再区分项目 
			 * ninh 
			 * 2005-03-24
			 */
//            if (Env.getProjectName().equals("cpf"))
//            {
//            	//中油没有取消状态，将记录置为无效
//            	info.setStatusId(Constant.RecordStatus.INVALID);//更新状态为已取消
//            }
//            else info.setStatusId(LOANConstant.ContractStatus.CANCEL);
            
			info.setStatusId(LOANConstant.ContractStatus.CANCEL);

			/*  TOCONFIG—END  */
			
            dao.update(info);
            //TransDiscountApplyDelegation applyDel = new TransDiscountApplyDelegation();
            TransDiscountApplyBiz applyDel = new TransDiscountApplyBiz();
          
            info = dao.findByID(lID);
            //modify by xwhe 2007/11/12
            if(info.getInOrOut()==LOANConstant.TransDiscountInOrOut.IN){
            bills = applyDel.findBillByTransDiscountApplyID(info.getLoanId(),info.getInOrOut());
            if( (bills != null) && (bills.size() > 0) )
			{
				Iterator it = bills.iterator();i++;
				while (it.hasNext() )
				{
					TransDiscountApplyBillInfo binfo = ( TransDiscountApplyBillInfo ) it.next();
					lListBillId[i]= binfo.getId();
					
				}
			}
            selectInfo.setAllBillID(lListBillId);
            selectInfo.setTransDiscountApplyID(info.getLoanId());
        	selectInfo.setInOrOut(info.getInOrOut());
        	applyDel.deleteAllBill(selectInfo);
            }
            //取消该合同对应的申请
            applyDel.cancel(info.getLoanId());
        }
        catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new LoanException("Loan_E100",e);
        }
	
	}

	/**
	 * 合同的提交操作
	 * @param info
	 * @throws java.rmi.RemoteException
	 * @throws LoanException
	 */
	public void submit(TransDiscountContractInfo info) throws java.rmi.RemoteException, LoanException
	{
		try
        {            
            TransDiscountContractDAO dao = new TransDiscountContractDAO(this.getTransDiscountContractTableName());
            info.setNextCheckLevel(1);
            info.setStatusId(LOANConstant.ContractStatus.SUBMIT);
            info.setNextCheckUserId(info.getInputUserID());
            dao.update(info);
        }
        catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new LoanException("Loan_E100",e);
        }
	}

	/**
	 *合同的单笔查询操作
	*/
	public TransDiscountContractInfo findByID(long lID) throws java.rmi.RemoteException, LoanException
	{
        TransDiscountContractInfo info = new TransDiscountContractInfo();
        //*
        try
        {
            TransDiscountContractDAO dao = new TransDiscountContractDAO(this.getTransDiscountContractTableName());
            
            info = (TransDiscountContractInfo)dao.findByID(lID);//,info.getClass());
            
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }//*/
        /*
        catch (RemoteException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //*/
        return info;
    }
	/**
	 *合同的单笔查询操作1
	*/
	public TransDiscountContractInfo findByID1(long lID) throws java.rmi.RemoteException, LoanException
	{
        TransDiscountContractInfo info = new TransDiscountContractInfo();
        //*
        try
        {
            TransDiscountContractDAO dao = new TransDiscountContractDAO(this.getTransDiscountContractTableName());
            
            info = (TransDiscountContractInfo)dao.findByID1(lID);//,info.getClass());
            
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }//*/
        /*
        catch (RemoteException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //*/
        return info;
    }

	/**
	 *合同的多笔查询操作
	*/
	public Collection findByMultiOption(TransDiscountContractQueryInfo qInfo) throws java.rmi.RemoteException, LoanException
	{
        Collection c = null;
        try
        {
            TransDiscountContractDAO dao = new TransDiscountContractDAO(this.getTransDiscountContractTableName());
            System.out.println("--------------------:"+qInfo.getQueryPurpose());
            if(qInfo.getQueryPurpose()==1)
            	c=dao.findByMultiOption(qInfo);
            else if(qInfo.getQueryPurpose()==2)
            	c=dao.findByMultiOption1(qInfo);
            else if(qInfo.getQueryPurpose()==3)
            	c=dao.findByMultiOption2(qInfo);
        }
        catch (LoanException e)
        {
            e.printStackTrace();
            throw new LoanException("Loan_E100",e);
        }
         
        return c;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getTransDiscountContractTableName()
    {
		/*  TOCONFIG—TODELETE  */
		/*
		 * 产品化不再区分项目 
		 * ninh 
		 * 2005-03-24
		 */

//        if(Env.getProjectName().equals("cpf"))
//        {
//            TransDiscountContractTableName = "Loan_ContractForm";
//        }
//        else// if(Env.getProjectName().equals("hn"))
//        {
//            TransDiscountContractTableName = "Loan_ContractForm";
//        }

		TransDiscountContractTableName = "Loan_ContractForm";

		/*  TOCONFIG—END  */
		
        return TransDiscountContractTableName;
    }

    /**
     *申请书下的票据保存操作
    */
    public long saveBill(TransDiscountContractBillInfo info) throws java.rmi.RemoteException, LoanException
    {
        long id = -1;
        
        try
        {
            TransDiscountContractDAO dao = new TransDiscountContractDAO(this.getTransDiscountContractTableName());
            TransDiscountContractBillDAO bdao = new TransDiscountContractBillDAO(this.getTransDiscountContractBillTableName());
            if (info.getInOrOut() == LOANConstant.BuyOrSaleType.BUY)
            {
            	if(info.getId() <= 0 )
            	{
            		//info.setContractCode(dao.createContractCode(info.getBorrowClientId(),info.getOfficeId()));
            		//设置下一个序列号
            		info.setSerialNo(bdao.findMaxTransDiscountContractBillSerialNo(info.getContractID())+1);
                
            		bdao.setUseMaxID();//取MAX_ID
                
            		id = bdao.add(info);
            	}
            	else
            	{
            		bdao.update(info);
            		id = info.getId();
            	}
            	info.setId(id);
            	//为合同票据关系表插入数据
            	dao.addBillRelation(info);
            
            	if(info.getContractID() > 0)
            	{
            		TransDiscountContractInfo cInfo = new TransDiscountContractInfo();
            		TransDiscountContractBillInfo bInfo = new TransDiscountContractBillInfo();
                
            		//cInfo = dao.findByID(info.getContractID());
            		bInfo = bdao.findByID(id);
                
            		cInfo.setId(info.getContractID());
                
            		//long statusId = cInfo.getStatusID();
            		Timestamp discountDate = cInfo.getDiscountDate();
            		Timestamp endDate = bInfo.getEnd();
                
            		if (discountDate == null && endDate != null)
            		{
            			//cInfo.setEndDate(endDate);
            			cInfo.setEndDate(endDate);
            			dao.update(cInfo);
            		}
            		else if (discountDate != null && endDate != null && discountDate.before(endDate))
            		{
            			cInfo.setEndDate(endDate);
            			dao.update(cInfo);
            		}
                
            	}
            }

        }
        catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }/*
        catch (RemoteException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }//*/
        
        return id;
    }



    /**
     * @param 
     * return String
     */
    public String getTransDiscountContractBillTableName()
    {
		/*  TOCONFIG—TODELETE  */
		/*
		 * 产品化不再区分项目 
		 * ninh 
		 * 2005-03-24
		 */
		 
//        if(Env.getProjectName().equals("cpf"))
//        {
//            TransDiscountContractBillTableName = "Loan_DiscountContractBill";
//        }
//        else// if(Env.getProjectName().equals("hn"))
//        {
//            TransDiscountContractBillTableName = "Loan_DiscountContractBill";
//        }
        
		TransDiscountContractBillTableName = "Loan_DiscountContractBill";

		/*  TOCONFIG—END  */
		
        return TransDiscountContractBillTableName;
    }

    /**
     * 中油合同复核操作
     * @param ATInfo
     * @throws java.rmi.RemoteException
     * @throws LoanException
     */
    public void cpfCheck(ApprovalTracingInfo ATInfo) throws java.rmi.RemoteException, LoanException
    {
        TransDiscountContractDAO dao = new TransDiscountContractDAO(this.getTransDiscountContractTableName());

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

        lApprovalContentIDList = ATInfo.getApprovalContentIDList();

        if (lApprovalContentIDList.length > 0)
        {
                //审核申请书
                dao.cpfCheck(ATInfo);

        }
    }
    
    /**
     * 中油的多条件查询操作
     * @param qInfo
     * @return
     * @throws java.rmi.RemoteException
     * @throws LoanException
     */
    public Collection cpfFindByMultiOption(TransDiscountContractQueryInfo qInfo) throws java.rmi.RemoteException, LoanException
	{
        Collection c = null;
        try
        {
            TransDiscountContractDAO dao = new TransDiscountContractDAO(this.getTransDiscountContractTableName());
            c=dao.cpfFindByMultiOption(qInfo);
        }
        catch (LoanException e)
        {
            e.printStackTrace();
            throw new LoanException("Loan_E100",e);
        }
         
        return c;
    }
    
    /**
     * 根据合同 id 查找该合同下的票据
     * @param lTransDiscountContractID
     * @return
     * @throws java.rmi.RemoteException
     * @throws LoanException
     */
    public Collection findBillByTransDiscountContractID(long lTransDiscountContractID) throws java.rmi.RemoteException, LoanException
	{
        Collection c = null;
        try
        {
            TransDiscountContractDAO dao = new TransDiscountContractDAO(this.getTransDiscountContractTableName());
            c=dao.findBillByTransDiscountContractID(lTransDiscountContractID);
        }
        catch (LoanException e)
        {
            e.printStackTrace();
            throw new LoanException("Loan_E100",e);
        }
         
        return c;
    
    	
	}
    
    /**
     * 根据合同 id 更新合同下的MCHECKAMOUNT字段
     * Boxu add 审核之前操作
     * 2007-2-7
     * @param lTransDiscountContractID
     * @return
     * @throws java.rmi.RemoteException
     * @throws LoanException
     */
    public void updateCheckAmount(long lTransDiscountContractID,double checkAmount) throws java.rmi.RemoteException, LoanException
	{
        try
        {
            TransDiscountContractDAO dao = new TransDiscountContractDAO(this.getTransDiscountContractTableName());
            dao.updateCheckAmount(lTransDiscountContractID,checkAmount);
        }
        catch (LoanException e)
        {
            e.printStackTrace();
            throw new LoanException("Loan_E100",e);
        }
	}
    
    /**
     * added by xwhe 2007/06/14
     * @param info
     * @return
     * @throws RemoteException
     * @throws IRollbackException
     */
    public long submitApplyAndApprovalInit(TransDiscountContractInfo info)
			throws RemoteException, IRollbackException {
		String strContractCode = "";
		long lReturnId = -1;
		try {
			
			lReturnId = save(info);
			TransDiscountContractDAO dao = new TransDiscountContractDAO(this.getTransDiscountContractTableName());
			InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
			//inutParameterInfo.setTransID(String.valueOf(lReturnId));
			inutParameterInfo.setUrl(inutParameterInfo.getUrl()+lReturnId);
			// 提交审批
			FSWorkflowManager.initApproval(inutParameterInfo);
			
			// 更新状态到"审批中"
			dao.update(lReturnId, info.getInputUserID(),LOANConstant.ContractStatus.APPROVALING);
			
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
    public long examinePass(TransDiscountContractInfo info)
	throws RemoteException, IRollbackException{
   	 long lReturnId = -1;
		 long lTransDiscountContractID=info.getId();
		 long lUserID=-1;
   			try
   			{
   				TransDiscountContractDAO dao = new TransDiscountContractDAO(this.getTransDiscountContractTableName());
   				TransDiscountContractInfo	appInfo=dao.findByID(info.getId());
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
   					if(info.getTypeId()==LOANConstant.LoanType.ZTX) //转贴现业务做特殊处理
   					{
	   					dao.update(
	   							lTransDiscountContractID,
	   							lUserID,
	   							LOANConstant.ContractStatus.NOTACTIVE);
	 	   	   		   //开始根据合同生成凭证信息
    				   Collection coll = dao.generateTransDiscountCredenceByContract(appInfo);
    				   if(coll != null && coll.size() > 0){
    					   TransDiscountCredenceBiz tdcBiz= new TransDiscountCredenceBiz();
    					   TransDiscountCredenceDAO tdDao= new TransDiscountCredenceDAO("LOAN_DISCOUNTCREDENCE");
    					   long lCredenceID = -1;
    					   Iterator it = coll.iterator();
    					   while(it.hasNext()){
    						   TransDiscountCredenceInfo tdcInfo = (TransDiscountCredenceInfo)it.next();
    						   lCredenceID = tdcBiz.save(tdcInfo);
    						   //审批贴现凭证CHECK
    						   tdDao.update(lCredenceID, lUserID, LOANConstant.DiscountCredenceStatus.CHECK);

    					   }
    				   }else{
    					   throw new IException("处理转贴现凭证失败，没有对应的转贴现合同票据，请检查！");
    				   }	   					
	   					
   					}
   					else
   					{
	   					dao.update(
	   							lTransDiscountContractID,
	   							lUserID,
	   							LOANConstant.ContractStatus.CHECK);
   					}
   					//审核完成以后，把该合同下的买入的票据置为可卖状态				  		
   					dao.doAfterCheckOver(lTransDiscountContractID);
   				}
   				//如果是最后一级,且为审批拒绝,更新状态为已保存
   				else if(returnInfo.isRefuse())
   				{
   					dao.update(
   							lTransDiscountContractID,
   							lUserID,
   							LOANConstant.ContractStatus.SAVE);
   				}	
   		 
   	 }
   	 catch(Exception e){
   		 throw new IRollbackException(context, e.getMessage(), e); 
   	 }
   	 return lReturnId;
    }
	/**
	 * Modify by xwge date 2007/09/10
	 * 审批流：取消审批方法
	 * @param loanInfo
	 * @return long
	 * @throws IRollbackException
	 */
	public long cancelApproval(TransDiscountContractInfo info)throws RemoteException, LoanException
	{
		long lReturn = -1;
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
		TransDiscountContractDAO dao = new TransDiscountContractDAO(this.getTransDiscountContractTableName());
		boolean ifUsed=false;
		try{
			ifUsed=dao.checkStatuID(info.getId());
		}catch (LoanDAOException e) {
			throw new LoanException();
		}
		if(ifUsed){
			throw new LoanException("请先取消转贴现凭证,再取消合同!",null);
		}
		try
		{   
			dao.cancelCredence(info.getId());
			//取消审批
			lReturn = dao.update(info.getId(), info.getInputUserID(), LOANConstant.ContractStatus.SAVE);
			
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

	
}
