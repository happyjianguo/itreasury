<%--
 页面名称 ： batchpay_c.jsp
 页面功能 :  批量导入数据验证页面
 作    者 ： niweinan
 日    期 ： 2011-2-25
 特殊说明 ：
 实现操作说明：
 修改历史 ：
--%>
<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.ebank.util.*" %>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef" %>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.AccountBalanceInfo" %>
<%@ page import="com.iss.itreasury.util.DataFormat" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%

	/* 标题固定变量 */
	String strTitle = "批量付款";


	
try{
	//用户登录检测 
    if (sessionMng.isLogin() == false)
    {
    	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
    	out.flush();
    	return;
    }

    // 判断用户是否有权限 
    if (sessionMng.hasRight(request) == false)
    {
        out.println(sessionMng.hasRight(request));
    	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
    	out.flush();
    	return;
    }
	
	String[] sPayerAcctNo = request.getParameterValues("sPayerAcctNo");
	String[] strAmount = request.getParameterValues("dAmount");
	OBFinanceInstrDao obfinanceInstrDao = new OBFinanceInstrDao();
	AccountBalanceInfo accountBalanceInfo = null;
	double UsableBalance = 0.00;
	double totalBalance = 0.00;
	double remainBalance = 0.00;
	for(int i=0;i<sPayerAcctNo.length;i++)
	{
		accountBalanceInfo = new AccountBalanceInfo();
		accountBalanceInfo = obfinanceInstrDao.getCurrBalanceByAccountID(NameRef.getAccountIdByNo(sPayerAcctNo[i]),sessionMng.m_lCurrencyID, -1);
		UsableBalance = accountBalanceInfo.getUsableBalance(); 
		UsableBalance = DataFormat.formatDouble(UsableBalance);
		totalBalance = DataFormat.formatDouble(Double.valueOf(DataFormat.reverseFormatAmount(strAmount[i])).doubleValue());
		remainBalance = UsableBalance - totalBalance;
		if(remainBalance<0)
		{
			out.println("<div id='result'>"+sPayerAcctNo[i]+"</div>");
			break;
		}
		if(i>=sPayerAcctNo.length-1)
		{
			out.println("<div id='result'>success</div>");
			break;
		}
	}
	
}
catch(Exception exp)
{
	exp.printStackTrace();
}
%>