<%--
 页面名称 ：d018-v.jsp
 页面功能 : 新增贴现申请-贴现申请详细信息 显示页面
 作    者 ：gqzhang
 日    期 ：2004年1月
 特殊说明 ：实现操作说明：
 修改历史 ：
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
                 java.util.Iterator,
				 java.util.Collection,
                 javax.servlet.*,
				 com.iss.itreasury.util.*,
				 com.iss.itreasury.loan.util.*,
                 com.iss.itreasury.ebank.obdiscountapply.bizlogic.*,
                 com.iss.itreasury.ebank.obdiscountapply.dataentity.*,
				 com.iss.itreasury.ebank.obsystem.bizlogic.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.*,
                 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.loan.loancommonsetting.dataentity.ClientInfo"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%
	/* 标题固定变量 */
	String strTitle = "[贴现申请]";
%>					
<%
  try
  {
	   Log.print("*******进入页面--ebank/loan/discountapply/d018-v.jsp*******");
	
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
		
		//定义变量
		 String strTemp = "";
		 String strAction = (String)request.getAttribute("txtAction");
		 String frompage = (String)request.getAttribute("frompage");
		
		//贴现信息
		 long lID = -1; //贴现ID标识
         String strDiscountCode =  ""; //贴现编号
         String strContractCode = ""; //贴现合同编号
         long lApplyClientID =  -1; //申请单位编号
         String strApplyClientName = ""; //申请单位名称
         String strApplyAccount = ""; //申请单位账号
		 String strApplyBank = ""; //申请单位开户银行
		 long lApplyOfficeID =  -1; //申请单位开户办事处标示
		 String strApplyOfficeName = ""; //申请单位开户办事处名称
		 String strApplyLOfficeAccount = ""; //申请单位开户办事处账号
		 long lTypeID =  -1; //贷款类型
		 long lCurrencyID =  -1; //币种
		 long lOfficeID =  -1; //办事处标示
		 String strOfficeName = ""; //办事处名称
		 String strLOfficeAccount = ""; //办事处账号
		 long lStatusID =  -1; //状态
		 long lInputUserID =  -1; //录入人标示
		 String strInputUserName = ""; //录入人名称
		 Timestamp tsInputDate =  null; //录入时间
		 long lNextCheckUserID =  -1; //下一个审核人标示
		 long lLsReviewUserID =  -1; //最后审核人ID
		 String strLsReviewUserName = ""; //最后审核人名称
		 long lReviewStatusID =  -1; //最后审核状态
		 long lIsCheck =  -1; //是否审核过
		 double dApplyDiscountAmount = 0.0; //申请贴现金额
		 double dExamineAmount = 0.0; //批准金额
		 double dCheckAmount = 0.0; //核定金额
		 double dDiscountRate = 0.0; //利率
		 String strDiscountPurpose = ""; //贴现用途
		 String strDiscountReason = ""; //贴现原因
		 Timestamp tsDiscountDate = null; //贴现计息时间
		 Timestamp tsDiscountStartDate = null; //贴现开始时间
		 Timestamp tsDiscountEndDate = null; //贴现到期时间
		 long lApplyDiscountPO =  -1; //申请贴现汇票（张数）
		 long lBankAccepPO =  -1; //银行承兑汇票（张数）(页面)
		 long lBizAcceptPO =  -1; //商业承兑汇票（张数）(页面)
		 long lBankCount =  -1; //银行承兑汇票（张数）
		 long lBizCount =  -1; //商业承兑汇票（张数）
		 double dInterest =  0.0; //贴现利息
		 long lBillCount =  -1; //汇票总张数
		 double dBillAmount =  0.0; //汇票总金额
		 long lCount =  -1; //记录数
		 String strDocumentType = "";//随表报送书面材料
		 long subtypeid=-1;
		 
		 //客户信息
		 String strName = "";
		 String strCode = "";
		 String strLicenceCode = "";
		 String strAccount = "";
		 String strBank1 = "";
		 String strBankAccount1 = "";
		 long lCorpNatureID = -1;
		 String strParentCorpName = "";
		 long lIsPartner = -1;
		 long lLoanClientTypeID = -1;
	    long loanType=-1;
		

		
		DiscountInfo discountInfo = new DiscountInfo();
		
		discountInfo = (DiscountInfo)request.getAttribute("resultInfo");
		
		if(discountInfo != null)
		{
          lID = discountInfo.getID(); //贴现ID标识
          strDiscountCode = discountInfo.getDiscountCode(); //贴现编号
          strContractCode = discountInfo.getContractCode(); //贴现合同编号
		  lApplyClientID = discountInfo.getApplyClientID(); //申请单位编号
		  strApplyClientName = discountInfo.getApplyClientName(); //申请单位名称
		  strApplyAccount = discountInfo.getApplyAccount(); //申请单位账号
		  strApplyBank = discountInfo.getApplyBank(); //申请单位开户银行
		  lApplyOfficeID = discountInfo.getApplyOfficeID(); //申请单位开户办事处标示
		  strApplyOfficeName = discountInfo.getApplyOfficeName(); //申请单位开户办事处名称
		  strApplyLOfficeAccount = discountInfo.getApplyLOfficeAccount(); //申请单位开户办事处账号
		  lTypeID = discountInfo.getTypeID(); //贷款类型
		  lCurrencyID = discountInfo.getCurrencyID(); //币种
		  lOfficeID = discountInfo.getOfficeID(); //办事处标示
		  strOfficeName = discountInfo.getOfficeName(); //办事处名称
		  strLOfficeAccount = discountInfo.getLOfficeAccount(); //办事处账号
		  lStatusID = discountInfo.getStatusID(); //状态
		  lInputUserID = discountInfo.getInputUserID(); //录入人标示
		  loanType=discountInfo.getTypeID();

		  
		  Log.print("\n===========录入人："+lInputUserID+"======登录人："+sessionMng.m_lUserID);
		  
		  strInputUserName = discountInfo.getInputUserName(); //录入人名称
		  tsInputDate = discountInfo.getInputDate(); //录入时间
		  
		  Log.print("\n===========录入时间："+tsInputDate);
		  
		  lNextCheckUserID = discountInfo.getNextCheckUserID(); //下一个审核人标示
		  lLsReviewUserID = discountInfo.getLsReviewUserID(); //最后审核人ID
		  strLsReviewUserName = discountInfo.getLsReviewUserName(); //最后审核人名称
		  lReviewStatusID = discountInfo.getReviewStatusID(); //最后审核状态
		  lIsCheck = discountInfo.getIsCheck(); //是否审核过
		  dApplyDiscountAmount = discountInfo.getApplyDiscountAmount(); //申请贴现金额
		  dExamineAmount = discountInfo.getExamineAmount(); //批准金额
		  dCheckAmount = discountInfo.getCheckAmount(); //核定金额
		  dDiscountRate = discountInfo.getDiscountRate(); //利率
		  strDiscountPurpose = discountInfo.getDiscountPurpose(); //贴现用途
		  strDiscountReason = discountInfo.getDiscountReason(); //贴现原因
		  tsDiscountDate = discountInfo.getDiscountDate(); //贴现计息时间
		  tsDiscountStartDate = discountInfo.getDiscountStartDate(); //贴现开始时间
		  tsDiscountEndDate = discountInfo.getDiscountEndDate(); //贴现到期时间
		  lApplyDiscountPO = discountInfo.getApplyDiscountPO(); //申请贴现汇票（张数）
		  lBankAccepPO = discountInfo.getBankAccepPO(); //银行承兑汇票（张数）(页面)
		  lBizAcceptPO = discountInfo.getBizAcceptPO(); //商业承兑汇票（张数）(页面)
		  lBankCount = discountInfo.getBankCount(); //银行承兑汇票（张数）
		  lBizCount = discountInfo.getBizCount(); //商业承兑汇票（张数）
		  dInterest = discountInfo.getInterest(); //贴现利息
		  lBillCount = discountInfo.getBillCount(); //汇票总张数
		  dBillAmount = discountInfo.getBillAmount(); //汇票总金额
		  lCount = discountInfo.getCount(); //记录数
		  strDocumentType = discountInfo.getDocumentType();
        }
		long[] loanTypeid={loanType};
			//根据ClientID查找申请单位信息
			long lClientID = sessionMng.m_lClientID;
			Log.print("=============客户ID："+lClientID);
			
			OBSystemHome  obSystemHome = null;
	        OBSystem      obSystem = null;
	        obSystemHome = (OBSystemHome)EJBObject.getEJBHome("OBSystemHome");
	        obSystem = obSystemHome.create();
	        ClientInfo clientInfo = null;
			
			clientInfo = obSystem.findClientByID(lClientID);
			if(clientInfo != null)
			{
			 strName = clientInfo.getName();
			 strCode = clientInfo.getCode();
			 strLicenceCode = clientInfo.getLicenceCode();
			 strAccount = clientInfo.getAccount();
			 strBank1 = clientInfo.getBank1();
			 strBankAccount1 = clientInfo.getBankAccount1();
			 lCorpNatureID = clientInfo.getCorpNatureID();
			 strParentCorpName = clientInfo.getParentCorpName();
			 lIsPartner = clientInfo.getIsPartner();
			 lLoanClientTypeID = clientInfo.getLoanClientTypeID();
			}
		

	  //显示文件头
      OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);

%>
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<safety:resources />
<form name="frm" method="post"  target="_self">
<input type="hidden" name="lID" value="<%=lID%>">
<input type="hidden" name="lStatusID" value="<%=OBConstant.LoanInstrStatus.SUBMIT%>"><!--用于提交申请!-->
<input type="hidden" name="strDiscountCode" value="<%=strDiscountCode%>">
<input type="Hidden" name="txtAction" value="<%=strAction%>">
<input type="hidden" name="strBackPage" value="/loan/discountapply/d018-v.jsp">
<TABLE border=0 class=top width="730">
  <TBODY>
  <TR class="tableHeader">
    <TD class=FormTitle height=35><B>贴现申请——申请书保存</B></TD></TR>
  <TR>
    <TD height=616 vAlign=top>
      <TABLE align=center border=0 width=100%>
        <TBODY>
        <TR>
          <TD height=2 width=1>&nbsp;</TD>
          <TD colSpan=10 height=2><B></B></TD></TR>
        <TR>
          <TD colSpan=11 height=207>
            <TABLE cellPadding=0 width=100%>
              <TBODY>
              <TR>
                <TD height=24 width=200 nowrap>贴现申请编号：<%=strDiscountCode%></TD>
                <TD colSpan=5 height=24>
                  <DIV align=right>
			<% if (lStatusID == OBConstant.LoanInstrStatus.SAVE || lStatusID == OBConstant.LoanInstrStatus.SUBMIT) { %>
				<INPUT class=button name="newApply" onclick="doCreateApply();" type=button value="新增贴现申请">
				<% if (lStatusID == OBConstant.LoanInstrStatus.SAVE && lInputUserID == sessionMng.m_lUserID) { %>	
				<INPUT class=button name="submitApply" onclick="confirmSave(frm);" type=button value="提交贴现申请">
				<% } %>
				<% if (lInputUserID == sessionMng.m_lUserID &&(lStatusID == OBConstant.LoanInstrStatus.SUBMIT||lStatusID == OBConstant.LoanInstrStatus.SAVE) ) { %>
				<INPUT class=button name="submitCancel" onclick="confirmCancel(frm);" type=button value="取消贴现申请">
				<% } %>
				<%if (lInputUserID == sessionMng.m_lUserID)
				{
				%>
				<INPUT class=button name=doPrint onclick="printIt('../discountapply/d022-v.jsp?lID=<%=lID%>');" type="button" value=" 打 印 "> 
				<%
				}
				%>
				<% } %>
				<INPUT class=button name="backTo" onclick="doGoBack();" type=button value=" 返 回">
				 </DIV></TD></TR>
				  <HR align=center SIZE=2 width="100%">
				 <tr>
          <TD colSpan=6 nowrap>贷款子类名称：
		  <select class='box'><option>
<%=LOANConstant.SubLoanType.getName(discountInfo.getSubTypeId())%>
			</option></select> </TD>	  
		  </tr>
              <TR>
                <TD colSpan=6 height=24>
                  <DIV align=center>
                 
                  </DIV>
                  <P><U>贴现申请单位资料</U></P></TD></TR>
              <TR>
                <TD height=19 width=129>
                  <P>单位名称：</P></TD>
                <TD height=19 width=251 colSpan=5>
                  <P><INPUT class=tar disabled 
                  name=tf_dw3 size="80" value="<%=DataFormat.formatString(strName)%>"></P></TD>
			  </TR>
			  <TR>
                <TD height=19 width=126>
				<%
				String sOfficeName = "客户名称";
				%>
				<%=sOfficeName%>

				</TD>
                <TD colSpan=5 height=19><INPUT class=tar disabled 
                  name=tf_dw33 value="<%=DataFormat.formatString(Env.getClientName())%>"></TD></TR>
              <TR>
                <TD height=2 width=129>
                  <P>客户编号：</P></TD>
                <TD height=2 width=251>
                  <P><INPUT class=tar disabled name=tf_dw32 
                  value="<%=DataFormat.formatString(strCode)%>"></P></TD>
                <TD height=2 width=126>
                  <P>营业执照号码：</P></TD>
                <TD height=2 width=101><INPUT 
                  class=tar disabled name=textfield223 value="<%=DataFormat.formatString(strLicenceCode)%>"> 
                  </TD>
                <TD colSpan=2 height=2>&nbsp;</TD></TR>
              <TR>
                <TD height=8 width=129>
				<%
				String strMagnifierNameAccount = "账号 ";
				%>
				<%=strMagnifierNameAccount%>
				</TD>
                <TD height=8 width=251><INPUT 
                 class=tar disabled value="<%=DataFormat.formatString(strAccount)%>"></TD>
                <TD height=8 width=126>&nbsp;</TD>
                <TD height=8 width=101>&nbsp;</TD>
                <TD colSpan=2 height=8>&nbsp;</TD></TR>
              <TR>
                <TD height=30 width=129>
                  <P>开户银行：</P></TD>
                <TD height=30 width=251>
                  <P><INPUT class=tar disabled value="<%=DataFormat.formatString(strBank1)%>"></P></TD>
                <TD height=30 width=126>
                  <P>开户银行账号：</P></TD>
                <TD colSpan=3 height=30>
				  <INPUT class=tar disabled value="<%=DataFormat.formatString(strBankAccount1)%>"></TD></TR>
              <TR>
                <TD colSpan=2 height=2>
				<%if (lInputUserID == sessionMng.m_lUserID && (lStatusID == OBConstant.LoanInstrStatus.SAVE || lStatusID == OBConstant.LoanInstrStatus.SUBMIT))
				{
				%>
				<!--INPUT class=button name=Submit32 onclick="doModifyUnit();" type=button value="修改贴现单位资料"--> 
				<%
				}
				//配置项,是否显示报表
				//目前的情况是:南航财务不显示,其他的项目显示.
				if (Config.getBoolean(ConfigConstant.LOAN_CLIENT_REPORT,true))
				{
	            %>
                &nbsp;&nbsp;&nbsp;&nbsp;<!--input class=button name="loanDCB" type=button value="贷款调查表" onclick="Javascript:window.open('../../content/c220-c.jsp?ParentID=<%=lID%>&control=view','','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes');"-->
				<%
				}
				%>
				  </TD>
                <TD colSpan=3 height=2>
                  <P><BR></P></TD>
                <TD height=2 width=174>&nbsp;</TD></TR></TBODY></TABLE>
            <HR>
          </TD></TR>
        <TR>
          <TD colSpan=11 height=104>
            <TABLE width="104%">
              <TBODY>
              <TR vAlign=middle>
                <TD height=31 width="14%"><U>贴现申请详情</U></TD>
                <TD height=31 width="13%">&nbsp;</TD>
                <TD height=31 width="8%">&nbsp;</TD>
                <TD height=31 width="15%">&nbsp;</TD>
                <TD height=31 width="11%">&nbsp;</TD>
                <TD height=31 width="15%">&nbsp;</TD>
                <TD height=31 vAlign=middle width="24%">&nbsp;</TD></TR>
              <TR vAlign=middle>
                <TD height=29 width="14%">申请贴现汇票：</TD>
                <TD height=29 width="13%">共 <INPUT class=tar disabled 
                  name=textfield282242 size=3 value="<%=lApplyDiscountPO>0 ? lApplyDiscountPO : 0%>"> 张</TD>
                <TD height=29 width="8%">其中：</TD>
                <TD height=29 width="15%">银行承兑汇票：</TD>
                <TD height=29 width="11%"><INPUT class=tar disabled 
                  name=textfield2822322 size=3 value="<%=lBankAccepPO>0 ? lBankAccepPO : 0%>"> 张</TD>
                <TD height=29 width="15%">商业承兑汇票：</TD>
                <TD height=29 vAlign=middle width="24%"><INPUT class=tar disabled 
                  name=textfield28222242 size=3 value="<%=lBizAcceptPO>0 ? lBizAcceptPO : 0%>"> 
            张</TD></TR></TBODY></TABLE>
            <TABLE border=0 width="100%">
             <TBODY>
              <TR>
                <TD height=41 width="14%">申请贴现金额：</TD>
                <TD height=41 width="2%">
                  <DIV align=right>￥</DIV></TD>
                <TD height=41 width="24%"><INPUT class=tar 
                  disabled name=textfield2822232 size=18 value="<%=dApplyDiscountAmount>0 ? DataFormat.formatDisabledAmount(dApplyDiscountAmount) : "0.00"%>"> </TD>
                <TD height=41 width="17%">&nbsp;</TD>
                <TD height=41 width="3%">&nbsp;</TD>
                <TD height=41 width="40%">&nbsp;</TD>
			  </TR>
			  <TR>
                <TD height=41 width="14%">贴现开始日期：</TD>
                <TD height=41 width="2%">&nbsp;</TD>
				<TD height=41 width="24%">
			    <input type="Hidden" name="tsDiscountStartDate" value="<%=tsDiscountStartDate%>"><!--贴现日期-->
				<INPUT class=tar disabled name=textfield2822232 size=18 value="<%=tsDiscountStartDate !=null ? DataFormat.getDateString(tsDiscountStartDate):""%>"></TD>
                <TD height=41 width="17%">贴现到期日期：</TD>
                <TD height=41 colspan="2"><INPUT class=tar 
                  disabled name=textfield28222321 size=18 value="<%=tsDiscountEndDate !=null ? DataFormat.getDateString(tsDiscountEndDate):""%>"> </TD>
			  </TR>
			 </TBODY></TABLE></TD></TR>
        <TR>
          <TD colSpan=2 height=14>贴现原因：</TD>
          <TD colSpan=9 height=14><TEXTAREA class=box cols=65 disabled name=textarea><%=DataFormat.formatString(strDiscountReason)%></TEXTAREA> 
            </TD></TR>
        <TR>
          <TD colSpan=2 height=14>贴现用途：</TD>
          <TD colSpan=9 height=14><TEXTAREA class=box cols=65 disabled name=textarea2><%=DataFormat.formatString(strDiscountPurpose)%></TEXTAREA> 
            </TD></TR>
        <TR>
		<TR>
				<TD colSpan=2 height=41>是否买方付息：</TD>
				<TD colSpan=9 height=41>
				<SELECT class='box' name="isPurchaserInterest" onfocus="nextfield='discountClientName'" disabled>
					<OPTION value="<%=Constant.YesOrNo.YES%>" 
					<%if ((discountInfo!=null)&&(discountInfo.getIsPurchaserInterest()==Constant.YesOrNo.YES)) {out.println(" selected");} else {out.println("");}%>>是</OPTION>
					<OPTION value="<%=Constant.YesOrNo.NO%>" 
					<%if ((discountInfo!=null)&&(discountInfo.getIsPurchaserInterest()==Constant.YesOrNo.NO)) {out.println(" selected");} else {out.println("");}%>>否</OPTION>
				</SELECT>
				</TD>
			</TR>
			<TR>
				<TD colSpan=2 height=41>出票人：</TD>
				<TD colSpan=9 height=41>
					<INPUT class=box disabled name=tf_dw3 size="80" value="<%=DataFormat.formatString(discountInfo.getDiscountClientName())%>">
				</TD>
			</TR>



		<tr>

          <TD colSpan=11 height=14>
		    <%if (lInputUserID == sessionMng.m_lUserID && (lStatusID == OBConstant.LoanInstrStatus.SAVE || lStatusID == OBConstant.LoanInstrStatus.SUBMIT))
		   {
		   %>
		  <INPUT class=button name=Submit322 onclick="doModifyApply();" type=button value="修改贴现申请详情"> 
		   <%
		  }
		  %>
            </TD></TR>
        <TR>
          <TD colSpan=11 height=14>
            <HR>
          </TD></TR>
        <TR>
          <TD colSpan=11 height=2><U>贴现票据明细表</U></TD></TR>
        <TR>
          <TD colSpan=11 height=2>
		  <%if (lInputUserID == sessionMng.m_lUserID && (lStatusID == OBConstant.LoanInstrStatus.SAVE || lStatusID == OBConstant.LoanInstrStatus.SUBMIT))
		   {
		   %>
		  <INPUT class=button name=Submit2 onclick="doModifyBillDetail();" type=button value="修改贴现票据明细表"> 
		  <%
		  }
		  else
		  {
		  %>
		  <INPUT class=button name=Submit2 onclick="doViewBillDetail();" type=button value="查看贴现票据明细表"> 
		  <%
		  }
		  %>
          </TD></TR>
        <TR>
          <TD colSpan=11 height=2>
            <HR>
          </TD></TR>
		   <TR>
          <TD colSpan=11 height=2><U>随表报送的书面材料</U></TD></TR>
       
		<%
		
		String[] strDocumentTypes = OBConstant.DocumentType.getAllCode();
		
		if (strDocumentType == null )
		{
		  strDocumentType = "z";
		}
		
		
		for(int i=0;i<strDocumentTypes.length;i++)
		{
		 
		  %>
		   <TR>
		   <TD colSpan=11 height=2>
		   &nbsp;&nbsp;&nbsp;&nbsp;
		   <%
		    
		   if (strDocumentType.indexOf(strDocumentTypes[i]) > 0)
		   {
		   %> 
		      <%if (lInputUserID == sessionMng.m_lUserID && (lStatusID == OBConstant.LoanInstrStatus.SAVE || lStatusID == OBConstant.LoanInstrStatus.SUBMIT))
				{
				%>
		         <input  type="Checkbox" name="DocumentType" value="<%=strDocumentTypes[i]%>" checked>
			    <%
				}
				else
				{
				%>
				<input  type="Checkbox" name="DocumentType" value="<%=strDocumentTypes[i]%>" checked disabled>
				<%
				}
				%>
		   <%
		   }
		   else
		   {
		   %>
		        <%if (lInputUserID == sessionMng.m_lUserID && (lStatusID == OBConstant.LoanInstrStatus.SAVE || lStatusID == OBConstant.LoanInstrStatus.SUBMIT))
				{
				%>
		       <input  type="Checkbox" name="DocumentType" value="<%=strDocumentTypes[i]%>">
			   <%
			   }
			   else
			   {
			   %>
			   <input  type="Checkbox" name="DocumentType" value="<%=strDocumentTypes[i]%>" disabled>
			   <%
			   }
			   %>
		   <%
		   }
		   %>
		   &nbsp;&nbsp;<%=OBConstant.DocumentType.getName(strDocumentTypes[i])%>
          </TD>
		  </TR>
		  <%
		 }
		%>
		  
        <TR>
          <TD colSpan=11 height=2>
            <HR>
          </TD></TR>
        <TR>
          <TD height=2 width=1>&nbsp;</TD>
          <TD align=left height=2 width=122 nowrap>录入人：<%=DataFormat.formatString(strInputUserName)%></TD>
          <TD align=center height=2 width=410>录入时间：<%=tsInputDate!=null ? DataFormat.getDateString(tsInputDate) : ""%></TD>
          <TD align=center height=2 width=252>状态： 
            <%=OBConstant.LoanInstrStatus.getName(lStatusID)%>
		  </TD>
          <TD align=right colSpan=2 height=2></TD>
          <TD align=right colSpan=2 height=2></TD>
          <TD align=right height=2 width=0>
            <DIV align=left></DIV></TD>
          <TD align=right colSpan=2 height=2 width=9>
            <DIV align=center></DIV></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
<P><BR></P>
</form>
	
<script language="JavaScript">
function doGoBack()
{
   if(confirm("是否返回？"))
	{
	
	  <%if(strAction != null && strAction.equals("modify"))//从查询返回
	  {
	  %>
		frm.action="../query/q003-c.jsp";
	  <%
	  }
	  else
	  {
	  %>
	  frm.action="../discountapply/d008-c.jsp";
	  <%
	  }
	  %>
		frm.target="_self";	
		showSending();
		frm.submit();
		return true;
	}
}

function doCreateApply()
{
  if(confirm("是否新增贴现申请？"))
	{
		document.location.href="../discountapply/d002-c.jsp";
		showSending();
		return true;
	}
}


function doModifyUnit()
{
   if(confirm("是否修改贴现单位材料信息？"))
	{
		frm.action="../discountapply/d002-c.jsp";
		showSending();
		frm.submit();
	}
}



function doModifyApply()
{
   if(confirm("是否修改贴现申请详情？"))
	{
		frm.action="../discountapply/d005-c.jsp?frompage=<%=frompage%>";
		showSending();
		frm.submit();
		return true;
	}
}

function doModifyBillDetail()
{
   if(confirm("是否修改贴现票据明细？"))
	{
		frm.action="../discountapply/d008-c.jsp?frompage=<%=frompage%>";
		showSending();
		frm.submit();
		return true;
	}
}

function doViewBillDetail()
{
   		frm.target="_blank";
		frm.action="../discountapply/d008-c.jsp?modifyFlag=1";
		frm.submit();	
}

function confirmSave(frm)
{
	/*if(<%=dApplyDiscountAmount%> != <%=dBillAmount%>)
	{
		alert("贴现汇票总金额应与贴现申请金额相等！");
		return false;
	}
	if(<%=lBankAccepPO%> != <%=lBankCount%>)
	{
		alert("银行承兑汇票张数应与申请张数相等！");
		return false;
	}
	if(<%=lBizAcceptPO%> != <%=lBizCount%>)
	{
		alert("商业承兑汇票张数应与申请张数相等！");
		return false;
	}
	if(<%=lApplyDiscountPO%> != <%=lBankCount+lBizCount%>)
	{
		alert("汇票张数应与申请张数相等！");
		return false;
	}*/
	if(confirm("是否提交申请？"))
	{
		frm.action="../discountapply/d020-c.jsp";
		showSending();
		frm.submit();
		return true;
	}
}

function confirmCancel(frm)
{
	if(confirm("是否取消申请？"))
	{
		frm.action="../discountapply/d021-c.jsp";
		showSending();
		frm.submit();
		return true;
	}
}

function printIt(url)
{
	if(confirm("是否打印？"))
	{
	 window.open(url,"popup","width=800,height=600,scrollbars=1,resizable=1,menubar=0,toolbar=0,locations=0,directories=0, screenX=0,screenY=0,left=300,top=180");
	} 
}

<% if (lStatusID == OBConstant.LoanInstrStatus.SAVE  && lInputUserID == sessionMng.m_lUserID) 
{ %>	
firstFocus(frm.submitApply);
//setSubmitFunction("confirmSave(frm)");
<%
}
%>
setFormName("frm");
</script>
<%	
   //显示文件尾
		OBHtml.showOBHomeEnd(out);	
    }
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>
<%@ include file="/common/SignValidate.inc" %>

