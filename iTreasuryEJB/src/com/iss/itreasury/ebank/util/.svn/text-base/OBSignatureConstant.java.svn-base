package com.iss.itreasury.ebank.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.Query_FinanceInfo;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;

public class OBSignatureConstant
{

	public static class OBSignatureConstantTools
	{

		public static String[] getSignNameArray(long transType)
		{

			String[] nameArray = null;
			switch ((int) transType)
			{
				// 资金划拨-本转
				case (int) OBConstant.SettInstrType.CAPTRANSFER_SELF :
					nameArray = CapTransfer.getSignNameArray();
					break;
				// 资金划拨-银行汇款
				case (int) OBConstant.SettInstrType.CAPTRANSFER_BANKPAY :
					nameArray = CapTransfer.getSignNameArray();
					break;
				// 资金划拨-内部转账
				case (int) OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT :
					nameArray = CapTransfer.getSignNameArray();
					break;
				// 资金划拨-其他
				case (int) OBConstant.SettInstrType.CAPTRANSFER_OTHER :
					nameArray = CapTransfer.getSignNameArray();
					break;
				// 通知开立
				case (int) OBConstant.SettInstrType.OPENNOTIFYACCOUNT :
					nameArray = OpenNotifyAccount.getSignNameArray();
					break;
				// 通知支取
				case (int) OBConstant.SettInstrType.NOTIFYDEPOSITDRAW :
					nameArray = NotifyDepositDraw.getSignNameArray();
					break;
				// 定期开立
				case (int) OBConstant.SettInstrType.OPENFIXDEPOSIT :
					nameArray = OpenFixDeposit.getSignNameArray();
					break;
				// 定期支取
				case (int) OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER :
					nameArray = FixedToCurrentTransfer.getSignNameArray();
					break;
				//定期续存
				case (int) OBConstant.SettInstrType.DRIVEFIXDEPOSIT :
					nameArray = DriveFixdePosit.getSignNameArray();
					break;
				//定期转存
				case (int) OBConstant.SettInstrType.CHANGEFIXDEPOSIT :
					nameArray = ChangeFixdeposit.getSignNameArray();
					break;	
					
					
			}
			return nameArray;
		}

		public static String[] getSignValueArrayFromReq(long transType, HttpServletRequest request)
		{

			String[] nameArray = null;
			switch ((int) transType)
			{
				// 资金划拨-本转
				case (int) OBConstant.SettInstrType.CAPTRANSFER_SELF :
					nameArray = CapTransfer.getSignValueArrayFromReq(request);
				// 资金划拨-银行汇款
				case (int) OBConstant.SettInstrType.CAPTRANSFER_BANKPAY :
					nameArray = CapTransfer.getSignValueArrayFromReq(request);
					break;
				// 资金划拨-内部转账
				case (int) OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT :
					nameArray = CapTransfer.getSignValueArrayFromReq(request);
					break;
				// 资金划拨-其他
				case (int) OBConstant.SettInstrType.CAPTRANSFER_OTHER :
					nameArray = CapTransfer.getSignValueArrayFromReq(request);
					break;
				// 通知开立
				case (int) OBConstant.SettInstrType.OPENNOTIFYACCOUNT :
					nameArray = OpenNotifyAccount.getSignValueArrayFromReq(request);
					break;
				// 通知支取
				case (int) OBConstant.SettInstrType.NOTIFYDEPOSITDRAW :
					nameArray = NotifyDepositDraw.getSignValueArrayFromReq(request);
					break;
				// 定期开立
				case (int) OBConstant.SettInstrType.OPENFIXDEPOSIT :
					nameArray = OpenFixDeposit.getSignValueArrayFromReq(request);
					break;
				// 定期支取
				case (int) OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER :
					nameArray = FixedToCurrentTransfer.getSignValueArrayFromReq(request);
					break;
				// 定期续存
				case (int) OBConstant.SettInstrType.DRIVEFIXDEPOSIT :
					nameArray = DriveFixdePosit.getSignValueArrayFromReq(request);
					break;
					//定期转存
				case (int) OBConstant.SettInstrType.CHANGEFIXDEPOSIT :
					nameArray = ChangeFixdeposit.getSignValueArrayFromReq(request);
					break;
			}
			return nameArray;
		}

	}

	// 通知
	public static class OpenNotifyAccount
	{
		//modify by xwhe 2008-10-15
		public static int iArrayLength = 6;
		public static final String[] getSignNameArray()
		{
			int iIndex = -1;
			String[] nameArray = new String[iArrayLength];
			nameArray[++iIndex] = "payeeAcctID";
			nameArray[++iIndex] = "payerAcctID";
			nameArray[++iIndex] = "noticeDay";
			nameArray[++iIndex] = "amount";
		//	nameArray[++iIndex] = "executeDate";
			nameArray[++iIndex] = "submitUserId";
			nameArray[++iIndex] = "lInstructionID";

			return nameArray;
		}

		public static final String[] getSignValueArrayFromInfo(FinanceInfo financeInfo)
		{
			int iIndex = -1;
			String[] valueArray = new String[iArrayLength];

			valueArray[++iIndex] = String.valueOf(financeInfo.getPayeeAcctID());
			valueArray[++iIndex] = String.valueOf(financeInfo.getPayerAcctID());
			valueArray[++iIndex] = String.valueOf(financeInfo.getNoticeDay());
			valueArray[++iIndex] = DataFormat.format(financeInfo.getAmount(), 2);
		//	valueArray[++iIndex] = DataFormat.formatDate(financeInfo.getExecuteDate(), DataFormat.FMT_DATE_YYYYMMDD);
			valueArray[++iIndex] = String.valueOf(financeInfo.getConfirmUserID());
			valueArray[++iIndex] = String.valueOf(-1);
			return valueArray;
		}

		public static final String[] getSignValueArrayFromReq(HttpServletRequest request)
		{
			int iIndex = -1;
			String[] valueArray = new String[iArrayLength];

			valueArray[++iIndex] = request.getParameter("nPayeeAccountID");
			valueArray[++iIndex] = request.getParameter("nPayerAccountID");
			valueArray[++iIndex] = request.getParameter("nNoticeDay");
			valueArray[++iIndex] = DataFormat.format(Double.valueOf(
			DataFormat.reverseFormatAmount((String) request.getParameter("dAmount"))).doubleValue(), 2);
		//	valueArray[++iIndex] = request.getParameter("tsExecute");
			valueArray[++iIndex] = request.getParameter("submitUserId");
			valueArray[++iIndex] = request.getParameter("lInstructionID");
			//valueArray[++iIndex] = "-1";

			return valueArray;
		}

		public static final void outSignNameArrayToView(JspWriter out) throws IOException
		{
			int iIndex = -1;
			out.println("var nameArray = new Array();");
			out.println("var valueArray = new Array();");

			out.println("nameArray["+ ++iIndex +"] = \"payeeAcctID\";");
			out.println("nameArray["+ ++iIndex +"] = \"payerAcctID\";");
			out.println("nameArray["+ ++iIndex +"] = \"noticeDay\";");
			out.println("nameArray["+ ++iIndex +"] = \"amount\";");
		//	out.println("nameArray["+ ++iIndex +"] = \"executeDate\";");
			out.println("nameArray["+ ++iIndex +"] = \"submitUserId\";");
			out.println("nameArray["+ ++iIndex +"] = \"lInstructionID\";");

		}

		public static final void outSignValueArrayToView(JspWriter out) throws IOException
		{
			int iIndex = -1;
			out.println("valueArray["+ ++iIndex +"] = frm.nPayeeAccountID.value;");
			out.println("valueArray["+ ++iIndex +"] = frm.nPayerAccountID.value;");
			out.println("valueArray["+ ++iIndex +"] = frm.nNoticeDay.value;");
			out.println("valueArray["+ ++iIndex +"] = parseFloat(reverseFormatAmount(frm.dAmount.value)).toFixed(2);");
		//	out.println("valueArray["+ ++iIndex +"] = frm.tsExecute.value;");
			out.println("valueArray["+ ++iIndex +"] = frm.submitUserId.value;");
			out.println("valueArray["+ ++iIndex +"] = frm.lInstructionID.value;");
			//out.println("valueArray["+ ++iIndex +"] = '-1';");
		}
	}

	// 通知支取
	public static class NotifyDepositDraw
	{
		//modify by xwhe 2008-10-15
		public static int iArrayLength = 8;
		public static final String[] getSignNameArray()
		{
			int iIndex = -1;
			String[] nameArray = new String[iArrayLength];
			nameArray[++iIndex] = "sNotifyDepositNoCtrl";
			nameArray[++iIndex] = "payeeAcctID";
			nameArray[++iIndex] = "interestPayeeAcctID";
			nameArray[++iIndex] = "amount";
	//		nameArray[4] = "executeDate";
			nameArray[++iIndex] = "remitType";
			nameArray[++iIndex] = "interestRemitType";
			nameArray[++iIndex] = "submitUserId";
			nameArray[++iIndex] = "lInstructionID";

			return nameArray;
		}

		public static final String[] getSignValueArrayFromInfo(FinanceInfo financeInfo)
		{
			int iIndex = -1;
			String[] valueArray = new String[iArrayLength];

			valueArray[++iIndex] = String.valueOf(financeInfo.getDepositNo()); // 存款单据号
			valueArray[++iIndex] = String.valueOf(financeInfo.getPayeeAcctID()); // 本金收款方账号（对应ID值）
			valueArray[++iIndex] = String.valueOf(financeInfo.getInterestPayeeAcctID()); // 利息收款方账号（对应ID值）
			valueArray[++iIndex] = DataFormat.format(financeInfo.getAmount(), 2); // 金额
		//	valueArray[++iIndex] = DataFormat.formatDate(financeInfo.getExecuteDate(), DataFormat.FMT_DATE_YYYYMMDD);// 执行日
			valueArray[++iIndex] = String.valueOf(financeInfo.getRemitType()); // 本金汇款方式
			valueArray[++iIndex] = String.valueOf(financeInfo.getInterestRemitType()); // 利息汇款方式
			valueArray[++iIndex] = String.valueOf(financeInfo.getConfirmUserID());
			valueArray[++iIndex] = String.valueOf(-1);
			return valueArray;
		}

		public static final String[] getSignValueArrayFromReq(HttpServletRequest request)
		{
			int iIndex = -1;
			String[] valueArray = new String[iArrayLength];

			valueArray[++iIndex] = request.getParameter("sNotifyDepositNoCtrl"); // 存款单据号
			valueArray[++iIndex] = request.getParameter("nPayeeAccountID"); // 本金收款方账号（对应ID值）
			valueArray[++iIndex] = request.getParameter("nInterestPayeeAccountID"); // 利息收款方账号（对应ID值）
			valueArray[++iIndex] = DataFormat.format(Double.valueOf(
					DataFormat.reverseFormatAmount((String) request.getParameter("dAmount"))).doubleValue(), 2);// 金额
	//		valueArray[++iIndex] = request.getParameter("tsExecute"); // 执行日
			valueArray[++iIndex] = request.getParameter("nRemitTypeHidden"); // 本金汇款方式
			valueArray[++iIndex] = request.getParameter("nInterestRemitTypeHidden");// 利息汇款方式
			valueArray[++iIndex] = request.getParameter("submitUserId");
			valueArray[++iIndex] = request.getParameter("lInstructionID");
			//valueArray[++iIndex] = "-1";

			return valueArray;
		}

		public static final void outSignNameArrayToView(JspWriter out) throws IOException
		{
			int iIndex = -1;
			out.println("var nameArray = new Array();");
			out.println("var valueArray = new Array();");

			out.println("nameArray["+ ++iIndex +"] = \"sNotifyDepositNoCtrl\";");
			out.println("nameArray["+ ++iIndex +"] = \"payeeAcctID\";");
			out.println("nameArray["+ ++iIndex +"] = \"interestPayeeAcctID\";");
			out.println("nameArray["+ ++iIndex +"] = \"amount\";");
		//	out.println("nameArray["+ ++iIndex +"] = \"executeDate\";");
			out.println("nameArray["+ ++iIndex +"] = \"remitType\";");
			out.println("nameArray["+ ++iIndex +"] = \"interestRemitType\";");
			out.println("nameArray["+ ++iIndex +"] = \"submitUserId\";");
			out.println("nameArray["+ ++iIndex +"] = \"lInstructionID\";");

		}

		public static final void outSignValueArrayToView(JspWriter out) throws IOException
		{
			int iIndex = -1;
			out.println("valueArray["+ ++iIndex +"] = frm.sNotifyDepositNoCtrl.value;");
			out.println("valueArray["+ ++iIndex +"] = frm.nPayeeAccountID.value;");
			out.println("valueArray["+ ++iIndex +"] = frm.nInterestPayeeAccountID.value;");
			out.println("valueArray["+ ++iIndex +"] = parseFloat(reverseFormatAmount(frm.dAmount.value)).toFixed(2);");
		//	out.println("valueArray["+ ++iIndex +"] = frm.tsExecute.value;");
			out.println("valueArray["+ ++iIndex +"] = frm.nRemitTypeHidden.value;");
			out.println("valueArray["+ ++iIndex +"] = frm.nInterestRemitTypeHidden.value;");
			out.println("valueArray["+ ++iIndex +"] = frm.submitUserId.value;");
			out.println("valueArray["+ ++iIndex +"] = frm.lInstructionID.value;");
			//out.println("valueArray["+ ++iIndex +"] = '-1';");
		}

	}

	// 定期开立
	public static class OpenFixDeposit
	{
		//modify by xwhe 2008-10-15
		public static int iArrayLength = 6;
		public static final String[] getSignNameArray()
		{
			int iIndex = -1;
			String[] nameArray = new String[iArrayLength];
			nameArray[++iIndex] = "payeeAcctID";
			nameArray[++iIndex] = "payerAcctID";
			nameArray[++iIndex] = "fixedDepositTime";
			nameArray[++iIndex] = "amount";
		//	nameArray[++iIndex] = "executeDate";
			nameArray[++iIndex] = "submitUserId";
			nameArray[++iIndex] = "lInstructionID";

			return nameArray;
		}

		public static final String[] getSignValueArrayFromInfo(FinanceInfo financeInfo)
		{
			int iIndex = -1;
			String[] valueArray = new String[iArrayLength];

			valueArray[++iIndex] = String.valueOf(financeInfo.getPayeeAcctID());
			valueArray[++iIndex] = String.valueOf(financeInfo.getPayerAcctID());
			valueArray[++iIndex] = String.valueOf(financeInfo.getFixedDepositTime());
			valueArray[++iIndex] = DataFormat.format(financeInfo.getAmount(), 2);
		//	valueArray[++iIndex] = DataFormat.formatDate(financeInfo.getExecuteDate(), DataFormat.FMT_DATE_YYYYMMDD);
			valueArray[++iIndex] = String.valueOf(financeInfo.getConfirmUserID());
			valueArray[++iIndex] = String.valueOf(-1);
			return valueArray;
		}

		public static final String[] getSignValueArrayFromReq(HttpServletRequest request)
		{
			int iIndex = -1;
			String[] valueArray = new String[iArrayLength];

			valueArray[++iIndex] = request.getParameter("nPayeeAccountID");
			valueArray[++iIndex] = request.getParameter("nPayerAccountID");
			valueArray[++iIndex] = request.getParameter("nFixedDepositTime");
			valueArray[++iIndex] = DataFormat.format(Double.valueOf(
					DataFormat.reverseFormatAmount((String) request.getParameter("dAmount"))).doubleValue(), 2);
		//	valueArray[++iIndex] = request.getParameter("tsExecute");
			valueArray[++iIndex] = request.getParameter("submitUserId");

			valueArray[++iIndex] = request.getParameter("lInstructionID");

			return valueArray;
		}

		public static final void outSignNameArrayToView(JspWriter out) throws IOException
		{
			int iIndex = -1;
			out.println("var nameArray = new Array();");
			out.println("var valueArray = new Array();");

			out.println("nameArray["+ ++iIndex +"] = \"payeeAcctID\";");
			out.println("nameArray["+ ++iIndex +"] = \"payerAcctID\";");
			out.println("nameArray["+ ++iIndex +"] = \"fixedDepositTime\";");
			out.println("nameArray["+ ++iIndex +"] = \"amount\";");
		//	out.println("nameArray["+ ++iIndex +"] = \"executeDate\";");
			out.println("nameArray["+ ++iIndex +"] = \"submitUserId\";");
			out.println("nameArray["+ ++iIndex +"] = \"lInstructionID\";");

		}

		public static final void outSignValueArrayToView(JspWriter out) throws IOException
		{
			int iIndex = -1;
			out.println("valueArray["+ ++iIndex +"] = frm.nPayeeAccountID.value;");
			out.println("valueArray["+ ++iIndex +"] = frm.nPayerAccountID.value;");
			out.println("valueArray["+ ++iIndex +"] = frm.nFixedDepositTime.value;");
			out.println("valueArray["+ ++iIndex +"] = parseFloat(reverseFormatAmount(frm.dAmount.value)).toFixed(2);");
	//		out.println("valueArray["+ ++iIndex +"] = frm.tsExecute.value;");
			out.println("valueArray["+ ++iIndex +"] = frm.submitUserId.value;");
			//out.println("valueArray["+ ++iIndex +"] = frm.lInstructionID.value;");
			out.println("valueArray["+ ++iIndex +"] = frm.lInstructionID.value;;");
		}

	}

	// 定期支取
	public static class FixedToCurrentTransfer
	{
        //modify by xwhe 2008-10-15
		public static int iArrayLength = 8;
		public static final String[] getSignNameArray()
		{
			int iIndex = -1;
			String[] nameArray = new String[iArrayLength];
			nameArray[++iIndex] = "sFixedDepositNoCtrl";
			nameArray[++iIndex] = "payeeAcctID";
			nameArray[++iIndex] = "interestPayeeAcctID";
			nameArray[++iIndex] = "amount";
		//	nameArray[++iIndex] = "executeDate";
			nameArray[++iIndex] = "remitType";
			nameArray[++iIndex] = "interestRemitType";
			nameArray[++iIndex] = "submitUserId";
			nameArray[++iIndex] = "lInstructionID";

			return nameArray;
		}

		public static final String[] getSignValueArrayFromInfo(FinanceInfo financeInfo)
		{
			int iIndex = -1;
			String[] valueArray = new String[iArrayLength];

			valueArray[++iIndex] = String.valueOf(financeInfo.getDepositNo()); // 存款单据号
			valueArray[++iIndex] = String.valueOf(financeInfo.getPayeeAcctID()); // 本金收款方账号（对应ID值）
			valueArray[++iIndex] = String.valueOf(financeInfo.getInterestPayeeAcctID()); // 利息收款方账号（对应ID值）
			valueArray[++iIndex] = DataFormat.format(financeInfo.getAmount(), 2); // 金额
		//	valueArray[++iIndex] = DataFormat.formatDate(financeInfo.getExecuteDate(), DataFormat.FMT_DATE_YYYYMMDD);// 执行日
			valueArray[++iIndex] = String.valueOf(financeInfo.getRemitType()); // 本金汇款方式
			valueArray[++iIndex] = String.valueOf(financeInfo.getInterestRemitType()); // 利息汇款方式
			valueArray[++iIndex] = String.valueOf(financeInfo.getConfirmUserID());
			valueArray[++iIndex] = String.valueOf(-1);
			return valueArray;
		}

		public static final String[] getSignValueArrayFromReq(HttpServletRequest request)
		{
			int iIndex = -1;
			String[] valueArray = new String[iArrayLength];

			valueArray[++iIndex] = request.getParameter("sFixedDepositNoCtrl"); // 存款单据号
			valueArray[++iIndex] = request.getParameter("nPayeeAccountID"); // 本金收款方账号（对应ID值）
			valueArray[++iIndex] = request.getParameter("nInterestPayeeAccountID"); // 利息收款方账号（对应ID值）
			valueArray[++iIndex] = DataFormat.format(Double.valueOf(
					DataFormat.reverseFormatAmount((String) request.getParameter("dAmount"))).doubleValue(), 2);// 金额
		//	valueArray[++iIndex] = request.getParameter("tsExecute"); // 执行日
			valueArray[++iIndex] = request.getParameter("nRemitTypeHidden"); // 本金汇款方式
			valueArray[++iIndex] = request.getParameter("nInterestRemitTypeHidden");// 利息汇款方式
			valueArray[++iIndex] = request.getParameter("submitUserId");
			//valueArray[++iIndex] = request.getParameter("lInstructionID");
			valueArray[++iIndex] = request.getParameter("lInstructionID");

			return valueArray;
		}

		public static final void outSignNameArrayToView(JspWriter out) throws IOException
		{
			int iIndex = -1;
			out.println("var nameArray = new Array();");
			out.println("var valueArray = new Array();");

			out.println("nameArray["+ ++iIndex +"] = \"sFixedDepositNoCtrl\";");
			out.println("nameArray["+ ++iIndex +"] = \"payeeAcctID\";");
			out.println("nameArray["+ ++iIndex +"] = \"interestPayeeAcctID\";");
			out.println("nameArray["+ ++iIndex +"] = \"amount\";");
		//	out.println("nameArray["+ ++iIndex +"] = \"executeDate\";");
			out.println("nameArray["+ ++iIndex +"] = \"remitType\";");
			out.println("nameArray["+ ++iIndex +"] = \"interestRemitType\";");
			out.println("nameArray["+ ++iIndex +"] = \"submitUserId\";");
			out.println("nameArray["+ ++iIndex +"] = \"lInstructionID\";");

		}

		public static final void outSignValueArrayToView(JspWriter out) throws IOException
		{
			int iIndex = -1;
			out.println("valueArray["+ ++iIndex +"] = frm.sFixedDepositNoCtrl.value;");
			out.println("valueArray["+ ++iIndex +"] = frm.nPayeeAccountID.value;");
			out.println("valueArray["+ ++iIndex +"] = frm.nInterestPayeeAccountID.value;");
			out.println("valueArray["+ ++iIndex +"] = parseFloat(reverseFormatAmount(frm.dAmount.value)).toFixed(2);");
		//	out.println("valueArray["+ ++iIndex +"] = frm.tsExecute.value;");
			out.println("valueArray["+ ++iIndex +"] = frm.nRemitTypeHidden.value;");
			out.println("valueArray["+ ++iIndex +"] = frm.nInterestRemitTypeHidden.value;");
			out.println("valueArray["+ ++iIndex +"] = frm.submitUserId.value;");
			//out.println("valueArray["+ ++iIndex +"] = frm.lInstructionID.value;");
			out.println("valueArray["+ ++iIndex +"] = frm.lInstructionID.value;;");
		}

	}

	// 定期续存
	public static class DriveFixdePosit
	{
		//modify by xwhe 2008-10-15
		public static int iArrayLength = 9;
		public static final String[] getSignNameArray()
		{
			int iIndex = -1;
			String[] nameArray = new String[iArrayLength];
			nameArray[++iIndex] = "nPayeeAccountID";// 定期账户ID
			nameArray[++iIndex] = "sPayerAccountNoZoomCtrl";// 定期存款子账号
			nameArray[++iIndex] = "dAmount";// 存单金额
			nameArray[++iIndex] = "nFixedDepositTime1";// 定期存款期限
	//		nameArray[++iIndex] = "tsExecute";// 执行日
			nameArray[++iIndex] = "dPayerCurrStartDate";// 新定期存款起始日
			nameArray[++iIndex] = "interesttype";// 利息处理方式
			nameArray[++iIndex] = "lInterestToAccountID";// 利息存入的活期账户
			nameArray[++iIndex] = "submitUserId";
			nameArray[++iIndex] = "lInstructionID";

			return nameArray;
		}

		public static final String[] getSignValueArrayFromInfo(FinanceInfo financeInfo)
		{
			int iIndex = -1;
			String[] valueArray = new String[iArrayLength];

			valueArray[++iIndex] = String.valueOf(financeInfo.getPayeeAcctID());
			valueArray[++iIndex] = String.valueOf(financeInfo.getSDepositBillNo());
			valueArray[++iIndex] = DataFormat.format(financeInfo.getAmount(), 2);
			valueArray[++iIndex] = String.valueOf(financeInfo.getSDepositBillPeriod());// 定期存款期限
		//	valueArray[++iIndex] = DataFormat.formatDate(financeInfo.getExecuteDate(), DataFormat.FMT_DATE_YYYYMMDD);
			valueArray[++iIndex] = DataFormat.formatDate(financeInfo.getSDepositBillStartDate(), DataFormat.FMT_DATE_YYYYMMDD);// 新定期存款起始日
			valueArray[++iIndex] = String.valueOf(financeInfo.getSDepositInterestDeal());// 利息处理方式
			valueArray[++iIndex] = String.valueOf(financeInfo.getSDepositInterestToAccountID());// 利息存入的活期账户
			valueArray[++iIndex] = String.valueOf(financeInfo.getConfirmUserID());
			valueArray[++iIndex] = String.valueOf(-1);
			return valueArray;
		}

		public static final String[] getSignValueArrayFromReq(HttpServletRequest request)
		{
			int iIndex = -1;
			String[] valueArray = new String[iArrayLength];
			valueArray[++iIndex] = request.getParameter("nPayeeAccountID");
			valueArray[++iIndex] = request.getParameter("sPayerAccountNoZoomCtrl");
			valueArray[++iIndex] = DataFormat.format(Double.valueOf(
			DataFormat.reverseFormatAmount((String) request.getParameter("dAmount"))).doubleValue(), 2);
			valueArray[++iIndex] = request.getParameter("nFixedDepositTime1");
		//	valueArray[++iIndex] = request.getParameter("tsExecute");
			valueArray[++iIndex] = request.getParameter("dPayerCurrStartDate");
			valueArray[++iIndex] = request.getParameter("interesttype");
			valueArray[++iIndex] = request.getParameter("lInterestToAccountID");
			valueArray[++iIndex] = request.getParameter("submitUserId");
			valueArray[++iIndex] = request.getParameter("lInstructionID");

			return valueArray;
		}

		public static final void outSignNameArrayToView(JspWriter out) throws IOException
		{
			int iIndex = -1;
			out.println("var nameArray = new Array();");
			out.println("var valueArray = new Array();");

			out.println("nameArray["+ ++iIndex +"] = \"nPayeeAccountID\";");
			out.println("nameArray["+ ++iIndex +"] = \"sPayerAccountNoZoomCtrl\";");
			out.println("nameArray["+ ++iIndex +"] = \"dAmount\";");
			out.println("nameArray["+ ++iIndex +"] = \"nFixedDepositTime1\";");
	//		out.println("nameArray["+ ++iIndex +"] = \"tsExecute\";");
			out.println("nameArray["+ ++iIndex +"] = \"dPayerCurrStartDate\";");
			out.println("nameArray["+ ++iIndex +"] = \"interesttype\";");
			out.println("nameArray["+ ++iIndex +"] = \"lInterestToAccountID\";");
			out.println("nameArray["+ ++iIndex +"] = \"submitUserId\";");
			out.println("nameArray["+ ++iIndex +"] = \"lInstructionID\";");

		}

		public static final void outSignValueArrayToView(JspWriter out) throws IOException
		{
			int iIndex = -1;
			out.println("valueArray["+ ++iIndex +"] = frm.nPayeeAccountID.value;");
			out.println("valueArray["+ ++iIndex +"] = frm.sPayerAccountNoZoomCtrl.value;");
			out.println("valueArray["+ ++iIndex +"] = parseFloat(reverseFormatAmount(frm.dAmount.value)).toFixed(2);");
			out.println("valueArray["+ ++iIndex +"] = frm.nFixedDepositTime1.value;");
		//	out.println("valueArray["+ ++iIndex +"] = frm.tsExecute.value;");

			out.println("valueArray["+ ++iIndex +"] = frm.dPayerCurrStartDate.value;");
			
//			radio 特殊处理
			out.println("var interesttypeTemp=2;");
			out.println("for(i=0;i<frm.interesttype.length;i++)" 
					+ "{" + "if(frm.interesttype[i].checked==true)"
					+ "{interesttypeTemp=frm.interesttype[i].value}" + "}");

			out.println("valueArray["+ ++iIndex +"] = interesttypeTemp;");
			out.println("valueArray["+ ++iIndex +"] = frm.lInterestToAccountID.value;");
			out.println("valueArray["+ ++iIndex +"] = frm.submitUserId.value;");
			out.println("valueArray["+ ++iIndex +"] = frm.lInstructionID.value;;");
		}

		public static final void outSignValueArrayToViewForView(JspWriter out) throws IOException
		{
			int iIndex = -1;
			out.println("valueArray["+ ++iIndex +"] = frm.nPayeeAccountID.value;");
			out.println("valueArray["+ ++iIndex +"] = frm.sPayerAccountNoZoomCtrl.value;");
			out.println("valueArray["+ ++iIndex +"] = parseFloat(reverseFormatAmount(frm.dAmount.value)).toFixed(2);");
			out.println("valueArray["+ ++iIndex +"] = frm.nFixedDepositTime1.value;");
		//	out.println("valueArray["+ ++iIndex +"] = frm.tsExecute.value;");

			out.println("valueArray["+ ++iIndex +"] = frm.dPayerCurrStartDate.value;");
			out.println("valueArray["+ ++iIndex +"] = frm.interesttype.value;");
			out.println("valueArray["+ ++iIndex +"] = frm.lInterestToAccountID.value;");

			out.println("valueArray["+ ++iIndex +"] = frm.submitUserId.value;");
//			out.println("valueArray["+ ++iIndex +"] = frm.lInstructionID.value;");
			out.println("valueArray["+ ++iIndex +"] = '-1';");
		}
		
	}

	//	 定期转存
	public static class ChangeFixdeposit
	{
       //modify by xwhe 2008-10-15
		public static int iArrayLength = 9;
		public static final String[] getSignNameArray()
		{
			int iIndex = -1;
			String[] nameArray = new String[iArrayLength];
			nameArray[++iIndex] = "nPayeeAccountID";// 定期账户ID
			nameArray[++iIndex] = "sPayerAccountNoZoomCtrl";// 定期存款子账号
			nameArray[++iIndex] = "dAmount";// 存单金额
			nameArray[++iIndex] = "nFixedDepositTime1";// 定期存款期限
		//	nameArray[++iIndex] = "tsExecute";// 执行日
			nameArray[++iIndex] = "dPayerStartDate";// 新定期存款起始日
			nameArray[++iIndex] = "interesttype";// 利息处理方式
			nameArray[++iIndex] = "lInterestToAccountID";// 利息存入的活期账户
			nameArray[++iIndex] = "submitUserId";
			nameArray[++iIndex] = "lInstructionID";

			return nameArray;
		}

		public static final String[] getSignValueArrayFromInfo(FinanceInfo financeInfo)
		{
			int iIndex = -1;
			String[] valueArray = new String[iArrayLength];

			valueArray[++iIndex] = String.valueOf(financeInfo.getPayeeAcctID());
			valueArray[++iIndex] = String.valueOf(financeInfo.getSDepositBillNo());
			valueArray[++iIndex] = DataFormat.format(financeInfo.getAmount(), 2);
			valueArray[++iIndex] = String.valueOf(financeInfo.getFixedDepositTime());// 定期存款期限
		//	valueArray[++iIndex] = DataFormat.formatDate(financeInfo.getExecuteDate(), DataFormat.FMT_DATE_YYYYMMDD);
			valueArray[++iIndex] = DataFormat.formatDate(financeInfo.getSDepositBillStartDate(), DataFormat.FMT_DATE_YYYYMMDD);// 新定期存款起始日
			valueArray[++iIndex] = String.valueOf(financeInfo.getSDepositInterestDeal());// 利息处理方式
			valueArray[++iIndex] = String.valueOf(financeInfo.getSDepositInterestToAccountID());// 利息存入的活期账户
			valueArray[++iIndex] = String.valueOf(financeInfo.getConfirmUserID());
			//valueArray[++iIndex] = String.valueOf(financeInfo.getID());
			valueArray[++iIndex] = String.valueOf(-1);

			return valueArray;
		}

		public static final String[] getSignValueArrayFromReq(HttpServletRequest request)
		{
			int iIndex = -1;
			String[] valueArray = new String[iArrayLength];

			valueArray[++iIndex] = request.getParameter("nPayeeAccountID");
			valueArray[++iIndex] = request.getParameter("sPayerAccountNoZoomCtrl");
			valueArray[++iIndex] = DataFormat.format(Double.valueOf(
					DataFormat.reverseFormatAmount((String) request.getParameter("dAmount"))).doubleValue(), 2);
			valueArray[++iIndex] = request.getParameter("nFixedDepositTime1");
		//	valueArray[++iIndex] = request.getParameter("tsExecute");
			valueArray[++iIndex] = request.getParameter("dPayerStartDate");
			valueArray[++iIndex] = request.getParameter("interesttype");
			valueArray[++iIndex] = request.getParameter("lInterestToAccountID");
			valueArray[++iIndex] = request.getParameter("submitUserId");
//			valueArray[++iIndex] = request.getParameter("lInstructionID");
			valueArray[++iIndex] = "-1";

			return valueArray;
		}

		public static final void outSignNameArrayToView(JspWriter out) throws IOException
		{
			int iIndex = -1;
			out.println("var nameArray = new Array();");
			out.println("var valueArray = new Array();");

			out.println("nameArray["+ ++iIndex +"] = \"nPayeeAccountID\";");
			out.println("nameArray["+ ++iIndex +"] = \"sPayerAccountNoZoomCtrl\";");
			out.println("nameArray["+ ++iIndex +"] = \"dAmount\";");
			out.println("nameArray["+ ++iIndex +"] = \"nFixedDepositTime1\";");
		//	out.println("nameArray["+ ++iIndex +"] = \"tsExecute\";");
			out.println("nameArray["+ ++iIndex +"] = \"dPayerStartDate\";");
			out.println("nameArray["+ ++iIndex +"] = \"interesttype\";");
			out.println("nameArray["+ ++iIndex +"] = \"lInterestToAccountID\";");
			out.println("nameArray["+ ++iIndex +"] = \"submitUserId\";");
			out.println("nameArray["+ ++iIndex +"] = \"lInstructionID\";");

		}

		public static final void outSignValueArrayToView(JspWriter out) throws IOException
		{
			int iIndex = -1;
			out.println("valueArray["+ ++iIndex +"] = frm.nPayeeAccountID.value;");
			out.println("valueArray["+ ++iIndex +"] = frm.sPayerAccountNoZoomCtrl.value;");
			out.println("valueArray["+ ++iIndex +"] = parseFloat(reverseFormatAmount(frm.dAmount.value)).toFixed(2);");
			out.println("valueArray["+ ++iIndex +"] = frm.nFixedDepositTime1.value;");
		//	out.println("valueArray[++iIndex] = frm.tsExecute.value;");

			out.println("valueArray["+ ++iIndex +"] = frm.dPayerStartDate.value;");
			
//			radio 特殊处理
			out.println("var interesttypeTemp=2;");
			out.println("for(i=0;i<frm.interesttype.length;i++)" 
					+ "{" + "if(frm.interesttype[i].checked==true)"
					+ "{interesttypeTemp=frm.interesttype[i].value}" + "}");

			out.println("valueArray["+ ++iIndex +"] = interesttypeTemp;");
			out.println("valueArray["+ ++iIndex +"] = frm.lInterestToAccountID.value;");

			out.println("valueArray["+ ++iIndex +"] = frm.submitUserId.value;");
//			out.println("valueArray["+ ++iIndex +"] = frm.lInstructionID.value;");
			out.println("valueArray["+ ++iIndex +"] = '-1';");
		}

		public static final void outSignValueArrayToViewForView(JspWriter out) throws IOException
		{
			int iIndex = -1;
			out.println("valueArray["+ ++iIndex +"] = frm.nPayeeAccountID.value;");
			out.println("valueArray["+ ++iIndex +"] = frm.sPayerAccountNoZoomCtrl.value;");
			out.println("valueArray["+ ++iIndex +"] = parseFloat(reverseFormatAmount(frm.dAmount.value)).toFixed(2);");
			out.println("valueArray["+ ++iIndex +"] = frm.nFixedDepositTime1.value;");
		//	out.println("valueArray["+ ++iIndex +"] = frm.tsExecute.value;");

			out.println("valueArray["+ ++iIndex +"] = frm.dPayerStartDate.value;");
			out.println("valueArray["+ ++iIndex +"] = frm.interesttype.value;");
			out.println("valueArray["+ ++iIndex +"] = frm.lInterestToAccountID.value;");

			out.println("valueArray["+ ++iIndex +"] = frm.submitUserId.value;");
//			out.println("valueArray["+ ++iIndex +"] = frm.lInstructionID.value;");
			out.println("valueArray["+ ++iIndex +"] = '-1';");
		}
		
	}
	
	// 资金划拨
	//modified by mzh_fu 2007/12/20 根据产品组项目经理与保利项目的要求,将签名要素"收款方ID(payeeAcctID)"去掉.
	public static class CapTransfer
	{	
		public static int iArrayLength = 4;

		public static final String[] getSignNameArray()
		{
			int iIndex = -1;
			String[] nameArray = new String[iArrayLength];
			
			//nameArray[++iIndex] = "payeeAcctID";
			nameArray[++iIndex] = "payerAcctID";
			nameArray[++iIndex] = "amount";
	//		nameArray[++iIndex] = "executeDate";
			nameArray[++iIndex] = "submitUserId";
			nameArray[++iIndex] = "lInstructionID";

			return nameArray;
		}

		public static final String[] getSignValueArrayFromInfo(FinanceInfo financeInfo)
		{
			int iIndex = -1;
			String[] valueArray = new String[iArrayLength];
			//valueArray[++iIndex] = String.valueOf(financeInfo.getPayeeAcctID());
			valueArray[++iIndex] = String.valueOf(financeInfo.getPayerAcctID());
			valueArray[++iIndex] = DataFormat.format(financeInfo.getAmount(), 2);
	//		valueArray[++iIndex] = DataFormat.formatDate(financeInfo.getExecuteDate(), DataFormat.FMT_DATE_YYYYMMDD);
			valueArray[++iIndex] = String.valueOf(financeInfo.getConfirmUserID());
			valueArray[++iIndex] = String.valueOf(-1);
			return valueArray;
		}
		
		public static final String[] getSignValueArrayFromInfo(Query_FinanceInfo financeInfo)
		{
			int iIndex = -1;
			String[] valueArray = new String[iArrayLength];
			valueArray[++iIndex] = String.valueOf(financeInfo.getNpayeracctid());
			valueArray[++iIndex] = DataFormat.format(financeInfo.getMAmount(), 2);
			valueArray[++iIndex] = String.valueOf(financeInfo.getConfirmUserID());
			valueArray[++iIndex] = "-1";
			return valueArray;
		}
		
		
		public static final String[] getSignValueArrayFromReq(HttpServletRequest request)
		{
			int iIndex = -1;
			String[] valueArray = new String[iArrayLength];

			//valueArray[++iIndex] = request.getParameter("nPayeeAccountID"); // 收款方
			valueArray[++iIndex] = request.getParameter("nPayerAccountID"); // 付款方
			valueArray[++iIndex] = DataFormat.format(Double.valueOf(DataFormat.reverseFormatAmount((String) request.getParameter("dAmount"))).doubleValue(), 2);
	//		valueArray[++iIndex] = request.getParameter("tsExecute");
			valueArray[++iIndex] = request.getParameter("submitUserId");
//			valueArray[++iIndex] = request.getParameter("lInstructionID");
			valueArray[++iIndex] = request.getParameter("lInstructionID");

			return valueArray;
		}

		public static final void outSignNameArrayToView(JspWriter out) throws IOException
		{

			int iIndex = -1;
			
			out.println("var nameArray = new Array();");
			out.println("var valueArray = new Array();");

			//out.println("nameArray["+ ++iIndex +"] = \"payeeAcctID\";");
			out.println("nameArray["+ ++iIndex +"] = \"payerAcctID\";");
			out.println("nameArray["+ ++iIndex +"] = \"amount\";");
		//	out.println("nameArray["+ ++iIndex +"] = \"executeDate\";");
			out.println("nameArray["+ ++iIndex +"] = \"submitUserId\";");
			out.println("nameArray["+ ++iIndex +"] = \"lInstructionID\";");

		}

		public static final void outSignValueArrayToView(JspWriter out, String strForm) throws IOException
		{
			int iIndex = -1;
			
			//out.println("valueArray["+ ++iIndex +"] = "+ strForm +".nPayeeAccountID.value;");
			out.println("valueArray["+ ++iIndex +"] = "+ strForm +".nPayerAccountID.value;");
			out.println("valueArray["+ ++iIndex +"] = parseFloat(reverseFormatAmount("+ strForm +".dAmount.value)).toFixed(2);");
		//	out.println("valueArray["+ ++iIndex +"] = "+ strForm +".tsExecute.value;");
			out.println("valueArray["+ ++iIndex +"] = "+ strForm +".submitUserId.value;");
//			out.println("valueArray["+ ++iIndex +"] = "+ strForm +".lInstructionID.value;");
			out.println("valueArray["+ ++iIndex +"] = "+ strForm +".lInstructionID.value;");
		}
	}
	
	public static String[] getNameArrayByAllOperation(FinanceInfo info) throws Exception
	{
		String[] nameArray = null;
    	if(info.getTransType()==OBConstant.SettInstrType.CAPTRANSFER_BANKPAY||info.getTransType()==OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT)
    	{
    		nameArray = OBSignatureConstant.CapTransfer.getSignNameArray();
 
    	}else if(info.getTransType()==OBConstant.SettInstrType.OPENFIXDEPOSIT)
    	{
    		nameArray = OBSignatureConstant.OpenFixDeposit.getSignNameArray();
         		
    	}else if(info.getTransType()==OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER)
    	{
    		nameArray = OBSignatureConstant.FixedToCurrentTransfer.getSignNameArray();
             		
    	}else if(info.getTransType()==OBConstant.SettInstrType.OPENNOTIFYACCOUNT)
    	{
    		nameArray = OBSignatureConstant.OpenNotifyAccount.getSignNameArray();
               		
    	}else if(info.getTransType()==OBConstant.SettInstrType.NOTIFYDEPOSITDRAW)
    	{
    		nameArray = OBSignatureConstant.NotifyDepositDraw.getSignNameArray();
                		
    	}else if(info.getTransType()==OBConstant.SettInstrType.DRIVEFIXDEPOSIT)
    	{
        	nameArray = OBSignatureConstant.DriveFixdePosit.getSignNameArray();
        		            		
    	}		
		return nameArray;
	}
	
	public static String[] getValueArrayByAllOperation(FinanceInfo info) throws Exception	
	{
		String[] valueArray = null;
    	if(info.getTransType()==OBConstant.SettInstrType.CAPTRANSFER_BANKPAY||info.getTransType()==OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT)
    	{
    		valueArray = OBSignatureConstant.CapTransfer.getSignValueArrayFromInfo(info);
    	}else if(info.getTransType()==OBConstant.SettInstrType.OPENFIXDEPOSIT)
    	{
    		valueArray = OBSignatureConstant.OpenFixDeposit.getSignValueArrayFromInfo(info);            		
    	}else if(info.getTransType()==OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER)
    	{
    		valueArray = OBSignatureConstant.FixedToCurrentTransfer.getSignValueArrayFromInfo(info);               		
    	}else if(info.getTransType()==OBConstant.SettInstrType.OPENNOTIFYACCOUNT)
    	{
    		valueArray = OBSignatureConstant.OpenNotifyAccount.getSignValueArrayFromInfo(info);               		
    	}else if(info.getTransType()==OBConstant.SettInstrType.NOTIFYDEPOSITDRAW)
    	{
    		valueArray = OBSignatureConstant.NotifyDepositDraw.getSignValueArrayFromInfo(info);              		
    	}else if(info.getTransType()==OBConstant.SettInstrType.DRIVEFIXDEPOSIT)
    	{
        	valueArray = OBSignatureConstant.DriveFixdePosit.getSignValueArrayFromInfo(info);    	            		
    	}		
		return valueArray;
	}
	
	public static FinanceInfo transFinanceInfo(Query_FinanceInfo qInfo)throws Exception
	{
		FinanceInfo info = new FinanceInfo();
		info.setID(qInfo.getID());
		info.setDepositNo(qInfo.getDepositNo());
		info.setPayeeAcctID(qInfo.getNpayeeacctid());
		info.setPayerAcctID(qInfo.getNpayeracctid());
		info.setInterestPayeeAcctID(qInfo.getInterestPayeeAcctID());
		info.setAmount(qInfo.getMAmount());
		info.setRemitType(qInfo.getRemitType());
		info.setInterestRemitType(qInfo.getInterestRemitType());
		info.setConfirmUserID(qInfo.getConfirmUserID());
		info.setTransType(qInfo.getNtranstype());
		info.setNoticeDay(qInfo.getNoticeDay());
		info.setSDepositBillNo(qInfo.getDepositBillNo());
		info.setSDepositBillPeriod(qInfo.getDepositBillPeriod());
		info.setSDepositBillStartDate(qInfo.getDepositBillStartDate());
		info.setSDepositInterestDeal(qInfo.getDepositInterestDeal());
		info.setSDepositInterestToAccountID(qInfo.getDepositInterestToAccountID());
		info.setFixedDepositTime(qInfo.getFixedDepositTime());
		info.setSignatureValue(qInfo.getSignatureValue());
		info.setTimestamp(qInfo.getTimestamp());
		return info;
	}
}
