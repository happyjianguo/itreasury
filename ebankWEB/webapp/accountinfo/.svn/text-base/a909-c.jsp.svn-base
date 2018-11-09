<%--
 页面名称 ：a909-c.jsp
 页面功能 : 复核 链接显示一条记录的控制页面
 作    者 ：qqgd
 日    期 ：
 特殊说明 ：实现操作说明：
				1、查询某一条记录
 修改历史 ：
--%>

<%//@ page import="com.iss.itreasury.settlement.util.EJBHomeFactory" %>
<%@ page import="com.iss.itreasury.settlement.transdiscount.bizlogic.TransDiscountHome" %>


<%@ page import="com.iss.itreasury.util.DataFormat" %>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.util.Collection"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.transdiscount.dataentity.*"%>
<%@ page import="com.iss.itreasury.settlement.bizdelegation.TransDiscountDelegation"%>
<%@ page import="com.iss.itreasury.settlement.transdiscount.dao.Sett_TransRepaymentDiscountDAO"%>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dao.OBCheckTransDirectionDao"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="com.iss.itreasury.util.Env" %>
<%@page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@page import="com.iss.itreasury.util.PageController"%>
<% String strContext = request.getContextPath();%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<script language="JavaScript" src="/webob/js/Control.js" ></script>
<script language="JavaScript" src="/webob/js/SettCheck.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script> 

	<%
System.out.println("\n\n 进入 c017.jsp \n\n");
	//页面控制变量
	String strNextPageURL = "";
	String strSuccessPageURL = "../view/v016.jsp";
	String strFailPageURL = "../view/v017.jsp";
	String strAction = null;
	String strActionResult = Constant.ActionResult.FAIL;
	long lnDiscountNoteID=-1;
    
	//定义页面变量
	long lID = -1; // 贴现发放业务ID	
	String strTransNo = "";
	
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
		
		//TransDiscountDelegation delegation=new TransDiscountDelegation();
		Sett_TransRepaymentDiscountDAO loanDelegation = new Sett_TransRepaymentDiscountDAO();
		
		
		
		//为修改页面查询数据
		String strTemp="";
		strTemp=(String)request.getAttribute("strAction");
		if (strTemp != null && strTemp.trim().length()>0 )
		{
			strAction = strTemp;
		}
		
		if ("Query".equals(strAction))
		{
			strTemp=(String)request.getAttribute("strTransNo");
			if (strTemp != null && strTemp.trim().length()>0 )
			{
				strTransNo = strTemp;
			}
	        strAction = (String)request.getAttribute("strAction");
			strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
			strFailPageURL = (String)request.getAttribute("strFailPageURL");
		}
		
        strTemp=(String)request.getAttribute("lID");
		if (strTemp != null && strTemp.trim().length()>0 )
		{
		    lID = Long.valueOf(strTemp).longValue();
		}
		
		if (strTransNo != null && !strTransNo.equals(""))
		{
			//lID = delegation.repaymentGetIDByTransNo(strTransNo);
			lID=loanDelegation.getIDByTransNo(strTransNo);
		}
		Log.print("lID = " + lID);
		
		/*----------------为了判断收付款方而设-----------*/
		OBCheckTransDirectionDao obdao= new OBCheckTransDirectionDao();
		long lOBReturn = obdao.CheckAccountID(strTransNo, "", sessionMng.m_lClientID, sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
		request.setAttribute("lReturn",String.valueOf(lOBReturn));
		Log.print("lOBReturn="+lOBReturn);
		/*--------end of qqgd adding fragment---------------*/
		
		if(lID > 0)
		{
			//根据请求操作，完成业务处理的调用
			//TransRepaymentDiscountInfo info = delegation.repaymentFindDetailByID(lID);	
			TransRepaymentDiscountInfo info=loanDelegation.findByID(lID);
			if(info!=null)
			{
				request.setAttribute("matchResult",info);
				strActionResult=Constant.ActionResult.SUCCESS;
			}
			else
			{
               // sessionMng.getActionMessages().addMessage("查询不到数据！");	     				
			    strActionResult=Constant.ActionResult.SUCCESS;
			}
		}	
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		//sessionMng.getActionMessages().addMessage(exp);
		strActionResult = Constant.ActionResult.FAIL;		
	}	
	request.setAttribute("strActionResult",strActionResult);
	//根据处理结果设置下一步跳转的目标页面
	strNextPageURL = (Constant.ActionResult.SUCCESS.equals(strActionResult))?strSuccessPageURL:strFailPageURL;
	//转向下一页面
	//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextURL);
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL

(pageControllerInfo));

	rd.forward( request,response);
%>