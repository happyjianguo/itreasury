<%--
 ҳ������ ��v001.jsp
 ҳ�湦�� : ��ʧҵ����������д���ҳ��
 ��    �� ��jinchen
 ��    �� ��2004-11-23
 ����˵�� ��
 ʵ�ֲ���˵����
 �޸���ʷ ��
--%>
<%@ page contentType="text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import="java.sql.Timestamp,
				com.iss.itreasury.util.PageCtrlInfo,
				com.iss.itreasury.util.Log,
				com.iss.itreasury.util.Constant,
			    com.iss.itreasury.util.Env,
				com.iss.itreasury.util.DataFormat,
				com.iss.itreasury.ebank.util.OBMagnifier,
				com.iss.itreasury.settlement.util.SETTHTML,
				com.iss.itreasury.settlement.util.NameRef,
				com.iss.itreasury.settlement.util.SETTConstant,
				com.iss.itreasury.loan.util.LOANConstant,
				com.iss.itreasury.settlement.reportlossorfreeze.dataentity.*,
				com.iss.itreasury.settlement.bizdelegation.TransAbatementDelegation,
				com.iss.itreasury.settlement.transabatement.dataentity.TransAbatementDetailInfo,
				com.iss.itreasury.settlement.transabatement.dataentity.TransAbatementInfo
				"
%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<%
	try{

	Log.print("=================����ҳ��reportlossorfreeze/view/v001.jsp=========");
	/** Ȩ�޼�� **/
	String strTableTitle = "ҵ���� ?? ��ʧ";
	   
	sessionMng.clearPageLoader();
	//��ʾ�ļ�ͷ

	OBHtml.showOBHomeHead(out, sessionMng, "��ʧҵ���ѯ", OBConstant.ShowMenu.YES);

	/**
	 * ��������
	 */
	long lCurrencyId				= sessionMng.m_lCurrencyID;			//��ǰϵͳʹ�ñ���ID
	long lOfficeId				    = sessionMng.m_lOfficeID;			//���´�ID
	//���ڿͻ�������Ϣ
	long lClientID = -1;
	String strClientNo = "";
	String strClientName = "";
	String startdate = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
	String enddate =  Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
	//�����˻�
	String strAccountNo = null;
	//�����˻�������Ϣ		
	String strCurrentAccountClientName = null;
	long lCurrentAccountID = -1;
	String strCurrentAccountNo = null;


	String strTemp = "";
	ReportLossOrFreezeInfo reportLossOrFreezeInfo=new ReportLossOrFreezeInfo();
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
				reportLossOrFreezeInfo.convertRequestToDataEntity(request);
			 }
				
		 }
	 }
	
	/**
	 * �������������ǳɹ�,��request�л�����к�dataentity�ֶΰ󶨵�����,���ҳ�����
	 */
	 if (pageInfo.getStrActionResult().equals(Constant.ActionResult.FAIL))
		reportLossOrFreezeInfo.convertRequestToDataEntity(request);

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
<!--����js�ļ�-->
<script language="JavaScript" src="/websett/js/Check.js" ></script>
<script language="JavaScript" src="/websett/js/Control.js" ></script>
<script language="JavaScript" src="/websett/js/SettCheck.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>

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
	<input type="hidden" name="hdnTransActionType" value="<%=SETTConstant.TransactionType.REPORTLOSS%>">
	<!--����ҵ������-->

  <TABLE width="100%" height="10" border=0 class=top>
       <TBODY>
        
		   <TR>
           <TD class=FormTitle height=2 width="100%"><B>	��ʧҵ���ѯ</B></TD>
         </TR>
         <TR>
           <TD width="100%" height=40 vAlign=bottom>
             <fieldset><TABLE align=center border=0 width="100%">
               <TBODY>
                 <TR borderColor="#E8E8E8">
									<%	
			
				long lOfficeIDC = sessionMng.m_lOfficeID;
				long lCurrencyIDC = sessionMng.m_lCurrencyID;
				String strFormNameC = "frm";
				String strCtrlNameC = "lClientID";
				String strTitleC = "<font color=#FF0000>*</font> �ͻ����";
				long lClientIDC = lClientID;
				String strClientNoC = strClientNo;
				String strFirstTDC = "";
				String SecondTDC = "";
				String[] sNextControlsClientC = {"startdate"};
				String strRtnClientNameCtrlC = "strClientName";
		
				OBMagnifier.createChildClientCtrl(
				out,
				lOfficeIDC,
				lCurrencyIDC,
				strFormNameC,
				strCtrlNameC,
				strTitleC,
				lClientIDC,
				strClientNoC,
				sessionMng.m_lClientID,
				strFirstTDC,
				SecondTDC,
				sNextControlsClientC,
				strRtnClientNameCtrlC);				
			 %>
									
                <TD height="20" width="16%"> �ͻ����� 
                  �� </TD>
									<TD height="20" width="39%">
										<textarea name="strClientName"  readonly rows="2" cols="30"><%=strClientName%></textarea>
									</TD>
								</TR>
                 <TR>
                   <TD >��ʼ���ڣ�</TD>
        	<TD>
          		<fs_c:calendar 
	          	    name="startdate"
		          	value="" 
		          	properties="nextfield ='enddate'" 
		          	size="20"/>
		        	  <script>
	          		$('#startdate').val('<%=startdate%>');
	          	</script>
            </TD>
			 <TD >��ֹ���ڣ�</TD>
          <TD>
          		<fs_c:calendar 
	          	    name="enddate"
		          	value="" 
		          	properties="nextfield ='submitfunction'" 
		          	size="20"/>
		         <script>
	          		$('#enddate').val('<%=enddate%>');
	          	</script>
            </TD>
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
                       <input name="Submit32" type="button" class="button" onClick="doGoNext();" value=" ���� ">
                     </DIV>
                   </TD>
                 </TR>
               </TBODY>
             </TABLE>
           </TD>
         </TR>


       </TBODY>
    </TABLE>
</form>	

<script language="JavaScript">
firstFocus(document.frm.lClientIDCtrl);
//setSubmitFunction("doGoNext()");
setFormName("frm"); 
</script>

<script language="javascript">
var isSubmited=false;
//js ��������
function doGoNext()
{
		if(isSubmited)	
		{
			alert("�������ύ�����Ժ�");
			return;
		}
	   if(!validateFields(frm)) 
		{
			return false;
		}
		if (!validate(frm) )
		{
			return false;
		}
			   
		   frm.action = "q032-c.jsp";
		   frm.strSuccessPageURL.value="/capital/query/q033-v.jsp";
		   frm.strFailPageURL.value="/capital/query/q033-v.jsp";
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
	frm.action="../control/c003.jsp";
	frm.strSuccessPageURL.value="../view/v002.jsp";
	frm.strFailPageURL.value="../view/v002.jsp";
	//frm.strCtrlPageURL.value="../control/c003.jsp";
	frm.strAction.value = "<%=SETTConstant.Actions.LINKSEARCH%>";//����--���Ӳ�ѯ
	showSending();
	isSubmited=true;
	frm.submit();
}
function allFields()
{
	
	this.aa=new Array("startdate","��ʼ����","date",0);
	this.ab=new Array("enddate","��ֹ����","date",0);
	
}

//���ڵĸ�ʽ
function validateDate(sDate)
{ 
  var iaDate = new Array(3);
	iaDate = sDate.toString().split("-");
	if (iaDate[0].length != 4 || iaDate[1].length != 2 || iaDate[2].length != 2) 
	 {
	 alert("��������ȷ�����ڸ�ʽ!");	
	 return false; 
	 }
	 return true;
} 

function validate(frm)
{
      
       var b = true;
	   if (!CompareDate(frm.startdate,frm.enddate,"ִ�����ڲ����ɴ�С�� "))

			return(false);
		if (frm.lClientID.value <=0)
		{
			alert("�ͻ���Ų���Ϊ��,��ӷŴ���ѡ��");
			return(false);
     	}

       return b;
}  
//��űȽ�
function CodeCompare(d_input1,d_input2,d_str)
{
	if (d_input1.value.length>0 && d_input2.value.length>0)
	{
		if (d_input1.value>d_input2.value)
		{
			alert(d_str+"�����ɴ���С��");
			d_input1.focus();
			return (false);
		}
	}
	return true;
}
























































































































































</script>
<%

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