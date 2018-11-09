<%--
/*
 * 程序名称：c003-c.jsp
 * 功能说明：资金划拨提交控制页面
 * 作　　者：刘琰
 * 完成日期：2004年01月06日 
 */
--%>

<!--Header start-->
<%@ page contentType="text/html;charset=gbk"%>
<%@ page import="java.sql.*"%>
<%@ page import="com.iss.itreasury.util.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.obsystem.bizlogic.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dao.*"%>
<%@ page import="com.iss.itreasury.ebank.obsystem.dataentity.*"%>
<%@ page import="com.iss.itreasury.budget.util.*"%>
<%@ page import="com.iss.itreasury.budget.executecontrol.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page
	import="com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo"%>
<%@ page import="com.iss.itreasury.common.attach.bizlogic.*"%>
<%@ page import="com.iss.itreasury.safety.util.*"%>
<%@ page import="com.iss.itreasury.safety.signature.*"%>
<%@ page import="com.iss.itreasury.safety.info.*"%>
<%@ page
	import="com.iss.itreasury.settlement.setting.dataentity.OBCommitTime"%>
<%@ page
	import="com.iss.itreasury.settlement.setting.bizlogic.OBCommitTimeBiz"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="com.iss.itreasury.system.translog.dataentity.*"%>
<%@ page import="com.iss.itreasury.system.translog.bizlogic.*"%>
<jsp:useBean id="sessionMng" scope="session"
	class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp"%>
<!--Header end-->
<%
	String strContext = request.getContextPath();
%>
<%!/* 标题固定变量 */
	String strTitle = "[资金划拨]";%>
<%
	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String) request.getParameter("menu");
	Log.print("--------strMenu=" + strMenu);
	if ((strMenu != null) && (strMenu.equals("hidden"))) {
		lShowMenu = OBConstant.ShowMenu.NO;
	}
	request.setAttribute("menu", strMenu);
	
	String lsign = null;  //判断是否为重新提交
	String sign = "";
	lsign = request.getParameter("sign");
	if(lsign!=null&&lsign.trim().length()>0)
	{
		sign = lsign;
	}
	
%>
<%
	/* 定义对表单的相应变量 */
	long lInstructionID = -1;//指令序号
	double dPayerCurrentBalance = 0.0;//当前余额
	double dPayerUsableBalance = 0.0;//可用余额
	long lChildClientID = -1;//下属单位ID
	String strPayerName = "";//  付款方名称
	String strPayerBankNo = "";// 银行账号
	String strPayerAcctNo = "";// 付款方账号
	long lPayerAcctID = -1;//付款方账户ID
	long lRemitType = 0;// 汇款方式
	long lPayeeAcctID = -1;//收款方账户ID
	String strPayeeName = "";// 收款方名称
	String strPayeeBankNo = "";// 收款方银行账号
	String strPayeeAcctNo = "";// 收款方账号
	String strPayeeBankName = "";// 汇入行名称
	String strPayeeProv = "";// 汇入省
	String strPayeeCity = "";// 汇入市
	double dAmount = 0.0;// 金额
	Timestamp tsExecute = null;// 执行日
	String strNote = "";// 汇款用途
	Timestamp dtmodify = null;
	String sApplyCode = ""; //外部系统指令序号
	String sPayeeBankCNAPSNO = "";  //汇入行CNAPS号
	String sPayeeBankOrgNO = "";//汇入行机构号
	String sPayeeBankExchangeNO = "";//汇入行联行号

	String tempTransCode = ""; //临时交易号
	String strTemp = "";
	TransInfo transinfo = new TransInfo();

	//Modify by leiyang date 2007/07/24
	boolean isSommitTimes = false;
	String msg = "";

	//增加取预算项目方法
	long lBudgetItemID = -1;
	
	long remitArea = -1;	//汇款区域
	long remitSpeed = -1;	//汇款速度
	
	/* 初始化信息类 */
	FinanceInfo financeInfo = new FinanceInfo();
	ClientCapInfo clientCapInfo = new ClientCapInfo();
	
	Timestamp now = new Timestamp(System.currentTimeMillis());//获取系统当前时间
%>

<%
	/* 用户登录检测与权限校验 */
	try {

		// 用户登录检测
		if (sessionMng.isLogin() == false) {
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E002");
			out.flush();
			return;
		}

		// 判断用户是否有权限 
		if (sessionMng.hasRight(request) == false) {
			out.println(sessionMng.hasRight(request));
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
			out.flush();
			return;
		}
%>

<!--Get Data start-->
<%

		OBAbstractSettingBiz OBAbstractSetting = new OBAbstractSettingBiz();
        OBAbstractSettingInfo OBinfo = new OBAbstractSettingInfo();
	/* 从FORM表单中获取相应数据 */
		/* 指令序号 */
		long strAction = -1;
		String strNextPage = "";
		String sTemp = "";
		long AbstractID = -1;//汇款用途ID
		String AbstractCode = "";//汇款用途编号
		
		lInstructionID = GetNumParam(request, "lInstructionID");
		Log.print("指令序号=" + lInstructionID);
		/*汇款方式*/
		if (GetNumParam(request, "nRemitType") > 0) {
			lRemitType = GetNumParam(request, "nRemitType");// 汇款方式
			Log.print("汇款方式=" + lRemitType);
		} else {
			lRemitType = GetNumParam(request, "nRemitTypeHidden");// 汇款方式
			Log.print("汇款方式=" + lRemitType);
		}

		/* 付款方资料 */
		lChildClientID = GetNumParam(request, "lClientID");
		Log.print("下属单位ID=" + lChildClientID);

		strPayerName = GetParam(request, "sPayerName");// 付款方名称
		Log.print("付款方名称=" + strPayerName);

		//System.out.println(request.getParameter("nPayerAccountID"));
		lPayerAcctID = GetNumParam(request, "nPayerAccountID");//付款方账户ID
		/*中交，下划的付款方id*/
		if (lRemitType == OBConstant.SettRemitType.BANKPAY_DOWNTRANSFER) {
			lPayerAcctID = GetNumParam(request, "nPayerAccountIDDown");
		}
		Log.print("付款方账户ID=" + lPayerAcctID);

		strPayerBankNo = GetParam(request, "sBankAccountNO");// 银行账号
		Log.print("银行账号=" + strPayerBankNo);

		strPayerAcctNo = GetParam(request, "sPayerAccountNoZoomCtrl");// 付款方账号
		//中交，下划，付款方账号
		if (lRemitType == OBConstant.SettRemitType.BANKPAY_DOWNTRANSFER) {
			strPayerAcctNo = GetParam(request,
					"sPayerAccountNoDownZoomCtrl");// 付款方账号
		}
		Log.print("付款方账号=" + strPayerAcctNo);

		if (request.getParameter("dPayerCurrBalance") != null
				&& ((String) request.getParameter("dPayerCurrBalance"))
						.length() > 0) {
			dPayerCurrentBalance = Double.valueOf(
					DataFormat.reverseFormatAmount((String) request
							.getParameter("dPayerCurrBalance")))
					.doubleValue();// 当前余额
			Log.print("当前余额=" + dPayerCurrentBalance);
		}

		if (request.getParameter("dPayerUsableBalance") != null
				&& ((String) request
						.getParameter("dPayerUsableBalance")).length() > 0) {
			dPayerUsableBalance = Double.valueOf(
					DataFormat.reverseFormatAmount((String) request
							.getParameter("dPayerUsableBalance")))
					.doubleValue();// 可用余额
			Log.print("可用余额=" + dPayerUsableBalance);
		}
		//中交，下划  ，当前余额和可用余额
		if (lRemitType == OBConstant.SettRemitType.BANKPAY_DOWNTRANSFER) {
			if (request.getParameter("dPayerCurrBalanceDown") != null
					&& ((String) request
							.getParameter("dPayerCurrBalanceDown"))
							.length() > 0) {
				dPayerCurrentBalance = Double
						.valueOf(
								DataFormat
										.reverseFormatAmount((String) request
												.getParameter("dPayerCurrBalanceDown")))
						.doubleValue();// 当前余额
				Log.print("当前余额=" + dPayerCurrentBalance);
			}

			if (request.getParameter("dPayerUsableBalanceDown") != null
					&& ((String) request
							.getParameter("dPayerUsableBalanceDown"))
							.length() > 0) {
				dPayerUsableBalance = Double
						.valueOf(
								DataFormat
										.reverseFormatAmount((String) request
												.getParameter("dPayerUsableBalanceDown")))
						.doubleValue();// 可用余额
				Log.print("可用余额=" + dPayerUsableBalance);
			}
		}

		/* 收款方资料 */

		lPayeeAcctID = GetNumParam(request, "nPayeeAccountID");//收款方账户ID
		//中交 ，下划，收款方账户id
		if (lRemitType == OBConstant.SettRemitType.BANKPAY_DOWNTRANSFER) {
			lPayeeAcctID = GetNumParam(request, "nPayeeAccountIDDown");
		}
		Log.print("收款方账户ID=" + lPayeeAcctID);

		switch ((int) lRemitType) {
		/* 汇款方式本转 */
		case (int) OBConstant.SettRemitType.SELF:

			strPayeeName = GetParam(request, "sPayeeNameZoomBankCtrl");//收款方名称
			Log.print("收款方名称=" + strPayeeName);

			strPayeeAcctNo = GetParam(request, "sPayeeBankNoZoomCtrl");// 收款方账号
			Log.print("收款方账号=" + strPayeeAcctNo);

			strPayeeBankName = GetParam(request, "sPayeeBankNameRead");// 汇入行名称
			Log.print("汇入行名称=" + strPayeeBankName);

			break;

		/* 汇款方式电汇、信汇、票汇 */
		case (int) OBConstant.SettRemitType.BANKPAY:
			strPayeeName = GetParam(request, "sPayeeNameZoomAcctCtrl");// 收款方名称
			Log.print("收款方名称=" + strPayeeName);

			strPayeeAcctNo = GetParam(request, "sPayeeAcctNoZoomCtrl");// 收款方账号
			Log.print("收款方账号=" + strPayeeAcctNo);

			strPayeeProv = GetParam(request, "sPayeeProv");// 汇入省
			Log.print("汇入省=" + strPayeeProv);

			strPayeeCity = GetParam(request, "sPayeeCity");// 汇入市
			Log.print("汇入市=" + strPayeeCity);

			strPayeeBankName = GetParam(request, "sPayeeBankName");// 汇入行名称
			Log.print("汇入行名称=" + strPayeeBankName);
			
			sPayeeBankCNAPSNO = GetParam(request, "sPayeeBankCNAPSNO");
			Log.print("汇入行CNAPS号=" + sPayeeBankCNAPSNO);
			
			sPayeeBankOrgNO = GetParam(request, "sPayeeBankOrgNO");
			Log.print("汇入行机构号=" + sPayeeBankOrgNO);
			
			sPayeeBankExchangeNO = GetParam(request, "sPayeeBankExchangeNO");
			Log.print("汇入行联行号=" + sPayeeBankExchangeNO);
			

			//预算新增
			lBudgetItemID = GetNumParam(request, "itemID");// 预算ID
			Log.print("预算ID=" + lBudgetItemID);
			break;
		/*汇款方式为－－下划成员单位*/
		case (int) OBConstant.SettRemitType.BANKPAY_DOWNTRANSFER:
			strPayeeName = GetParam(request,
					"sPayeeNameZoomAcctDownCtrl");// 收款方名称
			Log.print("收款方名称=" + strPayeeName);

			strPayeeAcctNo = GetParam(request,
					"sPayeeAcctNoDownZoomCtrl");// 收款方账号
			Log.print("收款方账号=" + strPayeeAcctNo);

			strPayeeProv = GetParam(request, "sPayeeProvDown");// 汇入省
			Log.print("汇入省=" + strPayeeProv);

			strPayeeCity = GetParam(request, "sPayeeCityDown");// 汇入市
			Log.print("汇入市=" + strPayeeCity);

			strPayeeBankName = GetParam(request, "sPayeeBankNameDown");// 汇入行名称
			Log.print("汇入行名称=" + strPayeeBankName);

			//预算新增
			lBudgetItemID = GetNumParam(request, "itemID");// 预算ID
			Log.print("预算ID=" + lBudgetItemID);
			break;
		/* 汇款方式-银行付款-财司代付 */
		case (int) OBConstant.SettRemitType.FINCOMPANYPAY:
			strPayeeName = GetParam(request, "sPayeeNameZoomAcctCtrl");// 收款方名称
			Log.print("收款方名称=" + strPayeeName);

			strPayeeAcctNo = GetParam(request, "sPayeeAcctNoZoomCtrl");// 收款方账号
			Log.print("收款方账号=" + strPayeeAcctNo);

			strPayeeProv = GetParam(request, "sPayeeProv");// 汇入省
			Log.print("汇入省=" + strPayeeProv);

			strPayeeCity = GetParam(request, "sPayeeCity");// 汇入市
			Log.print("汇入市=" + strPayeeCity);

			strPayeeBankName = GetParam(request, "sPayeeBankName");// 汇入行名称
			Log.print("汇入行名称=" + strPayeeBankName);

			//预算新增
			lBudgetItemID = GetNumParam(request, "itemID");// 预算ID
			Log.print("预算ID=" + lBudgetItemID);
			break;
		/* 汇款方式-银行付款-拨子账户 */
		case (int) OBConstant.SettRemitType.PAYSUBACCOUNT:
			strPayeeName = GetParam(request, "sPayeeNameZoomAcctCtrl");// 收款方名称
			Log.print("收款方名称=" + strPayeeName);

			strPayeeAcctNo = GetParam(request, "sPayeeAcctNoZoomCtrl");// 收款方账号
			Log.print("收款方账号=" + strPayeeAcctNo);

			strPayeeProv = GetParam(request, "sPayeeProv");// 汇入省
			Log.print("汇入省=" + strPayeeProv);

			strPayeeCity = GetParam(request, "sPayeeCity");// 汇入市
			Log.print("汇入市=" + strPayeeCity);

			strPayeeBankName = GetParam(request, "sPayeeBankName");// 汇入行名称
			Log.print("汇入行名称=" + strPayeeBankName);

			//预算新增
			lBudgetItemID = GetNumParam(request, "itemID");// 预算ID
			Log.print("预算ID=" + lBudgetItemID);
			break;
		/* 汇款方式内部转账 */
		case (int) OBConstant.SettRemitType.INTERNALVIREMENT:

			strPayeeName = GetParam(request, "sPayeeName");// 收款方名称
			Log.print("收款方名称=" + strPayeeName);

			strPayeeBankNo = GetParam(request,
					"sPayeeAccountInternalInternal");// 收款方银行账号
			Log.print("收款方银行账号(Iternal)=" + strPayeeBankNo);

			strPayeeAcctNo = GetParam(request,
					"sPayeeAccountInternalCtrl");// 收款方账号
			Log.print("收款方账号=" + strPayeeAcctNo);

			break;
		/* 汇款方式内部转账 */
		case (int) OBConstant.SettRemitType.OTHER:

			strPayeeName = GetParam(request, "sPayeeNameOther");// 收款方名称
			Log.print("收款方名称=" + strPayeeName);

			break;

		default:
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,
					"Gen_E001");
			return;
		}
		/* 划款资料 */
		if (request.getParameter("dAmount") != null) {
			dAmount = Double.valueOf(
					DataFormat.reverseFormatAmount((String) request
							.getParameter("dAmount"))).doubleValue();// 金额
			Log.print("金额=" + dAmount);
		}
		if (request.getParameter("tsExecute") != null) {
			tsExecute = DataFormat.getDateTime((String) request
					.getParameter("tsExecute"));// 执行日
			Log.print("执行日=" + tsExecute);
		}

		strNote = GetParam(request, "sNoteCtrl").trim();// 汇款用途
		Log.print("汇款用途=" + strNote);
		

		strTemp = (String) request.getAttribute("strTransTypeNo");
		if (strTemp != null && strTemp.trim().length() > 0) {
			tempTransCode = strTemp;
		}
		strTemp = (String) request.getAttribute("strAction");
		if (strTemp != null && strTemp.trim().length() > 0) {
			strAction = Long.parseLong(strTemp);
		}

		//Boxu Add 2010-12-01 增加"本地/异地"和"是否加急"
		strTemp = (String)request.getParameter("remitArea");
		if(strTemp != null && !strTemp.equals("")){
			remitArea = Long.parseLong(strTemp);
		}
		strTemp = (String)request.getParameter("remitSpeed");
		if(strTemp != null && !strTemp.equals("")){
			remitSpeed = Long.parseLong(strTemp);
		}
		
		strTemp = (String)request.getParameter("sApplyCode");
		if(strTemp != null && !strTemp.equals("")){
			sApplyCode = strTemp;
		}
		
		//MODIFY BY XWHE 2008-11-30
		String sbatchNO = "";
		strTemp = (String)request.getParameter("sbatchNO");
		if (strTemp != null && strTemp.trim().length() > 0 && !"null".equals(strTemp)) {
			sbatchNO = strTemp.trim();
		}		
		
		if (request.getParameter("dtmodify") != null&&!request.getParameter("dtmodify").equals("null")) {
			dtmodify = DataFormat.getDateTime((String) request
					.getParameter("dtmodify"));// 执行日
			Log.print("上次修改时间=" + dtmodify);
		}
		System.out.println("jzw start");
		/**判断指令的执行时间是否已超时系统的最迟接收时间开始*/
		//取消审批不作此验证，Modify by leiyang date 2007/07/24
		if (OBConstant.SettInstrStatus.CANCELAPPRVOAL != strAction) {

			Timestamp timeNow = Env.getSystemDate(
					sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);

			//当执行日等于开机日
			if (tsExecute.compareTo(timeNow) == 0) {
				String strCommitTime = "";
				long isControl = -1;

				OBCommitTime cTime = new OBCommitTime();
				cTime.setOfficeId(sessionMng.m_lOfficeID);
				cTime.setCurrencyId(sessionMng.m_lCurrencyID);
				OBCommitTime result = OBCommitTimeBiz
						.findOBCommitTime(cTime);

				if (result != null) {
					strCommitTime = result.getCommitTime();
					isControl = result.getIsControl();

					timeNow = Env.getSystemDateTime();

					//当前小时和分钟
					int lTNHours = timeNow.getHours();
					int lTNMinutes = timeNow.getMinutes();

					String commitTimes[] = strCommitTime.split(":");
					//停止接收的小时和分钟
					int lCTHours = Integer.parseInt(commitTimes[0]);
					int lCTMinutes = Integer.parseInt(commitTimes[1]);
					
					boolean isPassCommitTime = false;
					if (lCTHours < lTNHours 
							||(lCTHours == lTNHours && lCTMinutes < lTNMinutes)) {
						isPassCommitTime = true;
					}
					
					if(isPassCommitTime){
						if (isControl == SETTConstant.OBCommitTimeControlType.RIGID) 
						{
							throw new IException("提交时间已超过结算最迟接收时间");
						}else {
							//如果是柔性控制，自动将执行时间沿后一天
							tsExecute.setDate(tsExecute.getDate() + 1);
							isSommitTimes = true;
						}
					}
				}
			}
		}
		/**判断指令的执行时间是否已超时系统的最迟接收时间--结束*/

		/**数字签名校验，判断信息是否被篡改--开始*/
		//added by mingfang 2007/05/18 数字签名	 
		String strTroyName = Config.getProperty(
				ConfigConstant.GLOBAL_TROY_NAME,
				Constant.GlobalTroyName.NotUseCertificate);
		//得到客户端签名值
		String signatureValue = request
				.getParameter(SignatureConstant.SIGNATUREVALUE);
		String signatureOriginalValue = "";

		if (strTroyName.equals(Constant.GlobalTroyName.ITrus)
				&& (strAction == OBConstant.SettInstrStatus.DOAPPRVOAL
						|| strAction == OBConstant.SettInstrStatus.SAVE 
						|| strAction == OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL)) 
		{	
			
        	
			
			String[] nameArray = OBSignatureConstant.CapTransfer
					.getSignNameArray();
			String[] valueArray = OBSignatureConstant.CapTransfer
					.getSignValueArrayFromReq(request);
			String _blnIsNeedApproval = request.getParameter("isNeedApproval");
			long lActionStatus = Long.parseLong(request.getParameter("actionStatus"));

			String strIsRefused = request.getParameter("isRefused");
			strIsRefused = strIsRefused == null ? "" : strIsRefused;

			//特殊处理			
			if (sbatchNO != null
					&& sbatchNO.length() > 0
					&& strAction != OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL) {
				valueArray[OBSignatureConstant.CapTransfer.iArrayLength - 1] = "-1";
			}
			if (!(strAction == OBConstant.SettInstrStatus.DOAPPRVOAL)) {
				if (_blnIsNeedApproval.equals("true")) {
					if (!strIsRefused.equals("true")
							&& lActionStatus != OBConstant.SettActionStatus.CANCELAPPROVALED
							&& lActionStatus != OBConstant.SettActionStatus.CANCELCHECKED) {
						//modified by mzh_fu 2007/12/20			
						//valueArray[5] = "-1";
						valueArray[OBSignatureConstant.CapTransfer.iArrayLength - 1] = "-1";
					}
				} else {
					long lRsStatus = Long.parseLong(request
							.getParameter("rsStatus"));
					if (strAction == OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL
							&& lActionStatus == OBConstant.SettActionStatus.CANCELAPPROVALED) {
					} else {
						if (lRsStatus != OBConstant.SettInstrStatus.CHECK
								&& lActionStatus != OBConstant.SettActionStatus.CANCELCHECKED) {

							//modified by mzh_fu 2007/12/20
							//valueArray[5] = "-1";
							valueArray[OBSignatureConstant.CapTransfer.iArrayLength - 1] = "-1";
						}
					}
				}
			}
			SignatureInfo signatureInfo = new SignatureInfo();
			signatureInfo.setNameArray(nameArray);
			signatureInfo.setValueArray(valueArray);
			signatureOriginalValue = SignatureUtil.formatData(signatureInfo);
			//signatureOriginalValue = SignatureUtil.formatData(nameArray,valueArray);

			try {
				System.out.println("前一个页面按钮校验" + signatureValue);
				System.out.println("当前页面校验"
						+ (String) signatureOriginalValue.toString());
				signatureInfo.setOriginalData(signatureOriginalValue);
				signatureInfo.setSignatureValue(signatureValue);

				SignatureAuthentication.validateFromReq(signatureInfo);
				//SignatureAuthentication.validateFromReq(signatureOriginalValue,signatureValue);			
			} catch (Exception e) {
				Log.print("校验指令信息签名时出错，" + e.getMessage());
				e.printStackTrace();
				throw new IException(e.getMessage());
			}
		}else if(strTroyName.equals(Constant.GlobalTroyName.NetSign)
						&& (strAction == OBConstant.SettInstrStatus.DOAPPRVOAL
						|| strAction == OBConstant.SettInstrStatus.SAVE 
						|| strAction == OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL)){  //信安验证 2010-08-02 jzw
			String[] nameArray = OBSignatureConstant.CapTransfer
					.getSignNameArray();
			String[] valueArray = OBSignatureConstant.CapTransfer
					.getSignValueArrayFromReq(request);
			SignatureInfo signatureInfo = new SignatureInfo();
			signatureInfo.setNameArray(nameArray);
			signatureInfo.setValueArray(valueArray);
			boolean signBoolean = false;
			signatureOriginalValue = SignatureUtil.formatDataNetSign(signatureInfo);
			try {
				System.out.println("jzw 前一个页面原文" + signatureOriginalValue);
				signatureInfo.setOriginalData(signatureOriginalValue);
				signatureInfo.setSignatureValue(signatureValue);
				signBoolean = SignatureAuthentication.validateFromReqNetSign(signatureInfo);			
			} catch (Exception e) {
				Log.print("校验指令信息签名时出错，" + e.getMessage());
				throw new IException("服务端密文验证失败，失败原因："+e.getMessage());
			}
			if(!signBoolean){
				throw new IException("服务端密文验证失败,数据被非法截获并修改");
			}
		}
		/**数字签名校验，判断信息是否被篡改--结束*/
%>
<!--Get Data end-->

<!--Check start-->
<!--Check end-->

<!--Set FinanceInfo Attribute start-->
<%
	/* 根据FORM表单中的相应数据 设置信息类*/
		/* 指令序号 */
		if (lInstructionID > 0) {
			financeInfo.setID(lInstructionID);
		}

		if(dtmodify!=null)
		{
			financeInfo.setDtModify(dtmodify);
		}
		
		/* 付款方资料 */
		financeInfo.setChildClientID(lChildClientID);//下属单位
		financeInfo.setPayerName(strPayerName);// 付款方名称
		financeInfo.setPayerAcctID(lPayerAcctID);//付款方账户ID
		financeInfo.setPayerBankNo(strPayerAcctNo);// 银行账号
		financeInfo.setPayerAcctNo(strPayerAcctNo);// 付款方账号
		financeInfo.setCurrentBalance(dPayerCurrentBalance);// 付款方当前余额
		financeInfo.setUsableBalance(dPayerUsableBalance);// 付款方可用余额
		/* 收款方资料 */
		financeInfo.setRemitType(lRemitType);// 汇款方式
		financeInfo.setPayeeName(strPayeeName);
		clientCapInfo.setPayeeName(strPayeeName);// 收款方名称
		financeInfo.setPayeeAcctNo(strPayeeAcctNo);
		clientCapInfo.setPayeeAccoutNO(strPayeeAcctNo);// 收款方账号
		financeInfo.setPayeeAcctID(lPayeeAcctID);//收款方账户ID
		switch ((int) lRemitType) {
		case (int) OBConstant.SettRemitType.SELF:
			financeInfo.setPayeeBankNo("");// 收款方银行账号
			financeInfo.setPayeeBankName(strPayeeBankName);// 汇入行名称
			financeInfo
					.setIsCpfAcct(OBConstant.AccountOfCpf.CODE_ACCOUNTOFCPF_YES);//是中油财务内部账户
			break;
		case (int) OBConstant.SettRemitType.BANKPAY:
		case (int) OBConstant.SettRemitType.BANKPAY_DOWNTRANSFER:
			financeInfo.setPayeeProv(strPayeeProv);
			clientCapInfo.setPayeeProv(strPayeeProv);// 汇入省
			financeInfo.setPayeeCity(strPayeeCity);
			clientCapInfo.setCity(strPayeeCity);// 汇入市
			financeInfo.setPayeeBankName(strPayeeBankName);
			clientCapInfo.setPayeeBankName(strPayeeBankName);// 汇入行名称
			financeInfo.setSPayeeBankCNAPSNO(sPayeeBankCNAPSNO);
			clientCapInfo.setSPayeeBankCNAPSNO(sPayeeBankCNAPSNO);
			financeInfo.setSPayeeBankExchangeNO(sPayeeBankExchangeNO);
			clientCapInfo.setSPayeeBankExchangeNO(sPayeeBankExchangeNO);
			financeInfo.setSPayeeBankOrgNO(sPayeeBankOrgNO);
			clientCapInfo.setSPayeeBankOrgNO(sPayeeBankOrgNO);
			financeInfo
					.setIsCpfAcct(OBConstant.AccountOfCpf.CODE_ACCOUNTOFCPF_NO);
			clientCapInfo
					.setIsCPFAcct(OBConstant.AccountOfCpf.CODE_ACCOUNTOFCPF_NO);//是中油财务内部账户

			//预算新增项目
			financeInfo.setBudgetItemID(lBudgetItemID);

			break;
		case (int) OBConstant.SettRemitType.FINCOMPANYPAY:
			financeInfo.setPayeeProv(strPayeeProv);
			clientCapInfo.setPayeeProv(strPayeeProv);// 汇入省
			financeInfo.setPayeeCity(strPayeeCity);
			clientCapInfo.setCity(strPayeeCity);// 汇入市
			financeInfo.setPayeeBankName(strPayeeBankName);
			clientCapInfo.setPayeeBankName(strPayeeBankName);// 汇入行名称
			financeInfo
					.setIsCpfAcct(OBConstant.AccountOfCpf.CODE_ACCOUNTOFCPF_NO);
			clientCapInfo
					.setIsCPFAcct(OBConstant.AccountOfCpf.CODE_ACCOUNTOFCPF_NO);//是中油财务内部账户

			//预算新增项目
			financeInfo.setBudgetItemID(lBudgetItemID);

			break;
		case (int) OBConstant.SettRemitType.PAYSUBACCOUNT:
			financeInfo.setPayeeProv(strPayeeProv);
			clientCapInfo.setPayeeProv(strPayeeProv);// 汇入省
			financeInfo.setPayeeCity(strPayeeCity);
			clientCapInfo.setCity(strPayeeCity);// 汇入市
			financeInfo.setPayeeBankName(strPayeeBankName);
			clientCapInfo.setPayeeBankName(strPayeeBankName);// 汇入行名称
			financeInfo
					.setIsCpfAcct(OBConstant.AccountOfCpf.CODE_ACCOUNTOFCPF_NO);
			clientCapInfo
					.setIsCPFAcct(OBConstant.AccountOfCpf.CODE_ACCOUNTOFCPF_NO);//是中油财务内部账户

			//预算新增项目
			financeInfo.setBudgetItemID(lBudgetItemID);

			break;
		case (int) OBConstant.SettRemitType.INTERNALVIREMENT:
			financeInfo.setPayeeBankNo("");// 收款方银行账号
			financeInfo
					.setIsCpfAcct(OBConstant.AccountOfCpf.CODE_ACCOUNTOFCPF_YES);//是中油财务内部账户
			break;
		case (int) OBConstant.SettRemitType.OTHER:
			financeInfo.setPayeeBankNo("");// 收款方银行账号
			financeInfo
					.setIsCpfAcct(OBConstant.AccountOfCpf.CODE_ACCOUNTOFCPF_YES);//是中油财务内部账户
			break;
		default:
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,
					"Gen_E001");
			return;
		}
		/* 划款资料 */
		financeInfo.setAmount(dAmount);// 金额
		financeInfo.setExecuteDate(tsExecute);// 执行日
		financeInfo.setConfirmDate(tsExecute);//确认日期
		financeInfo.setNote(strNote);// 汇款用途
		financeInfo.setRemitArea(remitArea);
		financeInfo.setRemitSpeed(remitSpeed);
		financeInfo.setApplyCode(sApplyCode);
		/* 从session中获取相应数据 */
		financeInfo.setConfirmUserID(sessionMng.m_lUserID);
		clientCapInfo.setInputUserID(sessionMng.m_lUserID);// 确认人ID
		financeInfo.setClientID(sessionMng.m_lClientID);
		clientCapInfo.setClientID(sessionMng.m_lClientID);// 交易提交单位
		financeInfo.setCurrencyID(1);
		clientCapInfo.setCurrencyID(sessionMng.m_lCurrencyID);// 交易币种
		financeInfo.setOfficeID(sessionMng.m_lOfficeID);//默认办事处ID

		/* 确定网上交易类型和汇款方式 */
		switch ((int) lRemitType) {
		case (int) OBConstant.SettRemitType.SELF:
			financeInfo
					.setTransType(OBConstant.SettInstrType.CAPTRANSFER_SELF);
			break;
		case (int) OBConstant.SettRemitType.BANKPAY:
			financeInfo
					.setTransType(OBConstant.SettInstrType.CAPTRANSFER_BANKPAY);
			break;
		case (int) OBConstant.SettRemitType.BANKPAY_DOWNTRANSFER:
			financeInfo
					.setTransType(OBConstant.SettInstrType.BANKPAY_DOWNTRANSFER);
			break;
		case (int) OBConstant.SettRemitType.FINCOMPANYPAY:
			financeInfo
					.setTransType(OBConstant.SettInstrType.CAPTRANSFER_FINCOMPANYPAY);
			break;
		case (int) OBConstant.SettRemitType.PAYSUBACCOUNT:
			financeInfo
					.setTransType(OBConstant.SettInstrType.CAPTRANSFER_PAYSUBACCOUNT);
			break;
		case (int) OBConstant.SettRemitType.INTERNALVIREMENT:
			financeInfo
					.setTransType(OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT);
			break;
		case (int) OBConstant.SettRemitType.OTHER:
			financeInfo
					.setTransType(OBConstant.SettInstrType.CAPTRANSFER_OTHER);
			break;
		default:
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,
					"Gen_E001");
			return;
		}//网上交易类型
		if (financeInfo.getChildClientID() > 0) {
			financeInfo
					.setTransType(OBConstant.SettInstrType.CHILDCAPTRANSFER);
		}
		financeInfo.setRemitType(lRemitType);//汇款方式
		/*确定指令状态*/
		strTemp = (String) request.getAttribute("rsStatus");
		long statusid = -1;
		if (strTemp != null && strTemp.trim().length() > 0) {
			statusid = Long.valueOf(strTemp).longValue();
			if (statusid >= 0) {
				financeInfo.setStatus(statusid);
			}
		}

		if (financeInfo.getStatus() == -1) {
			financeInfo.setStatus(OBConstant.SettInstrStatus.SAVE);
		}

		//判断是否需要审批流 by ypxu 2007-05-10
		InutParameterInfo inutParameterInfo = new InutParameterInfo();
		inutParameterInfo.setOfficeID(sessionMng.m_lOfficeID);
		inutParameterInfo.setCurrencyID(sessionMng.m_lCurrencyID);
		inutParameterInfo.setClientID(sessionMng.m_lClientID);
		inutParameterInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
		inutParameterInfo.setTransTypeID(financeInfo.getTransType());
		//审批中和取消审批不区分本单位审批和下属单位审批
		if (OBConstant.SettInstrStatus.DOAPPRVOAL != strAction
				&& OBConstant.SettInstrStatus.CANCELAPPRVOAL != strAction) {
			inutParameterInfo.setIslowerunit(OBConstant.IsLowerun.ISNO);
		}
		boolean isNeedApproval = OBFSWorkflowManager
				.isNeedApproval(inutParameterInfo);
%>
<!--Set FinanceInfo Attribute end-->

<!--Access DB start-->
<%
		if (com.iss.itreasury.budget.util.UtilOperation.getParameter()
				.getEbankLink() > 0
				&& (lRemitType == OBConstant.SettRemitType.BANKPAY 
						|| lRemitType == OBConstant.SettRemitType.BANKPAY_DOWNTRANSFER)) //预算与网银挂接，交易类型为银行付款
		{
			System.out.println("****************与网银挂接**");

			ControlInfo info = new ControlInfo();
			info.setAccountID(lPayerAcctID);
			info.setItemID(lBudgetItemID);
			info.setClientID(sessionMng.m_lClientID);
			info.setExecuteDate(tsExecute);
			info.setAmount(dAmount);
			info.setCheckType(BUDGETConstant.TransactionStatus.SAVE);
			com.iss.itreasury.budget.util.UtilOperation ut = new com.iss.itreasury.budget.util.UtilOperation();

			info = ut.checkBudget(info);

			//test	
			//info.setErrCode(BUDGETConstant.BudgetCheckCode.OVERSUPPLENESS);
			//info.setErrMsg("****************");
			//request.setAttribute("BudgetPass","no");

			String strBudgetPass = (String) request
					.getAttribute("BudgetPass");// 预算是否通过

			System.out.println("****************与网银挂接**"
					+ strBudgetPass);

			if (info.getErrCode() == BUDGETConstant.BudgetCheckCode.OVERRIGIDITY) {
				throw new IException(info.getErrMsg());
			} else if (info.getErrCode() == BUDGETConstant.BudgetCheckCode.UNBUDGET) //账户不控制预算
			{
				/* 初始化EJB */
				OBFinanceInstrHome financeInstrHome = null;
				OBFinanceInstr financeInstr = null;
				financeInstrHome = (OBFinanceInstrHome) EJBObject
						.getEJBHome("OBFinanceInstrHome");
				financeInstr = financeInstrHome.create();
				Log.print("--------------------lInstructionID="
						+ lInstructionID);

				/* 调用EJB方法保存(或修改)和提取信息对象 */
				//刷新问题
				if (session.getAttribute("clickCount") == null) {
					session.setAttribute("clickCount", String
							.valueOf(0));
				}
				String strClickCount = request
						.getParameter("clickCount");
				Log.print("clickcount from request parameter:="
						+ strClickCount);
				boolean blnClickCheck = OBOperation.checkClick(
						strClickCount, session);
				if (blnClickCheck) {
					//不控制预算，将预算ID置为空
					financeInfo.setBudgetItemID(-1);
					
					long transID = financeInfo.getID();
					try {
						lInstructionID = financeInstr
								.addCapitalTrans(financeInfo);
						transinfo.setStatus(Constant.SUCCESSFUL);
						if (transID > 0) {
							transinfo
									.setActionType(Constant.TransLogActionType.modify);
						} else {
							transinfo
									.setActionType(Constant.TransLogActionType.insert);
						}
					} catch (Exception ex) {
						transinfo.setStatus(Constant.FAIL);
						if (transID > 0) {
							transinfo
									.setActionType(Constant.TransLogActionType.modify);
						} else {
							transinfo
									.setActionType(Constant.TransLogActionType.insert);
						}
						ex.printStackTrace();
						throw new IException(ex.getMessage());
					}
					if (lInstructionID > 0) {
						if (tempTransCode != null
								&& !tempTransCode.equals("")) {
							//数据访问对象
							AttachOperation attachOperation = new AttachOperation();
							attachOperation.updateOBTransCode(
									tempTransCode, String
											.valueOf(lInstructionID));
						}
					}
					financeInfo.setID(lInstructionID);
					session.setAttribute("lInstructionID", String
							.valueOf(lInstructionID));
					System.out.println("-----------------"
							+ lInstructionID);
				} else {
					if (lInstructionID < 0) {
						String strInstructionID = (String) session
								.getAttribute("lInstructionID");
						lInstructionID = Long
								.parseLong(strInstructionID);
					}
				}
				/*调用EJB方法查询结果*/
				financeInfo = financeInstr.findByID(lInstructionID,
						sessionMng.m_lUserID, sessionMng.m_lCurrencyID);

				/* 在请求中保存结果对象 */
				request.setAttribute("financeInfo", financeInfo);
				/* 获取上下文环境 */
				//ServletContext sc = getServletContext();
				/* 设置返回地址 */
				RequestDispatcher rd = null;
				/***************************************************/
				String flag = (String) request.getParameter("flag");
				if (flag != null && flag.equals("commit")) {
					request.setAttribute("TJCG", "success");
				}
				/***************************************************/
				PageControllerInfo pageControllerInfo = new PageControllerInfo();
				pageControllerInfo.setSessionMng(sessionMng);
				pageControllerInfo
						.setUrl("/capital/captransfer/c004-v.jsp?isNeedApproval="
								+ isNeedApproval);
				//分发
				rd = request.getRequestDispatcher(PageController
						.getDispatcherURL(pageControllerInfo));

				/* forward到结果页面 */
				rd.forward(request, response);
			} else if (info.getErrCode() == BUDGETConstant.BudgetCheckCode.OVERSUPPLENESS) //超出柔性预算
			{

				if (strBudgetPass != null
						&& "true".equals(strBudgetPass)) {
					/* 初始化EJB */
					OBFinanceInstrHome financeInstrHome = null;
					OBFinanceInstr financeInstr = null;
					financeInstrHome = (OBFinanceInstrHome) EJBObject
							.getEJBHome("OBFinanceInstrHome");
					financeInstr = financeInstrHome.create();
					Log.print("--------------------lInstructionID="
							+ lInstructionID);

					/* 调用EJB方法保存(或修改)和提取信息对象 */
					//刷新问题
					if (session.getAttribute("clickCount") == null) {
						session.setAttribute("clickCount", String
								.valueOf(0));
					}
					String strClickCount = request
							.getParameter("clickCount");
					Log.print("clickcount from request parameter:="
							+ strClickCount);
					boolean blnClickCheck = OBOperation.checkClick(
							strClickCount, session);
					if (blnClickCheck) {
						long transID = financeInfo.getID();
						try {
							lInstructionID = financeInstr
									.addCapitalTrans(financeInfo);
							transinfo.setStatus(Constant.SUCCESSFUL);
							if (transID > 0) {
								transinfo
										.setActionType(Constant.TransLogActionType.modify);
							} else {
								transinfo
										.setActionType(Constant.TransLogActionType.insert);
							}
						} catch (Exception ex) {
							transinfo.setStatus(Constant.FAIL);
							if (transID > 0) {
								transinfo
										.setActionType(Constant.TransLogActionType.modify);
							} else {
								transinfo
										.setActionType(Constant.TransLogActionType.insert);
							}
							ex.printStackTrace();
							throw new IException(ex.getMessage());
						}
						financeInfo.setID(lInstructionID);
						if (lInstructionID > 0) {
							if (tempTransCode != null
									&& !tempTransCode.equals("")) {
								//数据访问对象
								AttachOperation attachOperation = new AttachOperation();
								attachOperation.updateOBTransCode(
										tempTransCode,
										String.valueOf(lInstructionID));
							}
						}
						session.setAttribute("lInstructionID", String
								.valueOf(lInstructionID));
						System.out.println("-----------------"
								+ lInstructionID);
					} else {
						if (lInstructionID < 0) {
							String strInstructionID = (String) session
									.getAttribute("lInstructionID");
							lInstructionID = Long
									.parseLong(strInstructionID);
						}
					}
					/*调用EJB方法查询结果*/
					financeInfo = financeInstr.findByID(lInstructionID,
							sessionMng.m_lUserID,
							sessionMng.m_lCurrencyID);

					/* 在请求中保存结果对象 */
					request.setAttribute("financeInfo", financeInfo);
					/* 获取上下文环境 */
					//ServletContext sc = getServletContext();
					/* 设置返回地址 */
					RequestDispatcher rd = null;
					/***************************************************/
					String flag = (String) request.getParameter("flag");
					if (flag != null && flag.equals("commit")) {
						request.setAttribute("TJCG", "success");
					}
					/***************************************************/
					PageControllerInfo pageControllerInfo = new PageControllerInfo();
					pageControllerInfo.setSessionMng(sessionMng);
					pageControllerInfo
							.setUrl("/capital/captransfer/c004-v.jsp?isNeedApproval="
									+ isNeedApproval);
					//分发
					rd = request.getRequestDispatcher(PageController
							.getDispatcherURL(pageControllerInfo));

					/* forward到结果页面 */
					rd.forward(request, response);
				} else //超出柔性预算，返回提示
				{
					request.setAttribute("strCheckBudget", String
							.valueOf(info.getErrCode()));
					request.setAttribute("strErrMessage", info
							.getErrMsg());
					request.setAttribute("BudgetPass", "no"); // 预算没有通过

					/* 在请求中保存结果对象 */
					request.setAttribute("financeInfo", financeInfo);
					/* 获取上下文环境 */
					//ServletContext sc = getServletContext();
					/* 设置返回地址 */
					RequestDispatcher rd = null;
					PageControllerInfo pageControllerInfo = new PageControllerInfo();
					pageControllerInfo.setSessionMng(sessionMng);
					pageControllerInfo
							.setUrl("/capital/captransfer/c002-v.jsp");
					//分发

					rd = request.getRequestDispatcher(PageController
							.getDispatcherURL(pageControllerInfo));

					/* forward到结果页面 */
					rd.forward(request, response);

				}
			} else //正常
			{
				/* 初始化EJB */
				OBFinanceInstrHome financeInstrHome = null;
				OBFinanceInstr financeInstr = null;
				financeInstrHome = (OBFinanceInstrHome) EJBObject
						.getEJBHome("OBFinanceInstrHome");
				financeInstr = financeInstrHome.create();
				Log.print("--------------------lInstructionID="
						+ lInstructionID);

				/* 调用EJB方法保存(或修改)和提取信息对象 */
				//刷新问题
				if (session.getAttribute("clickCount") == null) {
					session.setAttribute("clickCount", String.valueOf(0));
				}
				String strClickCount = request
						.getParameter("clickCount");
				Log.print("clickcount from request parameter:="
						+ strClickCount);
				if (OBConstant.SettInstrStatus.DOAPPRVOAL == strAction) {
					//构造代办业务列表中代办业务明细链接页面(即审批页面)
					String strSuccessPageURL1 = "/capital/captransfer/vAppc004-v.jsp";
					String strFailPageURL1 = "/capital/captransfer/c002-v.jsp";

					//strNextPage ="/currentStep.do?operate=selectCurrentStepList";				
					//如果审批流的待办任务链接修改为v033.jsp则使用下面地址    mingfang
					strNextPage = "/approval/view/v033.jsp";

					long iTransType = OBConstant.SettInstrType.OPENNOTIFYACCOUNT;
					String surl = strContext
							+ "/capital/query/q003-c.jsp?strSuccessPageURL="
							+ strSuccessPageURL1 + "&&strFailPageURL="
							+ strFailPageURL1 + "&&txtTransType="
							+ iTransType + "&&txtID=" + lInstructionID;

					//构造参数类
					InutParameterInfo pInfo = new InutParameterInfo();
					pInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
					pInfo.setRequest(request);
					pInfo.setSessionMng(sessionMng);
					pInfo.setUrl(surl);
					pInfo.setTransTypeID(iTransType);
					financeInfo.setInutParameterInfo(pInfo);
					//审批
					try {

						//将签名值与原始数据保存
						financeInfo.setSignatureValue(signatureValue);
						financeInfo
								.setSignatureOriginalValue(signatureOriginalValue);

						financeInstr.doApproval(financeInfo);
						transinfo.setStatus(Constant.SUCCESSFUL);
						transinfo
								.setActionType(Constant.TransLogActionType.approval);
						//Modify by leiyang date 2007/07/24
						msg = "操作成功";
						sessionMng.getActionMessages().addMessage(msg);
					} catch (Exception e) {
						transinfo.setStatus(Constant.FAIL);
						transinfo
								.setActionType(Constant.TransLogActionType.approval);
						Log.print("EJB异常抛到前台处理");
						e.printStackTrace();
						throw new IException(e.getMessage());
					}
				}
				boolean blnClickCheck = OBOperation.checkClick(
						strClickCount, session);
				if (blnClickCheck) {
					strNextPage = "/capital/captransfer/c004-v.jsp?isNeedApproval="
							+ isNeedApproval;
					//如果是"保存并提交审批",构造InutParameterInfo并设置到FinanceInfo中
					//save方法中判断TransCurrentDepositAssembler中的InutParameterInfo是否为空,如果不为空,则调用"提交审批方法",然后更新状态
					if (OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL == strAction) {
						String strSuccessPageURL1 = "/capital/captransfer/vAppc004-v.jsp";
						String strFailPageURL1 = "/capital/captransfer/c004-v.jsp?isNeedApproval="
								+ isNeedApproval;
						//long iTransType = OBConstant.SettInstrType.OPENNOTIFYACCOUNT;
						String surl = strContext
								+ "/capital/query/q003-c.jsp?strSuccessPageURL="
								+ strSuccessPageURL1
								+ "&&strFailPageURL=" + strFailPageURL1
								+ "&&txtTransType=" + lRemitType
								+ "&&txtID=";

						//构造参数类
						InutParameterInfo pInfo = new InutParameterInfo();
						pInfo
								.setModuleID(Constant.ModuleType.SETTLEMENT);
						pInfo.setRequest(request);
						pInfo.setSessionMng(sessionMng);
						pInfo.setUrl(surl);
						pInfo.setTransTypeID(lRemitType);

						financeInfo.setInutParameterInfo(pInfo);
						//Modify by leiyang date 2007/07/24
						if (isSommitTimes == true) {
							msg = "提交审批成功, 时间已超过结算最迟接收时间, 执行日顺延一日!";
						} else {
							msg = "提交审批成功";
						}
					} else {
						//Modify by leiyang date 2007/07/24
						if (isSommitTimes == true) {
							msg = "保存成功, 时间已超过结算最迟接收时间, 执行日顺延一日!";
						} else {
							msg = "保存成功";
						}
					}
					long transID = financeInfo.getID();
					try {
						lInstructionID = financeInstr
								.addCapitalTrans(financeInfo);
						transinfo.setStatus(Constant.SUCCESSFUL);
						if (OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL == strAction) {
							transinfo
									.setActionType(Constant.TransLogActionType.initApproval);
						} else if (transID > 0) {
							transinfo
									.setActionType(Constant.TransLogActionType.modify);
						} else {
							transinfo
									.setActionType(Constant.TransLogActionType.insert);
						}
					} catch (Exception ex) {
						transinfo.setStatus(Constant.FAIL);
						if (OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL == strAction) {
							transinfo
									.setActionType(Constant.TransLogActionType.initApproval);
						} else if (transID > 0) {
							transinfo
									.setActionType(Constant.TransLogActionType.modify);
						} else {
							transinfo
									.setActionType(Constant.TransLogActionType.insert);
						}
						ex.printStackTrace();
						throw new IException(ex.getMessage());
					}
					sessionMng.getActionMessages().addMessage(msg);
					financeInfo.setID(lInstructionID);
					if (lInstructionID > 0) {
						if (tempTransCode != null
								&& !tempTransCode.equals("")) {
							//数据访问对象
							AttachOperation attachOperation = new AttachOperation();
							attachOperation.updateOBTransCode(
									tempTransCode, String
											.valueOf(lInstructionID));
						}
					}
					session.setAttribute("lInstructionID", String
							.valueOf(lInstructionID));
					System.out.println("-----------------"
							+ lInstructionID);
				} else {
					if (lInstructionID < 0) {
						String strInstructionID = (String) session
								.getAttribute("lInstructionID");
						lInstructionID = Long
								.parseLong(strInstructionID);
					}
				}
				/*调用EJB方法查询结果*/
				financeInfo = financeInstr.findByID(lInstructionID,
						sessionMng.m_lUserID, sessionMng.m_lCurrencyID);

				/* 在请求中保存结果对象 */
				request.setAttribute("financeInfo", financeInfo);
				/* 获取上下文环境 */
				//ServletContext sc = getServletContext();
				/* 设置返回地址 */
				RequestDispatcher rd = null;
				/***************************************************/
				String flag = (String) request.getParameter("flag");
				if (flag != null && flag.equals("commit")) {
					request.setAttribute("TJCG", "success");
				}
				/***************************************************/
				PageControllerInfo pageControllerInfo = new PageControllerInfo();
				pageControllerInfo.setSessionMng(sessionMng);
				pageControllerInfo.setUrl(strNextPage);
				//分发
				rd = request.getRequestDispatcher(PageController
						.getDispatcherURL(pageControllerInfo));

				/* forward到结果页面 */
				rd.forward(request, response);
			}

		} else //没有与预算挂接或者交易类型不是银行付款
		{

			/* 初始化EJB */
			OBFinanceInstrHome financeInstrHome = null;
			OBFinanceInstr financeInstr = null;
			financeInstrHome = (OBFinanceInstrHome) EJBObject
					.getEJBHome("OBFinanceInstrHome");
			financeInstr = financeInstrHome.create();
			Log.print("--------------------lInstructionID="
					+ lInstructionID);

			/* 调用EJB方法保存(或修改)和提取信息对象 */
			//刷新问题
			if (session.getAttribute("clickCount") == null) {
				session.setAttribute("clickCount", String.valueOf(0));
			}
			String strClickCount = request.getParameter("clickCount");
			Log.print("clickcount from request parameter:="
					+ strClickCount);
			if (OBConstant.SettInstrStatus.DOAPPRVOAL == strAction) {
				//构造代办业务列表中代办业务明细链接页面(即审批页面)
				String strSuccessPageURL1 = "/capital/captransfer/vAppc004-v.jsp";
				String strFailPageURL1 = "/capital/captransfer/c002-v.jsp";

				//strNextPage ="/currentStep.do?operate=selectCurrentStepList";
				//如果审批流的待办任务链接修改为v033.jsp 则使用下面地址  mingfang
				strNextPage = "/approval/view/v033.jsp";

				long iTransType = -1;
				switch ((int) lRemitType) {
				case (int) OBConstant.SettRemitType.BANKPAY:
					iTransType = OBConstant.SettInstrType.CAPTRANSFER_BANKPAY;
					break;
				case (int) OBConstant.SettRemitType.INTERNALVIREMENT:
					iTransType = OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT;
					break;
				}
				String surl = strContext
						+ "/capital/query/q003-c.jsp?strSuccessPageURL="
						+ strSuccessPageURL1 + "&strFailPageURL="
						+ strFailPageURL1 + "&txtTransType="
						+ iTransType + "&txtID=" + lInstructionID;
				//构造参数类
				InutParameterInfo pInfo = new InutParameterInfo();
				pInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
				pInfo.setRequest(request);
				pInfo.setSessionMng(sessionMng);
				pInfo.setUrl(surl);
				pInfo.setTransTypeID(iTransType);
				financeInfo.setInutParameterInfo(pInfo);
				//审批
				try {
					//将签名值与原始数据保存   add by mingfang
					financeInfo.setSignatureValue(signatureValue);
					financeInfo
							.setSignatureOriginalValue(signatureOriginalValue);

					financeInstr.doApproval(financeInfo);
					
					transinfo.setStatus(Constant.SUCCESSFUL);
					transinfo
							.setActionType(Constant.TransLogActionType.approval);
					sessionMng.getActionMessages().addMessage("操作成功");
				} catch (Exception e) {
					transinfo.setStatus(Constant.FAIL);
					transinfo.setActionType(Constant.TransLogActionType.approval);
					e.printStackTrace();
					throw new IException(e.getMessage());
				}
			}
			//取消审批   add by mingfang
			else if (OBConstant.SettInstrStatus.CANCELAPPRVOAL == strAction) {
				//构造代办业务列表中代办业务明细链接页面(即审批页面)
				String strSuccessPageURL1 = "/capital/captransfer/vAppc004-v.jsp";
				String strFailPageURL1 = "/capital/captransfer/c002-v.jsp";

				//strNextPage ="/currentStep.do?operate=selectCurrentStepList";
				//如果审批流的待办任务链接修改为v036.jsp 则使用下面地址  mingfang
				strNextPage = "/approval/view/v036.jsp";

				long iTransType = -1;
				switch ((int) lRemitType) {
				case (int) OBConstant.SettRemitType.BANKPAY:
					iTransType = OBConstant.SettInstrType.CAPTRANSFER_BANKPAY;
					break;
				case (int) OBConstant.SettRemitType.INTERNALVIREMENT:
					iTransType = OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT;
					break;
				}
				String surl = strContext
						+ "/capital/query/q003-c.jsp?strSuccessPageURL="
						+ strSuccessPageURL1 + "&strFailPageURL="
						+ strFailPageURL1 + "&txtTransType="
						+ iTransType + "&txtID=" + lInstructionID;
				//构造参数类
				InutParameterInfo pInfo = new InutParameterInfo();
				pInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
				pInfo.setRequest(request);
				pInfo.setSessionMng(sessionMng);
				pInfo.setUrl(surl);
				pInfo.setTransTypeID(iTransType);
				financeInfo.setInutParameterInfo(pInfo);
				//审批
				try {
					//将签名值与原始数据保存   add by mingfang
					financeInfo.setSignatureValue(signatureValue);
					financeInfo
							.setSignatureOriginalValue(signatureOriginalValue);

					financeInstr.cancelApproval(financeInfo);
					
					transinfo.setStatus(Constant.SUCCESSFUL);
					transinfo
							.setActionType(Constant.TransLogActionType.cancelApproval);
					sessionMng.getActionMessages().addMessage("取消审批成功");
				} catch (Exception e) {
					transinfo.setStatus(Constant.FAIL);
					transinfo
							.setActionType(Constant.TransLogActionType.cancelApproval);
					e.printStackTrace();
					throw new IException(e.getMessage());
				}
			}
			boolean blnClickCheck = OBOperation.checkClick(
					strClickCount, session);
			if (blnClickCheck) {
				strNextPage = "/capital/captransfer/c004-v.jsp?isNeedApproval="
						+ isNeedApproval + "&flagCommit=success";
				//如果是"保存并提交审批",构造InutParameterInfo并设置到FinanceInfo中
				//save方法中判断TransCurrentDepositAssembler中的InutParameterInfo是否为空,如果不为空,则调用"提交审批方法",然后更新状态
				if (OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL == strAction) {
					String strSuccessPageURL1 = "/capital/captransfer/vAppc004-v.jsp";
					String strFailPageURL1 = "/capital/captransfer/c002-v.jsp";
					strNextPage = "/capital/captransfer/c004-v.jsp?isNeedApproval="
							+ isNeedApproval;
					long iTransType = -1;
					switch ((int) lRemitType) {
					case (int) OBConstant.SettRemitType.BANKPAY:
						iTransType = OBConstant.SettInstrType.CAPTRANSFER_BANKPAY;
						break;
					case (int) OBConstant.SettRemitType.INTERNALVIREMENT:
						iTransType = OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT;
						break;
					}
					String surl = strContext
							+ "/capital/query/q003-c.jsp?strSuccessPageURL="
							+ strSuccessPageURL1 + "&strFailPageURL="
							+ strFailPageURL1 + "&txtTransType="
							+ iTransType + "&txtID=";

					//构造参数类
					InutParameterInfo pInfo = new InutParameterInfo();
					pInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
					pInfo.setRequest(request);
					pInfo.setSessionMng(sessionMng);
					pInfo.setUrl(surl);
					pInfo.setTransTypeID(iTransType);

					financeInfo.setInutParameterInfo(pInfo);

					//Modify by leiyang date 2007/07/24
					if (isSommitTimes == true) {
						msg = "提交审批成功, 时间已超过结算最迟接收时间, 执行日顺延一日!";
					} else {
						msg = "提交审批成功";
					}

				} else {
					if (isSommitTimes == true) {
						msg = "保存成功, 时间已超过结算最迟接收时间, 执行日顺延一日!";
					} else {
						msg = "保存成功";
					}
				}
				
				/*保存汇款用途摘要信息*/
				
				
				sTemp = (String)request.getAttribute("sNote");
				AbstractID = Long.parseLong(sTemp);//汇款用途ID
				
				AbstractCode = (String)request.getAttribute("sCode");//汇款用途编号
				
				if( AbstractID < 0)
				{
					long lMaxCode = OBAbstractSetting.getMaxCode(sessionMng.m_lOfficeID,sessionMng.m_lUserID);
					OBinfo.setScode(DataFormat.formatInt(lMaxCode,5,true,0));
				}else if(AbstractID > 0){
					OBinfo.setScode(AbstractCode);
				}
				
				OBinfo.setId(AbstractID);
				OBinfo.setSdesc(strNote);
				OBinfo.setNofficeid(sessionMng.m_lOfficeID);
				OBinfo.setNclientid(sessionMng.m_lUserID);
				OBinfo.setInputtime(now);
				OBinfo.setModifytime(now);
				OBAbstractSetting.saveStandardAbstract(OBinfo);
				

				//将签名值与原始数据保存   add by mingfang
				financeInfo.setSignatureValue(signatureValue);
				financeInfo
						.setSignatureOriginalValue(signatureOriginalValue);
				long transID = 0;
				if(sign.equals("again"))
				{
					financeInfo.setID(-1);
					transID=-1;
				}
				else
				{
					transID = financeInfo.getID();
				}
				try {
					lInstructionID = financeInstr
							.addCapitalTrans(financeInfo);
					transinfo.setStatus(Constant.SUCCESSFUL);
					if (OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL == strAction) {
						transinfo
								.setActionType(Constant.TransLogActionType.initApproval);
					} else if (transID > 0) {
						transinfo
								.setActionType(Constant.TransLogActionType.modify);
					} else {
						transinfo
								.setActionType(Constant.TransLogActionType.insert);
					}
				} catch (Exception ex) {
					transinfo.setStatus(Constant.FAIL);
					if (OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL == strAction) {
						transinfo
								.setActionType(Constant.TransLogActionType.initApproval);
					} else if (transID > 0) {
						transinfo
								.setActionType(Constant.TransLogActionType.modify);
					} else {
						transinfo
								.setActionType(Constant.TransLogActionType.insert);
					}
					ex.printStackTrace();
					throw new IException(ex.getMessage());
				}
				sessionMng.getActionMessages().addMessage(msg);

				financeInfo.setID(lInstructionID);
				if (lInstructionID > 0) {
					if (tempTransCode != null
							&& !tempTransCode.equals("")) {
						//数据访问对象
						AttachOperation attachOperation = new AttachOperation();
						attachOperation.updateOBTransCode(
								tempTransCode, String
										.valueOf(lInstructionID));
					}
				}
				session.setAttribute("lInstructionID", String
						.valueOf(lInstructionID));
				System.out
						.println("-----------------" + lInstructionID);
			} else {
				if (lInstructionID < 0) {
					String strInstructionID = (String) session
							.getAttribute("lInstructionID");
					lInstructionID = Long.parseLong(strInstructionID);
				}
			}
			/*调用EJB方法查询结果*/
			financeInfo = financeInstr.findByID(lInstructionID,
					sessionMng.m_lUserID, sessionMng.m_lCurrencyID);
			
			
		
%>
<!--Access DB end-->

<!--Forward start-->
<%
	/* 在请求中保存结果对象 */
			request.setAttribute("financeInfo", financeInfo);
			/* 获取上下文环境 */
			//ServletContext sc = getServletContext();
			/* 设置返回地址 */
			RequestDispatcher rd = null;
			PageControllerInfo pageControllerInfo = new PageControllerInfo();
			pageControllerInfo.setSessionMng(sessionMng);
			System.out.println("jzw next url = "+strNextPage);
			pageControllerInfo.setUrl(strNextPage);
			//分发
			rd = request.getRequestDispatcher(PageController
					.getDispatcherURL(pageControllerInfo));
			
			/* forward到结果页面 */
			try{
				rd.forward(request, response);
			}catch(Exception ex){
				System.out.println("jzw rd");
				ex.printStackTrace();
			}
		}

	} catch (IException ie) {

		ie.printStackTrace();
		session.setAttribute("financeInfo", financeInfo);
		OBHtml.showExceptionMessage(out, sessionMng, ie, strTitle, "", 1);
		return;
	} finally {
		// minzhao 20090520 start add translogger

		if (transinfo.getStatus() != -1) {
			TranslogBiz translofbiz = new TranslogBiz();
			transinfo.setHostip(request.getRemoteAddr());
			transinfo.setHostname(request.getRemoteHost());
			long iTransType = -1;
			switch ((int) lRemitType) {
			case (int) OBConstant.SettRemitType.BANKPAY:
				iTransType = OBConstant.SettInstrType.CAPTRANSFER_BANKPAY;
				break;
			case (int) OBConstant.SettRemitType.INTERNALVIREMENT:
				iTransType = OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT;
				break;
			}
			transinfo.setTransType(iTransType);
			translofbiz.saveTransLogInfo(sessionMng, financeInfo, transinfo);

		}

		//minzhao 20090520 end translogger 
	}
%>
<!--Forward end-->
