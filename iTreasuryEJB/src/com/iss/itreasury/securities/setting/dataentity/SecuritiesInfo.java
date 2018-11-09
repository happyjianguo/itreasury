/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author              kewen hu 
 * @version
 * Date of Creation     2004-03-18
 */
package com.iss.itreasury.securities.setting.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.securities.util.SECBaseDataEntity;

public class SecuritiesInfo extends SECBaseDataEntity {
	//֤ȯID	ID	Number	��	Y
	private long id = -1;
	//֤ȯ���	ShortName	Varchar2(100)		K
	private String ShortName = "";
	//֤ȯȫ��	Name	Varchar2(100)		K
	private String Name = "";
	//֤ȯ���ID	TypeID	Number	��	��	����֤ȯ�������
	private long TypeID = -1;
	//֤ȯ�����	SubType	Varchar2(100) ��Թ�Ʊ�����ȹɡ���ͨ�ɡ�����
	private String SubType = "";
	//����֤ȯ�г�ID	ExchangeCenterID	Number	��	��	����֤��������
	private long ExchangeCenterID = -1;
	//���ױ���	CurrencyID	Number	��	��	����
	private long CurrencyID = -1;
	//֤ȯ����(�׷�)	SecuritiesCode1	Varchar2(100) IPO����ʱ�Ĵ��롣���������ֻ���ĸ��
	private String SecuritiesCode1 = "";
	//֤ȯ����(����)	SecuritiesCode2	Varchar2(100)	��	��	����ʱ�Ĵ��롣���������ֻ���ĸ��
	private String SecuritiesCode2 = "";
	//֤ȯ����(����/���)	SecuritiesCode3	Varchar2(100)	��	��	��Ʊ����/���ʱʹ�õ�֤ȯ����
	private String SecuritiesCode3 = "";
	//֤ȯ���루������	SecuritiesCode4	Varchar2(100)	��	��	����
	private String SecuritiesCode4 = "";
	//��ֵ	ParValue	Number��20,2��	��	��	���ծȯ������
	private double ParValue = 0.0;
	//Ʊ������%	InterestRate	Number(17,12)	��	��	���ծȯ
	private double InterestRate = 0.0;
	//��Ϣ��	ValueDate	DATE	��	��	���ծȯ
	private Timestamp ValueDate = null;
	//������	MaturityDate	DATE	��	��	���ծȯ������
	private Timestamp MaturityDate = null;
	//����	Term	Number	��	��	�������֡���Ʊ��������ծȯ������ָ���޻ع���ָ�ûع�Ʒ�ֵĻع�����
	private double Term = 0.0;
	//��������	TermType	Varchar2(5)	��	��	Y=�ꡢM=�¡�D=��
	private String TermType = "";
	//��Ϣ����	Interestcycle	Varchar2(100)	��	��	���ծȯ������
	private String Interestcycle = "";
	//��Ϣ��	InterestDate	Varchar2(100)	��	��	���ծȯ
	private String InterestDate = "";
	//���������ɡ��ݡ��ţ�	Quantity	Number	��	��	��Ʊָ�ܹɱ���ծȯָ������������ָ�ܷݶ
	private long Quantity = -1;
	//��ͨ�������ɡ��ݡ��ţ�	QuantityCirculation	Number	��	��	��Ʊָ��ͨ�ɱ�������ָ��ͨ�ݶ
	private long QuantityCirculation = -1;
	//��ͨ���
	private String circulationSituation = "";
    //�����������ɡ��ݡ��ţ�	QuantityIssued	Number	��	��	��ƱָIPO�ܹɱ���ծȯָ������������ָIPO�ܷݶ		
	private long QuantityIssued = -1;
	//��������	IssueStartDate	DATE	��	��	��ʼ���е�����
	private Timestamp IssueStartDate = null;
	//����ֹ��	IssueEndDate	DATE	��	��	�������е�����
	private Timestamp IssueEndDate = null;
	//���м�	IssuePrice	Number(23,8)	��	��	��
	private double IssuePrice = 0.0;
	//���з�ʽ	IssueWay	Varchar2(100)	��	��	��
	private String IssueWay = "";
	//��ǩ����	DrawnDate	DATE	��	��	��
	private Timestamp DrawnDate = null;
	//��ǩ��%	DrawnRate	Number(17,12)	��	��	��
	private double DrawnRate = 0.0;
	//������ӯ��	IssueEarningsYield	Number(17,12)	��	��	��Թ�Ʊ
	private double IssueEarningsYield = 0.0;
	//��������	IssueUnderwriter	Varchar2(100)	��	��	��
	private String IssueUnderwriter = "";
	//�����Ƽ���	ListingRecommendation	Varchar2(100)	��	��	��
	private String ListingRecommendation = "";
	//��������	ListingDate	DATE	��	��	��
	private Timestamp ListingDate = null;
	//�����Ƿ�%	ListingMarkupRate	Number(17,12)	��	��	��
	private double ListingMarkupRate = 0.0;
	//�깺������%	IPOYieldRate	Number(17,12)	��	��	��
	private double IPOYieldRate = 0.0;
	//������	FundManager	Varchar2(100)	��	��	��Ի���
	private long FundManager = 0;
	//����/������	Promotor	Varchar2(100)	��	��	��Ի���ծȯ
	private String Promotor = "";
	//�й���	Trustee	Varchar2(100)	��	��	��Ի���
	private String Trustee = "";
	//��������	EstablishDate	DATE	��	��	��Ի���
	private Timestamp EstablishDate = null;
	//������	Manager	Varchar2(100)	��	��	��Ի���
	private String Manager = "";
	//�깺������	BuyDelivery	Number	��	��	��Ի���T+N
	private long BuyDelivery = -1;
	//��ػ�����	RedeemDelivery	Number	��	��	��Ի���T+N
	private long RedeemDelivery = -1;
	//��ʼת�ɼ�	InitialTransfersPrice	Number(23,8)	��	��	��Կ�תծ��
	private double InitialTransfersPrice = 0.0;
	//��ؼ۸�	RedeemPrice	Number(23,8)	��	��	��Կ�תծ��
	private double RedeemPrice = 0.0;
	//���ۼ۸�	BuyBackPrice	Number(23,8)	��	��	��Կ�תծ��
	private double BuyBackPrice = 0.0;
	//ת������	TransfersTerm	Varchar2(100)	��	��	��Թ�Ʊ����תծ�ȡ�
	private String TransfersTerm = "";
	//ת�ɴ���	TransfersCode	Varchar2(100)	��	��	��Թ�Ʊ����תծ�ȡ�תΪ����һ���¹�Ʊ�Ĵ���
	private String TransfersCode = "";
	//��������	AmendmentClause	Varchar2(1000)
	private String AmendmentClause = "";
	//�ع���Ѻ����%	PledgeRate	Number(17,12)	��	��	��Թ�ծ
	private double PledgeRate = 0.0;
	//��������	WithdrawalShareDate	DATE
	private Timestamp WithdrawalShareDate = null;
	//��ע	Remark	Varchar2(1000)
	private String Remark = "";
	//״̬	StatusID	Number	��	��	0=��ɾ����2=�ѱ��桢3=�Ѹ���
	private long StatusID = -1;
	//¼����	InputUserID	Number	��	��	��
	private long InputUserID = -1;
	//¼��ʱ��	InputDate	DATE
	private Timestamp InputDate = null;
	//�޸���	UpdateUserID	Number
	private long UpdateUserID = -1;
	//�޸�ʱ��	UpdateDate	DATE
	private Timestamp UpdateDate = null;
	//������	CheckUserID	Number
	private long CheckUserID = -1;
	//����ʱ��	CheckDate	DATE
	private Timestamp CheckDate = null;
	/**
	 * @return Returns the amendmentClause.
	 */
	public String getAmendmentClause() {
		return AmendmentClause;
	}
	/**
	 * @param amendmentClause The amendmentClause to set.
	 */
	public void setAmendmentClause(String amendmentClause) {
		putUsedField("amendmentClause", amendmentClause);
		AmendmentClause = amendmentClause;
	}
	/**
	 * @return Returns the buyBackPrice.
	 */
	public double getBuyBackPrice() {
		return BuyBackPrice;
	}
	/**
	 * @param buyBackPrice The buyBackPrice to set.
	 */
	public void setBuyBackPrice(double buyBackPrice) {
		putUsedField("buyBackPrice", buyBackPrice);
		BuyBackPrice = buyBackPrice;
	}
	/**
	 * @return Returns the buyDelivery.
	 */
	public long getBuyDelivery() {
		return BuyDelivery;
	}
	/**
	 * @param buyDelivery The buyDelivery to set.
	 */
	public void setBuyDelivery(long buyDelivery) {
		putUsedField("buyDelivery", buyDelivery);
		BuyDelivery = buyDelivery;
	}
	/**
	 * @return Returns the checkDate.
	 */
	public Timestamp getCheckDate() {
		return CheckDate;
	}
	/**
	 * @param checkDate The checkDate to set.
	 */
	public void setCheckDate(Timestamp checkDate) {
		putUsedField("checkDate", checkDate);
		CheckDate = checkDate;
	}
	/**
	 * @return Returns the checkUserID.
	 */
	public long getCheckUserID() {
		return CheckUserID;
	}
	/**
	 * @param checkUserID The checkUserID to set.
	 */
	public void setCheckUserID(long checkUserID) {
		putUsedField("checkUserID", checkUserID);
		CheckUserID = checkUserID;
	}
	/**
	 * @return Returns the currencyID.
	 */
	public long getCurrencyID() {
		return CurrencyID;
	}
	/**
	 * @param currencyID The currencyID to set.
	 */
	public void setCurrencyID(long currencyID) {
		putUsedField("currencyID", currencyID);
		CurrencyID = currencyID;
	}
	/**
	 * @return Returns the drawnDate.
	 */
	public Timestamp getDrawnDate() {
		return DrawnDate;
	}
	/**
	 * @param drawnDate The drawnDate to set.
	 */
	public void setDrawnDate(Timestamp drawnDate) {
		putUsedField("drawnDate", drawnDate);
		DrawnDate = drawnDate;
	}
	/**
	 * @return Returns the drawnRate.
	 */
	public double getDrawnRate() {
		return DrawnRate;
	}
	/**
	 * @param drawnRate The drawnRate to set.
	 */
	public void setDrawnRate(double drawnRate) {
		putUsedField("drawnRate", drawnRate);
		DrawnRate = drawnRate;
	}
	/**
	 * @return Returns the establishDate.
	 */
	public Timestamp getEstablishDate() {
		return EstablishDate;
	}
	/**
	 * @param establishDate The establishDate to set.
	 */
	public void setEstablishDate(Timestamp establishDate) {
		putUsedField("establishDate", establishDate);
		EstablishDate = establishDate;
	}
	/**
	 * @return Returns the exchangeCenterID.
	 */
	public long getExchangeCenterID() {
		return ExchangeCenterID;
	}
	/**
	 * @param exchangeCenterID The exchangeCenterID to set.
	 */
	public void setExchangeCenterID(long exchangeCenterID) {
		putUsedField("exchangeCenterID", exchangeCenterID);
		ExchangeCenterID = exchangeCenterID;
	}
	/**
	 * @return Returns the fundManager.
	 */
	public long getFundManager() {
		return FundManager;
	}
	/**  
	 * @param fundManager The fundManager to set.
	 */
	public void setFundManager(long fundManager) {
		putUsedField("fundManager", fundManager);
		FundManager = fundManager;
	}
	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		putUsedField("id", id);
		this.id = id;
	}
	/**
	 * @return Returns the initialTransfersPrice.
	 */
	public double getInitialTransfersPrice() {
		return InitialTransfersPrice;
	}
	/**
	 * @param initialTransfersPrice The initialTransfersPrice to set.
	 */
	public void setInitialTransfersPrice(double initialTransfersPrice) {
		putUsedField("initialTransfersPrice", initialTransfersPrice);
		InitialTransfersPrice = initialTransfersPrice;
	}
	/**
	 * @return Returns the inputDate.
	 */
	public Timestamp getInputDate() {
		return InputDate;
	}
	/**
	 * @param inputDate The inputDate to set.
	 */
	public void setInputDate(Timestamp inputDate) {
		putUsedField("inputDate", inputDate);
		InputDate = inputDate;
	}
	/**
	 * @return Returns the inputUserID.
	 */
	public long getInputUserID() {
		return InputUserID;
	}
	/**
	 * @param inputUserID The inputUserID to set.
	 */
	public void setInputUserID(long inputUserID) {
		putUsedField("inputUserID", inputUserID);
		InputUserID = inputUserID;
	}
	/**
	 * @return Returns the interestcycle.
	 */
	public String getInterestcycle() {
		return Interestcycle;
	} 
	/**
	 * @param interestcycle The interestcycle to set.
	 */
	public void setInterestcycle(String interestcycle) {
		putUsedField("interestcycle", interestcycle);
		Interestcycle = interestcycle;
	}
	/**
	 * @return Returns the interestDate.
	 */
	public String getInterestDate() {
		return InterestDate;
	}
	/**
	 * @param interestDate The interestDate to set.
	 */
	public void setInterestDate(String interestDate) {
		putUsedField("interestDate", interestDate);
		InterestDate = interestDate;
	}
	/**
	 * @return Returns the interestRate.
	 */
	public double getInterestRate() {
		return InterestRate;
	}
	/**
	 * @param interestRate The interestRate to set.
	 */
	public void setInterestRate(double interestRate) {
		putUsedField("interestRate", interestRate);
		InterestRate = interestRate;
	}
	/**
	 * @return Returns the iPOYieldRate.
	 */
	public double getIPOYieldRate() {
		return IPOYieldRate;
	}
	/**
	 * @param yieldRate The iPOYieldRate to set.
	 */
	public void setIPOYieldRate(double yieldRate) {
		putUsedField("yieldRate", yieldRate);
		IPOYieldRate = yieldRate;
	}
	/**
	 * @return Returns the issueEarningsYield.
	 */
	public double getIssueEarningsYield() {
		return IssueEarningsYield;
	}
	/**
	 * @param issueEarningsYield The issueEarningsYield to set.
	 */
	public void setIssueEarningsYield(double issueEarningsYield) {
		putUsedField("issueEarningsYield", issueEarningsYield);
		IssueEarningsYield = issueEarningsYield;
	}
	/**
	 * @return Returns the issueEndDate.
	 */
	public Timestamp getIssueEndDate() {
		return IssueEndDate;
	}
	/**
	 * @param issueEndDate The issueEndDate to set.
	 */
	public void setIssueEndDate(Timestamp issueEndDate) {
		putUsedField("issueEndDate", issueEndDate);
		IssueEndDate = issueEndDate;
	}
	/**
	 * @return Returns the issuePrice.
	 */
	public double getIssuePrice() {
		return IssuePrice;
	}
	/**
	 * @param issuePrice The issuePrice to set.
	 */
	public void setIssuePrice(double issuePrice) {
		putUsedField("issuePrice", issuePrice);
		IssuePrice = issuePrice;
	}
	/**
	 * @return Returns the issueStartDate.
	 */
	public Timestamp getIssueStartDate() {
		return IssueStartDate;
	}
	/**
	 * @param issueStartDate The issueStartDate to set.
	 */
	public void setIssueStartDate(Timestamp issueStartDate) {
		putUsedField("issueStartDate", issueStartDate);
		IssueStartDate = issueStartDate;
	}
	/**
	 * @return Returns the issueUnderwriter.
	 */
	public String getIssueUnderwriter() {
		return IssueUnderwriter;
	}
	/**
	 * @param issueUnderwriter The issueUnderwriter to set.
	 */
	public void setIssueUnderwriter(String issueUnderwriter) {
		putUsedField("issueUnderwriter", issueUnderwriter);
		IssueUnderwriter = issueUnderwriter;
	}
	/**
	 * @return Returns the issueWay.
	 */
	public String getIssueWay() {
		return IssueWay;
	}
	/**
	 * @param issueWay The issueWay to set.
	 */
	public void setIssueWay(String issueWay) {
		putUsedField("issueWay", issueWay);
		IssueWay = issueWay;
	}
	/**
	 * @return Returns the listingDate.
	 */
	public Timestamp getListingDate() {
		return ListingDate;
	}
	/**
	 * @param listingDate The listingDate to set.
	 */
	public void setListingDate(Timestamp listingDate) {
		putUsedField("listingDate", listingDate);
		ListingDate = listingDate;
	}
	/**
	 * @return Returns the listingMarkupRate.
	 */
	public double getListingMarkupRate() {
		return ListingMarkupRate;
	}
	/**
	 * @param listingMarkupRate The listingMarkupRate to set.
	 */
	public void setListingMarkupRate(double listingMarkupRate) {
		putUsedField("listingMarkupRate", listingMarkupRate);
		ListingMarkupRate = listingMarkupRate;
	}
	/**
	 * @return Returns the listingRecommendation.
	 */
	public String getListingRecommendation() {
		return ListingRecommendation;
	}
	/**
	 * @param listingRecommendation The listingRecommendation to set.
	 */
	public void setListingRecommendation(String listingRecommendation) {
		putUsedField("listingRecommendation", listingRecommendation);
		ListingRecommendation = listingRecommendation;
	}
	/**
	 * @return Returns the manager.
	 */
	public String getManager() {
		return Manager;
	}
	/**
	 * @param manager The manager to set.
	 */
	public void setManager(String manager) {
		putUsedField("manager", manager);
		Manager = manager;
	}
	/**
	 * @return Returns the maturityDate.
	 */
	public Timestamp getMaturityDate() {
		return MaturityDate;
	}
	/**
	 * @param maturityDate The maturityDate to set.
	 */
	public void setMaturityDate(Timestamp maturityDate) {
		putUsedField("maturityDate", maturityDate);
		MaturityDate = maturityDate;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return Name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		putUsedField("name", name);
		Name = name;
	}
	/**
	 * @return Returns the parValue.
	 */
	public double getParValue() {
		return ParValue;
	}
	/**
	 * @param parValue The parValue to set.
	 */
	public void setParValue(double parValue) {
		putUsedField("parValue", parValue);
		ParValue = parValue;
	}
	/**
	 * @return Returns the pledgeRate.
	 */
	public double getPledgeRate() {
		return PledgeRate;
	}
	/**
	 * @param pledgeRate The pledgeRate to set.
	 */
	public void setPledgeRate(double pledgeRate) {
		putUsedField("pledgeRate", pledgeRate);
		PledgeRate = pledgeRate;
	}
	/**
	 * @return Returns the promotor.
	 */
	public String getPromotor() {
		return Promotor;
	}
	/**
	 * @param promotor The promotor to set.
	 */
	public void setPromotor(String promotor) {
		putUsedField("promotor", promotor);
		Promotor = promotor;
	}
	/**
	 * @return Returns the quantity.
	 */
	public long getQuantity() {
		return Quantity;
	}
	/**
	 * @param quantity The quantity to set.
	 */
	public void setQuantity(long quantity) {
		putUsedField("quantity", quantity);
		Quantity = quantity;
	}
	/**
	 * @return Returns the quantityCirculation.
	 */
	public long getQuantityCirculation() {
		return QuantityCirculation;
	}
	/**
	 * @param quantityCirculation The quantityCirculation to set.
	 */
	public void setQuantityCirculation(long quantityCirculation) {
		putUsedField("quantityCirculation", quantityCirculation);
		QuantityCirculation = quantityCirculation;
	}
	
	public String getCirculationSituation(){
	  return circulationSituation;	
	} 
	public void setCirculationSituation(String circulationSituation){
	 putUsedField("circulationSituation",circulationSituation);
	 this.circulationSituation = circulationSituation;
	}
	/**
	 * @return Returns the quantityIssued.
	 */
	public long getQuantityIssued() {
		return QuantityIssued;
	}
	/**
	 * @param quantityIssued The quantityIssued to set.
	 */
	public void setQuantityIssued(long quantityIssued) {
		putUsedField("quantityIssued", quantityIssued);
		QuantityIssued = quantityIssued;
	}
	/**
	 * @return Returns the redeemDelivery.
	 */
	public long getRedeemDelivery() {
		return RedeemDelivery;
	}
	/**
	 * @param redeemDelivery The redeemDelivery to set.
	 */
	public void setRedeemDelivery(long redeemDelivery) {
		putUsedField("redeemDelivery", redeemDelivery);
		RedeemDelivery = redeemDelivery;
	}
	/**
	 * @return Returns the redeemPrice.
	 */
	public double getRedeemPrice() {
		return RedeemPrice;
	}
	/**
	 * @param redeemPrice The redeemPrice to set.
	 */
	public void setRedeemPrice(double redeemPrice) {
		putUsedField("redeemPrice", redeemPrice);
		RedeemPrice = redeemPrice;
	}
	/**
	 * @return Returns the remark.
	 */
	public String getRemark() {
		return Remark;
	}
	/**
	 * @param remark The remark to set.
	 */
	public void setRemark(String remark) {
		putUsedField("remark", remark);
		Remark = remark;
	}
	/**
	 * @return Returns the securitiesCode1.
	 */
	public String getSecuritiesCode1() {
		return SecuritiesCode1;
	}
	/**
	 * @param securitiesCode1 The securitiesCode1 to set.
	 */
	public void setSecuritiesCode1(String securitiesCode1) {
		putUsedField("securitiesCode1", securitiesCode1);
		SecuritiesCode1 = securitiesCode1;
	}
	/**
	 * @return Returns the securitiesCode2.
	 */
	public String getSecuritiesCode2() {
		return SecuritiesCode2;
	}
	/**
	 * @param securitiesCode2 The securitiesCode2 to set.
	 */
	public void setSecuritiesCode2(String securitiesCode2) {
		putUsedField("securitiesCode2", securitiesCode2);
		SecuritiesCode2 = securitiesCode2;
	}
	/**
	 * @return Returns the securitiesCode3.
	 */
	public String getSecuritiesCode3() {
		return SecuritiesCode3;
	}
	/**
	 * @param securitiesCode3 The securitiesCode3 to set.
	 */
	public void setSecuritiesCode3(String securitiesCode3) {
		putUsedField("securitiesCode3", securitiesCode3);
		SecuritiesCode3 = securitiesCode3;
	}
	/**
	 * @return Returns the securitiesCode4.
	 */
	public String getSecuritiesCode4() {
		return SecuritiesCode4;
	}
	/**
	 * @param securitiesCode4 The securitiesCode4 to set.
	 */
	public void setSecuritiesCode4(String securitiesCode4) {
		putUsedField("securitiesCode4", securitiesCode4);
		SecuritiesCode4 = securitiesCode4;
	}
	/**
	 * @return Returns the shortName.
	 */
	public String getShortName() {
		return ShortName;
	}
	/**
	 * @param shortName The shortName to set.
	 */
	public void setShortName(String shortName) {
		putUsedField("shortName", shortName);
		ShortName = shortName;
	}
	/**
	 * @return Returns the statusID.
	 */
	public long getStatusID() {
		return StatusID;
	}
	/**
	 * @param statusID The statusID to set.
	 */
	public void setStatusID(long statusID) {
		putUsedField("statusID", statusID);
		StatusID = statusID;
	}
	/**
	 * @return Returns the subType.
	 */
	public String getSubType() {
		return SubType;
	}
	/**
	 * @param subType The subType to set.
	 */
	public void setSubType(String subType) {
		putUsedField("subType", subType);
		SubType = subType;
	}
	/**
	 * @return Returns the term.
	 */
	public double getTerm() {
		return Term;
	}
	/**
	 * @param term The term to set.
	 */
	public void setTerm(double term) {
		putUsedField("term", term);
		Term = term;
	}
	/**
	 * @return Returns the termType.
	 */
	public String getTermType() {
		return TermType;
	}
	/**
	 * @param termType The termType to set.
	 */
	public void setTermType(String termType) {
		putUsedField("termType", termType);
		TermType = termType;
	}
	/**
	 * @return Returns the transfersCode.
	 */
	public String getTransfersCode() {
		return TransfersCode;
	}
	/**
	 * @param transfersCode The transfersCode to set.
	 */
	public void setTransfersCode(String transfersCode) {
		putUsedField("transfersCode", transfersCode);
		TransfersCode = transfersCode;
	}
	/**
	 * @return Returns the transfersTerm.
	 */
	public String getTransfersTerm() {
		return TransfersTerm;
	}
	/**
	 * @param transfersTerm The transfersTerm to set.
	 */
	public void setTransfersTerm(String transfersTerm) {
		putUsedField("transfersTerm", transfersTerm);
		TransfersTerm = transfersTerm;
	}
	/**
	 * @return Returns the trustee.
	 */
	public String getTrustee() {
		return Trustee;
	}
	/**
	 * @param trustee The trustee to set.
	 */
	public void setTrustee(String trustee) {
		putUsedField("trustee", trustee);
		Trustee = trustee;
	}
	/**
	 * @return Returns the typeID.
	 */
	public long getTypeID() {
		return TypeID;
	}
	/**
	 * @param typeID The typeID to set.
	 */
	public void setTypeID(long typeID) {
		putUsedField("typeID", typeID);
		TypeID = typeID;
	}
	/**
	 * @return Returns the updateDate.
	 */
	public Timestamp getUpdateDate() {
		return UpdateDate;
	}
	/**
	 * @param updateDate The updateDate to set.
	 */
	public void setUpdateDate(Timestamp updateDate) {
		putUsedField("updateDate", updateDate);
		UpdateDate = updateDate;
	}
	/**
	 * @return Returns the updateUserID.
	 */
	public long getUpdateUserID() {
		return UpdateUserID;
	}
	/**
	 * @param updateUserID The updateUserID to set.
	 */
	public void setUpdateUserID(long updateUserID) {
		putUsedField("updateUserID", updateUserID);
		UpdateUserID = updateUserID;
	}
	/**
	 * @return Returns the valueDate.
	 */
	public Timestamp getValueDate() {
		return ValueDate;
	}
	/**
	 * @param valueDate The valueDate to set.
	 */
	public void setValueDate(Timestamp valueDate) {
		putUsedField("valueDate", valueDate);
		ValueDate = valueDate;
	}
	/**
	 * @return Returns the withdrawalShareDate.
	 */
	public Timestamp getWithdrawalShareDate() {
		return WithdrawalShareDate;
	}
	/**
	 * @param withdrawalShareDate The withdrawalShareDate to set.
	 */
	public void setWithdrawalShareDate(Timestamp withdrawalShareDate) {
		putUsedField("withdrawalShareDate", withdrawalShareDate);
		WithdrawalShareDate = withdrawalShareDate;
	}
}