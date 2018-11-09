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
String strNextURL = "../control/c002.jsp";

long lShowMenu = OBConstant.ShowMenu.YES;
String strTitle = "通知存款支取指令提交";
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
	System.out.println("-------------------------c003.jsp---------------------------");
	
	long id = -1;
	String notifyDate="";//通知日期
	double amount=0;//支取金额
	String DepositNo="";//通知存款单据号
	long statusID=-1;//状态标识
	
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
	//Added by zwsun, 2007-6-25, 修改通知日期
	if (request.getAttribute("notifyDate")!= null && !request.getAttribute("notifyDate").equals(""))
	{
		notifyDate = (String) request.getAttribute("notifyDate");
	}else{
		notifyDate = DataFormat.getDateString();
	}	
	
	statusID = SETTConstant.NotifyInformStatus.SAVE;
	
	OBNotifyDepositInformBiz biz = new OBNotifyDepositInformBiz();
	NotifyDepositInformInfo info = new NotifyDepositInformInfo();
	
	//Modified by zwsun, 2006/6/25, 通知日期
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
		sessionMng.getActionMessages().addMessage("修改通知支取指令成功。");
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
