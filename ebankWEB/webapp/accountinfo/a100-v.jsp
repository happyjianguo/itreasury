<%--
/**
 ҳ������ ��a100-v.jsp
 ҳ�湦�� : ÿ���ʽ�䶯����ܱ�����
 ��    �� �� kewen hu
 ��    �� �� 2004-01-17
 ����˵�� ��ʵ�ֲ���˵����
 �޸���ʷ ��
 */
--%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page contentType="text/html;charset=gbk"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<jsp:include flush="true" page="/ShowMessage.jsp" />

<script language="javascript" src="/webob/js/Check.js"></script>
<script language="javascript" src="/webob/js/Control.js"></script>
<script language="javascript" src="/webob/js/date-picker.js"></script>
<script language="javascript" src="/webob/js/DropDownData.js"></script>
<script language="javascript" src="/webob/js/MagnifierSQL.js"></script>
<safety:resources />

<% String strContext = request.getContextPath();%>
<% System.out.println("����ҳ��:"+strContext);%>
<%
    //�������
	String strTitle = "[ÿ���ʽ�䶯����ܱ�]";
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
        /* ��ʾ�ļ�ͷ */
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);

    // ҳ����Ʊ���
    String sAction = null;
    String sActionResult = Constant.ActionResult.FAIL;
    String strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
    String strFailPageURL = (String)request.getAttribute("strFailPageURL");
    String strQueryDate = DataFormat.getDateString(Env.
        getSystemDate(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID));
	long lClientID = sessionMng.m_lClientID;

%>
<form name='frmQry' method='post' action='a100-v.jsp'>
        <input name="strAction" type="hidden" value="">
        <input type="hidden" name="strSuccessPageURL" value="">
        <input type="hidden" name="strFailPageURL" value="">

	<table width="730" border="0" cellspacing="0" cellpadding="0"  class = top>
        <tr > 
          <td colspan="4" height="1" class=FormTitle >ÿ���ʽ�䶯����ܱ�����</td>
        </tr>
      </table>
        
      <table width="730" border="0" cellspacing="0" cellpadding="0"  class = top>
<%
		//if (lClientID == 1 || lClientID == 2 || lClientID == 3 || lClientID == 20 || lClientID == 21) {
%>		
<!--
        <TR>
          <TD height=20 width="20%" class="graytext">��ѯ���ڣ�</td>
          <td width="40%" >
            <input type="text" name="queryDate" size="20" class="graytext" 
            onFocus="nextfield ='Submitv00201';" value="<%=strQueryDate%>">&nbsp;
            <A href="javascript:show_calendar('frmQry.queryDate');" 
            onMouseOut="window.status='';return true;" 
            onMouseOver="window.status='Date Picker';return true;">
            <IMG border="0" height="16" src="/websett/calendar.gif" width="17"></A>
          </TD>
        </TR>
        <tr> 
          <TD height=20 width="20%" nowrap class="graytext">�ɷݹ�˾ÿ���ʽ�䶯�����</td>
          <td height="38" colspan='3'> 
            <div align="right"> 
              <input type="button" name="Submitv00201" value=" �� �� " class="button" onClick="doSearch1();">
            </div>
          </td>
        </tr>
        <tr> 
          <TD height=20 width="20%" nowrap class="graytext">�ɷݹ�˾���ܵ糧ÿ���ʽ�䶯�����</td>
          <td height="38" colspan='3'> 
            <div align="right"> 
              <input type="button" name="Submitv00202" value=" �� �� " class="button" onClick="doSearch2();">
            </div>
          </td>
        </tr>
        <tr> 
          <TD height=20 width="20%" nowrap class="graytext">���Ź�˾�������糧���ÿ�ձ䶯�����</td>
          <td height="38" colspan='3'> 
            <div align="right"> 
              <input type="button" name="Submitv00203" value=" �� �� " class="button" onClick="doSearch3();">
            </div>
          </td>
        </tr>
    -->    
		<TR class="MsoNormal">
			<td height='10' colspan='4' class="MsoNormal"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</TR>        
        <TR class="MsoNormal">
                    <TD height=20 width="6%" nowrap class="MsoNormal">&nbsp;&nbsp;��ѯ���� �ɣ�</TD>
                    <TD height=20 width="20%" nowrap class="MsoNormal">
                  		<fs_c:calendar 
			          	    name="queryDateFrom"
				          	value="" 
				          	properties="nextfield ='queryDateTo'" 
				          	size="20"/>
				<script>
	          		$('#queryDateFrom').val('<%=strQueryDate%>');
	          	</script>
                 	</TD>
                    <TD height=20 width="6%" nowrap class="MsoNormal">����</TD> 
                    <TD height=20 width="20%" nowrap class="MsoNormal">
                    	<fs:calendar 
			          	    name="queryDateTo"
				          	value="" 
				          	properties="nextfield ='Submitv00204'" 
				          	size="20"/>
				    <script>
	          		$('#queryDateTo').val('<%=strQueryDate%>');
	          	</script>
                    </TD>
        </TR>
        <tr class="MsoNormal"> 
          <TD height=20 width="20%" nowrap class="MsoNormal">&nbsp;&nbsp;����Ҵ���ܱ�</td>
          <td height="38" colspan='3'> 
            <div align="right"> 
              <input type="button" name="Submitv00204" value=" �� �� " class="button" onClick="doSearch4();">
			  &nbsp;&nbsp;&nbsp;&nbsp;
            </div>
          </td>
        </tr>
<%
		//} else {
%>
<!--
<SCRIPT language="JavaScript">
	alert("�Բ�����û�в鿴ÿ���ʽ�䶯����������ܱ����Ȩ�ޣ�");
</SCRIPT>
-->
<%
		//}
%>
      </table>
      </form>
<SCRIPT language = "JavaScript">
	function checkDate1() {
        var queryDate = frmQry.queryDate.value;
        if (queryDate != "") {
            if(chkdate(queryDate) == 0) {
                alert("��������ȷ������");
                frmQry.queryDate.focus();
                return false;
            }
        }
		return true;
	}
	function checkDate2() {
        var queryDateFrom = frmQry.queryDateFrom.value;
        var queryDateTo = frmQry.queryDateTo.value;
        if (queryDateFrom != "") {
            if(chkdate(queryDateFrom) == 0) {
                alert("��������ȷ�Ŀ�ʼ����");
                frmQry.queryDateFrom.focus();
                return false;
            }
        }
        if (queryDateTo != "") {
            if(chkdate(queryDateTo) == 0) {
                alert("��������ȷ�Ľ�������");
                frmQry.queryDateTo.focus();
                return false;
            }
        }
        if ((queryDateFrom != "") && (queryDateTo != "")) {
            if (!CompareDate(frmQry.queryDateFrom, frmQry.queryDateTo, "��ʼ���ڲ��ܴ��ڽ�������")) {
                return false;
            }
        }
		return true;
	}
    function doSearch1() {
		if (checkDate1()) {
            document.frmQry.target='blank_';
            document.frmQry.strAction.value='<%=Constant.PageControl.LISTALL%>';
            document.frmQry.action = "<%=strContext%>/accountinfo/a101-v.jsp";
            document.frmQry.strSuccessPageURL.value = "/accountinfo/a101-v.jsp";
            document.frmQry.strFailPageURL.value = "/accountinfo/a100-v.jsp";
            document.frmQry.submit();
		}
    }
    function doSearch2() {
		if (checkDate1()) {
            document.frmQry.target='blank_';
            document.frmQry.strAction.value='<%=Constant.PageControl.LISTALL%>';
            document.frmQry.action = "<%=strContext%>/accountinfo/a102-v.jsp";
            document.frmQry.strSuccessPageURL.value = "/accountinfo/a102-v.jsp";
            document.frmQry.strFailPageURL.value = "/accountinfo/a100-v.jsp";
            document.frmQry.submit();
		}
    }
    function doSearch3() {
		if (checkDate1()) {
            document.frmQry.target='blank_';
            document.frmQry.strAction.value='<%=Constant.PageControl.LISTALL%>';
            document.frmQry.action = "<%=strContext%>/accountinfo/a103-v.jsp";
            document.frmQry.strSuccessPageURL.value = "/accountinfo/a103-v.jsp";
            document.frmQry.strFailPageURL.value = "/accountinfo/a100-v.jsp";
            document.frmQry.submit();
		}
    }
    function doSearch4() {
		if (checkDate2()) {
            document.frmQry.target='blank_';
            document.frmQry.strAction.value='<%=Constant.PageControl.LISTALL%>';
            document.frmQry.action = "<%=strContext%>/accountinfo/a104-v.jsp";
            document.frmQry.strSuccessPageURL.value = "/accountinfo/a104-v.jsp";
            document.frmQry.strFailPageURL.value = "/accountinfo/a100-v.jsp";
            document.frmQry.submit();
		}
    }
</SCRIPT>
<%
    } catch(IException ie) {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    }
    OBHtml.showOBHomeEnd(out);
%>

<%@ include file="/common/SignValidate.inc" %>