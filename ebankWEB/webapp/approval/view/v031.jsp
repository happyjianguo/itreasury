<%--
 ҳ������ ��v031.jsp
 ҳ�湦�� : ��������������(����)
 ��    �� ��ypxu
 ��    �� ��2007-4-16
 ����˵�� ��
 ʵ�ֲ���˵����
 �޸���ʷ ��
--%>
<%@ page contentType="text/html;charset=gbk"%>
<%@ page
	import="java.util.Collection,com.iss.itreasury.util.*,com.iss.itreasury.ebank.util.SessionOB,com.iss.itreasury.ebank.util.OBHtml,com.iss.itreasury.ebank.util.OBConstant,com.iss.itreasury.util.Constant,com.iss.itreasury.system.util.SYSHTML"%><%@ page
	import="com.iss.itreasury.ebank.util.OBConstant.ListType"%><%@ page
	import="com.iss.itreasury.util.Constant.ModuleType"%>
<jsp:useBean id="sessionMng" scope="session"
	class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
	String strTableTitle = null;
	try { //������
		/** Ȩ�޼�� **/
		//�û���¼��� 
		if (sessionMng.isLogin() == false) {
			OBHtml.showCommonMessage(out, sessionMng, strTableTitle,
					"", 1, "Gen_E002");
			out.flush();
			return;
		}

		// �ж��û��Ƿ���Ȩ�� 
		if (sessionMng.hasRight(request) == false) {
			out.println(sessionMng.hasRight(request));
			OBHtml.showCommonMessage(out, sessionMng, strTableTitle,
					"", 1, "Gen_E003");
			out.flush();
			return;
		}

		/* ��ʾ�ļ�ͷ */
		OBHtml.showOBHomeHead(out, sessionMng, strTableTitle,
				Constant.YesOrNo.YES);

		long lOfficeID = sessionMng.m_lOfficeID;
		long lCurrencyID = sessionMng.m_lCurrencyID;
		long lClientID = sessionMng.m_lClientID;
		long nextstep = Constant.Actions.NEXTSTEP;
%>

<jsp:include page="/ShowMessage.jsp" />
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<!-- ҳ��ʹ��js���� -->
<script language="JavaScript" src="/websys/js/Control.js"></script>
<script language="JavaScript" src="/websys/js/Check.js"></script>
<script language="JavaScript" src="/websett/js/SettCheck.js"></script>
<script language="JavaScript" src="/websett/js/MagnifierSQL.js"></script>

<safety:resources />

<!-- ҳ��ʹ��js�������� -->

<form name="frm" method="post" action="../control/c031.jsp">
	<input name="strSuccessPageURL" type="hidden" value="../view/v032.jsp">
	<input name="strFailPageURL" type="hidden" value="../view/v031.jsp">
	<input name="strAction" type="hidden" value="<%=nextstep%>">
	<input name="officeID" type="hidden" value="<%=lOfficeID%>">
	<input name="currencyID" type="hidden" value="<%=lCurrencyID%>">
	<input name="clientID" type="hidden" value="<%=lClientID%>">
	<input name="transTypeID" type="hidden" value="-1">
<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="24">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title  nowrap><span class="txt_til2">��������������</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
				<br />

				<table width=100% cellspacing="1" class=normal id="table1" align="">
					
					<tr>
						<td width="2%"></td>
						<td width="20%" nowrap>
							���´���
						</td>
						<td colspan=2>
							<%
								String strControlName = "officeID";
									String strID = "ID";
									String strName = "sName";
									String strTable = "office";
									String strCondition = " where nstatusid=1";
									String strProperty = " disabled ";
									long lData = sessionMng.m_lOfficeID;
									SYSHTML.showCommonListControl(out, strControlName, strID,
											strName, strTable, strCondition, strProperty, lData);
							%>

						</td>
					</tr>
					<tr>
						<td width="2%"></td>
						<td width="20%" nowrap>
							ģ��ѡ��
						</td>
						<td colspan=2>
							<%
								strControlName = "moduleID";
									long lSelectValue = -1;
									boolean isNeedAll = false;
									boolean isNeedBlank = false;
									int nType = 1;
									strProperty = "onfocus=nextfield='submitfunction'";
									long[] lArrayID = Constant.ModuleType.getAllCode(
											sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
									Constant.ModuleType.showList(out, strControlName, nType,
											lSelectValue, isNeedAll, isNeedBlank, strProperty,
											lArrayID);
							%>
						</td>
					</tr>
					<tr>
						<td width="2%"></td>
						<td width="20%" nowrap>
							�Ƿ������¼���λ��������
						</td>
						<td colspan=2>
							<select class="select" name="isLowerUnit">
								<option value="<%=OBConstant.IsLowerun.ISNO%>"><%=OBConstant.IsLowerun
								.getName(OBConstant.IsLowerun.ISNO)%></option>
								<option value="<%=OBConstant.IsLowerun.ISYES%>"><%=OBConstant.IsLowerun
								.getName(OBConstant.IsLowerun.ISYES)%></option>
						</td>
					</tr>
					<tr>
						<td colspan=5>
							&nbsp;
						</td>
					</tr>
					<tr>
						<td colspan="4">
							<div align="right">
								<input type="button" name="butNext" value=" ��һ�� "
									class="button1" onClick="frmSubmit(frm)"
									onfocus="nextfield ='submitfunction'">
								&nbsp;&nbsp;&nbsp;&nbsp;
							</div>
						</td>
					</tr>
					<tr>
						<td height="5"></td>
					<tr />
				</table>
				<br>

			</td>
		</tr>

	</table>
</form>

<script language="javascript">

/*��ʼ��ҳ��*/
firstFocus(frm.moduleID);
//setSubmitFunction("frmSubmit(frm);");
setFormName("frm"); 

function frmSubmit(frm)
{  	
	lMoudleType = frm.moduleID.value;	
	
	frm.strSuccessPageURL.value="../view/v032.jsp";
	frm.transTypeID.value = "";
	showSending();
	frm.submit();
}
</script>
<%
	/**
		 * ��ʾ�ļ�β
		 */
		OBHtml.showOBHomeEnd(out);
	} catch (IException ie) {
		//SYSHTML.showExceptionMessage(out,sessionMng,ie,request,response,strTableTitle, Constant.RecordStatus.VALID);
		out.flush();
		return;
	}
%>
<%@ include file="/common/SignValidate.inc"%>