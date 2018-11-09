package com.iss.itreasury.securities.deliveryorder.dataentity;

import java.sql.Timestamp;
import java.util.Calendar;

import com.iss.itreasury.securities.apply.dao.SEC_BidRangeDAO;
import com.iss.itreasury.securities.apply.dataentity.ApplyInfo;
import com.iss.itreasury.securities.apply.dataentity.BidRangeInfo;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.util.NameRef;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.securities.util.SECUtil;

/*
 * 
 * 13.�ʽ���
 * 
 * 16.��Ʊһ�������깺
 * 17.��Ʊһ�������깺
 * 18.��Ʊ����
 * 
 * 26.���м�ծȯ�ع�
 * 27.������ծȯ�ع�
 * 
 * 31.���м��ծһ��
 * 32.���м��ծ����
 * 33.��������ծһ��
 * 34.��������ծ����
 * 
 * 46.��ҵծһ��(���չ�Ʊһ�������깺)
 * 47.��ҵծ����(���չ�Ʊ����)
 * 
 * 53.��תծ����(���չ�Ʊ����)
 * 54.ծת��
 * 
 * 57.���ʽ����һ�������깺(���չ�Ʊһ�������깺) 58.���ʽ�������(���չ�Ʊ����)
 * 
 * 61.����ʽ����һ���Ϲ�
 * 62.����ʽ��������깺(���տ���ʽ����һ���Ϲ�) 
 * 63.����ʽ����������(���տ���ʽ����һ���Ϲ�) 
 * 64.����ʽ����ֺ����(û�������飬���Բ���Ҫת��)
 * 92.����ʽ����ת��
 * 
 * 
 */
public class MappingDataentity {

    public DeliveryOrderInfo map(ApplyInfo applyInfo) throws SecuritiesDAOException
    {
    	long businessType = Long.parseLong(Long.toString(applyInfo.getTransactionTypeId()).substring(0,2));
		DeliveryOrderInfo deliveryOrderInfo = new DeliveryOrderInfo();
		
		switch ((int) businessType)
		{
			//�ʽ���
			case (int) SECConstant.BusinessType.CAPITAL_LANDING.ID:
				deliveryOrderInfo = this.matchCapitalLanding(applyInfo);
				break;
				
			//��Ʊһ�������깺
			case (int) SECConstant.BusinessType.STOCK_BID_ONLINE.ID:
				deliveryOrderInfo = this.matchStockBidOnline(applyInfo);
				break;
			//��Ʊһ�������깺
			case (int) SECConstant.BusinessType.STOCK_BID.ID:
				deliveryOrderInfo = this.matchStockBid(applyInfo);
				break;
			//��Ʊ����
			case (int) SECConstant.BusinessType.STOCK_TRANSACTION.ID:
				deliveryOrderInfo = this.matchStockTransaction(applyInfo);
				break;
			//����Ʊ��һ��
			case (int) SECConstant.BusinessType.CENTRAL_BANK_NOTE_BID.ID:
				deliveryOrderInfo = this.matchCentralBankNoteBid(applyInfo);
				break;
			//����Ʊ�ݶ���
			case (int) SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.ID:
				deliveryOrderInfo = this.matchCentralBankNoteTransaction(applyInfo);
				break;
			//���м�ծȯ�ع�
			case (int) SECConstant.BusinessType.BANK_BOND_REPURCHASE.ID:
				deliveryOrderInfo = this.matchBankBondRepurchase(applyInfo);
				break;
			//������ծȯ�ع�
			case (int) SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.ID:
				deliveryOrderInfo = this.matchExchangeCenterBondRepurchase(applyInfo);
				break;
				
			//���м��ծһ��
			case (int) SECConstant.BusinessType.BANK_NATIONAL_BOND_BID.ID:
				deliveryOrderInfo = this.matchBankNationalBondBid(applyInfo);
				break;
			//���м��ծ����
			case (int) SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.ID:
				deliveryOrderInfo = this.matchBankNationalBondTransaction(applyInfo);
				break;
			//��������ծһ��
			case (int) SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_BID.ID:
				deliveryOrderInfo = this.matchExchangeCenterNationalBondBid(applyInfo);
				break;
			//��������ծ����
			case (int) SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.ID:
				deliveryOrderInfo = this.matchExchangeCenterNationalBondTransaction(applyInfo);
				break;
			//����ծһ��
			case (int) SECConstant.BusinessType.FINANCIAL_BOND_BID.ID:
				deliveryOrderInfo = this.matchFinacialBondBid(applyInfo);
				break;
			//����ծ����
			case (int) SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.ID:
				deliveryOrderInfo = this.matchFinacialBondTransaction(applyInfo);
				break;
			//�����Խ���ծһ��
			case (int) SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_BID.ID:
				deliveryOrderInfo = this.matchPolicyRelatedFinacialBondBid(applyInfo);
				break;
			//�����Խ���ծ����
			case (int) SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.ID:
				deliveryOrderInfo = this.matchPolicyRelatedFinacialBondTransaction(applyInfo);
				break;
			//��ҵծһ��
			case (int) SECConstant.BusinessType.ENTERPRISE_BOND_BID.ID:
				deliveryOrderInfo = this.matchEnterpriseBondBid(applyInfo);
				break;
			//��ҵծ����
			case (int) SECConstant.BusinessType.ENTERPRISE_BOND_TRANSACTION.ID:
				deliveryOrderInfo = this.matchEnterpriseBondTransaction(applyInfo);
				break;
			//��תծһ������
			case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_BID_ONLINE.ID:
				deliveryOrderInfo = this.matchTransformableBondBidOnline(applyInfo);
				break;
			//��תծһ������
			case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.ID:
				deliveryOrderInfo = this.matchTransformableBondBid(applyInfo);
				break;
			//��תծ����
			case (int) SECConstant.BusinessType.TRANSFORMABLE_BOND_TRANSACTION.ID:
				deliveryOrderInfo = this.matchTransformableBondTransaction(applyInfo);
				break;
			//ծת��
			case (int) SECConstant.BusinessType.DEBT_TO_EQUITY.ID:
				deliveryOrderInfo = this.matchDebt_to_equity(applyInfo);
				break;
			//���ʽ����һ�������깺
			case (int) SECConstant.BusinessType.ENCLOSED_FUND_BID_ONLINE.ID:
				deliveryOrderInfo = this.matchEnclosedFundBidOnline(applyInfo);
				break;
			//���ʽ����һ�������깺
			case (int) SECConstant.BusinessType.ENCLOSED_FUND_BID.ID:
				deliveryOrderInfo = this.matchEnclosedFundBid(applyInfo);
				break;
			//���ʽ�������
			case (int) SECConstant.BusinessType.ENCLOSED_FUND_TRANSACTION.ID:
				deliveryOrderInfo = this.matchEnclosedFundTransaction(applyInfo);
				break;
			//����ʽ����һ���Ϲ�
			case (int) SECConstant.BusinessType.MUTUAL_FUND_SUBSCRIBE.ID:
				deliveryOrderInfo = this.matchMutualFundSubscribe(applyInfo);
				break;
			//����ʽ��������깺
			case (int) SECConstant.BusinessType.MUTUAL_FUND_BID.ID:
				deliveryOrderInfo = this.matchMutualFundBid(applyInfo);
				break;
			//����ʽ����������
			case (int) SECConstant.BusinessType.MUTUAL_FUND_REDEEM.ID:
				deliveryOrderInfo = this.matchMutualFundRedeem(applyInfo);
				break;
			//����ת��
			case (int) SECConstant.BusinessType.FUND_TRANSFER.ID:
				deliveryOrderInfo = this.matchFundTransfer(applyInfo);
				break;
				
		}
		return deliveryOrderInfo;
    }
	/**
	 * 13.�ʽ���
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchCapitalLanding(ApplyInfo applyInfo)
	{
		DeliveryOrderInfo deliveryOrderInfo = new DeliveryOrderInfo();
		Calendar cal = Calendar.getInstance();
		
//		������ID
		deliveryOrderInfo.setApplyFormId(applyInfo.getId());
		
//		��������
		deliveryOrderInfo.setTransactionTypeId(applyInfo.getTransactionTypeId()); 
		
//		�ɽ�����
		deliveryOrderInfo.setTransactionDate(applyInfo.getTransactionEndDate());
		
//		ҵ��λ����  
		deliveryOrderInfo.setClientId(applyInfo.getClientId()); 
		
//		���׶��ֱ��
		deliveryOrderInfo.setCounterpartId(applyInfo.getCounterpartId()); 
		
//		��Ϣ��(�ɽ�����+�������е������ٶ�N��)
		cal.setTime(applyInfo.getTransactionEndDate());//��ɽ�����
		int liquidateRate = (int)applyInfo.getLiquidateRate();//�����ٶ�
		if(liquidateRate == -1) liquidateRate = 0;
		 
		cal.add(Calendar.DATE, liquidateRate);//�ɽ�����+�����ٶ�
		deliveryOrderInfo.setValueDate(new Timestamp(cal.getTime().getTime()));
		
//		������(Ĭ��ֵΪ��Ϣ��)
		deliveryOrderInfo.setDeliveryDate(new Timestamp(cal.getTime().getTime()));
		
//		������(��Ϣ��+�������)
		int term = (int)applyInfo.getTerm();//�������
		cal.add(Calendar.DATE, term);//��Ϣ��+�������
		deliveryOrderInfo.setMaturityDate(new Timestamp(cal.getTime().getTime()));

//		�����
		deliveryOrderInfo.setPledgeSecuritiesAmount(applyInfo.getPledgeSecuritiesAmount());//��Ԫ 
		deliveryOrderInfo.setAmount(applyInfo.getAmount());//Ԫ
		
//		�������
		deliveryOrderInfo.setRate(applyInfo.getRate());
		
//		�������
		deliveryOrderInfo.setTerm(applyInfo.getTerm());
		
//		Ӧ����Ϣ(�����*�������*�������/(360*100))
		double suspenseInterest = SECUtil.Arith.div(SECUtil.Arith.mul(SECUtil.Arith.mul(applyInfo.getPledgeSecuritiesAmount()*10000,applyInfo.getRate()),applyInfo.getTerm()),36000);
		deliveryOrderInfo.setSuspenseInterest(suspenseInterest);
		
//		���ڻ�����(�����+Ӧ����Ϣ)
		deliveryOrderInfo.setMaturityAmount(applyInfo.getPledgeSecuritiesAmount()*10000+suspenseInterest);
		
//		ʵ����Ϣ(Ĭ��ֵΪӦ����Ϣ)
		deliveryOrderInfo.setInterest(suspenseInterest);
		
//		˰��(Ĭ��ֵΪ0)
		deliveryOrderInfo.setTax(0.0);
		
//		ʵ���ո�(Ĭ��ֵΪ�����)
		deliveryOrderInfo.setNetIncome(applyInfo.getPledgeSecuritiesAmount());
		
		//��ע
		deliveryOrderInfo.setRemark(applyInfo.getRemark());
		
		return deliveryOrderInfo;
	}


	/**
	 * 16.��Ʊһ�������깺
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchStockBidOnline(ApplyInfo applyInfo)
	{
		DeliveryOrderInfo deliveryOrderInfo = new DeliveryOrderInfo();
		
//		������ID
		deliveryOrderInfo.setApplyFormId(applyInfo.getId());
		
//		��������
		deliveryOrderInfo.setTransactionTypeId(applyInfo.getTransactionTypeId()); 
		
//		�ɽ�����
		deliveryOrderInfo.setTransactionDate(applyInfo.getTransactionStartDate());
		
//		ҵ��λ����  
		deliveryOrderInfo.setClientId(applyInfo.getClientId()); 
		
//		����Ӫҵ������
		deliveryOrderInfo.setCounterpartId(applyInfo.getCounterpartId()); 
		
//		�ʽ��ʺ�
		deliveryOrderInfo.setAccountId(applyInfo.getAccountId());
		
//		�ɶ��˻�����/����(�ʽ��ʺŷŴ󾵴���)
//		����ʱ֤ȯ����(�׷�SecuritiesCode1)
		deliveryOrderInfo.setSecuritiesId(applyInfo.getSecuritiesId());
		
//		�ɽ��۸�
		deliveryOrderInfo.setPrice(applyInfo.getPrice());
		
//		�ɽ�����
		deliveryOrderInfo.setQuantity(applyInfo.getQuantity());
		
				
//		�ɽ����
		double amount = SECUtil.Arith.mul(applyInfo.getPrice(), applyInfo.getQuantity());
		deliveryOrderInfo.setAmount(amount);//Ԫ
		
//		˰��(Ĭ��ֵΪ0)
		deliveryOrderInfo.setTax(0.0);
		
		
//		ʵ���ո�(Ĭ��ֵΪ�����)
		deliveryOrderInfo.setNetIncome(applyInfo.getAmount());
		
		//��ע
		deliveryOrderInfo.setRemark(applyInfo.getRemark());
		
		return deliveryOrderInfo;
	}
	/**
	 * 17.��Ʊһ�������깺
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchStockBid(ApplyInfo applyInfo)
	{
		DeliveryOrderInfo deliveryOrderInfo = new DeliveryOrderInfo();
		
//		������ID
		deliveryOrderInfo.setApplyFormId(applyInfo.getId());
		
//		��������
		deliveryOrderInfo.setTransactionTypeId(applyInfo.getTransactionTypeId()); 
		
//		�ɽ�����
		deliveryOrderInfo.setTransactionDate(applyInfo.getTransactionStartDate());
		
//		ҵ��λ����  
		deliveryOrderInfo.setClientId(applyInfo.getClientId()); 
		
//		����Ӫҵ������
		deliveryOrderInfo.setCounterpartId(applyInfo.getCounterpartId()); 
		
//		�ʽ��ʺ�
		deliveryOrderInfo.setAccountId(applyInfo.getAccountId());
		
//		�ɶ��˻�����/����(�ʽ��ʺŷŴ󾵴���)
//		֤ȯ����(�׷�SecuritiesCode1)
		deliveryOrderInfo.setSecuritiesId(applyInfo.getSecuritiesId());
		
//		�ɽ��۸�
		deliveryOrderInfo.setPrice(applyInfo.getPrice());
		
//		�ɽ�����
		deliveryOrderInfo.setQuantity(applyInfo.getQuantity());
				
//		�ɽ����
		double amount = SECUtil.Arith.mul(applyInfo.getPrice(), applyInfo.getQuantity());
		deliveryOrderInfo.setAmount(amount);//Ԫ
		
//		Ԥ���������
		deliveryOrderInfo.setRate(applyInfo.getConvertRate());
		
		//Ԥ������(�ɽ����*Ԥ���������/100)
		double maturityAmount = SECUtil.Arith.div(SECUtil.Arith.mul(amount, applyInfo.getConvertRate()), 100);
		deliveryOrderInfo.setMaturityAmount(maturityAmount);
		
//		˰��(Ĭ��ֵΪ0)
		deliveryOrderInfo.setTax(0.0);
		
//		ʵ���ո�(Ĭ��ֵΪ�����)
		deliveryOrderInfo.setNetIncome(maturityAmount);
		
		//��ע
		deliveryOrderInfo.setRemark(applyInfo.getRemark());
		
		return deliveryOrderInfo;
	}
	/**
	 * 18.��Ʊ����
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchStockTransaction(ApplyInfo applyInfo)
	{
		DeliveryOrderInfo deliveryOrderInfo = new DeliveryOrderInfo();
		
//		������ID
		deliveryOrderInfo.setApplyFormId(applyInfo.getId());
		
//		��������
		deliveryOrderInfo.setTransactionTypeId(applyInfo.getTransactionTypeId()); 
		
//		�ɽ�����
		deliveryOrderInfo.setTransactionDate(applyInfo.getTransactionStartDate());
		
//		ҵ��λ����  
		deliveryOrderInfo.setClientId(applyInfo.getClientId()); 
		
//		����Ӫҵ������
		deliveryOrderInfo.setCounterpartId(applyInfo.getCounterpartId()); 
		
//		�ʽ��ʺ�
		deliveryOrderInfo.setAccountId(applyInfo.getAccountId());
		
//		�ɶ��˻�����/����(�ʽ��ʺŷŴ󾵴���)
//		֤ȯ����(����SecuritiesCode2)
		deliveryOrderInfo.setSecuritiesId(applyInfo.getSecuritiesId());
		
//		�ɽ��۸�
		deliveryOrderInfo.setPrice(applyInfo.getPrice());
		
//		�ɽ�����
		deliveryOrderInfo.setQuantity(applyInfo.getQuantity());
						
//		�ɽ����
		double amount = SECUtil.Arith.mul(applyInfo.getPrice(), applyInfo.getQuantity());
		deliveryOrderInfo.setAmount(amount);//Ԫ
		
//		˰��(Ĭ��ֵΪ0)
		deliveryOrderInfo.setTax(0.0);
		
		
//		ʵ���ո�(Ĭ��ֵΪ�����)
		deliveryOrderInfo.setNetIncome(amount);
		
		//��ע
		deliveryOrderInfo.setRemark(applyInfo.getRemark());
		
		return deliveryOrderInfo;
	}
	/**
	 * 21.����Ʊ��һ��
	 * @param amount The amount to set.
	 * @throws SecuritiesDAOException 
	 */
	private DeliveryOrderInfo matchCentralBankNoteBid(ApplyInfo applyInfo) throws SecuritiesDAOException
	{
		return matchBankNationalBondBid(applyInfo);
	}
	/**
	 * 22.����Ʊ�ݶ���
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchCentralBankNoteTransaction(ApplyInfo applyInfo)
	{
		return matchBankNationalBondTransaction(applyInfo);
	}
	/**
	 * 26.���м�ծȯ�ع�
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchBankBondRepurchase(ApplyInfo applyInfo)
	{
		DeliveryOrderInfo deliveryOrderInfo = new DeliveryOrderInfo();
		Calendar cal = Calendar.getInstance();
		
//		������ID
		deliveryOrderInfo.setApplyFormId(applyInfo.getId());
		
//		��������
		deliveryOrderInfo.setTransactionTypeId(applyInfo.getTransactionTypeId()); 
		
//		�ɽ�����
		deliveryOrderInfo.setTransactionDate(applyInfo.getTransactionStartDate());
		
//		ҵ��λ����  
		deliveryOrderInfo.setClientId(applyInfo.getClientId()); 
		
//		���׶��ֱ��
		deliveryOrderInfo.setCounterpartId(applyInfo.getCounterpartId()); 
		
//		�״ν�����(�ɽ�����+�������е������ٶ�N��)
		cal.setTime(applyInfo.getTransactionStartDate());//��ɽ�����
		int liquidateRate = (int)applyInfo.getLiquidateRate();//�����ٶ�
		if(liquidateRate == -1) liquidateRate = 0;
		
		cal.add(Calendar.DATE, liquidateRate);//�ɽ�����+�����ٶ�
		deliveryOrderInfo.setDeliveryDate(new Timestamp(cal.getTime().getTime()));

//		���ڽ�����(�״ν�����+�ع�����)
		int term = (int)applyInfo.getTerm();//�ع�����
		cal.add(Calendar.DATE, term);//�״ν�����+�ع�����
		deliveryOrderInfo.setMaturityDate(new Timestamp(cal.getTime().getTime()));

//		��Ѻծȯ����
		deliveryOrderInfo.setPledgeSecuritiesId(applyInfo.getSecuritiesId());
		
//		�ع�����
		deliveryOrderInfo.setRate(applyInfo.getRate());
		
//		�ع�����
		deliveryOrderInfo.setTerm(applyInfo.getTerm());
		
//		ȯ���ܶ�(��������ȡ������ֵ��Ԫ)
		deliveryOrderInfo.setPledgeSecuritiesAmount(applyInfo.getPledgeSecuritiesAmount());//��Ԫ 
				
//		�������
		deliveryOrderInfo.setRate(applyInfo.getRate());
		
//		�������
		deliveryOrderInfo.setPledgeRate(applyInfo.getConvertRate());
		
//		�ɽ����(ȯ���ܶ�*�������)
		//double amount = SECUtil.Arith.mul(applyInfo.getPledgeSecuritiesAmount(), applyInfo.getConvertRate())*100;
		deliveryOrderInfo.setAmount(applyInfo.getAmount());

//		Ӧ����Ϣ(ȯ���ܶ�*�ع�����*�ع�����/(365*100))
		double interest = SECUtil.Arith.div(SECUtil.Arith.mul(SECUtil.Arith.mul(applyInfo.getPledgeSecuritiesAmount()*10000,applyInfo.getRate()),applyInfo.getTerm()),36500);
		deliveryOrderInfo.setInterest(interest);

//		���ڻ�����(�ɽ����+Ӧ����Ϣ)
		deliveryOrderInfo.setMaturityAmount(applyInfo.getAmount() + interest);
		
//		˰��(Ĭ��ֵΪ0)
		deliveryOrderInfo.setTax(0.0);
		
//		ʵ���ո�(Ĭ��ֵΪ�ɽ����)
		deliveryOrderInfo.setNetIncome(applyInfo.getAmount());
		
		//��ע
		deliveryOrderInfo.setRemark(applyInfo.getRemark());
		
		return deliveryOrderInfo;
	}
	/**
	 * 27.������ծȯ�ع�
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchExchangeCenterBondRepurchase(ApplyInfo applyInfo)
	{
		DeliveryOrderInfo deliveryOrderInfo = new DeliveryOrderInfo();
		Calendar cal = Calendar.getInstance();
		
//		������ID
		deliveryOrderInfo.setApplyFormId(applyInfo.getId());
		
//		��������
		deliveryOrderInfo.setTransactionTypeId(applyInfo.getTransactionTypeId()); 
		
//		�ɽ�����
		deliveryOrderInfo.setTransactionDate(applyInfo.getTransactionStartDate());
		
//		ҵ��λ����  
		deliveryOrderInfo.setClientId(applyInfo.getClientId()); 
		
//		����Ӫҵ������
		deliveryOrderInfo.setCounterpartId(applyInfo.getCounterpartId()); 
		
//		�ʽ��ʺ�
		deliveryOrderInfo.setAccountId(applyInfo.getAccountId());
		
//		�ɶ��˻�����/����(�ʽ��ʺŷŴ󾵴���)
//		֤ȯ����(����SecuritiesCode2)
		deliveryOrderInfo.setSecuritiesId(applyInfo.getSecuritiesId());
		
//		��������(�ɽ�����+1+�ûع�Ʒ�������õĻع�������)
		cal.setTime(applyInfo.getTransactionStartDate());//��ɽ�����
		String pledgeTerm = NameRef.getPledgeTermBySecuritiesId(applyInfo.getSecuritiesId());
		
		if(pledgeTerm.equals("")){
		    pledgeTerm = "0";
		}
		int term = Integer.parseInt(pledgeTerm);//�ع�����
		cal.add(Calendar.DATE, term+1);//�ɽ�����+�ع�����+1
		deliveryOrderInfo.setDeliveryDate(new Timestamp(cal.getTime().getTime()));
				
//		�ɽ��۸�
		deliveryOrderInfo.setPrice(applyInfo.getPrice());
		
//		�ɽ�����
		deliveryOrderInfo.setQuantity(applyInfo.getQuantity());
		
//		��Ѻ��׼ȯ(Ĭ��ֵΪ�ɽ�����)
		deliveryOrderInfo.setPledgeSecuritiesQuantity((long)applyInfo.getQuantity());
		
//		�ɽ����
		deliveryOrderInfo.setAmount(applyInfo.getAmount());//Ԫ
		
		//���ؽ��(Ĭ��ֵ��0)
		deliveryOrderInfo.setMaturityAmount(0.0);
		
//		˰��(Ĭ��ֵΪ0)
		deliveryOrderInfo.setTax(0.0);
		
		
//		ʵ���ո�(Ĭ��ֵΪ�����)
		deliveryOrderInfo.setNetIncome(applyInfo.getAmount());
		
		//��ע
		deliveryOrderInfo.setRemark(applyInfo.getRemark());
		
		return deliveryOrderInfo;
	}
	
	/**
	 * 31.���м��ծһ��
	 * @param amount The amount to set.
	 * @throws SecuritiesDAOException 
	 * @throws SecuritiesDAOException 
	 * 
	 */
	//Modify by leiyang 2007/12/28  �ݲ��޸���Ҫȷ��
	private DeliveryOrderInfo matchBankNationalBondBid(ApplyInfo applyInfo) throws SecuritiesDAOException{
		DeliveryOrderInfo deliveryOrderInfo = new DeliveryOrderInfo();
		SEC_BidRangeDAO brDao = new SEC_BidRangeDAO();
		Calendar cal = Calendar.getInstance();
		
		//��������Ϣ
		BidRangeInfo bidRangeInfo = brDao.findMaxAmountByBidRangeInfo(applyInfo.getId());
		
//		������ID
		deliveryOrderInfo.setApplyFormId(applyInfo.getId());
		
//		��������
		deliveryOrderInfo.setTransactionTypeId(applyInfo.getTransactionTypeId()); 
		
//		�ɽ�����
		deliveryOrderInfo.setTransactionDate(applyInfo.getTransactionStartDate());
		
//		ҵ��λ����  
		deliveryOrderInfo.setClientId(applyInfo.getClientId()); 
		
//		ծȯ�����̱��
		deliveryOrderInfo.setCounterpartId(applyInfo.getCounterpartId()); 
		
//		֤ȯ����(�׷�SecuritiesCode1)
		deliveryOrderInfo.setSecuritiesId(applyInfo.getSecuritiesId());
		
//		Ͷ�귽ʽ
		deliveryOrderInfo.setBidTypeId(applyInfo.getBidTypeId());
		
//		������
		cal.setTime(applyInfo.getTransactionStartDate());//��ɽ�����
		int liquidateRate = (int)applyInfo.getLiquidateRate();//�����ٶ�
		if(liquidateRate == -1) liquidateRate = 0;
		
		cal.add(Calendar.DATE, liquidateRate);//�ɽ�����+�����ٶ�
		deliveryOrderInfo.setDeliveryDate(new Timestamp(cal.getTime().getTime()));
		
//		�ɽ��۸�
		deliveryOrderInfo.setPrice(bidRangeInfo.getBidEndAmount());
		
//		�ɽ�����
		deliveryOrderInfo.setRate(bidRangeInfo.getBidEndRate());
		
//		�ɽ�����
		deliveryOrderInfo.setQuantity(bidRangeInfo.getApplyQuantity());
		
//		�ɽ����
		deliveryOrderInfo.setAmount(bidRangeInfo.getBidEndAmount()*bidRangeInfo.getApplyQuantity());

//		Ԥ�������ѷ���
		deliveryOrderInfo.setCommissionCharge(applyInfo.getCommissionCharge());

//		˰��(Ĭ��ֵΪ0)
		deliveryOrderInfo.setTax(0.0);
		
//		ʵ���ո�(Ĭ��ֵΪ�����)
		deliveryOrderInfo.setNetIncome(bidRangeInfo.getBidEndAmount()*bidRangeInfo.getApplyQuantity());
		
		//��ע
		deliveryOrderInfo.setRemark(applyInfo.getRemark());

		return deliveryOrderInfo;
	}

	/**
	 * 32.���м��ծ����
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchBankNationalBondTransaction(ApplyInfo applyInfo)
	{
		DeliveryOrderInfo deliveryOrderInfo = new DeliveryOrderInfo();
		Calendar cal = Calendar.getInstance();
		
//		������ID
		deliveryOrderInfo.setApplyFormId(applyInfo.getId());
		
//		��������
		deliveryOrderInfo.setTransactionTypeId(applyInfo.getTransactionTypeId()); 
		
//		�ɽ�����
		deliveryOrderInfo.setTransactionDate(applyInfo.getTransactionStartDate());
		
//		ҵ��λ����  
		deliveryOrderInfo.setClientId(applyInfo.getClientId()); 
		
//		���׶�������
		deliveryOrderInfo.setCounterpartId(applyInfo.getCounterpartId()); 
		
//		֤ȯ����(�׷�SecuritiesCode1)
		deliveryOrderInfo.setSecuritiesId(applyInfo.getSecuritiesId());
		
//		������
		cal.setTime(applyInfo.getTransactionStartDate());//��ɽ�����
		int liquidateRate = (int)applyInfo.getLiquidateRate();//�����ٶ�
		if(liquidateRate == -1) liquidateRate = 0;
		
		cal.add(Calendar.DATE, liquidateRate);//�ɽ�����+�����ٶ�
		deliveryOrderInfo.setDeliveryDate(new Timestamp(cal.getTime().getTime()));
		
//		����(Ĭ��ֵ��0)
		deliveryOrderInfo.setNetPrice(0.0);
		
//		Ӧ����Ϣ(Ĭ��ֵ��0)
		
//		ȫ��
		deliveryOrderInfo.setPrice(applyInfo.getPrice());
		
//		ȯ���ܶ�(��Ԫ)(�ɽ�����*100/10000)
		deliveryOrderInfo.setPledgeSecuritiesAmount(SECUtil.Arith.div(applyInfo.getQuantity(), 100));
		
//		�ɽ�����
		deliveryOrderInfo.setQuantity(applyInfo.getQuantity());
		
//		���۽��(����*�ɽ�����)
//		Ӧ����Ϣ�ܶ�(Ӧ����Ϣ*�ɽ�����)

//		ȫ�۽��(ȫ��*�ɽ�����)
		double amount = SECUtil.Arith.mul(applyInfo.getPrice(), applyInfo.getQuantity());
		deliveryOrderInfo.setAmount(amount);

//		˰��(Ĭ��ֵΪ0)
		deliveryOrderInfo.setTax(0.0);
		
//		ʵ���ո�(Ĭ��ֵΪ�����)
		deliveryOrderInfo.setNetIncome(amount);
		
		//��ע
		deliveryOrderInfo.setRemark(applyInfo.getRemark());
		
//		���׶���ծȯ�й��˺�
        deliveryOrderInfo.setCounterpartTrusteeCode(NameRef.getCounterpartTrusteeCodeByCounterpartId(applyInfo.getCounterpartId()) );
		
		return deliveryOrderInfo;
	}
	/**
	 * 33.��������ծһ��
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchExchangeCenterNationalBondBid(ApplyInfo applyInfo)
	{
		DeliveryOrderInfo deliveryOrderInfo = new DeliveryOrderInfo();
		Calendar cal = Calendar.getInstance();
		
//		������ID
		deliveryOrderInfo.setApplyFormId(applyInfo.getId());
		
//		��������
		deliveryOrderInfo.setTransactionTypeId(applyInfo.getTransactionTypeId()); 
		
//		�ɽ�����
		deliveryOrderInfo.setTransactionDate(applyInfo.getTransactionStartDate());
		
//		ҵ��λ����  
		deliveryOrderInfo.setClientId(applyInfo.getClientId()); 
		
//		����Ӫҵ������
		deliveryOrderInfo.setCounterpartId(applyInfo.getCounterpartId()); 
		
//		�ʽ��ʺ�
		deliveryOrderInfo.setAccountId(applyInfo.getAccountId());
		
//		�ɶ��˻�����/����(�ʽ��ʺŷŴ󾵴���)
//		֤ȯ����(�׷�SecuritiesCode1)
		deliveryOrderInfo.setSecuritiesId(applyInfo.getSecuritiesId());
		
		//Ͷ�귽ʽ
		deliveryOrderInfo.setBidTypeId(applyInfo.getBidTypeId());
		
//		������(�ɽ�����+�����ٶ�)
		cal.setTime(applyInfo.getTransactionStartDate());//��ɽ�����
		int liquidateRate = (int)applyInfo.getLiquidateRate();//�����ٶ�
		if(liquidateRate == -1) liquidateRate = 0;
		
		cal.add(Calendar.DATE, liquidateRate);//�ɽ�����+�����ٶ�
		deliveryOrderInfo.setDeliveryDate(new Timestamp(cal.getTime().getTime()));
		
//		�ɽ��۸�
		deliveryOrderInfo.setPrice(applyInfo.getPrice());
		
//		�ɽ�����
		deliveryOrderInfo.setQuantity(applyInfo.getQuantity());
				
//		�ɽ����(�ɽ��۸�*�ɽ�����)
		double amount = SECUtil.Arith.mul(applyInfo.getPrice(), applyInfo.getQuantity());
		deliveryOrderInfo.setAmount(amount);//Ԫ
		
//		Ԥ�������ѷ���
		deliveryOrderInfo.setCommissionCharge(applyInfo.getCommissionCharge());
		
//		˰��(Ĭ��ֵΪ0)
		deliveryOrderInfo.setTax(0.0);
		
		
//		ʵ���ո�(Ĭ��ֵΪ�����)
		deliveryOrderInfo.setNetIncome(amount);
		
		//��ע
		deliveryOrderInfo.setRemark(applyInfo.getRemark());
		
		return deliveryOrderInfo;
	}
	/**
	 * 34.��������ծ����
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchExchangeCenterNationalBondTransaction(ApplyInfo applyInfo)
	{
		DeliveryOrderInfo deliveryOrderInfo = new DeliveryOrderInfo();
		Calendar cal = Calendar.getInstance();
		
//		������ID
		deliveryOrderInfo.setApplyFormId(applyInfo.getId());
		
//		��������
		deliveryOrderInfo.setTransactionTypeId(applyInfo.getTransactionTypeId()); 
		
//		�ɽ�����
		deliveryOrderInfo.setTransactionDate(applyInfo.getTransactionStartDate());
		
//		ҵ��λ����  
		deliveryOrderInfo.setClientId(applyInfo.getClientId()); 
		
//		����Ӫҵ������
		deliveryOrderInfo.setCounterpartId(applyInfo.getCounterpartId()); 
		
//		�ʽ��ʺ�
		deliveryOrderInfo.setAccountId(applyInfo.getAccountId());
		
//		�ɶ��˻�����/����(�ʽ��ʺŷŴ󾵴���)
//		����ʱ֤ȯ����(�׷�SecuritiesCode1)
		deliveryOrderInfo.setSecuritiesId(applyInfo.getSecuritiesId());
		
//		����(Ĭ��ֵ��0)
		deliveryOrderInfo.setNetPrice(0.0);
		
//		Ӧ����Ϣ(Ĭ��ֵ��0)
		
//		ȫ��
		deliveryOrderInfo.setPrice(applyInfo.getPrice());
		
//		�ɽ�����
		deliveryOrderInfo.setQuantity(applyInfo.getQuantity());
		
//		�ɽ����(ȫ��*�ɽ�����)
		double amount = SECUtil.Arith.mul(applyInfo.getPrice(), applyInfo.getQuantity());
		
		deliveryOrderInfo.setAmount(amount);

//		˰��(Ĭ��ֵΪ0)
		deliveryOrderInfo.setTax(0.0);
		
//		ʵ���ո�(Ĭ��ֵΪ�����)
		deliveryOrderInfo.setNetIncome(amount);
		
		//��ע
		deliveryOrderInfo.setRemark(applyInfo.getRemark());
		
		return deliveryOrderInfo;
	}
	/**
	 * 36.����ծһ��
	 * @param amount The amount to set.
	 * @throws SecuritiesDAOException 
	 */
	private DeliveryOrderInfo matchFinacialBondBid(ApplyInfo applyInfo) throws SecuritiesDAOException
	{
		return matchBankNationalBondBid(applyInfo);
	}
	/**
	 * 37.����ծ����
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchFinacialBondTransaction(ApplyInfo applyInfo)
	{
		return matchBankNationalBondTransaction(applyInfo);
	}
	/**
	 * 41.�����Խ���ծһ��
	 * @param amount The amount to set.
	 * @throws SecuritiesDAOException 
	 */
	private DeliveryOrderInfo matchPolicyRelatedFinacialBondBid(ApplyInfo applyInfo) throws SecuritiesDAOException
	{
		return matchBankNationalBondBid(applyInfo);
	}
	/**
	 * 42.�����Խ���ծ����
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchPolicyRelatedFinacialBondTransaction(ApplyInfo applyInfo)
	{
		return matchBankNationalBondTransaction(applyInfo);
	}
	/**
	 * 46.��ҵծһ��
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchEnterpriseBondBid(ApplyInfo applyInfo)
	{
		return matchStockBid(applyInfo);
	}
	/**
	 * 47.��ҵծ����
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchEnterpriseBondTransaction(ApplyInfo applyInfo)
	{
		return matchStockTransaction(applyInfo);
	}
	/**
	 * 51.��תծһ�������깺
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchTransformableBondBidOnline(ApplyInfo applyInfo)
	{
		return matchStockBidOnline(applyInfo);
	}
	/**
	 * 52.��תծһ�������깺
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchTransformableBondBid(ApplyInfo applyInfo)
	{
		return matchStockBid(applyInfo);
	}
	/**
	 * 53.��תծ����
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchTransformableBondTransaction(ApplyInfo applyInfo)
	{
		return matchStockTransaction(applyInfo);
	}
	/**
	 * 54.ծת��
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchDebt_to_equity(ApplyInfo applyInfo)
	{
		DeliveryOrderInfo deliveryOrderInfo = new DeliveryOrderInfo();
		
		deliveryOrderInfo.setTransactionTypeId(applyInfo.getTransactionTypeId());
		deliveryOrderInfo.setTransactionDate(applyInfo.getTransactionStartDate());
		deliveryOrderInfo.setClientId(applyInfo.getClientId());
		deliveryOrderInfo.setCounterpartId(applyInfo.getCounterpartId());
		deliveryOrderInfo.setAccountId(applyInfo.getAccountId());
		deliveryOrderInfo.setSecuritiesId(applyInfo.getSecuritiesId());
		
		deliveryOrderInfo.setPrice(applyInfo.getPrice());
		deliveryOrderInfo.setQuantity(applyInfo.getQuantity());
//		ת�ɽ��
		double amount = SECUtil.Arith.mul(applyInfo.getPrice(), applyInfo.getQuantity());
		deliveryOrderInfo.setAmount(amount);//Ԫ
		deliveryOrderInfo.setOppositeSecuritiesId(applyInfo.getStockId());
		
		deliveryOrderInfo.setOppositeQuantity(applyInfo.getStockQuantity());
		
		//��ע
		deliveryOrderInfo.setRemark(applyInfo.getRemark());
		
		
		return deliveryOrderInfo;
	}
	/**
	 * 56.���ʽ����һ�������깺
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchEnclosedFundBidOnline(ApplyInfo applyInfo)
	{
		return matchStockBidOnline(applyInfo);
	}
	/**
	 * 57.���ʽ����һ�������깺
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchEnclosedFundBid(ApplyInfo applyInfo)
	{
		return matchStockBid(applyInfo);
	}
	/**
	 * 58.���ʽ�������
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchEnclosedFundTransaction(ApplyInfo applyInfo)
	{
		return matchStockTransaction(applyInfo);
	}
	
	/**
	 * 61.����ʽ����һ���Ϲ�
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchMutualFundSubscribe(ApplyInfo applyInfo)
	{
		DeliveryOrderInfo deliveryOrderInfo = new DeliveryOrderInfo();
		
//		������ID
		deliveryOrderInfo.setApplyFormId(applyInfo.getId());
		
//		��������
		deliveryOrderInfo.setTransactionTypeId(applyInfo.getTransactionTypeId()); 
		
//		�ɽ�����
		deliveryOrderInfo.setTransactionDate(applyInfo.getTransactionStartDate());
		
//		ҵ��λ����  
		deliveryOrderInfo.setClientId(applyInfo.getClientId()); 
		
//		�������˾���
		deliveryOrderInfo.setCounterpartId(applyInfo.getCounterpartId()); 
		
//		�����ʺ�
		deliveryOrderInfo.setAccountId(applyInfo.getAccountId());
		
//		�����ʻ�����/����(�����ʺŷŴ󾵴���)

//		֤ȯ����(�׷�SecuritiesCode2)
		deliveryOrderInfo.setSecuritiesId(applyInfo.getSecuritiesId());
		
//		������(��Ҫ����)

//		�Ϲ����
		deliveryOrderInfo.setAmount(applyInfo.getCostAmount() * applyInfo.getQuantity());//Ԫ

//		ȷ�Ϸݶ�
		deliveryOrderInfo.setQuantity(applyInfo.getQuantity());
		
//		��ֵ(Ĭ��ֵ��0)
		deliveryOrderInfo.setNetPrice(applyInfo.getCostAmount());
		
//		˰��(Ĭ��ֵΪ0)
		deliveryOrderInfo.setTax(0.0);
		
//		ʵ���ո�(Ĭ��ֵΪ�Ϲ�����)
		deliveryOrderInfo.setNetIncome(applyInfo.getAmount());
		
		//��ע
		deliveryOrderInfo.setRemark(applyInfo.getRemark());
		
		return deliveryOrderInfo;
	}
	/**
	 * 62.����ʽ��������깺
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchMutualFundBid(ApplyInfo applyInfo)
	{
		return matchMutualFundSubscribe(applyInfo);
	}
	/**
	 * 63.����ʽ����������
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchMutualFundRedeem(ApplyInfo applyInfo)
	{
		return matchMutualFundSubscribe(applyInfo);
	}
	/**
	 * 92.����ת��
	 * @param amount The amount to set.
	 */
	private DeliveryOrderInfo matchFundTransfer(ApplyInfo applyInfo)
	{
		DeliveryOrderInfo deliveryOrderInfo = new DeliveryOrderInfo();
		
//		������ID
		deliveryOrderInfo.setApplyFormId(applyInfo.getId());
		
//		��������
		deliveryOrderInfo.setTransactionTypeId(applyInfo.getTransactionTypeId()); 
		
//		�ɽ�����
		deliveryOrderInfo.setTransactionDate(applyInfo.getTransactionStartDate());
		
//		ҵ��λ����  
		deliveryOrderInfo.setClientId(applyInfo.getClientId()); 
		
//		�������˾���
		deliveryOrderInfo.setCounterpartId(applyInfo.getCounterpartId()); 
		
//		�����ʺ�
		deliveryOrderInfo.setAccountId(applyInfo.getAccountId());
		
//		�����ʻ�����/����(�����ʺŷŴ󾵴���)

//		֤ȯ����(ת��)
		deliveryOrderInfo.setSecuritiesId(applyInfo.getSecuritiesId());
		//֤ȯ����(ת��)
		deliveryOrderInfo.setOppositeSecuritiesId(applyInfo.getStockId());
		
		
		
//		������(��Ҫ����)


//		ȷ�Ϸݶ�(ת��)
		deliveryOrderInfo.setQuantity(applyInfo.getQuantity());
		//ȷ�Ϸݶ�(ת��)
		deliveryOrderInfo.setOppositeQuantity(applyInfo.getStockQuantity());
				
//		ȷ�Ͻ��(ת��)
		deliveryOrderInfo.setMaturityAmount(applyInfo.getAmount());
		
		
//		��ֵ(Ĭ��ֵ��0)
		
//		˰��(Ĭ��ֵΪ0)
		//deliveryOrderInfo.setTax(0.0);
		
//		ʵ���ո�(Ĭ��ֵΪ�Ϲ�����)
		//deliveryOrderInfo.setNetIncome(applyInfo.getAmount());
		
		//��ע
		deliveryOrderInfo.setRemark(applyInfo.getRemark());
		
		return deliveryOrderInfo;
	}
	
	

	public static void main(String[] args) {
	}
}
