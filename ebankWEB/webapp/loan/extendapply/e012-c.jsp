<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
	java.util.*,
	java.lang.*,
	java.net.URLEncoder,
	com.iss.itreasury.ebank.obextendapply.bizlogic.*,
	com.iss.itreasury.ebank.obextendapply.dataentity.*,
	com.iss.itreasury.ebank.util.*,
	com.iss.itreasury.loan.util.*,
	com.iss.itreasury.ebank.obrepayplan.dataentity.*,
	com.iss.itreasury.loan.contract.bizlogic.*,
	com.iss.itreasury.loan.contract.dataentity.*,
	com.iss.itreasury.ebank.obdataentity.*,
	com.iss.itreasury.system.approval.bizlogic.*,
	com.iss.itreasury.system.approval.dataentity.*,
	com.iss.itreasury.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"/>

<%
	/* 标题固定变量 */
	String strTitle = "[展期申请]";
%>		

<%
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
                
               //定义变量
               long lContractID = -1;                   //合同ID                
               long lExtendApplyID = -1;                //展期申请ID
               String sTitle = "";                      //页头
			
			   OBSecurityInfo secinfo = new OBSecurityInfo();
               
               //获得展期申请的ID
               if( (String)request.getAttribute("lExtendApplyID")!=null )
               {
                   lExtendApplyID = Long.parseLong((String)request.getAttribute("lExtendApplyID"));
               }
               else
               {
                   lExtendApplyID = -1;
               }
               
               //获得合同的ID
               if( (String)request.getAttribute("lContractID")!=null )
               {
                   lContractID = Long.parseLong((String)request.getAttribute("lContractID"));
               }
               else
               {
                   lContractID = -1;
               }
               
               //展期申请的EJB
               OBExtendApplyHome extendApplyHome = (OBExtendApplyHome)EJBObject.getEJBHome("OBExtendApplyHome");
               OBExtendApply e_ejb = extendApplyHome.create();
               OBExtendApplyInfo e_info = new OBExtendApplyInfo();
               
               //得到展期申请内容
               e_info = e_ejb.findExtendByID(lExtendApplyID,-1,secinfo);
			   System.out.println("listinfo=======================================1"+e_info.getExtendList());
               lContractID = e_info.getContractID();
               //合同的EJB
               //ContractHome contractHome = (ContractHome)EJBObject.getEJBHome("ContractHome");
               //Contract c_ejb = contractHome.create();
               ContractInfo c_info = new ContractInfo();
               
               //得到合同内容
               c_info = e_ejb.findExtendByID(-1,lContractID,secinfo).getC_Info();
               
				request.setAttribute("c_info",c_info);
				request.setAttribute("e_info",e_info);

				int TRACENUMBER=8;
				if( c_info.getLoanTypeID() == LOANConstant.LoanType.WT )
				{
					TRACENUMBER = 5;
				}
				String[] sOpinion = new String[TRACENUMBER];
				String[] sCheckUser = new String[TRACENUMBER];
				String[] sCheckDate = new String[TRACENUMBER];
				for (int i=0; i<TRACENUMBER; i++)
				{
					sOpinion[i] = "";
					sCheckUser[i] = "";
					sCheckDate[i] = DataFormat.getDateString(Env.getSystemDate());
				}
				ApprovalBiz appBiz = new ApprovalBiz();
				ApprovalTracingInfo tracinginfo = new ApprovalTracingInfo();
				
				long lModuleID = Constant.ModuleType.LOAN;
				//模块类型
				long lLoanTypeID = c_info.getLoanTypeID();
				long lActionID = Constant.ApprovalAction.EXTEND_APPLY;
				Collection c = null;
				c = appBiz.findApprovalTracing(lModuleID,lLoanTypeID,lActionID,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,lExtendApplyID,Constant.PageControl.CODE_ASCORDESC_DESC);
				if (c != null) 
				{
					Iterator iter = c.iterator();
					int i=0;
					if(c.size() <= TRACENUMBER)
					{
						i=0;
						while (iter.hasNext() && i < TRACENUMBER) 
						{
							tracinginfo = (ApprovalTracingInfo)iter.next();
							sOpinion[i]=DataFormat.formatString(tracinginfo.getOpinion());
							sCheckUser[i]=DataFormat.formatString(tracinginfo.getUserName());
							sCheckDate[i]=DataFormat.getDateString(tracinginfo.getApprovalDate());
							i++;
						}
						while(i < TRACENUMBER)
						{
							sCheckDate[i] = DataFormat.getDateString(Env.getSystemDate());
							i++;
						}
					}
					else
					{
						i=7;
						while (iter.hasNext() && i>=0) 
						{
							tracinginfo = (ApprovalTracingInfo)iter.next();
							sOpinion[i]=DataFormat.formatString(tracinginfo.getOpinion());
							sCheckUser[i]=DataFormat.formatString(tracinginfo.getUserName());
							sCheckDate[i]=DataFormat.getDateString(tracinginfo.getApprovalDate());
							i--;
						}
					}
				}
				request.setAttribute("sOpinion",sOpinion);
				request.setAttribute("sCheckUser",sCheckUser);
				request.setAttribute("sCheckDate",sCheckDate);
						
				/* 获取上下文环境*/
				ServletContext sc = getServletContext();
				/* 设置返回地址 */
				RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/extendapply/e010-p.jsp")));
				/* forward到结果页面 */
				rd.forward(request, response);
	} 
	catch (IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
	}
%>