package com.iss.itreasury.settlement.bankinterface.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
/**
 * @author kenny hu
 * @version Date of Creation 2005-07-20
 */
public class QueryConditionInfo implements Serializable {
	private long officeId               = -1;  //���´�
	private long currencyId             = -1;  //����
    private String fileName             = null;//�ϴ����ļ�����
    private String strSuccessPageURL    = null;//�ɹ���ת����ҳ��
    private String strFailPageURL       = null;//ʧ�ܵ�ת����ҳ��
	/**
	 * @return Returns the strFailPageURL.
	 */
	public String getStrFailPageURL() {
		return strFailPageURL;
	}
	/**
	 * @param strFailPageURL The strFailPageURL to set.
	 */
	public void setStrFailPageURL(String strFailPageURL) {
		this.strFailPageURL = strFailPageURL;
	}
	/**
	 * @return Returns the strSuccessPageURL.
	 */
	public String getStrSuccessPageURL() {
		return strSuccessPageURL;
	}
	/**
	 * @param strSuccessPageURL The strSuccessPageURL to set.
	 */
	public void setStrSuccessPageURL(String strSuccessPageURL) {
		this.strSuccessPageURL = strSuccessPageURL;
	}
	/**
	 * @return Returns the fileName.
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName The fileName to set.
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return Returns the currencyId.
	 */
	public long getCurrencyId() {
		return currencyId;
	}
	/**
	 * @param currencyId The currencyId to set.
	 */
	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
	}
	/**
	 * @return Returns the officeId.
	 */
	public long getOfficeId() {
		return officeId;
	}
	/**
	 * @param officeId The officeId to set.
	 */
	public void setOfficeId(long officeId) {
		this.officeId = officeId;
	}
}