<%--
 ҳ������ ��v008.jsp
 ҳ�湦�� : ��ʧҵ����ҽ�����ҳ��
 ��    �� ��jinchen
 ��    �� ��2004-11-23
 ����˵�� ��
 ʵ�ֲ���˵����
 �޸���ʷ ��
--%>
<%@ page contentType="text/html;charset=gbk" %>
<%@page import="
				java.util.*,
				java.sql.*,
				com.iss.itreasury.util.*,
				com.iss.itreasury.settlement.util.SETTHTML,
				com.iss.itreasury.settlement.util.SETTConstant,
				com.iss.itreasury.settlement.util.NameRef,
				com.iss.itreasury.settlement.reportlossorfreeze.dataentity.*,
				com.iss.itreasury.settlement.reportlossorfreeze.bizlogic.*,
				com.iss.itreasury.settlement.reportlossorfreeze.dao.*,
				com.iss.itreasury.settlement.bizdelegation.TransAbatementDelegation,
				com.iss.itreasury.settlement.transabatement.dataentity.TransAbatementDetailInfo,
				com.iss.itreasury.settlement.transabatement.dataentity.TransAbatementInfo
				"
%>
<% String strContext = request.getContextPath();%>

<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>


<%
	try{

	Log.print("=================����ҳ��reportlossorfreeze/view/v008.jsp=========");
	/** Ȩ�޼�� **/
	String strTableTitle = "ҵ���� ���� ��ʧ";
	   
	//������
	//if(!SETTHTML.validateRequest(out, request,response)) return;
	/* �ж��û��Ƿ���Ȩ�� */
	if (sessionMng.hasRight(request) == false) {
		out.println(sessionMng.hasRight(request));
		OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E003");
		out.flush();
		return;
	}
		
	//��ʾ�ļ�ͷ
	//SETTHTML.showHomeHead(out,sessionMng,Env.getClientName());
	OBHtml.showOBHomeHead(out, sessionMng, "����ҵ���ѯ", OBConstant.ShowMenu.YES);
	/**
	 * ��������
	 */
	String strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
	String strFailPageURL = (String)request.getAttribute("strFailPageURL");
	long lCurrencyId				= sessionMng.m_lCurrencyID;			//��ǰϵͳʹ�ñ���ID
	long lOfficeId				    = sessionMng.m_lOfficeID;			//���´�ID
	String strTemp = "";
	TransAbatementInfo transAbatementInfo=new TransAbatementInfo();
	String isBack="false";
	long lDesc = Constant.PageControl.CODE_ASCORDESC_ASC;
	long lOrder = 1;
	
	String strPageLoaderKey = (String)request.getAttribute("_pageLoaderKey");
	Object[] queryResults = null;
	queryResults = (Object[])request.getAttribute(Constant.PageControl.SearchResults);
	strTemp = (String)request.getAttribute("Desc");
	if(strTemp != null && strTemp.length()>0)
	{
		lDesc = Long.valueOf(strTemp).longValue();
	}
	if( lDesc == Constant.PageControl.CODE_ASCORDESC_ASC )
	{
		lDesc = Constant.PageControl.CODE_ASCORDESC_DESC;
	}
	else
	{
		lDesc = Constant.PageControl.CODE_ASCORDESC_ASC;
	}
	strTemp = (String)request.getAttribute("Order");
	if(strTemp != null && strTemp.trim().length() > 0)
	{
		lOrder = Long.valueOf(strTemp).longValue();
	}	
	/**
	 * ҳ�������
	 */
	PageCtrlInfo pageInfo		= new PageCtrlInfo();
	pageInfo.convertRequestToDataEntity(request);
	
	/**
	 * ��request�õ�ҳ����Ʋ���
	 */
	 if (!pageInfo.getStrActionResult().equals(Constant.ActionResult.FAIL))
	 {
		 if(request.getAttribute("isBack")!=null)
		 {
			isBack=String.valueOf(request.getAttribute("isBack"));
			if(isBack.equalsIgnoreCase("true"))
			 {
				//transAbatementInfo.convertRequestToDataEntity(request);
			 }
				
		 }
	 }
	 
	
	/**
	 * �������������ǳɹ�,��request�л�����к�dataentity�ֶΰ󶨵�����,���ҳ�����
	 */
	 //if (pageInfo.getStrActionResult().equals(Constant.ActionResult.FAIL))
	//	transAbatementInfo.convertRequestToDataEntity(request);

//��ʾǰҳ��������ʾ
String SucInfo=(String) request.getAttribute("SucInfo");
if(SucInfo!=null&&SucInfo.length()>0)
{
%>
<script language="JavaScript">
alert("<%=SucInfo%>");
</script>
<%}%>

<!--jsp:include page="/ShowMessage.jsp"/-->

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<!--����js�ļ�-->
<script language="JavaScript" src="/websett/js/Control.js" ></script>
<script language="JavaScript" src="/websett/js/SettCheck.js"></script>
<script language="JavaScript" src="/websett/js/MagnifierSQL.js"></script>

<safety:resources />

<!--����js�ļ�-->

	<!--����ҵ������-->
	<table width="99%" class="top" height="140">
  
       <TBODY>
        
		 <TR>
           <TD class=FormTitle height=2 width="100%"><B> 	����ҵ���ѯ</B></TD>
         </TR>

         <TR>
           <TD height=20 vAlign=top width="100%">
		   
		   <form method="post" name="frm">
	<!--ҳ����Ʊ���-->
	<input type="hidden" name="strSourcePage" value="">
	<input type="hidden" name="strSuccessPageURL" value="">
	<input type="hidden" name="strFailPageURL" value="">
	<input type="hidden" name="strAction" value="">
	<input type="hidden" name="strCtrlPageURL" value="">
	
	<!--�ظ��������-->
	<!--ҳ����Ʊ���-->
	
	<!--����ҵ������-->
	<input type="hidden" name="isChange" value="">
	<input type="hidden" name="hdnId" value="">
	<input type="hidden" name="Order" value="">
	<input type="hidden" name="Desc" value="">

	<input type="hidden" name="_pageLoaderKey"  value="<%= strPageLoaderKey %>" >
	
             <TABLE width="100%" height=64 border=0 align=center cellspacing="1" class=ItemList>
               <TBODY>
                 <TR>
                   <td height="20" nowrap class="ItemTitle">
                     <div align="center"><A href="javascript:OrderPage(<%=ReportLossOrFreezeBean.OrderBy_TransNo%>)">���ױ��</a></div>
                   </td>
                   <TD height=20 align="center" nowrap class=ItemTitle>��������</TD>
                   <td nowrap class="ItemTitle" align="center" ><a href="#">��Ч����</a></td>
                   <td height="20" nowrap class="ItemTitle" align="center" ><A href="javascript:OrderPage(<%=ReportLossOrFreezeBean.OrderBy_ClientCode%>)">�ͻ����</a></td>
                   <td nowrap class="ItemTitle" align="center" ><A href="javascript:OrderPage(<%=ReportLossOrFreezeBean.OrderBy_ClientName%>)">�ͻ�����</a></td>
                   <td height="20" nowrap class="ItemTitle" align="center" ><A href="javascript:OrderPage(<%=ReportLossOrFreezeBean.OrderBy_AccountNo%>)">�˺�</a></td>
                   <td height="20" nowrap class="ItemTitle" align="center" ><A href="javascript:OrderPage(<%=ReportLossOrFreezeBean.OrderBy_OldDepositNo%>)">�浥��</a></td>
                   <td nowrap class="ItemTitle" align="center">���᷽ʽ</td>
				    <td nowrap class="ItemTitle" align="center"><A href="javascript:OrderPage(<%=ReportLossOrFreezeBean.OrderBy_FreezeAmount%>)">���</a></td>
                   <td nowrap class="ItemTitle" align="center" ><A href="javascript:OrderPage(<%=ReportLossOrFreezeBean.OrderBy_ExecuteDate%>)">ִ������</a></td>
                   <td nowrap class="ItemTitle" align="center"><A href="javascript:OrderPage(<%=ReportLossOrFreezeBean.OrderBy_CheckUserName%>)">����Ա</a></td>
<%
	String strDetailPageURL = null;
	if(queryResults!=null && queryResults.length>0)
	{
		for( int i=0; queryResults != null && i<queryResults.length; i++ )
		{
			ReportLossOrFreezeInfo info = (ReportLossOrFreezeInfo)queryResults[i];
			strDetailPageURL = strContext+"/capital/query/q051-c.jsp?hdnId="+info.getId()+"&strAction="+SETTConstant.Actions.TODETAIL+"&strSuccessPageURL=/capital/query/q052-v.jsp&strFailPageURL=/capital/query/q052-v.jsp";
%>
                 <TR>
                   <td class="ItemBody" height="20" align="right"><A href="<%= strDetailPageURL %>"  target="blank"><%=DataFormat.formatString(info.getTransNo())%> </a></td>
                   
                   <td class="ItemBody" height="20" align="left"><%=SETTConstant.TransactionType.getName(info.getTransActionType())%></td>
                   <td class="ItemBody" height="20" align="center"><%=DataFormat.getDateString(info.getEffectiveDate())%></td>
                   <td class="ItemBody" align="right"><%=DataFormat.formatString(info.getClientCode())%></td>
                   <td class="ItemBody" height="20" align="left"><%=DataFormat.formatString(info.getClientName())%></td>
                   <td class="ItemBody" height="20" align="right"><%=DataFormat.formatString(info.getAccountNo())%></td>
                   <td class="ItemBody" align="right" height="20"><%=DataFormat.formatString(info.getOldDepositNo())%></td>
                   <td class="ItemBody" align="left" height="20"><%=DataFormat.formatString(SETTConstant.SubAccountStatus.getName(info.getSubAccountNewStatus()))%></td>
				   <td class="ItemBody" align="right" height="20"><%=DataFormat.formatAmount(info.getFreezeAmount())%></td>
                   <td class="ItemBody" align="center" height="20"><%=DataFormat.getDateString(info.getExecuteDate())%></td>
				   <td class="ItemBody"  align="left" height="20"><%=DataFormat.formatString(info.getCheckUserName())%></td>
                 </TR>
<%
		}
	}
%>
				
               
                       
				



                       
               
               



       </TBODY>
     </TABLE>
	</form>
</td>
</tr>
	<!-- ��ҳ�ؼ� -->
				  <tr  align="center" > 
					<td width="99%"  valign="bottom">
						 <TABLE border="0" cellPadding=1 height=20 width="99%" class="ItemList">
						 <TBODY>
							 <TR>
								<TD height=20 width=99% class="ItemTitle">
									<DIV align=right> 
									   <jsp:include page="/pagenavigator.jsp"  />  
								  </DIV>
								</TD>
							  </TR>
						  </TBODY>
						  </TABLE>
					 </TD>
					</TR>
				  
 <tr>
 <td>
<TABLE align=center height=26 width="99%">
               <TBODY>
                 <TR vAlign=middle>
                   <TD width="70%" height=20>
                     <DIV align=right></DIV>
                     <DIV align=right>
                       <INPUT name="cmdBack" type=button class=button onclick="javascript:location.href='<%=strContext%>/capital/query/q041-v.jsp';" value=" �� �� ">
                     </DIV>
                   </TD>
                 </TR>
               </TBODY>
</TABLE>
</td>
</tr>
</TBODY>	
</table>


<script language="JavaScript">
//firstFocus(document.frm.lClientIDCtrl);
////setSubmitFunction("doGoNext()");
setFormName("frm"); 
</script>

<script language="javascript">
var isSubmited=false;
//js ��������
/*
 *  ��ѯ1����¼������ϸ��ѯ
 *
 */
function queryDetail(id)
{
	
	
	frm.hdnId.value = id;
	frm.action = "<%=strContext%>/settlement/tran/reportlossorfreeze/control/c001.jsp";
	frm.strSuccessPageURL.value="../view/v029.jsp";
	frm.strFailPageURL.value="../view/v029.jsp";
	frm.strCtrlPageURL.value="../control/c001.jsp";
	frm.strAction.value = "<%=SETTConstant.Actions.TODETAIL%>";//����--�㽻�׺ŵ���ϸҳ��
	showSending();
	isSubmited=true;
	frm.submit();

	
}

function OrderPage(nOrderBy)
{
	frm.action = "q053-c.jsp";
    frm.strSuccessPageURL.value="/capital/query/q043-v.jsp";
     frm.strFailPageURL.value="/capital/query/q043-v.jsp";
    //frm.strCtrlPageURL.value="..control/c001.jsp";
    //frm.strAction.value = "<%=SETTConstant.Actions.MATCHSEARCH%>";//����--ƥ�����
	frm.Desc.value="<%=lDesc%>";
    frm.Order.value=nOrderBy;
	frm._pageLoaderKey.value='<%=strPageLoaderKey%>'
	frm.strAction.value="<%= Constant.PageControl.FIRSTPAGE %>";
	showSending();//��ʾ����ִ��
	frm.submit();
} 


function doGoNext()
{
	if(isSubmited)	
   {
   		alert("�������ύ�����Ժ�");
		return;
   }
	if(!validateFields(frm)) return;
	   frm.action = "../control/c001.jsp";
	   frm.strSuccessPageURL.value="../view/v001.jsp";
	   frm.strFailPageURL.value="../view/v001.jsp";
	   frm.strCtrlPageURL.value="../control/c001.jsp";
	   showSending();
	   isSubmited=true;
	   frm.submit();
}
function doGoSearch()
{
	if(isSubmited)	
   {
   		alert("�������ύ�����Ժ�");
		return;
   }
	frm.action="../control/c004.jsp";
	frm.strSuccessPageURL.value="../view/v003.jsp";
	frm.strFailPageURL.value="../view/v001.jsp";
	frm.strCtrlPageURL.value="../control/c004.jsp";
	showSending();
	isSubmited=true;
	frm.submit();
}
function allFields()
{
	this.aa=new Array("lClientID","�ͻ����","magnifier",1);
	this.ab=new Array("lAccountID","�˺�","magnifier",1);
	this.ac=new Array("lSubAccountID","�浥","magnifier",1);
	this.ad=new Array("strExecuteDate","��Ч����","date",1);
	
}
</script>
<%
	//��ʾ�ļ�β
	//SETTHTML.showHomeEnd(out);
	OBHtml.showOBHomeEnd(out);
%>
<%	}
	catch(Exception e)
    {
		
		e.printStackTrace();
		out.flush();
		return; 
    }
%>
<%@ include file="/common/SignValidate.inc" %>