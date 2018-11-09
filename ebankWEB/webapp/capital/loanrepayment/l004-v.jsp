<%--
 ҳ������ ��l004-v.jsp
 ҳ�湦�� : [�����]--��Ӫ����
 ��    �� ��gqzhang
 ��    �� ��2004��2��
 ����˵�� ��ʵ�ֲ���˵����
 �޸���ʷ ��
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
                 javax.servlet.*,
				 com.iss.itreasury.util.*,
				 com.iss.itreasury.loan.util.*,
				 com.iss.itreasury.settlement.util.NameRef,
                 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
                 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.ebank.obsystem.bizlogic.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.*,
                 com.iss.itreasury.ebank.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<%
	/* ����̶����� */
	String strTitle = "[�����]--��Ӫ����";
%>					
<%
  try
  {
	Log.print("*******����ҳ��--ebank/capital/loanrepayment/l004-v.jsp*******");
	
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
		//�������
		String strTemp = "";
		String strAction = "";
        String strReturn = "";
		
		 strTemp = (String)request.getAttribute("strAction");
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			strAction = strTemp.trim();
		 }
		 
		  strTemp = (String)request.getAttribute("strReturn");
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			strReturn = strTemp.trim();
		 }
		 
		 String isYT = "";
		 strTemp = (String)request.getAttribute("isYT");
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			isYT = strTemp.trim();
			if (isYT.equals("1"))
			{
				strTitle = "[�����]--���Ŵ���";
			}
		 }
		
		
		//ҳ��һ��Ϣ
		long lID = -1; // ָ�����
	    String sPayerName = ""; // �������
		String sPayerAcctNo = ""; // ����˺� 
		long lPayerAcctID = -1;//����˻�ID
		double dCurrentBalance = 0.0; // ��ǰ���
	    double dUsableBalance = 0.0; // ���ý��
		String sPayeeName = ""; // �տ����
	    String sPayeeAcctNo = ""; // �տ�˺ţ��������˺ţ�
	    long lPayeeAcctID = -1; // �տ�˻�ID���������˻�id��
		long lContractID = -1;//�����ͬid
		String sLoanContractNo = ""; // �����ͬ��
		Timestamp tsPayDate = null; // ����ſ�����
		long lLoanNoteID = -1;
	    String  sLetOutCode = ""; // �ſ�֪ͨ����
		double dBalance = 0.0;//�������
		double dAmount = 0.0; // ������
		Timestamp tsExecuteDate = null; // ִ����
		String sNote = ""; // �����;/ժҪ
		
		
		//���˻���Ϣ
	    //long lLoanNoteID = -1; //�ſ�֪ͨ��id
		long lSubAccountID = -1;//���˻�id
	    double dInterest = 0.0; //Ӧ��������Ϣ
	    double dCompoundInterest = 0.0; //Ӧ������
	    double dOverDueInterest = 0.0; //Ӧ�����ڷ�Ϣ
	    double dSuretyFee = 0.0; //Ӧ��������
	    double dTotal = 0.0; //Ӧ����Ϣ�ͷ���֮��
		//�����ֶ�
		double dInterestReceiveable = 0.0;//������Ϣ
		double dInterestIncome = 0.0;//������Ϣ
		
		//Ϊ��ӡ�����ֶ�
	    Timestamp tsInterestStart = null; //������Ϣ��Ϣ��
	    double dInterestRate = 0.00; //������Ϣ����	
	 	Timestamp tsCompInterestStart = null; //������Ϣ��
	 	double dCompRate = 0.00; //��������
	 	double dCompoundAmount = 0.00; //��������
	 	Timestamp tsOverDueStart = null; //��Ϣ��Ϣ��
	 	double dOverDueRate = 0.00; //��Ϣ����
	 	double dOverDueAmount = 0.00; //Ӧ�����ڱ���
	 	Timestamp tsSuretyStart = null; //��������Ϣ��
	 	double dSuretyRate = 0.00; //��������
	 	Timestamp tsCommissionStart = null; //��������Ϣ��
	 	double dCommissionRate = 0.00; //��������
		
		long lLoanAcctID = -1;
	    strTemp = (String)request.getAttribute("lLoanNoteIDAccountID");//�տ�˻�ID���������˻�id��
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			lLoanAcctID = Long.valueOf(strTemp).longValue();
		 }
		
		//��ȡҳ��һ����Ϣ
	         strTemp = (String)request.getAttribute("lID");// ָ�����
			 if (strTemp != null && strTemp.trim().length() > 0)
			 {
				lID = Long.valueOf(strTemp).longValue();
			 }
			
			 strTemp = (String)request.getAttribute("sPayerName");// �������
			 if (strTemp != null && strTemp.trim().length() > 0)
			 {
				sPayerName = strTemp.trim();
			 }
			 
			 strTemp = (String)request.getAttribute("sPayerAcctNoCtrl");// ����˺� 
			 if (strTemp != null && strTemp.trim().length() > 0)
			 {
				sPayerAcctNo = strTemp.trim();
			 }
			 
			 strTemp = (String)request.getAttribute("lPayerAcctID");// ����˻�id
			 if (strTemp != null && strTemp.trim().length() > 0)
			 {
				lPayerAcctID = Long.valueOf(strTemp).longValue();
			 }
			
			 strTemp = (String)request.getAttribute("dCurrentBalance");//��ǰ���
			 if (strTemp != null && strTemp.trim().length() > 0)
			 {
				dCurrentBalance = DataFormat.parseNumber(strTemp);
			 }
			
			 strTemp = (String)request.getAttribute("dUsableBalance");//���ý��
			 if (strTemp != null && strTemp.trim().length() > 0)
			 {
				dUsableBalance = DataFormat.parseNumber(strTemp);
			 }
		
		     strTemp = (String)request.getAttribute("sPayeeName");//�տ����
			 if (strTemp != null && strTemp.trim().length() > 0)
			 {
				sPayeeName = strTemp.trim();
			 }
				
			 strTemp = (String)request.getAttribute("sPayeeAcctNo");//�տ�˺ţ��������˺ţ� 
			 if (strTemp != null && strTemp.trim().length() > 0)
			 {
				sPayeeAcctNo = strTemp.trim();
			 }
			 //Log.print("====�����˺ţ�"+sPayeeAcctNo);
			 
			  strTemp = (String)request.getAttribute("lPayeeAcctID");//�տ�˻�ID���������˻�id��
			 if (strTemp != null && strTemp.trim().length() > 0)
			 {
				lPayeeAcctID = Long.valueOf(strTemp).longValue();
			 }
			 
			 strTemp = (String)request.getAttribute("lContractID");//�����ͬid
			 if (strTemp != null && strTemp.trim().length() > 0)
			 {
				lContractID = Long.valueOf(strTemp).longValue();
				sLoanContractNo = NameRef.getContractNoByID(lContractID);
			 }
			 
			 strTemp = (String)request.getAttribute("lContractIDCtrl");//�����ͬ���
			 if (strTemp != null && strTemp.trim().length() > 0)
			 {
				sLoanContractNo = strTemp.trim();
			 }
			 
		 	 strTemp = (String)request.getAttribute("lLoanNoteIDCtrl");//����ſ�����
			 if (strTemp != null && strTemp.trim().length() > 0)
			 {
				tsPayDate =  DataFormat.getDateTime(strTemp);
			 }
			  Log.print("====�ſ����ڣ�"+tsPayDate);
	         
			  strTemp = (String)request.getAttribute("lLoanNoteID");// �ſ�֪ͨ��id
			 if (strTemp != null && strTemp.trim().length() > 0)
			 {
				lLoanNoteID = Long.valueOf(strTemp).longValue();
				sLetOutCode = NameRef.getPayFormNoByID(lLoanNoteID);
			 }
			 
			 strTemp = (String)request.getAttribute("dBalance");//�������
			 if (strTemp != null && strTemp.trim().length() > 0)
			 {
				dBalance = DataFormat.parseNumber(strTemp);
			 }
			 
			 strTemp = (String)request.getAttribute("dAmount");//������
			 if (strTemp != null && strTemp.trim().length() > 0)
			 {
				dAmount = DataFormat.parseNumber(strTemp);
			 }
			 
			  strTemp = (String)request.getAttribute("tsExecuteDate");//ִ����
			 if (strTemp != null && strTemp.trim().length() > 0)
			 {
				tsExecuteDate =  DataFormat.getDateTime(strTemp);
			 }
			 
			 strTemp = (String)request.getAttribute("sNote");// �����;/ժҪ
			 if (strTemp != null && strTemp.trim().length() > 0)
			 {
				sNote = strTemp.trim();
			 }		 
		 //��ȡ��ѯ���
		 SubLoanAccountDetailInfo subLoanAccountDetailInfo = null;
		 subLoanAccountDetailInfo = (SubLoanAccountDetailInfo)request.getAttribute("resultInfo");
		 if(subLoanAccountDetailInfo != null)
		 {
		   lSubAccountID = subLoanAccountDetailInfo.getSubAccountID();
		   Log.print("�������������������������˻�:"+lSubAccountID);
		   dInterest = subLoanAccountDetailInfo.getInterest();
		   Log.print("======������Ϣ��"+dInterest);
		   dCompoundInterest = subLoanAccountDetailInfo.getCompoundInterest();
		   dOverDueInterest = subLoanAccountDetailInfo.getOverDueInterest();
		   dSuretyFee = subLoanAccountDetailInfo.getSuretyFee();
		   dInterestReceiveable = subLoanAccountDetailInfo.getInterestReceiveable();
		   Log.print("======������Ϣ"+dInterestReceiveable);
		   dInterestIncome = subLoanAccountDetailInfo.getInterestIncome();
		   Log.print("======������Ϣ"+dInterestIncome);		   
		   dTotal = subLoanAccountDetailInfo.getTotal();
		   Log.print("����������������������Ӧ���ܶ�:"+dTotal);
		   
		   //Ϊ��ӡ�����ֶ�
		   tsInterestStart = subLoanAccountDetailInfo.getInterestStart(); //������Ϣ��Ϣ��
	       dInterestRate = subLoanAccountDetailInfo.getInterestRate(); //������Ϣ����	
	 	   tsCompInterestStart = subLoanAccountDetailInfo.getCompInterestStart(); //������Ϣ��
	 	   dCompRate = subLoanAccountDetailInfo.getCompRate(); //��������
	 	   dCompoundAmount = subLoanAccountDetailInfo.getCompoundAmount(); //��������
	 	   tsOverDueStart = subLoanAccountDetailInfo.getOverDueStart(); //��Ϣ��Ϣ��
	 	   dOverDueRate = subLoanAccountDetailInfo.getOverDueRate(); //��Ϣ����
	 	   dOverDueAmount = subLoanAccountDetailInfo.getOverDueAmount(); //Ӧ�����ڱ���
	 	   tsSuretyStart = subLoanAccountDetailInfo.getSuretyStart(); //��������Ϣ��
	 	   dSuretyRate = subLoanAccountDetailInfo.getSuretyRate(); //��������
	 	   tsCommissionStart = subLoanAccountDetailInfo.getCommissionStart(); //��������Ϣ��
	 	   dCommissionRate = subLoanAccountDetailInfo.getCommissionRate(); //��������
	     }
		 
		 /* ʵ�ֲ˵����� */
		long lShowMenu = OBConstant.ShowMenu.YES;
		String strMenu = (String)request.getAttribute("menu");
		if ((strMenu != null) && (strMenu.equals("hidden")))
		{
			lShowMenu = OBConstant.ShowMenu.NO;
		}
		
		//��ʾ�ļ�ͷ
       OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
		%>	
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>

<safety:resources />

<FORM name=frm method=post>
<input type="hidden" name="menu" value="<%=lShowMenu == OBConstant.ShowMenu.NO?"hidden":""%>">
<!--����hidden�ؼ����ڷ���ʱҳ��һ��Ϣ�Ļ����Լ�����!--->
<input type="Hidden" name="lID" value="<%=lID%>">
<input type="Hidden" name="sPayerName" value="<%=sPayerName%>">
<input type="Hidden" name="sPayerAcctNoCtrl" value="<%=sPayerAcctNo%>">
<input type="Hidden" name="lPayerAcctID" value="<%=lPayerAcctID%>">
<input type="Hidden" name="dCurrentBalance" value="<%=dCurrentBalance%>">
<input type="Hidden" name="dUsableBalance" value="<%=dUsableBalance%>">
<input type="Hidden" name="sPayeeName" value="<%=sPayeeName%>">
<input type="Hidden" name="sPayeeAcctNo" value="<%=sPayeeAcctNo%>">
<input type="Hidden" name="lPayeeAcctID" value="<%=lPayeeAcctID%>">
<input type="Hidden" name="lContractID" value="<%=lContractID%>">
<input type="Hidden" name="lContractIDCtrl" value="<%=sLoanContractNo%>">
<input type="Hidden" name="lLoanNoteIDCtrl" value="<%=tsPayDate%>">
<input type="Hidden" name="lLoanNoteID" value="<%=lLoanNoteID%>">
<input type="Hidden" name="dBalance" value="<%=dBalance%>">
<input type="Hidden" name="dAmount" value="<%=dAmount%>">
<input type="Hidden" name="tsExecuteDate" value="<%=tsExecuteDate%>">
<input type="Hidden" name="sNote" value="<%=sNote%>">
<input type="Hidden" name="lSubAccountID" value="<%=lSubAccountID%>">
<!--����hidden�ؼ����ڷ���ʱҳ��һ��Ϣ�Ļ����Լ�����!--->
<!--����hidden�ؼ����ڴ�ӡ������Ϣ!--->
<input type="Hidden" name="tsInterestStart" value="<%=tsInterestStart%>">
<input type="Hidden" name="dInterestRate" value="<%=dInterestRate%>">
<input type="Hidden" name="tsCompInterestStart" value="<%=tsCompInterestStart%>">
<input type="Hidden" name="dCompRate" value="<%=dCompRate%>">
<input type="Hidden" name="dCompoundAmount" value="<%=dCompoundAmount%>">
<input type="Hidden" name="tsOverDueStart" value="<%=tsOverDueStart%>">
<input type="Hidden" name="dOverDueRate" value="<%=dOverDueRate%>">
<input type="Hidden" name="dOverDueAmount" value="<%=dOverDueAmount%>">
<input type="Hidden" name="tsSuretyStart" value="<%=tsSuretyStart%>">
<input type="Hidden" name="dSuretyRate" value="<%=dSuretyRate%>">
<input type="Hidden" name="tsCommissionStart" value="<%=tsCommissionStart%>">
<input type="Hidden" name="dCommissionRate" value="<%=dCommissionRate%>">
<!--����hidden�ؼ����ڴ�ӡ������Ϣ!--->
<!--�������ڲ�ѯ����!-->
<input type="hidden" name="strAction" value="<%=strAction%>">
<input type="hidden" name="strReturn" value="<%=strReturn%>">
<!--�������ڲ�ѯ����!-->
<input type="hidden" name="lLoanAcctID" value="<%=lLoanNoteID%>">
          <table width="730" height="172" border="0" cellpadding="0" cellspacing="1" class="top">
            <tr class="tableHeader"> 
              <td colSpan=2 class="FormTitle" height=25>&nbsp;��Ϣ��������</td>
            </tr>
            <tr> 			
              <td colspan="2"  >&nbsp;��Ϣ�˺ţ�
			  <INPUT class=rebox  readOnly size=30 name=sPayInterestAccountNO value="<%=sPayerAcctNo%>"> </td>
            </tr>
            <tr> 
              <td width="29%" class=graytext >&nbsp;</td>
              <td width="71%" class=graytext >Ӧ����</td>
            </tr>
            <tr> 
              <td class=graytext >&nbsp;������Ϣ��</td>
              <td class=graytext ><%=sessionMng.m_strCurrencySymbol%> 
              <INPUT class=amountbox  readOnly size=30 name=dInterest value="<%=dInterest > 0 ?DataFormat.formatDisabledAmount(dInterest):"0.00"%>"></td>
            </tr>
			<tr>
			<td colspan="2">
			<table width="100%" border="0" cellpadding="0" cellspacing="1">
			<tr> 
              <td class=graytext  width="29%">&nbsp;&nbsp;����&nbsp;&nbsp;������Ϣ��&nbsp;&nbsp;</td>
              <td class=graytext  width="71%"><%=sessionMng.m_strCurrencySymbol%> 
              <INPUT class=amountbox  readOnly size=30 name=dInterestReceiveable value="<%=dInterestReceiveable > 0 ?DataFormat.formatDisabledAmount(dInterestReceiveable):"0.00"%>"></td>
            </tr>
			<tr> 
              <td class=graytext  width="29%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;������Ϣ��</td>
              <td class=graytext  width="71%"><%=sessionMng.m_strCurrencySymbol%> 
              <INPUT class=amountbox  readOnly size=30 name=dInterestIncome value="<%=dInterestIncome > 0 ?DataFormat.formatDisabledAmount(dInterestIncome):"0.00"%>"></td>
            </tr>
			</table>
			</td>
			</tr>
            <tr> 
              <td class=graytext >&nbsp;������</td>
              <td class=graytext ><%=sessionMng.m_strCurrencySymbol%>  
               <INPUT class=amountbox  readOnly size=30 name=dCompoundInterest value="<%=dCompoundInterest > 0 ? DataFormat.formatDisabledAmount(dCompoundInterest):"0.00"%>"></td>
            </tr>
            <tr> 
              <td class=graytext >&nbsp;���ڷ�Ϣ��</td>
              <td class=graytext ><%=sessionMng.m_strCurrencySymbol%>  
               <INPUT class=amountbox  readOnly size=30 name=dOverDueInterest value="<%=dOverDueInterest > 0 ? DataFormat.formatDisabledAmount(dOverDueInterest):"0.00"%>"></td>
            </tr>
            <tr> 
              <td class=graytext >&nbsp;�����ѣ�</td>
              <td class=graytext ><%=sessionMng.m_strCurrencySymbol%>  
               <INPUT class=amountbox  readOnly size=30 name=dSuretyFee value="<%=dSuretyFee > 0 ? DataFormat.formatDisabledAmount(dSuretyFee):"0.00"%>"></td>
            </tr>
            <tr> 
              <td class=graytext >&nbsp;��Ϣ���úϼƣ�</td>
              <td class=graytext ><%=sessionMng.m_strCurrencySymbol%>  
              <INPUT class=amountbox  readOnly size=30 name=dTotal value="<%=dTotal > 0 ? DataFormat.formatDisabledAmount(dTotal):"0.00"%>"></td>
            </tr>
          </table>
          <br>
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><TABLE cellSpacing=0 cellPadding=0 width=700 border=0>
                  <TR> 
                    <TD width=454> <DIV align=right></DIV><input type="hidden" name="isYT" value="<%=isYT%>"></TD>
                    <TD width=70> <DIV align=right><input type="button" name="doSubmit" value=" �� �� " class = button onfocus="nextfield='submitfunction';" onClick="doSubmitFun();"></DIV></TD>
                    <TD width=59> <DIV align=right><input type="button" name="doCalcel" value=" �� �� " class = button  onClick="doCancelFun();"></DIV></TD>
                  </TR>
                </TABLE></td>
            </tr>
          </table>
          <p>&nbsp;</p></form>
<script language="javascript">
function validate()
{
  var dBalance = parseFloat(reverseFormatAmount(frm.dUsableBalance.value)) 
  			    -(parseFloat(reverseFormatAmount(frm.dAmount.value)) 
				+ parseFloat(reverseFormatAmount(frm.dTotal.value))) ;
	
  if(dBalance < 0 )
  {
     alert("�˻��������С�ڱ�������Ϣ����֮�ͣ�");
	 return false;
  }			
  return true;				   

}

function doSubmitFun()
{
  if(!validate())
  {
    return;
  }
  if(confirm("�Ƿ��ύ��"))
  {
        frm.action="l005-c.jsp"
		showSending();
		frm.submit();
  }
}

function doCancelFun()
{
  if(confirm("�Ƿ񷵻أ�"))
  {
    frm.strAction.value="toCancel";
	frm.action="l002-v.jsp"
	showSending();
	frm.submit();
  }
}

firstFocus(frm.doSubmit);
//setSubmitFunction("doSubmitFun()");
setFormName("frm");	 
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