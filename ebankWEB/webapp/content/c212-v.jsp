<%@page contentType="text/html;charset=gbk"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
    //response.setHeader("Cache-Control","no-stored");
	//response.setHeader("Pragma","no-cache");
	//response.setDateHeader("Expires",0);
%>
<%@page import="java.util.*,
				com.iss.itreasury.util.*,
				com.iss.itreasury.loan.util.*,
				com.iss.itreasury.ebank.obcontent.bizlogic.*,
				com.iss.itreasury.ebank.util.*,
				com.iss.itreasury.loan.loancommonsetting.dataentity.*,
				com.iss.itreasury.ebank.obsystem.bizlogic.*,				
				com.iss.itreasury.ebank.obcontent.dao.OBContentDao"
%>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<%
	try
	{
		String strTableTitle = "资产负债表";
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

		long lContentID = -1;
		long lClientID = -1;
		String strYearNo = "";
		String strContent = "";
		String sTemp = "";
		strContent = (String) request.getAttribute("sContent");
		sTemp = (String) request.getAttribute("lContentID");
		if( sTemp != null && sTemp.length() > 0 )
		{
			lContentID = Long.parseLong(sTemp.trim());
		}
		sTemp = (String) request.getAttribute("lClientID");
		if( sTemp != null && sTemp.length() > 0 )
		{
			lClientID = Long.parseLong(sTemp.trim());
		}
		sTemp = (String) request.getAttribute("yearNo");
		if( sTemp != null && sTemp.length() > 0 )
		{
			strYearNo = sTemp.trim();
		}
	
		String arrContent[] = new String[100];
	//*
		if (lContentID > 0 && strContent.length() > 0)
		{
			int nIndex; //","的索引位置
			int nTmp = 0;
			nIndex = strContent.indexOf(OBContentDao.CONTENT_SEPERATOR);
			while (nIndex >= 0)
			{
					arrContent[nTmp] = strContent.substring(0, nIndex);
				strContent = strContent.substring(nIndex + 4);
				nIndex = strContent.indexOf(OBContentDao.CONTENT_SEPERATOR);
				nTmp++;
			}
			arrContent[nTmp] = strContent;
		}//*/
	/////////////////////////////////////////////////////////////////
	/*
		double[] dAmount = new double[35];
		for(int i=0;i<35;i++)
		{
			dAmount[i]=10000;
		}//*/
%>

<%
		//年：1.修改：今年，去年，前年； 2.新增：明年
		String strYear="";
		String strYearNow="";
		String strYearPre1="";
		String strYearPre2="";
		String strYearEnd="";
		String strYearNext1="";
		strYearNow=DataFormat.getDateString(Env.getSystemDate());
		strYearNow=strYearNow.substring(0,strYearNow.indexOf("-"));
		strYearPre1=DataFormat.getDateString(DataFormat.getPreviousYear(Env.getSystemDate(),1));
		strYearPre1=strYearPre1.substring(0,strYearPre1.indexOf("-"));
		strYearPre2=DataFormat.getDateString(DataFormat.getPreviousYear(Env.getSystemDate(),2));
		strYearPre2=strYearPre2.substring(0,strYearPre2.indexOf("-"));
		strYearEnd=DataFormat.getChineseDateString(Env.getSystemDate());
		strYearNext1=DataFormat.getDateString(DataFormat.getPreviousYear(Env.getSystemDate(),-1));
		strYearNext1=strYearNext1.substring(0,strYearNext1.indexOf("-"));
		if(lContentID >0 )
		{/*
			strTableTitle +="——修改";
			if(lYearNo==1)	strYear=strYearPre2;
			else if(lYearNo==2)	strYear=strYearPre1;
			else if(lYearNo==3)	strYear=strYearNow;
			else
			{
				//strYear=strYearNext1;
				strYear=strYearNow;
			}//*/
		}
		else
		{
			strTableTitle +="——新增";
			//strYear=strYearNext1;
			strYear=strYearNow;
		}

		
		//显示文件头
		OBHtml.showOBHomeHead(out, sessionMng,strTableTitle, Constant.YesOrNo.NO);
		int index = 1;
%>

<SCRIPT language="javascript" src="/webob/js/Control.js"></SCRIPT>
<SCRIPT language=JavaScript src="/webob/js/Check.js"></SCRIPT>

<safety:resources />

<form name="frm" action="c002-c.jsp">
<table width="100%" class="top" height="121">
	<tr class="tableHeader"> 
		<td class="FormTitle" height=2  width="100%"><b><%=strTableTitle%></b></td>
	</tr>
	<tr>
		<td>
		<table>
			<tr bordercolor="#FFFFFF"> 
				<td width="200" height="1"></td>
				<td width="200" height="1"></td>
				<td width="200" height="1"></td>
				<td width="200" height="1"></td>
				<td width="200" height="1"></td>
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
		<td height="36"> 
		<table width="100%" border="0" align="center" height="70" class="ItemList">
			<tr align=center class="tableHeader"> 
				<TD class=ItemTitle height=20 width="30%">项目</TD>
				<TD class=ItemTitle height=20 width="80%"><%=strYearNo==""?strYearNow:strYearNo%>年</TD>
			</tr>
			<TR>
				<TD class=ItemBody height=20><b>1.流动资产</b></TD>
				<TD class=ItemBody align=center>
					<input class=tar name="textfield" type="text" size="30" value="<%=DataFormat.formatString(arrContent[index])%>" onFocus="nextfield ='textfield[<%=index++%>]';">
				</TD>
				
			</TR>
			<TR>
				<TD class=ItemBody align=right>其中：货币资金</TD>
				<TD class=ItemBody align=center>
					<input class=tar name="textfield" type="text" size="30" value="<%=DataFormat.formatString(arrContent[index])%>" onFocus="nextfield ='textfield[<%=index++%>]';">
				</TD>
				
			</TR>
			<TR>
				<TD class=ItemBody align=right>应收票据</TD>
				<TD class=ItemBody align=center>
					<input class=tar name="textfield" type="text" size="30" value="<%=DataFormat.formatString(arrContent[index])%>" onFocus="nextfield ='textfield[<%=index++%>]';">
				</TD>
				
			</TR>
			<TR>
				<TD class=ItemBody align=right>应收账款</TD>
				<TD class=ItemBody align=center>
					<input class=tar name="textfield" type="text" size="30" value="<%=DataFormat.formatString(arrContent[index])%>" onFocus="nextfield ='textfield[<%=index++%>]';">
				</TD>
				
			</TR>
			<TR>
				<TD class=ItemBody align=right>其他应收款</TD>
				<TD class=ItemBody align=center>
					<input class=tar name="textfield" type="text" size="30" value="<%=DataFormat.formatString(arrContent[index])%>" onFocus="nextfield ='textfield[<%=index++%>]';">
				</TD>
				
			</TR>
			<TR>
				<TD class=ItemBody align=right>存货</TD>
				<TD class=ItemBody align=center>
					<input class=tar name="textfield" type="text" size="30" value="<%=DataFormat.formatString(arrContent[index])%>" onFocus="nextfield ='textfield[<%=index++%>]';">
				</TD>
				
			</TR>
			<TR>
				<TD class=ItemBody><b>2.长期投资</b></TD>
				<TD class=ItemBody align=center>
					<input class=tar name="textfield" type="text" size="30" value="<%=DataFormat.formatString(arrContent[index])%>" onFocus="nextfield ='textfield[<%=index++%>]';">
				</TD>
				
			</TR>
			<TR>
				<TD class=ItemBody><b>3.固定资产</b></TD>
				<TD class=ItemBody align=center>
					<input class=tar name="textfield" type="text" size="30" value="<%=DataFormat.formatString(arrContent[index])%>" onFocus="nextfield ='textfield[<%=index++%>]';">
				</TD>
				
			</TR>
			<TR>
				<TD class=ItemBody align=right>其中：固定资产净值</TD>
				<TD class=ItemBody align=center >
					<input class=tar name="textfield" type="text" size="30" value="<%=DataFormat.formatString(arrContent[index])%>" onFocus="nextfield ='textfield[<%=index++%>]';">
				</TD>
				
			</TR>
			<TR>
				<TD class=ItemBody align=right>在建工程</TD>
				<TD class=ItemBody align=center >
					<input class=tar name="textfield" type="text" size="30" value="<%=DataFormat.formatString(arrContent[index])%>" onFocus="nextfield ='textfield[<%=index++%>]';">
				</TD>
				
			</TR>
			<TR>
				<TD class=ItemBody><b>4.无形和递延资产</b></TD>
				<TD class=ItemBody align=center>
					<input class=tar name="textfield" type="text" size="30" value="<%=DataFormat.formatString(arrContent[index])%>" onFocus="nextfield ='textfield[<%=index++%>]';">
				</TD>
				
			</TR>
			<TR>
				<TD class=ItemBody><b>5.总资产</b></TD>
				<TD class=ItemBody align=center>
					<input class=tar name="textfield" type="text" size="30" value="<%=DataFormat.formatString(arrContent[index])%>" onFocus="nextfield ='textfield[<%=index++%>]';">
				</TD>
				
			</TR>
			<TR>
				<TD class=ItemBody><b>6.负债合计</b></TD>
				<TD class=ItemBody align=center>
					<input class=tar name="textfield" type="text" size="30" value="<%=DataFormat.formatString(arrContent[index])%>" onFocus="nextfield ='textfield[<%=index++%>]';">
				</TD>
				
			</TR>
			<TR>
				<TD class=ItemBody>流动负债</TD>
				<TD class=ItemBody align=center>
					<input class=tar name="textfield" type="text" size="30" value="<%=DataFormat.formatString(arrContent[index])%>" onFocus="nextfield ='textfield[<%=index++%>]';">
				</TD>
				
			</TR>
			<TR>
				<TD class=ItemBody align=right>其中：短期借款</TD>
				<TD class=ItemBody align=center >
					<input class=tar name="textfield" type="text" size="30" value="<%=DataFormat.formatString(arrContent[index])%>" onFocus="nextfield ='textfield[<%=index++%>]';">
				</TD>
				
			</TR>
			<TR>
				<TD class=ItemBody align=right>应付票据</TD>
				<TD class=ItemBody align=center >
					<input class=tar name="textfield" type="text" size="30" value="<%=DataFormat.formatString(arrContent[index])%>" onFocus="nextfield ='textfield[<%=index++%>]';">
				</TD>
				
			</TR>
			<TR>
				<TD class=ItemBody align=right>应付账款</TD>
				<TD class=ItemBody align=center >
					<input class=tar name="textfield" type="text" size="30" value="<%=DataFormat.formatString(arrContent[index])%>" onFocus="nextfield ='textfield[<%=index++%>]';">
				</TD>
				
			</TR>
			<TR>
				<TD class=ItemBody align=right>其他应交款</TD>
				<TD class=ItemBody align=center >
					<input class=tar name="textfield" type="text" size="30" value="<%=DataFormat.formatString(arrContent[index])%>" onFocus="nextfield ='textfield[<%=index++%>]';">
				</TD>
				
			</TR>
			<TR>
				<TD class=ItemBody align=right>其他应付款</TD>
				<TD class=ItemBody align=center >
					<input class=tar name="textfield" type="text" size="30" value="<%=DataFormat.formatString(arrContent[index])%>" onFocus="nextfield ='textfield[<%=index++%>]';">
				</TD>
				
			</TR>
			<TR>
				<TD class=ItemBody>长期负债</TD>
				<TD class=ItemBody align=center>
					<input class=tar name="textfield" type="text" size="30" value="<%=DataFormat.formatString(arrContent[index])%>" onFocus="nextfield ='textfield[<%=index++%>]';">
				</TD>
				
			</TR>
			<TR>
				<TD class=ItemBody align=right>其中：长期借款</TD>
				<TD class=ItemBody align=center >
					<input class=tar name="textfield" type="text" size="30" value="<%=DataFormat.formatString(arrContent[index])%>" onFocus="nextfield ='textfield[<%=index++%>]';">
				</TD>
				
			</TR>
			<TR>
				<TD class=ItemBody align=right>其他长期借款</TD>
				<TD class=ItemBody align=center >
					<input class=tar name="textfield" type="text" size="30" value="<%=DataFormat.formatString(arrContent[index])%>" onFocus="nextfield ='textfield[<%=index++%>]';">
				</TD>
			</TR>
			<TR>
				<TD class=ItemBody align=right>应付统借统还款</TD>
				<TD class=ItemBody align=center >
					<input class=tar name="textfield" type="text" size="30" value="<%=DataFormat.formatString(arrContent[index])%>" onFocus="nextfield ='textfield[<%=index++%>]';">
				</TD>
			</TR>
			<TR>
				<TD class=ItemBody><b>7.所有者权益</b></TD>
				<TD class=ItemBody align=center>
					<input class=tar name="textfield" type="text" size="30" value="<%=DataFormat.formatString(arrContent[index])%>" onFocus="nextfield ='textfield[<%=index++%>]';">
				</TD>
				
			</TR>
			<TR>
				<TD class=ItemBody align=right>其中：实收资本</TD>
				<TD class=ItemBody align=center >
					<input class=tar name="textfield" type="text" size="30" value="<%=DataFormat.formatString(arrContent[index])%>" onFocus="nextfield ='textfield[<%=index++%>]';">
				</TD>
			</TR>
			<TR>
				<TD class=ItemBody align=right>资本公积</TD>
				<TD class=ItemBody align=center >
					<input class=tar name="textfield" type="text" size="30" value="<%=DataFormat.formatString(arrContent[index])%>" onFocus="nextfield ='textfield[<%=index++%>]';">
				</TD>
			</TR>
			<TR>
				<TD class=ItemBody align=right>盈余公积</TD>
				<TD class=ItemBody align=center >
					<input class=tar name="textfield" type="text" size="30" value="<%=DataFormat.formatString(arrContent[index])%>" onFocus="nextfield ='textfield[<%=index++%>]';">
				</TD>
			</TR>
			<TR>
				<TD class=ItemBody align=right>未分配利润</TD>
				<TD class=ItemBody align=center >
					<input class=tar name="textfield" type="text" size="30" value="<%=DataFormat.formatString(arrContent[index])%>" onFocus="nextfield ='textfield[<%=index++%>]';">
				</TD>
			</TR>
			<TR>
				<TD class=ItemBody><b>8.销售收入</b></TD>
				<TD class=ItemBody align=center>
					<input class=tar name="textfield" type="text" size="30" value="<%=DataFormat.formatString(arrContent[index])%>" onFocus="nextfield ='textfield[<%=index++%>]';">
				</TD>
				
			</TR>
			<TR>
				<TD class=ItemBody><b>9.销售成本</b></TD>
				<TD class=ItemBody align=center>
					<input class=tar name="textfield" type="text" size="30" value="<%=DataFormat.formatString(arrContent[index])%>" onFocus="nextfield ='textfield[<%=index++%>]';">
				</TD>
				
			</TR>
			<TR>
				<TD class=ItemBody><b>10.销售毛利</b></TD>
				<TD class=ItemBody align=center>
					<input class=tar name="textfield" type="text" size="30" value="<%=DataFormat.formatString(arrContent[index])%>" onFocus="nextfield ='textfield[<%=index++%>]';">
				</TD>
				
			</TR>
			<TR>
				<TD class=ItemBody><b>11.利润总额</b></TD>
				<TD class=ItemBody align=center>
					<input class=tar name="textfield" type="text" size="30" value="<%=DataFormat.formatString(arrContent[index])%>" onFocus="nextfield ='textfield[<%=index++%>]';">
				</TD>
				
			</TR>
			<TR>
				<TD class=ItemBody><b>12.净利润</b></TD>
				<TD class=ItemBody align=center>
					<input class=tar name="textfield" type="text" size="30" value="<%=DataFormat.formatString(arrContent[index])%>" onFocus="nextfield ='AddSubmit';">
				</TD>
				
			</TR>
				</table>
			</td>
		</tr>
		<TR bordercolor="#999999"> 
			<TD align=right>
				<INPUT onfocus="nextfield=''" class=button name=AddSubmit onclick="FrmSubmit(frm,1)" type=button value=" 保存 "> 
				<INPUT onfocus="nextfield='AddSubmit'" class=button name=backSubmit onclick="FrmSubmit(frm,2)" onKeydown="if(event.keyCode==13) FrmSubmit(frm,2);" type=button value=" 返回 "> 
			</TD>
		</TR>
		<TR bordercolor="#999999"> 
			<TD>&nbsp;</TD>
		</TR>
				
</table>
		<input type="hidden" name="control" value="">
		<input type="hidden" name="SUBMIT" >
		<INPUT type="hidden" name="lClientID" value="<%=lClientID%>">
		<input type="hidden" name="lContentID" value="<%=lContentID%>">
		<input type="hidden" name="PageName"  value="c211-v.jsp">
		<input type="hidden" name="PageNo"  value="1">
		<input type="hidden" name="yearNo"  value="<%=strYearNo%>">
		<input type="hidden" name="lIsReadOnly"  value="<%=Constant.YesOrNo.NO%>">
</form>

<script language="JavaScript">
		firstFocus(frm.textfield[0]);
		//setSubmitFunction("FrmSubmit(frm,1)");
		setFormName("frm");
</script>

<script language="JavaScript">

function FrmSubmit(frm,nType)
{
	var bSubmit = false;

	if (nType == "1")
	{
		if (confirm("是否保存？"))
		{
			frm.PageName.value = "c211-v.jsp";
			frm.SUBMIT.value = "save";
			frm.action="c202-c.jsp";
			frm.submit();
			return true;
		}
		else
		{
			return false;
		}
	}
	else if (nType == "2")
	{
		if (confirm("放弃保存文本信息？"))
		{
			frm.PageName.value = "c211-v.jsp";
			frm.SUBMIT.value = "";
			frm.action="c201-c.jsp";
			frm.submit();
			return true;
		}
		else
		{
			return false;
		}
	}

}
</script>


<%
			OBHtml.showOBHomeEnd(out);
	}
	catch( IException ie )
	{
		OBHtml.showExceptionMessage(out,sessionMng,ie,"资产负债表","", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return; 
	}
%>
<%@ include file="/common/SignValidate.inc" %>