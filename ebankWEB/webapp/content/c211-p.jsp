<%@ page contentType="text/html;charset=gbk"%>
<%@ page import="java.util.*,
				com.iss.itreasury.util.*,
				com.iss.itreasury.loan.util.*,
				com.iss.itreasury.ebank.obcontent.bizlogic.*,
				com.iss.itreasury.ebank.util.*,
				com.iss.itreasury.loan.loancommonsetting.dataentity.*,
				com.iss.itreasury.ebank.obsystem.bizlogic.*,				
				com.iss.itreasury.ebank.obcontent.dao.OBContentDao"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%

	String strOfficeName = sessionMng.m_strOfficeName;
	String strUserName = sessionMng.m_strUserName;
	String strTableTitle = "资产负债表";
	try
	{
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
		
	//定义变量
	long lCurrencyID = sessionMng.m_lCurrencyID;
	long lOfficeID = sessionMng.m_lOfficeID;
	long lUserID = sessionMng.m_lUserID;
	long lIsSM = Constant.YesOrNo.NO;

	long lID = -1;
	long lClientID = 0;
	String strClientCode = "";
	String strClientName = "";
	String strLicence = "";
	//double dAmount=1111;
	double[][] dAmount=new double[3][100];
		String[] strContents = {"","",""};
		String[] strContentYears = {"","",""};
		String sTemp = "";
		long[] lContentID = {-1,-1,-1};
		
		sTemp = (String) request.getAttribute("lClientID");
		//lClientID = Long.parseLong(sTemp);
		if( sTemp != null && sTemp.length() > 0 )
		{
			lClientID = Long.parseLong(sTemp.trim());
		}
		sTemp = (String) request.getAttribute("strClientCode");
		if( sTemp != null && sTemp.length() > 0 )
		{
			strClientCode =  sTemp.trim();
		}
		sTemp = (String) request.getAttribute("strClientName");
		if( sTemp != null && sTemp.length() > 0 )
		{
			strClientName =  sTemp.trim();
		}
		sTemp = (String) request.getAttribute("strLicence");
		if( sTemp != null && sTemp.length() > 0 )
		{
			strLicence =  sTemp.trim();
		}

		sTemp = (String) request.getAttribute("lContentID1");
		if( sTemp != null && sTemp.length() > 0 )
		{
			lContentID[0] = Long.parseLong(sTemp.trim());
		}
		sTemp = (String) request.getAttribute("sContent1");
		if( sTemp != null && sTemp.length() > 0 )
		{
			strContents[0] = sTemp.trim();
		}
		sTemp = (String) request.getAttribute("sContentYear1");
		if( sTemp != null && sTemp.length() > 0 )
		{
			strContentYears[0] = sTemp.trim();
		}

		sTemp = (String) request.getAttribute("lContentID2");
		if( sTemp != null && sTemp.length() > 0 )
		{
			lContentID[1] = Long.parseLong(sTemp.trim());
		}
		sTemp = (String) request.getAttribute("sContent2");
		if( sTemp != null && sTemp.length() > 0 )
		{
			strContents[1] =  sTemp.trim();
		}
		sTemp = (String) request.getAttribute("sContentYear2");
		if( sTemp != null && sTemp.length() > 0 )
		{
			strContentYears[1] = sTemp.trim();
		}

		sTemp = (String) request.getAttribute("lContentID3");
		if( sTemp != null && sTemp.length() > 0 )
		{
			lContentID[2] = Long.parseLong(sTemp.trim());
		}
		sTemp = (String) request.getAttribute("sContent3");
		if( sTemp != null && sTemp.length() > 0 )
		{
			strContents[2] =  sTemp.trim();
		}
		sTemp = (String) request.getAttribute("sContentYear3");
		if( sTemp != null && sTemp.length() > 0 )
		{
			strContentYears[2] = sTemp.trim();
		}
		
		String arrContent[][] = new String[3][100];
	for(int i = strContents.length-1;i>=0;i--)
	{
		
		if (strContents[i].length() > 0)
		{
			
			int nIndex; 
			int nTmp = 0;
			nIndex = strContents[i].indexOf(OBContentDao.CONTENT_SEPERATOR);
			while (nIndex >= 0)
			{
				arrContent[i][nTmp] = strContents[i].substring(0, nIndex);
				
				if(arrContent[i][nTmp] != null && arrContent[i][nTmp] != "")
				{
					try
					{
						dAmount[i][nTmp] =  Double.parseDouble((arrContent[i][nTmp]).trim());
					}
					catch(Exception e)
					{
						dAmount[i][nTmp] =0;
					}
				}
				else
				{
					dAmount[i][nTmp] =0;
				}
	
				strContents[i] = strContents[i].substring(nIndex + 4);
				nIndex = strContents[i].indexOf(OBContentDao.CONTENT_SEPERATOR);
				nTmp++;
			}
			arrContent[i][nTmp] = strContents[i];
			
				if(arrContent[i][nTmp] != null && arrContent[i][nTmp] != "")
				{
					try
					{
						dAmount[i][nTmp] =  Double.parseDouble((arrContent[i][nTmp]).trim());
					}
					catch(Exception e)
					{
						dAmount[i][nTmp] =0;
					}
				}
				else
				{
					dAmount[i][nTmp] =0;
				}
		}
	}
		OBHtml.showOBHomeHead(out, sessionMng, strTableTitle, Constant.YesOrNo.NO);
		int index = 1;
%>


<%
		String strYearNow="";
		String strYearPre1="";
		String strYearPre2="";
		String strYearEnd="";
		strYearNow=DataFormat.getDateString(Env.getSystemDate());
		strYearNow=strYearNow.substring(0,strYearNow.indexOf("-"));
		strYearPre1=DataFormat.getDateString(DataFormat.getPreviousYear(Env.getSystemDate(),1));
		strYearPre1=strYearPre1.substring(0,strYearPre1.indexOf("-"));
		strYearPre2=DataFormat.getDateString(DataFormat.getPreviousYear(Env.getSystemDate(),2));
		strYearPre2=strYearPre2.substring(0,strYearPre2.indexOf("-"));
		strYearEnd=DataFormat.getChineseDateString(Env.getSystemDate());
		
		if(strContentYears[0]=="") strContentYears[0]=strYearPre2;
		if(strContentYears[1]=="") strContentYears[1]=strYearPre1;
		if(strContentYears[2]=="") strContentYears[2]=strYearNow;

%>


<SCRIPT language="javascript" src="/webob/js/Control.js"></SCRIPT>
<SCRIPT language=JavaScript src="/webob/js/Check.js"></SCRIPT>
<form name="frm">
<table width="99%" class="top" height="3">
	<tr> 
		<td class="FormTitle" height=2  width="100%"><b><%=DataFormat.formatString(strTableTitle)%></b></td>
	</tr>
	<tr> 
		<td height="2"> 
		<table width="500" height="2" border=0>
			<tr bordercolor="#FFFFFF"> 
				<td width="250"></td>
				<td width="200"></td>
				<td width="200"></td>
				<td width="200"></td>
			</tr>
			<TR bordercolor="#FFFFFF"> 
				<TD>客户编号：</TD>
				<TD colspan="1">
					<INPUT class=box maxLength=16 name="txtClientCode" value="<%=DataFormat.formatString(strClientCode)%>" size=22 disabled>
				</TD>
				<TD>客户名称：</TD>
				<TD colspan="1">
					<INPUT class=box maxLength=16 name="txtClientName" value="<%=DataFormat.formatString(strClientName)%>" size=22 disabled>
				</TD>
			</TR>
			<TR bordercolor="#FFFFFF"> 
				<TD >营业执照号码：</TD>
				<TD colspan="1">
					<INPUT class=box maxLength=16 name="txtClientLicence" value="<%=DataFormat.formatString(strLicence)%>" size=22 disabled>
				</TD>
			</TR>
		</table>
		</td>
	</tr>
	<tr>
		<td colspan="4" height="1">
			<hr>
		</td>
	</tr>
	<tr>
		<td>
		<table>
			<tr bordercolor="#FFFFFF"> 
				<td width="200"></td>
				<td width="200"></td>
				<td width="200"></td>
				<td width="200"></td>
				<td width="200"></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td colspan="3" align=center>截止日期：<%=strYearEnd%></td>
				<td colspan="1" align=center>单位：&nbsp;元
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td height="3"> 
		<table width="100%" border="0" align="center" height="70" class="table1">
			<tr align=center> 
				<TD class="td-rightbottom" height=20 width="20%">项目</TD>
				<TD class="td-rightbottom" height=20 width="20%">
				<%
				if(lContentID[0] >= 0)
				{
				%>
					<A href="javascript:FrmSubmit(frm,1,<%=lContentID[0]%>,<%=strContentYears[0]%>)">
						<%=strContentYears[0]%>年
					</a>
				<%
				}
				else
				{
				%>
						<%=strYearPre2%>年
				<%
				}
				%>
				</TD>
				<TD class="td-rightbottom" height=20 width="20%">
				<%
				if(lContentID[1] >= 0)
				{
				%>
					<A href="javascript:FrmSubmit(frm,1,<%=lContentID[1]%>,<%=strContentYears[1]%>)">
						<%=strContentYears[1]%>年
					</a>
				<%
				}
				else
				{
				%>
						<%=strYearPre1%>年
				<%
				}
				%>
				</TD>
				<TD class="td-bottom" height=20 width="20%">
				<%
				if(lContentID[2] >= 0)
				{
				%>
					<A href="javascript:FrmSubmit(frm,1,<%=lContentID[2]%>,<%=strContentYears[2]%>)">
						<%=strContentYears[2]%>年
					</a>
				<%
				}
				else
				{
				%>
						<%=strYearNow%>年
				<%
				}
				%>
				</TD>
			</tr>
		<TR>
			<TD class="td-rightbottom" height=20><b>1.流动资产</b></TD>


			<TD class=ItemBody align=right>
				<%=DataFormat.formatDisabledAmount(dAmount[0][index],0)%>
			</TD>
			<TD class=ItemBody align=right>
				<%=DataFormat.formatDisabledAmount(dAmount[1][index],0)%>
			</TD>
			<TD class=ItemBody align=right>
				<%=DataFormat.formatDisabledAmount(dAmount[2][index++],0)%>
			</TD>
	
		</TR>
		<TR>
			<TD class="td-rightbottom" align=right>&nbsp;其中：货币资金</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[0][index])%>
			</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[1][index])%>
			</TD>
			<TD class="td-bottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[2][index++])%>
			</TD>
		</TR>
		<TR>
			<TD class="td-rightbottom" align=right>&nbsp;应收票据</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[0][index])%>
			</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[1][index])%>
			</TD>
			<TD class="td-bottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[2][index++])%>
			</TD>
		</TR>
		<TR>
			<TD class="td-rightbottom" align=right>&nbsp;应收账款</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[0][index])%>
			</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[1][index])%>
			</TD>
			<TD class="td-bottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[2][index++])%>
			</TD>
		</TR>
		<TR>
			<TD class="td-rightbottom" align=right>&nbsp;其他应收款</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[0][index])%>
			</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[1][index])%>
			</TD>
			<TD class="td-bottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[2][index++])%>
			</TD>
		</TR>
		<TR>
			<TD class="td-rightbottom" align=right>&nbsp;存货</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[0][index])%>
			</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[1][index])%>
			</TD>
			<TD class="td-bottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[2][index++])%>
			</TD>
		</TR>
		<TR>
			<TD class="td-rightbottom" height=20><b>2.长期投资</b></TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[0][index])%>
			</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[1][index])%>
			</TD>
			<TD class="td-bottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[2][index++])%>
			</TD>
		</TR>
		<TR>
			<TD class="td-rightbottom" height=20><b>3.固定资产</b></TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[0][index])%>
			</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[1][index])%>
			</TD>
			<TD class="td-bottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[2][index++])%>
			</TD>
		</TR>
		<TR>
			<TD class="td-rightbottom" align=right>&nbsp;其中：固定资产净值</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[0][index])%>
			</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[1][index])%>
			</TD>
			<TD class="td-bottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[2][index++])%>
			</TD>
		</TR>
		<TR>
			<TD class="td-rightbottom" align=right>&nbsp;在建工程</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[0][index])%>
			</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[1][index])%>
			</TD>
			<TD class="td-bottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[2][index++])%>
			</TD>
		</TR>
		<TR>
			<TD class="td-rightbottom"><b>4.无形和递延资产</b></TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[0][index])%>
			</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[1][index])%>
			</TD>
			<TD class="td-bottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[2][index++])%>
			</TD>
		</TR>
		<TR>
			<TD class="td-rightbottom"><b>5.总资产</b></TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[0][index])%>
			</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[1][index])%>
			</TD>
			<TD class="td-bottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[2][index++])%>
			</TD>
		</TR>
		<TR>
			<TD class="td-rightbottom"><b>6.负债合计</b></TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[0][index])%>
			</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[1][index])%>
			</TD>
			<TD class="td-bottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[2][index++])%>
			</TD>
		</TR>
		<TR>
			<TD class="td-rightbottom">流动负债</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[0][index])%>
			</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[1][index])%>
			</TD>
			<TD class="td-bottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[2][index++])%>
			</TD>
		</TR>
		<TR>
			<TD class="td-rightbottom" align=right>&nbsp;其中：短期借款</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[0][index])%>
			</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[1][index])%>
			</TD>
			<TD class="td-bottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[2][index++])%>
			</TD>
		</TR>
		<TR>
			<TD class="td-rightbottom" align=right>&nbsp;应付票据</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[0][index])%>
			</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[1][index])%>
			</TD>
			<TD class="td-bottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[2][index++])%>
			</TD>
		</TR>
		<TR>
			<TD class="td-rightbottom" align=right>&nbsp;应付账款</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[0][index])%>
			</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[1][index])%>
			</TD>
			<TD class="td-bottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[2][index++])%>
			</TD>
		</TR>
		<TR>
			<TD class="td-rightbottom" align=right>&nbsp;其他应交款</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[0][index])%>
			</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[1][index])%>
			</TD>
			<TD class="td-bottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[2][index++])%>
			</TD>
		</TR>
		<TR>
			<TD class="td-rightbottom" align=right>&nbsp;其他应付款</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[0][index])%>
			</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[1][index])%>
			</TD>
			<TD class="td-bottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[2][index++])%>
			</TD>
		</TR>
		<TR>
			<TD class="td-rightbottom">长期负债</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[0][index])%>
			</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[1][index])%>
			</TD>
			<TD class="td-bottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[2][index++])%>
			</TD>
		</TR>
		<TR>
			<TD class="td-rightbottom" align=right>&nbsp;其中：长期借款</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[0][index])%>
			</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[1][index])%>
			</TD>
			<TD class="td-bottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[2][index++])%>
			</TD>
		</TR>
		<TR>
			<TD class="td-rightbottom" align=right>&nbsp;其他长期借款</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[0][index])%>
			</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[1][index])%>
			</TD>
			<TD class="td-bottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[2][index++])%>
			</TD>
		</TR>
		<TR>
			<TD class="td-rightbottom" align=right>&nbsp;应付统借统还款</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[0][index])%>
			</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[1][index])%>
			</TD>
			<TD class="td-bottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[2][index++])%>
			</TD>
		</TR>
		<TR>
			<TD class="td-rightbottom" height=20><b>7.所有者权益</b></TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[0][index])%>
			</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[1][index])%>
			</TD>
			<TD class="td-bottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[2][index++])%>
			</TD>
		</TR>
		<TR>
			<TD class="td-rightbottom" align=right>&nbsp;其中：实收资本</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[0][index])%>
			</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[1][index])%>
			</TD>
			<TD class="td-bottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[2][index++])%>
			</TD>
		</TR>
		<TR>
			<TD class="td-rightbottom" align=right>&nbsp;资本公积</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[0][index])%>
			</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[1][index])%>
			</TD>
			<TD class="td-bottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[2][index++])%>
			</TD>
		</TR>
		<TR>
			<TD class="td-rightbottom" align=right>&nbsp;盈余公积</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[0][index])%>
			</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[1][index])%>
			</TD>
			<TD class="td-bottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[2][index++])%>
			</TD>
		</TR>
		<TR>
			<TD class="td-rightbottom" align=right>&nbsp;未分配利润</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[0][index])%>
			</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[1][index])%>
			</TD>
			<TD class="td-bottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[2][index++])%>
			</TD>
		</TR>
		<TR>
			<TD class="td-rightbottom" height=20><b>8.销售收入</b></TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[0][index])%>
			</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[1][index])%>
			</TD>
			<TD class="td-bottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[2][index++])%>
			</TD>
		</TR>
		<TR>
			<TD class="td-rightbottom" height=20><b>9.销售成本</b></TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[0][index])%>
			</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[1][index])%>
			</TD>
			<TD class="td-bottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[2][index++])%>
			</TD>
		</TR>
		<TR>
			<TD class="td-rightbottom" height=20><b>10.销售毛利</b></TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[0][index])%>
			</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[1][index])%>
			</TD>
			<TD class="td-bottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[2][index++])%>
			</TD>
		</TR>
		<TR>
			<TD class="td-rightbottom" height=20><b>11.利润总额</b></TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[0][index])%>
			</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[1][index])%>
			</TD>
			<TD class="td-bottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[2][index++])%>
			</TD>
		</TR>
		<TR>
			<TD class="td-rightbottom" height=20><b>12.净利润</b></TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[0][index])%>
			</TD>
			<TD class="td-rightbottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[1][index])%>
			</TD>
			<TD class="td-bottom" align=right>&nbsp;
				<%=DataFormat.formatString(arrContent[2][index++])%>
			</TD>
		</TR>
				</table>
			</td>
		</tr>
		<TR bordercolor="#999999"> 
			<TD>
			<table>
				<TR>
				<TD width=500>报表打印日期： <%=strYearEnd%>
				</TD>
				</TR>
			</table>
			</TD>
		</TR>					
</table>
	<input type="hidden" name="control" value="">
					<INPUT type="hidden" name="lClientID" value="<%=lClientID%>">
					<INPUT type="hidden" name="strClientCode" value="<%=strClientCode%>">
					<INPUT type="hidden" name="strClientName" value="<%=strClientName%>">
					<INPUT type="hidden" name="strLicence" value="<%=strLicence%>">
					<INPUT type="hidden" name="lContentID" value="-1">
					<input type="hidden" name="SUBMIT" >
					<INPUT type="hidden" name="PageName" value="c212-v.jsp">
					<INPUT type="hidden" name="PageNo" value="1">
					<INPUT type="hidden" name="yearNo" value="1">
</form>

<script language="JavaScript">
<!--
window.print();
//-->
</script>


<script language="JavaScript">

function FrmSubmit(form_1,nType,ContentID,yNo)
{
	var bSubmit = false;

	if (nType == "1")//进入修改页面
	{
			form_1.lContentID.value = ContentID;
			form_1.PageName.value = "c212-v.jsp";
			form_1.SUBMIT.value = "";
			form_1.yearNo.value = yNo;
			form_1.action="c202-c.jsp";
			form_1.submit();
			return true;
	}
	if (nType == "2")//关闭当前窗口
	{
		if (confirm("是否关闭当前窗口？"))
		{
			window.close();
			return false;
		}
		else
		{
			return false;
		}
	}
	if (nType == "3")//新增
	{
<%
		if((lContentID[2] > 0)&&(strYearNow.equals(strContentYears[2])))
		{
%>
			alert("今年资产负债表已经新增，请选择修改");
			return false;
<%
		}
%>
<%
		if(lContentID[0] <= 0)
		{
%>
			form_1.yearNo.value = <%=strYearPre2%>;
<%
		}
		else if(lContentID[1] <= 0)
		{
%>
			form_1.yearNo.value = <%=strYearPre1%>;
<%
		}
		else
		{
%>
			form_1.yearNo.value = <%=strYearNow%>;
<%
		}
	
%>
		if (confirm("是否新增？"))
		{
			form_1.lContentID.value = "-1";
			form_1.SUBMIT.value = "";
			form_1.action="c212-v.jsp";
			form_1.submit();
			return true;
		}
		else
		{
			return false;
		}
	}
}
</SCRIPT>


<%
		OBHtml.showOBHomeEnd(out);
	}
	catch(IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng,ie,"资产负债表","", Constant.RecordStatus.VALID); 
		out.flush();
		return;
	}
%>
