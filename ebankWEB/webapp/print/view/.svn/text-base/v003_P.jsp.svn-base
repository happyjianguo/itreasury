<%
/**
 * 页面名称 ：v003_P.jsp
 * 页面功能 : 单据打印选择页面
 * 作    者 ：boxu
 * 日    期 ：2007-9-6
 * 特殊说明 ：
 *			
 * 修改历史 ：
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
	/* 标题固定变量 */
	String strTitle = "";
%>
 
<%
try
{
	/* 用户登录检测 */
    if (sessionMng.isLogin() == false)
    {
        OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
    	out.flush();
    	return;
    }

    /* 判断用户是否有权限 */
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
	long lOfficeID = sessionMng.m_lOfficeID;//办事处
	long lCurrencyID = sessionMng.m_lCurrencyID;//币种
	String strTransID = "";//交易id
	String strTransNo = "";//交易编号
	long transTypeID = -1;//交易类型
	
	long operationTypeID = -1;  //特殊交易类型
	
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
	
	//特殊交易类型
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
		//无特殊交易子类型
		coll = biz.getPrintOptionsByTransID(String.valueOf(transTypeID),lDeptID,lCurrencyID,lOfficeID,Constant.ModuleType.SETTLEMENT);

	}
	else
	{
		//有特殊交易子类型
		coll = biz.getPrintOptionsByTransID(String.valueOf(operationTypeID),lDeptID,lCurrencyID,lOfficeID,Constant.ModuleType.SETTLEMENT);
	}

	
%>

<html>
<head>
<Script Language="JavaScript">
self.moveTo(0,0)
self.resizeTo(1024,740);
</SCRIPT>
<title>打印</title>
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
</object>  <!-- 解决关闭打印预览页后，无法关闭。jzw 2010-6-07  -->
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
		
		/*  针对于"换开定期存单"的处理(此逻辑有待修正) add boxu 2007-9-3  */
		if(transTypeID == SETTConstant.TransactionType.OPENFIXEDDEPOSIT)  //定期开立
		{
			Sett_TransChangeFixedDepositDAO transChangeFixedDepositDAO = new Sett_TransChangeFixedDepositDAO();
			IsDepositBill = transChangeFixedDepositDAO.findByDepositBill(Long.parseLong(strTransID), strTransNo);
		}
		
		if(transTypeID == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER  //定期转活期（定期支取）
		|| transTypeID == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)  //通知存款支取
		{
			Sett_TransFixedWithDrawDAO dao = new Sett_TransFixedWithDrawDAO();
			resultInfo = dao.findByID(Long.parseLong(strTransID));
		}
		
		while(iter.hasNext()) 
		{
			BillrelationSetInfo info = (BillrelationSetInfo)iter.next();
			
			if(IsDepositBill)  //换开
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
			else  //定开
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
			
			//判断是否是提前支取,不是提前支取过滤掉"提前支取开户证实书"
			if ( resultInfo != null )
			{
				//定期转活期（定期支取）
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
					
				//通知存款支取
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
			<td class="ItemBody" width="150" nowrap align="center"><a href="javascript:doPrint('<%= info.getTemplatename() %>');"><font size="2">打印预览</font></a></td>
		</tr>
<%
		}	
%>
		<tr>
			<td colspan="3" align="center" height="28" class="button"><input type="button" class="button" name="butPrint" onclick="doPrintAll()" value=" 打 印 ">&nbsp;
		    	<!-- <input type="button" class="button" name="butPrint1" onclick="javascript:if(confirm('是否关闭？')){document.all.WebBrowser.ExecWB(45,1);}" value=" 关 闭 ">
		    	<OBJECT id=WebBrowser classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 height=0 width=0></OBJECT> -->
		    	<input type="button" class="button" name="butPrint1" onclick="doClose()" value=" 关 闭 ">
		    </td>
		</tr>
<%
	}
	else {
%>
		<tr>
			<td>该业务类型无关联打印单据！</td>
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
			alert("请选择需要的打印单据");
			return false;
		}
		
		if(confirm("是否打印?"))
		{
			//直接打印
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
		if(confirm("是否关闭？"))
		{
			
			document.all.WebBrowser.ExecWB(45,1);  //解决关闭打印预览页后，无法关闭。jzw 2010-6-07
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