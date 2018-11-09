<%
/**
 * 页面名称 ：q001-c.jsp
 * 页面功能 : 指令查询分类察看详细信息控制类
 */
%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,java.util.*,java.net.URLEncoder,
			com.iss.itreasury.loan.util.*,
			com.iss.itreasury.loan.loancommonsetting.dataentity.*,
			com.iss.itreasury.loan.loancommonsetting.bizlogic.*,
			com.iss.itreasury.ebank.obloanapply.dataentity.*,
			com.iss.itreasury.ebank.obloanapply.bizlogic.*,
			com.iss.itreasury.ebank.obsystem.bizlogic.*,
			com.iss.itreasury.ebank.util.*,	
			com.iss.itreasury.ebank.obdataentity.*,			
			com.iss.itreasury.ebank.obquery.dataentity.*,
			com.iss.itreasury.ebank.obquery.bizlogic.*,
			com.iss.itreasury.util.*"
%>
<%@ include file="/common/common.jsp" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
    try{
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
		long instrTypeID=GetNumParam(request,"instrTypeID",-1);
		long parentID=GetNumParam(request,"parentID",-1);
		String strURL="";
		
		switch ((int)instrTypeID)
		{
			case (int)OBConstant.LoanInstrType.LOAN_ZYDQ:
			//case (int)OBConstant.LoanInstrType.LOAN_ZYZCQ:
			case (int)OBConstant.LoanInstrType.LOAN_WT:
			//case (int)OBConstant.LoanInstrType.LOAN_WTTJTH:
			case (int)OBConstant.LoanInstrType.LOAN_ZGXEDQ:
			//case (int)OBConstant.LoanInstrType.LOAN_ZGXEZCQ:
			case (int)OBConstant.LoanInstrType.ASSURE:
			/*此处因为贷款申请模块为弹出窗口保存后刷新，因此做此改动 */
			
			long loanID=parentID;
			long userID=sessionMng.m_lUserID;
			strURL="/loan/loanapply/l029-v.jsp?lLoanID="+loanID
				+"&nOrderParam=1"
				+"&nDesc=1"
				+"&nPageNo=1";
			long lCurrencyID=sessionMng.m_lCurrencyID;
			long lUserID=sessionMng.m_lUserID;
			long lOfficeID=sessionMng.m_lOfficeID;
			OBSecurityInfo sInfo=new OBSecurityInfo();
			sInfo.setCurrencyID(lCurrencyID);
			sInfo.setOfficeID(lOfficeID);
			sInfo.setUserID(lUserID);
				
			OBLoanApplyHome home = (OBLoanApplyHome)EJBObject.getEJBHome("OBLoanApplyHome");
			OBLoanApply lla = home.create();
		
			OBSystemHome home2 = (OBSystemHome)EJBObject.getEJBHome("OBSystemHome");
			OBSystem lcs = home2.create();		
			OBLoanApplyInfo laInfo=lla.findByID(loanID,sInfo);	

			long statusID=laInfo.getStatusID();
			long inputUserID=laInfo.getInputUserID();
			if ( (statusID==OBConstant.LoanInstrStatus.SAVE)||(statusID==OBConstant.LoanInstrStatus.SUBMIT) )
			{
				if (inputUserID==sessionMng.m_lUserID )
				{
					strURL="/loan/loanapply/l037-v.jsp?lLoanID="+loanID
						+"&nOrderParam=1"
						+"&nDesc=1"
						+"&nPageNo=1";
				}
			}
		
				OBPageInfo pageInfo=new OBPageInfo();
				pageInfo.setPageNo(1);
				pageInfo.setOrderParam(1);
				pageInfo.setDesc(1);
			
				//查询计划信息
				Collection c=lla.findPlanByLoanID(loanID,pageInfo,sInfo);
				long clientID=laInfo.getBorrowClientID();
				long wtClientID=laInfo.getConsignClientID();
		
				ClientInfo clientInfo=lcs.findClientByID(clientID);
				ClientInfo wtClientInfo=lcs.findClientByID(wtClientID);
		
				request.setAttribute("LoanApplyInfo",laInfo);
				request.setAttribute("wtClientInfo",wtClientInfo);
				request.setAttribute("Collection",c);
				request.setAttribute("ClientInfo",clientInfo);
				/*
				strURL="/loan/loanapply/l028-c.jsp";
				request.setAttribute("lLoanID",String.valueOf(parentID));
				*/
				break;
			case (int)OBConstant.LoanInstrType.DISCOUNT:
				strURL="/loan/discountapply/d017-c.jsp";
				request.setAttribute("lID",String.valueOf(parentID));
				request.setAttribute("lStatusID",""+OBConstant.LoanInstrStatus.SAVE);
				break;
			case (int)OBConstant.LoanInstrType.DISCOUNTCREDENCE:
				strURL="/loan/discountapply/d033-c.jsp";
				request.setAttribute("lCredenceID",String.valueOf(parentID));
				request.setAttribute("nextPage","d037-v.jsp");
				break;
			case (int)OBConstant.LoanInstrType.EXTEND:
				strURL="/loan/extendapply/e002-c.jsp";
				request.setAttribute("lExtendID",String.valueOf(parentID));
				request.setAttribute("txtSearch","modify");
				break;				
			case (int)OBConstant.LoanInstrType.FREE:
				strURL="/loan/freeapply/f002-c.jsp";
				request.setAttribute("strAction","edit");
				request.setAttribute("lFreeApplyID",""+parentID);
				break;
			case (int)OBConstant.LoanInstrType.DUEBILL:
				strURL="/loan/paynotice/p002-c.jsp";
				request.setAttribute("strAction","modify");
				request.setAttribute("lPayID",""+parentID);
				break;
			case (int)OBConstant.LoanInstrType.AHEADPAY:
				strURL="/loan/aheadrepaynotice/a002-c.jsp";
				request.setAttribute("lID",String.valueOf(parentID));
				break;				
		}
		request.setAttribute("txtAction","modify");
		request.setAttribute("frompage","query");
		
		/**
		* 操作结果置为成功
		*/
		
		
		
		
		ServletContext sc = getServletContext();
		RequestDispatcher rd=null;
		rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,strURL)));

	    	rd.forward(request, response);
	
		return;
	
     }catch (IException ie) {
		OBHtml.showExceptionMessage(out,sessionMng,ie,"客户管理","", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return; 
     }
%>