/**
 * 
 */
package com.iss.itreasury.settlement.setting.dataentity;

import java.sql.Date;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;

import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;

/**
 * @author zyzhu
 * 
 */
public class AccountDeadLineInfo extends SettlementBaseDataEntity {

	private long accountId = -1; // 活期账户id

	private double balanceLimited = 0.00; // 账户余额底限

	private double debitLimited = 0.00; // 账户借方累计发生额上限

	private double creditLimited = 0.00; // 贷方累计发生额上线

	private double transpayLimited = 0.00; // 单笔支付发生额上限

	private Timestamp validDate = null; // 生效日期

	private long statDays = 0; // 统计周期天

	private long statusId = Constant.RecordStatus.VALID; // 纪录的状态
	
	private long officeId = -1;//办事处

	// 获取表单数据
	public static AccountDeadLineInfo GetForm(HttpServletRequest request) {

		// 定义变量
		AccountDeadLineInfo form = new AccountDeadLineInfo();

		// 获取表单数据
		try {
			String strTmp = "";
			
			//记录ID
			strTmp = (String) request.getParameter("lID");
			if (strTmp != null && strTmp.trim().length() > 0) {
				form.setId(Long.parseLong(strTmp));
			}

			// 活期账户id
			strTmp = (String) request.getParameter("lAccountId");
			if (strTmp != null && strTmp.trim().length() > 0) {
				form.setAccountId(Long.parseLong(strTmp));
			}

			// 余额底线
			strTmp = (String) request.getParameter("dBalanceLimited");
			if (strTmp != null && strTmp.trim().length() > 0) {
				form.setBalanceLimited(DataFormat.parseNumber(strTmp));
			}

			// 借方累计发生额上线
			strTmp = (String) request.getParameter("dDebitLimited");
			if (strTmp != null && strTmp.trim().length() > 0) {
				form.setDebitLimited(DataFormat.parseNumber(strTmp));
			}

			// 贷方累计发生额上线
			strTmp = (String) request.getParameter("dCreditLimited");
			if (strTmp != null && strTmp.trim().length() > 0) {
				form.setCreditLimited(DataFormat.parseNumber(strTmp));
			}

			// 单笔支付发生额上线
			strTmp = (String) request.getParameter("dTranspayLimited");
			if (strTmp != null && strTmp.trim().length() > 0) {
				form.setTranspayLimited(DataFormat.parseNumber(strTmp));
			}

			// 生效日期
			strTmp = (String) request.getParameter("dtValidDate");
			if (strTmp != null && strTmp.trim().length() > 0) {
				form.setValidDate(DataFormat.getDateTime(strTmp));
			}

			// 统计周期天
			strTmp = (String) request.getParameter("lStatDays");
			if (strTmp != null && strTmp.trim().length() > 0) {
				form.setStatDays(Long.parseLong(strTmp));
			}

			// 记录状态
			strTmp = (String) request.getParameter("lStatusId");
			if (strTmp != null && strTmp.trim().length() > 0) {
				form.setStatusId(Long.parseLong(strTmp));
			}
			
			
			//办事处
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		// 返回函数值
		return form;
	}

	/**
	 * @return 返回 accountId。
	 */
	public long getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId
	 *            要设置的 accountId。
	 */
	public void setAccountId(long accountId) {
		putUsedField("accountId", accountId);
		this.accountId = accountId;
	}

	/**
	 * @return 返回 balanceLimited。
	 */
	public double getBalanceLimited() {
		return balanceLimited;
	}

	/**
	 * @param balanceLimited
	 *            要设置的 balanceLimited。
	 */
	public void setBalanceLimited(double balanceLimited) {
		putUsedField("balanceLimited", balanceLimited);
		this.balanceLimited = balanceLimited;
	}

	/**
	 * @return 返回 creditLimited。
	 */
	public double getCreditLimited() {
		return creditLimited;
	}

	/**
	 * @param creditLimited
	 *            要设置的 creditLimited。
	 */
	public void setCreditLimited(double creditLimited) {
		putUsedField("creditLimited", creditLimited);
		this.creditLimited = creditLimited;
	}

	/**
	 * @return 返回 debitLimited。
	 */
	public double getDebitLimited() {
		return debitLimited;
	}

	/**
	 * @param debitLimited
	 *            要设置的 debitLimited。
	 */
	public void setDebitLimited(double debitLimited) {
		putUsedField("debitLimited", debitLimited);
		this.debitLimited = debitLimited;
	}

	/**
	 * @return 返回 statDays。
	 */
	public long getStatDays() {
		return statDays;
	}

	/**
	 * @param statDays
	 *            要设置的 statDays。
	 */
	public void setStatDays(long statDays) {
		putUsedField("statDays", statDays);
		this.statDays = statDays;
	}

	/**
	 * @return 返回 statusId。
	 */
	public long getStatusId() {
		return statusId;
	}

	/**
	 * @param statusId
	 *            要设置的 statusId。
	 */
	public void setStatusId(long statusId) {
		putUsedField("statusId", statusId);
		this.statusId = statusId;
	}

	/**
	 * @return 返回 transpayLimited。
	 */
	public double getTranspayLimited() {
		return transpayLimited;
	}

	/**
	 * @param transpayLimited
	 *            要设置的 transpayLimited。
	 */
	public void setTranspayLimited(double transpayLimited) {
		putUsedField("transpayLimited", transpayLimited);
		this.transpayLimited = transpayLimited;
	}

	/**
	 * @return 返回 validDate。
	 */
	public Timestamp getValidDate() {
		return validDate;
	}

	/**
	 * @param validDate
	 *            要设置的 validDate。
	 */
	public void setValidDate(Timestamp validDate) {
		putUsedField("validDate", validDate);
		this.validDate = validDate;
	}

	public long getOfficeId() {
		return officeId;
	}

	public void setOfficeId(long officeId) {
		putUsedField("officeId", officeId);
		this.officeId = officeId;
	}

}
