<%
/**
 * 页面名称 ：l010-v.jsp
 * 页面功能 : 贷款保证资料
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
	com.iss.itreasury.ebank.obdataentity.*,
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
		
		OBLoanApplyInfo appInfo=(OBLoanApplyInfo)request.getAttribute("LoanApplyInfo");
		OBPageInfo obPInfo=(OBPageInfo)request.getAttribute("OBPageInfo");
		
		if (obPInfo == null)
		{
			obPInfo = new OBPageInfo();
		}
		
		
	
    	long loanType=appInfo.getTypeID();
    	String loanTypeName=OBConstant.LoanInstrType.getName(loanType);
		long[] loanTypeid={loanType};
		boolean iswt=false;
    	boolean isyt=false;
    	boolean isCheck=false;
    	String action="";
    	long statusID=-1;
    	String tmp="";
        
    	long loanID=appInfo.getID();
    	long clientID=appInfo.getBorrowClientID();
    	long wtClientID=appInfo.getConsignClientID();
    	//String applyCode=appInfo.getApplyCode();
    	String applyCode=appInfo.getInstructionNo();
   
    	long isCredit=appInfo.getIsCredit();
    	long isAssure=appInfo.getIsAssure();
    	long isPledge=appInfo.getIsPledge();
    	long isImpawn=appInfo.getIsImpawn();
		long isRecognizance = appInfo.getIsRecognizance();
		
    	double loanAmount=appInfo.getLoanAmount();
    	Vector assVector=(Vector)appInfo.getAssureInfo();
    	boolean hasIsAssure=false;
    	boolean hasIsPledge=false;
    	boolean hasIsImpawn=false;
		boolean hasIsRecognizance=false;
    	
		tmp=(String)request.getAttribute("txtAction");
    	if ( (tmp!=null)&&(tmp.length()>0) )
    		action=tmp;
		statusID=appInfo.getStatusID(); 
		System.out.println(appInfo.getSubTypeId()+"^^^^^^^^^^^^^^^"+loanType+"&&&&&&&&&&"+isRecognizance);
		
        //显示文件头
        if ( (action.equals("modify"))||(action.equals("check")) )
        	OBHtml.showOBHomeHead(out,sessionMng,"[贷款保证资料]",Constant.YesOrNo.NO);
        else	
        	OBHtml.showOBHomeHead(out,sessionMng,"[贷款保证资料]",Constant.YesOrNo.YES);
	    if ( action.equals("check") )
       		isCheck=true;   
        
%>	

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />
<form name="frm">
<TABLE border=0 class=top width="730" align="center">
  <TR class="tableHeader">
      <TD class=FormTitle height=29> <B>  <%=loanTypeName%>——新增</B></TD>
    </TR>
  <TR>
    <TD>
      <TABLE cellPadding=0  width="100%">
        <TR>
            <TD  width=5> <P class=MsoNormal>&nbsp;</P></TD>
            <TD  colspan=7 align="left"><P class=MsoNormal> 申请指令编号: <%=applyCode%></P> </TD>
            <TD  nowrap align="right">贷款子类名称：<%LOANConstant.SubLoanType.showList(out,"subLoanType",appInfo.getSubTypeId(),false,true,"disabled",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,loanTypeid,null );%>
		   &nbsp;</TD>
        </TR>
        <TR>
            <TD width=5>&nbsp;</TD>
            <TD colSpan=8 vAlign=top><HR align=center SIZE=2 ></TD>
        </TR>
        <TR >
            <TD width=5>&nbsp;</TD>
            <TD colSpan=8 vAlign=top><U><%if(loanType == OBConstant.LoanInstrType.ASSURE){%>反担保方式<%}else{%>申请贷款担保方式<%}%></U></TD>
        </TR>
        <TR>
            <TD width=5> <P class=MsoNormal>&nbsp;</P></TD>
            <TD colSpan=8 vAlign=top>
                <TABLE align=center border=0 class=ItemList width="100%">
                  <TR align=center  class="tableHeader"> 
                    <TD class=ItemTitle width="30">&nbsp;</TD>
                    <TD class=ItemTitle ><A href="javascript:go('1');">客户编号</A></TD>
                    <TD class=ItemTitle><A href="javascript:go('2');">单位名称</A></TD>
                    <TD class=ItemTitle ><A href="javascript:go('3');">担保方式</A></TD>
                    <TD class=ItemTitle ><A href="javascript:go('4');">联系人</A></TD>
                    <TD class=ItemTitle ><A href="javascript:go('5');">电话</A></TD>
                    <TD class=ItemTitle><a href="javascript:go('6');">担保金额</a></TD>
                    <TD class=ItemTitle ><A href="javascript:go('6');">担保比例(%)</A></TD>
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
          			assRate=(assAmount/loanAmount)*100;
          			if ( asssContacter==null ) asssContacter="";
          			if( asssPhone==null ) asssPhone="";
          			if ( assTypeID==LOANConstant.AssureType.ASSURE) hasIsAssure=true;
          			if ( assTypeID==LOANConstant.AssureType.PLEDGE) hasIsPledge=true;
          			if ( assTypeID==LOANConstant.AssureType.IMPAWN) hasIsImpawn=true;
					if ( assTypeID==LOANConstant.AssureType.RECOGNIZANCE) hasIsRecognizance=true;
          			
          			
          %>			
                  <TR align=center> 
                    <TD class=ItemBody  ><INPUT name="checkbox<%=i%>" type=checkbox value="<%=assaID%>" ></TD>
                    <TD class=ItemBody ><A href="javascript:confirmModify(<%=assaID%>);" ><%=asssCode%></a></TD>
                    <TD class=ItemBody  ><%=asssName%></TD>
                    <TD class=ItemBody ><%=LOANConstant.AssureType.getName(assTypeID)%></TD>
                    <TD class=ItemBody  ><%=asssContacter%></TD>
                    <TD class=ItemBody ><%=asssPhone%></TD>
                    <TD class=ItemBody ><%=DataFormat.formatNumber(assAmount,2)%></TD>
                    <TD class=ItemBody ><%=DataFormat.formatNumber(assRate,2)%></TD>
                  </TR>
          <% 	
          		}
          	} else {
          %>    
          		<TR align=center> 
                    <TD class=ItemBody > </TD>
                    <TD class=ItemBody ></TD>
                    <TD class=ItemBody ></TD>
                    <TD class=ItemBody ></TD>
                    <TD class=ItemBody ></TD>
                    <TD class=ItemBody ></TD>
                    <TD class=ItemBody ></TD>
                    <TD class=ItemBody ></TD>
                  </TR>
           <%	}  %>
                      
                </TABLE>
                <DIV align=left><B><BR><INPUT class=button name="dd" type="button" onclick="DeleteAssure()" value=删除 <%if (isCheck) {%>disabled<%}%>> </B></DIV>
              </TD>
        </TR>
        <TR>
            <TD height=25 width=5>&nbsp;</TD>
          <TD colSpan=8 height=25 vAlign=middle><HR>
		   <%if(loanType == OBConstant.LoanInstrType.ASSURE){%>
		  <INPUT class=button name="Add_dbj"  onclick="addbzj('<%=LOANConstant.AssureType.RECOGNIZANCE%>')" onKeydown="if(event.keyCode==13) addbzj('<%=LOANConstant.AssureType.RECOGNIZANCE%>');" type=button value="  新 增 保 证 金" <% if (isRecognizance!=1) { %> disabled <% } %> <%if (isCheck) {%>disabled<%}%>> 
			<%}%>
			<INPUT class=button name="Add_db"  onclick="add('<%=LOANConstant.AssureType.ASSURE%>')" type=button value="  新 增 保 证 单 位" <% if (isAssure!=1) { %> disabled <% } %> <%if (isCheck) {%>disabled<%}%>> 
			<INPUT class=button name="Add_dy" onclick="add('<%=LOANConstant.AssureType.PLEDGE%>')" type=button value="  新 增 抵 押 单 位" <% if (isPledge!=1) { %> disabled <% } %> <%if (isCheck) {%>disabled<%}%>> 
			<INPUT class=button name="Add_zy" onclick="add('<%=LOANConstant.AssureType.IMPAWN%>')"  type=button value="  新 增 质 押 单 位" <% if (isImpawn!=1) { %> disabled <% } %> <%if (isCheck) {%>disabled<%}%>> 
          </TD>
        </TR>
        <TR>
          <TD height=18 width=5>&nbsp;</TD>
          <TD colSpan=8 height=18 vAlign=middle><HR align=center SIZE=2 ></TD>
        </TR>
        <TR>
        	<TD height=18 width=5>&nbsp;</TD>
        	<TD height=18 >其他附件:</TD>
            <TD colspan=7 align="right">
            
            <iframe src="../../attach/AttachFrame.jsp?lID=<%=loanID%>&lTypeID=<%=LOANConstant.AttachParentType.LOAN%>&sCaption=<%=URLEncoder.encode("链接附件")%>&showOnly=<%=isCheck%>" height="100" scrolling="Auto" frameborder="0" name="iFrame" >
			</iframe>
			
		    &nbsp;</TD>
		</TR>
<%if (Config.getBoolean(ConfigConstant.LOAN_CLIENT_REPORT,false))
{%>
        <!--TR>
            <TD height=18 width=5>&nbsp;</TD>
            <TD colSpan=8 height=18 vAlign=center><HR align=center SIZE=2 ></TD>
        </TR>

 		<TR>
            <TD height=18 width=5>&nbsp;</TD>
            <TD colSpan=1 height=18 >贷款调查表:</TD>
            <TD colSpan=1 height=18 > </TD>
            <TD colSpan=1 height=18 ></TD>
            <td width="1">&nbsp;</td>	
            <TD height=18 colspan="5" align="right"> 
            <% if (isCheck) { %>
            	<INPUT class=button name=Submit423223 type=button value=贷款调查表 onclick="Javascript:window.open('../../content/c220-c.jsp?ParentID=<%=loanID%>&control=view','','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes');"> 		  
            <% }else{ %> 
				<INPUT class=button name=Submit423223 type=button value=贷款调查表 onclick="Javascript:window.open('../../content/c220-c.jsp?ParentID=<%=loanID%>&control=edit','','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes');"> 		  
			<% } %>	
			&nbsp;</TD>
		</TR-->
<%
}
%>
        <TR>
            <TD height=37 width=5>&nbsp;</TD>
            <TD colSpan=8 height=37 vAlign=top><HR align=center SIZE=2 ></TD>
        </TR>

        <TR>
            <TD height=37 width=5> <P class=MsoNormal>&nbsp;</P></TD>
            <TD colSpan=8 height=37 vAlign=top>
             <P align=right class=MsoNormal>
             <% if (action.equals("modify")) {%>
					<INPUT class=button name="xybClose" onclick="confirmClose()" type="button" value=" 关 闭 "> 
			<%  	if (statusID==OBConstant.LoanInstrStatus.SAVE){ %>
					<INPUT class=button name="subnext"  onclick="finish()" type="button" value=" 完 成 "> 
			<%		}else if(statusID==OBConstant.LoanInstrStatus.SUBMIT) {    %>
					<INPUT class=button name="subnext"  onclick="finish()" type="button" value=" 修改并提交 "> 
			<%		}else if(statusID==OBConstant.LoanInstrStatus.ACCEPT) {    %>
					<INPUT class=button name="subnext"  onclick="finish()" type="button" value=" 修改并提交 "> 																			
			<%      }         %>		
			<% }else if ( action.equals("check") ){ %>
				<INPUT class=button name="xybClose" onclick="confirmClose()" type="button" value=" 关 闭 "> 				
			<% }else { %>
				<INPUT class=button name=Submi333t3 onclick="goBack()" type="button" value=" 返 回 "> 

				<%if(loanType == OBConstant.LoanInstrType.ASSURE){%>
					<INPUT class=button name=Submit3 onclick="confirmSubmit()" onKeydown="if(event.keyCode==13) confirmSubmit();" type="button" value=" 提 交 "> 
				<%}else{%>
					<INPUT class=button name=Submit3 onclick="goNext()" onKeydown="if(event.keyCode==13) goNext();" type="button" value=" 下一步 "> 
			<%}} %>
				
            </P>
           &nbsp; </TD>
        </TR>
      </TABLE>
    </TD>
  </TR>
 </TABLE>

<input type=hidden name="lLoanID" value="<%=loanID%>">
<input type=hidden name="lClientID" value="<%=clientID%>">
<input type=hidden name="lLoanType" value="<%=loanType%>">
<input type=hidden name="sApplyCode" value="<%=applyCode%>">
<input type=hidden name="lAssureTypeID" value="-1">
<input type=hidden name="lOrderParam" value="<%=obPInfo.getOrderParam()%>">
<input type=hidden name="lDesc" value="<%=obPInfo.getDesc()%>">
<input type=hidden name="lAssCount" value="<%=(assVector==null)?0:assVector.size()%>">
<input type=hidden name="lAssureID" value="-1">
<input type="hidden" name="txtAction" value="<%=action%>">
<input type="hidden" name="loanAmount" value="<%=loanAmount%>">

</form>			
<script language="JavaScript">
function finish()
{

	<% if ( (isRecognizance==1)&& !(hasIsRecognizance) ) {%>
		alert("还没有担保金纪录!")
		return false;
	<% }else if ( (isAssure==1)&& !(hasIsAssure) ) {%>
		alert("还没有担保纪录!")
		return false;
	<% } else if ( (isPledge==1)&& !(hasIsPledge) ) {%>
		alert("还没有抵押担保纪录!")
		return false;
	<% } else if ( (isImpawn==1) && !(hasIsImpawn) ) {%>
		alert("还没有质押担保纪录!")
		return false;
	<% } %>
	
	parent.opener.location.reload();
	window.close();
}
function confirmSubmit()
{
	<% if ( (isRecognizance==1)&& !(hasIsRecognizance) ) {%>
	alert("还没有担保金纪录!")
	return false;
	<% }else if ( (isAssure==1)&& !(hasIsAssure) ) {%>
	alert("还没有担保纪录!")
	return false;
	<% } else if ( (isPledge==1)&& !(hasIsPledge) ) {%>
	alert("还没有抵押担保纪录!")
	return false;
	<% } else if ( (isImpawn==1) && !(hasIsImpawn) ) {%>
	alert("还没有质押担保纪录!")
	return false;
	<% } %>
	
		frm.action="l028-c.jsp";
		showSending();
		frm.submit();
}
function confirmClose()
{
	window.close();
}
function confirmModify(id)
{
	frm.lAssureID.value=id;
	frm.action="l018-c.jsp";
	showSending();
	frm.submit();
}
function addbzj(para)
{
	frm.lAssureTypeID.value=para;
	frm.action="l015-v.jsp";
	showSending();
	frm.submit();
}

function add(para)
{
	frm.lAssureTypeID.value=para;
	frm.action="l013-v.jsp";
	showSending();
	frm.submit();
}

function goBack()
{

	frm.action="l012-c.jsp";
	showSending();
	frm.submit();
}
function goNext()
{
	<% if ( (isRecognizance==1)&& !(hasIsRecognizance) ) {%>
	alert("还没有担保金纪录!")
	return false;
	<% }else if ( (isAssure==1)&& !(hasIsAssure) ) {%>
	alert("还没有担保纪录!")
	return false;
	<% } else if ( (isPledge==1)&& !(hasIsPledge) ) {%>
	alert("还没有抵押担保纪录!")
	return false;
	<% } else if ( (isImpawn==1) && !(hasIsImpawn) ) {%>
	alert("还没有质押担保纪录!")
	return false;
	<% } %>
	
 	frm.action="l020-c.jsp";
 	showSending();
 	frm.submit();
}
function DeleteAssure()
{
	var i=0;
	var j=0;
	var s=<%=assVector.size()%>;
  	for(i=0;i<s;i++) 
  	{
     	if(eval("frm.checkbox"+i+".checked"))
     	    j=j+1;
  	}
  	
  	if (j==0)
  	{
  		alert("请选择删除的项");
  		return false;
  	}
	
    frm.action="l019-c.jsp";
  	showSending();
	frm.submit();


  
}

function go(para)
{
  if (frm.lOrderParam.value==para)
     {
	   if (frm.lDesc.value=="1")
	       frm.lDesc.value="2";
	   else
	   	  frm.lDesc.value="1"; 
	 }
   frm.lOrderParam.value=para;
   frm.action="l016-c.jsp";
   confirmSave(frm); 
}

function  confirmSave(frm)
	{
		showSending();
		frm.submit();

	}
		
</script>			
<%
	OBHtml.showOBHomeEnd(out);
	}
	catch( Exception ie )
	{
		//LOANHTML.showExceptionMessage(out,sessionMng,ie,request,response,"客户管理", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return; 
	}
%>
<%@ include file="/common/SignValidate.inc" %>