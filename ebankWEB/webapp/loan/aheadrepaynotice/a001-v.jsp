<%@page contentType="text/html;charset=gbk"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
    //response.setHeader("Cache-Control","no-stored");
	//response.setHeader("Pragma","no-cache");
	//response.setDateHeader("Expires",0);
%>
<%@page import="java.util.*,
java.net.URLEncoder,
com.iss.itreasury.loan.util.*,
com.iss.itreasury.util.*,
com.iss.itreasury.ebank.util.*,
com.iss.itreasury.loan.contract.dataentity.*
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
		
		//��ʾ�ļ�ͷ
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
%>

<SCRIPT language="javascript" src="/webob/js/Control.js"></SCRIPT>
<SCRIPT language=JavaScript src="/webob/js/Check.js"></SCRIPT>
<safety:resources />
<form name="frmFind" action="../aheadrepaynotice/a002-c.jsp" method="post">

<TABLE border=0 class=top width=740>
  <TBODY>
  <TR class="tableHeader">
      <TD class=FormTitle height=29><B>�����֪ͨ����������</B></TD>
    </TR>
  <TR>
    <TD align=center>
        <TABLE align=center border=0 width=691>
          <TBODY> 
          <TR> 
            <td width="1" height="2"><font color=#FF0000><strong>*</strong></font></td>
<%
		String strMagnifierName = URLEncoder.encode("��ͬ���");							//�Ŵ󾵵�����
		String strFormName = "frmFind";										//��ҳ�������
		String[] strMainNames1 = {"ctrlContractIDFromCtrl"};	//�Ŵ󾵻�����λֵ�б�
		String[] strMainFields1 = {"sContractCode"};		//�Ŵ󾵻�����λ��Ӧ�ı���ֶ�
		String[] strReturnNames1 = {"ctrlContractIDFrom"};							//�Ŵ󾵷���ֵ�б�(����ֵ)
		String[] strReturnFields1 = {"ID"}; //�Ŵ󾵷���ֵ(����ֵ)��Ӧ�ı���ֶ��б�	
		String sTempValue = "-1";
		String[] strReturnValues1 = {sTempValue};				//�Ŵ󾵷���ֵ(����ֵ)��Ӧ�ĳ�ʼֵ
		String[] strDisplayNames1 = {URLEncoder.encode("��ͬ���")};				//�Ŵ�С������ʾ����λ����
		String[] strDisplayFields1 = {"sContractCode"};					//�Ŵ�С������ʾ��λ��Ӧ�ı���ֶ�
		int intIndex = 0; 												//ȷ��ѡ����,��0��ʼ,���С��0,��Ĭ��������λ����ѡ��,
		String strReturnInitValues = "";									//�Ŵ󾵵Ķ�Ӧ�ؼ���λ����
		
		//���ò����Ŵ󾵵ķ���
		OBMagnifier.showZoomCtrl(out,strMagnifierName,strFormName,"",
		strMainNames1,strMainFields1,strReturnNames1,strReturnFields1,strReturnInitValues,
		strReturnValues1,strDisplayNames1,strDisplayFields1,
		intIndex,"","getContractCode()","","ctrlPayCtrl", "��ͬ���","","");
%>
            <td colspan="3" height="2">&nbsp;</td>
          </TR>
          <TR> 
            <td width="1" height="2"><font color=#FF0000><strong>*</strong></font></td>
<%
		 strMagnifierName = URLEncoder.encode("�ſ�֪ͨ�����");							//�Ŵ󾵵�����
		 strFormName = "frmFind";										//��ҳ�������
		 String[] strMainNames = {"ctrlPayCtrl"};	//�Ŵ󾵻�����λֵ�б�
		 String[] strMainFields = {"sCode"};		//�Ŵ󾵻�����λ��Ӧ�ı���ֶ�
		 String[] strReturnNames = {"ctrlPayID"};							//�Ŵ󾵷���ֵ�б�(����ֵ)
		 String[] strReturnFields = {"ID"}; //�Ŵ󾵷���ֵ(����ֵ)��Ӧ�ı���ֶ��б�	
		 String[] strReturnValues = {sTempValue};				//�Ŵ󾵷���ֵ(����ֵ)��Ӧ�ĳ�ʼֵ
		 String[] strDisplayNames = {URLEncoder.encode("�ſ�֪ͨ�����")};				//�Ŵ�С������ʾ����λ����
		 String[] strDisplayFields = {"sCode"};					//�Ŵ�С������ʾ��λ��Ӧ�ı���ֶ�
		intIndex = 0; 												//ȷ��ѡ����,��0��ʼ,���С��0,��Ĭ��������λ����ѡ��,
		strReturnInitValues = "";									//�Ŵ󾵵Ķ�Ӧ�ؼ���λ����
		
		//���ò����Ŵ󾵵ķ���
		OBMagnifier.showZoomCtrl(out,strMagnifierName,strFormName,"",
		strMainNames,strMainFields,strReturnNames,strReturnFields,strReturnInitValues,
		strReturnValues,strDisplayNames,strDisplayFields,
		intIndex,"","getPayCode()","","next", "�ſ�֪ͨ�����","","");
%>
            <td colspan="1" height="2">&nbsp;</td>
            <td colspan="2" height="2">&nbsp; </td>
          </TR>
          <TR> 
            <td width="1" height="2">&nbsp;</td>
            <td colspan="5" height="2"> 
              <hr>
            </td>
          </TR>
          <TR> 
            <TD width="1" height=2>&nbsp;</TD>
            <TD colSpan="5" height=2> 
              <DIV align="right"><FONT size=2> 
                <INPUT class=button name="next" onFocus="nextfield='submitfunction';" onclick="confirmSave()" type=button value=" �� �� ">
                <!--INPUT class=button name="link"  onclick="goPage()" type=button value=" ���Ӳ��� "-->
                </FONT></DIV>
            </TD>
          </TR>
          </TBODY>
        </TABLE>
      </TD></TR></TBODY></TABLE>
<P><BR></P>
</form>

<script language="javascript">
function goPage()
{
	window.location = "../aheadrepaynotice/a009-v.jsp?action=1";
}

function getContractCode()
{
	var sql =" SELECT id,sContractCode FROM loan_contractForm "  ;
	
	sql += "   WHERE  (nStatusID="	 + <%=LOANConstant.ContractStatus.ACTIVE%>;
	sql += "   OR nStatusID="	 + <%=LOANConstant.ContractStatus.EXTEND%> + ")";
	//sql += "   AND  nInputUserID="	+ <%=sessionMng.m_lUserID%>;	
	/*if  (frmFind.ctrlClientID.value > 0)
	{
		sql += "   AND  nBorrowClientID="	+ frmFind.ctrlClientID.value;
	}*/
	sql += " and ncurrencyid=<%=sessionMng.m_lCurrencyID%>  ";
	sql += " and NBORROWCLIENTID=<%=sessionMng.m_lClientID%>  ";
	sql += " ORDER BY sContractCode";
	return sql;
}

function getClient()
{
	var sql = "SELECT id,sCode,sName FROM client  ORDER BY  sCode";
	return sql ;
}

function getPayCode()
{
	var sql =" SELECT id,sCode FROM loan_payform "  ;
	
	sql += "   WHERE  nStatusID="	+ <%=LOANConstant.LoanPayNoticeStatus.USED%>;
		
	if  (frmFind.ctrlContractIDFrom.value > 0)
	{
		sql += "   AND  nContractID="	+ frmFind.ctrlContractIDFrom.value;
	}
	sql += " and ncurrencyid=<%=sessionMng.m_lCurrencyID%>  ";
	sql += " ORDER BY sCode";
	return sql;
}

function confirmSave()
{
	if (frmFind.ctrlContractIDFrom.value < 0 )
	{
		alert("�������ͬ��ţ�");
		return;
	}
	
	if (frmFind.ctrlPayID.value < 0 )
	{
		alert("������ſ�֪ͨ����ţ�");
		return;
	}
	
	showSending();
	frmFind.submit();
}

firstFocus(frmFind.ctrlContractIDFromCtrl);
//setSubmitFunction("confirmSave()");
setFormName("frmFind");	 
</script>			
<%
   		//��ʾ�ļ�β
		OBHtml.showOBHomeEnd(out);	
    }
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>
<%@ include file="/common/SignValidate.inc" %>
