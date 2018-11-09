<%
/**
 * ҳ������ ��q031-v.jsp
 * ҳ�湦�� : ��ͬ��ѯ���߼���ѯҳ��
 * ��    �� ��qqgd
 * ��    �� ��2003-09-27
 * ����˵�� ��
 *			  
 * ת��ҳ�� : 
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
        OBHtml.showOBHomeHead(out,sessionMng,"��ͬ�߼���ѯ",Constant.YesOrNo.YES);
        
		boolean isdq=false;
		termInfo.reset();	              
%>	

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="javascript" src="/itreasury/js/jquery-1.3.2.js"></script>

<safety:resources />
<form name="frm" >
<TABLE border=0 class=top width="100%">
  <TBODY>
  <TR class="tableHeader">
    <TD class=FormTitle height=29><B>�����ͬ��ѯ</B></TD></TR>
	<TR>
	<TD>
     <table align=center border=0 width=100%>
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
			intIndex1,strMainProperty1,"getContractCode("+strMainNames1[0]+".value)", strMatchValue1,strNextControls1 ,strTitle1, strFirstTD1, strSecondTD1 );
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
		LOANMagnifier.showZoomCtrl(out,strMagnifierName9,strFormName9,strPrefix9,strMainNames9,strMainFields9,
			strReturnNames9,strReturnFields9, strReturnInitValues9, strReturnValues9,strDisplayNames9,strDisplayFields9,
			intIndex9,strMainProperty9,"getContractUser()", strMatchValue9,strNextControls9 ,strTitle9, strFirstTD9, strSecondTD9 );
		%>  
		
        </TR>
        
        <TR><td colspan = 6>
            <HR></td>
          </TR>		
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
          <input class=box  type="text" name="intervalNum" size=8 maxlength="5" onfocus="nextfield='assureTypeID'">��
		  </TD>
		  <td colspan=2>&nbsp;</td>
          </TR>
    <tr> 
      <td colspan=9 height=18> 
        <hr>
      </td>
    </tr>
    <tr> 
      <td colspan=2>��֤��ʽ:</td>
      <td  >
        <select name="assureTypeID" class="BOX" size="1" onFocus="nextfield='creditLevelID';">
          <option value=-1> </option>
                <%
              		long assTypeVal[]=LOANConstant.AssureType.getAllCode();
              		String assTypeName="";
              		for ( int i=0;i<assTypeVal.length;i++ )
              		{
              			assTypeName=LOANConstant.AssureType.getName(assTypeVal[i]);
              	%>           
          <option value=<%=assTypeVal[i]%> ><%=assTypeName%></option>
          		<%
          			}
          		%>	
        </select>
	  </td>
      <td > 
          ���õȼ�:
      </td>
      <td > 
	  <select name="creditLevelID" class="BOX" size="1" onFocus="nextfield='isPartner'" >
                <option value="-1"></option>
			<%
				long[] CreditTmp = LOANConstant.CreditLevel.getAllCode();
				for(int i =0;i< CreditTmp.length;i++)
				{

			%>
						<OPTION value="<%=CreditTmp[i]%>">
							<%=LOANConstant.CreditLevel.getName(CreditTmp[i])%>
						</OPTION>
			<%
				}
			%>
              </select> 
      </td>
      <td >�ɶ�: </td>
      <td > 
        <select name="isPartner" class="BOX" size="1" onFocus="nextfield='isTechnical';">
          <option value=-1> </option>
          <option value=1>��</option>
          <option value=2>��</option>
        </select>
      </td>
	  <td colspan=2>&nbsp;</td>
    </tr>
    <tr> 
      <td  colspan=2>���Ĵ���: </td>
      <td > 
        <select name="isTechnical" class="BOX" size="1" onFocus="nextfield='isCircle';">
          <option value=-1> </option>
          <option value=1>��</option>
          <option value=2>��</option>
        </select>
      </td>
      <td >ѭ������:</td>
      <td > 
        <select name="isCircle" class="BOX" size="1" onFocus="nextfield='ParentCorpName';">
          <option value=-1>   </option>
          <option value=1>��</option>
          <option value=2>��</option>
        </select>
      </td>
      <td ></td>
      <td ></td>
      <td colspan=2> </td>
      </tr>
 
      <tr>
      <%
 		//�ϼ����ܲ��ŷŴ�
		String strMagnifierName7 = URLEncoder.encode("�ϼ����ܵ�λ");							//�Ŵ󾵵�����
		String strFormName7 = "frm";											//��ҳ�������
		String strPrefix7 ="";												////�ؼ�����ǰ׺
		String[] strMainNames7 = {"ParentCorpName"};							//�Ŵ󾵻�����λֵ�б�
		String[] strMainFields7 = { "sName"};								//�Ŵ󾵻�����λ��Ӧ�ı���ֶ�
		String[] strReturnNames7 = {"parentCorpID"};						//�Ŵ󾵷���ֵ�б�(����ֵ)
		String[] strReturnFields7 = {"ID"};									//�Ŵ󾵷���ֵ(����ֵ)��Ӧ�ı���ֶ��б�
		String   strReturnInitValues7="";						////�Ŵ󾵻�����λ��Ӧ�ĳ�ʼֵ
		String[] strReturnValues7 = {""};								//�Ŵ󾵷���ֵ(����ֵ)��Ӧ�ĳ�ʼֵ
		String[] strDisplayNames7 = {URLEncoder.encode("�ͻ����"),URLEncoder.encode("�ͻ�����")};				//�Ŵ�С������ʾ����λ����
		String[] strDisplayFields7 = {"sCode","sName"};					//�Ŵ�С������ʾ��λ��Ӧ�ı���ֶ�
		int intIndex7 = 0; 												//ȷ��ѡ����,��0��ʼ,���С��0,��Ĭ��������λ����ѡ��,
		String strMainProperty7 = "";									//�Ŵ󾵵Ķ�Ӧ�ؼ���λ����
		String strMatchValue7="sCode";									////�Ŵ�Ҫģ��ƥ����ֶ�
		String strNextControls7 = "riskLevelID";								//������һ������
		String strTitle7="�ϼ����ܲ���";
		String strFirstTD7=" colspan=2 ";
		String strSecondTD7=" ";
		String strSQL = "SELECT id,sCode,sName,SLOANCARDNO,SLOANCARDPWD FROM client where nStatusID="
						+ Constant.RecordStatus.VALID
						+ " and nOfficeID= "
						+ sessionMng.m_lOfficeID + " order by id";

		//OBMagnifier.showZoomCtrl(out,strMagnifierName7,strFormName7,strPrefix7,strMainNames7,strMainFields7,
		//	strReturnNames7,strReturnFields7, strReturnInitValues7, strReturnValues7,strDisplayNames7,strDisplayFields7,
		//	intIndex7,strMainProperty7,"getClient("+strMainNames7[0]+".value)", strMatchValue7,strNextControls7 ,strTitle7, strFirstTD7, strSecondTD7 );	
			
			OBMagnifier.showZoomCtrl(out,strMagnifierName7,strFormName7,strPrefix7,strMainNames7,strMainFields7,
			strReturnNames7,strReturnFields7, strReturnInitValues7, strReturnValues7,strDisplayNames7,strDisplayFields7,
			intIndex7,strMainProperty7, strSQL, strMatchValue7,strNextControls7 ,strTitle7, strFirstTD7, strSecondTD7 );					
		%>
		 
      <td height=13 >�������״̬��</td>
            <td height=13 > 
              <select class='box' name="riskLevelID" onfocus="nextfield='typdID1';">
                <option value=-1>��</option>
              <%
               	long ventureVal[]=LOANConstant.VentureLevel.getAllCode();
              	String ventureName="";
              	for ( int i=0;i<ventureVal.length;i++ )
              	{
              		ventureName=LOANConstant.VentureLevel.getName(ventureVal[i]);
              %> 
           		<option value="<%=ventureVal[i]%>"><%=ventureName%></option>
                <%	
              	}
              %> 
             </select>
            </td>
            <td height=16 >���������ࣺ</td>
            <td height=13 > 
            <%LOANHTML.showAreaTypeListControl(out,"typeID1", -1, " onfocus=nextfield='typeID2';" );%>
            </td>
            <td colspan=2>&nbsp;</td>
          </tr>
           <tr> 
            <td height=16 colspan=2><% if (!isdq) { %>   ����ҵ����1��<%}%></td>
            <td height=13 colspan=3> 
            <% if (!isdq) { %>   
            <%LOANHTML.showIndustryType1ListControl(out,"typeID2", -1, " onfocus=nextfield='document.frm.typeID3'; ");%>                          
            <%}%>
            </td>
            <td height=13 ><% if (!isdq) { %>   ����ҵ����2��<%}%></td>
           
            <td>
            <% if (!isdq) { %>   
            <%LOANHTML.showIndustryType2ListControl(out,"typeID3", -1, " onfocus=nextfield='searchlow';" );%>
            <%}%>
            </td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
      
      </tr>
    <!--
**********************************************************************************************************
**********************************************************************************************************
-->
    <tr> 
      <td colspan="6">&nbsp;</td>
      <td colspan="3"> 
        <input class=button name=searchlow onClick="confirmSave(frm)" type="button" value=" �� �� " onFocus="nextfield='submitfunction';">
        <input class=button name=searchhign onClick="location.href='q030-v.jsp?txtAction=First'" type="button" value=" �� �� ">
      </td>
    </tr>
  </table>
  </TD>
  </TR>
  </TBODY></TABLE>
<input type="hidden" name="control" value="view">
<input type="hidden" name="queryLevel" value="high">
<input type="hidden" name="searchAction" value="search">
<input type=hidden name=txtAction value=query>


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
	
	function  getClient(client)
	{
		var sql="select id,scode,sName from client where nStatusID=<%=Constant.RecordStatus.VALID%> order by sCode";

		return sql;
	}
	function  getContractCode(code)
	{
		var sql="select id, sContractCode from loan_ContractForm where nTypeID<><%=LOANConstant.LoanType.TX%>  and NCURRENCYID=<%=sessionMng.m_lCurrencyID%> order by sContractCode";
		return sql;	
	}
	function  getAccount(account)
	{
		var sql="select ID,SACCOUNTNO from sett_account where nStatusID=<%=Constant.RecordStatus.VALID%> order by id";
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

