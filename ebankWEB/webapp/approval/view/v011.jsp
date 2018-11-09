<%--
 * ҳ������ ��v011.jsp
 * ҳ�湦�� : ���������ã���ѯ(����)
 * ��    �� ��ygzhao
 * ��    �� ��2005-05-08
 * ����˵�� ��
 * ת��ҳ�� : 
--%>
<%@ page contentType="text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
	<%@ page import="java.sql.Timestamp" %>
	<%@ page import="com.iss.itreasury.util.*" %>
	<%@ page import="com.iss.itreasury.settlement.util.*" %>
	<%@ page import="com.iss.itreasury.settlement.util.SETTMagnifier" %>
	<%@ page import="com.iss.itreasury.settlement.util.SETTConstant" %>
	<%@ page import="com.iss.itreasury.ebank.util.*" %>
	<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
	<% String strContext = request.getContextPath();%>
	<%	
    try{
		String strTableTitle = "����������";
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

       
	%>
	<jsp:include page="/ShowMessage.jsp"/>
	
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
	
<script language="JavaScript" src="/websys/js/Control.js" ></script>
<script language="JavaScript" src="/websys/js/Check.js"></script>
<script language="JavaScript" src="/websett/js/SettCheck.js"></script>
<script language="JavaScript" src="/websett/js/MagnifierSQL.js"></script>

<safety:resources />

	<FORM name="frm" method="post" action="../control/c011.jsp">		
		<input name="strAction" type="hidden">
		<input name="strSuccessPageURL" type="hidden" value="../view/v012.jsp">
		<input name="strFailPageURL" type="hidden" value="../view/v011.jsp">
		<TABLE width="80%" height="10" border="0" class="top">
			<TBODY>
				<TR>
					<TD class="FormTitle" height="2" width="100%"><B>����������</B></TD>
				</TR>
				<TR>
					<TD width="100%" height="40" vAlign="bottom">
						<fieldset>
						<TABLE align="center" border="0" width="100%">
							<TBODY>
								<TR>									
									<%
		long lOfficeID = -1;//sessionMng.m_lOfficeID;
		long lCurrencyID = -1;//sessionMng.m_lCurrencyID;
		String strFormName = "frm";
		String strCtrlName = "inputUserID";		
		String strTitle = "¼����";
		long lUserID = -1;
		String strUserName = "";
		String strFirstTD = "";
		String strSecondTD = "";
		String[] strNextControls = {"startDate"};
	SETTMagnifier.createUserCtrl(
		out,
		lOfficeID,
		lCurrencyID,
		strFormName,
		strCtrlName,
		strTitle,
		lUserID,
		strUserName,
		strFirstTD,
		strSecondTD,
		strNextControls);
 %>
									
									<TD width="18%" height="20">	
									</TD>
									<TD width="29%" height="20">										
									</TD>									
								</TR>
								<TR>									
									<td>	��ʼ���ڣ�
									</td>
									<td>
										<fs_c:calendar 
							          	    name="startDate"
								          	value="" 
								          	properties="nextfield ='endDate'" 
								          	size="20"/>
							        </td>									
									<td>	��ֹ���ڣ�
									</td>
									<td>
										<fs_c:calendar 
							          	    name="endDate"
								          	value="" 
								          	properties="nextfield ='submitfunction'" 
								          	size="20"/>
							        </td>
								</TR>	
								<TR>									
									<td>	״̬��
									</td>
									<td>
									<%
										Constant.ApprovalStatus.showList(out,"statusID",-1,false,true,"");
									%>											
									</td>									
									<td>	
									</td>
									<td>										
									</td>
								</TR>							
							</TBODY>
						</TABLE></fieldset>
					</TD>
				</TR>
				<TR>
					<TD height="10" vAlign="top" width="100%">
						<TABLE align="center" height="15" width="97%">
							<TBODY>
								<TR>
									<TD colSpan='4' height="10">
										<DIV align=right>
                       						<INPUT name="btnSearch" type="button" class="button" onClick="doSearch(frm);"  value=" �� ѯ ">
                  							<INPUT name="btnAdd" type="button" class="button" onClick="location.href='../view/v014.jsp';"  value=" �� �� ">
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
<script language="javascript">
var isSubmited = false;
function doSearch(frm)
{	
	if(isSubmited == true)
    {
        alert("�������ύ����Ⱥ�");
        return;
    }	
	if(!validateFields(frm)) return;
	
	if(frm.startDate.value!="" && frm.endDate.value!="" & frm.startDate.value > frm.endDate.value)
	{
		alert("��ʼ���ڲ��ܴ�����ֹ����!");
		frm.startDate.focus();
		return false;
	}
	isSubmited = true;
	frm.strAction.value = "toQuery";
	showSending();
	document.frm.submit();	
}
function allFields()
{					
		this.aa = new Array("inputUserID","¼����","magnifier",0);
		this.ab = new Array("startDate","��ʼ����","date",0);
		this.ac = new Array("endDate","��ֹ����","date",0);		
} 
firstFocus(frm.inputUserIDCtrl);
//setSubmitFunction("doSearch(frm)");
setFormName("frm");
</script>
<%
	OBHtml.showOBHomeEnd(out);	
	}catch(Exception exp){
		Log.print(exp.getMessage());
	}
%>
<%@ include file="/common/SignValidate.inc" %>