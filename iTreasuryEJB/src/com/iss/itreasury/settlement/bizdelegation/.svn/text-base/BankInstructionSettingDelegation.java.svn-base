package com.iss.itreasury.settlement.bizdelegation;

import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.setting.dao.Sett_BankInstructionSettingDAO;
import com.iss.itreasury.settlement.setting.dataentity.BankInstructionSettingInfo;
import com.iss.itreasury.util.IException;

public class BankInstructionSettingDelegation {

	public Collection findBankInstructionSettingInfos(
			BankInstructionSettingInfo aInfo) {
		Sett_BankInstructionSettingDAO sbisd = new Sett_BankInstructionSettingDAO();
		Collection v = new ArrayList();
		try {
			v = sbisd.getAllBankSetValue(aInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return v;
	}

	public long addBankInstructionSettingInfos(BankInstructionSettingInfo aInfo)throws Exception {

		Sett_BankInstructionSettingDAO sbisd = new Sett_BankInstructionSettingDAO();
		long id = aInfo.getId();
		Collection conn = null;
		try {
			conn = sbisd.getAllBankSetValue(aInfo);
			//若该记录不存在
			if (conn.size() < 1) {
				sbisd.add(aInfo);
			} else {
				throw new IException("Sett_E059");
				//throw new IException("重复");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return id;
	}

	public void delBankInstructionSettingInfos(String[] ck) {
		Sett_BankInstructionSettingDAO sbisd = new Sett_BankInstructionSettingDAO();

		try {
			sbisd.deleteByIds(ck);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public BankInstructionSettingInfo findBankInstructionById(
			BankInstructionSettingInfo aInfo) {

		Sett_BankInstructionSettingDAO sbisd = new Sett_BankInstructionSettingDAO();
		Collection v = new ArrayList();
		try {

			aInfo = (BankInstructionSettingInfo) sbisd.findByID(aInfo.getId(),
					aInfo.getClass());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return aInfo;

	}

	public void updateBankInstructiong(BankInstructionSettingInfo aInfo) {
		Sett_BankInstructionSettingDAO sbisd = new Sett_BankInstructionSettingDAO();
		try {
			sbisd.update(aInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据账户ID得到账户模式
	 * @param accountId
	 * @return
	 */
	public long getAccountModuleByAccountId(long accountId) {
		long lReturn=-1;
		try {
			lReturn= new Sett_BankInstructionSettingDAO().getAccountModuleByAccountId(accountId);
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
		}
		return lReturn;
		
	}
	/**
	 * 根据账户ID得到账户模式
	 * @param accountId
	 * @return
	 */
	public long getAccountModuleByBankId(long bankId) {
		long lReturn=-1;
		try {
			lReturn= new Sett_BankInstructionSettingDAO().getAccountModuleByBankId(bankId);
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
		}
		return lReturn;
		
	}
	/**
	 * 根据账户ID得到该账户是否可以发送指令
	 * @param aInfo
	 * @param accountId
	 * @return
	 * @throws IException
	 */
	public boolean isSendByAccountId(BankInstructionSettingInfo aInfo,
			long accountId) throws IException {
		boolean blnReturn = false;
		try {
			long lIsSend = new Sett_BankInstructionSettingDAO()
					.findIsSendByAccountId(aInfo, accountId);

			blnReturn = lIsSend == com.iss.itreasury.util.Constant.TRUE ? true
					: false;
		} catch (Exception e) {
			throw new IException(e.getMessage());
		}

		return blnReturn;
	}
	
	/**
	 * 根据开户行ID得到该账户是否可以发送指令
	 * @param aInfo
	 * @param bankId
	 * @return
	 * @throws IException
	 */
	public boolean isSendByBankId(BankInstructionSettingInfo aInfo,
			long bankId) throws IException{		
		boolean blnReturn = false;
		try {
			long lIsSend = new Sett_BankInstructionSettingDAO()
					.findIsSendByBankId(aInfo, bankId);

			blnReturn = lIsSend == com.iss.itreasury.util.Constant.TRUE ? true
					: false;
			
		} catch (Exception e) {
			throw new IException(e.getMessage());
		}
		return blnReturn;
	}
	
	public BankInstructionSettingInfo getSettingInfoByAccountId(
			BankInstructionSettingInfo bankInstructionSettingQueryInfo)
			throws IException {
		
		return new Sett_BankInstructionSettingDAO()
				.getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
	}
	
	public BankInstructionSettingInfo getSettingInfoByTransType(
			BankInstructionSettingInfo bankInstructionSettingQueryInfo)
			throws IException {
		
		return new Sett_BankInstructionSettingDAO()
				.getSettingInfoByTransType(bankInstructionSettingQueryInfo);
	}
}
