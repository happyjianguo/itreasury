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

<%
	/**
	 * 页面控制类
	 */
	PageCtrlInfo pageInfo = new PageCtrlInfo();
	String strAction = "";
	String strTitle="[银行汇款]";

try
{	
	
	/** 权限检查 **/
	if (sessionMng.hasRight(request) == false)
	{
		OBHtml.showMessage(out,sessionMng,null,"登录",null,Constant.RecordStatus.INVALID,"Gen_E003");
		OBHtml.showOBHomeEnd(out);
		out.flush();
		return;
	}
	
	/* 初始化EJB */
	OBFinanceInstrHome financeInstrHome = null;
	OBFinanceInstr financeInstr = null;
	financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
	financeInstr = financeInstrHome.create();
	
	String doact=request.getParameter("doact");
	if(doact.equals("many"))
	{	//取消多个复核
		String[] retlong = null;
		long rlong = -1;
	 	if(request.getParameterValues("txtCheckbox")!=null)
	 	{
	 		retlong = request.getParameterValues("txtCheckbox");
	 	}
		
	 	if(retlong!=null)
		{
			String [] strR = new String[retlong.length];
			for(int i=0;i<retlong.length;i++)
			{
				//rlong = biz.Auditing(Long.parseLong(retlong[i]));
				rlong=financeInstr.cancelCheckBankPay(Long.parseLong(retlong[i]),sessionMng.m_lUserID);
				strR[i]=String.valueOf(rlong);
			}	
			
			/* 设置返回地址 */
			request.setAttribute("id",strR);
			
			//构建页面分发时需要用到的实体
			PageControllerInfo pageControllerInfo = new PageControllerInfo();
			pageControllerInfo.setSessionMng(sessionMng);
			pageControllerInfo.setUrl("../view/v105.jsp");
			//分发
			RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));	
		   /* forward到结果页面 */
			rd.forward(request, response);		
		}
	}
	if(doact.equals("one"))
	{	
		//单个取消复核页面提交的参数是该指令的各种信息
		OBBankPayInfo  info=new OBBankPayInfo();
		info.convertRequestToDataEntity(request);
		String[] strR=new String[1];
		long rlong=financeInstr.cancelCheckBankPay(info.getId(),sessionMng.m_lUserID);
		strR[0]=String.valueOf(rlong);
		/* 设置返回地址 */
		request.setAttribute("id",strR);

				
		//构建页面分发时需要用到的实体
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl("../view/v107.jsp");
		//分发
		RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	    /* forward到结果页面 */
		rd.forward(request, response);	
		
	}
	if(doact.equals("manysign"))
	{
		String[] retlong = null;
		long rlong = -1;
	 	if(request.getParameterValues("txtCheckbox")!=null)
	 	{
	 		retlong = request.getParameterValues("txtCheckbox");
	 	}
	 	
	 	if(retlong!=null)
		{
			String [] strR = new String[retlong.length];
			for(int i=0;i<retlong.length;i++)
			{
				//rlong = biz.Auditing(Long.parseLong(retlong[i]));
				rlong=financeInstr.signBankPay(Long.parseLong(retlong[i]),sessionMng.m_lUserID);
				strR[i]=String.valueOf(rlong);
			}	
		
			/* 设置返回地址 */
			request.setAttribute("id",strR);
				
			//构建页面分发时需要用到的实体
			PageControllerInfo pageControllerInfo = new PageControllerInfo();
			pageControllerInfo.setSessionMng(sessionMng);
			pageControllerInfo.setUrl("../view/v202.jsp");
			//分发
			RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
			
		   	/* forward到结果页面 */
			rd.forward(request, response);		
		}
	}
	
	
	if(doact.equals("manyauditing"))
	{
		String[] retlong = null;
		long rlong = -1;
	 	if(request.getParameterValues("txtCheckbox")!=null)
	 	{
	 		retlong = request.getParameterValues("txtCheckbox");
	 	}
	 	
	 	if(retlong!=null)
		{
			String [] strR = new String[retlong.length];
			for(int i=0;i<retlong.length;i++)
			{
				//rlong = biz.Auditing(Long.parseLong(retlong[i]));
				rlong=financeInstr.AuditingBankPay(Long.parseLong(retlong[i]),sessionMng.m_lUserID);
				strR[i]=String.valueOf(rlong);
			}	
		
			/* 设置返回地址 */
			request.setAttribute("id",strR);

			//构建页面分发时需要用到的实体
			PageControllerInfo pageControllerInfo = new PageControllerInfo();
			pageControllerInfo.setSessionMng(sessionMng);
			pageControllerInfo.setUrl("../view/v202.jsp");
			//分发
			RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));	
		   	/* forward到结果页面 */
			rd.forward(request, response);		
		}
	}
	
	if(doact.equals("onesign"))
	{	
		//单个签认
		OBBankPayInfo  info=new OBBankPayInfo();
		info.convertRequestToDataEntity(request);
		String[] strR=new String[1];
		long rlong=financeInstr.signBankPay(info.getId(),sessionMng.m_lUserID);
		strR[0]=String.valueOf(rlong);
		/* 设置返回地址 */
	
		request.setAttribute("id",strR);
		
		//构建页面分发时需要用到的实体
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl("../view/v202.jsp");
		//分发
		RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));		
	   	/* forward到结果页面 */
		rd.forward(request, response);	
		
	}
	if(doact.equals("cancelmanysign"))
	{
		String[] retlong = null;
		long rlong = -1;
	 	if(request.getParameterValues("txtCheckbox")!=null)
	 	{
	 		retlong = request.getParameterValues("txtCheckbox");
	 	}
	 	
	 	if(retlong!=null)
		{
			String [] strR = new String[retlong.length];
			for(int i=0;i<retlong.length;i++)
			{
				//rlong = biz.Auditing(Long.parseLong(retlong[i]));
				rlong=financeInstr.cancelSignBankPay(Long.parseLong(retlong[i]),sessionMng.m_lUserID);
				strR[i]=String.valueOf(rlong);
			}	
		
			/* 设置返回地址 */
			request.setAttribute("id",strR);
			
			//构建页面分发时需要用到的实体
			PageControllerInfo pageControllerInfo = new PageControllerInfo();
			pageControllerInfo.setSessionMng(sessionMng);
			pageControllerInfo.setUrl("../view/v204.jsp");
			//分发
			RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));	
		   	/* forward到结果页面 */
			rd.forward(request, response);		
		}
	}		
	if(doact.equals("cancelonesign"))
	{	
		//单个取消签认
		OBBankPayInfo  info=new OBBankPayInfo();
		info.convertRequestToDataEntity(request);
		String[] strR=new String[1];
		long rlong=financeInstr.cancelSignBankPay(info.getId(),sessionMng.m_lUserID);
		strR[0]=String.valueOf(rlong);
		/* 设置返回地址 */
		request.setAttribute("id",strR);
		
		//构建页面分发时需要用到的实体
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl("../view/v205.jsp");
		//分发
		RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		
	  	/* forward到结果页面 */
		rd.forward(request, response);	
		
	}
	
	if(doact.equals("cancelmanyauditing"))
	{
		String[] retlong = null;
		long rlong = -1;
	 	if(request.getParameterValues("txtCheckbox")!=null)
	 	{
	 		retlong = request.getParameterValues("txtCheckbox");
	 	}
	 	
	 	if(retlong!=null)
		{
			String [] strR = new String[retlong.length];
			for(int i=0;i<retlong.length;i++)
			{
				//rlong = biz.Auditing(Long.parseLong(retlong[i]));
				rlong=financeInstr.cancelAuditingBankPay(Long.parseLong(retlong[i]),sessionMng.m_lUserID);
				strR[i]=String.valueOf(rlong);
			}	
		
			/* 设置返回地址 */
			request.setAttribute("id",strR);

			
			//构建页面分发时需要用到的实体
			PageControllerInfo pageControllerInfo = new PageControllerInfo();
			pageControllerInfo.setSessionMng(sessionMng);
			pageControllerInfo.setUrl("../view/v204.jsp");
			//分发
			RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
			
		   	/* forward到结果页面 */
			rd.forward(request, response);		
		}
	}	
	
	if(doact.equals("oneauditing"))
	{	
		//单个签认
		OBBankPayInfo  info=new OBBankPayInfo();
		info.convertRequestToDataEntity(request);
		String[] strR=new String[1];
		long rlong=financeInstr.AuditingBankPay(info.getId(),sessionMng.m_lUserID);
		strR[0]=String.valueOf(rlong);
		/* 设置返回地址 */
		request.setAttribute("id",strR);

		
		//构建页面分发时需要用到的实体
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl("../view/v202.jsp");
		//分发
		RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));	
	   	/* forward到结果页面 */
		rd.forward(request, response);	
		
	}	
	
	if(doact.equals("canceloneauditing"))
	{	
		//单个取消签认
		OBBankPayInfo  info=new OBBankPayInfo();
		info.convertRequestToDataEntity(request);
		String[] strR=new String[1];
		long rlong=financeInstr.cancelAuditingBankPay(info.getId(),sessionMng.m_lUserID);
		strR[0]=String.valueOf(rlong);
		System.out.println("*************"+rlong);
		/* 设置返回地址 */
		request.setAttribute("id",strR);
		
		
		//构建页面分发时需要用到的实体
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl("../view/v205.jsp");
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