<%--
 页面名称 ：c054.jsp
 页面功能 : 多笔贷款收回处理控制页面
 作    者 ：Barry
 日    期 ：
 特殊说明 ：实现操作说明：
				1、业务复核
				2、业务取消复核
 修改历史 ：
--%>


<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.util.Collection"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.util.UtilOperation"%>
<%@ page import="com.iss.itreasury.settlement.transloan.dataentity.TransRepaymentLoanInfo"%>
<%@ page import="com.iss.itreasury.settlement.bizdelegation.TransLoanDelegation"%>
<%@ page import="com.iss.itreasury.settlement.transloan.dao.Sett_TransRepaymentLoanDAO"%>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dao.OBCheckTransDirectionDao"%>
<%@page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@page import="com.iss.itreasury.util.PageController"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
	//---------页面控制变量
    String strNextPageURL					 = "";
	String strSuccessPageURL				 = "";
	String strFailPageURL					 = "";
	String strAction						 = null;
	String strActionResult					 = Constant.ActionResult.FAIL;
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


		/**
		 * 取得页面控制参数
		 */
		strAction = (String)request.getAttribute("strAction");
		strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
		strFailPageURL = (String)request.getAttribute("strFailPageURL");

        /**
         * 定义变量		
         */
		long lCheckUserID			= sessionMng.m_lUserID;					//复核人
		String strCheckAbstract 	= "";									//复核备注
		java.sql.Timestamp tsModify = null;									//修改时间
		long lID 					= -1;									//记录ID
		long lStatusID 				= -1;									//交易状态
		String strTransNo           = "";                                   //交易号
		long lSerialNo				= -1;									//序列号

		String strTemp = null;												//获得参数中转变量
		strTemp = (String)request.getAttribute("lID");						//记录的ID
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("strCheckAbstract");			//复核备注
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strCheckAbstract = strTemp;
		}

		strTemp = (String)request.getAttribute("tsModify");					//修改时间
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    tsModify = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("lStatusID");				//状态
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lStatusID = Long.valueOf(strTemp).longValue();
		}
		
		strTemp = (String)request.getAttribute("strTransNo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strTransNo = strTemp;
		}
		strTemp = (String)request.getAttribute("lSerialNo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lSerialNo = Long.valueOf(strTemp.trim()).longValue();
		}
		
		//TransLoanDelegation loanDelegation = new TransLoanDelegation();				//delegation
		Sett_TransRepaymentLoanDAO transLoanDelegation = new Sett_TransRepaymentLoanDAO();

		//System.out.println(strAction);
		if("Query".equals(strAction))//修改
		{
			if ("Query".equals(strAction) && strTransNo != null && !strTransNo.equals("") && lSerialNo>0)
			{
				//lID = loanDelegation.repaymentGetIDByTransNoAndSerialNo(strTransNo,lSerialNo);
				lID=transLoanDelegation.getIDByTransNoAndSerialNo(strTransNo,lSerialNo);
				//System.out.println("strTransNo"+strTransNo+"-----lSerialNo"+lSerialNo);
				//lID=transLoanDelegation.getIDByTransNo(strTransNo);
			}
			lID=transLoanDelegation.getIDByTransNo(strTransNo);
		}
		
		/*----------------为了判断收付款方而设-----------*/
		OBCheckTransDirectionDao obdao= new OBCheckTransDirectionDao();
		long lOBReturn = obdao.CheckAccountID(strTransNo, ""+lSerialNo, sessionMng.m_lClientID, sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
		request.setAttribute("lReturn",String.valueOf(lOBReturn));
		/*--------end of qqgd adding fragment---------------*/

		
		Log.print("当前的ID:"+lID);
		TransRepaymentLoanInfo loanInfo = null;
		//loanInfo = loanDelegation.repaymentFindDetailByID(lID);						//获得还款交易记录		
		loanInfo = transLoanDelegation.findByID(lID);
		Log.print("获得还款交易成功");
		if(loanInfo == null)
		{
			throw new Exception("系统错误");
		}
		else
		{
			loanInfo.setModify(tsModify);
		} 

		
		/**
		 * 将数据填写到request中，后续代码执行发生异常时跳转到view页面可以完成数据回显
		 */
		UtilOperation.setDataentityToRequest(request,loanInfo);
		request.setAttribute("lRecordStatusID",""+lStatusID);			//存入原来的记录状态
        
        if(String.valueOf(SETTConstant.Actions.CHECK).equals(strAction))
        {/*
			Log.print("操作 -- 复核");
			loanInfo.setID(lID);
			loanInfo.setCheckAbstract(strCheckAbstract);
			loanInfo.setCheckUserID(lCheckUserID);
			
			Log.print("委托贷款ID:"+loanInfo.getID());
			Log.print("委托贷款复核人:"+loanInfo.getCheckUserID());
			Log.print("委托贷款描述:"+loanInfo.getCheckAbstract());
			
			long lReturn = loanDelegation.repaymentCheck(loanInfo);
			
            if(lReturn > 0)
            {
                strActionResult = Constant.ActionResult.SUCCESS;
                sessionMng.getActionMessages().addMessage("复核成功");
            }
            */
        }
		else if(String.valueOf(SETTConstant.Actions.CANCELCHECK).equals(strAction))
        {/*
			Log.print("操作 -- 取消复核");
			loanInfo.setID(lID);
			loanInfo.setCheckAbstract(strCheckAbstract);
			loanInfo.setCheckUserID(lCheckUserID);
			Log.print("ID = "+loanInfo.getID());
			
			long lReturn = loanDelegation.repaymentCancelCheck(loanInfo);
			
            if(lReturn > 0)
            {
                strActionResult = Constant.ActionResult.SUCCESS;
                sessionMng.getActionMessages().addMessage("取消复核成功");
				strSuccessPageURL = "../control/c052.jsp";
				request.setAttribute("strAction",String.valueOf(SETTConstant.Actions.LINKSEARCH));
            }
           */ 
        }
		else if ("Query".equals(strAction))
		{
			switch((int)loanInfo.getTransDirectionID()){
				case (int)SETTConstant.MultiLoanType.PAYMENT:{
					strSuccessPageURL = "../accountinfo/a921-v.jsp";
					break;
				}
				case (int)SETTConstant.MultiLoanType.TRUSTLOAN:{
					strSuccessPageURL = "../accountinfo/a922-v.jsp";
					break;
				}
				case (int)SETTConstant.MultiLoanType.CONSIGNLOAN:{
					strSuccessPageURL = "../accountinfo/a923-v.jsp";
					break;
				}
			}
			strActionResult = Constant.ActionResult.SUCCESS;
		}
		else
        {
            Log.print("无效操作");
        }
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		//sessionMng.getActionMessages().addMessage(exp);
		strActionResult = Constant.ActionResult.FAIL;	
	}

	request.setAttribute("strActionResult",strActionResult);

	strNextPageURL = (Constant.ActionResult.SUCCESS.equals(strActionResult))?strSuccessPageURL:strFailPageURL;

	Log.print("Next Page URL:"+strNextPageURL);	
	//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextURL);
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL

(pageControllerInfo));
	rd.forward( request,response );
%>
