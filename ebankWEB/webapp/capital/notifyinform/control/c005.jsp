<%--
 ҳ������ ��c001.jsp
 ҳ�湦�� : ֪֧ͨȡָ�� �ύ����ҳ��
 ��    �� ��YuanHuang
 ��    �� ��2006-11-03
 ����˵�� ��
--%>
<%@ page contentType="text/html;charset=gbk"%>

<%@ page import="com.iss.itreasury.settlement.util.*,
					com.iss.itreasury.util.*,
					com.iss.itreasury.util.DataFormat,
					com.iss.itreasury.ebank.util.OBHtml,				
					com.iss.itreasury.settlement.transfixeddeposit.dataentity.*,
					com.iss.itreasury.ebank.obnotifydepositinform.bizlogic.*,
					com.iss.itreasury.ebank.util.OBConstant,
					com.iss.itreasury.settlement.util.SETTConstant"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<script language="JavaScript" src="/webob/js/Control.js" ></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js" ></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
 <%@ include file="/common/common.jsp" %>


<%
String strNextURL = "../control/c002.jsp";

long lShowMenu = OBConstant.ShowMenu.YES;
String strTitle = "֪ͨ���֧ȡָ���ύ";
  try 
	{
      //�û���¼��� 
      if (sessionMng.isLogin() == false)
      {
      	OBHtml.showCommonMessage(out, sessionMng,  strTitle, "",1, "Gen_E002");
      	out.flush();
      	return;
      }

      // �ж��û��Ƿ���Ȩ�� 
      if (sessionMng.hasRight(request) == false)
      {
          out.println(sessionMng.hasRight(request));
      	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
      	out.flush();
      	return;
      }
	System.out.println("-------------------------c003.jsp---------------------------");
	
	long id = -1;
	String notifyDate="";//֪ͨ����
	double amount=0;//֧ȡ���
	String DepositNo="";//֪ͨ���ݺ�
	long statusID=-1;//״̬��ʶ
	
	if(request.getAttribute("id")!=null&&Long.parseLong((String)request.getAttribute("id"))>0)
	{
		id = Long.valueOf((String)request.getAttribute("id")).longValue();
	}	
	if (request.getAttribute("dDrawAmount")!= null && !request.getAttribute("dDrawAmount").equals(""))
	{
		amount = DataFormat.parseNumber((String)request.getAttribute("dDrawAmount"));
	}
	if (request.getAttribute("DepositNo1")!= null && !request.getAttribute("DepositNo1").equals(""))
	{
		DepositNo = (String) request.getAttribute("DepositNo1");
	}
	//Added by zwsun, 2007-6-25, �޸�֪ͨ����
	if (request.getAttribute("notifyDate")!= null && !request.getAttribute("notifyDate").equals(""))
	{
		notifyDate = (String) request.getAttribute("notifyDate");
	}else{
		notifyDate = DataFormat.getDateString();
	}	
	
	statusID = SETTConstant.NotifyInformStatus.SAVE;
	
	OBNotifyDepositInformBiz biz = new OBNotifyDepositInformBiz();
	NotifyDepositInformInfo info = new NotifyDepositInformInfo();
	
	//Modified by zwsun, 2006/6/25, ֪ͨ����
	//notifyDate = DataFormat.getDateString();
	
	info.setID(id);
	info.setNotifyDate(notifyDate);
	info.setAmount(amount);
	info.setDepositNo(DepositNo);
	info.setStatusID(statusID);

	long lret = -1;
	lret = biz.modify(info);
	if (lret>0)
	{
		sessionMng.getActionMessages().addMessage("�޸�֪֧ͨȡָ��ɹ���");
	}		 	 
	
	
	 

}
catch (IException ie)
{
    OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
	return;
}
RequestDispatcher rd = null;
response.sendRedirect(strNextURL);
%>
