<%--
/*
 * �������ƣ�consignReceiveStartFind.jsp
 * ����˵����ί���տ���ѯ����ҳ��
 * �������ߣ�xlchang
 * ������ڣ�2010-12-01
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
    //�������
    String strTitle = "ί���տ�𣭲�ѯ";
    String strContext = request.getContextPath();
    try {
        /* �û���¼��� */
        if (sessionMng.isLogin() == false) {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
            out.flush();
            return;
        }

        /* �ж��û��Ƿ���Ȩ�� */
        if (sessionMng.hasRight(request) == false) {
            out.println(sessionMng.hasRight(request));
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
            out.flush();
            return;
        }
        
         //��ʾ�ļ�ͷ
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
        
        long NPayerClientID = -1;      //���λid
		String NPayerClientName = "";   //���λ����
		long NPayerAcctID = -1;      //�����˺�id
		String NPayerAcctNo = "";     //�����˺�
		long NPayeeClientID = -1;     //�տλid
		String NPayeeClientName = "";  //�տλ����
		long NPayeeAcctID = -1;      //�տ��˺�id
		String NPayeeAcctNo = "";     //�տ��˺�
		String SAmount = "0.00";         //���
		String SExecute;         //ִ������
		long NAbstractID = -1;      //�����;id;
		String NAbstractName = "";  //�����;
		String SStatusName = ""; //״̬����
		String SInputUserName = ""; //¼��������
		String SInput = "";    //¼��ʱ��
		String SConfirmUserName = ""; //ȷ��������
		String SConfirm = "";    //ȷ��ʱ��
		
		long q_NStatus = -1;  //״̬
		
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
				<td width="100" class="graytext" >״̬��</td>
				<td class="graytext" align="left">
				<%
				//״̬
				OBHtmlCom.showConsignReceiveStatusListControl(
				    out,"q_NStatus",q_NStatus," onfocus=\"nextfield ='btnQuery';\" ",1);
				%>
				</td>				
			</tr>
			<tr>
				<td colspan="3">
				<div align="right">	
					<input type="Button" class="button1" value=" �� �� " name="btnQuery" onclick="javascript:query()">&nbsp;&nbsp;
					<input type="Button" class="button1" value=" �� �� " name="btnAdd"   onclick="javascript:toAdd()">&nbsp;&nbsp;			
				</div>
				</td>
			</tr>
		</table>
		</form>
		</br>
		<table width="100%" border="1"  cellpadding="0" cellspacing="0" class=normal >
		<thead>
		<tr height="18"> 
			<td width="5%" align="center" nowrap><div>���</div></td>
			<td width="10%" align="center" nowrap><div>����ͻ�</div></td>
			<td width="10%" align="center" nowrap><div>����˺�</div></td>
			<td width="10%" align="center" nowrap><div>�տ�ͻ�</div></td>
			<td width="10%" align="center" nowrap><div>�տ�˺�</div></td>
			<td width="10%" align="center" nowrap><div>���</div></td>		
			<td width="10%" align="center" nowrap><div>ժҪ</div></td>
			<td width="5%" align="center" nowrap><div>״̬</div></td>
			<td width="10%" align="center" nowrap><div>¼����</div></td>
			<td width="10%" align="center" nowrap><div>¼������</div></td>
			<td width="10%" align="center" nowrap><div>ȷ����</div></td>
			<td width="10%" align="center" nowrap><div>ȷ������</div></td>	
			<td width="10%" align="center" nowrap><div>ִ����</div></td>
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
		<!-- ��ҳ�ؼ� -->
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
	/* ҳ�潹�㼰�س����� 	*/
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
        /* ��ʾ�ļ�β */
        OBHtml.showOBHomeEnd(out);
    } catch (IException ie) {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    }
%>

