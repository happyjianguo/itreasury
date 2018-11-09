<%--
/**
 页面名称 ：queryControl.jsp
 页面功能 : 交易详细信息查询的控制类页面
 作    者 ：kewen hu
 日    期 ：2004-02-24
 特殊说明 ：
			在查询页面只需对使用下面的url
			    /settlement/query/control/querycontrol.jsp?TransactionTypeID=&TransNo=&strFailPageURL=
			一付多收：
				/settlement/query/control/querycontrol.jsp?TransactionTypeID=&TransNo=&SerialNo=&strFailPageURL=
 修改历史 ：
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>

<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.util.Collection"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.util.OBOperation"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
	//页面控制变量
    String strNextPageURL = null;
	String strSuccessPageURL = null;
	String strFailPageURL = null;
	String strAction = "Query";
	String strContext = request.getContextPath();

    //标题变量
    String strTitle = "[账户历史信息]";
    try {
         /* 用户登录检测 */
        if (sessionMng.isLogin() == false) {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
            out.flush();
            return;
        }

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false) {
            out.println(sessionMng.hasRight(request));
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
            out.flush();
            return;
        }

        //定义变量
		long lTransactionTypeID = -1;
		String strTransactionTypeID = "";
		String strTransNo = "";
		String strSerialNo = "";
		String strID = "";
		long lAccountID = 1;
		strFailPageURL = (String)request.getAttribute("strFailPageURL");

		String strTemp = null;
		strTemp = (String)request.getAttribute("TransactionTypeID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strTransactionTypeID = strTemp;
			lTransactionTypeID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("TransNo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strTransNo = strTemp;
		}
		strTemp = (String)request.getAttribute("SerialNo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strSerialNo = strTemp;
		}
		strTemp = (String)request.getAttribute("lID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strID = strTemp;
		}
		strTemp = (String)request.getAttribute("lAccountID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lAccountID = Long.parseLong(strTemp);
		}
		request.setAttribute("lAccountID",String.valueOf(lAccountID));

		Log.print("**********请求分发页面参数*************");
		Log.print("lTransactionTypeID="+lTransactionTypeID);
		Log.print("strTransNo="+strTransNo);
		Log.print("strSerialNo="+strSerialNo);
		Log.print("strID="+strID);
		Log.print("lAccountID="+lAccountID);

		switch ((int)lTransactionTypeID)
		{
			//活期交易
			case (int)SETTConstant.TransactionType.BANKPAY: //银行付款
				strNextPageURL = strContext+ "/accountinfo/a301-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a515-v.jsp&strFailPageURL="+strFailPageURL+"&lId="+strID;
				break;
			case (int)SETTConstant.TransactionType.BANKPAY_FINCOMPANYPAY: //财司代付
				strNextPageURL = strContext+ "/accountinfo/a301-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a516-v.jsp&strFailPageURL="+strFailPageURL+"&lId="+strID;
				break;
			case (int)SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT: //拨子账户
				strNextPageURL = strContext+ "/accountinfo/a301-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a517-v.jsp&strFailPageURL="+strFailPageURL+"&lId="+strID;
				break;
			case (int)SETTConstant.TransactionType.SUBCLIENT_BANKPAY: //下属单位银行付款
				strNextPageURL = strContext+ "/accountinfo/a301-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a515-v.jsp&strFailPageURL="+strFailPageURL+"&lId="+strID+"&child=1";
				break;
			case (int)SETTConstant.TransactionType.DRAFTPAY: //票汇付款
				strNextPageURL = strContext+ "/accountinfo/a301-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a305-v.jsp&strFailPageURL="+strFailPageURL+"&lId="+strID;
				break;
			case (int)SETTConstant.TransactionType.INTERNALVIREMENT: //内部转账
				strNextPageURL = strContext+ "/accountinfo/a301-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a315-v.jsp&strFailPageURL="+strFailPageURL+"&lId="+strID;
				break;
			case (int)SETTConstant.TransactionType.BANKRECEIVE: //银行收款
				strNextPageURL = strContext+ "/accountinfo/a301-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a325-v.jsp&strFailPageURL="+strFailPageURL+"&lId="+strID;
				break;
			case (int)SETTConstant.TransactionType.BANKRECEIVE_GATHERING: //银行收款－上收款项
				strNextPageURL = strContext+ "/accountinfo/a301-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a325-v.jsp&strFailPageURL="+strFailPageURL+"&lId="+strID;
				break;
			case (int)SETTConstant.TransactionType.BANKRECEIVE_SUBCLIENT: //银行收款－成员单位收款
				strNextPageURL = strContext+ "/accountinfo/a301-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a325-v.jsp&strFailPageURL="+strFailPageURL+"&lId="+strID;
				break;
			case (int)SETTConstant.TransactionType.BANKRECEIVE_TOSUBCLIENT: //银行收款－转成员单位收款
				strNextPageURL = strContext+ "/accountinfo/a301-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a325-v.jsp&strFailPageURL="+strFailPageURL+"&lId="+strID;
				break;
			case (int)SETTConstant.TransactionType.SUBCLIENT_BANKRECEIVE: //下属单位银行收款
				strNextPageURL = strContext+ "/accountinfo/a301-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a325-v.jsp&strFailPageURL="+strFailPageURL+"&lId="+strID+"&child=1";
				break;
			case (int)SETTConstant.TransactionType.CASHPAY: //现金付款
				strNextPageURL = strContext+ "/accountinfo/a301-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a335-v.jsp&strFailPageURL="+strFailPageURL+"&lId="+strID;
				break;
			case (int)SETTConstant.TransactionType.CHECKPAY: //支票付款
				strNextPageURL = strContext+ "/accountinfo/a301-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a345-v.jsp&strFailPageURL="+strFailPageURL+"&lId="+strID;
				break;
			case (int)SETTConstant.TransactionType.CASHRECEIVE: //现金收款
				strNextPageURL = strContext+ "/accountinfo/a301-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a355-v.jsp&strFailPageURL="+strFailPageURL+"&lId="+strID;
				break;
			case (int)SETTConstant.TransactionType.OTHERPAY: //其他付款
				strNextPageURL = strContext+ "/accountinfo/a301-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a365-v.jsp&strFailPageURL="+strFailPageURL+"&lId="+strID;
				break;
			case (int)SETTConstant.TransactionType.CONSIGNSAVE: //委托存款
				strNextPageURL = strContext+ "/accountinfo/a301-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a375-v.jsp&strFailPageURL="+strFailPageURL+"&lId="+strID;
				break;
			case (int)SETTConstant.TransactionType.CAUTIONMONEYSAVE: //保证金存款
				strNextPageURL = strContext+ "/accountinfo/a301-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a385-v.jsp&strFailPageURL="+strFailPageURL+"&lId="+strID;
				break;
			case (int)SETTConstant.TransactionType.ONETOMULTI: //一付多收
				strNextPageURL = strContext+ "/accountinfo/a313-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&lSerialNo="+strSerialNo+"&strFailPageURL="+strFailPageURL+"&lId="+strID;
				break;

			//总账类
			case (int)SETTConstant.TransactionType.GENERALLEDGER: //总账类
				strNextPageURL = strContext+ "/accountinfo/a302-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a306-v.jsp&strFailPageURL="+strFailPageURL+"&lID="+strID;
				break;

			//总账类
			case (int)SETTConstant.TransactionType.TRANSFEE: //交易费用处理
				strNextPageURL = strContext+ "/accountinfo/a303-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a307-v.jsp&strFailPageURL="+strFailPageURL+"&lID="+strID;
				break;

			//定期交易
			case (int)SETTConstant.TransactionType.OPENFIXEDDEPOSIT: //定期开立
				strNextPageURL = strContext+ "/accountinfo/a304-c.jsp?strTransNo="+strTransNo+"&strAction=FixedQuery&strSuccessPageURL=../accountinfo/a304-v.jsp&strFailPageURL="+strFailPageURL+"&lID="+strID;
				break;
			case (int)SETTConstant.TransactionType.OPENNOTIFYACCOUNT: //通知开立
				strNextPageURL = strContext+ "/accountinfo/a304-c.jsp?strTransNo="+strTransNo+"&strAction=FixedQuery&strSuccessPageURL=../accountinfo/a334-v.jsp&strFailPageURL="+strFailPageURL+"&lID="+strID;
				break;
			case (int)SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER: //定期支取
				strNextPageURL = strContext+ "/accountinfo/a305-c.jsp?strTransNo="+strTransNo+"&strAction=FixedQuery&strSuccessPageURL=../accountinfo/a316-v.jsp&strFailPageURL="+strFailPageURL+"&lID="+strID;
				break;
			case (int)SETTConstant.TransactionType.NOTIFYDEPOSITDRAW: //通知支取
				strNextPageURL = strContext+ "/accountinfo/a305-c.jsp?strTransNo="+strTransNo+"&strAction=FixedQuery&strSuccessPageURL=../accountinfo/a346-v.jsp&strFailPageURL="+strFailPageURL+"&lID="+strID;
				break;
			case (int)SETTConstant.TransactionType.FIXEDCONTINUETRANSFER: //定期续存
				strNextPageURL = strContext+ "/accountinfo/a306-c.jsp?strTransNo="+strTransNo+"&strAction=FixedQuery&strSuccessPageURL=../accountinfo/a326-v.jsp&strFailPageURL="+strFailPageURL+"&lID="+strID;
				break;

			//信托/委托贷款
			case (int)SETTConstant.TransactionType.TRUSTLOANGRANT: //信托发放
				strNextPageURL = strContext+ "/accountinfo/a913-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a914-v.jsp&strFailPageURL="+strFailPageURL+"&lID="+strID;		
				break;
			case (int)SETTConstant.TransactionType.TRUSTLOANRECEIVE: //信托收回
				strNextPageURL = strContext+ "/accountinfo/a917-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a925-v.jsp&strFailPageURL="+strFailPageURL+"&lID="+strID;//v016
				break;
			case (int)SETTConstant.TransactionType.CONSIGNLOANGRANT: //委托发放
				strNextPageURL = strContext+ "/accountinfo/a913-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a926-v.jsp&strFailPageURL="+strFailPageURL+"&lID="+strID;//v026
				break;
			case (int)SETTConstant.TransactionType.CONSIGNLOANRECEIVE: //委托收回
				strNextPageURL = strContext+ "/accountinfo/a917-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a918-v.jsp&strFailPageURL="+strFailPageURL+"&lID="+strID;
				break;

			//利息费用支付
			case (int)SETTConstant.TransactionType.INTERESTFEEPAYMENT: //利息费用支付
				strNextPageURL = strContext+ "/accountinfo/a901-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a902-v.jsp&strFailPageURL="+strFailPageURL+"&lID="+strID;
				break;

			//多笔贷款收回
			case (int)SETTConstant.TransactionType.MULTILOANRECEIVE: //多笔贷款收回
				strNextPageURL = strContext+ "/accountinfo/a920-c.jsp?strTransNo="+strTransNo+"&lSerialNo="+strSerialNo+"&strAction="+strAction+"&strSuccessPageURL=&strFailPageURL="+strFailPageURL+"&lID="+strID;
				break;

			//贴现贷款
			case (int)SETTConstant.TransactionType.DISCOUNTGRANT: //贴现发放
				strNextPageURL = strContext+ "/accountinfo/a905-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a906-v.jsp&strFailPageURL="+strFailPageURL+"&lID="+strID;
				break;
			case (int)SETTConstant.TransactionType.DISCOUNTRECEIVE: //贴现收回
				strNextPageURL = strContext+ "/accountinfo/a909-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a910-v.jsp&strFailPageURL="+strFailPageURL+"&lID="+strID;
				break;
				
			//银团贷款
			case (int)SETTConstant.TransactionType.BANKGROUPLOANGRANT: //银团贷款发放
				strNextPageURL = strContext+ "/accountinfo/a601-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a602-v.jsp&strFailPageURL="+strFailPageURL+"&lID="+strID;
				break;
			case (int)SETTConstant.TransactionType.BANKGROUPLOANRECEIVE: //银团贷款收回
				strNextPageURL = strContext+ "/accountinfo/a603-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a604-v.jsp&strFailPageURL="+strFailPageURL+"&lID="+strID;
				break;
			

			//特殊交易
			case (int)SETTConstant.TransactionType.SPECIALOPERATION: //特殊交易
				strNextPageURL = strContext+ "/accountinfo/a307-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a308-v.jsp&strFailPageURL="+strFailPageURL+"&lId="+strID;
				break;
				
			//保证金业务
			case (int)SETTConstant.TransactionType.OPENMARGIN: //保证金开立
				strNextPageURL = strContext+ "/accountinfo/a1011-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a1012-v.jsp&strFailPageURL="+strFailPageURL+"&lID="+strID;
				break;
			case (int)SETTConstant.TransactionType.WITHDRAWMARGIN: //保证金支取
				strNextPageURL = strContext+ "/accountinfo/a1013-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a1014-v.jsp&strFailPageURL="+strFailPageURL+"&lID="+strID;
				break;
			//case (int)SETTConstant.TransactionType.INTEREST_FEE_PAY_MARGIN: //利息费用支付保证金结息
				//strNextPageURL = strContext+ "/accountinfo/a1011-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a1012-v.jsp&strFailPageURL="+strFailPageURL+"&lID="+strID;
				//break;
			//融资租赁业务
			/*case (int)SETTConstant.TransactionType.RECEIVEFINANCE: //融资租赁收款
				strNextPageURL = strContext+ "/accountinfo/a601-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a602-v.jsp&strFailPageURL="+strFailPageURL+"&lID="+strID;
				break;
			case (int)SETTConstant.TransactionType.RETURNFINANCE: //融资租赁还款
				strNextPageURL = strContext+ "/accountinfo/a603-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a604-v.jsp&strFailPageURL="+strFailPageURL+"&lID="+strID;
				break;
				*/
			case (int)SETTConstant.TransactionType.BANKPAY_DOWNTRANSFER: //银行付款－下划成员单位
				strNextPageURL = strContext+ "/accountinfo/a301-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a515-v.jsp&strFailPageURL="+strFailPageURL+"&lId="+strID+"&child=1";
				break;
			default:
				strNextPageURL = null;
				out.print("请求业务类型不对！");
		}

		if(strNextPageURL != null)
		{
			response.sendRedirect(strNextPageURL);
		}
    } catch( IException ie ) {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    }
%>