<%--
/**
 页面名称 ：a308-v.jsp
 页面功能 : 特殊业务——复核/取消复核——显示页面
 作    者 : kewen hu
 日    期 ：2004-02-25
 */
 --%>
 
<%@ page contentType="text/html;charset=gbk" %>

<%@ page import="java.sql.*" %>
<%@ page import="com.iss.itreasury.util.Log" %>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.util.OBOperation"%>
<%@ page import="com.iss.itreasury.util.DataFormat" %>
<%@ page import="com.iss.itreasury.util.Env" %>
<%@ page import="com.iss.itreasury.settlement.util.SETTHTML" %>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant" %>
<%@ page import="com.iss.itreasury.settlement.util.SETTMagnifier.*" %>
<%@ page import="com.iss.itreasury.settlement.util.*" %>
<%@ page import="com.iss.itreasury.settlement.util.NameRef" %>
<%@ page import="com.iss.itreasury.settlement.transspecial.dataentity.TransSpecialOperationInfo" %>    
<%@ page import="com.iss.itreasury.settlement.bizdelegation.TransSpecialOperationDelegation" %>
<%@ page import="com.iss.itreasury.settlement.transspecial.dataentity.SpecialOperationInfo" %>  
<%@ page import="com.iss.itreasury.settlement.transspecial.dao.Sett_TransSpecialOperationDAO"%>
<%@ page import="com.iss.itreasury.settlement.transspecial.dao.Sett_SpecialOperationDAO"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<% String strContext = request.getContextPath();%>
<% Log.print("==============strContext="+strContext);%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<jsp:include flush="true" page="/ShowMessage.jsp" />
<script language="JavaScript" src="/websett/js/Control.js" ></script>
<script language="JavaScript" src="/websett/js/SettCheck.js"></script>
<script language="javascript" src="/webob/js/Check.js"></script>
<script language="javascript" src="/webob/js/Control.js"></script>
<script language="javascript" src="/webob/js/date-picker.js"></script>
<script language="javascript" src="/webob/js/DropDownData.js"></script>
<script language="javascript" src="/webob/js/MagnifierSQL.js"></script>
<safety:resources />

<%
    Log.print("\n*******进入页面--ebank/capital/accountinfo/a308-v.jsp******\n");
    //标题变量
    String strTitle = "[账户历史明细]";
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
        /* 显示文件头 */
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);

		//定义分割视图标志位--作用范围在整个try{}中
		boolean bPayment=true;   //付款方详细资料标示
		boolean bRepayment=true; //收款方详细资料标示
		boolean bVoucher=true;	 //票据类型
		boolean bBank=true;	     //银行资料
		
		//页面辅助变量
		String strAction = null;
		String strActionResult = Constant.ActionResult.FAIL;
		String strPreSaveResult = null;	
		
		//交易状态
	    long lNstatusid =-1;
		
		//设置付款方多选一信息标志位
		long lPayChecked = 1;
		//设置收款方多选一信息标志位
		long lRepayChecked = 1;
		String sReturn = (String) request.getAttribute("lReturn");
		long lReturn = -1;
		if (sReturn != null && sReturn.trim().length() > 0) {
			lReturn = Long.parseLong(sReturn);
		}
		Log.print("=============lReturn="+lReturn);
        //判断当前特殊业务类型编号
		Object o= request.getAttribute("lNoperationtypeid");
		long SELECTED=-1;
        if(o==null)
		{
        	SELECTED=-1;
        }
		else
		{
			SELECTED=Long.valueOf(request.getAttribute("lNoperationtypeid").toString()).longValue();
		}

		if( SELECTED==-1)
		{
%>	
<form name="formSave" method="post" action="" >
	<input name="ActionID"  type="hidden" value="<%=new java.util.Date().getTime()%>">
    <input name="strAction"  type="hidden">
	<input name="strSuccessPageURL"  type="hidden" value="">
	<input name="strFailPageURL"  type="hidden" value="">
	<input name="strContinueSave" type="hidden" value="false">
	<input name="lNtransactiontypeid" type="hidden" value="<%=SETTConstant.TransactionType.SPECIALOPERATION%>">

<TABLE class="top" height="80" width="89%">
	<TBODY>

			<TR class="tableHeader">
				<TD class="FormTitle"><B>特殊业务</B></TD>
			</TR>			                        
			<TR>
				<TD valign="middle">					
					<TABLE align="center" width="97%">
						<TR>
							<TD nowrap>	特殊业务类型：
<%   
 		String strSQL="SELECT id,sname FROM sett_specialoperation where nstatusid=1 and nOfficeID="+sessionMng.m_lOfficeID;
 		SETTHTML.showCommonListControl(out,"lNoperationtypeid",strSQL,"sname","id",SELECTED,false,"disabled",false);
 %>
 									</TD>
                                </TR>
								<TR>
	
								   	<TD height="38" align="right">
										<INPUT class=button name=linksearch onclick="chgOpType()" type=button value=" 链接查找 "> 
									</TD>
								</TR>
					      </TABLE>
                	   </TD>
          			 </TR> 		 		
		</tbody>
	</table>
</form>	
<%
		}
		else
		{
%>
<form name="formSave" method="post" action="" >
	<input name="ActionID"  type="hidden" value="<%=new java.util.Date().getTime()%>">
    <input name="strAction"  type="hidden">
	<input name="strSuccessPageURL"  type="hidden" value="">
	<input name="strFailPageURL"  type="hidden" value="">
	<input name="strContinueSave" type="hidden" value="false">
	<input name="lNtransactiontypeid" type="hidden" value="<%=SETTConstant.TransactionType.SPECIALOPERATION%>">

<TABLE class="top" height="80" width="89%">
	<TBODY>

			<TR class="tableHeader">
				<TD class="FormTitle"><B>业务复核 —— 特殊业务</B></TD>
			</TR>			                        
			<TR>
				<TD valign="middle">					
					<TABLE align="center" width="97%">
						<TR>
							<TD nowrap>	特殊业务类型：
<%   
		String strSQL="SELECT id,sname FROM sett_specialoperation where nstatusid=1 and nOfficeID="+sessionMng.m_lOfficeID;
 		SETTHTML.showCommonListControl(out,"lNoperationtypeid",strSQL,"sname","id",SELECTED,false,"",false);
 %>
 									</TD>
                                </TR>
							 </TABLE>
                   </TD>
           </TR> 				
<%			
		//已选择一个业务类型
		//录入人
		long lNinputuserid = -1;
		String strNinputuserid = "";
		//复核人
		long lNcheckuserid = -1;
		String strNcheckuserid = "";
		//录入时间：年月日时分秒
		Timestamp tsDtinput = null;
		String strDtinput = "";
		//修改时间：年月日时分秒
		Timestamp tsDtmodify = null;
		String strDtmodify = "";
		//确认摘要
		String strSconfirmabstract = "";
		//取消复核摘要
		String strScanclecheckabstract = "";
		//通存通兑对方办事处编号
		//确认人
		long lNconfirmuserid = -1;
		//签认人
		long lNsignuserid = -1;
		//付款方客户ID
		long lNPayClientID = -1;
		//付款方账户ID
		long lNpayaccountid = -1;
		//付款方子账户ID
		long lNpaysubaccountid = -1;
		//付款方存单号
		String strSpayfixeddepositno = "";
		//付款方合同ID
		long lNpaycontractid = -1;
		//strNpaycontractno
		String strNpaycontractno = "";
		//付款方放款通知单ID
		long lNpayloannoteid = -1;
		//strNpayloannoteid
		String strNpayloannoteid = "";
		//付款方开户行ID
		long lNpaybankid = -1;
		//strNpaybankid
		String strNpaybankid = "";
		//大桥提出行
		String strSpayextbankno = "";
		//付款方银行单边账类型ID
		long lNpaysinglebankaccounttypeid = -1;
		String strNpaysinglebankaccounttypeid = "";
		//付款方总账类ID
		long lNpaygeneralledgertypeid = -1;
		//strPaygeneralledgertypeid
		String strPaygeneralledgertypeid = "";
		//付款金额
		double dMpayamount = 0;
		String strMpayamount = "0.0";
		//付款方向
		long lNpaydirection = -1;
		String strNpaydirection = "借";
		//收款方向
		long lNreceivedirection = -1;
		String strNreceivedirection = "借";
		//收款金额
		double dMreceiveamount = 0;
		String strMreceiveamount = "0.0";
		//收款方总账类ID
		long lNreceivegeneralledgertypeid = -1;
		//strReceivegeneralledgertypeid
		String strReceivegeneralledgertypeid = "";
		//收款方客户编号
		long lNreceiveclientid = -1;
		//收款方银行单边账类型ID
		long lNreceivesinglebankaccounttypei = -1;
		String strNreceivesinglebankaccounttypei = "";
		//提入行
		String strSreceiveextbankno = "";
		//收款方开户行ID
		long lNreceivebankid = -1;
		//strReceivebankid
		String strReceivebankid = "";
		//收款方放款通知单ID
		long lNreceiveloannoteid = -1;
		//strReceiveloannoteid
		String strReceiveloannoteid = "";
		//收款方合同ID
		long lNreceivecontractid = -1;
		//strReceivecontractid
		String strReceivecontractid = "";
		//收款方存单号
		String strSreceivefixeddepositno = "";
		//收款方账户ID
		long lNreceiveaccountid = -1;
		//收款方子账户ID
		long lNreceivesubaccountid = -1;
		//票据类型ID
		long lNbilltypeid = -1;
		String strNbilltypeid = "电汇";
		//票据号
		String strSbillno = "";
		//票据发放银行ID
		long lNbillbankid = -1;
		//非财务公司账号（银行账号）
		String strSextaccountno = "";
		//银行支票号
		String strSbankchequeno = "";
		//非财务公司账户名称（银行账户名称）
		String strSextclientname = "";
		//报单号
		String strSdeclarationno = "";
		//汇入省
		String strSremitinprovince = "";
		//汇入市
		String strSremitincity = "";
		//汇入行名称
		String strSremitinbank = "";
		//执行日
		Timestamp tsDtexecute = null;
		//String  tsDtexecute =null;
		String strDtexecute = "";
		//起息日
		Timestamp tsDtintereststart = null;
		String strDtintereststart = "";
		//摘要
		String strSabstract = "";
		//摘要ID
		long lNabstractid = -1;
		//交易号
		String strStransno = "";
		//币种
		long lNcurrencyid = -1;
		//办事处
		long lNofficeid = -1;
		//内部编号
		long lId = -1;

		//交易类型lNtransactiontypeid
		long lNtransactiontypeid = -1;
		//特殊交易类型
		long lNoperationtypeid = -1;

		//付款方客户code
		String strPayClientCode = "";
		//付款方客户名称
		String strPayClientName = "";
		//付款方账号
		String strPayAccountNo = "";
		//lPayAccountClientID
		//long lPayAccountClientID=-1;

		//收款方客户code
		String strReceiveClientCode = "";
		//收款方客户名称
		String strReceiveClientName = "";
		//strReceiveAccountNo
		String strReceiveAccountNo = "";

		String strId = "-1";
		strId = (String) request.getAttribute("lId");
		
		if (strId != null && strId.compareTo("-1") != 0)
		{
			lId = Long.parseLong(strId);
		}
		Log.print("***************lId=*********************"+lId);

		//TransSpecialOperationDelegation tsod = new TransSpecialOperationDelegation();
		Sett_TransSpecialOperationDAO transdao = new Sett_TransSpecialOperationDAO();
		Sett_SpecialOperationDAO dao = new Sett_SpecialOperationDAO();

		//TransSpecialOperationInfo tsoi = (TransSpecialOperationInfo) tsod.findDetailByID(lId);
		TransSpecialOperationInfo tsoi = (TransSpecialOperationInfo) transdao.findByID(lId);

		//default tsoi value is from c004.jsp when the c004.jsp operation failured.
		if (request.getAttribute("cuurentResult") != null)
		{
			tsoi = (TransSpecialOperationInfo) request.getAttribute("cuurentResult");
		}

		if (tsoi != null)
		{
			lNcheckuserid = tsoi.getNcheckuserid();
			strNcheckuserid = DataFormat.formatString(NameRef.getUserNameByID(lNcheckuserid));

			//复核时间
			tsDtmodify = tsoi.getDtmodify();
			strDtmodify = DataFormat.formatDate(tsDtmodify);
			if (strNcheckuserid == null || (strNcheckuserid.compareTo("") == 0))
			{
				strDtmodify = "";
			}
			else
			{
				strDtmodify = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
			}

			//录入人
			lNinputuserid = tsoi.getNinputuserid();
			strNinputuserid = DataFormat.formatString(NameRef.getUserNameByID(lNinputuserid));
			//录入时间	
			tsDtinput = tsoi.getDtinput();
			strDtinput = DataFormat.formatDate(tsDtinput);

			//付款方客户ID
			lNPayClientID = tsoi.getNpayclientid();
			if (lNPayClientID > 0)
			{
				lPayChecked = 1;
			}
			Log.print("tsoi.getNpayclientid(): " + lNPayClientID);
			strPayClientCode = NameRef.getClientCodeByID(lNPayClientID);
			Log.print("NameRef.getClientCodeByID(lNPayClientID): " + strPayClientCode);
			strPayClientName = NameRef.getClientNameByID(lNPayClientID);
			Log.print("NameRef.getClientNameByID(lNpayClientID): " + strPayClientName);
			//付款方账户ID
			lNpayaccountid = tsoi.getNpayaccountid();
			strPayAccountNo = NameRef.getAccountNoByID(lNpayaccountid);
			Log.print("NameRef.getAccountNoByID(lNpayaccountid): " + strPayAccountNo);
			//付款方子账户ID
			lNpaysubaccountid = tsoi.getNpaysubaccountid();
			//付款方存单号
			strSpayfixeddepositno = tsoi.getSpayfixeddepositno();
			//付款方合同ID
			lNpaycontractid = tsoi.getNpaycontractid();
			//strNpaycontractno
			strNpaycontractno = NameRef.getContractNoByID(lNpaycontractid);
			//付款方放款通知单ID
			lNpayloannoteid = tsoi.getNpayloannoteid();
			if (lNpayloannoteid != -1)
			{
				strNpayloannoteid = NameRef.getPayFormNoByID(lNpayloannoteid);
			}
			//付款方开户行ID
			lNpaybankid = tsoi.getNpaybankid();
			if (lNpaybankid > 0)
			{
				lPayChecked = 2;
			}
			//strNpaybankid
			strNpaybankid = NameRef.getBankNameByID(lNpaybankid);
			//付款方银行单边账类型ID
			lNpaysinglebankaccounttypeid = tsoi.getNpaysinglebankaccounttypeid();
			if (lNpaysinglebankaccounttypeid == SETTConstant.SingleBankAccountType.TRANSFER)
			{
				strNpaysinglebankaccounttypeid = SETTConstant.SingleBankAccountType.getName(SETTConstant.SingleBankAccountType.TRANSFER);
			}
			else if (lNpaysinglebankaccounttypeid == SETTConstant.SingleBankAccountType.CASH)
			{
				strNpaysinglebankaccounttypeid = SETTConstant.SingleBankAccountType.getName(SETTConstant.SingleBankAccountType.CASH);
			}

			//付款方总账类ID
			lNpaygeneralledgertypeid = tsoi.getNpaygeneralledgertypeid();
			if (lNpaygeneralledgertypeid > 0)
			{
				lPayChecked = 3;
			}
			strPaygeneralledgertypeid = NameRef.getGLTypeDescByID(lNpaygeneralledgertypeid);
			//付款金额
			dMpayamount = tsoi.getMpayamount();
			strMpayamount = DataFormat.formatAmount(dMpayamount);
			//付款方向
			lNpaydirection = tsoi.getNpaydirection();
			strNpaydirection = SETTConstant.DebitOrCredit.getName(lNpaydirection);
			//收款方向
			lNreceivedirection = tsoi.getNreceivedirection();
			strNreceivedirection = SETTConstant.DebitOrCredit.getName(lNreceivedirection);
			//收款金额
			dMreceiveamount = tsoi.getMreceiveamount();
			strMreceiveamount = DataFormat.formatAmount(dMreceiveamount);

			//收款方总账类ID
			lNreceivegeneralledgertypeid = tsoi.getNreceivegeneralledgertypeid();
			if (lNreceivegeneralledgertypeid > 0)
			{
				lRepayChecked = 3;
				Log.print("lRepayChecked=3");
			}
			strReceivegeneralledgertypeid = NameRef.getGLTypeDescByID(lNreceivegeneralledgertypeid);
			//收款方客户编号
			lNreceiveclientid = tsoi.getNreceiveclientid();
			if (lNreceiveclientid > 0)
			{
				lRepayChecked = 1;
				Log.print("lRepayChecked=1");
			}
			strReceiveClientCode = NameRef.getClientCodeByID(lNreceiveclientid);
			strReceiveClientName = NameRef.getClientNameByID(lNreceiveclientid);
			//收款方银行单边账类型ID
			lNreceivesinglebankaccounttypei = tsoi.getNreceivesinglebankaccounttypei();

			if (lNreceivesinglebankaccounttypei == SETTConstant.SingleBankAccountType.TRANSFER)
			{
				strNreceivesinglebankaccounttypei = SETTConstant.SingleBankAccountType.getName(SETTConstant.SingleBankAccountType.TRANSFER);
			}
			else if (lNreceivesinglebankaccounttypei == SETTConstant.SingleBankAccountType.CASH)
			{
				strNreceivesinglebankaccounttypei = SETTConstant.SingleBankAccountType.getName(SETTConstant.SingleBankAccountType.CASH);
			}
			//收款方开户行ID
			lNreceivebankid = tsoi.getNreceivebankid();
			if (lNreceivebankid > 0)
			{
				lRepayChecked = 2;
				Log.print("lRepayChecked=2");
			}
			strReceivebankid = NameRef.getBankNameByID(lNreceivebankid);
			//收款方放款通知单ID
			lNreceiveloannoteid = tsoi.getNreceiveloannoteid();
			if (lNreceiveloannoteid != -1)
			{
				strReceiveloannoteid = NameRef.getPayFormNoByID(lNreceiveloannoteid);
			}
			//收款方合同ID
			lNreceivecontractid = tsoi.getNreceivecontractid();
			strReceivecontractid = NameRef.getContractNoByID(lNreceivecontractid);
			//收款方存单号
			strSreceivefixeddepositno = tsoi.getSreceivefixeddepositno();
			//收款方账户ID
			lNreceiveaccountid = tsoi.getNreceiveaccountid();
			strReceiveAccountNo = NameRef.getAccountNoByID(lNreceiveaccountid);
			//收款方子账户ID
			lNreceivesubaccountid = tsoi.getNreceivesubaccountid();
			//非财务公司账号（银行账号）
			strSextaccountno = tsoi.getSextaccountno();
			//银行支票号
			strSbankchequeno = tsoi.getSbankchequeno();
			//非财务公司账户名称（银行账户名称）
			strSextclientname = tsoi.getSextclientname();
			//报单号
			strSdeclarationno = tsoi.getSdeclarationno();
			//汇入省
			strSremitinprovince = tsoi.getSremitinprovince();
			//汇入市
			strSremitincity = tsoi.getSremitincity();
			//汇入行名称
			strSremitinbank = tsoi.getSremitinbank();
			//执行日
			tsDtexecute = tsoi.getDtexecute();
			strDtexecute = DataFormat.formatDate(tsDtexecute);
			//起息日
			tsDtintereststart = tsoi.getDtintereststart();
			//复核/取消复核摘要
			strDtintereststart = DataFormat.formatDate(tsDtintereststart);
			//摘要
			strSabstract = tsoi.getSabstract();
			if (tsoi.getScanclecheckabstract() != null)
			{
				strScanclecheckabstract = tsoi.getScanclecheckabstract();
			}
			//摘要ID
			lNabstractid = tsoi.getNabstractid();
			//交易号
			strStransno = tsoi.getStransno();
			//币种
			lNcurrencyid = tsoi.getNcurrencyid();
			//办事处
			lNofficeid = tsoi.getNofficeid();
			//内部编号
			lId = tsoi.getId();
			Log.print("v006 lId:" + lId);
			//交易类型lNtransactiontypeid
			lNtransactiontypeid = tsoi.getNtransactiontypeid();
			//特殊交易类型
			lNoperationtypeid = tsoi.getNoperationtypeid();
			//交易状态
			lNstatusid = tsoi.getNstatusid();
		}

		//从Request中获得参数
		//页面控制参数
		if (request.getAttribute("strActionResult") != null)
		{
			strActionResult = (String) request.getAttribute("strActionResult");
		}
		if (request.getAttribute("strAction") != null)
		{
			strAction = (String) request.getAttribute("strAction");
		}
		if (request.getAttribute("strPreSaveResult") != null)
		{
			strPreSaveResult = (String) request.getAttribute("strPreSaveResult");
		}
		//格式化数据
		strSabstract = DataFormat.formatString(strSabstract);
		strSdeclarationno = DataFormat.formatString(strSdeclarationno);
		strSbankchequeno = DataFormat.formatString(strSbankchequeno);
		strSremitincity = DataFormat.formatString(strSremitincity);
		strSremitinprovince = DataFormat.formatString(strSremitinprovince);
		strSremitinbank = DataFormat.formatString(strSremitinbank);
		strSextclientname = DataFormat.formatString(strSextclientname);
		strSextaccountno = DataFormat.formatString(strSextaccountno);
		strSbillno = DataFormat.formatString(strSbillno);
		strSreceiveextbankno = DataFormat.formatString(strSreceiveextbankno);
		strSreceivefixeddepositno = DataFormat.formatString(strSreceivefixeddepositno);
		strSpayextbankno = DataFormat.formatString(strSpayextbankno);
		strSpayfixeddepositno = DataFormat.formatString(strSpayfixeddepositno);
		strStransno = DataFormat.formatString(strStransno);

		strPayClientCode = (strPayClientCode == null) ? "" : strPayClientCode;

		if (request.getAttribute("lNoperationtypeid") != null)
		{
			if (lNoperationtypeid != -1)
			{
				//com.iss.itreasury.settlement.transspecial.dataentity.SpecialOperationInfo soi = (com.iss.itreasury.settlement.transspecial.dataentity.SpecialOperationInfo) tsod.findSettingDetailByID(lNoperationtypeid);
				SpecialOperationInfo soi = (SpecialOperationInfo) dao.findByID(lNoperationtypeid);
				String strContent = "";
				if (soi != null)
				{
					strContent = soi.getScontent();
					Log.print("v006 strContent: " + strContent);
				}
				else
				{
					bPayment = false;
					bRepayment = false;
					bVoucher = false;
					bBank = false;
				}
				//设置显示分割视图标志位
				if (strContent.indexOf("付款方详细资料") == -1)
				{
					bPayment = false;
				}
				else
				{
					bPayment = true;
				}
				if (strContent.indexOf("收款方详细资料") == -1)
				{
					bRepayment = false;
				}
				else
				{
					bRepayment = true;
				}
				if (strContent.indexOf("票据资料") == -1)
				{
					bVoucher = false;
				}
				else
				{
					bVoucher = true;
				}
				if (strContent.indexOf("银行资料") == -1)
				{
					bBank = false;
				}
				else
				{
					bBank = true;
				}
			}
		}
%>   
				          
						            
                       <TR>
                                <TD><input name="lId" type="hidden" value="<%=lId%>">
						  <input name="tsDtmodify" type="hidden" value="<%=tsDtmodify.toString()%>">
		    	   <TABLE align=center width="97%">
                    <TBODY>
                     <TR>					  
   		 <%
                if (bPayment )
                {
                //显示付款方详细信息
          %>
                         <TD width="50%">
                             <TABLE align=left border=1 borderColor=#999999 width="99%">
                                 <TBODY>
                                    <TR borderColor=#E8E8E8> 
									        <td>&nbsp;</td>                                       
                                            <TD nowrap width="150" ><U>付款方详细资料</U></TD>
                                            <TD>&nbsp;</TD>
                                    </TR>

									<!--付款方客户编号放大镜-->
									<TR borderColor=#E8E8E8>
									  <TD align="center"><INPUT name="lPay" type="radio" value="1"  disabled   <%=(lPayChecked==1?"checked":"")%>></TD> 	
										<td nowrap>付款方客户编号:</td>
										   <TD><input name="lNPayClientID" class="box" type="Text"  disabled  value="<%=strPayClientCode%>" >
										</TD>																		
									</TR>
									
                                    <TR borderColor=#E8E8E8>
                                           <td>&nbsp;</td>
                                            <TD nowrap>付款方客户名称：</TD>
                                            <TD>
											<%Log.print("v006 strPayClientName: "+strPayClientName);%>
											<textarea name="strPayClientName" disabled class="box" rows=2 cols=30><%=strPayClientName%></textarea>
                                            </TD>
                                    </TR>

									<!--//付款方账号放大镜-->
                                  	<TR borderColor=#E8E8E8>
									    <td>&nbsp;</td>
										<TD nowrap>付款方账号:</TD>									
										<td><input name="strPayAccountNo" class="box" type="Text"  disabled value="<%=strPayAccountNo%>" ></td>
									</TR>
									
									<!--存款单据号放大镜-->
                                    <TR borderColor=#E8E8E8>
									<td>&nbsp;</td>
									  <TD nowrap>存款单据号:</TD>
									  <td><input name="strSpayfixeddepositno" type="text" class="box" disabled value="<%=strSpayfixeddepositno%>">  </td>
									</TR>
									
									<!--合同号放大镜-->
                                	<TR borderColor=#E8E8E8>
									<td>&nbsp;</td>
										<TD nowrap>合同号:</TD>
									    <td><input name="strNpaycontractno" type="text" class="box" disabled value="<%=strNpaycontractno%>">  </td>
									</TR>
									
									
                                    <!--放款通知单据号放大镜-->       
         							<TR borderColor=#E8E8E8>
									  <td>&nbsp;</td>
									  <TD nowrap>放款通知单据号:</TD>
									  <td><input name="strNpayloannoteid" type="text" class="box" disabled value="<%=strNpayloannoteid%>">  </td>
									</TR>
									
									<!--开户行放大镜-->
                             		<TR borderColor=#E8E8E8>
											<TD align="center"><INPUT name="lPay" type="radio" value="2"  disabled   <%=(lPayChecked==2?"checked":"")%>></TD> 
                                            <TD nowrap>开户行:</td>
											<td>
											<textarea name="strNpaybankid" disabled class="box"  rows=2 cols=30><%=strNpaybankid%></textarea>
											</td>
								</TR>		
									
									<!--总账类类型放大镜-->
                                       <TR borderColor=#E8E8E8>
									     <TD align="center"><INPUT name="lPay" type="radio" value="3" disabled   <%=(lPayChecked==3?"checked":"")%>></TD> 
										<TD nowrap>总账类类型:</TD> 	
										<td ><INPUT type="Text" class=box name="strPaygeneralledgertypeid" disabled value="<%=strPaygeneralledgertypeid%>" ></td>																			
									</TR>
									
									<TR borderColor=#E8E8E8>
                      <TD nowrap>&nbsp;</TD>										
                      <TD nowrap>金额： </TD>
                      <td nowrap > 
					  <%=sessionMng.m_strCurrencySymbol%>
<input type="Text"  maxlength="20"class=tar name="strMpayamount" disabled value="<%=strMpayamount%>" >
<input type="Text" class=box name="strNpaydirection" size="3" disabled value="<%=strNpaydirection%>" >
                      </td>
									</TR>
                                    			                                       
			                    </TBODY>
			                </TABLE>
							 <!--付款方细节表结束-->
						</TD>
							<%
									}
									if (bRepayment )
									{
									//显示收款方详细信息
							%>
							<TD width="50%">
							 <!--收款方细节表开始-->
							     <TABLE align=left border=1 borderColor=#999999 width="99%">
									<TBODY>
									              
									<TR borderColor=#E8E8E8>
									
                      <TD>&nbsp;</TD>
                      <TD width="150" nowrap><u>收款方详细资料</u></TD>
					   <TD>&nbsp;</TD>									
									</TR>
									<!--收款方客户编号放大镜-->
									<TR borderColor=#E8E8E8>
										<TD align="center"><INPUT name="lRepay" type="radio" value="1" disabled  <%=(lRepayChecked==1?"checked":"")%>  ></TD>									
										<TD nowrap>收款方客户编号:</TD>
										<td ><INPUT type="Text" class=box name="strReceiveClientCode" disabled value="<%=strReceiveClientCode%>" ></td>																			
									</TR>
									<TR borderColor=#E8E8E8>
										<td>&nbsp;</td>									
										<TD nowrap>收款方客户名称：</TD>
										<TD>
										<textarea name="strReceiveClientName" disabled class="box"  rows=2 cols=30><%=strReceiveClientName%></textarea>
										</TD>
									</TR>
									<!--收款方账号放大镜-->
									<TR borderColor=#E8E8E8>
										<td>&nbsp;</td>									
										<TD nowrap>收款方账号:</TD>
										<TD>
										   <input name="strReceiveAccountNo" class=box disabled   value="<%=strReceiveAccountNo%>">
										</TD>
									</TR>
									<!--存款单据号放大镜-->
									<TR borderColor=#E8E8E8>
									<td>&nbsp;</td>
									  <TD nowrap>存款单据号:</TD>
									  <TD>
										   <input name="strSreceivefixeddepositno" class=box disabled   value="<%=strSreceivefixeddepositno%>">
										</TD>
								</TR>
									<!--合同号放大镜-->  									            
									<TR borderColor=#E8E8E8>
									<td>&nbsp;</td>
										<TD nowrap>合同号:</TD>
										 <TD>
										   <input name="strReceivecontractid" class=box disabled   value="<%=strReceivecontractid%>">
										</TD>
									</TR>
									<!--放款通知单据号放大镜-->
									<TR borderColor=#E8E8E8>
									<td>&nbsp;</td>
										 <TD nowrap>放款通知单据号:</TD>
										  <TD>
										   <input name="strReceiveloannoteid" class=box disabled   value="<%=strReceiveloannoteid%>">
										</TD>
									</TR>
									<!--开户行放大镜-->
									<TR borderColor=#E8E8E8>
									    <TD align="center"><INPUT name="lRepay" type="radio"  disabled value="2"  <%=(lRepayChecked==2?"checked":"")%>    ></TD>
										<TD nowrap>开户行:</td>
										 <TD>
										   <textarea name="strReceivebankid" disabled class="box"  rows=2 cols=30><%=strReceivebankid%></textarea>
										</TD>
									</TR>
									<!--总账类型放大镜-->
									<TR borderColor=#E8E8E8>
									 <TD align="center"><INPUT name="lRepay" type="radio" value="3"  disabled   <%=(lRepayChecked==3?"checked":"")%>></TD> 
									<TD nowrap>总账类类型:</TD> 
									<td><INPUT type="Text" class=box disabled name="strReceivegeneralledgertypeid" value="<%=strReceivegeneralledgertypeid%>" ></td>
									</TR>
									<TR borderColor=#E8E8E8>
										<td>&nbsp;</td>
                      <TD  nowrap>金额：</TD>
                      <TD nowrap> 
					    <%=sessionMng.m_strCurrencySymbol%>
                        <INPUT type="Text" class=tar disabled name="strMreceiveamount" value="<%=strMreceiveamount%>" >
										<INPUT type="Text" class=box disabled name="strNreceivedirection" size="3" value="<%=strNreceivedirection%>" >
										</TD>										
										</TR>
									</TBODY>
								</TABLE>
							 <!--收款方细节表结束-->
							</TD>
							<%
								}
							%>
					  </TR>
					</TBODY>
				  </TABLE>
				  <!--  外表结束-->	
				  <TR>
				  <TD>
				  <TABLE align=center height="30" width="97%">
					 <TBODY>
				  <%
							if(bVoucher)//(bVoucher)
							{
							//显示票据类型
				  %>
				  <!--大桥 票据类型  起始-->
					   <TR borderColor=#ffffff>
							<TD colSpan=4><HR></TD>
						</TR>
						<TR borderColor=#ffffff>
							<TD colSpan=2 height=14 nowrap><u>票据资料</u></TD>
							<TD height=14 width="18%">&nbsp;</TD>
							<TD height=14 width="33%">&nbsp;</TD>
						</TR>
						<TR borderColor=#ffffff>
							<TD width="17%" nowrap>票据类型：</TD>		
						<!--票据类型是一个listbox-->
						<td><INPUT type="Text" class=box disabled name="strNbilltypeid" value="<%=strNbilltypeid%>" ></td>
							<!--票据号放大镜-->
							<TD width="17%" nowrap>票据号:</TD>	
							<td><INPUT type="Text" class=box disabled name="strSbillno" value="<%=strSbillno%>" ></td>	
							</TR>					
				  <!--大桥 票据类型  结束-->
				    <%
							}
							if(bBank)
							{
							//显示银行资料
					%>
					<!--银行资料 起始-->
						<TR borderColor=#ffffff>
							<TD colSpan=4><HR></TD>
						</TR>
						<TR borderColor=#ffffff>
									<TD height=14 nowrap><u> 银行资料</u></TD>
									<TD>&nbsp;</TD>
									<TD>&nbsp;</TD>
						</TR>
						<TR borderColor=#ffffff>
								<!--银行账号放大镜-->
								<td nowrap>银行账号:</td>
								<td><INPUT type="Text" class=box disabled name="strSextaccountno" value="<%=strSextaccountno%>" ></td>	
								<TD width="18%" nowrap>银行账户客户名称：</TD>
									<TD width="33%">
									<INPUT class=box disabled name="strSextclientname" value="<%=strSextclientname%>" >
									</TD>
						</TR>
						<TR borderColor=#ffffff>
									<TD height=18 width="17%" nowrap>银行支票号：</TD>
									<TD height=18>
									<INPUT class=box disabled name="strSbankchequeno"  value="<%=strSbankchequeno%>"  size="20">
									</TD>
									<TD height=18 width="18%" nowrap>银行报单号：</TD>
									<TD height=18 width="33%">
									<INPUT class=box disabled name="strSdeclarationno" value="<%=strSdeclarationno%>" >
									</TD>
						</TR>
						<TR borderColor=#ffffff>
									<TD width="17%" nowrap>汇入地(省)：</TD>
									<TD><INPUT class=box name="strSremitinprovince" disabled value="<%=strSremitinprovince%>" ></TD>
									<TD width="18%" nowrap>汇入地(市)：</TD>
									<TD width="33%"><INPUT class=box name="strSremitincity" disabled value="<%=strSremitincity%>" ></TD>
						</TR>
						<TR borderColor=#ffffff>
									<TD height=18 width="17%" nowrap>汇入行名称：</TD>
									<TD height=18><INPUT class=box name="strSremitinbank" disabled value="<%=strSremitinbank%>" ></TD>
									<TD height=18 width="18%">&nbsp;</TD>
									<TD height=18 width="33%">&nbsp;</TD>
						</TR>
								<!--银行资料 结束-->
	  <%
						}
	%>	
							  <!--其他信息 开始-->
						<TR borderColor=#ffffff>
								<TD colSpan=4><HR></TD>
						</TR>
						
						<TR borderColor=#ffffff>
								<!--摘要放大镜-->
							<td nowrap>摘要:</td>
							<td><INPUT type="Text" class=box name="strSabstract" disabled value="<%=strSabstract%>" ></td>	
						</TR>
						<TR borderColor=#ffffff>
									<TD width="17%" nowrap>起息日：</TD>
									<TD nowrap>
									<INPUT class=box name="strDtintereststart" disabled value="<%=strDtintereststart%>" >
									</TD>
									<TD align=left width="18%" nowrap>执行日：</TD>
									<TD align=left width="33%" nowrap>
			                          <INPUT class=box name="strDtexecute" disabled value="<%=strDtexecute%>" >
									</TD>
						</TR>		
								<!--其他信息 结束-->	
			 		</TBODY>
			</TABLE> 	
			</TD>
			</TR>
			<TR>
				<TD height="38"  align="center">
				 <TABLE align=center border=0 cellPadding=2 cellSpacing=2 height=15 
      width="90%">
        <TBODY>
        <TR>
          <TD colSpan=3 height=19>
           	<div align="right">
					<input type="button" name="print" value=" 打 印 " readonly class="button" onClick="doPrint();">
					<input type="button" name="close" value=" 关 闭 " class="button" onClick="window.close();">
				</div>
			</TD>
			</TR>
			</TBODY>
			</TABLE>
				
				</TD>
			</TR>
			
			<TR borderColor=#ffffff>
				<TD colSpan=4><HR></TD>
			</TR>
					
			<TR>
				<TD>					
				      <table height="30" width="97%" border="0" align="center">
				        <tr> 
						  <td nowrap>复核备注：
						    <input type="text" class=box name="strScanclecheckabstract" value="<%=strScanclecheckabstract%>">
						  </td>
				          <td nowrap>录入人：<%=strNinputuserid%> </td>
				          <td nowrap>录入日期：<%=strDtinput%></td>		
						  <td nowrap>复核人:  <%=strNcheckuserid%> </td>		         
						  <td nowrap>复核日期：<%=strDtmodify%></td>
				          <td nowrap>状态：<%=SETTConstant.TransactionStatus.getName(lNstatusid)%></td>
				        </tr>
				      </table>
				</TD>
			</TR> 
<script language="JavaScript">
function doPrint()
{
	window.open("<%=strContext%>/accountinfo/a409-v.jsp?lID=<%=lId%>&lReturn=<%=lReturn%>&strSuccessPageURL=/iTreasury-ebank/accountinfo/a409-v.jsp&strFailPageURL=/iTreasury-ebank/accountinfo/a308-v.jsp");
}
</script>			
		 <%
		  }
		  %>	
		 
	</TBODY>
</TABLE>
</form>
<!--页面默认设置 废代码-->

<script language="JavaScript">
<%
	if(!"Query".equalsIgnoreCase(strAction))
	{
%>
firstFocus(formSave.strScanclecheckabstract);
<% 
	if(lNstatusid == SETTConstant.TransactionStatus.SAVE)
	{   
%>
//setSubmitFunction("doCheck()");
<%
   	}
	else if(lNstatusid == SETTConstant.TransactionStatus.CHECK)
	{
%>	
//setSubmitFunction("doUndoCheck()");	
function allFields()
{
	this.chkstrScanclecheckabstract=new Array("strScanclecheckabstract","复核备注","string",1);
}
<%
	}
%>
setFormName("formSave"); 
<%
	}
%>
//标识是否已提交请求
var isSubmited = false;

function checkSuccess()
{
    if (confirm("复核成功，是否打印?"))
    {
        //code
    }
	else
	{
		document.location.href="<%=strContext%>/settlement/tran/special/view/v004.jsp";
	}
}

function doCheck()
{
    if(isSubmited == true)
    {
        alert("请求已提交，请等候！");
        return;
    }

	if (confirm("是否复核此笔业务数据?")) 
	{
		isSubmited = true;
		document.formSave.strAction.value="<%=SETTConstant.Actions.CHECK%>";
		showSending();
		document.formSave.submit();
	}
}
function doUndoCheck()
{
    if(isSubmited == true)
    {
        alert("请求已提交，请等候！");
        return;
    }

	if(!validateFields(formSave)) return;
	
	if (confirm("是否取消复核此笔业务数据?")) 
	{
		isSubmited = true;
		document.formSave.strAction.value="<%=SETTConstant.Actions.CANCELCHECK%>";
		showSending();
		document.formSave.submit();
	}
}
function doReturn()
{
	<%
	if(lNstatusid == SETTConstant.TransactionStatus.SAVE)
	{%>
		document.location.href="<%=strContext%>/settlement/tran/special/view/v004.jsp";
	<%
	}else if(lNstatusid == SETTConstant.TransactionStatus.CHECK)
	{
	%>
		document.location.href="<%=strContext%>/settlement/tran/special/control/c003.jsp?lNstatusids=<%=SETTConstant.TransactionStatus.CHECK%>&lNtransactiontypeid=<%=SETTConstant.TransactionType.SPECIALOPERATION%>&strSuccessPageURL=../view/v005.jsp&strFailPageURL=../view/v005.jsp&strAction=<%=SETTConstant.Actions.LINKSEARCH%>";
	<%}else{%>
		document.location.href="<%=strContext%>/settlement/tran/special/view/v004.jsp";
	<%}%>
}

function allFields()
{
	this.aa = new Array("strScanclecheckabstract","取消复核备注","string",1);
} 

function chgOpType()
{
	window.location="../view/v006.jsp?lNoperationtypeid="+ formSave.lNoperationtypeid.value;
}
</script>

<%
    //成功结果导航
			if(Constant.ActionResult.SUCCESS.equals(strActionResult) && String.valueOf(SETTConstant.Actions.CREATESAVE).equals(strAction))
			{
				%>
					<script language="JavaScript">
						checkSuccess();
					</script>
				<%
			}
    } catch(IException ie) {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    }
    OBHtml.showOBHomeEnd(out);
%>

<%@ include file="/common/SignValidate.inc" %>
