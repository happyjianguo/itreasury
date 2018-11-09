package com.iss.itreasury.util;

import java.util.HashMap;
import java.util.Map;

import com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.util.OBFSWorkflowEntityConverter.MapKeys;

public class OBFSWorkflowEntityConverter {

	public static class MapKeys {
		// ҵ������
		public static String TRANSTYPEID = "transTypeId";

		// �ͻ�ID
		public static String CLIENTID = "clientId";

		// ���´�ID
		public static String OFFICEID = "officeId";

		// ����ID
		public static String CURRENCYID = "currencyId";

		// ģ��ID
		public static String MODULEID = "moduleId";

		// �տ�˻�ID
		public static String RECEIVEACCOUTID = "receiveAccoutId";

		// ����˻�ID
		public static String PAYACCOUNTID = "payAccountId";

		// ���
		public static String AMOUNT = "amount";
		
        //֪ͨ���Ʒ��
		public static String lNoticeDay = "lNoticeDay";

		//���ڴ������
		public static String lFixedDepositTime = "lFixedDepositTime";


		// ��������ID(�Ŵ�)
		public static String ACTIONID = "actionId";

		// ҵ��������ID(�Ŵ�)
		public static String TRANSSUBTYPEID = "transSubTypeId";

		// ����(�Ŵ�)
		public static String RATE = "rate";

		// ����(�Ŵ�)
		public static String INTERVALNUM = "intervalNum";
	}

	/**
	 * ��ʵ��ת��ΪHashMap
	 * @param inutParameterInfo
	 * @return
	 */
	public static Map entity2Map(InutParameterInfo inutParameterInfo) {

		// ����ʵ��
		Object objDataEntity = inutParameterInfo.getDataEntity();

		if (objDataEntity instanceof FinanceInfo) 
		{
			// ����ҵ�񡢶���֧ȡ�����ڿ����� ��������/ת�桢֪ͨ����/֧ȡ
			return financeInfo2Map(inutParameterInfo);
		}
		else
		{
			return new HashMap();
		}

	}

	/**
	 *  ����ҵ�񡢶���֧ȡ�����ڿ����� ��������/ת�桢֪ͨ����/֧ȡ
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
