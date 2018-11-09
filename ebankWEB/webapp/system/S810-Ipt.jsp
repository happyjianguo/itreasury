<%--
/**
 * �������ƣ�S810-Ipt.jsp
 * ����˵����Ʊ�ݲ�ѯ���롢���ҳ��
 * �������ߣ�����
 * ������ڣ�2003��8��22��
 */
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ page import="java.sql.*,
				 java.util.*,
				 com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.settlement.util.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.*,
				 com.iss.itreasury.ebank.obsystem.dao.*"
%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%
    response.setHeader("Cache-Control","no-stored");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader("Expires",0);
%>
<!--Header end-->

<% String strContext = request.getContextPath();%>
<jsp:include flush="true" page="/ShowMessage.jsp" />
<%!
	/* ����̶����� */
	String strTitle = "[Ʊ�ݲ�ѯ]";
%>

<%
	/* ��ʼ����ѯ����� */
	List lstQuery = null;
	ListIterator listIterator = null;
	/* ��ȡ�û�������Ϣ����Ӧ���� */
	QueryVoucherInfo queryVoucherInfo = null; // �û���Ϣ������
	//String strPayerBankNo = ""; // �����˺�
	long lType = -1; // Ʊ������
	String strStartSubmit = ""; // ��������-��
	String strEndSubmit = ""; // ��������-��
	String strStartVoucherNo = "-1"; // Ʊ�ݺ�-��
	String strEndVoucherNo = "-1"; // Ʊ�ݺ�-��

	/* �û���¼�����Ȩ��У�鼰�ļ�ͷ��ʾ */
    try 
	{
        /* �û���¼��� */
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        // �ж��û��Ƿ���Ȩ�� 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
        	out.flush();
        	return;
        }

		/* �������л�ȡ��Ϣ */
		lstQuery = (List) request.getAttribute("cltQvf");
		if (lstQuery != null)
		{
			listIterator = lstQuery.listIterator();
		}
		queryVoucherInfo = (QueryVoucherInfo) request.getAttribute("queryVoucherInfo");
		if (queryVoucherInfo != null)
		{
			//strPayerBankNo = (String)request.getAttribute("sPayerBankNo");
			lType = queryVoucherInfo.getTypeID();
			strStartSubmit = queryVoucherInfo.getStartDate();
			strEndSubmit = queryVoucherInfo.getEndDate();
			strStartVoucherNo = String.valueOf(queryVoucherInfo.getStartVoucherNo() );
			strEndVoucherNo = String.valueOf(queryVoucherInfo.getEndVoucherNo() );
		}
		
		//����Ĭ��Ϊϵͳʱ��
		if ((strStartSubmit != null) && strStartSubmit.equals(""))
		{
			strStartSubmit = DataFormat.getDateString();
		}
		if ((strEndSubmit != null) && strEndSubmit.equals(""))
		{
			strEndSubmit = DataFormat.getDateString();
		}
        /**
         * presentation start
         */
        /* ��ʾ�ļ�ͷ */
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
	} 
	catch (IException ie) 
	{
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<safety:resources />

<form method="post" name="frmpzcx">
	  <br>
	  <table width="730" align="center" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#456795">
          <td bgcolor="#456795" width="5" valign="top" align="left" height="25"><img src="\webob\graphics\blue_top_left.gif" width="3" height="3"></td>
          <td width="560"height="25" bgcolor="#456795" colspan="2"><font size="3" color="#FFFFFF" class="whitetext">�ͻ�����</font></td>
          <td width="5" valign="top" align="right" height="25">
            <div align="right"></div>
          </td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
          <td width="130" height="25" bgcolor="#C8D7EC" class="graytext">�ͻ����ƣ�</td>
          <td width="430" height="25" bgcolor="#C8D7EC" class="box">
            <input type="text" class="rebox" name="sPayerName" size="30" value="<%= sessionMng.m_strClientName %>" >
          </td>
          <td width="5" height="25"></td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
		  <td width="130" height="25" bgcolor="#C8D7EC" class="graytext">Ʊ�����ͣ�</td>
		  <td width="430" height="25" bgcolor="#C8D7EC" class="box">
<% 			
			OBHtmlCom.showBillTypeControl(out,"lType",lType," onfocus=\"nextfield ='sStartSubmit';\""); //����Ʊ������ѡ���
%>
		  </td>	 
          <td width="5"></td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>
      </table>
      <table width="730" align="center" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
          <td width="110" height="25" class="graytext" bgcolor="#C8D7EC">�������ڣ�</td>
          <td width="20" height="25" class="graytext" bgcolor="#C8D7EC">
            <div align="right">��</div>
          </td>
          <td width="130" height="25" class="box">
          <fs_c:calendar 
	        	name="sStartSubmit"
	          	value="" 
	          	properties="nextfield ='sEndSubmit'" 
	          	size="12"/>
	          	 <script>
	          		$('#sStartSubmit').val('<%=((strStartSubmit != null) && !strStartSubmit.equals("")) ? strStartSubmit : "" %>');
	          	</script>
		  </td>
		  <td width="300" height="25" class="box">
			<span class="graytext">��</span>
			  <fs_c:calendar 
	         	    name="sEndSubmit"
		          	value="" 
		          	properties="nextfield ='sStartVoucherNo'" 
		          	size="12"/>
		          	 <script>
	          		$('#sEndSubmit').val('<%=((strEndSubmit != null) && !strEndSubmit.equals("")) ? strEndSubmit : "" %>');
	          	</script>
          </td>
          <td width="5"></td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="6" height="1"></td>
        </tr>
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
          
      <td width="110" height="25" class="graytext" bgcolor="#C8D7EC">Ʊ�ݺţ�</td>
          <td width="20" height="25" class="graytext" bgcolor="#C8D7EC">
            <div align="right">��</div>
          </td>
          <td width="130" height="25" class="box">
            <input type="text" name="sStartVoucherNo" value="<%= strStartVoucherNo.equals("-1") ? "" : strStartVoucherNo %>" size="30" onfocus="nextfield ='sEndVoucherNo';">
		  </td>
		  <td width="300" height="25" class="box">
            	<span class="graytext"> �� </span>
            <input type="text" name="sEndVoucherNo" value="<%= strEndVoucherNo.equals("-1") ? "" : strEndVoucherNo %>" size="30" onfocus="nextfield ='';">
          </td>
          <td width="5"></td>
        </tr>
      </table>
      <br>
      <table width="730" align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="376">
            <div align="right"></div>
          </td>
          <td width="134">
            <div align="right"></div>
          </td>
          <td width="60">
            <div align="right"><img src="\webob\graphics\button_chazhao.gif" width="46" height="18" border="0" onclick="javascript:queryme();"></div>
          </td>
        </tr>
      </table>
	  <br>

<% 
	if (lstQuery == null) { // ��ѯΪ�գ���ʾһ���ռ�¼
%>
	<table width="730" border="0" cellspacing="0" cellpadding="0" align="center">
      <tr bgcolor="#FFFFFF">
        <td colspan="4" height="1"></td>
      </tr>
      <tr bgcolor="#C8D7EC">
        <td bgcolor="#0C3869" width="5" valign="top" align="left" height="22"><img src="\webob\graphics\blue_top_left.gif" width="3" height="3"></td>
        
      <td colspan="2"height="22" bgcolor="#0C3869"><b><font size="3" color="#FFFFFF" class="whitetext">Ʊ�ݿ������</font></b></td>
        <td width="5" height="22" bgcolor="#0C3869"></td>
      </tr>
      <tr bgcolor="#FFFFFF">
        <td colspan="4" height="1"></td>
      </tr>
    </table>
    <table width="570" border="0" cellspacing="0" cellpadding="0">
      <tr bgcolor="#FFFFFF">
        <td colspan="4" height="1"></td>
      </tr>
    </table>
    <table width="730" border="0" align="center" height="51" class="ItemList">
      <tr>
        <td class="whitetext" width="120" height="14" bgcolor="#456795">
          
        <div align="center">Ʊ�ݺ�</div>
        </td>
        <td class="whitetext" width="80" height="14" bgcolor="#456795">
          <div align="center">״̬</div>
        </td>
        <td class="whitetext" width="100" height="14" bgcolor="#456795">
          <div align="center">��������</div>
        </td>
      </tr>
      <tr bgcolor="#C8D7EC">
        <td class="graytext" width="120" height="14">
          <div align="center"></div>
        </td>
        <td class="graytext" width="80" height="14">
          <div align="center"></div>
        </td>
        <td class="graytext" width="100" height="14">
          <div align="center"></div>
        </td>
      </tr>
    </table>
<% 
	}
%>

<%
	/**
	 * ��ѯ�����Ϊ��
	 */
	if (lstQuery != null)
	{
%>

      <table width="730" border="0" cellspacing="0" cellpadding="0" align="center">
        <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>
        <tr bgcolor="#C8D7EC">
          <td bgcolor="#0C3869" width="5" valign="top" align="left" height="22"><img src="\webob\graphics\blue_top_left.gif" width="3" height="3"></td>
          
      <td colspan="2"height="22" bgcolor="#0C3869"><b><font size="3" color="#FFFFFF" class="whitetext">Ʊ�ݿ������</font></b></td>
          <td width="5" height="22" bgcolor="#0C3869"></td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>
      </table>
      <table width="730" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>
      </table>
      <table width="730" border="0" align="center" height="51" class="ItemList">
        <tr>
          <td class="whitetext" width="120" height="14" bgcolor="#456795">
            
        <div align="center">Ʊ�ݺ�</div>
          </td>
          <td class="whitetext" width="80" height="14" bgcolor="#456795">
            <div align="center">״̬</div>
          </td>
          <td class="whitetext" width="100" height="14" bgcolor="#456795">
            <div align="center">��������</div>
          </td>
        </tr>

<%
			VoucherInfo voucherInfo = null; // ��ʼ����Ϣ��

			/* ����ʽ��ʾ���м�¼ */
	   		while(listIterator.hasNext())
	   		{
				voucherInfo = (VoucherInfo)listIterator.next(); // ��ȡ��һ����¼��Ϣ
%>
        <tr bgcolor="#C8D7EC">
          <td class="graytext" width="120" height="14">
            <div align="center"><%=voucherInfo.getVoucherNo() /*Ʊ�ݺ�*/%></div>
          </td>
          <td class="graytext" width="80" height="14">
            <div align="center"><%= voucherInfo.getStatus() /*״̬*/%></div>
          </td>
          <td class="graytext" width="100" height="14">
            <div align="center"><%= voucherInfo.getDate() /*����*/%></div>
          </td>
        </tr>
<% 
			} 
%>
      </table>
<% 
	} 
%>
	 <br>
</form>
<!--presentation end-->

<script language="Javascript">

	/*
	 * ����У�鼰FORM�ύ
	 * javascript
	 */

    /* ��ѯ������ */
    function queryme()
    {
        frmpzcx.action = "<%=strContext%>/system/S811-Ctr.jsp";
		if (validate() == true)
        {
            frmpzcx.submit();
        }
    }

    /* У�麯�� */
    function validate()
    {
       /**
        * ��������������ϣ�return ture
        * ���У�����return false
        */

		/* Ʊ�����ͷǿ�У�� */
		

		/*  ��������У�� */
		var starSubmit = frmpzcx.sStartSubmit.value;
		var endSubmit = frmpzcx.sEndSubmit.value;

		if((starSubmit != "") && ( chkdate(starSubmit) == 0 ))
		{
			alert("��������ȷ�����뿪ʼ����");
			frmpzcx.sStartSubmit.focus();
			return false;
		}
		if((endSubmit != "") && ( chkdate(endSubmit) == 0 ))
		{
			alert("��������ȷ�������������");
			frmpzcx.sEndSubmit.focus();
			return false;
		}
		if ((starSubmit != "") && (endSubmit != ""))
		{	if (!CompareDate(frmpzcx.sStartSubmit, frmpzcx.sEndSubmit, "��ʼ���ڲ��ܴ��ڽ�������"))
			{
				return false;
			}
		}
		/* Ʊ�ݺ�У�� */
		if (isNaN(frmpzcx.sStartVoucherNo.value))
	    {
			alert("Ʊ�ݺ�ֻ������ֵ");
			frmpzcx.sStartVoucherNo.focus();
			return (false);
	    }
		if (isNaN(frmpzcx.sEndVoucherNo.value))
	    {
			alert("Ʊ�ݺ�ֻ������ֵ");
			frmpzcx.sEndVoucherNo.focus();
			return (false);
	    }
		if ((frmpzcx.sStartVoucherNo.value != "") && (frmpzcx.sEndVoucherNo.value != ""))
		{
			if (parseFloat(frmpzcx.sStartVoucherNo.value) > parseFloat(frmpzcx.sEndVoucherNo.value))
			{
				alert("��ʼƱ�ݺŲ��ܴ��ڽ���Ʊ�ݺ�");
				return false;
			}
		}

    	return true;
    }

</script>


<script language="javascript">
	/* ҳ�潹�㼰�س����� */
	firstFocus(frmpzcx.lType);
	setSubmitFunction("queryme(frmpzcx)");
	setFormName("frmpzcx");
</script>

<%
	/* ��ʾ�ļ�β */
	OBHtml.showOBHomeEnd(out);
%>

<%@ include file="/common/SignValidate.inc" %>