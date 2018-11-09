<%@page language="java" contentType="text/html;charset=gbk" %>
<%@page import="com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao" %>  
<%@page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.OpenDateInfo" %>
<%
	String temp = null;
	long accountId = -1;
	long currencyId = -1;
	long lOfficeId = -1;
	temp = request.getParameter("accountId");
	if(temp!=null&&temp.trim().length()>0)
	{
		accountId = Long.parseLong(temp);
	}
	temp = request.getParameter("currenctId");
	if(temp!=null&&temp.trim().length()>0)
	{
		currencyId = Long.parseLong(temp);
	}
	temp = request.getParameter("officeId");
	if(temp!=null&&temp.trim().length()>0)
	{
		lOfficeId = Long.parseLong(temp);
	}
	OBFinanceInstrDao obFinanceInstrDao = new OBFinanceInstrDao();
	if(lOfficeId<0&&accountId>0)
	{
		lOfficeId = obFinanceInstrDao.findOfficeByAccountId(accountId);
	}
	if(currencyId<0)
	{
		currencyId = 1; //默认为人民币
	}
	OpenDateInfo openDateInfo = new OpenDateInfo();
	openDateInfo = obFinanceInstrDao.getOpenDate(lOfficeId, currencyId);
	String openDate = openDateInfo.getOpenDate().toString();
	openDate = openDate.substring(0,10);
	out.println("<div id='result'>"+openDate+"</div>");
 %>

