/**
 * 
 */
package com.iss.itreasury.settlement.setting.dataentity;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;
import com.iss.itreasury.util.DataFormat;

/**
 * �˻�֧����Ϣ
 * 
 * @author zyzhu
 * 
 */
public class TransAccountGuardInfo extends SettlementBaseDataEntity {

	// �����Ա����

	private long currencyId = -1; // ����

	private long officeId = -1; // ���´�

	private long accountId = -1; // �����˻�ID

	private long payClientId = -1; // ����ͻ�ID

	private long payAccountId = -1; // ����˻�ID

	private long bankId = -1; // ������ID

	private long receiveClientId = -1; // �տ�ͻ�ID

	private long receiveAccountId = -1; // �տ�˻�ID

	private String extAccountNo = null; // �ⲿ�˻����

	private String extClientName = null; // �ⲿ�ͻ�����

	private String remitBank = null; // ������

	private String remitProvince = null; // ����ʡ

	private String remitCity = null; // ������

	private long transActionTypeId = -1; // ��������

	private double amount = 0.0; // ���׽��

	private Timestamp executeDate = null; // ִ����

	private String remark = null; // �ı���ע

	private long inputUserId = -1; // ¼����

	private Timestamp inputDate = null; // ¼��ʱ��

	private long modifyUserId = -1; // �޸���

	private Timestamp modifyDate = null; // �޸�ʱ��

	private long statusId = -1; // ��¼״̬

	private long verifyStatusId = -1; // У���Ƿ�ͨ��

	private String verifyRemark = null; // У������Ϣ

	/**
	 * �����˻�ID
	 * 
	 * @return ���� accountId��
	 */
	public long getAccountId() {
		return accountId;
	}

	/**
	 * �����˻�ID
	 * 
	 * @param accountId
	 *            Ҫ���õ� accountId��
	 */
	public void setAccountId(long accountId) {
		putUsedField("accountId", accountId);
		this.accountId = accountId;
	}

	/**
	 * ���׽��
	 * 
	 * @return ���� amount��
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * ���׽��
	 * 
	 * @param amount
	 *            Ҫ���õ� amount��
	 */
	public void setAmount(double amount) {
		putUsedField("amount", amount);
		this.amount = amount;
	}

	/**
	 * ������ID
	 * 
	 * @return ���� bankId��
	 */
	public long getBankId() {
		return bankId;
	}

	/**
	 * ������ID
	 * 
	 * @param bankId
	 *            Ҫ���õ� bankId��
	 */
	public void setBankId(long bankId) {
		putUsedField("bankId", bankId);
		this.bankId = bankId;
	}

	/**
	 * ����
	 * 
	 * @return ���� currencyId��
	 */
	public long getCurrencyId() {
		return currencyId;
	}

	/**
	 * ����
	 * 
	 * @param currencyId
	 *            Ҫ���õ� currencyId��
	 */
	public void setCurrencyId(long currencyId) {
		putUsedField("currencyId", currencyId);
		this.currencyId = currencyId;
	}

	/**
	 * ִ����
	 * 
	 * @return ���� executeDate��
	 */
	public Timestamp getExecuteDate() {
		return executeDate;
	}

	/**
	 * ִ����
	 * 
	 * @param executeDate
	 *            Ҫ���õ� executeDate��
	 */
	public void setExecuteDate(Timestamp executeDate) {
		putUsedField("executeDate", executeDate);
		this.executeDate = executeDate;
	}

	/**
	 * �ⲿ�˻����
	 * 
	 * @return ���� extAccountNo��
	 */
	public String getExtAccountNo() {
		return extAccountNo;
	}

	/**
	 * �ⲿ�˻����
	 * 
	 * @param extAccountNo
	 *            Ҫ���õ� extAccountNo��
	 */
	public void setExtAccountNo(String extAccountNo) {
		putUsedField("extAccountNo", extAccountNo);
		this.extAccountNo = extAccountNo;
	}

	/**
	 * �ⲿ�ͻ�����
	 * 
	 * @return ���� extClientName��
	 */
	public String getExtClientName() {
		return extClientName;
	}

	/**
	 * �ⲿ�ͻ�����
	 * 
	 * @param extClientName
	 *            Ҫ���õ� extClientName��
	 */
	public void setExtClientName(String extClientName) {
		putUsedField("extClientName", extClientName);
		this.extClientName = extClientName;
	}

	/**
	 * ¼��ʱ��
	 * 
	 * @return ���� inputDate��
	 */
	public Timestamp getInputDate() {
		return inputDate;
	}

	/**
	 * ¼��ʱ��
	 * 
	 * @param inputDate
	 *            Ҫ���õ� inputDate��
	 */
	public void setInputDate(Timestamp inputDate) {
		putUsedField("inputDate", inputDate);
		this.inputDate = inputDate;
	}

	/**
	 * ¼����
	 * 
	 * @return ���� inputUserId��
	 */
	public long getInputUserId() {
		return inputUserId;
	}

	/**
	 * ¼����
	 * 
	 * @param inputUserId
	 *            Ҫ���õ� inputUserId��
	 */
	public void setInputUserId(long inputUserId) {
		putUsedField("inputUserId", inputUserId);
		this.inputUserId = inputUserId;
	}

	/**
	 * �޸�ʱ��
	 * 
	 * @return ���� modifyDate��
	 */
	public Timestamp getModifyDate() {
		return modifyDate;
	}

	/**
	 * �޸�ʱ��
	 * 
	 * @param modifyDate
	 *            Ҫ���õ� modifyDate��
	 */
	public void setModifyDate(Timestamp modifyDate) {
		putUsedField("modifyDate", modifyDate);
		this.modifyDate = modifyDate;
	}

	/**
	 * �޸���
	 * 
	 * @return ���� modifyUserId��
	 */
	public long getModifyUserId() {
		return modifyUserId;
	}

	/**
	 * �޸���
	 * 
	 * @param modifyUserId
	 *            Ҫ���õ� modifyUserId��
	 */
	public void setModifyUserId(long modifyUserId) {
		putUsedField("modifyUserId", modifyUserId);
		this.modifyUserId = modifyUserId;
	}

	/**
	 * ���´�
	 * 
	 * @return ���� officeId��
	 */
	public long getOfficeId() {
		return officeId;
	}

	/**
	 * ���´�
	 * 
	 * @param officeId
	 *            Ҫ���õ� officeId��
	 */
	public void setOfficeId(long officeId) {
		putUsedField("officeId", officeId);
		this.officeId = officeId;
	}

	/**
	 * ����˻�ID
	 * 
	 * @return ���� payAccountId��
	 */
	public long getPayAccountId() {
		return payAccountId;
	}

	/**
	 * ����˻�ID
	 * 
	 * @param payAccountId
	 *            Ҫ���õ� payAccountId��
	 */
	public void setPayAccountId(long payAccountId) {
		putUsedField("payAccountId", payAccountId);
		this.payAccountId = payAccountId;
	}

	/**
	 * ����ͻ�ID
	 * 
	 * @return ���� payClientId��
	 */
	public long getPayClientId() {
		return payClientId;
	}

	/**
	 * ����ͻ�ID
	 * 
	 * @param payClientId
	 *            Ҫ���õ� payClientId��
	 */
	public void setPayClientId(long payClientId) {
		putUsedField("payClientId", payClientId);
		this.payClientId = payClientId;
	}

	/**
	 * �տ�˻�ID
	 * 
	 * @return ���� receiveAccountId��
	 */
	public long getReceiveAccountId() {
		return receiveAccountId;
	}

	/**
	 * �տ�˻�ID
	 * 
	 * @param receiveAccountId
	 *            Ҫ���õ� receiveAccountId��
	 */
	public void setReceiveAccountId(long receiveAccountId) {
		putUsedField("receiveAccountId", receiveAccountId);
		this.receiveAccountId = receiveAccountId;
	}

	/**
	 * �տ�ͻ�ID
	 * 
	 * @return ���� receiveClientId��
	 */
	public long getReceiveClientId() {
		return receiveClientId;
	}

	/**
	 * �տ�ͻ�ID
	 * 
	 * @param receiveClientId
	 *            Ҫ���õ� receiveClientId��
	 */
	public void setReceiveClientId(long receiveClientId) {
		putUsedField("receiveClientId", receiveClientId);
		this.receiveClientId = receiveClientId;
	}

	/**
	 * �ı���ע
	 * 
	 * @return ���� remark��
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * �ı���ע
	 * 
	 * @param remark
	 *            Ҫ���õ� remark��
	 */
	public void setRemark(String remark) {
		putUsedField("remark", remark);
		this.remark = remark;
	}

	/**
	 * ������
	 * 
	 * @return ���� remitBank��
	 */
	public String getRemitBank() {
		return remitBank;
	}

	/**
	 * ������
	 * 
	 * @param remitBank
	 *            Ҫ���õ� remitBank��
	 */
	public void setRemitBank(String remitBank) {
		putUsedField("remitBank", remitBank);
		this.remitBank = remitBank;
	}

	/**
	 * ������
	 * 
	 * @return ���� remitCity��
	 */
	public String getRemitCity() {
		return remitCity;
	}

	/**
	 * ������
	 * 
	 * @param remitCity
	 *            Ҫ���õ� remitCity��
	 */
	public void setRemitCity(String remitCity) {
		putUsedField("remitCity", remitCity);
		this.remitCity = remitCity;
	}

	/**
	 * ����ʡ
	 * 
	 * @return ���� remitProvince��
	 */
	public String getRemitProvince() {
		return remitProvince;
	}

	/**
	 * ����ʡ
	 * 
	 * @param remitProvince
	 *            Ҫ���õ� remitProvince��
	 */
	public void setRemitProvince(String remitProvince) {
		putUsedField("remitProvince", remitProvince);
		this.remitProvince = remitProvince;
	}

	/**
	 * ��¼״̬
	 * 
	 * @return ���� statusid��
	 */
	public long getStatusId() {
		return statusId;
	}

	/**
	 * ��¼״̬
	 * 
	 * @param statusid
	 *            Ҫ���õ� statusid��
	 */
	public void setStatusId(long statusId) {
		putUsedField("statusid", statusId);
		this.statusId = statusId;
	}

	/**
	 * ��������
	 * 
	 * @return ���� transActionTypeId��
	 */
	public long getTransActionTypeId() {
		return transActionTypeId;
	}

	/**
	 * ��������
	 * 
	 * @param transActionTypeId
	 *            Ҫ���õ� transActionTypeId��
	 */
	public void setTransActionTypeId(long transActionTypeId) {
		putUsedField("transActionTypeId", transActionTypeId);
		this.transActionTypeId = transActionTypeId;
	}

	/**
	 * У������Ϣ
	 * 
	 * @return ���� verifyRemark��
	 */
	public String getVerifyRemark() {
		return verifyRemark;
	}

	/**
	 * У������Ϣ
	 * 
	 * @param verifyRemark
	 *            Ҫ���õ� verifyRemark��
	 */
	public void setVerifyRemark(String verifyRemark) {
		putUsedField("verifyRemark", verifyRemark);
		this.verifyRemark = verifyRemark;
	}

	/**
	 * У���Ƿ�ͨ��
	 * 
	 * @return ���� verifyStatusId��
	 */
	public long getVerifyStatusId() {
		return verifyStatusId;
	}

	/**
	 * У���Ƿ�ͨ��
	 * 
	 * @param verifyStatusId
	 *            Ҫ���õ� verifyStatusId��
	 */
	public void setVerifyStatusId(long verifyStatusId) {
		putUsedField("verifyStatusId", verifyStatusId);
		this.verifyStatusId = verifyStatusId;
	}

	// ��Request��ȡ����ת��ΪTransAccountGuardInfoʵ��
	public static TransAccountGuardInfo getForm(HttpServletRequest request) {

		// �������
		TransAccountGuardInfo form = new TransAccountGuardInfo();

		String strTmp = "";

		// ��¼ID
		strTmp = (String) request.getAttribute("lID");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setId(Long.parseLong(strTmp));
		}

		// ����
		strTmp = (String) request.getAttribute("lCurrencyId");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setCurrencyId(Long.parseLong(strTmp));
		}

		// ���´�
		strTmp = (String) request.getAttribute("lOfficeId");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setOfficeId(Long.parseLong(strTmp));
		}

		// ����˻�ID
		strTmp = (String) request.getAttribute("lPayAccountId");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setAccountId(Long.parseLong(strTmp));
		}

		// ����ͻ�ID
		strTmp = (String) request.getAttribute("lPayClientId");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setPayClientId(Long.parseLong(strTmp));
		}

		// ����˻�ID
		strTmp = (String) request.getAttribute("lPayAccountId");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setPayAccountId(Long.parseLong(strTmp));
		}

		// ������ID
		strTmp = (String) request.getAttribute("lBankId");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setBankId(Long.parseLong(strTmp));
		}

		// �տ�ͻ�ID
		strTmp = (String) request.getAttribute("lReceiveClientId");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setReceiveClientId(Long.parseLong(strTmp));
		}

		// �տ�˻�ID
		strTmp = (String) request.getAttribute("lReceiveAccountId");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setReceiveAccountId(Long.parseLong(strTmp));
		}

		// �ⲿ�˻����
		strTmp = (String) request.getAttribute("lReceiveAccountIdCtrl");// strExtAccountNo
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setExtAccountNo(strTmp);
		}

		// ����������
		strTmp = (String) request.getAttribute("strRemitBank");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setRemitBank(strTmp);
		}

		// �ⲿ�ͻ�����
		strTmp = (String) request.getAttribute("strExtClientName");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setExtClientName(strTmp);
		}

		// ����ʡ
		strTmp = (String) request.getAttribute("strRemitProvince");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setRemitProvince(strTmp);
		}

		// ������
		strTmp = (String) request.getAttribute("strRemitCity");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setRemitCity(strTmp);
		}

		// ��������
		strTmp = (String) request.getAttribute("lTransActionTypeId");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setTransActionTypeId(Long.parseLong(strTmp));
		}

		// ���׽��
		strTmp = (String) request.getAttribute("dbAmount");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setAmount(DataFormat.parseNumber(strTmp));
		}

		// ִ����
		strTmp = (String) request.getAttribute("tmExecuteDate");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setExecuteDate(DataFormat.getDateTime(strTmp));
		}

		// �ı���ע
		strTmp = (String) request.getAttribute("strRemark");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setRemark(strTmp);
		}

		// ¼����
		strTmp = (String) request.getAttribute("lInputUserId");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setInputUserId(Long.parseLong(strTmp));
		}

		// ¼��ʱ��
		strTmp = (String) request.getAttribute("tmInputDate");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setInputDate(DataFormat.getDateTime(strTmp));
		}

		// �޸���
		strTmp = (String) request.getAttribute("lModifyUserId");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setModifyUserId(Long.parseLong(strTmp));
		}

		// �޸�ʱ��
		strTmp = (String) request.getAttribute("tmModifyDate");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setModifyDate(DataFormat.getDateTime(strTmp));
		}

		// ��¼״̬
		strTmp = (String) request.getAttribute("lStatusId");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setStatusId(Long.parseLong(strTmp));
		}

		// У���Ƿ�ͨ��
		strTmp = (String) request.getAttribute("lVerifyStatusId");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setVerifyStatusId(Long.parseLong(strTmp));
		}

		// У������Ϣ
		strTmp = (String) request.getAttribute("strVerifyRemark");
		if (strTmp != null && strTmp.trim().length() > 0) {
			form.setVerifyRemark(strTmp);
		}

		return form;

	}

}
