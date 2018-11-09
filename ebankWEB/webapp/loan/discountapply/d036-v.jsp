<%@ page contentType="text/html;charset=gbk" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"/>
<jsp:useBean id="Magnifier" scope="request" class="com.iss.itreasury.ebank.util.OBMagnifier"/>
<%@ page import="com.iss.itreasury.util.*,
                com.iss.itreasury.ebank.util.*,
    			com.iss.itreasury.ebank.obdiscountapply.bizlogic.*,
                com.iss.itreasury.ebank.obdiscountapply.dataentity.*,
				com.iss.itreasury.loan.util.*,
    			java.sql.*,
                java.rmi.RemoteException,
				java.util.*"
%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%
	/* 标题固定变量 */
	String strTitle = "[贴现凭证]";
%>	
<%
/////////////////////////////////////////////////////////////////////////////////
	
	try{    
		
	   /* 用户登录检测 */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }

		//定义变量，获取请求参数
		
		String strTmp = "";
		String strControl = "";
		String strBackURL = "S120-1";
		String strDisabled = " disabled";
		String strAction = "";
			
		long txtContract = -1;			//贴现标示
		String txtContractCode = "";	//贴现申请编号
		long txtClient = -1;			//单位标示
		String txtClientCtrl = "";		//单位名称
		

		long lContractID = -1;			//贴现标示
		long lCredenceID = -1;			//贴现凭证标示
		Timestamp tsFillDate = null;
		long lDraftTypeID = -1;
		String strDraftTypeName = "";
		String strDraftCode = "";
		Timestamp tsPublicDate = null;
		Timestamp tsAtTerm = null;
		String strApplyClient = "";
		String strApplyAccount = "";
		String strApplyBank = "";
		String strAcceptClient = "";
		String strAcceptAccount = "";
		String strAcceptBank = "";
		double dAmount = 0;
		double dRate = 0;
		double dInterest = 0;
		double dCheckAmount = 0;
		double dBillAmount = 0;
		double dBillInterest = 0;
		double dBillCheckAmount = 0;
		double dBillSum=0;
		double[] dResult = new double[3];

		long lGrantTypeID = -1;				//放款方式
		long lAccountBankID = -1;			//开户银行
		String strAccountBankName = "";		//开户银行
		String strAccountBankCode = "";		//开户银行账号
		String strReceiveClientCode = "";	//收款单位账号
		String strReceiveClientName = "";	//收款单位名称
		String strRemitBank = "";			//汇入行
		String strRemitInProvince = "";   	//汇入地（省）
		String strRemitInCity = "";       	//汇入地（市）
		long lGrantCurrentAccountID = -1;	//发放至活期账户
		String strGrantCurrentAccountCode = "";//发放至活期账户
		
		//////////////////////////////
		String strContractCode = "";
		String strCredenceCode = "";
		long lInputUserID = -1;
		String strInputUserName = "";
		Timestamp tsInputDate = null;
		long lStatusID = -1;
		long lCount = 0;
		//////////////////////////////
		String strBillID = null;
		long[] lBillIDArray = new long[1000];
///////control///////////////////////////////////////////////////////////////////

		//从指令查询过来的参数
		String txtAction = "";
		if( (String)request.getAttribute("txtAction") != null )
			txtAction = (String)request.getAttribute("txtAction");
			
		//配合指令查询过来的参数,如果为"yes",则表示查询后并修改过
		String txtChanged = "";
		if( (String)request.getAttribute("txtChanged") != null )
			txtChanged = (String)request.getAttribute("txtChanged");

	    strTmp = (String)request.getAttribute("control");
        if( strTmp != null && strTmp.length() > 0 )
		{
		     strControl = strTmp.trim();
		}

		strTmp = (String)request.getAttribute("lContractID");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 lContractID = Long.parseLong(strTmp.trim());
		}

		strTmp = (String)request.getAttribute("lCredenceID");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 lCredenceID = Long.parseLong(strTmp.trim());
		}
	
		strTmp = (String)request.getAttribute("backurl");
        if( strTmp != null && strTmp.length() > 0 )
		{
		     strBackURL = strTmp.trim();
		}
		
		strTmp = (String)request.getAttribute("txtContract");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 txtContract = Long.parseLong(strTmp.trim());
		}
			 
		strTmp = (String)request.getAttribute("txtContractCode");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 txtContractCode = strTmp.trim();
		}	 
		
		strTmp = (String)request.getAttribute("txtClient");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 txtClient = Long.parseLong(strTmp.trim());
		}	 

		strTmp = (String)request.getAttribute("txtClientCtrl");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 txtClientCtrl = DataFormat.toChinese(strTmp.trim());
		}
		
//
		DiscountCredenceInfo info = new DiscountCredenceInfo();
       	if (request.getAttribute("info") != null)
       	{
           	info = (DiscountCredenceInfo)request.getAttribute("info");
       	}

		strTmp = (String)request.getAttribute("strBillID");
        if(strTmp != null && strTmp.length() > 0)
        {
			strBillID = strTmp.trim();
			/**
			 * 将一个用","分开的串分解为一个Vector的数组
			 * @param strParam 需要拆分的参数
			 * @return 返回一个Vector，里面是Long型
			 */
			Vector v = new Vector();
			v = DataFormat.changeStringGroup(strBillID);
						
			if( (v != null) && (v.size() > 0) )
			{
				Iterator it = v.iterator();
                while (it.hasNext() )
                {
					Long lTmp = (Long) it.next();
					lBillIDArray[(int)lCount] = lTmp.longValue();
					lCount++;
				}
			}
		}


////////view/////////////////////////////////////////////////////////////////////
		//if (strControl.equals("view"))
		//{
			//if (lCredenceID > 0)
			//{
				//info = Discount.findDiscountCredenceByID (lCredenceID);

				strCredenceCode = info.getCode();
				lContractID = info.getDiscountContractID();
				strContractCode = info.getDiscountContractCode();
				lStatusID = info.getStatusID();

				tsFillDate = info.getFillDate();
				tsInputDate = info.getInputDate();
				lInputUserID = info.getInputUserID();
				strInputUserName = info.getInputUserName();
				lDraftTypeID = info.getDraftTypeID();
				strDraftCode = info.getDraftCode();
				tsPublicDate = info.getPublicDate();
				tsAtTerm = info.getAtTerm();

				strApplyClient = info.getApplyClientName();
				strApplyAccount = info.getApplyAccount();
				strApplyBank = info.getApplyBank();

				strAcceptClient = info.getAcceptClientName();
				strAcceptAccount = info.getAcceptAccount();
				strAcceptBank = info.getAcceptBank();

				dAmount = info.getExamineAmount();
				dRate = info.getDiscountRate();
				dCheckAmount = info.getCheckAmount();

				dBillAmount = info.getBillAmount();
				dBillInterest = info.getBillInterest();
				dBillCheckAmount = info.getBillCheckAmount();

				lGrantTypeID = info.getGrantTypeID();							//放款方式
				lAccountBankID = info.getAccountBankID();						//开户银行
				strAccountBankName = info.getAccountBankName();					//开户银行
				strAccountBankCode = info.getAccountBankCode();					//开户银行账号
				strReceiveClientCode = info.getReceiveClientCode();				//收款单位账号
				strReceiveClientName = info.getReceiveClientName();				//收款单位名称
				strRemitBank = info.getRemitBank();								//汇入行
				strRemitInProvince = info.getRemitInProvince();   				//汇入地（省）
				strRemitInCity = info.getRemitInCity();       					//汇入地（市）
				lGrantCurrentAccountID = info.getGrantCurrentAccountID();		//发放至活期账户
				strGrantCurrentAccountCode = info.getGrantCurrentAccountCode();	//发放至活期账户
				dBillSum=dBillAmount+dBillInterest;
			//}
       	//}

////////save/////////////////////////////////////////////////////////////////////
		/*if (strControl.equals("save"))
		{
			if (strAction.equals("cancel"))
			{
				Discount.cancelDiscountCredenceByID(lCredenceID);

				response.sendRedirect("S120-1.jsp?control=view");
                return;
			}
									
       	}*/

/////////////////////////////////////////////////////////////////////////////////

		OBHtml.showOBHomeHead(out,sessionMng,"贴现凭证",Constant.ShowMenu.YES);

%>

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />
<form name="frm">
<input type="hidden" name="txtAction" value=<%=txtAction%>>
<input type="hidden" name="txtChanged" value=<%=txtChanged%>>

<TABLE border=0 class=top height=60 width=740>
  <TBODY>
  <TR class="tableHeader">
    <TD class=FormTitle height=35><B>贴现凭证——贴现凭证明细</B></TD></TR>
  <TR>
    <TD vAlign=top>
      <TABLE align=center border=0 height=60 width=740>
        <TBODY>
        <TR>
          <TD height=23 width=1>&nbsp;</TD>
		  <TD colSpan=2 height=23>贴现申请编号：<%=DataFormat.formatString(strContractCode)%></TD>
          <TD colSpan=6 height=23 align="center">凭证编号：<%=DataFormat.formatString(strCredenceCode)%></TD></TR>
        <TR>
          <TD height=2 width=1>&nbsp;</TD>
          <TD colSpan=8 height=2>
            <HR>
            <U>贴现基本资料</U></TD>
          <TD height=2 width=1>&nbsp;</TD></TR>
        <TR>
          <TD height=109 width=1>&nbsp;</TD>
          <TD colSpan=7 height=109>
            <TABLE cellPadding=0 cellSpacing=0 width="100%">
              <TBODY>
              <TR>
                <TD height=31 width="23%">单位名称：</TD>
                <TD height=31 width="32%" colspan=6><INPUT class=box size=80 disabled name=tf_dw32 value="<%=DataFormat.formatString(strApplyClient)%>"> </TD>
              </TR>
              <TR>
                <TD height=32 width="23%">批准贴现金额：￥</TD>
                <TD height=32 width="35%"><INPUT class=box size=25 
                  disabled name=textfield2422322 value="<%=ChineseCurrency.showChinese(DataFormat.formatAmount( dAmount))%>"> （大写） 
                  <INPUT class=tar disabled name=textfield2422422 size=25 
                  value="<%=DataFormat.formatDisabledAmount(dAmount)%>"> </TD>
                <TD height=32 width="18%">贴现利率：</TD>
                <TD height=32 width="20%"><INPUT class=tar disabled name=textfield242222222 
                  size=10 value="<%=DataFormat.formatRate(dRate)%>"> %</TD>
                <TD height=32 width="0%">&nbsp;</TD>
                <TD height=32 width="0%">&nbsp;</TD>
                <TD height=32 width="7%">&nbsp;</TD></TR>
              <TR>
                <TD height=32 width="23%">核定贴现金额：￥</TD>
                <TD height=32 width="32%"><INPUT class=tar 
                  disabled name=textfield2422322222 size=25 value="<%=DataFormat.formatDisabledAmount(dCheckAmount)%>"> 
                  </TD>
                <TD height=32 width="18%">&nbsp;</TD>
                <TD height=32 width="20%"></TD>
                <TD height=32 width="0%">&nbsp;</TD>
                <TD height=32 width="0%">&nbsp;</TD>
                <TD height=32 width="7%">&nbsp;</TD></TR></TBODY></TABLE></TD>
          <TD height=109 width=1>&nbsp;</TD>
          <TD height=109 width=1>&nbsp;</TD></TR>
        <TR>
          <TD height=24 width=1>&nbsp;</TD>
          <TD colSpan=8 height=24>
            <HR>
          </TD>
          <TD align=right height=24 width=1>&nbsp;</TD></TR>
		<% if (false) { %>
        <TR>
          <TD height=24 width=1>&nbsp;</TD>
          <TD colSpan=8 height=24><U>贴现票据明细表</U></TD>
          <TD align=right height=24 width=1>&nbsp;</TD></TR>
        <TR>
          <TD height=24 width=1>&nbsp;</TD>
          <TD colSpan=8 height=24><INPUT class=button name=Submit42242 onclick="billinfo(frm)" type=button value="贴现票据明细表"> 
            </TD>
          <TD align=right height=24 width=1>&nbsp;</TD></TR>
        <TR>
          <TD height=24 width=1>&nbsp;</TD>
          <TD colSpan=8 height=24>
            <HR>
          </TD>
          <TD align=right height=24 width=1>&nbsp;</TD></TR>
		<% } %>
        <TR>
          <TD height=30 width=1>&nbsp;</TD>
          <TD colSpan=8 height=30>
            <DIV align=center><B><FONT size=4>贴现凭证</FONT></B></DIV></TD>
          <TD align=right height=30 width=1>&nbsp;</TD></TR>
        <TR>
          <TD height=13 width=1>&nbsp;</TD>
          <TD colSpan=8 height=13>
            <TABLE border=1 borderColor=#999999 cellPadding=0 cellSpacing=0 
            height=341 width="100%">
              <TBODY>
              <TR>
                <TD>
                  <TABLE border=0 height=136 width="98%">
                    <TBODY>
                    <TR>
                      <TD colSpan=3 height=32>填写日期</TD>
                      <TD colSpan=3 height=32>
                        <DIV align=left><INPUT class=box <%out.println(strDisabled);%>
                        name="tsFillDate" size=25 onfocus="nextfield='strApplyAccount';" value="<%if (tsFillDate!=null) out.print(DataFormat.getDateString(tsFillDate));%>"> <%if (lCredenceID < 1) {%><A href="javascript:show_calendar('frm.tsFillDate');" onmouseout="window.status='';return true;" onmouseover="window.status='Date Picker';return true;">
						<IMG border=0 height=16 src="/webob/graphics/calendar.gif" width=17></A><%}%></DIV></TD>
                      <TD height=32 width=181>&nbsp;</TD></TR>
                    <TR>
                      <TD colSpan=3 height=32>贴现汇票种类：</TD>
                      <TD height=32 width=224>
					  <SELECT class='box' name="lAcceptPOTypeID">
						<OPTION value="-1" 
						<%if (lDraftTypeID==-1) {out.println(" selected");}%>>详见明细表</OPTION>
						<OPTION value=<%=OBConstant.DraftType.BANK%> 
						<%if (lDraftTypeID==OBConstant.DraftType.BANK) {out.println(" selected");}%>><%=OBConstant.DraftType.getName(OBConstant.DraftType.BANK)%></OPTION>
						<OPTION value=<%=OBConstant.DraftType.BIZ%>
						<%if (lDraftTypeID==OBConstant.DraftType.BIZ) {out.println(" selected");}%>><%=OBConstant.DraftType.getName(OBConstant.DraftType.BIZ)%></OPTION>
					  </SELECT>
					  </TD>
                      <TD colSpan=2 height=32>贴现汇票号码：</TD>
                      <TD height=32 width=181><INPUT class=box 
                        name="strDraftCode" size=25 disabled value="<%=DataFormat.formatString(strDraftCode)%>"> </TD></TR>
                    <TR>
                      <TD colSpan=3 height=27>发票日：</TD>
                      <TD height=27 width=224><INPUT class=box 
                        name="tsPublicDate" size=25 disabled value="<%if (tsPublicDate!=null) out.println(DataFormat.getDateString(tsPublicDate)); else out.println("详见明细表");%>"></TD>
                      <TD colSpan=2 height=27>到期日：</TD>
                      <TD height=27 width=181><INPUT class=box 
                        name="tsAtTerm" size=25 disabled value="<%if (tsAtTerm!=null) out.print(DataFormat.getDateString(tsAtTerm)); else out.println("详见明细表");%>"></TD></TR>
                    <TR>
                      <TD colSpan=7 height=24>
                        <HR>
                      </TD></TR>
                    <TR>
                      <TD colSpan=3 height=24>申请单位名称：</TD>
                      <TD colSpan=4 height=24 width=224><INPUT class=box 
                        name=textfield242243422 size=80 disabled value="<%=DataFormat.formatString(strApplyClient)%>"> </TD>
                    </TR>
					<TR>
                      <TD colSpan=3 height=24>申请单位账号：</TD>
                      <TD colSpan=4 height=24 width=181><INPUT class=box <%out.println(strDisabled);%>
                        name="strApplyAccount" size=25 onfocus="nextfield='strApplyBank';" value="<%=DataFormat.formatString(strApplyAccount)%>"> </TD></TR>
                    <TR>
                      <TD colSpan=3 height=24>申请单位开户银行：</TD>
                      <TD height=24 width=224><INPUT class=box <%out.println(strDisabled);%>
                        name="strApplyBank" size=25 onfocus="nextfield='strAcceptClient';" value="<%=DataFormat.formatString(strApplyBank)%>"> </TD>
                      <TD colSpan=2 height=24></TD>
                      <TD height=24 width=181>&nbsp;</TD></TR>
                    <TR>
                      <TD colSpan=7 height=15>
                        <HR>
                      </TD></TR>
                    <TR>
                      <TD colSpan=3 height=24>承兑单位名称：</TD>
                      <TD colSpan=4 height=24 width=224><INPUT class=box <%out.println(strDisabled);%>
                        name="strAcceptClient" size="80" onfocus="nextfield='strAcceptAccount';" value="<%=DataFormat.formatString(strAcceptClient)%>"></TD>
                    </TR>
					<TR>                      
                      <TD colSpan=3 height=24>承兑单位账号：</TD>
                      <TD colSpan=4 height=24 width=181><INPUT class=box <%out.println(strDisabled);%>
                        name="strAcceptAccount" size=25 onfocus="nextfield='strAcceptBank';" value="<%=DataFormat.formatString(strAcceptAccount)%>"> </TD></TR>
                    <TR>
                      <TD colSpan=3 height=24>承兑单位开户银行：</TD>
                      <TD height=24 width=224><INPUT class=box <%out.println(strDisabled);%>
                        name="strAcceptBank" size=25 onfocus="nextfield='submitfunction';" value="<%=DataFormat.formatString(strAcceptBank)%>"> </TD>
                      <TD colSpan=2 height=24></TD>
                      <TD height=24 width=181></TD></TR>
                    <TR>
                      <TD colSpan=7 height=24>
                        <HR>
                      </TD></TR>
					<!-- ========================放款方式开始======================== -->
                    <TR> 
                      <TD height=24 colspan=7>放款方式：</TD>
                    </TR>
                    <TR> 
                      <TD height=36 colspan="7"> 
                      <TABLE border=1 borderColor=#999999 cellPadding=0 cellSpacing=0 width="100%">
                        <tr><td>
                          <table align=left border=0 bordercolor=#999999 width="100%">
                            <tr bordercolor=#D7DFE5> 
                              <td colspan=4 height=20 valign=middle> 
                                <%if( lGrantTypeID == LOANConstant.TransType.Bank )
                                {%>
                                <input type="checkbox" name="bankpay" value="checkbox" checked  onfocus="nextfield='txtBA';" disabled>
                                <%}else{%>
                                <input type="checkbox" name="bankpay" value="checkbox"  onfocus="nextfield='txtBA';" disabled>
                                <%}%>
                                银行付款</td>
                            </tr>
                            <tr bordercolor=#D7DFE5 valign=middle>							
							  <td height=20 width="20%">开户银行：</td>
                              <td height=20 width="30%">
							  <input class=box name="BankCode" size=20 maxlength="100" disabled value="<%=DataFormat.formatString(strAccountBankName)%>">
							  </td>
                              <td height=20 width="20%">银行账号：</td>
                              <td height=20 width="30%"> 
                                <p> 
                                  <input class=box name="BankCode" size=20 maxlength="100" disabled value="<%=DataFormat.formatString(strAccountBankCode)%>">
                                </p>
                              </td>
                            </tr>
                            <tr bordercolor=#D7DFE5 valign=middle> 
                              <td height=20 width="20%">收款方资料：</td>
                              <td height=20 width="30%">&nbsp;</td>
                              <td height=20 width="20%">&nbsp;</td>
                              <td height=20 width="30%">&nbsp;</td>
                            </tr>
                            <tr bordercolor=#D7DFE5 valign=middle>
							  <td height=20 width="20%">收款方账号：</td>
                              <td height=20 width="30%">
							  <input class=box name="BankCode" size=20 maxlength="100" disabled value="<%=DataFormat.formatString(strReceiveClientCode)%>">
							  </td>
                              <td height=20 width="20%">收款方名称：</td>
                              <td height=20 width="30%"> 
                                <p> 
                                  <input class=box name="sreceiveclientname" size=20 maxlength="100" value="<%=DataFormat.formatString(strReceiveClientName)%>" disabled>
                                </p>
                              </td>
                            </tr>
                            <tr bordercolor=#D7DFE5 valign=middle> 
                              <td height=20 width="20%">汇入地（省）：</td>
                              <td height=20 width="30%">
                                <input class=box name="sremitinprovince" size=20 maxlength="100" value="<%=DataFormat.formatString(strRemitInProvince)%>" onfocus="nextfield ='sremitincity';" disabled>
                              </td>
                              <td height=20 width="20%">汇入地（市）：</td>
                              <td height=20 width="30%">
                                <input class=box name="sremitincity" size=20 maxlength="100" value="<%=DataFormat.formatString(strRemitInCity)%>" onfocus="nextfield ='sremitbank';" disabled>
                              </td>
                            </tr>
                            <tr bordercolor=#D7DFE5 valign=middle> 
                              <td height=20 width="20%">汇入行名称：</td>
                              <td height=20 width="30%">
                                <input class=box name="sremitbank" size=20 maxlength="100" value="<%=DataFormat.formatString(strRemitBank)%>"  onfocus="nextfield='tjtxshq';" disabled>
                              </td>
                              <td height=20 width="20%">&nbsp;</td>
                              <td height=20 width="30%">&nbsp;</td>
                            </tr>
                          </table>
                          </td></tr></table>
                        </TD>
                      </TR>
                      <TR> 
                        <TD height=36 colspan="7"> 
                        <TABLE border=1 borderColor=#999999 cellPadding=0 cellSpacing=0 width="100%">
                        <tr><td>
                          <table align=left border=0 bordercolor=#999999 width="100%">
							<tr bordercolor=#D7DFE5> 
                              <td colspan=4 height=20 valign=middle> 
                              <%if( lGrantTypeID == LOANConstant.TransType.CurrentAccount )
                                {%>
                                <input type="checkbox" name="currentaccount" value="checkbox" checked onfocus="nextfield='txtAccountCodeCtrl1';" disabled>
                                <%}else{%>
                                <input type="checkbox" name="currentaccount" value="checkbox" onfocus="nextfield='txtAccountCodeCtrl1';" disabled>
                                <%}%>
                                活期账户</td>
                            </tr>
                            <tr bordercolor=#D7DFE5 valign=middle>
							  <td height=20 width="20%">账号：</td>
                              <td height=20 width="30%">
                                <input class=box name="sremitbank" size=20 maxlength="100" value="<%=DataFormat.formatString(strGrantCurrentAccountCode)%>"  onfocus="nextfield='tjtxshq';" disabled>
                              </td>
                              <td height=20 width="20%">&nbsp;</td>
                              <td height=20 width="30%">&nbsp;</td>
                            </tr>
                          </table>
                          </td></tr></table>
                        </TD>
                      </TR>
					<!-- ========================放款方式结束======================== -->
                    <TR>
                      <TD height=24 width=80>汇票金额：￥ </TD>
                      <TD colSpan=2 height=24>
                        <DIV align=right>(大写)</DIV><BR></TD>
                      <TD height=24 width=224><INPUT class=box
                        name=textfield24225 size=25 disabled value="<%=ChineseCurrency.showChinese( DataFormat.formatAmount(dBillAmount))%>"><BR><INPUT class=tar 
                        name=textfield2422224 size=25 disabled value="<%=DataFormat.formatDisabledAmount(dBillAmount)%>"> </TD>
                      <TD height=24 width=109>贴现率每年：</TD>
                      <TD height=24>&nbsp;</TD>
                      <TD height=24 width=181><INPUT class=tar 
                        name=textfield242222322 size=10 disabled value="<%=DataFormat.formatRate(dRate)%>"> % </TD></TR>
                    <TR>
                      <TD height=24 width=80>贴现利息：￥  </TD>
                      <TD colSpan=2 height=24>&nbsp;</TD>
                      <TD height=24 width=224><INPUT class=tar 
                        name=textfield242252 size=25 disabled value="<%= DataFormat.formatDisabledAmount(dBillInterest)%>"> </TD>
                      <TD height=24 width=109>实付贴现金额：￥ </TD>
                      <TD height=24 width=66>&nbsp;</TD>
                      <TD height=24 width=181><INPUT class=tar 
                        name=textfield24222242 size=25 disabled value="<%= DataFormat.formatDisabledAmount(dBillAmount-dBillInterest)%>"> 
                  </TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
          </TD>
          <TD align=right height=13 width=1>&nbsp;</TD></TR>
        <TR>
          <TD height=50 width=1>&nbsp;</TD>
          <TD align=right colSpan=8 height=50>            
            <DIV align=right>
				<INPUT class=button name="xztxshq" onclick="confirmAdd(frm)" type=button value="新增贴现凭证">
				<!--INPUT class=button name="xgtxshq" onclick="confirmUpdate(frm)" type=button value="修改贴现凭证"-->
<%if(lStatusID != OBConstant.LoanInstrStatus.ACCEPT && lStatusID != OBConstant.LoanInstrStatus.REFUSE && info.getStatusID() != OBConstant.LoanInstrStatus.CANCEL && info.getStatusID() != OBConstant.LoanInstrStatus.APPROVE)
{
	if ((lStatusID == OBConstant.LoanInstrStatus.SUBMIT)||(lStatusID == OBConstant.LoanInstrStatus.SAVE))
	{
		if (lInputUserID == sessionMng.m_lUserID)
		{
%>
			<INPUT class=button name="submitCancel" onclick="confirmCancel(frm);" type=button value="取消贴现凭证">
			<INPUT class=button name="xgtxshq" onclick="submitfrm(frm)" type=button value="提交贴现凭证">	
<%
		}
	}
}%>
<%if(lStatusID != OBConstant.LoanInstrStatus.REFUSE && info.getStatusID() != OBConstant.LoanInstrStatus.CANCEL&&lInputUserID == sessionMng.m_lUserID)
{%>
				<INPUT class=button name=Submit42232 onclick="printIt('../discountapply/d038-c.jsp?control=view&lCredenceID=<%=lCredenceID%>&lContractID=<%=lContractID%>&isSM=<%=Constant.YesOrNo.NO%>');" type=button value=" 打 印 ">
<%}%>
				<INPUT class=button name=Submit42232 onclick="backto()" type=button value=" 返 回 ">
            </DIV>
		  </TD>
		</TR>
		<TR>
		  <TD colSpan=9><HR></TD>
		</TR>
		<TR>
          <TD height=2 width=1>&nbsp;</TD>
          <TD align=left height=2 width=122>录入人：<%=DataFormat.formatString(strInputUserName)%></TD>
          <TD align=center height=2 width=410>录入时间：<%=DataFormat.getDateString(tsInputDate)%></TD>
          <TD align=right height=2 width=252>状态：<%= OBConstant.LoanInstrStatus.getName(lStatusID)%>
		  </TD>
          <TD align=right colSpan=2 height=2>&nbsp;</TD>
          <TD align=right colSpan=2 height=2>&nbsp;</TD>
          <TD align=right height=2 width=0>&nbsp;</TD>
          <TD align=right colSpan=2 height=2 width=9>
            <DIV align=center>&nbsp;</DIV>
		  </TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
<P><BR></P>
<%System.out.println("inside credenceid ==="+lCredenceID);%>
<input type="hidden" name="control" value="view">
<input type="hidden" name="backurl" value="<%=strBackURL%>">
<input type="hidden" name="lContractID" value="<%=lContractID%>">
<input type="hidden" name="lCredenceID" value="<%=lCredenceID%>">
<input type="hidden" name="strDraftCode" value="<%=strDraftCode%>">
<input type="hidden" name="tsPublicDate" value="<%=tsPublicDate%>">
<input type="hidden" name="tsAtTerm" value="<%=tsAtTerm%>">
<input type="hidden" name="strApplyClient" value="<%=strApplyClient%>">
<input type="hidden" name="dBillAmount" value="<%=dBillAmount%>">
<input type="hidden" name="dRate" value="<%=dRate%>">
<input type="hidden" name="dBillInterest" value="<%=dBillInterest%>">
<!--转到修改页037-v.jsp-->
<input type="hidden" name="nextPage" value="d037-v.jsp">
<input type="hidden" name="job" value="">
<input type="hidden" name="lStatusID" value="">	
</form>
	
<script language="JavaScript">

function printIt(url)
{
	window.open(url,"popup","width=800,height=600,scrollbars=1,resizable=1,menubar=0,toolbar=0,locations=0,directories=0, screenX=0,screenY=0,left=10,top=10");

}

function backto()
{
	 //eval("self.location='d031-c.jsp?control=view&lContractID=<%=lContractID%>&strBillID=<%=strBillID%>'");
	 <%if(txtAction.equals("modify"))
	 {%>
	 	eval("self.location='../query/q003-c.jsp'");
	 <%}else
	 {%>
	 	eval("self.location='../discountapply/d030-v.jsp?control=view&lContractID=<%=lContractID%>&strBillID=<%=strBillID%>'");
	<%}%>
}
function confirmSave(frm)
{  		
	if (!InputValid(frm.strDraftTypeName, 0, "string", 0, 0, 0,"贴现汇票种类")) 
	{
		return false;
	}
	if (!InputValid(frm.strApplyAccount, 0, "string", 0, 0, 0,"申请单位账号")) 
	{
		return false;
	}
	if (!InputValid(frm.strAcceptBank, 0, "string", 0, 0, 0,"申请单位开户银行")) 
	{
		return false;
	}
	if (!InputValid(frm.strAcceptClient, 0, "string", 0, 0, 0,"承兑单位名称")) 
	{
		return false;
	}
	if (!InputValid(frm.strAcceptAccount, 0, "string", 0, 0, 0,"承兑单位账号")) 
	{
		return false;
	}
	if (!InputValid(frm.strApplyBank, 0, "string", 0, 0, 0,"承兑单位开户银行")) 
	{
		return false;
	}

	if(confirm("是否提交贴现凭证？"))
	{
		frm.control.value="save";
		frm.job.value="submit";
		frm.action="d.jsp";
		showSending();
		frm.submit();
		return true;
	}	
}

function confirmCancel(frm)
{
	if(confirm("是否取消贴现凭证？"))
	{
		frm.control.value="save";
		frm.job.value="cancel";
		frm.lStatusID.value=<%=OBConstant.LoanInstrStatus.CANCEL%>;
		frm.action="../discountapply/d040-c.jsp";
		showSending();
		frm.submit();
		return true;
	}
}


function confirmAdd(frm)
{
	if(confirm("是否新增贴现凭证？"))
	{
		frm.action="../discountapply/d030-v.jsp";
		frm.submit();
		return true;
		//MM_goToURL ('self','d030-v.jsp?control=view');
		//return true;
	}
}

function submitfrm(frm)
{
	if(confirm("是否提交贴现凭证？"))
	{
		frm.action="../discountapply/d040-c.jsp";
		frm.lStatusID.value=<%=OBConstant.LoanInstrStatus.SUBMIT%>;
		showSending();
		frm.submit();
		return true;
	}
}
function confirmUpdate(frm)
{
	//frm.control.value="save";
	frm.action="../discountapply/d033-c.jsp";
	showSending();
	frm.submit();
	return true;
}
function billinfo(frm)
{
	alert('here');
}


//firstFocus(frm.strDraftTypeName);
////setSubmitFunction("confirmSave(frm)");
setFormName("frm");	 

</script>			
<%
	OBHtml.showOBHomeEnd(out);
%>
<%	}
	catch(IException ie)
    {
		//OBHtml.showExceptionMessage(out,sessionMng,ie,request,response,"贴现凭证", Constant.RecordStatus.VALID);
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"贴现凭证",1);
		ie.printStackTrace();
		out.flush();
		return; 
    }
%>
<%@ include file="/common/SignValidate.inc" %>