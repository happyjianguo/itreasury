<%
/**
 * 页面名称 ：q017-c.jsp
 * 页面功能 : 贷款申请书打印
 * 作    者 ：gump
 * 日    期 ：2003-10-27
 *			  
 * 转入页面 : 1020-c.jsp
 */
%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
	java.util.*,
	java.net.URLEncoder,
	com.iss.itreasury.loan.loancommonsetting.dataentity.*,
	com.iss.itreasury.loan.util.*,	
	com.iss.itreasury.ebank.util.*,
	com.iss.itreasury.ebank.obloanapply.dataentity.*,
	com.iss.itreasury.loan.loanapply.dataentity.*,	
	com.iss.itreasury.ebank.obsystem.bizlogic.*,
	com.iss.itreasury.ebank.obquery.bizlogic.*,
	com.iss.itreasury.ebank.obcontent.bizlogic.*,
	com.iss.itreasury.loan.contract.dao.*,
	com.iss.itreasury.system.approval.bizlogic.ApprovalBiz,
	com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo,
	com.iss.itreasury.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%	
	long        loanID=-1;          //贷款申请流水号
	long		userID=-1;
	long		loanTypeID=-1;
	String      strURL="";
	String 		tmp="";
	int i =0;
	
	/* 用户登录检测与权限校验及文件头显示 */
    try 
    {
		//判断是否登录
		if( !sessionMng.isLogin() )
		{	
			OBHtml.showMessage(out,sessionMng,null,"登录",null,Constant.RecordStatus.INVALID,"Gen_E002");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		//判断是否有权限
		if (sessionMng.hasRight(request) == false)
		{
			OBHtml.showMessage(out,sessionMng,null,"登录",null,Constant.RecordStatus.INVALID,"Gen_E003");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		
		userID=sessionMng.m_lUserID;
		tmp=(String)request.getAttribute("lLoanID");
		if ( (tmp!=null)&&(tmp.length()>0) )
			loanID=Long.valueOf((String)request.getAttribute("lLoanID")).longValue();	
		
		OBLoanQuery lla = new OBLoanQuery();
		OBSystemHome home2 = (OBSystemHome)EJBObject.getEJBHome("OBSystemHome");
		OBSystem lcs = home2.create();
		
					
		/*查询申请信息*/
		LoanApplyInfo laInfo=lla.findByID(loanID);		
		loanTypeID=laInfo.getTypeID();
		
		// add by fxzhang 2006-1-3
		long subloanTypeID=laInfo.getSubTypeId();
		
		//查询计划信息
	
		Collection c=lla.findPlanByLoanID(loanID,1,10000,99,1);
		long clientID=laInfo.getBorrowClientID();
		long wtClientID=laInfo.getConsignClientID();
		ClientInfo clientInfo=lcs.findClientByID(clientID);
		ClientInfo wtClientInfo=lcs.findClientByID(wtClientID);
			
		request.setAttribute("LoanApplyInfo",laInfo);
		request.setAttribute("planInfo",c);
		request.setAttribute("ClientInfo",clientInfo);
		request.setAttribute("wtClientInfo",wtClientInfo);
		
		int iCount = 0;
		
		if ( loanTypeID==LOANConstant.LoanType.WT )
		{
			strURL="/loan/query/q018-p.jsp";
			iCount = 6;
		}
		else
		{
			strURL="/loan/query/q019-p.jsp";
			iCount = 8;
		}
		
		long lModuleID = Constant.ModuleType.LOAN; //模块类型
		long lLoanTypeID = -1; //贷款类型
		long lActionID = Constant.ApprovalAction.LOAN_APPLY; //贷款申请审核
		ApprovalBiz appbiz = new ApprovalBiz();
		ContractDao cDao = new ContractDao();
		Collection cApproval = null;
		
	
		
		//取得审核的贷款类型
		lLoanTypeID = cDao.getApprovalLoanType(laInfo.getTypeID()); 


		String[] sOpinion = new String[iCount];
		String[] sCheckUser = new String[iCount];
		String[] sCheckDate = new String[iCount];
		for (i=0;i<iCount;i++)
		{
			sOpinion[i] = "";
			sCheckUser[i] = "";
			//sCheckDate[i] = DataFormat.getDateString(Env.getSystemDate());
			sCheckDate[i]="　　  　 　  　   　   　   　   　      ";
		}
		
		//取得审批意见
		//modified by fxzhang 2006-1-3
		
		//for (i=1;i<(iCount+1);i++)
		//{
		//	cApproval = appbiz.findApprovalTracing(lModuleID,lLoanTypeID,lActionID,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,loanID,-1);
		//	
		//	if (cApproval != null)
		//	{
		//		Iterator it = cApproval.iterator();
		//		ApprovalTracingInfo ATInfo = new ApprovalTracingInfo();
		//		if (it.hasNext())
		//		{
		//			ATInfo = (ApprovalTracingInfo)it.next();
		//			if (ATInfo.getOpinion() != null )
		//			{
		//				sOpinion[i-1] = ATInfo.getOpinion() ;//+ "-" + ATInfo.getUserName();
		//				sCheckUser[i-1] = ATInfo.getUserName();
		//				sCheckDate[i-1]=DataFormat.getDateString(ATInfo.getApprovalDate());
		//			}
		//			else
		//			{
		//				Log.print("===2===");
		//				sOpinion[i-1] = "";
		//				sCheckUser[i-1] = "";
		//			}		
		//		}
		//	}
		//}
		
		
			cApproval = appbiz.findLinkedApproval(lModuleID,lLoanTypeID,lActionID,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,loanID,subloanTypeID,1);
			if (cApproval != null)
			{
				Iterator it = cApproval.iterator();
				ApprovalTracingInfo ATInfo = new ApprovalTracingInfo();
				int t=1;
				while(it.hasNext()&&t<(iCount+1))
				{
					ATInfo = (ApprovalTracingInfo)it.next();
					if (ATInfo.getOpinion() != null )
					{
						sOpinion[t-1] = ATInfo.getOpinion() ;//+ "-" + ATInfo.getUserName();
						sCheckUser[t-1] = ATInfo.getUserName();
						//sCheckDate[t-1]=DataFormat.getDateString(ATInfo.getApprovalDate());
						//Log.print(sCheckDate[i-1]);
					}
					else
					{
						sOpinion[t-1] = "";
						sCheckUser[t-1] = "";
						//sCheckDate[i-1] = DataFormat.getDateString(Env.getSystemDate());
						//Log.print(sCheckDate[i-1]);
					}		
					t++;
				}
			}
		request.setAttribute("sOpinion",sOpinion);
		request.setAttribute("sCheckUser",sCheckUser);
		//request.setAttribute("sCheckDate",sCheckDate);
			
		/* 获取上下文环境 */
		ServletContext sc = getServletContext();
	    
		/* 设置返回地址 */
		RequestDispatcher rd=null;
		rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,strURL)));
		
		/* forward到结果页面 */
	    rd.forward(request, response);
		return;
		
     }catch (Exception ie) {
		//LOANHTML.showExceptionMessage(out,sessionMng,ie,request,response,"客户管理", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return; 
     }
%>