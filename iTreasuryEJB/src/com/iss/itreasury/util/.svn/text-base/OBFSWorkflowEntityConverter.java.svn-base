package com.iss.itreasury.util;

import java.util.HashMap;
import java.util.Map;

import com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.util.OBFSWorkflowEntityConverter.MapKeys;

public class OBFSWorkflowEntityConverter {

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
		
        //通知存款品种
		public static String lNoticeDay = "lNoticeDay";

		//定期存款期限
		public static String lFixedDepositTime = "lFixedDepositTime";


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

		if (objDataEntity instanceof FinanceInfo) 
		{
			// 付款业务、定期支取、定期开立、 到期续存/转存、通知开立/支取
			return financeInfo2Map(inutParameterInfo);
		}
		else
		{
			return new HashMap();
		}

	}

	/**
	 *  付款业务、定期支取、定期开立、 到期续存/转存、通知开立/支取
	 * 
	 * @param inutParameterInfo
	 * @return
	 */
	private static Map financeInfo2Map(
			InutParameterInfo inutParameterInfo) {

		FinanceInfo financeInfo = (FinanceInfo) inutParameterInfo
				.getDataEntity();

		Map hmpReturn = new HashMap();

		hmpReturn.put(MapKeys.TRANSTYPEID, String.valueOf(financeInfo.getTransType()));
		hmpReturn.put(MapKeys.CLIENTID, String.valueOf(financeInfo.getClientID()));
		hmpReturn.put(MapKeys.OFFICEID, String.valueOf(financeInfo.getOfficeID()));
		hmpReturn.put(MapKeys.CURRENCYID, String.valueOf(financeInfo.getCurrencyID()));
		hmpReturn.put(MapKeys.MODULEID, String.valueOf(inutParameterInfo.getModuleID()));
		hmpReturn.put(MapKeys.RECEIVEACCOUTID, String.valueOf(financeInfo.getPayeeAcctID()));
		hmpReturn.put(MapKeys.PAYACCOUNTID, String.valueOf(financeInfo.getPayerAcctID()));
		hmpReturn.put(MapKeys.AMOUNT, String.valueOf(financeInfo.getAmount()));
		hmpReturn.put(MapKeys.lFixedDepositTime, String.valueOf(financeInfo.getFixedDepositTime()));
		hmpReturn.put(MapKeys.lNoticeDay, String.valueOf(financeInfo.getNoticeDay()));
		return hmpReturn;
	}



}
