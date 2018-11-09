<%@ page contentType="text/html; charset=GBK" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%/*
    response.setHeader("Cache-Control","no-stored");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader("Expires",0);*/
%>

    <%@page import="java.util.*,
	com.iss.itreasury.util.*,
	com.iss.itreasury.ebank.util.NameRef,
	com.iss.itreasury.ebank.approval.bizlogic.InutApprovalRecordBiz,
	com.iss.itreasury.ebank.approval.dataentity.InutApprovalRecordInfo,
	com.iss.inut.workflow.ws.WorkflowManager
	" %>
	<%@page import="com.iss.itreasury.ebank.util.OBSortUtil"%>	
	<%@page import="com.iss.itreasury.ebank.obhistory.bizlogic.HistoryBiz"%>
	<%@page import="com.iss.itreasury.ebank.obhistory.dataentity.HistoryAdviseInfo"%>
<%String strContext = request.getContextPath();%>
<%
		long ID=-1;     //合同或者贷款的申请ID
		long typeID=-1; //附件类型
		String buttonCaption="";
		boolean showOnly=false;
		String control = null;	
		long ModuleID = -1;
		long TransTypeID = -1;	
		long TransSubTypeID = -1;
		long CurrencyID = -1;
		long ClientID = -1;
		long OfficeID = -1;	
		String transNo = "";//网银交易号
		long transType = -1;
		//取参数变量
		request.setCharacterEncoding("GBK");
		String strTmp = "";
		strTmp = (String)request.getParameter("lID");
        if( strTmp != null && strTmp.length() > 0 )
		{
		     ID = Long.parseLong(strTmp.trim());
		}
		if(ID < 0)
		{
			strTmp = (String)request.getParameter("transNo");
			transNo = strTmp;
		}
		strTmp = (String)request.getParameter("transType");
		if( strTmp != null && strTmp.length() > 0)
		{
			transType = Long.valueOf(strTmp).longValue();
		}
			
%>
<BODY>

<link rel="stylesheet" href="/webloan/css/approve.css" type="text/css">
<% 
		//Modify by leiyang date 2007/07/25
		WorkflowManager workflowManager = null;
		try{	
			   	workflowManager = WorkflowManager.instance(String.valueOf(sessionMng.m_lUserID));
			   	List approvalVector = new ArrayList();
			   	List sysApprovalVector = new ArrayList();
			   	HistoryBiz historyBiz=new HistoryBiz();
			   	if(ID > 0)
			   	{
			   		approvalVector = workflowManager.getHistoryAdvise(ID);
			   		HistoryAdviseInfo qInfo=new HistoryAdviseInfo();
			   		qInfo.setEntryID(ID);
			   		sysApprovalVector=historyBiz.queryByCondition(qInfo);	
			   	}
			   	else
			   	{
			   		InutApprovalRecordBiz biz = new InutApprovalRecordBiz();
			   		InutApprovalRecordInfo qInfo = new InutApprovalRecordInfo();
			   		qInfo.setTransID(transNo);
			   		qInfo.setTransTypeID(transType);
			   		Collection c = biz.queryByCondition(qInfo);
			   		if(c != null && c.size() > 0)
			   		{
			   			Iterator it = c.iterator();
			   			while(it.hasNext())
			   			{
			   				InutApprovalRecordInfo tempInfo = (InutApprovalRecordInfo)it.next();
			   				List tempList = workflowManager.getHistoryAdvise(tempInfo.getApprovalEntryID());
			   				approvalVector.addAll(tempList);
			   				HistoryAdviseInfo qhaInfo=new HistoryAdviseInfo();
					   		qhaInfo.setEntryID(tempInfo.getApprovalEntryID());
					   		List sysTempList=historyBiz.queryByCondition(qhaInfo);
					   		sysApprovalVector.add(sysTempList);
			   			}
			   		}
			   	}   	
			    List list = new ArrayList();
				list=OBSortUtil.AdviseRecordSort(approvalVector,sysApprovalVector);		
				 %>
	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class=list_table id="table1">
         <tr class=itemtitle> 
         	<td width="20%" height="26" align="center">序号</td>
           	<td width="20%" height="26" align="center">审核人</td>
           	<td width="25%" height="26" align="center">日期和时间</td>
           	<td width="20%" height="26" align="center">操作</td>
           	<td width="45%" height="26" align="center">意见内容</td>
         </tr>
	<%		
   	
	   	if ( (list!=null)&&(list.size()>0) )
		{ 			
			int num = 0;				
			Iterator it = list.iterator();
			while(it.hasNext())
			{					
				 HistoryAdviseInfo haInfo = (HistoryAdviseInfo)it.next();		
	                %>
       <tr> 
         <td align="center" height="26"><%=++num%></td>
         <td align="center" height="26"><%=haInfo.getOperator()%></td>
         <td align="center" height="26"><%=DataFormat.formatDate((java.util.Date )haInfo.getOpTime(),DataFormat.FMT_DATE_YYYYMMDD_HHMMSS)%></td>
         <td align="center" height="26"><%=DataFormat.formatNullString((String)haInfo.getAction())%></td>
         <td align="center" height="26"><%=DataFormat.formatNullString(haInfo.getAdvise())%></td>
       </tr>
           <%	} 	%>
           <% }else{ 	%>
        <tr> 
          <td height="26">&nbsp;</td>
          <td height="26">&nbsp;</td>
          <td height="26">&nbsp;</td>
          <td height="26">&nbsp;</td>
          <td height="26">&nbsp;</td>

        </tr>
	                 <% } %>
	</table>	
</BODY>
<%
//Modify by leiyang date 2007/07/25
}
catch(Exception e)
{
	e.printStackTrace();
}
finally
{
	try
			{
				if(workflowManager!=null)
				{
					workflowManager.closeSession();
				}
			}
			catch(Exception we)
			{
				we.printStackTrace();
			}
}
%>