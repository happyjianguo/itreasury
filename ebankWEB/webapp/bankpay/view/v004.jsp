<!--

author: niweinan
2010.10.16
����:�������ʽ𲦻� ���л�� ��ϸҳ��
-->

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.util.ArrayList,
				 com.iss.itreasury.util.ConfigConstant,
				 com.iss.itreasury.ebank.util.*,
                 com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.obquery.dataentity.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dao.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<!--Header end-->

<%!
	/* ����̶����� */
	String strTitle = "[���л��]";
%>

<%
System.out.println("**************************Enter v004.jsp");
	/* ʵ�ֲ˵����� */
	
	
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
    
	long lSourceType = 0;//ͷ��Ϣ��ʾ
    String strSource = request.getParameter("src");
    
    if ((strSource != null) && (strSource.length()>0))
	{
	    lSourceType = Long.parseLong(strSource);
	}
	
	String lTransType = "";
	lTransType = (String) request.getParameter("lTransType");
	if (lTransType == null)
		lTransType = (String) request.getParameter("txtTransType");
		
	long lCheckType = -1;//�����ڸ���ƥ���
	String strCheckType = request.getParameter("checktype");
	if ((strCheckType != null) && (strCheckType.length() > 0)) {
		lCheckType = Long.parseLong(strCheckType);
	}
	String sTemp = null;
	 long lID = 0;           //ָ�����
    sTemp = (String) request.getParameter("txtID");
    if (sTemp != null && sTemp.trim().length() > 0) {
        lID = Long.parseLong(sTemp);
    }

%>

<%
	/* ʵ������Ϣ�� */
	

	/* �û���¼�����Ȩ��У�鼰�ļ�ͷ��ʾ */
	try 
	{
        
     OBHtml.validateRequest(out,request,response);

		/* �������л�ȡ��Ϣ */
		OBBankPayInfo info = new OBBankPayInfo();
		info=(OBBankPayInfo)request.getAttribute("obbankpayinfo");
		
		
      
        /* ��ʾ�ļ�ͷ */
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
		String strTransNo = info.getId() + "";
		
		String payerAccountNo = com.iss.itreasury.ebank.util.NameRef.getBankPayAcctNameByAcctID(info.getNpayeracctid());  //����˺�
		OBFinanceInstrBiz obFinanceInstrBiz = new OBFinanceInstrBiz();
		boolean hasAuthority = false;
	

	//SEFC����
%>
<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>

<safety:resources />

<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">���л��</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>

  	    </td>
  </tr>
  
</table>
<br/>
	
		
      

		<table  border="0" cellspacing="0" cellpadding="0" class="">
			<tr>
				<td width="3"><a class=lab_title1></td>
				<td class="lab_title2"> �������</td>
				<td width="650"><a class=lab_title3></td>
			</tr>
		</table>
		<table width=80% border="0" cellspacing="0" cellpadding="0" class=normal id="table1" align="">
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">������ƣ�</td>
          <td width="430" height="25" class="MsoNormal"> <%=info.getName()%></td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
		
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">����˺ţ�</td>
          <td width="430" height="25" class="MsoNormal"><%=payerAccountNo%></td>
          <td width="5" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">����������ƣ�</td>
          <td width="430" height="25" class="MsoNormal"><%= com.iss.itreasury.ebank.util.NameRef.getBankNameByAcctID(info.getNpayeracctid()) %></td>
          <td width="5" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
      </table>
      <br>
		<table  border="0" cellspacing="0" cellpadding="0" class="">
			<tr>
				<td width="3"><a class=lab_title1></td>
				<td class="lab_title2"> �տ����</td>
				<td width="650"><a class=lab_title3></td>
			</tr>
		</table>
		<table width=80% border="0" cellspacing="0" cellpadding="0" class=normal id="table1" align="">
        <tr class="MsoNormal">
          <td colspan="4" height="1"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">
            ��ʽ��
          </td>
          <td width="430" height="25" class="MsoNormal">���л��</td>
          <td width="1" height="25" class="MsoNormal"></td>
        </tr>
      
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
      
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">
            �տ�˺ţ�
          </td>
          <td width="430" height="25" class="MsoNormal"><%=(info.getSpayeeacctno()==null)?"":info.getSpayeeacctno()%></td>
          <td width="1" height="25" class="MsoNormal"></td>
        </tr>
     
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
      
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">
            �տ���ƣ�
          </td>
          <td width="430" height="25" class="MsoNormal">
		  <%=info.getSpayeeacctname()%></td>
          <td width="1" height="25" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">
            ������CNAPS�ţ�
          </td>
          <td width="430" height="25" class="MsoNormal">
		  <%=info.getBankCNAPSNo() == null?"":info.getBankCNAPSNo()%></td>
          <td width="1" height="25" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">
            �������кţ�
          </td>
          <td width="430" height="25" class="MsoNormal">
		  <%=info.getBankconnectnumber() == null?"":info.getBankconnectnumber()%></td>
          <td width="1" height="25" class="MsoNormal"></td>
        </tr>
                 <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">
            �����ţ�
          </td>
          <td width="430" height="25" class="MsoNormal">
		  <%=info.getDepartmentnumber() == null?"":info.getDepartmentnumber()%></td>
          <td width="1" height="25" class="MsoNormal"></td>
        </tr>
		</table>
      <br>
		<table  border="0" cellspacing="0" cellpadding="0" class="">
			<tr>
				<td width="3"><a class=lab_title1></td>
				<td class="lab_title2"> ��������</td>
				<td width="650"><a class=lab_title3></td>
			</tr>
		</table>
		<table width=80% border="0" cellspacing="0" cellpadding="0" class=normal id="table1" align="">
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td height="25" class="MsoNormal" width="110">��</td>
          <td width="20" height="25" class="MsoNormal">
            <div align="right"><%= sessionMng.m_strCurrencySymbol %></div>
          </td>

          <td width="430" height="25" class="MsoNormal" id="ss"></td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <script>
          		var a = <%=info.getMamount()%>+"";
          		document.getElementById("ss").innerText = formatAmount1(a);
          </script>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal" colspan="2">�ύ���ڣ�</td>
          <td width="430" height="25" class="MsoNormal"><%=info.getDtconfirm().toString().substring(0,10)%></td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal" colspan="2">�����;��</td>
          <td width="430" height="25" class="MsoNormal"><%= (info.getSnote()==null)?"":info.getSnote() %></td>
          <td width="5" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
      

      </table>
	  <br>
	<% 
	  if ((info.getNstatus() == OBConstant.SettInstrStatus.SAVE)
			|| // �ѱ���
			(info.getNstatus() == OBConstant.SettInstrStatus.CHECK)
			|| // �Ѹ���
			(info.getNstatus() == OBConstant.SettInstrStatus.SIGN)
			|| // ��ǩ��
			(info.getNstatus() == OBConstant.SettInstrStatus.DEAL)
			|| //������
			(info.getNstatus() == OBConstant.SettInstrStatus.FINISH)
			|| //�����
			(info.getNstatus()==OBConstant.SettInstrStatus.APPROVALING)
			||//������
			(info.getNstatus()==OBConstant.SettInstrStatus.APPROVALED)
			||//������
			(info.getNstatus() == OBConstant.SettInstrStatus.REFUSE))
			//�Ѿܾ� 
		{
	%>
		<table  border="0" cellspacing="0" cellpadding="0" >
			<tr>
				<td width="3"><a class=lab_title1></td>
				<td class="lab_title2" style="width:150px"> �������봦������</td>
				<td width="650"><a class=lab_title3></td>
			</tr>
		</table>
      <table width=80% border="0" class="normal">
        <tr class="tableHeader">
          <td height="19" width="10%"  align="center" class="ItemTitle">
            <div align="center"><font size="2" >���к�</font></div>
          </td>
          
          <td height="19" class="ItemTitle" width="30%" align="center">
           �û�
          </td>
          
          <td  height="19"  class="ItemTitle" width="30%" align="center">
            <div align="center">��������</div>
          </td>
          
          <td  height="19"  class="ItemTitle" width="30%" align="center">
            <div align="center">ʱ�������</div>
          </td>
        </tr>
       
       
        <tr valign="middle">
          <td width="10%" align="left" class="ItemBody" height="25">
            <div align="center">1</div>
          </td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="30%" height="25">
            <div align="center"><%=com.iss.itreasury.ebank.util.NameRef.getUserNameByID(info.getNconfirmuserid())%></div>
          </td>          
          <td bgcolor="#C8D7EC" class="ItemBody" width="30%" height="25">
            <div align="center">¼��</div>
          </td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="30%" height="25">
            <div align="center"><%= info.getDtconfirm().toString().substring(0,10) %></div>
          </td>
        </tr>
        <%
        }
        if ((info.getNstatus() == OBConstant.SettInstrStatus.CHECK)
				|| // �Ѹ���
				(info.getNstatus() == OBConstant.SettInstrStatus.SIGN)
				|| // ��ǩ��
				(info.getNstatus() == OBConstant.SettInstrStatus.DEAL)
				|| //������
				(info.getNstatus()== OBConstant.SettInstrStatus.FINISH) 
				|| //�����
				(info.getNstatus() == OBConstant.SettInstrStatus.REFUSE)) 
				{
         %>
         <tr valign="middle">
          <td width="10%" align="left" class="ItemBody" height="25">
            <div align="center">2</div>
          </td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="30%" height="25">
            <div align="center"><%=com.iss.itreasury.ebank.util.NameRef.getUserNameByID(info.getNcheckuserid())%></div>
          </td>          
          <td bgcolor="#C8D7EC" class="ItemBody" width="30%" height="25">
            <div align="center">����</div>
          </td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="30%" height="25">
            <div align="center"><%= info.getDtcheck().toString().substring(0,10) %></div>
          </td>
        </tr>
		<%
		}
		if ((info.getNstatus() == OBConstant.SettInstrStatus.SIGN)
				|| // ��ǩ��
				(info.getNstatus() == OBConstant.SettInstrStatus.DEAL)
				|| //������
				(info.getNstatus() == OBConstant.SettInstrStatus.FINISH) 
				|| //�����
				(info.getNstatus() == OBConstant.SettInstrStatus.REFUSE)) 
				{
			
		%>
		 <tr valign="middle">
          <td width="10%" align="left" class="ItemBody" height="25">
            <div align="center">3</div>
          </td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="30%" height="25">
            <div align="center"><%=com.iss.itreasury.ebank.util.NameRef.getUserNameByID(info.getNsignuserid())%></div>
          </td>          
          <td bgcolor="#C8D7EC" class="ItemBody" width="30%" height="25">
            <div align="center">ǩ��</div>
          </td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="30%" height="25">
            <div align="center"><%= info.getDtsign().toString().substring(0,10) %></div>
          </td>
        </tr>
        <%} %>

 </table>
 
 <br>
 <%String strContext = request.getContextPath(); %>
 
	  <table width="80%" border="0" align="" cellspacing="0" cellpadding="0">
			  <tr>
				<td width="1"><a class=lab_title1></td>
				<td class="lab_title2" style="width:150px">��ʷ�������</td>
				<td width="800"><a class=lab_title3></td>
			</tr>
		</table>
	<table border="0" width="80%" cellspacing="0" cellpadding="0" align="" class=normal>
		<!-- ��ʷ������� -->
		<TR>
			<TD colspan="3">
				
			<fs:HistoryOpinionFrame
					  strTransNo='<%=strTransNo%>'
					  moduleID='<%=Constant.ModuleType.SETTLEMENT%>'
					  transTypeID='<%=OBConstant.SettInstrType.ONLINEBANK_BANKPAY%>'
					  clientID='<%=sessionMng.m_lClientID%>'
					  currencyID='<%=sessionMng.m_lCurrencyID%>'
					  officeID='<%=sessionMng.m_lOfficeID%>'
					  islowerunit = '<%=OBConstant.IsLowerun.ISNO%>'/>
			</TD>
		</TR>
		<!-- ��ʷ������� -->
		</table>
		<br />
      <form name="frmzjhb" method="post">
	  <table border="0" width=80% cellspacing="0" cellpadding="0">
        <tr>
		
      <td width="80%" align="right"> 
        

		<%
			
		if(info.getNstatus() == OBConstant.SettInstrStatus.SAVE){
			if(info.getNconfirmuserid()==sessionMng.m_lUserID&&obFinanceInstrBiz.hasAuthority(payerAccountNo,sessionMng.m_lUserID,sessionMng.m_lCurrencyID))
				{
		
		%>

		<input class=button1 name=add type=button value=" �� �� " onClick="Javascript:updateme();" >
		<input class=button1 name=add type=button value=" ɾ �� " onClick="Javascript:deleteme();" >

		<%
				}
		}
		if(info.getNEbankStatus()==OBConstant.BankInstructionStatus.CANCEL)
		{
			if(info.getNconfirmuserid()==sessionMng.m_lUserID&&obFinanceInstrBiz.hasAuthority(payerAccountNo,sessionMng.m_lUserID,sessionMng.m_lCurrencyID))
			{
		%>
		<input class=button1 name=cancel type=button value=" �����ύ " onClick="Javascript:cancelme();" >
		<%
			}
		}
		 %>
		
       
        <!--img src="\webob\graphics\button_fanhui.gif" width="46" height="18" border="0" onclick="javascript:returnme()"-->
		<input class=button1 name=add type=button value=" �� �� " onClick="window.close();" >







        
      </td>
        </tr>
      </table>

	  <input type="hidden" name="mamount" value="<%= info.getMamount() %>">
	  <input type="hidden" name="dtexecute" value="<%= info.getDtexecute() %>">
	  <input type="hidden" name="nconfirmuserid" value="<%= info.getNconfirmuserid() %>">
	  <input type="hidden" name="dtconfirm" value="<%= info.getDtconfirm() %>">
	  <input type="hidden" name="npayeracctid" value="<%= info.getNpayeracctid() %>">
	  <input type="hidden" name="npayeeacctid" value="<%= info.getNpayeeacctid() %>">
	  <input type="hidden" name="id" value="<%= info.getId() %>">
	  <INPUT type="hidden" name="ncurrencyid" value="<%=info.getNcurrencyid()%>">
	  <INPUT type="hidden" name="nclientid" value="<%=info.getNclientid()%>"> 
	  <INPUT type="hidden" name="spayeeacctno" value="<%=info.getSpayeeacctno()%>">
	  <INPUT type="hidden" name="spayeeprov" value="<%=info.getSpayeeprov()%>">
	  <INPUT type="hidden" name="spayeecity" value="<%=info.getSpayeecity()%>">
	  <INPUT type="hidden" name="spayeebankname" value="<%=info.getSpayeebankname()%>">
	  <INPUT type="hidden" name="spayeeacctname" value="<%=info.getSpayeeacctname()%>">
	  <INPUT type="hidden" name="spayeeacctno" value="<%=info.getSpayeeacctno()%>">
	  <INPUT type="hidden" name=snote value="<%=info.getSnote()%>">
		
	  </form>
<!--presentation end-->

<script language="javascript">
//��ӡί�и���ƾ֤
function PrintConsignVoucher()
{
}


	/* �˵����ƴ����� */
	function showMenu()
	{
		<%  if (lShowMenu == OBConstant.ShowMenu.NO)
		    {   %>
		        frmzjhb.menu.value="hidden";
		<%  }   %>
	}
	/*���ش����� */
	function returnme()
	{
		frmzjhb.action="../view/v001.jsp";
		frmzjhb.submit();
	}

	
	/* �޸Ĵ����� */
	function updateme()
	{
		frmzjhb.action="<%=strContext%>/bankpay/view/v001.jsp?lID=<%=lID%>&src=<%=lSourceType%>";
		frmzjhb.submit();
	}
	//����������
	function cancelme()
	{
		frmzjhb.action="<%=strContext%>/bankpay/view/v001.jsp?lID=<%=lID%>&nEbankStatus=<%=info.getNEbankStatus()%>";
		frmzjhb.submit();
	}
	
	/* ɾ�������� */
	function deleteme()
	{
		if (!confirm("�Ƿ�ɾ����"))
		{
			return false;
		}
		//showMenu();
		frmzjhb.action="../control/c003.jsp?doact=querydelete";
		showSending();
		frmzjhb.submit();
	}


</script>

<%
		if(lShowMenu == OBConstant.ShowMenu.YES)
		{	/* ��ʾ�ļ�β */
			OBHtml.showOBHomeEnd(out);
		}
	}
	catch(IException e)
	{
		OBHtml.showExceptionMessage(out,sessionMng, (IException)e, strTitle,"",1);
		return;
	}
	
	
%>
<%@ include file="/common/SignValidate.inc" %>