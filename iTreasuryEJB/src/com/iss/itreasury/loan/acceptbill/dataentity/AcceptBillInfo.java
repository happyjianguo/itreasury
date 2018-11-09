package com.iss.itreasury.loan.acceptbill.dataentity;

import java.sql.Date;

import javax.servlet.ServletRequest;

import com.iss.itreasury.loan.base.LoanBaseDataEntity;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.ITreasuryException;

public class AcceptBillInfo extends  LoanBaseDataEntity{
	private  long 		id = -1;       			//�жһ�Ʊ��Ϣ
	private  String 	code = "";				//�ڲ���ˮ��
	private  long		officeId = -1;			//���´�ID
	private  String 	billClient = "";			//��Ʊ��λ����
	private  String 	billClientName = "";			//��Ʊ��λ����
	private  String 	billID = "";				//��Ʊ���
	private  String 	contractNo = "";			//��ͬ/Э���
	private  String 	projectName = "";		//��Ŀ����
	private  String 	outTicketDate = "";		//��Ʊ����
	private  double     money = 0.0;				//���
	private  long       mCurrencyID = -1;		//������
	private  double     exchangeRate = 0.0;		//������һ���
	private  double     convertRMB = 0.0;			//�ۺ������
	private  double     convertRMB0 = 0.0;			//�޸�֮ǰ���ۺ������
    private  long       conferContractNo = -1;	//���ź�ͬ��ID
    private  String     conferContractCode = "";	//���ź�ͬ��
    private  String     certificateBank = "";	//��֤����
    private  String     payee = "";				//�տ���
    private  String     payeeAccountID = "";		//�տ����˺�
    private  String     startDate = "";			//ǩ������
	private  String     endDate = "";			//��������
    private  long       statusID = -1;			//״̬ 1Ϊδʹ�� 2Ϊ��ʹ��
    private  String     remark = "";				//��ע
    
    //ҳ����ֶ�
    private  String       fromStartDate = "";		//ǩ����ʼʱ��
    private  String 	  endStartDate = "";		//ǩ������ʱ��
    private  String 	  fromEndDate = "";		//���ڿ�ʼʱ��
    private  String 	  endEndDate = "";			//���ڽ���ʱ��
    
 
    public void  formatString()
	{
    	remark = DataFormat.formatString(remark);		
	}
    
    /**
     * ��ҳ�洫�������������͵��ַ���ȥ������
     * @param str
     * @return
     */
    public String toFloatString(String str)
    {
    	String temp="";
    	for(int i=0;i<str.length();i++)
    	{
    		if(str.charAt(i)!=',')
    		{
    			temp += str.charAt(i);
    		}
    	}
    	return temp;   	
    }
    /**
	 * �̳и����convertRequestToDataEntity���������Դ�Request��ȡ����AcceptBillInfo�󶨵Ĳ�����
	 * ����װ��dataentity��
	 */
    public void convertRequestToDataEntity(ServletRequest request)throws ITreasuryException{
		//super.convertRequestToDataEntity(request);
	
		String strTmp = "";
		strTmp = (String) request.getAttribute("id");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			id = Long.parseLong(strTmp.trim());
		}		
		strTmp = (String) request.getAttribute("code");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			code = strTmp.trim();
		}	
		strTmp = (String) request.getAttribute("officeId");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			officeId = (Long.valueOf(strTmp.trim())).longValue();
		}	
		strTmp = (String) request.getAttribute("billClient");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			billClient = strTmp.trim();
		}		
		strTmp = (String) request.getAttribute("billClientName");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			billClientName = strTmp.trim();
		}		
		strTmp = (String) request.getAttribute("billID");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			billID = strTmp.trim();
		}	
		strTmp = (String) request.getAttribute("contractNo");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			contractNo = strTmp.trim();
		}		
		strTmp = (String) request.getAttribute("projectName");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			projectName = strTmp.trim();
		}		
		strTmp = (String) request.getAttribute("outTicketDate");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			outTicketDate = strTmp.trim();
		}		
		strTmp = (String) request.getAttribute("money");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			
			money = (Double.valueOf(toFloatString(strTmp.trim()))).doubleValue();
		}		
		strTmp = (String) request.getAttribute("mCurrencyID");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			mCurrencyID = (Long.valueOf(strTmp.trim())).longValue();
		}		
		strTmp = (String) request.getAttribute("exchangeRate");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			exchangeRate = (Double.valueOf(toFloatString(strTmp.trim()))).doubleValue();
		}		
		strTmp = (String) request.getAttribute("convertRMB");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			convertRMB = (Double.valueOf(toFloatString(strTmp.trim()))).doubleValue();
		}		
		strTmp = (String) request.getAttribute("convertRMB0");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			convertRMB0 = (Double.valueOf(toFloatString(strTmp.trim()))).doubleValue();
		}		
		strTmp = (String) request.getAttribute("conferContractNo");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			conferContractNo = Long.valueOf(strTmp.trim()).longValue();
		}	
		strTmp = (String) request.getAttribute("conferContractCode");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			conferContractCode = strTmp.trim();
		}		
		strTmp = (String) request.getAttribute("certificateBank");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			certificateBank = strTmp.trim();
		}		
		strTmp = (String) request.getAttribute("payee");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			payee = strTmp.trim();
		}		
		strTmp = (String) request.getAttribute("payeeAccountID");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			payeeAccountID = strTmp.trim();
		}		
		strTmp = (String) request.getAttribute("startDate");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			startDate = strTmp.trim();
		}		
		strTmp = (String) request.getAttribute("endDate");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			endDate = strTmp.trim();
		}		
		strTmp = (String) request.getAttribute("statusID");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			statusID = (Long.valueOf(strTmp.trim())).longValue();
		}		
		strTmp = (String) request.getAttribute("remark");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			remark = strTmp.trim();
		}		
		
		
		
		strTmp = (String) request.getAttribute("fromStartDate");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			fromStartDate = strTmp.trim();
		}	
		strTmp = (String) request.getAttribute("endStartDate");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			endStartDate = strTmp.trim();
		}	
		strTmp = (String) request.getAttribute("fromEndDate");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			fromEndDate = strTmp.trim();
		}	
		strTmp = (String) request.getAttribute("endEndDate");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			endEndDate = strTmp.trim();
		}	
	
	}
    
    //  ��dataentity��Ϊ�յ�����ֵת��Ϊ���ַ���
	public void nullToString()
	{	
		if(code == null)
		{
			code = "";
		}
		if(billClient == null)
		{
			billClient = "";
		}
		if(billID == null)
		{
			billID = "";
		}
		if(contractNo == null)
		{
			contractNo = "";
		}
		if(projectName == null)
		{
			projectName = "";
		}
		if(outTicketDate == null)
		{
			outTicketDate = "";
		}
		if(certificateBank == null)
		{
			certificateBank = "";
		}
		if(payee == null)
		{
			payee = "";
		}
		if(payeeAccountID == null)
		{
			payeeAccountID = "";
		}
		if(startDate == null)
		{
			startDate = "";
		}
		if(endDate == null)
		{
			endDate = "";
		}
		if(remark == null)
		{
			remark = "";
		}
		if(fromStartDate == null)
		{
			fromStartDate = "";
		}
		if(endStartDate == null)
		{
			endStartDate = "";
		}
		if(fromEndDate == null)
		{
			fromEndDate = "";
		}
		if(endEndDate == null)
		{
			endEndDate = "";
		}
	}

	public String getBillClient() {
		return billClient;
	}

	public void setBillClient(String billClient) {
		this.billClient = billClient;
	}

	public String getBillID() {
		return billID;
	}

	public void setBillID(String billID) {
		this.billID = billID;
	}

	public String getCertificateBank() {
		return certificateBank;
	}

	public void setCertificateBank(String certificateBank) {
		this.certificateBank = certificateBank;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}



	public long getConferContractNo() {
		return conferContractNo;
	}
	public void setConferContractNo(long conferContractNo) {
		this.conferContractNo = conferContractNo;
	}
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public double getConvertRMB() {
		return convertRMB;
	}

	public void setConvertRMB(double convertRMB) {
		this.convertRMB = convertRMB;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getEndEndDate() {
		return endEndDate;
	}

	public void setEndEndDate(String endEndDate) {
		this.endEndDate = endEndDate;
	}

	public String getEndStartDate() {
		return endStartDate;
	}

	public void setEndStartDate(String endStartDate) {
		this.endStartDate = endStartDate;
	}

	public double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String getFromEndDate() {
		return fromEndDate;
	}

	public void setFromEndDate(String fromEndDate) {
		this.fromEndDate = fromEndDate;
	}

	public String getFromStartDate() {
		return fromStartDate;
	}

	public void setFromStartDate(String fromStartDate) {
		this.fromStartDate = fromStartDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getMCurrencyID() {
		return mCurrencyID;
	}

	public void setMCurrencyID(long currencyID) {
		mCurrencyID = currencyID;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getOutTicketDate() {
		return outTicketDate;
	}

	public void setOutTicketDate(String outTicketDate) {
		this.outTicketDate = outTicketDate;
	}

	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}

	public String getPayeeAccountID() {
		return payeeAccountID;
	}

	public void setPayeeAccountID(String payeeAccountID) {
		this.payeeAccountID = payeeAccountID;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public long getStatusID() {
		return statusID;
	}

	public void setStatusID(long statusID) {
		this.statusID = statusID;
	}
	
	/**
	 * ��������set�������Խ������ݿ���ȡ������Date���͵�����ת��ΪString���͵ġ�
	 * @param outTicketDate,startDate,endDate
	 */
	public void setOutTicketDate(Date outTicketDate)
    {
	    this.outTicketDate =outTicketDate.toString();
	}
	
	public void setStartDate(Date startDate)
	{
	    this.startDate = startDate.toString();
	}
	
	public void setEndDate(Date endDate)
	{
	    this.endDate = endDate.toString();
	}
	public long getOfficeId() {
		return officeId;
	}
	public void setOfficeId(long officeId) {
		this.officeId = officeId;
	}

	public String getBillClientName() {
		return billClientName;
	}

	public void setBillClientName(String billClientName) {
		this.billClientName = billClientName;
	}

	public String getConferContractCode() {
		return conferContractCode;
	}

	public void setConferContractCode(String conferContractCode) {
		this.conferContractCode = conferContractCode;
	}

	public double getConvertRMB0() {
		return convertRMB0;
	}

	public void setConvertRMB0(double convertRMB0) {
		this.convertRMB0 = convertRMB0;
	}
	   
}
