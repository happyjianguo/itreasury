<%--
/*
 * �������ƣ�consignReceiveConfirmAdd.jsp
 * ����˵����ί���տ�ȷ�ϲ���ҳ��
 * �������ߣ�xlchang
 * ������ڣ�2010-12-01
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="com.iss.itreasury.util.DataFormat" %>
<%@ page import="com.iss.itreasury.util.IException" %>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.util.NameRef" %>
<%@ page import="com.iss.itreasury.ebank.util.OBMagnifier"%>
<%@ page import="com.iss.itreasury.project.wisgfc.ebank.special.dataentity.ConsignReceiveInfo" %>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<script language="javascript" src="/itreasury/js/jquery-1.3.2.js"></script>

<%
    //�������
    String strTitle = "ί���տ�ȷ�ϣ���ѯ";
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
        
         
		//�Ŵ󾵹��ñ���
		String strFormName = "form";
		String strCtrlName = "";
		String strMagnifierName = "";
		String strFirstTD = "";
		String strSecondTD = "";
		String strMainProperty = "";
		String strPrefix = "";
		String strSQL = "";
		int nIndex = 0;	
		String[] sNextControls = null;	
		String[] strMainNames = null;
		String[] strMainFields = null;
		String[] strReturnNames = null;
		String[] strReturnFields = null;
		String[] strReturnValues = null;
		String[] strDisplayNames = null;
		String[] strDisplayFields = null;
        
        long NPayerClientID = -1;      //���λid
		String NPayerClientName = "";   //���λ����
		long NPayerAcctID = -1;      //�����˺�id
		String NPayerAcctNo = "";     //�����˺�
		long NPayeeClientID = -1;     //�տλid
		String NPayeeClientName = "";  //�տλ����
		long NPayeeAcctID = -1;      //�տ��˺�id
		String NPayeeAcctNo = "";     //�տ��˺�
		String SAmount = "0.00";         //���
		String SExecute = "";         //ִ������
		long NAbstractID = -1;      //�����;id;
		String NAbstractName = "";  //�����;
		String SContractCode = "";           //��ͬ��	
		String SStatusName = ""; //״̬����
		String SInputUserName = ""; //¼��������
		String SInput = "";    //¼��ʱ��
		String SConfirmUserName = ""; //ȷ��������
		String SConfirm = "";    //ȷ��ʱ��
		long NInstrID = -1;    //ob_financeinstr��ID
		
		long q_NStatus = -1;  //״̬
		String q_inputStart = "";//��ѯ����-�ύ���� ��
		String q_inputEnd = "";  //��ѯ����-�ύ���� ��
		long q_payeeClientIDStart = 0; //��ѯ����-�տ ��
		long q_payeeClientIDEnd = 0; //��ѯ����-�տ ��
		String q_payeeClientCodeStart = ""; //��ѯ����-�տ ��
		String q_payeeClientCodeEnd = ""; //��ѯ����-�տ ��
				
		int rowNum = 0;
        ConsignReceiveInfo info = null;
        NameRef nameRef = new NameRef();      
        long nOfficeID = sessionMng.m_lOfficeID;
		long nCurrencyID = sessionMng.m_lCurrencyID;	
		long nClientID = sessionMng.m_lClientID;
		NPayeeClientID = nClientID;
        
        String strPageLoaderKey = (String)request.getAttribute("_pageLoaderKey");
        ConsignReceiveInfo qInfo = (ConsignReceiveInfo)sessionMng.getQueryCondition(strPageLoaderKey);
        ConsignReceiveInfo[] searchResults = (ConsignReceiveInfo[])request.getAttribute(Constant.PageControl.SearchResults);
       	
       	if (qInfo != null) {
			q_NStatus = qInfo.getQ_NStatus();
			q_inputStart = DataFormat.formatString(qInfo.getQ_inputStart());
			q_inputEnd = DataFormat.formatString(qInfo.getQ_inputEnd());
			q_payeeClientIDStart = qInfo.getQ_payeeClientIDStart();
			q_payeeClientIDEnd = qInfo.getQ_payeeClientIDEnd();			
			q_payeeClientCodeStart = NameRef.getClientCodeByID(q_payeeClientIDStart);
			q_payeeClientCodeEnd = NameRef.getClientCodeByID(q_payeeClientIDEnd);
		}	
        
%>


<table cellpadding="0" cellspacing="0" class="title_top">
	<tr>
		<td height="24">
		<table cellspacing="0" cellpadding="0" class=title_Top1>
			<TR>
				<td class=title nowrap><span class="txt_til2"><%=strTitle %></span></td>
			    <td class=title_right><a class=img_title></td>
			</TR>
		</TABLE>
		<br/>	
		<form name="form" method="post" action="<%=strContext%>/project/wisgfc/special/control/consignReceiveConfirm.jsp">
		<input type="hidden" name="strAction" >
		<input type="hidden" name="id" >
		<input name="strSuccessPageURL"  type="hidden" value="<%=strContext%>/project/wisgfc/special/view/consignReceiveConfirmQuery.jsp">
		<input name="strFailPageURL"  type="hidden" value="<%=strContext%>/project/wisgfc/special/view/consignReceiveConfirmQuery.jsp">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class=normal  >			
			<tr>
	          <td width="5" height="25"></td>
	          <%   										
				strMagnifierName = URLEncoder.encode("�տ���");
				strCtrlName = "q_payeeClientIDStart";
				strTitle = "�տ���";		
				strFirstTD = " width = \"150\" ";				
				sNextControls = new String[]{"q_payeeClientIDEndCtrl"};
				strMainNames = new String[]{ strCtrlName + "Ctrl"};
				strMainFields = new String[]{ "code" };
				strReturnNames = new String[]{ "q_payeeClientIDStart" };
				strReturnFields = new String[]{ "id" };
				strReturnValues = new String[]{ String.valueOf(q_payeeClientIDStart)};
				strDisplayNames = new String[]{ URLEncoder.encode("��λ���"), URLEncoder.encode("��λ����")};
				strDisplayFields = new String[]{ "code", "name" };
				strMainProperty = " maxlength='40' value='" + q_payeeClientCodeStart + "'";
				strSQL = "getConsignReceiveClientSQL("+nOfficeID+","+ strCtrlName + "Ctrl.value" + ")";
										
				OBMagnifier.showZoomCtrl1(out,strMagnifierName,strFormName,strPrefix,
				strMainNames,strMainFields,strReturnNames,strReturnFields,strReturnValues,
				strDisplayNames,strDisplayFields,nIndex,strMainProperty,strSQL,sNextControls,
				strTitle,strFirstTD,strSecondTD,false,false);
																		
			%>				 
			<%   										
				strMagnifierName = URLEncoder.encode("�տ���");
				strCtrlName = "q_payeeClientIDEnd";
				strTitle = "��";		
				strFirstTD = " width = \"100\" ";				
				sNextControls = new String[]{"q_inputStart"};
				strMainNames = new String[]{ strCtrlName + "Ctrl"};
				strMainFields = new String[]{ "code" };
				strReturnNames = new String[]{ "q_payeeClientIDEnd" };
				strReturnFields = new String[]{ "id" };
				strReturnValues = new String[]{ String.valueOf(q_payeeClientIDEnd)};
				strDisplayNames = new String[]{ URLEncoder.encode("��λ���"), URLEncoder.encode("��λ����")};
				strDisplayFields = new String[]{ "code", "name" };
				strMainProperty = " maxlength='40' value='" + q_payeeClientCodeEnd + "'";
				strSQL = "getConsignReceiveClientSQL("+nOfficeID+","+ strCtrlName + "Ctrl.value" + ")";
										
				OBMagnifier.showZoomCtrl1(out,strMagnifierName,strFormName,strPrefix,
				strMainNames,strMainFields,strReturnNames,strReturnFields,strReturnValues,
				strDisplayNames,strDisplayFields,nIndex,strMainProperty,strSQL,sNextControls,
				strTitle,strFirstTD,strSecondTD,false,false);
																		
			%>	                    
	        
	          <td width="8"></td>
        	</tr>
			<tr>
	          <td width="5" height="25"></td>
	          <td width="78" height="25" class="graytext" >�ύ���ڣ�&nbsp;��</td>	        
	          <td width="188" height="25" class="graytext">
	          <fs_c:calendar 
	         	    name="q_inputStart"
		          	value="" 
		          	properties="nextfield ='q_inputEnd'" 
		          	size="18"/>
		           <script>
	          		$('#q_inputStart').val('<%=q_inputStart %>');
	          	</script>
	          </td>
	          <td height="25" class="graytext" align="left">
	            <span class="graytext">����</span>
	          </td>
	          <td width="330" height="25" class="graytext">
	             <fs_c:calendar 
		         	    name="q_inputEnd"
			          	value="" 
			          	properties="nextfield ='q_NStatus'" 
			          	size="18"/>
			      <script>
	          		$('#q_inputEnd').val('<%=q_inputEnd %>');
	          	</script>
			  </td>
	          <td width="8"></td>
        	</tr>
        	<tr id="commonStatus" height="25">
				<td width="5" height="25"></td>
				<td width="100" class="graytext" >״̬��</td>
				<td class="graytext" align="left">
				<%
				//״̬
				OBHtmlCom.showConsignReceiveStatusListControl(
				    out,"q_NStatus",q_NStatus," onfocus=\"nextfield ='btnQuery';\" ",2);
				%>
				</td>						
			</tr>
			<tr>
				<td colspan="6">
				<div align="right">	
					<input type="Button" class="button1" value=" �� �� " name="btnQuery" width="46" height="18"   onclick="javascript:query()">&nbsp;&nbsp;					
				</div>
				</td>
			</tr>
		</table>
		</form>
		</br>	
		<table width=100% border="1"  cellpadding="0" cellspacing="0" class=normal >
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
			<td width="10%" align="center" nowrap><div>�ύ��</div></td>
			<td width="10%" align="center" nowrap><div>�ύ����</div></td>
			<td width="10%" align="center" nowrap><div>ȷ����</div></td>
			<td width="10%" align="center" nowrap><div>ȷ������</div></td>	
			<td width="10%" align="center" nowrap><div>ִ����</div></td>
			<td width="10%" align="center" nowrap><div>��תָ���</div></td>	
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
					NAbstractID = info.getNAbstractID();
					NAbstractName = NameRef.getAbstractNameByID(NAbstractID);
					SContractCode = info.getSContractCode();
					SStatusName = OBConstant.SettInstrStatus.getName(info.getNStatus());
					SInputUserName = NameRef.getUserNameByID(info.getNInputUserID());
					SInput = DataFormat.getDateString(info.getDTInput());
					NInstrID = info.getNInstrID();
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
			<td align="center" nowrap><%=NInstrID>0?String.valueOf(NInstrID):""%></td>		         
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
			<td align="center" ></td>                                           
		</tr>				
	 	<%}%>		
		</table>
		<table width="100%">
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
	firstFocus(form.q_payeeClientIDStartCtrl);	
	//setSubmitFunction("query()");
	
</script>

<script language="javascript">
//У��
function allFields(){	
	this.aa = new Array("q_payeeClientIDStart","�տ�����","magnifier",0);
	this.ab = new Array("q_payeeClientIDEnd","�տ�����","magnifier",0);	
	this.ac = new Array("q_inputStart","�ύ������","date",0);
	this.ad = new Array("q_inputEnd","�ύ������","date",0);	
}

//У��
function validate(frm) {
	var bResult = true;	
	var q_inputStart = form.q_inputStart.value;
	var q_inputEnd = form.q_inputEnd.value;
	if (q_inputStart != "") {
	    if(chkdate(q_inputStart) == 0) {
	        alert("��������ȷ���ύ��ʼ����");
	        form.q_inputStart.focus();
	        return false;
	    }
	}
	if (q_inputEnd != "") {
	    if(chkdate(q_inputEnd) == 0) {
	        alert("��������ȷ���ύ��������");
	        form.q_inputEnd.focus();
	        return false;
	    }
	}
	if ((q_inputStart != "") && (q_inputEnd != "")) {
	    if (!CompareDate(form.q_inputStart, form.q_inputEnd, 
	        "�ύ���ڣ���ʼ���ڲ��ܴ��ڽ�������")) {
	        return false;
	    }
	}
	return bResult;
}

//��ѯ
function query(){
	if(!validateFields(form)) return false;
	if(!validate(form)) return false;	
	form.strAction.value="<%=OBConstant.Actions.MATCHSEARCH%>";
	form.submit();	
}

//������ϸҳ��
function doSee(id){
	form.id.value=id;
	form.action = "<%=strContext%>/project/wisgfc/special/control/consignReceiveConfirm.jsp?menu=hidden";
	form.strAction.value="<%=OBConstant.Actions.TODETAIL%>";
	form.strSuccessPageURL.value ="<%=strContext%>/project/wisgfc/special/view/consignReceiveConfirmUpdate.jsp";
	form.strFailPageURL.value="<%=strContext%>/project/wisgfc/special/view/consignReceiveConfirmQuery.jsp";
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

