<!-- 
/**
 * 页面名称 ：l006-v.jsp
 * 页面功能 : 贷款基本性质资料
 * 修改记录：
 * 2007-03-09 mzh_fu 解决了下拉列表框回车焦点跳转的问题
 * 2007-03-15 mzh_fu 1.解决此页面的下一个页面点“上一步”时出现的JS错误；2.将所有":"改成"："；3.解决了借款利率处对齐不整的问题
 */
 -->

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
	java.util.*,
	java.net.URLEncoder,
	com.iss.itreasury.loan.loancommonsetting.dataentity.*,
	com.iss.itreasury.loan.util.*,
	com.iss.itreasury.ebank.util.*,
	com.iss.itreasury.ebank.obloanapply.dataentity.*,
	com.iss.itreasury.util.*,
	com.iss.itreasury.loan.util.*"
%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>

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
		
		String type=(String)request.getAttribute("lLoanType");
    	long loanType=Long.valueOf(type).longValue();
		System.out.println("^^^^^^^^^^^loanType=======^^^^^^^^^^^^^"+loanType);
    	String loanTypeName=OBConstant.LoanInstrType.getName(loanType);
		long[] loanTypeid={loanType};
    	boolean iswt=false;
    	boolean isyt=false;
    	boolean isCheck=false;
    	String action="";
    	long statusID=-1;
    	String tmp="";
		String panduan="0";
		String strProperty = "";
    
    	tmp=(String)request.getAttribute("txtAction");
    	if ( (tmp!=null)&&(tmp.length()>0) )
    		action=tmp;
		tmp=(String)request.getAttribute("nStatusID");
    	if ( (tmp!=null)&&(tmp.length()>0) )
    		statusID=Long.valueOf(tmp).longValue();
    
    	OBLoanApplyInfo lInfo= (OBLoanApplyInfo) request.getAttribute("LoanApplyInfo");
    	
		long wtClientID=lInfo.getConsignClientID();			//委托客户
		//String loanCode=lInfo.getApplyCode();				//申请号
		String loanCode=lInfo.getInstructionNo();
		long loanID=lInfo.getID();							//申请ID
		long clientID=lInfo.getBorrowClientID();			//客户号
	
		double loanAmount=lInfo.getLoanAmount();			//申请金额
		String loanReason=lInfo.getLoanReason();			//借款原因
		String loanPurpose=lInfo.getLoanPurpose();			//借款目的
		String other=lInfo.getOther();					//其它
		double chargeRate=lInfo.getChargeRate();			//手续费率
		long   intervalNum=lInfo.getIntervalNum();			//期限
		Timestamp startDate=lInfo.getStartDate();			//开始日期
		Timestamp endDate=lInfo.getEndDate();				//结束日期
		double selfAmount=lInfo.getSelfAmount();			//财务公司成带劲金额
		//double selfScale = lInfo.getSelfScale();			//财务公司承贷比例
		double selfScale=0;//UNDO
		double interestRate=lInfo.getInterestRate();		//利息率
		String clientInfo=lInfo.getClientInfo();
		long chargeRateTypeID  = lInfo.getChargeRateTypeID();//收取方式
		double assureChargeRate = lInfo.getAssureChargeRate();		//担保费率
		long assureChargeTypeID = lInfo.getAssureChargeTypeID();	//担保费收取方式
		long assureTypeID1 = lInfo.getAssureTypeID1();				//担保类型1
		long assureTypeID2 = lInfo.getAssureTypeID2();				//担保类型2
		String beneficiary = lInfo.getBeneficiary();				//受益人
	
		/*对空值进行处理*/
		if ( clientInfo==null )
			clientInfo="";
		if ( loanReason==null )
			loanReason="";
		if ( loanPurpose==null )
			loanPurpose="";
		if ( other==null )
			other="";
		if ( beneficiary == null )
			beneficiary="";
		if ( intervalNum<1){
            	intervalNum=12;
           
		}	
 
 	/*对日期形数据和金额数据进行特殊处理*/
 	String sstartDate="";
 	String sendDate="";
 	String sloanAmount="";
 	String sselfAmount="";
	String sScale = "";
	String str1="借款";
	String str2="还款来源及措施：";

 	
 	if ( startDate!=null )
 		sstartDate=DataFormat.getDateString(startDate);
 	if ( endDate!=null )
 		sendDate=DataFormat.getDateString(endDate);
 	sloanAmount=DataFormat.formatNumber(loanAmount,2);
 	sselfAmount=DataFormat.formatNumber(selfAmount,2);
	sScale =DataFormat.formatNumber(selfScale,6);
	
    if ( loanType==OBConstant.LoanInstrType.LOAN_WT){
    	iswt=true;
		panduan="1";
	}else if(loanType==OBConstant.LoanInstrType.ASSURE){
		str1="担保";
		str2="备注：";
		panduan="2";
	}
	isyt=false;
        //显示文件头
        if ( (action.equals("modify"))||(action.equals("check")) )
        	OBHtml.showOBHomeHead(out,sessionMng,"[贷款基本资料]",Constant.YesOrNo.NO);
        else	
        	OBHtml.showOBHomeHead(out,sessionMng,"[贷款基本资料]",Constant.YesOrNo.YES);
        
        if ( action.equals("check") ){
       		isCheck=true;	
			strProperty = " disabled ";
		}
%>	

<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="javascript" src="/itreasury/js/jquery-1.3.2.js"></script>
<safety:resources />
<form name="frm" method="post"><input type=hidden name=pand value=<%=panduan%>>
<TABLE border=0 class=top height=254 width=730>
<TR class="tableHeader">
    <TD class=FormTitle height=29><B><%=loanTypeName%>－新增</B></TD>
</TR>
<TR>
    <TD>
    <% if (iswt){%>

      <TABLE align=center border=0 width=730>
      <TR>
          <TD height=28 >&nbsp;</TD>
          <TD colSpan=3 height=28>申请指令编号：<%=loanCode%></TD>
          <TD colSpan=3 height=28>贷款子类名称：
	<%LOANConstant.SubLoanType.showList(out,"subLoanType",-1,false,false," onfocus=\"nextfield='loanamount'\"",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,loanTypeid,null );%>
			
			</TD>
         
	    </TR>
		<tr>
          <TD height=29 >&nbsp;</TD>
          <TD colSpan=5 height=29>
            <HR>
          </TD>
          <TD align=right colSpan=5 height=29 >&nbsp;</TD>
        </TR>
        <TR>
          <TD height=29 >&nbsp;</TD>
          <TD colSpan=5 height=29><U>借款申请详情</U></TD>
          <TD align=right colSpan=5 height=29 >&nbsp;</TD></TR>
        <TR>
          <TD height=26 >&nbsp;</TD>
          <TD height=26 ><font color='#FF0000'>* </font>借款金额：</TD>
          <TD height=26 align=right>￥</TD>
          <TD height=26 >
          <% if ( isCheck ) { %>
          <input name="loanamount" size="25" value="<%=sloanAmount%>" class=box disabled><br>
          <input name="daxie" size="25" value="<%=ChineseCurrency.showChinese(DataFormat.formatAmount(loanAmount),1)%>" class=box disabled>
          <% }else{ %>
  		  	<script language="javascript">
        		createAmountCtrl("frm","loanamount","<%=sloanAmount%>","bankinterest","daxie");
          	</script>	<br>
          	<input type="text" name="daxie" size="20" value="" class=box disabled>（大写）
          <% } %>			
		  </TD>
          <TD height=26 valign="baseline"  align="right"><font color='#FF0000'>* </font>借款利率：</TD>
          <TD colSpan=6 height=26> 
          	<% if ( interestRate==0 ) {%>
          	  <input class=box name="bankinterest" size=8 value="" onfocus="nextfield='chargerate'" <%if (isCheck) {%>disabled<%}%> >
          	<% }else{ %>
              <input class=box name="bankinterest" size=8 value="<%=DataFormat.formatRate(interestRate)%>" onfocus="nextfield='chargerate'" <%if (isCheck) {%>disabled<%}%> >
            <% } %>  
              ％ 手续费：
             <% if ( chargeRate==0 ){ %>
             <input class=box name="chargerate" size=8  value="" onfocus="nextfield='checkPayType'" <%if (isCheck) {%>disabled<%}%> >
              ％
             <%  }else{ %>
             <input class=box name="chargerate" size=8  value="<%=DataFormat.formatRate(chargeRate)%>" onfocus="nextfield='checkPayType'" <%if (isCheck) {%>disabled<%}%> >
              ％
             <% } %>
              <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              手续费收取方式： 
              <% if (!isCheck){ %>
                      <select class='box' name="checkPayType"  onfocus="nextfield='startdate'" >
                        <option value=-1>　</option>
			 <%
              	long typeVal[]=LOANConstant.ChargeRatePayType.getAllCode();
              	String typeName="";
              	for ( int i=0;i<typeVal.length;i++ )
              	{
              		typeName=LOANConstant.ChargeRatePayType.getName(typeVal[i]);
              %> 
                <option value="<%=typeVal[i]%>" <% if (typeVal[i]==chargeRateTypeID ) {%> selected <% } %>><%=typeName%></option>
              <%	
              	}
              %> 
                      </select>
                   <%}else{%>
                  <select class='box' name="checkPayType"  onfocus="nextfield='startdate'" >
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
                   <%}%>
              
          </TD>
		</TR>
		  
        <TR>
          <TD height=24 >&nbsp;</TD>
          <TD colSpan=2 height=33>
            <P><font color='#FF0000'>* </font>借款日期：<BR><BR><font color='#FF0000'>* </font>期限：</P>
          </TD>
          <TD colSpan=3 height=33>
	          <fs_c:calendar 
	          	    name="startdate"
		          	value="" 
		          	properties="nextfield='enddate';" 
		          	size="20"/>
		          				          	  <script>
	          		$('#startdate').val('<%=sstartDate%>');
	          	</script>
		          	<script>
		          		$('#startDate').blur(function (){
		          			writed();
		          		});
		          	</script>
		          	 <%if (isCheck) {%>
		          	 	<script>
		          	 		$('#startDate').attr('readonly','true');
		          	 	</script>
		          	 <%}%>
		          	 <!-- 
              <input class=box onBlur="writed()" name="startdate" value="<%=sstartDate%>" <%if (isCheck) {%>disabled<%}%> onfocus="nextfield='enddate'">
              <A href="javascript:show_calendar('frm.startdate');" onMouseOut="window.status='';return true;" onMouseOver="window.status='Date Picker';return true;"><IMG border="0" height="16" src="/websett/image/calendar.gif" width="17"></A>  -->
              到 
              <INPUT class=box name="enddate" value="<%=sendDate%>" <%if (isCheck) {%>disabled<%}%> onfocus="nextfield='intervalnum'" readonly>
              <!--A href="javascript:show_calendar('frm.enddate');" onMouseOut="window.status='';return true;" onMouseOver="window.status='Date Picker';return true;"--><IMG border="0" height="16" src="/websett/image/calendar.gif" width="17">
              <br>
              <INPUT class=box onblur="writed()" name="intervalnum" size=3 maxlength="3" value="<%=intervalNum%>" onfocus="nextfield='loanpurpose'" <%if (isCheck) {%>disabled<%}%> >
              月
          </TD>
          <TD align=right colSpan=5 height=24 >&nbsp;</TD></TR>
        <TR>
          <TD height=24 >&nbsp;</TD>
          <TD colSpan=2 height=33><font color='#FF0000'>* </font>借款用途：</TD>
          <TD height=33 ><TEXTAREA   cols=30 name="loanpurpose"  onfocus="nextfield='wjh'" <%if (isCheck) {%>disabled<%}%> onchange="if(this.value.length>200) this.value=this.value.substr(0,200)" ><%=loanPurpose%></TEXTAREA> </TD>
          <TD height=33 >&nbsp;</TD>
          <TD height=33 >&nbsp;</TD>
          <TD align=right colSpan=5 height=24 >&nbsp;</TD></TR>
        <TR>
          <TD height=24 >&nbsp;</TD>
          <TD colSpan=2 height=33>
            <P>项目批准机关及文号：</P></TD>
          <TD height=33 ><TEXTAREA  cols=30 name="wjh" onfocus="nextfield='subnext'" <%if (isCheck) {%>disabled<%}%> onchange="if(this.value.length>200) this.value=this.value.substr(0,200)" ><%=other%></TEXTAREA> </TD>
          <TD height=33 >&nbsp;</TD>
          <TD height=33 >&nbsp;</TD>
          <TD align=right colSpan=5 height=24 >&nbsp;</TD></TR>
        <TR>
          <TD height=17 >&nbsp;</TD>
          <TD colSpan=5 height=17>
            <HR>
          </TD>
          <TD align=right colSpan=5 height=17 >&nbsp;</TD>
        </TR>
        <TR>
          <TD height=41 >&nbsp;</TD>
          <TD colSpan=3 height=41></TD>
          <TD height=41 ></TD>
          <TD align=right colSpan=6 height=41>
             <% if (action.equals("modify")) {%>
					<INPUT class=button name="xybClose" onclick="confirmClose()" type="button" value=" 关 闭 "> 
			<%  	if (statusID==OBConstant.LoanInstrStatus.SAVE){ %>
					<INPUT class=button name="subnext"  onclick="confirmSave(frm)" type="button" value=" 完 成 " onKeyDown="if(event.keyCode==13) confirmSave(frm);"> 
			<%		}else if(statusID==OBConstant.LoanInstrStatus.SUBMIT) {    %>
					<INPUT class=button name="subnext"  onclick="confirmSave(frm)" type="button" value=" 修改并提交 " onKeyDown="if(event.keyCode==13) confirmSave(frm);"> 
			<%		}else if(statusID==OBConstant.LoanInstrStatus.ACCEPT) {    %>
					<INPUT class=button name="subnext"  onclick="confirmSave(frm)" type="button" value=" 修改并提交 " onKeyDown="if(event.keyCode==13) confirmSave(frm);"> 		
			<%      }         %>
			<% }else if (action.equals("check") ) { %>
					<INPUT class=button name="xybClose" onclick="confirmClose()" type="button" value=" 关 闭 "> 		
			<% }else { %>
            <INPUT class=button name="subnext" onclick="confirmSave(frm)" type="button" value=" 下一步 " onKeyDown="if(event.keyCode==13) confirmSave(frm);">
            <% } %>
          </TD>
        </TR>
    </TABLE>
    <% }else{ %>
    <TABLE cellPadding=0 height=17 width=100%>
          <TR >
            <TD height=36 >&nbsp; </TD>
            <TD height=36 > 申请指令编号：<%=loanCode%></TD>
           <TD  height=28>贷款子类名称：  			<%LOANConstant.SubLoanType.showList(out,"subLoanType",-1,false,false,"onfocus=\"nextfield='loanamount'\"",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,loanTypeid,null );%>
			</TD>
          </TR>
       </TABLE>
       <TABLE cellPadding=0 height=50 >
          <TR > 
            <TD colSpan=7 height=66 vAlign=bottom> 
                <HR align=center SIZE=2 width="100%">
                  <P><U><%=str1%>申请详情</U></P>
            </TD>
          </TR>
          <TR> 
            <TD ><font color='#FF0000'>* </font><%=str1%>金额：
            </TD>
            <TD align="right">￥</TD>
            <TD > 
<%
			if (isyt)
			{
%>
<%
				if ( isCheck )
				{
%>
					<input name="loanamount" size="25" value="<%=sloanAmount%>" class=box disabled>
<%
				}
				else
				{
%>
					<script language="javascript">
						createAmountCtrl("frm","loanamount","<%=sloanAmount%>","intervalnum","");
					</script>
<%
				}
			}
			else
			{
				if (isCheck)
				{
%>
					<input name="loanamount" size="25" value="<%=sloanAmount%>" class=box disabled>
<%
				}
				else
				{
%>
					<script language="javascript">
						createAmountCtrl("frm","loanamount","<%=sloanAmount%>","intervalnum","");
					</script>
<%
				}
			}
%>
				</TD>
				<TD ><font color='#FF0000'>*</font> <%=str1%>期限：</TD>
				<TD >
<%
			if (isyt)
			{
%>
					<INPUT class=box name="intervalnum" size=6 maxlength="3" value="<%=intervalNum%>" onfocus="nextfield='sCale'" <%if (isCheck) {%>disabled<%}%> >月	
<%
			}
			else
			{
%>
					<INPUT class=box name="intervalnum" size=6 maxlength="3" value="<%=intervalNum%>" onfocus="nextfield='loanreason'" <%if (isCheck) {%>disabled<%}%> >月	
<%
			}
%>
					</TD>
				<TD ></TD>
				<TD ></TD>
          </TR>
<%
			if (isyt)
			{
%>
				<tr>
					<TD ><font color='#FF0000'>&nbsp;</font> 财务公司承贷额度：</TD>
					<TD >￥</TD>
					<TD >
						<input name="saleamount" size="18" value="<%=sselfAmount%>" class=tar readonly>
					</TD>
					<TD ><font color='#FF0000'>*</font> 承贷比例：</TD>
					<TD >
	<%
				String strReadonly = "";
				if (isCheck)
				{
					strReadonly = "readonly";
				}
	%>
						<INPUT class=box name="sCale" size=6 maxlength="8" value="<%=sScale%>" onblur="changeScale(frm)" onfocus="nextfield='loanreason'" <%=strReadonly%>> % 
					</TD>
				</tr>
<%
			}
%>

 <%if(loanType==OBConstant.LoanInstrType.ASSURE){%>
			<tr>
					
            <TD width="170"><font color='#FF0000'>* </font> <FONT size=2>担保费率：</FONT></TD>
					<TD width="82"></TD>
					<TD width="180">
					<input  class=tar  name="assureChargeRate" size=8 value="<%=DataFormat.formatRate(assureChargeRate)%>" onblur="formatChargeRate('frm','assureChargeRate');" onfocus="nextfield='assureChargeTypeID'" <%if (isCheck) {%>disabled<%}%> >
              % </TD>
					
            <TD width="100">&nbsp;</TD>
					
            <TD width="150">&nbsp; </TD>
				</tr>

						<tr>
					
            <TD width="170"><font color='#FF0000'>* </font> <FONT size=2>担保费收取方式：</FONT></TD>
					
            <TD width="82">&nbsp;</TD>
					<TD width="180">
						<%
							 String strPaytype = strProperty + " onfocus=nextfield='beneficiary'; ";
							LOANConstant.ChargeRatePayType.showList(out,"assureChargeTypeID",0,assureChargeTypeID,false,false,strPaytype,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
						%>
					</TD>
					
            <TD width="100"><FONT size=2>受益人：</FONT></TD>
					<TD width="150">
						<INPUT class=box name="beneficiary" size=15 maxlength="25" value="<%=beneficiary%>"  <%if (isCheck) {%>disabled<%}%> onfocus="nextfield='assureTypeID1'"> 
					</TD>
				</tr>
				<tr>
					
            <TD width="170"><font color='#FF0000'>* </font> <FONT size=2>担保类型一：</FONT></TD>
					<TD width="82"></TD>
					<TD width="180">
						<%
							String strProperty1 = strProperty + "onfocus=nextfield='assureTypeID2'; onchange='selectChange(this, frm.assureTypeID2, arrItems1, arrItemsGrp1,"+assureTypeID2+");' ";
							LOANConstant.AssureType1.showList(out,"assureTypeID1",0,assureTypeID1,false,true,strProperty1,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
						%>					
            </TD>
					
            <TD width="100"><font color='#FF0000'>*</font> <FONT size=2>担保类型二：</FONT></TD>
					
            <TD width="150">
				<% if( assureTypeID2<0 ){ %>
					<SELECT class='box' id=assureTypeID2 name=assureTypeID2 onfocus="nextfield='loanreason'"></SELECT>
				<%}else {
							String strProperty2 = strProperty + "onfocus=nextfield='loanreason'; ";
							LOANConstant.AssureType2.showList(out,"assureTypeID2",(int)assureTypeID1,assureTypeID2,false,true,strProperty2,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);								
				}%>
			</TD>
				</tr>

<%}%>

          <TR> 
            <TD > <font color='#FF0000'>* </font><%=str1%>原因：</TD>
            <TD >&nbsp;</TD>
            <TD colSpan=5 height=50> <TEXTAREA   cols=65 name="loanreason"  onfocus="nextfield='loanpurpose'" <%if (isCheck) {%>disabled<%}%> onchange="if(this.value.length>200) this.value=this.value.substr(0,200)" ><%=loanReason%></TEXTAREA></TD>
          </TR>
          <TR > 
            <TD ><font color='#FF0000'>* </font><%=str1%>用途：</TD>
            <TD >&nbsp;</TD>
            <TD colSpan=5 height=50> <TEXTAREA   cols=65 name="loanpurpose"  onfocus="nextfield='hkly'" <%if (isCheck) {%>disabled<%}%> onchange="if(this.value.length>200) this.value=this.value.substr(0,200)" ><%=loanPurpose%></TEXTAREA></TD>
          </TR>
          <TR > 
            <TD height=50 ><%if(loanType!=OBConstant.LoanInstrType.ASSURE){%> <font color='#FF0000'>* </font><%}%><%=str2%></TD>
            <TD height=50 >&nbsp;            </TD>
            <TD colSpan=5 height=50 > <TEXTAREA  cols=65 name="hkly"  onfocus="nextfield='clientinfo'" <%if (isCheck) {%>disabled<%}%> onchange="if(this.value.length>200) this.value=this.value.substr(0,200)" ><%=other%></TEXTAREA></TD>
          </TR>
		  <%if(loanType!=OBConstant.LoanInstrType.ASSURE){%>
          <TR > 
            <td height=50 colspan=2> <font color='#FF0000'>* </font>借款单位情况简介：</td>
            
            <td colspan=5 height=50 > <textarea   cols=65 name="clientinfo" onfocus="nextfield='subnext'" <%if (isCheck) {%>disabled<%}%> onchange="if(this.value.length>200) this.value=this.value.substr(0,200)" ><%=clientInfo%></textarea></td>
          </TR>
		  <%}%>
          <TR > 
            <TD colSpan=7 height=40 > 
              <HR align=center SIZE=2 width="100%">
            </TD>
          </TR>
          <TR > 
            <TD colSpan=7 align="right"> 
             <% if (action.equals("modify")) {%>
					<INPUT class=button name="xybClose" onclick="confirmClose()" type="button" value=" 关 闭 "> 
			<%  	if (statusID==OBConstant.LoanInstrStatus.SAVE){ %>
					<INPUT class=button name="subnext"  onclick="confirmSave(frm)" type="button" value=" 完 成 "> 
			<%		}else if(statusID==OBConstant.LoanInstrStatus.SUBMIT) {    %>
					<INPUT class=button name="subnext"  onclick="confirmSave(frm)" type="button" value=" 修改并提交 "> 
			<%		}else if(statusID==OBConstant.LoanInstrStatus.ACCEPT) {    %>
					<INPUT class=button name="subnext"  onclick="confirmSave(frm)" type="button" value=" 修改并提交 "> 		
			<%      }         %>	
			<% }else if (action.equals("check") ) { %>
					<INPUT class=button name="xybClose" onclick="confirmClose()" type="button" value=" 关 闭 "> 	
			<% }else { %>
            <INPUT class=button name="subnext" onclick="confirmSave(frm)" type="button" value=" 下一步 " onKeyDown="if(event.keyCode==13) confirmSave(frm);">
            <% } %>
            </TD>
          </TR>
        </TABLE>
	<%}%>
    
    </TD>
</TR>
</TABLE>
<input type="hidden" name="sApplyCode" value="<%=loanCode%>">
<input type=hidden name="lLoanType" value="<%=type%>">
<input type="hidden" name="lLoanID" value="<%=loanID%>">
<input type="hidden" name="lClientID" value="<%=clientID%>">
<input type="hidden" name="wtClientID" value="<%=wtClientID%>">
<input type="hidden" name="txtAction" value="<%=action%>">
</form>

<script language="javascript">
	var arrItems1 = new Array();
	var arrItemsGrp1 = new Array();

	arrItems1[0] = "";
	arrItemsGrp1[0] = -1;
	arrItems1[1] = "贷款担保";
	arrItemsGrp1[1] = 1;
	arrItems1[2] = "贸易项下的国内担保";
	arrItemsGrp1[2] = 1;
	arrItems1[3] = "贸易项下的国外担保";
	arrItemsGrp1[3] = 1;	
	arrItems1[4] = "招投标担保";
	arrItemsGrp1[4] = 2;
	arrItems1[5] = "履约担保";
	arrItemsGrp1[5] = 2;
	arrItems1[6] = "质保";
	arrItemsGrp1[6] = 2;
	arrItems1[7] = "预付款担保";
	arrItemsGrp1[7] = 2;
	arrItems1[8] = "其他";
	arrItemsGrp1[8] = 0;

function selectChange(control, controlToPopulate,ItemArray, GroupArray, selectValue)
{
  var myEle ;
  var x ;
  
  //mzh_fu 2007-03-15
  if(controlToPopulate!=null){
	  // Empty the second drop down box of any choices
	  for (var q=controlToPopulate.options.length;q>=0;q--) controlToPopulate.options[q]=null;
	  // ADD Default Choice - in case there are no values
	  //myEle = document.createElement("option") ;
	  //myEle.value = 0 ;
	  //myEle.text = "[SELECT]" ;
	  //controlToPopulate.add(myEle) ;
	  for ( x = 0 ; x < ItemArray.length  ; x++ )
	    {
	      if ( GroupArray[x] == control.value || GroupArray[x] == 0)
	        {
	          myEle = document.createElement("option") ;
	          myEle.value = x ;
	          myEle.text = ItemArray[x] ;
			  myEle.selected=false;
			  
			  if(x==selectValue)
			  {		 	
				myEle.selected=true;
			  }	
	          controlToPopulate.add(myEle) ;
	        }
	    }
    }
}


function writed()
{	
	if (frm.startdate.value!="" && frm.intervalnum.value!="")
    {
    	if(!checkDate(frm.startdate,1,"借款起始日期"))			
		{
			return false;
		}
		if (!InputValid(frm.intervalnum, 1, "int", 1, 1, 999,"借款期限")) 
		{
			return false;
		}		
   		frm.enddate.value=addDate(frm.startdate.value,eval(frm.intervalnum.value));
   	}
}
function confirmClose()
{
	window.close();
}

function changeScale(frm)
{
	if (!InputValid(frm.sCale,1, "float",1,0.01,100,"承贷比例")) 
	//if(!checkAmount(frm.sCale,1,"承贷比例"))
	{
	    return false;
	}
	frm.saleamount.value = formatAmount(eval(reverseFormatAmount1(frm.loanamount.value))*eval(frm.sCale.value)/100);
}

function addDate(strInputDate,lMonth)
{
 	var temp,s;
    temp=new String(strInputDate);
    s=new String("");
    for(var i=0;i<=temp.length-1;i++)
    {
    	if(temp.charAt(i)=="-" || temp.charAt(i)=="/")
    	{
      		s=s+"/";
  		} 
    	else
    	{
    		if(isNaN(Number(temp.charAt(i))))
	      	{
    	   		alert("请输入正确的开始日期");
       			form.txtDateFixedStart.focus();
       			return false;
   			}    
    		else
   			{
    			s=s+temp.charAt(i);
   			}
  		} 
   	}
   	var dtDate;
 	dtDate = new Date(s);
 	var strDate;
 	var yy,mm,temp;
 
 	var dtDay = dtDate.getDate();
 	temp = parseInt(lMonth) + dtDate.getMonth()+1 ;
 	var dtMonth = temp % 12;
 	var dtYear = dtDate.getYear() + parseInt(temp / 12);
 
 	if(parseInt(dtMonth)==0)
 	{
  		dtMonth='12';
  		dtYear=parseInt(dtYear)-1;
 	}
 	strDate=dtYear + "-" +dtMonth + "-" + dtDay;
 	return strDate;
}
	

function  confirmSave(frm)
{
//去除空格
trimFormValue();
frm.loanpurpose.value = Trim(frm.loanpurpose.value);

 if(!checkList(frm.subLoanType,"贷款子类"))
    return false;
<% if (isyt) {%>
	//借款额度 loanamount
	if(!checkAmount(frm.loanamount,1,"贷款额度"))
	{
	    return false;
	}

	//财务公司承贷比例
	if (!InputValid(frm.sCale,1, "float",1,0.01,100,"承贷比例")) 
	{
		return false;
	}
	if((frm.sCale.value <= 0) || (frm.sCale.value > 100 ))
	{
		
		alert("财务公司承贷比例不能小于0.01或大于100%!");
		return false;
	}
	changeScale(frm);
	//财务公司承贷额度 saleamount
	if(!checkAmount(frm.saleamount,1,"财务公司承贷额度"))
	{
	    return false;
	}
	
	if ( parseFloat(reverseFormatAmount1(frm.saleamount.value))>parseFloat(reverseFormatAmount1(frm.loanamount.value)) )
	{
		alert("财务公司承贷额度不能大于贷款额度!");
		return false;
	}
<%}else{%>
	//借款借额 loanamount
	if(!checkAmount(frm.loanamount,1,"借款金额"))
	{	
		frm.loanamount.focus();
	    return false;
	}
<%}%>	
<% if (panduan.equals("1") ) { %>
	//开始日期 startdate
	if(!checkDate(frm.startdate,1,"借款起始日期"))
	{frm.startdate.focus();
		return false;
	}
	//结束日期 enddate
	if(!checkDate(frm.enddate,1,"借款起始日期"))
	{frm.enddate.focus();
		return false;
	}
	
	startExe1 = formatedate(document.frm.startdate.value) ;
	endExe1 = formatedate(document.frm.enddate.value) ;
	
	if( startExe1.length != 0 && endExe1.length != 0 &&  startExe1 > endExe1){
		alert("开始日期不能大于结束日期");
		document.frm.enddate.focus();
		return false;
	} 
	/*	
	//借款利率 interestrate
	if (!InputValid(frm.interestrate, 1, "float", 0, 0, 0,"借款利率")) 
	{
		return false;
	}
	if (!InputValid(frm.chargerate, 1, "float", 0, 0, 0,"手续费")) 
	{
		return false;
	}
	*/
	
	if(!checkRate(frm.bankinterest,1,"借款利率"))
	{frm.bankinterest.focus();
		  return false;
	}
	//手续费率 chargerate
	if(!checkRate(frm.chargerate,1,"手续费"))
	{frm.chargerate.focus();
	  return false;
	}
	
	//手续费率收取方式 checkPayType
	if (frm.checkPayType.value==-1)
	{
		alert("手续费率收取方式不能为空");
		frm.checkPayType.focus();
		return false;
	}
		if (!InputValid(frm.loanpurpose, 1, "string", 0, 0, 100,"借款用途")) 
	{frm.loanpurpose.focus();
		return false;
	}
	
	
	//项目批准机关及问号 other
<%} else if(panduan.equals("2")) {%>

	if (!InputValid(frm.assureChargeRate,1, "float",1,0.01,100,"担保费率")) 
	{
		frm.assureChargeRate.focus();
	    return false;
	}
	if (frm.assureTypeID1.value==-1)
	{
		alert("担保类型一不能为空");
		frm.assureTypeID1.focus();
		return false;
	}
	//借款原因 loanreason
	if (!InputValid(frm.loanreason, 1, "string", 0, 0, 100,"担保原因")) 
	{
		frm.loanreason.focus();
		return false;
	}
	//借款用途 loanpurpose
	if (!InputValid(frm.loanpurpose, 1, "string", 0, 0, 100,"担保用途")) 
	{
		frm.loanpurpose.focus();
		return false;
	}
	
<%}else{%>	
	//借款原因 loanreason
	if (!InputValid(frm.loanreason, 1, "string", 0, 0, 100,"借款原因")) 
	{
		frm.loanreason.focus();
		return false;
	}
	//借款用途 loanpurpose
	if (!InputValid(frm.loanpurpose, 1, "string", 0, 0, 100,"借款用途")) 
	{
		frm.loanpurpose.focus();
		return false;
	}
	//还款来源和措施other
	
	if (!InputValid(frm.hkly, 1, "string", 0, 0, 100,"还款来源及措施") )
	{
		frm.hkly.focus();
		return false;
	}
	
	//借款单位简介 clientinfo
	if (!InputValid(frm.clientinfo, 1, "string", 0, 0, 100,"借款单位介绍")) 
	{
		frm.clientinfo.focus();
		return false;
	}
<%}%>
	if (!InputValid(frm.intervalnum, 1, "int", 1, 1, 999,"期限")) 
	{
		frm.intervalnum.focus();
		return false;
	}
	
		
	showSending();
	
	frm.action="l007-c.jsp";
	frm.submit();
	return true;
 }
 function formatChargeRate(fromName,rateCntlName)
 {
  var strData=eval(fromName + '.' + rateCntlName + '.value');
	if(isInnerFloat(strData))
 	{
 		
		if(strData!="")
 		{
			var i,strTemp;

			//将小数点前和后的数据分别取出来
	 		var nPoint;
	 		nPoint=strData.indexOf(".");
	 		var strFront=strData,strEnd="";
	 		if(nPoint!=-1)
	 		{
	 			strFront=strData.substring(0,nPoint);
	 			strEnd=strData.substring(nPoint+1,strData.length);
	 		}

			//小数点前如果是零位，则付0
			if (strFront.length==0)
			{
				strFront = "0";
			}
			//补或者截小数点后面的值，保持四位
	 		if(strEnd.length>6)
	 		{
	 			strEnd=strEnd.substring(0,6);
	 		}
	 		else
	 		{
	 			if(strEnd.length==1)
	 			{
	 				strEnd=strEnd+ "00000";
	 			}
	 			else
				if (strEnd.length==2)
				{
					strEnd=strEnd+ "0000";
				}
				else
				if (strEnd.length==3)
				{
					strEnd=strEnd+ "000";
				}
				else
				if (strEnd.length==4)
				{
					strEnd=strEnd+ "00";
				}
				else
				if (strEnd.length==5)
				{
					strEnd=strEnd+ "0";
				}
				else
	 				if(strEnd.length==0)
	 				{
	 					strEnd="000000";
	 				}
	 		}
	 		strData=strFront+"." + strEnd;
 		}
		else
		{
			strData = "0.000000";
		}
	}
	eval(fromName + "." + rateCntlName + ".value='"+strData+"'");
 }
<%if (action.equals("modify") ) {%>
firstFocus(frm.loanamount);
<%}%>
//setSubmitFunction("confirmSave(frm)");
setFormName("frm");	 
<%if(assureTypeID1<0){%>	
selectChange(frm.assureTypeID1, frm.assureTypeID2, arrItems1, arrItemsGrp1,"<%=assureTypeID2%>");
<%}%>
</script>
<%
	OBHtml.showOBHomeEnd(out);
	}
	catch( Exception ie )
	{
		//OBHtml.showExceptionMessage(out,sessionMng,ie,"客户选择","", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return; 
	}
%>
<%@ include file="/common/SignValidate.inc" %>