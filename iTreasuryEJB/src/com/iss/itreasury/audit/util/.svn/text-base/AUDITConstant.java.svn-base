
package com.iss.itreasury.audit.util;

import com.iss.itreasury.util.Constant;

public class AUDITConstant extends com.iss.itreasury.util.Constant
{
	/**
	 * 监审稽核专用
	 * @author zcwang
	 *
	 */
	public static class AmountTypeForAudit {
		public static final int AmountType_1 = 1; // 本金

		public static final int AmountType_2 = 2; // 利息支出 add by xfma 存款应该是“利息支出”

		public static final int AmountType_3 = 3; // 应付利息 add by xfma 存款应该是“应付利息”
		
		public static final int AmountType_4 = 4; // 手续费 贷款使用
		
		public static final int AmountType_5 = 5; // 利息税费 贷款使用
		
		public static final int AmountType_6 = 6; // 利息收入 add by xfma 贷款应该是“利息收入”

		public static final int AmountType_7 = 7; // 应收利息 add by xfma 贷款应该是“应收利息”

		public static final String getName(long lCode) {
			String strReturn = "";
			switch ((int) lCode) {
			case AmountType_1:
				strReturn = "本金";
				break;
			case AmountType_2:
				strReturn = "利息支出";
				break;
			case AmountType_3:
				strReturn = "应付利息";
				break;
			case AmountType_4:
				strReturn = "手续费";
				break;
			case AmountType_5:
				strReturn = "利息税费";
				break;
			case AmountType_6://add by xfma 贷款应该是“利息收入”
				strReturn = "利息收入";
				break;
			case AmountType_7://add by xfma 贷款应该是“应收利息”
				strReturn = "应收利息";
				break;
			
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { AmountType_1, AmountType_2, AmountType_3, AmountType_4, AmountType_5
					, AmountType_6, AmountType_7};
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.audit.util.AUDITConstant$AmountTypeForAudit",
							officeID, currencyID);
		}
		public static final long[] getDepositCode() {
			long[] lTemp = { AmountType_1, AmountType_2, AmountType_3
					 };
			return lTemp;
		}
		public static final long[] getLoanCode() {
			long[] lTemp = { AmountType_1, AmountType_6, AmountType_7, AmountType_4, AmountType_5
					 };
			return lTemp;
		}

	}
	/**
	 * 监审稽核专用
	 * @author zcwang
	 *
	 */
	public static class AmountShowTypeForAudit {
		public static final int AmountShowType_1 = 1; // 期初余额

		public static final int AmountShowType_2 = 2; // 借方发生额

		public static final int AmountShowType_3 = 3; // 贷方发生额

		public static final int AmountShowType_4 = 4; // 期末余额
		
		public static final String getName(long lCode) {
			String strReturn = "";
			switch ((int) lCode) {
			case AmountShowType_1:
				strReturn = "期初余额";
				break;
			case AmountShowType_2:
				strReturn = "借方发生额";
				break;
			case AmountShowType_3:
				strReturn = "贷方发生额";
				break;
			case AmountShowType_4:
				strReturn = "期末余额";
				break;
			
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { AmountShowType_1, AmountShowType_2, AmountShowType_3,AmountShowType_4
					 };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.audit.util.AUDITConstant$AmountShowTypeForAudit",
							officeID, currencyID);
		}

	}
}