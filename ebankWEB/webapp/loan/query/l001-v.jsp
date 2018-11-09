<!--Header start-->
<%
/**
 * ҳ������ ��l001-v.jsp
 * ҳ�湦�� : ��ʾ��λ���ϣ������ί�д���Ҳ��ʾί�е�λ����
 * ����˵�� ��
 */
%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,java.util.*,java.net.URLEncoder,
			com.iss.itreasury.loan.util.*,
			com.iss.itreasury.ebank.util.*,			
			com.iss.itreasury.ebank.obsystem.bizlogic.*,
			com.iss.itreasury.loan.loancommonsetting.dataentity.*,			
			java.rmi.RemoteException,
			com.iss.itreasury.util.*"
%>
<!--Header end-->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
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
	
       	OBHtml.showOBHomeHead(out,sessionMng,"[ָ���ѯ]",Constant.YesOrNo.YES);
        
%>	


<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/Control.js"></script>
<safety:resources />
<script language="javascript">

firstFocus(frm.wtclientname);
//setSubmitFunction("confirmSave(frm)");
setFormName("frm");	 	
</script>
<%
	OBHtml.showOBHomeEnd(out);
	}
	catch( IException ie )
	{
		ie.printStackTrace();
		out.flush();
		return; 
	}
%>
<%@ include file="/common/SignValidate.inc" %>