<%
/*
 复核 匹配
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
%>
<%	

		OBBankPayInfo  info=new OBBankPayInfo();
		
		info.convertRequestToDataEntity(request);
		//状态
		//info.setNstatus(1);
		//客户id
		info.setNclientid(sessionMng.m_lClientID);
		//币种
		info.setNcurrencyid(sessionMng.m_lCurrencyID);
		
		System.out.println("**************************"+info);
		
%>
<!--Get Data end-->

<!--Check start-->
<!--Check end-->


<!--Set FinanceInfo Attribute end-->

<!--Access DB start-->
<%
		/* 初始化EJB */
		OBFinanceInstrHome financeInstrHome = null;
		OBFinanceInstr financeInstr = null;
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		financeInstr = financeInstrHome.create();

		//OBFinanceInstrEJB financeInstr=new OBFinanceInstrEJB();
		long lInstructionID=-1;
		lInstructionID=financeInstr.checkBankPay(info.getId(),sessionMng.m_lUserID);
		if(lInstructionID!=-1)
		{
			info=financeInstr.findByID(lInstructionID);
			request.setAttribute("info",info);
			/* 获取上下文环境 */
			ServletContext sc = getServletContext();

			//构建页面分发时需要用到的实体
            PageControllerInfo pageControllerInfo = new PageControllerInfo();
            pageControllerInfo.setSessionMng(sessionMng);
            pageControllerInfo.setUrl("../view/check_v004.jsp");
            //分发
            RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
				
		   /* forward到结果页面 */
			rd.forward(request, response);
		}					
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