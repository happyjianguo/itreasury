/**
 * 
 */
package com.iss.itreasury.settlement.setting.dataentity;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;
import com.iss.itreasury.util.DataFormat;

/**
 * 账户支付信息
 * 
 * @author zyzhu
 * 
 */
public class TransAccountGuardInfo extends SettlementBaseDataEntity {

	// 定义成员变量

	private long currencyId = -1; // 币种

	private long officeId = -1; // 办事处

	private long accountId = -1; // 活期账户ID

	private long payClientId = -1; // 付款方客户ID

	private long payAccountId = -1; // 付款方账户ID

	private long bankId = -1; // 开户行ID

	private long receiveClientId = -1; // 收款方客户ID

	private long receiveAccountId = -1; // 收款方账户ID

	private String extAccountNo = null; // 外部账户编号

	private String extClientName = null; // 外部客户名称

	private String remitBank = null; // 汇入行

	private String remitProvince = null; // 汇入省

	private String remitCity = null; // 汇入市

	private long transActionTypeId = -1; // 交易类型

	private double amount = 0.0; // 交易金额

	private Timestamp executeDate = null; // 执行日

	private String remark = null; // 文本备注

	private long inputUserId = -1; // 录入人

	private Timestamp inputDate = null; // 录入时间

	private long modifyUserId = -1; // 修改人

	private Timestamp modifyDate = null; // 修改时间

	private long statusId = -1; // 记录状态

	private long verifyStatusId = -1; // 校验是否通过

	private String verifyRemark = null; // 校验结果信息

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
	 * 交易金额
	 * 
	 * @return 返回 amount。
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * 交易金额
	 * 
	 * @param amount
	 *            要设置的 amount。
	 */
	public void setAmount(double amount) {
		putUsedField("amount", amount);
		this.amount = amount;
	}

	/**
	 * 开户行ID
	 * 
	 * @return 返回 bankId。
	 */
	public long getBankId() {
		return bankId;
	}

	/**
	 * 开户行ID
	 * 
	 * @param bankId
	 *            要设置的 bankId。
	 */
	public void setBankId(long bankId) {
		putUsedField("bankId", bankId);
		this.bankId = bankId;
	}

	/**
	 * 币种
	 * 
	 * @return 返回 currencyId。
	 */
	public long getCurrencyId() {
		return currencyId;
	}

	/**
	 * 币种
	 * 
	 * @param currencyId
	 *            要设置的 currencyId。
	 */
	public void setCurrencyId(long currencyId) {
		putUsedField("currencyId", currencyId);
		this.currencyId = currencyId;
	}

	/**
	 * 执行日
	 * 
	 * @return 返回 executeDate。
	 */
	public Timestamp getExecuteDate() {
		return executeDate;
	}

	/**
	 * 执行日
	 * 
	 * @param executeDate
	 *            要设置的 executeDate。
	 */
	public void setExecuteDate(Timestamp executeDate) {
		putUsedField("executeDate", executeDate);
		this.executeDate = executeDate;
	}

	/**
	 * 外部账户编号
	 * 
	 * @return 返回 extAccountNo。
	 */
	public String getExtAccountNo() {
		return extAccountNo;
	}

	/**
	 * 外部账户编号
	 * 
	 * @param extAccountNo
	 *            要设置的 extAccountNo。
	 */
	public void setExtAccountNo(String extAccountNo) {
		putUsedField("extAccountNo", extAccountNo);
		this.extAccountNo = extAccountNo;
	}

	/**
	 * 外部客户名称
	 * 
	 * @return 返回 extClientName。
	 */
	public String getExtClientName() {
		return extClientName;
	}

	/**
	 * 外部客户名称
	 * 
	 * @param extClientName
	 *            要设置的 extClientName。
	 */
	public void setExtClientName(String extClientName) {
		putUsedField("extClientName", extClientName);
		this.extClientName = extClientName;
	}

	/**
	 * 录入时间
	 * 
	 * @return 返回 inputDate。
	 */
	public Timestamp getInputDate() {
		return inputDate;
	}

	/**
	 * 录入时间
	 * 
	 * @param inputDate
	 *            要设置的 inputDate。
	 */
	public void setInputDate(Timestamp inputDate) {
		putUsedField("inputDate", inputDate);
		this.inputDate = inputDate;
	}

	/**
	 * 录入人
	 * 
	 * @return 返回 inputUserId。
	 */
	public long getInputUserId() {
		return inputUserId;
	}

	/**
	 * 录入人
	 * 
	 * @param inputUserId
	 *            要设置的 inputUserId。
	 */
	public void setInputUserId(long inputUserId) {
		putUsedField("inputUserId", inputUserId);
		this.inputUserId = inputUserId;
	}

	/**
	 * 修改时间
	 * 
	 * @return 返回 modifyDate。
	 */
	public Timestamp getModifyDate() {
		return modifyDate;
	}

	/**
	 * 修改时间
	 * 
	 * @param modifyDate
	 *            要设置的 modifyDate。
	 */
	public void setModifyDate(Timestamp modifyDate) {
		putUsedField("modifyDate", modifyDate);
		this.modifyDate = modifyDate;
	}

	/**
	 * 修改人
	 * 
	 * @return 返回 modifyUserId。
	 */
	public long getModifyUserId() {
		return modifyUserId;
	}

	/**
	 * 修改人
	 * 
	 * @param modifyUserId
	 *            要设置的 modifyUserId。
	 */
	public void setModifyUserId(long modifyUserId) {
		putUsedField("modifyUserId", modifyUserId);
		this.modifyUserId = modifyUserId;
	}

	/**
	 * 办事处
	 * 
	 * @return 返回 officeId。
	 */
	public long getOfficeId() {
		return officeId;
	}

	/**
	 * 办事处
	 * 
	 * @param officeId
	 *            要设置的 officeId。
	 */
	public void setOfficeId(long officeId) {
		putUsedField("officeId", officeId);
		this.officeId = officeId;
	}

	/**
	 * 付款方账户ID
	 * 
	 * @return 返回 payAccountId。
	 */
	public long getPayAccountId() {
		return payAccountId;
	}

	/**
	 * 付款方账户ID
	 * 
	 * @param payAccountId
	 *            要设置的 payAccountId。
	 */
	public void setPayAccountId(long payAccountId) {
		putUsedField("payAccountId", payAccountId);
		this.payAccountId = payAccountId;
	}

	/**
	 * 付款方客户ID
	 * 
	 * @return 返回 payClientId。
	 */
	public long getPayClientId() {
		return payClientId;
	}

	/**
	 * 付款方客户ID
	 * 
	 * @param payClientId
	 *            要设置的 payClientId。
	 */
	public void setPayClientId(long payClientId) {
		putUsedField("payClientId", payClientId);
		this.payClientId = payClientId;
	}

	/**
	 * 收款方账户ID
	 * 
	 * @return 返回 receiveAccountId。
	 */
	public long getReceiveAccountId() {
		return receiveAccountId;
	}

	/**
	 * 收款方账户ID
	 * 
	 * @param receiveAccountId
	 *            要设置的 receiveAccountId。
	 */
	public void setReceiveAccountId(long receiveAccountId) {
		putUsedField("receiveAccountId", receiveAccountId);
		this.receiveAccountId = receiveAccountId;
	}

	/**
	 * 收款方客户ID
	 * 
	 * @return 返回 receiveClientId。
	 */
	public long getReceiveClientId() {
		return receiveClientId;
	}

	/**
	 * 收款方客户ID
	 * 
	 * @param receiveClientId
	 *            要设置的 receiveClientId。
	 */
	public void setReceiveClientId(long receiveClientId) {
		putUsedField("receiveClientId", receiveClientId);
		this.receiveClientId = receiveClientId;
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
	 * 汇入行
	 * 
	 * @return 返回 remitBank。
	 */
	public String getRemitBank() {
		return remitBank;
	}

	/**
	 * 汇入行
	 * 
	 * @param remitBank
	 *            要设置的 remitBank。
	 */
	public void setRemitBank(String remitBank) {
		putUsedField("remitBank", remitBank);
		this.remitBank = remitBank;
	}

	/**
	 * 汇入市
	 * 
	 * @return 返回 remitCity。
	 */
	public String getRemitCity() {
		return remitCity;
	}

	/**
	 * 汇入市
	 * 
	 * @param remitCity
	 *            要设置的 remitCity。
	 */
	public void setRemitCity(String remitCity) {
		putUsedField("remitCity", remitCity);
		this.remitCity = remitCity;
	}

	/**
	 * 汇入省
	 * 
	 * @return 返回 remitProvince。
	 */
	public String getRemitProvince() {
		return remitProvince;
	}

	/**
	 * 汇入省
	 * 
	 * @param remitProvince
	 *            要设置的 remitProvince。
	 */
	public void setRemitProvince(String remitProvince) {
		putUsedField("remitProvince", remitProvince);
		this.remitProvince = remitProvince;
	}

	/**
	 * 记录状态
	 * 
	 * @return 返回 statusid。
	 */
	public long getStatusId() {
		return statusId;
	}

	/**
	 * 记录状态
	 * 
	 * @param statusid
	 *            要设置的 statusid。
	 */
	public void setStatusId(long statusId) {
		putUsedField("statusid", statusId);
		this.statusId = statusId;
	}

	/**
	 * 交易类型
	 * 
	 * @return 返回 transActionTypeId。
	 */
	public long getTransActionTypeId() {
		return transActionTypeId;
	}

	/**
	 * 交易类型
	 * 
	 * @param transActionTypeId
	 *            要设置的 transActionTypeId。
	 */
	public void setTransActionTypeId(long transActionTypeId) {
		putUsedField("transActionTypeId", transActionTypeId);
		this.transActionTypeId = transActionTypeId;
	}

	/**
	 * 校验结果信息
	 * 
	 * @return 返回 verifyRemark。
	 */
	public String getVerifyRemark() {
		return verifyRemark;
	}

	/**
	 * 校验结果信息
	 * 
	 * @param verifyRemark
	 *            要设置的 verifyRemark。
	 */
	public void setVerifyRemark(String verifyRemark) {
		putUsedField("verifyRemark", verifyRemark);
		this.verifyRemark = verifyRemark;
	}

	/**
	 * 校验是否通过
	 * 
	 * @return 返回 verifyStatusId。
	 */
	public long getVerifyStatusId() {
		return verifyStatusId;
	}

	/**
	 * 校验是否通过
	 * 
	 * @param verifyStatusId
	 *            要设置的 verifyStatusId。
	 */
	public void setVerifyStatusId(long verifyStatusId) {
		putUsedField("verifyStatusId", verifyStatusId);
		this.verifyStatusId = verifyStatusId;
	}

	// 从Request读取数据转换为TransAccountGuardInfo实例
	public static TransAccountGuardInfo getForm(HttpServletRequest request) {

		// 定义变量
		TransAccountGuardInfo form = new TransAccountGuardInfo();

		String strTmp = "";

		// 记录ID
		strTmp = (String) request.getAttribute("lID");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setId(Long.parseLong(strTmp));
		}

		// 币种
		strTmp = (String) request.getAttribute("lCurrencyId");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setCurrencyId(Long.parseLong(strTmp));
		}

		// 办事处
		strTmp = (String) request.getAttribute("lOfficeId");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setOfficeId(Long.parseLong(strTmp));
		}

		// 付款方账户ID
		strTmp = (String) request.getAttribute("lPayAccountId");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setAccountId(Long.parseLong(strTmp));
		}

		// 付款方客户ID
		strTmp = (String) request.getAttribute("lPayClientId");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setPayClientId(Long.parseLong(strTmp));
		}

		// 付款方账户ID
		strTmp = (String) request.getAttribute("lPayAccountId");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setPayAccountId(Long.parseLong(strTmp));
		}

		// 开户行ID
		strTmp = (String) request.getAttribute("lBankId");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setBankId(Long.parseLong(strTmp));
		}

		// 收款方客户ID
		strTmp = (String) request.getAttribute("lReceiveClientId");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setReceiveClientId(Long.parseLong(strTmp));
		}

		// 收款方账户ID
		strTmp = (String) request.getAttribute("lReceiveAccountId");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setReceiveAccountId(Long.parseLong(strTmp));
		}

		// 外部账户编号
		strTmp = (String) request.getAttribute("lReceiveAccountIdCtrl");// strExtAccountNo
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setExtAccountNo(strTmp);
		}

		// 汇入行名称
		strTmp = (String) request.getAttribute("strRemitBank");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setRemitBank(strTmp);
		}

		// 外部客户名称
		strTmp = (String) request.getAttribute("strExtClientName");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setExtClientName(strTmp);
		}

		// 汇入省
		strTmp = (String) request.getAttribute("strRemitProvince");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setRemitProvince(strTmp);
		}

		// 汇入市
		strTmp = (String) request.getAttribute("strRemitCity");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setRemitCity(strTmp);
		}

		// 交易类型
		strTmp = (String) request.getAttribute("lTransActionTypeId");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setTransActionTypeId(Long.parseLong(strTmp));
		}

		// 交易金额
		strTmp = (String) request.getAttribute("dbAmount");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setAmount(DataFormat.parseNumber(strTmp));
		}

		// 执行日
		strTmp = (String) request.getAttribute("tmExecuteDate");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setExecuteDate(DataFormat.getDateTime(strTmp));
		}

		// 文本备注
		strTmp = (String) request.getAttribute("strRemark");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setRemark(strTmp);
		}

		// 录入人
		strTmp = (String) request.getAttribute("lInputUserId");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setInputUserId(Long.parseLong(strTmp));
		}

		// 录入时间
		strTmp = (String) request.getAttribute("tmInputDate");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setInputDate(DataFormat.getDateTime(strTmp));
		}

		// 修改人
		strTmp = (String) request.getAttribute("lModifyUserId");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setModifyUserId(Long.parseLong(strTmp));
		}

		// 修改时间
		strTmp = (String) request.getAttribute("tmModifyDate");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setModifyDate(DataFormat.getDateTime(strTmp));
		}

		// 记录状态
		strTmp = (String) request.getAttribute("lStatusId");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setStatusId(Long.parseLong(strTmp));
		}

		// 校验是否通过
		strTmp = (String) request.getAttribute("lVerifyStatusId");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setVerifyStatusId(Long.parseLong(strTmp));
		}

		// 校验结果信息
		strTmp = (String) request.getAttribute("strVerifyRemark");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setVerifyRemark(strTmp);
		}

		return form;

	}

}
