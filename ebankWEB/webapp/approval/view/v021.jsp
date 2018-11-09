<%--
 ҳ������ �� v032.jsp
 ҳ�湦�� :  ��������������
 ��    �� �� ygzhao
 ��    �� �� 2005-05-26
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
<%@ page import="com.iss.itreasury.ebank.approval.dataentity.ApprovalRelationInfo" %>	
<%@ page import="com.iss.itreasury.ebank.util.*" %>
<%@ page import="com.iss.itreasury.system.util.SYSHTML" %>


<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%

try
{
	/** Ȩ�޼�� **/
	String strTableTitle = "��������������";
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

	long lOfficeID = sessionMng.m_lOfficeID;
	long CurrencyID = sessionMng.m_lCurrencyID;
	long lClientID  = sessionMng.m_lClientID;
	
	long lLoanTypeID = -1;//��������
	long lSubLoanTypeID = -1;//����������ID	
	
	ApprovalRelationInfo qInfo = null;
	qInfo = (ApprovalRelationInfo)sessionMng.getQueryCondition("qInfo");
	
	if(qInfo.getLoanTypeID() > 0)
	{
		lLoanTypeID = qInfo.getLoanTypeID();
	}
	if(qInfo.getSubLoanTypeID() > 0)
	{
		lSubLoanTypeID = qInfo.getSubLoanTypeID();
	}
	
	Collection resultColl = (Collection)request.getAttribute("resultColl");
	
//////////
%>

<%//��������,����showMessage.jsp,����js�ļ�%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<!--����js�ļ�-->
<script language="JavaScript" src="/webob/js/Control.js" ></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>

<safety:resources />

<!--����js�ļ�-->

<form name="frm" method="post" action="../control/c021.jsp" >
<input name="strSuccessPageURL" type="hidden" value="../view/v021.jsp">
<input name="strFailPageURL" type="hidden" value="../view/v021.jsp">
<input name="strAction" type="hidden" value="<%=Constant.Actions.MODIFYSAVE%>">	

<%//�ڰ˲���,����ҵ������������%>

<!--����ҵ������-->
<!--����ҵ������-->

<%//�ھŲ���,ҳ��������ʾ����,Ҫ�����е�Ԫ���е�ֵ����dataentity�л��,�������Զ����ҳ������Լ������ҳ�����%>


<TABLE border=0 class=top height=100 width="83%">
	<TBODY>
	<TR>
		<TD class=FormTitle height=29><B>��������������</B></TD>
	</TR>
	<TR>
		<TD height=50 vAlign=top>


		<TABLE height=50 width="100%">
			<TBODY>
			<TR vAlign=middle>
				<TD height=28 width="5%">&nbsp;</TD>
				<TD height=28 width="20%">&nbsp;</TD>
				<TD height=28 width="20%">&nbsp;</TD>
				<TD height=28 width="15%">&nbsp;</TD>
				<TD height=28 width="10%">&nbsp;</TD>
				<TD height=28 width="30%">&nbsp;</TD>
			</TR>
			<TR vAlign=middle>
				<TD>&nbsp;</TD>
				<TD>���´���</TD>
				<TD>
					<%SYSHTML.showCommonListControl(out, "officeID", "ID", "sName", "office", " where nstatusid=1"," disabled ", sessionMng.m_lOfficeID);%>
				</TD>
				<TD></TD>
				<TD>					
				</TD>
			</TR>			
			<TR vAlign=middle>
				<TD>&nbsp;</TD>
				<TD>��λ��</TD>
				<TD>
					<%=NameRef.getClientNameByID(sessionMng.m_lClientID)%>
				</TD>
				<TD></TD>
				<TD>					
					
				</TD>
			</TR>
			<TR>
				<TD colSpan=5 height=20><HR></TD>
			</TR>
			<TR vAlign=middle>
				<TD>&nbsp;</TD>
				<TD>��������</TD>
				<TD align="center">	
				��ǰ����������			
				</TD>
				<TD align="center">	
				����			
				</TD>
			</TR>
			<TR>
				<TD colSpan=5 height=20><HR></TD>
			</TR>
<%
	long[] approvalAction = Constant.ApprovalAction.getAllCode(Constant.ModuleType.BUDGET);
	for(int i=0;i<approvalAction.length;i++)
	{
		long actionID = approvalAction[i];
		String actionName = Constant.ApprovalAction.getName(actionID);
%>
		<TR vAlign=middle>
				<TD>&nbsp;</TD>
				<TD><%=actionName%></TD>
				<TD align="center">
				<%
				Iterator itResult = null;
				boolean bSetted = false;
				if(resultColl != null)
				{
					itResult = resultColl.iterator();
					while(itResult!=null && itResult.hasNext())
					{
						ApprovalRelationInfo info = (ApprovalRelationInfo)itResult.next();
						if(info.getActionID() == actionID)//�ѶԸò�����������������
						{
							out.print(NameRef.getApprovalSettingNameByID(info.getApprovalID()));
							out.print("<input name='approvalID" + String.valueOf(actionID) + "' type='hidden' value='" + info.getApprovalID() + "'>");
							bSetted = true;
							break;
						}
					}
				}
				if(bSetted == false)
					out.print("��");
				%>
				</TD>				
				<%
				long lApprovalID = -1;
				String strFormName = "frm";
				String strControlName = "approvalSetting" + String.valueOf(actionID);
				String strPrefix = "";
				String strMainProperty = "";
				String strReturnName = "actionID" + String.valueOf(actionID);
				String strNextControls = "";
				
				OBMagnifier.CreateApprovalSettingCtrl(lOfficeID, CurrencyID,lClientID, out, lApprovalID, strFormName, strControlName, strPrefix, strMainProperty, strReturnName, strNextControls);
				%>				
		</TR>
<%
	}
%>
			</TBODY>
		</TABLE>
		</TD>
	</TR>
	<TR>
		<TD height=20 vAlign=top>
		<TABLE align=center border=0 height=20 width=819>
			<TBODY>
			<TR>
				<TD colSpan=10 height=20><HR></TD>
			</TR>
			<TR>
				<TD height=17 width=98>&nbsp;</TD>
				<TD align=left colSpan=2 height=17></TD>
				<TD align=right height=17 width=55><DIV align=left></DIV></TD>
				<TD align=right height=17 width=121></TD>
				<TD align=right colSpan=2 height=17></TD>
				<TD align=right height=17 width=6></TD>
				<TD align=right height=17 width=327>
					<DIV align=right>
						<INPUT class=button name="nextStep" type=button value=" �� �� " onfocus="nextfield='submitfunction'" onclick="doSave(frm)">
					</DIV>
				</TD>
			</TR>
			</TBODY>
		</TABLE>
		</TD>
	</TR>
	</TBODY>
</TABLE>

</form>
<script language="JavaScript">
	//firstFocus(document.frm001.acceptPO);
	//setSubmitFunction("doSave(frm)");
	setFormName("frm"); 
</script>
<script language="JavaScript">
function doSave(frm)
{
	if(confirm("�Ƿ񱣴棿"))
	{
		showSending();
		frm.submit();
	}
}
</script>

<%
	OBHtml.showOBHomeEnd(out);	
%>

<%
}
catch(IException ie)
{
	Log.print(ie.getMessage());
}
%>
<%@ include file="/common/SignValidate.inc" %>