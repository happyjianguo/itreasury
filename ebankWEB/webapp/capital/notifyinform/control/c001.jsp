<%--
 页面名称 ：c001.jsp
 页面功能 : 通知支取指令 提交控制页面
 作    者 ：YuanHuang
 日    期 ：2006-11-03
 特殊说明 ：
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
String strTitle = "通知存款支取通知";
  try 
	{
      //用户登录检测 
      if (sessionMng.isLogin() == false)
      {
      	OBHtml.showCommonMessage(out, sessionMng,  strTitle, "",1, "Gen_E002");
      	out.flush();
      	return;
      }

      // 判断用户是否有权限 
      if (sessionMng.hasRight(request) == false)
      {
          out.println(sessionMng.hasRight(request));
      	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
      	out.flush();
      	return;
      }
	System.out.println("-------------------------c001.jsp---------------------------");
	
	long lret = -1;
		
	long moduleID=-1;//模块标识
	long officeID=-1;//办事处标识
	String DepositNo="";//通知存款单据号
	String notifyDate="";//通知日期
	double amount=0;//支取金额
	long statusID=-1;//状态标识
	long userID= -1;//通知人ID
	
	NotifyDepositInformInfo info = new NotifyDepositInformInfo();
	OBNotifyDepositInformBiz biz = new OBNotifyDepositInformBiz();
	
	moduleID = Constant.ModuleType.EBANK;
	officeID = sessionMng.m_lOfficeID;
	
	if (request.getAttribute("lSubAccountID")!= null && !request.getAttribute("lSubAccountID").equals(""))
	{
		DepositNo = (String) request.getAttribute("lSubAccountID");
	}
	
	//Added by zwsun, 2007-06-18， 通知日期可页面输入
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
		sessionMng.getActionMessages().addMessage("保存通知支取指令成功。");  
	}
	
	 

}
catch (IException ie)
{
    OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
	return;
}
//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextURL);
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));


rd.forward( request,response );

%>
