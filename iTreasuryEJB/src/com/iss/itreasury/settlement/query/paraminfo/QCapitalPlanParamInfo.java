package com.iss.itreasury.settlement.query.paraminfo;

import java.sql.Timestamp;

public class QCapitalPlanParamInfo
{
	String strClientID = "";//�ͻ����
	Timestamp startDate = null;//��ʼ����
	Timestamp endDate = null;//��������
	public Timestamp getEndDate()
	{
		return endDate;
	}
	public void setEndDate(Timestamp endDate)
	{
		this.endDate = endDate;
	}
	public Timestamp getStartDate()
	{
		return startDate;
	}
	public void setStartDate(Timestamp startDate)
	{
		this.startDate = startDate;
	}
	public String getStrClientID()
	{
		return strClientID;
	}
	public void setStrClientID(String strClientID)
	{
		this.strClientID = strClientID;
	}
}
