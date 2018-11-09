<%--
/*
 * 程序名称：客户对账单.jsp
 * 功能说明：账户余额查询页面下载
 * 作　　者：xiren li
 * 完成日期：2004-03-02
 */
--%>

<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.util.EJBObject"%>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dataentity.*" %>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dao.*"%>
<%@ page import="com.iss.itreasury.ebank.util.*" %>
<%@ page import="com.iss.itreasury.settlement.util.*" %>
<%@ page import="com.iss.itreasury.settlement.util.NameRef" %>
<%@ page import="com.iss.itreasury.settlement.print.IPrintTemplate" %>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%@ page import="com.iss.itreasury.evoucher.util.VOUConstant.EBankDocRiht" %>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.bizlogic.OBAccountSignatureBiz" %>
<%@ page import="java.util.*" %>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>


<style type="text/css">
<!--
.table1 {  border: 2px solid #000000}
.table2 {  border: 1px solid #000000}
.td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}
.td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px}
.td-leftright {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 1px}
.td-leftbottomright {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px}
.td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px}
.td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}
.td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px}
.td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px}
.td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}
.td-right2bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 2px; border-bottom-width: 1px; border-left-width: 0px}
.td-toprightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px}
.td-topleftbottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 1px}
.td-topright2bottom {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 2px; border-bottom-width: 1px; border-left-width: 0px}
.td-topleftright2bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 2px; border-bottom-width: 2px; border-left-width: 1px}
.td-topleftrightbottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 1px}
.td-topleftrightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px}
.td-topbottom {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px}
.td-topright {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}
body {
	font-family: "宋体";
	font-size: 14px;
}
td {
	font-family: "宋体";
	font-size: 12px;
}
.f16 {
	font-family: "楷体_GB2312";
	font-size: 16px;
}
.f14 {
	font-family: "宋体";
	font-size: 14px;
}
.f10 {
	font-family: "宋体";
	font-size: 10px;
	line-height:10px;
}
.f12 {
	font-family: "宋体";
	font-size: 12px;
}

.f22
{
  font-family:"黑体";
  font-size:22px;
}
.f15 {
	font-family: "楷体_GB2312";
	font-size: 16px;
}
.tnt {Writing-mode:tb-rl;Text-align:center;font-size:12px}
-->
</style>

<%
    //标题变量
    String strTitle = "［客户对账单］";
    
        long pageCount=1;		
		//签章相关设置//////////////////////////////////////////////////// add by zhanglei 2010-05-20
     	String basePath = request.getScheme() +"://"+request.getServerName()+":"+request.getServerPort();//服务器端请求路径
		String nowDate = Env.getSystemDateTimeString();//当前日期
		String officeName = Env.getClientName();//办事处名称
		//查看是否有签章权限--start----
		long officeId = sessionMng.m_lOfficeID;
		long clientId = sessionMng.m_lClientID;
		long bzid = sessionMng.m_lCurrencyID;
		long userId = sessionMng.m_lUserID;
		//String nbilltypeId = EBankDocRiht.ebankDocType[1][0];
		String nbilltypeId = String.valueOf(EBankDocRiht.ZHMX);
		OBAccountSignatureBiz osb  =new OBAccountSignatureBiz();
		boolean hasRight = osb.checkHasRight(clientId,officeId,bzid,nbilltypeId,userId);
		//查看是否有签章权限--end----
		double px=300;//横坐标
		double py = 200;//纵坐标
		//////////////////////////////////////////////////////////////
    
    try 
	{
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

        
        //从请求中获取查询结果信息
        Collection resultColl = (Collection)request.getAttribute("searchResults");
		Iterator itResult = null;
		if(resultColl != null)
		{
			itResult = resultColl.iterator();
		}
		 double[] MorY=new double[4];
		if(request.getAttribute("MorY")!=null){
		MorY = (double[])request.getAttribute("MorY");
		}else{
		MorY[0]=0;
		MorY[1]=0;
		MorY[2]=0;
		MorY[3]=0;
		
		}
        // 系统时间
        
        Timestamp dtSysDate = Env.getSystemDateTime();
%>
<%
	long lPageLine = 20;
	long lLine = 1;
	boolean bIsFirstPage = false;
		
    String strAccountNo = "";
	String strDepositNo="";
	long lAccountID =-1;	
	String strTemp = null;
	strTemp = (String)request.getAttribute("lAccountID");
	if (strTemp != null && strTemp.trim().length() > 0)
	{
	    lAccountID = Long.valueOf(strTemp).longValue();
	}
	strTemp = (String)request.getAttribute("strDepositNo");
	if (strTemp != null && strTemp.trim().length() > 0)
	{
	    strDepositNo = strTemp;
	}	
	strAccountNo = DataFormat.formatString(NameRef.getAccountNoByID(lAccountID));
	
	String strEarlyBanlance = ""; //期初余额
	double mEarlyBanlance = 0.0;
	double mEveryBanlance = 0.0;//每笔以后的余额
	String strExecuteDate = "";    //执行日
	strExecuteDate = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
	OBAccountBalanceQueryDao dao = new OBAccountBalanceQueryDao(); 
	mEarlyBanlance = dao.findEarlyBalance(lAccountID,com.iss.itreasury.settlement.util.UtilOperation.getNextNDay(Env.getSystemDate(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID),-1));
	mEveryBanlance = mEarlyBanlance;
	strEarlyBanlance=DataFormat.formatListAmount(mEarlyBanlance);
   		
    IPrintTemplate.showPrintHead(out,true,"A4","",1,sessionMng.m_lOfficeID);
%>
<safety:resources />
<table width="950" align="center" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td colspan="3" align="center"><b><font style="font-size:21px"><%=Env.getClientName()%>客户对账单</font></b></td>
	</tr>
</TABLE>
<table  width="950" align="center" border="0">
	<tr>
		<td width="20%">开户机构：<%=sessionMng.m_strOfficeName%></td>
		<td width="25%"></td>
		<td width="25%">账号：<%=NameRef.getNoLineAccountNo(strAccountNo.toString())%></td>
	</tr>
	<tr>
		<td width="50%">户名：<%=DataFormat.formatString(NameRef.getAccountNameByID(lAccountID))%></td>
		<td width="20%" id="signaturePosition_12" name = "signaturePosition_2" ></td>
		<td width="30%" id="signaturePosition_2" >币种：<%=Constant.CurrencyType.getName(sessionMng.m_lCurrencyID)%></td>
	</tr>
</table>
	
    <table  width="950" align="center" border="0" cellspacing="0" cellpadding="3" class=table1>
       <tr>
            <td align="center" width=9% class="td-rightbottom" >交易编号</td> 
         <td align="center" width=9% class="td-rightbottom" >交易类型</td> 
         <td align="center" width=9% class="td-rightbottom" >日期</td>        
         <td align="center" width=20% class="td-rightbottom" >摘要</td>
         <td align="center" width=15% class="td-rightbottom" >对方账号</td>							
		 <td align="center" width=13% class="td-rightbottom" >对方账户名称</td>
         <%--<td align="center"  class="td-rightbottom" nowrap>单据号</td>
         <td align="center"  class="td-rightbottom" nowrap>支票号</td>
         --%><td align="center" width=7%   class="td-rightbottom" >借方金额</td>
         <td align="center" width=7%   class="td-rightbottom" >贷方金额</td>        
         <td align="center" width=13%  class="td-rightbottom" >余额</td>
       </tr>  
	   <tr>
	             
	             <td class="td-rightbottom" height="20">
					<div align="center">&nbsp;
					</div>
				</td>
				<td class="td-rightbottom"  height="20">
					<div align="center">&nbsp;
					</div>
				</td>	
				<td class="td-rightbottom"  height="20">
					<div align="center"><%=strExecuteDate%>
					</div>
				</td>
				<td class="td-rightbottom"  height="20">
					<div align="center">期初余额
					</div>
				</td>
				<td class="td-rightbottom" height="20">
					<div align="center">&nbsp;
					</div>
				</td>
				<td class="td-rightbottom"  height="20">
					<div align="center">&nbsp;
					</div>
				</td>
				<%--<td class="td-rightbottom"nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>		
				--%><td class="td-rightbottom" height="20">
					<div align="center">&nbsp;
					</div>
				</td>
				<td class="td-rightbottom"  height="20">
					<div align="center">&nbsp;
					</div>
				</td>						
				<td class="td-rightbottom" height="20">
					<div align="right">
					<%=strEarlyBanlance%>
					</div>
				</td>				
			</tr>     
	<%
	if(itResult != null && itResult.hasNext())
	{		
		double dbtAmountSum=0.0;   //借方合计
		double cdtAmountSum=0.0;   //贷方合计
		double banlanceSum=0.0;    //余额合计
		while(itResult.hasNext())
		{
			OBAccountResultInfo info = (OBAccountResultInfo)itResult.next();
			
			String strTransNo = "";   //交易号
			String strTransactionType = "";  //交易类型	
			strDepositNo = "";	       //单据号
			String strAmount = "" ;         //金额
			strExecuteDate = "";    //交易日
			
			String strBillNo = "";    //凭证号/支票号

			String strDebitAmount = "" ;         //借方金额
			String strCreditAmount = "" ;         //贷方金额
			
			String strBanlance = "";  //余额
			String strAbstract = ""; //摘要
			
			String strOppAccountNo = "";   //对方账号
			String strOppAccountName = "";   //对方账户名称
			
			strOppAccountNo = NameRef.getAccountNoByID(info.getOppAccountID());
			
			strOppAccountName = DataFormat.formatString(NameRef.getAccountNameByID(info.getOppAccountID()));
			strBillNo = DataFormat.formatString(info.getBillNo());	
			if(info.getTransDirection()==SETTConstant.DebitOrCredit.DEBIT)
			{
				strDebitAmount=DataFormat.formatListAmount(info.getAmount());
				dbtAmountSum = dbtAmountSum+info.getAmount();
				mEveryBanlance = mEveryBanlance-info.getAmount();
			}
			if(info.getTransDirection()==SETTConstant.DebitOrCredit.CREDIT)
			{
				strCreditAmount=DataFormat.formatListAmount(info.getAmount());
				cdtAmountSum = cdtAmountSum+info.getAmount();
				mEveryBanlance = mEveryBanlance+info.getAmount();
			}
			strBanlance = DataFormat.formatListAmount(mEveryBanlance);			
			banlanceSum=info.getBalance();
			
			strTransNo = DataFormat.formatEmptyString(info.getTransNo());
			strTransactionType = DataFormat.formatString(SETTConstant.TransactionType.getName(info.getTransactionTypeID()));
			
			strDepositNo = DataFormat.formatString(info.getDepositNo());
			strAmount=DataFormat.formatListAmount(info.getAmount());
			strExecuteDate = DataFormat.formatString(DataFormat.formatDate(info.getExecuteDate()));		
			strAbstract = DataFormat.formatString(info.getAbstract());

			//分页
			lLine ++;		
			if(lLine > lPageLine)
			{
				pageCount++;
	%>
	</table>
	    <table  width="950" align="center" border="0" cellspacing="0" cellpadding="3">
      <tr>
	    <td align="left" >操作人：<%=sessionMng.m_strUserName%></td>
        <td align="right" id="<%="printDate_"+pageCount %>" >打印时间：<%=Env.getSystemDateString(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%></td>
      </tr>
    </table>
	<br clear=all style='page-break-before:always'>
<table  width="950" align="center" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td colspan="3" align="center"><b><font style="font-size:21px"><%=Env.getClientName()%>客户对账单</font></b></td>
	</tr>
</table>
<table  width="950" align="center" border="0">
	<tr>
		<td width="20%">开户机构:<%=sessionMng.m_strOfficeName%></td>
		<td width="25%"></td>
		<td width="25%">账号：<%=strAccountNo%></td>
	</tr>
	<tr>
		<td width="50%">户名：<%=DataFormat.formatString(NameRef.getAccountNameByID(lAccountID))%></td>
		<td width="20%"></td>
		<td width="30%">币种：<%=Constant.CurrencyType.getName(sessionMng.m_lCurrencyID)%></td>
	</tr>
		<tr>
		<td height=0 width="50%">&nbsp;</td>
		<td height=0 width="30%">&nbsp;</td>
		<td height=0 name="signaturePosition_2" width="20%">&nbsp;</td>
	</tr>
</table>
<table  width="950" align="center" border="0"   cellspacing="0" cellpadding="3" class=table1>
	<tr>
            <td align="center" width=9% class="td-rightbottom" >交易编号</td> 
         <td align="center" width=9% class="td-rightbottom" >交易类型</td> 
         <td align="center" width=9% class="td-rightbottom" >日期</td>        
         <td align="center" width=20% class="td-rightbottom" >摘要</td>
         <td align="center" width=15% class="td-rightbottom" >对方账号</td>							
		 <td align="center" width=13% class="td-rightbottom" >对方账户名称</td>
         <%--<td align="center"  class="td-rightbottom" nowrap>单据号</td>
         <td align="center"  class="td-rightbottom" nowrap>支票号</td>
         --%><td align="center" width=7%   class="td-rightbottom" >借方金额</td>
         <td align="center" width=7%   class="td-rightbottom" >贷方金额</td>        
         <td align="center" width=13%  class="td-rightbottom" >余额</td>
       </tr>
<%
	    		lLine = 1;
				}	
%>	   
	   
		<tr>    
		          <td class="td-rightbottom"  height="20">
					<div align="center">		
					<%=strTransNo.equals("") ? "&nbsp;" : strTransNo%>		
					</div>
				</td>
				<td class="td-rightbottom"   height="20">
					<div align="center">
					<%=strTransactionType.equals("") ? "&nbsp;" : strTransactionType%>
					</div>
				</td>
				   
				<td class="td-rightbottom"  height="20">
					<div align="center">		
					<%=strExecuteDate.equals("") ? "&nbsp;" : strExecuteDate%>		
					</div>
				</td>
				<td class="td-rightbottom"   height="20">
					<div align="left">
					<%=strAbstract.equals("") ? "&nbsp;" : strAbstract%>
					</div>
				</td>
				<td class="td-rightbottom"  height="20">
					<div align="center">
						<%=strOppAccountNo.equals("") ? "&nbsp;" : strOppAccountNo%>
					</div>
				</td>
				<td class="td-rightbottom"  height="20">
					<div align="left">
						<%=strOppAccountName.equals("") ? "&nbsp;" : strOppAccountName%>
					</div>
				</td>
				<%--<td class="td-rightbottom"  nowrap height="20">
					<div align="center">
					    <%=strDepositNo.equals("") ? "&nbsp;" : strDepositNo%>
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">
					    <%=strBillNo.equals("") ? "&nbsp;" : strBillNo%>
					</div>
				</td>
				--%><td class="td-rightbottom"  height="20">
					<div align="right">
					    <%=strDebitAmount.equals("") ? "0.00" : strDebitAmount%>
					</div>
				</td>	
				<td class="td-rightbottom"   height="20">
					<div align="right">
					    <%=strCreditAmount.equals("") ? "0.00" : strCreditAmount%>
					</div>
				</td>	
				<td class="td-rightbottom"   height="20">
					<div align="right">
					    <%=strBanlance.equals("") ? "&0.00;" : strBanlance%>
					</div>
				</td>				
			</tr>
		<%
		}
	%>	
		
			<tr>
			    <td class="td-rightbottom" nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">本日合计
					</div>
				</td>
				<td class="td-rightbottom"nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>
				<%--<td class="td-rightbottom"nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>		
				--%><td class="td-rightbottom"nowrap height="20">
					<div align="right"><%=DataFormat.formatListAmount(dbtAmountSum)%>
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="right"><%=DataFormat.formatListAmount(cdtAmountSum)%>
					</div>
				</td>						
				<td class="td-rightbottom"nowrap height="20">
					<div align="right"><%=DataFormat.formatListAmount(banlanceSum)%>
					</div>
				</td>				
			</tr>
<%--			<tr>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">本月合计--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom"nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>		--%>
<%--				<td class="td-rightbottom"nowrap height="20">--%>
<%--					<div align="right"><%=MorY[0] != 0 ? DataFormat.formatListAmount(MorY[0]):"0.00"%>--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="right"><%=MorY[1] != 0 ? DataFormat.formatListAmount(MorY[1]):"0.00"%>--%>
<%--					</div>--%>
<%--				</td>						--%>
<%--				<td class="td-rightbottom"nowrap height="20">--%>
<%--					<div align="right"><%=DataFormat.formatListAmount(banlanceSum)%>--%>
<%--					</div>--%>
<%--				</td>				--%>
<%--			</tr>--%>
<%--			<tr>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">本年合计--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom"nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>		--%>
<%--				<td class="td-rightbottom"nowrap height="20">--%>
<%--					<div align="right"><%=MorY[2] != 0 ? DataFormat.formatListAmount(MorY[2]):"0.00"%>--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="right"><%=MorY[3] != 0 ? DataFormat.formatListAmount(MorY[3]):"0.00"%>--%>
<%--					</div>--%>
<%--				</td>						--%>
<%--				<td class="td-rightbottom"nowrap height="20">--%>
<%--					<div align="right"><%=banlanceSum !=0 ? DataFormat.formatListAmount(banlanceSum):"0.00"%>--%>
<%--					</div>--%>
<%--				</td>				--%>
<%--			</tr>--%>
<%			
	}
	else
	{		
		%>		
			<tr>
			   <td class="td-rightbottom" nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">本日合计
					</div>
				</td>
				<td class="td-rightbottom"nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>
				<%--<td class="td-rightbottom"nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>		
				--%><td class="td-rightbottom"nowrap height="20">
					<div align="right">0.00
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="right">0.00
					</div>
				</td>						
				<td class="td-rightbottom"nowrap height="20">
					<div align="right"><%=strEarlyBanlance=="&nbsp;"?"0.00":strEarlyBanlance%>
					</div>
				</td>				
			</tr>
<%--			<tr>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">本月合计--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom"nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>		--%>
<%--				<td class="td-rightbottom"nowrap height="20">--%>
<%--					<div align="right"><%=MorY[0] != 0 ? DataFormat.formatListAmount(MorY[0]):"0.00"%>--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="right"><%=MorY[1] != 0 ? DataFormat.formatListAmount(MorY[1]):"0.00"%>--%>
<%--					</div>--%>
<%--				</td>						--%>
<%--				<td class="td-rightbottom"nowrap height="20">--%>
<%--					<div align="right"><%=strEarlyBanlance=="&nbsp;"?"0.00":strEarlyBanlance%>--%>
<%--					</div>--%>
<%--				</td>				--%>
<%--			</tr>--%>
<%--			<tr>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">本年合计--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom"nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>		--%>
<%--				<td class="td-rightbottom"nowrap height="20">--%>
<%--					<div align="right"><%=MorY[2] != 0 ? DataFormat.formatListAmount(MorY[2]):"0.00"%>--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="right"><%=MorY[3] != 0 ? DataFormat.formatListAmount(MorY[3]):"0.00"%>--%>
<%--					</div>--%>
<%--				</td>						--%>
<%--				<td class="td-rightbottom"nowrap height="20">--%>
<%--					<div align="right"><%=strEarlyBanlance=="&nbsp;"?"0.00":strEarlyBanlance%>--%>
<%--					</div>--%>
<%--				</td>				--%>
<%--			</tr>--%>
<%
	}
%>		
 </table>		
    <table  width="950" align="center" border="0" cellspacing="0" cellpadding="3">
      <tr>
	    <td align="left" >操作人：<%=sessionMng.m_strUserName%></td>
        <td align="right" id="printDate" >打印时间：<%=Env.getSystemDateString(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%></td>
      </tr>
    </table>
<script language= "javascript">
	//factory.printing.Print(true);
</script>
<%
    }
	catch (IException ie) 
	{
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    }
%>

<%@ include file="/common/SignValidate.inc" %>
<%

	if(hasRight){

 %>
<BODY language="javascript" onresize="ReSetSignaturePosition();" onbeforeprint="setPirntPosition();"    style="margin-top:0;margin-bottom:0;padding-top:0;padding-bottom:0">	
<OBJECT id="SignatureControl"  codebase="/websignature/cab/iSignatureHTML.cab#Version=7,1,0,196"  classid="clsid:D85C89BE-263C-472D-9B6B-5264CD85B36E" width="0" height="0">
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
		var oldScrollTop=0;
		try{
			//防止滚动条位置改变而影响签章的位置，置顶。
			document.body.scrollTop=0;
			var sx;
			var sy;
			oRect   =   document.getElementById("printDate").getBoundingClientRect();   
			var width = document.getElementById("printDate").clientWidth;
			var x=oRect.left   
			var y=oRect.top   
			sy=parseInt(y)-80;
			sx = (parseInt(x)+parseInt(width)-100);
		    document.all.SignatureControl.EnableMove = "false";          //设置签章是否可以移动
		    document.all.SignatureControl.PassWord = "123456";           //签章密码,系统默认为"",当设置改参数后点签章后弹出的选择签章窗体中的密码将默认为该密码      
		    document.all.SignatureControl.ShowSignatureWindow = "0";     //读取、设置选择签章的窗口是否可见
		    document.all.SignatureControl.FieldsList = "strTransNos=组合业务编号";          //读取、设置签章保护信息列表
		    document.all.SignatureControl.SaveHistory = "false";         //读取、设置是否保存历史记录true-false
		    document.all.SignatureControl.UserName = "<%=Env.getClientName()%>"; //读取、设置签章的用户名称
		    document.all.SignatureControl.PositionByTagType = 0;
		    document.all.SignatureControl.Position(sx,sy);  //设置签章的位置
		    document.all.SignatureControl.ValidateCertTime = false;      //设置是否验证证书过期或未到期
		    document.all.SignatureControl.ExtParam = "11111111|11";//transNo
		    document.all.SignatureControl.ExtParam1 = "<%=nowDate%>";          //设置签章附加信息
		    //document.all.SignatureControl.SetWaterInfo("网络专用","隶书",0X0000FF,0);//设置签章数字水印信息
		    document.all.SignatureControl.WebSetFontOther(true,"","0","宋体",7,"$0000FF",false);//设置签章图案附属信息(日期时间、签章人员、意见等)显示模式
		    document.all.SignatureControl.DefaultSignTimeFormat = 8;    //设置签章附加时间默认格式
		    document.all.SignatureControl.SetSignTextOffset(0,30);      //设置盖章是的附加信息(包括时间)的偏移量
		  }catch(e){
		    alert(e);
		  }
		    try{
		    	document.all.SignatureControl.RunSignature();               //执行签章  
		    }catch(e){
		    	alert("添加签章错误，请联系开发人员");
		    }
		    
	window.onafterprint=function(){
		setAfterPrintPosition();
	}
	function setPirntPosition(){
		    oldScrollTop=document.body.scrollTop;
			document.body.scrollTop=0;
			var sx;
			var sy;
			var pagecount="<%=pageCount%>";
			var oRect   =   document.getElementById("printDate").getBoundingClientRect();   
			var width = document.getElementById("printDate").clientWidth;
			x=oRect.left   
			y=oRect.top   
			sy=parseInt(y)-80+(parseInt(pagecount)-1)*200;
			sx = 901;
			document.all.SignatureControl.MovePositionToNoSave(sx,sy); 
	}
	
			    	//如果窗口大小变化了，签章的位置也要改变。  add by zhanglei  2010.06.11	    
	function ReSetSignaturePosition(){
			setAfterPrintPosition();
	}
	
	
	function getElementLeft(element){
		var actualLeft = element.offsetLeft;
		var current = element.offsetParent;
		
		while (current !== null){
			actualLeft += current.offsetLeft;
			current = current.offsetParent;
		}
		
	　　return actualLeft;
	}
	function setAfterPrintPosition(){
			var sx;
			var sy;
			oRect   =   document.getElementById("printDate").getBoundingClientRect();   
			var width = document.getElementById("printDate").clientWidth;
			x=oRect.left   
			y=oRect.top   
			sy=parseInt(y)-80;
			sx = (parseInt(x)+parseInt(width)-100);
			document.all.SignatureControl.MovePositionToNoSave(sx,sy); 
			document.body.scrollTop=oldScrollTop;
	}
</script>
<%
	}
%>

