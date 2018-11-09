package com.iss.itreasury.settlement.setting.dataentity;

import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;

public class TransferSettingInfo extends SettlementBaseDataEntity
{
	private long id = -1;
	private long officeId = -1;  //���´�ID
	private long currencyId = -1;  //����ID
	private long contractTypeId = -1;  //ת�ú�ͬ����ID
    private long contractId = -1;   //ת�ú�ͬID
    private String finceCapitalSubject = "";  //�����ع������ʲ���
    private String finceRepayInteSubject = "";  //������ҵ������Ϣ֧����Ŀ
    private String fincePayInteSubject = "";  //������ҵ����Ӧ����Ϣ��Ŀ
    private String zyAmountSubject = "";  //��Ӫ������Ŀ
    private String payInterestSubject = "";   //Ӧ��������Ϣ��Ŀ
    private String payAmountSubject = "";  //Ӧ�����ϱ����Ŀ
    private String scommissionSubject = "";  //�����Ѽ�Ӷ�������Ŀ
    private long statusId = -1;   //״̬
    
	public long getContractId() {
		return contractId;
	}
	public void setContractId(long contractId) {
		this.contractId = contractId;
		putUsedField("CONTRACTID", contractId);
	}
	public long getContractTypeId() {
		return contractTypeId;
	}
	public void setContractTypeId(long contractTypeId) {
		this.contractTypeId = contractTypeId;
		putUsedField("CONTRACTTYPEID", contractTypeId);
	}
	public long getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
		putUsedField("CURRENCYID", currencyId);
	}
	public String getFinceCapitalSubject() {
		return finceCapitalSubject;
	}
	public void setFinceCapitalSubject(String finceCapitalSubject) {
		this.finceCapitalSubject = finceCapitalSubject;
		putUsedField("FINCECAPITALSUBJECT", finceCapitalSubject);
	}
	public String getFincePayInteSubject() {
		return fincePayInteSubject;
	}
	public void setFincePayInteSubject(String fincePayInteSubject) {
		this.fincePayInteSubject = fincePayInteSubject;
		putUsedField("FINCEPAYINTESUBJECT", fincePayInteSubject);
	}
	public String getFinceRepayInteSubject() {
		return finceRepayInteSubject;
	}
	public void setFinceRepayInteSubject(String finceRepayInteSubject) {
		this.finceRepayInteSubject = finceRepayInteSubject;
		putUsedField("FINCEREPAYINTESUBJECT", finceRepayInteSubject);
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("ID", id);
	}
	public long getOfficeId() {
		return officeId;
	}
	public void setOfficeId(long officeId) {
		this.officeId = officeId;
		putUsedField("OFFICEID", officeId);
	}
	public String getPayAmountSubject() {
		return payAmountSubject;
	}
	public void setPayAmountSubject(String payAmountSubject) {
		this.payAmountSubject = payAmountSubject;
		putUsedField("PAYAMOUNTSUBJECT", payAmountSubject);
	}
	public String getPayInterestSubject() {
		return payInterestSubject;
	}
	public void setPayInterestSubject(String payInterestSubject) {
		this.payInterestSubject = payInterestSubject;
		putUsedField("PAYINTERESTSUBJECT", payInterestSubject);
	}
	public String getScommissionSubject() {
		return scommissionSubject;
	}
	public void setScommissionSubject(String scommissionSubject) {
		this.scommissionSubject = scommissionSubject;
		putUsedField("SCOMMISSIONSUBJECT", scommissionSubject);
	}
	public long getStatusId() {
		return statusId;
	}
	public void setStatusId(long statusId) {
		this.statusId = statusId;
		putUsedField("STATUSID", statusId);
	}
	public String getZyAmountSubject() {
		return zyAmountSubject;
	}
	public void setZyAmountSubject(String zyAmountSubject) {
		this.zyAmountSubject = zyAmountSubject;
		putUsedField("ZYAMOUNTSUBJECT", zyAmountSubject);
	}
    
}
