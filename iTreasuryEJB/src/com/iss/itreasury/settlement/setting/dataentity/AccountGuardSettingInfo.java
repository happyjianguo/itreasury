/**
 * 
 */
package com.iss.itreasury.settlement.setting.dataentity;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;
import com.iss.itreasury.util.DataFormat;

/**
 * @author zyzhu
 * 
 */
public class AccountGuardSettingInfo extends SettlementBaseDataEntity {

	// 定义成员变量
	private long currencyId = -1; // 币种ID

	private long officeId = -1; // 办事处ID

	private long accountId = -1; // 活期账户ID

	private double sumLimited = 0.00; // 账户累计付款额上限

	private double transPayLimited = 0.00; // 单笔支付发生上限

	private Timestamp validDate = null; // 生效日期

	private long statDays = -1; // 统计周期（天）

	private String remark = null; // 文本备注

	private long isNeedGuard = -1; // 是否需要监管

	private long inputUserId = -1; // 录入人

	private Timestamp inputDate = null; // 录入时间

	private long modifyUserId = -1; // 修改人

	private Timestamp modifyDate = null; // 修改时间

	private long statusId = -1; // 纪录的状态

	/**
	 * 活期账户ID
	 * 
	 * @return 返回 accountId。
	 */
	public long getAccountId() {
		return accountId;
	}

	/**
	 * 活期账户ID
	 * 
	 * @param accountId
	 *            要设置的 accountId。
	 */
	public void setAccountId(long accountId) {
		putUsedField("accountId", accountId);
		this.accountId = accountId;
	}

	/**
	 * 是否需要监管
	 * 
	 * @return 返回 isNeedGuard。
	 */
	public long getIsNeedGuard() {
		return isNeedGuard;
	}

	/**
	 * 是否需要监管
	 * 
	 * @param isNeedGuard
	 *            要设置的 isNeedGuard。
	 */
	public void setIsNeedGuard(long isNeedGuard) {
		putUsedField("isNeedGuard", isNeedGuard);
		this.isNeedGuard = isNeedGuard;
	}

	/**
	 * 文本备注
	 * 
	 * @return 返回 remark。
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 文本备注
	 * 
	 * @param remark
	 *            要设置的 remark。
	 */
	public void setRemark(String remark) {
		putUsedField("remark", remark);
		this.remark = remark;
	}

	/**
	 * 统计周期（天）
	 * 
	 * @return 返回 statDays。
	 */
	public long getStatDays() {
		return statDays;
	}

	/**
	 * 统计周期（天）
	 * 
	 * @param statDays
	 *            要设置的 statDays。
	 */
	public void setStatDays(long statDays) {
		putUsedField("statDays", statDays);
		this.statDays = statDays;
	}

	/**
	 * 纪录的状态
	 * 
	 * @return 返回 statusID。
	 */
	public long getStatusId() {
		return statusId;
	}

	/**
	 * 纪录的状态
	 * 
	 * @param statusID
	 *            要设置的 statusID。
	 */
	public void setStatusId(long statusID) {
		putUsedField("statusID", statusID);
		this.statusId = statusID;
	}

	/**
	 * 账户累计付款额上限
	 * 
	 * @return 返回 sumLimited。
	 */
	public double getSumLimited() {
		return sumLimited;
	}

	/**
	 * 账户累计付款额上限
	 * 
	 * @param sumLimited
	 *            要设置的 sumLimited。
	 */
	public void setSumLimited(double sumLimited) {
		putUsedField("sumLimited", sumLimited);
		this.sumLimited = sumLimited;
	}

	/**
	 * 单笔支付发生上限
	 * 
	 * @return 返回 transPayLimited。
	 */
	public double getTransPayLimited() {
		return transPayLimited;
	}

	/**
	 * 单笔支付发生上限
	 * 
	 * @param transPayLimited
	 *            要设置的 transPayLimited。
	 */
	public void setTransPayLimited(double transPayLimited) {
		putUsedField("transPayLimited", transPayLimited);
		this.transPayLimited = transPayLimited;
	}

	/**
	 * 生效日期
	 * 
	 * @return 返回 validDate。
	 */
	public Timestamp getValidDate() {
		return validDate;
	}

	/**
	 * 生效日期
	 * 
	 * @param validDate
	 *            要设置的 validDate。
	 */
	public void setValidDate(Timestamp validDate) {
		putUsedField("validDate", validDate);
		this.validDate = validDate;
	}

	/**
	 * 输入日期
	 * 
	 * @return 返回 inputDate。
	 */
	public Timestamp getInputDate() {
		return inputDate;
	}

	/**
	 * 输入日期
	 * 
	 * @param inputDate
	 *            要设置的 inputDate。
	 */
	public void setInputDate(Timestamp inputDate) {
		putUsedField("inputDate", inputDate);
		this.inputDate = inputDate;
	}

	/**
	 * 输入人ID
	 * 
	 * @return 返回 inputUserId。
	 */
	public long getInputUserId() {
		return inputUserId;
	}

	/**
	 * 输入人ID
	 * 
	 * @param inputUserId
	 *            要设置的 inputUserId。
	 */
	public void setInputUserId(long inputUserId) {
		putUsedField("inputUserId", inputUserId);
		this.inputUserId = inputUserId;
	}

	/**
	 * 修改日期
	 * 
	 * @return 返回 modifyDate。
	 */
	public Timestamp getModifyDate() {
		return modifyDate;
	}

	/**
	 * 修改日期
	 * 
	 * @param modifyDate
	 *            要设置的 modifyDate。
	 */
	public void setModifyDate(Timestamp modifyDate) {
		putUsedField("modifyDate", modifyDate);
		this.modifyDate = modifyDate;
	}

	/**
	 * 修改人ID
	 * 
	 * @return 返回 modifyUserId。
	 */
	public long getModifyUserId() {
		return modifyUserId;
	}

	/**
	 * 修改人ID
	 * 
	 * @param modifyUserId
	 *            要设置的 modifyUserId。
	 */
	public void setModifyUserId(long modifyUserId) {
		putUsedField("modifyUserId", modifyUserId);
		this.modifyUserId = modifyUserId;
	}

	/**
	 * 当前币种
	 * 
	 * @return 返回 currencyId。
	 */
	public long getCurrencyId() {
		return currencyId;
	}

	/**
	 * 当前币种
	 * 
	 * @param currencyId
	 *            要设置的 currencyId。
	 */
	public void setCurrencyId(long currencyId) {
		putUsedField("currencyId", currencyId);
		this.currencyId = currencyId;
	}

	/**
	 * 办事处ID
	 * 
	 * @return 返回 officeId。
	 */
	public long getOfficeId() {
		return officeId;
	}

	/**
	 * 办事处ID
	 * 
	 * @param officeId
	 *            要设置的 officeId。
	 */
	public void setOfficeId(long officeId) {
		putUsedField("officeId", officeId);
		this.officeId = officeId;
	}

	// 获取表单数据
	public static AccountGuardSettingInfo GetForm(HttpServletRequest request)
			throws Exception {

		// 定义变量
		AccountGuardSettingInfo form = new AccountGuardSettingInfo();

		// 获取表单数据
		try {
			String strTmp = "";

			// 记录ID
			strTmp = (String) request.getParameter("lID");
			if (strTmp != null && strTmp.trim().length() > 0) {
				form.setId(Long.parseLong(strTmp));
			}

			// 当前币种
			strTmp = (String) request.getParameter("lCurrencyId");
			if (strTmp != null && strTmp.trim().length() > 0) {
				form.setCurrencyId(Long.parseLong(strTmp));
			}

			// 办事处
			strTmp = (String) request.getParameter("lOfficeId");
			if (strTmp != null && strTmp.trim().length() > 0) {
				form.setOfficeId(Long.parseLong(strTmp));
			}

			// 活期账户id
			strTmp = (String) request.getParameter("lAccountID");
			if (strTmp != null && strTmp.trim().length() > 0) {
				form.setAccountId(Long.parseLong(strTmp));
			}

			// 生效日期
			strTmp = (String) request.getParameter("strValidDate");
			if (strTmp != null && strTmp.trim().length() > 0) {
				form.setValidDate(DataFormat.getDateTime(strTmp));
			}

			// 统计周期（天）
			strTmp = (String) request.getParameter("lStatDays");
			if (strTmp != null && strTmp.trim().length() > 0) {
				form.setStatDays(Long.parseLong(strTmp));
			}

			// 是否需要监管
			strTmp = (String) request.getParameter("lIsNeedGuard");
			if (strTmp != null && strTmp.trim().length() > 0) {
				form.setIsNeedGuard(Long.parseLong(strTmp));
			}

			// 文本备注
			strTmp = (String) request.getParameter("strRemark");
			if (strTmp != null && strTmp.trim().length() > 0) {
				form.setRemark(strTmp);
			}

			// 单笔支付发生上限
			strTmp = (String) request.getParameter("dTransPayLimited");
			if (strTmp != null && strTmp.trim().length() > 0) {
				form.setTransPayLimited(DataFormat.parseNumber(strTmp));
			}

			// 账户累计付款额上限
			strTmp = (String) request.getParameter("dSumLimited");
			if (strTmp != null && strTmp.trim().length() > 0) {
				form.setSumLimited(DataFormat.parseNumber(strTmp));
			}

			// 录入人ID
			strTmp = (String) request.getParameter("lInputUserId");
			if (strTmp != null && strTmp.trim().length() > 0) {
				form.setInputUserId(Long.parseLong(strTmp));
			}

			// 录入日期
			strTmp = (String) request.getParameter("strInputDate");
			if (strTmp != null && strTmp.trim().length() > 0) {
				form.setInputDate(DataFormat.getDateTime(strTmp));
			}

			// 修改人ID
			strTmp = (String) request.getParameter("lModifyUserId");
			if (strTmp != null && strTmp.trim().length() > 0) {
				form.setModifyUserId(Long.parseLong(strTmp));
			}

			// 修改日期
			strTmp = (String) request.getParameter("strModifyDate");
			if (strTmp != null && strTmp.trim().length() > 0) {
				form.setModifyDate(DataFormat.getDateTime(strTmp));
			}

			// 记录状态
			strTmp = (String) request.getParameter("lStatusId");
			if (strTmp != null && strTmp.trim().length() > 0) {
				form.setStatusId(Long.parseLong(strTmp));
			}

		} catch (Exception e) {
			// e.printStackTrace();
			throw e;
		}

		// 返回函数值
		return form;

	}

}
