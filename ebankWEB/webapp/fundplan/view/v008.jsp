<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%--
/*
 * �������ƣ�v008.jsp     �ʽ�ƻ������ҡ�������>��ǰҳ�桪������>c008.jsp
 * ����˵�����ʽ�ƻ�-��ѯ���ҳ��
 * �������ߣ���Ӣ��
 * ������ڣ�200����1���£���
 */
--%>

<%@ page contentType="text/html;charset=gbk"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom,com.iss.itreasury.util.DataFormat,java.util.Date"%>
<%@ page import="com.iss.itreasury.ebank.util.OBMagnifier,java.net.URLEncoder,com.iss.itreasury.ebank.fundplan.dataentity.CapitalPlanCondition" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<jsp:include page="/ShowMessage.jsp"/>


<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<script language="javascript" src="/webob/js/Check.js"></script>
<script language="javascript" src="/webob/js/Control.js"></script>
<script language="javascript" src="/webob/js/date-picker.js"></script>
<script language="javascript" src="/webob/js/DropDownData.js"></script>
<script language="javascript" src="/webob/js/MagnifierSQL.js"></script>
<jsp:include flush="true" page="/ShowMessage.jsp" />

<script type="text/javascript" src="/webob/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="/webob/js/flexigrid.js"></script>
<script type="text/javascript" src="/webob/js/flexigridEncapsulation.js"></script>
<script type="text/javascript" src="/webob/js/suggest.js"></script>
<script type="text/javascript" src="/webob/js/jquery-ui-1.7.2.custom.min.js"></script>
<link rel="stylesheet" href="/webob/css/jquery-ui-1.7.2.custom.css" type="text/css">
<link rel="stylesheet" href="/webob/css/suggest.css" type="text/css">
<link rel="stylesheet" href="/webob/css/flexigrid.css" type="text/css">
<safety:resources />
<head>
	<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
</head>

<%
	//�������
	String strTitle = null;
	try {
		/* �û���¼��� */
		if (sessionMng.isLogin() == false) {
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,
			"Gen_E002");
			out.flush();
			return;
		}

		/* �ж��û��Ƿ���Ȩ�� */
		if (sessionMng.hasRight(request) == false) {
			out.println(sessionMng.hasRight(request));
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,
			"Gen_E003");
			out.flush();
			return;
		}

		//��ʾ�ļ�ͷ
		OBHtml.showOBHomeHead(out, sessionMng, strTitle,
		OBConstant.ShowMenu.YES);
		String strContext = request.getContextPath();
%>
<form name="frm_fund_plan" method="post" action="">
	<!-- �ɹ���ʧ��ҳ�� -->
	<input name="strSuccessPageURL" type="hidden" value="<%=strContext %>/fundplan/view/v009.jsp">
	<input name="strFailPageURL" type="hidden" value="<%=strContext %>/fundplan/view/v008.jsp">	
	<input type="hidden" name="clientId" value="<%=sessionMng.m_lClientID%>">
	<input type="hidden" name="currencyId" value="<%=sessionMng.m_lCurrencyID%>">
	<input type="hidden" name="officeId" value="<%=sessionMng.m_lOfficeID%>">
	<input type="hidden" name="cpCode" value="">
<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="24">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">�ʽ�ƻ���ѯ</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
	<br/>
<%
	
	String startdate = "";       //�ύ����-��
	String enddate = "";         //�ύ����-��
	long statusId = -1;              //״̬
	// Date _date = Env.getSystemDateTime();//Ӧ�������ݿ�ʱ��
       //����һ
	startdate = DataFormat.formatDate(DataFormat.getFirstDateOfNextWeek(Env.getSystemDateTime()));
   
    //����������
 	enddate = DataFormat.formatDate(DataFormat.getNextDate(DataFormat.getFirstDateOfNextWeek(Env.getSystemDateTime()),4));
 	
 %>	
 <% 

		
		long lOfficeID = sessionMng.m_lOfficeID;
		long lCurrencyID = sessionMng.m_lCurrencyID;
		String strFormName = "frm_fund_plan";
		String strCtrlName = "cPIDStart";           //�Ŵ󾵿ؼ������ƣ�FundPlan+Ctrl
		String strTitle2 = "�ʽ�ƻ�";             //�Ŵ󾵿ؼ�ͼ��ǰ��label����
		long lClientID = sessionMng.m_lClientID;
		String strFirstTD = "";	   //��һ�������
		String strSecondTD = " ";	   //�ڶ��������
		String[] strNextControls = {"cPIDEndCtrl"};  //��һ��������ȡ�����ҳ��Ԫ�ص�����
		//String strRtnClientNameCtrl = "FundPlanID";          //����ֵ���ƻ���ţ���Ӧ�Ŀؼ���
		/*OBMagnifier.createFundPlanCtrl(
		     out,
			 lOfficeID,
		     lCurrencyID,
		     strFormName,
		     strCtrlName,
		     strTitle2,
			 lClientID,
		     strFirstTD,
		     strSecondTD,
		     strNextControls
		);*/
		
%>	
	<table width=100% border="0" cellspacing="0" cellpadding="0" class=normal >
		<TR><TD height="5" colspan="7"></TD></TR>
		<TR>						  											
			<td width="5" height="25"></td>
			<td width="130" height="25" align="left">�ʽ�ƻ���</td>
							  	 <td>
							
									<fs_c:dic id="cPIDStartCtrl" size="22" form="frm_fund_plan" title="�ʽ�ƻ�" sqlFunction="getFundPlanSQL"  sqlParams='frm_fund_plan.cPIDStartCtrl.value,frm_fund_plan.currencyId.value,frm_fund_plan.clientId.value,frm_fund_plan.officeId.value' value="" nextFocus="cPIDEndCtrl" width="500">
										<fs_c:columns> 
											<fs_c:column display="���" name="id" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
											<fs_c:column display="�ʽ�ƻ����" name="cpcode" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
									</fs_c:columns>
										<fs_c:pageElements>
											<fs_c:pageElement elName="cPIDStartCtrl" name="cpcode" type="<%=PagerTypeConstant.STRING %>" elType="text" />
											<fs_c:pageElement elName="cPIDStart" name="id" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
										</fs_c:pageElements>							
									</fs_c:dic> 
							     </td>			
	  <% 
		long lOfficeID2 = sessionMng.m_lOfficeID;
		long lCurrencyID2 = sessionMng.m_lCurrencyID;
		String strFormName2 = "frm_fund_plan";
		String strCtrlName2 = "cPIDEnd";           //�Ŵ󾵿ؼ������ƣ�FundPlan+Ctrl
		String strTitle22 = "��";             //�Ŵ󾵿ؼ�ͼ��ǰ��label����
		long lClientID2 = sessionMng.m_lClientID;
		String strFirstTD2 = "";	   //��һ�������
		String strSecondTD2 = " ";	   //�ڶ��������
		String[] strNextControls2 = {"startdate"};  //��һ��������ȡ�����ҳ��Ԫ�ص�����
	/*	OBMagnifier.createFundPlanCtrl(
		     out,
			 lOfficeID2,
		     lCurrencyID2,
		     strFormName2,
		     strCtrlName2,
		     strTitle22,
			 lClientID2,
		     strFirstTD2,
		     strSecondTD2,
		     strNextControls2
		);*/
%>	
<td width="130" height="25" align="left">����</td>
							  	 <td>
							
										
									<fs_c:dic id="cPIDEndCtrl" size="22" form="frm_fund_plan" title="�ʽ�ƻ�" sqlFunction="getFundPlanSQL"  sqlParams='frm_fund_plan.cPIDEndCtrl.value,frm_fund_plan.currencyId.value,frm_fund_plan.clientId.value,frm_fund_plan.officeId.value' value="" nextFocus="startdate" width="500">
										<fs_c:columns> 
											<fs_c:column display="���" name="id" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
											<fs_c:column display="�ʽ�ƻ����" name="cpcode" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
									</fs_c:columns>
										<fs_c:pageElements>
											<fs_c:pageElement elName="cPIDEndCtrl" name="cpcode" type="<%=PagerTypeConstant.STRING %>" elType="text" />
											<fs_c:pageElement elName="cPIDEnd" name="id" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
										</fs_c:pageElements>							
									</fs_c:dic> 
							     </td>			
      
	    <td width="8" height="25"></td>
	    <td width="8"></td>																													
   </TR>	
		
		<tr id="submitDate">
			<td width="5" height="25"></td>
			<td height="25" class="graytext">�ƻ����ڣ� ��</td>
			<td width="188" height="25" class="graytext">
			<fs_c:calendar 
          	    name="startdate"
	          	value="" 
	          	properties="nextfield ='enddate'" 
	          	size="18"/>
	         <script>
	          		$('#startdate').val('<%=startdate%>');
	          	</script>
			</td>
			<td width="20" height="25" class="graytext" align="right"><span class="graytext">����</span></td>
			<td width="330" height="25" class="graytext">
				<fs_c:calendar 
	          	    name="enddate"
		          	value="" 
		          	properties="nextfield ='statusId'" 
		          	size="18"/>
		         <script>
	          		$('#enddate').val('<%=enddate%>');
	          	</script>
			</td>
			<td width="8"></td>
			<td width="5" height="25" colspan="1"  class="MsoNormal"></td>
		</tr>
		<tr>
			<td width="5" height="25"></td>
			<td height="25" class="graytext" >
				״̬��
			</td>
			<td height="25" class="graytext" colspan="4">
			<%
				//״̬
				OBHtmlCom.showQueryStatusListControlForfundPlan(out, "statusId", statusId,"","btnFind");
			%>
			</td>
			<td width="8" height="25"></td>
		</tr>
		<tr>
			<td colspan="7">
				<div align="right">
					<input type="Button" class="button1" name="btnFind" onkeydown="if(event.keyCode==13) document.frm_fund_plan.btnFind.click()" value=" �� �� " width="46"  height="18" onclick="javascript:toFind()" onfocus="nextfield ='submitfunction'">&nbsp;&nbsp;&nbsp;&nbsp;
				</div>
			</td>
		</tr>
		<TR><TD height="5" colspan="7"></TD></TR>
	</table>
   </td>
  </tr>
 </table>
</form>

<script language="javascript">
    /* ҳ�潹�㼰�س����� */
   firstFocus(frm_fund_plan.cPIDStartCtrl);
   setFormName("frm_fund_plan");
   ////setSubmitFunction("toFind()");
   function toFind()
	{
	   if (validate()) 
	   {
		   frm_fund_plan.action ="../control/c008.jsp";
		   showSending();
		   frm_fund_plan.submit();
		}
	}

    /* У�麯�� */
    function validate() 
    {
		var strStartDate = frm_fund_plan.startdate.value;
        var strEndDate = frm_fund_plan.enddate.value;
        if (strStartDate != "") {
           if(chkdate(strStartDate) == 0) {
               alert("��������ȷ���걨�ύ��ʼ����");
               frm_fund_plan.startdate.focus();
               return false;
           }
        }
        if (strEndDate != "") {
            if(chkdate(strEndDate) == 0) {
                alert("��������ȷ���걨�ύ��������");
                frm_fund_plan.enddate.focus();
                return false;
            }
        }
        if ((strStartDate != "") && (strEndDate != "")) {
            if (!CompareDate(frm_fund_plan.startdate, frm_fund_plan.enddate, 
                "�ύ���ڣ���ʼ���ڲ��ܴ��ڽ�������")) {
                return false;
            }
        }
		return true;	
    }
</script>

<%
		/* ��ʾ�ļ�β */
		OBHtml.showOBHomeEnd(out);
	} catch (IException ie) {
		OBHtml.showExceptionMessage(out, sessionMng, ie, strTitle, "",1);
	}
%>
<%@ include file="/common/SignValidate.inc" %>