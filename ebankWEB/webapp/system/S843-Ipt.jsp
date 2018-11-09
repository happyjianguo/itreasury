<%--
/*
 * �������ƣ�S843-Ipt.jsp
 * ����˵����ǩ�Ͻ���������롢���ҳ��
 * �������ߣ�����
 * ������ڣ�2003��8��26��
 */
--%>

<%@ page contentType = "text/html;charset=gbk"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ page import="java.sql.*,
				 java.util.*,
				 com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.*,
                 com.iss.itreasury.ebank.obsystem.dao.*"
%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<%
    response.setHeader("Cache-Control","no-stored");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader("Expires",0);
%>
<% String strContext = request.getContextPath();%>

<%!
	/* ����̶����� */
	String strTitle = "ǩ�Ͻ������";
%>

<%
	/* ��ʼ���û���Ϣ������ */
	SignAmountInfo signAmountInfo = null;
	/* ��ȡ��Ӧ��Ϣ */
	String sAmount1 = "0.00"; // ���һ
    String sAmount2 = "0.00"; // ����
    String sAmount3 = "0.00"; // �����
    long lSignUserID1 = -1; // ָ��ǩ����һ
    long lSignUserID2 = -1; // ָ��ǩ���˶�
    long lSignUserID3 = -1; // ָ��ǩ������

	/* �û���¼�����Ȩ��У�鼰�ļ�ͷ��ʾ */
    try 
	{
        /* �û���¼��� */
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E002");
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
		signAmountInfo = (SignAmountInfo) request.getAttribute("signAmountInfo");
		if (signAmountInfo != null)
		{
			sAmount1 = signAmountInfo.getFormatAmount1(); // ���һ
			Log.print("sAmount1=========" + sAmount1);
    		sAmount2 = signAmountInfo.getFormatAmount2(); // ����
		    sAmount3 = signAmountInfo.getFormatAmount3(); // �����
		    lSignUserID1 = signAmountInfo.getSignUserID1(); // ָ��ǩ����һ
		    lSignUserID2 = signAmountInfo.getSignUserID2(); // ָ��ǩ���˶�
		    lSignUserID3 = signAmountInfo.getSignUserID3(); // ָ��ǩ������
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

<script language="JavaScript" src="/webob/js/date-picker.js"></script>

<form method="post" name="frmqrjesz">
	
    <table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">ǩ�Ͻ������</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
  
	<br>
	 <table width=100% border="0" cellspacing="0" cellpadding="0" align="">
      <tr>
        <td>
        	<table width="110" border="0" cellspacing="0" cellpadding="0">
          
	          <tr>
				<td width="3"><a class=lab_title1></td>
				<td class="lab_title2"> ǩ�Ͻ��һ</td>
				<td width="17"><a class=lab_title3></td>
			</tr>
        	</table>
        </td>
      </tr>
    </table>
      <table width=100% border="0" cellspacing="0" cellpadding="0" class = "normal" align="">
        <tr >
          <td colspan="6" height="1"></td>
        </tr>
        <tr >
          <td width="2" height="58"></td>
          <td width="120" height="58" class="graytext"  nowrap><span class="graytext"><font color="red">*&nbsp;</font>��(���ڵ���)</span></td>
          <td width="20" height="58">
            <div align="right" class="graytext"><span class="graytext"><%= sessionMng.m_strCurrencySymbol %></span></div>
          </td>
          <td height="58" width="172" nowrap>  <span class="graytext">
           	<script  language="JavaScript">
				createAmountCtrl("frmqrjesz","sAmount1","<%= sAmount1 %>","nSignUserID1","",<%= sessionMng.m_lCurrencyID%>);
			</script>
            </span>Ԫ&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
            
          <td height="58" width="80" class="graytext" nowrap><font color="red">*&nbsp;</font>ָ��ǩ���ˣ� </td>
          <td><span class="graytext">
<% 			
			OBHtmlCom.showSignUserControl(out, "nSignUserID1",lSignUserID1,"onfocus=\"nextfield ='sAmount2';\"", sessionMng.m_lClientID, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,sessionMng.m_lUserID); 
%>
            </span></td>
        </tr>
      </table>
	<br>
	 <table width=100% border="0" cellspacing="0" cellpadding="0" align="">
      <tr>
        <td>
        	<table width="110" border="0" cellspacing="0" cellpadding="0">

	     		<tr>
					<td width="3"><a class=lab_title1></td>
					<td class="lab_title2"> ǩ�Ͻ���</td>
					<td width="17"><a class=lab_title3></td>
				</tr>
        	</table>
       	</td>
      </tr>
    </table>
      <table width=100% border="0" cellspacing="0" cellpadding="0" class = "normal" align="">
        <tr >
          <td colspan="5" height="1"></td>
        </tr>
        <tr >
          <td width="2" height="58"></td>
          <td width="120" height="58" class="graytext" nowrap><span class="graytext">
            <font color="red">*&nbsp;</font>��(���ڵ���) </span></td>
          <td width="20" height="58">
            <div align="right" class="graytext"><span class="graytext"><%= sessionMng.m_strCurrencySymbol %></span></div>
          </td>
          <td height="58" width="172" nowrap>  <span class="graytext">
            <script  language="JavaScript">
				createAmountCtrl("frmqrjesz","sAmount2","<%= sAmount2 %>","nSignUserID2","",<%= sessionMng.m_lCurrencyID%>);
			</script>
            </span>Ԫ&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
          <td height="58" width="80" class="graytext" nowrap><font color="red">*&nbsp;</font>ָ��ǩ���ˣ� </td>
          <td> <span class="graytext">
<% 		
			OBHtmlCom.showSignUserControl(out, "nSignUserID2",lSignUserID2,"onfocus=\"nextfield ='sAmount3';\"", sessionMng.m_lClientID, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,sessionMng.m_lUserID); 
%>
            </span></td>
        </tr>
      </table>
	 <table width=100% border="0" cellspacing="0" cellpadding="0" align="">
      <tr>
        <td>&nbsp;</td>
      </tr>
      
      <tr>
        <td>
        	<table width="110" border="0" cellspacing="0" cellpadding="0">
             	<tr>
					<td width="3"><a class=lab_title1></td>
					<td class="lab_title2"> ǩ�Ͻ����</td>
					<td width="17"><a class=lab_title3></td>
				</tr>
        	</table>
        </td>
      </tr>
    </table>
      <table width=100% border="0" cellspacing="0" cellpadding="0" class = "normal" align="">
        <tr >
          <td colspan="5" height="1"></td>
        </tr>
        <tr >
          <td width="2" height="58"></td>
          <td width="120" height="58" class="graytext" nowrap><span class="graytext"><font color="red">*&nbsp;</font>��(���ڵ���)</span></td>
          <td width="20" height="58">
            <div align="right" class="graytext"><span class="graytext"><%= sessionMng.m_strCurrencySymbol %></span></div>
          </td>
          <td height="58" width="172" nowrap>  <span class="graytext">
            <script  language="JavaScript">
				createAmountCtrl("frmqrjesz","sAmount3","<%= sAmount3 %>","nSignUserID3","",<%= sessionMng.m_lCurrencyID%>);
			</script>
            </span>Ԫ&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
          <td height="58" width="80" class="graytext" nowrap><font color="red">*&nbsp;</font>ָ��ǩ���ˣ�  </td>
          <td><span class="graytext">
<% 
			OBHtmlCom.showSignUserControl(out, "nSignUserID3",lSignUserID3,"onfocus=\"nextfield ='';\"", sessionMng.m_lClientID, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,sessionMng.m_lUserID); 
%>
            </span> </td>
        </tr>
      </table>
      <table width=100% border="0" cellspacing="0" cellpadding="0">
        <tr >
          <td colspan="3" height="1"></td>
        </tr>
      </table>
      <br>
	<table width=100% border="0" cellspacing="0" cellpadding="0" align="">
        <tr>
          <td width="605">
            <div align="right"></div>
          </td>
          <td width="63">
            <div align="right">
				<input type="button" name="Submitv00204" value=" �� �� " class="button1" onClick="javascript:submitme();">
			</div>
          </td>
          <td width="62">
            <div align="right">
				<input type="button" name="Submitv00204" value=" �� �� " class="button1" onClick="javascript:cancelme();">
			</div>
          </td>
        </tr>
      </table>
	</td>
  </tr>
</table>
</form>
<!--presentation end-->

<script language="Javascript">

	/*
	 * ����У�鼰FORM�ύ
	 * javascript
	 */

    /* ��ѯ������ */
    function submitme()
    {
		frmqrjesz.action = "<%=strContext%>/system/S845-Ctr.jsp";
		if (validate() == true)
		{
			frmqrjesz.submit();
		}
	
    }

	/* ȡ�������� */
    function cancelme()
    {
    	frmqrjesz.reset();
		//document.all.frmqrjesz.sAmount3.value="";
		//document.all.frmqrjesz.sAmount2.value="";
		//document.all.frmqrjesz.sAmount1.value="";
		//document.all.frmqrjesz.nSignUserID3.value="";
		//document.all.frmqrjesz.nSignUserID2.value="";
		//document.all.frmqrjesz.nSignUserID1.value="";
    }

    /* У�麯�� */
    function validate()
    {
       /**
        * ��������������ϣ�return ture
        * ���У�����return false
        */

		/* ���У�� */
		var amount1 = reverseFormatAmount(frmqrjesz.sAmount1.value);
		var amount2 = reverseFormatAmount(frmqrjesz.sAmount2.value);
		var amount3 = reverseFormatAmount(frmqrjesz.sAmount3.value);
		/* ���һ��
		 * ����ֵ
		 * ���Ϊ�ջ�ֵΪ0���ύ�������������У��
		 * ���ڵ���0��С��1000000000000
		 * ָ��ǩ���˲���Ϊ��
		 */
		if (frmqrjesz.sAmount1.value =="-") 
		{
			alert("���һֻ������ֵ" );
			frmqrjesz.sAmount1.focus();
			return false;
		}
		if (frmqrjesz.sAmount1.value ==",") 
		{
			alert("���һֻ������ֵ" );
			frmqrjesz.sAmount1.focus();
			return false;
		}
		if (frmqrjesz.sAmount2.value =="-") 
		{
			alert("����ֻ������ֵ" );
			frmqrjesz.sAmount2.focus();
			return false;
		}
		if (frmqrjesz.sAmount2.value ==",") 
		{
			alert("����ֻ������ֵ" );
			frmqrjesz.sAmount2.focus();
			return false;
		}
		if (frmqrjesz.sAmount3.value =="-") 
		{
			alert("�����ֻ������ֵ" );
			frmqrjesz.sAmount3.focus();
			return false;
		}
		if (frmqrjesz.sAmount3.value ==",") 
		{
			alert("�����ֻ������ֵ" );
			frmqrjesz.sAmount2.focus();
			return false;
		}
		if (!isFloat(amount1))
	    {
			alert("���һֻ������ֵ" );
			frmqrjesz.sAmount1.focus();
			return false;
	    }
	/*	if (((amount1 == "") || (parseFloat(amount1) == 0.00))&&(frmqrjesz.nSignUserID1.value == -1))
		{
			return true;
		}	
	*/	
		if (((amount1 == "") || (parseFloat(amount1) == 0.00))&&(frmqrjesz.nSignUserID1.value == -1))
		{
			if(((amount2 == "") || (parseFloat(amount2) == 0.00))&&(frmqrjesz.nSignUserID2.value == -1))
			{
				if (((amount3 == "") || (parseFloat(amount3) == 0.00))&&(frmqrjesz.nSignUserID3.value == -1))
				{
					return true;
				}
				alert ("ǩ�Ͻ�������Ϊ��");
				return false;
			}
			alert ("ǩ�Ͻ��һ����Ϊ��");
			return false;
		}
		
		if (((amount1 == "")||parseFloat(amount1) < 0.01 )&& !(frmqrjesz.nSignUserID1.value == -1))
		{
			alert("���һ����С��0.01");
			frmqrjesz.sAmount1.focus();
			return false;
		}
		
		if (amount1.length >= 16)
		{
			alert("���һֻ��С��1,000,000,000,000.00");
			frmqrjesz.sAmount1.focus();
			return false;
		}
		if (frmqrjesz.nSignUserID1.value == -1)
		{
			alert("ָ��ǩ����һ����Ϊ��");
			frmqrjesz.nSignUserID1.focus();
			return false;
		}
		/* ������
		 * ����ֵ
		 * ���Ϊ�ջ�ֵΪ0���ύ�������������У��
		 * ���ڵ���0��С��1000000000000
		 * �������ڽ��һ
		 * ָ��ǩ���˲���Ϊ��
		 */
		 
		if (!isFloat(amount2))
	    {
			alert("����ֻ������ֵ" );
			frmqrjesz.sAmount2.focus();
			return (false);
	    }
	    
		if (((amount2 == "")||parseFloat(amount1) < 0.01 )&& !(frmqrjesz.nSignUserID2.value == -1))
		{
			alert("��������С��0.01");
			frmqrjesz.sAmount2.focus();
			return false;
		}
		
		if (((amount2 == "") || (parseFloat(amount2) == 0.00))&&(frmqrjesz.nSignUserID2.value == -1))
		{
			if (((amount3 == "") || (parseFloat(amount3) == 0.00))&&(frmqrjesz.nSignUserID3.value == -1))
				{
					return true;
				}
				alert ("ǩ�Ͻ�������Ϊ��");
				return false;
		}
	
	/*	if (parseFloat(amount2) < 0.00)
		{
			alert("��������С��0.00");
			frmqrjesz.sAmount2.focus();
			return false;
		}
	*/
		if (amount2.length >= 16)
		{
			alert("����ֻ��С��1,000,000,000,000.00");
			frmqrjesz.sAmount2.focus();
			return false;
		}
		if (parseFloat(amount1) >= parseFloat(amount2))
		{
			alert ("����������ڽ��һ");
			frmqrjesz.sAmount2.focus();
			return false;
		}
		if (frmqrjesz.nSignUserID2.value == -1)
		{
			alert("ָ��ǩ���˶�����Ϊ��");
			frmqrjesz.nSignUserID2.focus();
			return false;
		}
		if (frmqrjesz.nSignUserID1.value == frmqrjesz.nSignUserID2.value)
		{
			alert("���һ�ͽ�����ָ��ǩ���˲�����ͬ");
			frmqrjesz.nSignUserID2.focus();
			return false;
		}
		
		/* �������
		 * ����ֵ
		 * ���Ϊ�ջ�ֵΪ0���ύ�������������У��
		 * ���ڵ���0��С��1000000000000
		 * ��������ڽ���
		 * ָ��ǩ���˲���Ϊ��
		 */
		if (!isFloat(amount3))
	    {
			alert("�����ֻ������ֵ" );
			frmqrjesz.sAmount3.focus();
			return (false);
	    }
	/*	if ((amount3 == "") || (parseFloat(amount3) == 0.00))
		{
			return true;
		}
	*/
		if (((amount3 == "") || (parseFloat(amount3) == 0.00))&&(frmqrjesz.nSignUserID3.value == -1))
		{
			return true;
		}
		if (((amount3 == "")||parseFloat(amount3) < 0.01 )&& !(frmqrjesz.nSignUserID3.value == -1))
		{
			alert("���������С��0.01");
			frmqrjesz.sAmount1.focus();
			return false;
		}
	
		if (amount3.length >= 16)
		{
			alert("�����ֻ��С��1,000,000,000,000.00");
			frmqrjesz.sAmount3.focus();
			return false;
		}
		if (parseFloat(amount2) >= parseFloat(amount3))
		{
			alert ("�����������ڽ���");
			frmqrjesz.sAmount3.focus();
			return false;
		}
		if (frmqrjesz.nSignUserID3.value == -1)
		{
			alert("ָ��ǩ����������Ϊ��");
			frmqrjesz.nSignUserID3.focus();
			return false;
		}
	/*	if (frmqrjesz.nSignUserID1.value == frmqrjesz.nSignUserID2.value)
		{
			alert("���һ�ͽ�����ָ��ǩ���˲�����ͬ");
			frmqrjesz.nSignUserID2.focus();
			return false;
		}
	*/
		if (frmqrjesz.nSignUserID1.value == frmqrjesz.nSignUserID3.value)
		{
			alert("���һ�ͽ������ָ��ǩ���˲�����ͬ");
			frmqrjesz.nSignUserID3.focus();
			return false;
		}
		if (frmqrjesz.nSignUserID2.value == frmqrjesz.nSignUserID3.value)
		{
			alert("�����ͽ������ָ��ǩ���˲�����ͬ");
			frmqrjesz.nSignUserID3.focus();
			return false;
		}
    	return true;
    }

</script>


<script language="javascript">
	/* ҳ�潹�㼰�س����� */
	firstFocus(frmqrjesz.sAmount1);
	setSubmitFunction("submitme(frmqrjesz)");
	setFormName("frmqrjesz");
</script>

<%
	//��ʾ�ύ�����ɹ�
	String stradd = (String)request.getAttribute("flag");
	if(stradd!=null&&stradd.equals("success")){
		out.println("<script language='javascript'>");
		out.println("alert('��������Ч')");
		out.println("</script>");
	}
	/* ��ʾ�ļ�β */
	OBHtml.showOBHomeEnd(out);
%>
<jsp:include flush="true" page="/ShowMessage.jsp" />
<%@ include file="/common/SignValidate.inc" %>