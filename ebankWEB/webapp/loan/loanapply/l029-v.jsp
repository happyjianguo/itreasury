<%
/**
 * 页面名称 ：l029-v.jsp
 * 页面功能 : 显示贷款申请的总资料，只是查看
 * 作    者 ：gump
 * 日    期 ：2003-09-27
 * 特殊说明 ：
 *			  
 * 修改历史 ：
 */
%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
	java.util.*,
	java.net.URLEncoder,
	com.iss.itreasury.loan.loancommonsetting.dataentity.*,
	com.iss.itreasury.loan.util.*,
	com.iss.itreasury.ebank.util.*,
	com.iss.itreasury.ebank.obloanapply.dataentity.*,
	com.iss.itreasury.ebank.obloanapply.bizlogic.*,
	com.iss.itreasury.ebank.obsystem.bizlogic.*,
	com.iss.itreasury.ebank.obdataentity.*,
	com.iss.itreasury.ebank.obquery.dataentity.*,
	com.iss.itreasury.loan.contract.bizlogic.*,
	com.iss.itreasury.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%
    try{
    
		//判断是否登录
		if( !sessionMng.isLogin() )
		{	
			OBHtml.showMessage(out,sessionMng,null,"登录",null,Constant.RecordStatus.INVALID,"Gen_E002");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		//判断是否有权限
		if (sessionMng.hasRight(request) == false)
		{
			OBHtml.showMessage(out,sessionMng,null,"登录",null,Constant.RecordStatus.INVALID,"Gen_E003");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}

		OBLoanApplyInfo appInfo=(OBLoanApplyInfo)request.getAttribute("LoanApplyInfo");		//贷款申请信息
	    long loanType=appInfo.getTypeID();
		long[] loanTypeid={loanType};
    	String action="";
		//boolean iswt=false;
    	boolean isyt=false;
		String str1="借款详情";
		String str2="贷款担保方式:";
		String str3="修改贷款性质/担保方式";
		String str4="贷款担保资料明细";
		String str5="修改担保资料明细";
		String str6="贷款金额:";

		if (loanType==OBConstant.LoanInstrType.ASSURE)
    	{
    		str1="担保申请详情";
			str2="反担保方式:";
			str3="修改反担保方式";
			str4="反担保资料明细";
			str5="修改反担保资料明细";
			str6="反担保金额:";
		}
		isyt=false;
	    String loanTypeName=OBConstant.LoanInstrType.getName(loanType);    

		Vector assVector=(Vector)appInfo.getAssureInfo();
		Vector planInfo=(Vector)request.getAttribute("Collection");						//贷款计划信息
		
		ClientInfo cInfo=null;
		ClientInfo wtClientInfo=null;
		if( loanType==OBConstant.LoanInstrType.LOAN_WT)
		{
			wtClientInfo=(ClientInfo)request.getAttribute("ClientInfo");
			cInfo=(ClientInfo)request.getAttribute("wtClientInfo");
		}else{
			cInfo=(ClientInfo)request.getAttribute("ClientInfo");
		}	
		
	
	    /* 客户的信息*/
    	long clientID=cInfo.getClientID();						//客户ID
 		long[] QTClientID={-1,-1,-1};
    	String[] QTClientName=new String[3];
    	float[] QTScale =new float[3];
    	String[] QTCardNo=new String[3];
    	String[] QTPwd=new String[3];
   
	    /*借款详情*/
 	   long loanID=appInfo.getID();
       long wtClientID=appInfo.getConsignClientID();
    	//String applyCode=appInfo.getApplyCode();
    	String applyCode=appInfo.getInstructionNo();
    	double interestRate=appInfo.getInterestRate();						//贷款执行利率
    	double chargeRate=appInfo.getChargeRate();
		long chargeRateTypeID=appInfo.getChargeRateTypeID();//收取方式
    	Timestamp applyDate=appInfo.getInputDate();							//贷款申请时间
    	long intervalNum=appInfo.getIntervalNum();							//期限
    	Timestamp applyStartDate=appInfo.getStartDate();					//贷款申请开始时间
    	Timestamp applyEndDate=appInfo.getEndDate();
    	double loanAmount=appInfo.getLoanAmount();
    	String loanReason=appInfo.getLoanReason();
    	String loanPurpose=appInfo.getLoanPurpose();
    	double selfAmount=appInfo.getSelfAmount();
    	String other=appInfo.getOther();
    	String clientInfo=appInfo.getClientInfo();
    	long planVersion=appInfo.getPlanVersion();
	    
	    long isCredit=appInfo.getIsCredit();
	    long isAssure=appInfo.getIsAssure();
	    long isPledge=appInfo.getIsPledge();
	    long isImpawn=appInfo.getIsImpawn();
	    long isCircle=appInfo.getIsCircle();
	    long isTechnical=appInfo.getIsTechnical();
	    long isSaleBuy=appInfo.getIsSaleBuy();
		long isRecognizance = appInfo.getIsRecognizance();	//是否保证金;

		double assureChargeRate = appInfo.getAssureChargeRate();		//担保费率
		long assureChargeTypeID = appInfo.getAssureChargeTypeID();	//担保费收取方式
		long assureTypeID1 = appInfo.getAssureTypeID1();				//担保类型1
		long assureTypeID2 = appInfo.getAssureTypeID2();				//担保类型2
		String beneficiary = appInfo.getBeneficiary();				//受益人
	    
	    double payAmount=appInfo.getPlanPayAmount();
		double repayAmount=appInfo.getPlanRepayAmount();
	    
	    boolean hasIsAssure=false;
    	boolean hasIsPledge=false;
    	boolean hasIsImpawn=false;
		boolean hasIsRecognizance=false;
    
    	if ( loanReason==null ) loanReason="";
    	if ( loanPurpose==null ) loanPurpose="";
    	if ( other==null ) other="";
	    
    	long pageNo=1;
    	long orderParam=99;
    	long lDesc=Constant.PageControl.CODE_ASCORDESC_DESC;
    	long userID=-1;
    	String userName="";
    	String tmp="";
         
	    double assSumAmount=0;
    	double creditAmount=0;  
		double recognizanceAmount=0;    
    
		userID=sessionMng.m_lUserID;
		userName=sessionMng.m_strUserName;
		tmp=(String)request.getAttribute("nOrderParam");
		if ( (tmp!=null)&&(tmp.length()>0) )
			orderParam=Long.valueOf(tmp).longValue();
		
		tmp=(String)request.getAttribute("nDesc");
		if ( (tmp!=null)&&(tmp.length()>0) )
			lDesc=Long.valueOf(tmp).longValue();

		tmp=(String)request.getAttribute("nPageNo");
		if ( (tmp!=null)&&(tmp.length()>0) )
			pageNo=Long.valueOf(tmp).longValue();

		tmp=(String)request.getAttribute("txtAction");
		if ( (tmp!=null)&&(tmp.length()>0) )
			action=tmp;
					
		if ( cInfo!=null )
        {
    		QTClientName=cInfo.getQTClientName();
			QTScale=cInfo.getFQTScale();
			QTCardNo=cInfo.getQTCardNo();
			QTPwd=cInfo.getQTPwd();
		}	
	
					
        //显示文件头
        OBHtml.showOBHomeHead(out,sessionMng,"[贷款计划维护]",Constant.YesOrNo.YES);
        
        boolean isdq=false;
%>	

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />
<form name="frm">
<TABLE border=0 class=top width=730>
  <TBODY>
  <TR class="tableHeader">
      <TD class=FormTitle colspan=11><B><%=loanTypeName%>——查看</B></TD>
  </TR>
  <TR>
    <TD vAlign=top colspan=11>
        <TABLE align=center border=0 width=730>
          <TBODY> 
          <TR> 
            <TD colSpan=5 height=22><FONT size=2>申请书编号：<%=applyCode%></FONT></TD>
          </tr>
          <tr> 
            <TD  width=1>&nbsp;</TD>
            <TD width=10>&nbsp;</TD>
            <TD  width=10>&nbsp;</TD>
            <TD colSpan=8  align="right" width="800"> <FONT size=2> 
                        	<INPUT class=button name="asdfsadfsdfasda" onclick="addNew()" type="button" value=" 新 增 ">
          <% if ( appInfo.getInputUserID()==sessionMng.m_lUserID ) {%>
              <% if ( appInfo.getStatusID()==OBConstant.LoanInstrStatus.SAVE ) { %>
              	<INPUT class=button name="sadfsdfasd" onclick="commitApply()" type="button" value=" 提 交 ">
             	<!--<INPUT class=button name="sadfsdfasd1" onclick="modifyLoan()" type="button" value=" 修 改 ">-->
              	<INPUT class=button name="sadfsdfasd2" onclick="cancelLoan()" type="button" value=" 取 消 ">
              <% } %>
              <% if ( appInfo.getStatusID()==OBConstant.LoanInstrStatus.SUBMIT ) { %>
             	<!--<INPUT class=button name="sadfsdfasd1" onclick="modifyLoan()" type="button" value=" 修 改 ">-->
              	<INPUT class=button name="sadfsdfasd2" onclick="cancelLoan()" type="button" value=" 取 消 ">
              <%  } %>
           <% } %>   
              <INPUT class=button name="asdfsadfsdfasd" onclick="goBack()" type="button" value=" 返 回 ">

              <INPUT class=button name="asdfasdafasd" onclick="Javascript: frmPrint();" type="button" value=" 打 印 " >
              </FONT>
            </TD>
          </TR>
		   <tr>
            <td width="1" >&nbsp;</td>
            <td colspan="8"> 
		  <hr>
		  <table width="100%"><tr>
          <TD colSpan=5  nowrap>贷款子类名称：<%LOANConstant.SubLoanType.showList(out,"subLoanType",appInfo.getSubTypeId(),false,true,"disabled",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,loanTypeid,null );%></TD>	  
		  </tr></table>
          <% if (loanType==OBConstant.LoanInstrType.LOAN_WT){ %>
              <table align=center border=0 width="100%">
                <tbody> 
                <tr> 
                  <td colspan=4 height=27> 
                    <p><font size=2><u>借款单位资料</u></font></p>
                  </td>
                </tr>
                <tr> 
                  <td height=31 width="25%"><font size=2>
                  	借款单位客户编号：
                  </font></td>
                  <td height=31 width="31%"><font size=2> 
                    <input class=box disabled name=textfield24 value="<%=(wtClientInfo.getCode()==null)?"":wtClientInfo.getCode()%>" >
                    </font></td>
                  <td height=31 width="14%">&nbsp;</td>
                  <td height=31 width="43%">&nbsp;</td>
                </tr>
                <tr> 
                  <td height=27 width="16%"><font size=2>借款单位：</font></td>
                  <td height=27 colspan=3><font size=2> 
                    <input class=box size=80 disabled name=textfield232 value="<%=(wtClientInfo.getName()==null)?"":wtClientInfo.getName()%>">
                    </font></td>
                </tr>
                <tr> 
                  <td height=27 width="16%"><font size=2>联系人：</font></td>
                  <td height=27 width="31%"><font size=2> 
                    <input class=box disabled name=textfield2222 size=18 value="<%=(wtClientInfo.getContacter()==null)?"":wtClientInfo.getContacter()%>">
                    </font></td>
                  <td height=27 width="14%"></td>
                  <td height=27 width="39%"></td>
                </tr>
                <TR> 
                  <TD colSpan=3 ><FONT size=2><BR>
                    <INPUT class=button name=Submit22 onclick="modifyWTClient()" type=button value=查看借款资料>
                    </FONT></TD>
                  <TD colSpan=2 >&nbsp;</TD>
                  <TD  width="40%">&nbsp;</TD>
                </TR>

                </tbody> 
              </table>
            </TD>
          </TR>
          <% } %>

    <%  
    	String CustomUrl = "/common/ShowClientInfo.jsp?ClientID="+String.valueOf(sessionMng.m_lClientID);
    	System.out.println("------@@@@@@@@@@@@###########-------------->"+CustomUrl);
    %>
   	 <jsp:include page='<%=CustomUrl%>' />
                <TR> 
                  <TD colspan=9>
                   <!--INPUT class=button name="dcb" type=button onfocus="nextfield='xyb'" value="贷款调查表" onclick="Javascript:window.open('../../content/c220-c.jsp?ParentID=<%=loanID%>&control=view','','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes');"-->
					&nbsp;&nbsp;&nbsp;<input class=button name="financialDCB" type=button value="资产负债表"  onClick="Javascript:window.open('../../content/c201-c.jsp?control=view&lClientID=<%=cInfo.getClientID()%>&PageName=c211-v.jsp&isSM=<%=Constant.YesOrNo.NO%>&lIsReadOnly=<%=Constant.YesOrNo.YES%>','','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes');" onKeydown="if(event.keyCode==13) Javascript:window.open('../contractcontent/c201-c.jsp?control=view&lClientID=<%=cInfo.getClientID()%>&PageName=c211-v.jsp&isSM=<%=Constant.YesOrNo.NO%>&lIsReadOnly=<%=Constant.YesOrNo.NO%>','','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes');">
					&nbsp;&nbsp;&nbsp;<input class=button name="financialDCB2" type=button value="损益表"  onClick="Javascript:window.open('../../content/view/v003.jsp?&lClientID=<%=cInfo.getClientID()%>&lTypeId=12&strClientName=<%=java.net.URLEncoder.encode(cInfo.getName())%>','','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes');" onKeydown="if(event.keyCode==13) Javascript:window.open('../../content/view/v003.jsp?&lClientID=<%=cInfo.getClientID()%>&lTypeId=12&strClientName=<%=java.net.URLEncoder.encode(cInfo.getName())%>','','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes');">


					<!--现金流量表-->
					&nbsp;&nbsp;&nbsp;<input class=button name="financialDCB3" type=button value="现金流量表"  onClick="Javascript:window.open('../../content/view/v004.jsp?&lClientID=<%=cInfo.getClientID()%>&lTypeId=13&strClientName=<%=java.net.URLEncoder.encode(cInfo.getName())%>','','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes');" onKeydown="if(event.keyCode==13) Javascript:window.open('../../content/view/v004.jsp?&lClientID=<%=cInfo.getClientID()%>&lTypeId=13&strClientName=<%=java.net.URLEncoder.encode(cInfo.getName())%>','','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes');">
                </TD>
                 
                </TR>
             <HR>
          <TR>   
                  <TD colSpan=12 height=46 >
                    <TABLE width="100%">
                      <TBODY> 
          <TR> 
                   
            <TD height=29 width=60>&nbsp;</TD>
            <TD colSpan=4 height=29 align=left><U><FONT size=2> <A href="#" onclick="modifyLoanBasic()"> 
              <%=str1%></A></FONT></U></TD>
            <TD colSpan=3 height=29>&nbsp;</TD>
            <TD align=left height=29 width=264>&nbsp;</TD>
            <TD align=right height=29 width=1>&nbsp;</TD>
            <TD align=right height=29 width=10>&nbsp;</TD>
          </TR>
           <% if (loanType==OBConstant.LoanInstrType.ASSURE) { %>
				<TR> 
                  <TD height=44 width=1>&nbsp;</TD>
                  <TD height=44 width=116 nowrap><font size="2">担保金额:</font></TD>
                  <TD height=44 width=26> 
                    <DIV align=right> 
                      <table width="10%" border="0" height="30">
                        <tr> 
                          <td height="10"><font size=2><%=Constant.CurrencyType.getSymbol(sessionMng.m_lCurrencyID)%></font></td>
                        </tr>
                        <tr> 
                          <td height="11">&nbsp;</td>
                        </tr>
                      </table>
                    </DIV>
                  </TD>
                  <TD height=44 width=236><FONT size=2> 
                    <INPUT class=tar disabled name=textfield2822 size=18 value="<%=DataFormat.formatNumber(loanAmount,2)%>">
                    （大写） 
                    <INPUT class=box disabled name=textfield242233 size=25 value="<%=ChineseCurrency.showChinese( DataFormat.formatAmount(loanAmount) ,1)%>">
                    </FONT> </TD>
                  <TD height=44 width=89 nowrap><FONT size=2>担保期限:</FONT></TD>
                  <TD height=44 width=324> <FONT size=2> 
                    <INPUT class=box disabled name=textfield211422 size=8 value="<%=intervalNum%>">
                    月</FONT></TD>
                </TR>
				<TR> 
                  <TD height=24 width=1>&nbsp;</TD>
                    <TD colSpan=2 height=33 nowrap><FONT size=2>担保费率:</FONT></TD>
                  <TD  height=33><FONT size=2>
				  	<input  align="right" class=tar name="assureChargeRate" size=8 value="<%=DataFormat.formatRate(assureChargeRate)%>" onfocus="nextfield='assureChargeTypeID'" disabled >
                      % </FONT> </TD>
                    <TD height=44 width=89>&nbsp;</TD>
                    <TD height=44 width=324> <FONT size=2>&nbsp; </FONT></TD>
                </TR>
                <TR> 
                  <TD height=24 width=1>&nbsp;</TD>
                  <TD colSpan=2 height=33 nowrap><FONT size=2>担保费收取方式:</FONT></TD>
                  <TD  height=33><FONT size=2>
				  	<select class='box' name="assureChargeTypeID">
                  			<option value="<%=assureChargeTypeID%>"  selected  ><%=LOANConstant.ChargeRatePayType.getName(assureChargeTypeID)%> </option>                  			
                      </select>
				   </FONT> </TD>
                  <TD height=44 width=89 nowrap><FONT size=2>受益人:</FONT></TD>
                  <TD height=44 width=324> <FONT size=2> 
                    <INPUT class=box disabled name=textfield211422 size=8 value="<%=(beneficiary!=null)?beneficiary:""%>">
                    </FONT></TD>
                </TR>
				<TR> 
                  <TD height=24 width=1>&nbsp;</TD>
                  <TD colSpan=2 height=33 nowrap><FONT size=2>担保类型一:</FONT></TD>
                  <TD  height=33><FONT size=2>
				  	<select class='box' name="AssureType1">
                  			<option value="<%=assureTypeID1%>"  selected  ><%=LOANConstant.AssureType1.getName(assureTypeID1)%> </option>                  			
                      </select>
				   </FONT> </TD>
                  <TD height=44 width=89 nowrap><font size="2">担保类型二:</font></TD>
                  <TD height=44 width=324> <FONT size=2>
				  		<select class='box' name="AssureType2">
                  			<option value="<%=assureTypeID2%>"  selected  ><%=LOANConstant.AssureType2.getName(assureTypeID2)%> </option>                  			
                      </select>
				   </FONT></TD>
                </TR>
                <TR> 
                  <TD height=24 width=1>&nbsp;</TD>
                  <TD colSpan=2 height=33 nowrap> 
                    <P><FONT size=2>担保原因:</FONT></P>
                  </TD>
                  <TD height=33 colspan="3"><FONT size=2> 
                    <TEXTAREA class=box cols=65 disabled name=textarea><%=loanReason%></TEXTAREA>
                    </FONT></TD>
                </TR>
                <TR> 
                  <TD height=24 width=1>&nbsp;</TD>
                  <TD colSpan=2 height=33 nowrap> 
                    <P><FONT size=2>担保用途:</FONT></P>
                  </TD>
                  <TD height=33 colspan="3"><FONT size=2> 
                    <TEXTAREA class=box cols=65 disabled name=textarea><%=loanPurpose%></TEXTAREA>
                    </FONT></TD>
                </TR>
                <TR> 
                  <TD height=24 width=1>&nbsp;</TD>
                  <TD colSpan=2 height=33> 
                    <P><FONT size=2>备注:</FONT></P>
                  </TD>
                  <TD colSpan=3 height=33><FONT size=2> 
                    <TEXTAREA class=box cols=65 disabled name=textarea><%=other%></TEXTAREA>
                    </FONT></TD>
                </TR>
				<TR> 
                  <TD height=24 width=1>&nbsp;</TD>
                  <TD colSpan=4><SPAN style="FONT-SIZE: 10pt"> 
                    <INPUT class=button name=Submit423222222222222 onclick="modifyLoanBasic()" type=button value="修改担保基本资料">
                    </SPAN></TD>
                </TR>
           <% }else  if (loanType==OBConstant.LoanInstrType.LOAN_WT) { %>
                <!--
                <TR> 
                  <TD height=26 width=1>&nbsp;</TD>
                  <TD colSpan=3 height=26><FONT size=2><U><FONT size=3><FONT 
                  size=2> <a href="S263-2.jsp?lLoanID=10344&control=view&lClientID=5">其他借款资料</a></FONT></FONT></U></FONT></TD>
                  <TD height=26 width=89>&nbsp;</TD>
                  <TD height=26 width=324>&nbsp;</TD>
                </TR>
                -->
                <TR> 
                  <TD height=44 width=50>&nbsp;</TD>
                  <TD colSpan=1 height=44 width=380><FONT size=2>贷款金额:</FONT></TD>
                  <TD height=44 width=76> 
                    <DIV align=right> 
                      <table width="10%" border="0" height="30">
                        <tr> 
                          <td height="10"><font size=2>￥</font></td>
                        </tr>
                        <tr> 
                          <td height="11">&nbsp;</td>
                        </tr>
                      </table>
                    </DIV>
                  </TD>
                  <TD colspan=2 height=44 width=236><FONT size=2> 
                  
                    <INPUT class=tar disabled name=textfield2822 size=18 value="<%=DataFormat.formatNumber(loanAmount,2)%>">
                    （大写） 
                    <INPUT class=box disabled name=textfield242233 size=25 value="<%=ChineseCurrency.showChinese( DataFormat.formatAmount(loanAmount) ,1)%>">
                    </FONT>
                    </TD>
                  <TD height=44 width=89><FONT size=2>贷款利率:<br>手续费:<br>手续费收取方式:</FONT></TD>
                  <TD height=44 width=324><FONT size=2> 
                    <INPUT class=box disabled name=textfield2422332 size=10 value="<%=DataFormat.formatRate(interestRate)%>">
                    ％  <br>
                    <INPUT class=box disabled name=textfield2422333 size=10 value="<%=DataFormat.formatRate(chargeRate)%>">
                    ％</FONT><FONT size=2>&nbsp;</FONT><br>
					<select class='box' name="checkPayType"  onfocus="nextfield='startdate'"  disabled>
			 <%
              	long typeVal[]=LOANConstant.ChargeRatePayType.getAllCode();
              	String typeName="";
              	for ( int i=0;i<typeVal.length;i++ )
              	{
              		typeName=LOANConstant.ChargeRatePayType.getName(typeVal[i]);
              		if (typeVal[i]==chargeRateTypeID)
              		{
              %> 
                <option value="<%=typeVal[i]%>" selected ><%=typeName%></option>
              <%	
              		}
              	}
              %> 
                      </select>
					</TD>
                </TR>
                <TR> 
                  <TD height=24 width=1>&nbsp;</TD>
                  <TD colSpan=2 height=33><FONT size=2>贷款期限:</FONT></TD>
                  <TD colSpan=3 height=33><FONT size=2> 
                    <INPUT class=box disabled name=textfield2422334 size=10 value="<%=DataFormat.getDateString(applyStartDate)%>">
                    到 
                    <INPUT class=box disabled name=textfield2422335 size=10 value="<%=DataFormat.getDateString(applyEndDate)%>">
                    <BR>
                    <INPUT class=box disabled name=textfield211422 size=2 value="<%=intervalNum%>">
                    月</FONT></TD>
                </TR>
                <TR> 
                  <TD height=24 width=1>&nbsp;</TD>
                  <TD colSpan=2 height=33> 
                    <P><FONT size=2>贷款用途:</FONT></P>
                  </TD>
                  <TD height=33 width=236><FONT size=2> 
                    <TEXTAREA  cols=31 disabled name=textarea><%=loanPurpose%></TEXTAREA>
                    </FONT></TD>
                  <TD height=33 width=89>&nbsp;</TD>
                  <TD height=33 width=324>&nbsp;</TD>
                </TR>
                <TR> 
                  <TD height=24 width=1>&nbsp;</TD>
                  <TD colSpan=2 height=33> 
                    <P><FONT size=2>项目批准机关及文号:</FONT></P>
                  </TD>
                  <TD colSpan=3 height=33><FONT size=2> 
                    <TEXTAREA  cols=31 disabled name=textarea><%=other%></TEXTAREA>
                    </FONT></TD>
                </TR>
                <TR> 
                  <TD height=24 width=1>&nbsp;</TD>
                  <TD colSpan=4><SPAN style="FONT-SIZE: 10pt"> 
                    <INPUT class=button name=Submit423222222222222 onclick="modifyLoanBasic()" type=button value="查看贷款基本资料">
                    </SPAN></TD>
                </TR>
         <% }else{ %> 
			<tr>
            <TD height=29 width=1>&nbsp;</TD>
            <TD height=29 width=250><FONT size=2> <% if ( isyt ) {%> 贷款额度: <% }else{ %>   借款金额:<% } %></FONT></TD>
            <TD height=29 width=1>&nbsp;</TD>
            <TD align=right height=29 width=31><FONT size=2>￥</FONT></TD>
            <TD colSpan=2 height=29><FONT size=2> 
              <INPUT class=tar disabled name=textfield2822 size=16 value="<%=DataFormat.formatNumber(loanAmount,2)%>">
              </FONT></TD>
              
            <% if (isyt) { %>   
            <TD height=29 width=200 ><FONT size=2>财务公司承贷额度:</FONT></TD>
            <TD height=29 width=180 >￥ 
            <INPUT class=tar disabled name=saleamount size=16 value="<%=DataFormat.formatNumber(selfAmount,2)%>">
		    </td>    
		    <TD height=29 width=94><FONT size=2>借款期限:</FONT></TD>
            <TD height=29 width=94><FONT size=2> <INPUT class=box disabled name=textfield211422 size=2 value="<%=intervalNum%>">
              月</FONT></TD>   
              
			<% }else{ %>
            <TD height=29 width=200 ><FONT size=2>借款期限:</FONT></TD>
            <TD height=29 width=180 ><FONT size=2> <INPUT class=box disabled name=textfield211422 size=2 value="<%=intervalNum%>">
              月</FONT></TD>    
                 	
          	<TD colSpan=2 height=29></TD>
            <TD align=right height=29 width=1>&nbsp;</TD>
            <TD align=right height=29 width=10>&nbsp;</TD>
            <% } %>
          </TR>
          <TR> 
            <TD height=2 width=1>&nbsp;</TD>
            <TD colSpan=3 height=2><FONT size=2>借款原因:</FONT></TD>
            <TD colSpan=7 height=2><FONT size=2> 
              <TEXTAREA  cols=65 disabled name=textfield><%=loanReason%></TEXTAREA>
              </FONT></TD>
            <TD align=right height=2 width=10></TD>
          </TR>
          <TR> 
            <TD height=30 width=1>&nbsp;</TD>
            <TD colSpan=3 height=30><FONT size=2>借款用途:</FONT></TD>
            <TD colSpan=7 height=30><FONT size=2> 
              <TEXTAREA  cols=65 disabled name=textarea><%=loanPurpose%></TEXTAREA>
              </FONT></TD>
            <TD align=right height=30 width=10></TD>
          </TR>
          <TR> 
            <TD height=2 width=1>&nbsp;</TD>
            <TD colSpan=3 height=2><FONT size=2>还款来源及措施:</FONT></TD>
            <TD colSpan=7 height=2><FONT size=2> 
              <TEXTAREA  cols=65 disabled name=textarea2><%=other%></TEXTAREA>
              </FONT></TD>
            <TD align=right height=2 width=10>&nbsp;</TD>
           
          </TR>
          <TR> 
          	<td> </td>
            <td colspan=3 height=2><font size="2">借款单位情况简介:</font></td>
            <td colspan=7 height=2><font size=2> 
              <textarea  cols=65 disabled name=textarea2><%=(clientInfo==null)?"":clientInfo%></textarea>
              </font></td>
            <TD align=right height=2 width=10>&nbsp;</TD>
          </TR>
          <TR> 
            <TD>&nbsp;</TD>
            <TD colspan="5"><FONT size=2> 
              <INPUT class=button name=Submit423222222222222 onclick="modifyLoanBasic()" type=button value="查看贷款基本资料">
              </FONT>
             
              </TD>
			     </TR>
             <% } %>  
       
                      </TBODY> 
                    </TABLE>
                  </TD>
                </TR> 
          <TR> 
            <TD height=244 width=1>&nbsp;</TD>
            <TD colSpan=8 height=244> 
           
            
              <TABLE cellPadding=0 width=100%>
                <TBODY> 
                <TR > 
                  <TD colSpan=10 height=46 > 
                    <HR>
                  </TD>
                </TR>
                <TR> 
                  <TD colSpan=10 height=46 >
                    <TABLE width="100%">
                      <TBODY> 
					  <% if (loanType!=OBConstant.LoanInstrType.ASSURE) { %>
                      <TR> 
                        <TD colSpan=4 height=24><U><FONT size=2> <A href="#" onclick="modifyLoanProperty()">贷款性质/担保方式</A></FONT></U></TD>
                      </TR>
                      <TR> 
                        <TD colSpan=4> 
                          <TABLE height=36 width="100%">
                            <TBODY> 
                            <TR> 
                              <TD width="14%" height="44">循环贷款:</TD>
                              <TD width="18%" height="44">
                                <select class=box disabled name=select2>
                                  <option value="-1"></option>
									<option value="1" <% if (isCircle==1) {%> selected <% } %>>是</option>
									<option value="2" <% if (isCircle==2) {%> selected <% } %>>否</option>
                                </select>
                              </TD>
                              <TD width="16%" height="44">买卖方贷款:</TD>
                              <TD width="19%" height="44">
                                <SELECT class=box disabled name=select>
                              	<option value="-1"></option>
								<option value="1" <% if (isSaleBuy==1) {%> selected <% } %> >买</option>
								<option value="2" <% if (isSaleBuy==2) {%> selected <% } %> >卖</option>
								<option value="3" <% if (isSaleBuy==3) {%> selected <% } %> >不适用</option>
                                </SELECT>
                                </TD>
                              <TD width="16%" height="44">技改贷款:</TD>
                              <TD width="12%" height="44">
                                <SELECT class=box disabled name=select>
                					<option value="-1"></option>
                					<option value="1" <% if (isTechnical==1) {%> selected <% } %> >是</option>
                					<option value="2" <% if (isTechnical==2) {%> selected <% } %> >否</option>
                                </SELECT>
                              </TD>
                              <TD width="9%" height="44">&nbsp;</TD>
                            </TR>
							<%}%>
                            <TR> 
                              <TD colSpan=7 height="50"><font size="2"><%=str2%>
                                <input type="checkbox" disabled name="checkbox" value="checkbox" <% if (isCredit>0) {%> checked <% } %> >
                                信用 
								<% if (loanType==OBConstant.LoanInstrType.ASSURE) { %>
                                <input type="checkbox" disabled name="checkbox2" value="checkbox" <% if (isRecognizance>0) {%> checked <% } %> >
                                保证金
								<%}%>
                                <input type="checkbox" disabled name="checkbox2" value="checkbox" <% if (isAssure>0) {%> checked <% } %> >
                                保证 
                                <input type="checkbox" disabled name="checkbox3" value="checkbox" <% if (isImpawn>0) {%> checked <% } %>>
                                质押 
                                <input type="checkbox" disabled name="checkbox4" value="checkbox" <% if (isPledge>0) {%> checked <% } %>>
                                抵押</font></TD>
                            </TR>
                            <TR> 
                              <TD colSpan=7><FONT size=2> 
                                <INPUT class=button name=Submit2as22 onclick="modifyLoanProperty()" type=button value="<%=str3%>">
                                </FONT></TD>
                            </TR>
                            </TBODY> 
                          </TABLE>
                                         
                        </TD>
                      </TR>
                      <TR> 
                        <TD colSpan=4> 
                          <HR align=center SIZE=2 width="100%">
                        </TD>
                      </TR>
                      <TR> 
                        <TD colSpan=4 height=33><U><SPAN lang=ZH-CN 
                        style="FONT-SIZE: 10pt"><FONT size=2> <A href="#" onclick="modifyAssure()"><%=str4%></A></FONT></SPAN></U></TD>
                      </TR>
                      <TR> 
                        <TD colSpan=4> 
                          <TABLE align=center border=0 class=ItemList width="100%">
                            <TBODY> 
                            <TR align=center class="tableHeader"> 
                              <TD class=ItemTitle height=14 width="17%"> <FONT size=2>客户编号</FONT></TD>
                              <TD class=ItemTitle height=14 width="16%"> <FONT size=2>单位名称</FONT></TD>
                              <TD class=ItemTitle height=14 width="15%"><FONT size=2>担保方式</FONT></TD>
                              <TD class=ItemTitle height=14 width="14%"> <FONT size=2>联系人</FONT></TD>
                              <TD class=ItemTitle height=14 width="14%"> <FONT size=2>电话</FONT> </TD>
                              <TD class=ItemTitle height=14 width="13%"> <FONT size=2>担保金额</FONT> </TD>
                              <TD class=ItemTitle height=14 width="11%" align="center"><font size=2>担保比例(%)</font></TD>
                            </TR>
            <% 
          	if ( (assVector!=null)&&(assVector.size()>0) ){
          		int iCount=assVector.size();
          		OBAssureInfo assInfo=null;
          		long assaID=-1;
          		String asssCode="";
          		String asssName="";
          		long assTypeID=-1;
          		String asssContacter="";
          		String asssPhone="";
          		double assAmount=0;
          		double assRate=0;
          		for ( int i=0;i<iCount;i++ )
          		{
          			assInfo=(OBAssureInfo)assVector.get(i);
          			assaID=assInfo.getID();
          			asssCode=assInfo.getClientCode();
          			asssName=assInfo.getClientName();
          			assTypeID=assInfo.getAssureTypeID();
          			asssContacter=assInfo.getClientContacter();
          			asssPhone=assInfo.getClientPhone();
          			assAmount=assInfo.getAmount();
          			if ( assTypeID==LOANConstant.AssureType.ASSURE) hasIsAssure=true;
          			if ( assTypeID==LOANConstant.AssureType.PLEDGE) hasIsPledge=true;
          			if ( assTypeID==LOANConstant.AssureType.IMPAWN) hasIsImpawn=true;
          			if ( assTypeID==LOANConstant.AssureType.RECOGNIZANCE) 
					{
						hasIsRecognizance=true;
						recognizanceAmount = recognizanceAmount+assAmount;
					}
					if ( loanAmount==0 )
          			{
          				assRate=0;
          			}
          			else
          			{
          				assRate=assAmount/loanAmount*100;
          			}	
          			if ( asssContacter==null ) asssContacter="";
          			if( asssPhone==null ) asssPhone="";
          			assSumAmount=assSumAmount+assAmount;
          			
          			
          %>			 
                            <TR align=center> 
                              <TD class=ItemBody height=35 width="17%"><FONT size=2><%=asssCode%></FONT></TD>
                              <TD class=ItemBody height=35 width="16%"><FONT size=2><%=asssName%></FONT></TD>
                              <TD class=ItemBody height=23 width="15%"><FONT size=2> <%=LOANConstant.AssureType.getName(assTypeID)%> </FONT></TD>
                              <TD class=ItemBody height=35 width="14%"><FONT size=2><%=asssContacter%></FONT></TD>
                              <TD class=ItemBody height=35 width="14%"><FONT size=2><%=asssPhone%></FONT></TD>
                              <TD class=ItemBody height=35 width="13%"><FONT size=2><%=DataFormat.formatNumber(assAmount,2)%></FONT></TD>
                              <TD class=ItemBody height=35 width="11%"><FONT size=2><%=DataFormat.formatNumber(assRate,2)%></font></TD>
                            </TR>
          <% 	
          		}
          	} else {
          %>        
          		           <TR align=center> 
                              <TD class=ItemBody height=35 width="17%"><FONT size=2>&nbsp;</FONT></TD>
                              <TD class=ItemBody height=35 width="16%"><FONT size=2>&nbsp;</FONT></TD>
                              <TD class=ItemBody height=23 width="15%"><FONT size=2>&nbsp;</FONT></TD>
                              <TD class=ItemBody height=35 width="14%"><FONT size=2>&nbsp;</FONT></TD>
                              <TD class=ItemBody height=35 width="14%"><FONT size=2>&nbsp;</FONT></TD>
                              <TD class=ItemBody height=35 width="13%"><FONT size=2>&nbsp;</FONT></TD>
                              <TD class=ItemBody height=35 width="11%"></TD>
                            </TR>
          
          <% } %>             
                            
                            </TBODY> 
                          </TABLE>
                        </TD>
                      </TR>
                      
                      <TR> 
                        <TD width="18%"><FONT size=2><%=str6%></FONT></TD>
                        <TD width="4%"> 
                          <DIV align=right><FONT size=2>￥</FONT></DIV>
                        </TD>
						<%if (loanType==OBConstant.LoanInstrType.ASSURE){%>
                        <TD width="31%"><FONT size=2> <INPUT class=tar disabled name=textfield33 size=18 value="<%=DataFormat.formatNumber(assSumAmount,2)%>"></FONT></TD>
						<%}else{%>
						 <TD width="31%"><FONT size=2> <INPUT class=tar disabled name=textfield33 size=18 value="<%=DataFormat.formatNumber(loanAmount,2)%>"></FONT></TD>
						 <%}%>
                        <TD width="47%">&nbsp;</TD>
                      </TR>
                      <TR> 
                        <TD height=33 width="18%"><FONT size=2><U>其中</U></FONT></TD>
                        <TD height=33 width="4%">&nbsp;</TD>
                        <TD height=33 width="31%">&nbsp;</TD>
                        <TD height=33 width="47%">&nbsp;</TD>
                      </TR>
					  <%if (loanType==OBConstant.LoanInstrType.ASSURE){%>
					<TR> 
                        <TD width="18%"><font size="2">保证金总额: </font></TD>
                        <TD width="4%"> <FONT size=2><%=Constant.CurrencyType.getSymbol(sessionMng.m_lCurrencyID)%></FONT> </TD>
                        <% if ( recognizanceAmount==0 ) {%>
                        <TD width="31%"><FONT size=2> <INPUT class=tar disabled name=textfield224 size=18 value="0.00"></FONT></TD>
                        <% }else{ %>
                        <TD width="31%"><FONT size=2> <INPUT class=tar disabled name=textfield224 size=18 value="<%=DataFormat.formatNumber(recognizanceAmount,2)%>"></FONT></TD>
                        <% } %>
                        <% if (assSumAmount==0) { %>
                        <TD width="47%"><font size="2">所占比例: <input class=tar disabled name=textfield2242 size=10 value="0.00">  %</font></TD>
                        <% }else{ %>
                        <TD width="47%"><font size="2">所占比例: <input class=tar disabled name=textfield2242 size=10 value="<%=DataFormat.formatNumber(recognizanceAmount/assSumAmount*100,2)%>">  %</font></TD>
                        <% } %>
                      </TR>      
				<%}else{%>
                      <TR> 
                        <TD width="18%"><FONT size=2>有担保贷款总额: </FONT></TD>
                        <TD width="4%"> <FONT size=2>￥</FONT> </TD>
                        <% if ( assSumAmount==0 ) {%>
                        <TD width="31%"><FONT size=2> <INPUT class=tar disabled name=textfield224 size=18 value="0.00"></FONT></TD>
                        <% }else{ %>
                        <TD width="31%"><FONT size=2> <INPUT class=tar disabled name=textfield224 size=18 value="<%=DataFormat.formatNumber(assSumAmount,2)%>"></FONT></TD>
                        <% } %>
                        <% if (loanAmount==0) { %>
                        <TD width="47%"><font size="2">所占比例: <input class=tar disabled name=textfield2242 size=10 value="0.00">  %</font></TD>
                        <% }else{ %>
                        <TD width="47%"><font size="2">所占比例: <input class=tar disabled name=textfield2242 size=10 value="<%=DataFormat.formatNumber(assSumAmount/loanAmount*100,2)%>">  %</font></TD>
                        <% } %>
                      </TR>
                      <TR> 
                        <TD width="18%"><FONT size=2>信用贷款总额:</FONT></TD>
                        <TD width="4%"> <FONT size=2>￥</FONT>  </TD>
                        <% creditAmount=loanAmount-assSumAmount;
                        	if (creditAmount<0) creditAmount=0;
                        	if ( creditAmount==0 ){
                        %>	
                        <TD width="31%"><FONT size=2> <INPUT class=tar disabled name=textfield4 size=18 value="0.00"></FONT></TD>
                        <% }else{ %>
                        <TD width="31%"><FONT size=2> <INPUT class=tar disabled name=textfield4 size=18 value="<%=DataFormat.formatNumber(creditAmount,2)%>"></FONT></TD>
                        <% } %>
                        <% if (loanAmount==0){%>
                        <TD width="47%"><font size="2">所占比例: <input class=tar disabled name=textfield22422 size=10 value="0.00"> %</font></TD>
                        <% }else{ %>
                        <TD width="47%"><font size="2">所占比例: <input class=tar disabled name=textfield22422 size=10 value="<%=DataFormat.formatNumber(creditAmount/loanAmount*100,2)%>"> %</font></TD>
                        <% } %>
                      </TR>
					 <%}%>
                      <TR> 
                        <TD colSpan=3>&nbsp;</TD>
                        <TD width="47%">&nbsp;</TD>
                      </TR>
                      
                      <TR> 
                        <TD colSpan=3> 
                          <DIV align=right></DIV>
                          <FONT size=2> 
                          <INPUT class=button name=Submit2222 onclick="modifyAssure()" type="button" value="<%=str5%>">
                          </FONT></TD>
                        <TD width="47%">&nbsp;</TD>
                      </TR>
                      </TBODY> 
                    </TABLE>
                    
                    <hr>
                    <table width="100%" border="0">
                      <tr> 
                        <td width="17%"  nowrap>其他附件:</td>
                        <td width="83%"><iframe src="../../attach/AttachFrame.jsp?lID=<%=loanID%>&lTypeID=<%=LOANConstant.AttachParentType.LOAN%>&sCaption=<%=URLEncoder.encode("链接附件")%>&showOnly=true" width=600 height="100" scrolling="Auto" frameborder="0" name="iFrame" ></iframe>
                        </td>
                      </tr>
                    </table>
                    
                  </TD>
                </TR>
                </TBODY> 
              </TABLE>
            </TD>
			
            <TD height=244 width=1>&nbsp;</TD>
            <TD align=right height=244 width=10>&nbsp;</TD>
          </TR>
		   
          <TR> 
            <TD height=2 width=1>&nbsp;</TD>
            <TD colSpan=4 height=2><FONT size=2>&nbsp;</FONT><BR> </TD>
            <TD align=right colSpan=4 height=2><FONT size=2>&nbsp;</FONT></TD>
            <TD height=2 width=1>&nbsp;</TD>
            <TD align=right height=2 width=10>&nbsp;</TD>
          </TR>
          <TR> 
            <TD height=13 width=1>&nbsp;</TD>
            <TD colSpan=9 height=13> </TD>
            <TD align=right height=13 width=10><FONT size=2>&nbsp;</FONT></TD>
          </TR>
		  <% if (loanType!=OBConstant.LoanInstrType.ASSURE) { %>
          <TR> 
            <TD height=29 width=1>&nbsp;</TD>
            <TD align=left colSpan=3 height=29 nowrap><FONT size=2><U>原始计划详细</U></FONT></TD>
           
            <TD align=right height=29 width=1>&nbsp;</TD>
            <TD align=right colSpan=2 height=29 width=190>&nbsp;</TD>
            <TD align=right height=29 width=11>&nbsp;</TD>
            <TD align=right height=29 width=264>&nbsp;</TD>
            <TD align=right height=29 width=1>&nbsp;</TD>
            <TD align=right height=29 width=10>&nbsp;</TD>
          </TR>
		  <%}%>
          <TR> 
          <td colspan=12>
		  <hr>
		  <table><tr>
            <TD height=29 width=1>&nbsp;</TD>
			<TD align=left width=100 height=29><FONT size=2> &nbsp;
			<% if (loanType!=OBConstant.LoanInstrType.ASSURE) { %>
              <INPUT class=button name=Submit4 onclick="modifyPlan()"  type=button value=查看原始计划>
              </FONT>
			  <%}%></TD>
            <TD align=right height=29 width="120"><FONT size=2>录入人:<%=appInfo.getInputUserName()%></Font></TD>
            <TD  colSpan=3 width="300" align=center><FONT size=2>录入日期:<%=DataFormat.getDateString(appInfo.getInputDate())%></font></TD>
            <TD  height=29 width=11>&nbsp;</TD>
            <TD align=right height=29 width=110> 
              <DIV align=center><FONT size=2>状态: <%=OBConstant.LoanInstrStatus.getName(appInfo.getStatusID())%> </FONT></DIV>
            </TD>
            <TD align=right height=29 width=1>&nbsp;</TD>
            <TD align=right height=29 width=10>&nbsp;</TD>
          </TR>
          </table>
          </td>
          </tr>
          </TBODY> 
        </TABLE>
      </TD></TR></TBODY></TABLE>
<input type="hidden" name="lPageCount" value="1" >
<input type="hidden" name="lPageNo" value="1" >
<input type="hidden" name="lOrderParam" value="1" >
<input type="hidden" name="lDesc" value="1" >
<input type="hidden" name="control" value="view" >
<input type="hidden" name="lClientID" value="<%=cInfo.getClientID()%>" >
<input type="hidden" name="isdelete" value="0">
<input type="hidden" name="backurl" value="null" >
<input type="hidden" name="pages" value="S20.jsp">
<input type="hidden" name="lLoanID" value="<%=loanID%>">
<input type="hidden" name="lLoanType" value="<%=loanType%>">
<input type="hidden" name="type" value="<%=loanType%>">
<input type="hidden" name="txtAction" value="<%=action%>">
</Form>

<script>
function addNew()
{
   if(confirm("是否新增？"))
   {
		frm.action="<%=Env.EBANK_URL%>loan/loanapply/l002-c.jsp";
		frm.txtAction.value="addNew";	   		
   		showSending();
		frm.submit();
   }
}
function cancelLoan()
{
   if(confirm("是否取消？"))
   {
   		frm.action="<%=Env.EBANK_URL%>loan/loanapply/l033-c.jsp"; 
   		showSending();
		frm.submit();
   }
}

function modifyLoan()
{
  		frm.action="<%=Env.EBANK_URL%>loan/loanapply/l036-c.jsp"; 
   		showSending();
		frm.submit();
}


function modifyWTClient()
{
	window.open('<%=Env.EBANK_URL%>loan/loanapply/l034-c.jsp?lLoanType=<%=loanType%>&lLoanID=<%=appInfo.getID()%>&lClientID=<%=wtClientInfo==null?-1:wtClientInfo.getClientID()%>&txtAction=check&statusID=<%=appInfo.getStatusID()%>&mType=wtclient', '查看客户基本资料', 'toolbar=yes,menubar=yes,resizable=yes,location=yes,width=800,height=600,scrollbars=yes', false)
}
function modifyClient()
{
	window.open('<%=Env.EBANK_URL%>loan/loanapply/l034-c.jsp?lLoanType=<%=loanType%>&lLoanID=<%=appInfo.getID()%>&lClientID=<%=cInfo.getClientID()%>&txtAction=check&statusID=<%=appInfo.getStatusID()%>&mType=client', '查看客户基本资料', 'toolbar=yes,menubar=yes,resizable=yes,location=yes,width=800,height=600,scrollbars=yes', false)
}
function modifyLoanBasic()
{
	window.open('<%=Env.EBANK_URL%>loan/loanapply/l034-c.jsp?lLoanType=<%=loanType%>&lLoanID=<%=appInfo.getID()%>&txtAction=check&statusID=<%=appInfo.getStatusID()%>&mType=loanBasic', '查看贷款基本资料', 'toolbar=yes,menubar=yes,resizable=yes,location=yes,width=800,height=600,scrollbars=yes', false)
}
function modifyLoanProperty()
{
	window.open('<%=Env.EBANK_URL%>loan/loanapply/l034-c.jsp?lLoanType=<%=loanType%>&lLoanID=<%=appInfo.getID()%>&txtAction=check&statusID=<%=appInfo.getStatusID()%>&mType=loanProperty', '查看贷款属性资料', 'toolbar=yes,menubar=yes,resizable=yes,location=yes,width=800,height=600,scrollbars=yes', false)
}
function modifyAssure()
{
	window.open('<%=Env.EBANK_URL%>loan/loanapply/l034-c.jsp?lLoanType=<%=loanType%>&lLoanID=<%=appInfo.getID()%>&txtAction=check&statusID=<%=appInfo.getStatusID()%>&mType=assure', '查看贷款保证资料', 'toolbar=yes,menubar=yes,resizable=yes,location=yes,width=800,height=600,scrollbars=yes', false)
}
function modifyPlan()
{
	window.open('<%=Env.EBANK_URL%>loan/loanapply/l034-c.jsp?lLoanType=<%=loanType%>&lLoanID=<%=appInfo.getID()%>&txtAction=check&statusID=<%=appInfo.getStatusID()%>&mType=plan', '查看贷款计划资料', 'toolbar=yes,menubar=yes,resizable=yes,location=yes,width=800,height=600,scrollbars=yes', false)
}

function commitApply()
{
   if(confirm("是否提交？"))
   {
   		//监测金额是否为0
   		<% if (loanAmount==0){%>
   		alert("金额为0，请修改贷款基本资料");
   		return false;
   		<%}%>
   		
   		//监测贷款属性资料
   		<% if ( (isCredit<1)&&(isAssure<1)&&(isPledge<1)&&(isImpawn<1) ){%>
   		alert("还没有选择贷款性质/担保方式，请修改贷款性质/担保方式");
   		return false;
   		<%}%>
   		
   		//监测保证信息
   		<% if ( (isAssure==1)&& !(hasIsAssure) ) {%>
		alert("还没有担保纪录!请修改贷款担保资料")
		return false;
		<% } else if ( (isPledge==1)&& !(hasIsPledge) ) {%>
		alert("还没有抵押担保纪录!请修改贷款担保资料")
		return false;
		<% } else if ( (isImpawn==1) && !(hasIsImpawn) ) {%>
		alert("还没有质押担保纪录!请修改贷款担保资料")
		return false;
		<% } %>
		
		//监测计划信息
		<% 	if ( payAmount!=loanAmount )
	{
		if ( payAmount!=repayAmount )
		{
%>		
			alert("提示: 放款合计数不等于申请金额:￥<%=DataFormat.formatAmount(loanAmount)%>！ 放款与还款合计数不相等！");
<%		}else{   %>
			alert("提示: 放款合计数不等于申请金额:￥<%=DataFormat.formatAmount(loanAmount)%>！ ");
<% 		}		%>
		if (!confirm("是否还要提交?"))
		{
			return false;
		}
<%		
	}else if (payAmount!=repayAmount){
%>				
		alert("提示: 还款合计数不等于申请金额:￥<%=DataFormat.formatAmount(loanAmount)%>！放款与还款合计数不相等！");	
		if (!confirm("是否还要提交?"))
		{
			return false;
		}
<%  }  %>		
   		frm.action="<%=Env.EBANK_URL%>loan/loanapply/l038-c.jsp";
	    showSending();
		frm.submit();
   }
}
function goBack()
{
   <% if (action.equals("")){ %>
   frm.action="<%=Env.EBANK_URL%>loan/loanapply/l002-c.jsp";
   <% } else { %>	
   frm.action="../query/q003-c.jsp";
   <% } %>
   showSending();
   frm.submit();
}

function frmPrint()  //打印 action on the Form : theform
{
     if (confirm("是否打印？"))
     {
         window.open('<%=Env.EBANK_URL%>loan/loanapply/l030-c.jsp?lLoanID=<%=loanID%>','',"width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes");
     }
}


</script>

<%
	OBHtml.showOBHomeEnd(out);
	}
	catch( Exception ie )
	{
		//OBHtmlCom.showExceptionMessage(out,sessionMng,ie,request,response,"客户管理", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return; 
	}
%>
<%@ include file="/common/SignValidate.inc" %>