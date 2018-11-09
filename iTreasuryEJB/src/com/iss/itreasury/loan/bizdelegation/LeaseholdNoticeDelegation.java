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
 * <p>Title: ��������(�տ�/����)֪ͨ��</p>
 *
 * <p>Description: �Ŵ�������������</p>
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
            throw new RemoteException("����CreateException", ce);
        }
    }

    /**  ���������տ�֪ͨ�� */
    /**
     * ���������տ�֪ͨ���ı������
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
     * ���������տ�֪ͨ����ȡ������
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
     * ���������տ�֪ͨ������˲���
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
     * ���������տ�֪ͨ���ĵ��ʲ�ѯ����
     * @param paynoticeid long
     * @return LeaseholdPayNoticeInfo
     * @throws RemoteException
     */
    public LeaseholdPayNoticeInfo findPayNoticeByID(long paynoticeid) throws
            RemoteException {
        return facade.findPayNoticeByID(paynoticeid);
    }

    /**
     * ���������տ�֪ͨ���Ķ�ʲ�ѯ����
     * @param paynoticeinfo LeaseholdPayNoticeInfo
     * @return Collection
     * @throws RemoteException
     */
    public Collection findPayNoticeByMultiOption(LeaseholdPayNoticeInfo
                                                 paynoticeinfo) throws
            RemoteException {
        return facade.findPayNoticeByMultiOption(paynoticeinfo);
    }

    /**  �������޻���֪ͨ�� */
    
    /**
     * added by xiong fei 2010-07-15
     * �жϸú�ͬ���Ƿ���֮ǰ��δ������
     * @param contractId,officeID,currencyID
     * @return long -1 ��ʾ��δ���� 1 Ϊû��
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
     * �������޻���֪ͨ���ı������
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
     * �������޻���֪ͨ����ȡ������
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
     * �������޻���֪ͨ������˲���
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
     * �������޻���֪ͨ���ĵ��ʲ�ѯ����
     * @param repaynoticeid long
     * @return LeaseholdRepayNoticeInfo
     * @throws RemoteException
     */
    public LeaseholdRepayNoticeInfo findRepayNoticeByID(long repaynoticeid) throws
            RemoteException {
        return facade.findRepayNoticeByID(repaynoticeid);

    }

    /**
     * �������޻���֪ͨ���Ķ�ʲ�ѯ����
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
     * ���ݺ�ͬ�ı��ID�õ���ͬ����
     *
     * @param lContractID long
     * @return com.iss.itreasury.loan.contract.dataentity.ContractInfo
     * @throws RemoteException
     */
    public static ContractInfo getLeaseholdContractInfo(long lContractID) throws
            RemoteException {
        Contract c_ejb;
        ContractInfo info = new ContractInfo();
        //�õ���ͬ����
        try {
            ContractHome chome;
            try {
                chome = (ContractHome) EJBHomeFactory.getFactory().
                        lookUpHome(ContractHome.class);
            } catch (IException e) {
                throw new RemoteException("EJBHomeFactory���Ӵ���", e);
            }
            c_ejb = (Contract) chome.create();
            info = c_ejb.findByID(lContractID);
        } catch (IException ex) {
            throw new RemoteException("����IException", ex);
        } catch (CreateException ce) {
            throw new RemoteException("����CreateException", ce);
        }
        return info;
    }

    /**
     * ��λ����
     *
     * @param usrID long
     * @return com.iss.itreasury.loan.loancommonsetting.dataentity.ClientInfo
     * @throws RemoteException
     */
    public static ClientInfo getLeaseholdClientInfo(long usrID) throws
            RemoteException {
        LoanCommonSetting lc_ejb;
        ClientInfo info = new ClientInfo();
        //�õ���ͬ����
        try {
            LoanCommonSettingHome loanCommonSettingHome;
            try {
                loanCommonSettingHome = (LoanCommonSettingHome) EJBHomeFactory.
                                        getFactory().
                                        lookUpHome(LoanCommonSettingHome.class);
            } catch (IException e) {
                throw new RemoteException("EJBHomeFactory���Ӵ���", e);
            }
            lc_ejb = (LoanCommonSetting) loanCommonSettingHome.create();

            info = lc_ejb.findClientByID(usrID);

        } catch (IException ex) {
            throw new RemoteException("����IException", ex);
        } catch (CreateException ce) {
            throw new RemoteException("����CreateException", ce);
        }
        return info;
    }

    /**
     * ��ȡ�տ�֪ͨ�����
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
            throw new RemoteException("��ȡ�տ�֪ͨ����ŷ���LoanDAOException", ex);
        }
        return sNewApplyCode;
    }
    
    /**
     * ��ȡ����֪ͨ�����
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
            throw new RemoteException("��ȡ����֪ͨ����ŷ���LoanDAOException", ex);
        }
        return sNewApplyCode;
    }
    
    /**
     * �������ڻ���������޸��ڵ���𳥸����
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
            throw new RemoteException("��ȡ��𳥸�����ϸ", ex);
        }
        return returnInfo;
    }
    
    /**added by xiong fei 2010-07-27
     * ���ݺ�ͬID����������޸��ڵ���𳥸����
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
            throw new RemoteException("��ȡ��𳥸�����ϸ", ex);
        }
        return returnInfo;
    }


	    /**
     * �����������ʵ���֪ͨ���ı������
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
     * ���������ʵ���֪ͨ����ȡ������
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
     * �����������ʵ���֪ͨ������˲���
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
	 * �����������ʵ���֪ͨ���ĵ��ʲ�ѯ����
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
     * �����������ʵ���֪ͨ���Ķ�ʲ�ѯ����
     * @param noticeinfo LeaseholdInterestAdjustInfo
     * @return Collection
     * @throws RemoteException
     */
    public Collection findAdjustNoticeByMultiOption(LeaseholdInterestAdjustInfo
            noticeinfo) throws RemoteException {
        return facade.findAdjustNoticeByMultiOption(noticeinfo);
    }
    
    /**
     * ��ѯδ�տ���(��ͬδ���տ�֪ͨ�����)
     * @param lContractID long
     * @return unPayAmount
     * @throws RemoteException
     */
    public double findUnPayAmountByID(long lContractID,long payID) throws RemoteException
    {
        return facade.findUnPayAmountByID(lContractID,payID);
    }
    
    /**
	 * @param lContractID ��ͬID
	 * @param rePayID ����֪ͨ��ID
	 * @return ��ѯδ������
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
	 * @function ���ҳ���¼�����շ�֪ͨ����Ϣ(LOAN_ASSURECHARGEFORM)����ͬ��ͬ�ŵ���ȡ����˻�,Ϊ����տ�֪ͨ����У��,��ͬ��ͬ����ȡ����˻����ձ�֤���˻�������ͬ
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
	 * @function ���ҳ�������������(LOAN_LOANFORM)�е�����������,Ϊ����տ�֪ͨ����У��,����������ܺͲ��ܳ�������������
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
	 * @function �տ�֪ͨ����֤�����
	 * @param ��ͬID
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
	 * @param contractID ��ͬID
	 * @param sysDate ϵͳ������
	 * @return String
	 * @throws RemoteException
	 * @function ��ǰ����뿪���������һ���Ƿ񻹹������Ϊ""���ʾ��û�л����������Ϊ�գ����ʾ�黹����
	 */
    public String getLastIssue(long contractID,String sysDate) throws RemoteException 
    {
    	/*
    	 * ����DAO
    	 */
    	LoanLeaseholdRepayFormDao dao = new LoanLeaseholdRepayFormDao();
    	/*
    	 * ����������ʱ����
    	 */
    	String issueTemp = "";
        try 
        {
        	issueTemp = dao.getLastIssue(contractID,sysDate);
        } 
        catch (Exception ex) 
        {
            throw new RemoteException("��ǰ�����ȡ��������", ex);
        }
        return issueTemp;
    }     
}
