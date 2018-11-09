<%
/**
 * ҳ������ ��q035-v.jsp
 * ҳ�湦�� : ��ͬ��ѯ����
 * ��    �� ��qqgd
 * ��    �� ��2003-09-27
 * ����˵�� ��
 *			  
 * ת��ҳ�� : 
 */
%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
	java.util.*,
	java.net.URLEncoder,
	com.iss.itreasury.loan.contract.bizlogic.*,
	com.iss.itreasury.loan.contract.dataentity.*,	
	com.iss.itreasury.loan.loancommonsetting.dataentity.*,
	com.iss.itreasury.loan.util.*,
	com.iss.itreasury.ebank.util.*,			
	com.iss.itreasury.ebank.obdataentity.*,	
	com.iss.itreasury.ebank.obquery.bizlogic.*,
	com.iss.itreasury.ebank.obquery.dataentity.*,
	com.iss.itreasury.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%
    try{
    
		if( !sessionMng.isLogin() )
		{	
			OBHtml.showMessage(out,sessionMng,null,"��¼",null,Constant.RecordStatus.INVALID,"Gen_E002");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		//�ж��Ƿ���Ȩ��
		if (sessionMng.hasRight(request) == false)
		{
			OBHtml.showMessage(out,sessionMng,null,"��¼",null,Constant.RecordStatus.INVALID,"Gen_E003");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
   		
        //��ʾ�ļ�ͷ
        OBHtml.showOBHomeHead(out,sessionMng,"��ͬ������ϸ",Constant.YesOrNo.NO);
        
        long loanTypeID=-1;
        long loanID=-1;
        
        /*��ͬ��Ϣ*/
        long contractID=-1;
        String contractCode="";
        String borrowClientName="";
        String consignClientName="";
        String strLoanAmount="";
        String strLeftAmount="";
        String strStartDate="";
        String strEndDate="";
        String strInterestRate="";
        String strPerformRate="";
        String strStatus="";
        long isCredit=-1;
        long isAssure=-1;
        long isPledge=-1;
        long isImpawn=-1;
        long extendCount=0;
        long freeCount=0;
        String strChargeRate="";
        long chargeRateType=-1;

		/*****haoning 2003-12-31****/
		long lRiskLevel = -1;
		String strRiskLevel = "";
        /*************/
        /*չ����Ϣ*/
        
        
        ContractInfo conInfo=(ContractInfo)request.getAttribute("ContractInfo");
        if (conInfo!=null)
        {
        	contractID=conInfo.getContractID();
        	contractCode=conInfo.getContractCode();
        	borrowClientName=(conInfo.getBorrowClientName()==null)?"":conInfo.getBorrowClientName();
        	consignClientName=(conInfo.getClientName()==null)?"":conInfo.getClientName();
        	strLoanAmount=DataFormat.formatListAmount(conInfo.getLoanAmount());
        	strLeftAmount=DataFormat.formatListAmount(conInfo.getBalance());
        	strStartDate=DataFormat.getDateString(conInfo.getLoanStart());
        	strEndDate=DataFormat.getDateString(conInfo.getLoanEnd());
        	strInterestRate=DataFormat.formatRate(conInfo.getRate());
        	strPerformRate=DataFormat.formatRate(conInfo.getInterestRate());
        	strStatus=LOANConstant.ContractStatus.getName(conInfo.getStatusID());
        	isCredit=conInfo.getIsCredit();
        	isAssure=conInfo.getIsAssure();
        	isPledge=conInfo.getIsPledge();
        	isImpawn=conInfo.getIsImpawn();
			loanTypeID=conInfo.getLoanTypeID();
			loanID=conInfo.getLoanID();        
			extendCount=conInfo.getExtendCount();
			freeCount=conInfo.getFreeCount();
			strChargeRate=DataFormat.formatRate(conInfo.getChargeRate());
			chargeRateType=conInfo.getChargeRateType();
			//----------------------
			lRiskLevel = conInfo.getRiskLevel();
			//Log.print("----"+lRiskLevel);
			strRiskLevel = LOANConstant.VentureLevel.getName(lRiskLevel);
			//----------------------
        }
%>	

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />
<form name="frm">
<table border="0" class="top" height="215">
  <tr class="tableHeader"> 
    <td class="FormTitle" height="29"><b>�����ͬ��ѯ</b></td>
  </tr>
  <tr> 
    <td valign="top" align=center> 
        <table width="730" border="0" align="center" height="8">
          <tr> 
            <td width="1" height="28">&nbsp;</td>
            <td height="28" colspan="2"><font size="2">��ͬ�ţ�</font></td>
            <td width="225" height="28"><font size="2"> <input type="text" size="17" name="textfield27343" class="box" value="<%=contractCode%>" disabled> </font></td>
            <td height="28" colspan="2"><font size="2"></font></td>
            <td width="260" height="28"><font size="2"></font></td>
            <td width="1" height="28"><font size="2"></font></td>
            <td colspan="5" height="28"><font size="2"></font></td>
          </tr>
          <tr> 
            <td width="1" height="10">&nbsp;</td>
            <td height="10" colspan="2"><font size="2">��λ��</font></td>
            <td colspan=5 height="10"><font size="2"> <input type="text" size="80" name="textfield273432" class="box" value="<%=borrowClientName%>" disabled> </font></td>
            <td colspan="5" height="10"><font size="2"></font></td>
          </tr>
          <tr> 
            <td width="1" height="10">&nbsp;</td>
            <td height="29" width="98"><font size="2">�����</font></td>
            <td height="29" width="22"><font size="2">��</font></td>
            <td width="225" height="10"><font size="2"> <input type="text" size="18" name="textfield273433" class="tar" value="<%=strLoanAmount%>" disabled> </font></td>
            <td width="96" height="29" align="right"><font size="2">������</font></td>
            <td width="19" height="29"> <div align="right"><font size="2">��</font></div></td>
            <td width="260" height="29"><font size="2"> <input type="text" size="18" name="textfield2734332" class="tar" value="<%=strLeftAmount%>" disabled> </font></td>
            <td width="1" height="10"><font size="2"></font></td>
            <td colspan="5" height="10"><font size="2"></font></td>
          </tr>
          <tr> 
            <td width="1" height="29">&nbsp;</td>
            <td height="29" width="98"><font size="2">�������ڣ�</font></td>
            <td height="29" width="22"><font size="2">��</font></td>
            <td width="225" height="29"><font size="2"> <input type="text" size="17" name="textfield273433" class="box" value="<%=strStartDate%>" disabled> </font></td>
            <td width="96" height="29"> <div align="right"><font size="2"> �� </font></div>  </td>
            <td width="19" height="29">&nbsp;</td>
            <td width="260" height="29"><font size="2"> <input type="text" size="17" name="textfield273434" class="box" value="<%=strEndDate%>" disabled> </font></td>
            <td align="right" width="1" height="29"><font size="2"></font></td>
            <td colspan="5" align="right" height="29"><font size="2"></font></td>
          </tr>
          <tr> 
            <td width="1" height="29">&nbsp;</td>
            <td height="29" colspan="2"><font size="2">��ͬ���ʣ�</font></td>
            <td align="left" width="225" height="29"><font size="2"> <input type="text" name="textfield2122" class="box" size="10" value="<%=strInterestRate%>" disabled> %</font></td>
            <td height="29" colspan="2" align="right"><font size="2">ִ�����ʣ�</font></td>
            <td align="left" width="260" height="29"><font size="2"> <input type="text" name="textfield21223" class="box" size="10" value="<%=strPerformRate%>" disabled> % </font></td>
            <td align="right" width="1" height="29">&nbsp;</td>
            <td colspan="5" align="right" height="29">&nbsp;</td>
          </tr>
          <tr> 
            <td width="1" height="29">&nbsp;</td>
            <td height="29" colspan="2"><font size="2">��ͬ״̬��</font></td>
            <td align="left" width="225" height="29"><font size="2"> 
              <input type="text" name="textfield21222" class="box" size="10" value="<%=strStatus%>" disabled>
              </font></td>
            <td height="29" colspan="2" align="right"><font size="2">��ͬ����״̬��</font></td>
            <td align="left" width="260" height="29"><font size="2"> <input type="text" name="textfield21223" class="box" size="10" value="<%=strRiskLevel%>" disabled>&nbsp;</font></td>
            <td align="right" width="1" height="29">&nbsp;</td>
            <td colspan="5" align="right" height="29">&nbsp;</td>
          </tr>  
          <% if ( loanTypeID==LOANConstant.LoanType.WT ){ %>
          <tr>
            <td width="1" height="29">&nbsp;</td>
            <td height="29" colspan="2"><font size="2">�������ʣ�</font></td>
            <td align="left" width="225" height="29"><font size="2"> 
              <input type="text" name="textfield21223" class="box" size="10" value="<%=strChargeRate%>" disabled>
              </font></td>
            <td height="29" colspan="2" align=right>��������ȡ��ʽ��</td>
            <td align="left" width="260" height="29"><font size="2"> 
              <input type="text" name="textfield21223" class="box" size="10" value="<%=LOANConstant.ChargeRatePayType.getName(chargeRateType)%>" disabled>
              </font>
            <td align="right" width="1" height="29">&nbsp;</td>
            <td colspan="5" align="right" height="29">&nbsp;</td>
          </tr>   
          <% } %>       
          <tr> 
            <td width="1" height="29">&nbsp;</td>
            <td height="29" colspan="2"><font size="2">������ʽ��</font></td>
            <td height="29" colspan="4"> <font size="2">
              <input type="checkbox" disabled name="checkbox" value="checkbox" <% if(isCredit==1){%>checked <%}%>>
              ���� 
              <input type="checkbox" disabled  name="checkbox2" value="checkbox" <% if(isAssure==1){%>checked <%}%>>
              ��֤ 
              <input type="checkbox" disabled  name="checkbox3" value="checkbox" <% if(isPledge==1){%>checked <%}%>>
              ��Ѻ 
              <input type="checkbox" disabled name="checkbox4" value="checkbox" <% if(isImpawn==1){%>checked <%}%>>
              ��Ѻ</font></td>
            <td align="right" width="1" height="29">&nbsp;</td>
            <td colspan="5" align="right" height="29">&nbsp;</td>
          </tr>
          <tr> 
            <td width="1" height="29">&nbsp;</td>
            <td height="29" colspan="6"> 
              <hr>
            </td>
            <td align="right" width="1" height="29">&nbsp;</td>
            <td colspan="5" align="right" height="29">&nbsp;</td>
          </tr>
          <tr> 
             <td height="39" align="center" colspan="13"> 
              <input type="button" name="Submit2"  value="��ͬ��ϸ����" class="button" onclick="window.open('<%=Env.EBANK_URL%>loan/query/q036-c.jsp?contractID=<%=contractID%>&action=3&type=<%=loanTypeID%>', '�鿴��ͬ��������', 'toolbar=yes,menubar=yes,resizable=yes,location=yes,width=800,height=600,scrollbars=yes', false)" >
              <input type="button" name="Submit25"  value="����������" class="button" onclick="window.open('<%=Env.EBANK_URL%>loan/query/q004-c.jsp?type=<%=loanTypeID%>&lLoanID=<%=loanID%>&txtAction=query', '�鿴�����������', 'toolbar=yes,menubar=yes,resizable=yes,location=yes,width=800,height=600,scrollbars=yes', false)" >
              <input type="button" name="Submit26"  value="�⻹����������" class="button" onclick="window.open('<%=Env.EBANK_URL%>loan/query/q081-c.jsp?actType=contract&codeBegin=<%=contractCode%>&codeEnd=<%=contractCode%>&typeID=-1','','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes')" <%if (freeCount<=0) {%> disabled <%}%> >
              <input type="button" name="Submit24" value="չ������" class="button" onclick="window.open('<%=Env.EBANK_URL%>loan/query/q072-c.jsp?actType=contract&control=view&codeBegin=<%=contractCode%>&codeEnd=<%=contractCode%>','','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes')" <%if (extendCount<=0) {%> disabled <%}%> >
              <input type="button" name="Submit22" value="ִ�мƻ�" class="button" onclick="window.open('<%=Env.EBANK_URL%>loan/query/q091-c.jsp?actType=contract&lContractID=<%=contractID%>','','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes')">
              <input type="button" name="Submit23"  value="ʵ�ʻ���/�ſ��¼" class="button" onclick="window.open('<%=Env.EBANK_URL%>loan/query/q061-c.jsp?actType=contract&lContractID=<%=contractID%>&contractCode=<%=contractCode%>','','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes')">
            
            </td>
          </tr>
        </table>
      <font size="2"> </font> </td>
  </tr>
</table>
<input type="hidden" name="control" value="view">
</form> 
<%
	OBHtml.showOBHomeEnd(out);
	}
	catch( IException ie )
	{
		//LOANHTML.showExceptionMessage(out,sessionMng,ie,request,response,"ί�пͻ�ѡ��", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return; 
	}
%>
<%@ include file="/common/SignValidate.inc" %> 

