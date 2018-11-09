package com.iss.itreasury.loan.creditext.dataentity;

import com.iss.itreasury.util.DataFormat;

public class BankCreditExtComplexResultInfo {
	private String contractNo;			//���ź�ͬ��
	private String companyCode;			//��λ���
	private String companyName;			//��λ����
	private String bankName;			//��������
	private double splitedMixAmount;	//���ö�ȷ�����
	private double usingMixAmount;		//���ö��ռ�ý��
	private String usedMixAmount="N/A";	//���ö����ʹ�ý��
	private double mixRiskCoefficient1;	//���ö��-���ڴ������ϵ��
	private double mixRiskCoefficient2;	//���ö��-�г��ڴ������ϵ��
	private double mixRiskCoefficient3;	//���ö��-����֤����ϵ��
	private double mixRiskCoefficient4;	//���ö��-��������ϵ��
	private double mixRiskCoefficient5;	//���ö��-�Ŵ�֤������ϵ��
	private double mixRiskCoefficient6;	//���ö��-�жһ�Ʊ����ϵ��
	private double splitedSLAmount;		//���ڴ��������
	private double usingSLAmount;		//���ڴ���ռ�ý��
	private double usedSLAmount;		//���ڴ�����ʹ�ý��
	private double sLRiskCoefficient;	//���ڴ������ϵ��
	private double splitedLLAmount;		//�г��ڴ��������
	private double usingLLAmount;		//�г��ڴ���ռ�ý��
	private double usedLLAmount;		//�г��ڴ�����ʹ�ý��
	private double LlRiskCoefficient;	//�г��ڴ������ϵ��
	private double splitedLCAmount;		//����֤������
	private double usingLCAmount;		//����֤ռ�ý��
	private double usedLCAmount;		//����֤��ʹ�ý��
	private double lCRiskCoefficient;	//����֤����ϵ��
	private double splitedLGAmount;		//����������
	private double usingLGAmount;		//����ռ�ý��
	private double usedLGAmount;		//������ʹ�ý��
	private double lGRiskCoefficient;	//��������ϵ��
	private double splitedCPAmount;		//�Ŵ�֤��������
	private double usingCPAmount;		//�Ŵ�֤��ռ�ý��
	private double usedCPAmount;		//�Ŵ�֤����ʹ�ý��
	private double cPRiskCoefficient;	//�Ŵ�֤������ϵ��
	private double splitedABAmount;		//�жһ�Ʊ������
	private double usingABAmount;		//�жһ�Ʊռ�ý��
	private double usedABAmount;		//�жһ�Ʊ��ʹ�ý��
	private double aBRiskCoefficient;	//�жһ�Ʊ����ϵ��
	
	
	public double getUsingSLAmount1() {
		return (this.sLRiskCoefficient == 0) ? this.usingSLAmount : this.usingSLAmount * this.sLRiskCoefficient / 100;
	}
	public double getUsedSLAmount1() {
		return (this.sLRiskCoefficient == 0) ? this.usedSLAmount : this.usedSLAmount * this.sLRiskCoefficient / 100;
	}
	public double getUsingLLAmount1() {
		return (this.LlRiskCoefficient == 0) ? this.usingLLAmount : this.usingLLAmount * this.LlRiskCoefficient / 100;
	}
	public double getUsedLLAmount1() {
		return (this.LlRiskCoefficient == 0) ? this.usedLLAmount : this.usedLLAmount * this.LlRiskCoefficient / 100;
	}
	public double getUsingLCAmount1() {
		return (this.lCRiskCoefficient == 0) ? this.usingLCAmount : this.usingLCAmount * this.lCRiskCoefficient / 100;
	}
	public double getUsedLCAmount1() {
		return (this.lCRiskCoefficient == 0) ? this.usedLCAmount : this.usedLCAmount * this.lCRiskCoefficient / 100;
	}
	public double getUsingLGAmount1() {
		return (this.lGRiskCoefficient == 0) ? this.usingLGAmount : this.usingLGAmount * this.lGRiskCoefficient / 100;
	}
	public double getUsedLGAmount1() {
		return (this.lGRiskCoefficient == 0) ? this.usedLGAmount : this.usedLGAmount * this.lGRiskCoefficient / 100;
	}
	public double getUsingCPAmount1() {
		return (this.cPRiskCoefficient == 0) ? this.usingCPAmount : this.usingCPAmount * this.cPRiskCoefficient / 100;
	}
	public double getUsedCPAmount1() {
		return (this.cPRiskCoefficient == 0) ? this.usedCPAmount : this.usedCPAmount * this.cPRiskCoefficient / 100;
	}
	public double getUsingABAmount1() {
		return (this.aBRiskCoefficient == 0) ? this.usingABAmount : this.usingABAmount * this.aBRiskCoefficient / 100;
	}
	public double getUsedABAmount1() {
		return (this.aBRiskCoefficient == 0) ? this.usedABAmount : this.usedABAmount * this.aBRiskCoefficient / 100;
	}
	
	
	
	public String getContractNo() {
		return this.contractNo;
	}
	
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	public String getCompanyCode() {
		return this.companyCode;
	}
	
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
	public String getCompanyName() {
		return this.companyName;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String getBankName() {
		return this.bankName;
	}
	
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getUsedMixAmount() {
		return usedMixAmount;
	}

	public double getABRiskCoefficient() {
		return aBRiskCoefficient;
	}

	public void setABRiskCoefficient(double riskCoefficient) {
		aBRiskCoefficient = riskCoefficient;
	}

	public double getCPRiskCoefficient() {
		return cPRiskCoefficient;
	}

	public void setCPRiskCoefficient(double riskCoefficient) {
		cPRiskCoefficient = riskCoefficient;
	}

	public double getLCRiskCoefficient() {
		return lCRiskCoefficient;
	}

	public void setLCRiskCoefficient(double riskCoefficient) {
		lCRiskCoefficient = riskCoefficient;
	}

	public double getLGRiskCoefficient() {
		return lGRiskCoefficient;
	}

	public void setLGRiskCoefficient(double riskCoefficient) {
		lGRiskCoefficient = riskCoefficient;
	}

	public double getLlRiskCoefficient() {
		return LlRiskCoefficient;
	}

	public void setLlRiskCoefficient(double llRiskCoefficient) {
		LlRiskCoefficient = llRiskCoefficient;
	}

	public double getMixRiskCoefficient1() {
		return mixRiskCoefficient1;
	}

	public void setMixRiskCoefficient1(double mixRiskCoefficient1) {
		this.mixRiskCoefficient1 = mixRiskCoefficient1;
	}

	public double getMixRiskCoefficient2() {
		return mixRiskCoefficient2;
	}

	public void setMixRiskCoefficient2(double mixRiskCoefficient2) {
		this.mixRiskCoefficient2 = mixRiskCoefficient2;
	}

	public double getMixRiskCoefficient3() {
		return mixRiskCoefficient3;
	}

	public void setMixRiskCoefficient3(double mixRiskCoefficient3) {
		this.mixRiskCoefficient3 = mixRiskCoefficient3;
	}

	public double getMixRiskCoefficient4() {
		return mixRiskCoefficient4;
	}

	public void setMixRiskCoefficient4(double mixRiskCoefficient4) {
		this.mixRiskCoefficient4 = mixRiskCoefficient4;
	}

	public double getMixRiskCoefficient5() {
		return mixRiskCoefficient5;
	}

	public void setMixRiskCoefficient5(double mixRiskCoefficient5) {
		this.mixRiskCoefficient5 = mixRiskCoefficient5;
	}

	public double getMixRiskCoefficient6() {
		return mixRiskCoefficient6;
	}

	public void setMixRiskCoefficient6(double mixRiskCoefficient6) {
		this.mixRiskCoefficient6 = mixRiskCoefficient6;
	}

	public double getSLRiskCoefficient() {
		return sLRiskCoefficient;
	}

	public void setSLRiskCoefficient(double riskCoefficient) {
		sLRiskCoefficient = riskCoefficient;
	}

	public double getSplitedABAmount() {
		return splitedABAmount;
	}

	public void setSplitedABAmount(double splitedABAmount) {
		this.splitedABAmount = splitedABAmount;
	}

	public double getSplitedCPAmount() {
		return splitedCPAmount;
	}

	public void setSplitedCPAmount(double splitedCPAmount) {
		this.splitedCPAmount = splitedCPAmount;
	}

	public double getSplitedLCAmount() {
		return splitedLCAmount;
	}

	public void setSplitedLCAmount(double splitedLCAmount) {
		this.splitedLCAmount = splitedLCAmount;
	}

	public double getSplitedLGAmount() {
		return splitedLGAmount;
	}

	public void setSplitedLGAmount(double splitedLGAmount) {
		this.splitedLGAmount = splitedLGAmount;
	}

	public double getSplitedLLAmount() {
		return splitedLLAmount;
	}

	public void setSplitedLLAmount(double splitedLLAmount) {
		this.splitedLLAmount = splitedLLAmount;
	}

	public double getSplitedMixAmount() {
		return splitedMixAmount;
	}

	public void setSplitedMixAmount(double splitedMixAmount) {
		this.splitedMixAmount = splitedMixAmount;
	}

	public double getSplitedSLAmount() {
		return splitedSLAmount;
	}
	
	public void setSplitedSLAmount(double splitedSLAmount) {
		this.splitedSLAmount = splitedSLAmount;
	}

	public double getUsedABAmount() {
		return usedABAmount;
	}

	public void setUsedABAmount(double usedABAmount) {
		this.usedABAmount = usedABAmount;
	}

	public double getUsedCPAmount() {
		return usedCPAmount;
	}

	public void setUsedCPAmount(double usedCPAmount) {
		this.usedCPAmount = usedCPAmount;
	}

	public double getUsedLCAmount() {
		return usedLCAmount;
	}

	public void setUsedLCAmount(double usedLCAmount) {
		this.usedLCAmount = usedLCAmount;
	}

	public double getUsedLGAmount() {
		return usedLGAmount;
	}

	public void setUsedLGAmount(double usedLGAmount) {
		this.usedLGAmount = usedLGAmount;
	}

	public double getUsedLLAmount() {
		return usedLLAmount;
	}

	public void setUsedLLAmount(double usedLLAmount) {
		this.usedLLAmount = usedLLAmount;
	}

	public double getUsedSLAmount() {
		return usedSLAmount;
	}

	public void setUsedSLAmount(double usedSLAmount) {
		this.usedSLAmount = usedSLAmount;
	}

	public double getUsingABAmount() {
		return usingABAmount;
	}

	public void setUsingABAmount(double usingABAmount) {
		this.usingABAmount = usingABAmount;
	}

	public double getUsingCPAmount() {
		return usingCPAmount;
	}

	public void setUsingCPAmount(double usingCPAmount) {
		this.usingCPAmount = usingCPAmount;
	}

	public double getUsingLCAmount() {
		return usingLCAmount;
	}

	public void setUsingLCAmount(double usingLCAmount) {
		this.usingLCAmount = usingLCAmount;
	}

	public double getUsingLGAmount() {
		return usingLGAmount;
	}

	public void setUsingLGAmount(double usingLGAmount) {
		this.usingLGAmount = usingLGAmount;
	}

	public double getUsingLLAmount() {
		return usingLLAmount;
	}


	
	public void setUsingLLAmount(double usingLLAmount) {
		this.usingLLAmount = usingLLAmount;
	}

	public double getUsingMixAmount() {
		return usingMixAmount;
	}

	public void setUsingMixAmount(double usingMixAmount) {
		this.usingMixAmount = usingMixAmount;
	}

	public double getUsingSLAmount() {
		return usingSLAmount;
	}

	public void setUsingSLAmount(double usingSLAmount) {
		this.usingSLAmount = usingSLAmount;
	}
	
	public String toString(double amount) {
		return amount <= 0 ? "" : DataFormat.formatNumber(amount, 2);
	}
	
	public String toZeroString(double amount) {
		return amount <= 0 ? "0.00" : DataFormat.formatNumber(amount, 2);
	}
	
}
