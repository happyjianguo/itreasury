<%--
/*
 * 程序名称：s003-c.jsp
 * 功能说明：交易指令详细信息察看控制页面
 * 作　　者：xfma
 * 完成日期：2011-04-19
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>

<%@ page import="com.iss.itreasury.util.*"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
    //标题变量
    String strTitle = "[业务签认]";
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
    Log.print("SelectType:"+iTransType);
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
    request.setAttribute("strAction", "query");
    String strFrom = "/bankpay/view/s102-v.jsp?src=1";  //forward页面

    try {
        /* 初始化EJB */
		OBFinanceInstrHome financeInstrHome = null;
		OBFinanceInstr financeInstr = null;
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		financeInstr = financeInstrHome.create();
        OBBankPayInfo info = new OBBankPayInfo();
        /*调用类中方法查询信息对象*/
        info = financeInstr.findByID(lID);//如果没有这个lID流水号则info ＝null
        if (info == null || strFrom == null) {//为空显示出错页面
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E001");
            return;
        }
        //在请求中保存结果对象
        request.setAttribute("info",info);
        request.setAttribute("resultInfo",info);
        request.setAttribute("lIsCanSign",String.valueOf(1));
        request.setAttribute("txtTransType",new Long(iTransType).toString());
        
        //构建页面分发时需要用到的实体
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
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