<%--
/*
 * 程序名称：c110.jsp
 * 功能说明：交易指令详细信息察看控制页面
 * 作　　者：niweinan
 * 完成日期：2010-10-21
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>

<%@ page import="java.util.Iterator"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dao.*"%>
<jsp:directive.page import="com.iss.itreasury.util.PageController"/>
<jsp:directive.page import="com.iss.itreasury.util.PageControllerInfo"/>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<% String strContext = request.getContextPath();%>
<%
    //标题变量
    String strTitle = "［交易申请查询］";
    //临时量
    String sTemp = null;
    long lShowMenu = OBConstant.ShowMenu.YES;
    sTemp = (String)request.getParameter("menu");
    if ((sTemp != null) && (sTemp.equals("hidden"))) {
        lShowMenu = OBConstant.ShowMenu.NO;
    }
    request.setAttribute("menu", sTemp);
    Log.print("menu:"+lShowMenu);
    long iTransType = 0;     //交易类型
    sTemp = (String) request.getParameter("txtTransType");
    if (sTemp != null && sTemp.trim().length() > 0) {
        iTransType = Long.parseLong(sTemp);
    }
    Log.print("txtTransType:"+iTransType);
    long lID = 0;           //指令序号
    sTemp = (String) request.getParameter("txtID");
    if (sTemp != null && sTemp.trim().length() > 0) {
        lID = Long.parseLong(sTemp);
    }
   
    Log.print("lID:"+lID);
    String strReturn = "";           //下一页
    sTemp = (String) request.getParameter("strReturn");
    if (sTemp != null && sTemp.trim().length() > 0) {
        strReturn = sTemp;
    }
    Log.print("strReturn:"+strReturn);
    request.setAttribute("strReturn", strReturn);
    String strSuccessPageURL = "";           //成功页
    sTemp = (String) request.getParameter("strSuccessPageURL");
    if (sTemp != null && sTemp.trim().length() > 0) {
        strSuccessPageURL = sTemp;
    }
    
    String strFailPageURL = "";           //失败页
    sTemp = (String) request.getParameter("strFailPageURL");
    if (sTemp != null && sTemp.trim().length() > 0) {
        strFailPageURL = sTemp;
    }
    
    //从"我的工作"传递的控制变量
	String strTempAction = "";
	if (request.getParameter("strTempAction") != null)
	{
		strTempAction = (String)request.getParameter("strTempAction");
		request.setAttribute("strTempAction", strTempAction);
	}
    
    request.setAttribute("search",request.getParameter("search"));
    System.out.println("search------------"+(String)request.getAttribute("search"));
    request.setAttribute("strAction", "query");
    String strFrom = null;  //forward页面
    strFrom = "/bankpay/view/v004.jsp?src=1";
      
    
    
    
    //审批流跳转页面
	    if(strSuccessPageURL != null && !"".equals(strSuccessPageURL))
	    {
	            strFrom = strSuccessPageURL;  
	    }

    try {
        /* 初始化信息查询类 */
        OBFinanceInstrBiz biz = new OBFinanceInstrBiz();
     //   FinanceInfo info = new FinanceInfo();
        OBBankPayInfo oBBankPayInfo = new OBBankPayInfo();
        /*调用类中方法查询信息对象*/
       oBBankPayInfo = biz.findEbankById(lID);
       
        	
        
        
        if (oBBankPayInfo == null || strFrom == null) {//为空显示出错页面
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E001");
            return;
        }

        //在请求中保存结果对象
        request.setAttribute("obbankpayinfo",oBBankPayInfo);
   //     request.setAttribute("financeInfo",info);
    //    request.setAttribute("resultInfo",info);
        //获取上下文环境
        //ServletContext sc = getServletContext();
        //设置返回地址
        
       
       
       //构建页面分发时需要用到的实体
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		System.out.println("############################^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+strContext + strFrom);
		pageControllerInfo.setUrl(strContext + strFrom);
		//分发
		
		RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	       
	    //forward到结果页面
	    rd.forward(request, response);
    } catch(IException ie) {
        //出错页面
        OBHtml.showExceptionMessage(out, sessionMng, ie, strTitle, "", 1);
    }
%>