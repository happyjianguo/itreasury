<%
/**
 * ҳ������ ��v003_P.jsp
 * ҳ�湦�� : ���ݴ�ӡѡ��ҳ��
 * ��    �� ��boxu
 * ��    �� ��2007-9-6
 * ����˵�� ��
 *			
 * �޸���ʷ ��
 */
%>
<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.evoucher.util.VOUConstant" %>
<%@ page import="com.iss.itreasury.ebank.print.bizlogic.EbankPrintApplyBiz"%>
<%@ page import="com.iss.itreasury.evoucher.setting.dataentity.BillrelationSetInfo"%>
<%@ page import="com.iss.itreasury.settlement.transfixeddeposit.dao.Sett_TransChangeFixedDepositDAO"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.transfixeddeposit.dao.Sett_TransFixedWithDrawDAO"%>
<%@ page import="com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedDrawInfo"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<% String strContext = request.getContextPath();%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<jsp:include flush="true" page="/ShowMessage.jsp" />
<script language="JavaScript" src="/websett/js/Control.js" ></script>
<script language="JavaScript" src="/websett/js/SettCheck.js"></script>
<script language="JavaScript" src="/websett/js/MagnifierSQL.js"></script>
<script src="../../fceform/js/fcpub.js"></script>
<safety:resources />


<%
	/* ����̶����� */
	String strTitle = "";
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
%>
	<jsp:include page="/ShowMessage.jsp"/>

<%
	long lOfficeID = sessionMng.m_lOfficeID;//���´�
	long lCurrencyID = sessionMng.m_lCurrencyID;//����
	String strTransID = "";//����id
	String strTransNo = "";//���ױ��
	long transTypeID = -1;//��������
	
	long operationTypeID = -1;  //���⽻������
	
	String strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
	String strFailPageURL = (String)request.getAttribute("strFailPageURL");

	String strTemp = "";

	strTemp = (String)request.getAttribute("lID");
	if(strTemp!=null && strTemp.length()>0)
	{
		strTransID = strTemp;
	}
	
    strTemp = (String)request.getAttribute("TransactionTypeID");
	if(strTemp!=null && strTemp.length()>0)
	{
		transTypeID = Long.valueOf(strTemp).longValue();
	}
	
	//���⽻������
	strTemp = (String)request.getAttribute("operationTypeID");
	if(strTemp!=null && strTemp.length()>0)
	{
		operationTypeID = Long.valueOf(strTemp).longValue();
	}

	strTemp = (String)request.getAttribute("TransNo");
	if(strTemp!=null && strTemp.length()>0)
	{
		strTransNo = strTemp;
	}

	Collection coll = null;
	Iterator iter = null;
	Iterator iter1 = null;
	long[] billNum = null;
	EbankPrintApplyBiz biz = new EbankPrintApplyBiz();
	
	long lDeptID = -1;
	lDeptID = VOUConstant.PrintSection.EBANKCUSTOMER;
	if(operationTypeID==-1)
	{
		//�����⽻��������
		coll = biz.getPrintOptionsByTransID(String.valueOf(transTypeID),lDeptID,lCurrencyID,lOfficeID,Constant.ModuleType.SETTLEMENT);

	}
	else
	{
		//�����⽻��������
		coll = biz.getPrintOptionsByTransID(String.valueOf(operationTypeID),lDeptID,lCurrencyID,lOfficeID,Constant.ModuleType.SETTLEMENT);
	}

	
%>

<html>
<head>
<Script Language="JavaScript">
self.moveTo(0,0)
self.resizeTo(1024,740);
</SCRIPT>
<title>��ӡ</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<style type="text/css">
.ItemList {
	BACKGROUND-COLOR: #d6d3ce; BORDER-BOTTOM: 1px solid #000000; BORDER-LEFT: 1px solid #000000; BORDER-RIGHT: 1px solid #000000; BORDER-TOP: 1px solid #000000
}
.ItemBody {
	BACKGROUND-COLOR: #d6d3ce; BORDER-BOTTOM: 1px solid #000000; BORDER-LEFT: 1px; BORDER-RIGHT: 1px solid #000000; BORDER-TOP: 1px
}
.ItemBottom {
	BACKGROUND-COLOR: #d6d3ce; BORDER-BOTTOM: 1px solid #000000; BORDER-LEFT: 1px; BORDER-RIGHT: 1px; BORDER-TOP: 1px
}
.ItemLeftRightBottom {
	BACKGROUND-COLOR: #d6d3ce; BORDER-BOTTOM: 1px solid #000000; BORDER-LEFT: 1px solid #000000; BORDER-RIGHT: 1px solid #000000; BORDER-TOP: 1px
}
.button {
	background-color: #d6d3ce;
	border-top-width: 1px;
	border-right-width: 1px;
	border-bottom-width: 1px;
	border-left-width: 1px;
	border-top-style: solid;
	border-right-style: solid;
	border-bottom-style: solid;
	border-left-style: solid;
	border-top-color: #FAFBFD;
	border-right-color: #314351;
	border-bottom-color: #314351;
	border-left-color: #FAFBFD;}
</style>
</head>	

<body bgcolor="#d6d3ce"  text="#000000">
<object id="WebBrowser" width=0 height=0 classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2">
</object>  <!-- ����رմ�ӡԤ��ҳ���޷��رա�jzw 2010-6-07  -->
<form name="frm" action="../control/c003_P.jsp" method="post">
<input name="strSuccessPageURL"  type="hidden" value="../view/v003_p.jsp">
<input name="strFailPageURL"  type="hidden" value="../view/v003_p.jsp">
<input type="hidden" name="transIDs" value="<%=strTransID%>">
<input type="hidden" name="strTransNos" value="<%=strTransNo%>">
<input type="hidden" name="lTransTypeID" value="<%=transTypeID%>">
<input type="hidden" name="operationTypeID" value="<%=operationTypeID%>">
<input type="hidden" name="printXMLName">
<input type="hidden" name="path">
<input type="hidden" name="strAction">


<table width="400" name="table" align="center" class="ItemList" cellpadding="0" cellspacing="0">
<%
	long lBillID = -1;
	int k=0;
	
	long tempID = 0;
	boolean IsDepositBill = false;
	TransFixedDrawInfo resultInfo = null;
	
	if(coll != null && coll.size()>0) 
	{
		iter = coll.iterator();
		
		/*  �����"�������ڴ浥"�Ĵ���(���߼��д�����) add boxu 2007-9-3  */
		if(transTypeID == SETTConstant.TransactionType.OPENFIXEDDEPOSIT)  //���ڿ���
		{
			Sett_TransChangeFixedDepositDAO transChangeFixedDepositDAO = new Sett_TransChangeFixedDepositDAO();
			IsDepositBill = transChangeFixedDepositDAO.findByDepositBill(Long.parseLong(strTransID), strTransNo);
		}
		
		if(transTypeID == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER  //����ת���ڣ�����֧ȡ��
		|| transTypeID == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)  //֪ͨ���֧ȡ
		{
			Sett_TransFixedWithDrawDAO dao = new Sett_TransFixedWithDrawDAO();
			resultInfo = dao.findByID(Long.parseLong(strTransID));
		}
		
		while(iter.hasNext()) 
		{
			BillrelationSetInfo info = (BillrelationSetInfo)iter.next();
			
			if(IsDepositBill)  //����
			{
				if(info.getTemplatename() != null && info.getTemplatename().trim().equals("settlement_DCKZA.xml"))
				{
					tempID++;
					continue;
				}
				else if(info.getTemplatename() != null && info.getTemplatename().trim().equals("settlement_DCKZB.xml"))
				{
					tempID++;
					continue;
				}
				else if(info.getTemplatename() != null && info.getTemplatename().trim().equals("settlement_DCKZC.xml"))
				{
					tempID++;
					continue;
				}
			}
			else  //����
			{
				if(info.getTemplatename() != null && info.getTemplatename().trim().equals("settlement_HDCDA.xml"))
				{
					tempID++;
					continue;
				}
				else if(info.getTemplatename() != null && info.getTemplatename().trim().equals("settlement_HDCDB.xml"))
				{
					tempID++;
					continue;
				}
				else if(info.getTemplatename() != null && info.getTemplatename().trim().equals("settlement_HDCDC.xml"))
				{
					tempID++;
					continue;
				}
			}
			
			//�ж��Ƿ�����ǰ֧ȡ,������ǰ֧ȡ���˵�"��ǰ֧ȡ����֤ʵ��"
			if ( resultInfo != null )
			{
				//����ת���ڣ�����֧ȡ��
				if(resultInfo.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER)
				{
					if( !( resultInfo.getAmount() > resultInfo.getDrawAmount() && resultInfo.getInterestStartDate().compareTo(resultInfo.getEndDate()) < 0 ) )
					{
						if(info.getTemplatename() != null && info.getTemplatename().trim().equals("settlement_TZKZA.xml"))
						{
							continue;
						}
						else if(info.getTemplatename() != null && info.getTemplatename().trim().equals("settlement_TZKZB.xml"))
						{
							continue;
						}
						else if(info.getTemplatename() != null && info.getTemplatename().trim().equals("settlement_TZKZC.xml"))
						{
							continue;
						}
					}
				}
					
				//֪ͨ���֧ȡ
				if(resultInfo.getTransactionTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
				{
					if( !( resultInfo.getAmount() > resultInfo.getDrawAmount() ) )
					{
						if(info.getTemplatename() != null && info.getTemplatename().trim().equals("settlement_TZKZA.xml"))
						{
							continue;
						}
						else if(info.getTemplatename() != null && info.getTemplatename().trim().equals("settlement_TZKZB.xml"))
						{
							continue;
						}
						else if(info.getTemplatename() != null && info.getTemplatename().trim().equals("settlement_TZKZC.xml"))
						{
							continue;
						}
					}
				}
			}
%>
		<tr height="28">
			<td class="ItemBody" width="250" nowrap><input type="checkbox" name="billName" value=<%=info.getTemplatename()%> ><font size="2"><%=info.getSetname()%></font></td>
			<!-- <td class="ItemBody" width="160" nowrap><%=info.getTemplatename()%></td> -->
			<td class="ItemBody" width="150" nowrap align="center"><a href="javascript:doPrint('<%= info.getTemplatename() %>');"><font size="2">��ӡԤ��</font></a></td>
		</tr>
<%
		}	
%>
		<tr>
			<td colspan="3" align="center" height="28" class="button"><input type="button" class="button" name="butPrint" onclick="doPrintAll()" value=" �� ӡ ">&nbsp;
		    	<!-- <input type="button" class="button" name="butPrint1" onclick="javascript:if(confirm('�Ƿ�رգ�')){document.all.WebBrowser.ExecWB(45,1);}" value=" �� �� ">
		    	<OBJECT id=WebBrowser classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 height=0 width=0></OBJECT> -->
		    	<input type="button" class="button" name="butPrint1" onclick="doClose()" value=" �� �� ">
		    </td>
		</tr>
<%
	}
	else {
%>
		<tr>
			<td>��ҵ�������޹�����ӡ���ݣ�</td>
		</tr>
<%
	}
%>
</table>
</form>
</body>
</html>

<script language="javascript">
	frm.path.value = "<%=Config.getProperty(ConfigConstant.GLOBAL_EVOUCHER_FILEPATH)%>";
	function selectAll()
	{
		if(isNaN(frm.billName.length) != true) {
			for(var i = 0;i < frm.billName.length;i++)
			{
				if(frm.billName[i].checked == true)
				{
					for(var j=0;j<frm.billID.length;j++)
					{
						if(frm.billName[i].value == frm.billID[j].value) {						
							frm.templateID[j].checked = true;
						}
					}
				}
				else {
					for(var j=0;j<frm.billID.length;j++)
					{
						if(frm.billName[i].value == frm.billID[j].value) {
							frm.templateID[j].checked = false;
						}
					}
				}
			}
		}
		else {
			if (isNaN(frm.templateID.length) != true)
			{
				if (frm.billName.checked == true)
				{
					for (var k=0; k<frm.templateID.length; k++ )
					{
						if (frm.billID[k].value == frm.billName.value)
						{
							frm.templateID[k].checked = true;
						}
					}
				}
				else {
					for (var k=0; k<frm.templateID.length; k++ )
					{
						if (frm.billID.value == frm.billName.value)
						{
							frm.templateID[k].checked = false;
						}
					}
				}
			}
			else 
			{
				if (frm.billName.checked == true)
				{
					frm.templateID.checked = true;
				}
				if (frm.billName.checked == false)
				{
					frm.templateID.checked = false;
				}
			}

		}
	}

	function doPrintAll()
	{
		var selectBillName = 0;
		if (isNaN(frm.billName.length) != true)
		{
			for (var k=0; k<frm.billName.length; k++ )
			{
				if (frm.billName[k].checked == true)
				{
					selectBillName = selectBillName + 1;
				}
			}
		}
		else 
		{
			if (frm.billName.checked == true)
			{
				selectBillName = selectBillName + 1;
			}
		}
		
		if(selectBillName == 0)
		{
			alert("��ѡ����Ҫ�Ĵ�ӡ����");
			return false;
		}
		
		if(confirm("�Ƿ��ӡ?"))
		{
			//ֱ�Ӵ�ӡ
			frm.strSuccessPageURL.value="../control/c005_p.jsp";
			frm.strFailPageURL.value="../view/v003_P.jsp";
			frm.action = "../control/c003_p.jsp";
			frm.submit();
		}
	}

	function doPrint(temp)
	{
		frm.strAction.value = "preview";
		frm.printXMLName.value = temp;
		frm.strSuccessPageURL.value="../control/c004_p.jsp";
		frm.strFailPageURL.value="../view/v003_P.jsp";
		frm.action = "../control/c003_p.jsp";
		frm.submit();	
	}
	
	function doClose()
	{
		if(confirm("�Ƿ�رգ�"))
		{
			
			document.all.WebBrowser.ExecWB(45,1);  //����رմ�ӡԤ��ҳ���޷��رա�jzw 2010-6-07
		}
	}
</script>

<%
	OBHtml.showOBHomeEnd(out);
}
catch( Exception exp )
{
	exp.printStackTrace();
	Log.print(exp.getMessage());
}
%>
<%@ include file="/common/SignValidate.inc" %>