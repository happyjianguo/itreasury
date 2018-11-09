<%
/**
 * 页面名称 ：q010-v.jsp
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
	com.iss.itreasury.loan.loanapply.dataentity.*,
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
		
		LoanApplyInfo appInfo=(LoanApplyInfo)request.getAttribute("LoanApplyInfo");
	
    	long loanType=appInfo.getTypeID();
    	String loanTypeName=OBConstant.LoanInstrType.getName(loanType);
		boolean iswt=false;
    	boolean isyt=false;
    	boolean isCheck=false;
    	String action="";
    	long statusID=-1;
    	String tmp="";
        
    	long loanID=appInfo.getID();
    	long clientID=appInfo.getBorrowClientID();
    	long wtClientID=appInfo.getConsignClientID();
    	String applyCode=appInfo.getApplyCode();
    	//String applyCode=appInfo.getInstructionNo();
   
    	long isCredit=appInfo.getIsCredit();
    	long isAssure=appInfo.getIsAssure();
    	long isPledge=appInfo.getIsPledge();
    	long isImpawn=appInfo.getIsImpawn();
		
    	double loanAmount=appInfo.getLoanAmount();
    	Vector assVector=(Vector)appInfo.getAssureInfo();
    	boolean hasIsAssure=false;
    	boolean hasIsPledge=false;
    	boolean hasIsImpawn=false;
    	
		tmp=(String)request.getAttribute("txtAction");
    	if ( (tmp!=null)&&(tmp.length()>0) )
    		action=tmp;
		statusID=appInfo.getStatusID();   
		
       	OBHtml.showOBHomeHead(out,sessionMng,"[贷款保证资料]",Constant.YesOrNo.NO);
		isCheck=true;   
        
%>	

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />
<form name="frm">
<TABLE border=0 class=top height=330 width="730">
  <TBODY>
  <TR class="tableHeader">
      <TD class=FormTitle height=29> <B>  <%=loanTypeName%>——新增</B></TD>
    </TR>
  <TR>
    <TD>
      <TABLE cellPadding=0 height=50>
        <TR>
            <TD height=56 width=5> <P class=MsoNormal>&nbsp;</P></TD>
            <TD height=56 colspan=8 align="left"><P class=MsoNormal> 申请指令编号: <%=applyCode%></P> </TD>
        </TR>
        <TR>
            <TD width=5>&nbsp;</TD>
            <TD colSpan=8 vAlign=top><HR align=center SIZE=2 ></TD>
        </TR>
        <TR >
            <TD width=5>&nbsp;</TD>
            <TD colSpan=8 vAlign=top><U>申请贷款担保方式</U></TD>
        </TR>
        <TR>
            <TD height=178 width=5> <P class=MsoNormal>&nbsp;</P></TD>
            <TD colSpan=8 height=178 vAlign=top>
                <TABLE align=center border=0 class=ItemList height=87 width="100%">
                  <TR align=middle class="tableHeader"> 
                    <TD class=ItemTitle height=14 >&nbsp;</TD>
                    <TD class=ItemTitle height=14 ><A href="javascript:go('1');">客户编号</A></TD>
                    <TD class=ItemTitle height=14 ><A href="javascript:go('2');">单位名称</A></TD>
                    <TD class=ItemTitle height=14 ><A href="javascript:go('3');">担保方式</A></TD>
                    <TD class=ItemTitle height=14 ><A href="javascript:go('4');">联系人</A></TD>
                    <TD class=ItemTitle height=14 ><A href="javascript:go('5');">电话</A></TD>
                    <TD class=ItemTitle height=14 ><a href="javascript:go('6');">担保金额</a></TD>
                    <TD class=ItemTitle height=14 ><A href="javascript:go('6');">担保比例(%)</A></TD>
                  </TR>
          <% 
          	if ( (assVector!=null)&&(assVector.size()>0) ){
          		int iCount=assVector.size();
          		AssureInfo assInfo=null;
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
          			assInfo=(AssureInfo)assVector.get(i);
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
          			
          			
          %>			
                  <TR align=middle> 
                    <TD class=ItemBody height=35 ><INPUT name="checkbox<%=i%>" type=checkbox value="<%=assaID%>" ></TD>
                    <TD class=ItemBody height=35 ><A href="javascript:confirmModify(<%=assaID%>);" ><%=asssCode%></a></TD>
                    <TD class=ItemBody height=35 ><%=asssName%></TD>
                    <TD class=ItemBody height=35 ><%=LOANConstant.AssureType.getName(assTypeID)%></TD>
                    <TD class=ItemBody height=35 ><%=asssContacter%></TD>
                    <TD class=ItemBody height=35 ><%=asssPhone%></TD>
                    <TD class=ItemBody height=35 ><%=DataFormat.formatNumber(assAmount,2)%></TD>
                    <TD class=ItemBody height=35 ><%=DataFormat.formatNumber(assRate,2)%></TD>
                  </TR>
          <% 	
          		}
          	} else {
          %>    
          		<TR align=middle> 
                    <TD class=ItemBody height=35 > </TD>
                    <TD class=ItemBody height=35 ></TD>
                    <TD class=ItemBody height=35 ></TD>
                    <TD class=ItemBody height=35 ></TD>
                    <TD class=ItemBody height=35 ></TD>
                    <TD class=ItemBody height=35 ></TD>
                    <TD class=ItemBody height=35 ></TD>
                    <TD class=ItemBody height=35 ></TD>
                  </TR>
           <%	}  %>
                      
                </TABLE>
                <DIV align=left><B><BR><INPUT class=button name="dd" type="button" onclick="DeleteAssure()" value=删除 <%if (isCheck) {%>disabled<%}%>> </B></DIV>
              </TD>
        </TR>
        <TR>
            <TD height=25 width=5>&nbsp;</TD>
          <TD colSpan=8 height=25 vAlign=center><HR>
			<INPUT class=button name="Add_db"  onclick="add('<%=LOANConstant.AssureType.ASSURE%>')" type=button value="  新 增 保 证 单 位" <% if (isAssure!=1) { %> disabled <% } %> <%if (isCheck) {%>disabled<%}%>> 
			<INPUT class=button name="Add_dy" onclick="add('<%=LOANConstant.AssureType.PLEDGE%>')" type=button value="  新 增 抵 押 单 位" <% if (isPledge!=1) { %> disabled <% } %> <%if (isCheck) {%>disabled<%}%>> 
			<INPUT class=button name="Add_zy" onclick="add('<%=LOANConstant.AssureType.IMPAWN%>')"  type=button value="  新 增 质 押 单 位" <% if (isImpawn!=1) { %> disabled <% } %> <%if (isCheck) {%>disabled<%}%>> 
          </TD>
        </TR>
        <TR>
          <TD height=18 width=5>&nbsp;</TD>
          <TD colSpan=8 height=18 vAlign=center><HR align=center SIZE=2 ></TD>
        </TR>
        <TR>
        	<TD height=18 width=5>&nbsp;</TD>
        	<TD height=18 >其他附件:</TD>
            <TD colspan=7>
            
            <iframe src="../../attach/AttachFramea.jsp?lID=<%=loanID%>&lTypeID=<%=LOANConstant.AttachParentType.LOAN%>&sCaption=链接附件&showOnly=<%=isCheck%>" height="100" scrolling="Auto" frameborder="0" name="iFrame" >
			</iframe>
			
		    </TD>
		</TR>
        <TR>
            <TD height=18 width=5>&nbsp;</TD>
            <TD colSpan=8 height=18 vAlign=center><HR align=center SIZE=2 ></TD>
        </TR>
 		<!--TR>
            <TD height=18 width=5>&nbsp;</TD>
            <TD colSpan=1 height=18 >贷款调查表:</TD>
            <TD colSpan=1 height=18 > </TD>
            <TD colSpan=1 height=18 ></TD>
            <td width="1">&nbsp;</td>	
            <TD height=18 colspan="5" align="left"> 
            	<INPUT class=button name=Submit423223 type=button value=贷款调查表 onclick="Javascript:window.open('../../content/c220a-c.jsp?ParentID=<%=loanID%>&control=view','','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes');"> 		  
			</TD>
		</TR-->
        <TR>
            <TD height=37 width=5>&nbsp;</TD>
            <TD colSpan=8 height=37 vAlign=top><HR align=center SIZE=2 ></TD>
        </TR>
        <TR>
            <TD height=37 width=5> <P class=MsoNormal>&nbsp;</P></TD>
            <TD colSpan=8 height=37 vAlign=top>
             <P align=right class=MsoNormal>
			<INPUT class=button name="xybClose" onclick="confirmClose()" type="button" value=" 关 闭 "> 
            </P>
            </TD>
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
<input type=hidden name="lOrderParam" value="1">
<input type=hidden name="lDesc" value="1">
<input type=hidden name="lAssCount" value="<%=(assVector==null)?0:assVector.size()%>">
<input type=hidden name="lAssureID" value="-1">
<input type="hidden" name="txtAction" value="<%=action%>">

</form>			
<script language="JavaScript">
function confirmClose()
{
	window.close();
}
function confirmModify(id)
{
	frm.lAssureID.value=id;
	frm.action="q011-c.jsp";
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