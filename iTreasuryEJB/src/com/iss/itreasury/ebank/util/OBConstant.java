package com.iss.itreasury.ebank.util;
/**
 * @author yzhang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Constant;
public class OBConstant extends com.iss.itreasury.util.Constant
{
	private static Log4j log4j = null;
	public OBConstant()
	{
		log4j = new Log4j(Constant.ModuleType.EBANK, this);
	}
	public static class SettInstrType
	{
		//
		public static final long CAPTRANSFER_SELF = 1; //资金划拨-本转
		public static final long CAPTRANSFER_BANKPAY = 2; //资金划拨-银行汇款
		public static final long CAPTRANSFER_INTERNALVIREMENT = 3; //资金划拨-内部转账
		public static final long CAPTRANSFER_OTHER = 11; //资金划拨-其他
		public static final long CHILDCAPTRANSFER = 12; //下属单位资金划拨
		public static final long OPENFIXDEPOSIT = 4; //定期开立
		public static final long FIXEDTOCURRENTTRANSFER = 5; //定期支取
		public static final long BILLOPENFIXDEPOSIT = 17;//换开定期存单
		public static final long OPENNOTIFYACCOUNT = 6; //通知开立
		public static final long NOTIFYDEPOSITDRAW = 7; //通知支取
		public static final long NOTIFYDEPOSITDRAWNOTICE = 32;//通知支取通知
		public static final long TRUSTLOANRECEIVE = 8; //自营贷款清还
		public static final long CONSIGNLOANRECEIVE = 9; //委托贷款清还	
		public static final long INTERESTFEEPAYMENT = 10; //利息费用清还			
		public static final long YTLOANRECEIVE = 13; //银团贷款清还
		public static final long APPLYCAPITAL = 14; //资金申领
		public static final long CAPTRANSFER_FINCOMPANYPAY = 15; //资金划拨-银行付款---财司代付
		public static final long CAPTRANSFER_PAYSUBACCOUNT = 16; //资金划拨-银行付款---拨子账户
		public static final long BANKPAY_DOWNTRANSFER = 18;//资金划拨－下划单位
		public static final long DRIVEFIXDEPOSIT = 19;//定期续存
		public static final long CHANGEFIXDEPOSIT = 20;//定期转存
		public static final long BUDGETNEW = 21;//用款计划新增
		public static final long BUDGETADJUST = 22;//用款计划调整		
		public static final long ONLINEBANK_BANKPAY = 23; //网银-资金拨划-银行汇款
		//网银批量复核专用
		public static final long BANK_CAPTRANSFER_DOUBLE = 24; //网银-逐笔付款（内部转账，银行汇款）
		public static final long BANK_OPENFIXDEPOSIT = 25; //网银-定期开立
		public static final long BANK_FIXEDTOCURRENTTRANSFER = 26; //网银-定期支取
		public static final long BANK_OPENNOTIFYACCOUNT = 27; //网银-通知开立
		public static final long BANK_NOTIFYDEPOSITDRAW = 28; //网银-通知支取
		public static final long BANK_DRIVEFIXDEPOSIT = 29; //网银-到期续存
		public static final long BANK_ONLINEBANK_BANKPAY=30;//网银-银行直联-银行汇款
		
		public static final long BANK_CONSIGNRECEIVESTART=31;//网银-委托收款发起
		
		public static final long[] getAllCode()
		{
			long[] lTemp = { CAPTRANSFER_SELF, CAPTRANSFER_BANKPAY, CAPTRANSFER_INTERNALVIREMENT, CAPTRANSFER_OTHER,
					CHILDCAPTRANSFER,OPENFIXDEPOSIT, FIXEDTOCURRENTTRANSFER, BILLOPENFIXDEPOSIT, OPENNOTIFYACCOUNT,
					NOTIFYDEPOSITDRAW, TRUSTLOANRECEIVE, CONSIGNLOANRECEIVE, INTERESTFEEPAYMENT,
					YTLOANRECEIVE, APPLYCAPITAL, CAPTRANSFER_FINCOMPANYPAY, CAPTRANSFER_PAYSUBACCOUNT,
					BANKPAY_DOWNTRANSFER, DRIVEFIXDEPOSIT, CHANGEFIXDEPOSIT, BUDGETNEW, BUDGETADJUST, ONLINEBANK_BANKPAY};
			return lTemp;
		}
		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.ebank.util.OBConstant$SettInstrType", officeID,
					currencyID);
		}  
		
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) CAPTRANSFER_SELF :
					strReturn = "资金划拨-本转";
					break;
				case (int) CAPTRANSFER_BANKPAY :
					strReturn = "逐笔付款-银行汇款";
					break;
				case (int) CAPTRANSFER_INTERNALVIREMENT :
					strReturn = "逐笔付款-内部转账";
					break;
				case (int) CAPTRANSFER_FINCOMPANYPAY :
					strReturn = "逐笔付款-银行付款-财司代付";
					break;
				case (int) CAPTRANSFER_PAYSUBACCOUNT :
					strReturn = "逐笔付款-银行付款-拨子账户";
					break;
				case (int) BANKPAY_DOWNTRANSFER :
					strReturn = "逐笔付款-下划";
				break;
				
				case (int) CAPTRANSFER_OTHER :
					strReturn = "逐笔付款-其他";
					break;
				case (int) CHILDCAPTRANSFER :
					strReturn = "下属单位资金划拨";
					break;
				case (int) OPENFIXDEPOSIT :
					strReturn = "定期开立";
					break;
				case (int) FIXEDTOCURRENTTRANSFER :
					strReturn = "定期支取";
					break;
				case (int) BILLOPENFIXDEPOSIT :
					strReturn = "换开定期存单";
					break;	
				case (int) OPENNOTIFYACCOUNT :
					strReturn = "通知开立";
					break;
				case (int) NOTIFYDEPOSITDRAW :
					strReturn = "通知支取";
					break;
				case (int) TRUSTLOANRECEIVE :
					strReturn = "自营贷款清还";				    
					break;
				case (int) CONSIGNLOANRECEIVE :
					strReturn = "委托贷款清还";
					break;
				case (int) INTERESTFEEPAYMENT :
					strReturn = "利息费用清还";
					break;
				case (int) YTLOANRECEIVE :
					strReturn = "银团贷款清还";
					break;
				case (int) APPLYCAPITAL :
					strReturn = "资金申领";
					break;	
				case (int) DRIVEFIXDEPOSIT :
					strReturn = "到期续存";
					break;	
				case (int) CHANGEFIXDEPOSIT :
					strReturn = "到期转存";
					break;	
				case (int) BUDGETNEW :
					strReturn = "用款计划新增";
					break;
				case (int) BUDGETADJUST :
					strReturn = "用款计划调整";				
					break;
				case (int) ONLINEBANK_BANKPAY :
					strReturn = "银行直联-银行汇款";
					break;	
					
				//网银批量复核专用
				case (int) BANK_CAPTRANSFER_DOUBLE:
					strReturn = "逐笔付款";
					break;
				case (int) BANK_OPENFIXDEPOSIT:
					strReturn = "定期开立";
				break;
				case (int) BANK_FIXEDTOCURRENTTRANSFER:
					strReturn = "定期支取";
				break;
				case (int) BANK_OPENNOTIFYACCOUNT:
					strReturn = "通知开立";
				break;
				case (int) BANK_NOTIFYDEPOSITDRAW:
					strReturn = "通知支取";
				break;
				case (int) BANK_DRIVEFIXDEPOSIT:
					strReturn = "到期续存";
				break;
				case (int) BANK_ONLINEBANK_BANKPAY:
					strReturn = "银行直联";
				break;
				case (int) BANK_CONSIGNRECEIVESTART:
					strReturn = "委托收款发起";
				break;
				
			}
			return strReturn;
		}
		
		public static final void showList(JspWriter out, String strControlName,int nType,
				long lSelectValue, boolean isNeedAll, boolean isNeedBlank,
				String strProperty, long[] lArrayID)
		{
			String[] strArrayName = null;
			try 
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
					break;
					case 1 :
						lArrayID = new long[]{CAPTRANSFER_BANKPAY, OPENFIXDEPOSIT, 
											FIXEDTOCURRENTTRANSFER, OPENNOTIFYACCOUNT, 
										NOTIFYDEPOSITDRAW, DRIVEFIXDEPOSIT, CHANGEFIXDEPOSIT};
					break;
					case 2 :
						lArrayID = new long[]{BANK_CAPTRANSFER_DOUBLE, BANK_OPENFIXDEPOSIT, BANK_FIXEDTOCURRENTTRANSFER, 
												BANK_OPENNOTIFYACCOUNT, BANK_NOTIFYDEPOSITDRAW, BANK_DRIVEFIXDEPOSIT};
											//逐笔付款，定期开立，定期支取，通知开立，通知支取，到期续存
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
		
		/**
		 * 不需要对下拉列表排序
		 */
		public static final void showList(JspWriter out, String strControlName,int nType,
				long lSelectValue, boolean isNeedAll, boolean isNeedBlank,
				String strProperty, long[] lArrayID,boolean isNeedSort)
		{
			String[] strArrayName = null;
			try 
			{
				switch (nType)
				{
				case 1 :
					lArrayID = new long[]{BANK_CAPTRANSFER_DOUBLE, BANK_OPENFIXDEPOSIT, BANK_FIXEDTOCURRENTTRANSFER, 
							BANK_OPENNOTIFYACCOUNT, BANK_NOTIFYDEPOSITDRAW, BANK_DRIVEFIXDEPOSIT};
							//逐笔付款，定期开立，定期支取，通知开立，通知支取，到期续存
					break;
				case 2 :
					lArrayID = new long[]{BANK_CAPTRANSFER_DOUBLE};
							//逐笔付款（新奥活期业务）
					break;
				case 3 :
					lArrayID = new long[]{BANK_OPENFIXDEPOSIT, BANK_FIXEDTOCURRENTTRANSFER, 
							BANK_OPENNOTIFYACCOUNT, BANK_NOTIFYDEPOSITDRAW, BANK_DRIVEFIXDEPOSIT};
					        //定期开立，定期支取，通知开立，通知支取，到期续存（新奥定期业务）
					break;		
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank,isNeedSort);
			}
			catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
		/**
		 * 不需要对下拉列表排序  银行直联-银行汇款
		 */
		public static final void bankpay_showList(JspWriter out, String strControlName,int nType,
				long lSelectValue, boolean isNeedAll, boolean isNeedBlank,
				String strProperty, long[] lArrayID,boolean isNeedSort)
		{
			String[] strArrayName = null;
			try 
			{
				switch (nType)
				{
				case 1 :
					lArrayID = new long[]{BANK_ONLINEBANK_BANKPAY};
							//银行汇款
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList_BankPay(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank,isNeedSort);
			}
			catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
		
	}
	
	//Modify by leiyang date 2007/06/21
	public static class OperationReminderType {
		
		public static final long OPENFIXDEPOSIT = 1;  //定期开立到期提醒
		public static final long NOTIFYDEPOSITDRAW = 2;  //通知支取到期提醒
		public static final long CAPTRANSFER = 3;  //用款计划提醒
		public static final long UNOPENFIXDEPOSIT = 4;  //定期开立过期提醒
		public static final long UNNOTIFYDEPOSITDRAW = 5;  //通知支取过期提醒
		
		public static final String getName(long lCode) {
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) OPENFIXDEPOSIT :
					strReturn = "定期存款";
					break;
				case (int) NOTIFYDEPOSITDRAW :
					strReturn = "通知存款";
					break;
				case (int) CAPTRANSFER :
					strReturn = "用款计划";
					break;
				case (int) UNOPENFIXDEPOSIT :
					strReturn = "定期存款过期";
					break;
				case (int) UNNOTIFYDEPOSITDRAW :
					strReturn = "通知存款过期";
					break;
			}
			return strReturn;
		}
	}
	
	public static class QueryInstrType
	{
		//
		public static final long ALL = 0; //全部
		public static final long CAPTRANSFER = 1; //资金划拨
		public static final long OPENFIXDEPOSIT = 2; //定期开立
		public static final long FIXEDTOCURRENTTRANSFER = 3; //定期支取
		public static final long OPENNOTIFYACCOUNT = 4; //通知开立
		public static final long NOTIFYDEPOSITDRAW = 5; //通知支取
		public static final long TRUSTLOANRECEIVE = 6; //自营贷款清还
		public static final long CONSIGNLOANRECEIVE = 7; //委托贷款清还	
		public static final long INTERESTFEEPAYMENT = 8; //利息费用清还	
		public static final long CHILDCAPTRANSFER = 9; //下属单位资金划拨
		public static final long YTLOANRECEIVE = 10; //银团贷款清还
		public static final long APPLYCAPITAL = 11; //资金申领
		public static final long BILLOPENFIXDEPOSIT = 12;//换开定期存单
		public static final long DRIVEFIXDEPOSIT = 19;//定期续存
		public static final long CHANGEFIXDEPOSIT = 20;//定期转存		
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) ALL :
					strReturn = "全部";
					break;
				case (int) CAPTRANSFER :
					strReturn = "逐笔付款";
					break;
				case (int) OPENFIXDEPOSIT :
					strReturn = "定期开立";
					break;
				case (int) FIXEDTOCURRENTTRANSFER :
					strReturn = "定期支取";
					break;
				case (int) OPENNOTIFYACCOUNT :
					strReturn = "通知开立";
					break;
				case (int) NOTIFYDEPOSITDRAW :
					strReturn = "通知支取";
					break;
				case (int) TRUSTLOANRECEIVE :
					//strReturn = "自营贷款清还";
					strReturn = "自营贷款清还";
					break;
				case (int) CONSIGNLOANRECEIVE :
					strReturn = "委托贷款清还";
					break;
				case (int) INTERESTFEEPAYMENT :
					strReturn = "利息费用清还";
					break;
				case (int) CHILDCAPTRANSFER :
					strReturn = "下属单位资金划拨";
					break;
				case (int) YTLOANRECEIVE :
					strReturn = "银团贷款清还";
					break;
				case (int) APPLYCAPITAL :
					strReturn = "资金申领";
					break;
				case (int) BILLOPENFIXDEPOSIT:
					strReturn = "换开定期存单";
					break;
				case (int) DRIVEFIXDEPOSIT:
					strReturn = "到期续存";
				    break;
				case (int) CHANGEFIXDEPOSIT:
					strReturn = "到期转存";
				    break;
			}
			return strReturn;
		}
	}
	public static class SettInstrStatus
	{
		//
		public static final long DELETE = 0; //已删除
		public static final long SAVE = 1; //已保存(未复核)
		public static final long CHECK = 2; //已复核
		public static final long SIGN = 3; //已签认(已提交)
		public static final long DEAL = 4; //处理中
		public static final long FINISH = 5; //已完成
		public static final long REFUSE = 6; //已拒绝
		public static final long APPROVALING = 10; //审批中		
		public static final long APPROVALED = 20;  //已审批
		public static final int SAVEANDINITAPPROVAL = 26; // 保存并提交审批
		public static final int DOAPPRVOAL = 27; // 审核
		public static final int CANCELAPPRVOAL = 28; // 取消审核
		public static final int SUBMIT = 29;   //已提交 (暂用于委托收款)
		public static final int CONFIRM = 30;   //已确认 (暂用于委托收款)
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) DELETE :
					strReturn = "已删除";
					break;
				case (int) SAVE :
					strReturn = "已保存";
					break;
				case (int) CHECK :
					strReturn = "已复核";
					break;
				case (int) SIGN :
					strReturn = "已签认";
					break;
				case (int) DEAL :
					strReturn = "处理中";
					break;
				case (int) FINISH :
					strReturn = "已完成";
					break;
				case (int) REFUSE :
					strReturn = "已拒绝";
					break;
				case (int) APPROVALING :
					strReturn = "审批中";
					break;
				case (int) APPROVALED :
					strReturn = "已审批";
					break;
				case (int) SAVEANDINITAPPROVAL :
					strReturn = "保存并提交审批";
					break;
				case (int) DOAPPRVOAL :
					strReturn = "审核";
					break;
				case (int) CANCELAPPRVOAL :
					strReturn = "取消审核";
					break;
				case (int) SUBMIT :
					strReturn = "已提交";
					break;
				case (int) CONFIRM :
					strReturn = "已确认";
					break;
			}
			return strReturn;
		}
	}
	
	//added by mzh_fu 2007/05/31 为签名服务用
	public static class SettActionStatus
	{
		public static final long CANCELCHECKED = 1; //已取消复核
		public static final long CANCELAPPROVALED = 2; //已取消审批
	}
	
	public static class SettRemitType
	{
		//汇款方式
		public static final long SELF = 101; //本转
		public static final long BANKPAY = 102; //银行汇款
		public static final long INTERNALVIREMENT = 103; //内部转账
		public static final long OTHER = 104; //其他
		public static final long FINCOMPANYPAY = 105; //银行付款---财司代付
		public static final long PAYSUBACCOUNT = 106; //银行付款---拨子账户
		public static final long BANKPAY_DOWNTRANSFER = 107; //银行付款－－下划成员单位
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) SELF :
					strReturn = "本转";
					break;
				case (int) BANKPAY :
					strReturn = "银行汇款";
					break;
				case (int) INTERNALVIREMENT :
					strReturn = "内部转账";
					break;
				case (int) OTHER :
					strReturn = "其他";
					break;
				
				case (int) FINCOMPANYPAY :
					strReturn = "银行付款-财司代付";
					break;
				case (int) PAYSUBACCOUNT :
					strReturn = "银行付款-拨子账户";
					break;
				case (int) BANKPAY_DOWNTRANSFER :
					strReturn = "下划";
					break;
					
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = { SELF, BANKPAY, INTERNALVIREMENT, OTHER ,BANKPAY_DOWNTRANSFER};
			return lTemp;
		}
		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.ebank.util.OBConstant$SettRemitType", officeID,
					currencyID);
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
						lArrayID = new long[]{BANKPAY, INTERNALVIREMENT};
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
	public static class AccountOfCpf
	{
		//是否中油内部账户
		public static final long CODE_ACCOUNTOFCPF_YES = 1; //内部账户
		public static final long CODE_ACCOUNTOFCPF_NO = 2; //非内部账户
	}
	public static class LoanInstrType
	{
		//		
		public static final long LOAN_ZYDQ = LOANConstant.LoanType.ZY; //自营贷款申请
		public static final long LOAN_WT = LOANConstant.LoanType.WT; //委托贷款申请
		public static final long DISCOUNT = LOANConstant.LoanType.TX; //贴现
		public static final long LOAN_ZGXEDQ = LOANConstant.LoanType.ZGXE; //最高限额
		//public static final long LOAN_ZGXEDQ = 6; //最高限额短期贷款申请
		//public static final long LOAN_ZGXEZCQ = 7; //最高限额中长期贷款申请		
		//public static final long DISCOUNT = 5; //贴现申请
		public static final long ASSURE = LOANConstant.LoanType.DB; //担保申请
		public static final long DISCOUNTCREDENCE = 9; //贴现凭证申请
		public static final long EXTEND = 10; //展期申请
		public static final long FREE = 11; //免还申请
		public static final long DUEBILL = 12; //提款申请
		//public static final long PLAN = 13; //合同执行计划修改
		public static final long AHEADPAY = 14; //提前还款申请
		public static final long[] getAllCode()
		{
			long[] lTemp = { LOAN_ZYDQ, LOAN_WT, DISCOUNT, LOAN_ZGXEDQ,ASSURE,DISCOUNTCREDENCE, EXTEND, FREE, DUEBILL, AHEADPAY };
			return lTemp;
		}
		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.ebank.util.OBConstant$LoanInstrType", officeID,
					currencyID);
		}         
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) LOAN_ZYDQ :
					strReturn = "自营贷款";
					break;				
				case (int) LOAN_WT :
					strReturn = "委托贷款申请";
					break;
				case (int) DISCOUNT :
					strReturn = "贴现申请";
					break;
				case (int) LOAN_ZGXEDQ :
					strReturn = "最高限额贷款申请";
					break;				
				case (int) ASSURE :
					strReturn = "担保申请";
					break;										
				case (int) DISCOUNTCREDENCE :
					strReturn = "贴现凭证申请";
					break;
				case (int) EXTEND :
					strReturn = "展期申请";
					break;
				case (int) FREE :
					strReturn = "免还申请";
					break;
				case (int) DUEBILL :
					strReturn = "提款申请";
					break;

				case (int) AHEADPAY :
					strReturn = "贷款还款申请";
					break;
			}
			return strReturn;
		}
	}
	public static class LoanInstrStatus
	{
		//
		public static final long SAVE = 1; //撰写
		public static final long SUBMIT = 2; //已提交
		public static final long ACCEPT = 3; //已接受--处理中
		public static final long APPROVE = 4; //已审批
		public static final long REFUSE = -1; //已拒绝
		public static final long CANCEL = -2; //已取消
		public static final long DELETE = -3; //已删除
		public static final long[] getAllCode()
		{
			long[] lTemp = { SAVE, SUBMIT, ACCEPT, APPROVE, REFUSE, CANCEL, DELETE };
			return lTemp;
		}
		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.ebank.util.OBConstant$LoanInstrStatus", officeID,
					currencyID);
		}         
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) SAVE :
					strReturn = "撰写";
					break;
				case (int) SUBMIT :
					strReturn = "已提交";
					break;
				case (int) ACCEPT :
					strReturn = "已接受";
					break;
				case (int) APPROVE :
					strReturn = "已审批";
					break;
				case (int) REFUSE :
					strReturn = "已拒绝";
					break;
				case (int) CANCEL :
					strReturn = "已取消";
					break;
				case (int) DELETE :
					strReturn = "已删除";
					break;
			}
			return strReturn;
		}
	}
	public static class FreeInstrStatus
	{
		//
		public static final long SAVE = 1; //撰写
		public static final long SUBMIT = 2; //已提交
		public static final long ACCEPT = 3; //已接受--处理中
		public static final long APPROVE = 4; //已审批
		public static final long REFUSE = -1; //已拒绝
		public static final long CANCEL = -2; //已取消
		public static final long DELETE = -3; //已删除
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) SAVE :
					strReturn = "撰写";
					break;
				case (int) SUBMIT :
					strReturn = "已提交";
					break;
				case (int) ACCEPT :
					strReturn = "已接受";
					break;
				case (int) APPROVE :
					strReturn = "已审批";
					break;
				case (int) REFUSE :
					strReturn = "已拒绝";
					break;
				case (int) CANCEL :
					strReturn = "已取消";
					break;
				case (int) DELETE :
					strReturn = "已删除";
					break;
			}
			return strReturn;
		}
	}
	//交易类型设置
	public static class TransTypeGroupSet
	{
		public static final long CAPTRANSFER = 11; //资金划拨
		public static final long FIXED = 12; //定期开立
		public static final long NOTISFY = 13; //资金集中管理		
		public static final long LOANREPAYMENT = 14; //贷款清还
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) CAPTRANSFER :
					strReturn = "资金划拨";
					break;
				case (int) FIXED :
					strReturn = "定期存款";
					break;
				case (int) NOTISFY :
					strReturn = "通知存款";
					break;
				case (int) LOANREPAYMENT :
					strReturn = "贷款清还";
					break;
			}
			return strReturn;
		}
	}
	public static class TransTypeSet
	{
		public static final long SELFTRANSFER = 1; //本转
		public static final long EXTERNALTRANSFER = 2; //对外付款
		public static final long INTERNALTRANSFER = 3; //内部转账
		public static final long OPENFIXDEPOSIT = 4; //定期开立申请
		public static final long CONSIGNUPSAVERETURN = 5; //上存资金调回
		public static final long SHORTDEBTRETURN = 6; //还短负
		public static final long TRUSTLOANREPAYMENT = 7; //信托贷款
		public static final long CONSIGNLOANREPAYMENT = 8; //委托贷款
		public static final long CYCLOANREPAYMENT = 9; //循环贷款
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) SELFTRANSFER :
					strReturn = "内部转账";
					break;
				case (int) EXTERNALTRANSFER :
					strReturn = "对外付款";
					break;
				case (int) INTERNALTRANSFER :
					strReturn = "本转";
					break;
				case (int) OPENFIXDEPOSIT :
					strReturn = "定期开立申请";
					break;
				case (int) CONSIGNUPSAVERETURN :
					strReturn = "上存资金调回";
					break;
				case (int) SHORTDEBTRETURN :
					strReturn = "还短负";
					break;
				case (int) TRUSTLOANREPAYMENT :
					strReturn = "信托贷款";
					break;
				case (int) CONSIGNLOANREPAYMENT :
					strReturn = "委托贷款";
					break;
				case (int) CYCLOANREPAYMENT :
					strReturn = "循环贷款";
					break;
			}
			return strReturn;
		}
	}
	//	透支限制
	public static class AccountOverDraftType
	{
		public static final long ALL = 1; //允许透支(或其他)
		public static final long CONSIGN = 2; //允许委托收款透支(或委托收款)
		public static final long INTEREST = 3; //允许付息透支(或付息)
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) ALL :
					strReturn = "允许透支";
					break;
				case (int) CONSIGN :
					strReturn = "允许委托收款透支";
					break;
				case (int) INTEREST :
					strReturn = "允许付息透支";
					break;
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = { ALL, CONSIGN, INTEREST };
			return lTemp;
		}
		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.ebank.util.OBConstant$AccountOverDraftType", officeID,
					currencyID);
		}         
	}
	/**
	 * 得到某个交易指令默认的一期业务类型
	 * @param lInstructionCode 交易指令类型
	 */
	public static long getDefaultTransactionType(long lTransType)
	{
		long lReturn = -1; //初始化返回值
		switch ((int) lTransType)
		{
			case (int) OBConstant.SettInstrType.CAPTRANSFER_SELF :
				lReturn = SETTConstant.TransactionType.INTERNALVIREMENT;
				break;
			case (int) OBConstant.SettInstrType.CAPTRANSFER_BANKPAY ://银行汇款
				lReturn = SETTConstant.TransactionType.BANKPAY;
				break;
				//中交，资金划拨－下划
			case (int) OBConstant.SettInstrType.BANKPAY_DOWNTRANSFER ://下划
				lReturn = SETTConstant.TransactionType.BANKPAY_DOWNTRANSFER;
				break;	
			case (int) OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT :
				lReturn = SETTConstant.TransactionType.INTERNALVIREMENT;
				break;
			case (int) OBConstant.SettInstrType.CAPTRANSFER_OTHER :
				lReturn = SETTConstant.TransactionType.SPECIALOPERATION;
				break;
			case (int) OBConstant.SettInstrType.CHILDCAPTRANSFER :
				lReturn = SETTConstant.TransactionType.SUBCLIENT_BANKPAY;
				break;
			case (int) OBConstant.SettInstrType.OPENFIXDEPOSIT :
				lReturn = SETTConstant.TransactionType.OPENFIXEDDEPOSIT;
				break;
			case (int) OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER :
				lReturn = SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER;
				break;
			case (int) OBConstant.SettInstrType.OPENNOTIFYACCOUNT :
				lReturn = SETTConstant.TransactionType.OPENNOTIFYACCOUNT;
				break;
			case (int) OBConstant.SettInstrType.NOTIFYDEPOSITDRAW :
				lReturn = SETTConstant.TransactionType.NOTIFYDEPOSITDRAW;
				break;
			case (int) OBConstant.SettInstrType.TRUSTLOANRECEIVE :
				lReturn = SETTConstant.TransactionType.TRUSTLOANRECEIVE;
				break;
			case (int) OBConstant.SettInstrType.CONSIGNLOANRECEIVE :
				lReturn = SETTConstant.TransactionType.CONSIGNLOANRECEIVE;
				break;
			case (int) OBConstant.SettInstrType.INTERESTFEEPAYMENT :
				lReturn = SETTConstant.TransactionType.INTERESTFEEPAYMENT;
				break;
			case (int) OBConstant.SettInstrType.YTLOANRECEIVE :
				lReturn = SETTConstant.TransactionType.BANKGROUPLOANRECEIVE;
				break;
			case (int) OBConstant.SettInstrType.APPLYCAPITAL :
				lReturn = SETTConstant.TransactionType.FUND_REQUEST;
				break;
			case (int) OBConstant.SettInstrType.DRIVEFIXDEPOSIT :
				lReturn = SETTConstant.TransactionType.FIXEDCONTINUETRANSFER;
				break;
		/*	case (int) OBConstant.SettInstrType.CAPTRANSFER_FINCOMPANYPAY :
				lReturn = SETTConstant.TransactionType.BANKPAY_FINCOMPANYPAY;
				break;
			case (int) OBConstant.SettInstrType.CAPTRANSFER_PAYSUBACCOUNT :
				lReturn = SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT;
				break;
		*/
		}
		return lReturn;
	}
	public static long getTransTypeByQueryType(long lQueryType)
	{
		long lReturn = -1; //初始化返回值
		switch ((int) lQueryType)
		{
			case (int) OBConstant.QueryInstrType.OPENFIXDEPOSIT :
				lReturn = OBConstant.SettInstrType.OPENFIXDEPOSIT;
				break;
			case (int) OBConstant.QueryInstrType.FIXEDTOCURRENTTRANSFER :
				lReturn = OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER;
				break;
			case (int) OBConstant.QueryInstrType.OPENNOTIFYACCOUNT :
				lReturn = OBConstant.SettInstrType.OPENNOTIFYACCOUNT;
				break;
			case (int) OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT :
				lReturn = OBConstant.SettInstrType.BILLOPENFIXDEPOSIT;
				break;				
			case (int) OBConstant.QueryInstrType.NOTIFYDEPOSITDRAW :
				lReturn = OBConstant.SettInstrType.NOTIFYDEPOSITDRAW;
				break;
			case (int) OBConstant.QueryInstrType.TRUSTLOANRECEIVE :
				lReturn = OBConstant.SettInstrType.TRUSTLOANRECEIVE;
				break;
			case (int) OBConstant.QueryInstrType.CONSIGNLOANRECEIVE :
				lReturn = OBConstant.SettInstrType.CONSIGNLOANRECEIVE;
				break;
			case (int) OBConstant.QueryInstrType.INTERESTFEEPAYMENT :
				lReturn = OBConstant.SettInstrType.INTERESTFEEPAYMENT;
				break;
			case (int) OBConstant.QueryInstrType.CHILDCAPTRANSFER :
				lReturn = OBConstant.SettInstrType.CHILDCAPTRANSFER;
				break;
			case (int) OBConstant.QueryInstrType.YTLOANRECEIVE :
				lReturn = OBConstant.SettInstrType.YTLOANRECEIVE;
				break;
			case (int) OBConstant.QueryInstrType.DRIVEFIXDEPOSIT :
				lReturn = OBConstant.SettInstrType.DRIVEFIXDEPOSIT;
				break;
			case (int) OBConstant.QueryInstrType.CHANGEFIXDEPOSIT :
				lReturn = OBConstant.SettInstrType.CHANGEFIXDEPOSIT;
				break;
		}
		return lReturn;
	}

	public static long getQueryTypeByTransType(long lTransType)
	{
		long lReturn = -1; //初始化返回值
		switch ((int) lTransType)
		{
			case (int) OBConstant.SettInstrType.CAPTRANSFER_BANKPAY :
			case (int) OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT :
			case (int) OBConstant.SettInstrType.CAPTRANSFER_SELF :
			case (int) OBConstant.SettInstrType.BANKPAY_DOWNTRANSFER :
				lReturn = OBConstant.QueryInstrType.CAPTRANSFER;
				break;
			case (int) OBConstant.SettInstrType.OPENFIXDEPOSIT :
				lReturn = OBConstant.QueryInstrType.OPENFIXDEPOSIT;
				break;
			case (int) OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER :
				lReturn = OBConstant.QueryInstrType.FIXEDTOCURRENTTRANSFER;
				break;
			case (int) OBConstant.SettInstrType.OPENNOTIFYACCOUNT :
				lReturn = OBConstant.QueryInstrType.OPENNOTIFYACCOUNT;
				break;
			case (int) OBConstant.SettInstrType.NOTIFYDEPOSITDRAW :
				lReturn = OBConstant.QueryInstrType.NOTIFYDEPOSITDRAW;
				break;
			case (int) OBConstant.SettInstrType.TRUSTLOANRECEIVE :
				lReturn = OBConstant.QueryInstrType.TRUSTLOANRECEIVE;
				break;
			case (int) OBConstant.SettInstrType.CONSIGNLOANRECEIVE :
				lReturn = OBConstant.QueryInstrType.CONSIGNLOANRECEIVE;
				break;
			case (int) OBConstant.SettInstrType.INTERESTFEEPAYMENT :
				lReturn = OBConstant.QueryInstrType.INTERESTFEEPAYMENT;
				break;
			case (int) OBConstant.SettInstrType.YTLOANRECEIVE :
				lReturn = OBConstant.QueryInstrType.YTLOANRECEIVE;
				break;
			case (int) OBConstant.SettInstrType.DRIVEFIXDEPOSIT :
				lReturn = OBConstant.QueryInstrType.DRIVEFIXDEPOSIT;
				break;
			case (int) OBConstant.SettInstrType.CHANGEFIXDEPOSIT :
				lReturn = OBConstant.QueryInstrType.CHANGEFIXDEPOSIT;
				break;
		}
		return lReturn;
	}

	//	资金管理交易指令查询类别
	public static class QueryOperationType
	{	
		public static final long CHECK = 1; //业务复核查询
		public static final long SIGN = 2; //业务签认查询
		public static final long QUERY = 3; //交易指令查询
		public static final long AUDITING = 4; //交易审核
	}
	// 收款方、付款方
	public static class PayerOrPayee
	{
		public static final long PAYER = 1; //付款方
		public static final long PAYEE = 2; //收款方
	}
	// 模块类型
	public static class SubModuleType
	{
		//模块类型
		//Modified by zwsun, 2007-06-12, 保持与Constant中变量一致
		public static final long SETTLEMENT = 1; //资金管理
		public static final long LOAN = 2; //贷款管理        
		public static final long SYSTEM = 3; //系统管理
		public static final long BANKPAY = 4; //银行汇款
//		public static final long SETTLEMENT = 11; //资金管理
//		public static final long LOAN = 12; //贷款管理        
//		public static final long SYSTEM = 13; //系统管理		
		public static final String getName(long lCode) throws Exception
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) SETTLEMENT :
					strReturn = "资金管理";
					break;
				case (int) LOAN :
					strReturn = "贷款管理";
					break;
				case (int) SYSTEM :
					strReturn = "系统管理";
					break;
				case (int) BANKPAY :
					strReturn = "银行汇款";
					break;
			}
			return strReturn;
		}
	}
	//汇票种类
	public static class DraftType
	{
		//汇票种类
		public static final long BANK = 1; //银行承兑汇票
		public static final long BIZ = 2; //商业承兑汇票
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) BANK :
					strReturn = "银行承兑汇票";
					break;
				case (int) BIZ :
					strReturn = "商业承兑汇票";
					break;
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = { BANK, BIZ };
			return lTemp;
		}
		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.ebank.util.OBConstant$DraftType", officeID,
					currencyID);
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
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty,long lOfficeID,long lCurrencyID)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode(lOfficeID,lCurrencyID);
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}

	}
	//下拉列表类型
	public static class ListType
	{
		public static final long LOANCLIENTTYPE = 1; //自营贷款客户分类
		public static final long SETTCLIENTTYPE = 2; //结算客户分类
		
		 /**
         * add by ypxu 2007-04-16
         * 
         * @return long[]
         */
        public static final long[] getAllCode()
        {
            long[] allCode = null;
                long[] lTemp =
                { LOANCLIENTTYPE, SETTCLIENTTYPE};
                allCode = lTemp;

                return allCode;
        }
        public static final long[] getAllCode(long officeID,long currencyID)
        {
        	return Constant.getAllCode("com.iss.itreasury.ebank.util.OBConstant$ListType",officeID,currencyID);
        } 
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) LOANCLIENTTYPE :
					strReturn = "自营贷款客户分类";
					break;
				case (int) SETTCLIENTTYPE :
					strReturn = "结算客户分类";
					break;
			}
			return strReturn;
		}
		public static final void showList(JspWriter out, String strControlName,
				long lSelectValue, boolean isNeedAll, boolean isNeedBlank,
				String strProperty, long[] lArrayID)
		{
			// long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				// lArrayID = getAllCode(lOfficeID,lCurrencyID);
				// lArrayID = getAllSetLoanTypeID(lOfficeID,lCurrencyID);
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
	//企业类型
	public static class ClientCorpIndustry
	{
		//企业类型
		public static final long YXZRGS = 1; //有限责任公司
		public static final long GYDZGS = 2; //国有独资企业
		public static final long WSDZGS = 3; //外商独资企业
		public static final long GFYXGS = 4; //股份有限公司
		public static final long ZWHZQY = 5; //中外合资企业
		public static final long ZWHZHQY = 6; //中外合作企业
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) YXZRGS :
					strReturn = "有限责任公司";
					break;
				case (int) GYDZGS :
					strReturn = "国有独资企业";
					break;
				case (int) WSDZGS :
					strReturn = "外商独资企业";
					break;
				case (int) GFYXGS :
					strReturn = "股份有限公司";
					break;
				case (int) ZWHZQY :
					strReturn = "中外合资企业";
					break;
				case (int) ZWHZHQY :
					strReturn = "中外合作企业";
					break;
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = null;
				lTemp = new long[6];
				lTemp[0] = YXZRGS;
				lTemp[1] = GYDZGS;
				lTemp[2] = WSDZGS;
				lTemp[3] = GFYXGS;
				lTemp[4] = ZWHZQY;
				lTemp[5] = ZWHZHQY;
			return lTemp;
		}
		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.ebank.util.OBConstant$ClientCorpIndustry", officeID,
					currencyID);
		}       
		//		上传附件（合同正式文本）所依附的主体类型
		public static class AttachParentType
		{
			//文档父类型
			public static final long CLIENT = 2; //客户
			public static final long LOAN = 3; //贷款申请
			public static final long EXTENDAPPLY = 5; //展期申请
			public static final long CONSIGNUPSAVESETTING = 6; //上存资金账户设置
			public static final long YTDRAWNOTICE = 7; //银团贷款提款通知单
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
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty,long lOfficeID,long lCurrencyID)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode(lOfficeID,lCurrencyID);
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}

		}
	}
	//	上传附件（合同正式文本）所依附的主体类型
	public static class AttachParentType
	{
		//文档父类型
		public static final long CLIENT = 2; //客户
		public static final long LOAN = 3; //贷款申请
		public static final long EXTENDAPPLY = 5; //展期申请
		public static final long CONSIGNUPSAVESETTING = 6; //上存资金账户设置
		public static final long YTDRAWNOTICE = 7; //银团贷款提款通知单
		public static final long OBBUDGET = 12;//网银预算附件
	}
	/**
	 * 随表报送的书面材料 To change this generated comment edit the template variable
	 * "typecomment": Window>Preferences>Java>Templates. To enable and
	 * disable the creation of type comments go to
	 * Window>Preferences>Java>Code Generation.
	 */
	public static class DocumentType
	{
		public static final String YYZZ = "0"; //营业执照（复印件）
		public static final String ZCFZB = "1"; //资产负债表
		public static final String SYB = "2"; //损益表
		public static final String XJLLB = "3"; //现金流量表
		public static final String FDDBRSFZB = "4"; //法定代表人身份证明
		public static final String SPGXHT = "5"; //商品购销合同（复印件）
		public static final String ZZSFP = "6"; //增值税发票（复印件）
		public static final String YHCDHP = "7"; //银行承兑汇票
		public static final String GSZC = "8"; //公司章程
		public static final String DSHJY = "9"; //董事会决议
		public static final String getName(String strCode)
		{
			String strReturn = ""; //初始化返回值
			if (strCode.equals("0"))
			{
				strReturn = "营业执照（复印件）";
			}
			if (strCode.equals("1"))
			{
				strReturn = "资产负债表";
			}
			if (strCode.equals("2"))
			{
				strReturn = "损益表";
			}
			if (strCode.equals("3"))
			{
				strReturn = "现金流量表";
			}
			if (strCode.equals("4"))
			{
				strReturn = "法定代表人身份证明";
			}
			if (strCode.equals("5"))
			{
				strReturn = "商品购销合同（复印件）";
			}
			if (strCode.equals("6"))
			{
				strReturn = "增值税发票（复印件）";
			}
			if (strCode.equals("7"))
			{
				strReturn = "银行承兑汇票";
			}
			if (strCode.equals("8"))
			{
				strReturn = "公司章程";
			}
			if (strCode.equals("9"))
			{
				strReturn = "董事会决议";
			}

			return strReturn;
		}
		public static final String[] getAllCode()
		{
			String[] lTemp = { YYZZ, ZCFZB, SYB, XJLLB, FDDBRSFZB, SPGXHT, ZZSFP, YHCDHP, GSZC, DSHJY };
			return lTemp;
		}
		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.ebank.util.OBConstant$DocumentType", officeID,
					currencyID);
		}    
	}

	//数据类型
	public static class DataType
	{
		//文档父类型
		public static final long LONG = 1; //长整型
		public static final long INT = 2; //整型
		public static final long DOUBLE = 3; //数值
		public static final long STRING = 4; //字符型
		public static final long TIMESTAMP = 5; //时间
	}

	//判断业务类型
	public static class CheckSettType
	{
		//文档父类型
		public static final long NoRecAndPayID = 0; //非收付款业务
		public static final long RecID = 1; //收款业务
		public static final long PayID = 2; //付款业务
		public static final long RecAndPayID = 3; //收付款业务
	}

	//打印的票据类型
	public static class VouchType
	{
		public static final long INTEREST_LXTZD = 0; //利息通知单
		public static final long REPAY_VOUCH1 = 1; //进账单
		public static final long REPAY_VOUCH2 = 2; //特种转账贷方凭证
		public static final long PAY_VOUCH1 = 3; //存款支取凭证
		public static final long PAY_VOUCH2 = 4; //特种转账借方凭证
		public static final long FIXED_KHZS = 5; //定期开户证实书
		public static final long NOTIFY_KHZS = 6; //通知开户证实书
		public static final long DEPOSIT_CKLXJFTZ = 7; //存款利息计付通知
		public static final long TRUST_DKFFPZ = 8; //自营贷款发放凭证
		public static final long CONSIGN_DKFFPZ = 9; //委托贷款发放凭证
		public static final long TRUST_DKSHPZ = 10; //自营贷款收回凭证
		public static final long CONSIGN_DKSHPZ = 11; //委托贷款收回凭证
		public static final long DISCOUNT_TXFFPZ = 12; //贴现发放凭证
		public static final long LOAN_DKSHPZ = 13; //贷款收回凭证(用于多笔贷款收回)

	}
	//打印时的金额标示
	public static class AmountType
	{
		public static final long AMOUNT_TYPE0 = 1; //金额种类1
		public static final long AMOUNT_TYPE1 = 2;
		public static final long AMOUNT_TYPE2 = 3;
		public static final long AMOUNT_TYPE3 = 4;
	}
//	贷款备注：是否到期
	public static class Loanremark
	{
		public static final long YES = 1; //已到期
		public static final long NO = 0; //未到期	
		public static final String TYPENAME[][]={{"未到期","unmature"},
				                                 {"已到期","mature"}};
		/**
		 * 得到交易类型的名称
		 * @param langcode  语种编号
		 * @param lCode     交易类型编号
		 * @return
		 */
		public static final String getLoanremarkName(String langcode,long lCode)
		{
			String strReturn = ""; //初始化返回值
			int index=Integer.parseInt(Long.toString(lCode));
			if(langcode.compareTo("zh")==0) 
				strReturn = TYPENAME[index][0];
			else
				strReturn = TYPENAME[index][1];
					
			return strReturn;
		}

	}
	public static class AccountisOwn
	{
		public static final long NO  = 0; //本单位账户
		public static final long YES = 1; //下属单位账户	
		public static final String TYPENAME[][]={{"本单位账户","company account"},
				                                 {"下属单位账户","corporate subsidiaries  acount"}};
		/**
		 * 得到交易类型的名称
		 * @param langcode  语种编号
		 * @param lCode     交易类型编号
		 * @return
		 */
		public static final String getAccountisOwnName(String langcode,long lCode)
		{
			String strReturn = ""; //初始化返回值
			int index=Integer.parseInt(Long.toString(lCode));
			if(langcode.compareTo("zh")==0) 
				strReturn = TYPENAME[index][0];
			else
				strReturn = TYPENAME[index][1];
					
			return strReturn;
		}

	}
	public static class AccountType
	{
		public static final long IN = 0; //内部账户
		public static final long OUT = 1; //银行账户	
		public static final String TYPENAME[][]={{"内部账户","Inside account"},
				                                 {"银行账户","Bank acount"}};
		/**
		 * 得到交易类型的名称
		 * @param langcode  语种编号
		 * @param lCode     交易类型编号
		 * @return
		 */
		public static final String getAccountTypeName(String langcode,long lCode)
		{
			String strReturn = ""; //初始化返回值
			int index=Integer.parseInt(Long.toString(lCode));
			if(langcode.compareTo("zh")==0) 
				strReturn = TYPENAME[index][0];
			else
				strReturn = TYPENAME[index][1];
					
			return strReturn;
		}

	}
	
	public static class QueryByUnderClient{
		public static final long YES = 2;
		public static final long NO = 1;
	}
	/**
	 * 资金计划
	 * @author jiamiao
	 *
	 */
	public static class OBCapitalPlan{
		public static final long DEL = 0;//已删除
		public static final long SUBMIT = 1;//已提交
		public static final long CHECK= 2;//已复核

		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) SUBMIT :
					strReturn = "已提交";
					break;
				case (int) CHECK :
					strReturn = "已复核";
					break;
				case (int) DEL :
					strReturn = "已删除";
					break;
			}
			return strReturn;
		}
	}
	
	public static class OBBudgetStatus
	{
		//
		public static final long DELETE = 0; //已删除
		public static final long SAVE = 1; //已保存
		public static final long CHECK = 2; //审批中
		public static final long APPROVE = 3; //已审批
		public static final long NOTDEAL = 4; //未拨付
		public static final long DEAL = 5; //已拨付
		public static final long FAILEDDEAL = 6; //拨付失败
		public static final long BEADJUSTED = 7; //被调整
		public static final long OBBUDGET = 8;	//用款计划
		public static final long AUTOTASK = 9;	//自动任务
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) DELETE :
					strReturn = "已删除";
					break;
				case (int) SAVE :
					strReturn = "已保存";
					break;
				case (int) CHECK :
					strReturn = "审批中";
					break;
				case (int) APPROVE :
					strReturn = "已审批";
					break;
				case (int) NOTDEAL :
					strReturn = "未拨付";
					break;
				case (int) DEAL :
					strReturn = "已拨付";
					break;
				case (int) FAILEDDEAL :
					strReturn = "拨付失败";
					break;
				case (int) BEADJUSTED :
					strReturn = "被调整";
					break;
			}
			return strReturn;
		}
	}
	
	
	public static class OBBankPayStatus 
	{

		//
		public static final long DELETE = 0; //已删除
		public static final long SAVE = 1; //未复核
		public static final long CHECK = 2; //已复核
		public static final long SIGN = 3; //已签认(已提交)
		public static final long DEAL = 4; //处理中
		public static final long FINISH = 5; //已完成
		public static final long REFUSE = 6; //已拒绝
		public static final long AUDITING = 7; //已审核 
		public static final long LOADSAVE = 20; //审核流未审核		
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) DELETE :
					strReturn = "已删除";
					break;
				case (int) SAVE :
					strReturn = "未复核";
					break;
				case (int) CHECK :
					strReturn = "已复核";
					break;
				case (int) SIGN :
					strReturn = "已签认";
					break;
				case (int) DEAL :
					strReturn = "处理中";
					break;
				case (int) FINISH :
					strReturn = "已完成";
					break;
				case (int) REFUSE :
					strReturn = "已拒绝";
					break;
				case (int) AUDITING :
					strReturn = "已审核";
					break;
				case (int) LOADSAVE :
					strReturn = "审核流未审核";
					break;
			}
			return strReturn;
		}
	
	}
	//是否设置关联下级单位
	public static class IsLowerun 
	{
		//
		public static final long ISYES = 1; //是
		public static final long ISNO = 2; //否
		
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) ISYES :
					strReturn = "是";
					break;
				case (int) ISNO :
					strReturn = "否";
					break;
				
			}
			return strReturn;
		}
	
	}
    //华联定义数组，
	public static class Arry{
		public static String [] strArry = new String[]{"1","2","3","4","5","6","7","8","9","0"};
		
		public static final long getSameCode(String sCode)
		{
			long lReturn = -1; //初始化返回值
			for(int i=0; i <strArry.length ; i++   ){
				if(sCode.equals(strArry[i])){
					lReturn=1;
				}								
			}									
			return lReturn;
		}
		
	}
		
	//华联是否同行
	public static class IsSameBank 
	{
		//
		public static final long ISYES = 1; //是
		public static final long ISNO = 0; //否
		
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) ISYES :
					strReturn = "是";
					break;
				case (int) ISNO :
					strReturn = "否";
					break;
			}
			return strReturn;
		}
		
		public static final void showList(JspWriter out,String strControlName,long lSelectValue,boolean isNeedAll,boolean isNeedBlank,String strProperty)
		{
			long[] lArray = null;
			String[] strArrayName = null;
			try
			{
				lArray = new long[]{ISYES,ISNO};
				strArrayName = new String[lArray.length];
				for(int i=0;i<lArray.length;i++)
				{
					strArrayName[i]=getName(lArray[i]);
				}
				out.println("<select name=\""+strControlName+"\" "+strProperty+" >");
				if(isNeedBlank==true)
				{
					if(lSelectValue==-1)
					{
						out.println("<option value='-1' selected></option>");
						
					}else
					{
						out.println("<option value='-1'></option>");
					}
					
				}
				if(isNeedAll==true)
				{
					if(lSelectValue==0)
					{
						out.println("<option value='0' selected>全部</option>");
					}
					else
					{
						out.println("<option value='0'>全部</option>");
					}
				}
				for(int i=0;i<lArray.length;i++)
				{
					
					if(lSelectValue==lArray[i])
					{
						out.println("<option value='"+lArray[i]+"' selected>"+strArrayName[i]+"</option>");
					}
					else
					{
						out.println("<option value='"+lArray[i]+"'>"+strArrayName[i]+"</option>");
					}
				}
			
				out.println("</select>");
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	
	}
	
	
	
	public static class IsDiffLocal 
	{
		//
		public static final long ISYES = 1; //是
		public static final long ISNO = 0; //否
		
	
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) ISYES :
					strReturn = "是";
					break;
				case (int) ISNO :
					strReturn = "否";
					break;
				
			}
			return strReturn;
		}
	
	}
	
	/**
	 * 资金计划模板类型
	 *
	 */
	public static class CapitalPlanModelType
	{
		public static long PLAN_DECLARE = 1;//资金计划申报模板
		
		public static long PLAN_EVERYWEEK = 2; //周资金计划模板
		
	}
	
	/**
	 * 银企指令发送状态
	 * @author zhouxiang
	 */
	public static class BankInstructionStatus
	{
		    public static final long CANCEL = 0;
		    public static final long SAVED = 1;
		    public static final long SUBMITTING = 2;
		    public static final long SUBMITTED = 4;
		    public static final long FAILED = 5;
		    public static final long UNKNOWENED = 6;
		    public static final long CONSTRUCTFAILED = 7;
		    public static final long EXISTED = 8;
		    public static final long SYSTEMNOTREG = 9;
		    public static final long NOTEXIST = 10;
		    public static final long ALLOCATE_SUBMMITING = 11;
		    public static final long ALLOCATE_SUCCESS = 12;
		    public static final long ALLOCATE_FAILED = 13;
		    public static final long ALLOCATE_UNKNOWNED = 14;
		    public static final long RECOVERY_SUBMMITING = 15;
		    public static final long RECOVERY_SUCCESS = 16;
		    public static final long RECOVERY_FAILED = 17;
		    public static final long RECOVERY_UNKNOWNED = 18;
		    
		    public static final long NONE = -100;
		    
		    public static final long UNSEND = -1;	//此状态为网银端指令查询专用

		    public static final String getName(long l)
		    {
		        String s = "";
		        switch((int)l)
		        {
		        case (int)CANCEL: 
		            s = "撤销";
		            break;

		        case (int)SAVED:
		            s = "已保存未发送";
		            break;

		        case (int)SUBMITTING:
		            s = "支付处理中";
		            break;

		        case (int)SUBMITTED:
		            s = "支付成功";
		            break;

		        case (int)FAILED:
		            s = "支付失败";
		            break;

		        case (int)UNKNOWENED:
		            s = "支付未知";
		            break;

		        case (int)CONSTRUCTFAILED:
		            s = "指令创建失败";
		            break;

		        case (int)EXISTED:
		            s = "指令已存在";
		            break;

		        case (int)SYSTEMNOTREG:
		            s = "系统未注册";
		            break;

		        case (int)NOTEXIST:
		            s = "指令不存在";
		            break;

		        case (int)ALLOCATE_SUBMMITING:
		            s = "拨款处理中";
		            break;

		        case (int)ALLOCATE_SUCCESS:
		            s = "拨款成功";
		            break;

		        case (int)ALLOCATE_FAILED:
		            s = "拨款失败";
		            break;

		        case (int)ALLOCATE_UNKNOWNED:
		            s = "拨款未知";
		            break;

		        case (int)RECOVERY_SUBMMITING:
		            s = "拨款收回处理中";
		            break;

		        case (int)RECOVERY_SUCCESS:
		            s = "拨款收回成功";
		            break;

		        case (int)RECOVERY_FAILED:
		            s = "拨款收回失败";
		            break;

		        case (int)RECOVERY_UNKNOWNED:
		            s = "拨款收回未知";
		            break;
		            
		        case (int)UNSEND:
		            s = "";
		            break;

		        case (int)NONE:
		        	s = "无";
		        	break;
		        
		        case 3:
		        default:
		            s = "无效状态";
		            break;
		        }
		        return s;
		    }
		    
	    public static final long[] getAllCode()
		{
			long[] lTemp = { CANCEL, SAVED, SUBMITTING, SUBMITTED, FAILED, UNKNOWENED, CONSTRUCTFAILED, EXISTED, SYSTEMNOTREG, NOTEXIST,
					ALLOCATE_SUBMMITING, ALLOCATE_SUCCESS, ALLOCATE_FAILED, ALLOCATE_UNKNOWNED, RECOVERY_SUBMMITING, RECOVERY_SUCCESS, 
					RECOVERY_FAILED, RECOVERY_UNKNOWNED };
			return lTemp;
		}    
		    
	    /**
		 * 显示下拉列表
		 * @param out
		 * @param strControlName，控件名称
		 * @param nType，控件类型（-1:显示全部；）
		 * @param lSelectValue 默认值
		 * @param isNeedAll，是否需要”全部项“
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
					case 1 :
						lArrayID = new long[]{NONE, CANCEL, SAVED, SUBMITTING, SUBMITTED, FAILED, UNKNOWENED};
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				
	            out.println("<select name=\"" + strControlName + "\" class=\"select\" " + strProperty + ">");
	            if (isNeedAll == true) 
	            {
	            	if (lSelectValue == -1)
	            	{
	            		out.println("<option value='-1' selected>全部</option>");
	            	}
	            	else
	            	{
	            		out.println("<option value='-1'>全部</option>");
	            	}
	            }
	            for (int i = 0; i < lArrayID.length; i++)
	            {
	                Log.print("lArrayID[i] = " + lArrayID[i]);
	                Log.print("lSelectValue = " + lSelectValue);
	                if (lArrayID[i] == lSelectValue)
	                {
	                    out.println("<option value='" + lArrayID[i] + "' selected >" + strArrayName[i] + "</option>");
	                }
	                else
	                {
	                    out.println("<option value='" + lArrayID[i] + "'>" + strArrayName[i] + "</option>");
	                }
	            }
	            out.println("</select>");
				
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}

		}

	}
	//账户授权
	public static class AccountAuthorize
	{
		//是否是直接下级
		public static final long DIRECT = 0;  //直接下级
		public static final long INDIRECT = 1; //非直接下级--变更为全部
		//是否拥有权限
		public static final long HASAUTHORIZE = 0; //拥有权限
		public static final long NOAUTHORIZE = 1; //没有权限
		public static final long ALL = 2; //全部
		
		public static final String getDirect(long isDirect)
		{
			String sName = "";
			switch((int)isDirect)
			{
				case (int)DIRECT:
					sName = "直接下级";
					break;
				case (int)INDIRECT:
					sName = "全部下级";
					break;
					
				
				
					
			}
			return sName;
			
		}
		public static final String getAuthorize(long hasAuthorize)
		{
			String Authorize = "";
			switch((int)hasAuthorize)
			{
				case (int)HASAUTHORIZE:
					Authorize = "是";
					break;
				case (int)NOAUTHORIZE:
					Authorize = "否";
					break;
				case (int)ALL:
					Authorize = "全部";
					break;
					
			}
			return Authorize;
		}
	
		public static final void ShowDirectList(JspWriter out, String strControlName,long lSelectValue,String strProperty) throws Exception
		{
			long[] lArray = null;
			String[] strArrayName = null;
			try{
			lArray = new long[]{DIRECT,INDIRECT};
			strArrayName = new String[lArray.length];
			for(int i=0;i<lArray.length;i++)
			{
				strArrayName[i]=getDirect(lArray[i]);
				
			}
			out.println("<select name=\""+strControlName+"\" "+strProperty+">");
			for(int i=0;i<lArray.length;i++)
			{
				if(lArray[i]==lSelectValue)
				{
					out.println("<option value='"+lArray[i]+"' selected>"+strArrayName[i]+"</option>");
				}
				else
				{
					out.println("<option value='"+lArray[i]+"' >"+strArrayName[i]+"</option>");
				}
			}
				out.println("</select>");
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}

		}
		public static final void showAuthorityList(JspWriter out, String strControlName,long lSelectValue,String strProperty) throws Exception
		{
			long[] lArray=null;
			String[] strArrayName = null;
			try
			{
				lArray = new long[]{ALL,HASAUTHORIZE,NOAUTHORIZE};
				strArrayName = new String[lArray.length];
				for(int i=0;i<lArray.length;i++)
				{
					strArrayName[i]=getAuthorize(lArray[i]);
					
				}
				out.println("<select name=\""+strControlName+"\" "+strProperty+">");
				for(int i=0;i<lArray.length;i++)
				{
					if(lArray[i]==lSelectValue)
					{
						out.println("<option value='"+lArray[i]+"' selected>"+strArrayName[i]+"</option>");
					}
					else
					{
						out.println("<option value='"+lArray[i]+"' >"+strArrayName[i]+"</option>");
					}
				}
				out.println("</select>");
			}
			catch(Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
	}
	
	/**
	 * 
	 * 页面操作定义
	 * @author xlchang 2010-12-01 从结算拷贝
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
		public static final int CANCELAPPLY = 30;	 //取消申请

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
	
	public static class VerifySignatureType
	{
		public static final int RIGIDITY = 1;  //刚性
		public static final int FLEXIBILITY = 2;  //柔性
		public static final String getName(int l)
		{
			String strReturn = "";
			switch(l)
			{
				case RIGIDITY:
					strReturn = "刚性";
					break;
				case FLEXIBILITY:
					strReturn = "柔性";
					break;
					
			}
			return strReturn;
		}
	}
	
	/**
	 * 日期控件
	 * @author zhouxiang
	 *
	 */
	public static class Calendar
	{
		/**
	     * 显示控件
		 * @throws IOException 
	     */
		public static final void show(JspWriter out,String controlName,String CalendarValue,String properties,String CalendarSize) throws IOException
	    {
	    	out.println("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"calendar_tab\">");
	    	out.println("<tr>");
	    	out.println("<td>");
	    	out.println("<input type=\"text\" name=\""+controlName+"\" id=\""+controlName+"\"  class=\"calendar_input\" value=\""+CalendarValue+"\" onfocus=\"showCalendar(this);"+properties+"\" size=\""+CalendarSize+"\">");
	    	out.println("</td>");
	    	out.println("<td>");
	    	out.println("<a class=\"calendar_img\" onclick=\"showCalendar(this,document.getElementById('"+controlName+"'));\"/>");
	    	out.println("</td>");
	    	out.println("</tr>");
	    	out.println("</table>");
	    }
	    
	}
	
	public static void main(String[] args)
	{
		//
	}

}