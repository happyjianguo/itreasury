<%--
 ҳ������ ��v032.jsp
 ҳ�湦�� : ��������������(��ϸ����ҳ��)
 ��    �� ��ypxu
 ��    �� ��2007-4-16
 ����˵�� ��
 ʵ�ֲ���˵����
 �޸���ʷ ��
--%>

<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.iss.itreasury.util.Env" %>
<%@ page import="com.iss.itreasury.util.Log" %>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="com.iss.itreasury.util.DataFormat" %>
<%@ page import="com.iss.itreasury.util.PageCtrlInfo" %>
<%@ page import="com.iss.itreasury.util.IException" %>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant" %>
<%@ page import="com.iss.itreasury.loan.util.LOANMagnifier" %>
<%@ page import="com.iss.itreasury.ebank.util.NameRef" %>
<%@ page import="com.iss.itreasury.ebank.approval.dataentity.InutApprovalRelationInfo" %>	
<%@ page import="com.iss.itreasury.ebank.approval.bizlogic.InutApprovalRelationBiz" %>	
<%@ page import="com.iss.itreasury.ebank.util.OBHtml" %>
<%@ page import="com.iss.itreasury.system.util.SYSHTML" %>
<%@ page import="com.iss.itreasury.ebank.util.*" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<jsp:include page="/ShowMessage.jsp"/>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<%
	String strTableTitle = null;
try
{
	//������
		/** Ȩ�޼�� **/
		//�û���¼��� 
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng,  strTableTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        // �ж��û��Ƿ���Ȩ�� 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTableTitle, "", 1, "Gen_E003");
        	out.flush();
        	return;
        }
		
		/* ��ʾ�ļ�ͷ */
        OBHtml.showOBHomeHead(out, sessionMng, strTableTitle, Constant.YesOrNo.YES);
        
        String strRootPath = request.getContextPath();//http://xxxx/../cpob
			
	InutApprovalRelationInfo qInfo = null;
	qInfo = (InutApprovalRelationInfo)sessionMng.getQueryCondition("qInfo");	
	
	Collection c_Result = (Collection)request.getAttribute("c_Result");
	//��ȡ����������
	InutApprovalRelationBiz inutApprovalRelationBiz = new InutApprovalRelationBiz();
	String strApprovalName = "";
	
	long lOfficeID = sessionMng.m_lOfficeID;
	long lCurrencyID = sessionMng.m_lCurrencyID;
	long lClientID = sessionMng.m_lClientID;	
	long lAction = Constant.Actions.MODIFYSAVE;	
	long moduleID = -1;
	if(qInfo != null && qInfo.getModuleID() > 0) 
	{
		moduleID = qInfo.getModuleID();
	}
	
//////////
%>

<form name="frm" method="post" action="../control/c032.jsp" >
<input name="strSuccessPageURL" type="hidden" value="../view/v031.jsp">
<input name="strFailPageURL" type="hidden" value="../view/v032.jsp">
<input name="strAction" type="hidden" value="<%=lAction%>">
<input name="lOfficeID" type="hidden" value="<%=lOfficeID%>">
<input name="currencyID" type="hidden" value="<%=lCurrencyID%>">
<input name="clientID" type="hidden" value="<%=lClientID%>">
<input name="moduleID" type="hidden" value="<%=moduleID%>">

<%//�ھŲ���,ҳ��������ʾ����,Ҫ�����е�Ԫ���е�ֵ����dataentity�л��,�������Զ����ҳ������Լ������ҳ�����%>

<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="24">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">��������������</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
<br/>

<table width=100%  cellspacing="1" class=normal id="table1" align="" >
	<tr>
		<td colspan=5>&nbsp;</td>
	</tr>
	<tr>		
				<td width="2%"></td>		
				<td nowrap>���´���</td>
				<td algin="left" colspan=2>
			<%
				String strControlName = "officeID";	
				String strID = "ID";
				String strName = "sName";	
				String strTable = "office";
				String strCondition = " where nstatusid=1";	
				String strProperty = " disabled ";
				long lData = sessionMng.m_lOfficeID;
				SYSHTML.showCommonListControl(out, strControlName, strID, strName, strTable, strCondition,strProperty, lData);
			%>
					
				</td>
		</tr>
		<tr>		
				<td width="2%"></td>		
				<td nowrap>ģ��ѡ��</td>
				<td algin="left" colspan=2>
			<%	
				strControlName = "moduleID";				
				long lSelectValue = qInfo.getModuleID();
				boolean isNeedAll = false;
				boolean isNeedBlank = false;	
				int nType = 1;		
				strProperty = "disabled";
				long[] lArrayID = Constant.ModuleType.getAllCode(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
				Constant.ModuleType.showList(out, strControlName, nType, lSelectValue, isNeedAll, isNeedBlank, strProperty, lArrayID);
			%>
				</td>
		</tr>
						<tr>		
				<td width="2%"></td>		
				<td nowrap>�Ƿ������¼���λ��������</td>
				<td colspan=2>
				<input type="hidden" name="isLowerUnit" value="<%=qInfo.getIslowerunit()%>">
				<select class="select" name="isLowerUnit2" disabled>
				<option value="<%=OBConstant.IsLowerun.ISNO%>"><%=OBConstant.IsLowerun.getName(OBConstant.IsLowerun.ISNO)%></option>
				<option value="<%=OBConstant.IsLowerun.ISYES%>"><%=OBConstant.IsLowerun.getName(OBConstant.IsLowerun.ISYES)%></option>
				<SCRIPT LANGUAGE="JavaScript">
				<!--
				var slt=document.forms[0].elements["isLowerUnit2"];
				for(var i=0;i<slt.options.length;i++)
				if(slt.options[i].value==<%=qInfo.getIslowerunit()%>)
				slt.options[i].selected=true;
				//-->
				</SCRIPT>
				</td>
		</tr>
		<tr>
			<td colspan=7>&nbsp;</td>
		</tr>	
		<tr>
				<td colSpan=7><hr color=#999999 style="height:1px" noshade></td>
		</tr>
		<tr>
				<td>&nbsp;</td>
				<td nowrap><input type="checkbox" name="checkAll" onclick="javascript:checkall();"/>ȫѡ</td>
				<td nowrap>ҵ������</td>
				<% 
					if(qInfo.getIslowerunit()==OBConstant.IsLowerun.ISNO)
					{
				%>
					<td nowrap>�Ƿ��ͽ��ϼ�����</td>
				<% 
					}
				%>
				<td align="center" nowrap>��ǰ����������</td>				
				<td colspan="2" align="center">����</td>
		</tr>
		<tr>
				<td colSpan=7><hr color=#999999 style="height:1px" noshade></td>
		</tr>
		<tr>
<%
	long[] lTransTypeID = null;
	long transTypeID = -1;
	String transName = "";
	if(qInfo.getModuleID() > 0 && qInfo.getModuleID() == Constant.ModuleType.LOAN)
	{
		//lTransTypeID = OBConstant.LoanInstrType.getAllCode(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
		lTransTypeID = OBConstant.LoanInstrType.getAllCode();
	}
	if(qInfo.getModuleID() > 0 && qInfo.getModuleID() == Constant.ModuleType.SETTLEMENT)
	{
		//lTransTypeID = OBConstant.SettInstrType.getAllCode(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
		lTransTypeID = OBConstant.SettInstrType.getAllCode();
	}
		for(int i=0;i<lTransTypeID.length;i++)
		{				
			transTypeID = lTransTypeID[i];
			%>
				<input type="hidden" name="transTypeID" value="<%=transTypeID%>">
			<%			
			if(qInfo.getModuleID() > 0 && qInfo.getModuleID() == Constant.ModuleType.LOAN)
			{
				transName = OBConstant.LoanInstrType.getName(transTypeID);
			}
			if(qInfo.getModuleID() > 0 && qInfo.getModuleID() == Constant.ModuleType.SETTLEMENT)
			{
				transName = OBConstant.SettInstrType.getName(transTypeID);
			}
			
				Iterator itResult = null;
				boolean bSetted = false;
				boolean ballowtransType = false;
				if(lTransTypeID[i]!=OBConstant.SettInstrType.CAPTRANSFER_SELF &&
			 	  lTransTypeID[i]!=OBConstant.SettInstrType.CAPTRANSFER_OTHER &&
		  	 	  lTransTypeID[i]!=OBConstant.SettInstrType.CHILDCAPTRANSFER &&
			  	  lTransTypeID[i]!=OBConstant.SettInstrType.TRUSTLOANRECEIVE &&
			  	  lTransTypeID[i]!=OBConstant.SettInstrType.CONSIGNLOANRECEIVE &&
			  	  lTransTypeID[i]!=OBConstant.SettInstrType.INTERESTFEEPAYMENT &&
			      lTransTypeID[i]!=OBConstant.SettInstrType.YTLOANRECEIVE &&
			      lTransTypeID[i]!=OBConstant.SettInstrType.APPLYCAPITAL &&
			      lTransTypeID[i]!=OBConstant.SettInstrType.CAPTRANSFER_FINCOMPANYPAY &&
			      lTransTypeID[i]!=OBConstant.SettInstrType.CAPTRANSFER_PAYSUBACCOUNT &&
			      lTransTypeID[i]!=OBConstant.SettInstrType.BANKPAY_DOWNTRANSFER &&
			      lTransTypeID[i]!=OBConstant.SettInstrType.CHANGEFIXDEPOSIT &&
			      lTransTypeID[i]!=OBConstant.SettInstrType.CHANGEFIXDEPOSIT )
			     {
					ballowtransType = true;
			     }
				
				if(c_Result != null)
				{
					itResult = c_Result.iterator();
					while(itResult!=null && itResult.hasNext())
					{
						InutApprovalRelationInfo info = (InutApprovalRelationInfo)itResult.next();
						if(info.getClientID() == lClientID && info.getTransTypeID() == transTypeID)//�ѶԸ�ҵ����������������
						{
							strApprovalName = inutApprovalRelationBiz.findNameByApprovalID(info.getApprovalID());
							out.print("<td>&nbsp;</td>");
							out.print("<td nowrap><input type='checkbox' name='txtIDCheckbox' value='"+i+"' onclick='checkboxChange()'>");
							out.print("<td nowrap>"+transName+"</td>");
							String ischecked = "";
							if(info.getIssendtoupclient()>0)
							{
								ischecked = "checked";
							}
							if(qInfo.getIslowerunit()==OBConstant.IsLowerun.ISNO)
							{
								out.print("<td nowrap align='center'><input type='checkbox' name='isSendToUpClientCheckbox"+i+"' value='1' "+ ischecked +">");
							}
							//out.print(NameRef.getApprovalSettingNameByID(info.getApprovalID()));
							out.print("<td align='center' nowrap>" + strApprovalName);							
							out.print("<input name='oldapprovalID' type='hidden' value='" + info.getApprovalID() + "'>"
									+"<input name='id' type='hidden' value='" + info.getId() + "'></td>");
							bSetted = true;
							break;
						}
					}
				}
				if(bSetted == false)
				{
					out.print("<td>&nbsp;</td>");
					out.print("<td nowrap><input type='checkbox' name='txtIDCheckbox' value='"+i+"' disabled onclick='checkboxChange()'>");
					out.print("<td nowrap>"+transName+"</td>");
					String strdisabled="";
					if(!ballowtransType)
					{
						strdisabled = "disabled";
					}
					if(qInfo.getIslowerunit()==OBConstant.IsLowerun.ISNO)
					{
						out.print("<td nowrap align='center'><input type='checkbox' name='isSendToUpClientCheckbox"+i+"' value='1' "+strdisabled+">");
					}
					out.print("<td align='center' nowrap>-</td>");
					out.print("<input name='id' type='hidden' value='-1'>");
				}	
				%>				
				<%-- 
				<input type=text name=<%="approvalID"%> value="">	
				--%>
				<%
			if(ballowtransType)
			{
				String _next = "";
				String approvalName = "approvalName"+i;
				String approvalID = "approvalID"+i;
				String hidApprovalName = "hidApprovalName"+i;
				request.setAttribute("approvalID",approvalID);
				request.setAttribute("hidApprovalName",hidApprovalName);
			%>
					<td width="80" height="25" align="left">���̹�����</td>
					<td>
 						<fs:dic id="<%=approvalName %>" size="22" form="frm" title="���̹���" sqlFunction="getApprovalRelationSettingSQL"  sqlParams='frm.lOfficeID.value,frm.clientID.value' 
 						value="" nextFocus="" width="500">
							<fs:columns> 
								<fs:column display="����������" name="Name" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
							</fs:columns>
							<fs:pageElements>
								<fs:pageElement elName="${approvalID}" name="ID" type="<%=PagerTypeConstant.STRING %>" elType="hidden"  />
								<fs:pageElement elName="${hidApprovalName}" name="Name" type="<%=PagerTypeConstant.STRING %>" elType="text" />
							</fs:pageElements>							
						</fs:dic> 
					</td>
			<%		
			}
			%>		
			
		</tr>
		
<%
	}		
	
%>

		<tr>
			<td height="10"></td>
		</tr>
		<tr>
			<td colspan="8">
				<div align="right">
					<INPUT name="relation" type=button value=" �� �� " class="button1"
						onfocus="nextfield='submitfunction'" onclick="doSave(frm)">
					&nbsp;&nbsp;
					<INPUT name="unRelation" type=button value=" ȡ������ " class="button1"
						onfocus="nextfield='submitfunction'" onclick="doDel(frm)">
					&nbsp;&nbsp;
					<INPUT name="return" type=button value=" �� �� " class="button1"
						onfocus="nextfield='submitfunction'" onclick="goBack(frm)">
					&nbsp;&nbsp;&nbsp;&nbsp;
				</div>
			</td>
		</tr>
		<tr>
			<td height="5"></td>
		</tr>
	</table>
<br>
	

</form>
<script language="JavaScript">
	//firstFocus(document.frm.approvalName);
	//setSubmitFunction("doSave(frm)");
	setFormName("frm"); 
</script>
<script language="JavaScript">
//����������������
function doSave(frm)
{
	var isNotNull = false;
	var approvalName = "";
	var hidApprovalName = "";
	for(var i = 0; i < document.frm.elements.length; i++)
   	{
   		for(var j = 0; j < document.frm.txtIDCheckbox.length; j++)
   		{
   			if(document.frm.elements[i].name == "approvalID" + j)
   			{
   				if(document.frm.elements[i].value > 0)
   				{
   					isNotNull = true;
   					break;
   				}  
   			}   
   		}
   	}
   	if(!isNotNull)
   	{
   		alert("��ӷŴ�������ѡ��һ����Ҫ������������");
   		return;
   	}
	if(confirm("�Ƿ����ù���?"))
	{
		showSending();
		frm.action = "../control/c032.jsp?operation=save";
		frm.submit();
	}	
}
//ɾ��������������
function doDel(frm)
{
	if (doCheck())
	{
		if(confirm("�Ƿ�ȡ��������"))
		{
			showSending();
			frm.action = "../control/c032.jsp?operation=del";
			frm.submit();
		}
	}
}
//��������������ҳ
function goBack(frm)
{	
	location.href='../view/v031.jsp'
	showSending();
}
//ȫѡ��ѡ��
 function checkall()
 {
 	for( var c = 0;c < document.frm.elements.length; c++)
	{
	    if(frm.elements[c].name == "txtIDCheckbox" )
	    {
	    	if(frm.elements[c].disabled == false)
	    	{
	       		frm.elements[c].checked = document.frm.checkAll.checked;
	       }
	    }
	}
 } 
 //�ж��Ƿ�ѡ
 function doCheck()
 {
 	  	var isCheck = false;
		for(i=0; i<document.frm.elements.length; i++)
   		{
     		if(document.frm.elements[i].name == "txtIDCheckbox")
			{   			
				if (document.frm.elements[i].checked == true && document.frm.elements[i].disabled == false)
				{
					isCheck = true;
					break;
				}
			}
		}
		if (!isCheck)
		{
			alert("��ѡ��Ҫȡ�������ļ�¼");
			return false;
		}
		return true;
 }
 //��ѡ�򴥷��¼�
 function checkboxChange()
{
	if(document.frm.txtIDCheckbox)
	{
		var allChecked = true;
		if(document.frm.txtIDCheckbox.length)
		{
			for(var i = 0; i < document.frm.txtIDCheckbox.length; i++)
			{
				if(document.frm.txtIDCheckbox[i].checked == false)
				{
					if(document.frm.txtIDCheckbox[i].disabled == false)
					{
						allChecked = false;
						break;
					}
				}
			}
		}
		else
		{
			allChecked = document.frm.txtIDCheckbox.checked;
		}
		document.frm.checkAll.checked = allChecked;
	}
}
</script>

<%
	/**
	* ��ʾ�ļ�β
	*/
	OBHtml.showOBHomeEnd(out);
%>

<%
}
catch(IException ie)
{
	
	ie.printStackTrace();
	out.flush();
	return; 
}
%>
<%@ include file="/common/SignValidate.inc" %>