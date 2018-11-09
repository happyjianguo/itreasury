package com.iss.itreasury.ebank.obdiscountapply.bizlogic;

import java.rmi.*;
import java.util.Vector;
import javax.ejb.*;
import java.util.*;
import com.iss.itreasury.ebank.obdiscountapply.dao.OBDiscountApplyDao;
import com.iss.itreasury.ebank.obdiscountapply.dataentity.*;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.*;
import com.iss.itreasury.loan.contract.dao.*;
import com.iss.itreasury.loan.contract.dataentity.*;
import com.iss.itreasury.loan.loancommonsetting.dao.*;
import com.iss.itreasury.loan.loancommonsetting.dataentity.*;

public class OBDiscountApplyEJB implements SessionBean {
	private javax.ejb.SessionContext mySessionCtx = null;

	private Log4j log4j = new Log4j(Constant.ModuleType.EBANK, this); //

	SessionContext sessionContext;

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

	/**
	 * Method saveDiscount1.
	 * 
	 * @param info
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long saveDiscount1(DiscountMainInfo info) throws RemoteException,
			IRollbackException {
		long lID = -1;
		OBDiscountApplyDao DiscountApplyDAO = new OBDiscountApplyDao();
		try {
			lID = DiscountApplyDAO.saveDiscount1(info);
		} catch (RemoteException e) {
			throw e;
		} catch (IRollbackException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, "Gen_E001", e
					.getMessage());
		} finally {
		}
		return lID;
	}

	/**
	 * Method saveDiscount2.
	 * 
	 * @param info
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long saveDiscount2(DiscountBillStatInfo info)
			throws RemoteException, IRollbackException {
		long lID = -1;
		OBDiscountApplyDao DiscountApplyDAO = new OBDiscountApplyDao();
		try {
			lID = DiscountApplyDAO.saveDiscount2(info);
		} catch (RemoteException e) {
			throw e;
		} catch (IRollbackException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, "Gen_E001", e
					.getMessage());
		} finally {
		}
		return lID;
	}

	/**
	 * Method updateStatus.
	 * 
	 * @param lDiscountID
	 * @param lStatusID
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long updateStatus(long lDiscountID, long lStatusID)
			throws RemoteException, IRollbackException {
		long lID = -1;
		OBDiscountApplyDao DiscountApplyDAO = new OBDiscountApplyDao();
		try {
			lID = DiscountApplyDAO.updateStatus(lDiscountID, lStatusID);
		} catch (RemoteException e) {
			throw e;
		} catch (IRollbackException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, "Gen_E001", e
					.getMessage());
		} finally {
		}
		return lID;
	}

	/**
	 * Method deleteDiscountBill.
	 * 
	 * @param lDiscountBillID
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long deleteDiscountBill(long[] lDiscountBillID)
			throws RemoteException, IRollbackException {
		long lID = -1;
		OBDiscountApplyDao DiscountApplyDAO = new OBDiscountApplyDao();
		try {
			lID = DiscountApplyDAO.deleteDiscountBill(lDiscountBillID);
		} catch (RemoteException e) {
			throw e;
		} catch (IRollbackException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, "Gen_E001", e
					.getMessage());
		} finally {
		}
		return lID;
	}

	/**
	 * Method findDiscountBillByDiscountID.
	 * 
	 * @param info
	 * @return Vector
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Vector findDiscountBillByDiscountID(DiscountBillQueryInfo info)
			throws RemoteException, IRollbackException {
		Vector vctResult = null;
		OBDiscountApplyDao DiscountApplyDAO = new OBDiscountApplyDao();
		try {
			vctResult = DiscountApplyDAO.findDiscountBillByDiscountID(info);
		} catch (RemoteException e) {
			throw e;
		} catch (IRollbackException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, "Gen_E001", e
					.getMessage());
		} finally {
		}
		return vctResult;
	}

	/**
	 * Method saveDiscountBill.
	 * 
	 * @param info
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long saveDiscountBill(DiscountBillInfo info) throws RemoteException,
			IRollbackException {
		long lID = -1;
		OBDiscountApplyDao DiscountApplyDAO = new OBDiscountApplyDao();
		try {
			lID = DiscountApplyDAO.saveDiscountBill(info);
		} catch (RemoteException e) {
			throw e;
		} catch (IRollbackException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, "Gen_E001", e
					.getMessage());
		} finally {
		}
		return lID;
	}

	/**
	 * Method updateDiscountBill.
	 * 
	 * @param info
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long updateDiscountBill(DiscountBillInfo info)
			throws RemoteException, IRollbackException {
		long lID = -1;
		OBDiscountApplyDao DiscountApplyDAO = new OBDiscountApplyDao();
		try {
			lID = DiscountApplyDAO.updateDiscountBill(info);
		} catch (RemoteException e) {
			throw e;
		} catch (IRollbackException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, "Gen_E001", e
					.getMessage());
		} finally {
		}
		return lID;
	}

	/**
	 * Method findDiscountBillByID.
	 * 
	 * @param lDiscountBillID
	 * @return DiscountBillInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public DiscountBillInfo findDiscountBillByID(long lDiscountBillID)
			throws RemoteException, IRollbackException {
		DiscountBillInfo resultInfo = null;
		OBDiscountApplyDao DiscountApplyDAO = new OBDiscountApplyDao();
		try {
			resultInfo = DiscountApplyDAO.findDiscountBillByID(lDiscountBillID);
		} catch (RemoteException e) {
			throw e;
		} catch (IRollbackException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, "Gen_E001", e
					.getMessage());
		} finally {
		}
		return resultInfo;
	}

	/**
	 * Method findDiscountByID.
	 * 
	 * @param lDiscountID
	 * @return DiscountInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public DiscountInfo findDiscountByID(long lDiscountID)
			throws RemoteException, IRollbackException {
		DiscountInfo resultInfo = null;
		OBDiscountApplyDao DiscountApplyDAO = new OBDiscountApplyDao();
		try {
			resultInfo = DiscountApplyDAO.findDiscountByID(lDiscountID);
		} catch (RemoteException e) {
			throw e;
		} catch (IRollbackException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, "Gen_E001", e
					.getMessage());
		} finally {
		}
		return resultInfo;
	}

	/**
	 * add by wjliu 2007/3/19
	 * 
	 * @param lDiscountBillID
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	
	public Vector findByID(DiscountBillQueryInfo discountBillQueryInfo)
			throws RemoteException, IRollbackException {
		//DiscountBillInfo discountBillInfo = new DiscountBillInfo();

		OBDiscountApplyDao DiscountApplyDAO = new OBDiscountApplyDao();

		Vector v = null;
		try {
			v = DiscountApplyDAO.findByID(discountBillQueryInfo);
			// discountInfo = c;
		}
		// catch (RemoteException e)
		// {
		// throw e;
		// }
		catch (Exception e) {
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, "Gen_E001", e
					.getMessage());
		}

		return v;
	}

	// private long saveUploadFileToDB(long lDiscountID,
	// com.jspsmart.upload.SmartUpload mySmartUpload) throws RemoteException,
	// IRollbackException;
	/**
	 * Method findDiscountBillByContractID.
	 * 
	 * @param info
	 * @return Collection
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection findDiscountBillByContractID(DiscountBillQueryInfo info)
			throws RemoteException, IRollbackException {
		Collection cResult = null;
		OBDiscountApplyDao DiscountApplyDAO = new OBDiscountApplyDao();
		try {
			cResult = DiscountApplyDAO.findDiscountBillByContractID(info);
		} catch (RemoteException e) {
			throw e;
		} catch (IRollbackException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, "Gen_E001", e
					.getMessage());
		} finally {
		}
		return cResult;
	}

	/**
	 * Method findDiscountBillByContractID.
	 * 
	 * @param info
	 * @return Collection
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public DiscountCredenceInfo findDiscountCredence(
			DiscountCredenceQueryInfo info) throws RemoteException,
			IRollbackException {
		System.out.println("hehreh0");
		DiscountCredenceInfo cResult = new DiscountCredenceInfo();
		ContractInfo conInfo = new ContractInfo();
		ClientInfo cInfo = new ClientInfo();
		DiscountBillInfo dbInfo = new DiscountBillInfo();
		OBDiscountApplyDao DiscountApplyDAO = new OBDiscountApplyDao();
		ContractDao ContractDAO = new ContractDao();
		LoanCommonSettingDao LoanCommonSettingDAO = new LoanCommonSettingDao();
		try {
			if (info.getDiscountCredenceID() > 0) {
				cResult = DiscountApplyDAO.findDiscountCredenceByID(info
						.getDiscountCredenceID());
			} else {
				/*
				 * 则调用贷款模块的ContractDao的findByID方法
				 * 和贷款模块下的LoanCommonSettingDao的findClientByID方法
				 * 以及OBDiscountApplyDao的findDiscountBillByBillsID方法
				 */
				System.out.println("hehreh1");
				conInfo = ContractDAO.findByID(info.getContractID());
				System.out.println("hehreh2");
				// cInfo =
				// LoanCommonSettingDAO.findClientByID(info.getClientID());
				cInfo = LoanCommonSettingDAO.findClientByID(conInfo
						.getBorrowClientID());
				System.out.println("hehreh3");
				dbInfo = DiscountApplyDAO.findDiscountBillByBillsID(info
						.getContractID(), info.getBillsID());
				// cResult = DiscountApplyDAO.findDiscountBillByBillsID();
				cResult.setDiscountContractCode(DataFormat.formatString(conInfo
						.getContractCode()));
				cResult.setExamineAmount(conInfo.getExamineAmount());
				cResult.setDiscountRate(conInfo.getDiscountRate());
				cResult.setCheckAmount(conInfo.getCheckAmount());

				cResult.setApplyClientName(cInfo.getName());
				cResult.setApplyAccount(cInfo.getBankAccount1());
				cResult.setApplyBank(cInfo.getBank1());

				cResult.setBillAmount(dbInfo.getTotalAmount());
				cResult.setBillInterest(dbInfo.getTotalInterest());
				cResult.setBillCheckAmount(dbInfo.getTotalAmount()
						- dbInfo.getTotalInterest());
				cResult.setDraftTypeID(dbInfo.getAcceptPOTypeID());
				System.out.println("typeid===" + cResult.getDraftTypeID());
				cResult.setDraftCode(DataFormat.formatString(dbInfo.getCode()));
				cResult.setPublicDate(dbInfo.getCreate());
				cResult.setAtTerm(dbInfo.getEnd());
				cResult.setAcceptClientName(dbInfo.getUser());
				//
				cResult.setAcceptBank(dbInfo.getBank());

				// cResult.setBillAmount(
				// cResult.setBillInterest(
				// cResult.setBillCheckAmount(

			}
			// to be changed
			// cResult = DiscountApplyDAO.findDiscountBillByContractID(info);
		} catch (RemoteException e) {
			throw e;
		} catch (IRollbackException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, "Gen_E001", e
					.getMessage());
		} finally {
		}
		System.out.println("the result is" + cResult);
		return cResult;
	}

	/**
	 * Method saveDiscountCredence.
	 * 
	 * @param info
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long saveDiscountCredence(DiscountCredenceInfo info)
			throws RemoteException, IRollbackException {
		long lID = -1;
		OBDiscountApplyDao DiscountApplyDAO = new OBDiscountApplyDao();
		try {
			lID = DiscountApplyDAO.saveDiscountCredence(info);
		} catch (RemoteException e) {
			throw e;
		} catch (IRollbackException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, "Gen_E001", e
					.getMessage());
		} finally {
		}
		return lID;
	}

	/**
	 * ninh 2004-3-10 Method cancelDiscountCredenceByID. 取消贴现票据明细信息
	 * 
	 * @param lDiscountCredenceID
	 * @return long
	 * @throws Exception
	 */
	public long cancelDiscountCredenceByID(long lDiscountCredenceID)
			throws RemoteException, IRollbackException {
		long lReturn = -1;
		OBDiscountApplyDao DiscountApplyDAO = new OBDiscountApplyDao();
		try {
			lReturn = DiscountApplyDAO
					.cancelDiscountCredenceByID(lDiscountCredenceID);
		} catch (RemoteException e) {
			throw e;
		} catch (IRollbackException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, "Gen_E001", e
					.getMessage());
		} finally {
		}
		return lReturn;
	}

	/**
	 * Method updateDiscount. 修改贴现信息
	 * 
	 * @param info
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long updateDiscount(DiscountInfo info) throws RemoteException,
			IRollbackException {
		long lID = -1;
		OBDiscountApplyDao DiscountApplyDAO = new OBDiscountApplyDao();
		try {
			lID = DiscountApplyDAO.updateDiscount(info);
		} catch (RemoteException e) {
			throw e;
		} catch (IRollbackException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, "Gen_E001", e
					.getMessage());
		} finally {
		}
		return lID;
	}

	/**
	 * Method updateDiscountCredence.
	 * 
	 * @param info
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long updateDiscountCredence(DiscountCredenceInfo info)
			throws RemoteException, IRollbackException {
		long lID = -1;
		OBDiscountApplyDao DiscountApplyDAO = new OBDiscountApplyDao();
		try {
			lID = DiscountApplyDAO.updateDiscountCredence(info);
		} catch (RemoteException e) {
			throw e;
		} catch (IRollbackException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, "Gen_E001", e
					.getMessage());
		} finally {
		}
		return lID;
	}

	/**
	 * Method updateCredenceStatus.
	 * 
	 * @param lDiscountCredenceID
	 * @param lStatusID
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long updateCredenceStatus(long lDiscountCredenceID, long lStatusID)
			throws RemoteException, IRollbackException {
		long lID = -1;
		OBDiscountApplyDao DiscountApplyDAO = new OBDiscountApplyDao();
		try {
			lID = DiscountApplyDAO.updateCredenceStatus(lDiscountCredenceID,
					lStatusID);
			System.out.println("lID!!!!!!!!!!!!!===(*******)" + lID);
		} catch (RemoteException e) {
			throw e;
		} catch (IRollbackException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, "Gen_E001", e
					.getMessage());
		} finally {
		}
		return lID;
	}

	public static void main(String[] args) {

	}
}