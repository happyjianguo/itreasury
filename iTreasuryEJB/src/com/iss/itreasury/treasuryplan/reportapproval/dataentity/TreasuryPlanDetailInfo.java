/*
 * Created on 2004-7-13
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.treasuryplan.reportapproval.dataentity;

import com.iss.itreasury.treasuryplan.util.*;
import java.sql.Timestamp;
/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TreasuryPlanDetailInfo extends TreasuryPlanBaseDataEntity
{
	private long id = -1;
	private long treasuryPlanID = -1;		//�ƻ��汾ID
	private Timestamp transactionDate = null;	//Ԥ��ִ������
	private long lineID = -1;				//����ĿID
	private String lineNo = "";				//�д�
	private String lineName = "";			//����Ŀ����
	private long lineLevel = -1;			//����Ŀ����
	private long parentLineID = -1;			//�ϼ�����ĿID
	private long isLeaf = -1;				//�Ƿ�Ҷ��
	private String authorizedDepartment = "";//��������
	private String authorizedUser = "";		//�����û�
	private double forecastAmount = 0;		//Ԥ����
	private double planAmount = 0;			//�ƻ���
	private long isNeedSum = 1;            //�Ƿ���빫ʽ����

	/**
	 * @return
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * @param l
	 */
	public void setId(long l)
	{
		id = l;
		putUsedField("ID", id);
	}

	/**
	 * @return
	 */
	public String getAuthorizedDepartment()
	{
		return authorizedDepartment;
	}

	/**
	 * @return
	 */
	public String getAuthorizedUser()
	{
		return authorizedUser;
	}

	/**
	 * @return
	 */
	public double getForecastAmount()
	{
		return (int)forecastAmount;
	}

	/**
	 * @return
	 */
	public long getIsLeaf()
	{
		return isLeaf;
	}

	/**
	 * @return
	 */
	public long getLineID()
	{
		return lineID;
	}

	/**
	 * @return
	 */
	public long getLineLevel()
	{
		return lineLevel;
	}

	/**
	 * @return
	 */
	public String getLineName()
	{
		return lineName;
	}

	/**
	 * @return
	 */
	public String getLineNo()
	{
		return lineNo;
	}

	/**
	 * @return
	 */
	public long getParentLineID()
	{
		return parentLineID;
	}

	/**
	 * @return
	 */
	public double getPlanAmount()
	{
		return (int)planAmount;
	}

	/**
	 * @return
	 */
	public Timestamp getTransactionDate()
	{
		return transactionDate;
	}

	/**
	 * @return
	 */
	public long getTreasuryPlanID()
	{
		return treasuryPlanID;
	}

	/**
	 * @param l
	 */
	public void setAuthorizedDepartment(String l)
	{
		authorizedDepartment = l;
		putUsedField("authorizedDepartment", authorizedDepartment);
	}

	/**
	 * @param l
	 */
	public void setAuthorizedUser(String l)
	{
		authorizedUser = l;
		putUsedField("authorizedUser", authorizedUser);
	}

	/**
	 * @param d
	 */
	public void setForecastAmount(double d)
	{
		forecastAmount = d;
		putUsedField("forecastAmount", forecastAmount);
	}

	/**
	 * @param l
	 */
	public void setIsLeaf(long l)
	{
		isLeaf = l;
		putUsedField("isLeaf",isLeaf );
	}

	/**
	 * @param l
	 */
	public void setLineID(long l)
	{
		lineID = l;
		putUsedField("lineID",lineID );
	}

	/**
	 * @param l
	 */
	public void setLineLevel(long l)
	{
		lineLevel = l;
		putUsedField("lineLevel", lineLevel);
	}

	/**
	 * @param string
	 */
	public void setLineName(String string)
	{
		lineName = string;
		putUsedField("linename",lineName );
	}

	/**
	 * @param string
	 */
	public void setLineNo(String string)
	{
		lineNo = string;
		putUsedField("lineNO",lineNo );
	}

	/**
	 * @param l
	 */
	public void setParentLineID(long l)
	{
		parentLineID = l;
		putUsedField("parentLineID",parentLineID );
	}

	/**
	 * @param d
	 */
	public void setPlanAmount(double d)
	{
		planAmount = d;
		putUsedField("planAmount", planAmount);
	}

	/**
	 * @param timestamp
	 */
	public void setTransactionDate(Timestamp timestamp)
	{
		transactionDate = timestamp;
		putUsedField("transactionDate",transactionDate );
	}

	/**
	 * @param l
	 */
	public void setTreasuryPlanID(long l)
	{
		treasuryPlanID = l;
		putUsedField("treasuryPlanID",treasuryPlanID );
	}

	/**
	 * @return Returns the isNeedSum.
	 */
	public long getIsNeedSum() {
		return isNeedSum;
	}
	/**
	 * @param isNeedSum The isNeedSum to set.
	 */
	public void setIsNeedSum(long isNeedSum) {
		this.isNeedSum = isNeedSum;
		putUsedField("isNeedSum",isNeedSum );
	}
}
