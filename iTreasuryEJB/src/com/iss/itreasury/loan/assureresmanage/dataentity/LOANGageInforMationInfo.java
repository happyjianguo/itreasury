package com.iss.itreasury.loan.assureresmanage.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class LOANGageInforMationInfo implements Serializable {

	private  long      Id             = -1; // ID

	private  long      CurrencyId     = -1; // 币种

	private  long      OfficeId       = -1; // 办事处

	private  long      FreeUnitSid    = -1; // 所有权人单位ID

	private  long      FeePersonType  = -1; // 所有权人信息选择单位

	private  long      InputUserId    = -1; // 录入人

	private  Timestamp InputDate      = null; // 录入时间

	private  long      Status         = -1; // 状态

	private  String    GageCode       = ""; // 担保品编号

	private  long      GagueProperty  = -1; // 担保品属性

	private  String    GagueName      = ""; // 担保品名称

	private  long      GagueType      = -1; // 担保品类别

	private  String    PawneePerson   = ""; // 他项权益人/质权人

	private  String    GageDescribing = ""; // 担保品描述
	
	//----------------------保险价值-------------------
	
	private  String    AppraiseUnits     = ""; // 评估单位

	private  double    AppraisValue      = 0; // 评估价值

	private  double    PledgeAmount      = 0; // 实际抵质押额（权利价值）

	private  double    ResidueAmount     = 0; // 剩余抵质押额

	private  long      IsNotarization    = -1; // 是否公证

	private  Timestamp AppraisDate       = null; // 评估时间

	private  double    Ratio             = 0; // 折价率

	private  String    InsureOrgName     = ""; // 保险公司名称

	private  double    InsureAmount      = 0; // 保险金额

	private  Timestamp StartDate         = null; // 保险期限从

	private  Timestamp EndDate           = null; // 保险期限到

	private  String    Insurant          = ""; // 被保险人
 
	private  String    GuaranteeSlipCode = ""; // 保单批单编号

	private  String    Beneficiary       = ""; // 受益人
	  //-------------------------------------------
	
	private  String    gytdlicence     = "";          //国有土地使用证
	    
	private  String    jsgcghlicence   = "";        //建设工程规划许可证
	    
	private  String    spfyslicence    = "";         //商品房预售许可证
	    
	private  String    jsydlicence     = "";          //建设用地规划许可证
	    
	private  String    jzgcsglicence   = "";        //建筑工程施工许可证
	
	private  long      creditTypeID      = -1; //信用保证授信项id
	
	private  long      creditID          = -1;
	
	private  String    creditName        = "";
	
	private  double    creditAmount      = 0;
	
	private  double    useAmount         = 0;
	
	private  double    creditresAmount   = 0;
	
	private  long      depositAccountID  = -1;//保证金账户ID
	
	private  long      accountID         = -1;
	
	private  String    accountNo         = "";
	
	private  double    mBalance          = 0;
	
	private  long      clientID          = -1;
	
	private  String    clientName        = "";
	
	private  String    eCode             = "";
	
	private  String    userName          = "";
	
	private  double    countAmount       = 0;//累计抵制押额
	
	private  List      mationTenanceList = null; 
	
	private  List      associationContractList = null; 
    
	private  LOANAccountReceivableInfo       loanAccountreceivableInfo       = null;//应收账款
	
	private  LOANCapitalRatingGeneralInfo    loanCapitalratinggeneralInfo    = null;//资产评估概要
	
	private  LOANGageMaintenanceInfo         loanGagemaintenanceInfo         = null;//担保品维护信息
	
	private  LOANBillInfo                    loanBillInfo                    = null;//票据
	
	private  LOANCarqualiFiedInfo            loanCarqualifiedInfo            = null;//汽车合格证
    
	private  LOANLandHousingWarrantInfo      loanLandHousingWarrantInfo = null;//国有、房屋
	
	private  LOANNegotiableseCuritiesInfo    loanNegotiablesecuritiesInfo    = null;//股票、股权
	
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
















