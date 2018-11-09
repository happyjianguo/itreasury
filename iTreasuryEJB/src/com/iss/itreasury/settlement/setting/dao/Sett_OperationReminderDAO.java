/*
 * Created on 2004-10-12
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.setting.dao;

import com.iss.itreasury.settlement.base.SettlementDAO;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.remind.process.RemindProcess;
import com.iss.itreasury.settlement.setting.dataentity.OperationReminderInfo;
import com.iss.itreasury.util.Log;

/**
 * @author weilu
 * 
 * To change the template for this generated type comment go to Window - Preferences - Java - Code Generation - Code and
 * Comments
 */
public class Sett_OperationReminderDAO extends SettlementDAO {
	/**
	 * 
	 */
	public Sett_OperationReminderDAO() {
		super("Sett_OperationReminder", true);
		// TODO Auto-generated constructor stub
	}

	/**
	 * <ol>
	 * 查询业务提醒设置
	 * <ul>
	 * <li>操作数据库表OperationReminder</li>
	 * </ul>
	 * <ol>
	 * 
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lOfficeID
	 */
	public OperationReminderInfo findOperationReminder(long lOfficeID, long lCurrencyID)
			throws SettlementException {
		OperationReminderInfo info = null;
		try {
			initDAO();
			String strSQL = "SELECT * FROM Sett_OperationReminder where NOFFICEID=? and nCurrencyID=?";
			transPS = prepareStatement(strSQL);
			transPS.setLong(1, lOfficeID);
			transPS.setLong(2, lCurrencyID);
			transRS = executeQuery();
			if (transRS != null && transRS.next()) {
				info = new OperationReminderInfo();
				info.m_lFixedDeposit = transRS.getLong("NFIXEDDEPOSIT");
				info.m_lLoan = transRS.getLong("NLOAN");
				info.m_lIsRemind = transRS.getLong("NISREMIND");
				info.m_lUnification = transRS.getLong("NUNIFICATION");
				info.m_lPreInput = transRS.getLong("NPREINPUT");
				info.m_lInterestCompute = transRS.getLong("NINTERESTCOMPUTE");
				info.m_l973 = transRS.getLong("N973");
				info.m_lNetOperation = transRS.getLong("NNETOPERATION");
				info.m_lSecOperation = transRS.getLong("NSecOPERATION");
				info.m_lOverDraft = transRS.getLong("NOVERDRAFT");
				info.m_lOfficeID = transRS.getLong("NOFFICEID");
				info.m_lPayForm = transRS.getLong("NLOANNOTE");
				info.m_lAheadRepayForm = transRS.getLong("NPREFORM");
				info.m_lFreeForm = transRS.getLong("nFreeForm");
				info.m_lAccountDeadLine = transRS.getLong("nAccountDeadLine");
				info.m_lNegotiation = transRS.getLong("nNegotiation");
				info.m_lEnsureDeposit = transRS.getLong("NENSUREDEPOSIT"); // 保证金存款提醒
				info.m_lOBSoundRemind = transRS.getLong("nIsOBSoundRemind");

				info.m_lIsNeedBillRemindDay = transRS.getLong("nBillRemindDay");
				info.m_lIsNeedBillConsignReceiveDay = transRS.getLong("nBillConsignReceiveDay");
				info.m_lIsNeedFreezeDay = transRS.getLong("nFreezeDay");
				info.m_lIsNeedLossDay = transRS.getLong("nLossDay");
				info.m_lIsNeedPrimnessDay = transRS.getLong("nPrimnessDay");
				info.m_lExhibitionDay = transRS.getLong("nExtendNotice");

				info.m_dIsNeedBankLowBalance = transRS.getDouble("mBankLowBalance");
				info.m_dIsNeedLowRate = transRS.getDouble("mBankLowRate");
				info.m_OffBalanceForm = transRS.getLong("OFFBALANCENOTICE");
			}
			this.finalizeDAO();
		} catch (Exception e) {
			e.printStackTrace();
			e.printStackTrace();throw new SettlementException();
		}
		return info;
	}

	/**
	 * 业务提醒设置
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol>
	 * <b>业务提醒设置</b>
	 * <ul>
	 * <li>操作数据库表OperationReminder
	 * <li>更新此表，此表只有一条记录
	 * </ul>
	 * </ol>
	 * 
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lOfficeID
	 * @param lFixedDeposit
	 * @param lLoan
	 * @param lUnification
	 * @param lPreInput
	 * @param lInterestCompute
	 * @param l973
	 * @param lNetOperation
	 * @param lOverDraft
	 * @return void
	 * @exception Exception
	 */
	public long saveOperationReminder(OperationReminderInfo settingInfo) throws SettlementException {
		long lResult = -1;
		long lCount = 0;
		long lID = -1;
		StringBuffer strBuff = null;
		try {
			initDAO();
			// 同一办事处和币种下只能有一个设置
			strBuff = new StringBuffer();
			strBuff.append(" SELECT COUNT(*) FROM Sett_OperationReminder where NOfficeID="
					+ settingInfo.m_lOfficeID + " and nCurrencyID=" + settingInfo.m_lCurrencyID);
			Log.print(strBuff.toString());
			transPS = prepareStatement(strBuff.toString());
			transRS = executeQuery();
			transRS.next();
			lCount = transRS.getLong(1);
			transRS.close();
			transRS = null;
			transPS.close();
			transPS = null;
			// insert the new record;
			if (lCount > 0) {
				String strSQL = "DELETE FROM Sett_OperationReminder  where  NOfficeID =  "
						+ settingInfo.m_lOfficeID + " and nCurrencyID=" + settingInfo.m_lCurrencyID;
				transPS = prepareStatement(strSQL);
				transPS.executeUpdate();
				transPS.close();
				transPS = null;
			}
			// 得到设置的ID
			strBuff = new StringBuffer();
			strBuff.append(" SELECT nvl(max(ID)+1,1) FROM Sett_OperationReminder");
			transPS = prepareStatement(strBuff.toString());
			transRS = transPS.executeQuery();
			transRS.next();
			lID = transRS.getLong(1);
			transRS.close();
			transRS = null;
			transPS.close();
			transPS = null;

			strBuff = new StringBuffer();
			strBuff.append("INSERT INTO Sett_OperationReminder(ID, nFixedDeposit,");
			strBuff.append("nLoan ,nIsRemind, NLOANNOTE,");
			strBuff.append("nPreInput,  nInterestCompute,  NPREFORM,  nNetOperation,");
			strBuff.append("nSecOperation , nOverDraft,nOfficeID,nCurrencyID,");
			strBuff.append("nFreeForm,nIsOBSoundRemind,nBillRemindDay,nBillConsignReceiveDay,");
			strBuff.append("nFreezeDay,nLossDay,nPrimnessDay,mBankLowBalance,mBankLowRate,");
			strBuff.append(" nAccountDeadLine,nExtendNotice,nNegotiation,NENSUREDEPOSIT,OFFBALANCENOTICE) ");
			strBuff.append(" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			Log.print(strBuff.toString());
			transPS = prepareStatement(strBuff.toString());
			transPS.setLong(1, lID);
			transPS.setLong(2, settingInfo.m_lFixedDeposit);
			transPS.setLong(3, settingInfo.m_lLoan);
			transPS.setLong(4, 1); // --------------------------pay
			// attention?????????????
			transPS.setLong(5, settingInfo.m_lPayForm);
			transPS.setLong(6, settingInfo.m_lPreInput);
			transPS.setLong(7, settingInfo.m_lInterestCompute);
			transPS.setLong(8, settingInfo.m_lAheadRepayForm);
			transPS.setLong(9, settingInfo.m_lNetOperation);
			transPS.setLong(10, settingInfo.m_lSecOperation);
			transPS.setLong(11, settingInfo.m_lOverDraft);
			transPS.setLong(12, settingInfo.m_lOfficeID);
			transPS.setLong(13, settingInfo.m_lCurrencyID);
			transPS.setLong(14, settingInfo.m_lFreeForm);
			transPS.setLong(15, settingInfo.m_lOBSoundRemind);

			transPS.setLong(16, settingInfo.m_lIsNeedBillRemindDay);
			transPS.setLong(17, settingInfo.m_lIsNeedBillConsignReceiveDay);
			transPS.setLong(18, settingInfo.m_lIsNeedFreezeDay);
			transPS.setLong(19, settingInfo.m_lIsNeedLossDay);
			transPS.setLong(20, settingInfo.m_lIsNeedPrimnessDay);

			transPS.setDouble(21, settingInfo.m_dIsNeedBankLowBalance);
			transPS.setDouble(22, settingInfo.m_dIsNeedLowRate);
			transPS.setDouble(23, settingInfo.m_lAccountDeadLine);
			transPS.setLong(24, settingInfo.m_lExhibitionDay);
			transPS.setLong(25, settingInfo.m_lNegotiation);
			transPS.setLong(26, settingInfo.m_lEnsureDeposit);	//保证金存款提醒设置
			transPS.setLong(27, settingInfo.m_OffBalanceForm);	//放款单逾期转表外提醒设置 Add By Wangzhen
			lResult = transPS.executeUpdate();

			finalizeDAO();

			Log.print("--刷新业务提醒后台处理---");
			// RemindProcess process = new RemindProcess();
			// process.StartRemindProcess();
		} catch (Exception e) {
			e.printStackTrace();
			e.printStackTrace();throw new SettlementException();
		}

		return lResult;
	}

	public static void main(String args[]) throws SettlementException {
		Sett_OperationReminderDAO app = new Sett_OperationReminderDAO();
		OperationReminderInfo settingInfo = new OperationReminderInfo();
		settingInfo.m_lExhibitionDay = 9999;
		settingInfo.m_lOfficeID = 1;
		settingInfo.m_lCurrencyID = 1;
		app.saveOperationReminder(settingInfo);

	}
}
