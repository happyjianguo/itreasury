<%
/**
 * ҳ������ ��q021-v.jsp
 * ҳ�湦�� : ���������ѯ-�߼���ѯ
 * ��    �� ��qqgd
 * ��    �� ��2003-09-27
 * ����˵�� ��
 *			  
 * ת��ҳ�� : l001-c.jsp			  
 */
%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import="java.sql.*,
	java.util.*,
	java.net.URLEncoder,
	com.iss.itreasury.loan.util.*,
	com.iss.itreasury.ebank.util.*,			
	com.iss.itreasury.ebank.obdataentity.*,	
	com.iss.itreasury.ebank.obquery.bizlogic.*,
	com.iss.itreasury.ebank.obquery.dataentity.*,
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
        OBHtml.showOBHomeHead(out,sessionMng,"���������ѯ",Constant.YesOrNo.YES);
        long officeID=sessionMng.m_lOfficeID;
        termInfo.reset();
%>	

<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/Control.js"></script>
<safety:resources />
<form name="frm" >
<TABLE border=0 class=top height=375 width="87%">
  <TBODY>
  <TR class="tableHeader">
    <TD class=FormTitle height=29><B>���������ѯ</B></TD></TR>
  <TR>
    <TD height=185>
      <TABLE align=center border=0 width=730>
        <TR>
          <TD colspan=2>�������ͣ�</TD>
          <TD>
		    <select name="typeID" class="box" size="1" onfocus="nextfield='sCodeBegin';">
		       <option value="99"></option>
                <%
              		long loanTypeVal[]=LOANConstant.LoanType.getAllCode(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
              		String loanTypeName="";
              		for ( int i=0;i<loanTypeVal.length;i++ )
              		{
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
          <TD>�������ţ�</TD>
        <%
 		//�������ŷŴ󾵣�����loan_loanForm
		
		String strMagnifierName1 = URLEncoder.encode("��������");							//�Ŵ󾵵�����
		String strFormName1 = "frm";										//��ҳ�������
		String strPrefix1 ="";												////�ؼ�����ǰ׺
		String[] strMainNames1 = {"sCodeBegin"};	//�Ŵ󾵻�����λֵ�б�
		String[] strMainFields1 = { "sApplyCode"};		//�Ŵ󾵻�����λ��Ӧ�ı���ֶ�
		String[] strReturnNames1 = {"codeBegin"};							//�Ŵ󾵷���ֵ�б�(����ֵ)
		String[] strReturnFields1 = {"sApplyCode"};								//�Ŵ󾵷���ֵ(����ֵ)��Ӧ�ı���ֶ��б�
		String   strReturnInitValues1="";						////�Ŵ󾵻�����λ��Ӧ�ĳ�ʼֵ
		String[] strReturnValues1 = {""};								//�Ŵ󾵷���ֵ(����ֵ)��Ӧ�ĳ�ʼֵ
		String[] strDisplayNames1 = {URLEncoder.encode("��������")};				//�Ŵ�С������ʾ����λ����
		String[] strDisplayFields1 = {"sApplyCode"};					//�Ŵ�С������ʾ��λ��Ӧ�ı���ֶ�
		int intIndex1 = 0; 												//ȷ��ѡ����,��0��ʼ,���С��0,��Ĭ��������λ����ѡ��,
		String strMainProperty1 = " ";									//�Ŵ󾵵Ķ�Ӧ�ؼ���λ����
		String strMatchValue1="sApplyCode";									////�Ŵ�Ҫģ��ƥ����ֶ�
		String strNextControls1 = "sCodeEnd";								//������һ������
		String strTitle1="��";
		String strFirstTD1=" ";
		String strSecondTD1=" ";
			
		//���ò����Ŵ󾵵ķ���
		OBMagnifier.showZoomCtrl(out,strMagnifierName1,strFormName1,strPrefix1,strMainNames1,strMainFields1,
			strReturnNames1,strReturnFields1, strReturnInitValues1, strReturnValues1,strDisplayNames1,strDisplayFields1,
			intIndex1,strMainProperty1,"getApplyCode()", strMatchValue1,strNextControls1 ,strTitle1, strFirstTD1, strSecondTD1 );
		%> 
		<%
 		//�������ŷŴ󾵣�����loan_loanForm
 		 		
		String strMagnifierName2 = URLEncoder.encode("��������");							//�Ŵ󾵵�����
		String strFormName2 = "frm";										//��ҳ�������
		String strPrefix2 ="";											////�ؼ�����ǰ׺
		String[] strMainNames2 = {"sCodeEnd"};								//�Ŵ󾵻�����λֵ�б�
		String[] strMainFields2 = { "sApplyCode"};							//�Ŵ󾵻�����λ��Ӧ�ı���ֶ�
		String[] strReturnNames2 = {"codeEnd"};							//�Ŵ󾵷���ֵ�б�(����ֵ)
		String[] strReturnFields2 = {"sApplyCode"};								//�Ŵ󾵷���ֵ(����ֵ)��Ӧ�ı���ֶ��б�
		String   strReturnInitValues2="";						////�Ŵ󾵻�����λ��Ӧ�ĳ�ʼֵ
		String[] strReturnValues2 = {""};								//�Ŵ󾵷���ֵ(����ֵ)��Ӧ�ĳ�ʼֵ
		String[] strDisplayNames2 = {URLEncoder.encode("��������")};				//�Ŵ�С������ʾ����λ����
		String[] strDisplayFields2 = {"sApplyCode"};					//�Ŵ�С������ʾ��λ��Ӧ�ı���ֶ�
		int intIndex2 = 0; 												//ȷ��ѡ����,��0��ʼ,���С��0,��Ĭ��������λ����ѡ��,
		String strMainProperty2 = " ";									//�Ŵ󾵵Ķ�Ӧ�ؼ���λ����
		String strMatchValue2="sApplyCode";									////�Ŵ�Ҫģ��ƥ����ֶ�
		String strNextControls2 = " statusID ";								//������һ������
		String strTitle2="��";
		String strFirstTD2=" align='right' colspan=2 ";
		String strSecondTD2=" ";
	
		OBMagnifier.showZoomCtrl(out,strMagnifierName2,strFormName2,strPrefix2,strMainNames2,strMainFields2,
			strReturnNames2,strReturnFields2, strReturnInitValues2, strReturnValues2,strDisplayNames2,strDisplayFields2,
			intIndex2,strMainProperty2,"getApplyCode()", strMatchValue2,strNextControls2,strTitle2, strFirstTD2, strSecondTD2);
		%>
		</TR>	
			  
        <TR>
          <TD colspan=2>����״̬��</TD>
          <TD colspan=5>
  		  <select class=box name="statusID" onfocus="nextfield='inputUser';">
			<option value="99"></option>
                <%
              		long loanStatusVal[]=LOANConstant.LoanStatus.getAllCode();
              		String loanStatusName="";
              		for ( int i=0;i<loanStatusVal.length;i++ )
              		{
              			loanStatusName=LOANConstant.LoanStatus.getName(loanStatusVal[i]);
              	%> 
					<option value="<%=loanStatusVal[i]%>"><%=loanStatusName%></option>
				<%
					}
				%>	
		</select>
		  </TD>
          </TR>
 		<TR>
         <%
       	String strMagnifierName9 = URLEncoder.encode("�����������");							//�Ŵ󾵵�����
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
		String strNextControls9 = "searchlow";								//������һ������
		String strTitle9="<FONT size=2>�����������</FONT>";
		String strFirstTD9=" colspan=2 ";
		String strSecondTD9=" ";
		boolean blnIsOptional9=false;
		OBMagnifier.showZoomCtrl(out,strMagnifierName9,strFormName9,strPrefix9,strMainNames9,strMainFields9,
			strReturnNames9,strReturnFields9, strReturnInitValues9, strReturnValues9,strDisplayNames9,strDisplayFields9,
			intIndex9,strMainProperty9,"getLoanUser()", strMatchValue9,strNextControls9 ,strTitle9, strFirstTD9, strSecondTD9 );
		%>  
		
        <TR>
        
        <TR>
          <TD colSpan=12 height=16>  <HR> </TD>
       </TR>
    <tr> 
      <td  colspan=2>������:  �ɣ�</td>
      <td> 
        <script language="javascript">
        	createAmountCtrl("frm","amountBeginStr","","amountEndStr","");
          </script>
      </td>
      <td>&nbsp;</td>
      <td align="right">����</td>
      <td> 
        <script language="javascript">
        	createAmountCtrl("frm","amountEndStr","","loanDateBegin","");
          </script>
      </td>
      <td colspan=3></td>
    </tr>
    <tr> 
      <td  colspan=2>��������:   ��</td>
      <td> 
      	<fs_c:calendar 
         	    name="loanDateBegin"
	          	value="" 
	          	properties="nextfield ='loanDateEnd'" 
	          	size="12"/>
      </td>
      <td>&nbsp;</td>
      <td align="right">��</td>
      <td> 
     	 <fs_c:calendar 
         	    name="loanDateEnd"
	          	value="" 
	          	properties="nextfield ='intervalNum'" 
	          	size="12"/>
      </td>
	  <td colspan=3>&nbsp;</td>
    </tr>
    <tr> 
      <td colspan=2>��������:</td>
      <td >
	    <input class=box  type="text" name="intervalNum" size=8 maxlength="5" onFocus="nextfield='inputDateBegin'">��
	  </td>
      <td colspan=2>&nbsp; </td>
      <td colspan=4>&nbsp;</td>
    </tr>
    <tr> 
      <td  colspan=2>�����ύ����:   ��</td>
      <td> 
      		<fs_c:calendar 
         	    name="inputDateBegin"
	          	value="" 
	          	properties="nextfield ='inputDateEnd'" 
	          	size="12"/>
      </td>
      <td>&nbsp;</td>
      <td align="right">��</td>
      <td> 
     		 <fs_c:calendar 
	         	    name="inputDateEnd"
		          	value="" 
		          	properties="nextfield ='assureTypeID'" 
		          	size="12"/>
      </td>
	  <td colspan=3>&nbsp;</td>
    </tr>
<!--/////////////////////////////////////////////////////////////////////////////////// -->
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
        <select name="creditLevelID" class="BOX" size="1" onFocus="nextfield='isPartner';">
          <option value=-1></option>
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
        <select name="isCircle" class="BOX" size="1" onFocus="nextfield='searchlow';">
          <option value=-1>   </option>
          <option value=1>��</option>
          <option value=2>��</option>
        </select>
      </td>
       <td colspan=4> </td>
      </tr>
      <tr>
        <td colspan=12> <HR> </td>
      </tr>
 
    <tr> 
      <td colSpan="5">&nbsp;</td>
      <td colspan="4" align="right"> 
        <input class=button name=searchlow onClick="confirmSave(frm)" type="button" value=" �� �� " onFocus="nextfield='submitfunction';">
        <input class=button name=searchhign onClick="location.href='q020-v.jsp'" type="button" value=" �� �� ">
      </td>
    </tr>
	</TABLE></TD></TR></TBODY></TABLE>
  <input type="hidden" name="searchAction" value="search">
  <input type="hidden" name="queryLevel" value="high">
	
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
  
  if (frm.inputDateBegin.value!="")
  {
	if (!checkDateStart(frm.inputDateBegin,frm.inputDateBegin.value,"�ύ��ʼ����"))
	  {
		 return false;
	  }
  }   
 if (frm.inputDateEnd.value!="")
  {
 
	if (!checkDateEnd(frm.inputDateEnd,frm.inputDateEnd.value,"�ύ��������"))
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
  	frm.action="q022-c.jsp";
    frm.submit();
	}
firstFocus(frm.typeID);
//setSubmitFunction("confirmSave(frm)");
setFormName("frm");
</script>

<script language="javascript">
	function  getApplyCode(code)
	{
		var sql="select id, sApplyCode from loan_loanForm  where nBorrowClientID=<%=sessionMng.m_lClientID%> and NCURRENCYID=<%=sessionMng.m_lCurrencyID%> order by sApplyCode";
		return sql;	
	}
	function getLoanUser()
	{
		//modified by mzh_fu 2007-3-21 ���˰��´���������
		var sql="select distinct a.nInputUserID as ID,b.sName from Loan_loanForm a,userInfo b where b.id=a.nInputUserID and a.nofficeid=<%=sessionMng.m_lOfficeID%> and b.nofficeid=a.nofficeid";
		return sql;
	}
</script>	
<%
	OBHtml.showOBHomeEnd(out);
	}
	catch( IException ie )
	{
		//LOANHTML.showExceptionMessage(out,sessionMng,ie,request,response,"��������߼���ѯ", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return; 
	}
%>
 <%@ include file="/common/SignValidate.inc" %>

