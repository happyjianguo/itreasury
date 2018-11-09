<%--
/*
 * �������ƣ�ck008-v.jsp
 * ����˵�����������ڴ浥����ƥ������ҳ��
 * �������ߣ����	
 * ������ڣ�2006��04��10��
 */
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import = "com.iss.itreasury.ebank.util.*,
				   com.iss.itreasury.util.*,
				   com.iss.itreasury.settlement.util.SETTHTML,
				   com.iss.itreasury.settlement.util.SETTConstant,
				   com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				   com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<!--Header end-->

<%!
	/* ����̶����� */
	String strTitle = "[ҵ�񸴺�]";
%>
<%
	/* ʵ�ֲ˵����� */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
%>
<%
	/* ʵ������Ϣ�� */
	FinanceInfo financeInfo = new FinanceInfo();
	PayerOrPayeeInfo payeeInfo = new PayerOrPayeeInfo();
	String sPayerCurrentBalance="";
	String sPayerUsableBalance="";
	long lTransType = -1;

	/* �û���¼�����Ȩ��У�鼰�ļ�ͷ��ʾ */
    try 
	{
        //�û���¼��� 
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng,  strTitle, "",1, "Gen_E002");
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
		if(request.getAttribute("financeInfo") != null)
		{
			financeInfo = (FinanceInfo)request.getAttribute("financeInfo");
		}
		payeeInfo = (PayerOrPayeeInfo)request.getAttribute("payerOrPayeeInfo");
		if(request.getAttribute("lTransType") != null)
		{
			lTransType = Long.parseLong((String)request.getAttribute("lTransType"));
		}
				
        /* ��ʾ�ļ�ͷ */
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
%>

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>

<safety:resources />

<form method="post" name="frm">
  <table width="730" border="0" cellspacing="0" cellpadding="0" class=top>
    <tr  class="tableHeader">
      <!--td bgcolor="#456795" width="5" valign="top" align="left" height="25"><img src="\webob\graphics\blue_top_left.gif" width="3" height="3"></td-->
      <td height="25" class=FormTitle colspan="5"><font size="3" >�������ڴ浥����</font></td>
      <!--td width="5" valign="top" align="right" height="25">
        <div align="right"></div>
      </td-->
    </tr>
    <tr class="MsoNormal">
      <td colspan="5" height="1" class="MsoNormal"></td>
    </tr>
    <tr class="MsoNormal">
      <td width="2" height="25" class="MsoNormal"></td>
      <%
		//����˺ŷŴ�
		String[] nextfocus = new String[]{"nFixedDepositTime"};
		OBMagnifier.createPayerAccountNoCtrl(out,sessionMng.m_lUserID,sessionMng.m_lCurrencyID,financeInfo.getID(),sessionMng.m_lClientID,"nPayerAccountID","dPayerCurrBalance","dPayerUsableBalance","frm",financeInfo.getPayerAcctNo(),"sPayerAccountNoZoom","&nbsp;&nbsp;����˺�"," width=\"300\" height=\"25\" class=\"MsoNormal\""," width=\"300\" height=\"25\" ",nextfocus);	
%>
      <td width="161" class="MsoNormal"></td>
    </tr>
    <tr class="MsoNormal">
      <td colspan="5" height="1" class="MsoNormal"></td>
    </tr>
    <tr class="MsoNormal">
      <td width="2" height="25" class="MsoNormal"></td>
      <td width="161" height="25" class="MsoNormal"><p>&nbsp;&nbsp;���ڴ�����ޣ�</p></td>
      <td width="492" height="25" class="MsoNormal"><%SETTHTML.showFixedDepositMonthListCtrl(out,"nFixedDepositTime",SETTConstant.TransQueryType.FIXED,financeInfo.getFixedDepositTime(),false,"onfocus=\"nextfield='dAmount';\"",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);%>
      </td>
      <td width="5" height="25" class="MsoNormal"></td>
    </tr>
    <tr class="MsoNormal">
      <td colspan="5" height="1" class="MsoNormal"></td>
    </tr>
    <tr class="MsoNormal">
      <td colspan="5" height="1" class="MsoNormal"></td>
    </tr>
    <tr >
      <td width="2" height="25" class="MsoNormal"></td>
      <td width="161" height="25" class="MsoNormal">&nbsp;&nbsp;��&nbsp;&nbsp;<%= sessionMng.m_strCurrencySymbol %></td>
      <td height="25" width="492" class="MsoNormal"><script  language="JavaScript">
				createAmountCtrl("frm","dAmount","<%= financeInfo.getFormatAmount() %>","tsExecute","",1);
			</script>
      </td>
      <td width="5" height="25" class="MsoNormal"></td>
    </tr>
    <tr class="MsoNormal">
      <td colspan="5" height="1" class="MsoNormal"></td>
    </tr>
    <tr class="MsoNormal">
      <td width="2" height="25" rowspan="3" class="MsoNormal"></td>
      <td width="161" height="25" class="MsoNormal" >&nbsp;&nbsp;ִ���գ�</td>
      <td height="25" class="MsoNormal"><input type="text" name="tsExecute" value="<%= (financeInfo.getID() == -1)?DataFormat.getDateString(Env.getSystemDate(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)) :financeInfo.getFormatExecuteDate() %>" onfocus="nextfield ='';" size="12" />
          <!--a href="javascript:show_calendar('frmzjhb.tsExecute');" onmouseout="window.status='';return true;" onmouseover="window.status='Date Picker';return true;">
				<img src="\webob\graphics\calendar.gif"  width="15" height="18" border=0>
			</a-->
      </td>
      <td width="5" height="25" class="MsoNormal"></td>
    </tr>
    <!-- 
    <tr class="MsoNormal">
      <td width="161" height="25" class="MsoNormal" >&nbsp;&nbsp;���ڴ浥�ţ�</td>
      <td height="25" class="MsoNormal"><input type="text" name="SDEPOSITBILLNO" size="24"  class="rebox" value="<%=financeInfo.getSDepositBillNo() == null?"":financeInfo.getSDepositBillNo() %>"/></td>
      <td width="5" height="25" class="MsoNormal"></td>
    </tr>
    -->
    <tr class="MsoNormal">
      <td colspan="5" height="1" class="MsoNormal"></td>
    </tr>
    <tr>
      <td  colspan ="3"></td>
      <td width="70"><div align="right">
          <!--img src="\webob\graphics\button_pipei.gif" width="46" height="18" onclick="Javascript:matchme();"-->
          <input type="button" name="Submitv00204" value=" ƥ  �� " class="button" onclick="Javascript:matchme();" />
      </div></td>
    </tr>
  </table>
  <input type="hidden" name="nPayerAccountID" size="16" value="<%= financeInfo.getPayerAcctID() %>" >
	  <input type="hidden" name="nPayeeAccountID" value="" >
	  <input type="hidden" name="SelectType" value="<%= lTransType %>">
	  <input type="hidden" name="lInstructionID" value="<%= financeInfo.getID() %>">
	  <input type="hidden" name="menu" value="<%=lShowMenu == OBConstant.ShowMenu.NO?"hidden":""%>">
</form>
<!--presentation end-->

<script language="Javascript">

    /* ƥ�亯�� */
    function matchme()
    {
		frm.action = "ck009-c.jsp";
		if (validate() == true)
        {
			/* ȷ���ύ */
			if (!confirm("�Ƿ�ƥ�䣿"))
			{
				return false;
			}
			showSending();
            frm.submit();
        }
    }
    /* ȡ�������� */
    function cancelme()
    {
		if (frm.lInstructionID.value == -1)
		{	
			if (confirm("ȷ��ȡ����"))
			{
				frm.action="";
				frm.submit();
			}
		}
		else
		{
			if (confirm("ȷ��ȡ����"))
			{
        		history.go(-1);
			}
		}
    }

    /* У�麯�� */
    function validate()
    {
       /**
        * ��������������ϣ�return ture
        * ���У�����return false
        */
		
		if (frm.sPayerAccountNoZoomCtrl.value == "")
		{
			alert("��ѡ�񸶿�˺�");
			frm.sPayerAccountNoZoomCtrl.focus();
			return false;
		}
		
		if((frm.SelectType.value == <%=OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT%>) && (frm.nFixedDepositTime.value<0))
		{
			alert("��ѡ���ڴ������");
			frm.nFixedDepositTime.focus();
			return false;
		}
				
		/* �������Ϸǿ�У�� */
		/* ���У�� */
		if(!checkAmount(frm.dAmount, 1, "���׽��"))
		{
			return false;
		}

		/* ִ����У�� */
		var tsExecute = frm.tsExecute.value;
		if(tsExecute=="")
		{
			alert("ִ���ղ���Ϊ�գ���¼��");
			frm.tsExecute.focus();
			return false;
		}
		if(chkdate(tsExecute) == 0)
		{
			alert("ִ���ո�ʽ����ȷ��������¼��");
			frm.tsExecute.focus();
			return false;
		}
		
		//if(frm.SDEPOSITBILLNO.value == "")
		//{
		///	alert("�����붨�ڴ浥��");
		//	frm.nNoticeDay.focus();
		//	return false;
		//}
				
		return true;
    }
</script>

<script language="javascript">
	/* ҳ�潹�㼰�س����� */
	firstFocus(frm.sPayerAccountNoZoomCtrl);
	//setSubmitFunction("matchme(frm)");
	setFormName("frm");
</script>

<%
		/* ��ʾ�ļ�β */
		OBHtml.showOBHomeEnd(out);	
    }
	catch (IException ie)
	{
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>
<%@ include file="/common/SignValidate.inc" %>