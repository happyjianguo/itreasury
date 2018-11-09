package com.iss.itreasury.ebank.obfreeapply.bizlogic;
import java.rmi.RemoteException;
import javax.ejb.*;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.ebank.obfreeapply.dataentity.*;
import com.iss.itreasury.ebank.obfreeapply.dao.*;
import com.iss.itreasury.loan.contract.dao.*;
import com.iss.itreasury.loan.loanpaynotice.dao.*;
import com.iss.itreasury.loan.contract.dataentity.*;
import com.iss.itreasury.loan.loanpaynotice.dataentity.*;
public class OBFreeApplyEJB implements SessionBean
{
    private static Log4j log4j = null;
    SessionContext sessionContext;
    public OBFreeApplyEJB()
    {
        log4j = new Log4j(Constant.ModuleType.EBANK, this);
    }
    public void ejbCreate()
    {
    }
    public void ejbRemove()
    {
    }
    public void ejbActivate()
    {
    }
    public void ejbPassivate()
    {
    }
    public void setSessionContext(SessionContext sessionContext)
    {
        this.sessionContext = sessionContext;
    }
    public FreeApplyInfo findFreeApply(FreeApplyQueryInfo queryInfo)
        throws RemoteException,Exception
    {
        FreeApplyInfo info = new FreeApplyInfo();
        ContractInfo cinfo = null;
        LoanPayNoticeInfo linfo = null;
        System.out.println(
            "!++++++++++++++++++++++++++++++++++++++++++="
                + queryInfo.getLFreeApplyID());
        try
        {
            if (queryInfo.getLFreeApplyID() <= 0)
            {
                OBFreeApplyDao freeApplyDao = new OBFreeApplyDao();
                ContractDao contractDao = new ContractDao();
                LoanPayNoticeDao loanPayNoticeDao = new LoanPayNoticeDao();
                info =
                    freeApplyDao.findFreeApplyByID(queryInfo.getLFreeApplyID());
                log4j.info("get freeApplyInfo \n");
                cinfo = contractDao.findByID(queryInfo.getLContractID());
                log4j.info("get contractInfo \n");
                //log4j.info("=====0======");
                log4j.info("=====ContractID======"+queryInfo.getLContractID());
                log4j.info("=====LoanPayID======"+queryInfo.getLLoanPayID());
                if (cinfo != null)
                {
                    info.setContractCode(cinfo.getContractCode());
                    log4j.info("=====1======");
                    info.setContractID(queryInfo.getLContractID());
                    info.setLoanAmount(cinfo.getLoanAmount());
                    //log4j.info("=====2======");
                    info.setIntervalNum(cinfo.getIntervalNum());
                    //log4j.info("=====3======");
                    info.setInterestRate(cinfo.getInterestRate());
                    //log4j.info("=====4======");
                    info.setBalance(cinfo.getBalance());
                    //log4j.info("=====5======");
                    info.setLoanPurpose(cinfo.getLoanPurpose());
                    //log4j.info("=====6======");
                    info.setClientName(cinfo.getClientName());
                    //log4j.info("=====7======");
                    info.setEndDate(cinfo.getLoanEnd());
                    info.setPlanVersionID(cinfo.getPlanVersionID());  
                    info.setChargeRate(cinfo.getChargeRate());
                    log4j.info("=====2======");
                }
                else
                {
                    log4j.info("=====Contract data is null ======");
                }
                log4j.info("set Contract data entity OK!");
                //---------------------------------------------------//
                linfo =
                    loanPayNoticeDao.findLoanPayNoticeByID(
                        queryInfo.getLLoanPayID());
                log4j.info("get loanPayNoticeInfo \n");
                if (linfo != null)
                {
                    info.setConsignClientName(linfo.getConsignClientName());
                    info.setLoanPayCode(linfo.getCode());
                    info.setLoanPayAmount(linfo.getAmount());
                    info.setLoanPayBalance(linfo.getBalance());
                    info.setLoanPayInterest(linfo.getInterest());
                    info.setFreeRate(linfo.getInterest()); //默认利息
                    info.setConsignClientAccount(linfo.getConsignAccount()); //委托账户
                    info.setFreeAmount(linfo.getAmount());     
                    info.setPayLoanStartDate(linfo.getStart());     
                    info.setPayLoanEndDate(linfo.getEnd());       
                }
                else
                {
                    log4j.info("=====payNotice data is null ======");
                }
                log4j.info("set payNotice data entity OK!");
            }
            else
            {
                OBFreeApplyDao freeApplyDao = new OBFreeApplyDao();
                ContractDao contractDao = new ContractDao();
                LoanPayNoticeDao loanPayNoticeDao = new LoanPayNoticeDao();
                info =
                    freeApplyDao.findFreeApplyByID(queryInfo.getLFreeApplyID());
                log4j.info("get freeApplyInfo \n");
                cinfo = contractDao.findByID(info.getContractID());
                log4j.info("get contractInfo \n");
                linfo =
                    loanPayNoticeDao.findLoanPayNoticeByID(info.getLoanPayID());
                log4j.info("get loanPayNoticeInfo \n");
                info.setContractCode(cinfo.getContractCode());
                info.setLoanAmount(cinfo.getLoanAmount());
                info.setIntervalNum(cinfo.getIntervalNum());
                info.setInterestRate((float) cinfo.getInterestRate());
                info.setBalance(cinfo.getBalance());
                info.setLoanPurpose(cinfo.getLoanPurpose());
                info.setClientName(cinfo.getClientName());
                info.setEndDate(cinfo.getLoanEnd());
                info.setPlanVersionID(cinfo.getPlanVersionID());
                info.setClientName(cinfo.getBorrowClientName());  
                info.setChargeRate(cinfo.getChargeRate());
                log4j.info("set Contract data entity OK!");
                //---------------------------------------------------//
                info.setConsignClientName(linfo.getConsignClientName());
                info.setLoanPayCode(linfo.getCode());
                info.setLoanPayAmount(linfo.getAmount());
                info.setLoanPayBalance(linfo.getBalance());
                info.setLoanPayInterest(linfo.getInterest());    
                info.setPayLoanStartDate(linfo.getStart());     
                info.setPayLoanEndDate(linfo.getEnd()); 
                log4j.info("set payNotice data entity OK!");
            }
        }
        catch (RemoteException e)
        {
            log4j.error(e.toString());
            throw new IException("Gen_E001");
        }
        return info;
    }
    public long saveFreeApply(FreeApplyInfo freeApplyInfo) throws RemoteException,Exception
    {
        long lReturn = -1;
        try
        {
            OBFreeApplyDao freeApplyDao = new OBFreeApplyDao();
            lReturn = freeApplyDao.saveFreeApply(freeApplyInfo);
        }
        catch (RemoteException e)
        {
            log4j.error(e.toString());
            throw new IException("Gen_E001");
        }
        return lReturn;
    }
    public long updateStatus(long lFreeApplyID, long lStatus) throws RemoteException,Exception
    {
        long lReturn = -1;
        try
        {
            OBFreeApplyDao freeApplyDao = new OBFreeApplyDao();
            lReturn = freeApplyDao.updateStatus(lFreeApplyID, lStatus);
        }
        catch (RemoteException e)
        {
            log4j.error(e.toString());
            throw new IException("Gen_E001");
        }
        return lReturn;
    }
    public long updateFreeApply(FreeApplyInfo freeApplyInfo) throws RemoteException,Exception
    {
        long lReturn = -1;
        try
        {
            OBFreeApplyDao freeApplyDao = new OBFreeApplyDao();
            lReturn = freeApplyDao.updateFreeApply(freeApplyInfo);
        }
        catch (RemoteException e)
        {
            log4j.error(e.toString());
            throw new IException("Gen_E001");
        }
        return lReturn;
    }
}