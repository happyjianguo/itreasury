<%--
/*
 * �������ƣ�LiShi.jsp
 * ����˵�����˻���ʷ��ϸ��ѯҳ��
 * �������ߣ�����
 * ������ڣ�2003��11��25��
 */
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import="com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.settlement.util.*,
				 com.iss.itreasury.ebank.obquery.dataentity.OBInterestWhereInfo,
				 com.iss.itreasury.ebank.obquery.dataentity.OBInterestInfo,
				 java.rmi.RemoteException,
				 java.sql.*,com.iss.itreasury.loan.util.LOANConstant,
				 java.util.*"  
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<!--Header end-->
<%!
	/* ����̶����� */
	String strTitle = "[Ӧ����Ϣ��ѯ]";
%>
<%

	
	try
	{
        // �û���¼��� 
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");;
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
		//��ʾ�ļ�ͷ
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, 1);
		
		//�������
		long lCurrencyID = -1;
		String strClientCode = "";//�ͻ����
		long lClientID = -1;//�ͻ�ID
		String strAccountNo = "";//�˺�
		long lAccountID = -1;//�˻�ID
		long lAccountTypeID =-1;//�˻�����ID
		long lAccountGroupID = -1;//�˻���ID

		String strDateEnd = "";
		
		OBInterestWhereInfo qtwi = null;
		
		if (request.getAttribute("whereInfo") != null)
		{
			qtwi = (OBInterestWhereInfo)request.getAttribute("whereInfo");
		}
		
		if (qtwi != null)
		{
			strDateEnd = qtwi.getEndDate();
		}
		
		Collection c = null;
		
		if (request.getAttribute("coll_resultInfo") != null)
		{
			c = (Collection)request.getAttribute("coll_resultInfo");
		}
		
%>
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>

<safety:resources />

<form name='frmQry' method='post'  >
      <table width="730" border="0" cellspacing="0" cellpadding="0" class=top>
        <tr > 
          <td colspan="4" height="1" class=FormTitle>Ӧ����Ϣ��ѯ</td>
        </tr>
      </table>

      <table width="730" border="0" cellspacing="0" cellpadding="0"class=top>
        <tr bgcolor="#FFFFFF" class="MsoNormal"> 
          <td colspan="5" height="1"></td>
        </tr>
        <tr id="AccountID"> 
          <td width="5" height="25"></td>
          
      <td height="25" class="MsoNormal" >�˺ţ�</td>
          <td width="60" height="25" class="MsoNormal"> 
            <div align="right"></div>
          </td>
          <td height="25" class="MsoNormal"> 
		  <input type="hidden" name="lAccountID" value="-1">
		  <input type="hidden" name="lAccountTypeID" value="-1"> 
		  <input type="hidden" name="lAccountGroupID" value="-1"> 
		  <input type="hidden" name="lClientID" value="<%=sessionMng.m_lClientID%>"> 
		  <input type="hidden" name="lContractTypeIDs" value="-1"> 
<%
			Log.print("�˻��ؼ�");
			OBHtmlCom.showAccountListControl( out, "lAccountID_T", sessionMng.m_lClientID,sessionMng.m_lCurrencyID, lAccountID,sessionMng.m_lUserID," onfocus=\"nextfield ='tsEnd';\" ");
%>
		  </td>
          <td width="5"></td>
        </tr>
		<tr  id="AccountIDLine" class="MsoNormal"> 
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <tr > 
          <td width="5" height="25"></td>
          <td height="25" class="MsoNormal">��ֹ���ڣ�</td>
          <td width="60" height="25" class="MsoNormal"> 
            
          </td>
          <td  height="25" class="MsoNormal"> 
          		<fs_c:calendar 
	          	    name="tsEnd"
		          	value="" 
		          	properties="nextfield =''" 
		          	size="12"/>
		       	  <script>
	          		$('#tsEnd').val('<%=strDateEnd%>');
	          	</script>
		  </td>
          <td width="5"></td>
        </tr>
        <tr > 
          <td colspan="5" height="1"></td>
        </tr>
        <tr > 
          <td colspan="5" height="1"></td>
        </tr>
      </table>
      <br>
	   <table width="730" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="376"> 
            <div align="right"></div>
          </td>
          <td width="294"> 
            <div align="right"></div>
          </td>
          <td width="60"> 
            <div align="right">
			<input class=button name=add type=button value=" ��  �� " onClick="ConfirmSearch(frmQry)" onKeyDown="ConfirmSearch(frmQry)">			
			</div>
          </td>
        </tr>
      </table> 
	  <br>	  
	  <table width="730" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#C8D7EC" class="tableHeader"> 
          <td height="22" bgcolor="#0C3869" class="FormTitle" ></td>
        </tr>
      </table>

	<table width="732" border="0" height="60" class="ItemList">
        <tr align="center" class="tableHeader">
          <td width="10%" height="20" class="ItemTitle">�˺�</td>
          <td width="10%" height="20" class="ItemTitle">�������</td>
          <td width="15%" height="20" class="ItemTitle">������</td>
		  <td width="10%" height="20" class="ItemTitle">��������(%)</td>         
		  <td width="10%" height="20" class="ItemTitle">�������(%)</td>
		  <td width="10%" height="20" class="ItemTitle">������Ϣ</td>
		  <td width="15%" height="20" class="ItemTitle">�����Ϣ</td>
		</tr>
		<%
		double interestTotal = 0;
		if (c!=null && c.size()>0)
		{
			Iterator it = c.iterator();
			while (it.hasNext())
			{
				OBInterestInfo info = new OBInterestInfo();
				info = (OBInterestInfo)it.next();
				
				interestTotal += info.getLoanInterest() + info.getDeposiInterest();
%>
		<tr bordercolor="#999999" align="center" class="ItemBody">
			<td  height="23"><%=info.getAcctNo()%></td>
			<td  height="23"><%=info.getFormatLoanBalance()%></td>
			<td  height="23"><%=info.getFormatDepositBalance()%></td>
			<td  height="23"><%=info.getFormatLoanRate()%></td>
			<td  height="23"><%=info.getFormatDepositRate()%></td>
			<td  height="23"><%=info.getFormatLoanInterest()%></td>
			<td  height="23"><%=info.getFormatDeposiInterest()%></td>
        </tr>
<%			}
		}
		else{
		%>
		<tr bordercolor="#999999" align="center" class="ItemBody">
			<td  height="23">&nbsp;  </td>
			<td  height="23">&nbsp; </td>
			<td  height="23">&nbsp; </td>
			<td  height="23">&nbsp; </td>
			<td  height="23">&nbsp; </td>
			<td  height="23">&nbsp; </td>
			<td  height="23">&nbsp; </td>
        </tr>
		<%}%>
      </table>
	  <br>	 
      
	  <table width="730" border="0" cellspacing="0" cellpadding="0">
      <tr>
	  <td>
	  ��Ϣ�ϼ�:<%=DataFormat.formatDisabledAmount(interestTotal)%>
	  </td>
	  </tr>
      </table>
	             
      <table width="730" border="0" cellspacing="0" cellpadding="0">
		<tr><td>
      <div align="left"><br>
        <span class="graytext">��ѯ���ڣ�<%=DataFormat.getDateString()%></span><br>
      </div></td></tr>
      </table>

 </form>
 <script language="javascript">
    /* ҳ�潹�㼰�س����� */
    firstFocus(frmQry.lAccountID_T);
    //setSubmitFunction("ConfirmSearch(frmQry)");
    setFormName("frmQry");
</script>
<SCRIPT language=JavaScript>
var sAccount_t = frmQry.lAccountID_T.value;
var lAccountID = sAccount_t.substring( 0, sAccount_t.indexOf(';') );
var lAccountGroupID = parseInt( sAccount_t.substring( sAccount_t.indexOf(';')+1 ) ); 
var lAccountTypeID = sAccount_t.substring( sAccount_t.indexOf('@')+1 );
frmQry.lAccountID.value = lAccountID;
frmQry.lAccountTypeID.value = lAccountTypeID;
frmQry.lAccountGroupID.value = lAccountGroupID;

//jump();
 
function jump()
{
	var sAccount_t = frmQry.lAccountID_T.value;
	var lAccountID = sAccount_t.substring( 0, sAccount_t.indexOf(';') );
	var lAccountGroupID = parseInt( sAccount_t.substring( sAccount_t.indexOf(';')+1 ) ); 
	var lAccountTypeID = sAccount_t.substring( sAccount_t.indexOf('@')+1 );
	
	frmQry.lAccountID.value = lAccountID;
	frmQry.lAccountTypeID.value = lAccountTypeID;
	frmQry.lAccountGroupID.value = lAccountGroupID;
	
	ContractCode.style.display = "none";
	ContractCodeLine.style.display = "none";
	LoanNoteNo.style.display = "none";
	LoanNoteNoLine.style.display = "none";
	FixedDepositNo.style.display = "none";
	FixedDepositNoLine.style.display = "none";
	NotifyDepositNo.style.display = "none";
	NotifyDepositNoLine.style.display = "none";
	
	if(lAccountGroupID==<%=SETTConstant.AccountGroupType.TRUST%>
		||lAccountGroupID==<%=SETTConstant.AccountGroupType.CONSIGN%>
		||lAccountGroupID==<%=SETTConstant.AccountGroupType.DISCOUNT%>
		||lAccountGroupID==<%=SETTConstant.AccountGroupType.OTHERLOAN%>)
	{
		ContractCode.style.display = "";
		ContractCodeLine.style.display = "";
		LoanNoteNo.style.display = "";
		LoanNoteNoLine.style.display = "";
		changeAccountType(lAccountGroupID);
	}
	if( lAccountGroupID==<%=SETTConstant.AccountGroupType.FIXED%> 
		||lAccountGroupID==<%=SETTConstant.AccountGroupType.NOTIFY%> )
	{
		if(lAccountGroupID == <%=SETTConstant.AccountGroupType.FIXED%>)
		{
			FixedDepositNo.style.display = "";
			FixedDepositNoLine.style.display = "";
		}
		if(lAccountGroupID == <%=SETTConstant.AccountGroupType.NOTIFY%>)
		{
			NotifyDepositNo.style.display = "";
			NotifyDepositNoLine.style.display = "";
		}
	}	
}

function changeAccountType(lAccountGroupID)
{
	var lContractTypeIDs = "" ;
	if (lAccountGroupID == <%=SETTConstant.AccountGroupType.TRUST %>)
	{
		lContractTypeIDs = "<%=LOANConstant.LoanType.ZY%>,<%= LOANConstant.LoanType.ZGXE%>" ;
	}
	else if (lAccountGroupID == <%=SETTConstant.AccountGroupType.CONSIGN%> )
	{
		lContractTypeIDs = "<%=LOANConstant.LoanType.WT%>";
	}
	//���� : TX
	else if (lAccountGroupID == <%=SETTConstant.AccountGroupType.DISCOUNT%> )
	{
		lContractTypeIDs = "<%=LOANConstant.LoanType.TX %>";
	}
	frmQry.lContractTypeIDs.value = lContractTypeIDs;
}

function ConfirmSearch(form)
{ 
	if (checkDate(form.tsEnd,1,"��ֹ����") == false)
		return false; 
	
	showSending();
	form.action = "q071-c.jsp";
	frmQry.target = "";
	form.submit();
}

function downloadresult()
{
	frmQry.action="Khdzd_d.jsp";
	frmQry.target = "NewWindow_S";
	frmQry.submit();
	frmQry.target = "";
}

function doFindTransDetail(lAccountID,lTransTypeID,strTransNo,lGroupID)
{
	window.open("../accountinfo/querycontrol.jsp?lAccountID="+lAccountID+"&TransactionTypeID="+lTransTypeID+"&TransNo="+strTransNo+"&SerialNo="+lGroupID);
}

function printme()
{
	frmQry.action="Khdzd.jsp";
	frmQry.target = "NewWindow_S";
	frmQry.submit();
	frmQry.target = "";
}
 </SCRIPT>
<%
OBHtml.showOBHomeEnd(out);
}
catch(Exception e)
{
	out.println( e.getMessage() );
}
%>
<%@ include file="/common/SignValidate.inc" %>