<%--
/*
 * 程序名称：consignReceiveStartFind.jsp
 * 功能说明：委托收款发起查询输入页面
 * 作　　者：xlchang
 * 完成日期：2010-12-01
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>

<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="com.iss.itreasury.util.DataFormat" %>
<%@ page import="com.iss.itreasury.util.IException" %>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.util.NameRef" %>
<%@ page import="com.iss.itreasury.project.wisgfc.ebank.special.dataentity.ConsignReceiveInfo" %>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
    //标题变量
    String strTitle = "委托收款发起－查询";
    String strContext = request.getContextPath();
    try {
        /* 用户登录检测 */
        if (sessionMng.isLogin() == false) {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
            out.flush();
            return;
        }

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false) {
            out.println(sessionMng.hasRight(request));
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
            out.flush();
            return;
        }
        
         //显示文件头
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
        
        long NPayerClientID = -1;      //付款单位id
		String NPayerClientName = "";   //付款单位名称
		long NPayerAcctID = -1;      //付款账号id
		String NPayerAcctNo = "";     //付款账号
		long NPayeeClientID = -1;     //收款单位id
		String NPayeeClientName = "";  //收款单位名称
		long NPayeeAcctID = -1;      //收款账号id
		String NPayeeAcctNo = "";     //收款账号
		String SAmount = "0.00";         //金额
		String SExecute;         //执行日期
		long NAbstractID = -1;      //汇款用途id;
		String NAbstractName = "";  //汇款用途
		String SStatusName = ""; //状态名称
		String SInputUserName = ""; //录入人名称
		String SInput = "";    //录入时间
		String SConfirmUserName = ""; //确认人名称
		String SConfirm = "";    //确认时间
		
		long q_NStatus = -1;  //状态
		
		int rowNum = 0;
        ConsignReceiveInfo info = null;
        NameRef nameRef = new NameRef();
           
        String strPageLoaderKey = (String)request.getAttribute("_pageLoaderKey");
        ConsignReceiveInfo qInfo = (ConsignReceiveInfo)sessionMng.getQueryCondition(strPageLoaderKey);
        ConsignReceiveInfo[] searchResults = (ConsignReceiveInfo[])request.getAttribute(Constant.PageControl.SearchResults);
       	
		if (qInfo != null) {
			q_NStatus = qInfo.getQ_NStatus();
		}		        
%>
     
<jsp:include flush="true" page="/ShowMessage.jsp" />
<table cellpadding="0" cellspacing="0" class="title_top"  >
	<tr>
		<td height="24">
		<table width="100%" cellspacing="0" cellpadding="0" class=title_Top1 >
			<TR>
				<td class=title nowrap><span class="txt_til2"><%=strTitle %></span></td>
			    <td class=title_right><a class=img_title></td>
			</TR>
		</TABLE>
		<br/>
		<form name="form" method="post" action="<%=strContext%>/project/wisgfc/special/control/consignReceiveStart.jsp">
		<input type="hidden" name="strAction" >
		<input type="hidden" name="id" >
		<input name="strSuccessPageURL"  type="hidden" value="<%=strContext%>/project/wisgfc/special/view/consignReceiveStartQuery.jsp">
		<input name="strFailPageURL"  type="hidden" value="<%=strContext%>/project/wisgfc/special/view/consignReceiveStartQuery.jsp">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class=normal  >
			<tr id="commonStatus" height="25">
				<td width="5" height="25"></td>
				<td width="100" class="graytext" >状态：</td>
				<td class="graytext" align="left">
				<%
				//状态
				OBHtmlCom.showConsignReceiveStatusListControl(
				    out,"q_NStatus",q_NStatus," onfocus=\"nextfield ='btnQuery';\" ",1);
				%>
				</td>				
			</tr>
			<tr>
				<td colspan="3">
				<div align="right">	
					<input type="Button" class="button1" value=" 查 找 " name="btnQuery" onclick="javascript:query()">&nbsp;&nbsp;
					<input type="Button" class="button1" value=" 新 增 " name="btnAdd"   onclick="javascript:toAdd()">&nbsp;&nbsp;			
				</div>
				</td>
			</tr>
		</table>
		</form>
		</br>
		<table width="100%" border="1"  cellpadding="0" cellspacing="0" class=normal >
		<thead>
		<tr height="18"> 
			<td width="5%" align="center" nowrap><div>序号</div></td>
			<td width="10%" align="center" nowrap><div>付款方客户</div></td>
			<td width="10%" align="center" nowrap><div>付款方账号</div></td>
			<td width="10%" align="center" nowrap><div>收款方客户</div></td>
			<td width="10%" align="center" nowrap><div>收款方账号</div></td>
			<td width="10%" align="center" nowrap><div>金额</div></td>		
			<td width="10%" align="center" nowrap><div>摘要</div></td>
			<td width="5%" align="center" nowrap><div>状态</div></td>
			<td width="10%" align="center" nowrap><div>录入人</div></td>
			<td width="10%" align="center" nowrap><div>录入日期</div></td>
			<td width="10%" align="center" nowrap><div>确认人</div></td>
			<td width="10%" align="center" nowrap><div>确认日期</div></td>	
			<td width="10%" align="center" nowrap><div>执行日</div></td>
		</tr>
		</thead>
	 	<%	
			if( searchResults != null && searchResults.length > 0) {				
				for (int i = 0; i < searchResults.length; i++) {			
					info = (ConsignReceiveInfo)searchResults[i];
					NPayerClientID = info.getNPayerClientID();
					NPayerClientName = NameRef.getClientNameByID(NPayerClientID);
					NPayerAcctID = info.getNPayerAcctID();
					NPayerAcctNo = nameRef.getAccountNOByIDFromSett(NPayerAcctID);
					NPayeeClientID = info.getNPayeeClientID();
					NPayeeClientName = NameRef.getClientNameByID(NPayeeClientID);
					NPayeeAcctID = info.getNPayeeAcctID();
					NPayeeAcctNo = nameRef.getAccountNOByIDFromSett(NPayeeAcctID);
					SAmount = DataFormat.formatDisabledAmount(info.getMAmount());
					SExecute = DataFormat.getDateString(info.getDTExecute());
					if (info.getNStatus() == OBConstant.SettInstrStatus.REFUSE) {
						SExecute = "";
					}
					NAbstractID = info.getNAbstractID();
					NAbstractName = NameRef.getAbstractNameByID(NAbstractID);
					SStatusName = OBConstant.SettInstrStatus.getName(info.getNStatus());
					SInputUserName = NameRef.getUserNameByID(info.getNInputUserID());
					SInput = DataFormat.getDateString(info.getDTInput());
					SConfirmUserName = NameRef.getUserNameByID(info.getNConfirmUserID());
					SConfirm = DataFormat.getDateString(info.getDTConfirm());
						
			  %>
		<tr height="18" >
			<td align="center" nowrap>			
				<u style="cursor:hand" onClick="doSee(<%=info.getId()%>);" ><%=++rowNum%></u>
			</td> 
			<td align="left" nowrap><%=NPayerClientName%></td> 
			<td align="center" nowrap><%=NPayerAcctNo%></td>
			<td align="left" nowrap><%=NPayeeClientName%></td>
			<td align="center" nowrap><%=NPayeeAcctNo%></td>
			<td align="right" nowrap><%=sessionMng.m_strCurrencySymbol%><%=SAmount%></td>
			<td align="left" nowrap><%=NAbstractName%></td>
			<td align="center" nowrap><%=SStatusName%></td>
			<td align="center" nowrap><%=SInputUserName%></td>
			<td align="center" nowrap><%=SInput%></td>	
			<td align="center" nowrap><%=SConfirmUserName%></td>
			<td align="center" nowrap><%=SConfirm%></td>	
			<td align="center" nowrap><%=SExecute%></td>	            
		</tr>
	  	<%
	  		}
		}
		else {%>					
		<tr height="18"> 
			<td align="center" ></td>
			<td align="center" ></td>
			<td align="center" ></td>
			<td align="center" ></td>
			<td align="center" ></td>
			<td align="center" ></td>
			<td align="center" ></td>
			<td align="center" ></td> 
			<td align="center" ></td>
			<td align="center" ></td> 
			<td align="center" ></td> 
			<td align="center" ></td>
			<td align="center" ></td>                                       
		</tr>				
	 	<%}%>		
		</table>
		<table width="100%" >
		<!-- 分页控件 -->
			<TR>
				<td width="100%" align="right">
					<table width=100% cellspacing="3" cellpadding="0" class="SearchBar" >
						  <TR>
					           <TD height=20 width=100% align="right">
					             <DIV align=right>
								    <jsp:include page="/pagenavigator.jsp"  />
								 </DIV>
							   </TD>
						  </TR>
				     </table> 
			     </td>
		     </TR> 
		</table>
		</td>
	</tr>
</table>


<script language="javascript">
	/* 页面焦点及回车控制 	*/
	setFormName("form");
	firstFocus(form.q_NStatus);	
	//setSubmitFunction("query()");
	
</script>	

<script language="javascript">
function query(){
	form.strAction.value="<%=OBConstant.Actions.MATCHSEARCH%>";	
	showSending();
	form.submit();	
}

function doSee(id){
	form.id.value=id;
	form.action = "<%=strContext%>/project/wisgfc/special/control/consignReceiveStart.jsp?menu=hidden";
	form.strAction.value="<%=OBConstant.Actions.TODETAIL%>";
	form.strSuccessPageURL.value ="<%=strContext%>/project/wisgfc/special/view/consignReceiveStartUpdate.jsp";
	form.submit(); 
}

function toAdd(){
	form.strAction.value="toAdd";
	form.strSuccessPageURL.value="<%=strContext%>/project/wisgfc/special/view/consignReceiveStartAdd.jsp";
	showSending();
	form.submit();	
}
</script>

<%
        /* 显示文件尾 */
        OBHtml.showOBHomeEnd(out);
    } catch (IException ie) {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    }
%>

