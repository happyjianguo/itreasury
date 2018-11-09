<%
/**
 * 页面名称 ：l004-v.jsp
 * 页面功能 : 显示借款单位资料，如果是委托贷款也显示委托单位资料
 * 特殊说明 ：
 */
%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
	java.util.*,
	java.net.URLEncoder,
	com.iss.itreasury.loan.loancommonsetting.dataentity.*,
	com.iss.itreasury.loan.util.*,
	com.iss.itreasury.ebank.util.*,
	com.iss.itreasury.settlement.util.*,
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
		
 		String type=(String)request.getAttribute("type");
	    long loanType=Long.valueOf(type).longValue();
    	String loanTypeName=OBConstant.LoanInstrType.getName(loanType);
		boolean iswt=false;
    	boolean isyt=false;
    	boolean isCheck=true;
    	String action="";
    	long statusID=-1;
    	long wtClientID=-1;
    	String tmp="";
    	long loanID=-1;
		String headstr="";
		String midStr="";
    	
       	if (loanType==OBConstant.LoanInstrType.LOAN_WT){
			headstr="委托单位资料";
			midStr="委托单位:";
		}else if(loanType==OBConstant.LoanInstrType.ASSURE) {
			headstr="被担保人资料";
			midStr="被担保人:";
		}else{
			headstr="借款单位资料";
			midStr="借款单位:";
		}
       		
	
    	
    	tmp=(String)request.getAttribute("txtAction");
    	if ( (tmp!=null)&&(tmp.length()>0) )
    		action=tmp;
    		
		tmp=(String)request.getAttribute("nStatusID");
    	if ( (tmp!=null)&&(tmp.length()>0) )
	    	statusID=Long.valueOf(tmp).longValue();
	    	
		tmp=(String)request.getAttribute("lLoanID");
    	if ( (tmp!=null)&&(tmp.length()>0) )
	    	loanID=Long.valueOf(tmp).longValue();	    	
		
    	long[] QTClientID={-1,-1,-1};
	    String[] QTClientName=new String[3];
    	float[] QTScale =new float[3];
    	String[] QTCardNo=new String[3];
    	String[] QTPwd=new String[3];
    	String tempStr="";
    	long tempLong=-1;
    
        //显示文件头
        if ( (action.equals("modify"))||(action.equals("check")) )
        	OBHtml.showOBHomeHead(out,sessionMng,"[客户基本资料]",Constant.YesOrNo.NO);
        else
        	OBHtml.showOBHomeHead(out,sessionMng,"[客户基本资料]",Constant.YesOrNo.YES);
        	
        //if ( action.equals("check") )
        	isCheck=true;	
        ClientInfo cInfo= (ClientInfo) request.getAttribute("ClientInfo");
        
        if ( cInfo!=null )
        {
    		QTClientName=cInfo.getQTClientName();
			QTScale=cInfo.getFQTScale();
			QTCardNo=cInfo.getQTCardNo();
			QTPwd=cInfo.getQTPwd();
		}	
		
		boolean isdq=false;
%>	

<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/Control.js"></script>
<safety:resources />
<form name="frm" method="post">
<TABLE border=0 class=top height=330 width="49%">
  <TR class="tableHeader">
      <TD class=FormTitle height=29><B><%=loanTypeName%>--新增</B></TD>
    </TR>
  <TR>
    <TD>
    <%  
    	String CustomUrl = "/common/ShowClientInfo.jsp?ClientID="+String.valueOf(sessionMng.m_lClientID);
    	System.out.println("------@@@@@@@@@@@@###########-------------->"+CustomUrl);
    %>
   	 <jsp:include page='<%=CustomUrl%>' />
<!--
      <TABLE cellPadding=0 border=0 width=730 >

          <TR> 
            <TD colSpan=7 > 
            </TD>
          </TR>
<%if ( Config.getBoolean(ConfigConstant.LOAN_CLIENT_REPORT,false) ){%>	
         <tr>
			<td colspan=5 height="32"> 
				<% if (action.equals("modify")) {%>
				<input class=button name="financialDCB" type=button value="财务情况统计表"  onClick="Javascript:window.open('../../content/c201-c.jsp?control=view&lClientID=<%=cInfo.getClientID()%>&PageName=c211-v.jsp&isSM=<%=Constant.YesOrNo.NO%>&lIsReadOnly=<%=Constant.YesOrNo.YES%>','','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes');" onKeydown="if(event.keyCode==13) Javascript:window.open('../contractcontent/c201-c.jsp?control=view&lClientID=<%=cInfo.getClientID()%>&PageName=c211-v.jsp&isSM=<%=Constant.YesOrNo.NO%>&lIsReadOnly=<%=Constant.YesOrNo.YES%>','','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes');">
				<% }else if (action.equals("check")) {%>
				<input class=button name="financialDCB" type=button value="财务情况统计表"  onClick="Javascript:window.open('../../content/c201-c.jsp?control=view&lClientID=<%=cInfo.getClientID()%>&PageName=c211-v.jsp&isSM=<%=Constant.YesOrNo.NO%>&lIsReadOnly=<%=Constant.YesOrNo.YES%>','','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes');" onKeydown="if(event.keyCode==13) Javascript:window.open('../contractcontent/c201-c.jsp?control=view&lClientID=<%=cInfo.getClientID()%>&PageName=c211-v.jsp&isSM=<%=Constant.YesOrNo.NO%>&lIsReadOnly=<%=Constant.YesOrNo.YES%>','','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes');">
				<% }else{ %>
				<input class=button name="financialDCB" type=button value="财务情况统计表"  onClick="Javascript:window.open('../../content/c201-c.jsp?control=view&lClientID=<%=cInfo.getClientID()%>&PageName=c211-v.jsp&isSM=<%=Constant.YesOrNo.NO%>&lIsReadOnly=<%=Constant.YesOrNo.NO%>','','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes');" onKeydown="if(event.keyCode==13) Javascript:window.open('../contractcontent/c201-c.jsp?control=view&lClientID=<%=cInfo.getClientID()%>&PageName=c211-v.jsp&isSM=<%=Constant.YesOrNo.NO%>&lIsReadOnly=<%=Constant.YesOrNo.NO%>','','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes');">
				<%}%>
			</td>
			<td>&nbsp; </td>
		</tr>
<%}%> 
        </table>
-->
        <TABLE cellPadding=0 height=50 width=720>
        <TR style="HEIGHT: 31.5pt">
          <TD height=19 width=10>&nbsp;</TD>
          <TD colSpan=3 height=19>  <HR align=center SIZE=2 width="100%">
          </TD> 
        </TR>
        <TR style="HEIGHT: 31.5pt">
          <TD height=9 width=10>
            <P class=MsoNormal>&nbsp;</P></TD>
          <TD height=9 width=529>
            <P align=right class=MsoNormal></P></TD>
          <TD colSpan=-3 height=9 width=258>
            <P align=right class=MsoNormal>
			<% if (action.equals("modify")) {%>
					<INPUT class=button name="xybClose"  onclick="confirmClose()" type="button" value=" 关 闭 "> 
			<%  	if (statusID==OBConstant.LoanInstrStatus.SAVE){ %>
					<INPUT class=button name="xyb"  onclick="confirmSave()" type="button" value=" 完 成 "> 
			<%		}else if(statusID==OBConstant.LoanInstrStatus.SUBMIT) {    %>
					<INPUT class=button name="xyb"  onclick="confirmSave()" type="button" value=" 修改并提交 "> 
			<%		}else if(statusID==OBConstant.LoanInstrStatus.ACCEPT) {    %>
					<INPUT class=button name="subnext"  onclick="confirmSave(frm)" type="button" value=" 修改并提交 "> 				
			<%      }         %>				
			<%  }
				else if (action.equals("check"))
				{
			%>
			<INPUT class=button name="xybClose"  onclick="confirmClose()" type="button" value=" 关 闭 ">
			<%		
				}else { 
			%>
            <INPUT class=button name="xyb" onfocus="nextfield='submitfunction';" onclick="confirmSave()" type="button" value=" 下一步 "> 
            <% } %>
            </p>
          </TD>
          <TD height=9 width=12>
            <P align=right class=MsoNormal >&nbsp;</P>
          </TD>
        </TR>
        </TABLE>
      </TD>
      </TR>
      </TABLE>
		  <input type=hidden name="lLoanType" value="<%=type%>">
		  <input type=hidden name="lClientID" value="<%=cInfo.getClientID()%>">
		  <input type=hidden name="wtClientID" value="<%=sessionMng.m_lClientID%>">
		  <input type=hidden name="lLoanID" value="<%=loanID%>">
		  <input type=hidden name="commandKey" value="<%=sessionMng.nextInterval()%>">
  </form>	  
	
<script language="javascript">
function confirmClose()
{
	window.close();
}
function getAllScale()
{
	var f1=0,f2=0,f3=0,f4=0 ;
	if(frm.ckgscale.value > 0) f1= frm.ckgscale.value;
	if(frm.cqtscale0.value > 0) f2 = frm.cqtscale0.value; 
	if(frm.cqtscale1.value > 0) f3 = frm.cqtscale1.value; 
	if(frm.cqtscale2.value > 0) f4 = frm.cqtscale2.value; 
	
	if((eval(f1) + eval(f2) + eval(f3) + eval(f4))>100)
	{
		alert("股东比例之和不能超过100%");
		return false;
	}
	return true;
}
function  confirmSave()
{
/*		
	    //财务公司账号 caccount
		if(frm.caccount.value!="")
		{
			if (!checkMagnifier("frm","caccountid","caccount","财务公司账号"))
			return false;
		}
	    //结算客户分类 lSettClientTypeID
	    if (frm.lSettClientTypeID.value=="-1")
		{
		  alert("请选择结算客户分类");
		  frm.lSettClientTypeID.focus();
		  return false;
		}
	    
	    //贷款卡号
		if (!InputValid(frm.loancardno, 1, "string", 0, 0, 0,"贷款卡号")) 
		{
			return false;
		}
		/*
		//贷款卡密码
		if (!InputValid(frm.loancardpwd, 1, "string", 0, 0, 0,"贷款卡密码")) 
		{
			return false;
		}
		*/
		// 省 cprovince
		/*if (!checkString(frm.cprovince,"省")) 
		{
			return false;
		} 
	    //市 ccity
	    if (!checkString(frm.ccity,"城市")) 
		{
			return false;
		} 
	    //地址 caddress
	    if (!checkString(frm.caddress,"地址")) 
		{
			return false;
		}
		
	    
		//银行账号 cbankaccount1
		
		//电话	cphone
		if (!InputValid(frm.cphone, 0, "fax", 0, 0, 0,"电话")) 
		{
			return false;
		}
		//传真  cfax
		if (!InputValid(frm.cfax, 0, "fax", 0, 0, 0,"传真")) 
		{
			return false;
		}
		
			
		//邮编 czipcode
		if (!InputValid(frm.czipcode, 0, "zip", 0, 0, 0,"邮编")) 
		{
			return false;
		}
		//电子邮件 cmail
		if (!InputValid(frm.cmail, 0, "email", 0, 0, 0,"电子邮件")) 
		{
			return false;
		}
		
		//企业类型 ccorpnatureid
		if (frm.ccorpnatureid.value=="-1")
		{
		  alert("请选择企业类型");
		  return false;
		}
		
		//上级主管单位
		if (!checkMagnifier("frm","cparentcorpid","ParentCorpName","上级主管单位"))
			return false;
		
		//股东 isShareHolder
		if (frm.isShareHolder.value=="-1")
		{
		  alert("请选择是否为股东");
		  return ;
		}
		
		//客户分类 lClientTypeID
		if (frm.lClientTypeID.value=="-1")
		{
		  alert("请选择自营贷款客户分类");
		  frm.lClientTypeID.focus();
		  return false;
		}*/
		//信用等级 lCreditLevel
		
		//机组容量 cgenerator
		
		<%if (!isdq){%>
		//注册资本金
	    /*if (!checkAmount(frm.ccaptial, 1, "注册资本金")) 
		{
			return false;
		}
		
		//经营范围 cdealscope
		if (!InputValid(frm.cdealscope, 1, "string", 0, 0, 0,"经营范围")) 
		{
				return false;
		}
		
		//股东单位 ckgclientname
		if (!checkMagnifier("frm","ckgclientid","ckgclientname","股东单位编号"))
			return false;
		
		//持股比例 ckgscale
		if (!InputValid(frm.ckgscale,1,"float",1,0,100,"股东单位持股比例") )
		{
			return false;
		}
		
				
		//持股卡号 ckgcardno
		if (!InputValid(frm.ckgcardno, 1, "string", 0, 0, 0,"持股卡号")) 
		{
				return false;
		}*/
		//卡密码  ckgpwd
		/*
		if (!InputValid(frm.ckgpwd, 1, "string", 0, 0, 0,"卡密码")) 
		{
				return false;
		}
		*/
		<%}else{%>
		//注册资本金
	    /*if (!InputValid(frm.ccaptial, 0, "string", 1, 1, 100,"注册资本金")) 
		{
			return false;
		}
		
		//经营范围 cdealscope
		if (!InputValid(frm.cdealscope, 0, "string", 0, 0, 0,"经营范围")) 
		{
				return false;
		}
		
		//持股比例 ckgscale
		if (!InputValid(frm.ckgscale,0,"float",1,0,100,"持股单位持股比例") )
		{
			return false;
		}
		
				
		//持股卡号 ckgcardno
		if (!InputValid(frm.ckgcardno, 0, "string", 0, 0, 0,"持股卡号")) 
		{
				return false;
		}*/
		/*
		//卡密码  ckgpwd
		if (!InputValid(frm.ckgpwd, 0, "string", 0, 0, 0,"卡密码")) 
		{
				return false;
		}
		*/
		<%}%>
		//其它股东单位1 cqtclientname0
		
		//其它比例1 cqtscale0
		/*if(isFloat(frm.cqtscale0) && frm.cqtscale0.value <=100)
		{
			if (!InputValid(frm.cqtscale0,0,"float",1,0,100,"其他单位1持股比例") )
			{
				return false;
			}
		}
		else
		{
			alert("请输入正确的其他单位1持股比例");
			return false;
			frm.cqtscale0.focus();
		}
				
		//其它卡号1 cqtcardno0
		
		//其它密码1 cqtpwd0
		
		//其它股东单位2 cqtclientname1
		
		//其它比例2 cqtscale1
		if(isFloat(frm.cqtscale1) && frm.cqtscale1.value <=100)
		{
			if (!InputValid(frm.cqtscale1,0,"float",1,0,100,"其他单位2持股比例") )
			{
				return false;
			}
		}
		else
		{
			alert("请输入正确的其他单位2持股比例");
			return false;
			frm.cqtscale1.focus();
		}
		//其它卡号2 cqtcardno1
		
		//其它密码2 cqtpwd1
		
		//其它股东单位3 cqtclientname2
		
		//其它比例3 cqtscale2
		if(isFloat(frm.cqtscale2) && frm.cqtscale2.value <=100)
		{
			if (!InputValid(frm.cqtscale2,0,"float",1,0,100,"其他单位3持股比例") )
			{
				return false;
			}
		}
		else
		{
			alert("请输入正确的其他单位3持股比例");
			return false;
			frm.cqtscale2.focus();
		}
		//其它卡号3 cqtcardno2
		
		//其它密码3 cqtpwd2
	    
		//检测四个股东比例之和大于100
		
		if ( !getAllScale() )	return false;	*/		
			
		showSending();
		<% if (action.equals("modify")) {%>
		frm.action="l003-c.jsp";
		<% }else{ %>		
		frm.action="l005-c.jsp";
		<% } %>
		frm.submit();
	}
	
			<% //if (action.equals("modify")) {%>
			//firstFocus(frm.xybClose);
			<%  //}
				//else if (action.equals("check"))
				//{
			%>
			//firstFocus(frm.xybClose);		
			<%		
				//}else { 
			%>
			//firstFocus(frm.caccount);
            <% //} %>
    
//setSubmitFunction("confirmSave(frm)");
setFormName("frm");	
</script>	

<script language="javascript">
	function  getAccount(account)
	{
		var sql="select ID,SACCOUNTNO from sett_account where nStatusID=<%=Constant.RecordStatus.VALID%> and nClientID=<%=cInfo.getClientID()%> order by id";
		return sql;
	}
	function  getClient(cname)
	{
		var sql = "SELECT id,sCode,sName,SLOANCARDNO,SLOANCARDPWD FROM client where nStatusID=<%=Constant.RecordStatus.VALID%> order by sCode";
		
		return sql ;
		
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


