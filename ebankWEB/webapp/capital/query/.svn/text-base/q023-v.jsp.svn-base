<%--
 页面名称 ：v205.jsp
 页面功能 : 利息费用匡算页面
 作    者 ：xirenli
 日    期 ：2003-12-01
 特殊说明 ：简单实现说明：
				1、
 修改历史 ：rxie 修改打印格式
--%>
<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTMagnifier"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTHTML"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ page import="com.iss.itreasury.evoucher.util.VOUConstant.EBankDocRiht" %>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.bizlogic.OBAccountSignatureBiz" %>
<%@ page import="java.util.*" %>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<%@ page import="com.iss.itreasury.settlement.interest.dataentity.InterestEstimateQueryResultInfo"%>
<%@ page import="com.iss.itreasury.settlement.interest.dataentity.InterestEstimateQueryInfo"%>
<%//@ page import="com.iss.itreasury.settlement.print.IPrintTemplate"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.eBankPrint"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<script language="JavaScript" src="/websett/js/Control.js" ></script>
<script language="JavaScript" src="/websett/js/SettCheck.js"></script>
<script language="JavaScript" src="/websett/js/MagnifierSQL.js"></script>

<safety:resources />

<!--jsp:include page="/ShowMessage.jsp"/-->

<% String strContext = request.getContextPath();%>
<%
//IPrintTemplate.showPrintHead(out,true,"A4","",1,sessionMng.m_lOfficeID);
eBankPrint.showPrintReport(out,sessionMng,"A4",2,false);
%>
<%
        		
		//签章相关设置//////////////////////////////////////////////////// add by zhanglei 2010-05-20
     	String basePath = request.getScheme() +"://"+request.getServerName()+":"+request.getServerPort();//服务器端请求路径
		String nowDate = Env.getSystemDateTimeString();//当前日期
		String officeName = Env.getClientName();//办事处名称
		//查看是否有签章权限--start----
		long officeId = sessionMng.m_lOfficeID;
		long clientId = sessionMng.m_lClientID;
		long bzid = sessionMng.m_lCurrencyID;
		long userId = sessionMng.m_lUserID;
		//String nbilltypeId = EBankDocRiht.ebankDocType[7][0];
		String nbilltypeId = String.valueOf(EBankDocRiht.YFLX);
		OBAccountSignatureBiz osb  =new OBAccountSignatureBiz();
		boolean hasRight = osb.checkHasRight(clientId,officeId,bzid,nbilltypeId,userId);
		//查看是否有签章权限--end----
		double px=300;//横坐标
		double py = 200;//纵坐标
		//////////////////////////////////////////////////////////////
try
{
	long lPageLine = 28;
	int sid = 1;
	int sigId=0;
	long lLine = 1;
	Collection resultColl = (Collection)request.getAttribute("searchResults");
	Iterator itResult = null;

	if(resultColl != null)
	{
		itResult = resultColl.iterator();
	}
	 String strAccountNo = "";                       //账号
	 String strClientName = "";                      //客户名称
	 String strBillNo = "" ;                          //票据号
     String strStartDate = "&nbsp;";                   //开始日期
     String strEndDate = "&nbsp;";                     //结束日期
     String strDays = "";                                //天数
     String strAmount = "";                         //本金
     String strRate = "";                    //利率
     String strInterest = "";                        //利息
	 String strInterestSort = "";                   //利息种类
%>
	<form name="frmV205" method="post" action="../control/c201.jsp">
		<input name="ActionID" type="hidden" value="<%=new java.util.Date().getTime()%>">
		
		<DIV align=center>
<table width="660" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td colspan="3" align="center"><b><font style="font-size:21px"><%=Env.getClientName()%></font></b></td>
	</tr>
	<tr>
		<td width="15%">&nbsp;</td>
		<td width="70%" align="center"><b><font style="font-size:22px">利息费用匡算表</font></b></td>
		<td width="15%">&nbsp;</td>
	</tr>
</TABLE>
<table width="660" border="0">
	<tr>
		<td width="50%">开户机构:<%=sessionMng.m_strOfficeName%></td>
		<td width="50%" align="right">币种：<%=Constant.CurrencyType.getName(sessionMng.m_lCurrencyID)%></td>
	</tr>
</table>
      <table width="660" border="0" class="table1" cellspacing="0" cellpadding="0">
        <TR>
          <TD class="td-right" noWrap width=8% height=20>
            <DIV align=center>账号</DIV></TD>
          <TD class="td-right" noWrap width=8% >
            <DIV align=center>客户名称</DIV></TD>
          <TD class="td-right" noWrap width=12% >
            <DIV align=center>单据号</DIV></TD>
          <TD class=td-right noWrap width=6% >
            <DIV align=center>开始日期</DIV></TD>
          <TD class=td-right noWrap width=6% >
            <DIV align=center>结束日期</DIV></TD>
          <TD class=td-right width="5%">
            <DIV align=center>天数</DIV></TD>
          <TD class=td-right noWrap width=10% >
            <DIV align=center>本金</DIV></TD>
          <TD class=td-right width="5%" >
            <DIV align=center>利率</DIV></TD>
          <TD class=td-right noWrap width="6%">
            <DIV align=center>利息/费用</DIV></TD>
          <TD class=td-right noWrap width="5%" >
            <DIV align=center>种类</DIV></TD>
		</TR>
<%  
	int printDateIndex=0;
    if(itResult != null && itResult.hasNext())
	{
		while(itResult.hasNext())
		{
					//分页
					lLine ++;
					sid++;
					printDateIndex++;
					if(lLine > lPageLine)
					{
					%>
</table>
  	<TABLE width="660" border="0">
		<TR>
			<TD align="left">操作人：<%=sessionMng.m_strUserName%></TD>
			<TD align="right"  id="<%="getSinWidthEle_"+printDateIndex%>">打印时间：<%=Env.getSystemDateString(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%></TD>
		</TR>
	</TABLE>
<br clear=all style='page-break-before:always'>
<table width="660" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td colspan="3" align="center"><b><font style="font-size:21px"><%=Env.getClientName()%></font></b></td>
	</tr>
	<tr>
		<td width="15%">&nbsp;</td>
		<td width="70%" align="center"><b><font style="font-size:22px">利息费用匡算表</font></b></td>
		<td width="15%">&nbsp;</td>
	</tr>
</TABLE>
<table width="660" border="0">
	<tr>
		<td width="50%">开户机构:<%=sessionMng.m_strOfficeName%></td>
		<td width="50%" align="right">币种：<%=Constant.CurrencyType.getName(sessionMng.m_lCurrencyID)%></td>
	</tr>
</table>
      <table width="660" border="0" class="table1" cellspacing="0" cellpadding="0">
        <TR>
          <TD class="td-right" noWrap width=8% height=20>
            <DIV align=center>账号</DIV></TD>
          <TD class="td-right" noWrap width=8% >
            <DIV align=center>客户名称</DIV></TD>
          <TD class="td-right" noWrap width=12% >
            <DIV align=center>单据号</DIV></TD>
          <TD class=td-right noWrap width=6% >
            <DIV align=center>开始日期</DIV></TD>
          <TD class=td-right noWrap width=6% >
            <DIV align=center>结束日期</DIV></TD>
          <TD class=td-right width="5%">
            <DIV align=center>天数</DIV></TD>
          <TD class=td-right noWrap width=10% >
            <DIV align=center>本金</DIV></TD>
          <TD class=td-right width="5%" >
            <DIV align=center>利率</DIV></TD>
          <TD class=td-right noWrap width="6%">
            <DIV align=center>利息/费用</DIV></TD>
          <TD class=td-right noWrap width="5%" >
            <DIV align=center>种类</DIV></TD>
		</TR>
					<%
						lLine = 1;
					}
					//分页End
			
			InterestEstimateQueryResultInfo resultInfo = (InterestEstimateQueryResultInfo )itResult.next();
			
			strAccountNo = DataFormat.formatEmptyString(resultInfo.getAccountNo());			
			strClientName = DataFormat.formatEmptyString(resultInfo.getClientName());			
			strBillNo = DataFormat.formatString(resultInfo.getContractNo())+"(" + DataFormat.formatString(resultInfo.getPayFormNo()) + ")";
			if(resultInfo.getStartDate() != null)
			{
				strStartDate = DataFormat.formatString(DataFormat.formatDate(resultInfo.getStartDate()));
			}
			else
			{
				strStartDate = "&nbsp;";
			}
			if(resultInfo.getEndDate() != null)
			{
				strEndDate = DataFormat.formatString(DataFormat.formatDate(resultInfo.getEndDate()));
			}
			else
			{
				strEndDate = "&nbsp;";
			}
			if(resultInfo.getDays()>=0)
			{
				strDays = String.valueOf(resultInfo.getDays());
			}
			strAmount = DataFormat.formatDisabledAmount(DataFormat.formatDouble(resultInfo.getInterestBalance(),2),2);
			strRate = DataFormat.formatDisabledAmount(DataFormat.formatDouble(resultInfo.getRate(),2),2);
				
			if(resultInfo.getInterestType()==SETTConstant.InterestFeeType.INTEREST)
			{
				strInterestSort="利息";				
				strInterest = DataFormat.formatDisabledAmount(DataFormat.formatDouble(resultInfo.getInterest(),2),2);
			}
			if(resultInfo.getInterestType()==SETTConstant.InterestFeeType.COMMISION)
			{
				strInterestSort="手续费";				
				strInterest = DataFormat.formatDisabledAmount(DataFormat.formatDouble(resultInfo.getCommission(),2),2);
			}
			if(resultInfo.getInterestType()==SETTConstant.InterestFeeType.ASSURE)
			{
				strInterestSort="担保费";				
				strInterest = DataFormat.formatDisabledAmount(DataFormat.formatDouble(resultInfo.getAssuranceCharge(),2),2);
			}
			if(resultInfo.getInterestType()==SETTConstant.InterestFeeType.COMPOUNDINTEREST)
			{
				strInterestSort="复利";		
				strInterest = DataFormat.formatDisabledAmount(DataFormat.formatDouble(resultInfo.getCompoundInterest(),2),2);		
			}
			if(resultInfo.getInterestType()==SETTConstant.InterestFeeType.FORFEITINTEREST)
			{
				strInterestSort="罚息";				
				strInterest = DataFormat.formatDisabledAmount(DataFormat.formatDouble(resultInfo.getForfeitInterest(),2),2);
			}
%>		
       <tr>				
	              <TD class="td-topright" height="20" nowrap><%= strAccountNo%></TD>
				  <TD class="td-topright" height="20" ><%= strClientName%></TD>
	              <TD class="td-topright" height="20" nowrap><%= strBillNo%></TD>
	              <TD class="td-topright" height="20" nowrap><%= strStartDate%></TD>
	              <TD class="td-topright" height="20" nowrap><%= strEndDate%></TD>
	              <TD class="td-topright" height="20" align="center"><%= strDays%></TD>
	              <TD class="td-topright" height="20" nowrap align="right"><%= strAmount%></TD>
				  <TD class="td-topright" height="20" nowrap align="right"><%= strRate%></TD>
	              <TD class="td-topright" height="20" nowrap align="right"><%= strInterest%></TD>
	              <TD class="td-top" height="20" nowrap align="center"><%= strInterestSort%></TD>
		</tr>
		<%
		if((sid+3)%lPageLine==0||sid==(resultColl.size()-2)){
			sigId++;
		 %>
		  <TR  id="signaturePosition__<%=sigId%>">    
          <TD class="td-topright"  height=0></TD>
          <TD class="td-topright"  height=0></TD>
          <TD class="td-topright"  height=0></TD>
          <TD class="td-topright"  height=0></TD>
          <TD class="td-topright"  height=0></TD>
          <TD class="td-topright"  height=0></TD>
          <TD class="td-topright"  height=0></TD>
          <TD class="td-topright"  height=0></TD>
          <TD class="td-topright"  height=0></TD>    
		  <TD class="td-top"  height=0></TD>    
		  </TR>
<%  }
		}
	}
	else
	{	
%>		
		<TR id="signaturePosition__1">    
          <TD class="td-topright"  height=0></TD>
          <TD class="td-topright"  height=0></TD>
          <TD class="td-topright"  height=0></TD>
          <TD class="td-topright"  height=0></TD>
          <TD class="td-topright"  height=0></TD>
          <TD class="td-topright"  height=0></TD>
          <TD class="td-topright"  height=0></TD>
          <TD class="td-topright"  height=0 ></TD>
          <TD class="td-topright"  height=0></TD>    
		  <TD class="td-top"  height=0></TD>    
		  </TR>
        <TR>    
          <TD class="td-topright" noWrap height=20>&nbsp;</TD>
          <TD class="td-topright" noWrap height=20>&nbsp;</TD>
          <TD class="td-topright" noWrap height=20>&nbsp;</TD>
          <TD class="td-topright" noWrap height=20>&nbsp;</TD>
          <TD class="td-topright" noWrap height=20>&nbsp;</TD>
          <TD class="td-topright" noWrap height=20>&nbsp;</TD>
          <TD class="td-topright" noWrap height=20>&nbsp;</TD>
          <TD class="td-topright" noWrap height=20>&nbsp;</TD>
          <TD class="td-topright" noWrap height=20>&nbsp;</TD>    
		  <TD class="td-top" noWrap height=20>&nbsp;</TD>    
		  </TR>
	<%
		}
	%>		  
           </TABLE>
   	<TABLE width="660" border="0">
		<TR>
			<TD align="left">操作人：<%=sessionMng.m_strUserName%></TD>
			<TD  id="<%="getSinWidthEle_"+(printDateIndex==0?1:printDateIndex)%>" align="right">打印时间：<%=Env.getSystemDateString(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%></TD>
		</TR>
	</TABLE>
           </DIV>
	</form>

<%@ include file="/common/SignValidate.inc" %>

<BODY language="javascript"   style="margin-top:0;margin-bottom:0;padding-top:0;padding-bottom:0">
<script type="text/javascript">
	function reSetSigPosition(c){
			window.scrollTo(0,0);
		for(var i=c;i>=1;i--){
			var o = document.getElementById("SignatureControl_"+i);
			var sx;
			var sy;
			var oRect   =  document.getElementById("signaturePosition__"+i).getBoundingClientRect();   
			var width = document.getElementById("signaturePosition__"+i).clientWidth;
			x=oRect.left   
			y=oRect.top  
			sy=parseInt(y);
			sx = (parseInt(x)+parseInt(width)-120);
			o.MovePositionToNoSave(sx,sy); 
		}
	}

</script>
<%
if(hasRight){
	for(int z=1;z<=sigId;z++){
 %>	
<OBJECT id="SignatureControl_<%=z %>"  classid="clsid:D85C89BE-263C-472D-9B6B-5264CD85B36E" width=0 height=0>
<param name="ServiceUrl" value="<%=basePath%>/NASApp/SignatureServlet">                       <!--读去数据库相关信息-->
<param name="WebAutoSign" value="0">                     <!--是否自动数字签名(0:不启用，1:启用)-->
<param name="PrintControlType" value=2>                  <!--打印控制方式（0:不控制  1：签章服务器控制  2：开发商控制）-->
<param name="MenuDocVerify" value=false>                 <!--菜单文档验证文档-->
<param name="MenuServerVerify" value=false>              <!--菜单在线验证,网络版本专用-->
<param name="MenuDigitalCert" value=false>               <!--菜单数字证书-->
<param name="MenuDocLocked" value=false>                 <!--菜单文档锁定-->
<param name="MenuDeleteSign" value=false>                <!--菜单撤消签章-->
<param name="MenuMoveSetting" value=true>                <!--菜单禁止移动-->
<param name="PrintWater" value=false>                    <!--是否打印水印-->
</OBJECT>
</BODY>
<script language="javascript">
		var o = document.getElementById("SignatureControl_<%=z %>");
		if(o!=null||o!=undefined){
		try{
			var sx;
			var sy;
			var oRect   =   document.getElementById("signaturePosition__<%=z %>").getBoundingClientRect();   
			var width = document.getElementById("signaturePosition__<%=z %>").clientWidth;
			x=oRect.left   
			y=oRect.top  
			sy=parseInt(y);
			sx = (parseInt(x)+parseInt(width)-120);
		    o.EnableMove = "false";          //设置签章是否可以移动
		    o.PassWord = "123456";           //签章密码,系统默认为"",当设置改参数后点签章后弹出的选择签章窗体中的密码将默认为该密码      
		    o.ShowSignatureWindow = "0";     //读取、设置选择签章的窗口是否可见
		    o.FieldsList = "strTransNos=组合业务编号";          //读取、设置签章保护信息列表
		    o.SaveHistory = "false";         //读取、设置是否保存历史记录true-false
		    o.UserName = "<%=Env.getClientName()%>"; //读取、设置签章的用户名称
		    //o.DivId = oPageSet.showSignatureName;          //读取、设置签章所在的层
		   // o.SetPositionRelativeTag("signaturePosition__<%=z %>",0);        //设置签章什么位置在Position处(0:左上角、1:中间、2:右上角)
		    o.PositionByTagType = 0;
		    o.Position(sx,sy); 
		    o.ValidateCertTime = false;      //设置是否验证证书过期或未到期
		    o.ExtParam = "11111111|11";//transNo
		    o.ExtParam1 = "<%=nowDate%>";          //设置签章附加信息
		    //o.SetWaterInfo("网络专用","隶书",0X0000FF,0);//设置签章数字水印信息
		    o.WebSetFontOther(true,"","0","宋体",7,"$0000FF",false);//设置签章图案附属信息(日期时间、签章人员、意见等)显示模式
		    o.DefaultSignTimeFormat = 8;    //设置签章附加时间默认格式
		    o.SetSignTextOffset(0,30);      //设置盖章是的附加信息(包括时间)的偏移量
		  }catch(e){
		    alert(e);
		  }
		    try{
		    	o.RunSignature();               //执行签章  
		    }catch(e){
		    	alert("添加签章错误，请联系开发人员");
		    }
		}
</script>
<%
	}
	}
	}
catch(Exception exp)
{
	Log.print(exp.getMessage());
}
%>