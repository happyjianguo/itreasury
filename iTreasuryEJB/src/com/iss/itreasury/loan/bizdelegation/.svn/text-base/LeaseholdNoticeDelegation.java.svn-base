package com.iss.itreasury.loan.bizdelegation;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;

import com.iss.itreasury.loan.assurechargenotice.dao.AssureChargeNoticeDao;
import com.iss.itreasury.loan.base.LoanDAOException;
import com.iss.itreasury.loan.base.LoanException;
import com.iss.itreasury.loan.contract.bizlogic.Contract;
import com.iss.itreasury.loan.contract.bizlogic.ContractHome;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.leasehold.bizlogic.LeaseholdNotice;
import com.iss.itreasury.loan.leasehold.bizlogic.LeaseholdNoticeHome;
import com.iss.itreasury.loan.leasehold.dao.LoanLeaseholdRepayFormDao;
import com.iss.itreasury.loan.leasehold.dataentity.LeaseholdPayNoticeInfo;
import com.iss.itreasury.loan.leasehold.dataentity.LeaseholdRepayNoticeInfo;
import com.iss.itreasury.loan.loanapply.dataentity.LoanPlanDetailInfo;
import com.iss.itreasury.loan.leasehold.dataentity.LeaseholdInterestAdjustInfo;
import com.iss.itreasury.loan.loancommonsetting.bizlogic.LoanCommonSetting;
import com.iss.itreasury.loan.loancommonsetting.bizlogic.LoanCommonSettingHome;
import com.iss.itreasury.loan.loancommonsetting.dataentity.ClientInfo;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.EJBObject;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;

import javax.rmi.PortableRemoteObject;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 *
 * <p>Title: 融资租赁(收款/还款)通知单</p>
 *
 * <p>Description: 信贷管理－融资租赁</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: iSoftStone</p>
 *
 * @author liuxz
 * @version 1.0
 */
public class LeaseholdNoticeDelegation {

    private LeaseholdNotice facade = null;

    public LeaseholdNoticeDelegation() throws RemoteException {
        try {
            LeaseholdNoticeHome home = null;
            Object obj = EJBObject.getEJBHome("LeaseholdNoticeHome");
            if (!(obj instanceof LeaseholdNoticeHome)) {
                //look up jndi name
                obj = EJBObject.lookupEjbJNDI("ejb/LeaseholdNotice",
                                              LeaseholdNoticeHome.class);
            }
            home = (LeaseholdNoticeHome) obj;
            facade = (LeaseholdNotice) home.create();
        } catch (Exception ce) {
            throw new RemoteException("发生CreateException", ce);
        }
    }

    /**  融资租赁收款通知单 */
    /**
     * 融资租赁收款通知单的保存操作
     *
     * @param paynoticeinfo LeaseholdPayNoticeInfo
     * @return long
     * @throws RemoteException
     */
    public long savePayNotice(LeaseholdPayNoticeInfo paynoticeinfo) throws
            RemoteException {
    	long lReturn = -1;
        try {
        	lReturn = facade.savePayNotice(paynoticeinfo);
		} catch (Exception e) {
			throw new RemoteException(e.getMessage());
		}
		return lReturn;
    }

    /**
     * 融资租赁收款通知单的取消操作
     * @param paynoticeid long
     * @throws RemoteException
     */
    public void cancelPayNotice(long paynoticeid) throws RemoteException {
         try {
        	 facade.cancelPayNotice(paynoticeid);
         } catch (Exception e) {
 			throw new RemoteException(e.getMessage());
 		}
    }

    /**
     * 融资租赁收款通知单的审核操作
     * @param info ApprovalTracingInfo
     * @throws RemoteException
     */
    public void checkPayNotice(ApprovalTracingInfo info) throws RemoteException {
    	 try {   
    		 facade.checkPayNotice(info);
	    } catch (Exception e) {
			throw new RemoteException(e.getMessage());
		}
    }

    /**
     * 融资租赁收款通知单的单笔查询操作
     * @param paynoticeid long
     * @return LeaseholdPayNoticeInfo
     * @throws RemoteException
     */
    public LeaseholdPayNoticeInfo findPayNoticeByID(long paynoticeid) throws
            RemoteException {
        return facade.findPayNoticeByID(paynoticeid);
    }

    /**
     * 融资租赁收款通知单的多笔查询操作
     * @param paynoticeinfo LeaseholdPayNoticeInfo
     * @return Collection
     * @throws RemoteException
     */
    public Collection findPayNoticeByMultiOption(LeaseholdPayNoticeInfo
                                                 paynoticeinfo) throws
            RemoteException {
        return facade.findPayNoticeByMultiOption(paynoticeinfo);
    }

    /**  融资租赁还款通知单 */
    
    /**
     * added by xiong fei 2010-07-15
     * 判断该合同下是否本期之前有未还款项
     * @param contractId,officeID,currencyID
     * @return long -1 表示有未还款 1 为没有
     * @throws RemoteException
     */
    public long isAheadRepay(long contractId,long officeID,long currencyID)
			throws RemoteException {
		long lReturn = -1;
		try {
			lReturn = facade.isAheadRepay(contractId,officeID,currencyID);
		} catch (Exception e) {
			throw new RemoteException(e.getMessage());
		}
		return lReturn;
	}

    /**
     * 融资租赁还款通知单的保存操作
     * @param repaynoticeInfo LeaseholdRepayNoticeInfo              
     * @return long
     * @throws RemoteException
     */
    public long saveRepayNotice(LeaseholdRepayNoticeInfo repaynoticeInfo) throws
            RemoteException {
    	long lReturn = -1;
    	try{
    		lReturn = facade.saveRepayNotice(repaynoticeInfo);
    	} catch (Exception e) {
			throw new RemoteException(e.getMessage());
		}
    	return lReturn;
    }

    /**
     * 融资租赁还款通知单的取消操作
     * @param repaynoticeid long
     * @throws RemoteException
     */
    public void cancelRepayNotice(long repaynoticeid) throws RemoteException {
    	try{  
    		facade.cancelRepayNotice(repaynoticeid);
	    } catch (Exception e) {
			throw new RemoteException(e.getMessage());
		}
    }

    /**
     * 融资租赁还款通知单的审核操作
     * @param info ApprovalTracingInfo
     * @throws RemoteException
     */
    public void checkRepayNotice(ApprovalTracingInfo info) throws
            RemoteException {
    	try{
    		facade.checkRepayNotice(info);
    	} catch (Exception e) {
			throw new RemoteException(e.getMessage());
		}        
    }

    /**
     * 融资租赁还款通知单的单笔查询操作
     * @param repaynoticeid long
     * @return LeaseholdRepayNoticeInfo
     * @throws RemoteException
     */
    public LeaseholdRepayNoticeInfo findRepayNoticeByID(long repaynoticeid) throws
            RemoteException {
        return facade.findRepayNoticeByID(repaynoticeid);

    }

    /**
     * 融资租赁还款通知单的多笔查询操作
     *
     * @param repaynoticeInfo LeaseholdRepayNoticeInfo
     * @return Collection
     * @throws RemoteException
     */
    public Collection findRepayNoticeByMultiOption(LeaseholdRepayNoticeInfo
            repaynoticeInfo) throws RemoteException {
        return facade.findRepayNoticeByMultiOption(repaynoticeInfo);
    }

    /**
     * 根据合同的编号ID得到合同内容
     *
     * @param lContractID long
     * @return com.iss.itreasury.loan.contract.dataentity.ContractInfo
     * @throws RemoteException
     */
    public static ContractInfo getLeaseholdContractInfo(long lContractID) throws
            RemoteException {
        Contract c_ejb;
        ContractInfo info = new ContractInfo();
        //得到合同内容
        try {
            ContractHome chome;
            try {
                chome = (ContractHome) EJBHomeFactory.getFactory().
                        lookUpHome(ContractHome.class);
            } catch (IException e) {
                throw new RemoteException("EJBHomeFactory连接错误", e);
            }
            c_ejb = (Contract) chome.create();
            info = c_ejb.findByID(lContractID);
        } catch (IException ex) {
            throw new RemoteException("发生IException", ex);
        } catch (CreateException ce) {
            throw new RemoteException("发生CreateException", ce);
        }
        return info;
    }

    /**
     * 单位内容
     *
     * @param usrID long
     * @return com.iss.itreasury.loan.loancommonsetting.dataentity.ClientInfo
     * @throws RemoteException
     */
    public static ClientInfo getLeaseholdClientInfo(long usrID) throws
            RemoteException {
        LoanCommonSetting lc_ejb;
        ClientInfo info = new ClientInfo();
        //得到合同内容
        try {
            LoanCommonSettingHome loanCommonSettingHome;
            try {
                loanCommonSettingHome = (LoanCommonSettingHome) EJBHomeFactory.
                                        getFactory().
                                        lookUpHome(LoanCommonSettingHome.class);
            } catch (IException e) {
                throw new RemoteException("EJBHomeFactory连接错误", e);
            }
            lc_ejb = (LoanCommonSetting) loanCommonSettingHome.create();

            info = lc_ejb.findClientByID(usrID);

        } catch (IException ex) {
            throw new RemoteException("发生IException", ex);
        } catch (CreateException ce) {
            throw new RemoteException("发生CreateException", ce);
        }
        return info;
    }

    /**
     * 获取收款通知单编号
     *
     * @param contractID long
     * @return String
     * @throws RemoteException
     */
    public static String getLeaseholdCode(long contractID) throws
            RemoteException {
        AssureChargeNoticeDao dao = new AssureChargeNoticeDao();
        String sNewApplyCode = "";
        try {
            sNewApplyCode = dao.getApplyCode(contractID);
        } catch (LoanDAOException ex) {
            throw new RemoteException("获取收款通知单编号发生LoanDAOException", ex);
        }
        return sNewApplyCode;
    }
    
    /**
     * 获取还款通知单编号
     *
     * @param contractID long
     * @return String
     * @throws RemoteException
     */
    public static String getLeasehRepayCode(long contractID) throws
            RemoteException {
    	LoanLeaseholdRepayFormDao dao = new LoanLeaseholdRepayFormDao();
        String sNewApplyCode = "";
        try {
            sNewApplyCode = dao.getApplyCode(contractID);
        } catch (LoanDAOException ex) {
            throw new RemoteException("获取还款通知单编号发生LoanDAOException", ex);
        }
        return sNewApplyCode;
    }
    
    /**
     * 根据日期获得融资租赁该期的租金偿付情况
     *
     * @param strDate String
     * @return LoanPlanDetailInfo
     * @throws RemoteException
     */
    public LoanPlanDetailInfo getPlanDetailInfo(String strDate, long lLoanID) throws RemoteException 
    {
    	LoanLeaseholdRepayFormDao dao = new LoanLeaseholdRepayFormDao();
    	LoanPlanDetailInfo returnInfo = null;
        try 
        {
        	returnInfo = dao.getPlanDetailInfo(strDate,lLoanID);
        } catch (Exception ex) 
        {
            throw new RemoteException("获取租金偿付表明细", ex);
        }
        return returnInfo;
    }
    
    /**added by xiong fei 2010-07-27
     * 根据合同ID获得融资租赁该期的租金偿付情况
     *
     * @param strDate String
     * @return LoanPlanDetailInfo
     * @throws RemoteException
     */
    public LoanPlanDetailInfo getRZZLPlanDetailInfo(long contractID) throws RemoteException 
    {
    	LoanLeaseholdRepayFormDao dao = new LoanLeaseholdRepayFormDao();
    	LoanPlanDetailInfo returnInfo = null;
        try 
        {
        	returnInfo = dao.getRZZLPlanDetailInfo(contractID);
        } catch (Exception ex) 
        {
            throw new RemoteException("获取租金偿付表明细", ex);
        }
        return returnInfo;
    }


	    /**
     * 融资租赁利率调整通知单的保存操作
     *
     * @param noticeinfo LeaseholdInterestAdjustInfo
     * @return long
     * @throws RemoteException
     */
    public long saveAdjustNotice(LeaseholdInterestAdjustInfo noticeinfo) throws
            RemoteException {
    	long lReturn = -1;
    	try{
    		lReturn = facade.saveAdjustNotice(noticeinfo);
    	}catch (Exception e) {
			throw new RemoteException(e.getMessage());
		}
        return lReturn;
    }

    /**
     * 融资租利率调整通知单的取消操作
     * @param noticeid long
     * @throws RemoteException
     */
    public void cancelAdjustNotice(long noticeid) throws RemoteException {
    	try{
    		facade.cancelAdjustNotice(noticeid);
	    }catch (Exception e) {
			throw new RemoteException(e.getMessage());
		}
    }

    /**
     * 融资租赁利率调整通知单的审核操作
     * @param info ApprovalTracingInfo
     * @throws RemoteException
     */
    public void checkAdjustNotice(ApprovalTracingInfo info)
			throws RemoteException {
		try {
			facade.checkAdjustNotice(info);
		} catch (Exception e) {
			throw new RemoteException(e.getMessage());
		}
	}

    /**
	 * 融资租赁利率调整通知单的单笔查询操作
	 * 
	 * @param noticeid
	 *            long
	 * @return LeaseholdInterestAdjustInfo
	 * @throws RemoteException
	 */
    public LeaseholdInterestAdjustInfo findAdjustNoticeByID(long noticeid) throws
            RemoteException {
        return facade.findAdjustNoticeByID(noticeid);
    }

    /**
     * 融资租赁利率调整通知单的多笔查询操作
     * @param noticeinfo LeaseholdInterestAdjustInfo
     * @return Collection
     * @throws RemoteException
     */
    public Collection findAdjustNoticeByMultiOption(LeaseholdInterestAdjustInfo
            noticeinfo) throws RemoteException {
        return facade.findAdjustNoticeByMultiOption(noticeinfo);
    }
    
    /**
     * 查询未收款金额(合同未做收款通知单金额)
     * @param lContractID long
     * @return unPayAmount
     * @throws RemoteException
     */
    public double findUnPayAmountByID(long lContractID,long payID) throws RemoteException
    {
        return facade.findUnPayAmountByID(lContractID,payID);
    }
    
    /**
	 * @param lContractID 合同ID
	 * @param rePayID 还款通知单ID
	 * @return 查询未还款金额
	 * @throws RemoteException
	 * @throws IException
	 */
    public double findUnRePayAmountByID(long lContractID,long payID) throws RemoteException,IException
    {
        return facade.findUnRePayAmountByID(lContractID,payID);
    }
    
	/**
	 * @author yunchang
	 * @date 2010-05-25 
	 * @function 查找出记录担保收费通知单信息(LOAN_ASSURECHARGEFORM)中相同合同号的收取租金账户,为多笔收款通知单做校验,相同合同的收取租金账户、收保证金账户必须相同
	 * @param LeaseholdPayNoticeInfo
	 * @throws RemoteException,IException
	 * @throws LeaseholdPayNoticeInfo
	 */
    public LeaseholdPayNoticeInfo getCollectionRentAccountID(LeaseholdPayNoticeInfo leaseholdPayNoticeInfo)throws RemoteException,IException
    {
    	return facade.getCollectionRentAccountID(leaseholdPayNoticeInfo);
    }

	/**
	 * @author yunchang
	 * @date 2010-06-01 19:24
	 * @function 查找出融资租赁新增(LOAN_LOANFORM)中的新增手续费,为多笔收款通知单做校验,多比手续费总和不能超过新增手续费
	 * @param LeaseholdPayNoticeInfo
	 * @throws RemoteException,IException
	 * @throws LeaseholdPayNoticeInfo
	 */
    public LeaseholdPayNoticeInfo getMchargeAmount(LeaseholdPayNoticeInfo leaseholdPayNoticeInfo)throws RemoteException,IException
    {
    	return facade.getMchargeAmount(leaseholdPayNoticeInfo);
    }
    
	/**
	 * @author yunchang
	 * @date 2010-06-28 17:37
	 * @function 收款通知单保证金余额
	 * @param 合同ID
	 * @throws RemoteException
	 * @throws IException
	 */    
    public double getSurplusRecognizanceAmount(long constractID)throws RemoteException,IException
    {
    	return facade.getSurplusRecognizanceAmount(constractID);
    }
    
	/**
	 * @author yunchang
	 * @date 2010-09-08 17:31
	 * @param contractID 合同ID
	 * @param sysDate 系统开机日
	 * @return String
	 * @throws RemoteException
	 * @function 提前还款：离开机日最近的一期是否还过，如果为""则表示并没有还过，如果不为空，则表示归还过。
	 */
    public String getLastIssue(long contractID,String sysDate) throws RemoteException 
    {
    	/*
    	 * 定义DAO
    	 */
    	LoanLeaseholdRepayFormDao dao = new LoanLeaseholdRepayFormDao();
    	/*
    	 * 定义期数临时变量
    	 */
    	String issueTemp = "";
        try 
        {
        	issueTemp = dao.getLastIssue(contractID,sysDate);
        } 
        catch (Exception ex) 
        {
            throw new RemoteException("提前还款获取期数错误", ex);
        }
        return issueTemp;
    }     
}
