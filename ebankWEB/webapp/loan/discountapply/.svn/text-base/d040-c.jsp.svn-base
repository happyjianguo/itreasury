<%@ page contentType="text/html;charset=gbk" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"/>
<jsp:useBean id="Magnifier" scope="request" class="com.iss.itreasury.ebank.util.OBMagnifier"/>
<%@ page import="com.iss.itreasury.util.*,
                com.iss.itreasury.ebank.util.*,
    			com.iss.itreasury.ebank.obdiscountapply.bizlogic.*,
                com.iss.itreasury.ebank.obdiscountapply.dataentity.*,
				com.iss.itreasury.ebank.obdiscountapply.dao.*,
    			java.sql.*,
                java.rmi.RemoteException,
				java.util.*"
%>
<%
 //response.setHeader("Cache-Control","no-stored");
 //response.setHeader("Pragma","no-cache");
 //response.setDateHeader("Expires",0);
%>
<%
	/* 标题固定变量 */
	String strTitle = "[贴现凭证]";
%>		
<%
/////////////////////////////////////////////////////////////////////////////////
	
	try{    
		
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

		//定义变量，获取请求参数

		String strTmp = "";
		long lCredenceID = -1;			//贴现凭证标示
		long lStatusID = -1;            //状态

		//////////////////////////////
		
		//贴现EJB
		OBDiscountApplyHome  obDiscountApplyHome = null;
		OBDiscountApply      obDiscountApply = null;
		//DiscountLoanInfo  dli = new DiscountLoanInfo ();
		obDiscountApplyHome = (OBDiscountApplyHome)EJBObject.getEJBHome("OBDiscountApplyHome");
		obDiscountApply = obDiscountApplyHome.create();
        
///////control///////////////////////////////////////////////////////////////////
//
		strTmp = (String)request.getAttribute("lCredenceID");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 lCredenceID = Long.parseLong(strTmp.trim());
		}
		
		strTmp = (String)request.getAttribute("lStatusID");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 lStatusID = Long.parseLong(strTmp.trim());
		}
		//从指令查询过来的参数
		String txtAction = "";
		if( (String)request.getAttribute("txtAction") != null )
			txtAction = (String)request.getAttribute("txtAction");
			
		//配合指令查询过来的参数,如果为"yes",则表示查询后并修改过
		String txtChanged = "";
		if( (String)request.getAttribute("txtChanged") != null )
			txtChanged = (String)request.getAttribute("txtChanged");
//
//取消操作
		long lResult = -1;
		//OBDiscountApplyDao dao = new OBDiscountApplyDao();
		System.out.println("qqq!!!!!!!!!!!!!===(*******)"+lStatusID);
		lResult = obDiscountApply.updateCredenceStatus(lCredenceID,lStatusID);
		System.out.println("www!!!!!!!!!!!!!===(*******)"+lResult);
		DiscountCredenceQueryInfo qinfo = new DiscountCredenceQueryInfo();
		qinfo.setDiscountCredenceID(lCredenceID);
		DiscountCredenceInfo info = obDiscountApply.findDiscountCredence(qinfo);
		RequestDispatcher rd;
		if (lResult > 0)		
        {
		   //temp = obDiscountApply.findDiscountBillByContractID(qinfo);
		   /* 获取上下文环境 */
	       ServletContext sc = getServletContext();
	       /* 设置返回地址 */
		   //如果是提交
		   if(lStatusID==OBConstant.LoanInstrStatus.SUBMIT)
		   {
		   		rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/discountapply/d045-v.jsp?lID="+lCredenceID+"&strCode="+info.getCode())));
		   }else 
		   {
		   		if(txtAction.equals("modify"))
				{
		   			rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/query/q003-c.jsp")));
		   		}
		  		 else
		   		{
	       			rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/discountapply/d030-v.jsp")));
	       		}
			}
		   /* forward到结果页面 */
	       rd.forward(request, response);
		}
		else 
	    {
		  OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E001");
		  return;
	    }
	}
	catch(IException ie)
    {
		//OBHtml.showExceptionMessage(out,sessionMng,ie,request,response,"贴现凭证", Constant.RecordStatus.VALID);
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"贴现凭证",1);
		ie.printStackTrace();
		out.flush();
		return; 
    }
%>