<%
/**
 * 页面名称 :q009-v.jsp
 * 页面功能 : 贷款属性资料
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
	com.iss.itreasury.loan.loanapply.dataentity.*,
	com.iss.itreasury.loan.loanapply.bizlogic.*,
	com.iss.itreasury.ebank.util.*,
	com.iss.itreasury.ebank.obloanapply.dataentity.*,
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
		String type=(String)request.getAttribute("lLoanType");
		System.out.println("type");
    	long loanType=Long.valueOf(type).longValue();
    	String loanTypeName=OBConstant.LoanInstrType.getName(loanType);
    	boolean iswt=false;
    	boolean isyt=false;
    	boolean isCheck=false;
    	String action="";
    	long statusID=-1;
    	String tmp="";
    
		LoanApplyInfo lInfo= (LoanApplyInfo) request.getAttribute("LoanApplyInfo");

		long wtClientID=lInfo.getConsignClientID();			//委托客户
		String loanCode=lInfo.getApplyCode();				//申请号
		long loanID=lInfo.getID();							//申请ID
		long clientID=lInfo.getBorrowClientID();			//客户号
		String applyCode=lInfo.getApplyCode();
		long isCircle=lInfo.getIsCircle();					//是否循环
		long isSaleBuy=lInfo.getIsSaleBuy();				//是否卖方贷款
		long isTechnical=lInfo.getIsTechnical();			//是否技改贷款
		long isCredit=lInfo.getIsCredit();					//是否信用保证
		long isAssure=lInfo.getIsAssure();					 //是否担保
		long isImpawn=lInfo.getIsImpawn();					//是否质押
		long isPledge=lInfo.getIsPledge();					//是否抵押
	
      
	    tmp=(String)request.getAttribute("txtAction");
    	if ( (tmp!=null)&&(tmp.length()>0) )
    		action=tmp;
		tmp=(String)request.getAttribute("nStatusID");
    	if ( (tmp!=null)&&(tmp.length()>0) )
    		statusID=Long.valueOf(tmp).longValue(); 
    	
       	OBHtml.showOBHomeHead(out,sessionMng,"[贷款基本性质资料]",Constant.YesOrNo.NO);

       	isCheck=true;		
        
%>	

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />
<form name="frm">
<TABLE border=0 class=top height=330 width="49%">
  <TBODY>
  <TR class="tableHeader">
      <TD class=FormTitle height=29><B><%=loanTypeName%>——新增</B></TD>
    </TR>
  <TR>
    <TD>
      <TABLE cellPadding=0 height=50 width=730>
      <TR style="HEIGHT: 21pt">
            <TD height=56 width=8> <P class=MsoNormal>&nbsp;</P></TD>
            <TD height=56 colspan=8><P class=MsoNormal>申请指令编号:<%=applyCode%></P> </TD>
      </TR>
        <TR >
            <TD height=11 width=8>&nbsp;</TD>
            <TD colSpan=8 vAlign=top><HR align=center SIZE=2 width="100%"> <P><font color='#FF0000'>* </font><U>贷款性质</U></P></TD>
        </TR>
        <TR style="HEIGHT: 21.75pt">
            <TD height=46 width=8>&nbsp; </TD>
            <TD height=46 colspan=8>
            	<table cellPadding=0 height=50 width=100% >
            	<tr>
            	<td width=16%>循环贷款:</TD>
            	<TD height=46 width=16%>
              		<select class=box name="iscircle" onfocus="nextfield='issalebuy';" value="1" <%if (isCheck) {%>disabled<%}%> >
					<option value="-1" ></option>
					<option value="1" <% if (isCircle==1) {%> selected <% } %> >是</option>
					<option value="2" <% if (isCircle!=1) {%> selected <% } %> >否</option>
			  		</select>
	            </TD>
            	<TD height=46 width=16%>买卖方贷款:</TD>
            	<TD height=46 width=16%>
              		<select class=box name="issalebuy"  onfocus="nextfield='istechnical';" <%if (isCheck) {%>disabled<%}%>>
					<option value="-1" ></option>
					<option value="1" <% if (isSaleBuy==1) {%> selected <% } %> >买</option>
					<option value="2" <% if (isSaleBuy==2) {%> selected <% } %> >卖</option>
					<option value="3" <% if ( (isSaleBuy!=1)&&(isSaleBuy!=2)) {%> selected <% } %> >不适用</option>
					</select>
		  		</TD>
            	<TD height=46 width=16%>技改贷款:</TD>
            	<TD height=46 width=20%>
            		<select class=box name="istechnical"  onfocus="nextfield='iscredit';" <%if (isCheck) {%>disabled<%}%>>
                	<option value="-1" > </option>
                	<option value="1" <% if (isTechnical==1) {%> selected <% } %> >是</option>
                	<option value="2" <% if (isTechnical!=1) {%> selected <% } %> >否</option>
              		</select>
              	</TD>
              	</tr>
              	</table>
            </TD>
          </TR>
        <TR >
            <TD height=100 width=8> 
              <P class=MsoNormal>&nbsp;</P></TD>
          <TD colSpan=8 height=100 vAlign=top align=center>
            <HR align=center SIZE=2 width="100%">
            <P align=left><font color='#FF0000'>* </font><U>贷款担保方式</U></P>
            <TABLE border=0 width="100%">
              <TR>
                <TD height=17 width="6%" align=center> <INPUT name="iscredit" type=checkbox  onfocus="nextfield='isassure';" value="1" <% if (isCredit>0) {%> checked <% } %> <%if (isCheck) {%>disabled<%}%> > </TD>
                <TD height=17 width="15%">信用</TD>
                <TD height=17 width="6%" align=center> <INPUT name="isassure" type=checkbox onfocus="nextfield='isimpawn';" value="1"  <% if (isAssure>0) {%> checked <% } %> <%if (isCheck) {%>disabled<%}%> ></TD>
                <TD height=17 width="15%">保证</TD>
                <TD height=17 width="6%"> <INPUT name="isimpawn" type=checkbox  onfocus="nextfield='ispledge';" value="1"  <% if (isImpawn>0) {%> checked <% } %> <%if (isCheck) {%>disabled<%}%> ></TD>
                <TD height=17 width="15%">质押</TD>
                <TD height=17 width="6%"> <INPUT name="ispledge" type=checkbox onfocus="nextfield='xyb';" value="1"  <% if (isPledge>0) {%> checked <% } %> <%if (isCheck) {%>disabled<%}%> ></TD>
                <TD height=17 width="15%">抵押</TD>
              </TR>
            </TABLE>
            <HR align=center SIZE=2 width="100%">
            </TD>
          </TR>
			
        <TR> 
            <TD align=left height="39"></td>     
            <TD height=39 vAlign=middle nowrap>其它附件:</TD>
            <TD align=left colspan=7 height="39"></td>
		</tr>
		<tr>
			<TD align=left height="39"></td>     
			<td colspan=8>
				<iframe src="/NASApp/iTreasury-ebank/attach/AttachFramea.jsp?lID=<%=loanID%>&lTypeID=<%=LOANConstant.AttachParentType.LOAN%>&sCaption=<%=URLEncoder.encode("链接附件")%>&showOnly=<%=isCheck%>" width=723 height="100" scrolling="Auto" frameborder="0" name="iFrame" >
				</iframe>
			</TD>
		</TR>
        <TR >
            <TD height=37 width=8>&nbsp;</TD>
          <TD colSpan=8 height=37 vAlign=top>
            <HR align=center SIZE=2 width="100%">
          </TD>
        </TR>
        <TR style="HEIGHT: 31.5pt">
          <TD height=37 width=8> <P class=MsoNormal>&nbsp;</P></TD>
          <TD colSpan=8 height=37 vAlign=top>
            <P align=right class=MsoNormal >
				<INPUT class=button name="xybClose" onclick="confirmClose()" type="button" value=" 关 闭 "> 
            </P>
          </TD>
        </TR>
      </TABLE>
     </TD>
    </TR>
   </TABLE>
<P><FONT size=5></FONT></P>
<input type="hidden" name="sApplyCode" value="<%=applyCode%>">
<input type="hidden" name="lLoanID" value="<%=loanID%>">
<input type="hidden" name="lClientID" value="<%=clientID%>">
<input type=hidden name="lLoanType" value="<%=type%>">
<input type="hidden" name="wtClientID" value="<%=wtClientID%>">
<input type="hidden" name="txtAction" value="<%=action%>">

</form>
<script language="JavaScript">
function confirmClose()
{
	window.close();
}
firstFocus(frm.xybClose);
//setSubmitFunction("confirmSave(frm)");
setFormName("frm");
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