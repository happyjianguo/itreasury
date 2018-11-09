/*
 * Created on 2003-12-15
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.aheadrepaynotice.dataentity;

import java.sql.Timestamp;
import com.iss.itreasury.util.*;
/**
 * @author hyzeng
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AheadRepayNoticeQueryInfo implements java.io.Serializable
{
	private long ClientID = -1; //��λ
	private String ClientName = ""; //��λ
	private long ContractID = -1; //��ͬID
	private String ContractNo = ""; //��ͬ���
	private double AmountFrom = 0; //��ǰ������:�� 
	private double AmountTo = 0; //��ǰ�������
	private Timestamp InputDateFrom = null; //¼�����ڣ� ��  
	private Timestamp InputDateTo = null; //¼�����ڣ� ��
	private long StatusID = -1; //֪ͨ��״̬��
	private long ActionID = -1;

	//��������
	private long InputUserID = -1; //¼����
	private long CurrencyID = -1; //����
	private long OfficeID = -1; //���´�

	//��ҳ����
	private long lPageLineCount = 0;
	private long lPageNo = 1;
	private long lOrderParam = 1;
	private long lDesc = Constant.PageControl.CODE_ASCORDESC_DESC;

	/**
	 * @return
	 */
	public double getAmountFrom()
	{
		return AmountFrom;
	}

	public String getFormatAmountFrom()
	{
		return DataFormat.formatDisabledAmount(AmountFrom);
	}

	/**
	 * @return
	 */
	public double getAmountTo()
	{
		return AmountTo;
	}

	public String getFormatAmountTo()
	{
		return DataFormat.formatDisabledAmount(AmountTo);
	}

	/**
	 * @return
	 */
	public long getClientID()
	{
		return ClientID;
	}

	/**
	 * @return
	 */
	public long getContractID()
	{
		return ContractID;
	}

	/**
	 * @return
	 */
	public Timestamp getInputDateFrom()
	{
		return InputDateFrom;
	}

	public String getFormatInputDateFrom()
	{
		return DataFormat.formatDate(InputDateFrom);
	}

	/**
	 * @return
	 */
	public Timestamp getInputDateTo()
	{
		return InputDateTo;
	}

	public String getFormatInputDateTo()
	{
		return DataFormat.formatDate(InputDateTo);
	}

	/**
	 * @return
	 */
	public long getStatusID()
	{
		return StatusID;
	}

	/**
	 * @param d
	 */
	public void setAmountFrom(double d)
	{
		AmountFrom = d;
	}

	/**
	 * @param d
	 */
	public void setAmountTo(double d)
	{
		AmountTo = d;
	}

	/**
	 * @param l
	 */
	public void setClientID(long l)
	{
		ClientID = l;
	}

	/**
	 * @param l
	 */
	public void setContractID(long l)
	{
		ContractID = l;
	}

	/**
	 * @param timestamp
	 */
	public void setInputDateFrom(Timestamp timestamp)
	{
		InputDateFrom = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setInputDateTo(Timestamp timestamp)
	{
		InputDateTo = timestamp;
	}

	/**
	 * @param l
	 */
	public void setStatusID(long l)
	{
		StatusID = l;
	}

	/**
	 * @return
	 */
	public long getDesc()
	{
		return lDesc;
	}

	/**
	 * @return
	 */
	public long getOrderParam()
	{
		return lOrderParam;
	}

	/**
	 * @return
	 */
	public long getPageLineCount()
	{
		return lPageLineCount;
	}

	/**
	 * @return
	 */
	public long getPageNo()
	{
		return lPageNo;
	}

	/**
	 * @param l
	 */
	public void setDesc(long l)
	{
		lDesc = l;
	}

	/**
	 * @param l
	 */
	public void setOrderParam(long l)
	{
		lOrderParam = l;
	}

	/**
	 * @param l
	 */
	public void setPageLineCount(long l)
	{
		lPageLineCount = l;
	}

	/**
	 * @param l
	 */
	public void setPageNo(long l)
	{
		lPageNo = l;
	}

	/**
	 * @return
	 */
	public long getCurrencyID()
	{
		return CurrencyID;
	}

	/**
	 * @return
	 */
	public long getInputUserID()
	{
		return InputUserID;
	}

	/**
	 * @return
	 */
	public long getOfficeID()
	{
		return OfficeID;
	}

	/**
	 * @param l
	 */
	public void setCurrencyID(long l)
	{
		CurrencyID = l;
	}

	/**
	 * @param l
	 */
	public void setInputUserID(long l)
	{
		InputUserID = l;
	}

	/**
	 * @param l
	 */
	public void setOfficeID(long l)
	{
		OfficeID = l;
	}

	/**
	 * @return
	 */
	public long getActionID()
	{
		return ActionID;
	}

	/**
	 * @param l
	 */
	public void setActionID(long l)
	{
		ActionID = l;
	}

	/**
	 * @return
	 */
	public String getContractNo()
	{
		return ContractNo;
	}

	/**
	 * @param string
	 */
	public void setContractNo(String string)
	{
		ContractNo = string;
	}

	/**
	 * @return
	 */
	public String getClientName()
	{
		return ClientName;
	}

	/**
	 * @param string
	 */
	public void setClientName(String string)
	{
		ClientName = string;
	}

}
