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
		String strTemp="";
		String sPayeeAcctNoZoomCtrl="";
		String sPayerAccountNoZoomCtrl="";
		String npayeracctid="";
		String npayeeacctid="";	
		String sChineseAmount="";
		info.convertRequestToDataEntity(request);
		//状态
		//info.setNstatus(1);
		//客户id
		info.setNclientid(sessionMng.m_lClientID);
		//币种
		info.setNcurrencyid(sessionMng.m_lCurrencyID);
		//确认人(当前用户)
		//info.setNconfirmuserid(sessionMng.m_lUserID);
		//确认时间（系统当前时间）
		//info.setDtconfirm(DataFormat.getDateTime(DataFormat.getDateString().substring(0,10)));
		
		strTemp = (String)request.getParameter("sPayerAccountNoZoomCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)					 //付款方账号
		{				
			sPayerAccountNoZoomCtrl = strTemp.trim();
		}
		strTemp = (String)request.getParameter("sPayeeAcctNoZoomCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)					 //收款方账号
		{				
			sPayeeAcctNoZoomCtrl = strTemp.trim();
		}
		strTemp = (String)request.getParameter("npayeracctid");
		if (strTemp != null && strTemp.trim().length() > 0)					 //付款方ID
		{				
			npayeracctid = strTemp.trim();
		}
		strTemp = (String)request.getParameter("npayeeacctid");
		if (strTemp != null && strTemp.trim().length() > 0)					 //付款方ID
		{				
			npayeeacctid = strTemp.trim();
		}
		strTemp = (String)request.getParameter("sChineseAmount");
		if (strTemp != null && strTemp.trim().length() > 0)					 // 大写金额
		{				
			sChineseAmount = strTemp.trim();
		}
		
		
%>
<!--Get Data end-->

<!--Check start-->
<!--Check end-->


<!--Set FinanceInfo Attribute end-->

<!--Access DB start-->
<%
	/* 修改返回结果 */
		long lUpdateResult = -1;
	
		/* 初始化EJB */
		OBFinanceInstrHome financeInstrHome = null;
		OBFinanceInstr financeInstr = null;
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		financeInstr = financeInstrHome.create();
		//Log.print("--------------------lInstructionID="+lInstructionID);
		//lInstructionID = financeInstr.addCapitalTrans(financeInfo);
		//out.print("<script>alert(\"提交成功\")</script>");	
		//OBFinanceInstrEJB financeInstr=new OBFinanceInstrEJB();
		String act="";
		act=request.getParameter("act");
		long lInstructionID=-1;
		if(act!=null && act.equals("match"))
		{
			//match
			System.out.println("userID---------->" + sessionMng.m_lUserID);
			lInstructionID=financeInstr.matching(info,sessionMng.m_lUserID);
			if(lInstructionID==-1 ) 
			{   
				request.setAttribute("info",info);
				//构建页面分发时需要用到的实体
				PageControllerInfo pageControllerInfo = new PageControllerInfo();
				pageControllerInfo.setSessionMng(sessionMng);
				pageControllerInfo.setUrl("../view/v101.jsp?report=report");
				//分发
				RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));	
			   /* forward到结果页面 */
				rd.forward(request, response);
			}
			else{
				info=financeInstr.findByID(lInstructionID);
				request.setAttribute("info",info);

				//构建页面分发时需要用到的实体
				PageControllerInfo pageControllerInfo = new PageControllerInfo();
				pageControllerInfo.setSessionMng(sessionMng);
				pageControllerInfo.setUrl("../view/v102.jsp");
				//分发
				RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));	
			    /* forward到结果页面 */
				rd.forward(request, response);	
			}
			
		}
		if(act!=null && act.equals("check"))
		{
			//check
			lInstructionID=financeInstr.checkBankPay(info.getId(),sessionMng.m_lUserID);
			if(lInstructionID!=-1)
			{
				info=financeInstr.findByID(lInstructionID);
				request.setAttribute("info",info);
				request.setAttribute("isCheck","1");
				
				//构建页面分发时需要用到的实体
				PageControllerInfo pageControllerInfo = new PageControllerInfo();
				pageControllerInfo.setSessionMng(sessionMng);
				pageControllerInfo.setUrl("../view/v103.jsp");
				//分发
				RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
					
			   	/* forward到结果页面 */
				rd.forward(request, response);
			}
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