<%--
/*
 * 程序名称：s002-c.jsp
 * 功能说明：业务签认查询显示页面
 * 作　　者：kewen hu
 * 完成日期：2004-02-05
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>

<%@ page import="java.util.Iterator"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.*"%>
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
<%@ include file="/common/common.jsp" %>

<%
    //标题变量
    String strTitle = "";
    try {
        //临时量
        String sTemp = null;
        //定义接收Form信息的变量
        QueryCapForm qcf = new QueryCapForm();
        // 网上银行交易类型,资金划拨由专门的类型
        sTemp = (String) request.getParameter("SelectType");
        String sign = "";
        if (sTemp != null && sTemp.trim().length() > 0) {
            qcf.setTransType(Long.parseLong(sTemp));
            Log.print("SelectType:"+sTemp);
        }
		if (qcf.getTransType()==OBConstant.QueryInstrType.CHILDCAPTRANSFER)
		{
			qcf.setChildClientID(-2);
		}
		
        // 提交日期-从
        sTemp = (String) request.getParameter("txtConfirmA");
        if (sTemp != null && sTemp.trim().length() > 0) {
            qcf.setStartSubmit(sTemp);
            Log.print("txtConfirmA:"+sTemp);
        }
        // 提交日期-到
        sTemp = (String) request.getParameter("txtConfirmB");
        if (sTemp != null && sTemp.trim().length() > 0) {
            qcf.setEndSubmit(sTemp);
            Log.print("txtConfirmB:"+sTemp);
        }
        // 交易指令状态
        sTemp = (String) request.getParameter("SelectStatus");
        if (sTemp != null && sTemp.trim().length() > 0) {
            qcf.setStatus(Long.parseLong(sTemp));
            Log.print("SelectStatus:"+sTemp);
        }
        // 交易金额-最小值
        if (request.getParameter("txtMinAmount") != null && (!request.getParameter("txtMinAmount").trim().equalsIgnoreCase(""))) {
            qcf.setMinAmount(Double.parseDouble(DataFormat.reverseFormatAmount(
                request.getParameter("txtMinAmount").trim())));
            Log.print("s002-c.jsp:qcf.dMinAmount="+qcf.getMinAmount());
        }
        // 交易金额-最大值
        if (request.getParameter("txtMaxAmount") != null && (!request.getParameter("txtMaxAmount").trim().equalsIgnoreCase(""))) {//是否中油账户
            qcf.setMaxAmount(Double.parseDouble(DataFormat.reverseFormatAmount(
                request.getParameter("txtMaxAmount").trim())));
            Log.print("s002-c.jsp:qcf.dMaxAmount="+qcf.getMaxAmount());
        }
        // 执行日期-从
        sTemp = (String) request.getParameter("txtExecuteA");
        if (sTemp != null && sTemp.trim().length() > 0) {
            qcf.setStartExe(sTemp);
            Log.print("txtExecuteA:"+sTemp);
        }
        // 执行日期-到
        sTemp = (String) request.getParameter("txtExecuteB");
        if (sTemp != null && sTemp.trim().length() > 0) {
            qcf.setEndExe(sTemp);
            Log.print("txtExecuteB:"+sTemp);
        }
        
        sTemp = (String) request.getParameter("sign");
        if (sTemp != null && sTemp.trim().length() > 0) {
        	sign = sTemp;
        	qcf.setSign(sign);
        }
		
        qcf.setClientID(sessionMng.m_lClientID);
        qcf.setCurrencyID(sessionMng.m_lCurrencyID);
        qcf.setOperationTypeID(OBConstant.QueryOperationType.SIGN);
        qcf.setUserID(sessionMng.m_lUserID);

        //定义流水号
        /* 初始化信息查询类 */
        OBFinanceInstrDao obFinanceInstrDao = new OBFinanceInstrDao();

        //调用类方法
        Collection rs = obFinanceInstrDao.query(qcf);
        Iterator iterator = null;
        if (rs != null) {
            iterator = rs.iterator();
        }
        //在请求中保存结果对象
        request.setAttribute("return",iterator);
        request.setAttribute("FormValue",qcf);
        //获取上下文环境
        //ServletContext sc = getServletContext();
        //设置返回地址
        
        //构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	if(sign.equals("current")){
	pageControllerInfo.setUrl("/capital/sign/s007-v.jsp");
	}else if(sign.equals("fixd")){
	pageControllerInfo.setUrl("/capital/sign/s008-v.jsp");
	}else{
	pageControllerInfo.setUrl("/capital/sign/s001-v.jsp");
	}
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
        
        
        
       
        //forward到结果页面
        rd.forward(request, response);
    } catch(IException ie) {
        OBHtml.showExceptionMessage(out ,sessionMng, ie, strTitle, "", 1);
    }
%>