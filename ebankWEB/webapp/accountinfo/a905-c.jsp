<%--
 页面名称 ：a905-c.jsp
 页面功能 : 链接显示一条记录的控制页面
 作    者 ：qqgd
 日    期 ：
 特殊说明 ：实现操作说明：
				1、查询某一条记录
 修改历史 ：
--%>

<%@ page import="com.iss.itreasury.util.DataFormat" %>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.util.Collection"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.transdiscount.dataentity.*"%>
<%@ page import="com.iss.itreasury.settlement.bizdelegation.TransDiscountDelegation"%>
<%@ page import="com.iss.itreasury.settlement.transdiscount.dao.Sett_TransGrantDiscountDAO"%>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dao.OBCheckTransDirectionDao"%>
<%@ page import="java.sql.Timestamp"%>
<%@page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@page import="com.iss.itreasury.util.PageController"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

	<%

	//页面控制变量
	String strNextPageURL = "";
	String strSuccessPageURL = "../view/v003.jsp";
	String strFailPageURL = "../view/v004.jsp";
	String strAction = null;
	String strActionResult = Constant.ActionResult.FAIL;
	long lnDiscountNoteID=-1;
    
	//定义页面变量
	long lID = -1; // 贴现发放业务ID	
	long nTypeID=-1;
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
		Sett_TransGrantDiscountDAO delegation=new Sett_TransGrantDiscountDAO();
			
		
		//为修改页面查询数据
		String strTemp="";

		strTemp=(String)request.getAttribute("nTypeID");

		if (strTemp != null && strTemp.trim().length()>0 )
		{
			nTypeID=Long.valueOf(strTemp).longValue();
		}

        
		
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
			//lID = delegation.grantGetIDByTransNo(strTransNo);getIDByTransNo
			lID = delegation.getIDByTransNo(strTransNo);
		}
		Log.print("lID = " + lID);
		
		/*----------------为了判断收付款方而设-----------*/
		OBCheckTransDirectionDao obdao= new OBCheckTransDirectionDao();
		long lOBReturn = obdao.CheckAccountID(strTransNo, "", sessionMng.m_lClientID, sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
		request.setAttribute("lReturn",String.valueOf(lOBReturn));
		/*--------end of qqgd adding fragment---------------*/
		
		if (lID > 0)
		{
			//根据请求操作，完成业务处理的调用
			//TransGrantDiscountInfo info = delegation.grantFindDetailByID(lID);	
			TransGrantDiscountInfo info = delegation.findByID(lID);	
			if(info!=null)
			{
				request.setAttribute("TransDiscountInfo",info);
				request.setAttribute("matchResult",info);
				strActionResult=Constant.ActionResult.SUCCESS;
			}
			else
			{
				//sessionMng.getActionMessages().addMessage("查询不到数据！");		   				
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
	request.setAttribute("nTypeID",String.valueOf(nTypeID));
	
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