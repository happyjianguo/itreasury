/*
 * Created on 2004-11-29 To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

package com.iss.itreasury.settlement.query.queryobj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.clientcenter.query.queryobj.BaseQueryObject;
import com.iss.itreasury.settlement.query.resultinfo.ContrastAccountBalanceBillInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryConditionInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log4j;

/**
 * @author kenny
 */
public class QAccountBalanceInfo extends BaseQueryObject {

	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);

	public QAccountBalanceInfo() {
		super();
	}

	/**
	 * ȡ�ö����˻�ͬ����˾���ڻ��ĶԱ��嵥
	 * 
	 * @param QueryConditionInfo conditionInfo
	 * @param Connection conn
	 * @throws Exception
	 */
	public Collection getBalanceContrastBillInfo(QueryConditionInfo conditionInfo) throws Exception {
		Connection conn = Database.getConnection();
		Collection collection = this.getBalanceContrastBillInfo(conditionInfo,conn);
		cleanup(conn);
		return collection;
	}

	/**
	 * ȡ�ö����˻�ͬ����˾���ڻ��ĶԱ��嵥
	 * 
	 * @param QueryConditionInfo conditionInfo
	 * @param Connection conn
	 * @return Collection
	 * @throws Exception
	 */
	public Collection getBalanceContrastBillInfo(QueryConditionInfo conditionInfo, Connection conn) throws Exception {
		ArrayList list = new ArrayList();
		Collection collection = null;
		// ��ɶԱ��嵥
		if (super.isToday(conditionInfo.getOfficeId(),conditionInfo.getCurrencyId(),conditionInfo.getQueryDate())) {
			collection = this.getCurAccountBalance(conditionInfo, conn);
			Iterator iterator = null;
			if (collection != null) {
				iterator = collection.iterator();
			}
			while (iterator != null && iterator.hasNext()) {
				ContrastAccountBalanceBillInfo info = (ContrastAccountBalanceBillInfo) iterator.next();
				//ȡ��δ���˽跽������
				conditionInfo.setDirection(SETTConstant.DebitOrCredit.DEBIT);
				//ȡ�������˻��Ľ跽������[������]
				conditionInfo.setAccountId(info.getBankAccountId());
				double debitAmount = this.getCurAmountByBankAccountID(conditionInfo,conn);
				//����δ���˽跽������
				info.setDebitMoney(debitAmount);
				//ȡ��δ���˴���������
				//ȡ�������˻��Ĵ���������[������]
				conditionInfo.setDirection(SETTConstant.DebitOrCredit.CREDIT);
				conditionInfo.setAccountId(info.getBankAccountId());
				double creditAmount = this.getCurAmountByBankAccountID(conditionInfo,conn);
				//����δ���˴���������
				info.setCreditMoney(creditAmount);
				list.add(info);
			}
		} else {
			collection = this.getHisAccountBalance(conditionInfo, conn);
			Iterator iterator = null;
			if (collection != null) {
				iterator = collection.iterator();
			}
			while (iterator != null && iterator.hasNext()) {
				ContrastAccountBalanceBillInfo info = (ContrastAccountBalanceBillInfo) iterator.next();
				//ȡ��δ���˽跽������
				conditionInfo.setDirection(SETTConstant.DebitOrCredit.DEBIT);
				//ȡ�������˻��Ľ跽������[������]
				conditionInfo.setAccountId(info.getBankAccountId());
				double debitAmount = this.getHisAmountByBankAccountID(conditionInfo,conn);
				//����δ���˽跽������
				info.setDebitMoney(debitAmount);
				//ȡ��δ���˴���������
				//ȡ�������˻��Ĵ���������[������]
				conditionInfo.setDirection(SETTConstant.DebitOrCredit.CREDIT);
				conditionInfo.setAccountId(info.getBankAccountId());
				double creditAmount = this.getHisAmountByBankAccountID(conditionInfo,conn);
				//����δ���˴���������
				info.setCreditMoney(creditAmount);
				list.add(info);
			}
		}

		return list;
	}

	/***************************************************************************
	 * ȡ���˻�����Ϣ[����]
	 * 
	 * @param QueryConditionInfo conditionInfo
	 * @param Connection conn
	 * @return Collection
	 * @throws Exception
	 **************************************************************************/
	public Collection getCurAccountBalance(QueryConditionInfo conditionInfo, Connection conn)
			throws Exception {
		ArrayList list = new ArrayList();
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();

		strSQL.append("select aa.accountId,aa.accountNo,aa.accountName,aa.accountBalance,bb.bankAccountId,bb.bankAccountNo,bb.bankAccountBalance \n");
		strSQL.append("from \n");
		strSQL.append("( \n");
		strSQL.append("	select a.Id accountId, a.saccountNo accountNo, a.sname accountName, sum(b.mbalance) accountBalance \n");
		strSQL.append("	from sett_account a,sett_subaccount b \n");
		strSQL.append("	where a.Id=b.naccountId \n");
		strSQL.append("		and a.nstatusId="+SETTConstant.AccountStatus.NORMAL+" \n");
		strSQL.append("		and a.ncheckstatusId="+SETTConstant.AccountCheckStatus.CHECK+" \n");
		strSQL.append("		and a.ncurrencyId="+conditionInfo.getCurrencyId()+" \n");
		strSQL.append("		and a.nofficeId="+conditionInfo.getOfficeId()+" \n");
		strSQL.append("		and a.naccountTypeId in ( \n");
		strSQL.append("		select id from sett_accountType where naccountGroupId in ("+SETTConstant.AccountGroupType.CURRENT+")) \n");
		strSQL.append("		and b.nstatusId="+SETTConstant.SubAccountStatus.NORMAL+" \n");
		strSQL.append("	group by a.Id, a.saccountNo, a.sname \n");
		strSQL.append(") aa, \n");
		strSQL.append("( \n");
		strSQL.append("		select a.n_Id bankAccountId, a.n_subjectId accountId, a.s_accountNo bankAccountNo, n_balance bankAccountBalance \n");
		strSQL.append("	from bs_bankaccountinfo a, \n");
		strSQL.append("		(select n_accountId,n_balance from bs_acctcurbalance \n");
		strSQL.append("		) b \n");
		strSQL.append("	where b.n_accountId(+)=a.n_Id \n");
		strSQL.append("		and a.n_currencyType="+conditionInfo.getCurrencyId()+" \n");
		strSQL.append("		and a.n_accountstatus=1 \n");
		strSQL.append("		and a.n_rdstatus=1 \n");
		strSQL.append(") bb \n");
		strSQL.append("where 1=1 \n");
		strSQL.append("	and aa.accountId=bb.accountId \n");
		strSQL.append("order by aa.accountNo \n");
		log.info(strSQL.toString());
		try {
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				ContrastAccountBalanceBillInfo contrastAccountBalanceBillInfo = new ContrastAccountBalanceBillInfo();
				contrastAccountBalanceBillInfo.setAccountId(rs.getLong("accountId"));
				contrastAccountBalanceBillInfo.setAccountNo(rs.getString("accountNo"));
				contrastAccountBalanceBillInfo.setAccountName(rs.getString("accountName"));
				contrastAccountBalanceBillInfo.setAccountBalance(rs.getDouble("accountBalance"));
				contrastAccountBalanceBillInfo.setBankAccountNo(rs.getString("bankAccountNo"));
				contrastAccountBalanceBillInfo.setBankAccountBalance(rs.getDouble("bankAccountBalance"));
				list.add(contrastAccountBalanceBillInfo);
			}
		} catch (Exception e) {
			log.error("ȡ���˻�����Ϣ[����]����");
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			cleanup(rs);
			cleanup(ps);
		}
		return list;
	}

	/***************************************************************************
	 * ȡ���˻�����Ϣ[��ʷ]
	 * 
	 * @param QueryConditionInfo conditionInfo
	 * @param Connection conn
	 * @return Collection
	 * @throws Exception
	 **************************************************************************/
	public Collection getHisAccountBalance(QueryConditionInfo conditionInfo, Connection conn)
			throws Exception {
		ArrayList list = new ArrayList();
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();

		strSQL.append("select aa.accountId,aa.accountNo,aa.accountName,aa.accountBalance,bb.bankAccountId,bb.bankAccountNo,bb.bankAccountBalance \n");
		strSQL.append("from \n");
		strSQL.append("( \n");
		strSQL.append("	select a.Id accountId, a.saccountNo accountNo, a.sname accountName, sum(b.mbalance) accountBalance \n");
		strSQL.append("	from sett_account a,sett_dailyaccountBalance b \n");
		strSQL.append("	where a.Id=b.naccountId \n");
		strSQL.append("		and a.nstatusId="+SETTConstant.AccountStatus.NORMAL+" \n");
		strSQL.append("		and a.ncheckstatusId="+SETTConstant.AccountCheckStatus.CHECK+" \n");
		strSQL.append("		and a.ncurrencyId="+conditionInfo.getCurrencyId()+" \n");
		strSQL.append("		and a.nofficeId="+conditionInfo.getOfficeId()+" \n");
		strSQL.append("		and a.naccountTypeId in ( \n");
		strSQL.append("		select id from sett_accountType where naccountGroupId in ("+SETTConstant.AccountGroupType.CURRENT+")) \n");
		strSQL.append("		and b.naccountstatusId="+SETTConstant.AccountStatus.NORMAL+" \n");
		strSQL.append("		and b.nsubaccountstatusId="+SETTConstant.SubAccountStatus.NORMAL+" \n");
		strSQL.append("		and b.dtdate=to_date('"+DataFormat.getDateString(conditionInfo.getQueryDate())+"','yyyy-mm-dd') \n");
		strSQL.append("	group by a.Id, a.saccountNo, a.sname \n");
		strSQL.append(") aa, \n");
		strSQL.append("( \n");
		strSQL.append("		select a.n_Id bankAccountId, a.n_subjectId accountId, a.s_accountNo bankAccountNo, b.n_balance bankAccountBalance \n");
		strSQL.append("	from bs_bankaccountinfo a, \n");
		strSQL.append("		(select n_accountId,n_balance from bs_accthisbalance \n");
		strSQL.append("		where to_char(dt_importTime,'yyyy-mm-dd')='"+DataFormat.getDateString(conditionInfo.getQueryDate())+"') b \n");
		strSQL.append("	where b.n_accountId(+)=a.n_Id \n");
		strSQL.append("		and a.n_currencyType="+conditionInfo.getCurrencyId()+" \n");
		strSQL.append("		and a.n_accountstatus=1 \n");
		strSQL.append("		and a.n_rdstatus=1 \n");
		strSQL.append(") bb \n");
		strSQL.append("where 1=1 \n");
		strSQL.append("	and aa.accountId=bb.accountId \n");
		strSQL.append("order by aa.accountNo \n");
		log.info(strSQL.toString());
		try {
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				ContrastAccountBalanceBillInfo contrastAccountBalanceBillInfo = new ContrastAccountBalanceBillInfo();
				contrastAccountBalanceBillInfo.setAccountId(rs.getLong("accountId"));
				contrastAccountBalanceBillInfo.setAccountNo(rs.getString("accountNo"));
				contrastAccountBalanceBillInfo.setAccountName(rs.getString("accountName"));
				contrastAccountBalanceBillInfo.setAccountBalance(rs.getDouble("accountBalance"));
				contrastAccountBalanceBillInfo.setBankAccountNo(rs.getString("bankAccountNo"));
				contrastAccountBalanceBillInfo.setBankAccountBalance(rs.getDouble("bankAccountBalance"));
				list.add(contrastAccountBalanceBillInfo);
			}
		} catch (Exception e) {
			log.error("ȡ���˻�����Ϣ[��ʷ]����");
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			cleanup(rs);
			cleanup(ps);
		}
		return list;
	}

	/***************************************************************************
	 * ��ѯ�������˷�����[����]
	 * 
	 * @param QueryConditionInfo conditionInfo
	 * @param Connection conn
	 * @return double
	 * @throws Exception
	 */
	public double getCurAmountByBankAccountID(QueryConditionInfo conditionInfo, Connection conn) throws Exception {
		double amount = 0.0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("select sum(b.n_amount) amount \n");
		strSQL.append("from sett_transcurrentDeposit a,bs_acctcurtransinfo b \n");
		strSQL.append("where a.ntransactionTypeId in ("+SETTConstant.TransactionType.BANKPAY+","+SETTConstant.TransactionType.BANKRECEIVE+") \n");
		strSQL.append("	and to_char(b.dt_turnin,'yyyy-mm-dd')='"+DataFormat.getDateString(conditionInfo.getQueryDate())+"' \n");
		strSQL.append("	and a.nofficeId="+conditionInfo.getOfficeId()+" \n");
		strSQL.append("	and a.ncurrencyId="+conditionInfo.getCurrencyId()+" \n");
		strSQL.append("	and a.nstatusId="+SETTConstant.TransactionStatus.CHECK+" \n");
		strSQL.append("	and b.n_direction="+conditionInfo.getDirection()+" \n");
		strSQL.append("	and a.sinstructionNo=b.s_transnoOfBank \n");
		strSQL.append("	and to_char(a.dtInterestStart,'yyyy-mm-dd')=to_char(b.dt_turnin-1,'yyyy-mm-dd') \n");
		strSQL.append("	and b.n_accountId="+conditionInfo.getAccountId()+" \n");
		log.info(strSQL.toString());
		try {
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
			}
		} catch (Exception e) {
			log.error("ͨ���˻�ID��ѯ������");
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			cleanup(rs);
			cleanup(ps);
		}
		return amount;
	}

	/***************************************************************************
	 * ��ѯ�������˷�����[��ʷ]
	 * 
	 * @param QueryConditionInfo conditionInfo
	 * @param Connection conn
	 * @return double
	 * @throws Exception
	 */
	public double getHisAmountByBankAccountID(QueryConditionInfo conditionInfo, Connection conn) throws Exception {
		double amount = 0.0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("select sum(b.n_amount) amount \n");
		strSQL.append("from sett_transcurrentDeposit a,bs_accthistransinfo b \n");
		strSQL.append("where a.ntransactionTypeId in ("+SETTConstant.TransactionType.BANKPAY+","+SETTConstant.TransactionType.BANKRECEIVE+") \n");
		strSQL.append("	and to_char(b.dt_turnin,'yyyy-mm-dd')='"+DataFormat.getDateString(conditionInfo.getQueryDate())+"' \n");
		strSQL.append("	and a.nofficeId="+conditionInfo.getOfficeId()+" \n");
		strSQL.append("	and a.ncurrencyId="+conditionInfo.getCurrencyId()+" \n");
		strSQL.append("	and a.nstatusId="+SETTConstant.TransactionStatus.CHECK+" \n");
		strSQL.append("	and b.n_direction="+conditionInfo.getDirection()+" \n");
		strSQL.append("	and a.sinstructionNo=b.s_transnoOfBank \n");
		strSQL.append("	and to_char(a.dtInterestStart,'yyyy-mm-dd')=to_char(b.dt_turnin-1,'yyyy-mm-dd') \n");
		strSQL.append("	and b.n_accountId="+conditionInfo.getAccountId()+" \n");
		log.info(strSQL.toString());
		try {
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
			}
		} catch (Exception e) {
			log.error("ͨ���˻�ID��ѯ������");
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			cleanup(rs);
			cleanup(ps);
		}
		return amount;
	}

	public static void main(String arg[]) {
	}
}
