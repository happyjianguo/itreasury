<%--
/*
 * 程序名称：q003-c.jsp
 * 功能说明：交易指令详细信息察看控制页面
 * 作　　者：kewen hu
 * 完成日期：2004-02-12
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
    request.setAttribute("strAction", "query");
    String strFrom = null;  //forward页面
    switch ((int)iTransType) {
	case (int)OBConstant.SettInstrType.APPLYCAPITAL://1;资金申领
        strFrom = "/capital/captransfer/c014-v.jsp?src=1";
        break;
    case (int)OBConstant.SettInstrType.CAPTRANSFER_SELF://1;资金划拨-本转
        strFrom = "/capital/captransfer/c004-v.jsp?src=1";
        break;
    case (int)OBConstant.SettInstrType.CAPTRANSFER_BANKPAY://2;资金划拨-银行付款
        strFrom = "/capital/captransfer/c004-v.jsp?src=1";
        break;
    case (int)OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT://3;资金划拨-内部转账
        strFrom = "/capital/captransfer/c004-v.jsp?src=1";
        break;
	case (int)OBConstant.SettInstrType.CAPTRANSFER_OTHER://3;资金划拨-其他
        strFrom = "/capital/captransfer/c004-v.jsp?src=1";
        break;
	case (int)OBConstant.SettInstrType.CHILDCAPTRANSFER://3;下属单位资金划拨
        strFrom = "/capital/captransfer/c004-v.jsp?src=1";
		request.setAttribute("child", "1");
        break;
    case (int)OBConstant.SettInstrType.OPENFIXDEPOSIT://4;定期开立
        strFrom = "/capital/fixed/f004-v.jsp?src=1";
        break;
    case (int)OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER://5;定期支取
        strFrom = "/capital/fixed/f014-v.jsp?src=1";
        break;
    case (int)OBConstant.SettInstrType.OPENNOTIFYACCOUNT://6;通知开立
        strFrom = "/capital/notify/n004-v.jsp?src=1";
        break;
    case (int)OBConstant.SettInstrType.NOTIFYDEPOSITDRAW://7;通知支取
        strFrom = "/capital/notify/n014-v.jsp?src=1";
        break;
    case (int)OBConstant.SettInstrType.TRUSTLOANRECEIVE://8;自营贷款清还
        strFrom = "/capital/loanrepayment/l006-v.jsp?src=1";
        break;
    case (int)OBConstant.SettInstrType.CONSIGNLOANRECEIVE://9;委托贷款清还
        strFrom = "/capital/loanrepayment/l016-v.jsp?src=1";
        break;
    case (int)OBConstant.SettInstrType.INTERESTFEEPAYMENT://10;利息费用清还
        strFrom = "/capital/loanrepayment/l026-v.jsp?src=1";
        break;
	case (int)OBConstant.SettInstrType.YTLOANRECEIVE://11;银团贷款清还
        strFrom = "/capital/loanrepayment/l006-v.jsp?src=1&isYT=1";
        break;
    default :
        OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E001");
        return;
    }

    try {
        /* 初始化信息查询类 */
        OBFinanceInstrDao obFinanceInstrDao = new OBFinanceInstrDao();
        FinanceInfo info = new FinanceInfo();
        /*调用类中方法查询信息对象*/
        info = obFinanceInstrDao.findByID(
            lID,sessionMng.m_lUserID,sessionMng.m_lCurrencyID);//如果没有这个lID流水号则info ＝null
        if (info == null || strFrom == null) {//为空显示出错页面
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E001");
            return;
        }
		
		ClientAccountInfo accountInfo=null;
		OBFinanceInstrDao dao = new OBFinanceInstrDao();
		//if (financeInfo!=null)	accountID=financeInfo.getPayerAcctID();
		accountInfo=dao.findAccountInfoByClient(sessionMng.m_lUserID, sessionMng.m_lClientID, -1, 1);
		
		request.setAttribute("accountInfo",accountInfo);


        //在请求中保存结果对象
        request.setAttribute("financeInfo",info);
        request.setAttribute("resultInfo",info);
        //获取上下文环境
        //ServletContext sc = getServletContext();
        //设置返回地址
       
        
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