package com.iss.itreasury.loan.bizdelegation;

import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.setting.dao.LoanAssortSettingDAO;
import com.iss.itreasury.loan.setting.dataentity.LoanAssortSettingInfo;
import com.iss.itreasury.settlement.setting.dao.Sett_BankInstructionSettingDAO;
import com.iss.itreasury.settlement.setting.dataentity.BankInstructionSettingInfo;
import com.iss.itreasury.util.IException;

public class LoanAssortSettingDelegation {

	public Collection findLoanAssortSettingInfos(
			LoanAssortSettingInfo aInfo) {
		LoanAssortSettingDAO sbisd = new LoanAssortSettingDAO();
		Collection v = new ArrayList();
		try {
			v = sbisd.getAllLoanAssortSetValue(aInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return v;
	}

	public long addLoanAssortSettingInfos(LoanAssortSettingInfo aInfo)throws Exception {

		LoanAssortSettingDAO sbisd = new LoanAssortSettingDAO();
		long id = aInfo.getId();
		Collection conn = null;
		try {
			conn = sbisd.getAllLoanAssortSetValue(aInfo);
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

	public void delAssortSettingInfos(String[] ck) {
		LoanAssortSettingDAO sbisd = new LoanAssortSettingDAO();

		try {
			sbisd.deleteByIds(ck);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public LoanAssortSettingInfo findAssortInfoById(
			LoanAssortSettingInfo aInfo) {

		LoanAssortSettingDAO sbisd = new LoanAssortSettingDAO();
		Collection v = new ArrayList();
		try {

			aInfo = (LoanAssortSettingInfo) sbisd.findByID(aInfo.getId(),aInfo.getClass());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return aInfo;

	}

	public void updateAssortSetting(LoanAssortSettingInfo aInfo) {
		LoanAssortSettingDAO sbisd = new LoanAssortSettingDAO();
		try {
			sbisd.update(aInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据业务类型得到业务名称
	 * @param accountId
	 * @return
	 */
	public Collection getAssortNameAndIDByAssortId(long assortId) {
		LoanAssortSettingDAO sbisd = new LoanAssortSettingDAO();
		Collection v = new ArrayList();
		try {
			v = sbisd.getAssortNameAndIDByAssortId(assortId);
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
		}
		return v;
	}
	
	public Collection getAssortNameAndIDByAssort(long officeId, long currencyId, long typeId) {
		LoanAssortSettingDAO sbisd = new LoanAssortSettingDAO();
		Collection v = new ArrayList();
		try {
			v = sbisd.getAssortNameAndIDByAssort(officeId, currencyId, typeId);
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
		}
		return v;
	}
	
	/**
	 * 根据ID得到业务名称
	 * @param accountId
	 * @return
	 */
	public String getAssortNameByID(long Id) {
		LoanAssortSettingDAO sbisd = new LoanAssortSettingDAO();
		String result = "";
		try {
			result = sbisd.getAssortNameByID(Id);
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
