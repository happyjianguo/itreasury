<!-- 
/**ҳ�湦��˵��
 * ҳ������ ��v001-e.jsp
 * ҳ�湦�� : ҵ����־��ѯҳ��--����Excelҳ��
 * ��    �� ��li liang
 * ��    �� ��2009-5-25
 */ 
 -->
<jsp:directive.page import="com.iss.itreasury.util.Env"/>

<!--�ർ�벿�ֿ�ʼ-->
<%@ page contentType = "text/html;charset=GBK" %>
<%@ page import="com.iss.itreasury.util.*,java.net.URLEncoder"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.ebank.system.util.*"%>
<%@ page import="com.iss.itreasury.ebank.util.NameRef"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTHTML"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.settlement.print.IPrintTemplate"%>
<%@ page import="com.iss.itreasury.system.translog.dataentity.QueryTransLogInfo"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<% String strContext = request.getContextPath();%>
<%IPrintTemplate.showPrintHeadForExcel(out,false,"A4","",1,sessionMng.m_lOfficeID);%>
<%
try
{
	Log.print("***����ҳ��V012.jsp****");
	//������
	//if(!SETTHTML.validateRequest(out, request,response)) return;
	long lPageLine = 13000;//ÿҳ��ҳ������
	long lLine = 0;//��ǰ����
	String strPageLoaderKey = (String)request.getAttribute("_pageLoaderKey");
	Log.print("Result Page ::��strPageLoaderKey : " + strPageLoaderKey);
	
	String strTemp = null;
	QueryTransLogInfo[] resultInfos = (QueryTransLogInfo[])request.getAttribute(Constant.PageControl.SearchResults);
	
	String strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
	String strFailPageURL = (String)request.getAttribute("strFailPageURL");

%>

<BODY text="#000000" bgcolor="#FFFFFF">
<%
	//IPrintTemplate.showPrintHead(out,false,"A4","",1,sessionMng.m_lOfficeID);
	//settPrint.showPrintReport(out,sessionMng,"A4",2);
%>
	<TABLE width="1000" border="0" cellspacing="0" cellpadding="0">
		<TR>
			<TD width="15%">&nbsp;	
			</TD>
			<TD width="70%" align="center"><B><FONT style="font-size:22px">ҵ����־��ѯ</FONT></B>
			</TD>
			<TD width="15%">&nbsp;	
			</TD>
		</TR>
	</TABLE>
	<TABLE border="0" cellspacing="0" cellpadding="3" class="table1" width="1000">
		<TR align="center">
	        <TD class="td-rightbottom">�����û�</TD>
			<TD class="td-rightbottom">�˵�</TD>
			<TD class="td-rightbottom">��Ҫ��Ϣ</TD>
			<TD class="td-rightbottom">״̬</TD>
			<TD class="td-rightbottom">����</TD>
			<TD class="td-rightbottom">ʱ��</TD>
			<TD class="td-rightbottom">����IP</TD>
			<TD class="td-bottom">����</TD>
		</TR>
<%
		int resultCount = 0;
		for( int i=0; resultInfos != null && i<resultInfos.length; i++ )
		{
			resultCount++;
			
			QueryTransLogInfo resultInfo = (QueryTransLogInfo)resultInfos[i];
			//String strDetailPageURL = strContext+"/settlement/query/control/querycontrol.jsp?TransactionTypeID="+resultInfo.getTransactionTypeID()+"&TransNo="+resultInfo.getTransNo()+"&strFailPageURL=../query/view/v012.jsp";
			
			lLine ++;
			if(lLine > lPageLine)
			{
				lLine = 1;
			%>
			</TABLE>
			
			<TABLE width="1000" border="0" cellspacing="4" cellpadding="0">
				<TR>
					<TD align="left">�����ˣ�<%=sessionMng.m_strUserName%></TD>
					<TD align="right">��ӡʱ�䣺<%=Env.getSystemDateString(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%></TD>
				</TR>
			</TABLE>
			<br clear=all style='page-break-before:always'>
			<TABLE width="1000" border="0" cellspacing="0" cellpadding="0">
				<TR>
					<TD width="15%">&nbsp;	
					</TD>
					<TD width="70%" align="center"><B><FONT style="font-size:22px">ҵ����־��ѯ</FONT></B>
					</TD>
					<TD width="15%">&nbsp;	
					</TD>
				</TR>
			</TABLE>
			
			<TABLE border="0" cellspacing="0" cellpadding="3" class="table1" width="1000">
			<TR align="center">
				<TD class="td-rightbottom">�����û�</TD>
				<!-- 
				<TD class="td-rightbottom">��־����</TD>
				 -->
				<TD class="td-rightbottom">�˵�</TD>
				<TD class="td-rightbottom">��Ҫ��Ϣ</TD>
				<TD class="td-rightbottom">״̬</TD>
				<TD class="td-rightbottom">����</TD>
				<TD class="td-rightbottom">ʱ��</TD>
				<TD class="td-rightbottom">����IP</TD>
				<TD class="td-bottom">����</TD>
			</TR>
<%
			}
						//��ȡԪ��-����ʱ��
						String datetime = resultInfo.getAccesstime().toString();
						//�á��ո񡱷���ԭΪTimestamp��ʽ���ַ�����indexΪ���
						int index = datetime.indexOf(" ");
						String date = datetime.substring(0,index);
						String time = datetime.substring(index,datetime.length()-2);
						System.out.println(" time"+time);
%>	
		<TR align="center">
			<TD class="td-topright" align="center" nowrap>	
			  <%=((resultInfo.getUsername() == null || resultInfo.getUsername().trim().equals("")) ? "&nbsp;" : resultInfo.getUsername())%>
			</TD>
			<TD class="td-topright" align="center" >
			  <%=(resultInfo.getFunctionpointdescription() == null || resultInfo.getFunctionpointdescription().trim().equals(""))? "&nbsp;" : resultInfo.getFunctionpointdescription()%>
			</TD>
			<TD class="td-topright" align="center" >
			  <%=(resultInfo.getMaininfo() == null || resultInfo.getMaininfo().trim().equals("")) ? "&nbsp;" : resultInfo.getMaininfo()%>
			</TD>
			<TD class="td-topright" align="center" nowrap>
			  <%=(Constant.LogActionResult.getName(resultInfo.getStatus()) ==  null || Constant.LogActionResult.getName(resultInfo.getStatus()).trim().equals("")) ? "&nbsp;" : Constant.LogActionResult.getName(resultInfo.getStatus())%>
			</TD>
			<TD class="td-topright" align="center" nowrap>
			  <%=(date == null  || date.trim().equals("")) ? "&nbsp;" :date%>
			</TD>
			<TD class="td-topright" align="center" nowrap>
			  <%=(time == null  || time.trim().equals("")) ? "&nbsp;" :time%>
			</TD>
			<TD class="td-topright" align="center" nowrap>
			  <%=(resultInfo.getClientip() == null || resultInfo.getClientip().trim().equals("")) ? "&nbsp;" :resultInfo.getClientip()%>
			</TD>
			<TD class="td-top" align="center" nowrap>	
			  <%=(NameRef.getCurrencyNameByID(resultInfo.getCurrencyid()) == null  || NameRef.getCurrencyNameByID(resultInfo.getCurrencyid()).trim().equals("")) ? "&nbsp;" :NameRef.getCurrencyNameByID(resultInfo.getCurrencyid())%>
			</TD>
		</TR>
<%  
		}
		//û�м�¼��ʾ�հ���
		if (resultCount == 0)
		{
%>	
		<TR align="center">
			<TD class="td-topright" align="center">&nbsp;</TD>
			<TD class="td-topright" align="center">&nbsp;</TD>
			<TD class="td-topright" align="center">&nbsp;</TD>
			<TD class="td-topright" align="center">&nbsp;</TD>
			<TD class="td-topright" align="center">&nbsp;</TD>
			<TD class="td-topright" align="center">&nbsp;</TD>
			<TD class="td-topright" align="center">&nbsp;</TD>
			<TD class="td-bottom" align="center">&nbsp;</TD>
		</TR>
<%  
		}
%>
	</TABLE>
	<TABLE width="1000" border="0" cellspacing="4" cellpadding="0">
		<TR>
			<TD align="left">�����ˣ�<%=sessionMng.m_strUserName%></TD>
			<TD align="right">��ӡʱ�䣺<%=Env.getSystemDateString(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%></TD>
		</TR>
</TABLE></BODY>
<%

}
catch( Exception exp )
{
	exp.printStackTrace();
	Log.print(exp.getMessage());
}
%>
