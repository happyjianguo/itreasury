package com.iss.itreasury.ebank.obpaynotice.bizlogic;

import java.rmi.RemoteException;
import javax.ejb.*;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.IException;
import javax.servlet.jsp.JspWriter;
import com.iss.itreasury.ebank.obpaynotice.dataentity.*;
import com.iss.itreasury.ebank.obpaynotice.dao.*;
import com.iss.itreasury.loan.contract.dao.*;
import com.iss.itreasury.loan.contract.dataentity.*;
import com.iss.itreasury.loan.loancommonsetting.dao.*;
import com.iss.itreasury.loan.loancommonsetting.dataentity.*;

public class OBPayNoticeEJB implements SessionBean {
	private static Log4j log4j = null;
	SessionContext sessionContext;
	public OBPayNoticeEJB() {
		log4j = new Log4j(Constant.ModuleType.EBANK, this);
	}
	public void ejbCreate() {
	}
	public void ejbRemove() {
	}
	public void ejbActivate() {
	}
	public void ejbPassivate() {
	}
	public void setSessionContext(SessionContext sessionContext) {
		this.sessionContext = sessionContext;
	}

	public PayNoticeInfo findPayNoticeByID(long lpayID, long lContractID)
		throws RemoteException,Exception {
		PayNoticeInfo info = null;
		try {

			OBPayNoticeDao payNoticeDao = new OBPayNoticeDao();
			LoanCommonSettingDao commonSetDao = new LoanCommonSettingDao();
			ContractDao contractDao = new ContractDao();

			ContractInfo cinfo = null;
			ClientInfo clientInfo, consignInfo = null;

			long lBorrowClientID = -1;

			info = payNoticeDao.findPayNoticeByID(lpayID);
			if(lContractID<0)
			{
				lContractID = info.getContractID();
			}
			cinfo = contractDao.findByID(lContractID);
		

			info.setContractCode(cinfo.getContractCode());
			info.setContractID(cinfo.getContractID());
			///////////////////////////////////////////////////////////账户行信息
			
			lBorrowClientID = cinfo.getBorrowClientID();

			clientInfo = commonSetDao.findClientByID(lBorrowClientID);
			info.setLoanClientName(clientInfo.getName());
			info.setLoanZipCode(clientInfo.getZipCode());
			info.setLoanPhone(clientInfo.getPhone());
			info.setLoanAddress(clientInfo.getAddress());
			////////////////////////////////////////////////////////////活期账户
			
			///////////////////////////////////////////////////////////合同信息
			info.setLoanAmount(cinfo.getLoanAmount());
			info.setIntervalNum(cinfo.getIntervalNum());
			info.setLoanPurpose(cinfo.getLoanPurpose());

			info.setLoanTypeID(cinfo.getLoanTypeID());
			info.setBalance(cinfo.getBalance());
			info.setUnPayAmount(cinfo.getAInfo().getUnPayAmount());
			info.setRepayAmount(cinfo.getAInfo().getRepayAmount());
			info.setOpenAmount(cinfo.getAInfo().getOpenAmount());
			info.setAdjustRate(cinfo.getAdjustRate());
			info.setExamineAmount(cinfo.getExamineAmount());
			info.setContractRate(cinfo.getBasicInterestRate()); //合同基本利率
			info.setPlanVersionID(cinfo.getPlanVersionID());
			///////////////////////////////////////////////////////////////////委托方信息
			long ConsignClientID = cinfo.getClientID();
			consignInfo = commonSetDao.findClientByID(ConsignClientID);

			if (lpayID <= 0 && lContractID > 0)
			{
				info.setConsignAccount(consignInfo.getAccount());
			}
			
			info.setConsignClientName(consignInfo.getName());

			////////////////////////////////////////////////////////////////////

			if (info.getLoanAccount()==null||info.getLoanAccount().equals("")) {
				info.setLoanAccount(cinfo.getLoanAccount());
			}

			if (info.getBankInterestID() <= 0) {
				info.setBankInterestID(cinfo.getBankInterestID()); //合同基本利率
			}
			if (info.getCommissionRate() <= 0) {
				info.setCommissionRate(cinfo.getChargeRate()); //手续
			}

			if (info.getEnd() == null) {
				info.setEnd(cinfo.getLoanEnd()); //结束日
			}

			if (info.getStart() == null) {
				info.setStart(cinfo.getLoanStart()); //开始日期
			}

			if (info.getWTInterestRate() <= 0) {
				info.setWTInterestRate(cinfo.getBasicInterestRate());
			}

			if (info.getWTInterestRate() > 0) //如果基本利率大于0
				{
				double tmpRate =
					info.getWTInterestRate()
						* (1 + cinfo.getAdjustRate() / 100);				
				info.setInterestRate(tmpRate);
			} else {

				info.setInterestRate(cinfo.getInterestRate()); //////执行利率！
			}
		} catch (RemoteException e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return info;
	}

	public long savePayNotice(PayNoticeInfo info) throws RemoteException,Exception {
		long lreturn = -1;
		try {
			OBPayNoticeDao payNoticeDao = new OBPayNoticeDao();
			lreturn = payNoticeDao.savePayNotice(info);
		} catch (RemoteException e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return lreturn;
	}
	public long updatePayNotice(PayNoticeInfo info) throws RemoteException,Exception {
		long lreturn = -1;
		try {
			OBPayNoticeDao payNoticeDao = new OBPayNoticeDao();
			lreturn = payNoticeDao.updatePayNotice(info);
		} catch (RemoteException e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return lreturn;
	}
	public long updateStatus(long ipayID, long nstatusID) throws RemoteException,Exception {
		long lreturn = -1;
		try {
			OBPayNoticeDao payNoticeDao = new OBPayNoticeDao();
			lreturn = payNoticeDao.updateStatus(ipayID, nstatusID);
		} catch (RemoteException e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return lreturn;
	}
}