<%
/**
 * ҳ������ ��q030-v.jsp
 * ҳ�湦�� : �����ͬ��ѯ һ���ѯҳ��
 * ��    �� ��qqgd
 * ��    �� ��2003-09-27
 * ����˵�� ��
 *			  
 */
%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import="java.sql.*,
	java.util.*,
	java.net.URLEncoder,
	com.iss.itreasury.ebank.util.*,			
	com.iss.itreasury.ebank.obdataentity.*,	
	com.iss.itreasury.ebank.obquery.bizlogic.*,
	com.iss.itreasury.ebank.obquery.dataentity.*,
	com.iss.itreasury.loan.loancommonsetting.dataentity.*,
	com.iss.itreasury.loan.util.*,
	com.iss.itreasury.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<jsp:useBean id="termInfo" scope="session" class="com.iss.itreasury.ebank.obquery.dataentity.OBQueryTermInfo"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%
    try{
 		//�ж��Ƿ��¼
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
        OBHtml.showOBHomeHead(out,sessionMng,"��ͬ��ѯ",Constant.YesOrNo.YES);
        
		boolean isdq=false;
		termInfo.reset();	           
%>	

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />
<form name="frm" >
<TABLE border=0 class=top width="87%">
  <TBODY>
  <TR class="tableHeader">
    <TD class=FormTitle height=29><B>�����ͬ��ѯ</B></TD></TR>
  <TR>
    <TD>
      <TABLE align=center border=0 width=730>
        <TR>
          <TD width="80"><FONT size=2>�������ͣ�</FONT></TD>
		  <TD>&nbsp;</TD>
          <TD width="100">
		  	<select name="typeID" class="box" size="1" onfocus="nextfield='contractCodeFrom';">
		       <option value="99"></option>
                <%
              		long loanTypeVal[]=LOANConstant.LoanType.getAllCode(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
              		String loanTypeName="";
              		for ( int i=0;i<loanTypeVal.length;i++ )
              		{
              			if ( loanTypeVal[i]==LOANConstant.LoanType.TX )
              				continue;
              			loanTypeName=LOANConstant.LoanType.getName(loanTypeVal[i]);
              	%> 
                   <option value="<%=loanTypeVal[i]%>"><%=loanTypeName%></option>
                <%      
                	}
                %>		   
            </select>		  
		  </TD>
		  <td colspan=3>&nbsp;</td>
          </TR>
        <TR>
          <TD><FONT size=2>��ͬ��ţ�</FONT></td>
        <%
 		//�������ŷŴ󾵣�����loan_loanForm
		String strMagnifierName1 = URLEncoder.encode("��ͬ���");							//�Ŵ󾵵�����
		String strFormName1 = "frm";										//��ҳ�������
		String strPrefix1 ="";												////�ؼ�����ǰ׺
		String[] strMainNames1 = {"contractCodeFrom"};				//�Ŵ󾵻�����λֵ�б�
		String[] strMainFields1 = { "sContractCode"};				//�Ŵ󾵻�����λ��Ӧ�ı���ֶ�
		String[] strReturnNames1 = {"codeBegin"};							//�Ŵ󾵷���ֵ�б�(����ֵ)
		String[] strReturnFields1 = {"sContractCode"};								//�Ŵ󾵷���ֵ(����ֵ)��Ӧ�ı���ֶ��б�
		String   strReturnInitValues1="";						////�Ŵ󾵻�����λ��Ӧ�ĳ�ʼֵ
		String[] strReturnValues1 = {""};								//�Ŵ󾵷���ֵ(����ֵ)��Ӧ�ĳ�ʼֵ
		String[] strDisplayNames1 = {URLEncoder.encode("��ͬ���")};				//�Ŵ�С������ʾ����λ����
		String[] strDisplayFields1 = {"sContractCode"};					//�Ŵ�С������ʾ��λ��Ӧ�ı���ֶ�
		int intIndex1 = 0; 												//ȷ��ѡ����,��0��ʼ,���С��0,��Ĭ��������λ����ѡ��,
		String strMainProperty1 = " ";									//�Ŵ󾵵Ķ�Ӧ�ؼ���λ����
		String strMatchValue1="sContractCode";									////�Ŵ�Ҫģ��ƥ����ֶ�
		String strNextControls1 = "contractCodeTo";								//������һ������
		String strTitle1="��";
		String strFirstTD1=" width=50 ";
		String strSecondTD1=" ";
			
		//���ò����Ŵ󾵵ķ���
		OBMagnifier.showZoomCtrl(out,strMagnifierName1,strFormName1,strPrefix1,strMainNames1,strMainFields1,
			strReturnNames1,strReturnFields1, strReturnInitValues1, strReturnValues1,strDisplayNames1,strDisplayFields1,
			intIndex1,strMainProperty1,"getContractCode()", strMatchValue1,strNextControls1 ,strTitle1, strFirstTD1, strSecondTD1 );
		%> 

		<%
 		//�������ŷŴ󾵣�����loan_loanForm
		String strMagnifierName2 = URLEncoder.encode("��ͬ���");							//�Ŵ󾵵�����
		String strFormName2 = "frm";										//��ҳ�������
		String strPrefix2 ="";											////�ؼ�����ǰ׺
		String[] strMainNames2 = {"contractCodeTo"};								//�Ŵ󾵻�����λֵ�б�
		String[] strMainFields2 = { "sContractCode"};							//�Ŵ󾵻�����λ��Ӧ�ı���ֶ�
		String[] strReturnNames2 = {"codeEnd"};							//�Ŵ󾵷���ֵ�б�(����ֵ)
		String[] strReturnFields2 = {"sContractCode"};								//�Ŵ󾵷���ֵ(����ֵ)��Ӧ�ı���ֶ��б�
		String   strReturnInitValues2="";						////�Ŵ󾵻�����λ��Ӧ�ĳ�ʼֵ
		String[] strReturnValues2 = {""};								//�Ŵ󾵷���ֵ(����ֵ)��Ӧ�ĳ�ʼֵ
		String[] strDisplayNames2 = {URLEncoder.encode("��ͬ���")};				//�Ŵ�С������ʾ����λ����
		String[] strDisplayFields2 = {"sContractCode"};					//�Ŵ�С������ʾ��λ��Ӧ�ı���ֶ�
		int intIndex2 = 0; 												//ȷ��ѡ����,��0��ʼ,���С��0,��Ĭ��������λ����ѡ��,
		String strMainProperty2 = " ";									//�Ŵ󾵵Ķ�Ӧ�ؼ���λ����
		String strMatchValue2="sContractCode";									////�Ŵ�Ҫģ��ƥ����ֶ�
		String strNextControls2 = " statusID ";								//������һ������
		String strTitle2="��";
		String strFirstTD2=" align='right' colspan=2 ";
		String strSecondTD2=" ";
	
		OBMagnifier.showZoomCtrl(out,strMagnifierName2,strFormName2,strPrefix2,strMainNames2,strMainFields2,
			strReturnNames2,strReturnFields2, strReturnInitValues2, strReturnValues2,strDisplayNames2,strDisplayFields2,
			intIndex2,strMainProperty2,"getContractCode("+strMainNames2[0]+".value)", strMatchValue2,strNextControls2,strTitle2, strFirstTD2, strSecondTD2);
		%>
		</TR>	
			  
        <TR>
          <TD><FONT size=2>��ͬ״̬��</FONT></TD>
          <td>&nbsp;</td>
          <TD colspan=5>
  		  		  
  		  <select class=box name="statusID" onfocus="nextfield='inputUser';">
					<option value="99"></option>
                <%
              		long contractStatusVal[]=LOANConstant.ContractStatus.getAllCode();
              		String contractStatusName="";
              		for ( int i=0;i<contractStatusVal.length;i++ )
              		{
              			contractStatusName=LOANConstant.ContractStatus.getName(contractStatusVal[i]);
              	%> 
					<option value="<%=contractStatusVal[i]%>"><%=contractStatusName%></option>
				<%
					}
				%>	
		 </select>		  
		  </TD>
          </TR>
          <tr>
        <%
       	String strMagnifierName9 = URLEncoder.encode("��ͬ������");							//�Ŵ󾵵�����
		String strFormName9 = "frm";										//��ҳ�������
		String strPrefix9 ="";											////�ؼ�����ǰ׺
		String[] strMainNames9 = {"inputUser"};	//�Ŵ󾵻�����λֵ�б�
		String[] strMainFields9 = { "sName"};		//�Ŵ󾵻�����λ��Ӧ�ı���ֶ�
		String[] strReturnNames9 = {"inputUserID"};							//�Ŵ󾵷���ֵ�б�(����ֵ)
		String[] strReturnFields9 = {"ID"};								//�Ŵ󾵷���ֵ(����ֵ)��Ӧ�ı���ֶ��б�
		String   strReturnInitValues9="";						////�Ŵ󾵻�����λ��Ӧ�ĳ�ʼֵ
		String[] strReturnValues9 = {"-1"};								//�Ŵ󾵷���ֵ(����ֵ)��Ӧ�ĳ�ʼֵ
		String[] strDisplayNames9 = {URLEncoder.encode("���"),URLEncoder.encode("����")};				//�Ŵ�С������ʾ����λ����
		String[] strDisplayFields9 = {"ID","sName"};					//�Ŵ�С������ʾ��λ��Ӧ�ı���ֶ�
		int intIndex9 = 0; 												//ȷ��ѡ����,��0��ʼ,���С��0,��Ĭ��������λ����ѡ��,
		String strMainProperty9 = " ";									//�Ŵ󾵵Ķ�Ӧ�ؼ���λ����
		String strMatchValue9="ID";									////�Ŵ�Ҫģ��ƥ����ֶ�
		String strNextControls9 = "amountBeginStr";								//������һ������
		String strTitle9="<FONT size=2>��ͬ������</FONT>";
		String strFirstTD9=" colspan=2 ";
		String strSecondTD9=" ";
		boolean blnIsOptional9=false;
		OBMagnifier.showZoomCtrl(out,strMagnifierName9,strFormName9,strPrefix9,strMainNames9,strMainFields9,
			strReturnNames9,strReturnFields9, strReturnInitValues9, strReturnValues9,strDisplayNames9,strDisplayFields9,
			intIndex9,strMainProperty9,"getContractUser()", strMatchValue9,strNextControls9 ,strTitle9, strFirstTD9, strSecondTD9 );
		%>  
        </TR>
        <TR><td colspan = 6><HR></td></TR>		
	    <TR>
          <TD><FONT size=2>������:</FONT></TD>
          <td align="right"><FONT size=2>�ɣ�</Font></td>
          <TD>
		  <script language="javascript">
	        	createAmountCtrl("frm","amountBeginStr","","amountEndStr","");
          </script>
		  </TD>
		  <td>&nbsp;</td>
		  <TD align="right"><FONT size=2>����</FONT></TD>
		  <TD>
 		  <script language="javascript">
        		createAmountCtrl("frm","amountEndStr","","loanDateBegin","");
          </script>
		  </TD>
		</TR>
        <TR>
          <TD><FONT size=2>��������:</FONT></TD>
          <td align="right"><FONT size=2>��</Font></td>
          <TD>
          <FONT size=2>
          <fs_c:calendar 
			         	    name="loanDateBegin"
				          	value="" 
				          	properties="nextfield ='loanDateEnd'" 
				          	size="12"/>
		  </FONT>
		  </TD>
		  <td>&nbsp;</td>
		  <TD align="right"><FONT size=2>��</FONT></TD>
		  <TD>
          <FONT size=2>
         	 <fs_c:calendar 
	         	    name="loanDateEnd"
		          	value="" 
		          	properties="nextfield ='intervalNum'" 
		          	size="12"/>
		  </FONT>
		  </TD>
		  
          </TR>	   
        <TR>
          <TD><FONT size=2>��������:</FONT></TD>
          <td align="right"><FONT size=2>&nbsp;</Font></td>
          <TD>
          <input class=box  type="text" name="intervalNum" size=8 maxlength="5" onfocus="nextfield='searchlow'">��
		  </TD>
		  <td colspan=2>&nbsp;</td>
          </TR>
<tr>
<td colspan="6">&nbsp;</td>
</tr>	   	   
<tr>
<td colspan="6">&nbsp;</td>
 <TD><FONT size=2>
 <INPUT class=button name=searchlow onclick="confirmSave(frm)" type="button" value=" �� �� " onfocus="nextfield='submitfunction';">
 <INPUT class=button name=searchhign onclick="location.href='q031-v.jsp'" type="button" value=" �� �� �� �� "> </FONT></TD>
</tr>
<tr>
<td colspan="6">&nbsp;</td>
</tr>	   
	  </TABLE>
		  
		  </TD></TR></TBODY></TABLE>
<input type="hidden" name="control" value="view">
<input type="hidden" name="queryLevel" value="low">
<input type="hidden" name="searchAction" value="search">
<input type=hidden name=txtAction value=query>
<input type="hidden" name="purpose" value="">
</form>
<script language="JavaScript">
function  confirmSave(frm)
{
  if (frm.loanDateBegin.value!="")
  {
	if (!checkDateStart(frm.loanDateBegin,frm.loanDateBegin.value,"������ʼ����"))
	  {
		 return false;
	  }
  }   
 if (frm.loanDateEnd.value!="")
  {
 
	if (!checkDateEnd(frm.loanDateEnd,frm.loanDateEnd.value,"�����������"))
	  {
		 return false;
	  }  
  }	  
///////////////////////////
	if (!checkAmount(frm.amountBeginStr,0,"���"))
	  {
		 return false;
	  }  
		if (!checkAmount(frm.amountEndStr,0,"���"))
	  {
		 return false;
	  }  
///////////////////////////	    
   if (!InputValid(frm.intervalNum, 0, "int", 0, 0, 0,"��������")) 
		{
			return false;
		}
		//////////////////////////////   
    frm.control.value="view";
	frm.action="q032-c.jsp";
    frm.submit();
    
	}
firstFocus(frm.typeID);
//setSubmitFunction("confirmSave(frm)");
setFormName("frm");
</script> 
<script language="javascript">
	
	function  getContractCode(code)
	{
		var sql="select id, sContractCode from loan_ContractForm where nTypeID<><%=LOANConstant.LoanType.TX%> and (nBorrowClientID=<%=sessionMng.m_lClientID%> or nConsignClientID=<%=sessionMng.m_lClientID%>) and NCURRENCYID=<%=sessionMng.m_lCurrencyID%> order by sContractCode";
		return sql;	
	}
	function getContractUser()
	{
		var sql="select distinct a.nInputUserID as ID,b.sName from Loan_ContractForm a,userInfo b where b.id=a.nInputUserID";
		return sql;
	}
</script>	
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

