<%
/**ҳ�湦��˵��
 * ҳ������ ��v011.jsp
 * ҳ�湦�� : ��ʷ����ѯ  ��ʾ��
 * ��    �� ��barneyliu
 * ��    �� ��2005-11-28
 * ��ʵ��˵����
 *				1��
 * ����˵�� ��
 * �޸���ʷ ��
 */
%>
<%@ page contentType="text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="common" %>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
	 response.setHeader("Pragma","no-cache");
	 int i = 0;
     String strRootPath = request.getContextPath();//http://xxxx/../iTreasury-ebank
 	 String colsname  = null;//en zh
    try
	{
         
        //��¼���
		//������
		 /*if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E002");
        	out.flush();
        	return;
        }*/
		

        /* �ж��û��Ƿ���Ȩ�� */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E003");
        	out.flush();
        	return;
        }
       OBHtml.showOBHomeHead(out, sessionMng, "�����˻���ʷ���", 1);
       
       String strDateStart = DataFormat.getDateString();
	   String strDateEnd = DataFormat.getDateString();
%>
<!--������Ϣ����ҳ��,��ҳ����Ե������ڵ���ʽ�����Ѿ���׽�����쳣-->
<jsp:include page="/ShowMessage.jsp"/>
<!--����js�ļ�-->
<script language="JavaScript" src="/webob/js/Control.js" ></script>
<script language="JavaScript" src="/webob/js/Check.js" ></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webscript/taglib.js"></script>
<safety:resources />

<!--form ��ʼ-->
<form name="frmV011" method=post>
<!-- ����ҳ����Ʋ��� -->
<input type="hidden" name="strSourcePage" value="">
<input type="hidden" name="strSuccessPageURL" value="">
<input type="hidden" name="strFailPageURL" value="">
<input type="hidden" name="strAction" value="findFirst">
<input type="hidden" name="strCtrlPageURL" value="a131-c.jsp">
 
<TABLE  align=center border="0" cellpadding="2" cellspacing="0" width=98% class="top">

		<tr>
		<td colspan="4" height="1" class=FormTitle>�����˻���ʷ����ѯ</td>
		</tr>
			<!--tag--multipleselect-->
          
			<TR>
			<td>
					<% String sql="SELECT n_id id,s_name name FROM BS_COUNTRYSETTING WHERE n_rdStatus = " + Constant.RecordStatus.VALID;%>
					<common:multipleSelect   form="frmV011" name="countryId"  label="����"  sql="<%=sql%>" sqlKey="id" sqlValue="name"/>			 
		 	</td>
		 	</TR>
			 <TR>
			 <td>
				 	
						<%sql="SELECT n_Id id,s_name name FROM bs_accountpropertyonesetting WHERE n_rdStatus =  " + Constant.RecordStatus.VALID;%>
						<common:multipleSelect   form="frmV011" name="areaCenter"  label="��������" sql="<%=sql%>"  sqlKey="id" sqlValue="name" />
			</td>	 
		 	</TR>
 			<TR>
 			<td>	 
						<%sql="SELECT  n_Id id,s_name name FROM  BS_BANKSETTING  WHERE  n_rdStatus  = " + Constant.RecordStatus.VALID+"union select -9,'����˾' from dual";%>
						<common:multipleSelect   form="frmV011" name="bankId"   label="��������"  sql="<%=sql%>"  sqlKey="id" sqlValue="name" />
			</td>	 
		 	</TR>
		 	<TR>
		 	<td>	 
						<%sql="select t.n_id id,t.s_name_zh name from bs_currencysetting t where N_RDSTATUS="+Constant.RecordStatus.VALID;%>
		    			<common:multipleSelect form="frmV011" name="currencyId"   label="����" sql="<%=sql%>" sqlKey="id" sqlValue="name"/>			 
			</td>
			</TR>
			
			<TR>
			<td>
				 	
						<%sql="select t.id id,t.saccountno name from sett_account t where t.ncheckstatusid = 4  and t.nclientid ="+sessionMng.m_lClientID+" union select b.n_id id,b.s_accountno name from bs_bankaccountinfo b where b.n_ischeck = 1 and b.n_rdstatus =1 and b.n_clientid="+sessionMng.m_lClientID+" order by name";
						//sql="select t.naccountid id,t.saccountno name from ob_accountownedbyuser t where t.nuserid="+sessionMng.m_lUserID;%>
						<common:multipleSelect form="frmV011" name="accountId"    sqlKey="id" sqlValue="name" label="�˻�"  sql="<%=sql%>"/>
				 
			</td>
			</TR>
			</table>
			
					 	
          <!--tag---dateInput-->
      <table width=98% class="top">
      <tr class="MsoNormal"> 
          <td width="5" height="25" class="MsoNormal"></td>
          <td height="25" class="MsoNormal">��ѯ���ڣ�</td>
          <td width="60" height="25" class="MsoNormal">
            <div align="right">��</div>
          </td>
          <td  height="25" class="MsoNormal"> 
          	 <fs_c:calendar 
          	    name="startDate"
	          	value="" 
	          	size="20"/>
	          	<script>
	          		$('#startDate').val('<%=strDateStart%>');
	          	</script>
			��
		     <fs_c:calendar 
          	    name="endDate"
	          	value="" 
	          	size="20"/>
	            <script>
	          		$('#endDate').val('<%=strDateEnd%>');
	          	</script>
		  </td>
          <td width="5" class="MsoNormal"></td>
        </tr>
        </table>


<hr>
	<!--tag--button-->
	<table width=75%>
		<TR>
			<TD align="center">
		        <TABLE align=center border=0 width=99%>
					<TR align="right">
						<TD align="right">
							<input type="button" class="button" name="button1" value="�� ѯ" onclick="javascript:doSearch();" onfocus="javascript:nextField='submitfunction'"/>						
						</TD>
					</TR>
				</TABLE>
			</TD>
	    </TR>
	    </table>
		
  
		<input type="hidden" name="control" value="view">
 
	 </form>

  <script language="javascript">
	/* ҳ�潹�㼰�س����� */
	firstFocus(frmV011.countryIdLeft);
	//setSubmitFunction("doSearch()");
	setFormName("frmV011");
</script>

<script language="javascript">

function doSearch()
{
	var strDate = "<%=strDateStart%>";
	
	//�������
	if( !(CompareDate(frmV011.startDate,frmV011.endDate,"�������ڲ������ڿ�ʼ����")) )
	{
		return false;
	}
	frmV011.startDate.value = formatedate(frmV011.startDate.value);//��ʽ������
	frmV011.endDate.value = formatedate(frmV011.endDate.value);
	if(!(CompareDateString(frmV011.startDate.value,strDate)))
	{
		alert("��ѯ��ʼ���ڲ������ڵ�ǰ����");
		return false;
	}
	if(!(CompareDateString(frmV011.endDate.value,strDate)))
	{
		alert("��ѯ�������ڲ������ڵ�ǰ����");
		return false;
	}
		
		showSending();//��ʾ����ִ��
		frmV011.action = "a131-c.jsp"
		frmV011.strSuccessPageURL.value="/accountinfo/a132-v.jsp";	//��������ɹ���������ҳ��
		frmV011.strFailPageURL.value="/accountinfo/a130-v.jsp";		//����ʧ�ܺ�������ҳ��
		frmV011.strAction.value="findFirst";	//�����������
		frmV011.submit();
	
} 
</script>   
<%	
	/**
	* ��ʾ�ļ�β
	*/
	OBHtml.showOBHomeEnd(out);
}
//�쳣����
catch(Exception exp)
{
	Log.print(exp.getMessage());
	OBHtml.showCommonMessage(out,sessionMng,"","",1,"Gen_E001");
}
%>

<%@ include file="/common/SignValidate.inc" %>
