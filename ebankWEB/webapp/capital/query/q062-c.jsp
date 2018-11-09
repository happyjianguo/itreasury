<%--
/*
 * 程序名称：q002-c.jsp
 * 功能说明：交易申请查询控制页面
 * 作　　者：kewen hu
 * 完成日期：2004-02-11
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
<%@ page import="com.iss.itreasury.util.EJBObject"%>
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
    String strTitle = "［交易申请查询］";
    try {
        long lTransType = -1;       // 网上银行交易类型
        long lContractID = -1;      // 合同ID
        String strContractNo = "";  // 合同号
		long lChildClientID = -1;//下属单位
		String sChildClientNo = "";//下属单位
        long lDepositID = -1;       // 存款单据ID
        String strDepositNo = "";   //存款单据号
        String strStartSubmit = ""; // 提交日期-从
        String strEndSubmit = "";   // 提交日期-到
        long lStatus = -1;          // 交易指令状态
        double dMinAmount = 0.00;   // 交易金额-最小值
        double dMaxAmount = 0.00;   // 交易金额-最大值
        String strStartExe = "";    // 执行日期-从
        String strEndExe = "";      // 执行日期-到
        String sNextSuccessPage = "";

        String sTemp = null;    //临时量
        sTemp = (String)request.getParameter("sNextSuccessPage");
        if(sTemp != null && sTemp.trim().length() > 0) {
            sNextSuccessPage = sTemp;
            Log.print("sNextSuccessPage=" + sNextSuccessPage);
        }
        sTemp = (String) request.getParameter("lTransType");
        if(sTemp != null && sTemp.trim().length() > 0) {
            lTransType = Long.parseLong(sTemp); // 网上银行交易类型
            if (lTransType == 0) {
                lTransType = -1;
            }
            Log.print("交易类型=" + lTransType);
        }
        sTemp = (String) request.getParameter("lStatus");
        if(sTemp != null && sTemp.trim().length() > 0) {
            lStatus = Long.parseLong(sTemp); // 交易指令状态
            Log.print("交易指令状态=" + lStatus);
        }
        sTemp = (String) request.getParameter("sStartSubmit");
        if(sTemp != null && sTemp.trim().length() > 0) {
            strStartSubmit = sTemp; // 提交日期-从
            Log.print("提交日期-从=" + strStartSubmit);
        }
        sTemp = (String) request.getParameter("sEndSubmit");
        if(sTemp != null && sTemp.trim().length() > 0) {
            strEndSubmit = sTemp; // 提交日期-到
            Log.print("提交日期-到=" + strEndSubmit);
        }
        sTemp = (String) request.getParameter("sStartExe");
        if(sTemp != null && sTemp.trim().length() > 0) {
            strStartExe = sTemp; // 执行日期-从
            Log.print("执行日期-从=" + strStartExe);
        }
        sTemp = (String) request.getParameter("sEndExe");
        if(sTemp != null && sTemp.trim().length() > 0) {
            strEndExe = sTemp; // 执行日期-到
            Log.print("执行日期-到=" + strEndExe);
        }
        sTemp = (String) request.getParameter("dMinAmount");
        if(sTemp != null && sTemp.trim().length() > 0) {
            dMinAmount = Double.parseDouble(DataFormat.reverseFormatAmount(sTemp)); // 金额最小值
            Log.print("金额最小值=" + dMinAmount);
        }
        sTemp = (String) request.getParameter("dMaxAmount");
        if(sTemp != null && sTemp.trim().length() > 0) {
            dMaxAmount = Double.parseDouble(DataFormat.reverseFormatAmount(sTemp)); // 金额最大值
            Log.print("金额最大值=" + dMaxAmount);
        }
        switch ((int)lTransType) {
            case (int)OBConstant.QueryInstrType.FIXEDTOCURRENTTRANSFER:
                sTemp = (String) request.getParameter("lFixedDepositID");
                if(sTemp != null && sTemp.trim().length() > 0) {
                    lDepositID = Long.parseLong(sTemp); // 定期存款单据ID
                    Log.print("定期存款单据ID=" + lDepositID);
                }
                sTemp = (String) request.getParameter("lFixedDepositIDCtrl");
                if(sTemp != null && sTemp.trim().length() > 0) {
                    strDepositNo = sTemp; // 定期存款单据号
                    Log.print("定期存款单据号=" + strDepositNo);
                }
                break;
            case (int)OBConstant.QueryInstrType.NOTIFYDEPOSITDRAW:
                sTemp = (String) request.getParameter("lNotifyDepositID");
                if(sTemp != null && sTemp.trim().length() > 0) {
                    lDepositID = Long.parseLong(sTemp); // 通知存款单据ID
                    Log.print("通知存款单据ID=" + lDepositID);
                }
                sTemp = (String) request.getParameter("lNotifyDepositIDCtrl");
                if(sTemp != null && sTemp.trim().length() > 0) {
                    strDepositNo = sTemp; // 通知存款单据号
                    Log.print("通知存款单据号=" + strDepositNo);
                }
                break;
            case (int)OBConstant.QueryInstrType.TRUSTLOANRECEIVE:
                sTemp = (String) request.getParameter("lLoanContractID");
                if(sTemp != null && sTemp.trim().length() > 0) {
                    lContractID = Long.parseLong(sTemp); // 合同ID(自营贷款清还)
                    Log.print("合同ID(自营贷款清还)=" + lContractID);
                }
                sTemp = (String) request.getParameter("lLoanContractIDCtrl");
                if(sTemp != null && sTemp.trim().length() > 0) {
                    strContractNo = sTemp; // 合同号(自营贷款清还)
                    Log.print("合同号(自营贷款清还)=" + strContractNo);
                }
                break;
            case (int)OBConstant.QueryInstrType.CONSIGNLOANRECEIVE:
                sTemp = (String) request.getParameter("lSettContractID");
                if(sTemp != null && sTemp.trim().length() > 0) {
                    lContractID = Long.parseLong(sTemp); // 合同ID(委托贷款清还)
                    Log.print("合同ID(委托贷款清还)=" + lContractID);
                }
                sTemp = (String) request.getParameter("lSettContractIDCtrl");
                if(sTemp != null && sTemp.trim().length() > 0) {
                    strContractNo = sTemp; // 合同号(委托贷款清还)
                    Log.print("合同号(委托贷款清还)=" + strContractNo);
                }
                break;
            case (int)OBConstant.QueryInstrType.INTERESTFEEPAYMENT:
                sTemp = (String) request.getParameter("lRateContractID");
                if(sTemp != null && sTemp.trim().length() > 0) {
                    lContractID = Long.parseLong(sTemp); // 合同ID(利息费用清还)
                    Log.print("合同ID(利息费用清还)=" + lContractID);
                }
                sTemp = (String) request.getParameter("lRateContractIDCtrl");
                if(sTemp != null && sTemp.trim().length() > 0) {
                    strContractNo = sTemp; // 合同号(利息费用清还)
                    Log.print("合同号(利息费用清还)=" + strContractNo);
                }
                break;
			case (int)OBConstant.QueryInstrType.CHILDCAPTRANSFER:
                sTemp = (String) request.getParameter("lChildClientID");
                if(sTemp != null && sTemp.trim().length() > 0) {
                    lChildClientID = Long.parseLong(sTemp); 
                    Log.print("下属单位ID=" + lChildClientID);
                }
				if (lChildClientID<=0)
				{
					lChildClientID = -2;
				}
                sTemp = (String) request.getParameter("txtClientCode");
                if(sTemp != null && sTemp.trim().length() > 0) {
                    sChildClientNo = sTemp; 
                    Log.print("下属单位编号=" + sChildClientNo);
                }
                break;
            default :
                break;
        }

        /* 初始化查询信息类 */		
        QueryCapForm queryCapForm = new QueryCapForm();
		if (lTransType==OBConstant.QueryInstrType.CHILDCAPTRANSFER)
		{
			queryCapForm.setChildClientID(lChildClientID);
			queryCapForm.setChildClientNo(sChildClientNo);
			request.setAttribute("child", "1");
		}
        queryCapForm.setTransType(lTransType);          // 网上银行交易类型
        queryCapForm.setStatus(lStatus);                // 交易指令状态
        queryCapForm.setStartSubmit(strStartSubmit);    // 提交日期-从
        queryCapForm.setEndSubmit(strEndSubmit);        // 提交日期-到
        queryCapForm.setStartExe(strStartExe);          // 执行日期-从
        queryCapForm.setEndExe(strEndExe);              // 执行日期-到
        queryCapForm.setMinAmount(dMinAmount);          // 金额最小值
        queryCapForm.setMaxAmount(dMaxAmount);          // 金额最大值
        queryCapForm.setContractID(lContractID);        // 合同ID
        queryCapForm.setDepositID(lDepositID);          // 存款单据ID
        queryCapForm.setContractNo(strContractNo);      // 合同号
        queryCapForm.setDepositNo(strDepositNo);        // 存款单据号

        /* 从session中获取相应数据 */
        queryCapForm.setClientID(sessionMng.m_lClientID);
        queryCapForm.setCurrencyID(sessionMng.m_lCurrencyID);
        queryCapForm.setUserID(sessionMng.m_lUserID);
        /* 根据页面菜单确定查询类型 */
        queryCapForm.setOperationTypeID(OBConstant.QueryOperationType.QUERY);
Log.print("===========OperationTypeID="+queryCapForm.getOperationTypeID());
        queryCapForm.setOrderBy(true);
        /* 初始化EJB */
        OBFinanceInstrDao financeInstrDao = new OBFinanceInstrDao();
        /* 调用EJB方法获得查询结果 */
        Collection cltQcf = financeInstrDao.query(queryCapForm);

        /* 在请求中保存结果对象 */
        request.setAttribute("cltQcf", cltQcf);
        request.setAttribute("queryCapForm", queryCapForm);
        /* 获取上下文环境 */
        //ServletContext sc = getServletContext();
        /* 设置返回地址 */
        RequestDispatcher rd = null;
        
        //构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	
        
        if (sNextSuccessPage == null || "".equals(sNextSuccessPage)) {
           
            
            pageControllerInfo.setUrl("/capital/query/q061-v.jsp");
 rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
        } else {
            
       
       pageControllerInfo.setUrl("/capital/query/" + sNextSuccessPage);
 rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
       
        }

        /* forward到结果页面 */
        rd.forward(request, response);
    } catch(IException ie) {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    }
%>