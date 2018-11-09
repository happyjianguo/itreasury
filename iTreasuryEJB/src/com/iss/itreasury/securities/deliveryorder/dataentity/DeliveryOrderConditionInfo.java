package com.iss.itreasury.securities.deliveryorder.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.securities.util.SECBaseDataEntity;

/**
 * @author yuxu
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class DeliveryOrderConditionInfo extends SECBaseDataEntity implements Serializable
{
	long id							= -1;		//Id
	long currencyId					= -1;		//����Id
	long officeId					= -1;		//���´�Id
	long secTransactionTypeId 		= -1;		//��������
	
	String secApplyFormInputDateFrom= "";		//������¼������(��)
	String secApplyFormInputDateTo	= "";		//������¼������(��)
	String secApplyFormStatusId		= "";		//������״̬
	
	String secTransactionDateFrom	= "";		//�ɽ�����(��)
	String secTransactionDateTo		= "";		//�ɽ�����(��)
	String secSystemTransactionCode = "";		//ϵͳ�ɽ����
	long secClientId				= -1;		//ҵ��λID
	long secCounterpartId			= -1;		//���׶���ID
	
	long secRelatedDeliveryOrderID = -1;		//��ǩ��������ϵ���깺���ID
	
	double secPledgeSecuritiesAmountFrom= 0.0;		//�ʽ�(��)
	double secPledgeSecuritiesAmountTo= 0.0;		//�ʽ�(��)
	double secAmountFrom 			= 0.0;		//�ʽ�(��)
	double secAmountTo				= 0.0;		//�ʽ�(��)

	double secPriceFrom 			= 0.0;		//�ɽ��۸�(��)
	double secPriceTo				= 0.0;		//�ɽ��۸�(��)

	double secNetPriceFrom 			= 0.0;		//��ֵ(��)
	double secNetPriceTo			= 0.0;		//��ֵ(��)

	double secQuantityFrom 			= 0.0;		//�ɽ�����(��)
	double secQuantityTo			= 0.0;		//�ɽ�����(��)

	double secOppositeQuantityFrom 			= 0.0;		//ת�ɹ�Ʊ�ɽ�����(��)
	double secOppositeQuantityTo			= 0.0;		//ת�ɹ�Ʊ�ɽ�����(��)
	
	long secAccountId 				= -1;		//�ʽ��ʻ�ID
	long secSecuritiesId			= -1;		//֤ȯID

	long secStockId					= -1;		//��ƱID
	
	//	-----Ϊ֤ȯ��ת���
	long secCompanyBankId			= -1;		//��˾������ID(����֤ȯ��תת����ҵ��λ)
	long secCounterpartBankId		= -1;		//���׶��ֿ�����ID(�û�֤ȯ��תת���Ŀ���Ӫҵ��)
	long secCounterpartAccountId	= -1;		//���׶����ʻ�ID(�û�֤ȯ��תת�����ʽ��ʻ�)
	long secOppositeSecuritiesId	= -1;		//(�û�֤ȯ��תת����֤ȯ)
	//-----Ϊ֤ȯ��ת���
	
	long secDeliveryOrderStatusId	= -1;		//���״̬
	long secInputUserId 			= -1;		//¼����
	long secCheckUserId				= -1;		//������
	
	String orderField				= "";		//�����ֶ�
	long desc						= -1;		//������
	
	long isRelatedByNoticeForm	= 0; //	�Ƿ��֪ͨ������(����)
	long startTerm						= 0; // ����(��)
	long endTerm						= 0; // ����(��)

	public long getEndTerm() {
		return endTerm;
	}

	public void setEndTerm(long endTerm) {
		this.endTerm = endTerm;
	}

	public long getStartTerm() {
		return startTerm;
	}

	public void setStartTerm(long startTerm) {
		this.startTerm = startTerm;
	}

	/**
	 * @return Returns the currencyId.
	 */
	public long getCurrencyId()
	{
		return currencyId;
	}

	/**
	 * @param currencyId The currencyId to set.
	 */
	public void setCurrencyId(long currencyId)
	{
		this.currencyId = currencyId;
		putUsedField("currencyId", currencyId);
	}

	/**
	 * @return Returns the desc.
	 */
	public long getDesc()
	{
		return desc;
	}

	/**
	 * @param desc The desc to set.
	 */
	public void setDesc(long desc)
	{
		this.desc = desc;
		putUsedField("desc", desc);
	}

	/**
	 * @return Returns the id.
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * @param id The id to set.
	 */
	public void setId(long id)
	{
		this.id = id;
		putUsedField("id", id);
	}

	/**
	 * @return Returns the officeId.
	 */
	public long getOfficeId()
	{
		return officeId;
	}

	/**
	 * @param officeId The officeId to set.
	 */
	public void setOfficeId(long officeId)
	{
		this.officeId = officeId;
	}

	/**
	 * @return Returns the orderField.
	 */
	public String getOrderField()
	{
		return orderField;
	}

	/**
	 * @param orderField The orderField to set.
	 */
	public void setOrderField(String orderField)
	{
		this.orderField = orderField;
	}

	/**
	 * @return Returns the secAmountFrom.
	 */
	public double getSecAmountFrom()
	{
		return secAmountFrom;
	}

	/**
	 * @param secAmountFrom The secAmountFrom to set.
	 */
	public void setSecAmountFrom(double secAmountFrom)
	{
		this.secAmountFrom = secAmountFrom;
	}

	/**
	 * @return Returns the secAmountTo.
	 */
	public double getSecAmountTo()
	{
		return secAmountTo;
	}

	/**
	 * @param secAmountTo The secAmountTo to set.
	 */
	public void setSecAmountTo(double secAmountTo)
	{
		this.secAmountTo = secAmountTo;
	}

	/**
	 * @return Returns the secApplyFormInputDateFrom.
	 */
	public String getSecApplyFormInputDateFrom()
	{
		return secApplyFormInputDateFrom;
	}

	/**
	 * @param secApplyFormInputDateFrom The secApplyFormInputDateFrom to set.
	 */
	public void setSecApplyFormInputDateFrom(String secApplyFormInputDateFrom)
	{
		this.secApplyFormInputDateFrom = secApplyFormInputDateFrom;
	}

	/**
	 * @return Returns the secApplyFormInputDateTo.
	 */
	public String getSecApplyFormInputDateTo()
	{
		return secApplyFormInputDateTo;
	}

	/**
	 * @param secApplyFormInputDateTo The secApplyFormInputDateTo to set.
	 */
	public void setSecApplyFormInputDateTo(String secApplyFormInputDateTo)
	{
		this.secApplyFormInputDateTo = secApplyFormInputDateTo;
	}

	/**
	 * @return Returns the secCheckUserId.
	 */
	public long getSecCheckUserId()
	{
		return secCheckUserId;
	}

	/**
	 * @param secCheckUserId The secCheckUserId to set.
	 */
	public void setSecCheckUserId(long secCheckUserId)
	{
		this.secCheckUserId = secCheckUserId;
	}

	/**
	 * @return Returns the secClientId.
	 */
	public long getSecClientId()
	{
		return secClientId;
	}

	/**
	 * @param secClientId The secClientId to set.
	 */
	public void setSecClientId(long secClientId)
	{
		this.secClientId = secClientId;
	}

	/**
	 * @return Returns the secCounterpartId.
	 */
	public long getSecCounterpartId()
	{
		return secCounterpartId;
	}

	/**
	 * @param secCounterpartId The secCounterpartId to set.
	 */
	public void setSecCounterpartId(long secCounterpartId)
	{
		this.secCounterpartId = secCounterpartId;
	}

	/**
	 * @return Returns the secDeliveryOrderStatusId.
	 */
	public long getSecDeliveryOrderStatusId()
	{
		return secDeliveryOrderStatusId;
	}

	/**
	 * @param secDeliveryOrderStatusId The secDeliveryOrderStatusId to set.
	 */
	public void setSecDeliveryOrderStatusId(long secDeliveryOrderStatusId)
	{
		this.secDeliveryOrderStatusId = secDeliveryOrderStatusId;
	}

	/**
	 * @return Returns the secInputUserId.
	 */
	public long getSecInputUserId()
	{
		return secInputUserId;
	}

	/**
	 * @param secInputUserId The secInputUserId to set.
	 */
	public void setSecInputUserId(long secInputUserId)
	{
		this.secInputUserId = secInputUserId;
	}

	/**
	 * @return Returns the secSystemTransactionCode.
	 */
	public String getSecSystemTransactionCode()
	{
		return secSystemTransactionCode;
	}

	/**
	 * @param secSystemTransactionCode The secSystemTransactionCode to set.
	 */
	public void setSecSystemTransactionCode(String secSystemTransactionCode)
	{
		this.secSystemTransactionCode = secSystemTransactionCode;
	}

	/**
	 * @return Returns the secTransactionDateFrom.
	 */
	public String getSecTransactionDateFrom()
	{
		return secTransactionDateFrom;
	}

	/**
	 * @param secTransactionDateFrom The secTransactionDateFrom to set.
	 */
	public void setSecTransactionDateFrom(String secTransactionDateFrom)
	{
		this.secTransactionDateFrom = secTransactionDateFrom;
	}

	/**
	 * @return Returns the secTransactionDateTo.
	 */
	public String getSecTransactionDateTo()
	{
		return secTransactionDateTo;
	}

	/**
	 * @param secTransactionDateTo The secTransactionDateTo to set.
	 */
	public void setSecTransactionDateTo(String secTransactionDateTo)
	{
		this.secTransactionDateTo = secTransactionDateTo;
	}

	/**
	 * @return Returns the secTransactionTypeId.
	 */
	public long getSecTransactionTypeId()
	{
		return secTransactionTypeId;
	}

	/**
	 * @param secTransactionTypeId The secTransactionTypeId to set.
	 */
	public void setSecTransactionTypeId(long secTransactionTypeId)
	{
		this.secTransactionTypeId = secTransactionTypeId;
	}

	/**
	 * @return Returns the secPledgeSecuritiesAmountFrom.
	 */
	public double getSecPledgeSecuritiesAmountFrom()
	{
		return secPledgeSecuritiesAmountFrom;
	}

	/**
	 * @param secPledgeSecuritiesAmountFrom The secPledgeSecuritiesAmountFrom to set.
	 */
	public void setSecPledgeSecuritiesAmountFrom(double secPledgeSecuritiesAmountFrom)
	{
		this.secPledgeSecuritiesAmountFrom = secPledgeSecuritiesAmountFrom;
	}

	/**
	 * @return Returns the secPledgeSecuritiesAmountTo.
	 */
	public double getSecPledgeSecuritiesAmountTo()
	{
		return secPledgeSecuritiesAmountTo;
	}

	/**
	 * @param secPledgeSecuritiesAmountTo The secPledgeSecuritiesAmountTo to set.
	 */
	public void setSecPledgeSecuritiesAmountTo(double secPledgeSecuritiesAmountTo)
	{
		this.secPledgeSecuritiesAmountTo = secPledgeSecuritiesAmountTo;
	}

	/**
	 * @return Returns the secPriceFrom.
	 */
	public double getSecPriceFrom() {
		return secPriceFrom;
	}
	/**
	 * @param secPriceFrom The secPriceFrom to set.
	 */
	public void setSecPriceFrom(double secPriceFrom) {
		this.secPriceFrom = secPriceFrom;
	}
	/**
	 * @return Returns the secPriceTo.
	 */
	public double getSecPriceTo() {
		return secPriceTo;
	}
	/**
	 * @param secPriceTo The secPriceTo to set.
	 */
	public void setSecPriceTo(double secPriceTo) {
		this.secPriceTo = secPriceTo;
	}
	/**
	 * @return Returns the secQuantityFrom.
	 */
	public double getSecQuantityFrom() {
		return secQuantityFrom;
	}
	/**
	 * @param secQuantityFrom The secQuantityFrom to set.
	 */
	public void setSecQuantityFrom(double secQuantityFrom) {
		this.secQuantityFrom = secQuantityFrom;
	}
	/**
	 * @return Returns the secQuantityTo.
	 */
	public double getSecQuantityTo() {
		return secQuantityTo;
	}
	/**
	 * @param secQuantityTo The secQuantityTo to set.
	 */
	public void setSecQuantityTo(double secQuantityTo) {
		this.secQuantityTo = secQuantityTo;
	}
	/**
	 * @return Returns the secAccountId.
	 */
	public long getSecAccountId() {
		return secAccountId;
	}
	/**
	 * @param secAccountId The secAccountId to set.
	 */
	public void setSecAccountId(long secAccountId) {
		this.secAccountId = secAccountId;
	}
	/**
	 * @return Returns the secSecuritiesId.
	 */
	public long getSecSecuritiesId() {
		return secSecuritiesId;
	}
	/**
	 * @param secSecuritiesId The secSecuritiesId to set.
	 */
	public void setSecSecuritiesId(long secSecuritiesId) {
		this.secSecuritiesId = secSecuritiesId;
	}
	/**
	 * @return Returns the secNetPriceFrom.
	 */
	public double getSecNetPriceFrom() {
		return secNetPriceFrom;
	}
	/**
	 * @param secNetPriceFrom The secNetPriceFrom to set.
	 */
	public void setSecNetPriceFrom(double secNetPriceFrom) {
		this.secNetPriceFrom = secNetPriceFrom;
	}
	/**
	 * @return Returns the secNetPriceTo.
	 */
	public double getSecNetPriceTo() {
		return secNetPriceTo;
	}
	/**
	 * @param secNetPriceTo The secNetPriceTo to set.
	 */
	public void setSecNetPriceTo(double secNetPriceTo) {
		this.secNetPriceTo = secNetPriceTo;
	}
	/**
	 * @return Returns the secApplyFormStatusId.
	 */
	public String getSecApplyFormStatusId()
	{
		return secApplyFormStatusId;
	}

	/**
	 * @param secApplyFormStatusId The secApplyFormStatusId to set.
	 */
	public void setSecApplyFormStatusId(String secApplyFormStatusId)
	{
		this.secApplyFormStatusId = secApplyFormStatusId;
	}

	/**
	 * @return Returns the secStockId.
	 */
	public long getSecStockId()
	{
		return secStockId;
	}

	/**
	 * @param secStockId The secStockId to set.
	 */
	public void setSecStockId(long secStockId)
	{
		this.secStockId = secStockId;
	}

	/**
	 * Returns the secOppositeQuantityFrom.
	 * @return double
	 */
	public double getSecOppositeQuantityFrom() {
		return secOppositeQuantityFrom;
	}

	/**
	 * Returns the secOppositeQuantityTo.
	 * @return double
	 */
	public double getSecOppositeQuantityTo() {
		return secOppositeQuantityTo;
	}

	/**
	 * Sets the secOppositeQuantityFrom.
	 * @param secOppositeQuantityFrom The secOppositeQuantityFrom to set
	 */
	public void setSecOppositeQuantityFrom(double secOppositeQuantityFrom) {
		this.secOppositeQuantityFrom = secOppositeQuantityFrom;
	}

	/**
	 * Sets the secOppositeQuantityTo.
	 * @param secOppositeQuantityTo The secOppositeQuantityTo to set
	 */
	public void setSecOppositeQuantityTo(double secOppositeQuantityTo) {
		this.secOppositeQuantityTo = secOppositeQuantityTo;
	}

	/**
	 * @return Returns the secRelatedDeliveryOrderID.
	 */
	public long getSecRelatedDeliveryOrderID() {
		return secRelatedDeliveryOrderID;
	}
	/**
	 * @param secRelatedDeliveryOrderID The secRelatedDeliveryOrderID to set.
	 */
	public void setSecRelatedDeliveryOrderID(long secRelatedDeliveryOrderID) {
		this.secRelatedDeliveryOrderID = secRelatedDeliveryOrderID;
	}
	/**
	 * @return Returns the secCompanyBankId.
	 */
	public long getSecCompanyBankId() {
		return secCompanyBankId;
	}

	/**
	 * @param secCompanyBankId The secCompanyBankId to set.
	 */
	public void setSecCompanyBankId(long secCompanyBankId) {
		this.secCompanyBankId = secCompanyBankId;
	}

	/**
	 * @return Returns the secCounterpartAccountId.
	 */
	public long getSecCounterpartAccountId() {
		return secCounterpartAccountId;
	}

	/**
	 * @param secCounterpartAccountId The secCounterpartAccountId to set.
	 */
	public void setSecCounterpartAccountId(long secCounterpartAccountId) {
		this.secCounterpartAccountId = secCounterpartAccountId;
	}

	/**
	 * @return Returns the secCounterpartBankId.
	 */
	public long getSecCounterpartBankId() {
		return secCounterpartBankId;
	}

	/**
	 * @param secCounterpartBankId The secCounterpartBankId to set.
	 */
	public void setSecCounterpartBankId(long secCounterpartBankId) {
		this.secCounterpartBankId = secCounterpartBankId;
	}

	/**
	 * @return Returns the secOppositeSecuritiesId.
	 */
	public long getSecOppositeSecuritiesId() {
		return secOppositeSecuritiesId;
	}

	/**
	 * @param secOppositeSecuritiesId The secOppositeSecuritiesId to set.
	 */
	public void setSecOppositeSecuritiesId(long secOppositeSecuritiesId) {
		this.secOppositeSecuritiesId = secOppositeSecuritiesId;
	}

	/**
	 * Returns the isRelatedByNoticeForm.
	 * @return long
	 */
	public long getIsRelatedByNoticeForm() {
		return isRelatedByNoticeForm;
	}

	/**
	 * Sets the isRelatedByNoticeForm.
	 * @param isRelatedByNoticeForm The isRelatedByNoticeForm to set
	 */
	public void setIsRelatedByNoticeForm(long isRelatedByNoticeForm) {
		this.isRelatedByNoticeForm = isRelatedByNoticeForm;
	}

}
