<%--
 ҳ������ ��v201.jsp
 ҳ�湦�� : ��Ϣ���ÿ��� - ����������ҳ��
 ��    �� ��xrli
 ��    �� ��2003-11-20
 ����˵�� ��
 �޸���ʷ ��modify by wjliu --2007/3/28 ע�͵�������Ҫ�Ĳ�ѯ����
--%>
<%@ page contentType="text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.SessionMng"%>   
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTMagnifier,com.iss.itreasury.ebank.util.OBMagnifier"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTHTML"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ page import="com.iss.itreasury.settlement.interest.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<% String strContext = request.getContextPath();%>
   <%
try
{
	sessionMng.clearPageLoader();
       //�������
	String strAccountNoFrom = "";
	String strAccountNoTo = "";
	java.sql.Timestamp tsClearInterestDate = null;
	long lClientIDFrom = -1;
	long lClientIDTo = -1;
	String strClientNoFrom = "";
	String strClientNoTo = "";
	long lConsignerIDFrom =-1;
	long lConsignerIDTo =-1;
	String strConsignerFrom = "";
	String strConsignerTo = "";
	long lConsignLoanSort = -1;	
	String strContractNoFrom = "";
	String strContractNoTo = "";
	long lCurrencyID = -1;
	java.sql.Timestamp tsDateFrom = null;
	java.sql.Timestamp tsDateTo = null;
	long lFeeType = -1;
	long lIsSelectCircleLoan = -1;
	long lIsSelectClearInterestDate = -1;
	long lIsSelectClientNo = -1;
	long lIsSelectCompoundInterest = -1;
	long lIsSelectConsigner = -1;
	long lIsSelectConsignLoanSort = -1;
	long lIsSelectContractNo = -1;
	long lIsSelectForfeitInterest = -1;
	long lIsSelectInterest = -1;
	long lIsSelectPayFormNo = -1;
	long lIsSelectSelfLoanSort = -1;
	long lIsSelectSelfLoanTerm = -1;
	long lIsSelectShortLoan = -1;
	long lIsSelectUnClearInterest = -1;
	long lNoticeDays = -1;
	long lOfficeID = -1;
	String strPayFormNoFrom = "";
	String strPayFormNoTo = "";
	double dRate = 0.0;
	long lSelfLoanSort = -1;
	long lSelfLoanTermFrom = -1;
	long lSelfLoanTermTo = -1;
	
	String strClearInterestDate = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
	String strDate = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);


	String strTemp = null;
	//ҳ�渨������
	String strActionResult = Constant.ActionResult.FAIL;
	String strAction = null;
	if (request.getAttribute("strActionResult") != null)
	{
		  strActionResult = (String)request.getAttribute("strActionResult");
	}
	if (request.getAttribute("strAction") != null)
	{
		  strAction = (String)request.getAttribute("strAction");
	}
	
	//SETTHTML.showHomeHead(out,sessionMng,Env.getClientName());
	OBHtml.showOBHomeHead(out, sessionMng, "Ӧ����Ϣ�ͷ��ÿ���", OBConstant.ShowMenu.YES);
%>
<form name="frmV201" action="q022-c.jsp" method="post" >	
	<input name="ActionID" type="hidden" value="<%=new java.util.Date().getTime()%>">
	<input name="strAction" type="hidden">
	<input type="hidden" name="strSuccessPageURL" value="/settlement/other/view/v202.jsp">
	<input type="hidden" name="strFailPageURL" value="/settlement/other/view/v202.jsp">
	<table cellpadding="0" cellspacing="0" class="title_top">
		  <tr>
		    <td height="24">
			    <table cellspacing="0" cellpadding="0" class=title_Top1>
					<TR>
				       <td class=title ><span class="txt_til2" style="width:150px">Ӧ����Ϣ�ͷ��ÿ���</span></td>
				       <td class=title_right><a class=img_title></td>
					</TR>
				</TABLE>
			<br/>
      <table width="100%" border="0" cellspacing="3" cellpadding="0" class=normal >
          
          <tr> 
            <td height="20"  nowrap>&nbsp;<input type="checkbox" name="lIsSelectSelfLoanSort" value="1" onfocus="nextfield ='lSelfLoanSort';">
              ��Ӫ�������ࣺ</td>
            <TD height=20 width="5%">&nbsp;</TD>
            <td height="20" colspan="4" > <%
				String strPropertyZYZL = "class='box' onFocus=nextfield='lSelfLoanTermFrom';";
				SETTConstant.SettLoanType.showList11(out,"lSelfLoanSort",(int)SETTConstant.AccountGroupType.TRUST,lSelfLoanSort,true,false,strPropertyZYZL,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
		  %> </td>
          </tr>
          <tr> 
            <td height="20"  nowrap>&nbsp;<input type="checkbox" name="lIsSelectConsignLoanSort" value="1" onfocus="nextfield ='lConsignLoanSort';">
              ί�д������ࣺ</td>
            <TD height=20 width="5%">&nbsp;</TD>
            <td height="20" colspan="4" > <%
				String strPropertyWTZL = "class='box' onFocus=nextfield='lIsSelectInterest';";
				SETTConstant.SettLoanType.showList11(out,"lConsignLoanSort",(int)SETTConstant.AccountGroupType.CONSIGN,lConsignLoanSort,true,false,strPropertyWTZL,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
		  %> </td>
          </tr>
          
          <tr> 
            <td height="20" >&nbsp;&nbsp;&nbsp;<font color="#FF0000">*</font>
              ��Ϣ���ڣ�</td>
            <TD height=20 width="5%">&nbsp;</TD>
            <TD  width=20% nowrap colspan="2">
            	<fs_c:calendar 
		          	    name="strClearInterestDate"
			          	value="<%=strClearInterestDate%>" 
			          	properties="nextfield ='lIsSelectSelfLoanSort'" 
			          	size="20"/>
            </TD>
          </tr>
          
          <TR borderColor=#ffffff> 
            <TD noWrap height=1>&nbsp;&nbsp;&nbsp;<font color="#FF0000">* </font>��Ϣ�������ͣ�</TD>
            <TD height=1 width="5%">&nbsp;</TD>
            <TD noWrap colSpan=3 height=1>
			 <input type="checkbox" name="lIsSelectInterest" value="1" onFocus="nextfield='lIsSelectCompoundInterest';">
              ��Ϣ 
              <input type="checkbox" name="lIsSelectCompoundInterest" value="1" onFocus="nextfield='lIsSelectForfeitInterest';">
              ���� 
              <input type="checkbox" name="lIsSelectForfeitInterest" value="1" onFocus="nextfield='lFeeType[0]';">
              ��Ϣ 
              <input type="radio" name="lFeeType" value="2" onFocus="nextfield='lFeeType[1]';">
              ������ 
              <input type="radio" name="lFeeType" value="3" onFocus="nextfield='interest';">
              ������</TD>
            <TD noWrap height=1>&nbsp;</TD>
          </TR>
          <tr> 
            <td height="38" width="1%" nowrap>&nbsp;</td>
            <td height="38"></td>
            <td height="38">&nbsp;</td>
            <td height="38">&nbsp;</td>
			<td height="38" align="right"> 
                <input type="button" name="interest" value=" ��Ϣ���ÿ���� "  class="button1" onFocus="nextfield='submitfunction';" onClick="doInterestEstimate();">
              &nbsp;&nbsp;</td>
            <td height="38">&nbsp;</td>
       </tr>
      </table>
     </td>
    </tr>
   </table>
 </form>
<script language="JavaScript">

var lTemp = 0;
function doSearchAccount()
{	
	if(!validataInterest()) return;		
	document.frmV201.strSuccessPageURL.value="/settlement/other/view/v202.jsp";
	document.frmV201.strFailPageURL.value="/settlement/other/view/v201.jsp";
	document.frmV201.strAction.value="searchAccount";
	showSending();//��ʾ����ִ��
    document.frmV201.submit();
}
function doSearchClient()
{	
	if(!validataInterest()) return;		
	document.frmV201.strSuccessPageURL.value="/settlement/other/view/v204.jsp";
	document.frmV201.strFailPageURL.value="/settlement/other/view/v201.jsp";
	document.frmV201.strAction.value="searchClient";
	showSending();//��ʾ����ִ��
    document.frmV201.submit();
}
function doInterestEstimate()
{
	if(!validataInterest()) return;
			
	/*document.frmV201.strSuccessPageURL.value="q021-v.jsp";
	document.frmV201.strFailPageURL.value="q021-v.jsp";
	document.frmV201.strAction.value="printInterestEstimate";
	showSending();//��ʾ����ִ��*/
	
	document.frmV201.strSuccessPageURL.value="/capital/query/q023-v.jsp";
	document.frmV201.strFailPageURL.value="/capital/query/q023-v.jsp";
	document.frmV201.strAction.value="printInterestEstimate";
	document.frmV201.target="_blank";
    document.frmV201.submit();
	//window.open("q022-c.jsp?strAction=interestEstimate&strSuccessPageURL=q023-v.jsp&strFailPageURL=q023-v.jsp");    
}
function doNoticeEstimate()
{	
	lTemp = 1;
	if(!validateFields(frmV201)) return;
	document.frmV201.strSuccessPageURL.value="/settlement/other/view/v201.jsp";
	document.frmV201.strFailPageURL.value="/settlement/other/view/v201.jsp";
	document.frmV201.strAction.value="printNoticeEstimate";
	showSending();//��ʾ����ִ��
    document.frmV201.submit();
}
function doLoanDunNotice()
{
  //�������֪ͨ��
  	if(!validataDun()) return;	
	lTemp = 2;
	 if(!validateFields(frmV201)) return;	
 	document.frmV201.strSuccessPageURL.value="/settlement/other/view/v203.jsp";
	document.frmV201.strFailPageURL.value="/settlement/other/view/v201.jsp";
	document.frmV201.strAction.value="LoanDunNotice";
	showSending();//��ʾ����ִ��
    document.frmV201.submit();
}
function doLoanMatureNotice()
{
 if(!validataMature()) return;	
  //���ڴ���֪ͨ��
  lTemp = 2;
  if(!validateFields(frmV201)) return;
  document.frmV201.strSuccessPageURL.value="/settlement/other/view/v203.jsp";
	document.frmV201.strFailPageURL.value="/settlement/other/view/v201.jsp";
	document.frmV201.strAction.value="LoanMatureNotice";
	showSending();//��ʾ����ִ��
    document.frmV201.submit();
}
function validataInterest()
{
	var smsg = "";
	var bResult = true;
	if(bResult)
	{
	}
	
	if(document.frmV201.strClearInterestDate.value=='')
	{
		smsg = smsg + "��Ϣ���ڲ���Ϊ�գ����������룡\n";
		bResult = false;
	}
	else if(chkdate(document.frmV201.strClearInterestDate.value)==0)
	{
		smsg = smsg + "��Ϣ���ڲ���ȷ�����������룡\n";
		bResult = false;
	}
	if(!CompareDateString('<%=Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID)%>',document.frmV201.strClearInterestDate.value))
	{
		alert("��Ϣ�ղ���С�ڵ�ǰϵͳ���ڣ����������룡");
		bResult = false;
	}
	
	if(bResult)
	{
		if(frmV201.lFeeType(0).checked == false && frmV201.lFeeType(1).checked == false && frmV201.lIsSelectInterest.checked == false && frmV201.lIsSelectCompoundInterest.checked == false && frmV201.lIsSelectForfeitInterest.checked == false)
		{
			//alert("����ѡ��һ����Ϣ���ͣ����������룡");
			smsg = smsg + "����ѡ��һ����Ϣ���ͣ����������룡\n";
			bResult = false;
		}		
	}
	if (smsg !="")
	{
		alert(smsg);
	}	
	return bResult;
}
function validataDun()
{
	var bResult = true;
	if(bResult)
	{
		if(frmV201.lIsSelectClearInterestDate.checked == true)
		{
			if(document.frmV201.strClearInterestDate.value=='')
			{
				alert("��Ϣ���ڲ���Ϊ�գ����������룡");
				bResult = false;
			}
		}
		else
		{
			alert("���빴ѡ��Ϣ���ڣ�");
			bResult = false;
		}		
	}
	if(bResult)
	{
		if(frmV201.lIsSelectContractNo.checked == false)
		{
			alert("���빴ѡ��ͬ�ţ�");
			bResult = false;
		}
	}	
	if(bResult)
	{
		if(frmV201.lIsSelectPayFormNo.checked == false)
		{
			alert("���빴ѡ�ſ�ţ�");
			bResult = false;
		}
	}
	if(bResult)
	{
		if(frmV201.lFeeType(0).checked == false && frmV201.lFeeType(1).checked == false && frmV201.lIsSelectInterest.checked == false && frmV201.lIsSelectCompoundInterest.checked == false && frmV201.lIsSelectForfeitInterest.checked == false)
		{
			alert("����ѡ��һ����Ϣ���ͣ����������룡");
			bResult = false;
		}
	}		
	return bResult;
}
function validataMature()
{
	var bResult = true;
	if(bResult)
	{
		if(frmV201.lIsSelectContractNo.checked == false)
		{
			alert("���빴ѡ��ͬ�ţ�");
			bResult = false;
		}
	}	
	if(bResult)
	{
		if(frmV201.lIsSelectPayFormNo.checked == false)
		{
			alert("���빴ѡ�ſ�ţ�");
			bResult = false;
		}
	}
	if(bResult)
	{
		if(frmV201.lFeeType(0).checked == false && frmV201.lFeeType(1).checked == false && frmV201.lIsSelectInterest.checked == false && frmV201.lIsSelectCompoundInterest.checked == false && frmV201.lIsSelectForfeitInterest.checked == false)
		{
			alert("����ѡ��һ����Ϣ���ͣ����������룡");
			bResult = false;
		}
	}		
	return bResult;
}
function allFields()
{	
	if (lTemp == 1)
	{
		this.ag = new Array("dRate","����","rate",1);
		this.ar = new Array("strNotifyStartDateFrom","��Ϣ��","date",1);
		this.as = new Array("strNotifyStartDateTo","������","date",1);		
	}
	if (lTemp == 2)
	{		
		this.a1 = new Array("lPayFormIDTo","��ʼ�ſ","magnifier",1);
		this.a2 = new Array("lPayFormIDFrom","��ֹ�ſ","magnifier",1);	
		this.a3 = new Array("lContractIDFrom","��ʼ��ͬ��","magnifier",1);
		this.a4 = new Array("lContractIDTo","��ֹ��ͬ��","magnifier",1);			
	}
}
	
</script>
<script language="javascript">
	firstFocus(document.frmV201.lIsSelectSelfLoanSort);
 	//setSubmitFunction("doInterestEstimate()");
	setFormName("frmV201");
</script>
<%
	
	if(Constant.ActionResult.SUCCESS.equals(strActionResult) && "printInterestEstimate".equals(strAction))
	{
	%>
	<!--	
		<script language="JavaScript">
			window.open("q022-c.jsp?strAction=interestEstimate&strSuccessPageURL=q023-v.jsp&strFailPageURL=q023-v.jsp");    
		</script>
	-->		
	<%
	}	
	//SETTHTML.showHomeEnd(out);
	OBHtml.showOBHomeEnd(out);
%>
<%
}
catch(Exception exp)
{
	Log.print(exp.getMessage());
}
%>
<%@ include file="/common/SignValidate.inc" %>