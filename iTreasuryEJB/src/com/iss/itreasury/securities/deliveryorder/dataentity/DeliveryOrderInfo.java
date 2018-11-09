package com.iss.itreasury.securities.deliveryorder.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.register.bizlogic.RegisterBean;
import com.iss.itreasury.securities.register.dataentity.PurchaseRegisterInfo;
import com.iss.itreasury.securities.register.dataentity.RepurchaseRegisterInfo;
import com.iss.itreasury.securities.setting.dao.SEC_TransactionTypeDAO;
import com.iss.itreasury.securities.setting.dataentity.TransactionTypeInfo;
import com.iss.itreasury.securities.util.SECBaseDataEntity;
import com.iss.itreasury.securities.util.SECConstant;


/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-2-27
 */
public class DeliveryOrderInfo extends SECBaseDataEntity implements Serializable{
	private long		id 								= -1;	//交割单编号Id
	private String		code 							= "";	//交割单编号
	private long		transactionTypeId 				= -1;	//交易类型编号Id
	private long		applyFormId 					= -1;	//申请书Id
	private Timestamp	transactionDate		 			= null;//成交日期
	private String		systemTransactionCode			= "";	//交易系统成交编号
	private long		clientId 						= -1;	//业务单位编号Id
	private long		counterpartId 					= -1;	//交易对手编号Id
	private Timestamp	valueDate 						= null;//起息日
	private Timestamp	deliveryDate		 		    = null;//交割日
	private Timestamp	maturityDate 					= null;//还款日
	private long		accountId 				    	= -1;	//交易帐号Id	
	private long		securitiesId 					= -1;	//证券代码Id
	private long		pledgeSecuritiesId				= -1;	//抵押债券代码
	private long		bidTypeId 						= -1;	//投标方式Id
	private String		counterpartTrusteeCode 			= "";	//交易对手债券托管账号
	private long		counterpartBankId 				= -1;	//交易对手开户行Id
	private long		counterpartAccountId 			= -1;	//交易对手银行账号Id
	private long		companyBankId 					= -1;	//公司开户行Id
	private long		companyAccountId 				= -1;	//公司银行账号Id
	private double	netPrice 						= 0.0;	//净价
	private double	perSuspenseInterest 			= 0.0;	//应计利息（元/百元面值）
	private double	netPriceAmount 					= 0.0;	//净价金额
	private double	pledgeSecuritiesAmount 			= 0.0;	//券面总额
	private double	price 							= 0.0;	//成交价格
	private double	quantity 						= 0.0;	//成交数量
	private double	pledgeSecuritiesQuantity 		= 0.0;	//抵押标准券
	private double	amount 							= 0.0;	//成交金额
	private double	rate 							= 0.0;	//拆借利率
	private long		term 							= -1;	//拆借期限
	private long		termTypeId 						= -1;	//期限类型
	private double	commissionCharge 				= 0.0;	//预计手续费返还
	private double	suspenseInterest				= 0.0;	//应计利息
	private double	pledgeRate                      = 0.0;  //抵押率
	private double	interest 						= 0.0;	//实计利息
	private double	maturityAmount 					= 0.0;	//到期还款金额
	private double	oppositeQuantity				= 0.0;	//转成股票数量
	private long		oppositeSecuritiesId 			= -1;	//转成证券代码Id
	private double	tax 							= 0.0;	//税费
	private double	netIncome 						= 0.0;	//实际收付
	private long		currencyId                      =-1;//币种ID 
	private long		officeId                        =-1;//办事处ID
	private long		registerId                      =-1;//登记簿ID
	private long		relatedDeliveryOrderID          = -1;//关联的交割单ID 
	private String		remark 							= "";	//备注
	private long		inputUserId 					= -1;	//录入人
	private Timestamp	inputDate 						= null;//录入时间
	private long		updateUserId					=-1;//修改人
	private Timestamp	updateDate						= null;//修改时间
	private long		deleteUserId 					= -1;	//删除人
	private Timestamp	deleteDate 						= null;//删除时间
	private long		checkUserId						= -1;//复核人
	private Timestamp	checkDate						= null;//复核时间
	private long		statusId 						= -1;	//状态
	private Timestamp	timeStamp 						= null;//时间戳
	private double	unitCost                        = 0.0; //单位成本
	private double	unitNetCost                     = 0.0; //单位净价成本
	private double	unitProfitLoss                  = 0.0; //单位实际盈亏
	private double	unitNetProfitLoss               = 0.0; //单位净价实际盈亏
	private long		isCarryCost                     = -1;  //是否已结转成本
	private long		isDelivery                      = -1;  //是否已交割
	private long		isViolation                     = -1;  //是否违规交割单
	private String		reasonOfViolation 				= "";	//违规原因
	
	private long isRepaid								= -1;	//是否已经反款
	
	private long defterm; // 期限
	
	/**
	 * 是否检测帐户余额,默认为检测(TRUE),如果页面抛回异常后用户选择"继续操作"则置入不检测(FALSE)
	 */
	private long isCheckOverDraft						= SECConstant.TRUE;
	
	/**
	 * 是否检测帐户,默认为检测(TRUE),自动交割不检查库存
	 */
	private long isCheckOverStock						= SECConstant.TRUE;	
	//国机
	private long OwnerTypeId				= 0; //	法人类型
	private long IsRelatedByNoticeForm	= SECConstant.FALSE; //	是否跟通知单关联(国机)
	
	
	
	//用于资金划拨业务的业务通知单传值
	private long nextCheckUserId						= -1;
	
    //显示与id相关的code和name
	private String transactionTypeName    = "";//交易类型名称
	private String applyFormCode          = "";//申请书编号
	private String clientName             = "";//业务单位名称
	private String counterpartCode        = "";//交易对手编号
	private String counterpartName        = "";//交易对手名称
	
	private String counterpartBankName    = "";//交易对手开户行名称
	private String counterpartAccountCode = "";//交易对手银行帐号
	private String counterpartAccountName = "";//交易对手帐户名称
	private String companyBankName         = "";//公司开户行名称
	private String companyAccountCode      = "";//公司银行帐号
	private String companyAccountName      = "";//公司帐户名称
	private String stockHolderAccountCode	= "";//股东帐户代码
	private String stockHolderAccountName	= "";//股东帐户名称
	
	//非数据库字段,交易类型Info类，用于后台业务的交易类型信息判断--Add by Huang Ye
	private TransactionTypeInfo transactionTypeInfo = null;
	
	//非数据库字段,标志位,如果是结算模块通过此对象调用证券模块产生会计分录,该值=SECConstant.TRUE--Add by Huang Ye
	private long isSettlementInvoke = -1;
	
	private long typeId = -1;  //业务类型
	private long actionTypeId = -1; //业务子类型
	
	//获取本交割单对应的交易类型的详细信息
	public TransactionTypeInfo getTransactionTypeInfo() throws SecuritiesException{
		if(transactionTypeInfo == null){
			SEC_TransactionTypeDAO dao = new SEC_TransactionTypeDAO();
			try {
				transactionTypeInfo = (TransactionTypeInfo) dao.findByID(transactionTypeId, TransactionTypeInfo.class);
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			}
		}
		return transactionTypeInfo;
	}

	/**
	 * 重新获取本交割单对应的交易类型的详细信息
	 * */
	public void resetTransactionTypeInfo() throws SecuritiesException{
			SEC_TransactionTypeDAO dao = new SEC_TransactionTypeDAO();
			try {
				transactionTypeInfo = (TransactionTypeInfo) dao.findByID(transactionTypeId, TransactionTypeInfo.class);
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			}		
	}
	
	public SECBaseDataEntity convertToRegisterInfo(long operationType) throws SecuritiesException{
		TransactionTypeInfo transTypeInfo = getTransactionTypeInfo();
		if(transTypeInfo.getRegisterProcess() == SECConstant.RegisterProcess.CAPITAL
		|| transTypeInfo.getRegisterProcess() == SECConstant.RegisterProcess.CAPITAL_MATURE){
			RepurchaseRegisterInfo resInfo = new RepurchaseRegisterInfo();
			//resInfo.setAccountID(NameRef.getAccountIDFromDeliveryOrder(this,true));
			resInfo.setAccountID(accountId);
			resInfo.setAmount(amount);
			//TBD????????
			/**
			 * 拆入/拆出/融资/融券时，余额=交易金额。返款时，余额-=返款交易金额。余额>0时表示未返款/未全部返款。
			 * 余额=0时表示全部交易完成，该项业务的生命周期结束。
			 * */
			resInfo.setBalance(amount);
			resInfo.setClientID(clientId);
			resInfo.setCounterpartID(counterpartId);
			//在到期业务中的LastDeliveryOrderID也通过次字段进行传递，在真正更新操作时取此字段更新LastDeliveryOrderID
			resInfo.setFirstDeliveryOrderID(id);
			resInfo.setId(registerId);
			resInfo.setInterestRate(rate);
			//resInfo.setLastDeliveryOrderID()
			resInfo.setMaturityDate(maturityDate);
			resInfo.setPledgeAmount(pledgeSecuritiesAmount);
			resInfo.setPledgeQuantity(pledgeSecuritiesQuantity);
			resInfo.setPledgeRate(pledgeRate);
			resInfo.setPledgeSecuritiesID(pledgeSecuritiesId);
			resInfo.setStatusID(SECConstant.BusinessAttributeStatus.SAVED);
			resInfo.setSystemTransactionCode(systemTransactionCode);
			resInfo.setTerm(term);
			resInfo.setTermTypeID(termTypeId);
			resInfo.setTransactionTypeID(transactionTypeId);
			resInfo.setValueDate(valueDate);
			return resInfo; 
		}else if(transTypeInfo.getRegisterProcess() == SECConstant.RegisterProcess.APPLICATION_APPLY
			  || transTypeInfo.getRegisterProcess() == SECConstant.RegisterProcess.APPLICATION_CONFIRM
			  || transTypeInfo.getRegisterProcess() == SECConstant.RegisterProcess.APPLICATION_MATURE
			  || transTypeInfo.getRegisterProcess() == SECConstant.RegisterProcess.APPLICATION_SELLOUT){
			PurchaseRegisterInfo resInfo = new PurchaseRegisterInfo();

			resInfo.setId(registerId);	
			switch((int)operationType){
				case RegisterBean.REGISTER_OPERATION_TYPE_REGISTER:
				case RegisterBean.REGISTER_OPERATION_TYPE_CANCELREGISTER:{
					//resInfo.setAccountID(NameRef.getAccountIDFromDeliveryOrder(this,true));
					resInfo.setAccountID(accountId);
					resInfo.setClientID(clientId);
					resInfo.setSecuritiesID(securitiesId);
					resInfo.setTransactionTypeID(transactionTypeId);					
					resInfo.setApplyDeliveryOrderID(id);
					resInfo.setApplyDate(deliveryDate);
					resInfo.setApplyQuantity(quantity);
					resInfo.setApplyPrice(price);
					if(maturityAmount > 0)
						resInfo.setApplyAmount(maturityAmount);
					else
						resInfo.setApplyAmount(amount);
					if(rate == 0.0)
						rate = 100.0;
					resInfo.setMarginRate(rate);
					resInfo.setMarginAmount(netIncome);
					resInfo.setStatusID(SECConstant.BusinessAttributeStatus.SAVED);
				}
				break;
				case RegisterBean.REGISTER_OPERATION_TYPE_MATURATE:
				{
				//	resInfo.setDrawbackAmount(amount);
					resInfo.setDrawbackDate(deliveryDate);
					resInfo.setDrawbackDeliveryOrderID(id);				
				}
				break;
				case RegisterBean.REGISTER_OPERATION_TYPE_CANCELMATURATE:{
					resInfo.setDrawbackAmount(0.0);
					resInfo.setDrawbackDate(SECBaseDataEntity.getNullTimeStamp());
					resInfo.setDrawbackDeliveryOrderID(-1);				
				}
				break;
				case RegisterBean.REGISTER_OPERATION_TYPE_APPLYCONFIRM:{
					resInfo.setConfirmAmount(amount);
					resInfo.setConfirmDate(deliveryDate);
					resInfo.setConfirmDeliveryOrderID(id);
					resInfo.setConfirmPrice(price);
					resInfo.setConfirmQuantity(quantity);
				}
				break;
				case RegisterBean.REGISTER_OPERATION_TYPE_CANCELAPPLYCONFIRM:{
					resInfo.setConfirmAmount(0.0);
					resInfo.setConfirmDate(SECBaseDataEntity.getNullTimeStamp());
					resInfo.setConfirmDeliveryOrderID(-1);
					resInfo.setConfirmPrice(0.0);
					resInfo.setConfirmQuantity(0);				
				}
				break;
				case RegisterBean.REGISTER_OPERATION_TYPE_APPLYSELL:{
					resInfo.setSellingAmount(amount);
					resInfo.setSellingDate(deliveryDate);
					resInfo.setSellingDeliveryOrderID(id);
					resInfo.setSellingPrice(price);
					resInfo.setSellingQuantity(quantity);
				}
				break;
				case RegisterBean.REGISTER_OPERATION_TYPE_CANCELAPPLYSELL:{
					resInfo.setSellingAmount(0.0);
					resInfo.setSellingDate(SECBaseDataEntity.getNullTimeStamp());
					resInfo.setSellingDeliveryOrderID(-1);
					resInfo.setSellingPrice(0.0);
					resInfo.setSellingQuantity(0);				
				}
				break;
			}
			return resInfo;			
		}
		return null;
	}
	
	/**
	 * @return Returns the amount.
	 */
	public double getAmount()
	{
		return amount;
	}

	/**
	 * @param amount The amount to set.
	 */
	public void setAmount(double amount)
	{
		this.amount = amount;
		putUsedField("amount", amount);
	}

	/**
	 * @return Returns the applyFormId.
	 */
	public long getApplyFormId()
	{
		return applyFormId;
	}

	/**
	 * @param applyFormId The applyFormId to set.
	 */
	public void setApplyFormId(long applyFormId)
	{
		this.applyFormId = applyFormId;
		putUsedField("applyFormId", applyFormId);
	}

	/**
	 * @return Returns the bidTypeId.
	 */
	public long getBidTypeId()
	{
		return bidTypeId;
	}

	/**
	 * @param bidTypeId The bidTypeId to set.
	 */
	public void setBidTypeId(long bidTypeId)
	{
		this.bidTypeId = bidTypeId;
		putUsedField("bidTypeId", bidTypeId);
	}

	/**
	 * @return Returns the clientId.
	 */
	public long getClientId()
	{
		return clientId;
	}

	/**
	 * @param clientId The clientId to set.
	 */
	public void setClientId(long clientId)
	{
		this.clientId = clientId;
		putUsedField("clientId", clientId);
	}

	/**
	 * @return Returns the commissionCharge.
	 */
	public double getCommissionCharge()
	{
		return commissionCharge;
	}

	/**
	 * @param commissionCharge The commissionCharge to set.
	 */
	public void setCommissionCharge(double commissionCharge)
	{
		this.commissionCharge = commissionCharge;
		putUsedField("commissionCharge", commissionCharge);
	}

	/**
	 * @return Returns the companyAccountId.
	 */
	public long getCompanyAccountId()
	{
		return companyAccountId;
	}

	/**
	 * @param companyAccountId The companyAccountId to set.
	 */
	public void setCompanyAccountId(long companyAccountId)
	{
		this.companyAccountId = companyAccountId;
		putUsedField("companyAccountId", companyAccountId);
	}

	/**
	 * @return Returns the companyBankId.
	 */
	public long getCompanyBankId()
	{
		return companyBankId;
	}

	/**
	 * @param companyBankId The companyBankId to set.
	 */
	public void setCompanyBankId(long companyBankId)
	{
		this.companyBankId = companyBankId;
		putUsedField("companyBankId", companyBankId);
	}

	/**
	 * @return Returns the counterpartAccountId.
	 */
	public long getCounterpartAccountId()
	{
		return counterpartAccountId;
	}

	/**
	 * @param counterpartAccountId The counterpartAccountId to set.
	 */
	public void setCounterpartAccountId(long counterpartAccountId)
	{
		this.counterpartAccountId = counterpartAccountId;
		putUsedField("counterpartAccountId", counterpartAccountId);
	}

	/**
	 * @return Returns the counterpartBankId.
	 */
	public long getCounterpartBankId()
	{
		return counterpartBankId;
	}

	/**
	 * @param counterpartBankId The counterpartBankId to set.
	 */
	public void setCounterpartBankId(long counterpartBankId)
	{
		this.counterpartBankId = counterpartBankId;
		putUsedField("counterpartBankId", counterpartBankId);
	}

	/**
	 * @return Returns the counterpartId.
	 */
	public long getCounterpartId()
	{
		return counterpartId;
	}

	/**
	 * @param counterpartId The counterpartId to set.
	 */
	public void setCounterpartId(long counterpartId)
	{
		this.counterpartId = counterpartId;
		putUsedField("counterpartId", counterpartId);
	}

	/**
	 * @return Returns the counterpartTrusteeCode.
	 */
	public String getCounterpartTrusteeCode()
	{
		return counterpartTrusteeCode;
	}

	/**
	 * @param counterpartTrusteeCode The counterpartTrusteeCode to set.
	 */
	public void setCounterpartTrusteeCode(String counterpartTrusteeCode)
	{
		this.counterpartTrusteeCode = counterpartTrusteeCode;
		putUsedField("counterpartTrusteeCode", counterpartTrusteeCode);
	}

	/**
	 * @return Returns the Code.
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * @param Code The Code to set.
	 */
	public void setCode(String code)
	{
		this.code = code;
		putUsedField("Code", code);
	}

	/**
	 * @return Returns the fundAccountId.
	 */
	public long getAccountId()
	{
		return accountId;
	}

	/**
	 * @param fundAccountId The fundAccountId to set.
	 */
	public void setAccountId(long accountId)
	{
		this.accountId = accountId;
		putUsedField("accountId", accountId);
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
	 * @return Returns the inputDate.
	 */
	public Timestamp getInputDate()
	{
		return inputDate;
	}

	/**
	 * @param inputDate The inputDate to set.
	 */
	public void setInputDate(Timestamp inputDate)
	{
		this.inputDate = inputDate;
		putUsedField("inputDate", inputDate);
	}

	/**
	 * @return Returns the inputUserId.
	 */
	public long getInputUserId()
	{
		return inputUserId;
	}

	/**
	 * @param inputUserId The inputUserId to set.
	 */
	public void setInputUserId(long inputUserId)
	{
		this.inputUserId = inputUserId;
		putUsedField("inputUserId", inputUserId);
	}

	/**
	 * @return Returns the interest.
	 */
	public double getInterest()
	{
		return interest;
	}

	/**
	 * @param interest The interest to set.
	 */
	public void setInterest(double interest)
	{
		this.interest = interest;
		putUsedField("interest", interest);
	}

	/**
	 * @return Returns the maturityAmount.
	 */
	public double getMaturityAmount()
	{
		return maturityAmount;
	}

	/**
	 * @param maturityAmount The maturityAmount to set.
	 */
	public void setMaturityAmount(double maturityAmount)
	{
		this.maturityAmount = maturityAmount;
		putUsedField("maturityAmount", maturityAmount);
	}

	/**
	 * @return Returns the maturityDate.
	 */
	public Timestamp getMaturityDate()
	{
		return maturityDate;
	}

	/**
	 * @param maturityDate The maturityDate to set.
	 */
	public void setMaturityDate(Timestamp maturityDate)
	{
		this.maturityDate = maturityDate;
		putUsedField("maturityDate", maturityDate);
	}

	/**
	 * @return Returns the netIncome.
	 */
	public double getNetIncome()
	{
		return netIncome;
	}

	/**
	 * @param netIncome The netIncome to set.
	 */
	public void setNetIncome(double netIncome)
	{
		this.netIncome = netIncome;
		putUsedField("netIncome", netIncome);
	}
	/**
	 * @return Returns the PledgeRate.
	 */
	public double getPledgeRate()
	{
		return pledgeRate;
	}

	/**
	 * @param PledgeRate The PledgeRate to set.
	 */
	public void setPledgeRate(double pledgeRate)
	{
		this.pledgeRate = pledgeRate;
		putUsedField("pledgeRate", pledgeRate);
	}

	/**
	 * @return Returns the netPrice.
	 */
	public double getNetPrice()
	{
		return netPrice;
	}

	/**
	 * @param netPrice The netPrice to set.
	 */
	public void setNetPrice(double netPrice)
	{
		this.netPrice = netPrice;
		putUsedField("netPrice", netPrice);
	}

	/**
	 * @return Returns the netPriceAmount.
	 */
	public double getNetPriceAmount()
	{
		return netPriceAmount;
	}

	/**
	 * @param netPriceAmount The netPriceAmount to set.
	 */
	public void setNetPriceAmount(double netPriceAmount)
	{
		this.netPriceAmount = netPriceAmount;
		putUsedField("netPriceAmount", netPriceAmount);
	}

	/**
	 * @return Returns the oppositeSecuritiesId.
	 */
	public long getOppositeSecuritiesId()
	{
		return oppositeSecuritiesId;
	}

	/**
	 * @param oppositeSecuritiesId The oppositeSecuritiesId to set.
	 */
	public void setOppositeSecuritiesId(long oppositeSecuritiesId)
	{
		this.oppositeSecuritiesId = oppositeSecuritiesId;
		putUsedField("oppositeSecuritiesId", oppositeSecuritiesId);
	}

	/**
	 * @return Returns the perSuspenseInterest.
	 */
	public double getPerSuspenseInterest()
	{
		return perSuspenseInterest;
	}

	/**
	 * @param perSuspenseInterest The perSuspenseInterest to set.
	 */
	public void setPerSuspenseInterest(double perSuspenseInterest)
	{
		this.perSuspenseInterest = perSuspenseInterest;
		putUsedField("perSuspenseInterest", perSuspenseInterest);
	}

	/**
	 * @return Returns the pledgeSecuritiesQuantity.
	 */
	public double getPledgeSecuritiesQuantity()
	{
		return pledgeSecuritiesQuantity;
	}

	/**
	 * @param pledgeSecuritiesQuantity The pledgeSecuritiesQuantity to set.
	 */
	public void setPledgeSecuritiesQuantity(double pledgeSecuritiesQuantity)
	{
		this.pledgeSecuritiesQuantity = pledgeSecuritiesQuantity;
		putUsedField("pledgeSecuritiesQuantity", pledgeSecuritiesQuantity);
	}

	/**
	 * @return Returns the price.
	 */
	public double getPrice()
	{
		return price;
	}

	/**
	 * @param price The price to set.
	 */
	public void setPrice(double price)
	{
		this.price = price;
		putUsedField("price", price);
	}

	/**
	 * @return Returns the rate.
	 */
	public double getRate()
	{
		return rate;
	}

	/**
	 * @param rate The rate to set.
	 */
	public void setRate(double rate)
	{
		this.rate = rate;
		putUsedField("rate", rate);
	}

	/**
	 * @return Returns the remark.
	 */
	public String getRemark()
	{
		return remark;
	}

	/**
	 * @param remark The remark to set.
	 */
	public void setRemark(String remark)
	{
		this.remark = remark;
		putUsedField("remark", remark);
	}


	/**
	 * @return Returns the securitiesId.
	 */
	public long getSecuritiesId()
	{
		return securitiesId;
	}

	/**
	 * @param securitiesId The securitiesId to set.
	 */
	public void setSecuritiesId(long securitiesId)
	{
		this.securitiesId = securitiesId;
		putUsedField("securitiesId", securitiesId);
	}

	/**
	 * @return Returns the statusId.
	 */
	public long getStatusId()
	{
		return statusId;
	}

	/**
	 * @param statusId The statusId to set.
	 */
	public void setStatusId(long statusId)
	{
		this.statusId = statusId;
		putUsedField("statusId", statusId);
	}

	/**
	 * @return Returns the suspenseInterest.
	 */
	public double getSuspenseInterest()
	{
		return suspenseInterest;
	}

	/**
	 * @param suspenseInterest The suspenseInterest to set.
	 */
	public void setSuspenseInterest(double suspenseInterest)
	{
		this.suspenseInterest = suspenseInterest;
		putUsedField("suspenseInterest", suspenseInterest);
	}

	/**
	 * @return Returns the systemTransactionCode.
	 */
	public String getSystemTransactionCode()
	{
		return systemTransactionCode;
	}

	/**
	 * @param systemTransactionCode The systemTransactionCode to set.
	 */
	public void setSystemTransactionCode(String systemTransactionCode)
	{
		this.systemTransactionCode = systemTransactionCode;
		putUsedField("systemTransactionCode", systemTransactionCode);
	}

	/**
	 * @return Returns the tax.
	 */
	public double getTax()
	{
		return tax;
	}

	/**
	 * @param tax The tax to set.
	 */
	public void setTax(double tax)
	{
		this.tax = tax;
		putUsedField("tax", tax);
	}

	/**
	 * @return Returns the term.
	 */
	public long getTerm()
	{
		return term;
	}

	/**
	 * @param term The term to set.
	 */
	public void setTerm(long term)
	{
		this.term = term;
		putUsedField("term", term);
	}

	/**
	 * @return Returns the timeStamp.
	 */
	public Timestamp getTimeStamp()
	{
		return timeStamp;
	}

	/**
	 * @param timeStamp The timeStamp to set.
	 */
	public void setTimeStamp(Timestamp timeStamp)
	{
		this.timeStamp = timeStamp;
		putUsedField("timeStamp", timeStamp);
	}



	/**
	 * @return Returns the transactionDate.
	 */
	public Timestamp getTransactionDate()
	{
		return transactionDate;
	}

	/**
	 * @param transactionDate The transactionDate to set.
	 */
	public void setTransactionDate(Timestamp transactionDate)
	{
		this.transactionDate = transactionDate;
		putUsedField("transactionDate", transactionDate);
	}

	/**
	 * @return Returns the transactionTypeId.
	 */
	public long getTransactionTypeId()
	{
		return transactionTypeId;
	}

	/**
	 * @param transactionTypeId The transactionTypeId to set.
	 */
	public void setTransactionTypeId(long transactionTypeId)
	{
		this.transactionTypeId = transactionTypeId;
		putUsedField("transactionTypeId", transactionTypeId);
	}

	/**
	 * @return Returns the checkDate.
	 */
	public Timestamp getCheckDate()
	{
		return checkDate;
	}

	/**
	 * @param checkDate The updateDate to set.
	 */
	public void setCheckDate(Timestamp checkDate)
	{
		this.checkDate = checkDate;
		putUsedField("checkDate", checkDate);
	}

	/**
	 * @return Returns the updateUserId.
	 */
	public long getCheckUserId()
	{
		return checkUserId;
	}

	/**
	 * @param updateUserId The updateUserId to set.
	 */
	public void setCheckUserId(long checkUserId)
	{
		this.checkUserId = checkUserId;
		putUsedField("checkUserId", checkUserId);
	}

	/**
	 * @return Returns the valueDate.
	 */
	public Timestamp getValueDate()
	{
		return valueDate;
	}

	/**
	 * @param valueDate The valueDate to set.
	 */
	public void setValueDate(Timestamp valueDate)
	{
		this.valueDate = valueDate;
		putUsedField("valueDate", valueDate);
	}


	/**
	 * Returns the counterpartAccountCode.
	 * @return String
	 */
	public String getCounterpartAccountCode() {
		return counterpartAccountCode;
	}

	/**
	 * Returns the counterpartAccountName.
	 * @return String
	 */
	public String getCounterpartAccountName() {
		return counterpartAccountName;
	}

	/**
	 * Returns the counterpartBankName.
	 * @return String
	 */
	public String getCounterpartBankName() {
		return counterpartBankName;
	}


	/**
	 * Sets the counterpartAccountCode.
	 * @param counterpartAccountCode The counterpartAccountCode to set
	 */
	public void setCounterpartAccountCode(String counterpartAccountCode) {
		this.counterpartAccountCode = counterpartAccountCode;
		//putUsedField("counterpartAccountCode", counterpartAccountCode);
	}

	/**
	 * Sets the counterpartAccountName.
	 * @param counterpartAccountName The counterpartAccountName to set
	 */
	public void setCounterpartAccountName(String counterpartAccountName) {
		this.counterpartAccountName = counterpartAccountName;
		//putUsedField("counterpartAccountName", counterpartAccountName);
	}

	/**
	 * Sets the counterpartBankName.
	 * @param counterpartBankName The counterpartBankName to set
	 */
	public void setCounterpartBankName(String counterpartBankName) {
		this.counterpartBankName = counterpartBankName;
		//putUsedField("counterpartBankName", counterpartBankName);
	}

	/**
	 * Returns the applyFormCode.
	 * @return String
	 */
	public String getApplyFormCode() {
		return applyFormCode;
	}

	/**
	 * Returns the clientName.
	 * @return String
	 */
	public String getClientName() {
		return clientName;
	}

	/**
	 * Returns the transactionTypeName.
	 * @return String
	 */
	public String getTransactionTypeName() {
		return transactionTypeName;
	}

	/**
	 * Sets the applyFormCode.
	 * @param applyFormCode The applyFormCode to set
	 */
	public void setApplyFormCode(String applyFormCode) {
		this.applyFormCode = applyFormCode;
		//putUsedField("applyFormCode", applyFormCode);
	}

	/**
	 * Sets the clientName.
	 * @param clientName The clientName to set
	 */
	public void setClientName(String clientName) {
		this.clientName = clientName;
		//putUsedField("clientName", clientName);
	}

	/**
	 * Sets the transactionTypeName.
	 * @param transactionTypeName The transactionTypeName to set
	 */
	public void setTransactionTypeName(String transactionTypeName) {
		this.transactionTypeName = transactionTypeName;
		//putUsedField("transactionTypeName", transactionTypeName);
	}

	/**
	 * Returns the companyAccountCode.
	 * @return String
	 */
	public String getCompanyAccountCode() {
		return companyAccountCode;
	}

	/**
	 * Returns the companyAccountName.
	 * @return String
	 */
	public String getCompanyAccountName() {
		return companyAccountName;
	}

	/**
	 * Returns the companyBankName.
	 * @return String
	 */
	public String getCompanyBankName() {
		return companyBankName;
	}

	/**
	 * Sets the companyAccountCode.
	 * @param companyAccountCode The companyAccountCode to set
	 */
	public void setCompanyAccountCode(String companyAccountCode) {
		this.companyAccountCode = companyAccountCode;
		//putUsedField("companyAccountCode", companyAccountCode);
	}

	/**
	 * Sets the companyAccountName.
	 * @param companyAccountName The companyAccountName to set
	 */
	public void setCompanyAccountName(String companyAccountName) {
		this.companyAccountName = companyAccountName;
		//putUsedField("companyAccountName", companyAccountName);
	}

	/**
	 * Sets the companyBankName.
	 * @param companyBankName The companyBankName to set
	 */
	public void setCompanyBankName(String companyBankName) {
		this.companyBankName = companyBankName;
		//putUsedField("companyBankName", companyBankName);
	}

	/**
	 * Returns the deliveryDate.
	 * @return Timestamp
	 */
	public Timestamp getDeliveryDate() {
		return deliveryDate;
	}

	/**
	 * Sets the deliveryDate.
	 * @param deliveryDate The deliveryDate to set
	 */
	public void setDeliveryDate(Timestamp deliveryDate) {
		this.deliveryDate = deliveryDate;
		putUsedField("deliveryDate", deliveryDate);
	}

	/**
	 * Returns the pledgeSecuritiesAmount.
	 * @return double
	 */
	public double getPledgeSecuritiesAmount() {
		return pledgeSecuritiesAmount;
	}

	/**
	 * Sets the pledgeSecuritiesAmount.
	 * @param pledgeSecuritiesAmount The pledgeSecuritiesAmount to set
	 */
	public void setPledgeSecuritiesAmount(double pledgeSecuritiesAmount) {
		this.pledgeSecuritiesAmount = pledgeSecuritiesAmount;
		putUsedField("pledgeSecuritiesAmount", pledgeSecuritiesAmount);
	}


	/**
	 * Returns the termTypeId.
	 * @return long
	 */
	public long getTermTypeId() {
		return termTypeId;
	}


	/**
	 * Sets the termTypeId.
	 * @param termTypeId The termTypeId to set
	 */
	public void setTermTypeId(long termTypeId) {
		this.termTypeId = termTypeId;
		putUsedField("termTypeId", termTypeId);
	}

	/**
	 * Returns the currencyId.
	 * @return long
	 */
	public long getCurrencyId() {
		return currencyId;
	}

	/**
	 * Sets the currencyId.
	 * @param currencyId The currencyId to set
	 */
	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
		putUsedField("currencyId", currencyId);
	}

	/**
	 * Returns the officeId.
	 * @return long
	 */
	public long getOfficeId() {
		return officeId;
	}

	/**
	 * Sets the officeId.
	 * @param officeId The officeId to set
	 */
	public void setOfficeId(long officeId) {
		this.officeId = officeId;
		putUsedField("officeId", officeId);
	}

	/**
	 * Returns the registerId.
	 * @return long
	 */
	public long getRegisterId() {
		return registerId;
	}

	/**
	 * Sets the registerId.
	 * @param registerId The registerId to set
	 */
	public void setRegisterId(long registerId) {
		this.registerId = registerId;
		putUsedField("registerId", registerId);
	}

	/**
	 * Returns the counterpartCode.
	 * @return String
	 */
	public String getCounterpartCode() {
		return counterpartCode;
	}

	/**
	 * Returns the counterpartName.
	 * @return String
	 */
	public String getCounterpartName() {
		return counterpartName;
	}

	/**
	 * Sets the counterpartCode.
	 * @param counterpartCode The counterpartCode to set
	 */
	public void setCounterpartCode(String counterpartCode) {
		this.counterpartCode = counterpartCode;
		//putUsedField("counterpartCode", counterpartCode);
	}

	/**
	 * Sets the counterpartName.
	 * @param counterpartName The counterpartName to set
	 */
	public void setCounterpartName(String counterpartName) {
		this.counterpartName = counterpartName;
		//putUsedField("counterpartName", counterpartName);
	}

	/**
	 * Returns the relatedDeliveryOrderID.
	 * @return long
	 */
	public long getRelatedDeliveryOrderID() {
		return relatedDeliveryOrderID;
	}

	/**
	 * Sets the relatedDeliveryOrderID.
	 * @param relatedDeliveryOrderID The relatedDeliveryOrderID to set
	 */
	public void setRelatedDeliveryOrderID(long relatedDeliveryOrderID) {
		this.relatedDeliveryOrderID = relatedDeliveryOrderID;
		putUsedField("relatedDeliveryOrderID", relatedDeliveryOrderID);
	}

	/**
	 * @return Returns the isCarryCost.
	 */
	public long getIsCarryCost() {
		return isCarryCost;
	}
	/**
	 * @param isCarryCost The isCarryCost to set.
	 */
	public void setIsCarryCost(long isCarryCost) {
		putUsedField("isCarryCost", isCarryCost);
		this.isCarryCost = isCarryCost;
	}
	/**
	 * @return Returns the isDelivery.
	 */
	public long getIsDelivery() {
		return isDelivery;
	}
	/**
	 * @param isDelivery The isDelivery to set.
	 */
	public void setIsDelivery(long isDelivery) {
		putUsedField("isDelivery", isDelivery);
		this.isDelivery = isDelivery;
	}
	/**
	 * @return Returns the unitNetCost.
	 */
	public double getUnitNetCost() {
		return unitNetCost;
	}
	/**
	 * @param unitNetCost The unitNetCost to set.
	 */
	public void setUnitNetCost(double unitNetCost) {
		putUsedField("unitNetCost", unitNetCost);
		this.unitNetCost = unitNetCost;
	}
	/**
	 * @return Returns the unitNetProfitLoss.
	 */
	public double getUnitNetProfitLoss() {
		return unitNetProfitLoss;
	}
	/**
	 * @param unitNetProfitLoss The unitNetProfitLoss to set.
	 */
	public void setUnitNetProfitLoss(double unitNetProfitLoss) {
		putUsedField("unitNetProfitLoss", unitNetProfitLoss);
		this.unitNetProfitLoss = unitNetProfitLoss;
	}
	/**
	 * @return Returns the unitProfitLoss.
	 */
	public double getUnitProfitLoss() {
		return unitProfitLoss;
	}
	/**
	 * @param unitProfitLoss The unitProfitLoss to set.
	 */
	public void setUnitProfitLoss(double unitProfitLoss) {
		putUsedField("unitProfitLoss", unitProfitLoss);
		this.unitProfitLoss = unitProfitLoss;
	}
	/**
	 * @return Returns the unitCost.
	 */
	public double getUnitCost() {
		return unitCost;
	}
	/**
	 * @param unitCost The unitCost to set.
	 */
	public void setUnitCost(double unitCost) {
		putUsedField("unitCost", unitCost);
		this.unitCost = unitCost;
	}
	/**
	 * Returns the deleteDate.
	 * @return Timestamp
	 */
	public Timestamp getDeleteDate() {
		return deleteDate;
	}

	/**
	 * Returns the deleteUserId.
	 * @return long
	 */
	public long getDeleteUserId() {
		return deleteUserId;
	}

	/**
	 * Returns the oppositeQuantity.
	 * @return long
	 */
	public double getOppositeQuantity() {
		return oppositeQuantity;
	}

	/**
	 * Returns the pledgeSecuritiesId.
	 * @return long
	 */
	public long getPledgeSecuritiesId() {
		return pledgeSecuritiesId;
	}

	/**
	 * Returns the quantity.
	 * @return long
	 */
	public double getQuantity() {
		return quantity;
	}

	/**
	 * Returns the updateDate.
	 * @return Timestamp
	 */
	public Timestamp getUpdateDate() {
		return updateDate;
	}

	/**
	 * Returns the updateUserId.
	 * @return long
	 */
	public long getUpdateUserId() {
		return updateUserId;
	}

	/**
	 * Sets the deleteDate.
	 * @param deleteDate The deleteDate to set
	 */
	public void setDeleteDate(Timestamp deleteDate) {
		this.deleteDate = deleteDate;
		putUsedField("deleteDate", deleteDate);
	}

	/**
	 * Sets the deleteUserId.
	 * @param deleteUserId The deleteUserId to set
	 */
	public void setDeleteUserId(long deleteUserId) {
		this.deleteUserId = deleteUserId;
		putUsedField("deleteUserId", deleteUserId);
	}

	/**
	 * Sets the oppositeQuantity.
	 * @param oppositeQuantity The oppositeQuantity to set
	 */
	public void setOppositeQuantity(double oppositeQuantity) {
		this.oppositeQuantity = oppositeQuantity;
		putUsedField("oppositeQuantity", oppositeQuantity);
	}

	/**
	 * Sets the pledgeSecuritiesId.
	 * @param pledgeSecuritiesId The pledgeSecuritiesId to set
	 */
	public void setPledgeSecuritiesId(long pledgeSecuritiesId) {
		this.pledgeSecuritiesId = pledgeSecuritiesId;
		putUsedField("pledgeSecuritiesId", pledgeSecuritiesId);
	}

	/**
	 * Sets the quantity.
	 * @param quantity The quantity to set
	 */
	public void setQuantity(double quantity) {
		this.quantity = quantity;
		putUsedField("quantity", quantity);
	}

	/**
	 * Sets the updateDate.
	 * @param updateDate The updateDate to set
	 */
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
		putUsedField("updateDate", updateDate);
	}

	/**
	 * Sets the updateUserId.
	 * @param updateUserId The updateUserId to set
	 */
	public void setUpdateUserId(long updateUserId) {
		this.updateUserId = updateUserId;
		putUsedField("updateUserId", updateUserId);
	}


	/**
	 * Returns the stockHolderAccountCode.
	 * @return String
	 */
	public String getStockHolderAccountCode() {
		return stockHolderAccountCode;
	}

	/**
	 * Returns the stockHolderAccountName.
	 * @return String
	 */
	public String getStockHolderAccountName() {
		return stockHolderAccountName;
	}

	/**
	 * Sets the stockHolderAccountCode.
	 * @param stockHolderAccountCode The stockHolderAccountCode to set
	 */
	public void setStockHolderAccountCode(String stockHolderAccountCode) {
		this.stockHolderAccountCode = stockHolderAccountCode;
	}

	/**
	 * Sets the stockHolderAccountName.
	 * @param stockHolderAccountName The stockHolderAccountName to set
	 */
	public void setStockHolderAccountName(String stockHolderAccountName) {
		this.stockHolderAccountName = stockHolderAccountName;
	}

	/**
	 * @return Returns the isCheckOverDraft.
	 */
	public long getIsCheckOverDraft()
	{
		return isCheckOverDraft;
	}

	/**
	 * @param isCheckOverDraft The isCheckOverDraft to set.
	 */
	public void setIsCheckOverDraft(long isCheckOverDraft)
	{
		this.isCheckOverDraft = isCheckOverDraft;
	}

	/**
	 * @return Returns the isSettlementInvoke.
	 */
	public long getIsSettlementInvoke() {
		return isSettlementInvoke;
	}
	/**
	 * @param isSettlementInvoke The isSettlementInvoke to set.
	 */
	public void setIsSettlementInvoke(long isSettlementInvoke) {
		this.isSettlementInvoke = isSettlementInvoke;
	}
	/**
	 * @return Returns the isRepaid.
	 */
	public long getIsRepaid()
	{
		return isRepaid;
	}

	/**
	 * @param isRepaid The isRepaid to set.
	 */
	public void setIsRepaid(long isRepaid)
	{
		this.isRepaid = isRepaid;
	}

	/**
	 * Returns the nextCheckUserId.
	 * @return long
	 */
	public long getNextCheckUserId() {
		return nextCheckUserId;
	}

	/**
	 * Sets the nextCheckUserId.
	 * @param nextCheckUserId The nextCheckUserId to set
	 */
	public void setNextCheckUserId(long nextCheckUserId) {
		this.nextCheckUserId = nextCheckUserId;
	}

	/**
	 * @return Returns the isCheckOverStock.
	 */
	public long getIsCheckOverStock() {
		return isCheckOverStock;
	}
	/**
	 * @param isCheckOverStock The isCheckOverStock to set.
	 */
	public void setIsCheckOverStock(long isCheckOverStock) {
		this.isCheckOverStock = isCheckOverStock;
	}
	/**
	 * Returns the isRelatedByNoticeForm.
	 * @return long
	 */
	public long getIsRelatedByNoticeForm() {
		return IsRelatedByNoticeForm;
	}

	/**
	 * Sets the isRelatedByNoticeForm.
	 * @param isRelatedByNoticeForm The isRelatedByNoticeForm to set
	 */
	public void setIsRelatedByNoticeForm(long isRelatedByNoticeForm) {
		IsRelatedByNoticeForm = isRelatedByNoticeForm;
		putUsedField("IsRelatedByNoticeForm", IsRelatedByNoticeForm);
	}

	/**
	 * Returns the ownerTypeId.
	 * @return long
	 */
	public long getOwnerTypeId() {
		return OwnerTypeId;
	}

	/**
	 * Sets the ownerTypeId.
	 * @param ownerTypeId The ownerTypeId to set
	 */
	public void setOwnerTypeId(long ownerTypeId) {
		OwnerTypeId = ownerTypeId;
		putUsedField("OwnerTypeId", OwnerTypeId);
	}

	/**
	 * @return Returns the isViolation.
	 */
	public long getIsViolation() {
		return isViolation;
	}
	/**
	 * @param isViolation The isViolation to set.
	 */
	public void setIsViolation(long isViolation) {
		putUsedField("isViolation", isViolation);
		this.isViolation = isViolation;
	}
	/**
	 * @return Returns the reasonOfViolation.
	 */
	public String getReasonOfViolation() {
		return reasonOfViolation;
	}
	/**
	 * @param reasonOfViolation The reasonOfViolation to set.
	 */
	public void setReasonOfViolation(String reasonOfViolation) {
		putUsedField("reasonOfViolation", reasonOfViolation);
		this.reasonOfViolation = reasonOfViolation;
	}

	public long getDefterm() {
		return defterm;
	}

	public void setDefterm(long defterm) {
		this.defterm = defterm;
	}

	public long getTypeId() {
		return typeId;
	}

	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}

	public long getActionTypeId() {
		return actionTypeId;
	}

	public void setActionTypeId(long actionTypeId) {
		this.actionTypeId = actionTypeId;
	}
}
