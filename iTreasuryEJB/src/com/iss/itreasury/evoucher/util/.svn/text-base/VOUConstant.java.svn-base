/*
 * Created on 2004-11-26
 * 
 * TODO To change the template for this generated file go to Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.evoucher.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.setting.dao.Sett_SpecialOperationDAO;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.SETTConstant.TransactionType;
import com.iss.itreasury.util.Constant;

/**
 * @author hyzeng
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VOUConstant extends com.iss.itreasury.util.Constant { 

	public static class privilegeType {

		public static final int privilegeType_1 = 1; // 查询权限

		public static final int privilegeType_2 = 2; // 划款权限
	}

	// 打印部门
	public static class PrintSection {

		public static final int FINANCECOMPANY = 1; // 财务公司

		public static final int EBANKCUSTOMER = 2; // 网上客户

		// 取得打印部门名称
		public static final String getName(long operCode) {

			String strReturn = ""; // 初始化返回值

			switch ((int) operCode) {
			case (int) FINANCECOMPANY:
				strReturn = "财务公司";
				break;
			case (int) EBANKCUSTOMER:
				strReturn = "网上客户";
				break;
			}

			return strReturn;
		}

		// 取得打印部门类型
		public static final long[] getAllCode() {

			long[] transType = { FINANCECOMPANY, EBANKCUSTOMER };
			return transType;
		}

		public static final void showList(JspWriter out, String strControlName,
				long lSelectValue, boolean isNeedAll, boolean isNeedBlank,
				String strProperty, long[] lArrayID) {

			String[] strArrayName = null;
			strArrayName = new String[lArrayID.length];

			for (int i = 0; i < lArrayID.length; i++) {
				strArrayName[i] = getName(lArrayID[i]);
			}
			showCommonList(out, strControlName, lArrayID, strArrayName,
					lSelectValue, isNeedAll, strProperty, isNeedBlank);
		}
	}

	// 电子单据柜客户授权常量
	public static class EleDocsSet {
		// 已经授权
		public static final long HASSETRIGHT = 1;

		// 未授权
		public static final long HASNOSETRIGHT = 0;

	}

	/**
	 * 网银单据类型授权
	 * 
	 * @author zhanglei
	 * 
	 */
	public static class EBankDocRiht {
		/*
		public static final String[][] ebankDocType = {
				{ "201001", "信息查询-账户信息-账户余额" },
				{ "201002", "信息查询-账户信息-账户余额-账户明细" },
				{ "201003", "信息查询-账户信息-账户历史明细" },
				{ "201004", "信息查询-账户信息-下属单位账户查询" },
				{ "201005", "信息查询-账户信息-下属单位账户查询-账户明细" },
				{ "201006", "信息查询-银行账户信息-账户对账单-账户交易明细" },
				{ "201007", "信息查询-银行账户信息-下属单位账户查询-账户交易明细" },
				{ "201008", "信息查询-应付利息和费用匡算" }//,
//				{String.valueOf(TransType.BANKRECEIVE),"银行收款"},
//				{String.valueOf(TransType.BANKPAY),"企业付款"},
//				{String.valueOf(TransType.INTERNALTRANSFER),"内部转帐"},
//				{String.valueOf(TransType.OPENFIXDEPOSIT),"定期开立"},
//				{String.valueOf(TransType.FIXEDTOCURRENTTRANSFER),"定期支取"},
//				{String.valueOf(TransType.FIXEDCONTINUETRANSFER),"定期转存"},
//				{String.valueOf(TransType.OPENNOTIFYACCOUNT),"通知存款开立"},
//				{String.valueOf(TransType.NOTIFYDEPOSITDRAW),"通知存款支取"},
//				{String.valueOf(TransType.TRUSTLOANGRANT),"自营贷款发放"},
//				{String.valueOf(TransType.TRUSTLOANRECEIVE),"自营贷款收回"},
//				{String.valueOf(TransType.DISCOUNTGRANT),"贴现发放"},
//				{String.valueOf(TransType.DISCOUNTRECEIVE),"贴现收回"},
//				{String.valueOf(TransType.OPENMARGIN),"保证金开立"},
//				{String.valueOf(TransType.WITHDRAWMARGIN),"保证金支取"},
//				{String.valueOf(TransType.INTERESTFEEPAYMENT),"利息费用支付"},
//				{String.valueOf(TransType.GENERALLEDGER),"总帐类"},
//				{String.valueOf(TransType.SPECIALOPERATION),"特殊交易"}
				};*/
		public static final long ZHYE = 201001;	//信息查询-账户信息-账户余额
		public static final long ZHMX = 201002;	//信息查询-账户信息-账户余额-账户明细
		public static final long ZHLSMX = 201003;	//信息查询-账户信息-账户历史明细
		public static final long XSDWZHCX = 201004;	//信息查询-账户信息-下属单位账户查询
		public static final long XSZHMX = 201005;	//信息查询-账户信息-下属单位账户查询-账户明细
		public static final long ZHJYMX = 201006;	//信息查询-银行账户信息-账户对账单-账户交易明细
		public static final long XSZHJYMX = 201007;	//信息查询-银行账户信息-下属单位账户查询-账户交易明细
		public static final long YFLX = 201008;	//信息查询-应付利息和费用匡算
		
		public static final String getName(long typeId){
			
			String strReturn = "";
			switch ((int) typeId) {
				case (int)ZHYE:
					strReturn = "信息查询-账户信息-账户余额";
					break;
				case (int)ZHMX:
					strReturn = "信息查询-账户信息-账户余额-账户明细";
					break;
				case (int)ZHLSMX:
					strReturn = "信息查询-账户信息-账户历史明细";
					break;
				case (int)XSDWZHCX:
					strReturn = "信息查询-账户信息-下属单位账户查询";
					break;
				case (int)XSZHMX:
					strReturn = "信息查询-账户信息-下属单位账户查询-账户明细";
					break;
				case (int)ZHJYMX:
					strReturn = "信息查询-银行账户信息-账户对账单-账户交易明细";
					break;
				case (int)XSZHJYMX:
					strReturn = "信息查询-银行账户信息-下属单位账户查询-账户交易明细";
					break;
				case (int)YFLX:
					strReturn = "信息查询-应付利息和费用匡算";
					break;
			}
			return strReturn;
		}
		
		
		public static final String getTypeDesc(long typeId){

			String strReturn = "";
			switch ((int) typeId) {
			//------------网银部分。
			case (int)ZHYE:
				strReturn = "信息查询-账户信息-账户余额";
				break;
			case (int)ZHMX:
				strReturn = "信息查询-账户信息-账户余额-账户明细";
				break;
			case (int)ZHLSMX:
				strReturn = "信息查询-账户信息-账户历史明细";
				break;
			case (int)XSDWZHCX:
				strReturn = "信息查询-账户信息-下属单位账户查询";
				break;
			case (int)XSZHMX:
				strReturn = "信息查询-账户信息-下属单位账户查询-账户明细";
				break;
			case (int)ZHJYMX:
				strReturn = "信息查询-银行账户信息-账户对账单-账户交易明细";
				break;
			case (int)XSZHJYMX:
				strReturn = "信息查询-银行账户信息-下属单位账户查询-账户交易明细";
				break;
			case (int)YFLX:
				strReturn = "信息查询-应付利息和费用匡算";
				break;
			//---------------------------------------------
			case (int) TransactionType.BANKRECEIVE:
				strReturn = "银行收款";
				break;
			case (int) TransactionType.BANKRECEIVE_GATHERING:
				strReturn = "银行收款－上收款项";
				break;
			case (int) TransactionType.BANKRECEIVE_SUBCLIENT:
				strReturn = "银行收款－成员单位收款";
				break;
			case (int) TransactionType.BANKRECEIVE_TOSUBCLIENT:
				strReturn = "银行收款－转成员单位收款";
				break;
			case (int) TransactionType.CASHRECEIVE:
				strReturn = "现金收款";
				break;
			case (int) TransactionType.BANKPAY:
				strReturn = "银行付款";
				break;
			case (int) TransactionType.BANKPAY_DOWNTRANSFER:
				strReturn = "银行付款－下划成员单位";
				break;
			case (int) TransactionType.BANKPAY_FINCOMPANYPAY:
				strReturn = "银行付款-财司代付";
				break;
			case (int) TransactionType.BANKPAY_PAYSUBACCOUNT:
				strReturn = "银行付款-拨子账户";
				break;
			case (int) TransactionType.SUBCLIENT_BANKRECEIVE:
				strReturn = "下属单位银行收款";
				break;
			case (int) TransactionType.SUBCLIENT_BANKPAY:
				strReturn = "下属单位银行付款";
				break;
			case (int) TransactionType.CHECKPAY:
				strReturn = "支票付款";
				break;
			case (int) TransactionType.CASHPAY:
				strReturn = "现金付款";
				break;
			case (int) TransactionType.DRAFTPAY:
				strReturn = "票汇付款";
				break;
			case (int) TransactionType.INTERNALVIREMENT:
				strReturn = "内部转账";
				break;
			case (int) TransactionType.CONSIGNRECEIVE:
				strReturn = "委托收款";
				break;
			case (int) TransactionType.CONSIGNSAVE:
				strReturn = "委托存款";
				break;
			case (int) TransactionType.CAUTIONMONEYSAVE:
				strReturn = "保证金存款";
				break;
			case (int) TransactionType.NATIONALDEBT_BUY:
				strReturn = "国债买入";
				break;
			case (int) TransactionType.NATIONALDEBT_SELL:
				strReturn = "国债卖出";
				break;
			case (int) TransactionType.ONETOMULTI:
				strReturn = "多借多贷";
				break;
			case (int) TransactionType.OTHERPAY:
				strReturn = "其他付款";
				break;
			case (int) TransactionType.OPENFIXEDDEPOSIT:
				strReturn = "定期开立";
				break;
			case (int) TransactionType.FIXEDTOCURRENTTRANSFER:
				strReturn = "定期支取";
				break;
			case (int) TransactionType.FIXEDCONTINUETRANSFER:
				strReturn = "到期续存";
				break;
			case (int) TransactionType.OPENNOTIFYACCOUNT:
				strReturn = "通知存款开立";
				break;
			case (int) TransactionType.NOTIFYDEPOSITDRAW:
				strReturn = "通知存款支取";
				break;
			case (int) TransactionType.TRUSTLOANGRANT:
				strReturn = "自营贷款发放";
				break;
			case (int) TransactionType.TRUSTLOANRECEIVE:
				strReturn = "自营贷款收回";
				break;
			case (int) TransactionType.CONSIGNLOANGRANT:
				strReturn = "委托贷款发放";
				break;
			case (int) TransactionType.CONSIGNLOANRECEIVE:
				strReturn = "委托贷款收回";
				break;
			case (int) TransactionType.DISCOUNTGRANT:
				strReturn = "贴现发放";
				break;
			case (int) TransactionType.DISCOUNTRECEIVE:
				strReturn = "贴现收回";
				break;
			case (int) TransactionType.MULTILOANRECEIVE:
				strReturn = "多笔贷款收回";
				break;
			case (int) TransactionType.TRANSDISCOUNTGRANT:
				strReturn = "转贴现发放";
				break;	
			case (int) TransactionType.CONSIGNUPRECEIVE:
				strReturn = "委托上收资金";
				break;
			case (int) TransactionType.CONSIGNUPSAVE:
				strReturn = "上存资金调回及发放负息资金";
				break;
			case (int) TransactionType.CONSIGNUPSAVERECEIVE:
				strReturn = "上存资金－上收及调回";
				break;
			case (int) TransactionType.SHORTDEBTRECEIVE:
				strReturn = "还短负";
				break;
			case (int) TransactionType.CONSIGNCAPITALOPERATION:
				strReturn = "客户委托资金调拨";
				break;
			case (int) TransactionType.SHORTLOANGRANT:
				strReturn = "发放短期贷款";
				break;
			case (int) TransactionType.CYCLOANGRANT:
				strReturn = "发放循环贷款";
				break;
			case (int) TransactionType.GENERALLEDGER:
				strReturn = "总账业务";
				break;
			case (int) TransactionType.TRANSFEE:
				strReturn = "交易费用";
				break;
			case (int) TransactionType.SPECIALOPERATION:
				strReturn = "特殊业务";
				break;
			case (int) TransactionType.COMPATIBILITY:
				strReturn = "兼容业务";
				break;
			case (int) TransactionType.TRANSABATEMENT:
				strReturn = "转贴现卖出自动冲销";
				break;
			case (int) TransactionType.SHORTLOANRECEIVE:
				strReturn = "短期贷款收回";
				break;
			case (int) TransactionType.INTERESTGRANT:
				strReturn = "发放利息";
				break;
			case (int) TransactionType.SHUTDOWN:
				strReturn = "关机";
				break;
			case (int) TransactionType.CYCLOANRECEIVE:
				strReturn = "循环贷款收回";
				break;
			case (int) TransactionType.BANKUPSAVE:
				strReturn = "银行上收";
				break;
			case (int) TransactionType.BANKUPRECEIVE:
				strReturn = "银行调回";
				break;
			case (int) TransactionType.BANKINTEREST:
				strReturn = "银行发放负息资金";
				break;
			case (int) TransactionType.CYCCONSIGNLOANGRANT:
				strReturn = "循环委托贷款发放";
				break;
			case (int) TransactionType.CYCCONSIGNLOANRECEIVE:
				strReturn = "循环委托贷款收回";
				break;
			case (int) TransactionType.INTERESTFEEPAYMENT:
				strReturn = "利息费用支付";
				break;
			case (int) TransactionType.SECURITIESRECEIVE:
				strReturn = "财务公司收款";
				break;
			case (int) TransactionType.SECURITIESPAY:
				strReturn = "财务公司付款";
				break;
			case (int) TransactionType.SEC_WTLC_RECEIVE:
				strReturn = "委托理财收款";
				break;
			case (int) TransactionType.SEC_WTLC_PAY:
				strReturn = "委托理财付款";
				break;
			case (int) TransactionType.SEC_ZQCX_RECEIVE:
				strReturn = "债券承销收款";
				break;
			case (int) TransactionType.SEC_ZQCX_PAY:
				strReturn = "债券承销付款";
				break;
			case (int) TransactionType.INTEREST_FEE_PAY_CURRENT:
				strReturn = "利息费用支付活期结息";
				break;
			case (int) TransactionType.INTEREST_FEE_PAY_MARGIN:
				strReturn = "利息费用支付保证金结息";
				break;
			case (int) TransactionType.YT_LOAN_COMMISION_FEE:
				strReturn = "银团贷款结手续费";
				break;
			case (int) TransactionType.FIXED_DEPOSIT_PREDRAW_INTEREST:
				strReturn = "定期存款计提应付利息（含冲销）";
				break;
			case (int) TransactionType.MARGIN_DEPOSIT_PREDRAW_INTEREST:
				strReturn = "保证金存款计提应付利息（含冲销）";
				break;
			case (int) TransactionType.TRUST_LOAN_PREDRAW_INTEREST:
				strReturn = "自营贷款计提应收利息（含冲销）";
				break;
			case (int) TransactionType.YT_LOAN_PREDRAW_INTEREST:
				strReturn = "银团贷款计提应收利息（含冲销）";
				break;
			case (int) TransactionType.TRUST_LOAN_INTEREST:
				strReturn = "自营贷款结息";
				break;
			case (int) TransactionType.YT_LOAN_INTEREST:
				strReturn = "银团贷款结息";
				break;
			case (int) TransactionType.TRUST_LOAN_SURETY_FEE:
				strReturn = "自营贷款结担保费";
				break;
			case (int) TransactionType.CONSIGN_LOAN_INTEREST:
				strReturn = "委托贷款结息";
				break;
			case (int) TransactionType.CONSIGN_LOAN_COMMISION_FEE:
				strReturn = "委托贷款结手续费";
				break;
			case (int) TransactionType.GRANT_DEBIT_INTEREST:
				strReturn = "发放负息资金";
				break;
			case (int) TransactionType.RECEIVE_DEBIT_INTEREST:
				strReturn = "收回负息资金";
				break;
			case (int) TransactionType.BANKGROUPLOANGRANT:
				strReturn = "银团贷款发放";
				break;
			case (int) TransactionType.BANKGROUPLOANRECEIVE:
				strReturn = "银团贷款收回";
				break;
			// 海尔专用（04/10/20 add by weilu）
			case (int) TransactionType.CHECK_RECEIVE:
				strReturn = "银行支票收款";
				break;
			case (int) TransactionType.REMIT_RECEIVE:
				strReturn = "银行电汇收款";
				break;
			case (int) TransactionType.OTHER_RECEIVE:
				strReturn = "银行其它形式收款";
				break;
			case (int) TransactionType.CHECK_PAY:
				strReturn = "银行支票付款";
				break;
			case (int) TransactionType.REMIT_PAY:
				strReturn = "银行电汇付款";
				break;
			case (int) TransactionType.TAX_PAY:
				strReturn = "银行税单付款";
				break;
			case (int) TransactionType.OTHER_PAY:
				strReturn = "银行其它形式付款";
				break;
			case (int) TransactionType.DELEGATION_INCOME_OFFBALANCE:
				strReturn = "代保管有价值品类表外业务收入";
				break;
			case (int) TransactionType.DELEGATION_PAYOUT_OFFBALANCE:
				strReturn = "代保管有价值品类表外业务付出";
				break;
			case (int) TransactionType.CONSIGN_INCOME_OFFBALANCE:
				strReturn = "贷款未收利息类表外业务收入";
				break;
			case (int) TransactionType.CONSIGN_PAYOUT_OFFBALANCE:
				strReturn = "贷款未收利息类表外业务付出";
				break;
			case (int) TransactionType.DISCOUNT_INCOME_OFFBALANCE:
				strReturn = "商业汇票贴现类表外业务收入";
				break;
			case (int) TransactionType.DISCOUNT_PAYOUT_OFFBALANCE:
				strReturn = "商业汇票贴现类表外业务付出";
				break;
			case (int) TransactionType.ASSURE_INCOME_OFFBALANCE:
				strReturn = "开出保函凭信类表外业务收入";
				break;
			case (int) TransactionType.ASSURE_PAYOUT_OFFBALANCE:
				strReturn = "开出保函凭信类表外业务付出";
				break;
			case (int) TransactionType.REPORTLOSS:
				strReturn = "挂失";
				break;
			case (int) TransactionType.REPORTFIND:
				strReturn = "解挂";
				break;
			case (int) TransactionType.CHANGECERTIFICATE:
				strReturn = "换发证书";
				break;
			case (int) TransactionType.FREEZE:
				strReturn = "冻结";
				break;
			case (int) TransactionType.DEFREEZE:
				strReturn = "解冻";
				break;
			case (int) TransactionType.ACCOUNTOPEN:
				strReturn = "账户开户";
				break;
			case (int) TransactionType.ACCOUNTMODIFY:
				strReturn = "帐户修改";
				break;	
			case (int) TransactionType.FUND_REQUEST:
				strReturn = "资金申领";
				break;
			case (int) TransactionType.FUND_RETURN:
				strReturn = "资金上存";
				break;
			case (int) TransactionType.BILL_REGISTER:
				strReturn = "空白凭证注册";
				break;
			case (int) TransactionType.BILL_USE:
				strReturn = "空白凭证领用";
				break;
			case (int) TransactionType.INITIATIVE_TURNIN:
				strReturn = "主动上收";
				break;
			case (int) TransactionType.DRAW_PRINCIPAL:
				strReturn = "通知定期支取本金";
				break;
			case (int) TransactionType.DRAW_INTEREST:
				strReturn = "通知定期支取利息";
				break;
			case (int) TransactionType.UPGATHERING:
				strReturn = "资金上收";
				break;
			case (int) TransactionType.COMMISSION:
				strReturn = "交易手续费收取";
				break;
			case (int) TransactionType.INTERESTFEEPAYMENT_SURETY:
				strReturn = "利息支付－自营贷款担保费";
				break;
			case (int) TransactionType.INTERESTFEEPAYMENT_COMMISION:
				strReturn = "利息支付－委托贷款手续费";
				break;
			case (int) TransactionType.OPENMARGIN:
				strReturn = "担保收款";
				break;
			case (int) TransactionType.WITHDRAWMARGIN:
				strReturn = "保后处理";
				break;
			case (int) TransactionType.RECEIVEFINANCE:
				strReturn = "融资租赁收款";
				break;
			case (int) TransactionType.RETURNFINANCE:
				strReturn = "融资租赁还款";
				break;
				
			case (int) TransactionType.CAPITALTRANSFER:
				strReturn = "资金划拨";
				break;
			case (int) TransactionType.DISCOUNTACCRUAL:
				strReturn = "贴现贷款计提应收利息（含冲销）";
				break;
			case (int) TransactionType.DISCOUNT_LOAN_INTEREST:
				strReturn = "贴现贷款结息";
				break;
			case (int) TransactionType.CONSIGN_LOAN_PREDRAW_INTEREST:
				strReturn = "委托贷款计提应收利息（含冲销）";
				break;
			case (int) TransactionType.CURRENT_DEPOSIT_PREDRAW_INTEREST:
				strReturn = "活期存款计提应付利息（含冲销）";
				break;
			case (int) TransactionType.CURRENT_NOTIFY_PREDRAW_INTEREST:
				strReturn = "通知存款计提应付利息（含冲销）";
				break;
			case (int) TransactionType.ABJUSTINTEREST:
				strReturn = "累计费用调整";
				break;
			case (int)TransactionType.BANKPAY_NOTONLINE:
				strReturn = "银行付款－落地处理";
				break;
			case (int) TransactionType.CAPITALUP:
				strReturn = "企业资金上划";
				break;
			case (int)TransactionType.CAPITALDOWN:
				strReturn = "企业资金下拨";
				break;
			case (int) TransactionType.TRANSFERPAY:
				strReturn = "信贷资产转让付款";
				break;
			case (int) TransactionType.TRANSFERREPAY:
				strReturn = "信贷资产转让收款";
				break;
			case (int) TransactionType.AGENTTRANSFERREPAY:
				strReturn = "信贷资产转让代收款";
				break;
			case (int) TransactionType.REPURCHASEDRAW:
				strReturn = "卖出回购计提";
				break;
			case (int) TransactionType.BREAKINTERESTNOTIFY:
				strReturn = "卖出卖断结息";
				break;
				
				
			default:// 当设置了特殊业务的交易类型后，需要将特殊业务的所有交易类型合并到现有业务的交易类型中来
			{
				try {
					Sett_SpecialOperationDAO specialDao = new Sett_SpecialOperationDAO();
					strReturn = specialDao.findSpecialOperationByID(typeId).m_strName;
				} catch (Exception e) {
					System.out.println("取得特殊交易类型时异常");
					e.printStackTrace();
				}
			}
			}
			return strReturn;
		
		}
		
		public static final long[] getAllCode()
        {
            long[] lTemp ={ ZHYE, ZHMX, ZHLSMX, XSDWZHCX, XSZHMX, ZHJYMX, XSZHJYMX, YFLX};
            return lTemp;
        }
		
		public static final long[] getAllCode(long officeID, long currencyID) {

			long[] constantArray = Constant.getAllCode(
					"com.iss.itreasury.evoucher.util.VOUConstant$EBankDocRiht",
					officeID, currencyID);
			return constantArray;
		}
		
	}

	// 业务类型
	public static class TransType {

		public static final long BANKRECEIVE = 1; // 银行收款

		public static final long BANKPAY = 2; // 企业付款

		public static final long INTERNALTRANSFER = 6; // 内部转帐

		public static final long OPENFIXDEPOSIT = 12; // 定期开立

		public static final long FIXEDTOCURRENTTRANSFER = 13; // 定期支取

		public static final long FIXEDCONTINUETRANSFER = 14; // 定期转存

		public static final long OPENNOTIFYACCOUNT = 15; // 通知存款开立

		public static final long NOTIFYDEPOSITDRAW = 16; // 通知存款支取

		public static final long TRUSTLOANGRANT = 17; // 自营贷款发放

		public static final long TRUSTLOANRECEIVE = 18; // 自营贷款收回

		public static final long DISCOUNTGRANT = 21; // 贴现发放

		public static final long DISCOUNTRECEIVE = 22; // 贴现收回

		public static final long OPENMARGIN = 301; // 保证金开立

		public static final long WITHDRAWMARGIN = 302; // 保证金支取

		public static final long INTERESTFEEPAYMENT = 45; // 利息费用支付

		public static final long GENERALLEDGER = 31; // 总帐类

		public static final long SPECIALOPERATION = 33; // 特殊交易

		// 取得业务类型名称
		public static final String getName(long operCode) {

			String strReturn = ""; // 初始化返回值

			switch ((int) operCode) {
			case (int) BANKRECEIVE:
				strReturn = "银行收款";
				break;
			case (int) BANKPAY:
				strReturn = "企业付款";
				break;
			case (int) INTERNALTRANSFER:
				strReturn = "内部转帐";
				break;
			case (int) OPENFIXDEPOSIT:
				strReturn = "定期开立";
				break;
			case (int) FIXEDTOCURRENTTRANSFER:
				strReturn = "定期支取";
				break;
			case (int) FIXEDCONTINUETRANSFER:
				strReturn = "定期转存";
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
			case (int) DISCOUNTGRANT:
				strReturn = "贴现发放";
				break;
			case (int) DISCOUNTRECEIVE:
				strReturn = "贴现收回";
				break;
			case (int) OPENMARGIN:
				strReturn = "保证金开立";
				break;
			case (int) WITHDRAWMARGIN:
				strReturn = "保证金支取";
				break;
			case (int) INTERESTFEEPAYMENT:
				strReturn = "利息费用支付";
				break;
			case (int) GENERALLEDGER:
				strReturn = "总帐类";
				break;
			case (int) SPECIALOPERATION:
				strReturn = "特殊交易";
				break;
			}

			return strReturn;
		}

		// 取得所有业务类型
		public static final long[] getAllCode() {

			long[] transType = { BANKRECEIVE, BANKPAY, INTERNALTRANSFER,
					OPENFIXDEPOSIT, FIXEDTOCURRENTTRANSFER,
					FIXEDCONTINUETRANSFER, OPENNOTIFYACCOUNT,
					NOTIFYDEPOSITDRAW, TRUSTLOANGRANT, TRUSTLOANRECEIVE,
					DISCOUNTGRANT, DISCOUNTRECEIVE, OPENMARGIN, WITHDRAWMARGIN,
					INTERESTFEEPAYMENT, GENERALLEDGER, SPECIALOPERATION };
			return transType;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {

			long[] constantArray = Constant.getAllCode(
					"com.iss.itreasury.evoucher.util.VOUConstant$TransType",
					officeID, currencyID);

			Sett_SpecialOperationDAO specialDao = new Sett_SpecialOperationDAO();
			long[] specialArray = specialDao
					.getAllSpecialOperationForConstant();
			if (specialArray != null && specialArray.length > 0) {
				long[] arrReturn = new long[constantArray.length
						+ specialArray.length];
				System.arraycopy(constantArray, 0, arrReturn, 0,
						constantArray.length);
				System.arraycopy(specialArray, 0, arrReturn,
						constantArray.length, specialArray.length);
				return arrReturn;
			} else
			// 如果数据库中没有设置特殊业务类型，则直接返回constant中定义的类型
			{
				return constantArray;
			}
		}

		public static final void showList(JspWriter out, String strControlName,
				long lSelectValue, boolean isNeedAll, boolean isNeedBlank,
				String strProperty, long[] lArrayID) {

			String[] strArrayName = null;
			strArrayName = new String[lArrayID.length];

			for (int i = 0; i < lArrayID.length; i++) {
				strArrayName[i] = getName(lArrayID[i]);
			}
			showCommonList(out, strControlName, lArrayID, strArrayName,
					lSelectValue, isNeedAll, strProperty, isNeedBlank);
		}

		/**
		 * 根据模块的ID显示该模块的业务类型
		 * 
		 * @param out
		 * @param strControlName
		 * @param lSelectValue
		 * @param isNeedAll
		 * @param isNeedBlank
		 * @param strProperty
		 * @param lArrayID
		 * @param moduleID
		 *            模块ID
		 */
		public static final void showList(JspWriter out, String strControlName,
				long lSelectValue, boolean isNeedAll, boolean isNeedBlank,
				String strProperty, int moduleID, long officeID, long currencyID) {

			String[] strArrayName = null;
			long[] lArrayID = null;

			switch ((int) moduleID) {
			case (int) Constant.ModuleType.SETTLEMENT:
				lArrayID = SETTConstant.TransactionType.getAllCode(officeID,
						currencyID);
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = SETTConstant.TransactionType
							.getName(lArrayID[i]);
				}
				break;
			case (int) Constant.ModuleType.LOAN:
				lArrayID = LOANConstant.LoanType.getAllCode();
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = LOANConstant.LoanType
							.getName(lArrayID[i]);
				}
				break;
			}

			showCommonList(out, strControlName, lArrayID, strArrayName,
					lSelectValue, isNeedAll, strProperty, isNeedBlank);
		}
	}

	// 是否从网银申请
	public static class IsEbankApply {

		public static final long YES = 1;

		public static final long NO = 2;
	}

	// 是否签章
	public static class IsChapter {

		public static final long YES = 1;

		public static final long NO = 2;
	}

	// 单据状态
	public static class VoucherStatus {

		public static final long SAVE = 1;

		public static final long APPROVALING = 2; // 审批中

		public static final long APPROVED = 3; // 已审批

		public static final long SIGN = 4; // 已签认

		public static final long REFUSE = 5; // 已拒绝

		public static final long USED = 6; // 已使用

		public static final long[] getAllCode() {

			long[] lTemp = { SAVE, APPROVALING, APPROVED, SIGN, REFUSE, USED };
			return lTemp;
		}

		public static final String getName(long lCode) {

			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) SAVE:
				strReturn = "已保存";
				break;
			case (int) APPROVALING:
				strReturn = "审批中";
				break;
			case (int) APPROVED:
				strReturn = "已审批";
				break;
			case (int) SIGN:
				strReturn = "已签认";
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

	public static class ReceiptType {

		public static final long INRECONING = 1; // 进帐单

		public static final long DEBIETRANSFERSUBPENA = 2; // 特种转帐借方传票

		public static final long LENDERTRANSFERSUBPENA = 3; // 特种转帐贷方传票

		public static final long DEPOSITVOUCHER = 4; // 存款支取凭证

		public static final String getName(long operCode) {

			String strName = "";
			switch ((int) operCode) {
			case (int) INRECONING:
				strName = "进帐单";
			case (int) DEBIETRANSFERSUBPENA:
				strName = "特种转帐借方传票";
			case (int) LENDERTRANSFERSUBPENA:
				strName = "特种转帐贷方传票";
			case (int) DEPOSITVOUCHER:
				strName = "存款支取凭证";
			}
			return strName;
		}
	}

	// 打印模块
	public static class PrintModule {

		public static final int SETTMENT = 1; // 结算

		public static final int LOAN = 2; // 贷款

		// 取得打印模块名称
		public static final String getName(long operCode) {

			String strReturn = ""; // 初始化返回值

			switch ((int) operCode) {
			case (int) SETTMENT:
				strReturn = "结算  ";
				break;
			case (int) LOAN:
				strReturn = "贷款  ";
				break;
			}

			return strReturn;
		}

		// 取得打印模块类型
		public static final long[] getAllCode() {

			long[] transType = { SETTMENT, LOAN };
			return transType;
		}

		public static final void showList(JspWriter out, String strControlName,
				long lSelectValue, boolean isNeedAll, boolean isNeedBlank,
				String strProperty, long[] lArrayID) {

			String[] strArrayName = null;
			strArrayName = new String[lArrayID.length];

			for (int i = 0; i < lArrayID.length; i++) {
				strArrayName[i] = getName(lArrayID[i]);
			}
			showCommonList(out, strControlName, lArrayID, strArrayName,
					lSelectValue, isNeedAll, strProperty, isNeedBlank);
		}
	}

	// added by mzh_fu 2007/07/04 审批类型(审批流使用)
	public static class ApprovalType {
		public static final int REPRINTAPPLY = 1; // 补打申请

		public static final String getName(long operCode) {
			String strReturn = ""; // 初始化返回值

			switch ((int) operCode) {
			case (int) REPRINTAPPLY:
				strReturn = "补打申请 ";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] transType = { REPRINTAPPLY };
			return transType;
		}

		public static final void showList(JspWriter out, String strControlName,
				long lSelectValue, boolean isNeedAll, boolean isNeedBlank,
				String strProperty, long[] lArrayID) {
			String[] strArrayName = null;
			strArrayName = new String[lArrayID.length];

			for (int i = 0; i < lArrayID.length; i++) {
				strArrayName[i] = getName(lArrayID[i]);
			}
			showCommonList(out, strControlName, lArrayID, strArrayName,
					lSelectValue, isNeedAll, strProperty, isNeedBlank);
		}
	}

	public static class XMLValueType {
		public static final int RETURNTABLENAME = 1;

		public static final int RETURNDESC = 2;

		public static final int RETURNVALUE = 3;
	}
}