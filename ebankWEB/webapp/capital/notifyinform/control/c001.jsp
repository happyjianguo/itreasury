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
String strNextURL = "../view/v001.jsp";  
long lShowMenu = OBConstant.ShowMenu.YES;
String strTitle = "֪ͨ���֧ȡ֪ͨ";
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
	System.out.println("-------------------------c001.jsp---------------------------");
	
	long lret = -1;
		
	long moduleID=-1;//ģ���ʶ
	long officeID=-1;//���´���ʶ
	String DepositNo="";//֪ͨ���ݺ�
	String notifyDate="";//֪ͨ����
	double amount=0;//֧ȡ���
	long statusID=-1;//״̬��ʶ
	long userID= -1;//֪ͨ��ID
	
	NotifyDepositInformInfo info = new NotifyDepositInformInfo();
	OBNotifyDepositInformBiz biz = new OBNotifyDepositInformBiz();
	
	moduleID = Constant.ModuleType.EBANK;
	officeID = sessionMng.m_lOfficeID;
	
	if (request.getAttribute("lSubAccountID")!= null && !request.getAttribute("lSubAccountID").equals(""))
	{
		DepositNo = (String) request.getAttribute("lSubAccountID");
	}
	
	//Added by zwsun, 2007-06-18�� ֪ͨ���ڿ�ҳ������
	if(request.getAttribute("notifyDate")!= null && !request.getAttribute("notifyDate").equals("")){
		notifyDate =  (String)request.getAttribute("notifyDate");
	}
	
	if (request.getAttribute("dDrawAmount")!= null && !request.getAttribute("dDrawAmount").equals(""))
	{
		amount = DataFormat.parseNumber((String)request.getAttribute("dDrawAmount"));
	}
	statusID = SETTConstant.NotifyInformStatus.SAVE;
	userID = sessionMng.m_lUserID;
	
	info.setModuleID(moduleID);
	info.setOfficeID(officeID);
	info.setDepositNo(DepositNo);
	info.setNotifyDate(notifyDate);
	info.setAmount(amount);
	info.setStatusID(statusID);
	info.setUserID(userID);
	
	lret = biz.add(info);
	if ( lret > 0 )
	{
		sessionMng.getActionMessages().addMessage("����֪֧ͨȡָ��ɹ���");  
	}
	
	 

}
catch (IException ie)
{
    OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
	return;
}
//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextURL);
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));


rd.forward( request,response );

%>
