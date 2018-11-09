/*
 * Created on 2003-10-8
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.obfinanceinstr.dataentity;

/**
 * @author hyzeng
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
import java.sql.Timestamp;
import com.iss.itreasury.util.DataFormat;

public class OBCapSummarizeInfo implements java.io.Serializable
{
	private Timestamp tsConfirmDate = null; // ȷ��ʱ��
	private long lTotalCount = 0; //���б���
	private long lUnCheckCount = 0; //δ���˱���
	private long lApprovalingCount = 0; //�����б���
	private long lApprovaledCount = 0; //������ɱ���
	private long lCheckCount = 0; //�Ѹ��˱���
	private long lSignCount = 0; //��ǩ�ϱ���
	private long lOnGoingCount = 0; //�����б���
	private long lFinishedCount = 0; //����ɱ���
	private long lRefusedCount = 0; //�Ѿܾ�����
	private double dTotalAmount = 0; //�ܽ��׽��
	private double dLoanAmount = 0; //���д����
	private double dDebitAmount = 0; //���н���
	/**
	 * @return
	 */
	public double getDDebitAmount()
	{
		return dDebitAmount;
	}

	/**
	 * @return
	 */
	public double getDLoanAmount()
	{
		return dLoanAmount;
	}

	/**
	 * @return
	 */
	public double getDTotalAmount()
	{
		return dTotalAmount;
	}

	/**
	 * @return
	 */
	public long getCheckCount()
	{
		return lCheckCount;
	}

	/**
	 * @return
	 */
	public long getFinishedCount()
	{
		return lFinishedCount;
	}

	/**
	 * @return
	 */
	public long getOnGoingCount()
	{
		return lOnGoingCount;
	}

	/**
	 * @return
	 */
	public long getRefusedCount()
	{
		return lRefusedCount;
	}

	/**
	 * @return
	 */
	public long getSignCount()
	{
		return lSignCount;
	}

	/**
	 * @return
	 */
	public long getTotalCount()
	{
		return lTotalCount;
	}

	/**
	 * @return
	 */
	public long getUnCheckCount()
	{
		return lUnCheckCount;
	}

	/**
	 * @return
	 */
	public Timestamp getConfirmDate()
	{
		return tsConfirmDate;
	}
	
	public String getFormatConfirmDate()
	{
		return DataFormat.getDateTimeString(tsConfirmDate);
	} 

	/**
	 * @param d
	 */
	public void setDDebitAmount(double d)
	{
		dDebitAmount = d;
	}

	/**
	 * @param d
	 */
	public void setDLoanAmount(double d)
	{
		dLoanAmount = d;
	}

	/**
	 * @param d
	 */
	public void setDTotalAmount(double d)
	{
		dTotalAmount = d;
	}

	/**
	 * @param l
	 */
	public void setCheckCount(long l)
	{
		lCheckCount = l;
	}

	/**
	 * @param l
	 */
	public void setFinishedCount(long l)
	{
		lFinishedCount = l;
	}

	/**
	 * @param l
	 */
	public void setOnGoingCount(long l)
	{
		lOnGoingCount = l;
	}

	/**
	 * @param l
	 */
	public void setRefusedCount(long l)
	{
		lRefusedCount = l;
	}

	/**
	 * @param l
	 */
	public void setSignCount(long l)
	{
		lSignCount = l;
	}

	/**
	 * @param l
	 */
	public void setTotalCount(long l)
	{
		lTotalCount = l;
	}

	/**
	 * @param l
	 */
	public void setUnCheckCount(long l)
	{
		lUnCheckCount = l;
	}

	/**
	 * @param timestamp
	 */
	public void setConfirmDate(Timestamp timestamp)
	{
		tsConfirmDate = timestamp;
	}

	/**
	 * @return
	 */
	public double getDebitAmount()
	{
		return dDebitAmount;
	}
	
	public String getFormatDebitAmount()
	{
		return DataFormat.formatDisabledAmount(dDebitAmount);
	}
		
	/**
	 * @return
	 */
	public double getLoanAmount()
	{
		return dLoanAmount;
	}
	
	public String getFormatLoanAmount()
	{
		return DataFormat.formatDisabledAmount(dLoanAmount);
	}

	/**
	 * @return
	 */
	public double getTotalAmount()
	{
		return dTotalAmount;
	}
	
	public String getFormatTotalAmount()
	{
		return DataFormat.formatDisabledAmount(dTotalAmount);
	}

	/**
	 * @param d
	 */
	public void setDebitAmount(double d)
	{
		dDebitAmount = d;
	}

	/**
	 * @param d
	 */
	public void setLoanAmount(double d)
	{
		dLoanAmount = d;
	}

	/**
	 * @param d
	 */
	public void setTotalAmount(double d)
	{
		dTotalAmount = d;
	}

	public long getLApprovaledCount() {
		return lApprovaledCount;
	}

	public void setLApprovaledCount(long approvaledCount) {
		lApprovaledCount = approvaledCount;
	}

	public long getLApprovalingCount() {
		return lApprovalingCount;
	}

	public void setLApprovalingCount(long approvalingCount) {
		lApprovalingCount = approvalingCount;
	}

	public long getLUnCheckCount() {
		return lUnCheckCount;
	}

	public void setLUnCheckCount(long unCheckCount) {
		lUnCheckCount = unCheckCount;
	}

}
