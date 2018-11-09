<%--
/*
 * 程序名称：账户余额对账单.jsp
 * 功能说明：账户余额查询页面下载
 * 作　　者：liwang
 * 完成日期：2007-01-26
 */
--%>

<%@ page contentType="text/html;charset=gbk"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dao.OBAccountBalanceQueryDao"%>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dataentity.*"%>
<%@ page import="com.iss.itreasury.loan.util.LOANConstant"%>
<%@ page import="com.iss.itreasury.settlement.print.IPrintTemplate" %>
<%@ page import="com.iss.itreasury.evoucher.util.VOUConstant.EBankDocRiht" %>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.bizlogic.OBAccountSignatureBiz" %>
<%@ page import="java.util.*" %>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
	<%
	//	标题变量
    String strTitle = "［账户余额］";
    		
		//签章相关设置//////////////////////////////////////////////////// add by zhanglei 2010-05-20
     	String basePath = request.getScheme() +"://"+request.getServerName()+":"+request.getServerPort();//服务器端请求路径
		String nowDate = Env.getSystemDateTimeString();//当前日期
		String officeName = Env.getClientName();
		
		//查看是否有签章权限--start----
		long officeId = sessionMng.m_lOfficeID;
		long clientId = sessionMng.m_lClientID;
		long bzid = sessionMng.m_lCurrencyID;
		long userId = sessionMng.m_lUserID;
		int pagecount=1;
		//String nbilltypeId = EBankDocRiht.ebankDocType[0][0];
		String nbilltypeId = String.valueOf(EBankDocRiht.ZHYE);
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

		//定义变量  对应后台的DataEntity
		double currentSum = 0.0;      ///活期合计
		double depositSum = 0.0;     //存款余额合计
		double loanSum = 0.0;     //存款余额合计
		
		long lStatusID= -1; 
		
        //活期账户
        double crt_dAc_mcapitallimitamount = 0.0;
		double crt_dMbalance = 0.0;
		long crt_lNaccounttypeid = -1;
		String crt_strSaccountno = "";
		double crt_dSubSum = 0.0;
		double crt_dSum = 0.0;

		//活期账户页面链接
		long lQueryType=-1;
		long lPayAccountIDEndCtrl=-1;
		long lPayAccountIDStartCtrl=-1;
		long lReceiveAccountIDEndCtrl=-1;
		long lReceiveAccountIDStartCtrl=-1;


		//委托账户
		double cgn_dAc_mcapitallimitamount = 0.0;
		double cgn_dMbalance = 0.0;
		long cgn_lNaccounttypeid = -1;
		String cgn_strSaccountno = "";
		double cgn_dSubSum = 0.0;

		//定期账户
		java.sql.Timestamp fixed_tsAf_dtend = null;
		java.sql.Timestamp fixed_tsAf_dtstart = null;
		double fixed_dAf_mrate = 0.0;
		long fixed_lAf_ndepositterm = -1;
		double fixed_dMbalance = 0.0;
		double fixed_dMopenamount = 0.0;
		long fixed_lNaccounttypeid = -1;
		long fixed_lNstatusid = -1;
		String fixed_strSaccountno = "";
		double fixed_dSubSum = 0.0;
		double fixed_dSum = 0.0;
		long fixed_lNaccountID = -1;
		long fixed_lNtype=-1;

		//贷款账户
		java.sql.Timestamp loan_tsDtEndDate = null;
		java.sql.Timestamp loan_tsDtStartDate = null;
		double loan_dLoanBalance = 0.0;
		double loan_dMAmount = 0.0;
		long loan_lNaccounttypeid = -1;
		long loan_lNborrowclientid = -1;
		long loan_lNIntervalNum = -1;
		long loan_lNstatusid = -1;
		double loan_dRate = 0.0;
		String loan_strSaccountno = "";
		String loan_strSCONTRACTCODE = "";
		double loan_dSubSum = 0.0;

		//页面跳转变量
		String strFailPageURL="";
		String strSuccessPageURL="";

		//页面辅助变量
		String strAction = null;
		String strActionResult = Constant.ActionResult.FAIL;
		String strPreSaveResult = null;

		String strExecuteDate = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
		String strInterestStartDate = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
		String strModifyTime = null;		

		//从Request中获得参数
		//页面控制参数
		if (request.getAttribute("strActionResult") != null)
		{
				 strActionResult = (String)request.getAttribute("strActionResult");
		}
		if (request.getAttribute("strAction") != null)
		{
				 strAction = (String)request.getAttribute("strAction");
		}
		if (request.getAttribute("strPreSaveResult") != null)
		{
				 strPreSaveResult = (String)request.getAttribute("strPreSaveResult");
		}
        
		//业务参数
		String strTemp = null;
		
		strTemp = (String)request.getParameter("lStatusID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lStatusID = Long.valueOf(strTemp).longValue();
		}
        //活期账户数据
		strTemp = (String)request.getAttribute("crt_dAc_mcapitallimitamount");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			crt_dAc_mcapitallimitamount = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String)request.getAttribute("crt_dMbalance");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			crt_dMbalance = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String)request.getAttribute("crt_lNaccounttypeid");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			crt_lNaccounttypeid = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("crt_strSaccountno");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			crt_strSaccountno = strTemp;
		}
		strTemp = (String)request.getAttribute("crt_dSubSum");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			crt_dSubSum = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String)request.getAttribute("crt_dSum");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			crt_dSum = Double.valueOf(strTemp).doubleValue();
		}

		//委托账户数据
		strTemp = (String)request.getAttribute("cgn_dAc_mcapitallimitamount");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			cgn_dAc_mcapitallimitamount = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String)request.getAttribute("cgn_dMbalance");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			cgn_dMbalance = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String)request.getAttribute("cgn_lNaccounttypeid");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			cgn_lNaccounttypeid = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("cgn_strSaccountno");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			cgn_strSaccountno = strTemp;
		}
		strTemp = (String)request.getAttribute("cgn_dSubSum");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			cgn_dSubSum = Double.valueOf(strTemp).doubleValue();
		}

		//定期账户数据
		strTemp = (String)request.getAttribute("fixed_tsAf_dtend");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			fixed_tsAf_dtend = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("fixed_tsAf_dtstart");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			fixed_tsAf_dtstart = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("fixed_dAf_mrate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			fixed_dAf_mrate = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String)request.getAttribute("fixed_lAf_ndepositterm");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			fixed_lAf_ndepositterm = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("fixed_dMbalance");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			fixed_dMbalance = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String)request.getAttribute("fixed_dMopenamount");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			fixed_dMopenamount = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String)request.getAttribute("fixed_lNaccounttypeid");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			fixed_lNaccounttypeid = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("fixed_lNstatusid");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			fixed_lNstatusid = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("fixed_strSaccountno");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			fixed_strSaccountno = strTemp;
		}
		strTemp = (String)request.getAttribute("fixed_dSubsum");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			fixed_dSubSum = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String)request.getAttribute("fixed_dSum");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			fixed_dSum = Double.valueOf(strTemp).doubleValue();
		}
        //定期账户的主账号
		strTemp = (String)request.getAttribute("fixed_lNaccountID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			fixed_lNaccountID = Long.valueOf(strTemp).longValue();
		}	
		//期限或者品种的类型
		strTemp = (String)request.getAttribute("fixed_lNtype");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			fixed_lNtype = Long.valueOf(strTemp).longValue();
		}	
		

		//贷款数据
		strTemp = (String)request.getAttribute("loan_tsDtEndDate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			loan_tsDtEndDate = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("loan_tsDtStartDate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			loan_tsDtStartDate = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("loan_dLoanBalance");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			loan_dLoanBalance = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String)request.getAttribute("loan_dMAmount");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			loan_dMAmount = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String)request.getAttribute("loan_lNaccounttypeid");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			loan_lNaccounttypeid = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("loan_lNborrowclientid");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			loan_lNborrowclientid = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("loan_lNIntervalNum");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			loan_lNIntervalNum = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("loan_lNstatusid");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			loan_lNstatusid = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("loan_dRate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			loan_dRate = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String)request.getAttribute("loan_strSaccountno");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			loan_strSaccountno = strTemp;
		}
		strTemp = (String)request.getAttribute("loan_strSCONTRACTCODE");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			loan_strSCONTRACTCODE = strTemp;
		}
		strTemp = (String)request.getAttribute("loan_dSubSum");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			loan_dSubSum = Double.valueOf(strTemp).doubleValue();
		}
		//add by xwhe 2008-11-24
		long lNoperationtypeid = 1;
		strTemp = (String) request.getParameter("operationtypeid");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lNoperationtypeid = Long.valueOf(strTemp).longValue();
		}
		IPrintTemplate.showPrintHead(out,true,"A4","",1,sessionMng.m_lOfficeID);
		
	%>

	

<safety:resources />

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

     <table border="0" align="center" bordercolor="#999999" id="aaaa"  width="700px"   cellspacing="0" cellpadding="0">
     <tr><td class="f22" align="center" height="50" colspan="4">
     账户余额
     </td></tr>
     <tr><td class="f12" align="left" height="30">
     户名：<%=DataFormat.formatString(NameRef.getClientNameByID(sessionMng.m_lClientID))%>
     </td>
     <td class="f12" align="right" height="30">
     币种：<%=Constant.CurrencyType.getName(sessionMng.m_lCurrencyID)%>
     </td>
     </tr>
     </table>


    
	<!--活期账户-->
            <table border="0"  align="center" bordercolor="#999999" class="table1" width="700px"   cellspacing="0" cellpadding="0" <%if(lNoperationtypeid != 1&&lNoperationtypeid != 4){%>style="display:none;"<% } %>>
              
              <tr <%if(lNoperationtypeid == 1 || lNoperationtypeid == 4 ){%> id="getTableWidthEle" <% } %> >                
                <td colspan="4" height="30"  class="td-bottom" nowrap>&nbsp;&nbsp;&nbsp;&nbsp;
				活期账户</td>                
              </tr>
              
            
              <tr align=center > 
                <td class="td-rightbottom" nowrap width="25%" height="23"　align="center">账户类型</td>
                <td class="td-rightbottom" nowrap width="25%" height="23"　align="center">账号</td>
                <td class="td-rightbottom" nowrap width="25%" height="23"　align="center">资金余额</td>
				<td class="td-bottom" nowrap width="25%" height="23"　align="center">最低余额限制</td>
              </tr>
<%    
long lPageLine = 20;
long lLine = 0;
        Collection resultColl = null;
       OBAccountBalanceQueryDao dao = new OBAccountBalanceQueryDao();
		if(lNoperationtypeid == 1||lNoperationtypeid== 4)
        {
		 resultColl = dao.seekCurrentBalace2(sessionMng.m_lClientID, sessionMng.m_lCurrencyID,sessionMng.m_lOfficeID,sessionMng.m_lUserID);
        } 
		Iterator it = null;

		//账户类型
		String sAcctType="";
		String sAcctNo="";
		String sAmount="";
		String sLimited="";
	
		if (resultColl != null && (lNoperationtypeid == 1||lNoperationtypeid== 4))
		{
			it = resultColl.iterator();
			long length=resultColl.size();
		}		
		
		long i=0;
		while( it != null && it.hasNext()&& (lNoperationtypeid == 1||lNoperationtypeid== 4) )
		{
			i++;
			OBAccountBalanceCurrentInfo crt_info = (OBAccountBalanceCurrentInfo ) it.next();
			crt_dAc_mcapitallimitamount = crt_info.getAc_mcapitallimitamount();
			crt_dMbalance = crt_info.getMbalance();
			crt_lNaccounttypeid = crt_info.getNaccounttypeid();
			crt_strSaccountno = crt_info.getSaccountno();
			
			
			crt_dSubSum = crt_info.getSubSum();
			crt_dSum = crt_info.getSum();
			
			String strURL= "a004_c.jsp?strAction=current&lAccountID=" + crt_info.getAccountID() + "&strSuccessPageURL=a004_v.jsp&strFailPageURL=a004_v.jsp";
						
			sAcctType=SETTConstant.AccountType.getName(crt_lNaccounttypeid);
			if(crt_lNaccounttypeid==0)
				sAcctType="";
		    
		    //crt_strSaccountno -1 表示"小计"  -2 表示"活期小计"
			if(crt_strSaccountno.equals("-1"))
			{
				sAcctNo="小计";
			}
			else if(crt_strSaccountno.equals("-2"))
			{
				//sAcctNo="活期小计";
				sAcctNo="合计";
				currentSum = currentSum+crt_info.getMbalance();
			}
			else sAcctNo=crt_strSaccountno;

			//资金余额
			sAmount=DataFormat.formatListAmount(crt_dMbalance); 
			
			if(sAmount.equals("&nbsp;"))
				sAmount="0.00";
			//最低余额限制
			sLimited=DataFormat.formatListAmount(crt_dAc_mcapitallimitamount); 
			if(sLimited.equals("&nbsp;") )
				sLimited="0.00";
			if(crt_strSaccountno.equals("-1") || crt_strSaccountno.equals("-2") )
				sLimited="";

			//
			
			if(lLine > lPageLine)
		  {pagecount++;
				%>
			</table>
			 <table border="0" bordercolor="#999999" align="center" width="700px"  border="0" cellspacing="0" cellpadding="0">
	    <tr> 
	    <td  colspan="3" align="left" valign="top" height="16" ><br>
<%
			Timestamp ts=Env.getSystemDateTime(); 
%>
	      <span class="td-rightbottom">查询日期：<%=ts.toString().substring(0,10)%> 查询时间：<%=ts.toString().substring(10,19)%></span><br>
	    </td>
	   </tr>
        <tr> 
          <td width="471"> 
            <div align="right"></div>
          </td>
          <td width="49"> 
            <div align="right"></div>
          </td>
          <td width="50"> 
            
          </td>
        </tr>
      </table>
			<br clear=all style='page-break-before:always'>
			
			<table border="0" bordercolor="#999999"  width="700px" align="center"  cellspacing="0" cellpadding="0">
     <tr><td class="f22" align="center" height="50" colspan="4">
     账户余额
     </td></tr>
     <tr><td class="f12" align="left" height="30">
     客户名称：<%=DataFormat.formatString(NameRef.getClientNameByID(sessionMng.m_lClientID))%>
     </td>
     <td class="f12" align="right" height="30">
     币种：<%=Constant.CurrencyType.getName(sessionMng.m_lCurrencyID)%>
     </td>
     </tr>
     </table>
			<table border="0" bordercolor="#999999" class=table1 align="center" width="700px" border="0" cellspacing="0" cellpadding="0">
              <tr >                
                <td colspan="4"height="30"  class="td-bottom" nowrap>&nbsp;&nbsp;&nbsp;&nbsp;
				活期账户</td>                
              </tr>
              <tr align=center > 
                <td class="td-rightbottom" nowrap width="25%" height="23"　align="center">账户类型</td>
                <td class="td-rightbottom" nowrap width="25%" height="23"　align="center">账号</td>
                <td class="td-rightbottom" nowrap width="25%" height="23"　align="center">资金余额</td>
				<td class="td-bottom" nowrap width="25%" height="23"　align="center">最低余额限制</td>
              </tr>	
				
				
				<% 
				lLine = 0;	
		  }
			lLine ++;
			System.out.println("当前统计行数为："+lLine);
			
			
			if(sAcctNo.equals("小计") || sAcctNo.equals("合计") )			
			{
				
%>
	        <tr align="center"  > 
                <td align="center"  class="td-rightbottom" align="left" height="18">&nbsp;<%=(sAcctType==null?"&nbsp;":sAcctType)%></td>
                <td align="center"  class="td-rightbottom" height="18">&nbsp;<%=(sAcctNo==null ? "&nbsp;" : sAcctNo)%></td>
                <td class="td-rightbottom" height="18" align="right" nowrap>&nbsp;<%=(sAmount==null?"&nbsp;":sAmount)%></td>
	            <td class="td-bottom" height="18" align="right" nowrap>&nbsp;<%=(sLimited==null?"&nbsp;":sLimited)%>
	            </td>
            </tr>
<%}
			
			else
			{
				String[] straccountno = crt_strSaccountno.split("-");
				int len = straccountno.length;	
				String[] straccountno1 = new String[len];
				String straccountno2 ="";
				
				for (int s=0;s<len;s++)
				{
					
					straccountno1[s] = straccountno[s];
					straccountno2+=straccountno1[s];
				}
				
				
				
%>
            <tr align="center"  > 
                <td class="td-rightbottom" height="18" align="left">&nbsp;<%=(sAcctType==null?"&nbsp;":sAcctType)%></td>
                <td class="td-rightbottom" height="18">
					
						&nbsp;<%=(crt_strSaccountno==null ? "&nbsp;" : straccountno2)%>
					
				</td>
                <td class="td-rightbottom" height="18" align="right" nowrap>&nbsp;<%=(sAmount==null?"&nbsp;":sAmount)%>
	            </td>
	            <td class="td-bottom" height="18" align="right" nowrap>&nbsp;<%=(sLimited==null?"&nbsp;":sLimited)%>
	            </td>
            </tr>
<%	
			}
		}
		lLine=0;
		lLine=lLine+5;
		
%>         
      </table>			
		<br>	
	
	<!--定期账户-->		

      <table border="0" bordercolor="#999999" class="table1" align="center" width="700px"  border="0" cellspacing="0" cellpadding="0" <%if(lNoperationtypeid != 2&&lNoperationtypeid != 4){%>style="display:none;"<% } %>>
        
        <tr  <%if(lNoperationtypeid == 2){%> id="getTableWidthEle" <% } %> >           
          <td colspan="9" height="22" class="td-bottom" >&nbsp;&nbsp;&nbsp;&nbsp;定期账户</td>          
        </tr>
		<tr  >           	  
          <td colspan="9"height="22"  class="td-bottom">
		
		 显示已结清子账户信息</td>
        </tr>
        
      
      
        <tr align=center > 
          <td class="td-rightbottom" height="23" >账户类型</td>
          <td class="td-rightbottom" height="23" >账号/子账号</td>
          <td class="td-rightbottom" height="23" >存入日</td>
          <td class="td-rightbottom" height="23" >到期日</td>
          <td class="td-rightbottom" height="23" >期限/品种</td>
		  <td class="td-rightbottom" height="23" >利率</td>		  
          <td class="td-rightbottom" height="23" >存款金额</td>
		  <td class="td-rightbottom" height="23" >存款余额</td>
          <td class="td-bottom" height="23" >备注</td>
        </tr>
<%
		//存款金额
        String sFixedAmount="";
		//存款余额
		String sFixedBalance="";
		//存入日
		String sTerm="";
		//利率
		String sRate="";
		//备注
		String sTatus="";
	    if(lNoperationtypeid == 2||lNoperationtypeid== 4)
        {
		resultColl = dao.seekFixedBalace(sessionMng.m_lClientID,sessionMng.m_lCurrencyID, sessionMng.m_lOfficeID,lStatusID,sessionMng.m_lUserID);
        } 
		it = null;		
	
		if (resultColl != null &&(lNoperationtypeid == 2||lNoperationtypeid== 4))
		{
			it = resultColl.iterator();
		}	
		
        while( it != null && it.hasNext()&&(lNoperationtypeid == 2||lNoperationtypeid== 4) )
		{
			OBAccountBalanceFixedInfo fixed_info = (OBAccountBalanceFixedInfo) it.next();
			fixed_tsAf_dtend = fixed_info.getAf_dtend();
			fixed_tsAf_dtstart = fixed_info.getAf_dtstart();
			fixed_dAf_mrate = fixed_info.getAf_mrate();
			fixed_lAf_ndepositterm = fixed_info.getAf_ndepositterm();
			
			fixed_dMbalance = fixed_info.getMbalance();
			fixed_dMopenamount = fixed_info.getMopenamount();
			fixed_lNaccounttypeid = fixed_info.getNaccounttypeid();
			fixed_lNstatusid = fixed_info.getNstatusid();
			fixed_strSaccountno = fixed_info.getSaccountno();
			fixed_dSubSum = fixed_info.getSubsum();
			fixed_dSum = fixed_info.getSum();
			fixed_lNtype = fixed_info.getNtype();
			System.out.println("fixed_info.getNtype()的值是:"+fixed_info.getNtype());
            
			if(lLine > lPageLine)
			{pagecount++;
				%>
				</table>
				 <table border="0" bordercolor="#999999" align="center" width="700px"  border="0" cellspacing="0" cellpadding="0">
	    <tr> 
	    <td  colspan="3" align="left" valign="top" height="16" ><br>
<%
			Timestamp ts=Env.getSystemDateTime(); 
%>
	      <span class="td-rightbottom" id='<%="printDate_"+(pagecount-1)%>'>查询日期：<%=ts.toString().substring(0,10)%> 查询时间：<%=ts.toString().substring(10,19)%></span><br>
	    </td>
	   </tr>
        <tr> 
          <td width="471"> 
            <div align="right"></div>
          </td>
          <td width="49"> 
            <div align="right"></div>
          </td>
          <td width="50"> 
            
          </td>
        </tr>
      </table>
			<br clear=all style='page-break-before:always'>
		<table border="0" bordercolor="#999999" align="center" width="700px"   cellspacing="0" cellpadding="0">
     <tr><td class="f22" align="center" height="50" colspan="4">
     账户余额
     </td></tr>
     <tr><td class="f12" align="left" height="30">
    客户名称：<%=DataFormat.formatString(NameRef.getClientNameByID(sessionMng.m_lClientID))%>
     </td>
     <td class="f12" align="right" height="30">
     币种：<%=Constant.CurrencyType.getName(sessionMng.m_lCurrencyID)%>
     </td>
     </tr>
     </table>		
		<table border="0" bordercolor="#999999" align="center" class="table1" width="700px"  border="0" cellspacing="0" cellpadding="0">
       
        <tr >           
          <td colspan="9" height="22" class="td-bottom">&nbsp;&nbsp;&nbsp;&nbsp;定期账户</td>          
        </tr>
		<tr >           	  
          <td colspan="9"height="22" class="td-bottom">
		
		 显示已结清存单信息</td>
        </tr>
        
      
        <tr align=center > 
          <td class="td-rightbottom" height="23" >账户类型</td>
          <td class="td-rightbottom" height="23" >账号/单据号</td>
          <td class="td-rightbottom" height="23" >存入日</td>
          <td class="td-rightbottom" height="23" >到期日</td>
          <td class="td-rightbottom" height="23" >期限/品种</td>
		  <td class="td-rightbottom" height="23" >利率</td>		  
          <td class="td-rightbottom" height="23" >存款金额</td>
		  <td class="td-rightbottom" height="23" >存款余额</td>
          <td class="td-bottom" height="23" >备注</td>
        </tr>
       
				<% 
				lLine = 0;
				
				
			}	
			lLine ++;
			//账户类型
			sAcctType=SETTConstant.AccountType.getName(fixed_lNaccounttypeid);
			if(fixed_lNaccounttypeid==0 || fixed_lNaccounttypeid ==-1 )
				sAcctType="";
		    
		    //账号
			sAcctNo=fixed_strSaccountno;
			if(fixed_strSaccountno.equals("-2"))
			{
				sAcctNo="小计";	
				//存款汇总余额
				//depositSum = depositSum+fixed_dMbalance;
			}	
			//存款金额
			if(fixed_dMopenamount==-1)
				sFixedAmount="";
			else 
				sFixedAmount=DataFormat.formatListAmount(fixed_dMopenamount); 
			//存款余额
			if(fixed_dMbalance==-1)
				sFixedBalance="";
			else
				sFixedBalance=DataFormat.formatListAmount(fixed_dMbalance); 
			if(sFixedBalance.equals("&nbsp;") && fixed_tsAf_dtstart!=null )
				sFixedBalance="0.00";
			//期限
			if(fixed_lAf_ndepositterm==-1 || fixed_lAf_ndepositterm==-2)
			{
			    sTerm="";
			}
			else if(fixed_lNtype==6)
			{
				fixed_lAf_ndepositterm = fixed_lAf_ndepositterm - 10000;
				sTerm=fixed_lAf_ndepositterm+"天";
			}	
			else if(fixed_lNtype==5)
			{
				sTerm=fixed_lAf_ndepositterm+"个月";
			}

			//判断定期还是通知
			String strURL = "" ;
			if(fixed_lNtype == 5)
			{
			  	System.out.println("进入定期判断" );
				strURL= "a004_c.jsp?strAction=fixed&strDepositNo=" + fixed_strSaccountno + "&lSubAccountID=" + fixed_info.getSubAccountID() + "&lAccountID=" + fixed_info.getAccountID() + "&strSuccessPageURL=a005_v.jsp&strFailPageURL=a005_v.jsp";
			}
			else if(fixed_lNtype == 6)
			{
				System.out.println("进入通知判断" );
				strURL= "a004_c.jsp?strAction=notice&strDepositNo=" + fixed_strSaccountno + "&lSubAccountID=" + fixed_info.getSubAccountID() + "&lAccountID=" + fixed_info.getAccountID() + "&strSuccessPageURL=a006_v.jsp&strFailPageURL=a006_v.jsp";
			}

			//利率
			if(fixed_dAf_mrate==-1)
			{
				sRate="";
			}
			else
			{
				if(fixed_dAf_mrate>0)			
				{
					sRate=DataFormat.formatListAmount(fixed_dAf_mrate)+"%"; 
				}
				else
				{
					sRate=DataFormat.formatListAmount(fixed_dAf_mrate);
				}
			}
			if(fixed_dAf_mrate==-2)
			{
				sRate="小计";
				depositSum = depositSum+fixed_dMbalance;
			}
			//else if(sRate.equals("&nbsp;%") && fixed_tsAf_dtstart!=null )
			//	sRate="0.00%";
			//备注
			if(fixed_lNstatusid==-1)
			{
				sTatus="";
			}	
			else
			{
				if(SETTConstant.AccountType.isFixAccountType(fixed_lNtype))
				{
					if(fixed_lNstatusid==SETTConstant.SubAccountStatus.FINISH)
					{
						sTatus=SETTConstant.SubAccountStatus.getName(fixed_lNstatusid);
					}
					else
					{
						if(fixed_info.getAf_dtend()!=null)
						{
							if(fixed_info.getAf_dtend().after(Env.getSystemDate(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID)))
							{
								sTatus="未到期";
							}
							else
							{
								sTatus="已到期";
							}
						}
					}
				}
				else
				{
					sTatus=SETTConstant.SubAccountStatus.getName(fixed_lNstatusid);
				}
			}
%>		
		<tr align="center" > 
	      <!--账户类型-->
		  <td class="td-rightbottom"  align="left" height="22"><%=(sAcctType==null?"":sAcctType)%>&nbsp;</td>
		  <!--账号/单据号-->
          <td class="td-rightbottom" height="22"  align=center> 
		  <%
		  String[] strfixedaccountno = fixed_strSaccountno.split("-");
			int fixedlen = strfixedaccountno.length;	
			String[] strfixedaccountno1 = new String[fixedlen];
			String strfixedaccountno2 ="";
			
			for (int t=0;t<fixedlen;t++)
			{
				
				strfixedaccountno1[t] = strfixedaccountno[t];
				strfixedaccountno2+=strfixedaccountno1[t];
			}
		  if (fixed_tsAf_dtstart!=null)
		  {
			  System.out.println("定期strURL的值是:\"" + strURL + "\"");
		  %>
			
			  <%=(fixed_strSaccountno.equals("0")?"":strfixedaccountno2)%>
		  <%
		  }
          else
		  {
		  %>
			
		     <%=(fixed_strSaccountno.equals("0")?"":strfixedaccountno2)%>
          <%
		  }	
		  %>&nbsp;
		  </td>
		  <!--存入日-->
          <td class="td-rightbottom"  height="22">&nbsp;<%=(fixed_tsAf_dtstart==null?"":DataFormat.formatDate(fixed_tsAf_dtstart))%></td>
		  <!--到期日-->
          <td class="td-rightbottom" align="center" height="22">&nbsp;<%=(fixed_tsAf_dtend==null?"":DataFormat.formatDate(fixed_tsAf_dtend))%></td>
		  <!--期限/品种-->
          <td class="td-rightbottom" align="right" height="22" >&nbsp;<%=sTerm%></td>
		  <!--利率-->
		  <td class="td-rightbottom"  height="22" >&nbsp;<%=sRate%></td>
		  <!--存款金额-->
          <td class="td-rightbottom"  height="18" align="right" nowrap>&nbsp;<%=sFixedAmount%></td>
		  <!--存款余额-->
		  <td class="td-rightbottom"  height="22" align="right" nowrap>&nbsp;<%=sFixedBalance%></td>
		  <!--备注-->
          <td class="td-bottom"  height="22"><%=sTatus%>&nbsp;</td>
        </tr>      
<%
		}
        lLine=0;
        lLine=lLine+6;
%>
		<tr align="center" > 
	      <!--账户类型-->
		  <td class="td-rightbottom"  height="22">&nbsp;</td>
		  <!--账号/单据号-->
          <td class="td-rightbottom" height="22" >&nbsp; 		  
		  </td>
		  <!--存入日-->
          <td class="td-rightbottom"  height="22">&nbsp;</td>
		  <!--到期日-->
          <td class="td-rightbottom"  height="22">&nbsp;</td>
		  <!--期限/品种-->
          <td class="td-rightbottom"  height="22" >&nbsp;</td>
		  <!--利率-->
		  <td class="td-rightbottom"  height="22" >&nbsp;</td>
		  <!--存款金额-->
          
      <td class="td-rightbottom"  height="22" align="right" nowrap><strong>存款余额总计：</strong></td>
		  <!--存款余额-->
		  <td class="td-rightbottom"  height="22" align="right" nowrap>&nbsp;<%=DataFormat.formatListAmount(depositSum)%></td>
		  <!--备注-->
          <td class="td-bottom"  height="22">&nbsp;</td>
        </tr>	
      </table>
      <br>
	  <!--贷款账户-->
      <table border="0" bordercolor="#999999" align="center" class="table1" width="700px"  border="0" cellspacing="0" cellpadding="0" <%if(lNoperationtypeid != 3 && lNoperationtypeid != 4){%>style="display:none;"<%} %>>
        
        <tr <%if(lNoperationtypeid == 3){%> id="getTableWidthEle" <% } %> >          
          <td colspan="10"height="25" class="td-bottom">&nbsp;&nbsp;&nbsp;&nbsp;贷款账户</td>
        </tr>
        
      
        <tr align="center" > 
          <td class="td-rightbottom" width="10%" height="23" >账户类型</td>
          <td class="td-rightbottom" width="10%" height="23"  >账号/合同号</td>
          <td class="td-rightbottom" width="10%" height="23" >借款单位</td>
          <td class="td-rightbottom" width="10%" height="23" >起始日</td>
          <td class="td-rightbottom" width="10%" height="23" >到期日</td>
          <td class="td-rightbottom" width="10%" height="23">期限</td>
          <td class="td-rightbottom"  width="10%" height="23">贷款金额</td>
          <td class="td-rightbottom" width="10%"  height="23">贷款余额</td>
          <td class="td-rightbottom" width="10%"  height="23">利率</td>
		  <td class="td-bottom" width="10%" height="23">合同状态</td>
        </tr>
<%
		//贷款金额
        String sLoanAmount="";
		//贷款余额
		String sLoanBalance="";
		//借款单位
		String sBrwClient="";
		//合同状态
		String sContractStatus="";
         if(lNoperationtypeid == 3 ||lNoperationtypeid== 4)
        {
        resultColl = dao.seekLoanBalace(sessionMng.m_lClientID,sessionMng.m_lCurrencyID, sessionMng.m_lOfficeID,sessionMng.m_lUserID);
        }
		it = null;		
	
		if (resultColl != null && (lNoperationtypeid == 3 ||lNoperationtypeid== 4 ))
		{
			it = resultColl.iterator();
		}	
		
        while( it != null && it.hasNext()&& (lNoperationtypeid == 3 ||lNoperationtypeid== 4 ) )
		{
			OBAccountBalanceLoanInfo loan_info = (OBAccountBalanceLoanInfo) it.next();
            loan_tsDtEndDate = loan_info.getDtEndDate();
			loan_tsDtStartDate = loan_info.getDtStartDate();
			loan_dLoanBalance = loan_info.getLoanBalance();
			loan_dMAmount = loan_info.getMAmount();
			loan_lNaccounttypeid = loan_info.getNaccounttypeid();
			loan_lNborrowclientid = loan_info.getNborrowclientid();
			loan_lNIntervalNum = loan_info.getNIntervalNum();
			loan_lNstatusid = loan_info.getNstatusid();
			loan_dRate = loan_info.getRate();
			loan_strSaccountno = loan_info.getSaccountno();
			loan_strSCONTRACTCODE = loan_info.getSCONTRACTCODE();
			loan_dSubSum = loan_info.getSubSum();
			if(lLine > lPageLine)
			{pagecount++;
				
				%>
				</table>
				 <table border="0" bordercolor="#999999" align="center"  width="700px"  border="0" cellspacing="0" cellpadding="0">
	    <tr> 
	    <td  colspan="3" align="left" valign="top" height="16" ><br>
<%
			Timestamp ts=Env.getSystemDateTime(); 
%>
	      <span class="td-rightbottom">查询日期：<%=ts.toString().substring(0,10)%> 查询时间：<%=ts.toString().substring(10,19)%></span><br>
	    </td>
	   </tr>
        <tr> 
          <td width="471"> 
            <div align="right"></div>
          </td>
          <td width="49"> 
            <div align="right"></div>
          </td>
          <td width="50"> 
            
          </td>
        </tr>
      </table>
			<br clear=all style='page-break-before:always'>
				<table border="0" bordercolor="#999999"  width="700px"   cellspacing="0" cellpadding="0">
     <tr><td class="f22" align="center" height="50" colspan="4">
     账户余额
     </td></tr>
     <tr><td class="f12" align="left" height="30">
     客户名称：<%=DataFormat.formatString(NameRef.getClientNameByID(sessionMng.m_lClientID))%>
     </td>
     <td class="f12" align="right" height="30">
     币种：<%=Constant.CurrencyType.getName(sessionMng.m_lCurrencyID)%>
     </td>
     </tr>
     </table>
			<table border="0" bordercolor="#999999" align="center" class="table1" width="700px"  border="0" cellspacing="0" cellpadding="0">
        
        <tr  >          
          <td colspan="10" height="25" class="td-bottom">&nbsp;&nbsp;&nbsp;&nbsp;贷款账户</td>
        </tr>
        
        <tr align="center" > 
          <td class="td-rightbottom" width=10% height="23" >账户类型</td>
          <td class="td-rightbottom"width=10% height="23"  >账号/合同号</td>
          <td class="td-rightbottom"width=10% height="23" >借款单位</td>
          <td class="td-rightbottom"width=10% height="23" >起始日</td>
          <td class="td-rightbottom"width=10% height="23" >到期日</td>
          <td class="td-rightbottom"width=10% height="23">期限</td>
          <td class="td-rightbottom"width=10%  height="23">贷款金额</td>
          <td class="td-rightbottom"width=10%  height="23">贷款余额</td>
          <td class="td-rightbottom"width=10% height="23">利率</td>
		  <td class="td-bottom"width=10% height="23">合同状态</td>
        </tr>	
         
				<%
				
           lLine = 0;
				
				
			}	
			lLine ++;
			//账户类型
			sAcctType=SETTConstant.AccountType.getName(loan_lNaccounttypeid);
			if(loan_lNaccounttypeid==0 || loan_lNaccounttypeid==-1 )
				sAcctType="";	
			//借款单位
            if(loan_lNborrowclientid==-1)
				sBrwClient="";
			else
				sBrwClient=NameRef.getClientNameByID(loan_lNborrowclientid);

			//贷款金额
			if(loan_dMAmount==-1)
				sLoanAmount="";
			else 
				sLoanAmount=DataFormat.formatListAmount(loan_dMAmount); 
			//贷款余额
			if(loan_dLoanBalance==-1)
				sLoanBalance="";
			else
				sLoanBalance=DataFormat.formatListAmount(loan_dLoanBalance);
			if(sLoanBalance.equals("&nbsp;") && loan_tsDtStartDate!=null && loan_tsDtEndDate!=null)
				sLoanBalance="0.00";
			//期限
			if(loan_lNIntervalNum==-1 )
			    sTerm="";
			else if(loan_lNIntervalNum==-2 )
			{
				sTerm="小计";
				loanSum=loanSum+loan_dLoanBalance;
			}
			else 
				sTerm=loan_lNIntervalNum+"个月";

			if(sTerm.equals("小计") && !loan_strSaccountno.equals("") )
				sTerm="";
			//利率
			if(loan_dRate==-1 || loan_tsDtStartDate == null || loan_tsDtEndDate == null )
				sRate="";
			else 
				sRate=DataFormat.formatRate(loan_dRate)+"%"; 
			if(sRate.equals("&nbsp;%"))
				sRate="0.00%";
				
			String strURL= "a004_c.jsp?strAction=loan&lAccountID=" + loan_info.getAccountID() + "&lContractID=" + loan_info.getContractID() + "&strSuccessPageURL=a007_v.jsp&strFailPageURL=a007_v.jsp";	
			//合同状态  
			if(loan_lNstatusid==-1)
				sContractStatus="";
			else
				sContractStatus=""+LOANConstant.ContractStatus.getName(loan_lNstatusid);
if(loan_dMAmount==0.0)
				
				continue;	
			    //sContractStatus=""+loan_lNstatusid;
%>
          <tr align="center"> 
	        <!--账户类型-->
            <td class="td-rightbottom" width=10%  height="18"><%=(sAcctType==null||"".equals(sAcctType))?"&nbsp;":sAcctType%></td>
		    <!--账号/合同号  索引值待定-->
<%
String[] strloanaccountno = loan_strSaccountno.split("-");
int loanlen = strloanaccountno.length;	
String[] strloanaccountno1 = new String[loanlen];
String strloanaccountno2 ="";

for (int m=0;m<loanlen;m++)
{
	
	strloanaccountno1[m] = strloanaccountno[m];
	strloanaccountno2+=strloanaccountno1[m];
}
           if(loan_tsDtStartDate!=null && loan_tsDtEndDate!=null)
			{
%>
            <td class="td-rightbottom"width=10%  height="18">
		    
			<%=((loan_strSaccountno==null||"".equals(loan_strSaccountno))?"&nbsp;":loan_strSaccountno)%>		 
		    </td>
<%
			}
            else
			{
%>
            <td class="td-rightbottom"  width=10% height="18">
		    <%=((loan_strSaccountno==null||"".equals(loan_strSaccountno))?"&nbsp;":strloanaccountno2)%>		 
		    </td>
<%
			}
%>
		    <!--借款单位-->
            <td class="td-rightbottom"  width=10% align="left" height="18"><%="".equals(sBrwClient)?"&nbsp;":sBrwClient%></td>
	        <!--起始日-->
            <td class="td-rightbottom"width=10%  align="center" height="18"><%=(loan_tsDtStartDate==null?"&nbsp;":DataFormat.formatDate(loan_tsDtStartDate))%></td>
		    <!--到期日-->
            <td class="td-rightbottom" width=10% height="18"><%=(loan_tsDtEndDate==null?"&nbsp;":DataFormat.formatDate(loan_tsDtEndDate))%></td>
		    <!--期限-->
            <td class="td-rightbottom" width=10% <%if(sTerm!=null&&sTerm.equals("小计")) {%>align=center<% }else {%>align=right<%}%> height="18"><%="".equals(sTerm)?"&nbsp;":sTerm%></td>
		    <!--贷款金额-->
            <td class="td-rightbottom" width=10%  height="18" align="right" > 
              <div align="right"><%="".equals(sLoanAmount)?"&nbsp;":sLoanAmount%></div>
            </td>
		    <!--贷款余额-->
            <td class="td-rightbottom" width=10%  height="18" align="right" > 
              <div align="right"><%="".equals(sLoanBalance)?"&nbsp;":sLoanBalance%></div>
            </td>
		    <!--利率-->
            <td class="td-rightbottom" width=10%  height="18" align="right"><%="".equals(sRate)?"&nbsp;":sRate%></td>
		    <!--合同状态-->
		    <td class="td-bottom" width=10%  height="18" align="center"><%="".equals(sContractStatus)?"&nbsp;":sContractStatus%></td>
        </tr>
<%
		}
        lLine=0;
        lLine=lLine+5;
        
%>     
	<tr align="center" > 
	        <!--账户类型-->
            <td class="td-rightbottom"  height="18">&nbsp;</td>
		    <!--账号/合同号  索引值待定-->
            <td class="td-rightbottom"  height="18" >&nbsp;
		    </td>
		    <!--借款单位-->
            <td class="td-rightbottom"  height="18">&nbsp;</td>
	        <!--起始日-->
            <td class="td-rightbottom"  height="18">&nbsp;</td>
		    <!--到期日-->
            <td class="td-rightbottom"  height="18">&nbsp;</td>
		    <!--期限-->
            <td class="td-rightbottom"  height="18">&nbsp;</td>
		    <!--贷款金额-->
            
      <td class="td-rightbottom"  height="18" align="right" > <strong>贷款余额总计：</strong> 
      </td>
		    <!--贷款余额-->
            <td class="td-rightbottom"  height="18" align="right" > 
              <div align="right"><%=DataFormat.formatListAmount(loanSum)%></div>&nbsp;
            </td>
		    <!--利率-->
            <td class="td-rightbottom"  height="18" align="right">&nbsp;</td>
		    <!--合同状态-->
		    <td class="td-bottom"  height="18" align="right">&nbsp;</td>
		    
        </tr>
   
      </table>
<br>
      <%-- 
      	
 <!--保证金账户-->	
      <!--此处将保金账户功能去掉了,要想继续使用，请在VSS上获得2007-02-01版本，修改：王利　地点：长春-->		
	
<table border="0" bordercolor="#999999" class="table1" width="700px"  border="0" cellspacing="0" cellpadding="0">
        
        <tr  >          
          <td colspan="10" height="25" class="td-bottom">&nbsp;&nbsp;&nbsp;&nbsp;保证金账户</td>
        </tr>
		<tr align="center" > 
          <td class="td-rightbottom" height="23" >账户类型</td>
          <td class="td-rightbottom" height="23"  >账号/单据号</td>
          <td class="td-rightbottom" height="23" >存入日</td>
          <td class="td-rightbottom" height="23" >到期日</td>
          <td class="td-rightbottom" height="23" >利率</td>
          <td class="td-rightbottom" height="23">存款金额</td>
          <td class="td-rightbottom"  height="23">存款余额</td>
          <td class="td-rightbottom"  height="23">备注</td>
        </tr>	
	
		<% 
				double marginDepositSum = 0.00;
				Collection resultMarginColl = dao.seekMarginBalace(
				sessionMng.m_lClientID, sessionMng.m_lCurrencyID,
				sessionMng.m_lOfficeID, lStatusID);

				it = null;

				if (resultMarginColl != null) {
					it = resultMarginColl.iterator();
				}

				while (it != null && it.hasNext()) {
					OBAccountBalanceFixedInfo fixed_info = (OBAccountBalanceFixedInfo) it
					.next();
					fixed_tsAf_dtend = fixed_info.getAf_dtend();
					fixed_tsAf_dtstart = fixed_info.getAf_dtstart();
					fixed_dAf_mrate = fixed_info.getAf_mrate();
					fixed_lAf_ndepositterm = fixed_info.getAf_ndepositterm();

					double fixed_dMbalance1 = fixed_info.getMbalance();
					fixed_dMopenamount = fixed_info.getMopenamount();
					fixed_lNaccounttypeid = fixed_info.getNaccounttypeid();
					fixed_lNstatusid = fixed_info.getNstatusid();
					fixed_strSaccountno = fixed_info.getSaccountno();
					fixed_dSubSum = fixed_info.getSubsum();
					fixed_dSum = fixed_info.getSum();
					fixed_lNtype = fixed_info.getNtype();
					System.out.println("fixed_lNaccounttypeid的值是:"
					+ fixed_lNaccounttypeid);
					if(lLine > lPageLine)
			{
				
				%>
				</table>
				 <table border="0" bordercolor="#999999"  width="700px"  border="0" cellspacing="0" cellpadding="0">
	    <tr> 
	    <td  colspan="3" align="left" valign="top" height="16" ><br>
<%
			Timestamp ts=Env.getSystemDateTime(); 
%>
	      <span class="td-rightbottom">查询日期：<%=ts.toString().substring(0,10)%> 查询时间：<%=ts.toString().substring(10,19)%></span><br>
	    </td>
	   </tr>
        <tr> 
          <td width="471"> 
            <div align="right"></div>
          </td>
          <td width="49"> 
            <div align="right"></div>
          </td>
          <td width="50"> 
            
          </td>
        </tr>
      </table>
			<br clear=all style='page-break-before:always'>
				<table border="0" bordercolor="#999999"  width="700px"   cellspacing="0" cellpadding="0">
     <tr><td class="f22" align="center" height="50" colspan="4">
     账户余额
     </td></tr>
     <tr><td class="f12" align="left" height="30">
     客户名称：<%=DataFormat.formatString(NameRef.getClientNameByID(sessionMng.m_lClientID))%>
     </td>
     <td class="f12" align="right" height="30">
     币种：<%=Constant.CurrencyType.getName(sessionMng.m_lCurrencyID)%>
     </td>
     </tr>
     </table>
     <table border="0" bordercolor="#999999" class="table1" width="700px"  border="0" cellspacing="0" cellpadding="0">
        
        <tr  >          
          <td colspan="10" height="25" class="td-bottom">&nbsp;&nbsp;&nbsp;&nbsp;保证金账户</td>
        </tr>
		<tr align="center" > 
          <td class="td-rightbottom" height="23" >账户类型</td>
          <td class="td-rightbottom" height="23"  >账号/单据号</td>
          <td class="td-rightbottom" height="23" >存入日</td>
          <td class="td-rightbottom" height="23" >到期日</td>
          <td class="td-rightbottom" height="23" >利率</td>
          <td class="td-rightbottom" height="23">存款金额</td>
          <td class="td-rightbottom"  height="23">存款余额</td>
          <td class="td-rightbottom"  height="23">备注</td>
        </tr>	
     
     <% 
     
     lLine = 0;
				
				
			}	
			lLine ++;
					//账户类型
					sAcctType = SETTConstant.AccountType
					.getName(fixed_lNaccounttypeid);
					if (fixed_lNaccounttypeid == 0
					|| fixed_lNaccounttypeid == -1)
				sAcctType = "";

					//账号
					sAcctNo = fixed_strSaccountno;
					if (fixed_strSaccountno.equals("-2")) {
				sAcctNo = "小计";
				//存款汇总余额
				//depositSum = depositSum+fixed_dMbalance;
					}
					//存款金额
					if (fixed_dMopenamount == -1)
				sFixedAmount = "";
					else
				sFixedAmount = DataFormat
						.formatListAmount(fixed_dMopenamount);
					//存款余额
					if (fixed_dMbalance1 == -1)
				sFixedBalance = "";
					else
				sFixedBalance = DataFormat
						.formatListAmount(fixed_dMbalance1);
					if (sFixedBalance.equals("&nbsp;")
					&& fixed_tsAf_dtstart != null)
				sFixedBalance = "0.00";

					//判断保证金
					String strURL = "";
					//if(SETTConstant.AccountType.isMarginAccountType(fixed_lNaccounttypeid))
					//{ 
					System.out.println("进入保证金判断");
					strURL = "a004_c.jsp?strAction=fixed&strDepositNo="
					+ fixed_strSaccountno
					+ "&lSubAccountID="
					+ fixed_info.getSubAccountID()
					+ "&lAccountID="
					+ fixed_info.getAccountID()
					+ "&strSuccessPageURL=a005_v.jsp&strFailPageURL=a005_v.jsp";
					//}

					//利率
					if (fixed_dAf_mrate == -1) {
				sRate = "";
					} else {
				if (fixed_dAf_mrate > 0) {
					sRate = DataFormat
					.formatListAmount(fixed_dAf_mrate)
					+ "%";
				} else {
					sRate = DataFormat
					.formatListAmount(fixed_dAf_mrate);
				}
					}
					if (fixed_dAf_mrate == -2) {
				sRate = "小计";
				marginDepositSum = marginDepositSum + fixed_dMbalance1;
					}
					//else if(sRate.equals("&nbsp;%") && fixed_tsAf_dtstart!=null )
					//	sRate="0.00%";
					//备注
					if (fixed_lNstatusid == -1) {
				sTatus = "";
					} else {
				if (SETTConstant.AccountType
						.isFixAccountType(fixed_lNtype)) {
					if (fixed_lNstatusid == SETTConstant.SubAccountStatus.FINISH) {
						sTatus = SETTConstant.SubAccountStatus
						.getName(fixed_lNstatusid);
					} else {
						if (fixed_info.getAf_dtend().after(
						Env.getSystemDate(
						sessionMng.m_lOfficeID,
						sessionMng.m_lCurrencyID))) {
					sTatus = "未到期";
						} else {
					sTatus = "已到期";
						}
					}
				} else {
					sTatus = SETTConstant.SubAccountStatus
					.getName(fixed_lNstatusid);
				}
					}
		%>
		<tr align="center" >
			<!--账户类型-->
			<td  class="td-rightbottom"  align="left" height="18">&nbsp;
				<%=(sAcctType == null ? "" : sAcctType)%>
			</td>
			<!--账号/单据号-->
			<td  class="td-rightbottom"  align="left" height="18">&nbsp;
				<% 
						if (fixed_tsAf_dtstart != null) {
						System.out.println("strURL的值是:" + strURL);
				%>
				<%=(fixed_strSaccountno.equals("0") ? ""
										: fixed_strSaccountno)%>
				
				<% 
				} else {
				%>

				<%=(fixed_strSaccountno.equals("0") ? ""
										: fixed_strSaccountno)%>
				<% 
				}
				%>
			</td>
			<!--存入日-->
			<td  class="td-rightbottom"  align="left" height="18">&nbsp;
				<%=(fixed_tsAf_dtstart == null ? "" : DataFormat
									.formatDate(fixed_tsAf_dtstart))%>
			</td>
			<!--到期日-->
			<td class="td-rightbottom"  align="left" height="18">&nbsp;
				<%=(fixed_tsAf_dtend == null ? "" : DataFormat
									.formatDate(fixed_tsAf_dtend))%>
			</td>
			<!--利率-->
			<td class="td-rightbottom"  align="left" height="18" >&nbsp;
				<%=sRate%>
			</td>
			<!--存款金额-->
			<td  class="td-rightbottom"  align="left" height="18">&nbsp;
				<%=sFixedAmount%>
			</td>
			<!--存款余额-->
			<td  class="td-rightbottom"  align="left" height="18">&nbsp;
				<%=sFixedBalance%>
			</td>
			<!--备注-->
			<td  class="td-rightbottom"  align="left" height="18">&nbsp;
				<%=sTatus%>
			</td>
		</tr>
		<%
		}
		lLine=0;
        lLine=lLine+6;
		%>
		<tr align="center" >
			<!--账户类型-->
			<td class="td-rightbottom"  align="left" height="18">&nbsp;</td>
			<!--账号/单据号-->
			<td class="td-rightbottom"  align="left" height="18">&nbsp;
			</td>
			<!--存入日-->
			<td  class="td-rightbottom"  align="left" height="18">&nbsp;</td>
			<!--到期日-->
			<td  class="td-rightbottom"  align="left" height="18">&nbsp;</td>
			<!--利率-->
			<td  class="td-rightbottom"  align="left" height="18">&nbsp;</td>
			<!--存款金额-->

			<td  class="td-rightbottom"  align="left" height="18">
				&nbsp;<strong>存款余额总计：</strong>
			</td>
			<!--存款余额-->
			<td  class="td-rightbottom"  align="left" height="18">
				&nbsp;<%=DataFormat.formatListAmount(marginDepositSum)%>
			</td>
			<!--备注-->
			<td class="td-rightbottom"  align="left" height="18">&nbsp;</td>
		</tr>
	</table>
	
 --%>
      
            

      
      <table border="0" bordercolor="#999999"  align="center" width="700px"  border="0" cellspacing="0" cellpadding="0">
	    <tr> 
	    <td  colspan="3" align="left" valign="top" id="aaa" height="16" ><br>
<%
			Timestamp ts=Env.getSystemDateTime(); 
%>
	      <span class="td-rightbottom" id="printDate">查询日期：<%=ts.toString().substring(0,10)%> 查询时间：<%=ts.toString().substring(10,19)%></span><br>
	    </td>
	   </tr>
        <tr> 
          <td width="471" name="" id=""> 
            <div align="right"></div>
          </td>
          <td width="49"> 
            <div align="right"></div>
          </td>
          <td width="50"> 
            
          </td>
        </tr>
      </table>


<%				
		OBHtml.showOBHomeEnd(out);
	    }
		catch( Exception exp )
		{
			Log.print(exp.getMessage());
		}  
		
%>

<%@ include file="/common/SignValidate.inc" %> 
<%

	if(hasRight){

 %>
<BODY language="javascript" onresize="ReSetSignaturePosition();" style="margin-top:0;margin-bottom:0;padding-top:0;padding-bottom:0">	
<OBJECT id="SignatureControl" codebase="/websignature/cab/iSignatureHTML.cab#Version=7,1,0,196" classid="clsid:D85C89BE-263C-472D-9B6B-5264CD85B36E" width=0 height=0>
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
/**
	打印时签章位置的调整    add by zhanglei   2010.06.25
**/
var pagecount = "<%=pagecount%>";
window.onbeforeprint=function(){
	setPirntPosition();
}
window.onafterprint=function(){
	ReSetSignaturePosition();
}
		try{
			var sx = document.getElementById("getTableWidthEle").clientWidth;
			var left = getElementLeft(document.getElementById("getTableWidthEle"));
			var sy;
			oRect   =   document.getElementById("printDate").getBoundingClientRect();   
			var width = document.getElementById("printDate").clientWidth;
			y=oRect.top   
			sy=parseInt(y)-89;
			sx = (parseInt(sx)-100+left);
		    document.all.SignatureControl.EnableMove = "false";          //设置签章是否可以移动
		    document.all.SignatureControl.PassWord = "123456";           //签章密码,系统默认为"",当设置改参数后点签章后弹出的选择签章窗体中的密码将默认为该密码      
		    document.all.SignatureControl.ShowSignatureWindow = "0";     //读取、设置选择签章的窗口是否可见
		    document.all.SignatureControl.FieldsList = "strTransNos=组合业务编号";          //读取、设置签章保护信息列表
		    document.all.SignatureControl.SaveHistory = "false";         //读取、设置是否保存历史记录true-false
		    document.all.SignatureControl.UserName = "<%=Env.getClientName()%>"; //读取、设置签章的用户名称
		   	document.all.SignatureControl.PositionByTagType = 0;
		    document.all.SignatureControl.Position(sx,sy);  //设置签章的位置    //设置签章什么位置在什么元素上和Position处(0:左上角、1:中间、2:右上角)
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
		    	//如果窗口大小变化了，签章的位置也要改变。  add by zhanglei  2010.06.11	    
	function ReSetSignaturePosition(){
			var sx = document.getElementById("getTableWidthEle").clientWidth;
			var left = getElementLeft(document.getElementById("getTableWidthEle"));
			var sy;
			oRect   =   document.getElementById("printDate").getBoundingClientRect();   
			var width = document.getElementById("printDate").clientWidth;
			y=oRect.top   
			sy=parseInt(y)-89;;
			sx = (parseInt(sx)-100+left);
			document.all.SignatureControl.MovePositionToNoSave(sx,sy); 
	}	
	function setPirntPosition(){
		    oldScrollTop=document.body.scrollTop;
			document.body.scrollTop=0;
			var sx;
			var sy;
			oRect   =   document.getElementById("printDate").getBoundingClientRect();   
			var width = document.getElementById("printDate").clientWidth;
			x=oRect.left   
			y=oRect.top
			var allYval = 0;   
			if(pagecount>1){
				for(var i=1;i<=pagecount-1;i++){
					var o=document.getElementById("printDate_"+i).getBoundingClientRect();
					var yval = o.top;
					allYval=(975-parseInt(yval)+parseInt(allYval));
				}
			}
			sy=parseInt(y)-80+parseInt(allYval);
			sx = parseInt(x)+590;
			document.all.SignatureControl.MovePositionToNoSave(sx,sy); 
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
			
</script>
<%
	}
%>