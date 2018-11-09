package com.iss.itreasury.ebank.obdrawnotice.bizlogic;

import java.rmi.RemoteException;
import javax.ejb.*;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.ebank.obdrawnotice.dataentity.*;
import com.iss.itreasury.ebank.obdrawnotice.dao.*;
import com.iss.itreasury.loan.contract.dao.*;
import com.iss.itreasury.loan.contract.dataentity.*;

public class OBDrawNoticeEJB implements SessionBean {
	private static Log4j log4j = null;
	SessionContext sessionContext;
	public OBDrawNoticeEJB() {
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
	public DrawNoticeInfo findDrawNoticeByID(
		long lDrawNoticeID,
		long lContractID)
		throws RemoteException,Exception {
		DrawNoticeInfo info = null;
		ContractInfo cinfo = null;
		try {
			OBDrawNoticeDao drawNoticeDao = new OBDrawNoticeDao();
			ContractDao contractDao = new ContractDao();
			info = drawNoticeDao.findDrawNoticeByID(lDrawNoticeID);
			cinfo = contractDao.findByID(lContractID);
			log4j.info(lContractID+"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			//////////////////////set contract info //////////////////////////
			info.setContractID(cinfo.getContractID());
			info.setContractCode(cinfo.getContractCode());
			info.setExamineAmount(cinfo.getExamineAmount());
			info.setInterestRate(cinfo.getInterestRate());
			info.setIntervalNum(cinfo.getIntervalNum());	
			info.setAgentRate(cinfo.getChargeRate());
			info.setClientName(cinfo.getBorrowClientName());
			info.setClientCode(cinfo.getBorrowClientCode());
			info.setLoanStart(cinfo.getLoanStart());
			info.setLoanEnd(cinfo.getLoanEnd());
			info.setUnPayAmount(cinfo.getAInfo().getUnPayAmount());
			info.setYTInfo(cinfo.getYTInfo());
			log4j.info("set contract info sucess!!");
			
		} catch (RemoteException e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return info;
	}

	public long saveDrawNotice(DrawNoticeInfo info) throws RemoteException,Exception {
		long lDrawNoticeID = -1;
		try {
			OBDrawNoticeDao drawNoticeDao = new OBDrawNoticeDao();
			lDrawNoticeID = drawNoticeDao.saveDrawNotice(info);
		} catch (RemoteException e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return lDrawNoticeID;
	}

	public long updateStatus(long lDrawNoticeID, long lStatus)
		throws RemoteException,Exception {
		long lReturn = -1;
		try {

			OBDrawNoticeDao drawNoticeDao = new OBDrawNoticeDao();
			lReturn = drawNoticeDao.updateStatus(lDrawNoticeID, lStatus);
		} catch (RemoteException e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return lReturn;
	}
	public long updateDrawNotice(DrawNoticeInfo drawNoticeInfo)
		throws Exception {
		long lReturn = -1;
		try {

			OBDrawNoticeDao drawNoticeDao = new OBDrawNoticeDao();
			lReturn = drawNoticeDao.updateDrawNotice(drawNoticeInfo);
		} catch (RemoteException e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}

		return lReturn;
	}
}