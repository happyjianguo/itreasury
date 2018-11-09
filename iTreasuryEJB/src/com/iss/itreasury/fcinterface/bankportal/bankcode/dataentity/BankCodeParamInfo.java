package com.iss.itreasury.fcinterface.bankportal.bankcode.dataentity;

/**
 * fszhu
 * 2008-11-27
 * 
 */
import com.iss.itreasury.fcinterface.bankportal.sysframe.dataentity.BaseDataEntity;

public class BankCodeParamInfo extends BaseDataEntity 
{

	private String bankCode; // ���б��

	private String bankName; // ��������

	private String bankTypeCode; // �������ͱ���

	private String bankTypeName; // ������������

	private String provinceName; // ʡ����

	private String cityName; // ������
	
	private String oldReceiveBranchName; //�ύʱ�Ŀ���������
	
	private String lbankName;

	public String getOldReceiveBranchName() 
	{
		return oldReceiveBranchName;
	}

	public void setOldReceiveBranchName(String oldReceiveBranchName) 
	{
		this.oldReceiveBranchName = oldReceiveBranchName;
	}
	
	public String getBankCode() 
	{
		return bankCode;
	}

	public void setBankCode(String bankCode) 
	{
		this.bankCode = bankCode;

	}

	public String getBankName() 
	{
		return bankName;
	}

	public void setBankName(String bankName) 
	{
		this.bankName = bankName;

	}

	public String getBankTypeCode() 
	{
		return bankTypeCode;
	}

	public void setBankTypeCode(String bankTypeCode)
	{
		this.bankTypeCode = bankTypeCode;
	}

	public long getId() 
	{
		return 0;
	}

	public void setId(long id) 
	{
		
	}

	public String getBankTypeName() 
	{
		return bankTypeName;
	}

	public void setBankTypeName(String bankTypeName) 
	{
		this.bankTypeName = bankTypeName;
	}

	public String getCityName() 
	{
		return cityName;
	}

	public void setCityName(String cityName) 
	{
		this.cityName = cityName;
	}

	public String getProvinceName() 
	{
		return provinceName;
	}

	public void setProvinceName(String provinceName) 
	{
		this.provinceName = provinceName;
	}

	public String getLbankName() {
		return lbankName;
	}

	public void setLbankName(String lbankName) {
		this.lbankName = lbankName;
	}

}
