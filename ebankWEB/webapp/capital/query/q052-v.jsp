<%--
 ҳ������ ��v009.jsp
 ҳ�湦�� : ��ʧҵ������ϸ�������ҳ��2
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

<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>


<%
	try{

	Log.print("=================����ҳ��reportlossorfreeze/view/v009.jsp=========");
	/** Ȩ�޼�� **/
	String strTableTitle = "ҵ���� ���� ��ʧ";
	   
	//������
	//if(!SETTHTML.validateRequest(out, request,response)) return;
		
	//��ʾ�ļ�ͷ
	//SETTHTML.showHomeHead(out,sessionMng,Env.getClientName());
	OBHtml.showOBHomeHead(out, sessionMng, "������ϸ��Ϣ", OBConstant.ShowMenu.NO);
	/**
	 * ��������
	 */
	long lCurrencyId				= sessionMng.m_lCurrencyID;			//��ǰϵͳʹ�ñ���ID
	long lOfficeId				    = sessionMng.m_lOfficeID;			//���´�ID
	String strTemp = "";
	TransAbatementInfo transAbatementInfo=new TransAbatementInfo();
	String isBack="false";
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
	ReportLossOrFreezeInfo info = new ReportLossOrFreezeInfo();
	info = (ReportLossOrFreezeInfo)request.getAttribute("ResultInfo");
	if(info==null)
	{
		info = new ReportLossOrFreezeInfo();
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
	<input type="hidden" name="hdnId" value="<%=info.getId()%>">
	<input type="hidden" name="hdnTransActionType" value="<%=SETTConstant.TransactionType.REPORTLOSS%>">
	<!--����ҵ������-->

  <TABLE width="100%" height="10" border=0 class=top>
       <TBODY>
	

	
		 <TR>
           <TD class=FormTitle height=2 width="100%"><B>���� ���� ��ϸ��Ϣ</B></TD>
         </TR>
         <TR>
           <TD width="100%" height=40 vAlign=bottom>
             <fieldset>
             <TABLE align=center border=0 width="100%">
               <TBODY>
				             <TR>
                   <td >���ױ�ţ�</td>
                   <td><input type="text" name="lClientIDCtrl3" class="box"  maxlength='30' value="<%=DataFormat.formatString(info.getTransNo())%>" disabled>
                   </td>
                   <TD height=20>&nbsp;</TD>
                   <TD height=20>&nbsp;</TD>
                 </TR>
                 <TR>
                   <input type="hidden" name="lClientID" value="-1">
                   <input type="hidden" name="lClientIDFromClient" value="0">
                   <td width="19%" > �ͻ���ţ�&nbsp;</td>
                   <td width="27%"><input type="text" name="lClientIDCtrl" class="box"  maxlength='30' value='<%=DataFormat.formatString(NameRef.getClientCodeByID(info.getClientId()))%>' disabled>
                   </td>
                  
                   <TD width="18%" height=20>�ͻ����ƣ�</TD>
                 <TD width="36%" height=20><textarea class="box" name="lClientIDCtrl2"  disabled ><%=DataFormat.formatString(NameRef.getClientCodeByID(info.getClientId()))%></textarea></TD>
                 </TR>
                 <TR>
                   <script language="javascript">showDisableAccountCtrl("strAccountNo","<%=DataFormat.formatString(NameRef.getAccountNoByID(info.getAccountId()))%>","�˺�","width=15%","width=30%");
					</script>
                    <td > �浥�ţ�</td>
                   <td ><input type="text" name="lClientIDCtrl4" class="box"  maxlength='30' value='<%=DataFormat.formatString(info.getOldDepositNo())%>' disabled>
				</td>
                 </TR>
                 <TR>
                   <!--**************************�����˻�******************************-->
                   <input type="hidden" name="lDiscountAccountID" value="-1">
                   <input type="hidden" name="lDiscountAccountIDClientID" value="-1">
                   <td >��Ч���ڣ� </td>
                 <td ><input type="text" name="lClientIDCtrl42" class="box"  maxlength='30' value='<%=DataFormat.getDateString(info.getEffectiveDate())%>' disabled></td>
                   <TD width="18%" height=20 vAlign=center>�������ޣ��£��� </TD>
                   <TD width="36%" height=20 vAlign=top><input type="text" name="lClientIDCtrl4222" class="box"  maxlength='30' value='<%=DataFormat.formatNumber(info.getFreezeTerm())%>' disabled>
</TD>
                 </TR>
                 <TR>
                   <td >�������ڣ�</td>
                   <td><input type="text" name="lClientIDCtrl423" class="box"  maxlength='30' value='<%=DataFormat.getDateString(info.getFreezeEndDate())%>' disabled></td>
                   <td >���᷽ʽ��</td>
                   <td>
				     <%
				SETTConstant.SubAccountStatus.showList(out,"freezeType", 4, info.getFreezeType(), false, true, " disabled ",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
				%>
				   </td>
                 </TR>
                 <TR>
                   <td >�����Ԫ����</td>
                   <td><input type="text" name="lClientIDCtrl4232" class="box"  maxlength='30' value='<%=DataFormat.formatAmount(info.getFreezeAmount())%>' disabled></td>
                   <td >ִ�л��أ�</td>
                   <td><input type="text" name="lClientIDCtrl4223" class="box"  maxlength='30' value='<%=DataFormat.formatString(info.getExecuteGovernment())%>' disabled></td>
                 </TR>
                 <TR>
                   <td >���뵥λ��</td>
                   <td><input type="text" name="lClientIDCtrl42322" class="box"  maxlength='30' value='<%=DataFormat.formatString(info.getApplyCompany())%>' disabled></td>
                   <td >���������ţ�</td>
                   <td><input type="text" name="lClientIDCtrl42232" class="box"  maxlength='30' value='<%=DataFormat.formatString(info.getLawWritNo())%>' disabled></td>
                 </TR>
                 <TR>
                   <!--*********************���ֿͻ���ͬ*******************************-->
                   <input type="hidden" name="lDiscountContractID" value="-1">
                   <input type="hidden" name="lDiscountContractIDClientID" value="-1">
                   <td > ժҪ��</td>
                   <td>                     <input type="text" name="lClientIDCtrl43" class="box"  maxlength='30' value='<%=DataFormat.formatString(info.getAbstract())%>' disabled>
                 </td>
                   <td >ִ�����ڣ�</td>
                   <td><input type="text" name="lClientIDCtrl422" class="box"  maxlength='30' value='<%=DataFormat.getDateString(info.getExecuteDate())%>' disabled></td>
                   
                   <!--*****************************************************************************-->
                 </TR>






                
             
               </TBODY>
             </TABLE>
             </fieldset>
           </TD>
         </TR>
         <TR>
         <TD height=10 vAlign=top width="100%">
             <TABLE align=center height="15" width="97%">
               <TBODY>
                 <TR>
                   <TD colSpan='6' height="10"><DIV align=right>
                      
                       <input name="Submit32" type="button" class="button" onClick="doBack();" value=" �� �� " >
                     </DIV>
                   </TD>
                 </TR>
               </TBODY>
             </TABLE>
             <table width="100%" border="0">
               <tr>
                 <td >¼���ˣ�</td>
                 <td><%=DataFormat.formatString(NameRef.getUserNameByID(info.getInputUserId()))%></td>
              
                <TD >¼������:</TD>
                <TD ><%=DataFormat.getDateString(info.getInputDate())%></TD>
                <TD >������:</TD>
                <TD ><%=DataFormat.formatString(NameRef.getUserNameByID(info.getCheckUserId()))%></TD>
                <TD >��������:</TD>
                <TD ><%=DataFormat.getDateString(info.getCheckDate())%></TD>
                <TD >״̬:</TD>
                <TD><%=DataFormat.formatString(SETTConstant.TransactionStatus.getName(info.getStatus()))%></TD>
               </tr>
             </table></TD>
         </TR>



       </TBODY>
    </TABLE>
</form>	

<script language="JavaScript">
//firstFocus(document.frm.lClientIDCtrl);
//setSubmitFunction("doGoNext()");
setFormName("frm"); 
</script>

<script language="javascript">
var isSubmited=false;
//js ��������
function doBack()
{
	if(confirm("�Ƿ�ȷ���رմ��ڣ�"))
	window.close();
}
function doGoNext()
{
	
	
		if(isSubmited)	
		{
			alert("�������ύ�����Ժ�");
			return;
		}
		

		
			   
		   frm.action = "../control/c003.jsp";
		   frm.strSuccessPageURL.value="/settlement/tran/reportlossorfreeze/view/v008.jsp";
		   frm.strFailPageURL.value="/settlement/tran/reportlossorfreeze/view/v008.jsp";
		   //frm.strCtrlPageURL.value="/settlement/tran/reportlossorfreeze/control/c003.jsp";
		   frm.strAction.value = "<%=SETTConstant.Actions.MATCHSEARCH%>";//����--ƥ�����
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
	frm.action="../control/c001.jsp";
	frm.strSuccessPageURL.value="../view/v007.jsp";
	frm.strFailPageURL.value="../view/v007.jsp";
	frm.strCtrlPageURL.value="../control/c001.jsp";
	frm.strAction.value = "<%=SETTConstant.Actions.LINKSEARCH%>";//����--���Ӳ�ѯ
	showSending();
	isSubmited=true;
	frm.submit();
}
function allFields()
{
	
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