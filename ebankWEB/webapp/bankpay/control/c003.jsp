<%
	/*
	*页面名称：c001.jsp
	*功能：银行汇款信息删除
	*作者：libaihui
	*time:2006-09-11
	*/
%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
				 com.iss.itreasury.util.*,
                 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
                 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.ebank.obsystem.bizlogic.*,
                 com.iss.itreasury.ebank.obfinanceinstr.dao.*,				 
				 com.iss.itreasury.ebank.obsystem.dataentity.*,
				 com.iss.itreasury.budget.util.*,
                 com.iss.itreasury.budget.executecontrol.dataentity.*,
                 com.iss.itreasury.ebank.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<!--Header end-->
<%!
	/* 标题固定变量 */
	String strTitle="[银行汇款]";
%>
<%
try{
		/* 实现菜单控制 */
		OBHtml.validateRequest(out,request,response);
		long lShowMenu = OBConstant.ShowMenu.YES;
		String strMenu = (String)request.getParameter("menu");
		Log.print("--------strMenu="+strMenu);
		if ((strMenu != null) && (strMenu.equals("hidden")))
		{
		    lShowMenu = OBConstant.ShowMenu.NO;
		}
		request.setAttribute("menu", strMenu);
		
		OBBankPayInfo  info=new OBBankPayInfo();		
		info.convertRequestToDataEntity(request);
		
		//删除操作
		OBFinanceInstrBiz biz =new OBFinanceInstrBiz();
		biz.deleteEbankByID(info.getId());
		/* 设置返回地址 */
		String doact=request.getParameter("doact");
	
	   	if(doact!=null && doact.equals("querydelete"))
	   	{
			System.out.print("*******querydelete**********"+info.getId());
%>
			 <script type="text/javascript">
				 	alert("删除成功！");
				 	window.close();
				 	window.opener.queryme();
				</script>
<%
		return;
		}	
		
		//构建页面分发时需要用到的实体
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl("../view/v001.jsp");
		//分发
		RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	   	
	   	/* forward到结果页面 */
		rd.forward(request, response);	
}
catch(IException ie) 
	{
		
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
	catch(Exception e) 
	{
		Log.print("e:"+e.toString());
		return;
    }
%>