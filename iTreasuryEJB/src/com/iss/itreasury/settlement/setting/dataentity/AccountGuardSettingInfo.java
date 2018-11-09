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

	// �����Ա����
	private long currencyId = -1; // ����ID

	private long officeId = -1; // ���´�ID

	private long accountId = -1; // �����˻�ID

	private double sumLimited = 0.00; // �˻��ۼƸ��������

	private double transPayLimited = 0.00; // ����֧����������

	private Timestamp validDate = null; // ��Ч����

	private long statDays = -1; // ͳ�����ڣ��죩

	private String remark = null; // �ı���ע

	private long isNeedGuard = -1; // �Ƿ���Ҫ���

	private long inputUserId = -1; // ¼����

	private Timestamp inputDate = null; // ¼��ʱ��

	private long modifyUserId = -1; // �޸���

	private Timestamp modifyDate = null; // �޸�ʱ��

	private long statusId = -1; // ��¼��״̬

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
	 * �Ƿ���Ҫ���
	 * 
	 * @return ���� isNeedGuard��
	 */
	public long getIsNeedGuard() {
		return isNeedGuard;
	}

	/**
	 * �Ƿ���Ҫ���
	 * 
	 * @param isNeedGuard
	 *            Ҫ���õ� isNeedGuard��
	 */
	public void setIsNeedGuard(long isNeedGuard) {
		putUsedField("isNeedGuard", isNeedGuard);
		this.isNeedGuard = isNeedGuard;
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
	 * ͳ�����ڣ��죩
	 * 
	 * @return ���� statDays��
	 */
	public long getStatDays() {
		return statDays;
	}

	/**
	 * ͳ�����ڣ��죩
	 * 
	 * @param statDays
	 *            Ҫ���õ� statDays��
	 */
	public void setStatDays(long statDays) {
		putUsedField("statDays", statDays);
		this.statDays = statDays;
	}

	/**
	 * ��¼��״̬
	 * 
	 * @return ���� statusID��
	 */
	public long getStatusId() {
		return statusId;
	}

	/**
	 * ��¼��״̬
	 * 
	 * @param statusID
	 *            Ҫ���õ� statusID��
	 */
	public void setStatusId(long statusID) {
		putUsedField("statusID", statusID);
		this.statusId = statusID;
	}

	/**
	 * �˻��ۼƸ��������
	 * 
	 * @return ���� sumLimited��
	 */
	public double getSumLimited() {
		return sumLimited;
	}

	/**
	 * �˻��ۼƸ��������
	 * 
	 * @param sumLimited
	 *            Ҫ���õ� sumLimited��
	 */
	public void setSumLimited(double sumLimited) {
		putUsedField("sumLimited", sumLimited);
		this.sumLimited = sumLimited;
	}

	/**
	 * ����֧����������
	 * 
	 * @return ���� transPayLimited��
	 */
	public double getTransPayLimited() {
		return transPayLimited;
	}

	/**
	 * ����֧����������
	 * 
	 * @param transPayLimited
	 *            Ҫ���õ� transPayLimited��
	 */
	public void setTransPayLimited(double transPayLimited) {
		putUsedField("transPayLimited", transPayLimited);
		this.transPayLimited = transPayLimited;
	}

	/**
	 * ��Ч����
	 * 
	 * @return ���� validDate��
	 */
	public Timestamp getValidDate() {
		return validDate;
	}

	/**
	 * ��Ч����
	 * 
	 * @param validDate
	 *            Ҫ���õ� validDate��
	 */
	public void setValidDate(Timestamp validDate) {
		putUsedField("validDate", validDate);
		this.validDate = validDate;
	}

	/**
	 * ��������
	 * 
	 * @return ���� inputDate��
	 */
	public Timestamp getInputDate() {
		return inputDate;
	}

	/**
	 * ��������
	 * 
	 * @param inputDate
	 *            Ҫ���õ� inputDate��
	 */
	public void setInputDate(Timestamp inputDate) {
		putUsedField("inputDate", inputDate);
		this.inputDate = inputDate;
	}

	/**
	 * ������ID
	 * 
	 * @return ���� inputUserId��
	 */
	public long getInputUserId() {
		return inputUserId;
	}

	/**
	 * ������ID
	 * 
	 * @param inputUserId
	 *            Ҫ���õ� inputUserId��
	 */
	public void setInputUserId(long inputUserId) {
		putUsedField("inputUserId", inputUserId);
		this.inputUserId = inputUserId;
	}

	/**
	 * �޸�����
	 * 
	 * @return ���� modifyDate��
	 */
	public Timestamp getModifyDate() {
		return modifyDate;
	}

	/**
	 * �޸�����
	 * 
	 * @param modifyDate
	 *            Ҫ���õ� modifyDate��
	 */
	public void setModifyDate(Timestamp modifyDate) {
		putUsedField("modifyDate", modifyDate);
		this.modifyDate = modifyDate;
	}

	/**
	 * �޸���ID
	 * 
	 * @return ���� modifyUserId��
	 */
	public long getModifyUserId() {
		return modifyUserId;
	}

	/**
	 * �޸���ID
	 * 
	 * @param modifyUserId
	 *            Ҫ���õ� modifyUserId��
	 */
	public void setModifyUserId(long modifyUserId) {
		putUsedField("modifyUserId", modifyUserId);
		this.modifyUserId = modifyUserId;
	}

	/**
	 * ��ǰ����
	 * 
	 * @return ���� currencyId��
	 */
	public long getCurrencyId() {
		return currencyId;
	}

	/**
	 * ��ǰ����
	 * 
	 * @param currencyId
	 *            Ҫ���õ� currencyId��
	 */
	public void setCurrencyId(long currencyId) {
		putUsedField("currencyId", currencyId);
		this.currencyId = currencyId;
	}

	/**
	 * ���´�ID
	 * 
	 * @return ���� officeId��
	 */
	public long getOfficeId() {
		return officeId;
	}

	/**
	 * ���´�ID
	 * 
	 * @param officeId
	 *            Ҫ���õ� officeId��
	 */
	public void setOfficeId(long officeId) {
		putUsedField("officeId", officeId);
		this.officeId = officeId;
	}

	// ��ȡ������
	public static AccountGuardSettingInfo GetForm(HttpServletRequest request)
			throws Exception {

		// �������
		AccountGuardSettingInfo form = new AccountGuardSettingInfo();

		// ��ȡ������
		try {
			String strTmp = "";

			// ��¼ID
			strTmp = (String) request.getParameter("lID");
			if (strTmp != null && strTmp.trim().length() > 0) {
				form.setId(Long.parseLong(strTmp));
			}

			// ��ǰ����
			strTmp = (String) request.getParameter("lCurrencyId");
			if (strTmp != null && strTmp.trim().length() > 0) {
				form.setCurrencyId(Long.parseLong(strTmp));
			}

			// ���´�
			strTmp = (String) request.getParameter("lOfficeId");
			if (strTmp != null && strTmp.trim().length() > 0) {
				form.setOfficeId(Long.parseLong(strTmp));
			}

			// �����˻�id
			strTmp = (String) request.getParameter("lAccountID");
			if (strTmp != null && strTmp.trim().length() > 0) {
				form.setAccountId(Long.parseLong(strTmp));
			}

			// ��Ч����
			strTmp = (String) request.getParameter("strValidDate");
			if (strTmp != null && strTmp.trim().length() > 0) {
				form.setValidDate(DataFormat.getDateTime(strTmp));
			}

			// ͳ�����ڣ��죩
			strTmp = (String) request.getParameter("lStatDays");
			if (strTmp != null && strTmp.trim().length() > 0) {
				form.setStatDays(Long.parseLong(strTmp));
			}

			// �Ƿ���Ҫ���
			strTmp = (String) request.getParameter("lIsNeedGuard");
			if (strTmp != null && strTmp.trim().length() > 0) {
				form.setIsNeedGuard(Long.parseLong(strTmp));
			}

			// �ı���ע
			strTmp = (String) request.getParameter("strRemark");
			if (strTmp != null && strTmp.trim().length() > 0) {
				form.setRemark(strTmp);
			}

			// ����֧����������
			strTmp = (String) request.getParameter("dTransPayLimited");
			if (strTmp != null && strTmp.trim().length() > 0) {
				form.setTransPayLimited(DataFormat.parseNumber(strTmp));
			}

			// �˻��ۼƸ��������
			strTmp = (String) request.getParameter("dSumLimited");
			if (strTmp != null && strTmp.trim().length() > 0) {
				form.setSumLimited(DataFormat.parseNumber(strTmp));
			}

			// ¼����ID
			strTmp = (String) request.getParameter("lInputUserId");
			if (strTmp != null && strTmp.trim().length() > 0) {
				form.setInputUserId(Long.parseLong(strTmp));
			}

			// ¼������
			strTmp = (String) request.getParameter("strInputDate");
			if (strTmp != null && strTmp.trim().length() > 0) {
				form.setInputDate(DataFormat.getDateTime(strTmp));
			}

			// �޸���ID
			strTmp = (String) request.getParameter("lModifyUserId");
			if (strTmp != null && strTmp.trim().length() > 0) {
				form.setModifyUserId(Long.parseLong(strTmp));
			}

			// �޸�����
			strTmp = (String) request.getParameter("strModifyDate");
			if (strTmp != null && strTmp.trim().length() > 0) {
				form.setModifyDate(DataFormat.getDateTime(strTmp));
			}

			// ��¼״̬
			strTmp = (String) request.getParameter("lStatusId");
			if (strTmp != null && strTmp.trim().length() > 0) {
				form.setStatusId(Long.parseLong(strTmp));
			}

		} catch (Exception e) {
			// e.printStackTrace();
			throw e;
		}

		// ���غ���ֵ
		return form;

	}

}
