<%--
/*
 * 程序名称：f021-c.jsp
 * 功能说明：换开定期存单查询控制页面
 * 作　　者：刘琰
 * 完成日期：2004年01月08日
 */
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.util.*,
                 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.settlement.util.SETTConstant,
			     javax.servlet.*,
			     java.util.Collection" 
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<!--Header start-->

<%!
	/* 标题固定变量 */
	String strTitle = "[换开定期存单]";
%>

<%
	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getParameter("menu");
	Log.print("--------strMenu="+strMenu);
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
	request.setAttribute("menu", strMenu);
	String strTamp = request.getParameter("nOrderByCode");
	String OrderByCode = "";
	if(strTamp != null)
	{		
		OrderByCode = strTamp;
	}
	strTamp = request.getParameter("lID");
	String lID ="";
	if(strTamp == null)
		System.out.println("lID为null，非链接查询页面");
	else
		lID = strTamp;
	strTamp = request.getParameter("type");
	String type ="";
	if(strTamp == null)
		System.out.println("type为null，非链接查询页面");
	else
		type = strTamp;
		
%>


<%  
	/* 用户登录检测与权限校验 */
	try 
	{
		OBHtml.validateRequest(out, request, response);
	
%>

<!--Access DB start-->
<%
		String nextPage = "";
		/* 实例化信息类 */
		FinanceInfo financeInfo = new FinanceInfo();

		/* 初始化EJB */
		OBFinanceInstrHome financeInstrHome = null;
		OBFinanceInstr financeInstr = null;
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		financeInstr = financeInstrHome.create();
		financeInfo.setClientID(sessionMng.m_lClientID);
		financeInfo.setOrderByCode(OrderByCode);
		
		if(lID.equals(""))
		{
			System.out.println("id为空！");
			if(type.equals(""))
			{
				System.out.println("id为空里的type为空！");	
				if(OrderByCode.equals(""))
				{
					System.out.println("id为空里的type为空里的OrderByCode为空！");
					nextPage = "/capital/fixed/f022-v.jsp";
				}else 
				{
					System.out.println("id为空里的type为空里的OrderByCode不为空！");
					nextPage = "/capital/fixed/f022-v.jsp";					
				}
			}
		}else
		{
			System.out.println("id不为空！");
			if(type.equals(""))
			{
				System.out.println("id不为空里的type为空！");
				if(OrderByCode.equals(""))
				{
					System.out.println("id不为空里的type为空里的OrderByCode为空！");
					nextPage = "/capital/fixed/f022-v.jsp";
				}else 
				{
					System.out.println("id不为空里的type为空里的OrderByCode不为空！");
					financeInfo.setNDepositBillStatusId(SETTConstant.Actions.MODIFYSAVE);
					nextPage = "/capital/fixed/f022-v.jsp?type=linkSearch&lID=" + lID;
				}
			}else
			{
				System.out.println("id不为空里的type不为空！");
				nextPage = "/capital/fixed/f022-v.jsp";
			}
		}
		/* 调用方法获取信息对象 */	
		Collection searchResults = financeInstr.getTransOpenFixdDePosit(financeInfo);
			
	
%>
<!--Access DB end-->

<!--Forward start-->
<%
		/* 在请求中保存结果对象 */
	    request.setAttribute("searchResults", searchResults);
		
	    /* 获取上下文环境*/
	    //ServletContext sc = getServletContext();
		
	    /* 设置返回地址 */
	  
	    
	    
	    //构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(nextPage);
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
%>
<!--Forward end-->

