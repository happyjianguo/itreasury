<%@page contentType="text/html;charset=gbk"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@page import="java.util.*,
				com.iss.itreasury.util.*,
				com.iss.itreasury.loan.util.*,
				com.iss.itreasury.ebank.util.*,
				com.iss.itreasury.loan.contractcontent.dao.ContractContentDao"
%>
<%
	String strOfficeName = sessionMng.m_strOfficeName;
	String strUserName = sessionMng.m_strUserName;
	String strTableTitle = "损益表－查询";
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
		long lIsReadOnly = Constant.YesOrNo.YES;
		long lTypeId= -1;
		String sTemp="";
		/////////////////////////////////////
		boolean isRead=false;
		//int index++=0;
		
		String[] arrContent=null;
		arrContent=new String[200];
		String strContent=(String)request.getAttribute("sContent");
   	   //String[] arrContent=new String[64];
   	   if (strContent!=null&&strContent.length() > 0)
	   {
			int nIndex; //","的索引位置
			int nTmp = 0;
			nIndex = strContent.indexOf(ContractContentDao.CONTENT_SEPERATOR);
			while (nIndex >= 0)
			{
				arrContent[nTmp] = strContent.substring(0, nIndex);
				strContent = strContent.substring(nIndex + 4);
				nIndex = strContent.indexOf(ContractContentDao.CONTENT_SEPERATOR);
				nTmp++;
			}
			arrContent[nTmp] = strContent;
		}
		//显示文件头
		 OBHtml.showOBHomeHead(out,sessionMng,"",Constant.YesOrNo.NO);
		int index = 1;
%>
<%
		long lContentID = -1;
		long lClientID = -1;
		String strTemp="";
		String strYear="";
		String strSeason="";
		String strClientName="";

		strTemp = (String)request.getAttribute("strYear");
		if (strTemp != null && strTemp.trim().length() > 0){				//年份
			strYear = strTemp.trim();
			
		}
	
		 strTemp = (String)request.getAttribute("strSeason");
		if (strTemp != null && strTemp.trim().length() > 0){				//季度
			strSeason = strTemp.trim();			
		}
		strTemp = (String)request.getAttribute("hdnstrYear");
		if (strTemp != null && strTemp.trim().length() > 0){				//年份
			strYear = strTemp.trim();			
		}
		 strTemp = (String)request.getAttribute("hdnstrSeason");
		if (strTemp != null && strTemp.trim().length() > 0){				//季度
			strSeason = strTemp.trim();			
		}
		strTemp = (String)request.getAttribute("lTypeId");
		if (strTemp != null && strTemp.trim().length() > 0){				//利率类型编号
			lTypeId = Long.parseLong(strTemp.trim());
		}
		strTemp = (String)request.getAttribute("lClientID");
		if (strTemp != null && strTemp.trim().length()>0){				//操作结果
			lClientID = Long.parseLong(strTemp.trim());
		}

		strContent = (String) request.getAttribute("sContent");
		strTemp = (String) request.getAttribute("lContentID");
		if( strTemp != null && strTemp.length() > 0 )
		{
			lContentID = Long.parseLong(strTemp.trim());
		}
		strTemp = (String) request.getAttribute("lClientID");
		if( strTemp != null && strTemp.length() > 0 )
		{
			lClientID = Long.parseLong(strTemp.trim());
		}
		strTemp = (String) request.getAttribute("hdnClientName");
		if( strTemp != null && strTemp.length() > 0 )
		{
			strClientName = strTemp.trim();
		}

		String strControl = "";
		strTemp = (String) request.getAttribute("hdnControl");
		if( strTemp != null && strTemp.length() > 0 )
		{
			strControl = strTemp.trim();
		}
		if("view".equals(strControl))
		{
			isRead=true;
		}
%>
<SCRIPT language="javascript" src="/webloan/js/Control.js"></SCRIPT>
<SCRIPT language=JavaScript src="/webloan/js/Check.js"></SCRIPT>
<form name="frm" method="post">
	<!--页面控制变量-->
	<input type="hidden" name="strSourcePage" value="">
	<input type="hidden" name="strSuccessPageURL" value="">
	<input type="hidden" name="strFailPageURL" value="">
	<input type="hidden" name="strAction" value="">
	
	<!--重复请求控制-->
	<!--页面控制变量-->
	
	<!--隐含业务数据-->
	<input type=hidden name="hdnTypeId" value="<%=lTypeId%>">
	<input type=hidden name="hdnlClientID" value="<%=lClientID%>">
	<input type=hidden name="hdnstrYear" value="<%=strYear%>">
	<input type=hidden name="hdnstrSeason" value="<%=strSeason%>">
	<input type=hidden name="hdnClientName" value="<%=strClientName%>">
	<input type="hidden" name="hdnControl" value="<%=strControl%>">
	
	<!--隐含业务数据-->
	<TABLE border=0 class=top width="99%">
<tr>
<td>编制单位:</td><td><%=strClientName%></td><td>年份:</td><td><%=strYear%></td><td>季度:</td>
<td><%=strSeason%></td></tr></TABLE>

<TABLE border=0 class=top width="99%">
	<tr>
		<td>

		</td>
	</tr>
  <tr> 
		<td class="FormTitle" height=2  width="100%"><b>损益表</b></td>
  </tr>


  <tr>
    <td width="100%">
		<table width="100%" border="0"  class="ItemList" height="102">
      
        <tr>
          <td class=ItemTitle width="38%" height="47" align="center">
            项目</td>
          <td class=ItemTitle width="8%" height="47" align="center">
            行次</td>
          <td class=ItemTitle width="22%" height="47" align="center">
            本月数</td>
          <td class=ItemTitle width="22%" height="47" align="center">
            本年累计数</td>
        </tr>
        <tr>
          <td class=ItemBody  >　一.&nbsp;主营业务收入&nbsp;</td>
          <td class=ItemBody   align="center">　1</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;减:&nbsp;主营业务成本</td>
          <td class=ItemBody  align="center">　4</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;主营业务税金及附加</td>
          <td class=ItemBody  align="center">　5</td>
          <td class=ItemBody  align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
          <td class=ItemBody  align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　二.&nbsp;主营业务利润(亏损以"-"号填列)</td>
          <td class=ItemBody   align="center">　10</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;加:&nbsp;其它业务利润(亏损以"-"号填列)</td>
          <td class=ItemBody   align="center">　11</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;减:&nbsp;营业费用</td>
          <td class=ItemBody   align="center">　14</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;管理费用</td>
          <td class=ItemBody  align="center" >　15</td>
          <td class=ItemBody  align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
          <td class=ItemBody  align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;财务费用</td>
          <td class=ItemBody  align="center" >　16</td>
          <td class=ItemBody  align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
          <td class=ItemBody  align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　三.&nbsp;营业利润(亏损以"-"号填列)</td>
          <td class=ItemBody  align="center" >　18</td>
          <td class=ItemBody  align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
          <td class=ItemBody  align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody >　&nbsp;&nbsp;&nbsp;&nbsp;加:&nbsp;投资收益(亏损以"-"号填列)</td>
          <td class=ItemBody  align="center">　19</td>
         <td class=ItemBody align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
          <td class=ItemBody align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;补贴收入</td>
          <td class=ItemBody  align="center" >　22</td>
          <td class=ItemBody  align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
          <td class=ItemBody  align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;营业外收入</td>
          <td class=ItemBody  align="center" >　23</td>
          <td class=ItemBody  align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
          <td class=ItemBody  align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;减:&nbsp;营业外支出</td>
          <td class=ItemBody  align="center" >　25</td>
          <td class=ItemBody  align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
          <td class=ItemBody  align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　四.&nbsp;利润总额(亏损总额以"-"号填列)</td>
          <td class=ItemBody  align="center" >　27</td>
          <td class=ItemBody  align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
          <td class=ItemBody  align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;减:&nbsp;所得税</td>
          <td class=ItemBody  align="center" >　28</td>
          <td class=ItemBody  align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
          <td class=ItemBody  align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　五:&nbsp;净利润(净亏损以"-"号填列)</td>
          <td class=ItemBody  align="center" >　30</td>
          <td class=ItemBody  align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
          <td class=ItemBody  align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
      </table>
    </td>
  </tr>
  
	  	<tr>
    <td width="100%" align="left">补充资料:</td>
		</tr>
  <tr>
    <td width="100%">
		<table width="100%" border="0"  class="ItemList" height="102">
      
        <tr>
          <td class=ItemTitle width="38%" height="47" align="center">
            项目</td>
          <td class=ItemTitle width="28%" height="47" align="center">
            本年累计数</td>
          <td class=ItemTitle width="32%" height="47" align="center">
            上年实际数</td>
        </tr>
        <tr>
          <td class=ItemBody  >　1.&nbsp;出售,处置部门或被投资单位所得收益</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　2.&nbsp;自然灾害发生的损失</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　3.&nbsp;会计政策变更增加(或减少)利润总额</td>
          <td class=ItemBody  align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
          <td class=ItemBody  align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　4.&nbsp;会计估计变更增加(或减少)利润总额</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　5.&nbsp;债务重组损失</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　6.&nbsp;其它</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='AddSubmit';" >
          </td>
        </tr>
      </table>
    </td>
  </tr>
		
		<TR bordercolor="#999999"> 
			<TD align=right>

<% 
	if(!isRead)
	{
%>
				<INPUT onfocus="nextfield=''" class=button name=AddSubmit onclick="FrmSubmit(frm,1)" type=button value=" 保存 "> 

<%
	}
%>

				<INPUT onfocus="nextfield='AddSubmit'" class=button name=backSubmit onclick="FrmSubmit(frm,2)" onKeydown="if(event.keyCode==13) FrmSubmit(frm,2);" type=button value=" 返回 "> 
				
			</TD>
		</TR>
</table>
		<input type="hidden" name="control" value="">
		<input type="hidden" name="SUBMIT" >
		<INPUT type="hidden" name="lClientID" value="<%=lClientID%>">
		<input type="hidden" name="lContentID" value="<%=lContentID%>">
		<input type="hidden" name="PageName"  value="c211-v.jsp">
		<input type="hidden" name="PageNo"  value="1">
		
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
			frm.action = "../control/c002.jsp";
			frm.strSuccessPageURL.value = "../view/v003.jsp";
			frm.strFailPageURL.value = "../view/v003.jsp";		
			frm.PageName.value = "c211-v.jsp";
			frm.SUBMIT.value = "save";
			
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

			frm.action = "../control/c002.jsp";
			frm.strSuccessPageURL.value = "../view/v003.jsp";
			frm.strFailPageURL.value = "../view/v003.jsp";	
			frm.PageName.value = "c211-v.jsp";
			frm.SUBMIT.value = "";
			
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
	catch(IException ie)
	{
		//LOANHTML.showExceptionMessage(out,sessionMng,ie,request,response,strTableTitle, Constant.RecordStatus.VALID);
		out.flush();
		return;
	}
%>
