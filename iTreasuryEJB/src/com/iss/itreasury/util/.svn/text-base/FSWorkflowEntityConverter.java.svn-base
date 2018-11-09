package com.iss.itreasury.util;

import java.util.HashMap;
import java.util.Map;

import com.iss.itreasury.loan.loanapply.dataentity.LoanApplyInfo;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedChangeInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedDrawInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedOpenInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedContinueInfo;
import com.iss.itreasury.settlement.transgeneralledger.dataentity.TransGeneralLedgerInfo;
import com.iss.itreasury.settlement.transspecial.dataentity.TransSpecialOperationInfo;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;

public class FSWorkflowEntityConverter {
	public static class MapKeys {
		// 业务类型
		public static String TRANSTYPEID = "transTypeId";

		// 客户ID
		public static String CLIENTID = "clientId";

		// 办事处ID
		public static String OFFICEID = "officeId";

		// 货币ID
		public static String CURRENCYID = "currencyId";

		// 模块ID
		public static String MODULEID = "moduleId";

		// 收款方账户ID
		public static String RECEIVEACCOUTID = "receiveAccoutId";

		// 付款方账户ID
		public static String PAYACCOUNTID = "payAccountId";

		// 金额
		public static String AMOUNT = "amount";

		// 操作类型ID(信贷)
		public static String ACTIONID = "actionId";

		// 业务子类型ID(信贷)
		public static String TRANSSUBTYPEID = "transSubTypeId";

		// 利率(信贷)
		public static String RATE = "rate";

		// 期限(信贷)
		public static String INTERVALNUM = "intervalNum";
	}

	/**
	 * 将实体转换为HashMap
	 * @param inutParameterInfo
	 * @return
	 */
	public static Map entity2Map(InutParameterInfo inutParameterInfo) {

		// 数据实体
		Object objDataEntity = inutParameterInfo.getDataEntity();

		if (objDataEntity instanceof TransCurrentDepositInfo) {
			// 活期
			return transCurrentDepositInfo2Map(inutParameterInfo);

		} else if (objDataEntity instanceof TransFixedOpenInfo) {
			// 定期开立、通知存款开立、通知存款支取
			return transFixedOpenInfo2Map(inutParameterInfo);

		} else if (objDataEntity instanceof TransFixedDrawInfo) {
			// 定期支取
			return transFixedDrawInfo2Map(inutParameterInfo);

		} else if (objDataEntity instanceof TransFixedContinueInfo) {
			// 定期存款续期转存
			return transFixedContinueInfo2Map(inutParameterInfo);

		} else if (objDataEntity instanceof TransFixedChangeInfo) {
			// 换开定期存单交易
			return transFixedChangeInfo2Map(inutParameterInfo);

		} else if (objDataEntity instanceof TransGeneralLedgerInfo) {
			// 总账业务
			return transGeneralLedgerInfo2Map(inutParameterInfo);

		} else if (objDataEntity instanceof TransSpecialOperationInfo) {
			// 特殊业务
			return transSpecialOperationInfo2Map(inutParameterInfo);

		} else {
			return new HashMap();
		}

	}
	
	/**
	 * 将实体转换为HashMap-信贷模块
	 * @param inutParameterInfo
	 * @return
	 */
	public static Map entity2LoanMap(InutParameterInfo inutParameterInfo) {

		// 数据实体
		Object objDataEntity = inutParameterInfo.getDataEntity();

		if (objDataEntity instanceof LoanApplyInfo) {
			// 贷款申请
			return loanApplyInfo2Map(inutParameterInfo);

		} else {
			return new HashMap();
		}

	}
	
	/**
	 * 贷款申请
	 * @param inutParameterInfo
	 * @return
	 */
	private static Map loanApplyInfo2Map(
			InutParameterInfo inutParameterInfo) {

		LoanApplyInfo loanApplyInfo = (LoanApplyInfo) inutParameterInfo
				.getDataEntity();

		Map hmpReturn = new HashMap();

		hmpReturn.put(MapKeys.TRANSTYPEID, String.valueOf(loanApplyInfo
				.getTypeID()));
		hmpReturn.put(MapKeys.CLIENTID, String.valueOf(loanApplyInfo
				.getInputUserID()));
		hmpReturn.put(MapKeys.OFFICEID, String.valueOf(loanApplyInfo
				.getOfficeID()));
		hmpReturn.put(MapKeys.CURRENCYID, String.valueOf(loanApplyInfo
				.getCurrencyID()));
		hmpReturn.put(MapKeys.MODULEID, String.valueOf(inutParameterInfo
				.getModuleID()));
		hmpReturn.put(MapKeys.AMOUNT, String.valueOf(loanApplyInfo
				.getExamineAmount()));

		return hmpReturn;
	}

	/**
	 * 活期
	 * 
	 * @param inutParameterInfo
	 * @return
	 */
	private static Map transCurrentDepositInfo2Map(
			InutParameterInfo inutParameterInfo) {

		TransCurrentDepositInfo transCurrentDepositInfo = (TransCurrentDepositInfo) inutParameterInfo
				.getDataEntity();

		Map hmpReturn = new HashMap();

		hmpReturn.put(MapKeys.TRANSTYPEID, String
				.valueOf(transCurrentDepositInfo.getTransactionTypeID()));
		hmpReturn.put(MapKeys.CLIENTID, String.valueOf(transCurrentDepositInfo
				.getInputUserID()));
		hmpReturn.put(MapKeys.OFFICEID, String.valueOf(transCurrentDepositInfo
				.getOfficeID()));
		hmpReturn.put(MapKeys.CURRENCYID, String
				.valueOf(transCurrentDepositInfo.getCurrencyID()));
		hmpReturn.put(MapKeys.MODULEID, String.valueOf(inutParameterInfo
				.getModuleID()));
		hmpReturn.put(MapKeys.RECEIVEACCOUTID, String
				.valueOf(transCurrentDepositInfo.getReceiveAccountID()));
		hmpReturn.put(MapKeys.PAYACCOUNTID, String
				.valueOf(transCurrentDepositInfo.getPayAccountID()));
		hmpReturn.put(MapKeys.AMOUNT, String.valueOf(transCurrentDepositInfo
				.getAmount()));

		return hmpReturn;
	}

	/**
	 * 定期开立、通知存款开立、通知存款支取
	 * 
	 * @param inutParameterInfo
	 * @return
	 */
	private static Map transFixedOpenInfo2Map(
			InutParameterInfo inutParameterInfo) {
		TransFixedOpenInfo transFixedOpenInfo = (TransFixedOpenInfo) inutParameterInfo
				.getDataEntity();

		Map hmpReturn = new HashMap();
		hmpReturn.put(MapKeys.TRANSTYPEID, String.valueOf(transFixedOpenInfo
				.getTransactionTypeID()));
		hmpReturn.put(MapKeys.CLIENTID, String.valueOf(transFixedOpenInfo
				.getInputUserID()));
		hmpReturn.put(MapKeys.OFFICEID, String.valueOf(transFixedOpenInfo
				.getOfficeID()));
		hmpReturn.put(MapKeys.CURRENCYID, String.valueOf(transFixedOpenInfo
				.getCurrencyID()));
		hmpReturn.put(MapKeys.MODULEID, String.valueOf(inutParameterInfo
				.getModuleID()));
//		hmpReturn.put(MapKeys.PAYACCOUNTID, String
//				.valueOf(transFixedOpenInfo.getCurrentAccountID()));
//		hmpReturn.put(MapKeys.RECEIVEACCOUTID, String
//				.valueOf(transFixedOpenInfo()));
		hmpReturn.put(MapKeys.AMOUNT, String.valueOf(transFixedOpenInfo
				.getAmount()));
		return hmpReturn;
	}

	/**
	 * 定期支取
	 * 
	 * @param inutParameterInfo
	 * @return
	 */
	private static Map transFixedDrawInfo2Map(
			InutParameterInfo inutParameterInfo) {

		TransFixedDrawInfo transFixedDrawInfo = (TransFixedDrawInfo) inutParameterInfo
				.getDataEntity();

		Map hmpReturn = new HashMap();
		hmpReturn.put(MapKeys.TRANSTYPEID, String.valueOf(transFixedDrawInfo
				.getTransactionTypeID()));
		hmpReturn.put(MapKeys.CLIENTID, String.valueOf(transFixedDrawInfo
				.getInputUserID()));
		hmpReturn.put(MapKeys.OFFICEID, String.valueOf(transFixedDrawInfo
				.getOfficeID()));
		hmpReturn.put(MapKeys.CURRENCYID, String.valueOf(transFixedDrawInfo
				.getCurrencyID()));
		hmpReturn.put(MapKeys.MODULEID, String.valueOf(inutParameterInfo
				.getModuleID()));
		// hmpReturn.put(MapKeys.RECEIVEACCOUTID,
		// String.valueOf(transFixedDrawInfo.));
		// hmpReturn.put(MapKeys.PAYACCOUNTID,transFixedDrawInfo.);
		hmpReturn.put(MapKeys.AMOUNT, String.valueOf(transFixedDrawInfo
				.getAmount()));
		return hmpReturn;
	}

	/**
	 * 定期存款续期转存
	 * 
	 * @param inutParameterInfo
	 * @return
	 */
	private static Map transFixedContinueInfo2Map(
			InutParameterInfo inutParameterInfo) {
		TransFixedContinueInfo transFixedContinueInfo = (TransFixedContinueInfo) inutParameterInfo
				.getDataEntity();

		Map hmpReturn = new HashMap();
		hmpReturn.put(MapKeys.TRANSTYPEID, String
				.valueOf(transFixedContinueInfo.getTransactionTypeID()));
		hmpReturn.put(MapKeys.CLIENTID, String.valueOf(transFixedContinueInfo
				.getInputUserID()));
		hmpReturn.put(MapKeys.OFFICEID, String.valueOf(transFixedContinueInfo
				.getOfficeID()));
		hmpReturn.put(MapKeys.CURRENCYID, String.valueOf(transFixedContinueInfo
				.getCurrencyID()));
		hmpReturn.put(MapKeys.MODULEID, String.valueOf(inutParameterInfo
				.getModuleID()));
		// hmpReturn.put(MapKeys.RECEIVEACCOUTID, transFixedContinueInfo.);
		// hmpReturn.put(MapKeys.PAYACCOUNTID,"");
		hmpReturn.put(MapKeys.AMOUNT, String.valueOf(transFixedContinueInfo
				.getAmount()));
		hmpReturn.put(MapKeys.RATE, String.valueOf(transFixedContinueInfo
				.getRate()));
		return hmpReturn;
	}

	/**
	 * 换开定期存单交易
	 * 
	 * @param inutParameterInfo
	 * @return
	 */
	private static Map transFixedChangeInfo2Map(
			InutParameterInfo inutParameterInfo) {
		TransFixedChangeInfo transFixedChangeInfo = (TransFixedChangeInfo) inutParameterInfo
				.getDataEntity();
		Map hmpReturn = new HashMap();
		hmpReturn.put(MapKeys.TRANSTYPEID, String.valueOf(transFixedChangeInfo
				.getTransactionTypeID()));
		hmpReturn.put(MapKeys.CLIENTID, String.valueOf(transFixedChangeInfo
				.getInputUserID()));
		hmpReturn.put(MapKeys.OFFICEID, String.valueOf(transFixedChangeInfo
				.getOfficeID()));
		hmpReturn.put(MapKeys.CURRENCYID, String.valueOf(transFixedChangeInfo
				.getCurrencyID()));
		hmpReturn.put(MapKeys.MODULEID, String.valueOf(inutParameterInfo
				.getModuleID()));
		// hmpReturn.put(MapKeys.RECEIVEACCOUTID, "");
		// hmpReturn.put(MapKeys.PAYACCOUNTID,"");
		hmpReturn.put(MapKeys.AMOUNT, String.valueOf(transFixedChangeInfo
				.getAmount()));
		
		return hmpReturn;
	}

	/**
	 * 总账业务
	 * 
	 * @param inutParameterInfo
	 * @return
	 */
	private static Map transGeneralLedgerInfo2Map(
			InutParameterInfo inutParameterInfo) {
		TransGeneralLedgerInfo transGeneralLedgerInfo = (TransGeneralLedgerInfo) inutParameterInfo
				.getDataEntity();
		Map hmpReturn = new HashMap();
		hmpReturn.put(MapKeys.TRANSTYPEID, String
				.valueOf(transGeneralLedgerInfo.getTransActionTypeID()));
		hmpReturn.put(MapKeys.CLIENTID, String.valueOf(transGeneralLedgerInfo
				.getInputUserID()));
		hmpReturn.put(MapKeys.OFFICEID, String.valueOf(transGeneralLedgerInfo
				.getOfficeID()));
		hmpReturn.put(MapKeys.CURRENCYID, String.valueOf(transGeneralLedgerInfo
				.getCurrencyID()));
		hmpReturn.put(MapKeys.MODULEID, String.valueOf(inutParameterInfo
				.getModuleID()));
		// hmpReturn.put(MapKeys.RECEIVEACCOUTID, "");
		// hmpReturn.put(MapKeys.PAYACCOUNTID,"");
		hmpReturn.put(MapKeys.AMOUNT, String.valueOf(transGeneralLedgerInfo
				.getAmount()));
		return hmpReturn;
	}

	/**
	 * 特殊业务
	 * 
	 * @param inutParameterInfo
	 * @return
	 */
	private static Map transSpecialOperationInfo2Map(
			InutParameterInfo inutParameterInfo) {
		TransSpecialOperationInfo transSpecialOperationInfo = (TransSpecialOperationInfo) inutParameterInfo
				.getDataEntity();
		Map hmpReturn = new HashMap();
		hmpReturn.put(MapKeys.TRANSTYPEID, String
				.valueOf(transSpecialOperationInfo.getNtransactiontypeid()));
		hmpReturn.put(MapKeys.CLIENTID, String
				.valueOf(transSpecialOperationInfo.getNinputuserid()));
		hmpReturn.put(MapKeys.OFFICEID, String
				.valueOf(transSpecialOperationInfo.getNofficeid()));
		hmpReturn.put(MapKeys.CURRENCYID, String
				.valueOf(transSpecialOperationInfo.getNcurrencyid()));
		hmpReturn.put(MapKeys.MODULEID, String.valueOf(inutParameterInfo
				.getModuleID()));
		hmpReturn.put(MapKeys.RECEIVEACCOUTID, String
				.valueOf(transSpecialOperationInfo.getNreceiveaccountid()));
		hmpReturn.put(MapKeys.PAYACCOUNTID, String
				.valueOf(transSpecialOperationInfo.getNpayaccountid()));
		// hmpReturn.put(MapKeys.AMOUNT,String.valueOf(transSpecialOperationInfo));
		return hmpReturn;
	}
	
	/**
	 * add by kevin(刘连凯)2011-05-25
	 * 将实体转换为HashMap-信贷模块
	 * @param inutParameterInfo
	 * @return
	 */
	public static Map LoanMap(InutParameterInfo inutParameterInfo) {
		Map hmpReturn = new HashMap();
		//hmpReturn=entity2LoanMap(inutParameterInfo);
		if (inutParameterInfo!=null&&hmpReturn.size()<=0) {			
			if(inutParameterInfo.getOfficeID()>0)
			    hmpReturn.put(MapKeys.OFFICEID, String.valueOf(inutParameterInfo.getOfficeID()));
			if(inutParameterInfo.getCurrencyID()>0)
			    hmpReturn.put(MapKeys.CURRENCYID, String.valueOf(inutParameterInfo.getCurrencyID()));
			if(inutParameterInfo.getModuleID()>0)
			    hmpReturn.put(MapKeys.MODULEID, String.valueOf(inutParameterInfo.getModuleID()));
			if(inutParameterInfo.getTransTypeID()>0)
			    hmpReturn.put(MapKeys.TRANSTYPEID, String.valueOf(inutParameterInfo.getTransTypeID()));
			if(inutParameterInfo.getUserID()>0)
			    hmpReturn.put(MapKeys.CLIENTID, String.valueOf(inutParameterInfo.getUserID()));
			if(inutParameterInfo.getAmount()>0)
			    hmpReturn.put(MapKeys.AMOUNT, String.valueOf(inutParameterInfo.getAmount()));				
		}
		return hmpReturn;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
