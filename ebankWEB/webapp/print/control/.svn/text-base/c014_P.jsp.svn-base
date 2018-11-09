<%--
 页面名称 ：c013_P.jsp
 页面功能 : 打印申请新增控制
 作    者 ：zyyao
 日    期 ：2007-8-3
 特殊说明 ：实现操作说明：
				1、
 修改历史 ：
--%>


<%@ page contentType="text/html;charset=gbk" %>	
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.iss.itreasury.util.*" %>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.settlement.ebankPrint.bizlogic.PrintEbankApplyBiz"%>
<%@ page import="com.iss.itreasury.evoucher.util.VOUConstant"%>
<%@ page import="com.iss.itreasury.settlement.ebankPrint.dataentity.PrintEbankApply"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>

<%
	/* 标题固定变量 */
	String strTitle = "[打印申请新增]";
%>

	<%	

		String strSuccessPageURL = "";
		String strFailPageURL = "";
		String strNextPageURL = "";
		String strActionResult = Constant.ActionResult.FAIL;
		String strAction = "";
		long lID = -1;
		Collection coll = null;
		long typeid = -1;
		String transno = "";
		
	try
	{
		/* 用户登录检测 */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }

		String strTemp = "";
		strTemp = (String)request.getAttribute("strSuccessPageURL");  //成功页面
		if ( strTemp != null && strTemp.length() > 0 )
		{
			strSuccessPageURL = strTemp;
		}
        strTemp = (String)request.getAttribute("strFailPageURL");  //失败页面
		if ( strTemp != null && strTemp.length() > 0 )
		{
			strFailPageURL = strTemp;
		}
		strTemp = (String)request.getAttribute("strAction");  //页面控制变量
		if ( strTemp != null && strTemp.length() > 0 )
		{
			strAction = strTemp;
		}
		strTemp = (String)request.getAttribute("lID");  //交易ID
		if ( strTemp != null && strTemp.trim().length() > 0 )
		{
			lID = Long.parseLong(strTemp);
		}
		System.out.println("lID===="+lID);
		strTemp = (String)request.getAttribute("typeid");  //业务类型ID
		if ( strTemp != null && strTemp.trim().length() > 0 )
		{
			typeid = Long.parseLong(strTemp);
		}
		System.out.println("typeid===="+typeid);
		strTemp = (String)request.getAttribute("transno");  //交易编号
		if ( strTemp != null && strTemp.trim().length() > 0 )
		{
			transno = strTemp;
		}
		System.out.println("transno===="+transno);
		
		//根据控制变量走向
		if ( strAction.equals("save") )
		{
			String seleAll[] = request.getParameterValues("seleAll");








           PrintEbankApplyBiz printEbankApplyBiz = new PrintEbankApplyBiz();
           PrintEbankApply printEbankApply = null;





			
			for ( int i=0;i<seleAll.length;i++ )
			{
					
				printEbankApply = new PrintEbankApply();


				printEbankApply.setNofficeid(sessionMng.m_lOfficeID);
	            printEbankApply.setNcurrency(sessionMng.m_lCurrencyID);
	            printEbankApply.setNprintcontentid(lID);
	            printEbankApply.setNprintcontentno(transno);
	            
	            printEbankApply.setNdeptid(VOUConstant.PrintSection.EBANKCUSTOMER);
	            //printEbankApply.setNbillid(Long.parseLong(Setid[(Integer.parseInt(h))-1]));
	            //printEbankApply.setNtempid(Long.parseLong(templateid[g]));
	            printEbankApply.setNstatusid(VOUConstant.VoucherStatus.REFUSE);
	            
	            printEbankApply.setIschapter(VOUConstant.IsChapter.NO);
	            printEbankApply.setNclientid(sessionMng.m_lClientID);
	            printEbankApply.setNinputuserid(sessionMng.m_lUserID);
	            printEbankApply.setNinputdate(Env.getSystemDateTime());

	            printEbankApply.setNtypeid(typeid);
	             printEbankApply.setIsebank(1);
                printEbankApply.setStempname(seleAll[i]);
				
				//现在只用于结算模块,以后需要新增加贷款模块
				printEbankApply.setNmoduleid(Constant.ModuleType.SETTLEMENT);
			    printEbankApplyBiz.updateEbankPrintstatus(printEbankApply);

			}
			
			strActionResult = Constant.ActionResult.SUCCESS;
			sessionMng.getActionMessages().addMessage("保存成功！");
		}
		else
		{
			strActionResult = Constant.ActionResult.FAIL;
		}
		
		//回到查询所有信息页面
		request.setAttribute("strSuccessPageURL",strContext + "/print/view/v011_P.jsp");
		request.setAttribute("strFailPageURL",strContext + "/print/view/v011_P.jsp");
		
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		sessionMng.getActionMessages().addMessage(exp);
		//出现异常,操作结果置为失败	
		strActionResult = Constant.ActionResult.FAIL;
	}	
	//将操作结果置入request中 
	strNextPageURL = (Constant.ActionResult.SUCCESS.equals(strActionResult)) ? strSuccessPageURL : strFailPageURL;

	System.out.println("strNextPageURL===="+strNextPageURL);

	//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextPageURL);
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	rd.forward( request,response );
%>