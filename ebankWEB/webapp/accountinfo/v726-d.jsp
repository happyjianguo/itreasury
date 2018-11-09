<%--
 页面名称 ：v726-d.jsp
 页面功能 : 查询-上收资金查询-查看页面
 作    者 ：xgzhang
 日    期 ：2005-09-13
 特殊说明 ：简单实现说明：
 				1、查询一付多收中交易类型为资金上收的交易记录
 修改历史 ：
--%>
<%@ page contentType="text/html;charset=gbk"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTHTML"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTMagnifier"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ page import="com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransOnePayMultiReceiveInfo"%>
<%@ page import="com.iss.itreasury.settlement.transcurrentdeposit.dao.Sett_TransOnePayMultiReceiveDAO"%>
<%@ page import="com.iss.itreasury.settlement.query.resultinfo.QueryTransactionInfo"%>
<%@ page import="com.iss.itreasury.settlement.query.resultinfo.QueryTransactionSumInfo"%>
<%@ page import="com.iss.system.dao.PageLoader"%>
<%@ page import="com.iss.itreasury.settlement.query.paraminfo.QueryTransactionConditionInfo"%>
<%@ page import="com.iss.itreasury.settlement.query.resultinfo.QueryTransactionInfo"%>
<%@ page import="com.iss.itreasury.settlement.query.queryobj.QTransaction"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<% String strContext = request.getContextPath();%>
<!-- 引入此用户当前回话的Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<jsp:include flush="true" page="/ShowMessage.jsp" />

<script language="JavaScript" src="/websett/js/Control.js"></script>
<script language="JavaScript" src="/websett/js/Check.js"></script>
<script language="JavaScript" src="/websett/js/SettCheck.js"></script>
<script language="JavaScript" src="/websett/js/MagnifierSQL.js"></script>
<safety:resources />
<%/**============定义变量===============**/
		String theTitle = "资金上收查询";
		String strTransactionCode = null; //交易号
		long lSerialNo = -1; //序列号
		long lDirectionIDUp = -1; //收付方向
		long lDirectionIDDown = -1;
		String strPayerClientCode = null; //付款方编号
		String strPayerClientName = null; //付款方名称
		String strRecerClientCode = null; //付款方编号
		String strRecerClientName = null; //付款方名称
		String strCorrespondingAccount  = ""; //对应账号
		PageLoader pageLoader = null;
		int resultCount = 0;
		double dAmount = 0.00; //金额
		String strTemp = null;
		String strAbstract = null;//摘要
		String strExcuteDate = null; //执行日
		String strPageLoaderKey = null;
		/**============业务处理===============**/
		
		try {
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

		OBHtml.showOBHomeHead(out,sessionMng,Env.getClientName(),Constant.YesOrNo.NO);
			
			strTemp = (String) request.getAttribute("TransNo");
 			if (strTemp != null && strTemp.trim().length() > 0) {
				strTransactionCode = strTemp.trim();
			}
			System.out.println("<<<TransNo>>>" + strTemp);
			strTemp = (String) request.getAttribute("SerialNo");
 			if (strTemp != null && strTemp.trim().length() > 0) {
				lSerialNo = Long.parseLong(strTemp.trim());
			}
			System.out.println("<<<SerialNo>>>" + strTemp);
			strTemp = (String) request.getAttribute("_pageLoaderKey");
			if (strTemp != null && strTemp.trim().length() > 0) {
				strPageLoaderKey = strTemp.trim();
			}
			System.out.println("<<<strPageLoaderKey>>>" + strTemp);
			QueryTransactionConditionInfo conditionInfo = new QueryTransactionConditionInfo();
			if(strPageLoaderKey != null)
				conditionInfo = (QueryTransactionConditionInfo)sessionMng.getQueryCondition(strPageLoaderKey);
			else {
				conditionInfo.setQueryType(-1);
				conditionInfo.setOfficeID(sessionMng.m_lOfficeID);
				conditionInfo.setCurrencyID(sessionMng.m_lCurrencyID);
				conditionInfo.setTransactionTypeIDs(String.valueOf(SETTConstant.TransactionType.UPGATHERING));
			}
			QTransaction qobj = new QTransaction();
			pageLoader = qobj.getTransactionByTransNo(conditionInfo,strTransactionCode);
			strPageLoaderKey = sessionMng.setPageLoader(pageLoader);
			QueryTransactionInfo[] resultInfos = (QueryTransactionInfo[])pageLoader.listAll();	
			System.out.println("<<<resultInfos>>>" + resultInfos);
			if(resultInfos != null)
				resultCount = resultInfos.length;
				System.out.println("<<<resultCount>>>" + resultCount);
			for(int i = 0; i < resultCount; i ++)
			{
				QueryTransactionInfo qti = resultInfos[i];
				if(lSerialNo == qti.getSerialNo())
				{
					if(qti.getPayAccountID() != -1) {
						lDirectionIDUp = SETTConstant.ReceiveOrPay.PAY;
						lDirectionIDDown = SETTConstant.ReceiveOrPay.RECEIVE;
						strPayerClientCode = qti.getPayClientCode();
						strPayerClientName = qti.getPayClientName();
						dAmount = qti.getPayAmount();
					} else {
						lDirectionIDUp = SETTConstant.ReceiveOrPay.RECEIVE;
						lDirectionIDDown = SETTConstant.ReceiveOrPay.PAY;
						strPayerClientCode = qti.getReceiveClientCode();
						strPayerClientName = qti.getReceiveClientName();
						dAmount = qti.getReceiveAmount();
					}
					strAbstract = qti.getAbstract();
					strExcuteDate = qti.getExecute().toString();
				}
			}
			for(int j = 0; j < resultCount; j ++)
			{
				QueryTransactionInfo qti = resultInfos[j];
				if(lDirectionIDUp == SETTConstant.ReceiveOrPay.PAY) {
					if(qti.getReceiveAccountID() != -1)
						strCorrespondingAccount = qti.getReceiveAccountNo()+ ";";
				} else if(lDirectionIDUp == SETTConstant.ReceiveOrPay.RECEIVE){
				
					if(qti.getPayAccountID() != -1)
						strCorrespondingAccount += qti.getPayAccountNo() + ";";
				}
			}
%>
<jsp:include page="/ShowMessage.jsp" />
<TABLE class="top" height="100" width="97%">
	<TBODY>
		<TR>
			<TD class="FormTitle" height="13"><B><%=theTitle%></B></TD>
		</TR>
		<TR>
			<TD height="30" valign="bottom">
				<TABLE align="center" width="97%">
					<TBODY>
						<TR bordercolor="#ffffff">
							<TD height=20 width="16%" bordercolor="#ffffff" nowrap>交易号:</TD>
							<TD height=20 width="32%">
								<INPUT class=box readonly 
								            name=textfield523 value="<%=(strTransactionCode==null?"":strTransactionCode)%>">
							</TD>
							<TD height=20 width="18%" nowrap>序列号：</TD>
							<TD height=20 width="34%">
								<INPUT class=box readonly 
								            name=textfield5223 size=3 value=" <%=(lSerialNo<=0?"":String.valueOf(lSerialNo))%>">
							</TD>
						</TR>
					</TBODY>
				</TABLE>	
			</TD>
		</TR>
		<TR>
			<TD>
				<TABLE align=center border=1 borderColor=#999999 height=20 width="97%">
       		 		<TBODY>
       		 			<TR borderColor=#E8E8E8>
       		 				<TD height=20 width="16%" nowrap>收付方向：</TD>
							<TD height=20 width="32%">
								<INPUT class=box readonly 
           							 name=txtDirection size=2 value=<%=(lDirectionIDUp<0?"":SETTConstant.ReceiveOrPay.getName(lDirectionIDUp)) %>>
 							</TD>
							<TD height=20 width="18%">&nbsp;</TD>
							<TD height=20 width="34%">&nbsp;</TD>
       		 			</TR>
       		 			<%
       		 				if(lDirectionIDUp == SETTConstant.ReceiveOrPay.PAY)
       		 				{
       		 			%>
       		 			<TR borderColor=#E8E8E8>
       		 				<TD height=20 width="16%" nowrap>付款方客户编号： </TD>
							<TD height=20 width="32%">
								<INPUT class=box readonly maxLength=6 
								            name=textfield323222222 size=6 value=<%=(strPayerClientCode==null?"":strPayerClientCode)%>>
							</TD>
							<TD height=20 width="18%" nowrap>付款方客户名称：</TD>
							<TD height=20 width="34%">
								<INPUT class=box readonly 
								            name=textfield232223 size=30 value=<%=(strPayerClientName==null?"":strPayerClientName)%>>
 							</TD>
       		 			</TR>
       		 			<% } else {%>
       		 			<TR borderColor=#E8E8E8>
							<TD height=20 width="16%" nowrap>收款方客户编号： </TD>
							<TD height=20 width="32%">
								<INPUT class=box readonly maxLength=6 
								            name=textfield323222222 size=6 value=<%=(strPayerClientCode==null?"":strPayerClientCode)%>>
							</TD>
							<TD height=20 width="18%" nowrap>收款方客户名称：</TD>
							<TD height=20 width="34%">
								<INPUT class=box readonly 
								            name=textfield232223 size=30 value=<%=(strPayerClientName==null?"":strPayerClientName)%>>
							 </TD>
       		 			</TR>
       		 			<%}%>
       		 			<TR borderColor=#E8E8E8>
       		 				<TD height=20 width="16%" nowrap>金额：</TD>
							<TD height=20 width="32%" nowrap><%=sessionMng.m_strCurrencySymbol%>  
								<INPUT class=box readonly 
								            name=textfield23222 value=<%= DataFormat.formatDouble(dAmount)%>>
								 </TD>
							<TD height=20 width="18%">&nbsp;</TD>
							<TD height=20 width="34%">&nbsp;</TD>
       		 			</TR>
       		 		</TBODY>
       		 	</TABLE>
			</TD>
		</TR>
		<TR>
			<TD>
				<TABLE align=center border=1 borderColor=#999999 height=20 width="97%">
       			 	<TBODY>
       			 		<TR borderColor="#E8E8E8">
       			 			<TD height=20 width="16%" nowrap><U>对应账户详细资料</U></TD>
							<TD height=20 width="32%">&nbsp;</TD>
							<TD height=20 width="18%">&nbsp;</TD>
							<TD height=20 width="34%">&nbsp;</TD>
       			 		</TR>
       			 		<TR borderColor=#E8E8E8>
       			 			<TD height=20 width="16%" nowrap>收付方向：</TD>
							<TD height=20 width="32%">
							<INPUT class=box readonly 
							            name=textfield5222222 size=2 value=<%=(lDirectionIDDown<0?"":SETTConstant.ReceiveOrPay.getName(lDirectionIDDown))%>>
							 </TD>
							<TD height=20 width="18%">&nbsp;</TD>
							<TD height=20 width="34%">&nbsp;</TD>
       			 		</TR>
       			 		<TR borderColor=#E8E8E8>
       			 			<TD nowrap height=20 vAlign=top width="16%">对应账号： </TD>
							<TD height=20 width="32%">
								<TEXTAREA class=box cols=70 readonly name=textfield3222322><%=(strCorrespondingAccount==null?"":strCorrespondingAccount)%></TEXTAREA>
							</TD>
							<TD height=20 width="18%">&nbsp;</TD>
							<TD height=20 width="34%">&nbsp;</TD>
       			 		</TR>
       			 	</TOPDY>
       			 </TABLE>
			</TD>
		</TR>
		<TR>
			<TD>
				<TABLE valign=top align=center border=0 borderColor=#999999 height=50 width="97%">
				    <TBODY >  
						<TR borderColor=#E8E8E8>　        
							<TD width="16%" nowrap>起息日：</TD>
							<TD width="34%">
								<INPUT class=box readonly  name=textfield7222 value=<%=(strExcuteDate==null?"":DataFormat.formatString(strExcuteDate))%>>
							 </TD>
							<TD width="16%" nowrap>执行日：</TD>
							<TD  width="34%">　          
							 <INPUT class=box readonly  name=textfield72222 value=<%=(strExcuteDate==null?"":DataFormat.formatString(strExcuteDate))%>>
							</TD>
						</TR>      
						<TR borderColor=#E8E8E8>        
							<TD colSpan=4>            
								<DIV align=right>
								<INPUT class=button name=save onclick="doPrint()" type=button value=" 打 印 " >
								<INPUT class=button name=Submit22 onclick="window.close()"  type=button value=" 关 闭 ">
								</DIV>
								<BR>
							</TD>
						</TR>
						<BR>
					</TBODY>
				</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>
<form name="listReport" method="post" >
	<input name="DirectionID" type="hidden" value="<%=lDirectionIDUp%>">
	<input name="strAction"  type="hidden">
	<input name="strSuccessPageURL"  type="hidden" >
	<input name="strFailPageURL"  type="hidden" >
	<input name="_pageLoaderKey"  type="hidden" value="<%=strPageLoaderKey%>">
</form>
<%
		} catch (Exception ex) {
			Log.print(ex.getMessage());
		} finally {
			OBHtml.showOBHomeEnd(out);
		}
%>
<script language="javascript">
function doPrint() {
	 if(confirm("是否打印"))
	  {
	    listReport.action = '<%=strContext%>'+"/pagecontrol.jsp";
		listReport.target='blank_';
		listReport.strAction.value='<%=Constant.PageControl.LISTALL%>';
		listReport.strSuccessPageURL.value = '/accountinfo/v205.jsp';
		listReport.strFailPageURL.value = '/accountinfo/v726-d.jsp';
		listReport.submit();
	  }
}
</script>

<%@ include file="/common/SignValidate.inc" %>