package com.iss.itreasury.settlement.autotask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.ebank.obbudget.bizlogic.OBBudgetBiz;
import com.iss.itreasury.ebank.obbudget.dataentity.OBBudgetInfo;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.settlement.bankinterface.dataentity.BankAccountInfo;
import com.iss.itreasury.settlement.bizdelegation.TransCurrentDepositDelegation;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositAssembler;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Env;

public class BudgetProcess extends SettlementDAO{
	public BudgetProcess(Connection conn) {
		super(conn);
	}

	public BudgetProcess() {
	}
	
	/**
	 * 开始自动任务后台处理
	 * 
	 * @param lOfficeID
	 *            办事处
	 * @param lCurrencyID
	 *            币种
	 */
	public void StartProcess() throws Exception {
		System.out.println("自动执行用款计划开始");
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;
		long officeid = -1;
		long currencyid = -1;
		OBBudgetInfo info = new OBBudgetInfo();
		try {
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" select distinct nOfficeID,nCurrencyID from SETT_OFFICETIME c where nstatusid = "+ Constant.RecordStatus.VALID + " \n");
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				officeid = rs.getLong("nOfficeID");
				currencyid = rs.getLong("nCurrencyID");
				OBBudgetBiz biz = new OBBudgetBiz();
				List list = biz.getAllTodayAutoTask(officeid, currencyid);
				for(int i = 0 ; i < list.size() ; i ++){
					info = (OBBudgetInfo)list.get(i);
					System.out.println("账户号："+info.getAccountID());
					TransCurrentDepositInfo transCurrentDepositInfo =new TransCurrentDepositInfo();
					transCurrentDepositInfo.setTransactionTypeID(SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT);
					transCurrentDepositInfo.setOfficeID(officeid);
					transCurrentDepositInfo.setCurrencyID(currencyid);
					transCurrentDepositInfo.setAmount(info.getAmount());
					transCurrentDepositInfo.setBankID(info.getBranchId());
					transCurrentDepositInfo.setPayAccountID(info.getAccountID());
					transCurrentDepositInfo.setPayClientID(info.getClientID());
					transCurrentDepositInfo.setExecuteDate(Env.getSystemDate());
					transCurrentDepositInfo.setInterestStartDate(Env.getSystemDate());
					transCurrentDepositInfo.setInputUserID(Constant.MachineUser.InputUser);
					
					//获取外部账户信息
					/*
					List list2 = biz.getBankAccountInfo(info.getAccountID());
					BankAccountInfo bankAccountInfo = new BankAccountInfo();
					if (list2 != null && list2.size() > 0) {
						bankAccountInfo = (BankAccountInfo)list.get(0); 
					}
					if (bankAccountInfo != null) {
						transCurrentDepositInfo.setExtAccountNo(bankAccountInfo.getS_accountNo());
						transCurrentDepositInfo.setExtClientName(bankAccountInfo.getS_accountName());
						transCurrentDepositInfo.setRemitInProvince(bankAccountInfo.getS_branchAreaSeg1());
						transCurrentDepositInfo.setRemitInCity(bankAccountInfo.getS_branchAreaSeg2());
						transCurrentDepositInfo.setRemitInBank(bankAccountInfo.getS_branchName());
					}
					*/
					TransCurrentDepositAssembler assembler = new TransCurrentDepositAssembler(transCurrentDepositInfo);
					TransCurrentDepositDelegation delegation = new TransCurrentDepositDelegation();
					delegation.saveAndCheckAutomatically(assembler);
					info.setStatus(OBConstant.OBBudgetStatus.DEAL);
					biz.update(info);
				}
			}
		System.out.println("自动执行用款计划成功结束");	
		} catch (Exception e) {
			System.out.println("自动执行用款计划失败结束");
			info.setStatus(OBConstant.OBBudgetStatus.FAILEDDEAL);
			OBBudgetBiz biz = new OBBudgetBiz();
			biz.update(info);
			e.printStackTrace();
			e.printStackTrace();throw new Exception(e.getMessage());			
		} finally {
			System.out.println("自动执行用款计划关闭");
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);
			} catch (Exception e) {
				e.printStackTrace();throw new Exception("remote exception : " + e.toString());
			}
		}
	}
}
