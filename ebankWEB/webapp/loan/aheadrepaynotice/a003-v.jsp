<%@page contentType="text/html;charset=gbk"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
    //response.setHeader("Cache-Control","no-stored");
	//response.setHeader("Pragma","no-cache");
	//response.setDateHeader("Expires",0);
%>
<%@page import="java.util.*,
com.iss.itreasury.loan.util.*,
com.iss.itreasury.util.*,
com.iss.itreasury.ebank.util.*,
com.iss.itreasury.ebank.obaheadrepaynotice.dataentity.AheadRepayNoticeInfo
" 
%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%
	/* ����̶����� */
	String strTitle = "[�����֪ͨ��]";
%>	
<%
	  try
	  {
	   /* �û���¼��� */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        /* �ж��û��Ƿ���Ȩ�� */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }
		String strTemp = "";
		long lID = -1;
		AheadRepayNoticeInfo info  = (AheadRepayNoticeInfo) request.getAttribute("info");
		//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@="+info);
		strTemp = (String)request.getAttribute("lID");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				lID = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				lID = -1;
			}
		}
		
		String txtAction = "";
		if( (String)request.getAttribute("txtAction") != null )
			txtAction = (String)request.getAttribute("txtAction");
			
		String txtChanged = "";
		if( (String)request.getAttribute("txtChanged") != null )
			txtChanged = (String)request.getAttribute("txtChanged");

		//��ʾ�ļ�ͷ
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
%>
<SCRIPT language="javascript" src="/webob/js/Control.js"></SCRIPT>
<SCRIPT language=JavaScript src="/webob/js/Check.js"></SCRIPT>
<safety:resources />
<form name="frm" action="../aheadrepaynotice/a004-c.jsp" method="post">
<input type="hidden" name="lID" value=<%=lID%>>	
<input type="hidden" name="txtAction" value=<%=txtAction%>>
<input type="hidden" name="txtChanged" value=<%=txtChanged%>>
<table width=740 border=0 class=top height="113">  
<tr class="tableHeader">    
      <td class="FormTitle" height="13"><b>�����֪ͨ�������ύ</b></td>
</tr>  
<tr>     
      <td valign="bottom"> 
        <table width="100%" border="0">
          <tr> 
            <td width="15%" height="26"><u>��������</u>��</td>
            <td width="49%" height="26">&nbsp;</td>
            <td width="15%" height="26"></td>
            <td width="49%" height="26"></td>
          </tr>
          <tr> 
            <td width="17%">��λ��</td>
            <td colspan=3> 
              <input type="text" name="textfield" size="76" class="box" value="<%=info.getClientName()%>" disabled>
            </td>
          </tr>
          <tr> 
            <td width="17%">��ͬ��ţ�</td>
            <td width="19%"> 
              <input type="text" name="textfield3" size="18" class="box" value="<%=info.getContractCode()%>" disabled>
            </td>
            <td width="15%">�������ޣ�</td>
            <td width="49%"> 
              <input type="text" name="textfield33" size="18" class="box" value="<%=info.getIntervalNum()%>" disabled>
            </td>
          </tr>
          <tr> 
            <td width="17%">��ͬ����</td>
            <td width="19%"> 
              <input type="text" size=18 name="Input222" class="tar" value="<%=DataFormat.formatDisabledAmount(info.getContractAmount())%>" disabled>
            </td>
            <td width="15%">��ͬ����</td>
            <td width="49%"> 
              <input type="text" size=18 name="Input2222" class="tar" value="<%=DataFormat.formatDisabledAmount(info.getContractBalance())%>" disabled>
            </td>
          </tr>
          <tr> 
            <td width="17%">�ſ�֪ͨ����ţ�</td>
            <td width="19%"> 
              <input type="text" name="textfield22" size="18" class="box" value="<%=info.getLetoutNoticeCode()%>" disabled>
            </td>
            <td width="15%">�ſ�֪ͨ�����ʣ�</td>
            <td width="49%"> 
              <input type="text" name="txt1" class="tar" size="18" value="<%=DataFormat.formatRate(info.getLetoutNoticeRate())%>" disabled>
              % </td>
          </tr>
          <tr> 
            <td width="17%" nowrap>�ſ�֪ͨ������</td>
            <td width="19%"> 
              <input type="text" size=18 name="Input2" class="tar" value="<%=DataFormat.formatDisabledAmount(info.getLetoutNoticeAmount())%>" disabled>
            </td>
            <td width="15%" nowrap>�ſ�֪ͨ������</td>
            <td width="49%"> 
              <input type="text" size=18 name="balance" class="tar" value="<%=DataFormat.formatDisabledAmount(info.getLetoutNoticeBalance())%>" disabled>
            </td>
          </tr>
          <tr> 
            <td width="17%"><font color="#CC0000">*</font> �������</td>
            <td width="19%"> 
            <script language="javascript">
				createAmountCtrl("frm", "dAheadRepayAmount", "<%=DataFormat.formatDisabledAmount(info.getAmount())%>", "submitfunction", null);
			</script>
			<input type="hidden" name="ContractID"  value="<%=info.getContractID()%>" >
			<input type="hidden" name="PayID"  value="<%=info.getLetoutNoticeID()%>" >
            </td>
            <td width="15%">�������ʣ�</td>
            <td width="49%"> 
              <input type="text" name="txt12" class="tar" size="18" value="<%=DataFormat.formatRate(info.getLetoutNoticeRate())%>" disabled>
              % </td>
          </tr>
		   <tr> 
            <td width="17%"><font color="#CC0000">*</font> �Ƿ���ǰ���</td>
            <td width="19%"> 
<%
			LOANConstant.YesOrNo.showList(out,"nIsAhead",0,info.getIsAhead()==1?1:2,false,false,"onfocus=nextfield='submitfunction';");
%>      
            </td>
            <td width="15%">&nbsp;</td>
            <td width="49%">&nbsp;</td>
          </tr>
          <tr> 
            <td colspan="4"> 
              <hr>
            </td>
          </tr>
          <tr> 
            <td width="17%"> 
            <INPUT class=button name=btnModifyExPlan onclick="Javascript: frmModifyExPlan('<%=info.getPlanID()%>');" type="button" value=" �鿴ִ�мƻ� ">
			</td>
            <td width="19%">&nbsp; </td>
            <td width="15%"></td>
            <td width="49%">&nbsp;</td>
          </tr>
        </table>
      <tr>    
		
      <td valign="top"> 
        <hr>        
        <table width="100%" border="0">
          <tr align="right">
            <td width="97%"> 
              <input class=button onFocus="nextfield='submitfunction';" onClick="confirmSave(frm)" type=button value=" �� �� " name="save">
					  
              <input class=button onClick="backto(frm)" type=button value=" �� �� " name=Submitsavebutton22>
            </td>
            <td colspan="1" width="3%">&nbsp; </td>
          </tr>
        </table>
      </td>
</tr>
</table>
<P><BR></P>
</form>

<script language="javascript">

function frmModifyExPlan(planID) //ִ�мƻ�  action on the Form : frmPage
{
	var url = "../query/q094-v.jsp?control=view&isYU=2&nTmpID="+planID;
	window.open(url, "ִ�мƻ�", "width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes");
}


function newdiff()
{
	//eval("self.location='T8.jsp?control=view&lAheadRepayID=35&sNewStartDate=&backpage=T1'");
}

function backto(frm)
{
	if(frm.txtAction.value=="modify")
	{
		eval("self.location='../query/q003-c.jsp'");
	}else
	{
		eval("self.location='../aheadrepaynotice/a001-v.jsp'");
	}
}

function confirmSave(frm)
{
		if(confirm("�Ƿ񱣴�֪ͨ����"))
		{
		
			if (!checkAmount(frm.dAheadRepayAmount,1,"������")) 
			{
				return false;
			}
			
			if (frm.balance.value == "")
			{
				frm.balance.value = 0;
			}
			
			if  (parseFloat(reverseFormatAmount(frm.dAheadRepayAmount.value))  > parseFloat(reverseFormatAmount(frm.balance.value)) )
			{
				alert("������ܴ��ڷſ�֪ͨ����");
				frm.dAheadRepayAmount.focus();
				return false;
			}
			
			showSending();
			frm.submit(); 
			return true;
		}
}

function confirmCancel(frm)
{
	if(confirm("�Ƿ�ȡ��֪ͨ����"))
	{
		frm.control.value = "save";
		frm.job.value="cancel";
		frm.action = "T2.jsp"
		showSending();
		frm.submit();
		return true;
	}
}

firstFocus(frm.dAheadRepayAmount);
//setSubmitFunction("confirmSave(frm)");
setFormName("frm");
</script>
<%
		OBHtml.showOBHomeEnd(out);	
	}
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>
<%@ include file="/common/SignValidate.inc" %>
