/*
 * Created on 2003-8-7
 *  
 */
package com.iss.itreasury.archivesmanagement.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.jsp.JspWriter;

// import com.iss.itreasury.bs.constant.Direction;
// import com.iss.itreasury.bs.constant.PriorityType;
// import com.iss.itreasury.bs.util.BranchIdentify;
import com.iss.itreasury.archivesmanagement.dao.ArchivesManagementDao;
import com.iss.itreasury.archivesmanagement.dataentity.ArchivesManagementInfo;
import com.iss.itreasury.loan.base.LoanException;
import com.iss.itreasury.loan.setting.bizlogic.LoanTypeSettingBiz;
import com.iss.itreasury.loan.setting.dataentity.LoanTypeSettingInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.account.dataentity.AccountTypeInfo;
// import com.iss.itreasury.settlement.bizdelegation.BankServiceDelegation;
import com.iss.itreasury.settlement.setting.dao.Sett_SpecialOperationDAO;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log;

/** 
 * 包含结算子系统所有的常量
 * 
 * @author wlming
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SETTConstant extends com.iss.itreasury.util.Constant {
	/**
	 * boolean值常量类
	 * 
	 * @author wlming
	 * 
	 * To change the template for this generated type comment go to
	 * Window>Preferences>Java>Code Generation>Code and Comments
	 */
	public static class BooleanValue {
		public static final long ISFALSE = 0; // false or no

		public static final long ISTRUE = 1; // true or yes
	}

	/**
	 * 银行票据状态常量类
	 */

	// 票据状态
	public static class BillStatus {
		// 票据状态
		public static final long KC = 1; // 库存

		public static final long ZT = 2; // 在途

		public static final long YHG = 3; // 已回够

	}
	
	public static class SubInvestMentType {
		public static final long TYCR = 10; // 同业拆入

		public static final long TYCC = 11; // 同业拆出

		public static final long CRHK = 12; // 拆入还款

		public static final long CCHK = 13; // 拆出还款

		public static final long HG = 18; // 回购18

		public static final long ZHGDQ = 19; // 正回购到期19

		public static final long NHG = 20; // 逆回购20

		public static final long NHGDQ = 21; // 逆回购到期21

		public static final long RZHG = 22; // 融资回购22

		public static final long RZGH = 23; // 融资购回23

		public static final long RJHG = 24; // 融券回购24

		public static final long RJGH = 25; // 融券购回25

		public static final long YHPJMR = 26; // 央行票据买入26

		public static final long YHPJMC = 27; // 央行票据卖出27

		public static final long YHJGZMR = 28; // 银行间国债买入28

		public static final long YHJGZMC = 29; // 银行间国债卖出29

		public static final long JRZMR = 30; // 金融债买入30

		public static final long JRZMC = 31; // 金融债卖出31

		public static final long HBSCZJMR = 32; // 货币市场基金买入32

		public static final long HBSCZJMC = 33; // 货币市场基金卖出33

		public static final long QTYHJXJMR = 34; // 其他银行间现券买入34

		public static final long QTYHJXJMC = 35; // 其他银行间现券卖出35

		public static final long GPMR = 36; // 股票买入

		public static final long GPMC = 37; // 股票卖出

		public static final long JYSGZMR = 38; // 交易所国债买入

		public static final long JYSGZMC = 39; // 交易所国债卖出

		public static final long KZZMR = 40; // 可转债买入

		public static final long KZZMC = 41; // 可转债卖出

		public static final long QYZMR = 42; // 企业债买入

		public static final long QYZMC = 43; // 企业债卖出

		public static final long FBSJJMR = 44; // 封闭式基金买入

		public static final long FBSJJMC = 45; // 封闭式基金卖出

		public static final long KFSJJMR = 46; // 开放式基金买入

		public static final long KFSJJMC = 47; // 开放式基金卖出

		public static final long QTJYSXJMR = 48; // 其他交易所间现券买入

		public static final long QTJYSXJMC = 49; // 其他交易所间现券卖出

		public static final long ZCHGJK = 50; // 资产回购进款(卖出)

		public static final long ZCHGCK = 51; // 资产回购出款(买入)

		public static final long ZTXMCMD = 52; // 转贴现卖出（买断）

		public static final long ZTXMCHG = 53; // 转贴现卖出（回购）

		public static final long ZTXMCDQSH = 54; // 转贴现卖出（到期收回）

		public static final long ZTXMRDQSHMD = 55; // 转贴现卖入到期收回（买断）

		public static final long ZTXMRGHHG = 56; // 转贴现买入购回（回购）

		public static final long ZTXMRMD = 57; // 转贴现买入（买断）

		public static final long ZTXMRHG = 58; // 转贴现买入（回购）

		public static final long ZTXMCGHHG = 59; // 转贴现卖出购回（回购）

		public static final long ZCXJRZMR = 60; // 政策性金融债买入60

		public static final long ZCXJRZMC = 61; // 政策性金融债卖出61

		public static final long CRLX = 62; // 拆入利息

		public static final long CCLX = 63; // 拆出利息

		public static final long DKMRLX = 64; // 贷款回购--买入利息

		public static final long DKMCLX = 65; // 贷款回购--卖出利息

		public static final long ZQZHGLX = 66; // 银/交之间债券回购 - 正回购利息

		public static final long ZQNHGLX = 67; // 债券回购 - 逆回购利息

		public static final long ZTXMRMDLX = 68; // 转贴现买入-买断利息

		public static final long ZTXMRHGLX = 69; // 转贴现买入-回购利息

		public static final long ZTXMCMDLX = 70; // 转贴现卖出-买断利息

		public static final long ZTXMCHGLX = 71; // 转贴现卖出-回购利息

		public static final long ZCHGJKDQ = 72; // 资产回购进款(买入 已购回)

		public static final long ZCHGCKDQ = 73; // 资产回购出款(卖出 已购回)

		public static final String getName(long id) {
			String rnt = "";
			if (id == 10) {
				rnt = "同业拆入";
			} else if (id == 11) {
				rnt = "同业拆出";
			} else if (id == 12) {
				rnt = "拆入还款";
			} else if (id == 13) {
				rnt = "拆出还款";
			} else if (id == 18) {
				rnt = "回购";
			} else if (id == 19) {
				rnt = "正回购到期";
			} else if (id == 20) {
				rnt = "逆回购";
			} else if (id == 21) {
				rnt = "逆回购到期";
			} else if (id == 22) {
				rnt = "融资回购";
			} else if (id == 23) {
				rnt = "融资购回";
			} else if (id == 24) {
				rnt = "融券回购";
			} else if (id == 25) {
				rnt = "融券购回";
			} else if (id == 26) {
				rnt = "央行票据买入";
			} else if (id == 27) {
				rnt = "央行票据卖出";
			} else if (id == 28) {
				rnt = "银行间国债买入";
			} else if (id == 29) {
				rnt = "银行间国债卖出";
			} else if (id == 30) {
				rnt = "金融债买入";
			} else if (id == 31) {
				rnt = "金融债卖出";
			} else if (id == 32) {
				rnt = "货币市场基金买入";
			} else if (id == 33) {
				rnt = "货币市场基金卖出";
			} else if (id == 34) {
				rnt = "其他银行间现券买入";
			} else if (id == 35) {
				rnt = "其他银行间现券卖出";
			} else if (id == 36) {
				rnt = "股票买入";
			} else if (id == 37) {
				rnt = "股票卖出";
			} else if (id == 38) {
				rnt = "交易所国债买入";
			} else if (id == 39) {
				rnt = "交易所国债卖出";
			} else if (id == 40) {
				rnt = "可转债买入";
			} else if (id == 41) {
				rnt = "可转债卖出";
			} else if (id == 42) {
				rnt = "企业债买入";
			} else if (id == 43) {
				rnt = "企业债卖出";
			} else if (id == 44) {
				rnt = "封闭式基金买入";
			} else if (id == 45) {
				rnt = "封闭式基金卖出";
			} else if (id == 46) {
				rnt = "开放式基金买入";
			} else if (id == 47) {
				rnt = "开放式基金卖出";
			} else if (id == 48) {
				rnt = "其他交易所间现券买入";
			} else if (id == 49) {
				rnt = "其他交易所间现券卖出";
			} else if (id == 50) {
				rnt = "资产回购进款(卖出)";
			} else if (id == 51) {
				rnt = "资产回购出款(买入)";
			} else if (id == 52) {
				rnt = "转贴现卖出（买断）";
			} else if (id == 53) {
				rnt = "转贴现卖出（回购）";
			} else if (id == 54) {
				rnt = "转贴现卖出（到期收回）";
			} else if (id == 55) {
				rnt = "转贴现卖入到期收回（买断）";
			} else if (id == 56) {
				rnt = "转贴现买入购回（回购）";
			} else if (id == 57) {
				rnt = "转贴现买入（买断）";
			} else if (id == 58) {
				rnt = "转贴现买入（回购）";
			} else if (id == 59) {
				rnt = "转贴现卖出购回（回购）";
			} else if (id == 60) {
				rnt = "政策性金融债买入";
			} else if (id == 61) {
				rnt = "政策性金融债卖出";

			} else if (id == 62) {
				rnt = "拆入利息";
			} else if (id == 63) {
				rnt = "拆出利息";
			} else if (id == 64) {
				rnt = "贷款回购-买入利息";
			} else if (id == 65) {
				rnt = "贷款回购-卖出利息";
			} else if (id == 66) {
				rnt = "债券回购-正回购利息";
			} else if (id == 67) {
				rnt = "债券回购-逆回购利息";
			} else if (id == 68) {
				rnt = "转贴现买入-买断利息";
			} else if (id == 69) {
				rnt = "转贴现买入-回购利息";
			} else if (id == 70) {
				rnt = "转贴现卖出-买断利息";
			} else if (id == 71) {
				rnt = "转贴现卖出-回购利息";

			} else if (id == 72) {
				rnt = "资产回购进款(买入已购回)";
			} else if (id == 73) {
				rnt = "资产回购出款(卖出已购回)";
			}
			return rnt;
		}
	}

	public static class BankBillStatus {
		public static final long DELETE = 0; // 注销

		public static final long REGISTER = 1; // 注册

		public static final long REQUIRE = 2; // 申领

		public static final long USE = 3; // 使用

		public static final long REPORTLOSS = 4; // 挂失

		public static final long TERMINATE = 5; // 终止

		/**
		 * 得到所有的银行票据状态
		 * 
		 * @return long[]
		 */
		public static final long[] getAllCode() {
			long[] lTemp = { DELETE, REGISTER, REQUIRE, USE, REPORTLOSS,
					TERMINATE };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$BankBillStatus",
							officeID, currencyID);
		}

		/**
		 * 显示中文状态的方法
		 * 
		 * @param lCode
		 * @return String
		 */
		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) DELETE:
				strReturn = "注销";
				break;
			case (int) REGISTER:
				strReturn = "注册";
				break;
			case (int) REQUIRE:
				strReturn = "申领";
				break;
			case (int) USE:
				strReturn = "使用";
				break;
			case (int) REPORTLOSS:
				strReturn = "挂失";
				break;
			case (int) TERMINATE:
				strReturn = "终止";
				break;
			default:
				strReturn = "错误状态";
			}
			return strReturn;
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型（0，显示全部；1，仅显示注册、申领；2，显示除注销以外的状态）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				case 1:
					lArrayID = new long[2];
					lArrayID[0] = REGISTER;
					lArrayID[1] = REQUIRE;
					break;
				case 2:
					lArrayID = new long[4];
					lArrayID[0] = REGISTER;
					lArrayID[1] = REQUIRE;
					lArrayID[2] = USE;
					lArrayID[3] = REPORTLOSS;
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				case 1:
					lArrayID = new long[2];
					lArrayID[0] = REGISTER;
					lArrayID[1] = REQUIRE;
					break;
				case 2:
					lArrayID = new long[4];
					lArrayID[0] = REGISTER;
					lArrayID[1] = REQUIRE;
					lArrayID[2] = USE;
					lArrayID[3] = REPORTLOSS;
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

	}

	/**
	 * 标准摘要类型
	 */
	public static class StandardAbstractType {
		public static final long PAYIN = 1; // 收入类

		public static final long PAYOUT = 2; // 支出类

		/**
		 * 得到所有的标准摘要类型
		 * 
		 * @return long[]
		 */
		public static final long[] getAllCode() {
			long[] lTemp = { PAYIN, PAYOUT };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$StandardAbstractType",
							officeID, currencyID);
		}

		/**
		 * 显示中文状态的方法
		 * 
		 * @param lCode
		 * @return String
		 */
		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) PAYIN:
				strReturn = "收入类";
				break;
			case (int) PAYOUT:
				strReturn = "支出类";
				break;
			}
			return strReturn;
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型（0，显示全部）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

	}

	/**
	 * 银行票据类型常量类：
	 */
	public static class BankBillType {
		// 1、包含的常量值：
		public static final long CASH_CHEQUE = 1; // 现金支票

		public static final long TRANSFER_ACCOUNT_CHEQUE = 2; // 转账支票

		public static final long CABLE_TRANSFER = 3; // 电汇

		public static final long DRAFT_TRUST_DEED = 4; // 汇票委托书

		public static final long SEAL_CARD = 5; // 印鉴卡（票据号为数字）

		public static final long FIXED_DEPOSIT_AUTHENTICATION = 6; // 定期存款证实书

		public static final long NOTIFY_DEPOSIT_AUTHENTICATION = 7; // 通知存款证实书

		/**
		 * 得到所有的银行票据类型
		 * 
		 * @return long[]
		 */
		public static final long[] getAllCode() {
			long[] lTemp = { CASH_CHEQUE, TRANSFER_ACCOUNT_CHEQUE,
					CABLE_TRANSFER, DRAFT_TRUST_DEED, SEAL_CARD,
					FIXED_DEPOSIT_AUTHENTICATION, NOTIFY_DEPOSIT_AUTHENTICATION };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$BankBillType",
							officeID, currencyID);
		}

		/**
		 * 显示中文状态的方法
		 * 
		 * @param lCode
		 * @return String
		 */
		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) CASH_CHEQUE:
				strReturn = "现金支票";
				break;
			case (int) TRANSFER_ACCOUNT_CHEQUE:
				strReturn = "转账支票";
				break;
			case (int) CABLE_TRANSFER:
				strReturn = "电汇";
				break;
			case (int) DRAFT_TRUST_DEED:
				strReturn = "汇票委托书";
				break;
			case (int) SEAL_CARD:
				strReturn = "印鉴卡";
				break;
			case (int) FIXED_DEPOSIT_AUTHENTICATION:
				strReturn = "定期存款证实书";
				break;
			case (int) NOTIFY_DEPOSIT_AUTHENTICATION:
				strReturn = "通知存款证实书";
				break;
			default:
				strReturn = "错误类型";
			}
			return strReturn;
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型
		 *            （0，显示全部；1，非证实书类；2，证实书类；3(显示电汇、汇票委托书)；4(显示转账支票)
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				case 1:
					lArrayID = new long[5];
					lArrayID[0] = CASH_CHEQUE;
					lArrayID[1] = TRANSFER_ACCOUNT_CHEQUE;
					lArrayID[2] = CABLE_TRANSFER;
					lArrayID[3] = DRAFT_TRUST_DEED;
					lArrayID[4] = SEAL_CARD;
					break;
				case 2:
					lArrayID = new long[2];
					lArrayID[0] = FIXED_DEPOSIT_AUTHENTICATION;
					lArrayID[1] = NOTIFY_DEPOSIT_AUTHENTICATION;
					break;
				case 3:
					lArrayID = new long[2];
					lArrayID[0] = CABLE_TRANSFER;
					lArrayID[1] = DRAFT_TRUST_DEED;
					break;
				case 4:
					lArrayID = new long[1];
					lArrayID[0] = TRANSFER_ACCOUNT_CHEQUE;
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				case 1:
					lArrayID = new long[5];
					lArrayID[0] = CASH_CHEQUE;
					lArrayID[1] = TRANSFER_ACCOUNT_CHEQUE;
					lArrayID[2] = CABLE_TRANSFER;
					lArrayID[3] = DRAFT_TRUST_DEED;
					lArrayID[4] = SEAL_CARD;
					break;
				case 2:
					lArrayID = new long[2];
					lArrayID[0] = FIXED_DEPOSIT_AUTHENTICATION;
					lArrayID[1] = NOTIFY_DEPOSIT_AUTHENTICATION;
					break;
				case 3:
					lArrayID = new long[2];
					lArrayID[0] = CABLE_TRANSFER;
					lArrayID[1] = DRAFT_TRUST_DEED;
					break;
				case 4:
					lArrayID = new long[1];
					lArrayID[0] = TRANSFER_ACCOUNT_CHEQUE;
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

	}

	/**
	 * 
	 * 清算类型常量类
	 */
	public static class ReckoningType {
		// 包含的常量值
		public static final long RemitInDebit = 1; // 提入借方

		public static final long RemitOutCredictFirst = 2; // 提出贷方一次

		public static final long RemitOutCredictSecond = 3; // 提出贷方二次

		/**
		 * 得到所有清算类型
		 * 
		 * @return long[]
		 */
		public static final long[] getAllCode() {
			long[] lTemp = { RemitInDebit, RemitOutCredictFirst,
					RemitOutCredictSecond };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$ReckoningType",
							officeID, currencyID);
		}

		/**
		 * 显示中文清算类型的方法
		 * 
		 * @param lCode
		 * @return String
		 */
		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) RemitInDebit:
				strReturn = "提入借方";
				break;
			case (int) RemitOutCredictFirst:
				strReturn = "提出贷方一次";
				break;
			case (int) RemitOutCredictSecond:
				strReturn = "提出贷方二次";
				break;
			}
			return strReturn;
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型
		 *            （0，显示全部)
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayId = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayId = getAllCode();
					break;
				case 1:
					lArrayId = new long[2];
					lArrayId[0] = RemitOutCredictFirst;
					lArrayId[1] = RemitOutCredictSecond;
					break;
				}
				strArrayName = new String[lArrayId.length];
				for (int i = 0; i < lArrayId.length; i++) {
					strArrayName[i] = getName(lArrayId[i]);
				}
				showCommonList(out, strControlName, lArrayId, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayId = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayId = getAllCode(lOfficeID, lCurrencyID);
					break;
				case 1:
					lArrayId = new long[2];
					lArrayId[0] = RemitOutCredictFirst;
					lArrayId[1] = RemitOutCredictSecond;
					break;
				}
				strArrayName = new String[lArrayId.length];
				for (int i = 0; i < lArrayId.length; i++) {
					strArrayName[i] = getName(lArrayId[i]);
				}
				showCommonList(out, strControlName, lArrayId, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

	}

	public static class TransactionStatus {
		public static final long DELETED = 0; // 已删除

		public static final long TEMPSAVE = 1; // 已暂存

		public static final long SAVE = 2; // 已保存（未复核）

		public static final long CHECK = 3; // 已复核

		public static final long NOTSIGN = 4; // 未签认

		public static final long SIGN = 5; // 已签认

		public static final long CONFIRM = 6; // 已确定

		public static final long CIRCLE = 7; // 已勾账			
		
		public static final long APPROVALING = 10; //审批中
		
		public static final long APPROVALED = 20;  //已审批
		
		public static final long REFUSE = 30;  //已拒绝
		
		//		added by qhzhou 2007.6.20
		public static final long WAITAPPROVAL=40;//待审批

		public static final long[] getAllCode() {
			long[] lTemp = { DELETED, TEMPSAVE, SAVE, APPROVALING, APPROVALED, CHECK, NOTSIGN, SIGN,
					CONFIRM, CIRCLE ,WAITAPPROVAL};
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$TransactionStatus",
							officeID, currencyID);
		}

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) DELETED:
				strReturn = "已删除";
				break;
			case (int) TEMPSAVE:
				strReturn = "已暂存";
				break;
			case (int) SAVE:
				strReturn = "已保存";
				break;
			case (int) CHECK:
				strReturn = "已复核";
				break;
			case (int) NOTSIGN:
				strReturn = "未签认";
				break;
			case (int) SIGN:
				strReturn = "已签认";
				break;
			case (int) CONFIRM:
				strReturn = "已确定";
				break;
			case (int) CIRCLE:
				strReturn = "已勾账";
				break;
			case (int) APPROVALING:
				strReturn = "审批中";
				break;
			case (int) APPROVALED:
				strReturn = "已审批";
				break;
			case (int) WAITAPPROVAL:
				strReturn = "待审批";
				break;
			default:
				strReturn = "错误状态";
			}
			return strReturn;
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型
		 *            （0，显示全部；1，业务处理；2，业务复核;3,显示3项（暂存、未复核、已复核））
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				case 1:
					lArrayID = new long[2];
					lArrayID[0] = TEMPSAVE;
					lArrayID[1] = SAVE;
					break;
				case 2:
					lArrayID = new long[1];
					lArrayID[0] = CHECK;
					break;
				case 3:
					lArrayID = new long[] { TEMPSAVE, SAVE, CHECK, CIRCLE };
					break;
				case 4:
					lArrayID = new long[4];
					lArrayID[0] = TEMPSAVE;
					lArrayID[1] = SAVE;
					lArrayID[2] = APPROVALING;
					lArrayID[3] = APPROVALED;
					break;
				case 5:
					lArrayID = new long[6];
					lArrayID[0] = TEMPSAVE;
					lArrayID[1] = SAVE;
					lArrayID[2] = APPROVALING;
					lArrayID[3] = APPROVALED;
					lArrayID[4] = CHECK;
					lArrayID[5] = CIRCLE;
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				case 1:
					lArrayID = new long[2];
					lArrayID[0] = TEMPSAVE;
					lArrayID[1] = SAVE;
					break;
				case 2:
					lArrayID = new long[1];
					lArrayID[0] = CHECK;
					break;
				case 3:
					lArrayID = new long[] { TEMPSAVE, SAVE, CHECK };
					break;
				case 4:
					lArrayID = new long[4];
					lArrayID[0] = TEMPSAVE;
					lArrayID[1] = SAVE;
					lArrayID[2] = APPROVALING;
					lArrayID[3] = APPROVALED;
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

	}

	/**
	 * 主动上收业务入账状态 银行接口业务新增（qijaing，08-18增加）
	 */
	public static class InitiativeTurnInTransStatus {
		public static final long UNSAVED = 1; // 已上收，待保存

		public static final long UNCHECKED = 2; // 已上收，待复核

		public static final long CHECKED = 3; // 已上收，已复核

		public static final long[] getAllCode() {
			long[] lTemp = { UNSAVED, UNCHECKED, CHECKED };
			return lTemp;
		}

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) UNSAVED:
				strReturn = "已上收，待保存";
				break;
			case (int) UNCHECKED:
				strReturn = "已上收，待复核";
				break;
			case (int) CHECKED:
				strReturn = "已上收，已复核";
				break;

			default:
				strReturn = "错误状态";
			}
			return strReturn;
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型
		 *            （0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}
	}

	// 账户状态
	public static class AccountStatus {
		public static final long NORMAL = 1; // 正常

		public static final long FREEZE = 2; // 冻结---只收不付冻结

		public static final long SEALUP = 3; // 封存

		public static final long CLOSE = 4; // 销户

		public static final long REPORTLOSS = 5; // 挂失

		public static final long ALLFREEZE = 7; // 不收不付冻结

		public static final long PARTFREEZE = 8; // 部分冻结

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) NORMAL:
				strReturn = "正常";
				break;
			case (int) FREEZE:
				strReturn = "只收不付冻结";
				break;
			case (int) SEALUP:
				strReturn = "封存";
				break;
			case (int) CLOSE:
				strReturn = "销户";
				break;
			case (int) REPORTLOSS:
				strReturn = "挂失";
				break;
			case (int) ALLFREEZE:
				strReturn = "不收不付冻结";
				break;
			case (int) PARTFREEZE:
				strReturn = "部分冻结";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { NORMAL, FREEZE, SEALUP, CLOSE };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$AccountStatus",
							officeID, currencyID);
		}

		public static final long convertFromSubAccount(long lCode) {
			long lTemp = -1; // 初始化返回值
			switch ((int) lCode) {
			case (int) SubAccountStatus.NORMAL:
				lTemp = NORMAL;
				break;
			case (int) SubAccountStatus.FINISH:
				lTemp = CLOSE;
				break;
			case (int) SubAccountStatus.REPORTLOSS:
				lTemp = NORMAL;
				break;
			case (int) SubAccountStatus.ONLYPAYFREEZE:
				lTemp = FREEZE;
				break;
			case (int) SubAccountStatus.ALLFREEZE:
				lTemp = ALLFREEZE;
				break;
			case (int) SubAccountStatus.PARTFREEZE:
				lTemp = PARTFREEZE;
				break;
			default:
				lTemp = NORMAL;
				break;
			}
			return lTemp;
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型（0，显示全部；1,没有清户选项；2，只有正常、清户；3、正常）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				case 1:
					lArrayID = new long[3];
					lArrayID[0] = NORMAL;
					lArrayID[1] = FREEZE;
					lArrayID[2] = SEALUP;
					break;
				case 2:
					lArrayID = new long[2];
					lArrayID[0] = NORMAL;
					lArrayID[1] = CLOSE;
					break;
				case 3:
					lArrayID = new long[1];
					lArrayID[0] = NORMAL;
					break;
				case 4:
					lArrayID = new long[3];
					lArrayID[0] = NORMAL;
					lArrayID[1] = SEALUP;
					lArrayID[2] = CLOSE;
					break;
				case 5:
					lArrayID = new long[4];
					lArrayID[0] = FREEZE;
					lArrayID[1] = ALLFREEZE;
					lArrayID[2] = PARTFREEZE;
					lArrayID[3] = REPORTLOSS;
					break;
				case 6:
					lArrayID = new long[4];
					lArrayID[0] = NORMAL;
					lArrayID[1] = FREEZE;
					lArrayID[2] = SEALUP;
					lArrayID[3] = CLOSE;	
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				case 1:
					lArrayID = new long[3];
					lArrayID[0] = NORMAL;
					lArrayID[1] = FREEZE;
					lArrayID[2] = SEALUP;
					break;
				case 2:
					lArrayID = new long[2];
					lArrayID[0] = NORMAL;
					lArrayID[1] = CLOSE;
					break;
				case 3:
					lArrayID = new long[1];
					lArrayID[0] = NORMAL;
					break;
				case 4:
					lArrayID = new long[3];
					lArrayID[0] = NORMAL;
					lArrayID[1] = SEALUP;
					//lArrayID[2] = CLOSE;  //没有清户功能
					lArrayID[2] = FREEZE; //新增帐户时，帐户状态可以为只收不付的。
					break;
				case 5:
					lArrayID = new long[4];
					lArrayID[0] = FREEZE;
					lArrayID[1] = ALLFREEZE;
					lArrayID[2] = PARTFREEZE;
					lArrayID[3] = REPORTLOSS;
					break;
				case 6:
					lArrayID = new long[4];
					lArrayID[0] = NORMAL;
					lArrayID[1] = FREEZE;
					lArrayID[2] = SEALUP;
					lArrayID[3] = CLOSE;	
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

	}

	// /利率计划类型
	public static class InterestRatePlanType {
		public static final long BALANCE = 1; // 按余额设置利率

		public static final long DATE = 2; // 按日期设置利率

		public static final long DAYS = 3; // 按持续天数设置利率

		public static final long BALANCE_AND_DATE = 4; //

		public static final long BALANCE_AND_DAYS = 5; //

		public static final long BALANCE_AVERAGE = 6; // 日均余额设置利率

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) BALANCE:
				strReturn = "按余额设置利率";
				break;
			case (int) DATE:
				strReturn = "按日期设置利率";
				break;
			case (int) DAYS:
				strReturn = "按持续天数设置利率";
				break;
			case (int) BALANCE_AND_DATE:
				strReturn = "按余额和日期设置利率";
				break;
			case (int) BALANCE_AND_DAYS:
				strReturn = "按余额和天数设置利率";
				break;
			case (int) BALANCE_AVERAGE:
				strReturn = "按日均余额设置利率";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { BALANCE, DATE, DAYS, BALANCE_AND_DATE,
					BALANCE_AND_DAYS, BALANCE_AVERAGE };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$InterestRatePlanType",
							officeID, currencyID);
		}
	}

	/** 利率日期类型 */
	public static class InterestRatePlanIntemType {
		public static final long OVER = 1; // 之上

		public static final long UNDER = 2; // 之下

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) OVER:
				strReturn = "之上";
				break;
			case (int) UNDER:
				strReturn = "之下";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { OVER, UNDER };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$InterestRatePlanIntemType",
							officeID, currencyID);
		}
	}

	/** 利率类型 */
	public static class InterestRateTypeFlag {
		public static final long DAY = 3; // 之上

		public static final long MONTH = 2; // 之下

		public static final long YEAR = 1; // 之下
	}

	/** 年日利率转换基数标志 */
	public static class InterestRateDaysFlag {
		public static final long DAYS_360 = 1; // 按每年360天

		public static final long DAYS_365 = 2; // 按每年365天

		public static final long DAYS_366 = 3; // 按每年366天

		static public long getDaysByFlag(long interestRateDaysFlag) {
			if (interestRateDaysFlag == DAYS_360)
				return 360;
			else if (interestRateDaysFlag == DAYS_365)
				return 365;
			else
				return 366;
		}
	}

	// 透支限制
	public static class AccountOverDraftType {
		public static final long ALL = 1; // 允许透支(或其他)

		public static final long CONSIGN = 2; // 允许委托收款透支(或委托收款)

		public static final long INTEREST = 3; // 允许付息透支(或付息)

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) ALL:
				strReturn = "允许透支";
				break;
			case (int) CONSIGN:
				strReturn = "允许委托收款透支";
				break;
			case (int) INTEREST:
				strReturn = "允许付息透支";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { ALL, CONSIGN, INTEREST };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$AccountOverDraftType",
							officeID, currencyID);
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型（0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				case 1:
					lArrayID = new long[1];
					lArrayID[0]=ALL;
					break;
				case 2:
					lArrayID = new long[1];
					lArrayID[0]=CONSIGN;
					break;
				case 3:
					lArrayID = new long[1];
					lArrayID[0]=INTEREST;
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

	}

	// 账户复核状态
	public static class AccountCheckStatus {
		public static final long NEWSAVE = 1; // 新增

		public static final long OLDSAVE = 2; // 已修改

		public static final long BATCHSAVE = 3; // 批量修改

		public static final long CHECK = 4; // 复核
		
		public static final long NEWSAVE_APPROVALING = 5; //新增审批中
		
		public static final long NEWSAVE_APPROVALED = 6;  //新增已审批
		
		
        public static final long OLDSAVE_APPROVALING = 7; //修改审批中
		
		public static final long OLDSAVE_APPROVALED = 8;  //修改已审批

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) NEWSAVE:
				strReturn = "新增未复核";
				break;
			case (int) OLDSAVE:
				strReturn = "修改未复核";
				break;
			case (int) BATCHSAVE:
				strReturn = "批量修改未复核";
				break;
			case (int) CHECK:
				strReturn = "已复核";
				break;
			case (int) NEWSAVE_APPROVALING:
				strReturn = "新增审批中";
				break;
			case (int) NEWSAVE_APPROVALED:
				strReturn = "新增已审批";
				break;
			case (int) OLDSAVE_APPROVALING:
				strReturn = "修改审批中";
				break;
			case (int) OLDSAVE_APPROVALED:
				strReturn = "修改已审批";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { NEWSAVE, OLDSAVE, BATCHSAVE, CHECK, NEWSAVE_APPROVALING, NEWSAVE_APPROVALED, OLDSAVE_APPROVALING, OLDSAVE_APPROVALED };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$AccountCheckStatus",
							officeID, currencyID);
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型（0，显示全部；1，新增复核的状态
		 *            2,只有“未复核”状态）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				case 1:
					lArrayID = new long[2];
					lArrayID[0] = NEWSAVE;
					lArrayID[1] = CHECK;
					break;
				case 2:
					lArrayID = new long[1];
					lArrayID[0] = NEWSAVE;
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				case 1:
					lArrayID = new long[2];
					lArrayID[0] = NEWSAVE;
					lArrayID[1] = CHECK;
					break;
				case 2:
					lArrayID = new long[1];
					lArrayID[0] = NEWSAVE;
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

	}

	// 利息费用结算的操作类型
	public static class InterestOperateType {
		public static final long INTERESTSETTLEMENT = 1; // 结息

		public static final long PREDRAWINTEREST = 2; // 计提利息

		public static final long CLEANPREDRAWINTEREST = 3; // 冲销计提利息

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) INTERESTSETTLEMENT:
				strReturn = "结息";
				break;
			case (int) PREDRAWINTEREST:
				strReturn = "计提利息";
				break;
			case (int) CLEANPREDRAWINTEREST:
				strReturn = "冲销计提利息";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { INTERESTSETTLEMENT, PREDRAWINTEREST,
					CLEANPREDRAWINTEREST };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$InterestOperateType",
							officeID, currencyID);
		}
	}

	// 利息费用计算的费用类型
	public static class InterestFeeType {
		public static final long INTEREST = 1; // 利息

		public static final long COMMISION = 2; // 手续费

		public static final long ASSURE = 3; // 担保费

		public static final long COMPOUNDINTEREST = 4; // 复利

		public static final long FORFEITINTEREST = 5; // 罚息

		public static final long PREDRAWINTEREST = 6; // 计提利息

		public static final long INTERESTTAX = 7; // 利息税费

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) INTEREST:
				strReturn = "利息";
				break;
			case (int) COMMISION:
				strReturn = "手续费";
				break;
			case (int) ASSURE:
				strReturn = "担保费";
				break;
			case (int) INTERESTTAX:
				strReturn = "利息税费";
				break;
			case (int) COMPOUNDINTEREST:
				strReturn = "复利";
				break;
			case (int) FORFEITINTEREST:
				strReturn = "罚息";
				break;
			case (int) PREDRAWINTEREST:
				strReturn = "计提利息";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { INTEREST, COMMISION, ASSURE, INTERESTTAX,
					COMPOUNDINTEREST, FORFEITINTEREST, PREDRAWINTEREST };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$InterestFeeType",
							officeID, currencyID);
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型（0，显示全部；1,没有复利、罚息、计提利息选项）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				case 1:
					lArrayID = new long[3];
					lArrayID[0] = INTEREST; // 利息
					lArrayID[1] = COMMISION; // 手续费
					lArrayID[2] = ASSURE; // 担保费
					break;
				case 2:
					lArrayID = new long[5];
					lArrayID[0] = INTEREST; // 利息
					lArrayID[1] = COMMISION; // 手续费
					lArrayID[2] = ASSURE; // 担保费
					lArrayID[3] = COMPOUNDINTEREST; // 复利
					lArrayID[4] = FORFEITINTEREST; // 罚息
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				case 1:
					lArrayID = new long[3];
					lArrayID[0] = INTEREST; // 利息
					lArrayID[1] = COMMISION; // 手续费
					lArrayID[2] = ASSURE; // 担保费
					break;
				case 2:
					lArrayID = new long[5];
					lArrayID[0] = INTEREST; // 利息
					lArrayID[1] = COMMISION; // 手续费
					lArrayID[2] = ASSURE; // 担保费
					lArrayID[3] = COMPOUNDINTEREST; // 复利
					lArrayID[4] = FORFEITINTEREST; // 罚息
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

	}

	public static class ConsignVoucherStatus {
		// 委托付款凭证状态
		public static final long NOTUSE = 1; // 没有使用

		public static final long ISUSE = 2; // 已使用

		public static final long STOPPAY = 3; // 止付

		public static final long LOST = 4; // 挂失

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) NOTUSE:
				strReturn = "未使用";
				break;
			case (int) ISUSE:
				strReturn = "已使用";
				break;
			case (int) STOPPAY:
				strReturn = "止付";
				break;
			case (int) LOST:
				strReturn = "挂失";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { NOTUSE, ISUSE, STOPPAY, LOST };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$ConsignVoucherStatus",
							officeID, currencyID);
		}
	}

	/**
	 * 业务类型/交易类型
	 * 
	 * @author wlming
	 * 
	 * To change the template for this generated type comment go to
	 * Window>Preferences>Java>Code Generation>Code and Comments
	 */
	public static class TransactionType {
		// 活期存款业务类型
		public static final long BANKRECEIVE = 1; // 银行收款

		public static final long CASHRECEIVE = 46; // 现金收款

		public static final long BANKPAY = 2; // 银行付款
		
		public static final long CHECKPAY = 3; // 支票付款

		public static final long CASHPAY = 4; // 现金付款

		public static final long DRAFTPAY = 5; // 票汇付款

		public static final long INTERNALVIREMENT = 6; // 内部转账

		public static final long CONSIGNRECEIVE = 7; // 委托收款

		public static final long CONSIGNSAVE = 8; // 委托存款

		public static final long CAUTIONMONEYSAVE = 9; // 保证金存款

		public static final long NATIONALDEBT_BUY = 10; // 国债买入

		public static final long NATIONALDEBT_SELL = 34; // 国债卖出

		public static final long ONETOMULTI = 11; // 一付多收

		public static final long OTHERPAY = 47; // 其他付款

		public static final long SUBCLIENT_BANKRECEIVE = 52; // 下属单位银行收款

		public static final long SUBCLIENT_BANKPAY = 53; // 下属单位银行付款
		
		public static final long BANKPAY_NOTONLINE = 702; // 银行付款落地处理

		// 中交新增 银行付款 交易类型
		public static final long BANKPAY_DOWNTRANSFER = 181;// 银行付款－下划成员单位

		// 定期业务
		public static final long OPENFIXEDDEPOSIT = 12; // 定期开立

		public static final long FIXEDTOCURRENTTRANSFER = 13; // 定期转活期（定期支取）

		public static final long FIXEDCONTINUETRANSFER = 14; // 定期续存-到期续存

		// 通知业务
		public static final long OPENNOTIFYACCOUNT = 15; // 通知存款开立

		public static final long NOTIFYDEPOSITDRAW = 16; // 通知存款支取

		// 保证金业务
		public static final long OPENMARGIN = 301; // 担保收款

		public static final long WITHDRAWMARGIN = 302; // 保后处理

		// 融资租赁业务 add by feiye 2006.4.26
		public static final long RECEIVEFINANCE = 401; // 融资租赁收款

		public static final long RETURNFINANCE = 402; // 融资租赁还款

		// 贷款业务
		public static final long TRUSTLOANGRANT = 17; // 信托贷款发放(自营贷款发放)(内部资金占用发放)

		public static final long TRUSTLOANRECEIVE = 18; // 信托贷款收回（自营贷款收回）（内部资金占用收回）

		public static final long CONSIGNLOANGRANT = 19; // 委托贷款发放

		public static final long CONSIGNLOANRECEIVE = 20; // 委托贷款收回

		public static final long DISCOUNTGRANT = 21; // 贴现发放

		public static final long DISCOUNTRECEIVE = 22; // 贴现收回

		public static final long MULTILOANRECEIVE = 23; // 多笔贷款收回
		
		public static final long TRANSDISCOUNTGRANT = 321 ;//转贴现发放

		// 银团贷款
		public static final long BANKGROUPLOANGRANT = 54; // 银团贷款发放

		public static final long BANKGROUPLOANRECEIVE = 55; // 银团贷款收回

		// 委托业务（中油专用）
		public static final long CONSIGNUPRECEIVE = 24; // 委托上收资金

		public static final long CONSIGNUPSAVE = 25; // 上存资金调回及发放负息资金

		public static final long CONSIGNUPSAVERECEIVE = 26; // 上存资金－上收及调回

		public static final long SHORTDEBTRECEIVE = 27; // 还短负

		public static final long CONSIGNCAPITALOPERATION = 28; // 客户委托资金调拨

		public static final long SHORTLOANGRANT = 29; // 发放短期贷款

		public static final long CYCLOANGRANT = 30; // 发放循环贷款

		// 其它
		public static final long GENERALLEDGER = 31; // 总账类(总账业务)

		public static final long TRANSFEE = 32; // 交易费用

		public static final long SPECIALOPERATION = 33; // 特殊交易

		public static final long COMPATIBILITY = 108; // 兼容交易

		public static final long TRANSABATEMENT = 109; // 转贴现卖出自动冲销

		// 34已经被占用
		public static final long SHORTLOANRECEIVE = 35; // 短期贷款收回

		public static final long INTERESTGRANT = 36; // 发放利息

		public static final long SHUTDOWN = 37; // 关机

		public static final long CYCLOANRECEIVE = 38; // 循环贷款收回

		public static final long BANKUPSAVE = 40; // 银行上收

		public static final long BANKUPRECEIVE = 41; // 银行调回

		public static final long BANKINTEREST = 42; // 银行发放负息资金

		public static final long CYCCONSIGNLOANGRANT = 43; // 循环委托贷款发放

		public static final long CYCCONSIGNLOANRECEIVE = 44; // 循环委托贷款收回

		public static final long INTERESTFEEPAYMENT = 45; // 利息费用支付

		// 46已经被占用
		// 47已经被占用
		// 证券投资结算
		public static final long SECURITIESRECEIVE = 48; // 财务公司收款（证券投资结算）

		public static final long SECURITIESPAY = 49; // 财务公司付款（证券投资结算）

		public static final long SEC_WTLC_RECEIVE = 56; // 委托理财收款（证券投资结算）

		public static final long SEC_WTLC_PAY = 57; // 委托理财付款（证券投资结算）

		public static final long SEC_ZQCX_RECEIVE = 58; // 债券承销收款（证券投资结算）

		public static final long SEC_ZQCX_PAY = 59; // 债券承销付款（证券投资结算）

		// 资金集中管理业务（国机专用）
		public static final long GRANT_DEBIT_INTEREST = 50; // 发放负息资金

		public static final long RECEIVE_DEBIT_INTEREST = 51; // 收回负息资金

		// 利息交易类型
		public static final long INTEREST_FEE_PAY_CURRENT = 101; // 利息费用支付活期结息

		public static final long FIXED_DEPOSIT_PREDRAW_INTEREST = 102; // 定期存款计提应付利息（含冲销）

		public static final long TRUST_LOAN_PREDRAW_INTEREST = 103; // 自营贷款计提应收利息（含冲销）

		public static final long TRUST_LOAN_INTEREST = 104; // 自营贷款结息(内部资金占用结息) 
		
		public static final long YT_LOAN_INTEREST = 211; // 银团贷款结息 ADD BY ZCWANG 20076-23
		
		public static final long YT_LOAN_PREDRAW_INTEREST = 212; // 银团贷款计提应收利息（含冲销） ADD BY ZCWANG 20076-28

		public static final long TRUST_LOAN_SURETY_FEE = 105; // 自营贷款结担保费（内部资金占用担保）

		public static final long CONSIGN_LOAN_INTEREST = 106; // 委托贷款结息

		public static final long CONSIGN_LOAN_COMMISION_FEE = 107; // 委托贷款结手续费

		public static final long MARGIN_DEPOSIT_PREDRAW_INTEREST = 171; // 保证金存款计提应付利息（含冲销）

		public static final long INTEREST_FEE_PAY_MARGIN = 172; // 利息费用支付保证金结息
		
		public static final long YT_LOAN_COMMISION_FEE = 210; //银团贷款结手续费
		// 108已经被占用
		// 109已经被占用
		// 银行收款业务(海尔专用)
		public static final long CHECK_RECEIVE = 110; // 支票收款

		public static final long REMIT_RECEIVE = 111; // 汇款收款

		public static final long OTHER_RECEIVE = 112; // 其它收款

		// 银行付款业务(海尔专用)
		public static final long CHECK_PAY = 116; // 支票付款

		public static final long REMIT_PAY = 117; // 汇款付款

		public static final long TAX_PAY = 118; // 税单付款

		public static final long OTHER_PAY = 119; // 其它付款

		// 表外业务(上海电气专用)
		public static final long DELEGATION_INCOME_OFFBALANCE = 120; // 1.
																		// 代保管有价值品类表外业务收入

		public static final long DELEGATION_PAYOUT_OFFBALANCE = 121; // 1.
																		// 代保管有价值品类表外业务付出

		public static final long CONSIGN_INCOME_OFFBALANCE = 122; // 2.
																	// 贷款未收利息类表外业务收入

		public static final long CONSIGN_PAYOUT_OFFBALANCE = 123; // 2.
																	// 贷款未收利息类表外业务付出

		public static final long DISCOUNT_INCOME_OFFBALANCE = 124; // 3.
																	// 商业汇票贴现类表外业务收入

		public static final long DISCOUNT_PAYOUT_OFFBALANCE = 125; // 3.
																	// 商业汇票贴现类表外业务付出

		public static final long ASSURE_INCOME_OFFBALANCE = 126; // 4.
																	// 开出保函凭信类表外业务收入

		public static final long ASSURE_PAYOUT_OFFBALANCE = 127; // 4.
																	// 开出保函凭信类表外业务付出

		// 冻结、挂失
		public static final long REPORTLOSS = 130; // 挂失

		public static final long REPORTFIND = 131; // 解挂

		public static final long CHANGECERTIFICATE = 132; // 焕发证书

		public static final long FREEZE = 133; // 冻结

		public static final long DEFREEZE = 134; // 解冻
		
		public static final long ACCOUNTOPEN = 135;//账户开户
		
		public static final long ACCOUNTMODIFY = 136;//账户修改
		

		// 联通公司专用
		public static final long FUND_REQUEST = 140; // 资金申领,活期类业务

		public static final long FUND_RETURN = 141; // 资金上存,活期类业务

		// 针对财务公司内部活期账户
		public static final long UPGATHERING = 151;// 资金上收

		// 收取手续费
		public static final long COMMISSION = 161;// 交易手续费收取

		// 票据管理专用
		public static final long BILL_REGISTER = 201;

		public static final long BILL_USE = 202;

		// 南航财务新增 银行付款 交易类型
		public static final long BANKPAY_FINCOMPANYPAY = 221;// 银行付款－财司代付

		public static final long BANKPAY_PAYSUBACCOUNT = 222;// 银行付款-拨子账户

		// 南航财务新增 银行收款 交易类型
		public static final long BANKRECEIVE_GATHERING = 231;// 银行收款－上收款项

		public static final long BANKRECEIVE_SUBCLIENT = 232;// 银行收款－成员单位收款

		public static final long BANKRECEIVE_TOSUBCLIENT = 233;// 银行收款－转成员单位收款

		// 银行接口新增
		public static final long INITIATIVE_TURNIN = 501; // 主动上收业务

		// 方便程序处理，用来区分是本金或利息的指令
		public static final long DRAW_PRINCIPAL = 502; // 通知、定期支取本金

		public static final long DRAW_INTEREST = 503; // 通知、定期支取利息

		public static final long INTERESTFEEPAYMENT_SURETY = 601; // 利息支付－自营贷款担保费(利息支付－内部资金占用担保费)

		public static final long INTERESTFEEPAYMENT_COMMISION = 602; // 利息支付－委托贷款手续费

        public static final long CAPITALTRANSFER = 701;//资金划拨
        
        public static final long DISCOUNTACCRUAL = 801;//贴现贷款计提应收利息（含冲销）
        
        public static final long DISCOUNT_LOAN_INTEREST = 804;//贴现贷款结息
        
        public static final long CONSIGN_LOAN_PREDRAW_INTEREST = 802;//委托贷款计提应收利息（含冲销）
        
        public static final long CURRENT_DEPOSIT_PREDRAW_INTEREST = 803; //活期存款计提应付利息（含冲销）

        public static final long CURRENT_NOTIFY_PREDRAW_INTEREST = 805; //通知存款计提应付利息（含冲销）
        
        //added by leiyang  2008/03/04
		// 企业资金上划
		public static final long CAPITALUP = 810; // 企业资金上划
		// 企业资金下拨
		public static final long CAPITALDOWN = 811; // 企业资金下拨
		//end
        
        //结算其他－－累计费用调整
        public static final long ABJUSTINTEREST = 900;//累计费用调整
        
		public static boolean isCurrentTransaction(long transactionTypeID) {
			if (transactionTypeID == BANKRECEIVE
					|| transactionTypeID == CASHRECEIVE
					|| transactionTypeID == BANKPAY
					|| transactionTypeID == SUBCLIENT_BANKPAY
					|| transactionTypeID == SUBCLIENT_BANKRECEIVE
					|| transactionTypeID == CHECKPAY
					|| transactionTypeID == CASHPAY
					|| transactionTypeID == DRAFTPAY
					|| transactionTypeID == INTERNALVIREMENT
					|| transactionTypeID == CONSIGNRECEIVE
					|| transactionTypeID == CONSIGNSAVE
					|| transactionTypeID == CAUTIONMONEYSAVE
					|| transactionTypeID == NATIONALDEBT_BUY
					|| transactionTypeID == NATIONALDEBT_SELL
					|| transactionTypeID == ONETOMULTI
					|| transactionTypeID == OTHERPAY
					|| transactionTypeID == GRANT_DEBIT_INTEREST
					|| transactionTypeID == RECEIVE_DEBIT_INTEREST
					|| transactionTypeID == CHECK_RECEIVE
					|| transactionTypeID == REMIT_RECEIVE
					|| transactionTypeID == OTHER_RECEIVE
					|| transactionTypeID == CHECK_PAY
					|| transactionTypeID == REMIT_PAY
					|| transactionTypeID == TAX_PAY
					|| transactionTypeID == OTHER_PAY
					|| transactionTypeID == FUND_REQUEST
					|| transactionTypeID == FUND_RETURN
					|| transactionTypeID == BANKPAY_FINCOMPANYPAY
					|| transactionTypeID == BANKPAY_PAYSUBACCOUNT
					|| transactionTypeID == BANKRECEIVE_GATHERING
					|| transactionTypeID == BANKRECEIVE_SUBCLIENT
					|| transactionTypeID == BANKRECEIVE_TOSUBCLIENT
					|| transactionTypeID == BANKPAY_DOWNTRANSFER
					|| transactionTypeID == BANKPAY_NOTONLINE
					|| transactionTypeID == CAPITALUP
					|| transactionTypeID == CAPITALDOWN) {
				return true;
			} else {
				return false;
			}
		}

		public static boolean isFixedTransaction(long transactionTypeID) {
			if (transactionTypeID <= NOTIFYDEPOSITDRAW
					&& transactionTypeID >= OPENFIXEDDEPOSIT) {
				return true;
			} else {
				return false;
			}
		}

		public static boolean isLoanTransaction(long transactionTypeID) {
			if ((transactionTypeID <= TRUSTLOANGRANT && transactionTypeID >= MULTILOANRECEIVE)
					|| (transactionTypeID >= BANKGROUPLOANGRANT && transactionTypeID <= BANKGROUPLOANRECEIVE)) {
				return true;
			} else
				return false;
		}

		/**
		 * 是否利息交易类型
		 * 
		 * @param transactionTypeID
		 * @return
		 */
		public static boolean isInterestTransaction(long transactionTypeID) {
			if (transactionTypeID == INTEREST_FEE_PAY_CURRENT
					|| transactionTypeID == INTEREST_FEE_PAY_MARGIN
					|| transactionTypeID == FIXED_DEPOSIT_PREDRAW_INTEREST
					|| transactionTypeID == TRUST_LOAN_PREDRAW_INTEREST
					|| transactionTypeID == YT_LOAN_PREDRAW_INTEREST
					|| transactionTypeID == TRUST_LOAN_INTEREST
					|| transactionTypeID == YT_LOAN_INTEREST
					|| transactionTypeID == TRUST_LOAN_SURETY_FEE
					|| transactionTypeID == CONSIGN_LOAN_INTEREST
					|| transactionTypeID == CONSIGN_LOAN_COMMISION_FEE
					|| transactionTypeID == MARGIN_DEPOSIT_PREDRAW_INTEREST
					|| transactionTypeID == DISCOUNTACCRUAL
					|| transactionTypeID == CONSIGN_LOAN_PREDRAW_INTEREST
					|| transactionTypeID == CURRENT_DEPOSIT_PREDRAW_INTEREST
					|| transactionTypeID == CURRENT_NOTIFY_PREDRAW_INTEREST
					|| transactionTypeID == DISCOUNT_LOAN_INTEREST ) {
				return true;
			} else {
				return false;
			}
		}

		/**
		 * 根据“账户类型”、“利息费用计算的费用类型”和“利息费用结算的操作类型”获得利息交易类型
		 * 
		 * @param lAccountType
		 * @param lInterestType
		 * @param lInterestOperateType
		 * @return long
		 */
		public static long getTransactionType(long lAccountType,
				long lInterestType, long lInterestOperateType) {
			long result = -1;
			switch ((int) lInterestOperateType) {
			case (int) SETTConstant.InterestOperateType.INTERESTSETTLEMENT: {
				if (SETTConstant.AccountType.isCurrentAccountType(lAccountType)
						|| SETTConstant.AccountType
								.isOtherDepositAccountType(lAccountType)) {
					if (lInterestType == SETTConstant.InterestFeeType.INTEREST)
						result = INTEREST_FEE_PAY_CURRENT;
				} else if (SETTConstant.AccountType
						.isMarginAccountType(lAccountType)) {
					if (lInterestType == SETTConstant.InterestFeeType.INTEREST)
						result = INTEREST_FEE_PAY_MARGIN;
				} else if (SETTConstant.AccountType
						.isLoanAccountType(lAccountType)) {
					long lAccountGroupType = SETTConstant.AccountType
							.getAccountGroupTypeIDByAccountTypeID(lAccountType);
					if (SETTConstant.AccountGroupType.TRUST == lAccountGroupType
							&& (lInterestType == SETTConstant.InterestFeeType.INTEREST
									|| lInterestType == SETTConstant.InterestFeeType.COMPOUNDINTEREST || lInterestType == SETTConstant.InterestFeeType.FORFEITINTEREST))
						result = TRUST_LOAN_INTEREST;
					//ADD BY ZCWANG 2007-6-23
					else if (SETTConstant.AccountGroupType.YT == lAccountGroupType
							&& (lInterestType == SETTConstant.InterestFeeType.INTEREST
									|| lInterestType == SETTConstant.InterestFeeType.COMPOUNDINTEREST || lInterestType == SETTConstant.InterestFeeType.FORFEITINTEREST))
						result = YT_LOAN_INTEREST;
					//
					else if (SETTConstant.AccountGroupType.CONSIGN == lAccountGroupType
							&& (lInterestType == SETTConstant.InterestFeeType.INTEREST
									|| lInterestType == SETTConstant.InterestFeeType.COMPOUNDINTEREST || lInterestType == SETTConstant.InterestFeeType.FORFEITINTEREST))
						result = CONSIGN_LOAN_INTEREST;
					else if (lInterestType == SETTConstant.InterestFeeType.ASSURE)
						result = TRUST_LOAN_SURETY_FEE;
					else if (lInterestType == SETTConstant.InterestFeeType.COMMISION)
						result = CONSIGN_LOAN_COMMISION_FEE;
				}
				break;
			}
			case (int) SETTConstant.InterestOperateType.PREDRAWINTEREST: {
				if (SETTConstant.AccountType.isFixAccountType(lAccountType)
						|| SETTConstant.AccountType
								.isNotifyAccountType(lAccountType)) {
					if (lInterestType == SETTConstant.InterestFeeType.INTEREST)
						result = FIXED_DEPOSIT_PREDRAW_INTEREST;
				}
				else if (SETTConstant.AccountType
						.isLoanAccountType(lAccountType)) {
					long lAccountGroupType = SETTConstant.AccountType
					.getAccountGroupTypeIDByAccountTypeID(lAccountType);
					if ( SETTConstant.AccountGroupType.TRUST == lAccountGroupType && lInterestType == SETTConstant.InterestFeeType.INTEREST)
					{
						result = TRUST_LOAN_PREDRAW_INTEREST;
					}
					else if(SETTConstant.AccountGroupType.YT == lAccountGroupType && lInterestType == SETTConstant.InterestFeeType.INTEREST)
					{
						result = YT_LOAN_PREDRAW_INTEREST;
					}
				} else if (SETTConstant.AccountType
						.isMarginAccountType(lAccountType)) {
					if (lInterestType == SETTConstant.InterestFeeType.INTEREST)
						result = MARGIN_DEPOSIT_PREDRAW_INTEREST;
				}
				break;
			}
			case (int) SETTConstant.InterestOperateType.CLEANPREDRAWINTEREST: {
				if (SETTConstant.AccountType.isFixAccountType(lAccountType)) {
					if (lInterestType == SETTConstant.InterestFeeType.PREDRAWINTEREST)
						result = FIXED_DEPOSIT_PREDRAW_INTEREST;
				} else if (SETTConstant.AccountType
						.isLoanAccountType(lAccountType)) {
					long lAccountGroupType = SETTConstant.AccountType
					.getAccountGroupTypeIDByAccountTypeID(lAccountType);
					if (SETTConstant.AccountGroupType.TRUST == lAccountGroupType && lInterestType == SETTConstant.InterestFeeType.PREDRAWINTEREST)
					{
						result = TRUST_LOAN_PREDRAW_INTEREST;
					}
					else if(SETTConstant.AccountGroupType.YT == lAccountGroupType && lInterestType == SETTConstant.InterestFeeType.PREDRAWINTEREST)
					{
						result = YT_LOAN_PREDRAW_INTEREST;
					}
				} else if (SETTConstant.AccountType
						.isMarginAccountType(lAccountType)) {
					if (lInterestType == SETTConstant.InterestFeeType.PREDRAWINTEREST)
						result = MARGIN_DEPOSIT_PREDRAW_INTEREST;
				}
				break;
			}
			}
			return result;
		}

		public static final String getName(long lCode) {
			String strReturn = "";
			switch ((int) lCode) {
			case (int) BANKRECEIVE:
				strReturn = "银行收款";
				break;
			case (int) BANKRECEIVE_GATHERING:
				strReturn = "银行收款－上收款项";
				break;
			case (int) BANKRECEIVE_SUBCLIENT:
				strReturn = "银行收款－成员单位收款";
				break;
			case (int) BANKRECEIVE_TOSUBCLIENT:
				strReturn = "银行收款－转成员单位收款";
				break;
			case (int) CASHRECEIVE:
				strReturn = "现金收款";
				break;
			case (int) BANKPAY:
				strReturn = "银行付款";
				break;
			case (int) BANKPAY_DOWNTRANSFER:
				strReturn = "银行付款－下划成员单位";
				break;
			case (int) BANKPAY_FINCOMPANYPAY:
				strReturn = "银行付款-财司代付";
				break;
			case (int) BANKPAY_PAYSUBACCOUNT:
				strReturn = "银行付款-拨子账户";
				break;
			case (int) SUBCLIENT_BANKRECEIVE:
				strReturn = "下属单位银行收款";
				break;
			case (int) SUBCLIENT_BANKPAY:
				strReturn = "下属单位银行付款";
				break;
			case (int) CHECKPAY:
				strReturn = "支票付款";
				break;
			case (int) CASHPAY:
				strReturn = "现金付款";
				break;
			case (int) DRAFTPAY:
				strReturn = "票汇付款";
				break;
			case (int) INTERNALVIREMENT:
				strReturn = "内部转账";
				break;
			case (int) CONSIGNRECEIVE:
				strReturn = "委托收款";
				break;
			case (int) CONSIGNSAVE:
				strReturn = "委托存款";
				break;
			case (int) CAUTIONMONEYSAVE:
				strReturn = "保证金存款";
				break;
			case (int) NATIONALDEBT_BUY:
				strReturn = "国债买入";
				break;
			case (int) NATIONALDEBT_SELL:
				strReturn = "国债卖出";
				break;
			case (int) ONETOMULTI:
				strReturn = "多借多贷";
				break;
			case (int) OTHERPAY:
				strReturn = "其他付款";
				break;
			case (int) OPENFIXEDDEPOSIT:
				strReturn = "定期开立";
				break;
			case (int) FIXEDTOCURRENTTRANSFER:
				strReturn = "定期支取";
				break;
			case (int) FIXEDCONTINUETRANSFER:
				strReturn = "到期续存";
				break;
			case (int) OPENNOTIFYACCOUNT:
				strReturn = "通知存款开立";
				break;
			case (int) NOTIFYDEPOSITDRAW:
				strReturn = "通知存款支取";
				break;
			case (int) TRUSTLOANGRANT:
				strReturn = "自营贷款发放";
				break;
			case (int) TRUSTLOANRECEIVE:
				strReturn = "自营贷款收回";
				break;
			case (int) CONSIGNLOANGRANT:
				strReturn = "委托贷款发放";
				break;
			case (int) CONSIGNLOANRECEIVE:
				strReturn = "委托贷款收回";
				break;
			case (int) DISCOUNTGRANT:
				strReturn = "贴现发放";
				break;
			case (int) DISCOUNTRECEIVE:
				strReturn = "贴现收回";
				break;
			case (int) MULTILOANRECEIVE:
				strReturn = "多笔贷款收回";
				break;
			case (int) TRANSDISCOUNTGRANT:
				strReturn = "转贴现发放";
				break;	
			case (int) CONSIGNUPRECEIVE:
				strReturn = "委托上收资金";
				break;
			case (int) CONSIGNUPSAVE:
				strReturn = "上存资金调回及发放负息资金";
				break;
			case (int) CONSIGNUPSAVERECEIVE:
				strReturn = "上存资金－上收及调回";
				break;
			case (int) SHORTDEBTRECEIVE:
				strReturn = "还短负";
				break;
			case (int) CONSIGNCAPITALOPERATION:
				strReturn = "客户委托资金调拨";
				break;
			case (int) SHORTLOANGRANT:
				strReturn = "发放短期贷款";
				break;
			case (int) CYCLOANGRANT:
				strReturn = "发放循环贷款";
				break;
			case (int) GENERALLEDGER:
				strReturn = "总账业务";
				break;
			case (int) TRANSFEE:
				strReturn = "交易费用";
				break;
			case (int) SPECIALOPERATION:
				strReturn = "特殊交易";
				break;
			case (int) COMPATIBILITY:
				strReturn = "兼容业务";
				break;
			case (int) TRANSABATEMENT:
				strReturn = "转贴现卖出自动冲销";
				break;
			case (int) SHORTLOANRECEIVE:
				strReturn = "短期贷款收回";
				break;
			case (int) INTERESTGRANT:
				strReturn = "发放利息";
				break;
			case (int) SHUTDOWN:
				strReturn = "关机";
				break;
			case (int) CYCLOANRECEIVE:
				strReturn = "循环贷款收回";
				break;
			case (int) BANKUPSAVE:
				strReturn = "银行上收";
				break;
			case (int) BANKUPRECEIVE:
				strReturn = "银行调回";
				break;
			case (int) BANKINTEREST:
				strReturn = "银行发放负息资金";
				break;
			case (int) CYCCONSIGNLOANGRANT:
				strReturn = "循环委托贷款发放";
				break;
			case (int) CYCCONSIGNLOANRECEIVE:
				strReturn = "循环委托贷款收回";
				break;
			case (int) INTERESTFEEPAYMENT:
				strReturn = "利息费用支付";
				break;
			case (int) SECURITIESRECEIVE:
				strReturn = "财务公司收款";
				break;
			case (int) SECURITIESPAY:
				strReturn = "财务公司付款";
				break;
			case (int) SEC_WTLC_RECEIVE:
				strReturn = "委托理财收款";
				break;
			case (int) SEC_WTLC_PAY:
				strReturn = "委托理财付款";
				break;
			case (int) SEC_ZQCX_RECEIVE:
				strReturn = "债券承销收款";
				break;
			case (int) SEC_ZQCX_PAY:
				strReturn = "债券承销付款";
				break;
			case (int) INTEREST_FEE_PAY_CURRENT:
				strReturn = "利息费用支付活期结息";
				break;
			case (int) INTEREST_FEE_PAY_MARGIN:
				strReturn = "利息费用支付保证金结息";
				break;
			case (int) YT_LOAN_COMMISION_FEE:
				strReturn = "银团贷款结手续费";
				break;
			case (int) FIXED_DEPOSIT_PREDRAW_INTEREST:
				strReturn = "定期存款计提应付利息（含冲销）";
				break;
			case (int) MARGIN_DEPOSIT_PREDRAW_INTEREST:
				strReturn = "保证金存款计提应付利息（含冲销）";
				break;
			case (int) TRUST_LOAN_PREDRAW_INTEREST:
				strReturn = "自营贷款计提应收利息（含冲销）";
				break;
			case (int) YT_LOAN_PREDRAW_INTEREST:
				strReturn = "银团贷款计提应收利息（含冲销）";
				break;
			case (int) TRUST_LOAN_INTEREST:
				strReturn = "自营贷款结息";
				break;
			case (int) YT_LOAN_INTEREST:
				strReturn = "银团贷款结息";
				break;
			case (int) TRUST_LOAN_SURETY_FEE:
				strReturn = "自营贷款结担保费";
				break;
			case (int) CONSIGN_LOAN_INTEREST:
				strReturn = "委托贷款结息";
				break;
			case (int) CONSIGN_LOAN_COMMISION_FEE:
				strReturn = "委托贷款结手续费";
				break;
			case (int) GRANT_DEBIT_INTEREST:
				strReturn = "发放负息资金";
				break;
			case (int) RECEIVE_DEBIT_INTEREST:
				strReturn = "收回负息资金";
				break;
			case (int) BANKGROUPLOANGRANT:
				strReturn = "银团贷款发放";
				break;
			case (int) BANKGROUPLOANRECEIVE:
				strReturn = "银团贷款收回";
				break;
			// 海尔专用（04/10/20 add by weilu）
			case (int) CHECK_RECEIVE:
				strReturn = "银行支票收款";
				break;
			case (int) REMIT_RECEIVE:
				strReturn = "银行电汇收款";
				break;
			case (int) OTHER_RECEIVE:
				strReturn = "银行其它形式收款";
				break;
			case (int) CHECK_PAY:
				strReturn = "银行支票付款";
				break;
			case (int) REMIT_PAY:
				strReturn = "银行电汇付款";
				break;
			case (int) TAX_PAY:
				strReturn = "银行税单付款";
				break;
			case (int) OTHER_PAY:
				strReturn = "银行其它形式付款";
				break;
			case (int) DELEGATION_INCOME_OFFBALANCE:
				strReturn = "代保管有价值品类表外业务收入";
				break;
			case (int) DELEGATION_PAYOUT_OFFBALANCE:
				strReturn = "代保管有价值品类表外业务付出";
				break;
			case (int) CONSIGN_INCOME_OFFBALANCE:
				strReturn = "贷款未收利息类表外业务收入";
				break;
			case (int) CONSIGN_PAYOUT_OFFBALANCE:
				strReturn = "贷款未收利息类表外业务付出";
				break;
			case (int) DISCOUNT_INCOME_OFFBALANCE:
				strReturn = "商业汇票贴现类表外业务收入";
				break;
			case (int) DISCOUNT_PAYOUT_OFFBALANCE:
				strReturn = "商业汇票贴现类表外业务付出";
				break;
			case (int) ASSURE_INCOME_OFFBALANCE:
				strReturn = "开出保函凭信类表外业务收入";
				break;
			case (int) ASSURE_PAYOUT_OFFBALANCE:
				strReturn = "开出保函凭信类表外业务付出";
				break;
			case (int) REPORTLOSS:
				strReturn = "挂失";
				break;
			case (int) REPORTFIND:
				strReturn = "解挂";
				break;
			case (int) CHANGECERTIFICATE:
				strReturn = "换发证书";
				break;
			case (int) FREEZE:
				strReturn = "冻结";
				break;
			case (int) DEFREEZE:
				strReturn = "解冻";
				break;
			case (int) ACCOUNTOPEN:
				strReturn = "账户开户";
				break;
			case (int) ACCOUNTMODIFY:
				strReturn = "帐户修改";
				break;	
			case (int) FUND_REQUEST:
				strReturn = "资金申领";
				break;
			case (int) FUND_RETURN:
				strReturn = "资金上存";
				break;
			case (int) BILL_REGISTER:
				strReturn = "空白凭证注册";
				break;
			case (int) BILL_USE:
				strReturn = "空白凭证领用";
				break;
			case (int) INITIATIVE_TURNIN:
				strReturn = "主动上收";
				break;
			case (int) DRAW_PRINCIPAL:
				strReturn = "通知定期支取本金";
				break;
			case (int) DRAW_INTEREST:
				strReturn = "通知定期支取利息";
				break;
			case (int) UPGATHERING:
				strReturn = "资金上收";
				break;
			case (int) COMMISSION:
				strReturn = "交易手续费收取";
				break;
			case (int) INTERESTFEEPAYMENT_SURETY:
				strReturn = "利息支付－自营贷款担保费";
				break;
			case (int) INTERESTFEEPAYMENT_COMMISION:
				strReturn = "利息支付－委托贷款手续费";
				break;
			case (int) OPENMARGIN:
				strReturn = "担保收款";
				break;
			case (int) WITHDRAWMARGIN:
				strReturn = "保后处理";
				break;
			case (int) RECEIVEFINANCE:
				strReturn = "融资租赁收款";
				break;
			case (int) RETURNFINANCE:
				strReturn = "融资租赁还款";
				break;
				
			case (int) CAPITALTRANSFER:
				strReturn = "资金划拨";
				break;
			case (int) DISCOUNTACCRUAL:
				strReturn = "贴现贷款计提应收利息（含冲销）";
				break;
			case (int) DISCOUNT_LOAN_INTEREST:
				strReturn = "贴现贷款结息";
				break;
			case (int) CONSIGN_LOAN_PREDRAW_INTEREST:
				strReturn = "委托贷款计提应收利息（含冲销）";
				break;
			case (int) CURRENT_DEPOSIT_PREDRAW_INTEREST:
				strReturn = "活期存款计提应付利息（含冲销）";
				break;
			case (int) CURRENT_NOTIFY_PREDRAW_INTEREST:
				strReturn = "通知存款计提应付利息（含冲销）";
				break;
			case (int) ABJUSTINTEREST:
				strReturn = "累计费用调整";
				break;
			case (int)BANKPAY_NOTONLINE:
				strReturn = "银行付款－落地处理";
				break;
			case (int) CAPITALUP:
				strReturn = "企业资金上划";
				break;
			case (int)CAPITALDOWN:
				strReturn = "企业资金下拨";
				break;

			default:// 当设置了特殊业务的交易类型后，需要将特殊业务的所有交易类型合并到现有业务的交易类型中来
			{
				try {
					Sett_SpecialOperationDAO specialDao = new Sett_SpecialOperationDAO();
					strReturn = specialDao.findSpecialOperationByID(lCode).m_strName;
				} catch (Exception e) {
					System.out.println("取得特殊交易类型时异常");
					e.printStackTrace();
				}
			}
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			// 返回全部
			long[] lTemp = { BANKRECEIVE, CASHRECEIVE, BANKPAY,BANKPAY_NOTONLINE,
					BANKPAY_DOWNTRANSFER, SUBCLIENT_BANKRECEIVE,
					SUBCLIENT_BANKPAY, CHECKPAY, CASHPAY, DRAFTPAY,
					INTERNALVIREMENT, CONSIGNRECEIVE, CONSIGNSAVE,
					CAUTIONMONEYSAVE, NATIONALDEBT_BUY, NATIONALDEBT_SELL,
					ONETOMULTI, OTHERPAY, OPENFIXEDDEPOSIT,
					FIXEDTOCURRENTTRANSFER, FIXEDCONTINUETRANSFER,
					OPENNOTIFYACCOUNT, NOTIFYDEPOSITDRAW, TRUSTLOANGRANT,
					TRUSTLOANRECEIVE, CONSIGNLOANGRANT, CONSIGNLOANRECEIVE,
					BANKGROUPLOANGRANT, BANKGROUPLOANRECEIVE, DISCOUNTGRANT,
					DISCOUNTRECEIVE, MULTILOANRECEIVE,TRANSDISCOUNTGRANT,CONSIGNUPRECEIVE,
					CONSIGNUPSAVE, CONSIGNUPSAVERECEIVE, SHORTDEBTRECEIVE,
					CONSIGNCAPITALOPERATION, SHORTLOANGRANT, CYCLOANGRANT,
					GENERALLEDGER, TRANSFEE, SPECIALOPERATION, COMPATIBILITY,
					TRANSABATEMENT, SHORTLOANRECEIVE, INTERESTGRANT, SHUTDOWN,
					CYCLOANRECEIVE, BANKUPSAVE, BANKUPRECEIVE, BANKINTEREST,
					CYCCONSIGNLOANGRANT, CYCCONSIGNLOANRECEIVE,
					INTEREST_FEE_PAY_CURRENT, INTEREST_FEE_PAY_MARGIN,
					FIXED_DEPOSIT_PREDRAW_INTEREST,
					MARGIN_DEPOSIT_PREDRAW_INTEREST,
					TRUST_LOAN_PREDRAW_INTEREST,YT_LOAN_PREDRAW_INTEREST, TRUST_LOAN_INTEREST,YT_LOAN_INTEREST,
					TRUST_LOAN_SURETY_FEE, CONSIGN_LOAN_INTEREST,
					CONSIGN_LOAN_COMMISION_FEE, GRANT_DEBIT_INTEREST,
					RECEIVE_DEBIT_INTEREST, FUND_REQUEST, FUND_RETURN,
					BILL_REGISTER, BILL_USE, UPGATHERING, COMMISSION,
					OPENMARGIN, WITHDRAWMARGIN, RECEIVEFINANCE, RETURNFINANCE,
					INTERESTFEEPAYMENT_COMMISION, INTERESTFEEPAYMENT_SURETY,BANKPAY_NOTONLINE,CAPITALUP,CAPITALDOWN };

			// 中交修改，去掉不需要的查询类型
			/**
			 * long[] lTemp = { BANKRECEIVE ,BANKPAY
			 * ,INTERNALVIREMENT,OTHERPAY,BANKPAY_DOWNTRANSFER
			 * ,OPENFIXEDDEPOSIT, FIXEDTOCURRENTTRANSFER,FIXEDCONTINUETRANSFER
			 * ,OPENNOTIFYACCOUNT ,NOTIFYDEPOSITDRAW ,
			 * TRUSTLOANGRANT,TRUSTLOANRECEIVE,GENERALLEDGER,SPECIALOPERATION,SHUTDOWN,INTERESTFEEPAYMENT,
			 * INTEREST_FEE_PAY_CURRENT,FIXED_DEPOSIT_PREDRAW_INTEREST,TRUST_LOAN_PREDRAW_INTEREST,
			 * TRUST_LOAN_INTEREST,TRUST_LOAN_SURETY_FEE,REPORTLOSS,REPORTFIND,CHANGECERTIFICATE,FREEZE,
			 * DEFREEZE,COMMISSION,DRAW_PRINCIPAL,DRAW_INTEREST,INTERESTFEEPAYMENT_SURETY };
			 */

			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			long[] constantArray = Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$TransactionType",
							officeID, currencyID);

			Sett_SpecialOperationDAO specialDao = new Sett_SpecialOperationDAO();
			
			//modified by mzh_fu 2007/06/05
//			long[] specialArray = specialDao
//					.getAllSpecialOperationForConstant();
			long[] specialArray = specialDao.getSpecialOperationIdsByOfficeID(officeID);
			
			if (specialArray != null && specialArray.length > 0) {
				long[] arrReturn = new long[constantArray.length
						+ specialArray.length];
				System.arraycopy(constantArray, 0, arrReturn, 0,
						constantArray.length);
				System.arraycopy(specialArray, 0, arrReturn,
						constantArray.length, specialArray.length);
				
				return arrReturn;
			} else// 如果数据库中没有设置特殊业务类型，则直接返回constant中定义的类型
			{
				return constantArray;
			}
		}

		/**
		 * 获得产生银行转账指令地业务类型编码
		 * 
		 * @return long[]
		 */
		public static final long[] getAllCodeForGenerateBankInstruction() {
			long[] lTemp = { BANKPAY, FIXEDTOCURRENTTRANSFER,
					FIXEDCONTINUETRANSFER, NOTIFYDEPOSITDRAW, TRUSTLOANGRANT,
					CONSIGNLOANGRANT, DISCOUNTGRANT, SPECIALOPERATION,
					FUND_REQUEST, INITIATIVE_TURNIN };
			return lTemp;
		}

		/**
		 * 获得产生证券投资结算业务类型编码
		 * 
		 * @return long[]
		 */
		public static final long[] getAllCodeForSecurities() {
			long[] lTemp = { SECURITIESRECEIVE, SECURITIESPAY,
					SEC_WTLC_RECEIVE, SEC_WTLC_PAY, SEC_ZQCX_RECEIVE,
					SEC_ZQCX_PAY };
			return lTemp;
		}

		/**
		 * 获得产生国机母子公司结算业务类型编码
		 * 
		 * @return long[]
		 */
		public static final long[] getAllCodeForCapitalSupervise() {
			long[] lTemp = { GRANT_DEBIT_INTEREST, RECEIVE_DEBIT_INTEREST };
			return lTemp;
		}
		
		/**
		 * 获得产生合并凭证号结算业务类型编码
		 * 
		 * @return long[]
		 */
		public static final long[] getAllCodeForMergevoucher() {
			long[] lTemp = { BANKRECEIVE, BANKPAY,
					INTERNALVIREMENT, CONSIGNSAVE,
					ONETOMULTI, OTHERPAY, OPENFIXEDDEPOSIT,
					FIXEDTOCURRENTTRANSFER, FIXEDCONTINUETRANSFER,
					OPENNOTIFYACCOUNT, NOTIFYDEPOSITDRAW, TRUSTLOANGRANT,
					TRUSTLOANRECEIVE, CONSIGNLOANGRANT, CONSIGNLOANRECEIVE,
					 DISCOUNTGRANT,
					DISCOUNTRECEIVE,
					GENERALLEDGER, TRANSFEE, SPECIALOPERATION,INTERESTFEEPAYMENT,
					FIXED_DEPOSIT_PREDRAW_INTEREST,
					TRUST_LOAN_PREDRAW_INTEREST,TRUST_LOAN_INTEREST,
					CONSIGN_LOAN_INTEREST,
					CONSIGN_LOAN_COMMISION_FEE,DISCOUNTACCRUAL,DISCOUNT_LOAN_INTEREST,
					CONSIGN_LOAN_PREDRAW_INTEREST,CURRENT_DEPOSIT_PREDRAW_INTEREST,
					CURRENT_NOTIFY_PREDRAW_INTEREST
					};

			return lTemp;
		}

		/**
		 * 获得联通需要的资金申领和资金上存业务类型编码
		 * 
		 * @author fanyang
		 * 
		 * TODO To change the template for this generated type comment go to
		 * Window - Preferences - Java - Code Style - Code Templates
		 */
		public static final long[] getAllCodeForICBC() {
			long[] lTemp = { FUND_REQUEST, FUND_RETURN };
			return lTemp;
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型
		 *            （0，显示全部；1,显示产生银行转账指令地业务类型）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank，是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				case 1:
					// 银企接口专用
					lArrayID = getAllCodeForGenerateBankInstruction();
					break;
				case 2:
					// 证券结算接口使用
					lArrayID = getAllCodeForSecurities();
					break;
				case 3:
					// 国机母子公司业务使用
					lArrayID = getAllCodeForCapitalSupervise();
				case 4:
					// 获得联通需要的资金申领和资金上存业务类型编码
					lArrayID = getAllCodeForICBC();
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				case 1:
					// 银企接口专用
					lArrayID = getAllCodeForGenerateBankInstruction();
					break;
				case 2:
					// 证券结算接口使用
					lArrayID = getAllCodeForSecurities();
					break;
				case 3:
					// 国机母子公司业务使用
					lArrayID = getAllCodeForCapitalSupervise();
				case 4:
					// 获得联通需要的资金申领和资金上存业务类型编码
					lArrayID = getAllCodeForICBC();
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}
	}


	/**
	 * 编码规则业务类型
	 * 
	 * @author wlming
	 * 
	 * To change the template for this generated type comment go to
	 * Window>Preferences>Java>Code Generation>Code and Comments
	 */
	public static class CodingRuleTransactionType {
		// 活期存款业务类型
		public static final long BANKRECEIVE = SETTConstant.TransactionType.BANKRECEIVE; // 银行收款

		public static final long CASHRECEIVE = SETTConstant.TransactionType.CASHRECEIVE; // 现金收款

		public static final long BANKPAY = SETTConstant.TransactionType.BANKPAY; // 银行付款

		public static final long CHECKPAY = SETTConstant.TransactionType.CHECKPAY; // 支票付款

		public static final long CASHPAY = SETTConstant.TransactionType.CASHPAY; // 现金付款

		public static final long DRAFTPAY = SETTConstant.TransactionType.DRAFTPAY; // 票汇付款

		public static final long INTERNALVIREMENT = SETTConstant.TransactionType.INTERNALVIREMENT; // 内部转账

		public static final long CONSIGNRECEIVE = SETTConstant.TransactionType.CONSIGNRECEIVE; // 委托收款

		public static final long CONSIGNSAVE = SETTConstant.TransactionType.CONSIGNSAVE; // 委托存款

		public static final long CAUTIONMONEYSAVE = SETTConstant.TransactionType.CAUTIONMONEYSAVE; // 保证金存款

		public static final long NATIONALDEBT_BUY = SETTConstant.TransactionType.NATIONALDEBT_BUY; // 国债买入

		public static final long NATIONALDEBT_SELL = SETTConstant.TransactionType.NATIONALDEBT_SELL; // 国债卖出

		public static final long ONETOMULTI = SETTConstant.TransactionType.ONETOMULTI; // 一付多收

		public static final long OTHERPAY = SETTConstant.TransactionType.OTHERPAY; // 其他付款

		public static final long SUBCLIENT_BANKRECEIVE = SETTConstant.TransactionType.SUBCLIENT_BANKRECEIVE; // 下属单位银行收款

		public static final long SUBCLIENT_BANKPAY = SETTConstant.TransactionType.SUBCLIENT_BANKPAY; // 下属单位银行付款
		
		public static final long BANKPAY_NOTONLINE = SETTConstant.TransactionType.BANKPAY_NOTONLINE; // 下属单位银行付款落地处理
		// 中交新增 银行付款 交易类型
		public static final long BANKPAY_DOWNTRANSFER = SETTConstant.TransactionType.BANKPAY_DOWNTRANSFER;// 银行付款－下划成员单位

		// 定期业务
		public static final long OPENFIXEDDEPOSIT = SETTConstant.TransactionType.OPENFIXEDDEPOSIT; // 定期开立

		public static final long FIXEDTOCURRENTTRANSFER = SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER; // 定期转活期（定期支取）

		public static final long FIXEDCONTINUETRANSFER = SETTConstant.TransactionType.FIXEDCONTINUETRANSFER; // 定期续存-到期续存

		// 通知业务
		public static final long OPENNOTIFYACCOUNT = SETTConstant.TransactionType.OPENNOTIFYACCOUNT; // 通知存款开立

		public static final long NOTIFYDEPOSITDRAW = SETTConstant.TransactionType.NOTIFYDEPOSITDRAW; // 通知存款支取

		// 保证金业务
		public static final long OPENMARGIN = SETTConstant.TransactionType.OPENMARGIN; // 担保收款

		public static final long WITHDRAWMARGIN = SETTConstant.TransactionType.WITHDRAWMARGIN; // 保后处理

		// 融资租赁业务 add by feiye 2006.4.26
		public static final long RECEIVEFINANCE = SETTConstant.TransactionType.RECEIVEFINANCE; // 融资租赁收款

		public static final long RETURNFINANCE = SETTConstant.TransactionType.RETURNFINANCE; // 融资租赁还款

		// 贷款业务
		public static final long TRUSTLOANGRANT = SETTConstant.TransactionType.TRUSTLOANGRANT; // 信托贷款发放(自营贷款发放)(内部资金占用发放)

		public static final long TRUSTLOANRECEIVE = SETTConstant.TransactionType.TRUSTLOANRECEIVE; // 信托贷款收回（自营贷款收回）（内部资金占用收回）

		public static final long CONSIGNLOANGRANT = SETTConstant.TransactionType.CONSIGNLOANGRANT; // 委托贷款发放

		public static final long CONSIGNLOANRECEIVE = SETTConstant.TransactionType.CONSIGNLOANRECEIVE; // 委托贷款收回

		public static final long DISCOUNTGRANT = SETTConstant.TransactionType.DISCOUNTGRANT; // 贴现发放

		public static final long DISCOUNTRECEIVE = SETTConstant.TransactionType.DISCOUNTRECEIVE; // 贴现收回

		public static final long MULTILOANRECEIVE = SETTConstant.TransactionType.MULTILOANRECEIVE; // 多笔贷款收回

		public static final long TRANSDISCOUNTGRANT = SETTConstant.TransactionType.TRANSDISCOUNTGRANT;//转贴现发放
		// 银团贷款
		public static final long BANKGROUPLOANGRANT = SETTConstant.TransactionType.BANKGROUPLOANGRANT; // 银团贷款发放

		public static final long BANKGROUPLOANRECEIVE = SETTConstant.TransactionType.BANKGROUPLOANRECEIVE; // 银团贷款收回

		// 委托业务（中油专用）
		public static final long CONSIGNUPRECEIVE = SETTConstant.TransactionType.CONSIGNUPRECEIVE; // 委托上收资金

		public static final long CONSIGNUPSAVE = SETTConstant.TransactionType.CONSIGNUPSAVE; // 上存资金调回及发放负息资金

		public static final long CONSIGNUPSAVERECEIVE = SETTConstant.TransactionType.CONSIGNUPSAVERECEIVE; // 上存资金－上收及调回

		public static final long SHORTDEBTRECEIVE = SETTConstant.TransactionType.SHORTDEBTRECEIVE; // 还短负

		public static final long CONSIGNCAPITALOPERATION = SETTConstant.TransactionType.CONSIGNCAPITALOPERATION; // 客户委托资金调拨

		public static final long SHORTLOANGRANT = SETTConstant.TransactionType.SHORTLOANGRANT; // 发放短期贷款

		public static final long CYCLOANGRANT = SETTConstant.TransactionType.CYCLOANGRANT; // 发放循环贷款

		// 其它
		public static final long GENERALLEDGER = SETTConstant.TransactionType.GENERALLEDGER; // 总账类(总账业务)

		public static final long TRANSFEE = SETTConstant.TransactionType.TRANSFEE; // 交易费用

		public static final long SPECIALOPERATION = SETTConstant.TransactionType.SPECIALOPERATION; // 特殊交易

		public static final long COMPATIBILITY = SETTConstant.TransactionType.COMPATIBILITY; // 兼容交易

		public static final long TRANSABATEMENT = SETTConstant.TransactionType.TRANSABATEMENT; // 转贴现卖出自动冲销

		// 34已经被占用
		public static final long SHORTLOANRECEIVE = SETTConstant.TransactionType.SHORTLOANRECEIVE; // 短期贷款收回

		public static final long INTERESTGRANT = SETTConstant.TransactionType.INTERESTGRANT; // 发放利息

		public static final long SHUTDOWN = SETTConstant.TransactionType.SHUTDOWN; // 关机

		public static final long CYCLOANRECEIVE = SETTConstant.TransactionType.CYCLOANRECEIVE; // 循环贷款收回

		public static final long BANKUPSAVE = SETTConstant.TransactionType.BANKUPSAVE; // 银行上收

		public static final long BANKUPRECEIVE = SETTConstant.TransactionType.BANKUPRECEIVE; // 银行调回

		public static final long BANKINTEREST = SETTConstant.TransactionType.BANKINTEREST; // 银行发放负息资金

		public static final long CYCCONSIGNLOANGRANT = SETTConstant.TransactionType.CYCCONSIGNLOANGRANT; // 循环委托贷款发放

		public static final long CYCCONSIGNLOANRECEIVE = SETTConstant.TransactionType.CYCCONSIGNLOANRECEIVE; // 循环委托贷款收回

		public static final long INTERESTFEEPAYMENT = SETTConstant.TransactionType.INTERESTFEEPAYMENT; // 利息费用支付

		// 46已经被占用
		// 47已经被占用
		// 证券投资结算
		public static final long SECURITIESRECEIVE = SETTConstant.TransactionType.SECURITIESRECEIVE; // 财务公司收款（证券投资结算）

		public static final long SECURITIESPAY = SETTConstant.TransactionType.SECURITIESPAY; // 财务公司付款（证券投资结算）

		public static final long SEC_WTLC_RECEIVE = SETTConstant.TransactionType.SEC_WTLC_RECEIVE; // 委托理财收款（证券投资结算）

		public static final long SEC_WTLC_PAY = SETTConstant.TransactionType.SEC_WTLC_PAY; // 委托理财付款（证券投资结算）

		public static final long SEC_ZQCX_RECEIVE = SETTConstant.TransactionType.SEC_ZQCX_RECEIVE; // 债券承销收款（证券投资结算）

		public static final long SEC_ZQCX_PAY = SETTConstant.TransactionType.SEC_ZQCX_PAY; // 债券承销付款（证券投资结算）

		// 资金集中管理业务（国机专用）
		public static final long GRANT_DEBIT_INTEREST = SETTConstant.TransactionType.GRANT_DEBIT_INTEREST; // 发放负息资金

		public static final long RECEIVE_DEBIT_INTEREST = SETTConstant.TransactionType.RECEIVE_DEBIT_INTEREST; // 收回负息资金

		// 利息交易类型
		public static final long INTEREST_FEE_PAY_CURRENT = SETTConstant.TransactionType.INTEREST_FEE_PAY_CURRENT; // 利息费用支付活期结息

		public static final long FIXED_DEPOSIT_PREDRAW_INTEREST = SETTConstant.TransactionType.FIXED_DEPOSIT_PREDRAW_INTEREST; // 定期存款计提应付利息（含冲销）

		public static final long TRUST_LOAN_PREDRAW_INTEREST = SETTConstant.TransactionType.TRUST_LOAN_PREDRAW_INTEREST; // 自营贷款计提应收利息（含冲销）

		public static final long TRUST_LOAN_INTEREST = SETTConstant.TransactionType.TRUST_LOAN_INTEREST; // 自营贷款结息(内部资金占用结息) 
		
		public static final long YT_LOAN_INTEREST = SETTConstant.TransactionType.YT_LOAN_INTEREST; // 银团贷款结息 ADD BY ZCWANG 20076-23
		
		public static final long YT_LOAN_PREDRAW_INTEREST = SETTConstant.TransactionType.YT_LOAN_PREDRAW_INTEREST; // 银团贷款计提应收利息（含冲销） ADD BY ZCWANG 20076-28

		public static final long TRUST_LOAN_SURETY_FEE = SETTConstant.TransactionType.TRUST_LOAN_SURETY_FEE; // 自营贷款结担保费（内部资金占用担保）

		public static final long CONSIGN_LOAN_INTEREST = SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST; // 委托贷款结息

		public static final long CONSIGN_LOAN_COMMISION_FEE = SETTConstant.TransactionType.CONSIGN_LOAN_COMMISION_FEE; // 委托贷款结手续费

		public static final long MARGIN_DEPOSIT_PREDRAW_INTEREST = SETTConstant.TransactionType.MARGIN_DEPOSIT_PREDRAW_INTEREST; // 保证金存款计提应付利息（含冲销）

		public static final long INTEREST_FEE_PAY_MARGIN = SETTConstant.TransactionType.INTEREST_FEE_PAY_MARGIN; // 利息费用支付保证金结息
		
		public static final long YT_LOAN_COMMISION_FEE = SETTConstant.TransactionType.YT_LOAN_COMMISION_FEE; //银团贷款结手续费
		// 108已经被占用
		// 109已经被占用
		// 银行收款业务(海尔专用)
		public static final long CHECK_RECEIVE = SETTConstant.TransactionType.CHECK_RECEIVE; // 支票收款

		public static final long REMIT_RECEIVE = SETTConstant.TransactionType.REMIT_RECEIVE; // 汇款收款

		public static final long OTHER_RECEIVE = SETTConstant.TransactionType.OTHER_RECEIVE; // 其它收款

		// 银行付款业务(海尔专用)
		public static final long CHECK_PAY = SETTConstant.TransactionType.CHECK_PAY; // 支票付款

		public static final long REMIT_PAY = SETTConstant.TransactionType.REMIT_PAY; // 汇款付款

		public static final long TAX_PAY = SETTConstant.TransactionType.TAX_PAY; // 税单付款

		public static final long OTHER_PAY = SETTConstant.TransactionType.OTHER_PAY; // 其它付款

		// 表外业务(上海电气专用)
		public static final long DELEGATION_INCOME_OFFBALANCE = SETTConstant.TransactionType.DELEGATION_INCOME_OFFBALANCE; // 1.
																		// 代保管有价值品类表外业务收入

		public static final long DELEGATION_PAYOUT_OFFBALANCE = SETTConstant.TransactionType.DELEGATION_PAYOUT_OFFBALANCE; // 1.
																		// 代保管有价值品类表外业务付出

		public static final long CONSIGN_INCOME_OFFBALANCE = SETTConstant.TransactionType.CONSIGN_INCOME_OFFBALANCE; // 2.
																	// 贷款未收利息类表外业务收入

		public static final long CONSIGN_PAYOUT_OFFBALANCE = SETTConstant.TransactionType.CONSIGN_PAYOUT_OFFBALANCE; // 2.
																	// 贷款未收利息类表外业务付出

		public static final long DISCOUNT_INCOME_OFFBALANCE = SETTConstant.TransactionType.DISCOUNT_INCOME_OFFBALANCE; // 3.
																	// 商业汇票贴现类表外业务收入

		public static final long DISCOUNT_PAYOUT_OFFBALANCE = SETTConstant.TransactionType.DISCOUNT_PAYOUT_OFFBALANCE; // 3.
																	// 商业汇票贴现类表外业务付出

		public static final long ASSURE_INCOME_OFFBALANCE = SETTConstant.TransactionType.ASSURE_INCOME_OFFBALANCE; // 4.
																	// 开出保函凭信类表外业务收入

		public static final long ASSURE_PAYOUT_OFFBALANCE = SETTConstant.TransactionType.ASSURE_PAYOUT_OFFBALANCE; // 4.
																	// 开出保函凭信类表外业务付出

		// 冻结、挂失
		public static final long REPORTLOSS = SETTConstant.TransactionType.REPORTLOSS; // 挂失

		public static final long REPORTFIND = SETTConstant.TransactionType.REPORTFIND; // 解挂

		public static final long CHANGECERTIFICATE = SETTConstant.TransactionType.CHANGECERTIFICATE; // 焕发证书

		public static final long FREEZE = SETTConstant.TransactionType.FREEZE; // 冻结

		public static final long DEFREEZE = SETTConstant.TransactionType.DEFREEZE; // 解冻
		
		public static final long ACCOUNTOPEN = SETTConstant.TransactionType.ACCOUNTOPEN;//账户开户
		
		public static final long ACCOUNTMODIFY = SETTConstant.TransactionType.ACCOUNTMODIFY;//账户修改
		

		// 联通公司专用
		public static final long FUND_REQUEST = SETTConstant.TransactionType.FUND_REQUEST; // 资金申领,活期类业务

		public static final long FUND_RETURN = SETTConstant.TransactionType.FUND_RETURN; // 资金上存,活期类业务

		// 针对财务公司内部活期账户
		public static final long UPGATHERING = SETTConstant.TransactionType.UPGATHERING;// 资金上收

		// 收取手续费
		public static final long COMMISSION = SETTConstant.TransactionType.COMMISSION;// 交易手续费收取

		// 票据管理专用
		public static final long BILL_REGISTER = SETTConstant.TransactionType.BILL_REGISTER;

		public static final long BILL_USE = SETTConstant.TransactionType.BILL_USE;

		// 南航财务新增 银行付款 交易类型
		public static final long BANKPAY_FINCOMPANYPAY = SETTConstant.TransactionType.BANKPAY_FINCOMPANYPAY;// 银行付款－财司代付

		public static final long BANKPAY_PAYSUBACCOUNT = SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT;// 银行付款-拨子账户

		// 南航财务新增 银行收款 交易类型
		public static final long BANKRECEIVE_GATHERING = SETTConstant.TransactionType.BANKRECEIVE_GATHERING;// 银行收款－上收款项

		public static final long BANKRECEIVE_SUBCLIENT = SETTConstant.TransactionType.BANKRECEIVE_SUBCLIENT;// 银行收款－成员单位收款

		public static final long BANKRECEIVE_TOSUBCLIENT = SETTConstant.TransactionType.BANKRECEIVE_TOSUBCLIENT;// 银行收款－转成员单位收款

		// 银行接口新增
		public static final long INITIATIVE_TURNIN = SETTConstant.TransactionType.INITIATIVE_TURNIN; // 主动上收业务

		// 方便程序处理，用来区分是本金或利息的指令
		public static final long DRAW_PRINCIPAL = SETTConstant.TransactionType.DRAW_PRINCIPAL; // 通知、定期支取本金

		public static final long DRAW_INTEREST = SETTConstant.TransactionType.DRAW_INTEREST; // 通知、定期支取利息

		public static final long INTERESTFEEPAYMENT_SURETY = SETTConstant.TransactionType.INTERESTFEEPAYMENT_SURETY; // 利息支付－自营贷款担保费(利息支付－内部资金占用担保费)

		public static final long INTERESTFEEPAYMENT_COMMISION = SETTConstant.TransactionType.INTERESTFEEPAYMENT_COMMISION; // 利息支付－委托贷款手续费

        public static final long CAPITALTRANSFER = SETTConstant.TransactionType.CAPITALTRANSFER;//资金划拨
        
        public static final long DISCOUNTACCRUAL = SETTConstant.TransactionType.DISCOUNTACCRUAL;//贴现贷款计提应收利息（含冲销）
        
        public static final long DISCOUNT_LOAN_INTEREST = SETTConstant.TransactionType.DISCOUNT_LOAN_INTEREST;//贴现贷款结息
        
        public static final long CONSIGN_LOAN_PREDRAW_INTEREST = SETTConstant.TransactionType.CONSIGN_LOAN_PREDRAW_INTEREST;//委托贷款计提应收利息（含冲销）
        
        public static final long CURRENT_DEPOSIT_PREDRAW_INTEREST = SETTConstant.TransactionType.CURRENT_DEPOSIT_PREDRAW_INTEREST; //活期存款计提应付利息（含冲销）

        public static final long CURRENT_NOTIFY_PREDRAW_INTEREST = SETTConstant.TransactionType.CURRENT_NOTIFY_PREDRAW_INTEREST; //通知存款计提应付利息（含冲销）
        
        //结算其他－－累计费用调整
        public static final long ABJUSTINTEREST = SETTConstant.TransactionType.ABJUSTINTEREST;//累计费用调整
        
        
        public static final long FIXEDDEPOSITRECEIPT = 9999;  //定期存单
        
        public static final long NOTIFYACCOUNTRECEIPT = 9998; //通知存单
        
        public static final long MARGINRECEIPT = 9997;    //保证金存单
        
		// 上划
		public static final long CAPITALUP = SETTConstant.TransactionType.CAPITALUP; // 企业资金上划
		// 下拨
		public static final long CAPITALDOWN = SETTConstant.TransactionType.CAPITALDOWN; // 企业资金下拨
        
		public static boolean isCurrentTransaction(long transactionTypeID) {
			if (transactionTypeID == BANKRECEIVE
					|| transactionTypeID == CASHRECEIVE
					|| transactionTypeID == BANKPAY
					|| transactionTypeID == SUBCLIENT_BANKPAY
					|| transactionTypeID == SUBCLIENT_BANKRECEIVE
					|| transactionTypeID == CHECKPAY
					|| transactionTypeID == CASHPAY
					|| transactionTypeID == DRAFTPAY
					|| transactionTypeID == INTERNALVIREMENT
					|| transactionTypeID == CONSIGNRECEIVE
					|| transactionTypeID == CONSIGNSAVE
					|| transactionTypeID == CAUTIONMONEYSAVE
					|| transactionTypeID == NATIONALDEBT_BUY
					|| transactionTypeID == NATIONALDEBT_SELL
					|| transactionTypeID == ONETOMULTI
					|| transactionTypeID == OTHERPAY
					|| transactionTypeID == GRANT_DEBIT_INTEREST
					|| transactionTypeID == RECEIVE_DEBIT_INTEREST
					|| transactionTypeID == CHECK_RECEIVE
					|| transactionTypeID == REMIT_RECEIVE
					|| transactionTypeID == OTHER_RECEIVE
					|| transactionTypeID == CHECK_PAY
					|| transactionTypeID == REMIT_PAY
					|| transactionTypeID == TAX_PAY
					|| transactionTypeID == OTHER_PAY
					|| transactionTypeID == FUND_REQUEST
					|| transactionTypeID == FUND_RETURN
					|| transactionTypeID == BANKPAY_FINCOMPANYPAY
					|| transactionTypeID == BANKPAY_PAYSUBACCOUNT
					|| transactionTypeID == BANKRECEIVE_GATHERING
					|| transactionTypeID == BANKRECEIVE_SUBCLIENT
					|| transactionTypeID == BANKRECEIVE_TOSUBCLIENT
					|| transactionTypeID == BANKPAY_DOWNTRANSFER
					|| transactionTypeID == CAPITALUP
					|| transactionTypeID == CAPITALDOWN
					) {
				return true;
			} else {
				return false;
			}
		}

		public static boolean isFixedTransaction(long transactionTypeID) {
			if (transactionTypeID <= NOTIFYDEPOSITDRAW
					&& transactionTypeID >= OPENFIXEDDEPOSIT) {
				return true;
			} else {
				return false;
			}
		}

		public static boolean isLoanTransaction(long transactionTypeID) {
			if ((transactionTypeID <= TRUSTLOANGRANT && transactionTypeID >= MULTILOANRECEIVE)
					|| (transactionTypeID >= BANKGROUPLOANGRANT && transactionTypeID <= BANKGROUPLOANRECEIVE)) {
				return true;
			} else
				return false;
		}

		/**
		 * 是否利息交易类型
		 * 
		 * @param transactionTypeID
		 * @return
		 */
		public static boolean isInterestTransaction(long transactionTypeID) {
			if (transactionTypeID == INTEREST_FEE_PAY_CURRENT
					|| transactionTypeID == INTEREST_FEE_PAY_MARGIN
					|| transactionTypeID == FIXED_DEPOSIT_PREDRAW_INTEREST
					|| transactionTypeID == TRUST_LOAN_PREDRAW_INTEREST
					|| transactionTypeID == YT_LOAN_PREDRAW_INTEREST
					|| transactionTypeID == TRUST_LOAN_INTEREST
					|| transactionTypeID == YT_LOAN_INTEREST
					|| transactionTypeID == TRUST_LOAN_SURETY_FEE
					|| transactionTypeID == CONSIGN_LOAN_INTEREST
					|| transactionTypeID == CONSIGN_LOAN_COMMISION_FEE
					|| transactionTypeID == MARGIN_DEPOSIT_PREDRAW_INTEREST) {
				return true;
			} else {
				return false;
			}
		}

		/**
		 * 根据“账户类型”、“利息费用计算的费用类型”和“利息费用结算的操作类型”获得利息交易类型
		 * 
		 * @param lAccountType
		 * @param lInterestType
		 * @param lInterestOperateType
		 * @return long
		 */
		public static long getTransactionType(long lAccountType,
				long lInterestType, long lInterestOperateType) {
			long result = -1;
			switch ((int) lInterestOperateType) {
			case (int) SETTConstant.InterestOperateType.INTERESTSETTLEMENT: {
				if (SETTConstant.AccountType.isCurrentAccountType(lAccountType)
						|| SETTConstant.AccountType
								.isOtherDepositAccountType(lAccountType)) {
					if (lInterestType == SETTConstant.InterestFeeType.INTEREST)
						result = INTEREST_FEE_PAY_CURRENT;
				} else if (SETTConstant.AccountType
						.isMarginAccountType(lAccountType)) {
					if (lInterestType == SETTConstant.InterestFeeType.INTEREST)
						result = INTEREST_FEE_PAY_MARGIN;
				} else if (SETTConstant.AccountType
						.isLoanAccountType(lAccountType)) {
					long lAccountGroupType = SETTConstant.AccountType
							.getAccountGroupTypeIDByAccountTypeID(lAccountType);
					if (SETTConstant.AccountGroupType.TRUST == lAccountGroupType
							&& (lInterestType == SETTConstant.InterestFeeType.INTEREST
									|| lInterestType == SETTConstant.InterestFeeType.COMPOUNDINTEREST || lInterestType == SETTConstant.InterestFeeType.FORFEITINTEREST))
						result = TRUST_LOAN_INTEREST;
					//ADD BY ZCWANG 2007-6-23
					else if (SETTConstant.AccountGroupType.YT == lAccountGroupType
							&& (lInterestType == SETTConstant.InterestFeeType.INTEREST
									|| lInterestType == SETTConstant.InterestFeeType.COMPOUNDINTEREST || lInterestType == SETTConstant.InterestFeeType.FORFEITINTEREST))
						result = YT_LOAN_INTEREST;
					//
					else if (SETTConstant.AccountGroupType.CONSIGN == lAccountGroupType
							&& (lInterestType == SETTConstant.InterestFeeType.INTEREST
									|| lInterestType == SETTConstant.InterestFeeType.COMPOUNDINTEREST || lInterestType == SETTConstant.InterestFeeType.FORFEITINTEREST))
						result = CONSIGN_LOAN_INTEREST;
					else if (lInterestType == SETTConstant.InterestFeeType.ASSURE)
						result = TRUST_LOAN_SURETY_FEE;
					else if (lInterestType == SETTConstant.InterestFeeType.COMMISION)
						result = CONSIGN_LOAN_COMMISION_FEE;
				}
				break;
			}
			case (int) SETTConstant.InterestOperateType.PREDRAWINTEREST: {
				if (SETTConstant.AccountType.isFixAccountType(lAccountType)
						|| SETTConstant.AccountType
								.isNotifyAccountType(lAccountType)) {
					if (lInterestType == SETTConstant.InterestFeeType.INTEREST)
						result = FIXED_DEPOSIT_PREDRAW_INTEREST;
				}
				else if (SETTConstant.AccountType
						.isLoanAccountType(lAccountType)) {
					long lAccountGroupType = SETTConstant.AccountType
					.getAccountGroupTypeIDByAccountTypeID(lAccountType);
					if ( SETTConstant.AccountGroupType.TRUST == lAccountGroupType && lInterestType == SETTConstant.InterestFeeType.INTEREST)
					{
						result = TRUST_LOAN_PREDRAW_INTEREST;
					}
					else if(SETTConstant.AccountGroupType.YT == lAccountGroupType && lInterestType == SETTConstant.InterestFeeType.INTEREST)
					{
						result = YT_LOAN_PREDRAW_INTEREST;
					}
				} else if (SETTConstant.AccountType
						.isMarginAccountType(lAccountType)) {
					if (lInterestType == SETTConstant.InterestFeeType.INTEREST)
						result = MARGIN_DEPOSIT_PREDRAW_INTEREST;
				}
				break;
			}
			case (int) SETTConstant.InterestOperateType.CLEANPREDRAWINTEREST: {
				if (SETTConstant.AccountType.isFixAccountType(lAccountType)) {
					if (lInterestType == SETTConstant.InterestFeeType.PREDRAWINTEREST)
						result = FIXED_DEPOSIT_PREDRAW_INTEREST;
				} else if (SETTConstant.AccountType
						.isLoanAccountType(lAccountType)) {
					long lAccountGroupType = SETTConstant.AccountType
					.getAccountGroupTypeIDByAccountTypeID(lAccountType);
					if (SETTConstant.AccountGroupType.TRUST == lAccountGroupType && lInterestType == SETTConstant.InterestFeeType.PREDRAWINTEREST)
					{
						result = TRUST_LOAN_PREDRAW_INTEREST;
					}
					else if(SETTConstant.AccountGroupType.YT == lAccountGroupType && lInterestType == SETTConstant.InterestFeeType.PREDRAWINTEREST)
					{
						result = YT_LOAN_PREDRAW_INTEREST;
					}
				} else if (SETTConstant.AccountType
						.isMarginAccountType(lAccountType)) {
					if (lInterestType == SETTConstant.InterestFeeType.PREDRAWINTEREST)
						result = MARGIN_DEPOSIT_PREDRAW_INTEREST;
				}
				break;
			}
			}
			return result;
		}

		public static final String getName(long lCode) {
			String strReturn = "";
			switch ((int) lCode) {
			case (int) BANKRECEIVE:
				strReturn = "银行收款";
				break;
			case (int) BANKRECEIVE_GATHERING:
				strReturn = "银行收款－上收款项";
				break;
			case (int) BANKRECEIVE_SUBCLIENT:
				strReturn = "银行收款－成员单位收款";
				break;
			case (int) BANKRECEIVE_TOSUBCLIENT:
				strReturn = "银行收款－转成员单位收款";
				break;
			case (int) CASHRECEIVE:
				strReturn = "现金收款";
				break;
			case (int) BANKPAY:
				strReturn = "银行付款";
				break;
			case (int) BANKPAY_DOWNTRANSFER:
				strReturn = "银行付款－下划成员单位";
				break;
			case (int) BANKPAY_FINCOMPANYPAY:
				strReturn = "银行付款-财司代付";
				break;
			case (int) BANKPAY_PAYSUBACCOUNT:
				strReturn = "银行付款-拨子账户";
				break;
			case (int) SUBCLIENT_BANKRECEIVE:
				strReturn = "下属单位银行收款";
				break;
			case (int) SUBCLIENT_BANKPAY:
				strReturn = "下属单位银行付款";
				break;
			case (int) BANKPAY_NOTONLINE:
				strReturn = "下属单位银行付款落地处理";
				break;
			case (int) CHECKPAY:
				strReturn = "支票付款";
				break;
			case (int) CASHPAY:
				strReturn = "现金付款";
				break;
			case (int) DRAFTPAY:
				strReturn = "票汇付款";
				break;
			case (int) INTERNALVIREMENT:
				strReturn = "内部转账";
				break;
			case (int) CONSIGNRECEIVE:
				strReturn = "委托收款";
				break;
			case (int) CONSIGNSAVE:
				strReturn = "委托存款";
				break;
			case (int) CAUTIONMONEYSAVE:
				strReturn = "保证金存款";
				break;
			case (int) NATIONALDEBT_BUY:
				strReturn = "国债买入";
				break;
			case (int) NATIONALDEBT_SELL:
				strReturn = "国债卖出";
				break;
			case (int) ONETOMULTI:
				strReturn = "多借多贷";
				break;
			case (int) OTHERPAY:
				strReturn = "其他付款";
				break;
			case (int) OPENFIXEDDEPOSIT:
				strReturn = "定期开立";
				break;
			case (int) FIXEDTOCURRENTTRANSFER:
				strReturn = "定期支取";
				break;
			case (int) FIXEDCONTINUETRANSFER:
				strReturn = "到期续存";
				break;
			case (int) OPENNOTIFYACCOUNT:
				strReturn = "通知存款开立";
				break;
			case (int) NOTIFYDEPOSITDRAW:
				strReturn = "通知存款支取";
				break;
			case (int) TRUSTLOANGRANT:
				strReturn = "自营贷款发放";
				break;
			case (int) TRUSTLOANRECEIVE:
				strReturn = "自营贷款收回";
				break;
			case (int) CONSIGNLOANGRANT:
				strReturn = "委托贷款发放";
				break;
			case (int) CONSIGNLOANRECEIVE:
				strReturn = "委托贷款收回";
				break;
			case (int) DISCOUNTGRANT:
				strReturn = "贴现发放";
				break;
			case (int) DISCOUNTRECEIVE:
				strReturn = "贴现收回";
				break;
			case (int) MULTILOANRECEIVE:
				strReturn = "多笔贷款收回";
				break;
			case (int) TRANSDISCOUNTGRANT:
				strReturn = "转贴现发放";
				break;
			case (int) CONSIGNUPRECEIVE:
				strReturn = "委托上收资金";
				break;
			case (int) CONSIGNUPSAVE:
				strReturn = "上存资金调回及发放负息资金";
				break;
			case (int) CONSIGNUPSAVERECEIVE:
				strReturn = "上存资金－上收及调回";
				break;
			case (int) SHORTDEBTRECEIVE:
				strReturn = "还短负";
				break;
			case (int) CONSIGNCAPITALOPERATION:
				strReturn = "客户委托资金调拨";
				break;
			case (int) SHORTLOANGRANT:
				strReturn = "发放短期贷款";
				break;
			case (int) CYCLOANGRANT:
				strReturn = "发放循环贷款";
				break;
			case (int) GENERALLEDGER:
				strReturn = "总账业务";
				break;
			case (int) TRANSFEE:
				strReturn = "交易费用";
				break;
			case (int) SPECIALOPERATION:
				strReturn = "特殊交易";
				break;
			case (int) COMPATIBILITY:
				strReturn = "兼容业务";
				break;
			case (int) TRANSABATEMENT:
				strReturn = "转贴现卖出自动冲销";
				break;
			case (int) SHORTLOANRECEIVE:
				strReturn = "短期贷款收回";
				break;
			case (int) INTERESTGRANT:
				strReturn = "发放利息";
				break;
			case (int) SHUTDOWN:
				strReturn = "关机";
				break;
			case (int) CYCLOANRECEIVE:
				strReturn = "循环贷款收回";
				break;
			case (int) BANKUPSAVE:
				strReturn = "银行上收";
				break;
			case (int) BANKUPRECEIVE:
				strReturn = "银行调回";
				break;
			case (int) BANKINTEREST:
				strReturn = "银行发放负息资金";
				break;
			case (int) CYCCONSIGNLOANGRANT:
				strReturn = "循环委托贷款发放";
				break;
			case (int) CYCCONSIGNLOANRECEIVE:
				strReturn = "循环委托贷款收回";
				break;
			case (int) INTERESTFEEPAYMENT:
				strReturn = "利息费用支付";
				break;
			case (int) SECURITIESRECEIVE:
				strReturn = "财务公司收款";
				break;
			case (int) SECURITIESPAY:
				strReturn = "财务公司付款";
				break;
			case (int) SEC_WTLC_RECEIVE:
				strReturn = "委托理财收款";
				break;
			case (int) SEC_WTLC_PAY:
				strReturn = "委托理财付款";
				break;
			case (int) SEC_ZQCX_RECEIVE:
				strReturn = "债券承销收款";
				break;
			case (int) SEC_ZQCX_PAY:
				strReturn = "债券承销付款";
				break;
			case (int) INTEREST_FEE_PAY_CURRENT:
				strReturn = "利息费用支付活期结息";
				break;
			case (int) INTEREST_FEE_PAY_MARGIN:
				strReturn = "利息费用支付保证金结息";
				break;
			case (int) YT_LOAN_COMMISION_FEE:
				strReturn = "银团贷款结手续费";
				break;
			case (int) FIXED_DEPOSIT_PREDRAW_INTEREST:
				strReturn = "定期存款计提应付利息（含冲销）";
				break;
			case (int) MARGIN_DEPOSIT_PREDRAW_INTEREST:
				strReturn = "保证金存款计提应付利息（含冲销）";
				break;
			case (int) TRUST_LOAN_PREDRAW_INTEREST:
				strReturn = "自营贷款计提应收利息（含冲销）";
				break;
			case (int) YT_LOAN_PREDRAW_INTEREST:
				strReturn = "银团贷款计提应收利息（含冲销）";
				break;
			case (int) TRUST_LOAN_INTEREST:
				strReturn = "自营贷款结息";
				break;
			case (int) YT_LOAN_INTEREST:
				strReturn = "银团贷款结息";
				break;
			case (int) TRUST_LOAN_SURETY_FEE:
				strReturn = "自营贷款结担保费";
				break;
			case (int) CONSIGN_LOAN_INTEREST:
				strReturn = "委托贷款结息";
				break;
			case (int) CONSIGN_LOAN_COMMISION_FEE:
				strReturn = "委托贷款结手续费";
				break;
			case (int) GRANT_DEBIT_INTEREST:
				strReturn = "发放负息资金";
				break;
			case (int) RECEIVE_DEBIT_INTEREST:
				strReturn = "收回负息资金";
				break;
			case (int) BANKGROUPLOANGRANT:
				strReturn = "银团贷款发放";
				break;
			case (int) BANKGROUPLOANRECEIVE:
				strReturn = "银团贷款收回";
				break;
			// 海尔专用（04/10/20 add by weilu）
			case (int) CHECK_RECEIVE:
				strReturn = "银行支票收款";
				break;
			case (int) REMIT_RECEIVE:
				strReturn = "银行电汇收款";
				break;
			case (int) OTHER_RECEIVE:
				strReturn = "银行其它形式收款";
				break;
			case (int) CHECK_PAY:
				strReturn = "银行支票付款";
				break;
			case (int) REMIT_PAY:
				strReturn = "银行电汇付款";
				break;
			case (int) TAX_PAY:
				strReturn = "银行税单付款";
				break;
			case (int) OTHER_PAY:
				strReturn = "银行其它形式付款";
				break;
			case (int) DELEGATION_INCOME_OFFBALANCE:
				strReturn = "代保管有价值品类表外业务收入";
				break;
			case (int) DELEGATION_PAYOUT_OFFBALANCE:
				strReturn = "代保管有价值品类表外业务付出";
				break;
			case (int) CONSIGN_INCOME_OFFBALANCE:
				strReturn = "贷款未收利息类表外业务收入";
				break;
			case (int) CONSIGN_PAYOUT_OFFBALANCE:
				strReturn = "贷款未收利息类表外业务付出";
				break;
			case (int) DISCOUNT_INCOME_OFFBALANCE:
				strReturn = "商业汇票贴现类表外业务收入";
				break;
			case (int) DISCOUNT_PAYOUT_OFFBALANCE:
				strReturn = "商业汇票贴现类表外业务付出";
				break;
			case (int) ASSURE_INCOME_OFFBALANCE:
				strReturn = "开出保函凭信类表外业务收入";
				break;
			case (int) ASSURE_PAYOUT_OFFBALANCE:
				strReturn = "开出保函凭信类表外业务付出";
				break;
			case (int) REPORTLOSS:
				strReturn = "挂失";
				break;
			case (int) REPORTFIND:
				strReturn = "解挂";
				break;
			case (int) CHANGECERTIFICATE:
				strReturn = "换发证书";
				break;
			case (int) FREEZE:
				strReturn = "冻结";
				break;
			case (int) DEFREEZE:
				strReturn = "解冻";
				break;
			case (int) ACCOUNTOPEN:
				strReturn = "账户开户";
				break;
			case (int) ACCOUNTMODIFY:
				strReturn = "帐户修改";
				break;	
			case (int) FUND_REQUEST:
				strReturn = "资金申领";
				break;
			case (int) FUND_RETURN:
				strReturn = "资金上存";
				break;
			case (int) BILL_REGISTER:
				strReturn = "空白凭证注册";
				break;
			case (int) BILL_USE:
				strReturn = "空白凭证领用";
				break;
			case (int) INITIATIVE_TURNIN:
				strReturn = "主动上收";
				break;
			case (int) DRAW_PRINCIPAL:
				strReturn = "通知定期支取本金";
				break;
			case (int) DRAW_INTEREST:
				strReturn = "通知定期支取利息";
				break;
			case (int) UPGATHERING:
				strReturn = "资金上收";
				break;
			case (int) COMMISSION:
				strReturn = "交易手续费收取";
				break;
			case (int) INTERESTFEEPAYMENT_SURETY:
				strReturn = "利息支付－自营贷款担保费";
				break;
			case (int) INTERESTFEEPAYMENT_COMMISION:
				strReturn = "利息支付－委托贷款手续费";
				break;
			case (int) OPENMARGIN:
				strReturn = "担保收款";
				break;
			case (int) WITHDRAWMARGIN:
				strReturn = "保后处理";
				break;
			case (int) RECEIVEFINANCE:
				strReturn = "融资租赁收款";
				break;
			case (int) RETURNFINANCE:
				strReturn = "融资租赁还款";
				break;
				
			case (int) CAPITALTRANSFER:
				strReturn = "资金划拨";
				break;
			case (int) DISCOUNTACCRUAL:
				strReturn = "贴现贷款计提应收利息（含冲销）";
				break;
			case (int) DISCOUNT_LOAN_INTEREST:
				strReturn = "贴现贷款结息";
				break;
			case (int) CONSIGN_LOAN_PREDRAW_INTEREST:
				strReturn = "委托贷款计提应收利息（含冲销）";
				break;
			case (int) CURRENT_DEPOSIT_PREDRAW_INTEREST:
				strReturn = "活期存款计提应付利息（含冲销）";
				break;
			case (int) CURRENT_NOTIFY_PREDRAW_INTEREST:
				strReturn = "通知存款计提应付利息（含冲销）";
				break;
			case (int) ABJUSTINTEREST:
				strReturn = "累计费用调整";
				break;
			case (int) FIXEDDEPOSITRECEIPT:
				strReturn = "定期存单";
				break;
			case (int) NOTIFYACCOUNTRECEIPT:
				strReturn = "通知存单";
				break;
			case (int) MARGINRECEIPT:
				strReturn = "保证金存单";
				break;
			case (int) CAPITALUP:
				strReturn = "企业资金上划";
				break;
			case (int) CAPITALDOWN:
				strReturn = "企业资金下拨";
				break;				

			default:// 当设置了特殊业务的交易类型后，需要将特殊业务的所有交易类型合并到现有业务的交易类型中来
			{
				try {
					Sett_SpecialOperationDAO specialDao = new Sett_SpecialOperationDAO();
					strReturn = specialDao.findSpecialOperationByID(lCode).m_strName;
				} catch (Exception e) {
					System.out.println("取得特殊交易类型时异常");
					e.printStackTrace();
				}
			}
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			// 返回全部
			long[] lTemp = { BANKRECEIVE, CASHRECEIVE, BANKPAY,
					BANKPAY_DOWNTRANSFER, SUBCLIENT_BANKRECEIVE,
					SUBCLIENT_BANKPAY, CHECKPAY, CASHPAY, DRAFTPAY,
					INTERNALVIREMENT, CONSIGNRECEIVE, CONSIGNSAVE,
					CAUTIONMONEYSAVE, NATIONALDEBT_BUY, NATIONALDEBT_SELL,
					ONETOMULTI, OTHERPAY, OPENFIXEDDEPOSIT,
					FIXEDTOCURRENTTRANSFER, FIXEDCONTINUETRANSFER,
					OPENNOTIFYACCOUNT, NOTIFYDEPOSITDRAW, TRUSTLOANGRANT,
					TRUSTLOANRECEIVE, CONSIGNLOANGRANT, CONSIGNLOANRECEIVE,
					BANKGROUPLOANGRANT, BANKGROUPLOANRECEIVE, DISCOUNTGRANT,
					DISCOUNTRECEIVE, MULTILOANRECEIVE,TRANSDISCOUNTGRANT, CONSIGNUPRECEIVE,
					CONSIGNUPSAVE, CONSIGNUPSAVERECEIVE, SHORTDEBTRECEIVE,
					CONSIGNCAPITALOPERATION, SHORTLOANGRANT, CYCLOANGRANT,
					GENERALLEDGER, TRANSFEE, SPECIALOPERATION, COMPATIBILITY,
					TRANSABATEMENT, SHORTLOANRECEIVE, INTERESTGRANT, SHUTDOWN,
					CYCLOANRECEIVE, BANKUPSAVE, BANKUPRECEIVE, BANKINTEREST,
					CYCCONSIGNLOANGRANT, CYCCONSIGNLOANRECEIVE,
					INTEREST_FEE_PAY_CURRENT, INTEREST_FEE_PAY_MARGIN,
					FIXED_DEPOSIT_PREDRAW_INTEREST,
					MARGIN_DEPOSIT_PREDRAW_INTEREST,
					TRUST_LOAN_PREDRAW_INTEREST,YT_LOAN_PREDRAW_INTEREST, TRUST_LOAN_INTEREST,YT_LOAN_INTEREST,
					TRUST_LOAN_SURETY_FEE, CONSIGN_LOAN_INTEREST,
					CONSIGN_LOAN_COMMISION_FEE, GRANT_DEBIT_INTEREST,
					RECEIVE_DEBIT_INTEREST, FUND_REQUEST, FUND_RETURN,
					BILL_REGISTER, BILL_USE, UPGATHERING, COMMISSION,
					OPENMARGIN, WITHDRAWMARGIN, RECEIVEFINANCE, RETURNFINANCE,
					INTERESTFEEPAYMENT_COMMISION, INTERESTFEEPAYMENT_SURETY,CAPITALUP,CAPITALDOWN };

			// 中交修改，去掉不需要的查询类型
			/**
			 * long[] lTemp = { BANKRECEIVE ,BANKPAY
			 * ,INTERNALVIREMENT,OTHERPAY,BANKPAY_DOWNTRANSFER
			 * ,OPENFIXEDDEPOSIT, FIXEDTOCURRENTTRANSFER,FIXEDCONTINUETRANSFER
			 * ,OPENNOTIFYACCOUNT ,NOTIFYDEPOSITDRAW ,
			 * TRUSTLOANGRANT,TRUSTLOANRECEIVE,GENERALLEDGER,SPECIALOPERATION,SHUTDOWN,INTERESTFEEPAYMENT,
			 * INTEREST_FEE_PAY_CURRENT,FIXED_DEPOSIT_PREDRAW_INTEREST,TRUST_LOAN_PREDRAW_INTEREST,
			 * TRUST_LOAN_INTEREST,TRUST_LOAN_SURETY_FEE,REPORTLOSS,REPORTFIND,CHANGECERTIFICATE,FREEZE,
			 * DEFREEZE,COMMISSION,DRAW_PRINCIPAL,DRAW_INTEREST,INTERESTFEEPAYMENT_SURETY };
			 */

			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			long[] constantArray = Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$CodingRuleTransactionType",
							officeID, currencyID);

			Sett_SpecialOperationDAO specialDao = new Sett_SpecialOperationDAO();
			
			//modified by mzh_fu 2007/06/05
//			long[] specialArray = specialDao
//					.getAllSpecialOperationForConstant();
			long[] specialArray = specialDao.getSpecialOperationIdsByOfficeID(officeID);
			
			if (specialArray != null && specialArray.length > 0) {
				long[] arrReturn = new long[constantArray.length
						+ specialArray.length];
				System.arraycopy(constantArray, 0, arrReturn, 0,
						constantArray.length);
				System.arraycopy(specialArray, 0, arrReturn,
						constantArray.length, specialArray.length);
				
				return arrReturn;
			} else// 如果数据库中没有设置特殊业务类型，则直接返回constant中定义的类型
			{
				return constantArray;
			}
		}

		/**
		 * 获得产生银行转账指令地业务类型编码
		 * 
		 * @return long[]
		 */
		public static final long[] getAllCodeForGenerateBankInstruction() {
			long[] lTemp = { BANKPAY, FIXEDTOCURRENTTRANSFER,
					FIXEDCONTINUETRANSFER, NOTIFYDEPOSITDRAW, TRUSTLOANGRANT,
					CONSIGNLOANGRANT, DISCOUNTGRANT, SPECIALOPERATION,
					FUND_REQUEST, INITIATIVE_TURNIN };
			return lTemp;
		}

		/**
		 * 获得产生证券投资结算业务类型编码
		 * 
		 * @return long[]
		 */
		public static final long[] getAllCodeForSecurities() {
			long[] lTemp = { SECURITIESRECEIVE, SECURITIESPAY,
					SEC_WTLC_RECEIVE, SEC_WTLC_PAY, SEC_ZQCX_RECEIVE,
					SEC_ZQCX_PAY };
			return lTemp;
		}

		/**
		 * 获得产生国机母子公司结算业务类型编码
		 * 
		 * @return long[]
		 */
		public static final long[] getAllCodeForCapitalSupervise() {
			long[] lTemp = { GRANT_DEBIT_INTEREST, RECEIVE_DEBIT_INTEREST };
			return lTemp;
		}

		/**
		 * 获得联通需要的资金申领和资金上存业务类型编码
		 * 
		 * @author fanyang
		 * 
		 * TODO To change the template for this generated type comment go to
		 * Window - Preferences - Java - Code Style - Code Templates
		 */
		public static final long[] getAllCodeForICBC() {
			long[] lTemp = { FUND_REQUEST, FUND_RETURN };
			return lTemp;
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型
		 *            （0，显示全部；1,显示产生银行转账指令地业务类型）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank，是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				case 1:
					// 银企接口专用
					lArrayID = getAllCodeForGenerateBankInstruction();
					break;
				case 2:
					// 证券结算接口使用
					lArrayID = getAllCodeForSecurities();
					break;
				case 3:
					// 国机母子公司业务使用
					lArrayID = getAllCodeForCapitalSupervise();
				case 4:
					// 获得联通需要的资金申领和资金上存业务类型编码
					lArrayID = getAllCodeForICBC();
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				case 1:
					// 银企接口专用
					lArrayID = getAllCodeForGenerateBankInstruction();
					break;
				case 2:
					// 证券结算接口使用
					lArrayID = getAllCodeForSecurities();
					break;
				case 3:
					// 国机母子公司业务使用
					lArrayID = getAllCodeForCapitalSupervise();
				case 4:
					// 获得联通需要的资金申领和资金上存业务类型编码
					lArrayID = getAllCodeForICBC();
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}
	}


	public static class ConsignReceiveType {
		// 委托收款类型
		public static final long GLJY = 1; // 关联交易委托收款

		public static final long GFGS = 2; // 股份公司委托收款
	}

	/**
	 * 付款方式
	 * 
	 * @author wlming
	 * 
	 * To change the template for this generated type comment go to
	 * Window>Preferences>Java>Code Generation>Code and Comments
	 */
	public static class PayType {
		public static final long TELE = 1; // 电汇

		public static final long BILL = 2; // 票汇

		public static final long LETTER = 3; // 信汇

		public static final long CHECK = 4; // 支票

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) TELE:
				strReturn = "电汇";
				break;
			case (int) BILL:
				strReturn = "票汇";
				break;
			case (int) LETTER:
				strReturn = "信汇";
				break;
			case (int) CHECK:
				strReturn = "支票";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { TELE, BILL, LETTER, CHECK };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.settlement.util.SETTConstant$PayType",
					officeID, currencyID);
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型
		 *            （0，显示全部；1,显示三项（电汇、票汇、信汇））
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				case 1:
					lArrayID = new long[] { TELE, BILL, LETTER };
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				case 1:
					lArrayID = new long[] { TELE, BILL, LETTER };
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

	}

	public static class AdjustInterestDirection {
		public static final long ADD = 1; // 增

		public static final long REDUCE = 2; // 减

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) ADD:
				strReturn = "增";
				break;
			case (int) REDUCE:
				strReturn = "减";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { ADD, REDUCE };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$AdjustInterestDirection",
							officeID, currencyID);
		}
	}

	public static class AccountGroupType {
		// 账户组
		public static final long CURRENT = 1; // 活期存款

		public static final long FIXED = 2; // 定期存款

		public static final long NOTIFY = 3; // 通知存款

		public static final long TRUST = 4; // 信托贷款

		public static final long CONSIGN = 5; // 委托贷款

		public static final long DISCOUNT = 6; // 贴现贷款

		public static final long OTHERDEPOSIT = 7; // 其他存款

		public static final long OTHERLOAN = 8; // 其他贷款

		public static final long OFFBALANCE = 9; // 表外账户

		public static final long BANK = 10; // 银行二级账户

		public static final long MARGIN = 11; // 保证金存款
		
		public static final long YT = 12; //银团贷款

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) CURRENT:
				strReturn = "活期存款";
				break;
			case (int) FIXED:
				strReturn = "定期存款";
				break;
			case (int) NOTIFY:
				strReturn = "通知存款";
				break;
			case (int) TRUST:
				strReturn = "自营贷款";
				break;
			case (int) CONSIGN:
				strReturn = "委托贷款";
				break;
			case (int) DISCOUNT:
				strReturn = "贴现贷款";
				break;
			case (int) OTHERDEPOSIT:
				strReturn = "其他存款";
				break;
			case (int) OTHERLOAN:
				strReturn = "其他贷款";
				break;
			case (int) OFFBALANCE:
				strReturn = "表外账户";
				break;
			case (int) BANK:
				strReturn = "二级账户";
				break;
			case (int) MARGIN:
				strReturn = "保证金存款";
				break;
			case (int) YT:
				strReturn = "银团贷款";
				break;
			}
			return strReturn;
		}

		/*
		 * 中交添加方法，只显示，活期，定期，通知， 内部资金占用
		 */
		public static final long[] getCodeForZJ() {
			long lTemp[] = { CURRENT, FIXED, NOTIFY, TRUST };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			long[] l = Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$AccountGroupType",
							officeID, currencyID);
			int len = (l == null ? 0 : l.length);
			if (len > 0) {
				Arrays.sort(l);
			}
			return l;
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型
		 *            （0，显示全部）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */

		public static final void showList(JspWriter out, String strControlName,
				long lCurrencyID, int nType, long lSelectValue,
				boolean isNeedAll, boolean isNeedBlank, String strProperty,
				long lOfficeID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				case 1:// 为中交账户类型编码设置
					lArrayID = getCodeForZJ();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

	}

	/**
	 * 账户交易类型
	 * 
	 * @author wlming
	 * 
	 * To change the template for this generated type comment go to
	 * Window>Preferences>Java>Code Generation>Code and Comments
	 */
	public static class AccountTransactionType {
		public static final long CURRENT_DEPOSIT = 1; // 活期存款

		public static final long FIXED_DEPOSIT = 2; // 定期存款

		public static final long NOTIFY_DEPOSIT = 3; // 通知存款

		public static final long TRUST_LOAN = 4; // 自营贷款

		public static final long CONSIGN_LOAN = 5; // 委托贷款

		public static final long DISCOUNT_LOAN = 6; // 贴现

		public static final long SHORT_LOAN = 7; // 短期贷款

		public static final long CYCLE_LOAN = 8; // 循环贷款

		public static final long CONSIGN_TRANSACTION = 9; // 委托业务

		public static final long OUT_PAYMENT = 10; // 对外付款

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) CURRENT_DEPOSIT:
				strReturn = "活期存款";
				break;
			case (int) FIXED_DEPOSIT:
				strReturn = "定期存款";
				break;
			case (int) NOTIFY_DEPOSIT:
				strReturn = "通知存款";
				break;
			case (int) TRUST_LOAN:
				strReturn = "自营贷款";
				break;
			case (int) CONSIGN_LOAN:
				strReturn = "委托贷款";
				break;
			case (int) DISCOUNT_LOAN:
				strReturn = "贴现";
				break;
			case (int) SHORT_LOAN:
				strReturn = "短期贷款";
				break;
			case (int) CYCLE_LOAN:
				strReturn = "循环贷款";
				break;
			case (int) CONSIGN_TRANSACTION:
				strReturn = "委托业务";
				break;
			case (int) OUT_PAYMENT:
				strReturn = "对外付款";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { CURRENT_DEPOSIT, FIXED_DEPOSIT, NOTIFY_DEPOSIT,
					TRUST_LOAN, CONSIGN_LOAN, DISCOUNT_LOAN, SHORT_LOAN,
					CYCLE_LOAN, CONSIGN_TRANSACTION, OUT_PAYMENT };
			return lTemp;
		}

		// 中交加入，查询－交易记录查询－交易类型 保留 活期存款，定期存款，通知存款，内部资金占用
		public static final long[] getCodeForZj() {
			long[] lTemp = { CURRENT_DEPOSIT, FIXED_DEPOSIT, NOTIFY_DEPOSIT,
					TRUST_LOAN };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$AccountTransactionType",
							officeID, currencyID);
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型
		 *            （0，显示全部;）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank,
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				case 1:// 中交加入，查询－交易记录查询－交易类型 保留 活期存款，定期存款，通知存款，内部资金占用
					lArrayID = getCodeForZj();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

	}

	/**
	 * 转账类型：内、外
	 * 
	 * @author zpli
	 * 
	 */
	public static class AccountTransferType {
		public static final long Reference = 1; // 通过关联号转账

		public static final long External = 2; // 通过外部账号转账
	}

	/**
	 * 账户类型
	 * 
	 * @author wlming
	 * 
	 * To change the template for this generated type comment go to
	 * Window>Preferences>Java>Code Generation>Code and Comments
	 */
	public static class AccountType {
		/*
		 * //账户类型 public static final long CURRENTDEPOSIT = 1; //活期存款
		 * 
		 * public static final long FIXEDDEPOSIT = 2; //定期存款
		 * 
		 * public static final long NOTIFY = 3; //通知存款
		 * 
		 * public static final long CONSIGNSAVE_LOAN = 4; //委托存款
		 * 
		 * //public static final long CONSIGNSAVE_NOTLOAN = 5; //委托存款未放款 public
		 * static final long CONSIGNMONEYSAVE = 6; //保证金存款
		 * 
		 * //public static final long NATIONALDEBIT = 7; //国债账户 public static
		 * final long TRUSTLOAN = 8; //自营贷款
		 * 
		 * public static final long CONSIGNLOAN = 9; //委托贷款
		 * 
		 * public static final long DISCOUNT = 10; //贴现
		 * 
		 * public static final long CYCLOAN = 11; //循环贷款
		 * 
		 * public static final long SHORTLOAN = 12; //短期贷款
		 * 
		 * public static final long REDISCOUNTSALE = 31; //转贴现卖出
		 * 
		 * public static final long REDISCOUNTBUY = 32; //转贴现买入
		 * 
		 * public static final long DEBIT_INTEREST = 96; //负息资金(国机)属于活期存款账户组
		 * 
		 * public static final long TEMP_CURRENTDEPOSIT = 97; //过渡活期账户
		 * 
		 * public static final long PREPARE = 98; //准备金存款
		 * 
		 * public static final long BACK = 99; //备付金
		 * 
		 * //离岸账户 public static final long FOREIGN_CURRENTDEPOSIT = 13; //离岸活期存款
		 * 
		 * public static final long FOREIGN_FIXEDDEPOSIT = 14; //离岸定期存款
		 * 
		 * public static final long FOREIGN_NOTIFY = 15; //离岸通知存款
		 * 
		 * public static final long FOREIGN_CONSIGNSAVE_LOAN = 16; //离岸委托存款已放款
		 * 
		 * public static final long FOREIGN_CONSIGNSAVE_NOTLOAN = 17;
		 * //离岸委托存款未放款
		 * 
		 * public static final long FOREIGN_TRUSTLOAN = 18; //离岸信托贷款
		 * 
		 * public static final long FOREIGN_CONSIGNLOAN = 19; //离岸委托贷款
		 * 
		 * public static final long FOREIGN_CONSIGNMONEYSAVE = 20; //离岸保证金存款
		 */
		

		
		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			AccountTypeInfo info = (AccountTypeInfo) Env.getAccountTypeHash(lCode)
					.get(String.valueOf(lCode));
			if (info != null)
				strReturn = info.getAccountType();
			return strReturn;
		}
		
		//add by zcwang 2008-04-10
		public static final String getNameByOfficeCurrencyAndCode(long lOfficeID,long lCurrencyID,long lCode){
			String strReturn = ""; // 初始化返回值
			AccountTypeInfo info = (AccountTypeInfo) Env.getAccountTypeHashByOfficeCurrencyAndCode(lOfficeID, lCurrencyID)
					.get(String.valueOf(lCode));
			if (info != null)
				strReturn = info.getAccountType();
			return strReturn;
		}
		//
		public static final long[] getAllCode() {
			long[] arrayReturn = new long[Env.getAccountTypeHash().size()];
			int i = 0;
			Enumeration enumeration = Env.getAccountTypeHash().keys();
			while (enumeration.hasMoreElements()) {
				arrayReturn[i] = Long.parseLong((String) enumeration
						.nextElement());
				i++;
			}
			return arrayReturn;
		}

		public static final long[] getAllCode(long lCurrencyID) {
			long[] arrayReturn = new long[Env.getAccountTypeHash().size()];
			int i = 0;
			Enumeration enumeration = Env.getAccountTypeHash().keys();
			while (enumeration.hasMoreElements()) {
				arrayReturn[i] = Long.parseLong((String) enumeration
						.nextElement());
				i++;
			}
			i = arrayReturn == null ? 0 : arrayReturn.length;
			if (i > 0) {
				Arrays.sort(arrayReturn);
			}
			return arrayReturn;
		}
		
		/**
		 * 2007.6.14 by qhzhou
		 * @param lOfficeID
		 * @param lCurrencyID
		 * @return
		 */
		public static final long[] getAllCodeByOfficeAndCurrency(long lOfficeID,long lCurrencyID) {
			long[] arrayReturn = new long[Env.getAccountTypeHashByOfficeAndCurrency(lOfficeID, lCurrencyID).size()];
			int i = 0;
			Enumeration enumeration = Env.getAccountTypeHashByOfficeAndCurrency(lOfficeID, lCurrencyID).keys();
			while (enumeration.hasMoreElements()) {
				arrayReturn[i] = Long.parseLong((String) enumeration
						.nextElement());
				i++;
			}
			i = arrayReturn == null ? 0 : arrayReturn.length;
			if (i > 0) {
				Arrays.sort(arrayReturn);
			}
			return arrayReturn;
		}
		
		/**
		 * 存款账户类型编码，包括活期和定期，保证金存款
		 * 
		 * @return
		 */
		public static final long[] getDepositAccountTypeCode() {
			long[] arrayReturn = null;
			long[] tmpArray = new long[Env.getAccountTypeHash().size()];
			int i = 0;

			Enumeration enumeration = Env.getAccountTypeHash().elements();
			while (enumeration.hasMoreElements()) {
				AccountTypeInfo info = (AccountTypeInfo) enumeration
						.nextElement();
				if (info.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT
						|| info.getAccountGroupID() == SETTConstant.AccountGroupType.FIXED
						|| info.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY
						|| info.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERDEPOSIT
						|| info.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN) {
					tmpArray[i] = info.getId();
					i++;
				}
			}
			arrayReturn = new long[i];
			System.arraycopy(tmpArray, 0, arrayReturn, 0, i);
			return arrayReturn;
		}

		public static final long[] getDepositAccountTypeCode(long lCurrencyID,long lOfficeID) {
			long[] arrayReturn = null;
			long[] tmpArray = new long[Env.getAccountTypeHashByOfficeAndCurrency(lOfficeID, lCurrencyID).size()];
			int i = 0;

			Enumeration enumeration = Env.getAccountTypeHashByOfficeAndCurrency(lOfficeID, lCurrencyID).elements();
			while (enumeration.hasMoreElements()) {
				AccountTypeInfo info = (AccountTypeInfo) enumeration
						.nextElement();
				if (info.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT
						|| info.getAccountGroupID() == SETTConstant.AccountGroupType.FIXED
						|| info.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY
						|| info.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN
						|| info.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERDEPOSIT) {
					tmpArray[i] = info.getId();
					i++;
				}
			}
			arrayReturn = new long[i];
			System.arraycopy(tmpArray, 0, arrayReturn, 0, i);
			return arrayReturn;
		}

		//监审稽核专用
		public static final long[] getDepositAccountTypeCodeForAudit(long lCurrencyID,long lOfficeID) {
			long[] arrayReturn = null;
			long[] tmpArray = new long[Env.getAccountTypeHashByOfficeAndCurrency(lOfficeID, lCurrencyID).size()];
			int i = 0;

			Enumeration enumeration = Env.getAccountTypeHashByOfficeAndCurrency(lOfficeID, lCurrencyID).elements();
			while (enumeration.hasMoreElements()) {
				AccountTypeInfo info = (AccountTypeInfo) enumeration
						.nextElement();
				if (info.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT
						|| info.getAccountGroupID() == SETTConstant.AccountGroupType.FIXED
						|| info.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY
						) {
					tmpArray[i] = info.getId();
					i++;
				}
			}
			arrayReturn = new long[i];
			System.arraycopy(tmpArray, 0, arrayReturn, 0, i);
			return arrayReturn;
		}
		/**
		 * 贷款账户类型编码
		 * 
		 * @return
		 */
		public static final long[] getLoanAccountTypeCode() {
			long[] arrayReturn = null;
			long[] tmpArray = new long[Env.getAccountTypeHash().size()];
			int i = 0;

			Enumeration enumeration = Env.getAccountTypeHash().elements();
			while (enumeration.hasMoreElements()) {
				AccountTypeInfo info = (AccountTypeInfo) enumeration
						.nextElement();
				if (info.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST
						|| info.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN
						|| info.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT
						|| info.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN
						|| info.getAccountGroupID() == SETTConstant.AccountGroupType.YT) {
					tmpArray[i] = info.getId();
					i++;
				}
			}
			arrayReturn = new long[i];
			System.arraycopy(tmpArray, 0, arrayReturn, 0, i);
			return arrayReturn;
		}

		public static final long[] getLoanAccountTypeCode(long lCurrencyID,long lOfficeID) {
			long[] arrayReturn = null;
			long[] tmpArray = new long[Env.getAccountTypeHashByOfficeAndCurrency(lOfficeID, lCurrencyID).size()];
			int i = 0;

			Enumeration enumeration = Env.getAccountTypeHashByOfficeAndCurrency(lOfficeID, lCurrencyID).elements();
			while (enumeration.hasMoreElements()) {
				AccountTypeInfo info = (AccountTypeInfo) enumeration
						.nextElement();
				if (info.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST
						|| info.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN
						|| info.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT
						|| info.getAccountGroupID() == SETTConstant.AccountGroupType.YT
						|| info.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN) {
					tmpArray[i] = info.getId();
					i++;
				}
			}
			arrayReturn = new long[i];
			System.arraycopy(tmpArray, 0, arrayReturn, 0, i);
			return arrayReturn;
		}

		//监审稽核专用
		public static final long[] getLoanAccountTypeCodeForAudit(long lCurrencyID,long lOfficeID) {
			long[] arrayReturn = null;
			long[] tmpArray = new long[Env.getAccountTypeHashByOfficeAndCurrency(lOfficeID, lCurrencyID).size()];
			int i = 0;

			Enumeration enumeration = Env.getAccountTypeHashByOfficeAndCurrency(lOfficeID, lCurrencyID).elements();
			while (enumeration.hasMoreElements()) {
				AccountTypeInfo info = (AccountTypeInfo) enumeration
						.nextElement();
				if (info.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST
						|| info.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN
						|| info.getAccountGroupID() == SETTConstant.AccountGroupType.YT
						) {
					tmpArray[i] = info.getId();
					i++;
				}
			}
			arrayReturn = new long[i];
			System.arraycopy(tmpArray, 0, arrayReturn, 0, i);
			return arrayReturn;
		}

		/**
		 * 活期组账户类型编码
		 * 
		 * @return
		 */
		public static final long[] getCurrentAccountTypeCode() {
			long[] arrayReturn = null;
			long[] tmpArray = new long[Env.getAccountTypeHash().size()];
			int i = 0;

			Enumeration enumeration = Env.getAccountTypeHash().elements();
			while (enumeration.hasMoreElements()) {
				AccountTypeInfo info = (AccountTypeInfo) enumeration
						.nextElement();
				if (info.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT) {
					tmpArray[i] = info.getId();
					i++;
				}
			}
			arrayReturn = new long[i];
			System.arraycopy(tmpArray, 0, arrayReturn, 0, i);
			return arrayReturn;
		}

		public static final long[] getCurrentAccountTypeCode(long lCurrencyID,long lOfficeID) {
			long[] arrayReturn = null;
			long[] tmpArray = new long[Env.getAccountTypeHashByOfficeAndCurrency(lOfficeID, lCurrencyID).size()];
			int i = 0;

			Enumeration enumeration = Env.getAccountTypeHashByOfficeAndCurrency(lOfficeID, lCurrencyID).elements();
			while (enumeration.hasMoreElements()) {
				AccountTypeInfo info = (AccountTypeInfo) enumeration
						.nextElement();
				if (info.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT) {
					tmpArray[i] = info.getId();
					i++;
				}
			}
			arrayReturn = new long[i];
			System.arraycopy(tmpArray, 0, arrayReturn, 0, i);
			return arrayReturn;
		}

		/**
		 * 定期组账户类型编码
		 * 
		 * @return
		 */
		public static final long[] getFixAccountTypeCode() {
			long[] arrayReturn = null;
			long[] tmpArray = new long[Env.getAccountTypeHash().size()];
			int i = 0;

			Enumeration enumeration = Env.getAccountTypeHash().elements();
			while (enumeration.hasMoreElements()) {
				AccountTypeInfo info = (AccountTypeInfo) enumeration
						.nextElement();
				if (info.getAccountGroupID() == SETTConstant.AccountGroupType.FIXED) {
					tmpArray[i] = info.getId();
					i++;
				}
			}
			arrayReturn = new long[i];
			System.arraycopy(tmpArray, 0, arrayReturn, 0, i);
			return arrayReturn;
		}
		
		/**
		 * 定期组账户类型编码 add by zcwang 2007-8-13 处理缓存问题
		 * 
		 * @return
		 */
		public static final long[] getFixAccountTypeCode(long lAccountTypeID) {
			long[] arrayReturn = null;
			long[] tmpArray = new long[Env.getAccountTypeHash(lAccountTypeID).size()];
			int i = 0;

			Enumeration enumeration = Env.getAccountTypeHash(lAccountTypeID).elements();
			while (enumeration.hasMoreElements()) {
				AccountTypeInfo info = (AccountTypeInfo) enumeration
						.nextElement();
				if (info.getAccountGroupID() == SETTConstant.AccountGroupType.FIXED) {
					tmpArray[i] = info.getId();
					i++;
				}
			}
			arrayReturn = new long[i];
			System.arraycopy(tmpArray, 0, arrayReturn, 0, i);
			return arrayReturn;
		}
		public static final long[] getFixAccountTypeCode(long lCurrencyID,long lOfficeID) {
			long[] arrayReturn = null;
			long[] tmpArray = new long[Env.getAccountTypeHashByOfficeAndCurrency(lOfficeID, lCurrencyID).size()];
			int i = 0;

			Enumeration enumeration = Env.getAccountTypeHashByOfficeAndCurrency(lOfficeID, lCurrencyID).elements();
			while (enumeration.hasMoreElements()) {
				AccountTypeInfo info = (AccountTypeInfo) enumeration
						.nextElement();
				if (info.getAccountGroupID() == SETTConstant.AccountGroupType.FIXED) {
					tmpArray[i] = info.getId();
					i++;
				}
			}
			arrayReturn = new long[i];
			System.arraycopy(tmpArray, 0, arrayReturn, 0, i);
			return arrayReturn;
		}
		/**
		 * 保证金组账户类型编码 add by zcwang 2007-8-13 处理缓存问题
		 * 
		 * @return
		 */
		public static final long[] getMarginAccountTypeCode(long lAccountTypeID) {
			long[] arrayReturn = null;
			long[] tmpArray = new long[Env.getAccountTypeHash(lAccountTypeID).size()];
			int i = 0;

			Enumeration enumeration = Env.getAccountTypeHash(lAccountTypeID).elements();
			while (enumeration.hasMoreElements()) {
				AccountTypeInfo info = (AccountTypeInfo) enumeration
						.nextElement();
				if (info.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN) {
					tmpArray[i] = info.getId();
					i++;
				}
			}
			arrayReturn = new long[i];
			System.arraycopy(tmpArray, 0, arrayReturn, 0, i);
			return arrayReturn;
		}

		
		/**
		 * 保证金组账户类型编码　
		 * 
		 * @return
		 */
		public static final long[] getMarginAccountTypeCode() {
			long[] arrayReturn = null;
			long[] tmpArray = new long[Env.getAccountTypeHash().size()];
			int i = 0;

			Enumeration enumeration = Env.getAccountTypeHash().elements();
			while (enumeration.hasMoreElements()) {
				AccountTypeInfo info = (AccountTypeInfo) enumeration
						.nextElement();
				if (info.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN) {
					tmpArray[i] = info.getId();
					i++;
				}
			}
			arrayReturn = new long[i];
			System.arraycopy(tmpArray, 0, arrayReturn, 0, i);
			return arrayReturn;
		}
		public static final long[] getMarginAccountTypeCode(long lCurrencyID,long lOfficeID) {
			long[] arrayReturn = null;
			long[] tmpArray = new long[Env.getAccountTypeHashByOfficeAndCurrency(lOfficeID, lCurrencyID).size()];
			int i = 0;

			Enumeration enumeration = Env.getAccountTypeHashByOfficeAndCurrency(lOfficeID, lCurrencyID).elements();
			while (enumeration.hasMoreElements()) {
				AccountTypeInfo info = (AccountTypeInfo) enumeration
						.nextElement();
				if (info.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN) {
					tmpArray[i] = info.getId();
					i++;
				}
			}
			arrayReturn = new long[i];
			System.arraycopy(tmpArray, 0, arrayReturn, 0, i);
			return arrayReturn;
		}

		/**
		 * 通知存款账户类型编码
		 * 
		 * @return
		 */
		public static final long[] getNotifyAccountTypeCode() {
			long[] arrayReturn = null;
			long[] tmpArray = new long[Env.getAccountTypeHash().size()];
			int i = 0;

			Enumeration enumeration = Env.getAccountTypeHash().elements();
			while (enumeration.hasMoreElements()) {
				AccountTypeInfo info = (AccountTypeInfo) enumeration
						.nextElement();
				if (info.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY) {
					tmpArray[i] = info.getId();
					i++;
				}
			}
			arrayReturn = new long[i];
			System.arraycopy(tmpArray, 0, arrayReturn, 0, i);
			return arrayReturn;
		}

		/**
		 * 通知存款账户类型编码 add by zcwang 2007-8-13处理缓存问题
		 * 
		 * @return
		 */
		public static final long[] getNotifyAccountTypeCode(long lAccountType) {
			long[] arrayReturn = null;
			long[] tmpArray = new long[Env.getAccountTypeHash(lAccountType).size()];
			int i = 0;

			Enumeration enumeration = Env.getAccountTypeHash(lAccountType).elements();
			while (enumeration.hasMoreElements()) {
				AccountTypeInfo info = (AccountTypeInfo) enumeration
						.nextElement();
				if (info.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY) {
					tmpArray[i] = info.getId();
					i++;
				}
			}
			arrayReturn = new long[i];
			System.arraycopy(tmpArray, 0, arrayReturn, 0, i);
			return arrayReturn;
		}

		public static final long[] getNotifyAccountTypeCode(long lCurrencyID,long lOfficeID) {
			long[] arrayReturn = null;
			long[] tmpArray = new long[Env.getAccountTypeHashByOfficeAndCurrency(lOfficeID, lCurrencyID).size()];
			int i = 0;

			Enumeration enumeration = Env.getAccountTypeHashByOfficeAndCurrency(lOfficeID, lCurrencyID).elements();
			while (enumeration.hasMoreElements()) {
				AccountTypeInfo info = (AccountTypeInfo) enumeration
						.nextElement();
				if (info.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY) {
					tmpArray[i] = info.getId();
					i++;
				}
			}
			arrayReturn = new long[i];
			System.arraycopy(tmpArray, 0, arrayReturn, 0, i);
			return arrayReturn;
		}

		
		/**
		 * 信托存款账户类型编码
		 * 
		 * @return
		 */
		public static final long[] getTrustAccountTypeCode() {
			long[] arrayReturn = null;
			long[] tmpArray = new long[Env.getAccountTypeHash().size()];
			int i = 0;

			Enumeration enumeration = Env.getAccountTypeHash().elements();
			while (enumeration.hasMoreElements()) {
				AccountTypeInfo info = (AccountTypeInfo) enumeration
						.nextElement();
				if (info.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST) {
					tmpArray[i] = info.getId();
					i++;
				}
			}
			arrayReturn = new long[i];
			System.arraycopy(tmpArray, 0, arrayReturn, 0, i);
			return arrayReturn;
		}
		
		/**
		 * 信托存款账户类型编码
		 * add by zcwang 2007-6-18
		 * @return
		 */
		public static final long[] getYTAccountTypeCode() {
			long[] arrayReturn = null;
			long[] tmpArray = new long[Env.getAccountTypeHash().size()];
			int i = 0;

			Enumeration enumeration = Env.getAccountTypeHash().elements();
			while (enumeration.hasMoreElements()) {
				AccountTypeInfo info = (AccountTypeInfo) enumeration
						.nextElement();
				if (info.getAccountGroupID() == SETTConstant.AccountGroupType.YT) {
					tmpArray[i] = info.getId();
					i++;
				}
			}
			arrayReturn = new long[i];
			System.arraycopy(tmpArray, 0, arrayReturn, 0, i);
			return arrayReturn;
		}
		//add by zcwang 2007-10-18 
		public static final long[] getYTAccountTypeCode(long lCurrencyID,long lOfficeID) {
			long[] arrayReturn = null;
			long[] tmpArray = new long[Env.getAccountTypeHashByOfficeAndCurrency(lOfficeID, lCurrencyID).size()];
			int i = 0;

			Enumeration enumeration = Env.getAccountTypeHashByOfficeAndCurrency(lOfficeID, lCurrencyID).elements();
			while (enumeration.hasMoreElements()) {
				AccountTypeInfo info = (AccountTypeInfo) enumeration
						.nextElement();
				if (info.getAccountGroupID() == SETTConstant.AccountGroupType.YT) {
					tmpArray[i] = info.getId();
					i++;
				}
			}
			arrayReturn = new long[i];
			System.arraycopy(tmpArray, 0, arrayReturn, 0, i);
			return arrayReturn;
		}
		//
		public static final long[] getTrustAccountTypeCode(long lCurrencyID,long lOfficeID) {
			long[] arrayReturn = null;
			long[] tmpArray = new long[Env.getAccountTypeHashByOfficeAndCurrency(lOfficeID, lCurrencyID).size()];
			int i = 0;

			Enumeration enumeration = Env.getAccountTypeHashByOfficeAndCurrency(lOfficeID, lCurrencyID).elements();
			while (enumeration.hasMoreElements()) {
				AccountTypeInfo info = (AccountTypeInfo) enumeration
						.nextElement();
				if (info.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST) {
					tmpArray[i] = info.getId();
					i++;
				}
			}
			arrayReturn = new long[i];
			System.arraycopy(tmpArray, 0, arrayReturn, 0, i);
			return arrayReturn;
		}
		/**
		 * 委托存款账户类型编码
		 * 
		 * @return
		 */
		public static final long[] getConsignAccountTypeCode() {
			long[] arrayReturn = null;
			long[] tmpArray = new long[Env.getAccountTypeHash().size()];
			int i = 0;

			Enumeration enumeration = Env.getAccountTypeHash().elements();
			while (enumeration.hasMoreElements()) {
				AccountTypeInfo info = (AccountTypeInfo) enumeration
						.nextElement();
				if (info.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN) {
					tmpArray[i] = info.getId();
					i++;
				}
			}
			arrayReturn = new long[i];
			System.arraycopy(tmpArray, 0, arrayReturn, 0, i);
			return arrayReturn;
		}

		public static final long[] getConsignAccountTypeCode(long lCurrencyID,long lOfficeID) {
			long[] arrayReturn = null;
			long[] tmpArray = new long[Env.getAccountTypeHashByOfficeAndCurrency(lOfficeID, lCurrencyID).size()];
			int i = 0;

			Enumeration enumeration = Env.getAccountTypeHashByOfficeAndCurrency(lOfficeID, lCurrencyID).elements();
			while (enumeration.hasMoreElements()) {
				AccountTypeInfo info = (AccountTypeInfo) enumeration
						.nextElement();
				if (info.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN) {
					tmpArray[i] = info.getId();
					i++;
				}
			}
			arrayReturn = new long[i];
			System.arraycopy(tmpArray, 0, arrayReturn, 0, i);
			return arrayReturn;
		}

		/**
		 * 贴现账户类型编码
		 * 
		 * @return
		 */
		public static final long[] getDiscountAccountTypeCode() {
			long[] arrayReturn = null;
			long[] tmpArray = new long[Env.getAccountTypeHash().size()];
			int i = 0;

			Enumeration enumeration = Env.getAccountTypeHash().elements();
			while (enumeration.hasMoreElements()) {
				AccountTypeInfo info = (AccountTypeInfo) enumeration
						.nextElement();
				if (info.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT) {
					tmpArray[i] = info.getId();
					i++;
				}
			}
			arrayReturn = new long[i];
			System.arraycopy(tmpArray, 0, arrayReturn, 0, i);
			return arrayReturn;
		}

		public static final long[] getDiscountAccountTypeCode(long lCurrencyID,long lOfficeID) {
			long[] arrayReturn = null;
			long[] tmpArray = new long[Env.getAccountTypeHashByOfficeAndCurrency(lOfficeID, lCurrencyID).size()];
			int i = 0;

			Enumeration enumeration = Env.getAccountTypeHashByOfficeAndCurrency(lOfficeID, lCurrencyID).elements();
			while (enumeration.hasMoreElements()) {
				AccountTypeInfo info = (AccountTypeInfo) enumeration
						.nextElement();
				if (info.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT) {
					tmpArray[i] = info.getId();
					i++;
				}
			}
			arrayReturn = new long[i];
			System.arraycopy(tmpArray, 0, arrayReturn, 0, i);
			return arrayReturn;
		}

		/**
		 * 其他存款账户类型编码
		 * 
		 * @return
		 */
		public static final long[] getOtherDepositAccountTypeCode() {
			long[] arrayReturn = null;
			long[] tmpArray = new long[Env.getAccountTypeHash().size()];
			int i = 0;

			Enumeration enumeration = Env.getAccountTypeHash().elements();
			while (enumeration.hasMoreElements()) {
				AccountTypeInfo info = (AccountTypeInfo) enumeration
						.nextElement();
				if (info.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERDEPOSIT) {
					tmpArray[i] = info.getId();
					i++;
				}
			}
			arrayReturn = new long[i];
			System.arraycopy(tmpArray, 0, arrayReturn, 0, i);
			return arrayReturn;
		}

		public static final long[] getOtherDepositAccountTypeCode(
				long lCurrencyID,long lOfficeID) {
			long[] arrayReturn = null;
			long[] tmpArray = new long[Env.getAccountTypeHashByOfficeAndCurrency(lOfficeID, lCurrencyID).size()];
			int i = 0;

			Enumeration enumeration = Env.getAccountTypeHashByOfficeAndCurrency(lOfficeID, lCurrencyID).elements();
			while (enumeration.hasMoreElements()) {
				AccountTypeInfo info = (AccountTypeInfo) enumeration
						.nextElement();
				if (info.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERDEPOSIT) {
					tmpArray[i] = info.getId();
					i++;
				}
			}
			arrayReturn = new long[i];
			System.arraycopy(tmpArray, 0, arrayReturn, 0, i);
			return arrayReturn;
		}

		/**
		 * 其他贷款账户类型编码
		 * 
		 * @return
		 */
		public static final long[] getOtherLoanAccountTypeCode() {
			long[] arrayReturn = null;
			long[] tmpArray = new long[Env.getAccountTypeHash().size()];
			int i = 0;

			Enumeration enumeration = Env.getAccountTypeHash().elements();
			while (enumeration.hasMoreElements()) {
				AccountTypeInfo info = (AccountTypeInfo) enumeration
						.nextElement();
				if (info.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN) {
					tmpArray[i] = info.getId();
					i++;
				}
			}
			arrayReturn = new long[i];
			System.arraycopy(tmpArray, 0, arrayReturn, 0, i);
			return arrayReturn;
		}

		public static final long[] getOtherLoanAccountTypeCode(long lCurrencyID,long lOfficeID) {
			long[] arrayReturn = null;
			long[] tmpArray = new long[Env.getAccountTypeHashByOfficeAndCurrency(lOfficeID, lCurrencyID).size()];
			int i = 0;

			Enumeration enumeration = Env.getAccountTypeHashByOfficeAndCurrency(lOfficeID, lCurrencyID).elements();
			while (enumeration.hasMoreElements()) {
				AccountTypeInfo info = (AccountTypeInfo) enumeration
						.nextElement();
				if (info.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN) {
					tmpArray[i] = info.getId();
					i++;
				}
			}
			arrayReturn = new long[i];
			System.arraycopy(tmpArray, 0, arrayReturn, 0, i);
			return arrayReturn;
		}

		/**
		 * 表外账户类型编码
		 * 
		 * @return
		 */
		public static final long[] getOffBalanceAccountTypeCode() {
			long[] arrayReturn = null;
			long[] tmpArray = new long[Env.getAccountTypeHash().size()];
			int i = 0;

			Enumeration enumeration = Env.getAccountTypeHash().elements();
			while (enumeration.hasMoreElements()) {
				AccountTypeInfo info = (AccountTypeInfo) enumeration
						.nextElement();
				if (info.getAccountGroupID() == SETTConstant.AccountGroupType.OFFBALANCE) {
					tmpArray[i] = info.getId();
					i++;
				}
			}
			arrayReturn = new long[i];
			System.arraycopy(tmpArray, 0, arrayReturn, 0, i);
			return arrayReturn;
		}

		public static final long[] getOffBalanceAccountTypeCode(long lCurrencyID,long lOfficeID) {
			long[] arrayReturn = null;
			long[] tmpArray = new long[Env.getAccountTypeHashByOfficeAndCurrency(lOfficeID, lCurrencyID).size()];
			int i = 0;

			Enumeration enumeration = Env.getAccountTypeHashByOfficeAndCurrency(lOfficeID, lCurrencyID).elements();
			while (enumeration.hasMoreElements()) {
				AccountTypeInfo info = (AccountTypeInfo) enumeration
						.nextElement();
				if (info.getAccountGroupID() == SETTConstant.AccountGroupType.OFFBALANCE) {
					tmpArray[i] = info.getId();
					i++;
				}
			}
			arrayReturn = new long[i];
			System.arraycopy(tmpArray, 0, arrayReturn, 0, i);
			return arrayReturn;
		}

		/**
		 * 二级户账户类型编码
		 * 
		 * @return
		 */
		public static final long[] getBankAccountTypeCode() {
			long[] arrayReturn = null;
			long[] tmpArray = new long[Env.getAccountTypeHash().size()];
			int i = 0;

			Enumeration enumeration = Env.getAccountTypeHash().elements();
			while (enumeration.hasMoreElements()) {
				AccountTypeInfo info = (AccountTypeInfo) enumeration
						.nextElement();
				if (info.getAccountGroupID() == SETTConstant.AccountGroupType.BANK) {
					tmpArray[i] = info.getId();
					i++;
				}
			}
			arrayReturn = new long[i];
			System.arraycopy(tmpArray, 0, arrayReturn, 0, i);
			return arrayReturn;
		}

		public static final long[] getBankAccountTypeCode(long lCurrencyID,long lOfficeID) {
			long[] arrayReturn = null;
			long[] tmpArray = new long[Env.getAccountTypeHashByOfficeAndCurrency(lOfficeID, lCurrencyID).size()];
			int i = 0;

			Enumeration enumeration = Env.getAccountTypeHashByOfficeAndCurrency(lOfficeID, lCurrencyID).elements();
			while (enumeration.hasMoreElements()) {
				AccountTypeInfo info = (AccountTypeInfo) enumeration
						.nextElement();
				if (info.getAccountGroupID() == SETTConstant.AccountGroupType.BANK) {
					tmpArray[i] = info.getId();
					i++;
				}
			}
			arrayReturn = new long[i];
			System.arraycopy(tmpArray, 0, arrayReturn, 0, i);
			return arrayReturn;
		}

		/**
		 * 是否存款账户组类型
		 * 
		 * @param lAccountID
		 * @return
		 */
		public static boolean isDepositAccountType(long lAccountType) {
			boolean b = false;
			long[] array = getDepositAccountTypeCode();
			for (int i = 0; i < array.length; i++) {
				if (array[i] == lAccountType) {
					b = true;
					break;
				}
			}
			return b;
		}

		/**
		 * 是否贷款账户组类型
		 * 
		 * @param lAccountID
		 * @return
		 */
		public static boolean isLoanAccountType(long lAccountType) {
			boolean b = false;
			long[] array = getLoanAccountTypeCode();
			for (int i = 0; i < array.length; i++) {
				if (array[i] == lAccountType) {
					b = true;
					break;
				}
			}
			return b;
		}

		/**
		 * 是否活期账户组类型
		 * 
		 * @param lAccountID
		 * @return
		 */
		public static boolean isCurrentAccountType(long lAccountType) {
			boolean b = false;
			long[] array = getCurrentAccountTypeCode();
			for (int i = 0; i < array.length; i++) {
				if (array[i] == lAccountType) {
					b = true;
					break;
				}
			}
			return b;
		}
		/**
		 * 是否协定存款账户组类型
		 * 
		 * @param lAccountID
		 * @return
		 */
		public static boolean nIsNegotiate(long lAccountType) {
			boolean b = false;
			long[] array = getCurrentAccountTypeCode();
			for (int i = 0; i < array.length; i++) {
				if (array[i] == lAccountType) {
					b = true;
					break;
				}
			}
			return b;
		}

		/**
		 * 是否保证金账户组类型
		 * 
		 * @param lAccountID
		 * @return
		 */
		public static boolean isMarginAccountType(long lAccountType) {
			boolean b = false;
			long[] array = getMarginAccountTypeCode(lAccountType);
			for (int i = 0; i < array.length; i++) {
				if (array[i] == lAccountType) {
					b = true;
					break;
				}
			}
			return b;
		}

		/**
		 * 是否定期账户组类型
		 * 
		 * @param lAccountID
		 * @return
		 */
		public static boolean isFixAccountType(long lAccountType) {
			boolean b = false;
			long[] array = getFixAccountTypeCode(lAccountType);
			for (int i = 0; i < array.length; i++) {
				if (array[i] == lAccountType) {
					b = true;
					break;
				}
			}
			return b;
		}

		/**
		 * 是否通知账户组类型
		 * 
		 * @param lAccountID
		 * @return
		 */
		public static boolean isNotifyAccountType(long lAccountType) {
			boolean b = false;
			long[] array = getNotifyAccountTypeCode(lAccountType);
			for (int i = 0; i < array.length; i++) {
				if (array[i] == lAccountType) {
					b = true;
					break;
				}
			}
			return b;
		}

		/**
		 * 是否信托贷款账户组类型
		 * 
		 * @param lAccountID
		 * @return
		 */
		public static boolean isTrustAccountType(long lAccountType) {
			boolean b = false;
			long[] array = getTrustAccountTypeCode();
			for (int i = 0; i < array.length; i++) {
				if (array[i] == lAccountType) {
					b = true;
					break;
				}
			}
			return b;
		}
		
		/**
		 * 是否银团贷款账户组类型
		 * add by zcwang 2007-6-18
		 * @param lAccountID
		 * @return
		 */
		public static boolean isYTAccountType(long lAccountType) {
			boolean b = false;
			long[] array = getYTAccountTypeCode();
			for (int i = 0; i < array.length; i++) {
				if (array[i] == lAccountType) {
					b = true;
					break;
				}
			}
			return b;
		}
		
		/**
		 * 是否委托贷款账户组类型
		 * 
		 * @param lAccountID
		 * @return
		 */
		public static boolean isConsignAccountType(long lAccountType) {
			boolean b = false;
			long[] array = getConsignAccountTypeCode();
			for (int i = 0; i < array.length; i++) {
				if (array[i] == lAccountType) {
					b = true;
					break;
				}
			}
			return b;
		}

		/**
		 * 是否贴现贷款账户组类型
		 * 
		 * @param lAccountID
		 * @return
		 */
		public static boolean isDiscountAccountType(long lAccountType) {
			boolean b = false;
			long[] array = getDiscountAccountTypeCode();
			for (int i = 0; i < array.length; i++) {
				if (array[i] == lAccountType) {
					b = true;
					break;
				}
			}
			return b;
		}

		/**
		 * 是否其他存款账户组类型
		 * 
		 * @param lAccountID
		 * @return
		 */
		public static boolean isOtherDepositAccountType(long lAccountType) {
			boolean b = false;
			long[] array = getOtherDepositAccountTypeCode();
			for (int i = 0; i < array.length; i++) {
				if (array[i] == lAccountType) {
					b = true;
					break;
				}
			}
			return b;
		}

		/**
		 * 是否其他贷款账户组类型
		 * 
		 * @param lAccountID
		 * @return
		 */
		public static boolean isOtherLoanAccountType(long lAccountType) {
			boolean b = false;
			long[] array = getOtherLoanAccountTypeCode();
			for (int i = 0; i < array.length; i++) {
				if (array[i] == lAccountType) {
					b = true;
					break;
				}
			}
			return b;
		}

		/**
		 * 是否表外账户账户组类型
		 * 
		 * @param lAccountID
		 * @return
		 */
		public static boolean isOffBalanceAccountType(long lAccountType) {
			boolean b = false;
			long[] array = getOffBalanceAccountTypeCode();
			for (int i = 0; i < array.length; i++) {
				if (array[i] == lAccountType) {
					b = true;
					break;
				}
			}
			return b;
		}

		/**
		 * 是否银行二级账户账户组类型
		 * 
		 * @param lAccountID
		 * @return
		 */
		public static boolean isBankAccountType(long lAccountType) {
			boolean b = false;
			long[] array = getBankAccountTypeCode();
			for (int i = 0; i < array.length; i++) {
				if (array[i] == lAccountType) {
					b = true;
					break;
				}
			}
			return b;
		}

		/**
		 * 根据账户类型ID取账户类型信息
		 * 
		 * @param lAccountTypeID
		 * @return
		 */
		public static AccountTypeInfo getAccountTypeInfoByAccountTypeID(
				long lAccountTypeID) {
			AccountTypeInfo info = (AccountTypeInfo) Env.getAccountTypeHash(lAccountTypeID)
					.get(String.valueOf(lAccountTypeID));
			return info;
		}

		/**
		 * 根据账户类型ID取其所属账户组
		 * 
		 * @param lAccountTypeID
		 * @return
		 */
		public static long getAccountGroupTypeIDByAccountTypeID(
				long lAccountTypeID) {
			long lReturn = -1;
			AccountTypeInfo info = (AccountTypeInfo) Env.getAccountTypeHash(lAccountTypeID)
					.get(String.valueOf(lAccountTypeID));
			if (info != null) {
				lReturn = info.getAccountGroupID();
			}
			return lReturn;
		}
		/**
		 *2007.6.14 by qhzhou
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型
		 *            （0，显示全部； 1，显示所有贷款账户类型）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList11(JspWriter out, String strControlName,
				long lCurrencyID,long lOfficeID, int nType, long lSelectValue,
				boolean isNeedAll, boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID =  getAllCodeByOfficeAndCurrency(lOfficeID,lCurrencyID);
					
					break;
				case 1:
					lArrayID = getLoanAccountTypeCode();
					break;
				}

				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}
		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型
		 *            （0，显示全部； 1，显示所有贷款账户类型）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				long lCurrencyID, int nType, long lSelectValue,
				boolean isNeedAll, boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				case 1:
					lArrayID = getLoanAccountTypeCode();
					break;
				}

				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}
	}

	public static class FixedDepositDraw {
		// 定期开立
		public static final long CODE_TRANSFIXEDDEPOSIT_TOCURRENT = 7; // 转活期

		public static final long CODE_TRANSFIXEDDEPOSIT_CONTINUE = 8; // 续存
	}

	public static class SubjectType {
		// 科目类型
		public static final long BANKRECEIVE = 1; // 银行收款

		public static final long BANKPAY = 2; // 银行付款

		public static final long CHECKPAY = 3; // 支票付款

		public static final long CASHPAY = 4; // 现金付款

		public static final long DRAFTPAY = 5; // 票汇付款

		public static final long INTERNALVIREMENT_MAIN = 6; // 内部转账总部账

		public static final long CONSIGNRECEIVE_MAIN = 7; // 委托收款总部账

		public static final long CONSIGNDEPOSIT_MAIN = 8; // 委托存款总部账

		public static final long CAUTIONMONEYSAVE = 9; // 保证金存款

		public static final long NATIONALDEBT_BUY = 10; // 国债买入

		public static final long NATIONALDEBT_SELL = 11; // 国债卖出

		public static final long OPENFIXEDDEPOSIT_INTER = 12; // 定期开立资金来源为活期存款

		public static final long OPENFIXEDDEPOSIT_BANK = 13; // 定期开立资金来源为银行收款

		public static final long FIXEDTOCURRENTTRANSFER_PRIN_TOCURRENT = 14;

		// 定期转活期本金转至活期账户号
		public static final long FIXEDTOCURRENTTRANSFER_PRIN_TOBANK = 15;

		// 定期转活期本金付款
		public static final long FIXEDTOCURRENTTRANSFER_ACCOUNT_INTEREST_TOCURRENT = 16;

		// 定期转活期挂账利息（活期）
		public static final long FIXEDTOCURRENTTRANSFER_ACCOUNT_INTEREST_TOBANK = 17;

		// 定期转活期挂账利息（银行）
		public static final long FIXEDTOCURRENTTRANSFER_PAY_INTEREST_TOCURRENT = 18;

		// 定期转活期利息支付（活期）
		public static final long FIXEDTOCURRENTTRANSFER_PAY_INTEREST_TOBANK = 19;

		// 定期转活期利息支付（银行）
		public static final long FIXEDTOCURRENTTRANSFER_CURRENT_INTEREST_TOCURRENT = 20;

		// 定期转活期活期利息（活期）
		public static final long FIXEDTOCURRENTTRANSFER_CURRENT_INTEREST_TOBANK = 21;

		// 定期转活期活期利息（银行）
		public static final long FIXEDCONTINUETRANSFER = 22; // 定期转存（本金）

		public static final long FIXEDCONTINUETRANSFER_ALL = 23; // 定期转存本利续存（本金）

		public static final long FIXEDCONTINUETRANSFER_ACCOUNT_INTEREST_TOCURRENT = 24;

		// 定期转存挂账利息（活期）
		public static final long FIXEDCONTINUETRANSFER_PAY_INTEREST_TOCURRENT = 25;

		// 定期转存利息支付（活期）
		public static final long FIXEDCONTINUETRANSFER_ACCOUNT_INTEREST_TOBANK = 26;

		// 定期转存挂账利息（银行）
		public static final long FIXEDCONTINUETRANSFER_PAY_INTEREST_TOBANK = 27;

		// 定期转存利息支付（银行）
		public static final long FIXEDCONTINUETRANSFER_ACCOUNT_INTEREST_ALL = 28;

		// 定期转存挂账利息（本利续存）
		public static final long FIXEDCONTINUETRANSFER_PAY_INTEREST_ALL = 29;

		// 定期转存利息支付（本利续存）
		public static final long FIXEDDEPOSIT_RECEIVEACCOUNT_INTEREST_PAY = 51;

		// 定期付出利息
		public static final long FIXEDDEPOSIT_RECEIVEACCOUNT_INTEREST_BOOKED = 52;

		// 定期挂账利息
		public static final long OPENNOTIFYACCOUNT_CURRENT = 30;

		// 通知存款开立资金来源为活期存款
		public static final long OPENNOTIFYACCOUNT_BANK = 31; // 通知存款开立资金来源为银行收款

		public static final long NOTIFYDEPOSITDRAW_PRIN_TOCURRENT = 32;

		// 通知存款支取本金（活期）
		public static final long NOTIFYDEPOSITDRAW_PRIN_PAY = 33;

		// 通知存款支取本金（银行）
		public static final long NOTIFYDEPOSITDRAW_INTER_TOCURRENT = 34;

		// 通知存款支取利息（活期）
		public static final long NOTIFYDEPOSITDRAW_INTER_PAY = 35;

		// 通知存款支取利息（银行）
		public static final long NOTIFYDEPOSITDRAW_INTER_AHEADTOCURRENT = 65;

		// 提前通知存款支取利息（活期）
		public static final long NOTIFYDEPOSITDRAW_INTER_AHEADPAY = 66;

		// 提前通知存款支取利息（银行）
		public static final long TRUSTLOANGRANT_PAY = 36; // 信托贷款发放（银行）

		public static final long TRUSTLOANGRANT_TOCURRENT_MAIN = 37;

		// 信托贷款发放（活期）
		public static final long TRUSTLOANRERECEIVE_BANKRECEIVE = 38;

		// 信托贷款收回本金（银行）
		public static final long TRUSTLOANRERECEIVE_PRIN_MAIN = 39;

		// 信托贷款收回本金（活期）
		public static final long TRUSTLOANRECEIVE_INTEREST_MAIN = 40;

		// 信托贷款收回利息账
		public static final long TRUSTLOANRECEIVE_INTEREST_AHEADREPAY_TRUSTFLOW = 50;

		// 信托贷款收回利息账
		public static final long TRUSTLOANRECEIVE_INTEREST_AHEADREPAY_TRUSTFIX = 56;

		// 信托贷款收回利息账
		public static final long CONSIGNLOANGRANT_ISKEEPACCOUNT = 41;

		// 委托贷款发放后补贷款记账处理
		public static final long CONSIGNLOANGRANT_CONSIGN = 42; // 委托贷款发放委托方

		public static final long CONSIGNLOANGRANT_DEBIT_BANK = 43;

		// 委托贷款发放借款方（银行）
		public static final long CONSIGNLOANGRANT_DEBIT_CURRENT = 44;

		// 委托贷款发放借款方（活期）
		public static final long CONSIGNLOANRECEIVE_ISCANCELLOAN = 45;

		// 委托贷款收回取消贷款处理
		public static final long CONSIGNLOANRECEIVE_CONSIGN = 46; // 委托贷款收回委托方

		public static final long CONSIGNLOANRECEIVE_BANKRECEIVE = 47;

		// 委托贷款收回贷款方（银行）
		public static final long CONSIGNLOANRECEIVE_CURRENT = 48;

		// 委托贷款收回贷款方（活期）
		public static final long CONSIGNLOANRECEIVE_INTEREST = 49; // 委托贷款收回利息

		public static final long DISCOUNTGRANT_INTEREST = 60; // 贴现发放利息设置

		public static final long DISCOUNTGRANT_MAIN = 61; // 贴现发放（活期）

		public static final long DISCOUNTGRANT_TOBANK = 62; // 贴现发放（银行）

		public static final long CYCLOANGRANT = 71; // 循环贷款发放

		public static final long CYCLOANRECEIVE = 72; // 循环贷款收回

		public static final long DISCOUNTRECEIVE = 101; // 贴现收回无罚息

		public static final long DISCOUNTRECEIVE_INTEREST_MAIN = 102; // 贴现收回罚息

		public static final long CONSIGNUPRECEIVE = 103; // 委托上收资金

		public static final long CONSIGNUPSAVE = 104; // 上存资金调回及发放负息资金

		public static final long CONSIGNUPSAVERECEIVE = 105; // 上存资金－上收及调回

		public static final long SHORTDEBTRECEIVE = 106; // 还短负

		public static final long CONSIGNCAPITALOPERATION = 107; // 客户委托资金调拨

		public static final long GENERALLEDGER = 108; // 总账类

		public static final long TRANSFEE = 109; // 交易费用(活期）

		public static final long TRANSFEE_FROMBANK = 110; // 交易费用（银行）

		public static final long SPECIALOPERATION = 111; // 特殊交易

		public static final long SHORTLOANGRANT = 112; // 发放短期贷款

		public static final long SHORTLOANRECEIVE = 113; // 短期贷款收回

		public static final long ONETOMULTI_INTER = 114; // 一付多收内部转账

		public static final long ONETOMULTI_BANKPAY = 115; // 一付多收对外付款

		// public static final long MULTILOAN=23;//多笔贷款收回
		// public static final long CYCLOAN=30;//发放循环贷款
		public static final long INTERESTFEE_CURRENT = 301; // 活期利息

		public static final long INTERESTFEE_BACK = 302; // 备付金利息收入

		public static final long TRUSTLOANRECEIVE_SURETY = 303; // 信托贷款担保费

		public static final long CONSIGNLOANRECEIVE_COMMISSION = 304; // 委托贷款手续费

		public static final long CONSIGNLOANRECEIVE_INTERESTTAX = 305;

		// 委托贷款利息税费
		public static final long SHORTLOAN_INTEREST = 306; // 短期贷款利息

		public static final long CYCLOAN_INTEREST = 307; // 循环贷款利息

		public static final long INTERESTFEE_PREPARE = 308; // 准备金利息收入

		public static final long INTERESTFEE_TRUSTFIX = 309; // 信托固定收息

		public static final long INTERESTFEE_TRUSTFLOW = 310; // 信托流动收息

		public static final long INTERESTFEE_TRUST = 311; // 信托贷款收回利息账

		public static final long INTERNALVIREMENT_RECEIVER = 201; // 通存通兑办事处收款方

		public static final long INTERNALVIREMENT_PAYER = 202; // 通存通兑办事处付款方

		public static final long INTERNALVIREMENT_MAIN_RECEIVER = 203;

		// 通存通兑总部收款方
		public static final long INTERNALVIREMENT_MAIN_PAYER = 204; // 通存通兑总部付款方

		public static final long CONSIGNRECEIVE_RECEIVER = 201; // 委托收款办事处收款方

		public static final long CONSIGNRECEIVE_PAYER = 202; // 委托收款办事处付款方

		public static final long CONSIGNRECEIVE_MAIN_RECEIVER = 203;

		// 委托收款总部收款账
		public static final long CONSIGNRECEIVE_MAIN_PAYER = 204; // 委托收款总部付款账

		public static final long CONSIGNDEPOSIT_RECEIVER = 201; // 委托存款办事处收款账

		public static final long CONSIGNDEPOSIT_PAYER = 202; // 委托存款办事处付款账

		public static final long CONSIGNDEPOSIT_MAIN_RECEIVER = 203;

		// 委托存款总部收款账
		public static final long CONSIGNDEPOSIT_MAIN_PAYER = 204; // 委托存款总部付款账

		public static final long TRUSTLOANRECEIVE_PRIN_RECEIVE = 201;

		// 信托贷款收回本金通存通兑办事处收款账
		public static final long TRUSTLOANRECEIVE_PRIN_PAY = 202;

		// 信托贷款收回本金通存通兑办事处付款账
		public static final long TRUSTLOANRECEIVE_PRIN_MAIN_RECEIVE = 203;

		// 信托贷款收回本金通存通兑总部收款账
		public static final long TRUSTLOANRECEIVE_PRIN_MAIN_PAY = 204;

		// 信托贷款收回本金通存通兑总部付款账
		public static final long TRUSTLOANRECEIVE_INTEREST_RECEIVE = 201;

		// 信托贷款收回利息通存通兑办事处收款账
		public static final long TRUSTLOANRECEIVE_INTEREST_PAY = 202;

		// 信托贷款收回利息通存通兑办事处付款账
		public static final long TRUSTLOANRECEIVE_INTEREST_MAIN_RECEIVE = 203;

		// 信托贷款收回利息通存通兑总部收款账
		public static final long TRUSTLOANRECEIVE_INTEREST_MAIN_PAY = 204;

		// 信托贷款收回利息通存通兑总部付款账
		public static final long CONSIGNLOANGRANT_OFFICE_RECEIVE = 201;

		// 委托贷款发放办事处收款
		public static final long CONSIGNLOANGRANT_OFFICE_PAY = 202;

		// 委托贷款发放办事处付款
		public static final long CONSIGNLOANGRANT_MAIN_RECEIVE = 203;

		// 委托贷款发放通存通兑总部收款
		public static final long CONSIGNLOANGRANT_MAIN_PAY = 204;

		// 委托贷款发放通存通兑总部付款
		public static final long DISCOUNTGRANT_PRIN_RECEIVE = 201; // 贴现发放办事处收款

		public static final long DISCOUNTGRANT_PRIN_PAY = 202; // 贴现发放办事处付款

		public static final long DISCOUNTGRANT_MAIN_RECEIVE = 203; // 贴现发放总部收款

		public static final long DISCOUNTGRANT_MAIN_PAY = 204; // 贴现发放总部付款

		public static final long DISCOUNTRECEIVE_RECEIVE = 201; // 贴现收回罚息办事处收款

		public static final long DISCOUNT_PAY = 202; // 贴现收回罚息办事处付款

		public static final long DISCOUNTRECEIVE_INTEREST_MAIN_RECEIVE = 203;

		// 贴现收回罚息总部收款
		public static final long DISCOUNTRECEIVE_INTEREST_MAIN_PAY = 204;

		// 贴现收回罚息总部付款
		public static final long TRUSTLOANGRANT_TOCURRENT_RECEIVE = 201;

		// 信托贷款发放转至活期账户办事处收款账
		public static final long TRUSTLOANGRANT_TOCURRENT_PAY = 202;

		// 信托贷款发放转至活期账户办事处付款账
		public static final long TRUSTLOANGRANT_TOCURRENT_MAIN_RECEIVE = 203;

		// 信托贷款发放转至活期账户总部收款账
		public static final long TRUSTLOANGRANT_TOCURRENT_MAIN_PAY = 204;
		// 信托贷款发放转至活期账户总部付款账
	}

	public static class SubjectSettingType {
		// 借贷双方的类型
		public static final long PAYACCOUNT = 1; // 付款方账户

		public static final long RECEIVEACCOUNT = 2; // 收款方账户

		public static final long BANK = 3; // 开户行

		public static final long RESERVEACCOUNT = 4; // 备付金

		public static final long GENERALLEDGER = 5; // 总账

		public static final long TRANSFEE = 6; // 交易费

		public static final long SELF = 7; // 自设

		public static final long FIXEDACCOUNT = 8; // 定期账户号

		public static final long LOANACCOUNT = 9; // 贷款账户号

		public static final long RECEIVEACCOUNT_FIXEDPAYINTEREST = 10;

		// 收款方账户-利息付
		public static final long RECEIVEACCOUNT_FIXEDBOOKEDINTEREST = 11;

		// 收款方账户-挂账利息
		public static final long PAYACCOUNT_TRUSTLOANRECEIVEINTEREST = 12;

		// 付款方账户-利息收入
		public static final long PAYACCOUNT_FIXEDPAYINTEREST = 13; // 付款方账户-利息付

		public static final long PAYACCOUNT_FIXEDBOOKEDINTEREST = 14;

		// 付款方账户-挂账利息
		public static final long RECEIVEACCOUNT_TRUSTLOANRECEIVEINTEREST = 15;
		// 收款方账户-利息收入
	}

	/**
	 * 定期/通知存款来源
	 * 
	 * @author wlming
	 * 
	 * To change the template for this generated type comment go to
	 * Window>Preferences>Java>Code Generation>Code and Comments
	 */
	public static class FixedDepositSource {
		public static final long CURRENT = 1; // 活期存款

		public static final long BANK = 2; // 银行收款

		public static final long FIXEDDEPOSIT = 3; // 定期续存

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) CURRENT:
				strReturn = "活期存款";
				break;
			case (int) BANK:
				strReturn = "银行收款";
				break;
			case (int) FIXEDDEPOSIT:
				strReturn = "到期续存";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { CURRENT, BANK, FIXEDDEPOSIT };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$FixedDepositSource",
							officeID, currencyID);
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型
		 *            （0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

	}

	public static class NotifySource {
		// 通知存款存款来源
		public static final long CURRENT = 1; // 活期存款

		public static final long BANK = 2; // 银行收款
	}

	public static class LoanNoticeType {
		// 通知书类型
		public static final long LoanMatureNotice = 1; // 贷款到期通知书

		public static final long LoanDunNotice = 2; // 贷款催收通知书

		public static final long LoanInterestNotice = 3; // 应付贷款利息通知书

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) LoanMatureNotice:
				strReturn = "贷款到期通知书";
				break;
			case (int) LoanDunNotice:
				strReturn = "贷款催收通知书";
				break;
			case (int) LoanInterestNotice:
				strReturn = "应付贷款利息通知书";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { LoanMatureNotice, LoanDunNotice,
					LoanInterestNotice };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$LoanNoticeType",
							officeID, currencyID);
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型
		 *            （0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

	}

	public static class NotifyDepositType {
		// 通知存款类型
		public static final long ONE_DAY = 1; // 1天

		public static final long SEVEN_DAY = 7; // 7天

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) ONE_DAY:
				strReturn = "1天";
				break;
			case (int) SEVEN_DAY:
				strReturn = "7天";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { ONE_DAY, SEVEN_DAY };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$NotifyDepositType",
							officeID, currencyID);
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型
		 *            （0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}
	}

	public static class NotifyDraw {
		// 通知存款支取资金去向
		public static final long PRIN_CURRENT = 1; // 本金放至活期存款

		public static final long PRIN_BANK = 2; // 本金付款

		public static final long INTEREST_CURRENT = 3; // 利息放至活期存款账户

		public static final long INTEREST_BANK = 4; // 利息付款
	}

	public static class AccountRecordType {
		// ///记账，挂账
		public static final long CODE_ACCOUNT_BOOKED = 1; // 挂账

		public static final long CODE_ACCOUNT_BANK = 2; // 记账

		public static final long CODE_ACCOUNT_STRIKE = 3; // 冲账
	}

	public static class TransQueryType {
		// 交易查询类型
		public static final long CURRENT = 1; // 活期存款

		public static final long FIXED = 2; // 定期存款

		public static final long NOTIFY = 3; // 通知存款

		public static final long TRUST = 4; // 信托贷款

		public static final long CONSIGNLOAN = 5; // 委托贷款

		public static final long DISCOUNT = 6; // 短期贷款

		public static final long SHORTLOAN = 7; // 贴现

		public static final long CYCLOAN = 8; // 循环贷款

		public static final long CONSIGN = 9; // 委托业务

		public static final long OUTPAY = 10; // 对外付款

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) CURRENT:
				strReturn = "活期存款";
				break;
			case (int) FIXED:
				strReturn = "定期存款";
				break;
			case (int) NOTIFY:
				strReturn = "通知存款";
				break;
			case (int) TRUST:
				strReturn = "信托贷款";
				break;
			case (int) CONSIGNLOAN:
				strReturn = "委托贷款";
				break;
			case (int) DISCOUNT:
				strReturn = "贴现";
				break;
			case (int) SHORTLOAN:
				strReturn = "短期贷款";
				break;
			case (int) CYCLOAN:
				strReturn = "循环贷款";
				break;
			case (int) CONSIGN:
				strReturn = "委托业务";
				break;
			case (int) OUTPAY:
				strReturn = "对外付款";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { CURRENT, FIXED, NOTIFY, TRUST, CONSIGNLOAN,
					DISCOUNT, SHORTLOAN, CYCLOAN, CONSIGN, OUTPAY };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$TransQueryType",
							officeID, currencyID);
		}

	}

	public static class SettInstrType {
		//
		public static final long CAPTRANSFER_SELF = 1; // 资金划拨-本转
		
		public static final long CAPTRANSFER_BANKPAY = 2; // 资金划拨-银行付款

		public static final long CAPTRANSFER_BANKPAY_DOWNTRANSFER = 18;// 资金划拨－－下划

		public static final long CAPTRANSFER_FINCOMPANYPAY = 15; // 资金划拨-银行付款---财司代付

		public static final long CAPTRANSFER_PAYSUBACCOUNT = 16; // 资金划拨-银行付款---拨子账户

		public static final long CAPTRANSFER_INTERNALVIREMENT = 3; // 资金划拨-内部转账

		public static final long CAPTRANSFER_OTHER = 11; // 资金划拨-其他

		public static final long CHILDCAPTRANSFER = 12; // 下属单位资金划拨

		public static final long OPENFIXDEPOSIT = 4; // 定期开立

		public static final long FIXEDTOCURRENTTRANSFER = 5; // 定期支取

		public static final long OPENNOTIFYACCOUNT = 6; // 通知开立

		public static final long NOTIFYDEPOSITDRAW = 7; // 通知支取

		public static final long TRUSTLOANRECEIVE = 8; // 自营贷款清还

		public static final long CONSIGNLOANRECEIVE = 9; // 委托贷款清还

		public static final long INTERESTFEEPAYMENT = 10; // 利息费用清还

		public static final long YTLOANRECEIVE = 13; // 银团贷款清还

		public static final long CHANGEFIXDEPOSIT = 14; // 换开定期存单指令
		
		public static final long DRIVEFIXDEPOSIT = 19;//定期续存

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) CAPTRANSFER_SELF:
				strReturn = "资金划拨-本转";
				break;
			case (int) CAPTRANSFER_BANKPAY:
				strReturn = "资金划拨-银行付款";
				break;
			case (int) CAPTRANSFER_FINCOMPANYPAY:
				strReturn = "资金划拨-银行付款-财司代付";
				break;
			case (int) CAPTRANSFER_PAYSUBACCOUNT:
				strReturn = "资金划拨-银行付款-拨子账户";
				break;
			case (int) CAPTRANSFER_INTERNALVIREMENT:
				strReturn = "资金划拨-内部转账";
				break;
			case (int) CAPTRANSFER_BANKPAY_DOWNTRANSFER:
				strReturn = "资金划拨-下划";
				break;
			case (int) CAPTRANSFER_OTHER:
				strReturn = "资金划拨-其他";
				break;
			case (int) CHILDCAPTRANSFER:
				strReturn = "下属单位资金划拨";
				break;
			case (int) OPENFIXDEPOSIT:
				strReturn = "定期开立";
				break;
			case (int) FIXEDTOCURRENTTRANSFER:
				strReturn = "定期支取";
				break;
			case (int) OPENNOTIFYACCOUNT:
				strReturn = "通知开立";
				break;
			case (int) NOTIFYDEPOSITDRAW:
				strReturn = "通知支取";
				break;
			case (int) TRUSTLOANRECEIVE:
				strReturn = "自营贷款清还";
				break;
			case (int) CONSIGNLOANRECEIVE:
				strReturn = "委托贷款清还";
				break;
			case (int) INTERESTFEEPAYMENT:
				strReturn = "利息费用清还";
				break;
			case (int) YTLOANRECEIVE:
				strReturn = "银团贷款清还";
				break;
			case (int) CHANGEFIXDEPOSIT:
				strReturn = "换开定期存单";
				break;
			case (int) DRIVEFIXDEPOSIT:
				strReturn = "到期续存";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { CAPTRANSFER_SELF, CAPTRANSFER_BANKPAY,
					CAPTRANSFER_BANKPAY_DOWNTRANSFER,
					CAPTRANSFER_FINCOMPANYPAY, CAPTRANSFER_PAYSUBACCOUNT,
					CAPTRANSFER_OTHER, CHILDCAPTRANSFER,
					CAPTRANSFER_INTERNALVIREMENT, OPENFIXDEPOSIT,
					FIXEDTOCURRENTTRANSFER, OPENNOTIFYACCOUNT,
					NOTIFYDEPOSITDRAW, TRUSTLOANRECEIVE, CONSIGNLOANRECEIVE,
					INTERESTFEEPAYMENT, YTLOANRECEIVE, CHANGEFIXDEPOSIT };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$SettInstrType",
							officeID, currencyID);
		}

		public static final long[] getCodeForZJ(long officeID, long currencyID) {
			long[] lTemp = { CAPTRANSFER_SELF, CAPTRANSFER_BANKPAY,
					CAPTRANSFER_BANKPAY_DOWNTRANSFER, CHILDCAPTRANSFER,
					CAPTRANSFER_INTERNALVIREMENT, OPENFIXDEPOSIT,
					FIXEDTOCURRENTTRANSFER, OPENNOTIFYACCOUNT,
					NOTIFYDEPOSITDRAW, TRUSTLOANRECEIVE, CONSIGNLOANRECEIVE,
					INTERESTFEEPAYMENT, YTLOANRECEIVE, CHANGEFIXDEPOSIT ,DRIVEFIXDEPOSIT };
			return lTemp;
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型
		 *            （0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				case 1:
					lArrayID = getCodeForZJ(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}
//		自定义下拉菜单状态，根据传入的状态进行显示
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID,long[] lArrayID) {
			//long[] lArrayID = null;
			String[] strArrayName = null;
			try {
			
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}
	}
    /**
     * 批量复核状态
     * @author Administrator
     *
     */
	public static class SettInstrStatus11 {
		
		
		public static final long DEAL = 4; // 未复核

		public static final long FINISH = 5; // 已复核

		

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			
			
			
			case (int) DEAL:
				strReturn = "未复核";
				break;
			case (int) FINISH:
				strReturn = "已复核";
				break;
			
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = {DEAL, FINISH  };
			return lTemp;
		}
		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$SettInstrStatus11",
							officeID, currencyID);
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型
		 *            （0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

	}

	
	/**
	 * （结算模块的）网上业务指令的状态，注意与网银模块取值不一样，必须转换。
	 */
	public static class SettInstrStatus {
		public static final long SIGN = 1000; // 已提交

		public static final long DEAL = 4; // 处理中

		public static final long FINISH = 5; // 已完成

		public static final long REFUSE = 6; // 已拒绝
		
		public static final long EXPORT = 7;  //已导出
		
		public static final long NOTEXPORT = 8; //未导出

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) SIGN:
				strReturn = "已提交";
				break;
			case (int) DEAL:
				strReturn = "处理中";
				break;
			case (int) FINISH:
				strReturn = "已完成";
				break;
			case (int) REFUSE:
				strReturn = "已拒绝";
				break;
			case (int) EXPORT:
				strReturn = "已导出";
				break;
			case (int) NOTEXPORT:
				strReturn = "未导出";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { SIGN, DEAL, FINISH, REFUSE };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$SettInstrStatus",
							officeID, currencyID);
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型
		 *            （0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}
		
		//自定义下拉菜单状态，根据传入的状态进行显示
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID,long[] lArrayID) {
			//long[] lArrayID = null;
			String[] strArrayName = null;
			try {
			
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

	}

	public static class ExcuteRemindType {
		// //调用自动发放利息程序的地方/
		public static final long AUTO = 1;

		public static final long GRANT = 2;

		public static final long REMIND = 3;
	}

	public static class InterestRecordSource { // ///结息记录来源
		public static final long GRANTAUTO = 1; // 自动发息

		public static final long NORMAL = 2; // 通过界面的计算利息结息

		public static final long FINISHLOAN = 3; // 贷款付息

		public static final long ADJUSTTOZERO = 4; // 调零结息
	}

	public static class GrantInterestStatus { // 利息费用账户状态
		public static final long NOTEXECUTE = 1; // 未执行

		public static final long SUCCESS = 2; // 成功

		public static final long FAIL = 3; // 失败

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) NOTEXECUTE:
				strReturn = "未执行";
				break;
			case (int) SUCCESS:
				strReturn = "成功";
				break;
			case (int) FAIL:
				strReturn = "失败";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { NOTEXECUTE, SUCCESS, FAIL };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$GrantInterestStatus",
							officeID, currencyID);
		}
	}

	public static class IndustryType { // 利息费用账户状态
		public static final long INDUSTRY = 1; // 工业企业

		public static final long COMMERCE = 2; // 商业企业

		public static final long BUILD = 3; // 建筑企业

		public static final long REALESTATE = 4; // 房地产企业

		public static final long OTHER = 15; // 其他企业

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) INDUSTRY:
				strReturn = "工业企业";
				break;
			case (int) COMMERCE:
				strReturn = "商业企业";
				break;
			case (int) BUILD:
				strReturn = "建安企业";
				break;
			case (int) REALESTATE:
				strReturn = "房地产开发企业";
				break;
			case (int) OTHER:
				strReturn = "其他企业";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { INDUSTRY, COMMERCE, BUILD, REALESTATE, OTHER };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$IndustryType",
							officeID, currencyID);
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型
		 *            （0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

	}

	public static class ConsignUpSaveType {
		public static final int UPSAVE = 1; // //上存-调回：上存

		public static final int UPRECEIVE = 2; // //上存-调回：调回

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) UPSAVE:
				strReturn = "上存";
				break;
			case (int) UPRECEIVE:
				strReturn = "调回";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { UPSAVE, UPRECEIVE };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$ConsignUpSaveType",
							officeID, currencyID);
		}
	}

	public static class SingleBankAccountType {
		// 单边账类型
		public static final long CASH = 1; // 现金付款

		public static final long TRANSFER = 2; // 银行转账

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) CASH:
				strReturn = "现金付款";
				break;
			case (int) TRANSFER:
				strReturn = "银行转账";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { CASH, TRANSFER };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$SingleBankAccountType",
							officeID, currencyID);
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型
		 *            （0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

	}

	/**
	 * 子账户状态
	 */
	public static class SubAccountStatus {
		public static final long NORMAL = 1; // 未结清

		public static final long FINISH = 2; // 已结清

		// 为上海电气增加 的子账户状态
		public static final long REPORTLOSS = 5; // 挂失

		public static final long ONLYPAYFREEZE = 6; // 只付不收冻结

		public static final long ALLFREEZE = 7; // 不收不付冻结

		public static final long PARTFREEZE = 8; // 部分冻结

		// 为 兼容业务增加 的子账户状态
		public static final long NEWSAVE = 3; // 新增状态

		public static final long OLDSAVE = 4; // 已修改状态

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) NORMAL:
				strReturn = "未结清";
				break;
			case (int) FINISH:
				strReturn = "已结清";
				break;
			case (int) NEWSAVE:
				strReturn = "未复核";
				break;
			case (int) OLDSAVE:
				strReturn = "已修改";
				break;
			case (int) REPORTLOSS:
				strReturn = "已挂失";
				break;
			case (int) ONLYPAYFREEZE:
				strReturn = "只收不付冻结";
				break;
			case (int) ALLFREEZE:
				strReturn = "不收不付冻结";
				break;
			case (int) PARTFREEZE:
				strReturn = "部分冻结";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { NORMAL, FINISH };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$SubAccountStatus",
							officeID, currencyID);
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型（0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				case 1:
					lArrayID = new long[3];
					lArrayID[0] = NEWSAVE;
					lArrayID[1] = OLDSAVE;
					lArrayID[2] = NORMAL;
					break;
				case 2:
					lArrayID = new long[2];
					lArrayID[0] = NEWSAVE;
					lArrayID[1] = NORMAL;
					break;
				case 3:
					lArrayID = new long[2];
					lArrayID[0] = OLDSAVE;
					lArrayID[1] = NORMAL;
					break;
				case 4:
					lArrayID = new long[3];
					lArrayID[0] = ONLYPAYFREEZE;
					lArrayID[1] = ALLFREEZE;
					lArrayID[2] = PARTFREEZE;
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				case 1:
					lArrayID = new long[3];
					lArrayID[0] = NEWSAVE;
					lArrayID[1] = OLDSAVE;
					lArrayID[2] = NORMAL;
					break;
				case 2:
					lArrayID = new long[2];
					lArrayID[0] = NEWSAVE;
					lArrayID[1] = NORMAL;
					break;
				case 3:
					lArrayID = new long[2];
					lArrayID[0] = OLDSAVE;
					lArrayID[1] = NORMAL;
					break;
				case 4:
					lArrayID = new long[3];
					lArrayID[0] = ONLYPAYFREEZE;
					lArrayID[1] = ALLFREEZE;
					lArrayID[2] = PARTFREEZE;
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

	}

	/**
	 * 借贷方向
	 * 
	 * @author wlming
	 * 
	 * To change the template for this generated type comment go to
	 * Window>Preferences>Java>Code Generation>Code and Comments
	 */
	public static class DebitOrCredit {
		public static final long DEBIT = 1; // 借

		public static final long CREDIT = 2; // 贷

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) DEBIT:
				strReturn = "借";
				break;
			case (int) CREDIT:
				strReturn = "贷";
				break;
			}
			return strReturn;
		}

		public static final String getBankAccountName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) DEBIT:
				strReturn = "银行收款";
				break;
			case (int) CREDIT:
				strReturn = "银行付款";
				break;
			}
			return strReturn;
		}

		/**
		 * 将银企接口中定义的借贷常量对象，转换为当前编码值
		 * 
		 * @param d
		 * @return long
		 */
		/*
		 * public static final long convertFromConstantObjectOfBS(Direction d) {
		 * long result = -1; if (d.equals(Direction.DEBIT)) { result = DEBIT; }
		 * else if (d.equals(Direction.CREDIT)) { result = CREDIT; } return
		 * result; }
		 */
		/**
		 * 将当前编码值转换为银企接口中定义的借贷常量对象
		 * 
		 * @param d
		 * @return long
		 */
		/*
		 * public static final Direction convertToConstantObjectOfBS(long d) {
		 * Direction result = null; if (d == DEBIT) { result = Direction.DEBIT; }
		 * else if (d == CREDIT) { result = Direction.CREDIT; } return result; }
		 */
		public static final long[] getAllCode() {
			long[] lTemp = { DEBIT, CREDIT };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$DebitOrCredit",
							officeID, currencyID);
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型（0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

		/**
		 * 显示下拉列表
		 * 
		 * @author xrli
		 * 
		 * To change the template for this generated type comment go to
		 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
		 */
		public static final void showBankAccountList(JspWriter out,
				String strControlName, int nType, long lSelectValue,
				boolean isNeedAll, boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getBankAccountName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}
	}

	public static class TransactionOperate {
		public static final long SAVE = 1; // 保存

		public static final long CHECK = 2; // 复核

		public static final long CANCELCHECK = 3; // 取消复核

		public static final long CONFIRM = 4; // 确认

		public static final long CANCELCONFIRM = 5; // 取消确认

		public static final long SIGN = 6; // 签认

		public static final long EXECUTE = 7; // 执行

		public static final long GOUZHANG = 8; // 勾账

		public static final long CANCEL_GOUZHANG = 9; // 取消勾账

		public static final long DELETE = 10; // 删除
	}
 public static class settArchivesType {
	 public static final String getName(long ID) {
			String strReturn = ""; // 初始化返回值
			ArchivesManagementDao biz = new ArchivesManagementDao();
			try {
				strReturn = biz.getNameByID(ID);
			}catch(Exception e){
				e.printStackTrace();
			}
			return strReturn;
		}	 
		public static final long[] getAllCode() { 
			long[] lTemp = null;
			ArchivesManagementDao biz = new ArchivesManagementDao();
			try {
				Collection c = biz.getAll();

				if (c != null && c.size() > 0) {
					int i = 0;
					lTemp = new long[c.size()];
					Iterator it = c.iterator();
					while (it.hasNext()) {
						lTemp[i] = ((ArchivesManagementInfo) it.next()).getId();
						i++;
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return lTemp;
		} 
		public static final long[] getCodeByOfficeID(long officeID) { 
			long[] lTemp = null;
			ArchivesManagementDao biz = new ArchivesManagementDao();
			try {
				Collection c = biz.getAllByOfficeID(officeID);

				if (c != null && c.size() > 0) {
					int i = 0;
					lTemp = new long[c.size()];
					Iterator it = c.iterator();
					while (it.hasNext()) {
						lTemp[i] = ((ArchivesManagementInfo) it.next()).getId();
						i++;
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return lTemp;
		}
		public static final void showArchivesTypeList(JspWriter out,
				String strControlName, int nType, long lSelectValue,
				boolean isNeedAll, boolean isNeedBlank, String strProperty,
				long lOfficeID, long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				
				
				lArrayID = getCodeByOfficeID(lOfficeID);
					
				
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]); 
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		
 }
	public static class SettLoanType {
		// 结算贷款类型
		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			LoanTypeSettingBiz biz = new LoanTypeSettingBiz();
			try {
				LoanTypeSettingInfo info = biz.findByID(lCode);
				strReturn = info.getName();
			} catch (LoanException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return strReturn;
		}

		public static final String getTypeCode(long id) {
			String strReturn = ""; // 初始化返回值
			LoanTypeSettingBiz biz = new LoanTypeSettingBiz();
			try {
				LoanTypeSettingInfo info = biz.findByID(id);
				strReturn = info.getCode();
			} catch (LoanException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return strReturn;
		}

		public static final long getBigType(long id) {
			long LoanTypeID = -1; // 初始化返回值
			LoanTypeSettingBiz biz = new LoanTypeSettingBiz();
			try {
				LoanTypeSettingInfo info = biz.findByID(id);
				LoanTypeID = info.getLoanTypeID();
			} catch (LoanException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return LoanTypeID;
		}

		public static final long[] getAllCode() {
			long[] lTemp = null;
			LoanTypeSettingBiz biz = new LoanTypeSettingBiz();
			try {
				Collection c = biz.findByLoanTypeID(-1);

				if (c != null && c.size() > 0) {
					int i = 0;
					lTemp = new long[c.size()];
					Iterator it = c.iterator();
					while (it.hasNext()) {
						lTemp[i] = ((LoanTypeSettingInfo) it.next()).getId();
						i++;
					}
				}
			} catch (LoanException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return lTemp;
		}

		public static final long[] getAllCodeLoanType(long officeID, long currencyID) {
			return Constant
			.getAllCode(
					"com.iss.itreasury.settlement.util.LOANConstant$LoanType",
					officeID, currencyID);
		}

		public static final long[] getAllCodeLoanType() {
			long[] lTemp = {LOANConstant.LoanType.ZY, LOANConstant.LoanType.WT,
				LOANConstant.LoanType.TX, LOANConstant.LoanType.ZGXE,
				LOANConstant.LoanType.YT, LOANConstant.LoanType.MFXD,
				LOANConstant.LoanType.DB, LOANConstant.LoanType.RZZL,
				LOANConstant.LoanType.OTHER};
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return getAllCode();
		}
		public static final long[] getConsignLoanCode1() {
			long[] lTemp = null;
			LoanTypeSettingBiz biz = new LoanTypeSettingBiz();
			try {
				Collection c = biz.findByLoanTypeID2(LOANConstant.LoanType.WT);

				if (c != null && c.size() > 0) {
					int i = 0;
					lTemp = new long[c.size()];
					Iterator it = c.iterator();
					while (it.hasNext()) {
						lTemp[i] = ((LoanTypeSettingInfo) it.next()).getId();
						i++;
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return lTemp;
		}
		//2007.6.11 qhzhou
		public static final long[] getConsignLoanCode1(long lOfficeID,long lCurrencyID) {
			long[] lTemp = null;
			LoanTypeSettingBiz biz = new LoanTypeSettingBiz();
			try {
				Collection c = biz.findByLoanTypeID2(LOANConstant.LoanType.WT,lOfficeID,lCurrencyID);

				if (c != null && c.size() > 0) {
					int i = 0;
					lTemp = new long[c.size()];
					Iterator it = c.iterator();
					while (it.hasNext()) {
						lTemp[i] = ((LoanTypeSettingInfo) it.next()).getId();
						i++;
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return lTemp;
		}

		public static final long[] getDidcountLoanCode1() {
			long[] lTemp = null;
			LoanTypeSettingBiz biz = new LoanTypeSettingBiz();
			try {
				Collection c = biz.findByLoanTypeID2(LOANConstant.LoanType.TX);

				if (c != null && c.size() > 0) {
					int i = 0;
					lTemp = new long[c.size()];
					Iterator it = c.iterator();
					while (it.hasNext()) {
						lTemp[i] = ((LoanTypeSettingInfo) it.next()).getId();
						i++;
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return lTemp;
		}
//		2007.6.11 qhzhou
		public static final long[] getDidcountLoanCode1(long lOfficeID,long lCurrencyID) {
			long[] lTemp = null;
			LoanTypeSettingBiz biz = new LoanTypeSettingBiz();
			try {
//				2007.6.11 qhzhou
//				Collection c = biz.findByLoanTypeID2(LOANConstant.LoanType.TX);
				Collection c = biz.findByLoanTypeID2(LOANConstant.LoanType.TX,lOfficeID,lCurrencyID);

				if (c != null && c.size() > 0) {
					int i = 0;
					lTemp = new long[c.size()];
					Iterator it = c.iterator();
					while (it.hasNext()) {
						lTemp[i] = ((LoanTypeSettingInfo) it.next()).getId();
						i++;
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return lTemp;
		}

		public static final long[] getOtherLoanCode1() {
			long[] lTemp = null;
			LoanTypeSettingBiz biz = new LoanTypeSettingBiz();
			try {
				Collection c = biz
						.findByLoanTypeID2(LOANConstant.LoanType.OTHER);

				if (c != null && c.size() > 0) {
					int i = 0;
					lTemp = new long[c.size()];
					Iterator it = c.iterator();
					while (it.hasNext()) {
						lTemp[i] = ((LoanTypeSettingInfo) it.next()).getId();
						i++;
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return lTemp;
		}
//		2007.6.11 qhzhou
		public static final long[] getOtherLoanCode1(long lOfficeID,long lCurrencyID) {
			long[] lTemp = null;
			LoanTypeSettingBiz biz = new LoanTypeSettingBiz();
			try {
				Collection c = biz
						.findByLoanTypeID2(LOANConstant.LoanType.OTHER,lOfficeID,lCurrencyID);

				if (c != null && c.size() > 0) {
					int i = 0;
					lTemp = new long[c.size()];
					Iterator it = c.iterator();
					while (it.hasNext()) {
						lTemp[i] = ((LoanTypeSettingInfo) it.next()).getId();
						i++;
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return lTemp;
		}
		public static final long[] getConsignLoanCode() {
			long[] lTemp = null;
			LoanTypeSettingBiz biz = new LoanTypeSettingBiz();
			try {
				Collection c = biz.findByLoanTypeID(LOANConstant.LoanType.WT);

				if (c != null && c.size() > 0) {
					int i = 0;
					lTemp = new long[c.size()];
					Iterator it = c.iterator();
					while (it.hasNext()) {
						lTemp[i] = ((LoanTypeSettingInfo) it.next()).getId();
						i++;
					}
				}
			} catch (LoanException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return lTemp;
		}

		public static final long[] getDiscountLoanCode() {
			long[] lTemp = null;
			LoanTypeSettingBiz biz = new LoanTypeSettingBiz();
			try {
				Collection c = biz.findByLoanTypeID(LOANConstant.LoanType.TX);

				if (c != null && c.size() > 0) {
					int i = 0;
					lTemp = new long[c.size()];
					Iterator it = c.iterator();
					while (it.hasNext()) {
						lTemp[i] = ((LoanTypeSettingInfo) it.next()).getId();
						i++;
					}
				}
			} catch (LoanException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return lTemp;
		}

		public static final long[] getTrustLoanCode1() {
			long[] lTemp = null;
			LoanTypeSettingBiz biz = new LoanTypeSettingBiz();
			try {
				Collection c = biz.findByLoanTypeIDTrust(new long[] {
						LOANConstant.LoanType.ZY, LOANConstant.LoanType.ZGXE });

				if (c != null && c.size() > 0) {
					int i = 0;
					lTemp = new long[c.size()];
					Iterator it = c.iterator();
					while (it.hasNext()) {
						lTemp[i] = ((LoanTypeSettingInfo) it.next()).getId();
						i++;
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return lTemp;
		}
		//2007.6.11 qhzhou
		public static final long[] getTrustLoanCode1(long lOfficeID,long lCurrencyID) {
			long[] lTemp = null;
			LoanTypeSettingBiz biz = new LoanTypeSettingBiz();
			try {
				Collection c = biz.findByLoanTypeIDTrust(new long[] {
						LOANConstant.LoanType.ZY, LOANConstant.LoanType.ZGXE },lOfficeID,lCurrencyID);

				if (c != null && c.size() > 0) {
					int i = 0;
					lTemp = new long[c.size()];
					Iterator it = c.iterator();
					while (it.hasNext()) {
						lTemp[i] = ((LoanTypeSettingInfo) it.next()).getId();
						i++;
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return lTemp;
		}
		
//		2007.6.15 add by zcwang
		public static final long[] getYTLoanCode1(long lOfficeID,long lCurrencyID) {
			long[] lTemp = null;
			LoanTypeSettingBiz biz = new LoanTypeSettingBiz();
			try {
				Collection c = biz.findByLoanTypeID(new long[] {
						LOANConstant.LoanType.YT },lOfficeID,lCurrencyID);

				if (c != null && c.size() > 0) {
					int i = 0;
					lTemp = new long[c.size()];
					Iterator it = c.iterator();
					while (it.hasNext()) {
						lTemp[i] = ((LoanTypeSettingInfo) it.next()).getId();
						i++;
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return lTemp;
		}
		
		public static final long[] getTrustLoanCode() {
			long[] lTemp = null;
			LoanTypeSettingBiz biz = new LoanTypeSettingBiz();
			try {
				Collection c = biz.findByLoanTypeID(new long[] {
						LOANConstant.LoanType.ZY, LOANConstant.LoanType.ZGXE });

				if (c != null && c.size() > 0) {
					int i = 0;
					lTemp = new long[c.size()];
					Iterator it = c.iterator();
					while (it.hasNext()) {
						lTemp[i] = ((LoanTypeSettingInfo) it.next()).getId();
						i++;
					}
				}
			} catch (LoanException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return lTemp;
		}

//		2006.6.11 qhzhou
		public static final long[] getTrustConsignTXCode(long officeID,
				long currencyID) {
			LoanTypeSettingBiz biz = new LoanTypeSettingBiz();
			if (currencyID == Constant.CurrencyType.RMB) {
				long[] lTemp = null;
				try {
					Collection c = biz.findByLoanTypeID(new long[] {
							LOANConstant.LoanType.ZY, LOANConstant.LoanType.WT,
							LOANConstant.LoanType.TX },officeID,currencyID);

					if (c != null && c.size() > 0) {
						int i = 0;
						lTemp = new long[c.size()];
						Iterator it = c.iterator();
						while (it.hasNext()) {
							lTemp[i] = ((LoanTypeSettingInfo) it.next())
									.getId();
							i++;
						}
					}
				} catch (LoanException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return lTemp;
			} else {
				long[] lTemp = null;
				try {
					Collection c = biz
							.findByLoanTypeID(LOANConstant.LoanType.ZY);

					if (c != null && c.size() > 0) {
						int i = 0;
						lTemp = new long[c.size()];
						Iterator it = c.iterator();
						while (it.hasNext()) {
							lTemp[i] = ((LoanTypeSettingInfo) it.next())
									.getId();
							i++;
						}
					}
				} catch (LoanException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return lTemp;
			}
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型
		 *            （0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
				default:
					lArrayID = getAllCode();
					break;
				case (int) AccountGroupType.TRUST:
					lArrayID = getTrustLoanCode();
					break;
				case (int) AccountGroupType.CONSIGN:
					lArrayID = getConsignLoanCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				// TODO
				switch (nType) {
				case 0:
				default:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				case (int) AccountGroupType.TRUST:
					lArrayID = getTrustLoanCode();
					break;
				case (int) AccountGroupType.CONSIGN:
					lArrayID = getConsignLoanCode();
					break;
				case 890:
					lArrayID = getTrustConsignTXCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

		public static final void showList11(JspWriter out,
				String strControlName, int nType, long lSelectValue,
				boolean isNeedAll, boolean isNeedBlank, String strProperty,
				long lOfficeID, long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				// TODO
				switch (nType) {
				case 0:
				default:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				case (int) AccountGroupType.TRUST:
					//lArrayID = getTrustLoanCode1();
//					2007.6.11 qhzhou
					lArrayID = getTrustLoanCode1(lOfficeID,lCurrencyID);//区分办事处
					break;
				case (int) AccountGroupType.CONSIGN:
					//lArrayID = getConsignLoanCode1();
//					2007.6.11 qhzhou
					lArrayID = getConsignLoanCode1(lOfficeID,lCurrencyID);//区分办事处
					break;
				case (int) AccountGroupType.DISCOUNT:
//					lArrayID = getDidcountLoanCode1();
//					2006.6.11 qhzhou
					lArrayID = getDidcountLoanCode1(lOfficeID,lCurrencyID);
					break;
				case (int) AccountGroupType.OTHERLOAN:
//					lArrayID = getDidcountLoanCode1();
//					2006.6.11 qhzhou					
					lArrayID = getOtherLoanCode1(lOfficeID,lCurrencyID);
					break;
				case (int) AccountGroupType.YT:
//					lArrayID = getDidcountLoanCode1();
//					add by zcwang 2007-6-15					
					lArrayID = getYTLoanCode1(lOfficeID,lCurrencyID);
					break;
				case 890:
//					lArrayID = getDidcountLoanCode1();
//					2006.6.11 qhzhou
					lArrayID = getTrustConsignTXCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

		public static final void showListLoanType(JspWriter out,
				String strControlName, int nType, long lSelectValue,
				boolean isNeedAll, boolean isNeedBlank, String strProperty,
				long lOfficeID, long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				// TODO
				switch (nType) {
				case 0:
					lArrayID = getAllCodeLoanType();
				default:
					lArrayID = getAllCodeLoanType();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = LOANConstant.LoanType.getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

	}

	public static class InterestFeeRecordStatus {
		// 利息记录状态
		public static final long CAL = 1; // 计算

		public static final long RECORD = 3; // 记账
	}

	public static class PayNoticeSource {
		// 放款通知单修改来源
		public static final long SETTLEMENT = 1; // 结算

		public static final long LOAN = 2; // 信贷
	}

	public static class SystemStatus {
		// 系统状态
		public static final long OPEN = 1; // 开机

		public static final long CLOSE = 2; // 关机

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) OPEN:
				strReturn = "开机";
				break;
			case (int) CLOSE:
				strReturn = "关机";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { OPEN, CLOSE };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$SystemStatus",
							officeID, currencyID);
		}
	}

	/**
	 * 账户属性
	 * 
	 * @author yqwu
	 * 
	 * 
	 */
	public static class SubjectAttribute {
		public static final int ASSET = 1; // 资产类科目

		public static final int DEBT = 2; // 负债类科目

		public static final int RIGHT = 3; // 权益类科目

		public static final int INCOME = 4; // 收入类科目

		public static final int PAYOUT = 5; // 支出类科目

		public static final int TABLEOUT = 6; // 表外科目

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case ASSET:
				strReturn = "资产类科目";
				break;
			case DEBT:
				strReturn = "负债类科目";
				break;
			case RIGHT:
				strReturn = "权益类科目";
				break;
			case INCOME:
				strReturn = "收入类科目";
				break;
			case PAYOUT:
				strReturn = "支出类科目";
				break;
			case TABLEOUT:
				strReturn = "表外科目";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { ASSET, DEBT, RIGHT, INCOME, PAYOUT, TABLEOUT };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$SubjectAttribute",
							officeID, currencyID);
		}

		public static final long getDirection(long lTypeID) {
			long lDiretionID = DebitOrCredit.CREDIT;
			if (lTypeID == ASSET || lTypeID == PAYOUT || lTypeID == TABLEOUT) {
				lDiretionID = DebitOrCredit.DEBIT;
			}
			return lDiretionID;
		}
	}

	/**
	 * 
	 * 资金流向类型
	 * 
	 * @author yqwu
	 * 
	 */
	public static class CapitalType {
		public static final int IRRESPECTIVE = 0; // 无关

		public static final int INTERNAL = 1; // 内部转账

		public static final int BANK = 2; // 银行

		public static final int GENERALLEDGER = 3; // 总账类

		public static final String getName(long lCode) {
			String strReturn = "";
			switch ((int) lCode) {
			case IRRESPECTIVE:
				strReturn = "无关";
				break;
			case INTERNAL:
				strReturn = "内部转账";
				break;
			case BANK:
				strReturn = "银行";
				break;
			case GENERALLEDGER:
				strReturn = "总账类";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { IRRESPECTIVE, INTERNAL, BANK, GENERALLEDGER };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$CapitalType",
							officeID, currencyID);
		}
	}

	public static class EntryType {
		public static final int IRRESPECTIVE = 0; // 无关

		public static final int MERGER = 1; // 合并

		public static final int DIVIDE = 2; // 分拆

		public static final int RECOIL = 3; // 反冲

		public static final String getName(long lCode) {
			String strReturn = "";
			switch ((int) lCode) {
			case IRRESPECTIVE:
				strReturn = "无关";
				break;
			case MERGER:
				strReturn = "合并";
				break;
			case DIVIDE:
				strReturn = "分拆";
				break;
			case RECOIL:
				strReturn = "反冲";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { IRRESPECTIVE, MERGER, DIVIDE, RECOIL };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.settlement.util.SETTConstant$EntryType",
					officeID, currencyID);
		}
	}

	/**
	 * 科目类型
	 * 
	 * @author yqwu
	 * 
	 */
	public static class EntrySubjectType {
		public static final int SUBJECT_TYPE_1 = 1; // 收款方账户

		public static final int SUBJECT_TYPE_2 = 2; // 付款方账户

		public static final int SUBJECT_TYPE_3 = 3; // 收息账户

		public static final int SUBJECT_TYPE_4 = 4; // 付息账户

		public static final int SUBJECT_TYPE_5 = 5; // 委托收款方账户

		public static final int SUBJECT_TYPE_6 = 6; // 委托付款方账户

		public static final int SUBJECT_TYPE_7 = 7; // 收担保费账户

		public static final int SUBJECT_TYPE_8 = 8; // 付担保费账户

		public static final int SUBJECT_TYPE_9 = 9; // 付手续费账户/出票人付息账户

		public static final int SUBJECT_TYPE_10 = 10; // 开户行

		public static final int SUBJECT_TYPE_11 = 11; // 收款方账户对应的利息收入科目

		public static final int SUBJECT_TYPE_12 = 12; // 付款方账户对应的利息收入科目

		public static final int SUBJECT_TYPE_13 = 13; // 收款方账户对应的利息支出科目

		public static final int SUBJECT_TYPE_14 = 14; // 付款方账户对应的利息支出科目

		public static final int SUBJECT_TYPE_15 = 15; // 收款方账户对应的计提利息科目

		public static final int SUBJECT_TYPE_16 = 16; // 付款方账户对应的计提利息科目

		public static final int SUBJECT_TYPE_17 = 17; // 收款方账户对应的利息税费科目

		public static final int SUBJECT_TYPE_18 = 18; // 付款方账户对应的利息税费科目

		public static final int SUBJECT_TYPE_19 = 19; // 收款方账户对应的协定利息科目

		public static final int SUBJECT_TYPE_20 = 20; // 付款方账户对应的协定利息科目

		public static final int SUBJECT_TYPE_21 = 21; // 收款方账户对应的协定利息科目

		public static final int SUBJECT_TYPE_22 = 22; // 收付款方账户对应的协定利息科目

		public static final int SUBJECT_TYPE_23 = 23; // 总账类

		public static final int SUBJECT_TYPE_24 = 24; // 融资租赁保证金收款方账户

		public static final int SUBJECT_TYPE_25 = 25; // 融资租赁保证金付款方账户
		
		public static final int SUBJECT_TYPE_26 = 26; //银团贷款参与行

		public static final int SUBJECT_TYPE_99 = 99; // 自设

		public static final String getName(long lCode) {
			String strReturn = "";
			switch ((int) lCode) {
			case SUBJECT_TYPE_1:
				strReturn = "收款方账户";
				break;
			case SUBJECT_TYPE_2:
				strReturn = "付款方账户";
				break;
			case SUBJECT_TYPE_3:
				strReturn = "收息账户";
				break;
			case SUBJECT_TYPE_4:
				strReturn = "付息账户";
				break;
			case SUBJECT_TYPE_5:
				strReturn = "委托收款方账户";
				break;
			case SUBJECT_TYPE_6:
				strReturn = "委托付款方账户";
				break;
			case SUBJECT_TYPE_7:
				strReturn = "收担保费账户";
				break;
			case SUBJECT_TYPE_8:
				strReturn = "付担保费账户";
				break;
			case SUBJECT_TYPE_9:
				strReturn = "付手续费账户/出票人付息账户";
				break;
			case SUBJECT_TYPE_10:
				strReturn = "开户行";
				break;
			case SUBJECT_TYPE_11:
				strReturn = "收款方账户对应的利息收入科目";
				break;
			case SUBJECT_TYPE_12:
				strReturn = "付款方账户对应的利息收入科目";
				break;
			case SUBJECT_TYPE_13:
				strReturn = "收款方账户对应的利息支出科目";
				break;
			case SUBJECT_TYPE_14:
				strReturn = "付款方账户对应的利息支出科目";
				break;
			case SUBJECT_TYPE_15:
				strReturn = "收款方账户对应的计提利息科目";
				break;
			case SUBJECT_TYPE_16:
				strReturn = "付款方账户对应的计提利息科目";
				break;
			case SUBJECT_TYPE_17:
				strReturn = "收款方账户对应的利息税费科目";
				break;
			case SUBJECT_TYPE_18:
				strReturn = "付款方账户对应的利息税费科目";
				break;
			case SUBJECT_TYPE_19:
				strReturn = "收款方账户对应的协定利息科目";
				break;
			case SUBJECT_TYPE_20:
				strReturn = "付款方账户对应的协定利息科目";
				break;
			case SUBJECT_TYPE_21:
				strReturn = "收款方账户对应的手续费科目";
				break;
			case SUBJECT_TYPE_22:
				strReturn = "付款方账户对应的手续费科目";
				break;
			case SUBJECT_TYPE_23:
				strReturn = "总账类";
				break;
			case SUBJECT_TYPE_24:
				strReturn = "融资租赁保证金收款方账户";
				break;
			case SUBJECT_TYPE_25:
				strReturn = "融资租赁保证金付款方账户";
				break;
			case SUBJECT_TYPE_26:
				strReturn = "银团贷款参与行";
				break;
			case SUBJECT_TYPE_99:
				strReturn = "自设";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { SUBJECT_TYPE_1, SUBJECT_TYPE_2, SUBJECT_TYPE_3,
					SUBJECT_TYPE_4, SUBJECT_TYPE_5, SUBJECT_TYPE_6,
					SUBJECT_TYPE_7, SUBJECT_TYPE_8, SUBJECT_TYPE_9,
					SUBJECT_TYPE_10, SUBJECT_TYPE_11, SUBJECT_TYPE_12,
					SUBJECT_TYPE_13, SUBJECT_TYPE_14, SUBJECT_TYPE_15,
					SUBJECT_TYPE_16, SUBJECT_TYPE_17, SUBJECT_TYPE_18,
					SUBJECT_TYPE_19, SUBJECT_TYPE_20, SUBJECT_TYPE_21,
					SUBJECT_TYPE_22, SUBJECT_TYPE_23, SUBJECT_TYPE_24,
					SUBJECT_TYPE_25, SUBJECT_TYPE_26, SUBJECT_TYPE_99 };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$EntrySubjectType",
							officeID, currencyID);
		}
	}

	/**
	 * 金额方向
	 * 
	 * @author yqwu
	 * 
	 */
	public static class AmountDirection {
		public static final int BLUE = 1; // 蓝字

		public static final int RED = 2; // 红字

		public static final String getName(long lCode) {
			String strReturn = "";
			switch ((int) lCode) {
			case BLUE:
				strReturn = "蓝字";
				break;
			case RED:
				strReturn = "红字";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { BLUE, RED };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$AmountDirection",
							officeID, currencyID);
		}
	}

	/**
	 * 电厂账户类型
	 * 
	 * @author xrli
	 * 
	 * To change the template for this generated type comment go to
	 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
	 */
	public static class FilialeBankAccountType {
		public static final long PAYIN = 1; // 收入户

		public static final long PAYOUT = 2; // 支出户

		public static final long PAYINANDOUT = 3; // 收支户

		/**
		 * 得到所有的电厂账户类型
		 * 
		 * @return long[]
		 */
		public static final long[] getAllCode() {
			long[] lTemp = { PAYIN, PAYOUT, PAYINANDOUT };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$FilialeBankAccountType",
							officeID, currencyID);
		}

		/**
		 * 显示中文状态的方法
		 * 
		 * @param lCode
		 * @return String
		 */
		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) PAYIN:
				strReturn = "收入户";
				break;
			case (int) PAYOUT:
				strReturn = "支出户";
				break;
			case (int) PAYINANDOUT:
				strReturn = "收支户";
				break;
			}
			return strReturn;
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型（0，显示全部）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

	}

	/**
	 * 
	 * 客户企业性质
	 * 
	 * @author yqwu
	 * 
	 */
	public static class EnterpriseNature {
		// 华能使用
		public static final long MARKET = 1; // 上市

		public static final long OUTOFMARKET = 2; // 非上市

		public static final long OTHER = 3; // 其他

		// 大桥使用
		public static final long OPERATIONUNIT = 11; // 经营单位

		public static final long OUTLAYUNIT = 12; // 经费单位

		public static final String getName(long lCode) {
			String strReturn = "";
			switch ((int) lCode) {
			case (int) MARKET:
				strReturn = "上市";
				break;
			case (int) OUTOFMARKET:
				strReturn = "非上市";
				break;
			case (int) OTHER:
				strReturn = "其他";
				break;
			case (int) OPERATIONUNIT:
				strReturn = "经营单位";
				break;
			case (int) OUTLAYUNIT:
				strReturn = "经费单位";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lReturn;
			long[] lTemp = { MARKET, OUTOFMARKET, OTHER };
			lReturn = lTemp;

			return lReturn;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$EnterpriseNature",
							officeID, currencyID);
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型（0，显示全部）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}
	}

	/**
	 * 
	 * 金额类型
	 * 
	 * @author yqwu
	 * 
	 */
	public static class AmountType {
		public static final int AmountType_1 = 1; // 本金/交易金额

		public static final int AmountType_2 = 2; // 利息合计

		public static final int AmountType_3 = 3; // 计提利息

		public static final int AmountType_4 = 4; // 未计提利息

		public static final int AmountType_5 = 5; // 提前/逾期利息

		public static final int AmountType_6 = 6; // 罚息

		public static final int AmountType_7 = 7; // 复利

		public static final int AmountType_8 = 8; // 担保费/贴现人承担利息

		public static final int AmountType_9 = 9; // 手续费/出票人承担利息

		public static final int AmountType_10 = 10; // 利息税费

		public static final int AmountType_11 = 11; // 本息合计

		public static final int AmountType_12 = 12; // 豁免利息

		public static final int AmountType_13 = 13; // 实收利息/税后利息

		public static final int AmountType_14 = 14; // 豁免本金

		public static final int AmountType_15 = 15; // 融资租赁保证金本息合计

		public static final String getName(long lCode) {
			String strReturn = "";
			switch ((int) lCode) {
			case AmountType_1:
				strReturn = "本金/交易金额";
				break;
			case AmountType_2:
				strReturn = "利息合计";
				break;
			case AmountType_3:
				strReturn = "计提利息";
				break;
			case AmountType_4:
				strReturn = "未计提利息";
				break;
			case AmountType_5:
				strReturn = "提前/逾期利息";
				break;
			case AmountType_6:
				strReturn = "罚息";
				break;
			case AmountType_7:
				strReturn = "复利";
				break;
			case AmountType_8:
				strReturn = "担保费/贴现人承担利息";
				break;
			case AmountType_9:
				strReturn = "手续费/出票人承担利息";
				break;
			case AmountType_10:
				strReturn = "利息税费";
				break;
			case AmountType_11:
				strReturn = "本息合计";
				break;
			case AmountType_12:
				strReturn = "豁免/冲销计提利息/正常利息";
				break;
			case AmountType_13:
				strReturn = "实收利息/税后利息/协定利息";
				break;
			case AmountType_14:
				strReturn = "豁免本金";
				break;
			case AmountType_15: // add by feiye 2006.5.26
				strReturn = "融资租赁保证金本息合计";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { AmountType_1, AmountType_2, AmountType_3,
					AmountType_4, AmountType_5, AmountType_6, AmountType_7,
					AmountType_8, AmountType_9, AmountType_10, AmountType_11,
					AmountType_12, AmountType_13, AmountType_14, AmountType_15 };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$AmountType",
							officeID, currencyID);
		}

	}

	/**
	 * 
	 * 会计分录状态
	 * 
	 * @author yqwu
	 * 
	 */
	public static class EntryStatus {
		public static final int DELETED = 0; // 已删除

		public static final int CHECKED = 3; // 已复核
		
		public static final int MERGED = 8; //  已合并

		public static final String getName(long lCode) {
			String strReturn = "";
			switch ((int) lCode) {
			case DELETED:
				strReturn = "已删除";
				break;
			case CHECKED:
				strReturn = "已复核";
				break;
			case MERGED:
				strReturn = "已合并";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { DELETED, CHECKED, MERGED };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$EntryStatus",
							officeID, currencyID);
		}
	}

	/**
	 * 对账状态，2005-09-16增加
	 * 
	 * @author qijiang
	 * 
	 */
	public static class CheckAccountBookStatus {
		public static final long UNCHECKED = 1; // 结算记账数据未达项

		public static final long CHECKED = 2; // 结算记账数据已达项

		public static final long CLEARED = 3; // 结算记账数据清除项

		public static final String getName(long lCode) {
			String strReturn = "";
			switch ((int) lCode) {
			case (int) UNCHECKED:
				strReturn = "未达项";
				break;
			case (int) CHECKED:
				strReturn = "已达项";
				break;
			case (int) CLEARED:
				strReturn = "清除项";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { UNCHECKED, CHECKED, CLEARED };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$CheckAccountBookStatus",
							officeID, currencyID);
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型
		 *            （0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

	}

	/**
	 * 收付方向
	 * 
	 * @author yqwu
	 * 
	 */
	public static class ReceiveOrPay {
		public static final long RECEIVE = 8; // 收

		public static final long PAY = 9; // 付

		public static final String getName(long lCode) {
			String strReturn = "";
			switch ((int) lCode) {
			case (int) RECEIVE:
				strReturn = "收";
				break;
			case (int) PAY:
				strReturn = "付";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { RECEIVE, PAY };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$ReceiveOrPay",
							officeID, currencyID);
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型
		 *            （0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				case 1:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];

				for (int i = 0; i < lArrayID.length; i++) {
					if (nType == 0) {
						strArrayName[i] = getName(lArrayID[i]);
					} else if (nType == 1) {
						if (lArrayID[i] == ReceiveOrPay.RECEIVE)
							strArrayName[i] = "贷";
						else if (lArrayID[i] == ReceiveOrPay.PAY)
							strArrayName[i] = "借";
					}
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

	}

	/**
	 * 多笔贷款类型
	 * 
	 * @author yychen
	 * 
	 */
	public static class MultiLoanType {
		public static final long PAYMENT = 1; // 付款

		public static final long TRUSTLOAN = 2; // 自营贷款

		public static final long CONSIGNLOAN = 3; // 委托贷款

		public static final String getName(long lCode) {
			String strReturn = "";
			switch ((int) lCode) {
			case (int) PAYMENT:
				strReturn = "付款";
				break;
			case (int) TRUSTLOAN:
				strReturn = "自营贷款收回";
				break;
			case (int) CONSIGNLOAN:
				strReturn = "委托贷款收回";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { PAYMENT, TRUSTLOAN, CONSIGNLOAN };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$MultiLoanType",
							officeID, currencyID);
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型
		 *            （0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

	}

	/**
	 * 本金/利息处理办法
	 * 
	 * @author yqwu
	 * 
	 */
	public static class CapitalAndInterestDealWay {
		public static final long SUM_DEAL = 1; // 汇总处理

		public static final long DIVIDE_DEAL = 2; // 分笔处理

		public static final String getName(long lCode) {
			String strReturn = "";
			switch ((int) lCode) {
			case (int) SUM_DEAL:
				strReturn = "汇总处理";
				break;
			case (int) DIVIDE_DEAL:
				strReturn = "分笔处理";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { SUM_DEAL, DIVIDE_DEAL };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$CapitalAndInterestDealWay",
							officeID, currencyID);
		}
	}

	/**
	 * 
	 * 金额控制方向
	 * 
	 * @author yqwu
	 * 
	 */
	public static class ControlDirection {
		public static final int DEBIT = 1; // 借方

		public static final int CREDIT = 2; // 贷方

		public static final int DOUBLE = 9; // 借贷双向

		public static final String getName(long lCode) {
			String strReturn = "";
			switch ((int) lCode) {
			case DEBIT:
				strReturn = "借方";
				break;
			case CREDIT:
				strReturn = "贷方";
				break;
			case DOUBLE:
				strReturn = "借贷双向";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { DEBIT, CREDIT, DOUBLE };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$ControlDirection",
							officeID, currencyID);
		}
	}

	/**
	 * 
	 * 页面操作定义
	 * 
	 * @author rongyang
	 */
	public static class Actions {
		public static final int CREATETEMPSAVE = 1; // 新增临时保存

		public static final int MODIFYTEMPSAVE = 2; // 修改临时保存

		public static final int CREATESAVE = 3; // 创建保存

		public static final int MODIFYSAVE = 4; // 修改保存

		public static final int DELETE = 5; // 删除

		public static final int LINKSEARCH = 6; // 链接查找

		public static final int MATCHSEARCH = 7; // 匹配查找

		public static final int CHECK = 8; // 复核

		public static final int CANCELCHECK = 9; // 取消复核

		public static final int NEXTSTEP = 10; // 下一步

		public static final int TODETAIL = 11; // 点交易号到详细页面

		public static final int SQUAREUP = 12; // 勾账

		public static final int CANCELSQUAREUP = 13; // 取消勾账

		public static final int MODIFYNEXTSTEP = 14; // 更改下一步

		public static final int SEND = 15; // 发送

		public static final int UPSTEP = 22;// 上一步

		public static final int UPLOAD = 23;// 上传

		public static final int DOWNLOAD = 24;// 下载
		
		public static final int INITAPPROVAL = 25;// 提交审核
		
		public static final int SAVEANDINITAPPROVAL = 26; // 保存并提交审批
		
		public static final int DOAPPRVOAL = 27; // 审核
		
		public static final int APPMATCHSEARCH = 28; // 匹配查找
		
		public static final int CANCELAPPROVAL = 29; //取消审批

		public static final String getName(long lCode) {
			String strReturn = "";
			switch ((int) lCode) {
			case CREATETEMPSAVE:
				strReturn = "临时保存";
				break;
			case MODIFYTEMPSAVE:
				strReturn = "临时保存";
				break;
			case CREATESAVE:
				strReturn = "创建保存";
				break;
			case MODIFYSAVE:
				strReturn = "修改保存";
				break;
			case DELETE:
				strReturn = "删除";
				break;
			case LINKSEARCH:
				strReturn = "链接查找";
				break;
			case MATCHSEARCH:
				strReturn = "匹配查找";
				break;
			case CHECK:
				strReturn = "复核";
				break;
			case CANCELCHECK:
				strReturn = "取消复核";
				break;
			case UPSTEP:
				strReturn = "上一步";
				break;
			case UPLOAD:
				strReturn = "上传";
				break;
			case DOWNLOAD:
				strReturn = "下载";
				break;
			case INITAPPROVAL:
				strReturn = "提交审批";
				break;
			case SAVEANDINITAPPROVAL:
				strReturn = "保存并提交审批";
				break;				
			case DOAPPRVOAL:
				strReturn = "审批";
				break;
			case APPMATCHSEARCH:
				strReturn = "审批查找";
				break;
			case CANCELAPPROVAL:
				strReturn = "取消审批";
				break;
			}
			return strReturn;
		}
	}

	public static class PreSaveResult {
		// 交易预保存的处理结果
		public static final long NORMAL = 0; // 正常

		public static final long REPEATED = 3; // 重复交易
	}

	public static class BalanceType {
		public static final int UPPER = 1; // 以上

		public static final int LOWER = 2; // 以下

		public static final String getName(long lCode) {
			String strReturn = "";
			switch ((int) lCode) {
			case UPPER:
				strReturn = "以上";
				break;
			case LOWER:
				strReturn = "以下";
				break;
			}
			return strReturn;
		}
	}

	/**
	 * 合同年期
	 * 
	 * @author wlming
	 * 
	 * To change the template for this generated type comment go to
	 * Window>Preferences>Java>Code Generation>Code and Comments
	 */
	public static class ContractYearTerm {
		/**
		 * 得到所有的合同年期
		 * 
		 * @return long[]
		 */
		public static final long[] getAllYear() {
			long[] lTemp = { 1996, 1997, 1998, 1999, 2000, 2001, 2002, 2003,
					2004, 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012, 2013,
					2014, 2015 };
			return lTemp;
		}

		/*
		 * public static final long[] getAllCode(long officeID, long currencyID) {
		 * return Constant.getAllCode(
		 * "com.iss.itreasury.settlement.util.SETTConstant$ContractYearTerm",
		 * officeID, currencyID); }
		 */

		/**
		 * 显示合同年期的方法
		 * 
		 * @param lCode
		 * @return String
		 */
		public static final String getName(long lCode) {
			String strReturn = String.valueOf(lCode);
			return strReturn;
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型（0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllYear();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllYear();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

	}

	/**
	 * 多笔贷款类型
	 * 
	 * @author yychen
	 * 
	 */
	public static class DepositLoanType {
		public static final long TRUSTDEPOSIT = 1; // 信托存款

		public static final long CONSIGNDEPOSIT = 2; // 委托存款

		public static final long TRUSTLOAN = 3; // 自营贷款

		public static final long CONSIGNLOAN = 4; // 委托贷款

		public static final long OTHERDEPOSIT = 5; // 其它存款

		public static final long OTHERLOAN = 6; // 其它贷款

		public static final String getName(long lCode) {
			String strReturn = "";
			switch ((int) lCode) {
			case (int) TRUSTDEPOSIT:
				strReturn = "信托存款";
				break;
			case (int) CONSIGNDEPOSIT:
				strReturn = "委托存款";
				break;
			case (int) TRUSTLOAN:
				strReturn = "自营贷款";
				break;
			case (int) CONSIGNLOAN:
				strReturn = "委托贷款";
				break;
			case (int) OTHERDEPOSIT:
				strReturn = "其它存款";
				break;
			case (int) OTHERLOAN:
				strReturn = "其它贷款";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { TRUSTDEPOSIT, CONSIGNDEPOSIT, TRUSTLOAN,
					CONSIGNLOAN, OTHERDEPOSIT, OTHERLOAN };
			return lTemp;
		}

		// 中交加入，保留下拉列表框中的内部资金占用，其他存款，其他贷款 ，全部
		public static final long[] getAllCodeForZJ() {
			long[] lTemp = { TRUSTLOAN, OTHERDEPOSIT, OTHERLOAN };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$DepositLoanType",
							officeID, currencyID);
		}

		/*
		 * 中交添加 modify by hyshd
		 * 
		 * public static final long[] getAllCodeForZJ() { long[] lTemp =
		 * {TRUSTLOAN,OTHERDEPOSIT,OTHERLOAN}; return lTemp; }
		 */

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型
		 *            （0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				case 1:// 中交加入，下拉列表中只保留内部资金占用，其他存款，其他贷款，全部
					lArrayID = getAllCodeForZJ();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

	}

	/**
	 * 
	 * 交易子类型
	 * 
	 * 
	 */
	public static class SubTransactionType {
		public static final long IRRESPECTIVE = 0; // 无关

		public static final long NORMAL = 1; // 正常

		public static final long CNACEL = 2; // 退票/后补/取消

		public static final long PAYMENT = 3; // 付款

		public static final long RECEIEVE = 4; // 收款

		public static final long TRUSTRECEIEVE = 5; // 自营收回

		public static final long CONSIGNRECEIEVE = 6; // 委贷收回

		public static final long INTERESTTAX = 7; // 利息税费

		public static final String getName(long lCode) {
			String strReturn = "";
			switch ((int) lCode) {
			case (int) IRRESPECTIVE:
				strReturn = "无关";
				break;
			case (int) NORMAL:
				strReturn = "正常";
				break;
			case (int) CNACEL:
				strReturn = "退票/后补/取消";
				break;
			case (int) PAYMENT:
				strReturn = "付款";
				break;
			case (int) RECEIEVE:
				strReturn = "收款";
				break;
			case (int) TRUSTRECEIEVE:
				strReturn = "自营收回";
				break;
			case (int) CONSIGNRECEIEVE:
				strReturn = "委贷收回";
				break;
			case (int) INTERESTTAX:
				strReturn = "利息税费";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { IRRESPECTIVE, NORMAL, CNACEL, PAYMENT, RECEIEVE,
					TRUSTRECEIEVE, CONSIGNRECEIEVE, INTERESTTAX };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$SubTransactionType",
							officeID, currencyID);
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型
		 *            （0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

	}

	/**
	 * 多笔贷款类型
	 * 
	 * @author yychen
	 * 
	 */
	public static class BankType {
		public static final long BC = 1; // 中国银行

		public static final long ICBC = 2; // 中国工商银行

		public static final long CCB = 3; // 中国建设银行

		public static final long ABOCN = 4; // 中国农业银行

		public static final long CMBC = 5; // 中国民生银行

		public static final long CMBCHINA = 6; // 招商银行

		public static final long BANKCOMM = 7; // 交通银行

		public static final long SDB = 8; // 深圳发展银行

		public static final long CITICIB = 9; // 中信实业银行

		public static final long CEBBANK = 10; // 中国光大银行

		public static final long SPDB = 11; // 上海浦东发展银行

		public static final long FIB = 12; // 兴业银行

		public static final long GDB = 13; // 广东发展银行

		public static final String getName(long lCode) {
			String strReturn = "";
			switch ((int) lCode) {
			case (int) BC:
				strReturn = "中国银行";
				break;
			case (int) ICBC:
				strReturn = "中国工商银行";
				break;
			case (int) CCB:
				strReturn = "中国建设银行";
				break;
			case (int) ABOCN:
				strReturn = "中国农业银行";
				break;
			case (int) CMBC:
				strReturn = "中国民生银行";
				break;
			case (int) CMBCHINA:
				strReturn = "招商银行";
				break;
			case (int) BANKCOMM:
				strReturn = "交通银行";
				break;
			case (int) SDB:
				strReturn = "深圳发展银行";
				break;
			case (int) CITICIB:
				strReturn = "中信实业银行";
				break;
			case (int) CEBBANK:
				strReturn = "中国光大银行";
				break;
			case (int) SPDB:
				strReturn = "上海浦东发展银行";
				break;
			case (int) FIB:
				strReturn = "兴业银行";
				break;
			case (int) GDB:
				strReturn = "广东发展银行";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { BC, ICBC, CCB, ABOCN, CMBC, CMBCHINA, BANKCOMM,
					SDB, CITICIB, CEBBANK, SPDB, FIB, GDB };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.settlement.util.SETTConstant$BankType",
					officeID, currencyID);
		}

		public static final String getIndicationCode(long lCode) {
			String strReturn = "";
			switch ((int) lCode) {
			case (int) BC:
				strReturn = "BC";
				break;
			case (int) ICBC:
				strReturn = "ICBC";
				break;
			case (int) CCB:
				strReturn = "CCB";
				break;
			case (int) ABOCN:
				strReturn = "ABOCN";
				break;
			case (int) CMBC:
				strReturn = "CMBC";
				break;
			case (int) CMBCHINA:
				strReturn = "CMBCHINA";
				break;
			case (int) BANKCOMM:
				strReturn = "BANKCOMM";
				break;
			case (int) SDB:
				strReturn = "SDB";
				break;
			case (int) CITICIB:
				strReturn = "CITICIB";
				break;
			case (int) CEBBANK:
				strReturn = "CEBBANK";
				break;
			case (int) SPDB:
				strReturn = "SPDB";
				break;
			case (int) FIB:
				strReturn = "FIB";
				break;
			case (int) GDB:
				strReturn = "GDB";
				break;

			}
			return strReturn;
		}

		/**
		 * 判断给定的银行名称（String）是否是指定的银行类型（long）
		 * 
		 * @param bankType
		 *            银行类型，若没有指定的银行类型，返回false
		 * @param bankName
		 *            银行名称，可以为银行简称，如“建行”、“工行”等
		 * @return
		 */
		/*
		 * public static final boolean isSameBank(long bankType, String
		 * bankName) { boolean isSameBank = false; try { isSameBank =
		 * BranchIdentify.isSameBankName(BankType .getName(bankType), bankName); }
		 * catch (Exception e) { System.out.println("判断是否同行出错");
		 * e.printStackTrace(); } return isSameBank; }
		 */
		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型
		 *            （0，显示全部；1,显示当前已实现接口的银行）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				case 1:
					lArrayID = new long[] { ICBC, BC };
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}
		/*
		 * public static final void showList(JspWriter out, String
		 * strControlName, int nType, long lSelectValue, boolean isNeedAll,
		 * boolean isNeedBlank, String strProperty,long lOfficeID,long
		 * lCurrencyID) { long[] lArrayID = null; String[] strArrayName = null;
		 * try { switch (nType) { case 0 : lArrayID =
		 * getAllCode(lOfficeID,lCurrencyID); break; case 1 : //lArrayID = new
		 * long[]{ICBC}; lArrayID = BankServiceDelegation
		 * .getAllAvailableBankType(lOfficeID,lCurrencyID); break; }
		 * strArrayName = new String[lArrayID.length]; for (int i = 0; i <
		 * lArrayID.length; i++) { strArrayName[i] = getName(lArrayID[i]); }
		 * showCommonList(out, strControlName, lArrayID, strArrayName,
		 * lSelectValue, isNeedAll, strProperty, isNeedBlank); } catch
		 * (Exception ex) { Log.print(ex.toString()); } }
		 */

	}

	/**
	 * 银行指令状态
	 * 
	 * @author yychen
	 * 
	 */
	public static class BankInstructionStatus {
		public static final long DELETED = 10; // 已删除

		public static final long SAVED = 20; // 已保存,未发送（银行付款）

		public static final long SUBMITTING = 30; // 已发送,待确认（银行付款）

		public static final long SUBMITTED = 40; // 已确认（银行付款）

		public static final long FAILED = 50; // 已失败

		public static final long UNKNOWENED = 60; // 可疑

		// public static final long RECEIEVING = 4; //已收到,未处理（银行收款）
		// public static final long RECEIEVED = 5; //已收到,已处理（银行收款）
		public static final String getName(long lCode) {
			String strReturn = "";
			switch ((int) lCode) {
			case (int) DELETED:
				strReturn = "已撤销";
				break;
			case (int) SAVED:
				strReturn = "已保存,未发送";
				break;
			case (int) SUBMITTING:
				strReturn = "已发送,待处理";
				break;
			case (int) SUBMITTED:
				strReturn = "已成功";
				break;
			// case (int) RECEIEVING :
			// strReturn = "已收到,未处理";
			// break;
			// case (int) RECEIEVED :
			// strReturn = "已收到,已处理";
			// break;
			case (int) FAILED:
				strReturn = "已失败";
				break;
			case (int) UNKNOWENED:
				strReturn = "可疑";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { DELETED, SAVED, SUBMITTING, SUBMITTED, FAILED,
					UNKNOWENED };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$BankInstructionStatus",
							officeID, currencyID);
		}

		/**
		 * 将银行服务包中得转账指令状态对象转换为结算系统定义得常量
		 * 
		 * @param d
		 * @return long
		 */
		/*
		 * public static final long
		 * convertFromConstantObjectOfBS(com.iss.itreasury.bs.constant.TransactionStatus
		 * status) { long result = -1; if
		 * (com.iss.itreasury.bs.constant.TransactionStatus.FAILED.equals(status)) {
		 * result = SETTConstant.BankInstructionStatus.FAILED; } else if
		 * (com.iss.itreasury.bs.constant.TransactionStatus.SUCCEED.equals(status)) {
		 * result = SETTConstant.BankInstructionStatus.SUBMITTED; } else if
		 * (com.iss.itreasury.bs.constant.TransactionStatus.PROCESSING.equals(status)) {
		 * result = SETTConstant.BankInstructionStatus.SUBMITTING; } else if
		 * (com.iss.itreasury.bs.constant.TransactionStatus.UNKNOWENED.equals(status)) {
		 * result = SETTConstant.BankInstructionStatus.UNKNOWENED; } return
		 * result; }
		 */
		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型（0，显示全部；1:显示”已保存,未发送“和“已失败”）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				case 1:
					lArrayID = new long[] { SAVED, FAILED };
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				case 1:
					lArrayID = new long[] { SAVED, FAILED };
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

	}

	/**
	 * 银行付款类型
	 * 
	 * @author yychen
	 * 
	 */
	public static class BankPaymentType {
		public static final long NOTSUPPORT = 1; // 不支持

		public static final long BATCH = 2; // 批量支付

		public static final long SINGLE = 3; // 实时支付

		public static final String getName(long lCode) {
			String strReturn = "";
			switch ((int) lCode) {
			case (int) NOTSUPPORT:
				strReturn = "不支持";
				break;
			case (int) BATCH:
				strReturn = "批量支付";
				break;
			case (int) SINGLE:
				strReturn = "实时支付";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { NOTSUPPORT, BATCH, SINGLE };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$BankPaymentType",
							officeID, currencyID);
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型
		 *            （0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

	}

	/**
	 * 导入银行数据的执行结果
	 * 
	 * @author rongyang
	 */
	public static final class ImportBankDataExecuteStatus {
		public static final long SUCCESS = 1;

		public static final long FAIL = 2;

		public static final long NOTHING = 3;

		public static final long HALFSUCCESS = 4;

		/**
		 * 用代码值得到代码名称
		 * 
		 * @param lCodeType
		 *            代码类型
		 * @param lCode
		 *            代码
		 */
		public static final String getName(long lCode) {
			String strReturn = "";
			switch ((int) lCode) {
			case (int) SUCCESS:
				strReturn = "成功";
				break;
			case (int) FAIL:
				strReturn = "失败";
				break;
			case (int) NOTHING:
				strReturn = "未导入";
				break;
			case (int) HALFSUCCESS:
				strReturn = "交易导入成功，无余额";
				break;
			}
			return strReturn;
		}
	}

	/**
	 * 部分客户ID
	 * 
	 * @author zqhu 时间：2004-04-15
	 */
	// TODO TODELETE
	public static class PartClientCode {
		public static final String CODE0000 = "01-0000"; // 01-0000

		public static final String CODE0001 = "01-0001"; // 01-0001

		public static final String CODE0002 = "01-0002"; // 01-0002

		public static final String CODE0003 = "01-0003"; // 01-0003

		public static final String CODE0006 = "01-0006"; // 01-0006

		public static final String CODE0007 = "01-0007"; // 01-0007

		public static final String CODE0011 = "01-0011"; // 01-0011

		public static final String CODE0015 = "01-0015"; // 01-0015

		public static final String CODE0017 = "01-0017"; // 01-0017

		public static final String CODE0020 = "01-0020"; // 01-0020

		public static final String CODE0021 = "01-0021"; // 01-0021

		public static final String CODE0025 = "01-0025"; // 01-0025

		public static final String CODE0188 = "01-0188"; // 01-0188

		public static final String CODE0195 = "01-0195"; // 01-0195

		public static final String[] getAllCode() {
			String[] lTemp = { CODE0001, CODE0021, CODE0002, CODE0020,
					CODE0003, CODE0188, CODE0006, CODE0015, CODE0017, CODE0011,
					CODE0007, CODE0025, CODE0195 };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$PartClientCode",
							officeID, currencyID);
		}
	}

	/**
	 * 报表类型
	 * 
	 * @author zqhu 时间：2004-04-26
	 */
	public static class ReportType {
		public static final long ReportType001 = 1; // 股份公司每日资金变动情况表

		public static final long ReportType002 = 2; // 股份公司代管电厂每日资金变动情况表

		public static final long ReportType003 = 3; // 华能集团公司及控股电厂存款每日变动情况表

		public static final String getName(long lCode) {
			String strReturn = "";
			switch ((int) lCode) {
			case (int) ReportType001:
				strReturn = "股份公司每日资金变动情况表";
				break;
			case (int) ReportType002:
				strReturn = "股份公司代管电厂每日资金变动情况表";
				break;
			case (int) ReportType003:
				strReturn = "华能集团公司及下属电厂存款每日变动情况表";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { ReportType001, ReportType002, ReportType003 };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$ReportType",
							officeID, currencyID);
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				long lSelectValue, boolean isNeedAll, boolean isNeedBlank,
				String strProperty) {
			String[] strArrayName = null;
			try {
				long[] lArrayID = getAllCode();
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				long lSelectValue, boolean isNeedAll, boolean isNeedBlank,
				String strProperty, long lOfficeID, long lCurrencyID) {
			String[] strArrayName = null;
			try {
				long[] lArrayID = getAllCode(lOfficeID, lCurrencyID);
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

		/**
		 * 股份公司每日资金变动情况表
		 * 
		 * @author zqhu
		 */
		public static class TotalType001 {
			public static final long TotalType001 = 1; // 全资电厂小计

			public static final long TotalType002 = 2; // 控股电厂小计

			public static final String getName(long lCode) {
				String strReturn = "";
				switch ((int) lCode) {
				case (int) TotalType001:
					strReturn = "全资电厂小计";
					break;
				case (int) TotalType002:
					strReturn = "控股电厂小计";
					break;
				}
				return strReturn;
			}
		}

		/**
		 * 股份公司代管电厂每日资金变动情况表
		 * 
		 * @author zqhu
		 */
		public static class TotalType002 {
			public static final long TotalType001 = 1; // 集团控股电厂合计

			public static final long TotalType002 = 2; // 开发控股电厂合计

			public static final String getName(long lCode) {
				String strReturn = "";
				switch ((int) lCode) {
				case (int) TotalType001:
					strReturn = "集团控股电厂合计";
					break;
				case (int) TotalType002:
					strReturn = "开发控股电厂合计";
					break;
				}
				return strReturn;
			}
		}

		/**
		 * 华能集团公司及控股电厂存款每日变动情况表
		 * 
		 * @author zqhu
		 */
		public static class TotalType003 {
			public static final long TotalType001 = 1; // 集团全资电厂小计

			public static final long TotalType002 = 2; // 开发全资电厂小计

			public static final long TotalType003 = 3; // 集团控股电厂小计

			public static final long TotalType004 = 4; // 开发控股电厂小计

			public static final String getName(long lCode) {
				String strReturn = "";
				switch ((int) lCode) {
				case (int) TotalType001:
					strReturn = "集团全资电厂小计";
					break;
				case (int) TotalType002:
					strReturn = "开发全资电厂小计";
					break;
				case (int) TotalType003:
					strReturn = "集团控股电厂小计";
					break;
				case (int) TotalType004:
					strReturn = "开发控股电厂小计";
					break;
				}
				return strReturn;
			}
		}
	}

	// 合同状态
	public static class SettContractStatus {
		// 合同状态
		public static final long SAVE = 1; // 撰写

		public static final long SUBMIT = 2; // 已提交

		public static final long CHECK = 3; // 已审核

		public static final long NOTACTIVE = 4; // 未执行

		public static final long ACTIVE = 5; // 执行中

		public static final long EXTEND = 6; // 已展期

		public static final long OVERDUE = 7; // 已逾期

		public static final long DELAYDEBT = 8; // 呆滞

		public static final long BADDEBT = 9; // 呆账

		public static final long FINISH = 10; // 已结束

		public static final long CANCEL = 11; // 已取消

		public static final long REFUSE = 12; // 已拒绝

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) SAVE:
				strReturn = "撰写";
				break;
			case (int) SUBMIT:
				strReturn = "已提交";
				break;
			case (int) CHECK:
				strReturn = "已审核";
				break;
			case (int) NOTACTIVE:
				strReturn = "未执行";
				break;
			case (int) ACTIVE:
				strReturn = "执行中";
				break;
			case (int) EXTEND:
				strReturn = "已展期";
				break;
			case (int) OVERDUE:
				strReturn = "已逾期";
				break;
			case (int) DELAYDEBT:
				strReturn = "呆滞";
				break;
			case (int) BADDEBT:
				strReturn = "呆账";
				break;
			case (int) FINISH:
				strReturn = "已结束";
				break;
			case (int) CANCEL:
				strReturn = "已取消";
				break;
			case (int) REFUSE:
				strReturn = "已拒绝";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = null;
			lTemp = new long[12];
			lTemp[0] = SAVE;
			lTemp[1] = SUBMIT;
			lTemp[2] = CHECK;
			lTemp[3] = NOTACTIVE;
			lTemp[4] = ACTIVE;
			lTemp[5] = EXTEND;
			lTemp[6] = OVERDUE;
			lTemp[7] = DELAYDEBT;
			lTemp[8] = BADDEBT;
			lTemp[9] = FINISH;
			lTemp[10] = CANCEL;
			lTemp[11] = REFUSE;
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$SettContractStatus",
							officeID, currencyID);
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型（0，显示全部；1,结算使用（不显示撰写，已提交，已审核，已取消，已拒绝））
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				case 1:
					lArrayID = new long[7];
					lArrayID[0] = NOTACTIVE;
					lArrayID[1] = ACTIVE;
					lArrayID[2] = EXTEND;
					lArrayID[3] = OVERDUE;
					lArrayID[4] = DELAYDEBT;
					lArrayID[5] = BADDEBT;
					lArrayID[6] = FINISH;
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				case 1:
					lArrayID = new long[7];
					lArrayID[0] = NOTACTIVE;
					lArrayID[1] = ACTIVE;
					lArrayID[2] = EXTEND;
					lArrayID[3] = OVERDUE;
					lArrayID[4] = DELAYDEBT;
					lArrayID[5] = BADDEBT;
					lArrayID[6] = FINISH;
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

	}

	/**
	 * 票据是否已经冲销
	 */
	public static class DiscountBillAbatementStatus {
		public static final long NO = 0; // 未冲销

		public static final long YES = 1; // 已冲销

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) NO:
				strReturn = "未冲销";
				break;
			case (int) YES:
				strReturn = "已冲销";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { NO, YES };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$DiscountBillAbatementStatus",
							officeID, currencyID);
		}

	}

	// 放款通知单状态
	public static class SettPayNoticeStatus {
		// 放款通知单状态
		public static final long SUBMIT = 2; // 已提交

		public static final long CHECK = 3; // 已审核

		public static final long USED = 4; // 已使用

		public static final long REFUSE = -2; // 已拒绝

		public static final long[] getAllCode() {
			long[] lTemp = { SUBMIT, CHECK, USED, REFUSE };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$SettPayNoticeStatus",
							officeID, currencyID);
		}

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) SUBMIT:
				strReturn = "已提交";
				break;
			case (int) CHECK:
				strReturn = "已复核";
				break;
			case (int) USED:
				strReturn = "已使用";
				break;
			case (int) REFUSE:
				strReturn = "已拒绝";
				break;
			}
			return strReturn;
		}
	}

	// 利率调整状态
	public static class SettAdjustInterestStatus {
		// 利率调整状态
		public static final long SUBMIT = 1; // 已提交

		public static final long CHECK = 2; // 已审核

		public static final long REFUSE = -2; // 已拒绝

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) SUBMIT:
				strReturn = "已提交";
				break;
			case (int) CHECK:
				strReturn = "已审核";
				break;
			case (int) REFUSE:
				strReturn = "已拒绝";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { SUBMIT, CHECK };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$SettAdjustInterestStatus",
							officeID, currencyID);
		}
	}

	/**
	 * 转贴现种类 买断式、回购式
	 * 
	 * @author haoning
	 */
	public static class TransDiscountType {
		public static final long BUYBREAK = 1; // 买断式

		public static final long REPURCHASE = 2; // 回购式

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) BUYBREAK:
				strReturn = "买断式";
				break;
			case (int) REPURCHASE:
				strReturn = "回购式";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { BUYBREAK, REPURCHASE };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$TransDiscountType",
							officeID, currencyID);
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型（0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
	}

	/**
	 * 投融资业务类型
	 * 
	 * @author wlming
	 * 
	 * To change the template for this generated type comment go to Window -
	 * Preferences - Java - Code Generation - Code and Comments
	 */
	public static class InvestTransactionType {
		public static final long CJ = 1; // 拆借

		public static final long ZTX = 2; // 转贴现

		public static final long ZQHG_YH = 3; // 银行间债券回购

		public static final long ZQHG_JYS = 4; // 交易所间债券回购

		public static final long XQ_YH = 5; // 银行间现券

		public static final long XQ_JYS = 6; // 交易所间现券

		public static final long DKHG = 7; // 贷款回购

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) CJ:
				strReturn = "拆借";
				break;
			case (int) ZTX:
				strReturn = "转贴现";
				break;
			case (int) ZQHG_YH:
				strReturn = "银行间债券回购";
				break;
			case (int) ZQHG_JYS:
				strReturn = "交易所间债券回购";
				break;
			case (int) XQ_YH:
				strReturn = "银行间现券";
				break;
			case (int) XQ_JYS:
				strReturn = "交易所间现券";
				break;
			case (int) DKHG:
				strReturn = "贷款回购";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { CJ, ZTX, ZQHG_YH, ZQHG_JYS, XQ_YH, XQ_JYS, DKHG };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$InvestTransactionType",
							officeID, currencyID);
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型（0，显示全部；1,没有清户选项；2，只有正常、清户；3、正常）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

	}

	/**
	 * 客户扩展属性
	 * 
	 * @author wlming
	 * 
	 * To change the template for this generated type comment go to Window -
	 * Preferences - Java - Code Generation - Code and Comments
	 */
	public static class ExtendAttribute {
		public static final long Attribute1 = 1; // 扩展属性1

		public static final long Attribute2 = 2; // 扩展属性2

		public static final long Attribute3 = 3; // 扩展属性3

		public static final long Attribute4 = 4; // 扩展属性4

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) Attribute1:
				strReturn = "扩展属性1";
				break;
			case (int) Attribute2:
				strReturn = "扩展属性2";
				break;
			case (int) Attribute3:
				strReturn = "扩展属性3";
				break;
			case (int) Attribute4:
				strReturn = "扩展属性4";
				break;

			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { Attribute1, Attribute2, Attribute3, Attribute4 };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$ExtendAttribute",
							officeID, currencyID);
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型（0，显示全部；1,没有清户选项；2，只有正常、清户；3、正常）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

	}

	/**
	 * 自动任务状态常量
	 * 
	 * @author mxzhou
	 * 
	 * To change the template for this gen erated type comment go to Window -
	 * Preferences - Java - Code Generation - Code and Comments
	 */
	public static class TaskStatus {
		public static final long ACTIVE = 1; // 激活

		public static final long STOP = 2; // 停止

		public static final String getName(long lCode) {
			String strReturn = "";
			switch ((int) lCode) {
			case (int) ACTIVE:
				strReturn = "激活";
				break;
			case (int) STOP:
				strReturn = "停止";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { ACTIVE, STOP };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$TaskStatus",
							officeID, currencyID);
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,控件名称
		 * @param nType，控件类型（0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println("显示任务状态下拉列表框错误。错误原因：" + ex.getMessage());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println("显示任务状态下拉列表框错误。错误原因：" + ex.getMessage());
			}
		}

	}

	/**
	 * 业务类型方向
	 * 
	 * @author kewen hu
	 */
	public static class TransactionDirection {
		public static final long INCOME = 1; // 收入

		public static final long PAYOUT = 2; // 付出

		public static final String getName(long lCode) {
			String strReturn = "";
			switch ((int) lCode) {
			case (int) INCOME:
				strReturn = "收入";
				break;
			case (int) PAYOUT:
				strReturn = "付出";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { INCOME, PAYOUT };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$TransactionDirection",
							officeID, currencyID);
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,控件名称
		 * @param nType，控件类型（0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println("显示任务状态下拉列表框错误。错误原因：" + ex.getMessage());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println("显示任务状态下拉列表框错误。错误原因：" + ex.getMessage());
			}
		}

	}

	/**
	 * 转账交易优先级常量
	 */
	public static class RemitPriorityType {
		public static final long NORMAL = 1; // 普通

		public static final long PRIOR = 2; // 加急

		public static final String getName(long lCode) {
			String strReturn = "";
			switch ((int) lCode) {
			case (int) NORMAL:
				strReturn = "普通";
				break;
			case (int) PRIOR:
				strReturn = "加急";
				break;
			}
			return strReturn;
		}

		/**
		 * 将银企接口中定义的交易优先级常量，转换为当前编码值
		 * 
		 * @param d
		 * @return long
		 */
		/*
		 * public static final long convertFromConstantObjectOfBS(PriorityType
		 * p) { long result = -1; if (p.equals(PriorityType.NORMAL)) { result =
		 * NORMAL; } else if (p.equals(PriorityType.PRIOR)) { result = PRIOR; }
		 * return result; }
		 */
		/**
		 * 将当前编码值转换为银企接口中定义的交易优先级常量
		 * 
		 * @param d
		 * @return long
		 */
		/*
		 * public static final PriorityType convertToConstantObjectOfBS(long p) {
		 * PriorityType result = null; if (p == NORMAL) { result =
		 * PriorityType.NORMAL; } else if (p == PRIOR) { result =
		 * PriorityType.PRIOR; } return result; }
		 */
		public static final long[] getAllCode() {
			long[] lTemp = { NORMAL, PRIOR };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$RemitPriorityType",
							officeID, currencyID);
		}

		/**
		 * 根据名称获得ID
		 * 
		 * @param name
		 * @return long
		 */
		public static final long getIdByName(String name) {
			long[] ids = getAllCode();

			for (int n = 0; n < ids.length; n++) {
				if (getName(n).equals(name)) {
					return n;
				}
			}
			return -1;
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型（0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
	}

	/**
	 * 自动入账的银行交易状态,兼容华能的主动上收交易的状态定义 <br>
	 * 银行接口业务新增
	 */
	public static class AutoTurnInBankTransStatus {
		public static final long UNTURNIN = 1; // 应入账，待入账

		public static final long SAVEFAILED = 2; // 结算保存失败

		public static final long SAVESUCCESS = 3; // 结算保存成功

		public static final long CHECKFAILED = 4; // 结算复核失败

		public static final long CHECKSUCCESS = 5; // 结算复核成功

		public static final long[] getAllCode() {
			long[] lTemp = { UNTURNIN, SAVEFAILED, SAVESUCCESS, CHECKFAILED,
					CHECKSUCCESS };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$AutoTurnInBankTransStatus",
							officeID, currencyID);
		}

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) UNTURNIN:
				strReturn = "应入账，待入账";
				break;
			case (int) SAVEFAILED:
				strReturn = "结算保存失败";
				break;
			case (int) SAVESUCCESS:
				strReturn = "结算保存成功";
				break;
			case (int) CHECKFAILED:
				strReturn = "结算复核失败";
				break;
			case (int) CHECKSUCCESS:
				strReturn = "结算复核成功";
				break;
			default:
				strReturn = "错误状态";
			}
			return strReturn;
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型
		 *            （0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

	}

	/**
	 * 银行指令操作类型常量
	 * 
	 * @author mxzhou
	 * 
	 * To change the template for this generated type comment go to Window -
	 * Preferences - Java - Code Generation - Code and Comments
	 */
	public static class BankInstructionOperateType {
		public static final long ADD = 1; // 新增

		public static final long SEND = 2; // 发送

		public static final long UPDATE = 3; // 更新

		public static final long CANCEL = 4; // 撤销

		public static final String getName(long lCode) {
			String strReturn = "";
			switch ((int) lCode) {
			case (int) ADD:
				strReturn = "新增";
				break;
			case (int) SEND:
				strReturn = "发送";
				break;
			case (int) UPDATE:
				strReturn = "更新";
				break;
			case (int) CANCEL:
				strReturn = "撤销";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { ADD, SEND, UPDATE, CANCEL };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$BankInstructionOperateType",
							officeID, currencyID);
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,控件名称
		 * @param nType，控件类型（0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println("显示任务状态下拉列表框错误。错误原因：" + ex.getMessage());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println("显示任务状态下拉列表框错误。错误原因：" + ex.getMessage());
			}
		}

	}

	/**
	 * 商业汇票贴现票据状态常量类
	 * 
	 * @author ygzhao
	 */
	public static class DiscountBillStatus {
		public static final long PAID = 1; // 已核销(已做了付出交易)

		public static final long NOPAID = 2; // 未核销(没做付出交易)

		public static final long EXPIATING = 3; // 待赎回(已做了转贴现付出,但没做转贴现收入)

		public static final String getName(long lCode) {
			String strReturn = "";
			switch ((int) lCode) {
			case (int) PAID:
				strReturn = "已核销";
				break;
			case (int) NOPAID:
				strReturn = "未核销";
				break;
			case (int) EXPIATING:
				strReturn = "待赎回";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { PAID, NOPAID, EXPIATING };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$DiscountBillStatus",
							officeID, currencyID);
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,控件名称
		 * @param nType，控件类型（0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println("商业汇票贴现票据状态下拉列表框错误：" + ex.getMessage());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println("商业汇票贴现票据状态下拉列表框错误：" + ex.getMessage());
			}
		}

	}

	/**
	 * 股份公司统计项目项目类型
	 * 
	 * @author weilu
	 */
	public static class StockProjectType {
		public static final long ASSETHOCK = 1; // 资产负债项目

		public static final long PROFITANDLOSS = 2; // 损益表项目

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) ASSETHOCK:
				strReturn = "资产负债项目";
				break;
			case (int) PROFITANDLOSS:
				strReturn = "损益表项目";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { ASSETHOCK, PROFITANDLOSS };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$StockProjectType",
							officeID, currencyID);
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型（0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
	}

	/**
	 * 股份公司损益类型定义
	 * 
	 * @author weilu
	 */
	public static class profitAndLossType {
		public static final long INTEREST = 1; // 利息

		public static final long REINTEREST = 2; // 复息

		public static final long PUNISHINTEREST = 3; // 罚息

		public static final long POUNDAGE = 4; // 手续费

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) INTEREST:
				strReturn = "利息";
				break;
			case (int) REINTEREST:
				strReturn = "复息";
				break;
			case (int) PUNISHINTEREST:
				strReturn = "罚息";
				break;
			case (int) POUNDAGE:
				strReturn = "手续费";
				break;
			}
			return strReturn;
		}

		// 根据数组性ID取数组行名称(,号分割)
		public static final String getName(String sCode) {
			String strReturn = ""; // 初始化返回值
			String[] str = sCode.split(",");
			for (int i = 0; i < str.length; i++) {
				strReturn += getName(Long.parseLong(str[i]));
				if (i != str.length - 1)
					strReturn += ",";
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { INTEREST, REINTEREST, PUNISHINTEREST, POUNDAGE };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$profitAndLossType",
							officeID, currencyID);
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型（0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
	}

	/**
	 * 股份公司有关客户定义
	 * 
	 * @author weilu
	 */
	public static class RelateClientType {
		public static final long APPLY = 1; // 申请人

		public static final long CONSIGN = 2; // 委托人

		public static final long BARGAINOR = 3; // 卖方

		public static final long REMITTER = 4; // 出票人

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) APPLY:
				strReturn = "申请人";
				break;
			case (int) CONSIGN:
				strReturn = "委托人";
				break;
			case (int) BARGAINOR:
				strReturn = "卖方";
				break;
			case (int) REMITTER:
				strReturn = "出票人";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { APPLY, CONSIGN, BARGAINOR, REMITTER };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$RelateClientType",
							officeID, currencyID);
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型（0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

	}

	/**
	 * 资金上收策略方式
	 * 
	 * @author ygzhao
	 */
	public static class UpGatheringType {
		public static final long GATHER = 1; // 汇总

		public static final long DIVIDE = 2; // 分笔

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) GATHER:
				strReturn = "汇总";
				break;
			case (int) DIVIDE:
				strReturn = "分笔";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { GATHER, DIVIDE };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$UpGatheringType",
							officeID, currencyID);
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型（0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

	}

	/**
	 * 通知存款支取，通知指令状态
	 * 
	 * @author yuanhuang
	 */

	public static class NotifyInformStatus {
		public static final long SAVE = 1; // 保存

		public static final long USED = 2; // 已使用

		public static final long CANCEL = 0; // 取消

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) SAVE:
				strReturn = "保存";
				break;
			case (int) USED:
				strReturn = "已使用";
				break;
			case (int) CANCEL:
				strReturn = "取消";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { SAVE, USED, CANCEL };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.settlement.util.SETTConstant$NotifyInformStatus",
							officeID, currencyID);
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型（0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

	}

	/**
	 * 结算手续费设置
	 * 
	 * @author wlming
	 * 
	 * To change the template for this generated type comment go to
	 * Window>Preferences>Java>Code Generation>Code and Comments
	 */
	public static class SettType {
		public static final long BANKBILL = 1; // 银行汇票

		public static final long BANKACCEPTBILL = 2; // 银行承兑汇票

		public static final long CHEQUE = 3; // 本票

		public static final long CABLE_TRANSFER = 4; // 电汇

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) BANKBILL:
				strReturn = "银行汇票";
				break;
			case (int) BANKACCEPTBILL:
				strReturn = "银行承兑汇票";
				break;
			case (int) CHEQUE:
				strReturn = "本票";
				break;
			case (int) CABLE_TRANSFER:
				strReturn = "电汇";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { BANKBILL, BANKACCEPTBILL, CHEQUE, CABLE_TRANSFER };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.settlement.util.SETTConstant$SettType",
					officeID, currencyID);
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型
		 *            （0，显示全部）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

	}
	
	/**
	 * 2007/03/31 新增是否发送指令
	 * @author hxw
	 *
	 */
public static class IsSend {
	
	public static final long YES = 1;//发送指令
	public static final long NO = 0;//发送指令
 public static final String getName(long lCode)	{
	 String strReturn = "";
	 switch ((int) lCode) {
		case (int) YES:
			strReturn = "是";
			break;
		case (int) NO:
			strReturn = "否";
			break;
	 
 }
	 return strReturn;
}
 public static final long[] getAllCode() {
		long[] lTemp = { YES,NO};//, OTHER};
		return lTemp;
	}
 

	/**
	 * 显示下拉列表
	 * 
	 * @param out
	 * @param strControlName,
	 *            控件名称
	 * @param nType，控件类型（0，显示全部；）
	 * @param lSelectValue
	 * @param isNeedAll，是否需要”全部项“
	 * @param isNeedBlank
	 *            是否需要空白行
	 * @param strProperty
	 */
	public static final void showList(JspWriter out, String strControlName,
			int nType, long lSelectValue, boolean isNeedAll,
			boolean isNeedBlank, String strProperty) {
		long[] lArrayID = null;
		String[] strArrayName = null;
		try {
			switch (nType) {
			case 0:
				lArrayID = getAllCode();
				break;
			}
			strArrayName = new String[lArrayID.length];
			for (int i = 0; i < lArrayID.length; i++) {
				strArrayName[i] = getName(lArrayID[i]);
			}
			showCommonList(out, strControlName, lArrayID, strArrayName,
					lSelectValue, isNeedAll, strProperty, isNeedBlank);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}
}	
	/**
	 * 2007/03/28 新增账户模式
	 * @author fumz
	 *
	 */
	public static class AccountModule {
		public static final long TWOLEVEL = 1; // 二级户模式

		public static final long PORTAL = 2; // 门户模式

		public static final long INCOMEEXPENSE  = 3; // 收支两条线模式
		
		//public static final long AGENT = 4; // 代理汇兑模式
		
		//public static final long FIRSTALLOCATE_LASTPAY = 5; // 先拨后支模式
		
		//public static final long COMMON_PAY = 6;//普通付款模式
		
		//public static final long BRANCHBANK = 4;//开户行模式
		

		//public static final long INSIDE_PAY = 8;  //内部转帐
		
		//public static final long BANK_PAY= 9;     //银行汇款
	
		
		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) TWOLEVEL:
				strReturn = "二级户模式";
				break;
			case (int) PORTAL:
				strReturn = "门户模式";
				break;
			case (int) INCOMEEXPENSE:
				strReturn = "收支两条线模式";
			    break;
/*			case (int) BRANCHBANK:
				strReturn = "开户行模式";
				break;*/
				
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { TWOLEVEL, PORTAL, INCOMEEXPENSE};//,BRANCHBANK};//, OTHER};
			return lTemp;
		}
		//public static final long[] getTransfer() {
		//	long[] payTemp = { INSIDE_PAY, BANK_PAY};//, OTHER};
		//	return payTemp;
		//}

		/**
		 * 显示下拉列表       //显示帐户模式   2007-7-20
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型（0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
	}
	
	/**
	 * 2007-7-23  付款方式类型
	 * @author liaoy
	 *
	 */
	public static class PayModule {
		
		public static final long COMMON_PAY = 1;//普通付款方式
		
        public static final long AGENT = 2; // 代理汇兑方式
		
		public static final long FIRSTALLOCATE_LASTPAY = 3; // 先拨后支方式
		
		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) AGENT:
				strReturn = "代理汇兑方式";
				break;
			case (int) FIRSTALLOCATE_LASTPAY:
				strReturn = "先拨后支方式";
				break;
			case (int) COMMON_PAY:
				strReturn = "普通付款方式";
				break;

			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { COMMON_PAY,AGENT,FIRSTALLOCATE_LASTPAY};//, OTHER};
			return lTemp;
		}
		
	}
	
	/**
	 * 2007-7-23  转帐方式类型
	 * @author liaoy
	 *
	 */
	public static class TransModule {
		
        public static final long INSIDE_PAY = 1;  //内部转帐
		
		public static final long BANK_PAY= 2;     //银行汇款
		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) INSIDE_PAY:
				strReturn = "内部转帐";
				break;	
			case (int) BANK_PAY:
				strReturn = "银行付款";
				break;	
				
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { INSIDE_PAY, BANK_PAY};//, OTHER};
			return lTemp;
		}
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
	}
	
	

	/**
	 * 2007/04/19 开户行科目类型
	 * @author fumz
	 *
	 */
	public static class BankSubjectType {
		public static final long NORMAL = 1; // 正常科目

		public static final long TRANSITION = 2; //过渡科目


		
		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) NORMAL:
				strReturn = "正常科目";
				break;
			case (int) TRANSITION:
				strReturn = "过渡科目";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { NORMAL, TRANSITION};
			return lTemp;
		}


		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型（0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
	}
	
	/**
	 * 2007/06/12 区刚分柔性
	 * @author leiyang
	 *
	 */
	public static class AccountExtendType {
		public static final long SOFT = 0; // 柔性
		public static final long FIRM = 1; // 刚性
		
		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) SOFT:
				strReturn = "柔性";
				break;
			case (int) FIRM:
				strReturn = "刚性";
				break;
			}
			return strReturn;
		}
	}
	
	/**
	 * 2007/06/18 计息方式类型选择
	 * @author leiyang
	 *
	 */
	public static class InterestCalculationMode {
		public static final long FACTDAY  = 1; //按实际天数处理
		public static final long TDAY30  = 2; //按30天处理
		
		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) FACTDAY:
				strReturn = "按实际天数处理";
				break;
			case (int) TDAY30:
				strReturn = "按30天处理";
				break;
			}
			return strReturn;
		}
		
		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName, 控件名称
		 * @param nType，控件类型（0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank, 是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
		
		public static final long[] getAllCode() {
			long[] lTemp = {FACTDAY, TDAY30};
			return lTemp;
		}
	}
	
	/**
	 * 2007/04/19 区分是正常业务还是归集业务
	 * @author fumz
	 *
	 */
	public static class GlobalBusinessTypeId {
		public static final long NORMAL = 1; // 正常业务

		public static final long CONVERGE = 2; //归集业务


		
		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) NORMAL:
				strReturn = "正常业务";
				break;
			case (int) CONVERGE:
				strReturn = "归集业务";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { NORMAL, CONVERGE};
			return lTemp;
		}
	}
	
	/**
	 *Modify by leiyang date 2007/06/27
	 *开关机状态
	 * @author leiyang
	 *
	 */
	public static class OpenCloseType {
		public static final long OPEN = 1; //开机
		public static final long CLOSE = 2; //关机
		
		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) OPEN:
				strReturn = "开机";
				break;
			case (int) CLOSE:
				strReturn = "关机";
				break;
			}
			return strReturn;
		}
		
		
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode();
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
		
		public static final long[] getAllCode() {
			long[] lTemp = {OPEN, CLOSE};
			return lTemp;
		}
	}
	
	public static class OpenCloseStatus {
		public static final long SUCCEED = 1;  //成功
		public static final long UNSUCCESSFUL = 2;  //失败
		
		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) SUCCEED:
				strReturn = "成功";
				break;
			case (int) UNSUCCESSFUL:
				strReturn = "失败";
				break;
			}
			return strReturn;
		}
	}
	
	public static class OpenSystemLogType {
		public static final long ERRORINFO = 1; //错误信息中断
		public static final long READSUBJECT = 2; //导入科目余额
		public static final long MODIFYSYSTEMSTATE = 3; //修改系统状态
		
		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) ERRORINFO:
				strReturn = "错误信息中断";
				break;
			case (int) READSUBJECT:
				strReturn = "导入科目余额";
				break;
			case (int) MODIFYSYSTEMSTATE:
				strReturn = "修改系统状态";
				break;
			}
			return strReturn;
		}
	}
	
	/**
	 *Modify by leiyang date 2007/06/28
	 *关机步骤
	 * @author leiyang
	 *
	 */
	public static class CloseSystemLogType {
		public static final long CHECKOVERDRAFT = 1; //检查透支帐户
		public static final long ERRORINFO = 2; //错误信息中断
		public static final long ACCORDDEPOSIT = 3; //校验协定存款
		public static final long BUSINESS = 4; //校验业务
		public static final long DAYBOOKDISPOSE = 5; //日终处理
		public static final long COUNTINTEREST = 6; //计算利息
		public static final long EXPORTTREASURERLIST = 7; //导出会计分录数据
		public static final long EXTRACTIVEBANKROLLPLAN = 8; //抽取资金计划数据
		public static final long MODIFYCONTRACTSTATE = 9; //修改合同状态
		public static final long MODIFYSYSTEMSTATE = 10; //修改系统状态
		public static final long CHECKCURRENTACCOUNT =11;//校验活期账户
		public static final long MODIFYCONTRACTSTARTDATE = 12;//修改合同起始日期
		
		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) CHECKOVERDRAFT:
				strReturn = "检查透支帐户";
				break;
			case (int) ERRORINFO:
				strReturn = "错误信息中断";
				break;
			case (int) ACCORDDEPOSIT:
				strReturn = "校验协定存款";
				break;
			case (int) BUSINESS:
				strReturn = "校验业务";
				break;
			case (int) DAYBOOKDISPOSE:
				strReturn = "日终处理";
				break;
			case (int) COUNTINTEREST:
				strReturn = "计算利息";
				break;
			case (int) EXPORTTREASURERLIST:
				strReturn = "导出会计分录数据";
				break;
			case (int) EXTRACTIVEBANKROLLPLAN:
				strReturn = "抽取资金计划数据";
				break;
			case (int) MODIFYCONTRACTSTATE:
				strReturn = "修改合同状态";
				break;
			case (int) MODIFYSYSTEMSTATE:
				strReturn = "修改系统状态";
				break;
			case (int) CHECKCURRENTACCOUNT:
				strReturn = "校验活期账户";
				break;
			case (int) MODIFYCONTRACTSTARTDATE:
				strReturn = "修改合同起始日期";
				break;
			}
			return strReturn;
		}
	}
	
	public static class AttachParentType
	{  
		//文档父类型
		public static final long CLIENT = 2; //客户
		public static final long LOAN = 3; //贷款申请
		public static final long EXTENDAPPLY = 5; //展期申请
		public static final long CONSIGNUPSAVESETTING = 6; //上存资金帐户设置
		public static final long YTDRAWNOTICE = 7; //银团贷款提款通知单
		public static final long OBBUDGET = 12;//网银预算附件
	}
	
	
	/**
	 * Added by zwsun, 2007/6/28
	 * 审批流链接地址
	 */
	public static class ApprovalURL{
		//待办业务
		public static final String CURRENTWORKLIST = "/NASApp/iTreasury-settlement/settlement/mywork/currentMainWorkList.jsp";
		//取消审批
		public static final String CANCELAPPROVALLIST = "/NASApp/iTreasury-settlement/settlement/mywork/cancelApprovalListMain.jsp";
		//拒绝业务
		public static final String REFUSEWORKLIST = "/NASApp/iTreasury-settlement/settlement/mywork/refuseMainWorkList.jsp";
	}
	
	//网银指令接收时间设置刚柔性
	public static class OBCommitTimeControlType {
		public static final long RIGID = 1;  //刚性
		public static final long FLEXIBLE = 2;  //柔性
	}
	
	public static void main(String[] args) {
		System.out.println(SETTConstant.AccountType.getAllCode().length);
		SETTConstant.AccountType.isFixAccountType(2);
	}
	
	//自动任务状态
	public static class AutoTaskStatus{
		public static final long SET = 1; //已设置
		public static final long CANCELSET = 2 ; //已取消
		public static final long NOTSET = 3; //未设置
		
		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) SET:
				strReturn = "已设置";
				break;
			case (int) CANCELSET:
				strReturn = "已取消";
				break;
			case (int) NOTSET:
				strReturn = "未设置";
				break;
			}
			return strReturn;
		}
	}
}