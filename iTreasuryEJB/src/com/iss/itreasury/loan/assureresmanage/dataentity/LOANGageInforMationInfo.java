package com.iss.itreasury.loan.assureresmanage.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class LOANGageInforMationInfo implements Serializable {

	private  long      Id             = -1; // ID

	private  long      CurrencyId     = -1; // ����

	private  long      OfficeId       = -1; // ���´�

	private  long      FreeUnitSid    = -1; // ����Ȩ�˵�λID

	private  long      FeePersonType  = -1; // ����Ȩ����Ϣѡ��λ

	private  long      InputUserId    = -1; // ¼����

	private  Timestamp InputDate      = null; // ¼��ʱ��

	private  long      Status         = -1; // ״̬

	private  String    GageCode       = ""; // ����Ʒ���

	private  long      GagueProperty  = -1; // ����Ʒ����

	private  String    GagueName      = ""; // ����Ʒ����

	private  long      GagueType      = -1; // ����Ʒ���

	private  String    PawneePerson   = ""; // ����Ȩ����/��Ȩ��

	private  String    GageDescribing = ""; // ����Ʒ����
	
	//----------------------���ռ�ֵ-------------------
	
	private  String    AppraiseUnits     = ""; // ������λ

	private  double    AppraisValue      = 0; // ������ֵ

	private  double    PledgeAmount      = 0; // ʵ�ʵ���Ѻ�Ȩ����ֵ��

	private  double    ResidueAmount     = 0; // ʣ�����Ѻ��

	private  long      IsNotarization    = -1; // �Ƿ�֤

	private  Timestamp AppraisDate       = null; // ����ʱ��

	private  double    Ratio             = 0; // �ۼ���

	private  String    InsureOrgName     = ""; // ���չ�˾����

	private  double    InsureAmount      = 0; // ���ս��

	private  Timestamp StartDate         = null; // �������޴�

	private  Timestamp EndDate           = null; // �������޵�

	private  String    Insurant          = ""; // ��������
 
	private  String    GuaranteeSlipCode = ""; // �����������

	private  String    Beneficiary       = ""; // ������
	  //-------------------------------------------
	
	private  String    gytdlicence     = "";          //��������ʹ��֤
	    
	private  String    jsgcghlicence   = "";        //���蹤�̹滮���֤
	    
	private  String    spfyslicence    = "";         //��Ʒ��Ԥ�����֤
	    
	private  String    jsydlicence     = "";          //�����õع滮���֤
	    
	private  String    jzgcsglicence   = "";        //��������ʩ�����֤
	
	private  long      creditTypeID      = -1; //���ñ�֤������id
	
	private  long      creditID          = -1;
	
	private  String    creditName        = "";
	
	private  double    creditAmount      = 0;
	
	private  double    useAmount         = 0;
	
	private  double    creditresAmount   = 0;
	
	private  long      depositAccountID  = -1;//��֤���˻�ID
	
	private  long      accountID         = -1;
	
	private  String    accountNo         = "";
	
	private  double    mBalance          = 0;
	
	private  long      clientID          = -1;
	
	private  String    clientName        = "";
	
	private  String    eCode             = "";
	
	private  String    userName          = "";
	
	private  double    countAmount       = 0;//�ۼƵ���Ѻ��
	
	private  List      mationTenanceList = null; 
	
	private  List      associationContractList = null; 
    
	private  LOANAccountReceivableInfo       loanAccountreceivableInfo       = null;//Ӧ���˿�
	
	private  LOANCapitalRatingGeneralInfo    loanCapitalratinggeneralInfo    = null;//�ʲ�������Ҫ
	
	private  LOANGageMaintenanceInfo         loanGagemaintenanceInfo         = null;//����Ʒά����Ϣ
	
	private  LOANBillInfo                    loanBillInfo                    = null;//Ʊ��
	
	private  LOANCarqualiFiedInfo            loanCarqualifiedInfo            = null;//�����ϸ�֤
    
	private  LOANLandHousingWarrantInfo      loanLandHousingWarrantInfo = null;//���С�����
	
	private  LOANNegotiableseCuritiesInfo    loanNegotiablesecuritiesInfo    = null;//��Ʊ����Ȩ
	
	private  long applyID = -1;
	
	private  long assureDesc = -1;
	
	private  double assureAmount = 0;
	
	private  double creditAssureAmount = 0;	
	
	//add by dwj 20090517
	private double modulus = 0.0;
	//end 
	
	public double getAssureAmount() {
		return assureAmount;
	}

	public void setAssureAmount(double assureAmount) {
		this.assureAmount = assureAmount;
	}

	public long getAccountID() {
		return accountID;
	}

	public void setAccountID(long accountID) {
		this.accountID = accountID;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public Timestamp getAppraisDate() {
		return AppraisDate;
	}

	public void setAppraisDate(Timestamp appraisDate) {
		AppraisDate = appraisDate;
	}

	public String getAppraiseUnits() {
		return AppraiseUnits;
	}

	public void setAppraiseUnits(String appraiseUnits) {
		AppraiseUnits = appraiseUnits;
	}

	public double getAppraisValue() {
		return AppraisValue;
	}

	public void setAppraisValue(double appraisValue) {
		AppraisValue = appraisValue;
	}

	public String getBeneficiary() {
		return Beneficiary;
	}

	public void setBeneficiary(String beneficiary) {
		Beneficiary = beneficiary;
	}

	public long getClientID() {
		return clientID;
	}

	public void setClientID(long clientID) {
		this.clientID = clientID;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public double getCountAmount() {
		return countAmount;
	}

	public void setCountAmount(double countAmount) {
		this.countAmount = countAmount;
	}

	public double getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(double creditAmount) {
		this.creditAmount = creditAmount;
	}

	public long getCreditID() {
		return creditID;
	}

	public void setCreditID(long creditID) {
		this.creditID = creditID;
	}

	public String getCreditName() {
		return creditName;
	}

	public void setCreditName(String creditName) {
		this.creditName = creditName;
	}

	public double getCreditresAmount() {
		return creditresAmount;
	}

	public void setCreditresAmount(double creditresAmount) {
		this.creditresAmount = creditresAmount;
	}

	public long getCreditTypeID() {
		return creditTypeID;
	}

	public void setCreditTypeID(long creditTypeID) {
		this.creditTypeID = creditTypeID;
	}

	public long getCurrencyId() {
		return CurrencyId;
	}

	public void setCurrencyId(long currencyId) {
		CurrencyId = currencyId;
	}

	public long getDepositAccountID() {
		return depositAccountID;
	}

	public void setDepositAccountID(long depositAccountID) {
		this.depositAccountID = depositAccountID;
	}

	public String getECode() {
		return eCode;
	}

	public void setECode(String code) {
		eCode = code;
	}

	public Timestamp getEndDate() {
		return EndDate;
	}

	public void setEndDate(Timestamp endDate) {
		EndDate = endDate;
	}

	public long getFeePersonType() {
		return FeePersonType;
	}

	public void setFeePersonType(long feePersonType) {
		FeePersonType = feePersonType;
	}

	public long getFreeUnitSid() {
		return FreeUnitSid;
	}

	public void setFreeUnitSid(long freeUnitSid) {
		FreeUnitSid = freeUnitSid;
	}

	public String getGageCode() {
		return GageCode;
	}

	public void setGageCode(String gageCode) {
		GageCode = gageCode;
	}

	public String getGageDescribing() {
		return GageDescribing;
	}

	public void setGageDescribing(String gageDescribing) {
		GageDescribing = gageDescribing;
	}

	public String getGagueName() {
		return GagueName;
	}

	public void setGagueName(String gagueName) {
		GagueName = gagueName;
	}

	public long getGagueProperty() {
		return GagueProperty;
	}

	public void setGagueProperty(long gagueProperty) {
		GagueProperty = gagueProperty;
	}

	public long getGagueType() {
		return GagueType;
	}

	public void setGagueType(long gagueType) {
		GagueType = gagueType;
	}

	public String getGuaranteeSlipCode() {
		return GuaranteeSlipCode;
	}

	public void setGuaranteeSlipCode(String guaranteeSlipCode) {
		GuaranteeSlipCode = guaranteeSlipCode;
	}

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public Timestamp getInputDate() {
		return InputDate;
	}

	public void setInputDate(Timestamp inputDate) {
		InputDate = inputDate;
	}

	public long getInputUserId() {
		return InputUserId;
	}

	public void setInputUserId(long inputUserId) {
		InputUserId = inputUserId;
	}

	public String getInsurant() {
		return Insurant;
	}

	public void setInsurant(String insurant) {
		Insurant = insurant;
	}

	public double getInsureAmount() {
		return InsureAmount;
	}

	public void setInsureAmount(double insureAmount) {
		InsureAmount = insureAmount;
	}

	public String getInsureOrgName() {
		return InsureOrgName;
	}

	public void setInsureOrgName(String insureOrgName) {
		InsureOrgName = insureOrgName;
	}

	public long getIsNotarization() {
		return IsNotarization;
	}

	public void setIsNotarization(long isNotarization) {
		IsNotarization = isNotarization;
	}

	public LOANAccountReceivableInfo getLoanAccountreceivableInfo() {
		
		if(loanAccountreceivableInfo == null)loanAccountreceivableInfo = new LOANAccountReceivableInfo();
		return loanAccountreceivableInfo;
	}

	public void setLoanAccountreceivableInfo(LOANAccountReceivableInfo loanAccountreceivableInfo) {
		this.loanAccountreceivableInfo = loanAccountreceivableInfo;
	}

	public LOANBillInfo getLoanBillInfo() {
		if(loanBillInfo == null)loanBillInfo = new LOANBillInfo();
		return loanBillInfo;
	}

	public void setLoanBillInfo(LOANBillInfo loanBillInfo) {
		this.loanBillInfo = loanBillInfo;
	}

	public LOANCapitalRatingGeneralInfo getLoanCapitalratinggeneralInfo() {
		
		if(loanCapitalratinggeneralInfo == null)loanCapitalratinggeneralInfo = new LOANCapitalRatingGeneralInfo();
		return loanCapitalratinggeneralInfo;
	}

	public void setLoanCapitalratinggeneralInfo(
			LOANCapitalRatingGeneralInfo loanCapitalratinggeneralInfo) {
		this.loanCapitalratinggeneralInfo = loanCapitalratinggeneralInfo;
	}

	public LOANCarqualiFiedInfo getLoanCarqualifiedInfo() {
		
		if(loanCarqualifiedInfo == null)loanCarqualifiedInfo = new LOANCarqualiFiedInfo();
		return loanCarqualifiedInfo;
	}

	public void setLoanCarqualifiedInfo(LOANCarqualiFiedInfo loanCarqualifiedInfo) {
		this.loanCarqualifiedInfo = loanCarqualifiedInfo;
	}

	public LOANGageMaintenanceInfo getLoanGagemaintenanceInfo() {
		
		if(loanGagemaintenanceInfo == null)loanGagemaintenanceInfo = new LOANGageMaintenanceInfo();
		return loanGagemaintenanceInfo;
	}

	public void setLoanGagemaintenanceInfo(LOANGageMaintenanceInfo loanGagemaintenanceInfo) {
		this.loanGagemaintenanceInfo = loanGagemaintenanceInfo;
	}



	public LOANNegotiableseCuritiesInfo getLoanNegotiablesecuritiesInfo() {
		
		if(loanNegotiablesecuritiesInfo == null)loanNegotiablesecuritiesInfo = new LOANNegotiableseCuritiesInfo();
		return loanNegotiablesecuritiesInfo;
	}

	public void setLoanNegotiablesecuritiesInfo(LOANNegotiableseCuritiesInfo loanNegotiablesecuritiesInfo) {
		this.loanNegotiablesecuritiesInfo = loanNegotiablesecuritiesInfo;
	}


	public List getMationTenanceList() {
		return mationTenanceList;
	}

	public void setMationTenanceList(List mationTenanceList) {
		this.mationTenanceList = mationTenanceList;
	}

	public double getMBalance() {
		return mBalance;
	}

	public void setMBalance(double balance) {
		mBalance = balance;
	}

	public long getOfficeId() {
		return OfficeId;
	}

	public void setOfficeId(long officeId) {
		OfficeId = officeId;
	}

	public String getPawneePerson() {
		return PawneePerson;
	}

	public void setPawneePerson(String pawneePerson) {
		PawneePerson = pawneePerson;
	}

	public double getPledgeAmount() {
		return PledgeAmount;
	}

	public void setPledgeAmount(double pledgeAmount) {
		PledgeAmount = pledgeAmount;
	}

	public double getRatio() {
		return Ratio;
	}

	public void setRatio(double ratio) {
		Ratio = ratio;
	}

	public double getResidueAmount() {
		return ResidueAmount;
	}

	public void setResidueAmount(double residueAmount) {
		ResidueAmount = residueAmount;
	}

	public Timestamp getStartDate() {
		return StartDate;
	}

	public void setStartDate(Timestamp startDate) {
		StartDate = startDate;
	}

	public long getStatus() {
		return Status;
	}

	public void setStatus(long status) {
		Status = status;
	}

	public double getUseAmount() {
		return useAmount;
	}

	public void setUseAmount(double useAmount) {
		this.useAmount = useAmount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGytdlicence() {
		return gytdlicence;
	}

	public void setGytdlicence(String gytdlicence) {
		this.gytdlicence = gytdlicence;
	}

	public String getJsgcghlicence() {
		return jsgcghlicence;
	}

	public void setJsgcghlicence(String jsgcghlicence) {
		this.jsgcghlicence = jsgcghlicence;
	}

	public String getJsydlicence() {
		return jsydlicence;
	}

	public void setJsydlicence(String jsydlicence) {
		this.jsydlicence = jsydlicence;
	}

	public String getJzgcsglicence() {
		return jzgcsglicence;
	}

	public void setJzgcsglicence(String jzgcsglicence) {
		this.jzgcsglicence = jzgcsglicence;
	}

	public String getSpfyslicence() {
		return spfyslicence;
	}

	public void setSpfyslicence(String spfyslicence) {
		this.spfyslicence = spfyslicence;
	}

	public LOANLandHousingWarrantInfo getLoanLandHousingWarrantInfo() {
		
		if(loanLandHousingWarrantInfo == null)loanLandHousingWarrantInfo = new LOANLandHousingWarrantInfo();
		return loanLandHousingWarrantInfo;
		
	}

	public void setLoanLandHousingWarrantInfo(LOANLandHousingWarrantInfo loanLandHousingWarrantInfo) {
		this.loanLandHousingWarrantInfo = loanLandHousingWarrantInfo;
	}

	public List getAssociationContractList() {
		return associationContractList;
	}

	public void setAssociationContractList(List associationContractList) {
		this.associationContractList = associationContractList;
	}

	public long getApplyID() {
		return applyID;
	}

	public void setApplyID(long applyID) {
		this.applyID = applyID;
	}

	public long getAssureDesc() {
		return assureDesc;
	}

	public void setAssureDesc(long assureDesc) {
		this.assureDesc = assureDesc;
	}

	public double getModulus() {
		return modulus;
	}

	public void setModulus(double modulus) {
		this.modulus = modulus;
	}

	public double getCreditAssureAmount() {
		return creditAssureAmount;
	}

	public void setCreditAssureAmount(double creditAssureAmount) {
		this.creditAssureAmount = creditAssureAmount;
	}



}
















