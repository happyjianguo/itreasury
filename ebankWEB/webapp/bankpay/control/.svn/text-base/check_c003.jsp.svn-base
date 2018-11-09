<%--
/*
 * 程序名称：check_c006.jsp
 * 功能说明：交易指令详细信息察看控制页面
 * 作　　者：
 * 完成日期：2010-10-12
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>

<%@ page import="com.iss.itreasury.util.*"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"%>

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
    long lID = 0;           //指令序号
    sTemp = (String) request.getParameter("txtID");
    if (sTemp != null && sTemp.trim().length() > 0) {
        lID = Long.parseLong(sTemp);
    }
    Log.print("lID:"+lID);
    String strSuccessPageURL = "";           //成功页
    sTemp = (String) request.getParameter("strSuccessPageURL");
    if (sTemp != null && sTemp.trim().length() > 0) {
        strSuccessPageURL = sTemp;
    }
    
    request.setAttribute("search",request.getParameter("search"));
    System.out.println("search------------"+(String)request.getAttribute("search"));
    request.setAttribute("strAction", "query");
    
    String strFrom = null;  //forward页面    
    strFrom = "../view/check_v003.jsp?src=1";
    
    //审批流跳转页面
    if(strSuccessPageURL != null && !"".equals(strSuccessPageURL))
    {
            strFrom = strSuccessPageURL;  
    }

    try {
       	//OBFinanceInstrEJB financeInstr=new OBFinanceInstrEJB();
       	/* 初始化EJB */
		OBFinanceInstrHome financeInstrHome = null;
		OBFinanceInstr financeInstr = null;
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		financeInstr = financeInstrHome.create();
       	/* 初始化信息查询类*/
        //OBBankPayDao obBankPayDao = new  OBBankPayDao();
       	OBBankPayInfo info = new OBBankPayInfo();
       	/*调用类中方法查询信息对象*/
       	info=financeInstr.findByID(lID);
       	if (info == null || strFrom == null) {//为空显示出错页面
          	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E001");
         	return;
     	}

        //在请求中保存结果对象
       	request.setAttribute("info",info);
        //获取上下文环境
        //ServletContext sc = getServletContext();
        //设置返回地址
        
       //构建页面分发时需要用到的实体
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		System.out.println("############################^^^^^^^^^^^^^^^^^^^^^^^^^^^^" + strFrom);
        pageControllerInfo.setUrl(strFrom);
		//分发
		RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	       
	    //forward到结果页面
	    rd.forward(request, response);
    } catch(IException ie) {
        //出错页面
       OBHtml.showExceptionMessage(out, sessionMng, ie, strTitle, "", 1);
    }
%>