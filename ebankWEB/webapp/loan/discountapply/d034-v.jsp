<%@ page contentType="text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import=" java.util.*,java.rmi.*,java.net.URLEncoder"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"/>
<jsp:useBean id="Magnifier" scope="request" class="com.iss.itreasury.ebank.util.OBMagnifier"/>
<%@ page import="com.iss.itreasury.util.*,
                com.iss.itreasury.ebank.util.*,
    			com.iss.itreasury.ebank.obdiscountapply.bizlogic.*,
                com.iss.itreasury.ebank.obdiscountapply.dataentity.*,
				com.iss.itreasury.loan.util.*,
				com.iss.itreasury.settlement.util.*,
    			java.sql.*,
    			java.net.URLEncoder,
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
		String strBackURL = "S125";
		String strDisabled = " disabled";

		long lBillID = -1;
		long[] lBillIDArray = new long[1000];
		String strBillID = "";
		Vector v = null;
			
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
		long lInputUserID = -1;
		String strInputUserName = "";
		long lStatusID = -1;
		long lCount = 0;

		
///////control///////////////////////////////////////////////////////////////////
	    strTmp = (String)request.getAttribute("control");
        if( strTmp != null && strTmp.length() > 0 )
		{
		     strControl = strTmp.trim();
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

		strTmp = (String)request.getAttribute("strBillID");
System.out.println("strBillID===================================================================================="+strTmp);
        if(strTmp != null && strTmp.length() > 0)
        {
			strBillID = strTmp.trim();
			/**
			 * 将一个用","分开的串分解为一个Vector的数组
			 * @param strParam 需要拆分的参数
			 * @return 返回一个Vector，里面是Long型
			 */
			 
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
		

		//我的代码
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
//
		DiscountCredenceInfo info = new DiscountCredenceInfo();
       	if (request.getAttribute("info") != null)
       	{
           	info = (DiscountCredenceInfo)request.getAttribute("info");
       	}
		
		//cinfo = Contract.findByID(lContractID);
		strContractCode = DataFormat.formatString(info.getDiscountContractCode());
		dAmount = info.getExamineAmount();
		dRate = info.getDiscountRate();
		dCheckAmount = info.getCheckAmount();
		
		//clientinfo = loanCommonSetting.findClientByID(cinfo.getBorrowClientID());
		strApplyClient = DataFormat.formatString(info.getApplyClientName());
		strApplyAccount = DataFormat.formatString(info.getApplyAccount());
		strApplyBank = DataFormat.formatString(info.getApplyBank());

		//dbi = Discount.findBillInterestByBillID(lContractID,strBillID);				
		dBillAmount = info.getBillAmount();       //凭证总金额
		dBillInterest = info.getBillInterest();   //凭证利息
		dBillCheckAmount = info.getBillCheckAmount();  //凭证和定金额
		tsFillDate = Env.getSystemDate();

		//lDraftTypeID = dbi.getAcceptPOTypeID();
		lDraftTypeID = info.getDraftTypeID();
		strDraftTypeName = DataFormat.formatString(LOANConstant.DraftType.getName(info.getDraftTypeID())); //汇票种类
		strDraftCode = DataFormat.formatString(info.getDraftCode());    //汇票号码
		tsPublicDate = info.getPublicDate();
		tsAtTerm = info.getAtTerm();
		strAcceptClient =  DataFormat.formatString(info.getAcceptClientName());
		strAcceptAccount =  "";
		strAcceptBank = DataFormat.formatString(info.getAcceptBank());
		dBillSum=dBillAmount+dBillInterest;

		lGrantTypeID = -1;				//放款方式
		lAccountBankID = -1;			//开户银行
		strAccountBankName = "";		//开户银行
		strAccountBankCode = "";		//开户银行账号
		strReceiveClientCode = "";		//收款单位账号
		strReceiveClientName = "";		//收款单位名称
		strRemitBank = "";				//汇入行
		strRemitInProvince = "";   		//汇入地（省）
		strRemitInCity = "";       		//汇入地（市）
		lGrantCurrentAccountID = -1;	//发放至活期账户
		strGrantCurrentAccountCode = "";//发放至活期账户

/////////////////////////////////////////////////////////////////////////////////

		OBHtml.showOBHomeHead(out,sessionMng,"贴现凭证",Constant.ShowMenu.YES);

%>

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="javascript" src="/itreasury/js/jquery-1.3.2.js"></script>

<safety:resources />
<form name="frm" method="post">

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
          <TD colSpan=2 height=23>贴现合同编号：<%=DataFormat.formatString(strContractCode)%></TD>
          <TD colSpan=6 height=23>
            <DIV align=left></DIV></TD></TR>
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
          <TD colSpan=8 height=24><INPUT class=button name=Submit42242 onclick="MM_goToURL('self','S116.jsp?lCredenceID=<%=lCredenceID%>&lContractID=<%=lContractID%>&sDiscountCode=<%=strContractCode%>&control=view&backurl=S119');" type=button value="贴现票据明细表"> 
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
                        <DIV align=left>
                     
                         <%if (lCredenceID < 1) {%>
                         	<fs_c:calendar 
						          	    name="tsFillDate"
							          	value=""
							          	properties="nextfield ='strApplyAccount'"
							          	size="25"/>
							   <script>
	          		$('#tsFillDate').val('<%if (tsFillDate!=null) out.print(DataFormat.getDateString(tsFillDate));%>');
	          	</script>
							          	<!--  
                         <A href="javascript:show_calendar('frm.tsFillDate');" onmouseout="window.status='';return true;" onmouseover="window.status='Date Picker';return true;">
						<IMG border=0 height=16 src="/webob/graphics/calendar.gif" width=17></A>
						-->
						<%}else{%>
                        	<INPUT class=box name="tsFillDate" size=25 onfocus="nextfield='strApplyAccount';" value="<%if (tsFillDate!=null) out.print(DataFormat.getDateString(tsFillDate));%>">
						<%} %>
						
						</DIV>
						</TD>
                      <TD height=32 width=181>&nbsp;</TD></TR>
                    <TR>
                      <TD colSpan=3 height=32>贴现汇票种类：</TD>
                      <TD height=32 width=224>
					  <SELECT class='box' name="lAcceptPOTypeID">
						<OPTION value="<%=strDraftTypeName%>" 
						<%if (lDraftTypeID==-1) {out.println(" selected");}%>>详见明细表</OPTION>
						<OPTION value="<%=OBConstant.DraftType.BANK%>"
						<%if (lDraftTypeID==OBConstant.DraftType.BANK) {out.println(" selected");}%>><%=OBConstant.DraftType.getName(OBConstant.DraftType.BANK)%></OPTION>
						<OPTION value="<%=OBConstant.DraftType.BIZ%>"
						<%if (lDraftTypeID==OBConstant.DraftType.BIZ) {out.println(" selected");}%>><%=OBConstant.DraftType.getName(OBConstant.DraftType.BIZ)%></OPTION>
					  </SELECT>
					  </TD>
                      <TD colSpan=2 height=32>贴现汇票号码：</TD>
                      <TD height=32 width=181><INPUT class=box 
                        name="strDraftCode" size=25 disabled value="<%if (strDraftCode!=null||!strDraftCode.equals("")) out.println(strDraftCode); else out.println("详见明细表");%>"> </TD></TR>
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
                      <TD colSpan=4 height=24 width=181><INPUT class=box 
                        name="strApplyAccount" size=25 onfocus="nextfield='strApplyBank';" value="<%=DataFormat.formatString(strApplyAccount)%>"> </TD></TR>
                    <TR>
                      <TD colSpan=3 height=24>申请单位开户银行：</TD>
                      <TD height=24 width=224><INPUT class=box 
                        name="strApplyBank" size=25 onfocus="nextfield='strAcceptClient';" value="<%=DataFormat.formatString(strApplyBank)%>"> </TD>
                      <TD colSpan=2 height=24></TD>
                      <TD height=24 width=181>&nbsp;</TD></TR>
                    <TR>
                      <TD colSpan=7 height=15>
                        <HR>
                      </TD></TR>
                    <TR>
                      <TD colSpan=3 height=24>承兑单位名称：</TD>
                      <TD colSpan=4 height=24 width=224><INPUT class=box 
                        name="strAcceptClient" size="80" onfocus="nextfield='strAcceptAccount';" value="<%=DataFormat.formatString(strAcceptClient)%>"></TD>
                    </TR>
					<TR>                      
                      <TD colSpan=3 height=24>承兑单位账号：</TD>
                      <TD colSpan=4 height=24 width=181><INPUT class=box 
                        name="strAcceptAccount" size=25 onfocus="nextfield='strAcceptBank';" value="<%=DataFormat.formatString(strAcceptAccount)%>"> </TD></TR>
                    <TR>
                      <TD colSpan=3 height=24>承兑单位开户银行：</TD>
                      <TD height=24 width=224><INPUT class=box 
                        name="strAcceptBank" size=25 onfocus="nextfield='bankpay';" value="<%=DataFormat.formatString(strAcceptBank)%>"> </TD>
                      <TD colSpan=2 height=24></TD>
                      <TD height=24 width=181></TD></TR>
                    <TR>
                      <TD colSpan=7 height=24>
                        <HR>
                      </TD></TR>
					<!-- ========================放款方式开始======================== -->
                    <TR> 
                      <TD height=24 colspan=7><font color=#FF0000>* </font>放款方式：</TD>
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
                                <input type="checkbox" name="bankpay" value="checkbox" checked  onfocus="nextfield='txtBA';">
                                <%}else{%>
                                <input type="checkbox" name="bankpay" value="checkbox"  onfocus="nextfield='txtBA';">
                                <%}%>
                                银行付款</td>
                            </tr>
                            <tr bordercolor=#D7DFE5 valign=middle> 
<%                            
                            String sMagnifierNameBank = URLEncoder.encode("开户银行");
                            String sFormNameBank = "frm";
                            String sPrefixBank = "";
                            String[] sMainNamesBank = {"txtBA","BankCode"};
                            String[] sMainFieldsBank = {"BranchName","BranchNo"};
                            String[] sReturnNamesBank = {"naccountbankid"};
                            String[] sReturnFieldsBank = {"BranchID"};
                            String sReturnInitValuesBank = strAccountBankName;
                            String[] sReturnValuesBank = {""+lAccountBankID+""};
                            String[] sDisplayNamesBank = {URLEncoder.encode("编号"),URLEncoder.encode("名称")};
                            String[] sDisplayFieldsBank = {"BranchNo","BranchName"};
                            int nIndexBank = 0;
                            String sMainPropertyBank = "";
                            String strSQLBank = "getBranchSQL("+sessionMng.m_lOfficeID+","+sessionMng.m_lCurrencyID+",-1,-1,frm.txtBA.value)";
                            String strMatchValueBank = "BranchName";
                            String strNextControlsBank = "txtAccount";
                            String strTitleBank = "<font color=#FF0000>* </font>开户银行"; 
                            String strFirstTDBank = "";
                            String strSecondTDBank = "";
                            
                            Magnifier.showZoomCtrl(out
                            ,sMagnifierNameBank
                            ,sFormNameBank
                            ,sPrefixBank
                            ,sMainNamesBank
                            ,sMainFieldsBank
                            ,sReturnNamesBank
                            ,sReturnFieldsBank
                            ,sReturnInitValuesBank
                            ,sReturnValuesBank
                            ,sDisplayNamesBank
                            ,sDisplayFieldsBank
                            ,nIndexBank
                            ,sMainPropertyBank
                            ,strSQLBank
                            ,strMatchValueBank
                            ,strNextControlsBank
                            ,strTitleBank
                            ,strFirstTDBank
                            ,strSecondTDBank);
%>
                              
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
<%                          
                            String sMagnifierNameAccount = URLEncoder.encode("收款方账号");
                            String sFormNameAccount = "frm";
                            String sPrefixAccount = "";
                            String[] sMainNamesAccount = {"txtAccount","sreceiveclientname","sremitinprovince","sremitincity","sremitbank"};
                            String[] sMainFieldsAccount = {"ExtAcctNo","ExtAcctName","ExtProvince","ExtCity","ExtBankName"};
                            String[] sReturnNamesAccount = {"sreceiveaccount"};
                            String[] sReturnFieldsAccount = {"ExtAcctNo"};
                            String sReturnInitValuesAccount = (strReceiveClientCode==null?"":""+strReceiveClientCode);
                            String[] sReturnValuesAccount = {""};
                            String[] sDisplayNamesAccount = {URLEncoder.encode("账号"),URLEncoder.encode("名称")};
                            String[] sDisplayFieldsAccount = {"ExtAcctNo","ExtAcctName"};
                            int nIndexAccount1 = 0;
                            String sMainPropertyAccount = "";
                            String sSQLAccount = "getExtAcctSQL("+sessionMng.m_lOfficeID+",frm.txtAccount.value,"+sessionMng.m_lClientID+")";
                            String sMatchValueAccount = "ExtAcctNo";
                            String sNextControlsAccount = "sremitinprovince";
                            String sTitleAccount = "<font color=#FF0000>* </font>收款方账号";
                            String sFirstTDAccount = "";
                            String sSecondTDAccount = ""; 
                            
                            Magnifier.showZoomCtrl(out
                            ,sMagnifierNameAccount
                            ,sFormNameAccount
                            ,sPrefixAccount
                            ,sMainNamesAccount
                            ,sMainFieldsAccount
                            ,sReturnNamesAccount
                            ,sReturnFieldsAccount
                            ,sReturnInitValuesAccount
                            ,sReturnValuesAccount
                            ,sDisplayNamesAccount
                            ,sDisplayFieldsAccount
                            ,nIndexAccount1
                            ,sMainPropertyAccount
                            ,sSQLAccount
                            ,sMatchValueAccount
                            ,sNextControlsAccount
                            ,sTitleAccount
                            ,sFirstTDAccount
                            ,sSecondTDAccount);
%>                              
                              <td height=20 width="20%">收款方名称：</td>
                              <td height=20 width="30%"> 
                                <p> 
                                  <input class=box name="sreceiveclientname" size=20 maxlength="100" value="<%=DataFormat.formatString(strReceiveClientName)%>">
                                </p>
                              </td>
                            </tr>
                            <tr bordercolor=#D7DFE5 valign=middle> 
                              <td height=20 width="20%"><font color=#FF0000>* </font>汇入地（省）：</td>
                              <td height=20 width="30%">
                                <input class=box name="sremitinprovince" size=20 maxlength="100" value="<%=DataFormat.formatString(strRemitInProvince)%>" onfocus="nextfield ='sremitincity';" >
                              </td>
                              <td height=20 width="20%"><font color=#FF0000>* </font>汇入地（市）：</td>
                              <td height=20 width="30%">
                                <input class=box name="sremitincity" size=20 maxlength="100" value="<%=DataFormat.formatString(strRemitInCity)%>" onfocus="nextfield ='sremitbank';" >
                              </td>
                            </tr>
                            <tr bordercolor=#D7DFE5 valign=middle> 
                              <td height=20 width="20%"><font color=#FF0000>* </font>汇入行名称：</td>
                              <td height=20 width="30%">
                                <input class=box name="sremitbank" size=20 maxlength="100" value="<%=DataFormat.formatString(strRemitBank)%>"  onfocus="nextfield='tjtxshq';">
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
                                <input type="checkbox" name="currentaccount" value="checkbox" checked onfocus="nextfield='txtAccountCodeCtrl1';">
                                <%}else{%>
                                <input type="checkbox" name="currentaccount" value="checkbox" onfocus="nextfield='txtAccountCodeCtrl1';">
                                <%}%>
                                活期账户</td>
                            </tr>
                            
                            <tr bordercolor=#D7DFE5 valign=middle> 
<%
                                String strMagnifierNameAccount = URLEncoder.encode("账号");
                                String strFormNameAccount = "frm";
                                String strPrefixAccount = "";
                                int nCaseNumberAccount = 4;
                                String[] strMainNamesAccount = {"txtAccountCode"};
                                String[] strMainFieldsAccount = {"sAccountNo"};
                                String[] strReturnNamesAccount = {"ngrantcurrentaccountid"};
                                String[] strReturnFieldsAccount = {"id"};
                                String[] strReturnValuesAccount = {""+lGrantCurrentAccountID+""};
                                String[] strDisplayNamesAccount = {URLEncoder.encode("账号"),URLEncoder.encode("客户名称")};
                                String[] strDisplayFieldsAccount = {"sAccountNo","sName"};
                                int nIndexAccount = 0;
                                String strMainPropertyAccount = "";
                                String strSQLAccount = "getAccountSQL("+sessionMng.m_lOfficeID+","+sessionMng.m_lCurrencyID+","+sessionMng.m_lClientID+",-1,"+SETTConstant.AccountGroupType.CURRENT+")";
                                String strNextControlsAccount = "tjtxshq";
                                String strAccountNoAccount = strGrantCurrentAccountCode;
                                String strTitleAccount = "<font color=#FF0000>* </font>账号";
                                String strFirstTDAccount = "";
                                String strSecondTDAccount = "";

                                Magnifier.showSpecialZoomCtrl(out
                                ,sessionMng.m_lOfficeID
                                ,sessionMng.m_lCurrencyID
                                ,strMagnifierNameAccount
                                ,strFormNameAccount
                                ,strPrefixAccount
                                ,nCaseNumberAccount
                                ,strMainNamesAccount
                                ,strMainFieldsAccount
                                ,strReturnNamesAccount
                                ,strReturnFieldsAccount
                                ,strReturnValuesAccount
                                ,strDisplayNamesAccount
                                ,strDisplayFieldsAccount
                                ,nIndexAccount
                                ,strMainPropertyAccount
                                ,strSQLAccount
                                ,strNextControlsAccount
                                ,strAccountNoAccount
                                ,strTitleAccount
                                ,strFirstTDAccount
                                ,strSecondTDAccount);
%>
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
                        name=textfield24225 size=25 disabled value="<%=ChineseCurrency.showChinese( DataFormat.formatAmount(dBillSum))%>"><BR><INPUT class=tar 
                        name=textfield2422224 size=25 disabled value="<%=DataFormat.formatDisabledAmount(dBillSum)%>"> </TD>
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
                        name=textfield24222242 size=25 disabled value="<%= DataFormat.formatDisabledAmount(dBillAmount)%>"> 
                  </TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
            
          </TD>
          <TD align=right height=13 width=1>&nbsp;</TD></TR>
        <TR>
          <TD height=50 width=1>&nbsp;</TD>
          <TD height=50>&nbsp;</TD>
          <TD align=right colSpan=7 height=50>            
            <DIV align=right>				
				<INPUT class=button name=Submit42232 onclick="backto()" type=button value=" 上一步 ">
				<INPUT class=button name="tjtxshq" onclick="confirmSave(frm)" type=button  onfocus="nextfield='submitfunction';" value="保 存">	
            </DIV>
		  </TD>
		</TR>
		</TBODY></TABLE></TD></TR></TBODY></TABLE>
<P><BR></P>

<input type="hidden" name="control" value="view">
<input type="hidden" name="backurl" value="<%=strBackURL%>">
<input type="hidden" name="lContractID" value="<%=lContractID%>">
<input type="hidden" name="lCredenceID" value="<%=lCredenceID%>">
<input type="hidden" name="lDraftTypeID" value="<%=lDraftTypeID%>">
<input type="hidden" name="strDraftCode" value="<%=strDraftCode%>">
<input type="hidden" name="tsPublicDate" value="<%=tsPublicDate%>">
<input type="hidden" name="tsAtTerm" value="<%=tsAtTerm%>">
<input type="hidden" name="strApplyClient" value="<%=strApplyClient%>">
<input type="hidden" name="dBillAmount" value="<%=dBillAmount%>">
<input type="hidden" name="dRate" value="<%=dRate%>">
<input type="hidden" name="dBillInterest" value="<%=dBillInterest%>">
<input type="hidden" name="strBillID" value="<%=strBillID%>">
<input type="hidden" name="lGrantTypeID" value="<%=lGrantTypeID%>">
</form>
	
<script language="JavaScript">

function backto()
{

	 eval("self.location='../discountapply/d031-c.jsp?control=view&lContractID=<%=lContractID%>&strBillID=<%=strBillID%>'");

}
function confirmSave(frm)
{
  	if (!checkDate(frm.tsFillDate,0,"填写日期")) 
	{
		return false;
	}
	/*
	if (!InputValid(frm.strDraftTypeName, 0, "string", 0, 0, 0,"贴现汇票种类")) 
	{
		return false;
	}
	*/
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
	
	if(frm.bankpay.checked == false && frm.currentaccount.checked == false)
    {
		alert('请选择一种放款方式');
        return false;
    }
            
    if(frm.bankpay.checked == true && frm.currentaccount.checked == true)
    {
		alert('只能选择一种放款方式');
        return false;
    }
                
    if(frm.bankpay.checked == true)
    {
		frm.lGrantTypeID.value = <%=LOANConstant.TransType.Bank%>;
		//开户银行,added by fanyang
        if (!checkMagnifier("frm","naccountbankid","txtBA","开户银行"))
        {
			return false;
        }

        if (!checkMagnifier("frm","sreceiveaccount","txtAccount","收款方账号"))
        {
			return false;
        }
        
		if (!checkString(frm.sreceiveclientname,"收款方账号"))
        {
            return false;
        }

        if (!checkString(frm.sreceiveclientname,"收款方名称"))
        {
            return false;
        }
                    
        if (!checkString(frm.sremitinprovince,"汇入地（省）"))
        {
            return false;
        } 
               
        if (!checkString(frm.sremitincity,"汇入地（市）"))
        {
			return false;
        } 
                  
        if (!checkString(frm.sremitbank,"汇入行名称"))
        {
			return false;
        }
    }
                
    if(frm.currentaccount.checked == true)
    {
		frm.lGrantTypeID.value = <%=LOANConstant.TransType.CurrentAccount%>;

        if (!checkMagnifier("frm","ngrantcurrentaccountid","txtAccountCodeCtrl1","账号"))
        {
			return false;
        }
    }

	if(confirm("是否保存贴现凭证？"))
	{
		frm.control.value="save";
		frm.action="../discountapply/d035-c.jsp";
		showSending();
		frm.submit();
		return true;
	}
}

/**
 * 开户行放大镜的SQL语句
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nIsSingleBank 是否单边账银行（1，是；其它，不是）
 * nAccountID 账户ID
 * sBranchNoOrName 开户行编号或名称
 */
function getBranchSQL(nOfficeID, nCurrencyID,nIsSingleBank,nAccountID,sBranchNoOrName)
{
	var sql = "";
	if (nAccountID > 0)
	{
		sql = "select distinct b.ID BranchID,b.sCode BranchNo,b.sName BranchName,ab.sBankAccountNo BranchAccountNo";
		sql += " from sett_Branch b,sett_AccountBank ab ";
	    sql += " where b.nStatusID=1 and ab.nBankID(%2B)=b.ID";
		
		sql += " and ab.nAccountID = " + nAccountID;
	}
	else
	{
		sql = "select distinct b.ID BranchID,b.sCode BranchNo,b.sName BranchName,'' BranchAccountNo";
		sql += " from sett_Branch b ";
	    sql += " where b.nStatusID=1 ";
	}
    if (nOfficeID > 0)
	{
		sql += " and b.nOfficeID = " + nOfficeID;
	}
    if (nCurrencyID > 0)
	{
		sql += " and b.nCurrencyID = " + nCurrencyID;
	}		
	if (nIsSingleBank = 1)
	{
		//目前还没有用
	}	
	if (sBranchNoOrName != null && sBranchNoOrName.length > 0)
	{
		sql += " and (b.sCode like '%25" + sBranchNoOrName + "%25' or b.sName like '%25"+sBranchNoOrName+"%25')";
	}	
	sql += " order by b.sCode";
	
	return sql;
}

/**
 * 外部账户放大镜
 * nOfficeID 办事处ID
 * sExtAcctNo 外部账号
 */
function getExtAcctSQL(nOfficeID,sExtAcctNo,nClientID)
{      
	var sql = "select ID ExtAcctID,sPayeeacctno ExtAcctNo,sPayeename ExtAcctName,sPayeeprov ExtProvince,sPayeecity ExtCity,sPayeebankname ExtBankName ";
    sql += " from ob_payeeinfo where nIsCpfAcct=2";
	sql += "  and  ncurrencyid=" +<%=sessionMng.m_lCurrencyID%>;
	sql += "  and  nStatusid=" +<%=OBConstant.RecordStatus.VALID%>;
	
    if (nClientID > 0)
	{
		sql += " and nClientID = " + nClientID;
	}
	if (sExtAcctNo != null && sExtAcctNo.length > 0)
	{
		sql += " and sPayeeacctno like '%25" + sExtAcctNo + "%25'";
	}
	sql += " order by sPayeeacctno";
	
    return sql;
}

<%if (lCredenceID < 1) {%>
	firstFocus(frm.tsFillDate);
<%} else {%>
	firstFocus(frm.strApplyAccount);
<%}%>
//setSubmitFunction("confirmSave(frm)");
setFormName("frm");	 

</script>

<jsp:include page="../../magnifier/MagnifierSQL.jsp"/>

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